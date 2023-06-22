package pl.edu.pb.wi.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.entity.Role;
import pl.edu.pb.wi.repository.RoleRepository;
import pl.edu.pb.wi.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    private static final Logger logger = LogManager.getLogger(RoleServiceImpl.class);

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByName(String name) {
        logger.info("Retrieving role by name: {}", name);
        Role role = roleRepository.getRoleByName(name);

        if (role == null) {
            logger.warn("Role not found for name: {}", name);
        } else {
            logger.info("Role found: {}", role);
        }

        return role;
    }
}
