package com.teamsys.portafolios.dto;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PortafolioResponseDTO {
    private Long id;
    private String nombreCompleto;
    private String profesion;
    private String especializacion;
    private String ubicacion;
    private String disponibilidad;
    private String modalidadTrabajo;
    private List<String> tecnologias;
    private List<String> idiomas;
    private int experienciaAnios;
    private int cantidadProyectos;
    private String fotoPerfilUrl;
    private String urlPublica;
    private String resumen;
}