package com.sigsauer.univ.abc.controllers;

import com.sigsauer.univ.abc.models.clients.NaturalClient;
import com.sigsauer.univ.abc.models.communication.Email;
import com.sigsauer.univ.abc.models.exceptions.NaturalClientAlreadyExistException;
import com.sigsauer.univ.abc.models.exceptions.NaturalClientInvalidTin;
import com.sigsauer.univ.abc.models.exceptions.NaturalClientIsBlockedException;
import com.sigsauer.univ.abc.models.exceptions.NaturalClientNotFoundException;
import com.sigsauer.univ.abc.models.users.User;
import com.sigsauer.univ.abc.payload.request.EmailRequest;
import com.sigsauer.univ.abc.security.services.UserService;
import com.sigsauer.univ.abc.service.EmailService;
import com.sigsauer.univ.abc.service.NaturalClientService;
import com.sigsauer.univ.abc.service.ProductService;
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
@RequestMapping("/api/clients/natural")
public class NaturalClientController {

    private static final Logger log = LoggerFactory.getLogger(NaturalClientController.class);

    @Autowired
    NaturalClientService ncService;

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @Autowired
    ProductService productService;

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

    @PostMapping("{id}/send")
    ResponseEntity sendEmail(@PathVariable Long id, @RequestBody EmailRequest emailRequest) {
        log.info("IN NaturalClientController POST EMAIL request by id={}", id);
        NaturalClient nc = ncService.findById(id);
        User sender = userService.getOne(emailRequest.getUserId());
        Email email = new Email(sender, nc, emailRequest.getSubject(), emailRequest.getBody());
        log.info("Email to send: {}", emailRequest);
        //send email
        return emailService.sendEmail(email)
                ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("{id}/emails")
    ResponseEntity getEmails(@PathVariable Long id) {
        log.info("getEmails() by ID = {}", id);
        return ResponseEntity.ok(emailService.getEmailsByClientId(id));
    }

    @GetMapping("{id}/products")
    ResponseEntity getProducts(@PathVariable Long id) {
        log.info("getProducts() by ID = {}", id);
        return ResponseEntity.ok(productService.getProductsByClientId(id));
    }




}
