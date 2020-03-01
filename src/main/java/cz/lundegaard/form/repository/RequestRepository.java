package cz.lundegaard.form.repository;

import cz.lundegaard.form.entity.KindOfRequest;
import cz.lundegaard.form.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Interface for request repository handled by hibernate
 */
public interface RequestRepository extends JpaRepository<Request, Long> {
    Optional<Request> findByIdAndPersonId(long id, long personId);

    Optional<Request> findByRequestType(KindOfRequest requestType);

    Optional<Request> findByPolicyNumber(long policyNumber);

    Optional<Request> findByRequestText(String requestText);

    List<Request> findAllByPersonId(long personId);
}