package cz.lundegaard.form.service;

import cz.lundegaard.form.entity.Person;
import cz.lundegaard.form.exception.ResourceNotFoundException;

import javax.validation.Valid;
import java.util.List;

public interface PersonService {
    /**
     * Finds all people
     *
     * @return list of all people
     */
    List<Person> getAllPeople();

    /**
     * Finds person by his id
     *
     * @param id person to be found
     * @return found person
     * @throws ResourceNotFoundException thrown if the person does not exist
     */
    Person getPersonById(long id) throws ResourceNotFoundException;

    /**
     * Creates new person
     *
     * @param person new person
     * @return created person
     */
    Person createPerson(@Valid Person person);

    /**
     * Finds person by his id and updates him with given information
     *
     * @param id        person to be updated
     * @param personNew new person
     * @return updated person
     * @throws ResourceNotFoundException thrown if person if not found
     */
    Person updatePerson(long id, @Valid Person personNew) throws ResourceNotFoundException;

    /**
     * Finds person by his id and deletes him from the repository
     *
     * @param id person to be deleted
     * @throws ResourceNotFoundException thrown if person is not found
     */
    void deletePerson(long id) throws ResourceNotFoundException;
}