package cz.lundegaard.form.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Db model for request entity
 */
@Entity
@Table(name = "request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "request_id", nullable = false)
    private long id;

    @NotNull
    @Column(name = "request_type", nullable = false)
    private KindOfRequest requestType;

    @NotNull
    @Min(0)
    @Max(999999)
    @Column(name = "policy_number", nullable = false)
    private long policyNumber;

    @NotNull
    @Size(max = 5000)
    @Column(name = "request_text", nullable = false)
    private String requestText;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("person_id")
    private Person person;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public KindOfRequest getRequestType() {
        return requestType;
    }

    public void setRequestType(KindOfRequest requestType) {
        this.requestType = requestType;
    }

    public long getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(long policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getRequestText() {
        return requestText;
    }

    public void setRequestText(String requestText) {
        this.requestText = requestText;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return id == request.id &&
                policyNumber == request.policyNumber &&
                requestType == request.requestType &&
                Objects.equals(requestText, request.requestText) &&
                Objects.equals(person, request.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, requestType, policyNumber, requestText, person);
    }
}
