package library.model;

import java.util.List;

public interface Library {
    void addNewBook(Book book);
    void deleteBook(Book book);
    List<Author> showAllAuthor();
    boolean containsAuthor(Author author);
    List<Book> findAllBookByAuthor(Author author);
    List<Book> findAllBookByPlace(Place place);
}
