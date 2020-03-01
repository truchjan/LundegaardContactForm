package cz.lundegaard.form.service;

import cz.lundegaard.form.entity.Person;
import cz.lundegaard.form.entity.Request;
import cz.lundegaard.form.exception.ResourceNotFoundException;
import cz.lundegaard.form.repository.PersonRepository;
import cz.lundegaard.form.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private PersonRepository personRepository;

    /**
     * Finds all requests and than removes those not owned by given person
     *
     * @param personId person owning given requests
     * @return list of all requests of given person
     * @throws ResourceNotFoundException thrown if person does not exist
     */
    public List<Request> getAllRequests(long personId) throws ResourceNotFoundException {
        if (!personRepository.existsById(personId))
            throw new ResourceNotFoundException("Person " + personId + " not found");
        return requestRepository.findAllByPersonId(personId);
    }

    /**
     * Finds request with given id owned by person with given id
     *
     * @param personId  person owning the request
     * @param requestId request to be found
     * @return one request of given id
     * @throws ResourceNotFoundException thrown if person or request do not exist
     */
    public Request getRequestById(long personId, long requestId) throws ResourceNotFoundException {
        return requestRepository.findByIdAndPersonId(requestId, personId)
                .orElseThrow(() -> new ResourceNotFoundException("Request " + requestId + " belonging to person " + personId + " not found"));
    }

    /**
     * Creates a request with given information and connects it to given person
     *
     * @param personId person who will own the new request
     * @param request  request to be created
     * @return response entity
     * @throws ResourceNotFoundException thrown if person is not found
     */
    public Request createRequest(long personId, Request request) throws ResourceNotFoundException {
        Person person = personRepository.findById(personId).orElseThrow(() -> new ResourceNotFoundException("Person " + personId + " not found"));
        request.setPerson(person);
        person.getRequests().add(request);
        return requestRepository.save(request);
    }

    /**
     * Finds given person and request, updates the request and connects it to the person
     *
     * @param personId   person owning the request
     * @param requestId  request to be updated
     * @param requestNew changes to the request
     * @return updated request
     * @throws Exception thrown if person of request do not exist
     */
    public Request updateRequest(long personId, long requestId, Request requestNew) throws ResourceNotFoundException {
        Person person = personRepository.findById(personId).orElseThrow(() -> new ResourceNotFoundException("Person " + personId + " not found"));
        Request request = requestRepository.findByIdAndPersonId(requestId, personId)
                .orElseThrow(() -> new ResourceNotFoundException("Request " + requestId + " belonging to person " + personId + " not found"));

        request.setRequestType(requestNew.getRequestType());
        request.setPolicyNumber(requestNew.getPolicyNumber());
        request.setRequestText(requestNew.getRequestText());
        request.setPerson(person);

        return requestRepository.save(request);
    }

    /**
     * Finds given request by person and deletes the request
     *
     * @param personId  person owning the request
     * @param requestId request to be deleted
     */
    public void deleteRequest(long personId, long requestId) throws ResourceNotFoundException {
        Request request = requestRepository.findByIdAndPersonId(requestId, personId)
                .orElseThrow(() -> new ResourceNotFoundException("Request " + requestId + " belonging to person " + personId + " not found"));

        requestRepository.delete(request);
    }
}
