package domain.service;

import application.dto.BookRequest;
import application.dto.BookResponse;
import domain.model.Book;
import domain.repository.IBookRepository;
import domain.service.contract.IBookService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static application.mapper.BookMapper.BOOK_MAPPER;

@ApplicationScoped
@RequiredArgsConstructor
public class BookService implements IBookService {

    private static final String BOOK_ID_NOT_FOUND = "Book not found with id ";

    private final IBookRepository repository;

    @Override
    public List<BookResponse> getAll() {
        return repository
                .getAll()
                .stream()
                .map(BOOK_MAPPER::mapToBookResponse)
                .toList();
    }

    @Override
    public BookResponse getById(Long id) {
        return BOOK_MAPPER.mapToBookResponse(getBookById(id));
    }

    @Override
    @Transactional
    public void create(BookRequest bookRequest) {
        repository.save(BOOK_MAPPER.mapToBook(bookRequest));
    }

    @Override
    @Transactional
    public BookResponse update(Long id, BookRequest bookRequest) {
        var book = getBookById(id);

        BOOK_MAPPER.updateBookFromDto(bookRequest, book);
        repository.save(book);

        return BOOK_MAPPER.mapToBookResponse(book);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.delete(id);
    }

    private Book getBookById(Long id) {
        return repository.getById(id)
                .orElseThrow(() -> new EntityNotFoundException(BOOK_ID_NOT_FOUND + id));
    }
}
