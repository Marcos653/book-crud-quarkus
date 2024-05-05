package helper;

import application.dto.BookRequest;
import application.dto.BookResponse;
import domain.model.Book;

public class BookHelper {

    public static Book createBook(Long id, String title, String author) {
        return new Book(id, title, author);
    }

    public static BookRequest createBookRequest(String title, String author) {
        return new BookRequest(title, author);
    }

    public static BookResponse createBookResponse(Long id, String title, String author) {
        return new BookResponse(id, title, author);
    }
}
