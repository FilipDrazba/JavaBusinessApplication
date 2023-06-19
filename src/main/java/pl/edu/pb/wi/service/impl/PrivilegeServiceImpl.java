package pl.edu.pb.wi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.entity.Privilege;
import pl.edu.pb.wi.entity.Role;
import pl.edu.pb.wi.repository.PrivilegeRepository;
import pl.edu.pb.wi.service.PrivilegeService;

import java.util.List;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {
    private final PrivilegeRepository privilegeRepository;

    @Autowired
    PrivilegeServiceImpl(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    public List<Privilege> getByRole(Role role) {
        return privilegeRepository.getAllByRole(role);
    }

    @Override
    public List<Privilege> getAll() {
        return privilegeRepository.getAllPrivileges();
    }
}
