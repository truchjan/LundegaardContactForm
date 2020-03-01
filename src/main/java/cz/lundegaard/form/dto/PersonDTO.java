package cz.lundegaard.form.dto;

import cz.lundegaard.form.entity.Request;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class PersonDTO {

    private long id;

    @NotNull
    @Size(max = 99)
    private String name;

    @NotNull
    @Size(max = 99)
    private String surname;

    private List<@Valid RequestDTO> requests = new ArrayList<>();
}
