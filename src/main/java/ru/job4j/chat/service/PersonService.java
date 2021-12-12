package ru.job4j.chat.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.chat.exception.UserValidateException;
import ru.job4j.chat.model.domain.Person;
import ru.job4j.chat.repository.PersonRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PersonService implements CommonService<Person> {

    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder encoder;

    public PersonService(final PersonRepository personRepository,
                            BCryptPasswordEncoder encoder) {
        this.personRepository = personRepository;
        this.encoder = encoder;
    }

    @Override
    public List<Person> findAll() {
        return StreamSupport.stream(
                this.personRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @Override
    public Optional<Person> findById(int id) {
        validateId(id);
        return this.personRepository.findById(id);
    }

    @Override
    public Person create(Person person) {
        validatePerson(person);
        person.setPassword(encoder.encode(person.getPassword()));
        return this.personRepository.save(person);
    }

    @Override
    public void update(Person person) {
        validatePerson(person);
        this.personRepository.save(person);
    }

    @Override
    public void delete(int id) {
        validateId(id);
        Person person = new Person();
        person.setId(id);
        this.personRepository.delete(person);
    }

    private void validatePerson(Person person) {
        if (person.getLogin().length() < 3) {
            throw new UserValidateException("Person: login must be more than 3 characters");
        }
        if (person.getLogin() == null || person.getPassword() == null) {
            throw new NullPointerException("Person: login and password mustn't be empty");
        }
    }

    private void validateId(int id) {
        if (id < 1) {
            throw new IllegalArgumentException("Person id must not be less than 1");
        }
    }

}
