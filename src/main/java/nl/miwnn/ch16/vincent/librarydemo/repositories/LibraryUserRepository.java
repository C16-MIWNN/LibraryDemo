package nl.miwnn.ch16.vincent.librarydemo.repositories;

import nl.miwnn.ch16.vincent.librarydemo.model.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibraryUserRepository extends JpaRepository<LibraryUser, Long> {
    Optional<LibraryUser> findByUsername(String username);
}
