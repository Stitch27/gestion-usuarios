package com.estefanini.controller;

import com.estefanini.model.RegistrarModel;
import org.springframework.http.ResponseEntity;
import com.estefanini.service.GestionUsuariosService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/middleware")
public class GestionUsuariosController {

    @Autowired
    private GestionUsuariosService servicio;

    @PostMapping("/usuarios")
    public ResponseEntity<Object> registrar(@RequestBody(required = false) RegistrarModel peticion) {

        return servicio.registrar(peticion);

    }

    @DeleteMapping("/usuarios/{identificador}")
    public ResponseEntity<Object> eliminar(@PathVariable Integer identificador) {

        return servicio.eliminar(identificador);

    }

    @GetMapping("/usuarios")
    public ResponseEntity<Object> usuarios() {

        return servicio.usuarios();

    }

    @GetMapping("/usuarios/{identificador}")
    public ResponseEntity<Object> usuario(@PathVariable Integer identificador) {

        return servicio.usuario(identificador);

    }

    @PutMapping("/usuarios/{identificador}")
    public ResponseEntity<Object> actualizar_usuario(@PathVariable Integer identificador, @RequestBody(required = false) RegistrarModel peticion) {

        return servicio.actualizar_usuario(identificador, peticion);

    }

    @PatchMapping("/usuarios/{identificador}")
    public ResponseEntity<Object> actualizar_correo(@PathVariable Integer identificador, @RequestBody(required = false) RegistrarModel peticion) {

        return servicio.actualizar_correo(identificador, peticion);

    }

}
