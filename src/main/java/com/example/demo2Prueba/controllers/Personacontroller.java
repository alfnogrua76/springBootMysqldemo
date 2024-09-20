package com.example.demo2Prueba.controllers;

import com.example.demo2Prueba.Services.PersonaService;
import com.example.demo2Prueba.dto.PersonDTO;
import com.example.demo2Prueba.dto.PersonaDTO;
import com.example.demo2Prueba.entities.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persona")
public class Personacontroller {

    private final PersonaService personaService;

    public Personacontroller(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping()
    public String holaMundo(){
        return "HOA MUNDO";
    }

    @GetMapping("/all")
    public List<Persona> listarPerswonas() {
        return personaService.buscarAllPersonas();
    }

    @GetMapping("/activos")//aqui vamos a buscar el listado de personas pero activas osea status ==true
    public List<Persona> listarPerswonasActivas() {
        return personaService.buscarPersonasActivas();
    }

    @PostMapping("/actualizar-persona/{id}")
    public Persona publicarPersona(@RequestBody Persona persona){
        if (persona == null){
            return null;
        }
        return personaService.crearPersona(persona);
    }

    @PutMapping("/actualizar-persona/{id}")
    public PersonaDTO actualizarPersona(@RequestBody PersonaDTO persona, @PathVariable Long id){
        if (persona == null){
            return null;
        }
        return personaService.updatePersona(persona);
    }

    @DeleteMapping("/eliminar-persona/")
    public boolean eliminarPersona(@RequestParam Long id) {//request Param otra manera de recibir datos en springBoot
        return personaService.deletePersona(id);
    }

    //Metodo para el query derivado
    @GetMapping("/prueba")
    public Persona ejemploQueryDerivada(@RequestParam String nombre, @RequestParam String apellido){
        return personaService.ejemploQueryDerivada(nombre,apellido);
    }

    //Metodo paginado
    @GetMapping("/mostrar-pagina/")
    public ResponseEntity<Page<PersonDTO>> mostrarPaginasJpaql(@PageableDefault(size = 2, sort = {"nombre"})
                                                          Pageable pageable){
        Page<PersonDTO> pagina = personaService.buscarPaginado(pageable);
        return ResponseEntity.ok(pagina);
    }

    //igual un paginado pero dinamico
    @GetMapping("/mostrar-pagina-dinamica/")
    public ResponseEntity<Page<PersonDTO>> mostrarPaginasDinamicamente(@RequestParam(defaultValue = "2") int tamanio,
                                                                       @RequestParam(defaultValue = "0") int numberPag
                                                                      ){
        Sort sort = Sort.by(Sort.Direction.DESC, "fechaNacimiento");

        Pageable pageable = PageRequest.of(numberPag, tamanio, sort);
        Page<PersonDTO> pagina = personaService.buscarPaginado(pageable);
        return ResponseEntity.ok(pagina);
    }

    //Metodo paginado
    @GetMapping("/query-derivada/")
    public ResponseEntity<List<PersonDTO>> queryDerivadas (@RequestParam String nombre){
        if(nombre.isBlank()){
            ResponseEntity.badRequest();
        }

        return ResponseEntity.ok(personaService.usarQueryDerivada(nombre));
    }

}
