package hr.fer.or.controller;

import hr.fer.or.service.DataExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class DataExportController {

    private final DataExportService dataExportService;

    @GetMapping("/refresh-snapshots")
    public String refreshSnapshots(Model model, @AuthenticationPrincipal OidcUser principal) {
        if (principal != null) {
            model.addAttribute("profile", principal.getClaims());
            try {
                dataExportService.exportDataToCsvAndJson();
                return "download";
            } catch (IOException e) {
                return "Pogreška pri izvozu podataka: " + e.getMessage();
            }
        }
        return "index";
    }
}
