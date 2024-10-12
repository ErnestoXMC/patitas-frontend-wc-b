package pe.edu.cibertec.patitasfrontendwc.dto;

import java.util.Date;

public record LogoutResponseDTO(Boolean resultado, Date fecha, String mensajeError) {
}
