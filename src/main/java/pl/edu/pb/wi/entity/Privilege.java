package pl.edu.pb.wi.entity;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

@Entity
@SuperBuilder
@RequiredArgsConstructor
public class Privilege {
    public Privilege(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
}
