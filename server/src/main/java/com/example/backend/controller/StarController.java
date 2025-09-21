package com.example.backend.controller;

import com.example.backend.model.Star;
import com.example.backend.repository.StarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/star")
public class StarController {
    @Autowired
    private StarRepository starRepository;

    @GetMapping("/get-all")
    public ResponseEntity<List<Star>> getAllStars() {
        List<Star> stars = starRepository.findAll();
        return ResponseEntity.ok(stars);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Star> getStar(@PathVariable Long id) {
        return starRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createStar(@RequestBody Star incomingStar) {
        incomingStar.setDateCreated(new Date());

        Star savedStar = starRepository.save(incomingStar);
        return ResponseEntity.ok(savedStar);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (!starRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        starRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}