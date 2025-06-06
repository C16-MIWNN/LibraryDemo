package nl.miwnn.ch16.vincent.librarydemo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * @author Vincent Velthuizen
 * The concept of a book for which the library can have copies
 */

@Entity
public class Book {
    @Id @GeneratedValue
    private Long bookId;

    private String title;
    private String author;

    @Override
    public String toString() {
        return String.format("%s - %s", this.title, this.author);
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
