package com.estefanini.entity;

import lombok.Data;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
@Table(name = "TAB_GESTION_USUARIOS")
public class GestionUsuariosEntity implements Serializable {

    @Id
    @Column(name = "IDENTIFICADOR_INT")
    private Integer identificador;

    @Column(name = "NOMBRES_VAR")
    private String nombres;

    @Column(name = "A_PATERNO_VAR")
    private String a_paterno;

    @Column(name = "A_MATERNO_VAR")
    private String a_materno;

    @Column(name = "CORREO_VAR")
    private String correo;

    @Column(name = "CU_VAR")
    private String c_u;

}
