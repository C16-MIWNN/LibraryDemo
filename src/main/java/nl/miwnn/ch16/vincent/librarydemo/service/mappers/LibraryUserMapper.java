package nl.miwnn.ch16.vincent.librarydemo.service.mappers;

import nl.miwnn.ch16.vincent.librarydemo.dto.NewLibraryUserDTO;
import nl.miwnn.ch16.vincent.librarydemo.model.LibraryUser;

/**
 * @author Vincent Velthuizen
 * Converts NewLibraryUserDTO Objects into LibraryUsers
 */
public class LibraryUserMapper {

    public static LibraryUser fromDTO(NewLibraryUserDTO newLibraryUserDTO) {
        LibraryUser libraryUser = new LibraryUser();

        libraryUser.setUsername(newLibraryUserDTO.getUsername());
        libraryUser.setPassword(newLibraryUserDTO.getPassword());

        return libraryUser;
    }

}
