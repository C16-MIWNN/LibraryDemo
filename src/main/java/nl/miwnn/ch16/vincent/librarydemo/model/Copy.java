package nl.miwnn.ch16.vincent.librarydemo.model;

import jakarta.persistence.*;

/**
 * @author Vincent Velthuizen
 * Represents a copy of a book that the library can lend out
 */

@Entity
public class Copy {
    @Id @GeneratedValue
    private Long copyId;

    private Boolean available;

    @ManyToOne
    private Book book;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Long getCopyId() {
        return copyId;
    }

    public void setCopyId(Long copyId) {
        this.copyId = copyId;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
