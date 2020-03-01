package cz.lundegaard.form.service;

import cz.lundegaard.form.entity.Person;
import cz.lundegaard.form.exception.ResourceNotFoundException;
import cz.lundegaard.form.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    /**
     * Finds all people
     *
     * @return list of all people
     */
    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }

    /**
     * Finds person by his id
     *
     * @param id person to be found
     * @return found person
     * @throws ResourceNotFoundException thrown if the person does not exist
     */
    public Person getPersonById(long id) throws ResourceNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person " + id + " not found"));
    }

    /**
     * Creates new person
     *
     * @param person new person
     * @return created person
     */
    public Person createPerson(@Valid Person person) {
        return personRepository.save(person);
    }

    /**
     * Finds person by his id and updates him with given information
     *
     * @param id        person to be updated
     * @param personNew new person
     * @return updated person
     * @throws ResourceNotFoundException thrown if person if not found
     */
    public Person updatePerson(long id, @Valid Person personNew) throws ResourceNotFoundException {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person " + id + " not found"));

        person.setName(personNew.getName());
        person.setSurname(personNew.getSurname());
        person.setRequests(personNew.getRequests());

        return personRepository.save(person);
    }

    /**
     * Finds person by his id and deletes him from the repository
     *
     * @param id person to be deleted
     * @throws ResourceNotFoundException thrown if person is not found
     */
    public void deletePerson(long id) throws ResourceNotFoundException {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person " + id + " not found"));

        personRepository.delete(person);
    }
}