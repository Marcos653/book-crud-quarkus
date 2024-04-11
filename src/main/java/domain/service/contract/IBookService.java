package domain.service.contract;

import application.dto.BookRequest;
import application.dto.BookResponse;

import java.util.List;

public interface IBookService {

    List<BookResponse> getAll();

    BookResponse getById(Long id);

    void create(BookRequest bookRequest);

    BookResponse update(Long id, BookRequest bookRequest);

    void delete(Long id);
}
