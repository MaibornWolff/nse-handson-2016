package de.maibornwolff.microservices.shout.repository;

import de.maibornwolff.microservices.shout.model.RoomAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Andreas Jochem, MaibornWolff GmbH
 */
public interface RoomAccountRepository extends MongoRepository<RoomAccount, String> {
}
