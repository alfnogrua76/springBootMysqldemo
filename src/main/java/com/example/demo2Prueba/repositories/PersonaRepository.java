package com.example.demo2Prueba.repositories;

import com.example.demo2Prueba.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

//springdata jpa docs --> https://docs.spring.vmware.com/spring-data-jpa-distribution/docs/3.1.13/reference/html/index.html#jpa.repositories
public interface PersonaRepository extends JpaRepository<Persona, Long> {//JpaRepositary mas actual es ina interfaz mas ompleta

    //select conde npombre y apellido sena los que le pase
    Persona findByNombreAndApellido(String nombre, String apellido);



    //Persona findByFechaNacimientoDateAfter(LocalDate fecha);

    @Query("SELECT p FROM Persona p WHERE p.nombre LIKE '%:name%'")
    List<Persona> encontrarCoincidenciasNombre(@Param("name") String nombre);
//
//
}
