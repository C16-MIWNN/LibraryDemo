package nl.miwnn.ch16.vincent.librarydemo.service;

import nl.miwnn.ch16.vincent.librarydemo.dto.NewLibraryUserDTO;
import nl.miwnn.ch16.vincent.librarydemo.model.LibraryUser;
import nl.miwnn.ch16.vincent.librarydemo.repositories.LibraryUserRepository;
import nl.miwnn.ch16.vincent.librarydemo.service.mappers.LibraryUserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vincent Velthuizen
 */

@Service
public class LibraryUserService implements UserDetailsService {
    private final LibraryUserRepository libraryUserRepository;
    private final PasswordEncoder passwordEncoder;

    public LibraryUserService(LibraryUserRepository libraryUserRepository, PasswordEncoder passwordEncoder) {
        this.libraryUserRepository = libraryUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return libraryUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User: %s was not found.", username)));
    }

    public void saveUser(LibraryUser libraryUser) {
        libraryUser.setPassword(passwordEncoder.encode(libraryUser.getPassword()));
        libraryUserRepository.save(libraryUser);
    }

    public List<LibraryUser> getAllUsers() {
        return libraryUserRepository.findAll();
    }

    public boolean usernameInUse(String username) {
        return libraryUserRepository.existsByUsername(username);
    }

    public void save(NewLibraryUserDTO userDtoToBeSaved) {
        saveUser(LibraryUserMapper.fromDTO(userDtoToBeSaved));
    }
}
