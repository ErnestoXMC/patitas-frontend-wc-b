package pe.edu.cibertec.patitasfrontendwc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.cibertec.patitasfrontendwc.client.AutenticacionClient;
import pe.edu.cibertec.patitasfrontendwc.client.LogoutClient;
import pe.edu.cibertec.patitasfrontendwc.dto.LoginRequestDTO;
import pe.edu.cibertec.patitasfrontendwc.dto.LoginResponseDTO;
import pe.edu.cibertec.patitasfrontendwc.dto.LogoutRequestDTO;
import pe.edu.cibertec.patitasfrontendwc.dto.LogoutResponseDTO;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins  ="http://localhost:5173")
public class LoginControllerAsync {

    @Autowired
    WebClient webClientAutenticacion;

    @Autowired
    AutenticacionClient autenticacionClient;

    @Autowired
    LogoutClient logoutClient;



    @PostMapping("/autenticar-async")
    public Mono<LoginResponseDTO> autenticar(@RequestBody LoginRequestDTO loginRequestDTO){
        if(loginRequestDTO.tipoDocumento() == null || loginRequestDTO.tipoDocumento().trim().length() == 0 ||
            loginRequestDTO.numeroDocumento() == null || loginRequestDTO.numeroDocumento().trim().length() == 0 ||
            loginRequestDTO.password() == null || loginRequestDTO.password().trim().length() == 0){

            return Mono.just(new LoginResponseDTO("01", "Error, Debe Completar correctamente sus credenciales","",  "", "", ""));
        }
        try {
            //Consumir el servicio backend de autenticacion
            return webClientAutenticacion.post()
                    .uri("/login")
                    .body(Mono.just(loginRequestDTO), LoginRequestDTO.class)
                    .retrieve()
                    .bodyToMono(LoginResponseDTO.class)
                    .flatMap(response -> {
                        if(response.codigo().equals("00")){
                            return Mono.just(new LoginResponseDTO("00", "", response.numeroDocumento(), response.tipoDocumento(), response.nombreUsuario(), ""));
                        }else{
                            return Mono.just(new LoginResponseDTO("02", "Error Autenticacion Fallida", "", "", "", ""));
                        }
                    });

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Mono.just(new LoginResponseDTO("99", "Error: Ocurrio un problema con el servidor", "", "", "", ""));
        }
    }


    @PostMapping("/cerrar-sesion-async")
    public Mono<LogoutResponseDTO> logout(@RequestBody LogoutRequestDTO logoutRequestDTO) {
        if (logoutRequestDTO.tipoDocumento() == null || logoutRequestDTO.tipoDocumento().trim().isEmpty() ||
                logoutRequestDTO.numeroDocumento() == null || logoutRequestDTO.numeroDocumento().trim().isEmpty()) {
            return Mono.just(new LogoutResponseDTO(false, null, "Error: Debe proporcionar correctamente sus credenciales"));
        }

        try {

            return webClientAutenticacion.post()
                    .uri("/logout")
                    .body(Mono.just(logoutRequestDTO), LogoutRequestDTO.class)
                    .retrieve()
                    .bodyToMono(LogoutResponseDTO.class)
                    .flatMap(response -> {
                        if (response.resultado()) {
                            return Mono.just(new LogoutResponseDTO(true, response.fecha(), ""));
                        } else {
                            return Mono.just(new LogoutResponseDTO(false, null, "Error: No se pudo cerrar la sesión correctamente"));
                        }
                    });

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Mono.just(new LogoutResponseDTO(false, null, "Error: Ocurrió un problema con el servidor"));
        }
    }


}
