package org.example.assignment.controller;

import lombok.RequiredArgsConstructor;
import org.example.assignment.model.Data;
import org.example.assignment.model.UploadResult;
import org.example.assignment.service.DataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.internalServerError;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController("/")
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;

    @PostMapping
    public ResponseEntity<UploadResult> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            return ok(dataService.upload(file.getInputStream()));
        } catch (IOException e) {
            return badRequest().build();
        }
    }

    @GetMapping("/{code}")
    public ResponseEntity<Data> getByCode(@PathVariable String code) {
        return dataService.findByCode(code).map(ResponseEntity::ok).orElseGet(() -> notFound().build());
    }

    @GetMapping
    public ResponseEntity<Collection<Data>> fetch() {
        return ok(dataService.fetch());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        return dataService.deleteAll() ? ok().build() : internalServerError().build();
    }
}
