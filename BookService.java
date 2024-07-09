import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;

public class BookService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Book> parseBooks(String jsonResponse) throws IOException {
        return objectMapper.readValue(jsonResponse, objectMapper.getTypeFactory().constructCollectionType(List.class, Book.class));
    }

    public Book parseSingleBook(String jsonResponse) throws IOException {
        List<Book> books = objectMapper.readValue(jsonResponse, objectMapper.getTypeFactory().constructCollectionType(List.class, Book.class));
        return books.isEmpty() ? null : books.get(0);
    }
}
