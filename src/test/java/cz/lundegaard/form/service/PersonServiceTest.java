package cz.lundegaard.form.service;

import cz.lundegaard.form.dto.PersonDTO;
import cz.lundegaard.form.entity.Person;
import cz.lundegaard.form.exception.ResourceNotFoundException;
import cz.lundegaard.form.repository.PersonRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class PersonServiceTest {
    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Before
    public void before() {
        personRepository.deleteAll();

        Person person = new Person();
        person.setName("Cristiano");
        person.setSurname("Ronaldo");
        personService.createPerson(modelMapper.map(person, PersonDTO.class));
    }

    @Test
    public void createPersonTest() throws ResourceNotFoundException {
        Person person = personRepository.findByName("Cristiano").orElseThrow(() -> new ResourceNotFoundException("Person not found"));
        Assert.assertNotNull(person);
    }

    @Test
    public void updatePersonText() throws ResourceNotFoundException {
        Person person = personRepository.findByName("Cristiano").orElseThrow(() -> new ResourceNotFoundException("Person not found"));

        Person personNew = new Person();
        personNew.setName("Zlatan");
        personNew.setSurname("Ibrahimovic");

        personService.updatePerson(person.getId(), modelMapper.map(personNew, PersonDTO.class));

        PersonDTO refreshPerson = personService.getPersonById(person.getId());
        Assert.assertEquals(refreshPerson.getName(), "Zlatan");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deletePersonTest() throws ResourceNotFoundException {
        Person person = personRepository.findByName("Cristiano").orElseThrow(() -> new ResourceNotFoundException("Person not found"));
        personService.deletePerson(person.getId());
        personService.getPersonById(person.getId());
    }
}
