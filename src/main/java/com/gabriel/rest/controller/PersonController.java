package com.gabriel.rest.controller;

import com.gabriel.rest.exceptions.ResourceNotFoundException;
import com.gabriel.rest.model.Person;
import com.gabriel.rest.model.PersonDTO;
import com.gabriel.rest.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public ResponseEntity<PersonDTO> insert(@RequestBody @Valid PersonDTO person) {
        System.out.println("Received: " + person);
        return ResponseEntity.ok(personService.insert(person));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonDTO> findById(@PathVariable Long id) {
        try {
            PersonDTO person = personService.findById(id);
            return ResponseEntity.ok(person);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping(value = "/all")
    public List<PersonDTO> findAll() {
        return personService.findAll();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PersonDTO> update(@PathVariable Long id, @RequestBody @Valid PersonDTO person) {
        person.setId(id);
        personService.update(person);
        return ResponseEntity.ok(person);
    }
}
