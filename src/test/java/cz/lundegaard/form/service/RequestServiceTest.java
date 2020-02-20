package cz.lundegaard.form.service;

import cz.lundegaard.form.entity.KindOfRequest;
import cz.lundegaard.form.entity.Person;
import cz.lundegaard.form.entity.Request;
import cz.lundegaard.form.repository.PersonRepository;
import cz.lundegaard.form.repository.RequestRepository;
import org.hibernate.Hibernate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    @Before
    public void before() throws Exception {
        personRepository.deleteAll();
        requestRepository.deleteAll();

        Person person = new Person();
        person.setName("Cristiano");
        person.setSurname("Ronaldo");
        personService.createPerson(person);

        Request request = new Request();
        request.setRequestType(KindOfRequest.COMPLAINT);
        request.setPolicyNumber(123);
        request.setRequestText("Text");
        request.setPerson(person);
        requestService.createRequest(person.getId(), request);
    }

    @Test
    public void createRequestTest() throws Exception {
        Request request = requestRepository.findByRequestText("Text").orElseThrow(() -> new Exception("Request not found"));
        Assert.assertNotNull(request);
    }

    @Test
    public void updateRequestText() throws Exception {
        Request request = requestRepository.findByRequestText("Text").orElseThrow(() -> new Exception("Request not found"));

        Request requestNew = new Request();
        requestNew.setRequestType(KindOfRequest.CONTRACT_ADJUSTMENT);
        requestNew.setPolicyNumber(12345);
        requestNew.setRequestText("Novytext");
        requestNew.setPerson(request.getPerson());

        requestService.updateRequest(request.getPerson().getId(), request.getId(), requestNew);

        Request refreshRequest = requestService.getRequestById(request.getPerson().getId(), request.getId());
        Assert.assertEquals(refreshRequest.getRequestText(), "Novytext");
    }

    @Test(expected = Exception.class)
    public void deleteRequestTest() throws Exception {
        Request request = requestRepository.findByRequestText("Text").orElseThrow(() -> new Exception("Request not found"));

        requestService.deleteRequest(request.getPerson().getId(), request.getId());
        requestService.getRequestById(request.getPerson().getId(), request.getId());
    }
}
