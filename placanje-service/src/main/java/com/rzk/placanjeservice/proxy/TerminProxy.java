package com.rzk.placanjeservice.proxy;

import com.rzk.placanjeservice.dto.TerminDTO;
import com.rzk.placanjeservice.dto.UslugaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "termin-service")
public interface TerminProxy {

    @GetMapping("/api/termin/getTerminDTOById/{id}")
    public ResponseEntity<TerminDTO> getTerminDTOById (@PathVariable Integer id);

    @GetMapping("/api/usluga/getById/{id}")
    UslugaDTO getUslugaById(@PathVariable("id") Integer id);

    @GetMapping("/api/termin/getPotvrdjeni")
    public List<TerminDTO> getPotvrdjeniTermini();

}
