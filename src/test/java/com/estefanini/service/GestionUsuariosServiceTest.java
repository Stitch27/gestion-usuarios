package com.estefanini.service;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.context.jdbc.Sql;
import com.estefanini.entity.GestionUsuariosEntity;
import org.springframework.test.context.ActiveProfiles;
import com.estefanini.repository.GestionUsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@ActiveProfiles("test")
class GestionUsuariosServiceTest {

    @Autowired
    private GestionUsuariosRepository repositorio;

    @Test
    @Sql("classpath:test-data.sql")
    public void consultar_usuarios() {

        List<GestionUsuariosEntity> lista = repositorio.consultar_usuarios(1);
        Assertions.assertNotNull(lista);

    }

    @Test
    @Sql("classpath:test-data.sql")
    public void eliminar() {

        repositorio.eliminar_usuario(3, 0);

    }

    @Test
    @Sql("classpath:test-data.sql")
    public void consultar_usuario() {

        GestionUsuariosEntity entidad = repositorio.consultar_usuario(6, 1);
        Assertions.assertNull(entidad);

    }

    @Test
    @Sql("classpath:test-data.sql")
    public void correo() {

        Integer actualizar = repositorio.actualizar_correo("hdz.aaron.27@gmail.com", 2);
        Assertions.assertEquals(actualizar, 1);

    }

    @Test
    @Sql("classpath:test-data.sql")
    public void registrar() {

        repositorio.registrar_usuario(8, "Dariana", "Hernandez", "Hernandez", "dariana.030@gmail.com", "dariana.030#");

    }

    @Test
    @Sql("classpath:test-data.sql")
    public void actualizar_usuario() {

        Integer actualizar = repositorio.actualizar_usuario("Aaron", "Perez", "Hernandez", "stitch.027@gmail.com", "stitch.027#", 1);
        Assertions.assertEquals(actualizar, 1);

    }

}