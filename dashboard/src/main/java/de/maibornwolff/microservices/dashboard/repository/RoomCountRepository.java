package de.maibornwolff.microservices.dashboard.repository;

import de.maibornwolff.microservices.dashboard.model.RoomCount;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Bartosz Boron, MaibornWolff GmbH
 */
public interface RoomCountRepository extends MongoRepository<RoomCount, String> {
}
