package com.teamsys.portafolios.services;

import com.teamsys.portafolios.dto.PortafolioCompletoDTO;
import com.teamsys.portafolios.entities.*;
import com.teamsys.portafolios.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PortafolioService {

    private final ExperienciaLaboralRepository experienciaRepository;
    private final FormacionRepository formacionRepository;
    private final HabilidadTecnicaRepository habTecnicaRepository;
    private final HabilidadBlandaRepository habBlandaRepository;
    private final ProyectoRepository proyectoRepository;
    private final RedSocialRepository redSocialRepository;

    public PortafolioService(ExperienciaLaboralRepository experienciaRepository,
                             FormacionRepository formacionRepository,
                             HabilidadTecnicaRepository habTecnicaRepository,
                             HabilidadBlandaRepository habBlandaRepository,
                             ProyectoRepository proyectoRepository,
                             RedSocialRepository redSocialRepository) {
        this.experienciaRepository = experienciaRepository;
        this.formacionRepository = formacionRepository;
        this.habTecnicaRepository = habTecnicaRepository;
        this.habBlandaRepository = habBlandaRepository;
        this.proyectoRepository = proyectoRepository;
        this.redSocialRepository = redSocialRepository;
    }

    @Transactional(readOnly = true)
    public PortafolioCompletoDTO compilarPortafolio(Usuario usuario) {
        Long idUsuario = usuario.getIdUsuario();

        // 1. Mapear Experiencias Laborales (Extrayendo nombres de tecnologías)
        List<PortafolioCompletoDTO.ExperienciaLaboralResumenDTO> listaExperiencias = 
            experienciaRepository.findByUsuario(usuario).stream().map(exp -> 
                PortafolioCompletoDTO.ExperienciaLaboralResumenDTO.builder()
                    .nombreEmpresa(exp.getNombreEmpresa())
                    .cargoPuesto(exp.getCargoPuesto())
                    .areaProfesional(exp.getAreaProfesional())
                    .especializacion(exp.getEspecializacion())
                    .fechaInicio(exp.getFechaInicio().toString())
                    .fechaFin(exp.getFechaFin() != null ? exp.getFechaFin().toString() : null)
                    .actualmenteTrabajoAqui(exp.isActualmenteTrabajoAqui())
                    .modalidadTrabajo(exp.getModalidadTrabajo() != null ? exp.getModalidadTrabajo().name() : null)
                    .ubicacion(exp.getUbicacion())
                    .tipoContrato(exp.getTipoContrato() != null ? exp.getTipoContrato().name() : null)
                    .descripcionProyecto(exp.getDescripcionProyecto())
                    .evidenciaLaboralPdfUrl(exp.getEvidenciaLaboralPdfUrl())
                    .proyectoRelacionadoUrl(exp.getProyectoRelacionadoUrl())
                    .tecnologias(exp.getTecnologiasHerramientas().stream()
                            .map(Tecnologia::getNombre)
                            .collect(Collectors.toList()))
                    .build()
            ).collect(Collectors.toList());

        // 2. Mapear Formación Académica
        List<PortafolioCompletoDTO.FormacionAcademicaResumenDTO> listaFormaciones = 
            formacionRepository.findByUsuario(usuario).stream().map(form -> 
                PortafolioCompletoDTO.FormacionAcademicaResumenDTO.builder()
                    .institucion(form.getInstitucion())
                    .tituloObtenido(form.getTituloObtenido())
                    .nivel(form.getNivel() != null ? form.getNivel().name() : null)
                    .area(form.getArea())
                    .fechaInicio(form.getFechaInicio().toString())
                    .fechaFin(form.getFechaFin() != null ? form.getFechaFin().toString() : null)
                    .descripcion(form.getDescripcion())
                    .estado(form.getEstado() != null ? form.getEstado().name() : null)
                    .urlImagen(form.getUrlImagen())
                    .build()
            ).collect(Collectors.toList());

        // 3. Mapear Habilidades Técnicas (Extrayendo nombre de Categoria)
        List<PortafolioCompletoDTO.HabilidadTecnicaResumenDTO> listaHabTecnicas = 
            habTecnicaRepository.findByUsuario(usuario).stream().map(ht -> 
                PortafolioCompletoDTO.HabilidadTecnicaResumenDTO.builder()
                    .nombre(ht.getNombre())
                    .categoria(ht.getCategoria() != null ? ht.getCategoria().getNombre() : null)
                    .nivelDominio(ht.getNivelDominio() != null ? ht.getNivelDominio().name() : null)
                    .anosExperiencia(ht.getAnosExperiencia())
                    .descripcion(ht.getDescripcion())
                    .certificadoUrl(ht.getCertificadoUrl())
                    .build()
            ).collect(Collectors.toList());

        // 4. Mapear Habilidades Blandas (Extrayendo nombre de Categoria)
        List<PortafolioCompletoDTO.HabilidadBlandaResumenDTO> listaHabBlandas = 
            habBlandaRepository.findByUsuario(usuario).stream().map(hb -> 
                PortafolioCompletoDTO.HabilidadBlandaResumenDTO.builder()
                    .nombre(hb.getNombre())
                    .categoria(hb.getCategoria() != null ? hb.getCategoria().getNombre() : null)
                    .descripcion(hb.getDescripcion())
                    .evidenciaUrl(hb.getEvidenciaUrl())
                    .build()
            ).collect(Collectors.toList());

        // 5. Mapear Proyectos (Filtrando públicos por seguridad y extrayendo tecnologías)
        List<PortafolioCompletoDTO.ProyectoResumenDTO> listaProyectos = 
            proyectoRepository.findByUsuario(usuario).stream()
                .filter(Proyecto::isEsPublico)
                .map(proy -> PortafolioCompletoDTO.ProyectoResumenDTO.builder()
                    .titulo(proy.getTitulo())
                    .rolProyecto(proy.getRolProyecto())
                    .descripcion(proy.getDescripcion())
                    .urlsImagenes(proy.getUrlsImagenes())
                    .urlsAdicionales(proy.getUrlsAdicionales())
                    .tecnologias(proy.getTecnologias().stream()
                            .map(Tecnologia::getNombre)
                            .collect(Collectors.toList()))
                    .enlaceGithub(proy.getEnlaceGithub())
                    .enlaceDemo(proy.getEnlaceDemo())
                    .fechaInicio(proy.getFechaInicio())
                    .fechaFinalizacion(proy.getFechaFinalizacion())
                    .estadoProyecto(proy.getEstadoProyecto())
                    .build()
            ).collect(Collectors.toList());

        // 6. Mapear Redes Sociales (Filtrando las marcadas como públicas)
        List<PortafolioCompletoDTO.RedSocialResumenDTO> listaRedes = 
            redSocialRepository.findByUsuario(usuario).stream()
                .filter(RedSocial::isEsPublico)
                .map(red -> PortafolioCompletoDTO.RedSocialResumenDTO.builder()
                    .nombreRed(red.getNombreRed())
                    .urlPerfil(red.getUrlPerfil())
                    .build()
            ).collect(Collectors.toList());

        // Compilar todo en el DTO raíz de respuesta
        return PortafolioCompletoDTO.builder()
                .nombre(usuario.getNombre())
                .correo(usuario.getCorreo())
                .foto(usuario.getFoto())
                .profesion(usuario.getProfesion() != null ? usuario.getProfesion().getNombreProfesion() : "Sin profesión especificada")
                .biografia(usuario.getBiografia())
                .telefono(usuario.getTelefono())
                .direccion(usuario.getDireccion())
                .enlacePublico(usuario.getEnlacePublico())
                .experienciasLaborales(listaExperiencias)
                .formacionesAcademica(listaFormaciones)
                .habilidadesTecnicas(listaHabTecnicas)
                .habilidadesBlandas(listaHabBlandas)
                .proyectos(listaProyectos)
                .redesSociales(listaRedes)
                .build();
    }
}