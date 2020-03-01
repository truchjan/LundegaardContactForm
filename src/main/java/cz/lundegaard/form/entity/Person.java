package cz.lundegaard.form.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Db model for person entity
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "person_id", nullable = false)
    private long id;

    @NotNull
    @Size(max = 99)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Size(max = 99)
    @Column(name = "surname", nullable = false)
    private String surname;

    @OneToMany(mappedBy = "person")
    private List<@Valid Request> requests = new ArrayList<>();
}
