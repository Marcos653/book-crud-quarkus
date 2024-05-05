package domain;

import application.dto.BookRequest;
import application.dto.BookResponse;
import domain.model.Book;
import domain.repository.IBookRepository;
import domain.service.contract.IBookService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static helper.BookHelper.createBook;
import static helper.BookHelper.createBookRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@QuarkusTest
class BookServiceTest {

    private static final String BOOK_NOT_FOUND_MESSAGE = "Book not found with id ";
    private final long nonexistentId = 2L;

    @Inject
    private IBookService bookService;
    @InjectMock
    private IBookRepository bookRepository;

    private Book book;
    private BookRequest bookRequest;
    private List<Book> bookList;

    @BeforeEach
    void setUp() {
        book = createBook(1L, "The Hobbit", "J.R.R. Tolkien");
        bookRequest = createBookRequest("The Hobbit", "J.R.R. Tolkien");
        bookList = List.of(book);
    }

    @Test
    @DisplayName("Should return all books as a list not empty")
    void getAllBooks_shouldReturnListOfBooks_whenBooksExist() {
        when(bookRepository.getAll())
                .thenReturn(bookList);

        List<BookResponse> result = bookService.getAll();

        assertFalse(result.isEmpty());
        verify(bookRepository).getAll();
    }

    @Test
    @DisplayName("Should get a booky by id")
    void getBookById_shouldReturnBook_whenBookExists() {
        when(bookRepository.getById(book.getId()))
                .thenReturn(Optional.of(book));

        BookResponse result = bookService.getById(book.getId());

        assertNotNull(result);
        verify(bookRepository).getById(book.getId());
    }

    @Test
    @DisplayName("getBookById Should throw exception when book does not exist")
    void getBookById_shouldThrowException_whenBookDoesNotExist() {
        when(bookRepository.getById(nonexistentId))
                .thenReturn(Optional.empty());

        EntityNotFoundException error = assertThrows(EntityNotFoundException.class,
                () -> bookService.getById(nonexistentId));

        assertEquals(BOOK_NOT_FOUND_MESSAGE + nonexistentId, error.getMessage());
        verify(bookRepository).getById(nonexistentId);
    }

    @Test
    @DisplayName("Should save a book")
    void createBook_shouldSaveNewBook() {
        doNothing()
                .when(bookRepository)
                .save(book);

        bookService.create(bookRequest);

        verify(bookRepository).save(any(Book.class));
    }

    @Test
    @DisplayName("Should update a book")
    void updateBook_shouldReturnUpdatedBook_whenBookExists() {
        when(bookRepository.getById(book.getId()))
                .thenReturn(Optional.of(book));
        doNothing()
                .when(bookRepository)
                .save(book);

        BookResponse result = bookService.update(book.getId(), bookRequest);

        assertNotNull(result);
        verify(bookRepository).getById(book.getId());
        verify(bookRepository).save(book);
    }

    @Test
    @DisplayName("update Should throw exception when book does not exist")
    void updateBook_shouldThrowException_whenBookDoesNotExist() {
        when(bookRepository.getById(nonexistentId))
                .thenReturn(Optional.empty());

        EntityNotFoundException error = assertThrows(EntityNotFoundException.class,
                () -> bookService.update(nonexistentId, bookRequest));

        assertEquals(BOOK_NOT_FOUND_MESSAGE + nonexistentId, error.getMessage());
        verify(bookRepository).getById(nonexistentId);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void deleteBook_shouldDeleteBook_whenBookExists() {
        when(bookRepository.getById(book.getId()))
                .thenReturn(Optional.of(book));
        doNothing()
                .when(bookRepository)
                .delete(book.getId());

        bookService.delete(book.getId());

        verify(bookRepository).delete(book.getId());
    }

    @Test
    @DisplayName("delete Should throw exception when book does not exist")
    void deleteBook_shouldThrowException_whenBookDoesNotExist() {
        when(bookRepository.getById(nonexistentId))
                .thenReturn(Optional.empty());

        EntityNotFoundException error = assertThrows(EntityNotFoundException.class,
                () -> bookService.delete(nonexistentId));

        assertEquals(BOOK_NOT_FOUND_MESSAGE + nonexistentId, error.getMessage());
        verify(bookRepository).getById(nonexistentId);
        verify(bookRepository, never()).delete(nonexistentId);
    }
}
