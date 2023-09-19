package br.com.carro.controller;

import java.util.List;

import br.com.carro.repository.CarroRepository;
import br.com.carro.entity.Carro;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/carro")
public class CarroController {
    @Autowired
    CarroRepository repository;

    @GetMapping
    public List<Carro> find( Carro filtro ){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING );

        Example example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Carro save( @RequestBody Carro carro ){
        return repository.save(carro);
    }
    @GetMapping("/{id}")
    public Carro getCarroById( @PathVariable Long id ){
        return repository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Carro não encontrado"));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Long id ){
        repository.findById(id)
                .map( carro -> {
                    repository.delete(carro);
                    return carro;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Carro não encontrado") );

    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update( @PathVariable Long id,
                        @RequestBody Carro carro ){
        repository
                .findById(id)
                .map( carroExistente -> {
                    carro.setId(carroExistente.getId());
                    repository.save(carro);
                    return carroExistente;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Carro não encontrado") );
    }

}
