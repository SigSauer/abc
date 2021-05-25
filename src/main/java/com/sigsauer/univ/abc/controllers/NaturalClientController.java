package com.sigsauer.univ.abc.controllers;

import com.sigsauer.univ.abc.models.clients.NaturalClient;
import com.sigsauer.univ.abc.models.exceptions.NaturalClientAlreadyExistException;
import com.sigsauer.univ.abc.models.exceptions.NaturalClientIsBlockedException;
import com.sigsauer.univ.abc.models.exceptions.NaturalClientNotFoundException;
import com.sigsauer.univ.abc.service.NaturalClientService;
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
@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
@RequestMapping("/api/clients/natural")
public class NaturalClientController {

    private static final Logger log = LoggerFactory.getLogger(NaturalClientController.class);

    @Autowired
    NaturalClientService ncService;

    //Natural client's REST

    @GetMapping
    ResponseEntity<List<NaturalClient>> getAll(@RequestParam(required = false) String mobile,
                                               @RequestParam(required = false) String tin,
                                               @RequestParam(required = false) Boolean status) {
        log.info("IN NaturalClientController GET request with param: mobile={}, tin={}, status={}", mobile, tin, status);
        List<NaturalClient> clients = ncService.findAll();
        if(status != null) return ResponseEntity.ok(clients.stream()
                .filter(NaturalClient::isActive).collect(Collectors.toList()));
        if(mobile != null) return ResponseEntity.ok(clients.stream()
                .filter(c -> c.getMobile().contains(mobile)).collect(Collectors.toList()));
        if(tin != null) return ResponseEntity.ok(clients.stream()
                .filter(c -> c.getTin().contains(tin)).collect(Collectors.toList()));
        return ResponseEntity.ok(clients);
    }

    @GetMapping("{id}")
    ResponseEntity getOne(@PathVariable Long id) {
        log.info("IN NaturalClientController GET(id) request with id={}", id);
        try {
            NaturalClient nc = ncService.findById(id);
            return ResponseEntity.ok(nc);
        } catch (NaturalClientNotFoundException e) {
            Map<String, String> message = new HashMap<>();
            message.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(message);
        }
    }

    @PostMapping
    ResponseEntity create(@RequestBody NaturalClient nc) {
        log.info("IN NaturalClientController POST request");
        try {
            nc = ncService.add(nc);
            return ResponseEntity.ok(nc);
        } catch (NaturalClientAlreadyExistException e) {
            Map<String, String> message = new HashMap<>();
            message.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(message);
        }
    }

    @PutMapping("{id}")
    ResponseEntity update(@PathVariable Long id, @RequestBody NaturalClient nc) {
        log.info("IN NaturalClientController PUT request by id={}", id);
        try{
            nc = ncService.update(id, nc);
            return ResponseEntity.ok(nc);
        } catch (NaturalClientNotFoundException | NaturalClientIsBlockedException e) {
        Map<String, String> message = new HashMap<>();
        message.put("message", e.getMessage());
        return ResponseEntity.badRequest().body(message);
    }
}

    @DeleteMapping("{id}")
    ResponseEntity delete(@PathVariable Long id) {
        log.info("IN NaturalClientController DELETE request by id={}", id);
        try {
            NaturalClient nc = ncService.block(id);
            return ResponseEntity.ok(nc);
    }catch (NaturalClientNotFoundException e) {
        Map<String, String> message = new HashMap<>();
        message.put("message", e.getMessage());
        return ResponseEntity.badRequest().body(message);
    }
}


}
