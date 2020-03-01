package cz.lundegaard.form.service;

import cz.lundegaard.form.entity.Person;
import cz.lundegaard.form.entity.Request;
import cz.lundegaard.form.exception.ResourceNotFoundException;
import cz.lundegaard.form.repository.PersonRepository;
import cz.lundegaard.form.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Request> getAllRequests(long personId) throws ResourceNotFoundException {
        if (!personRepository.existsById(personId))
            throw new ResourceNotFoundException("Person " + personId + " not found");
        return requestRepository.findAllByPersonId(personId);
    }

    @Override
    public Request getRequestById(long personId, long requestId) throws ResourceNotFoundException {
        return requestRepository.findByIdAndPersonId(requestId, personId)
                .orElseThrow(() -> new ResourceNotFoundException("Request " + requestId + " belonging to person " + personId + " not found"));
    }

    @Override
    public Request createRequest(long personId, @Valid Request request) throws ResourceNotFoundException {
        Person person = personRepository.findById(personId).orElseThrow(() -> new ResourceNotFoundException("Person " + personId + " not found"));
        request.setPerson(person);
        person.getRequests().add(request);
        return requestRepository.save(request);
    }

    @Override
    public Request updateRequest(long personId, long requestId, @Valid Request requestNew) throws ResourceNotFoundException {
        Person person = personRepository.findById(personId).orElseThrow(() -> new ResourceNotFoundException("Person " + personId + " not found"));
        Request request = requestRepository.findByIdAndPersonId(requestId, personId)
                .orElseThrow(() -> new ResourceNotFoundException("Request " + requestId + " belonging to person " + personId + " not found"));

        request.setRequestType(requestNew.getRequestType());
        request.setPolicyNumber(requestNew.getPolicyNumber());
        request.setRequestText(requestNew.getRequestText());
        request.setPerson(person);

        return requestRepository.save(request);
    }

    @Override
    public void deleteRequest(long personId, long requestId) throws ResourceNotFoundException {
        Request request = requestRepository.findByIdAndPersonId(requestId, personId)
                .orElseThrow(() -> new ResourceNotFoundException("Request " + requestId + " belonging to person " + personId + " not found"));

        requestRepository.delete(request);
    }
}
