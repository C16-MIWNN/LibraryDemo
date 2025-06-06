package nl.miwnn.ch16.vincent.librarydemo.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

/**
 * @author Vincent Velthuizen
 * The concept of a book for which the library can have copies
 */

@Entity
public class Book {
    @Id @GeneratedValue
    private Long bookId;

    private String title;

    @ManyToMany
    private Set<Author> authors;

    @OneToMany(mappedBy = "book")
    private Set<Copy> copies;

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

    public String getAuthorNames() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Author author : authors) {
            stringBuilder.append(author.getName()).append(", ");
        }

        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return String.format("%s", this.title);
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

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Copy> getCopies() {
        return copies;
    }

    public void setCopies(Set<Copy> copies) {
        this.copies = copies;
    }
}
