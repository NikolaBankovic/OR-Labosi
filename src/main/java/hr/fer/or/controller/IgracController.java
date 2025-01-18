package hr.fer.or.controller;

import hr.fer.or.model.Igrac;
import hr.fer.or.response.ApiResponse;
import hr.fer.or.service.IgracService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class IgracController {

    private final IgracService igracService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getAllIgraci() {
        List<Igrac> igraci = igracService.getAllIgraci();
        List<Map<String, Object>> igraciJsonLd = igraci.stream()
                .map(this::convertToJsonLd)
                .toList();
        return ResponseEntity.ok(new ApiResponse<>("OK", "Fetched all players", igraciJsonLd));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getIgracById(@PathVariable final Long id) {
        return igracService.getIgracById(id)
                .map(igrac -> ResponseEntity.ok(new ApiResponse<>("OK", "Fetched player object", convertToJsonLd(igrac))))
                .orElse(ResponseEntity.status(404).body(new ApiResponse<>("Not Found", "Player with the provided ID doesn't exist", null)));
    }

    @GetMapping("/name")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> searchByIme(@RequestParam final String ime) {
        List<Igrac> igraci = igracService.searchByIme(ime);
        List<Map<String, Object>> igraciJsonLd = igraci.stream()
                .map(this::convertToJsonLd)
                .toList();
        return ResponseEntity.ok(new ApiResponse<>("OK", "Fetched players matching the provided name", igraciJsonLd));
    }

    @GetMapping("/position")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> filterByPozicija(@RequestParam final String pozicija) {
        List<Igrac> igraci = igracService.filterByPozicija(pozicija);
        List<Map<String, Object>> igraciJsonLd = igraci.stream()
                .map(this::convertToJsonLd)
                .toList();
        return ResponseEntity.ok(new ApiResponse<>("OK", "Fetched players filtered by position", igraciJsonLd));
    }

    @GetMapping("/height")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> filterByVisina(@RequestParam final Double minVisina) {
        List<Igrac> igraci = igracService.filterByVisina(minVisina);
        List<Map<String, Object>> igraciJsonLd = igraci.stream()
                .map(this::convertToJsonLd)
                .toList();
        return ResponseEntity.ok(new ApiResponse<>("OK", "Fetched players with height above " + minVisina, igraciJsonLd));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> addIgrac(@RequestBody @Valid final Igrac igrac) {
        Igrac savedIgrac = igracService.addIgrac(igrac);
        return ResponseEntity.ok(new ApiResponse<>("Created", "Player successfully added", convertToJsonLd(savedIgrac)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateIgrac(@PathVariable final Long id, @RequestBody @Valid final Igrac updatedIgrac) {
        Igrac updated = igracService.updateIgrac(id, updatedIgrac);
        return ResponseEntity.ok(new ApiResponse<>("OK", "Player successfully updated", convertToJsonLd(updated)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteIgrac(@PathVariable final Long id) {
        igracService.deleteIgrac(id);
        return ResponseEntity.ok(new ApiResponse<>("OK", "Player successfully deleted", null));
    }

    private Map<String, Object> convertToJsonLd(Igrac igrac) {
        Map<String, Object> jsonLd = new HashMap<>();
        jsonLd.put("@context", "http://schema.org");
        jsonLd.put("@type", "Person");

        jsonLd.put("id", igrac.getId());
        jsonLd.put("ime", Map.of("@type", "Person", "name", igrac.getIme()));
        jsonLd.put("prezime", Map.of("@type", "Person", "familyName", igrac.getPrezime()));
        jsonLd.put("pozicija", Map.of("@type", "Role", "name", igrac.getPozicija(), "description", "A forward is a player who is positioned closest to the opponent's goal, responsible for scoring goals."));
        jsonLd.put("brojDresa", igrac.getBrojDresa());
        jsonLd.put("godine", igrac.getGodine());
        jsonLd.put("visina", Map.of("@type", "QuantitativeValue", "value", igrac.getVisina(), "unitCode", "CM"));
        jsonLd.put("tezina", Map.of("@type", "QuantitativeValue", "value", igrac.getTezina(), "unitCode", "KG"));
        jsonLd.put("drzava", igrac.getDrzava());
        jsonLd.put("godinaPridruzivanja", igrac.getGodinaPridruzivanja());

        return jsonLd;
    }
}
