package domain.repository;

import domain.model.Book;

import java.util.List;
import java.util.Optional;

public interface IBookRepository {

    List<Book> getAll();

    Optional<Book> getById(Long id);

    void save(Book book);

    void delete(Long id);
}
