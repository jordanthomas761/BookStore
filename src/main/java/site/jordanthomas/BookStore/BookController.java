package site.jordanthomas.BookStore;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class BookController {

    private final BookRepository repository;

    private final BookRepresentationModelAssembler assembler;

    BookController(BookRepository repository, BookRepresentationModelAssembler assembler){
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping(value={"/books", "/"})
    CollectionModel<EntityModel<Book>> all(){
        List<EntityModel<Book>> books = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(books, linkTo(methodOn(BookController.class).all()).withSelfRel());
    }

    @PostMapping("/books")
    ResponseEntity<?> newBook(@RequestBody Book newBook) throws URISyntaxException {
        EntityModel<Book> bookEntityModel = assembler.toModel(repository.save(newBook));

        return ResponseEntity.created(new URI(bookEntityModel.getLink("self").get().getHref())).body(bookEntityModel);
    }

    // Aggregate root

    @GetMapping("/books/{id}")
    EntityModel<Book> one(@PathVariable Long id) throws BookNotFoundException {
        Book book = repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        return assembler.toModel(book);
    }

    @PutMapping("/books/{id}")
    ResponseEntity<?> replaceBook(@RequestBody Book newBook, @PathVariable Long id) throws URISyntaxException {

        Book updatedBook = repository.findById(id)
                .map(book -> {
                    book.setAuthor(newBook.getAuthor());
                    book.setTitle(newBook.getTitle());
                    book.setPrice(newBook.getPrice());
                    return repository.save(book);
                })
                .orElseGet(() -> {
                    newBook.setId(id);
                    return repository.save(newBook);
                });

        EntityModel<Book> bookEntityModel = assembler.toModel(updatedBook);

        return ResponseEntity.created(new URI(bookEntityModel.getLink("self").get().getHref())).body(bookEntityModel);
    }

    @DeleteMapping("/books/{id}")
    ResponseEntity<?> deleteBook(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
