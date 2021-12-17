package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.domain.Role;
import ru.job4j.chat.model.validator.Operation;
import ru.job4j.chat.service.RoleService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/role")
@Validated
public class RoleController {

    private final RoleService roleService;

    public RoleController(final RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/")
    public List<Role> findAll() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> findById(@PathVariable int id) {
        return new ResponseEntity<>(
                this.roleService.findById(id).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("Role with id %s not found.", id))),
                HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Role> create(@Valid @RequestBody Role role) {
        return new ResponseEntity<>(
                this.roleService.create(role),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@Valid @RequestBody Role role) {
        this.roleService.update(role);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        this.roleService.delete(id);
        return ResponseEntity.ok().build();
    }
}
