package htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface IAuthorRepository extends CrudRepository<Author, UUID> {
    Author findByFirstNameOrLastName(String firstName, String lastName);
}
