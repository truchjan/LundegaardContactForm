package cz.lundegaard.form.controller;

import java.util.List;

import cz.lundegaard.form.dto.RequestDTO;
import cz.lundegaard.form.exception.ResourceNotFoundException;
import cz.lundegaard.form.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Spring controller maintains operations for requests
 */
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:8081"})
@RestController
@RequestMapping(path = "/api/v1/person/{personId}/request")
public class RequestController {

    @Autowired
    private RequestService requestService;

    /**
     * Finds all requests owned by person with given id
     *
     * @param personId person owning given requests
     * @return list of all requests of given person
     * @throws ResourceNotFoundException thrown if person does not exist
     */
    @ResponseBody
    @GetMapping(path = "/all")
    public List<RequestDTO> getAllRequests(@PathVariable long personId) throws ResourceNotFoundException {
        return requestService.getAllRequests(personId);
    }


    /**
     * Finds request with given id owned by person with given id
     *
     * @param personId  person owning the request
     * @param requestId request to be found
     * @return one request of given id
     * @throws ResourceNotFoundException thrown if person or request do not exist
     */
    @GetMapping(path = "/{requestId}")
    public RequestDTO getRequestById(@PathVariable long personId, @PathVariable long requestId) throws ResourceNotFoundException {
        return requestService.getRequestById(personId, requestId);
    }

    /**
     * Creates a request with given information
     *
     * @param personId person who will own the new request
     * @param request  request to be created
     * @return response entity
     * @throws ResourceNotFoundException thrown if person is not found
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RequestDTO createRequest(@PathVariable long personId, @Valid @RequestBody RequestDTO request) throws ResourceNotFoundException {
        return requestService.createRequest(personId, request);
    }

    /**
     * Update request
     *
     * @param personId  person owning the request
     * @param requestId request to be updated
     * @param request   changes to the request
     * @return updated request
     * @throws ResourceNotFoundException thrown if person of request do not exist
     */
    @ResponseBody
    @PutMapping(path = "/{requestId}")
    public RequestDTO updateRequest(@PathVariable long personId, @PathVariable long requestId, @Valid @RequestBody RequestDTO request) throws ResourceNotFoundException {
        return requestService.updateRequest(personId, requestId, request);
    }

    /**
     * Deletes given request
     *
     * @param personId  person owning the request
     * @param requestId request to be deleted
     * @return response entity - success or not found
     */
    @ResponseBody
    @DeleteMapping(path = "/{requestId}")
    public ResponseEntity deleteRequest(@PathVariable long personId, @PathVariable long requestId) {
        try {
            requestService.deleteRequest(personId, requestId);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
