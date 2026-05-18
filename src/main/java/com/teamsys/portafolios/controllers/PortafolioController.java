package com.teamsys.portafolios.controllers;

import com.teamsys.portafolios.dto.BusquedaFiltrosDTO;
import com.teamsys.portafolios.dto.ResultadoBusquedaDTO;
import com.teamsys.portafolios.services.PortafolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/portafolios")
public class PortafolioController {

    @Autowired
    private PortafolioService portafolioService;

    @PostMapping("/buscar")
    public ResponseEntity<?> buscarPortafolios(@RequestBody BusquedaFiltrosDTO filtros) {
        try {
            ResultadoBusquedaDTO resultado = portafolioService.buscarPortafoliosConFiltros(filtros);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Error al procesar la búsqueda avanzada: " + e.getMessage()
            ));
        }
    }
}