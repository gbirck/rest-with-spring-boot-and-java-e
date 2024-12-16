package com.gabriel.rest.service;

import com.gabriel.rest.controller.PersonController;
import com.gabriel.rest.exceptions.ResourceNotFoundException;
import com.gabriel.rest.mapper.DozerMapper;
import com.gabriel.rest.model.Person;
import com.gabriel.rest.model.dto.PersonDTO;
import com.gabriel.rest.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public PersonDTO insert(PersonDTO person) {
        System.out.println("Persisting: " + person);
        var entity = DozerMapper.parseObject(person, Person.class);
        var dto = DozerMapper.parseObject(personRepository.save(entity), PersonDTO.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getKey())).withSelfRel());
        return dto;
    }

    @Transactional
    public PersonDTO findById(Long id) {
        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID" + id + "not found"));
        var dto = DozerMapper.parseObject(entity, PersonDTO.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return dto;
    }

    @Transactional
    public List<PersonDTO> findAll() {
        var persons = DozerMapper.parseListObjects(personRepository.findAll(), PersonDTO.class);
        persons
                .stream()
                .forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
        return persons;
    }

    @Transactional
    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    @Transactional
    public PersonDTO update(PersonDTO person) {
        var entity = DozerMapper.parseObject(person, Person.class);
        var dto = DozerMapper.parseObject(personRepository.save(entity), PersonDTO.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getKey())).withSelfRel());
        return dto;
    }
}
