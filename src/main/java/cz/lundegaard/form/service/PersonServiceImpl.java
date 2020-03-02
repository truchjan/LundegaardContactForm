package cz.lundegaard.form.service;

import cz.lundegaard.form.dto.PersonDTO;
import cz.lundegaard.form.entity.Person;
import cz.lundegaard.form.exception.ResourceNotFoundException;
import cz.lundegaard.form.repository.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<PersonDTO> getAllPeople() {
        List<Person> personList = personRepository.findAll();
        List<PersonDTO> personDTOList = new ArrayList<>();
        for (Person person : personList) {
            personDTOList.add(modelMapper.map(person, PersonDTO.class));
        }
        return personDTOList;
    }

    @Override
    public PersonDTO getPersonById(long id) throws ResourceNotFoundException {
        Person personEntity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person " + id + " not found"));

        return modelMapper.map(personEntity, PersonDTO.class);
    }

    @Override
    public PersonDTO createPerson(@Valid PersonDTO personDTO) {
        Person personEntity = modelMapper.map(personDTO, Person.class);
        Person personCreated = personRepository.save(personEntity);
        return modelMapper.map(personCreated, PersonDTO.class);
    }

    @Override
    public PersonDTO updatePerson(long id, @Valid PersonDTO personDTONew) throws ResourceNotFoundException {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person " + id + " not found"));
        Person personNew = modelMapper.map(personDTONew, Person.class);

        person.setName(personNew.getName());
        person.setSurname(personNew.getSurname());
        person.setRequests(personNew.getRequests());

        Person personUpdated = personRepository.save(person);
        return modelMapper.map(personUpdated, PersonDTO.class);
    }

    @Override
    public void deletePerson(long id) throws ResourceNotFoundException {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person " + id + " not found"));

        personRepository.delete(person);
    }
}
