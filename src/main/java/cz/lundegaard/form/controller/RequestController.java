package cz.lundegaard.form.controller;

import java.util.List;

import cz.lundegaard.form.entity.Request;
import cz.lundegaard.form.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Spring controller maintains operations for requests
 */
@RestController
@RequestMapping(path = "/person/{personId}/request")
public class RequestController {

    @Autowired
    private RequestService requestService;

    /**
     * Finds all requests owned by person with given id
     *
     * @param personId person owning given requests
     * @return list of all requests of given person
     * @throws Exception thrown if person does not exist
     */
    @ResponseBody
    @GetMapping(path = "/all")
    public List<Request> getAllRequests(@PathVariable(value = "personId") long personId) throws Exception {
        return requestService.getAllRequests(personId);
    }


    /**
     * Finds request with given id owned by person with given id
     *
     * @param personId  person owning the request
     * @param requestId request to be found
     * @return one request of given id
     * @throws Exception thrown if person or request do not exist
     */
    @GetMapping(path = "/{requestId}")
    public Request getRequestById(@PathVariable(value = "personId") long personId, @PathVariable(value = "requestId") long requestId) throws Exception {
        return requestService.getRequestById(personId, requestId);
    }

    /**
     * Creates a request with given information
     *
     * @param personId person who will own the new request
     * @param request  request to be created
     * @return response entity
     * @throws Exception thrown if person is not found
     */
    @ResponseBody
    @PostMapping(path = "/create")
    public ResponseEntity createRequest(@PathVariable(value = "personId") long personId, @RequestBody Request request) throws Exception {
        return ResponseEntity.ok(requestService.createRequest(personId, request));
    }

    /**
     * Update request
     *
     * @param personId  person owning the request
     * @param requestId request to be updated
     * @param request   changes to the request
     * @return updated request
     * @throws Exception thrown if person of request do not exist
     */
    @ResponseBody
    @PutMapping(path = "/update/{requestId}")
    public Request updateRequest(@PathVariable(value = "personId") long personId, @PathVariable(value = "requestId") long requestId, @RequestBody Request request) throws Exception {
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
    @DeleteMapping(path = "/delete/{requestId}")
    public ResponseEntity deleteRequest(@PathVariable(value = "personId") long personId, @PathVariable(value = "requestId") long requestId) {
        try {
            requestService.deleteRequest(personId, requestId);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
