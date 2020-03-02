package cz.lundegaard.form.service;

import cz.lundegaard.form.dto.RequestDTO;
import cz.lundegaard.form.entity.Person;
import cz.lundegaard.form.entity.Request;
import cz.lundegaard.form.exception.ResourceNotFoundException;
import cz.lundegaard.form.repository.PersonRepository;
import cz.lundegaard.form.repository.RequestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private PersonRepository personRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<RequestDTO> getAllRequests(long personId) throws ResourceNotFoundException {
        if (!personRepository.existsById(personId))
            throw new ResourceNotFoundException("Person " + personId + " not found");
        List<Request> requestList = requestRepository.findAllByPersonId(personId);
        List<RequestDTO> requestDTOList = new ArrayList<>();
        for(Request request : requestList) {
            requestDTOList.add(modelMapper.map(request, RequestDTO.class));
        }

        return requestDTOList;
    }

    @Override
    public RequestDTO getRequestById(long personId, long requestId) throws ResourceNotFoundException {
        Request requestEntity = requestRepository.findByIdAndPersonId(requestId, personId)
                .orElseThrow(() -> new ResourceNotFoundException("Request " + requestId + " belonging to person " + personId + " not found"));

        return modelMapper.map(requestEntity, RequestDTO.class);
    }

    @Override
    public RequestDTO createRequest(long personId, @Valid RequestDTO request) throws ResourceNotFoundException {
        Person person = personRepository.findById(personId).orElseThrow(() -> new ResourceNotFoundException("Person " + personId + " not found"));
        Request requestEntity = modelMapper.map(request, Request.class);
        requestEntity.setPerson(person);
        person.getRequests().add(requestEntity);

        Request requestCreated = requestRepository.save(requestEntity);
        return modelMapper.map(requestCreated, RequestDTO.class);
    }

    @Override
    public RequestDTO updateRequest(long personId, long requestId, @Valid RequestDTO requestDTONew) throws ResourceNotFoundException {
        Person person = personRepository.findById(personId).orElseThrow(() -> new ResourceNotFoundException("Person " + personId + " not found"));
        Request request = requestRepository.findByIdAndPersonId(requestId, personId)
                .orElseThrow(() -> new ResourceNotFoundException("Request " + requestId + " belonging to person " + personId + " not found"));

        Request requestNew = modelMapper.map(requestDTONew, Request.class);
        request.setRequestType(requestNew.getRequestType());
        request.setPolicyNumber(requestNew.getPolicyNumber());
        request.setRequestText(requestNew.getRequestText());
        request.setPerson(person);

        Request requestUpdated = requestRepository.save(request);
        return modelMapper.map(requestUpdated, RequestDTO.class);
    }

    @Override
    public void deleteRequest(long personId, long requestId) throws ResourceNotFoundException {
        Request request = requestRepository.findByIdAndPersonId(requestId, personId)
                .orElseThrow(() -> new ResourceNotFoundException("Request " + requestId + " belonging to person " + personId + " not found"));

        requestRepository.delete(request);
    }
}
