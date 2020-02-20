package cz.lundegaard.form.service;

import cz.lundegaard.form.entity.Person;
import cz.lundegaard.form.repository.PersonRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    @Before
    public void before() {
        personRepository.deleteAll();

        Person person = new Person();
        person.setName("Cristiano");
        person.setSurname("Ronaldo");
        personService.createPerson(person);
    }

    @Test
    public void createPersonTest() throws Exception {
        Person person = personRepository.findByName("Cristiano").orElseThrow(() -> new Exception("Person not found"));
        Assert.assertNotNull(person);
    }

    @Test
    public void updatePersonText() throws Exception {
        Person person = personRepository.findByName("Cristiano").orElseThrow(() -> new Exception("Person not found"));

        Person personNew = new Person();
        personNew.setName("Zlatan");
        personNew.setSurname("Ibrahimovic");

        personService.updatePerson(person.getId(), personNew);

        Person refreshPerson = personService.getPersonById(person.getId());
        Assert.assertEquals(refreshPerson.getName(), "Zlatan");
    }

    @Test(expected = Exception.class)
    public void deletePersonTest() throws Exception {
        Person person = personRepository.findByName("Cristiano").orElseThrow(() -> new Exception("Person not found"));
        personService.deletePerson(person.getId());
        personService.getPersonById(person.getId());
    }
}
