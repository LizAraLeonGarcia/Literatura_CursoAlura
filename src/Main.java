import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class BookCatalogApplication implements CommandLineRunner {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final BookService bookService = new BookService();
    private static List<Book> searchedBooks = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(BookCatalogApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        showMenu();
    }

    private void showMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Mostrar todos los libros buscados");
            System.out.println("3. Listar autores de los libros buscados");
            System.out.println("4. Listar autores vivos en un año específico");
            System.out.println("5. Salir");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Ingrese el título del libro:");
                    String searchTerm = scanner.nextLine();
                    try {
                        Book book = fetchBookByTitle(searchTerm);
                        if (book != null) {
                            searchedBooks.add(book);
                            System.out.println(book);
                        } else {
                            System.out.println("No se encontró ningún libro con ese título.");
                        }
                    } catch (IOException | InterruptedException e) {
                        System.out.println("Ocurrió un error al buscar el libro: " + e.getMessage());
                    }
                    break;
                case 2:
                    if (searchedBooks.isEmpty()) {
                        System.out.println("No se han buscado libros todavía.");
                    } else {
                        searchedBooks.forEach(System.out::println);
                    }
                    break;
                case 3:
                    listAuthorsOfSearchedBooks();
                    break;
                case 4:
                    System.out.println("Ingrese el año para verificar autores vivos:");
                    int year = scanner.nextInt();
                    listAuthorsAliveInYear(year);
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }

        scanner.close();
    }

    private static Book fetchBookByTitle(String title) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://gutendex.com/books?search=" + title))
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Unexpected response code: " + response.statusCode());
        }

        return bookService.parseSingleBook(response.body());
    }

    private void listAuthorsOfSearchedBooks() {
        if (searchedBooks.isEmpty()) {
            System.out.println("No se han buscado libros todavía.");
        } else {
            System.out.println("Autores de los libros buscados:");
            for (Book book : searchedBooks) {
                System.out.println(book.getAuthor());
            }
        }
    }

    private void listAuthorsAliveInYear(int year) {
        if (searchedBooks.isEmpty()) {
            System.out.println("No se han buscado libros todavía.");
        } else {
            System.out.println("Autores vivos en el año " + year + ":");
            for (Book book : searchedBooks) {
                Book.Author author = book.getAuthor();
                if (author != null && author.isAliveInYear(year)) {
                    System.out.println(author);
                }
            }
        }
    }
}
