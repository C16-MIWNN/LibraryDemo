package nl.miwnn.ch16.vincent.librarydemo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * @author Vincent Velthuizen
 * An entity that is responsible for the writing of a book
 */

@Entity
public class Author {

    @Id
    @GeneratedValue
    private Long authorId;

    private String name;

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
}
