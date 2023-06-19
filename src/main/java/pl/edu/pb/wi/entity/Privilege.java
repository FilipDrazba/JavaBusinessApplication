package pl.edu.pb.wi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

@Entity
@Getter
@Setter
@SuperBuilder
@RequiredArgsConstructor
public class Privilege {
    public Privilege(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
}
