package cz.lundegaard.form.repository;

import cz.lundegaard.form.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Interface for person repository handled by hibernate
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByName(String name);

    Optional<Person> findBySurname(String surname);

    Optional<Person> findByNameAndSurname(String name, String surname);
}