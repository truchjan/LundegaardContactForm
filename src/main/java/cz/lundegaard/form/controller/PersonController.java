package cz.lundegaard.form.controller;

import cz.lundegaard.form.entity.Person;
import cz.lundegaard.form.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Spring controller maintains operations for people
 */
@RestController
@RequestMapping(path = "/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    /**
     * Finds all people
     *
     * @return list of all people in the system
     */
    @ResponseBody
    @GetMapping(path = "/all")
    public List<Person> getAllPeople() {
        return personService.getAllPeople();
    }

    /**
     * Finds one person by his id
     *
     * @param id person to be found
     * @return given person
     * @throws Exception thrown if person is not found
     */
    @GetMapping(path = "/{id}")
    public Person getPersonById(@PathVariable(value = "id") long id) throws Exception {
        return personService.getPersonById(id);
    }

    /**
     * Creates person with given information
     *
     * @param person to be created
     * @return response entity
     */
    @ResponseBody
    @PostMapping
    public ResponseEntity createPerson(@RequestBody Person person) {
        return ResponseEntity.ok(personService.createPerson(person));
    }

    /**
     * Updates person
     *
     * @param id     person to be updated
     * @param person changes to the person
     * @return updated person
     * @throws Exception thrown if person is not found
     */
    @ResponseBody
    @PutMapping(path = "/{id}")
    public Person updatePerson(@PathVariable(value = "id") long id, @RequestBody Person person) throws Exception {
        return personService.updatePerson(id, person);
    }

    /**
     * Delete one person with given id
     *
     * @param id person to be deleted
     * @return response entity - success or not found
     */
    @ResponseBody
    @DeleteMapping(path = "/{id}")
    public ResponseEntity deletePerson(@PathVariable(value = "id") long id) {
        try {
            personService.deletePerson(id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
}