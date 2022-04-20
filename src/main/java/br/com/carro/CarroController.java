package br.com.carro;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class CarroController {

    CarroRepository repository;

    @GetMapping("/carro")
    public  List<Carro> getAllCarros(){
        return repository.findAll();
    }
    @PostMapping("/carro")
    public Carro saveCarro(@RequestBody Carro carro){
        return repository.save(carro);
    }
    @GetMapping("/carro/{id}")
    public Carro getCarroById(@PathVariable Long id){
        return repository.getById(id);
    }
    @DeleteMapping("/carro/{id}")
    public  void deleteCarro(@PathVariable Long id){
        repository.deleteById(id);
    }

}
