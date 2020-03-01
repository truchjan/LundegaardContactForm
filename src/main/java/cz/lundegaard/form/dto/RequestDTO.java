package cz.lundegaard.form.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import cz.lundegaard.form.entity.KindOfRequest;
import cz.lundegaard.form.entity.Person;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class RequestDTO {

    private long id;

    @NotNull
    private KindOfRequest requestType;

    @NotNull
    @Min(0)
    @Max(999999)
    private long policyNumber;

    @NotNull
    @Size(max = 5000)
    private String requestText;

    @Valid
    private Person person;
}
