package nl.miwnn.ch16.vincent.librarydemo.model;

import jakarta.persistence.*;

import java.util.Set;

/**
 * @author Vincent Velthuizen
 * An entity that is responsible for the writing of a book
 */

@Entity
public class Author implements Comparable<Author> {
    @Id
    @GeneratedValue
    private Long authorId;

    private String name;

    private String imageUrl;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;

    @Override
    public int compareTo(Author otherAuthor) {
        return this.name.compareTo(otherAuthor.name);
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
