import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    @JsonAlias("title")
    private String title;

    @JsonAlias("author")
    private Author author;

    @JsonAlias("languages")
    private List<String> languages;

    @JsonAlias("download_count")
    private int downloadCount;

    // Getters y setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    // toString para imprimir el libro
    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author=" + author +
                ", languages=" + languages +
                ", downloadCount=" + downloadCount +
                '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Author {
        @JsonAlias("name")
        private String name;

        @JsonAlias("birth_year")
        private Integer birthYear;

        @JsonAlias("death_year")
        private Integer deathYear;

        // Getters y setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getBirthYear() {
            return birthYear;
        }

        public void setBirthYear(Integer birthYear) {
            this.birthYear = birthYear;
        }

        public Integer getDeathYear() {
            return deathYear;
        }

        public void setDeathYear(Integer deathYear) {
            this.deathYear = deathYear;
        }

        @Override
        public String toString() {
            return name;
        }

        // Método para verificar si el autor está vivo en un año específico
        public boolean isAliveInYear(int year) {
            return birthYear != null && (deathYear == null || deathYear > year);
        }
    }
}
