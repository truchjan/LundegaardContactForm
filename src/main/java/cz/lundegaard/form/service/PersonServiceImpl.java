package cz.lundegaard.form.service;

import cz.lundegaard.form.entity.Person;
import cz.lundegaard.form.exception.ResourceNotFoundException;
import cz.lundegaard.form.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }

    @Override
    public Person getPersonById(long id) throws ResourceNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person " + id + " not found"));
    }

    @Override
    public Person createPerson(@Valid Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person updatePerson(long id, @Valid Person personNew) throws ResourceNotFoundException {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person " + id + " not found"));

        person.setName(personNew.getName());
        person.setSurname(personNew.getSurname());
        person.setRequests(personNew.getRequests());

        return personRepository.save(person);
    }

    @Override
    public void deletePerson(long id) throws ResourceNotFoundException {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person " + id + " not found"));

        personRepository.delete(person);
    }
}
