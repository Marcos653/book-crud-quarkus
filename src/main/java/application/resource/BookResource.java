package application.resource;

import application.dto.BookRequest;
import application.dto.BookResponse;
import application.resource.contract.IBookResource;
import domain.service.contract.IBookService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BookResource implements IBookResource {

    private final IBookService service;

    @Override
    public List<BookResponse> getAll() {
        return service.getAll();
    }

    @Override
    public BookResponse getById(Long id) {
        return service.getById(id);
    }

    @Override
    public void create(BookRequest bookRequest) {
        service.create(bookRequest);
    }

    @Override
    public BookResponse update(Long id, BookRequest bookRequest) {
        return service.update(id, bookRequest);
    }

    @Override
    public void delete(Long id) {
        service.delete(id);
    }
}
