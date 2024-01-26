package htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface IAuthorRepository extends CrudRepository<Author, UUID> {
<<<<<<< Updated upstream
=======
    Author findByFirstNameOrLastName(String firstName, String lastName);

    Iterable<Author> findByDistrict(String district);

    Iterable<Author> findByPlz(String plz);
>>>>>>> Stashed changes
}
