package com.teamsys.portafolios.dto;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ResultadoBusquedaDTO {
    private long total;
    private int pagina;
    private int limite;
    private int totalPaginas;
    private List<PortafolioResponseDTO> data;
}