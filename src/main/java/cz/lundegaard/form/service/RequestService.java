package cz.lundegaard.form.service;

import cz.lundegaard.form.entity.Person;
import cz.lundegaard.form.entity.Request;
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
     * @throws Exception thrown if person does not exist
     */
    public List<Request> getAllRequests(long personId) throws Exception {
        if (!personRepository.existsById(personId))
            throw new Exception("Person " + personId + " not found");
        List<Request> requests = requestRepository.findAll();
        return removeOtherPersonIds(requests, personId);
    }

    /**
     * Finds request with given id owned by person with given id
     *
     * @param personId  person owning the request
     * @param requestId request to be found
     * @return one request of given id
     * @throws Exception thrown if person or request do not exist
     */
    public Request getRequestById(long personId, long requestId) throws Exception {
        return requestRepository.findByIdAndPersonId(requestId, personId)
                .orElseThrow(() -> new Exception("Request " + requestId + " belonging to person " + personId + " not found"));
    }

    /**
     * Creates a request with given information and connects it to given person
     *
     * @param personId person who will own the new request
     * @param request  request to be created
     * @return response entity
     * @throws Exception thrown if person is not found
     */
    public Request createRequest(long personId, Request request) throws Exception {
        Person person = personRepository.findById(personId).orElseThrow(() -> new Exception("Person " + personId + " not found"));
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
    public Request updateRequest(long personId, long requestId, Request requestNew) throws Exception {
        Person person = personRepository.findById(personId).orElseThrow(() -> new Exception("Person " + personId + " not found"));
        Request request = requestRepository.findByIdAndPersonId(requestId, personId)
                .orElseThrow(() -> new Exception("Request " + requestId + " belonging to person " + personId + " not found"));

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
    public void deleteRequest(long personId, long requestId) throws Exception {
        Request request = requestRepository.findByIdAndPersonId(requestId, personId)
                .orElseThrow(() -> new Exception("Request " + requestId + " belonging to person " + personId + " not found"));

        requestRepository.delete(request);
    }

    /**
     * I apologize for this, it is very ugly. I believe RequestRepository can be used to make it prettier
     * Removes all requests that do not belong to he given person
     * First it removes requests with no person attached to them and then those with a different one
     *
     * @param requests list of all requests to be update
     * @param personId person owning the requests
     * @return updated list of requests
     */
    private List<Request> removeOtherPersonIds(List<Request> requests, long personId) {
        requests.removeIf(value -> value.getPerson() == null);
        requests.removeIf(value -> value.getPerson().getId() != personId);
        return requests;
    }
}
