package com.example.demo2Prueba.Services;

import com.example.demo2Prueba.dto.PersonDTO;
import com.example.demo2Prueba.dto.PersonaDTO;
import com.example.demo2Prueba.entities.Persona;
import com.example.demo2Prueba.repositories.PersonaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service("PersonaService")
public class PersonaService {

    public final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }
    public List<Persona> buscarAllPersonas(){
        return personaRepository.findAll();
    }

    public List<Persona> buscarPersonasActivas(){
        return personaRepository.findAll()
                .stream()
                .filter(x -> x.getStatus() == true)
                .toList();
    }


    public Persona crearPersona(Persona persona)
    {
        return personaRepository.save(persona);

    }
    //---------------------------manera 1 de hacer el update-----------------------
    //@Transactional(readOnly = true)
//    public Persona updatePersona(Persona persona) {
//        Persona personaEncontrada = personaRepository.findById(persona.getId())
//                .orElseThrow(() -> new RuntimeException("Persona no existe"));
//
//        personaEncontrada.setApellido(persona.getApellido());
//        personaEncontrada.setNombre(persona.getNombre());
//        personaEncontrada.setDireccion(persona.getDireccion());
//        personaEncontrada.setNombre(persona.getNombre());
//        personaEncontrada.setFechaNacimiento(persona.getFechaNacimiento());
//
//
//        personaRepository.save(personaEncontrada);
//        return personaEncontrada;
//    }
    //Otra manera de hacer el update ojo BeanUtils-------------------------------------------//
//    public Persona updatePersona(Persona persona) {
//        Optional<Persona> personaEncontrada = personaRepository.findById(persona.getId());
//        if(personaEncontrada.isEmpty()) {
//             new RuntimeException("Persona no existe");
//        }
//
//        Persona personaExistente = personaEncontrada.get();
//        .coBeanUtilspyProperties(persona,personaExistente);
//
//        return personaRepository.save(personaExistente);
//    }
//Manera 3 de hacer update con una transformacion interna

    public PersonaDTO updatePersona(PersonaDTO personaDto) {
        Persona personaEncontrada = personaRepository.findById(personaDto.getId())
                .orElseThrow(()-> new RuntimeException("Persona no Existe"));//esto es pqara ahorrarme la validacion
                    //y que directamente me traiga un objeto en vez del Optonal

        personaEncontrada.setNombre(personaDto.getNombre());
        personaEncontrada.setApellido(personaDto.getApellido());
        personaRepository.save(personaEncontrada);

        return setData(personaEncontrada);

    }
    //iNVESTIGAR ModelMapper libreria  para estos mapeos
    private  PersonaDTO setData (Persona persona){
        PersonaDTO personaNueva = new PersonaDTO();
        personaNueva.setNombre(persona.getNombre());
        personaNueva.setApellido(persona.getApellido());
        personaNueva.setId(persona.getId());
        return personaNueva;

    }
//Primer metodo el mas sencillo para el delete
    //    public boolean deletePersona(Long id) {
//
//        if(repo.existsById(id)){
//            repo.deleteById(id);
//            return true;
//        }
//        return false;
//
//    }
//segundo metodo para el delete
    public boolean deletePersona(Long id) {
        Persona personaEncontrada = personaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no existe"));

        personaEncontrada.setStatus(false);
        personaRepository.save(personaEncontrada);

        return true;
    }

    ///metodo para el query derivado
    public Persona ejemploQueryDerivada(String nombre, String apellido) {
        return personaRepository.findByNombreAndApellido(nombre,apellido);
    }

    public Page<PersonDTO> buscarPaginado(Pageable pageable) {
        Page<Persona> page = personaRepository.findAll(pageable);
        //mapeando la entidad con un lambda
        return page.map(x -> new PersonDTO(
                x.getId(),
                x.getNombre(),
                x.getApellido()

        ));
    }

    public List<PersonDTO> usarQueryDerivada(String nombre) {

        return personaRepository.encontrarCoincidenciasNombre(nombre)
                .stream()
                .map(x -> new PersonDTO(x.getId(), x.getNombre(),x.getApellido())).toList();
    }

//    public LocalDate fechaParaSerMayorDeEdad(){
//        //LocalDate
//    }

}
