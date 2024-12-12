package com.gabriel.rest.service;

import com.gabriel.rest.exceptions.ResourceNotFoundException;
import com.gabriel.rest.mapper.DozerMapper;
import com.gabriel.rest.model.Person;
import com.gabriel.rest.model.PersonDTO;
import com.gabriel.rest.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        return dto;
    }

    @Transactional
    public PersonDTO findById(Long id) {
        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID" + id + "not found"));
        return DozerMapper.parseObject(entity, PersonDTO.class);
    }

    @Transactional
    public List<PersonDTO> findAll() {
        return DozerMapper.parseListObjects(personRepository.findAll(), PersonDTO.class);
    }

    @Transactional
    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    @Transactional
    public PersonDTO update(PersonDTO person) {
        var entity = DozerMapper.parseObject(person, Person.class);
        var dto = DozerMapper.parseObject(personRepository.save(entity), PersonDTO.class);
        return dto;
    }
}
