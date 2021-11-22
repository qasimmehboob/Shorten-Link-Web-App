package com.ieng.shortenLink.controller;

import com.ieng.shortenLink.entity.ShorterLink;
import com.ieng.shortenLink.payload.Error;
import com.ieng.shortenLink.service.ShorterService;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/links")
public class ShorterController {
    Logger logger = LoggerFactory.getLogger(ShorterController.class.getSimpleName());

    @Autowired
    private ShorterService shorterService;

    @PostMapping
    public ResponseEntity createShortLink(@Valid @RequestBody ShorterLink shorter){
        UrlValidator validator = new UrlValidator(
                new String[]{"http", "https"}
        );
        logger.info("Expiry Date: " + shorter.getExpiryDate());
        if(!validator.isValid(shorter.getOriginalLink())) {
            Error error = new Error("URL", shorter.getOriginalLink(), "Invalid URL");
            return ResponseEntity.badRequest().body(error);
        }

        ShorterLink shorterLink = shorterService.createShorterUrl(shorter);
        if(shorterLink != null) {
            return new ResponseEntity(shorterLink, HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping
    public ResponseEntity getShorterLinks() {
        List<ShorterLink> shorterLinks = shorterService.getAllLinks();
        return new ResponseEntity(shorterLinks, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity updateShorterLink(@PathVariable("id") long id, @Valid @RequestBody ShorterLink shorter) {
        logger.info("Put Request - Expiry Date: " + shorter.getExpiryDate());
        ShorterLink shorterLink = null;
        try {
            shorterLink = shorterService.updateShorterUrl(id, shorter);
            return new ResponseEntity(shorterLink, HttpStatus.OK);
        } catch (Exception e) {
            Error error = new Error("URL", shorter.getOriginalLink(), e.getMessage());
            return new ResponseEntity(error, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteShorterLink(@PathVariable("id") long id) {
        try {
            shorterService.deleteShorterUrl(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{hash}")
    public ResponseEntity redirectShorter(@PathVariable("hash") String hash) {
        ShorterLink shorter = shorterService.findLinkByHash(hash);
        if (shorter != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", shorter.getOriginalLink());
            return new ResponseEntity<String>(headers, HttpStatus.FOUND);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
