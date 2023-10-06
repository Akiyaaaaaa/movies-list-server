package dev.akiyaaa.moviesreview.repositories;

import dev.akiyaaa.moviesreview.models.ERole;
import dev.akiyaaa.moviesreview.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
