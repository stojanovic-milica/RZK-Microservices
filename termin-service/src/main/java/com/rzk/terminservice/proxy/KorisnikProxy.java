package com.rzk.terminservice.proxy;

import com.rzk.terminservice.dto.KorisnikDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "korisnik-service")
//@FeignClient(name = "korisnik-service", url = "http://localhost:8081")
public interface KorisnikProxy {

    @GetMapping("/api/korisnik/{id}")
    ResponseEntity<KorisnikDTO> getKorisnikById (@PathVariable("id") int id);

}
