package infra.repository;

import domain.model.Book;
import domain.repository.IBookRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BookRepositoryAdapter implements PanacheRepositoryBase<Book, Long>, IBookRepository {

    @Override
    public List<Book> getAll() {
        return listAll();
    }

    @Override
    public Optional<Book> getById(Long id) {
        return findByIdOptional(id);
    }

    @Override
    public void save(Book book) {
        persist(book);
    }

    @Override
    public void delete(Long id) {
        deleteById(id);
    }
}
