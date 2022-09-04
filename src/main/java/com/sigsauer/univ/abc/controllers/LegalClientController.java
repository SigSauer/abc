package com.sigsauer.univ.abc.controllers;

import com.sigsauer.univ.abc.models.clients.LegalClient;
import com.sigsauer.univ.abc.models.exceptions.LegalClientAlreadyExistException;
import com.sigsauer.univ.abc.models.exceptions.LegalClientIsBlockedException;
import com.sigsauer.univ.abc.models.exceptions.LegalClientNotFoundException;
import com.sigsauer.univ.abc.service.LegalClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:8081", "http://192.168.1.112:8081/" },
        methods = {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.PATCH},
        allowedHeaders = "*",
        maxAge = 3600)
@RestController
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RISK', 'COLLECTOR')")
@RequestMapping("/api/clients/legal")
public class LegalClientController {

    private static final Logger log = LoggerFactory.getLogger(LegalClientController.class);

    @Autowired
    LegalClientService lcService;

    //Legal client's REST

    @GetMapping
    ResponseEntity<List<LegalClient>> getAll(@RequestParam(required = false) String edrpou,
                                             @RequestParam(required = false) String title,
                                             @RequestParam(required = false) Boolean status) {
        log.info("IN LegalClientController GET request with param: edrpou={}, title={} status={}", edrpou, status, title);
        List<LegalClient> clients = lcService.findAll();
        if(status != null) return ResponseEntity.ok(clients.stream().
                filter(LegalClient::isActive).collect(Collectors.toList()));
        if(title != null) return ResponseEntity.ok(clients.stream().
                filter(c -> c.getTitle().contains(title)).collect(Collectors.toList()));
        if(edrpou != null) return ResponseEntity.ok(clients.stream().
                filter(c -> c.getEdrpou().contains(edrpou)).collect(Collectors.toList()));
        return ResponseEntity.ok(clients);
    }

    @GetMapping("{id}")
    ResponseEntity getOne(@PathVariable Long id) {
        log.info("IN LegalClientController GET(id) request with id={}", id);
        try {
            LegalClient lc = lcService.findById(id);
            return ResponseEntity.ok(lc);
        }catch (LegalClientNotFoundException e) {
            Map<String, String> message = new HashMap<>();
            message.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(message);
        }

    }


    @PostMapping
    ResponseEntity create(@RequestBody LegalClient ls) {
        log.info("IN LegalClientController POST request");
        try {
            ls = lcService.add(ls);
            return ResponseEntity.ok(ls);
        } catch (LegalClientAlreadyExistException e) {
            Map<String, String> message = new HashMap<>();
            message.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(message);
        }
    }

    @PutMapping("{id}")
    ResponseEntity update(@PathVariable Long id, @RequestBody LegalClient ls) {
        log.info("IN LegalClientController PUT request by id={}", id);
        try {
            ls = lcService.update(id, ls);
            return ResponseEntity.ok(ls);
        } catch (LegalClientAlreadyExistException | LegalClientIsBlockedException e) {
        Map<String, String> message = new HashMap<>();
        message.put("message", e.getMessage());
        return ResponseEntity.badRequest().body(message);
    }
}

    @DeleteMapping("{id}")
    ResponseEntity delete(@PathVariable Long id) {
        log.info("IN LegalClientController DELETE request by id={}", id);
        try {
        LegalClient ls = lcService.block(id);
        return ResponseEntity.ok(ls);
    } catch (LegalClientNotFoundException e) {
        Map<String, String> message = new HashMap<>();
        message.put("message", e.getMessage());
        return ResponseEntity.badRequest().body(message);
    }
}
}
