package com.estefanini.service;

import java.util.List;
import java.util.Objects;
import java.util.HashMap;
import java.util.LinkedHashMap;
import com.estefanini.model.RegistrarModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.core.env.Environment;
import com.estefanini.entity.GestionUsuariosEntity;
import com.estefanini.repository.GestionUsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class GestionUsuariosService {

    @Autowired
    private Environment propiedades;

    @Autowired
    private GestionUsuariosRepository repositorio;

    public ResponseEntity<Object> registrar(RegistrarModel peticion) {

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        if (Objects.isNull(peticion)) {

            resultado.put("codigo", "101");
            resultado.put("descripcion", "Peticion vacia.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getNombres()) || peticion.getNombres().trim().isEmpty()) {

            resultado.put("codigo", "102");
            resultado.put("descripcion", "Ingresar nombre(s).");

            respuesta.put("resultado", resultado);
            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getA_paterno()) || peticion.getA_paterno().trim().isEmpty()) {

            resultado.put("codigo", "103");
            resultado.put("descripcion", "Ingresar apellido paterno.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getA_materno()) || peticion.getA_materno().trim().isEmpty()) {

            resultado.put("codigo", "104");
            resultado.put("descripcion", "Ingresar apellido materno.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getCorreo()) || peticion.getCorreo().trim().isEmpty()) {

            resultado.put("codigo", "105");
            resultado.put("descripcion", "Ingresar correo electronico.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getC_u()) || peticion.getC_u().trim().isEmpty()) {

            resultado.put("codigo", "106");
            resultado.put("descripcion", "Ingresar contraseña.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        Integer identificador;

        try {

            identificador = repositorio.consultar_identificador();

        } catch (Exception e) {

            resultado.put("codigo", "-54");
            resultado.put("descripcion", "No fue posible consultar el identificador.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        if (Objects.isNull(identificador)) {

            identificador = 1;

        } else {

            identificador++;

        }

        try {

            repositorio.registrar_usuario(identificador,
                    peticion.getNombres().trim(),
                    peticion.getA_paterno().trim(),
                    peticion.getA_materno().trim(),
                    peticion.getCorreo().trim(),
                    peticion.getC_u().trim());

        } catch (Exception e) {

            resultado.put("codigo", "-55");
            resultado.put("descripcion", "No fue posible registrar al usuario.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        resultado.put("codigo", "100");
        resultado.put("descripcion", "Usuario registrado.");

        respuesta.put("resultado", resultado);
        respuesta.put("identificador", identificador);
        return new ResponseEntity(respuesta, HttpStatus.OK);

    }

    public ResponseEntity<Object> eliminar(Integer identificador) {

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        try {

            GestionUsuariosEntity usuario = repositorio.consultar_usuario(identificador, Integer.valueOf(propiedades.getProperty("valor.estatus.activo")));

            if (Objects.isNull(usuario)) {

                resultado.put("codigo", "101");
                resultado.put("descripcion", "Usuario no valido.");

                respuesta.put("resultado", resultado);
                return new ResponseEntity<>(respuesta, HttpStatus.CONFLICT);

            }

        } catch (Exception e) {

            resultado.put("codigo", "-58");
            resultado.put("descripcion", "No fue posible consultar la informacion del usuario.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        try {

            Integer eliminar = repositorio.eliminar_usuario(identificador, Integer.valueOf(propiedades.getProperty("valor.estatus.inactivo")));

            if (eliminar != 1) {

                resultado.put("codigo", "102");
                resultado.put("descripcion", "Eliminacion fallida.");

                respuesta.put("resultado", resultado);
                return new ResponseEntity<>(respuesta, HttpStatus.CONFLICT);

            }

            resultado.put("codigo", "100");
            resultado.put("descripcion", "Usuario eliminado.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);

        } catch (Exception e) {

            resultado.put("codigo", "-56");
            resultado.put("descripcion", "No fue posible eliminar la informacion del usuario.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    public ResponseEntity<Object> usuarios() {

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        try {

            List<GestionUsuariosEntity> usuarios = repositorio.consultar_usuarios(Integer.valueOf(propiedades.getProperty("valor.estatus.activo")));

            if (usuarios.isEmpty()) {

                resultado.put("codigo", "101");
                resultado.put("descripcion", "No se encontraron usuarios activos.");

                respuesta.put("resultado", resultado);
                return new ResponseEntity<>(respuesta, HttpStatus.OK);

            }

            resultado.put("codigo", "100");
            resultado.put("descripcion", "Peticion realizada con exito.");

            respuesta.put("resultado", resultado);
            respuesta.put("usuarios", usuarios);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);

        } catch (Exception e) {

            resultado.put("codigo", "-57");
            resultado.put("descripcion", "No fue posible consultar la informacion de los usuarios.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    public ResponseEntity<Object> usuario(Integer identificador) {

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        try {

            GestionUsuariosEntity usuario = repositorio.consultar_usuario(identificador, Integer.valueOf(propiedades.getProperty("valor.estatus.activo")));

            if (Objects.isNull(usuario)) {

                resultado.put("codigo", "101");
                resultado.put("descripcion", "Usuario no valido.");

                respuesta.put("resultado", resultado);
                return new ResponseEntity<>(respuesta, HttpStatus.CONFLICT);

            }

            resultado.put("codigo", "100");
            resultado.put("descripcion", "Peticion realizada con exito.");

            respuesta.put("resultado", resultado);

            HashMap<String, String> informacion_usuario = new LinkedHashMap<>();
            informacion_usuario.put("nombres", usuario.getNombres());
            informacion_usuario.put("a_paterno", usuario.getA_paterno());
            informacion_usuario.put("a_materno", usuario.getA_materno());
            informacion_usuario.put("correo", usuario.getCorreo());
            informacion_usuario.put("c_u", usuario.getC_u());

            respuesta.put("usuario", informacion_usuario);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);

        } catch (Exception e) {

            resultado.put("codigo", "-58");
            resultado.put("descripcion", "No fue posible consultar la informacion del usuario.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    public ResponseEntity<Object> actualizar_usuario(Integer identificador, RegistrarModel peticion) {

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        if (Objects.isNull(peticion)) {

            resultado.put("codigo", "101");
            resultado.put("descripcion", "Peticion vacia.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getNombres()) || peticion.getNombres().trim().isEmpty()) {

            resultado.put("codigo", "102");
            resultado.put("descripcion", "Ingresar nombre(s).");

            respuesta.put("resultado", resultado);
            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getA_paterno()) || peticion.getA_paterno().trim().isEmpty()) {

            resultado.put("codigo", "103");
            resultado.put("descripcion", "Ingresar apellido paterno.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getA_materno()) || peticion.getA_materno().trim().isEmpty()) {

            resultado.put("codigo", "104");
            resultado.put("descripcion", "Ingresar apellido materno.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getCorreo()) || peticion.getCorreo().trim().isEmpty()) {

            resultado.put("codigo", "105");
            resultado.put("descripcion", "Ingresar correo electronico.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getC_u()) || peticion.getC_u().trim().isEmpty()) {

            resultado.put("codigo", "106");
            resultado.put("descripcion", "Ingresar contraseña.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        try {

            GestionUsuariosEntity usuario = repositorio.consultar_usuario(identificador, Integer.valueOf(propiedades.getProperty("valor.estatus.activo")));

            if (Objects.isNull(usuario)) {

                resultado.put("codigo", "107");
                resultado.put("descripcion", "Usuario no valido.");

                respuesta.put("resultado", resultado);
                return new ResponseEntity<>(respuesta, HttpStatus.CONFLICT);

            }

        } catch (Exception e) {

            resultado.put("codigo", "-58");
            resultado.put("descripcion", "No fue posible consultar la informacion del usuario.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        try {

            Integer actualizar = repositorio.actualizar_usuario(peticion.getNombres().trim(),
                    peticion.getA_paterno().trim(),
                    peticion.getA_materno().trim(),
                    peticion.getCorreo().trim(),
                    peticion.getC_u().trim(),
                    identificador);

            if (actualizar != 1) {

                resultado.put("codigo", "108");
                resultado.put("descripcion", "Actualizacion de usuario fallida.");

                respuesta.put("resultado", resultado);
                return new ResponseEntity<>(respuesta, HttpStatus.CONFLICT);

            }

        } catch (Exception e) {

            resultado.put("codigo", "-60");
            resultado.put("descripcion", "No fue posible actualizar la informacion del usuario.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        resultado.put("codigo", "100");
        resultado.put("descripcion", "Usuario actualizado.");

        respuesta.put("resultado", resultado);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);

    }

    public ResponseEntity<Object> actualizar_correo(Integer identificador, RegistrarModel peticion) {

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        if (Objects.isNull(peticion)) {

            resultado.put("codigo", "101");
            resultado.put("descripcion", "Peticion vacia.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getCorreo()) || peticion.getCorreo().trim().isEmpty()) {

            resultado.put("codigo", "102");
            resultado.put("descripcion", "Ingresar correo electronico.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        try {

            GestionUsuariosEntity usuario = repositorio.consultar_usuario(identificador, Integer.valueOf(propiedades.getProperty("valor.estatus.activo")));

            if (Objects.isNull(usuario)) {

                resultado.put("codigo", "103");
                resultado.put("descripcion", "Usuario no valido.");

                respuesta.put("resultado", resultado);
                return new ResponseEntity<>(respuesta, HttpStatus.CONFLICT);

            }

        } catch (Exception e) {

            resultado.put("codigo", "-58");
            resultado.put("descripcion", "No fue posible consultar la informacion del usuario.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        try {

            Integer actualizar = repositorio.actualizar_correo(peticion.getCorreo().trim(), identificador);

            if (actualizar != 1) {

                resultado.put("codigo", "104");
                resultado.put("descripcion", "Actualizacion de correo fallida.");

                respuesta.put("resultado", resultado);
                return new ResponseEntity<>(respuesta, HttpStatus.CONFLICT);

            }

        } catch (Exception e) {

            resultado.put("codigo", "-59");
            resultado.put("descripcion", "No fue posible actualizar el correo.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        resultado.put("codigo", "100");
        resultado.put("descripcion", "Correo actualizado.");

        respuesta.put("resultado", resultado);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);

    }

}
