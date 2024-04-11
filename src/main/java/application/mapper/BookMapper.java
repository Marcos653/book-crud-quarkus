package application.mapper;

import application.dto.BookRequest;
import application.dto.BookResponse;
import domain.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {

    BookMapper BOOK_MAPPER = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "id", source = "book.id")
    @Mapping(target = "title", source = "book.title")
    @Mapping(target = "author", source = "book.author")
    BookResponse mapToBookResponse(Book book);

    @Mapping(target = "title", source = "request.title")
    @Mapping(target = "author", source = "request.author")
    Book mapToBook(BookRequest request);

    void updateBookFromDto(BookRequest bookRequest, @MappingTarget Book book);
}
