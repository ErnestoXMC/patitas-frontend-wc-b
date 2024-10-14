package pe.edu.cibertec.patitasfrontendwc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import pe.edu.cibertec.patitasfrontendwc.config.FeignClientConfig;
import pe.edu.cibertec.patitasfrontendwc.dto.LogoutRequestDTO;
import pe.edu.cibertec.patitasfrontendwc.dto.LogoutResponseDTO;

@FeignClient(name = "logoutClient", url = "http://localhost:8081/autenticacion", configuration = FeignClientConfig.class )
public interface LogoutClient {

    @PostMapping("/logout")
    ResponseEntity<LogoutResponseDTO> logout(LogoutRequestDTO logoutRequestDTO);
}
