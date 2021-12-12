package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.domain.Role;
import ru.job4j.chat.repository.RoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RoleService implements CommonService<Role> {

    private final RoleRepository roleRepository;

    public RoleService(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return StreamSupport.stream(
                this.roleRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @Override
    public Optional<Role> findById(int id) {
        validateId(id);
        return this.roleRepository.findById(id);
    }

    @Override
    public Role create(Role role) {
        validateRole(role);
        return this.roleRepository.save(role);
    }

    @Override
    public void update(Role role) {
        validateRole(role);
        this.roleRepository.save(role);
    }

    @Override
    public void delete(int id) {
        validateId(id);
        Role role = new Role();
        role.setId(id);
        this.roleRepository.delete(role);
    }

    private void validateRole(Role role) {
        if (role.getRole() == null) {
            throw new NullPointerException("Role: role mustn't be empty");
        }
    }

    private void validateId(int id) {
        if (id < 1) {
            throw new IllegalArgumentException("Role id must not be less than 1");
        }
    }
}
