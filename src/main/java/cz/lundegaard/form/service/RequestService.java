package cz.lundegaard.form.service;

import cz.lundegaard.form.entity.Request;
import cz.lundegaard.form.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public interface RequestService {

    /**
     * Finds all requests and than removes those not owned by given person
     *
     * @param personId person owning given requests
     * @return list of all requests of given person
     * @throws ResourceNotFoundException thrown if person does not exist
     */
    List<Request> getAllRequests(long personId) throws ResourceNotFoundException;

    /**
     * Finds request with given id owned by person with given id
     *
     * @param personId  person owning the request
     * @param requestId request to be found
     * @return one request of given id
     * @throws ResourceNotFoundException thrown if person or request do not exist
     */
    Request getRequestById(long personId, long requestId) throws ResourceNotFoundException;

    /**
     * Creates a request with given information and connects it to given person
     *
     * @param personId person who will own the new request
     * @param request  request to be created
     * @return response entity
     * @throws ResourceNotFoundException thrown if person is not found
     */
    Request createRequest(long personId, @Valid Request request) throws ResourceNotFoundException;

    /**
     * Finds given person and request, updates the request and connects it to the person
     *
     * @param personId   person owning the request
     * @param requestId  request to be updated
     * @param requestNew changes to the request
     * @return updated request
     * @throws ResourceNotFoundException thrown if person of request do not exist
     */
    Request updateRequest(long personId, long requestId, @Valid Request requestNew) throws ResourceNotFoundException;

    /**
     * Finds given request by person and deletes the request
     *
     * @param personId  person owning the request
     * @param requestId request to be deleted
     */
    void deleteRequest(long personId, long requestId);
}
