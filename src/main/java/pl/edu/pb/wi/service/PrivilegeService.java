package pl.edu.pb.wi.service;

import pl.edu.pb.wi.entity.Privilege;
import pl.edu.pb.wi.entity.Role;

import java.util.List;

public interface PrivilegeService {
    List<Privilege> getByRole(Role role);
    List<Privilege> getAll();
}
