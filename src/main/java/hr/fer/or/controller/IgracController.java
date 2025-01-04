package hr.fer.or.controller;

import hr.fer.or.model.Igrac;
import hr.fer.or.response.ApiResponse;
import hr.fer.or.service.IgracService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/player")
@RequiredArgsConstructor
public class IgracController {

    private final IgracService igracService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Igrac>>> getAllIgraci() {
        List<Igrac> igraci = igracService.getAllIgraci();
        return ResponseEntity.ok(new ApiResponse<>("OK", "Fetched all players", igraci));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Igrac>> getIgracById(@PathVariable final Long id) {
        return igracService.getIgracById(id)
                .map(igrac -> ResponseEntity.ok(new ApiResponse<>("OK", "Fetched player object", igrac)))
                .orElse(ResponseEntity.status(404).body(new ApiResponse<>("Not Found", "Player with the provided ID doesn't exist", null)));
    }

    @GetMapping("/name")
    public ResponseEntity<ApiResponse<List<Igrac>>> searchByIme(@RequestParam final String ime) {
        List<Igrac> igraci = igracService.searchByIme(ime);
        return ResponseEntity.ok(new ApiResponse<>("OK", "Fetched players matching the provided name", igraci));
    }

    @GetMapping("/position")
    public ResponseEntity<ApiResponse<List<Igrac>>> filterByPozicija(@RequestParam final String pozicija) {
        List<Igrac> igraci = igracService.filterByPozicija(pozicija);
        return ResponseEntity.ok(new ApiResponse<>("OK", "Fetched players filtered by position", igraci));
    }

    @GetMapping("/height")
    public ResponseEntity<ApiResponse<List<Igrac>>> filterByVisina(@RequestParam final Double minVisina) {
        List<Igrac> igraci = igracService.filterByVisina(minVisina);
        return ResponseEntity.ok(new ApiResponse<>("OK", "Fetched players with height above " + minVisina, igraci));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Igrac>> addIgrac(@RequestBody @Valid final Igrac igrac) {
        Igrac savedIgrac = igracService.addIgrac(igrac);
        return ResponseEntity.ok(new ApiResponse<>("Created", "Player successfully added", savedIgrac));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Igrac>> updateIgrac(@PathVariable final Long id, @RequestBody @Valid final Igrac updatedIgrac) {
        Igrac updated = igracService.updateIgrac(id, updatedIgrac);
        return ResponseEntity.ok(new ApiResponse<>("OK", "Player successfully updated", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteIgrac(@PathVariable final Long id) {
        igracService.deleteIgrac(id);
        return ResponseEntity.ok(new ApiResponse<>("OK", "Player successfully deleted", null));
    }
}
