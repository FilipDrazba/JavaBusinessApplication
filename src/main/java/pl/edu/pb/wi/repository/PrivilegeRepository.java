package pl.edu.pb.wi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pb.wi.entity.Privilege;

import java.util.List;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    @Query("SELECT p FROM Privilege p")
    List<Privilege> getAllPrivileges();

    @Query("SELECT p FROM Privilege p WHERE p.name = :name")
    Privilege getPrivilegeByName(String name);
}
