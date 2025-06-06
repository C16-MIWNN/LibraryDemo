package nl.miwnn.ch16.vincent.librarydemo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

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

    @OneToMany(mappedBy = "book")
    private List<Copy> copies;

    public int getNumberOfCopies() {
        return copies.size();
    }

    public int getNumberOfAvailableCopies() {
        int count = 0;

        for (Copy copy : copies) {
            if (copy.getAvailable()) {
                count++;
            }
        }

        return count;
    }

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

    public List<Copy> getCopies() {
        return copies;
    }
}
