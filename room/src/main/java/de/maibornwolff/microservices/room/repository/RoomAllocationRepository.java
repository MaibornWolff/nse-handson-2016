package de.maibornwolff.microservices.room.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import de.maibornwolff.microservices.room.model.RoomAllocation;

/**
 * Created by Philipp Lamp, MaibornWolff GmbH
 */
public interface RoomAllocationRepository extends MongoRepository<RoomAllocation, String> {
}
