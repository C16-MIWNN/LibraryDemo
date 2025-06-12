package nl.miwnn.ch16.vincent.librarydemo.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Vincent Velthuizen
 * Purpose for the class
 */
class BookTest {

    @Test
    void getNumberOfCopiesWithDifferingAvailability() {
        // Arrange
        Book book = new Book();
        int expectedNumberOfCopies = 3;

        addCopiesToBook(expectedNumberOfCopies, expectedNumberOfCopies - 2, book);

        // Act
        int actualNumberOfCopies = book.getNumberOfCopies();

        // Assert
        assertEquals(expectedNumberOfCopies, actualNumberOfCopies);
    }

    @Test
    @DisplayName("Available Copies when all copies are available")
    void availableCopiesWhenAllCopiesAreAvailable() {
        // Arrange
        Book book = new Book();
        int expectedNumberOfAvailableCopies = 3;

        addCopiesToBook(expectedNumberOfAvailableCopies, expectedNumberOfAvailableCopies, book);

        // Act
        int actualNumberOfAvailableCopies = book.getNumberOfAvailableCopies();

        // Assert
        assertEquals(expectedNumberOfAvailableCopies, actualNumberOfAvailableCopies);
    }

    @Test
    @DisplayName("Available Copies when no copies are available")
    void availableCopiesWhenNoCopiesAreAvailable() {
        // Arrange
        Book book = new Book();
        int numberOfCopies = 5;
        int expectedNumberOfAvailableCopies = 0;

        addCopiesToBook(numberOfCopies, expectedNumberOfAvailableCopies, book);

        // Act
        int actualNumberOfAvailableCopies = book.getNumberOfAvailableCopies();

        // Assert
        assertEquals(expectedNumberOfAvailableCopies, actualNumberOfAvailableCopies);
    }

    @Test
    @DisplayName("Available copies when some copies are available")
    void availableCopiesWhenSomeCopiesAreAvailable() {
        // Arrange
        Book book = new Book();
        int numberOfCopies = 5;
        int expectedNumberOfAvailableCopies = 3;

        addCopiesToBook(numberOfCopies, expectedNumberOfAvailableCopies, book);

        // Act
        int actualNumberOfAvailableCopies = book.getNumberOfAvailableCopies();

        // Assert
        assertEquals(expectedNumberOfAvailableCopies, actualNumberOfAvailableCopies);
    }

    private void addCopiesToBook(int expectedNumberOfCopies, int availableCopies, Book book) {
        book.setCopies(new HashSet<>());
        for (int i = 0; i < expectedNumberOfCopies; i++) {
            Copy copy = new Copy();
            copy.setBook(book);
            copy.setAvailable(availableCopies-- > 0);
            book.getCopies().add(copy);
        }
    }
}