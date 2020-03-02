package cz.lundegaard.form.service;

import cz.lundegaard.form.dto.PersonDTO;
import cz.lundegaard.form.dto.RequestDTO;
import cz.lundegaard.form.entity.KindOfRequest;
import cz.lundegaard.form.entity.Person;
import cz.lundegaard.form.entity.Request;
import cz.lundegaard.form.exception.ResourceNotFoundException;
import cz.lundegaard.form.repository.PersonRepository;
import cz.lundegaard.form.repository.RequestRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RequestServiceTest {

    @Autowired
    private RequestService requestService;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Before
    public void before() throws ResourceNotFoundException {
        personRepository.deleteAll();
        requestRepository.deleteAll();

        Person person = new Person();
        person.setName("Cristiano");
        person.setSurname("Ronaldo");
        personService.createPerson(modelMapper.map(person, PersonDTO.class));

        Request request = new Request();
        request.setRequestType(KindOfRequest.COMPLAINT);
        request.setPolicyNumber(123);
        request.setRequestText("Text");
        request.setPerson(person);
        long personId = personRepository.findByName(person.getName()).orElseThrow(() -> new ResourceNotFoundException("Person not found")).getId();;
        requestService.createRequest(personId, modelMapper.map(request, RequestDTO.class));
    }

    @Test
    public void createRequestTest() throws ResourceNotFoundException {
        Request request = requestRepository.findByRequestText("Text").orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        Assert.assertNotNull(request);
    }

    @Test
    public void updateRequestText() throws ResourceNotFoundException {
        Request request = requestRepository.findByRequestText("Text").orElseThrow(() -> new ResourceNotFoundException("Request not found"));

        Request requestNew = new Request();
        requestNew.setRequestType(KindOfRequest.CONTRACT_ADJUSTMENT);
        requestNew.setPolicyNumber(12345);
        requestNew.setRequestText("Novytext");
        requestNew.setPerson(request.getPerson());

        requestService.updateRequest(request.getPerson().getId(), request.getId(), modelMapper.map(requestNew, RequestDTO.class));

        RequestDTO refreshRequest = requestService.getRequestById(request.getPerson().getId(), request.getId());
        Assert.assertEquals(refreshRequest.getRequestText(), "Novytext");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteRequestTest() throws ResourceNotFoundException {
        Request request = requestRepository.findByRequestText("Text").orElseThrow(() -> new ResourceNotFoundException("Request not found"));

        requestService.deleteRequest(request.getPerson().getId(), request.getId());
        requestService.getRequestById(request.getPerson().getId(), request.getId());
    }
}
