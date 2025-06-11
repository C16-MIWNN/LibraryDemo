package nl.miwnn.ch16.vincent.librarydemo.controller;

import nl.miwnn.ch16.vincent.librarydemo.model.Author;
import nl.miwnn.ch16.vincent.librarydemo.model.Book;
import nl.miwnn.ch16.vincent.librarydemo.model.Copy;
import nl.miwnn.ch16.vincent.librarydemo.repositories.AuthorRepository;
import nl.miwnn.ch16.vincent.librarydemo.repositories.BookRepository;
import nl.miwnn.ch16.vincent.librarydemo.repositories.CopyRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Vincent Velthuizen
 * Set some intitial data in the database for (manual) testing purposes.
 */

@Controller
public class InitializeController {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;
    
    public InitializeController(AuthorRepository authorRepository, BookRepository bookRepository, CopyRepository copyRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.copyRepository = copyRepository;
    }

    @EventListener
    private void seed(ContextRefreshedEvent ignoredEvent) {
        if (authorRepository.count() == 0) {
            intializeDB();
        }
    }

    private void intializeDB() {
        Author brandon = makeAuthor("Brandon Sanderson", "https://gamingbolt.com/wp-content/uploads/2021/12/Brandon-Sanderson.jpg");
        Author tolkien = makeAuthor("J.R.R. Tolkien", "https://cdn.britannica.com/65/66765-050-63A945A7/JRR-Tolkien.jpg");
        Author patrick = makeAuthor("Patrick Rothfuss", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7f/Patrick-rothfuss-2014-kyle-cassidy.jpg/500px-Patrick-rothfuss-2014-kyle-cassidy.jpg");

        Book hobbit = makeBook("The Hobbit", "The Hobbit, or There and Back Again is a children's fantasy novel by the English author J. R. R. Tolkien. It was published in 1937 to wide critical acclaim, being nominated for the Carnegie Medal and awarded a prize from the New York Herald Tribune for best juvenile fiction. The book is recognized as a classic in children's literature and is one of the best-selling books of all time, with over 100 million copies sold.", "https://images.thenile.io/r1000/9780261103283.jpg", tolkien);
        makeCopies(hobbit, 3, 1);

        Book lotr = makeBook("The Lord of the Rings", "The Lord of the Rings is an epic high fantasy novel by the English author and scholar J. R. R. Tolkien. Set in Middle-earth, the story began as a sequel to Tolkien's 1937 children's book The Hobbit, but eventually developed into a much larger work. Written in stages between 1937 and 1949, The Lord of the Rings is one of the best-selling books ever written, with over 150 million copies sold.", "https://www.bibdsl.co.uk/imagegallery/bookdata/cd427/9780261103252.JPG", tolkien);
        makeCopies(lotr, 4, 2);

        Book finalEmpire = makeBook("The Final Empire", "Mistborn: The Final Empire, also known simply as Mistborn or The Final Empire, is a fantasy novel written by American author Brandon Sanderson. It was published on July 17, 2006, by Tor Books and is the first novel in the Mistborn trilogy, followed by The Well of Ascension in 2007 and The Hero of Ages in 2008.", "https://upload.wikimedia.org/wikipedia/en/4/44/Mistborn-cover.jpg", brandon);
        makeCopies(finalEmpire, 2, 0);

        Book well = makeBook("The Well of Ascension", "Mistborn: The Well of Ascension is a fantasy novel written by American author Brandon Sanderson. It was published on August 21, 2007, by Tor Books and is the second novel in the Mistborn trilogy. It is preceded by The Final Empire in 2006 and followed by The Hero of Ages in 2008.", "https://upload.wikimedia.org/wikipedia/en/7/7b/Mistborn-_The_Well_of_Ascension_by_Brandon_Sanderson.jpg", brandon);
        makeCopies(well, 2, 1);

        Book hero = makeBook("The Hero of Ages", "Mistborn: The Hero of Ages is an epic fantasy novel written by American author Brandon Sanderson. It was published on October 14, 2008, by Tor Books and is the third and final novel in the Mistborn trilogy. It is preceded by The Well of Ascension in 2007 and followed by The Alloy of Law in the Mistborn: Era 2 series, Wax and Wayne in 2011.", "https://upload.wikimedia.org/wikipedia/en/b/bb/The_Hero_of_Ages_-_Book_Three_of_Mistborn.png", brandon);
        makeCopies(hero, 2, 2);

        Book name = makeBook("The Name of the Wind", "The Name of the Wind, also referred to as The Kingkiller Chronicle: Day One, is a heroic fantasy novel written by American author Patrick Rothfuss. It is the first book in the ongoing fantasy trilogy The Kingkiller Chronicle, followed by The Wise Man's Fear. It was published on March 27, 2007, by DAW Books.", "https://upload.wikimedia.org/wikipedia/en/5/56/TheNameoftheWind_cover.jpg", patrick);
        makeCopies(name, 4, 1);

        Book fear = makeBook("The Wise Man's Fear", "The Wise Man's Fear is a fantasy novel written by American author Patrick Rothfuss and the second volume in The Kingkiller Chronicle. It was published on March 1, 2011, by DAW Books. It is the sequel to 2007's The Name of the Wind.", "https://upload.wikimedia.org/wikipedia/en/7/7b/The_Wise_Man%27s_Fear.jpg", patrick);
        makeCopies(fear, 3, 1);

        makeBook("The Doors of Stone", """
                Book 3 in The Kingkiller Chronicle series, titled The Doors of Stone, is coming, it is just a matter of when.\s
                
                Written by American author Patrick Rothfuss, the Kingkiller novels take readers on an adventure in an original fantasy world, following an adventurer and musician named Kvothe.\s
                
                The first two books (The Name of the Wind and The Wise Man's Fear) were released in 2007 and 2011, respectively. It has been more than a decade since the last book in the series hit store shelves.""", "https://images.thedirect.com/media/photos/doors_of_stone.jpg", patrick);

        Book collaboration = makeBook("The Final Name of the Rings", "A fantasy fantasy book written by three famous fantasy authors", "https://thebookcoverdesigner.com/wp-content/uploads/2015/02/fantasy_cover.bmp", tolkien, brandon, patrick);
        makeCopies(collaboration, 5, 5);

    }

    private Author makeAuthor(String name, String imageUrl) {
        Author author = new Author();
        author.setName(name);
        author.setImageUrl(imageUrl);
        authorRepository.save(author);
        return author;
    }

    private Book makeBook(String title, String description, String imageUrl, Author ... authors) {
        Book book = new Book();

        book.setTitle(title);
        book.setDescription(description);
        book.setImageUrl(imageUrl);

        Set<Author> authorSet = new HashSet<>(Arrays.asList(authors));
        book.setAuthors(authorSet);

        bookRepository.save(book);
        return book;
    }

    private Copy makeCopy(Book book, boolean available) {
        Copy copy = new Copy();

        copy.setBook(book);
        copy.setAvailable(available);

        copyRepository.save(copy);
        return copy;
    }

    private void makeCopies(Book book, int numberOfCopies, int numberUnavailable) {
        for (int i = 0; i < numberOfCopies; i++) {
            makeCopy(book, numberUnavailable-- <= 0);
        }
    }
}
