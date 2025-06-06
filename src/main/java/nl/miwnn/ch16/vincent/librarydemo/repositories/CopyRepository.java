package nl.miwnn.ch16.vincent.librarydemo.repositories;

import nl.miwnn.ch16.vincent.librarydemo.model.Copy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CopyRepository extends JpaRepository<Copy, Long> {
}
