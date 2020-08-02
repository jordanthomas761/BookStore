package site.jordanthomas.BookStore;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookRepresentationModelAssembler implements RepresentationModelAssembler<Book, EntityModel<Book>> {

    @Override
    public EntityModel<Book> toModel(Book book) {
        return new EntityModel<>(book,
                linkTo(methodOn(BookController.class).one(book.getId())).withSelfRel(),
                linkTo(methodOn(BookController.class).all()).withRel("books"));
    }
}
