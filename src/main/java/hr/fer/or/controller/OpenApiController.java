package hr.fer.or.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/docs")
@RequiredArgsConstructor
public class OpenApiController {

    @GetMapping(value = "/openapi.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOpenApiSpec() throws IOException {
        Path specPath = new ClassPathResource("backend/openapi.json").getFile().toPath();
        String spec = Files.readString(specPath);
        return ResponseEntity.ok(spec);
    }
}
