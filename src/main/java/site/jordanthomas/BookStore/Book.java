package site.jordanthomas.BookStore;

import lombok.Data;
import lombok.Generated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class Book {
    private @Id @GeneratedValue Long id;
    private String title;
    private String author;
    private BigDecimal price;

    public Book() {}

    public Book(String title, String author, BigDecimal price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }
}
