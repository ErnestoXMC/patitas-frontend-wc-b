package pe.edu.cibertec.patitasfrontendwc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import pe.edu.cibertec.patitasfrontendwc.config.FeignClientConfig;
import pe.edu.cibertec.patitasfrontendwc.dto.LoginRequestDTO;
import pe.edu.cibertec.patitasfrontendwc.dto.LoginResponseDTO;

@FeignClient(name = "auntenticacion", url = "http://localhost:8081/autenticacion", configuration = FeignClientConfig.class)
public interface AutenticacionClient {

    @PostMapping("/login")
    ResponseEntity<LoginResponseDTO> login(LoginRequestDTO loginRequestDTO);

}
