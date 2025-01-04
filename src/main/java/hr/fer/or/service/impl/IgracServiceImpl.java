package hr.fer.or.service.impl;

import hr.fer.or.model.Igrac;
import hr.fer.or.repository.IgracRepository;
import hr.fer.or.service.IgracService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IgracServiceImpl implements IgracService {

    private final IgracRepository igracRepository;

    public List<Igrac> getAllIgraci() {
        return igracRepository.findAll();
    }

    public Optional<Igrac> getIgracById(final Long id) {
        return igracRepository.findById(id);
    }

    public List<Igrac> searchByIme(final String ime) {
        return igracRepository.findByIme(ime);
    }

    public List<Igrac> filterByPozicija(final String pozicija) {
        return igracRepository.findByPozicijaContainsIgnoreCase(pozicija);
    }

    public List<Igrac> filterByVisina(final Double visina) {
        return igracRepository.findByVisinaGreaterThan(visina);
    }

    public Igrac addIgrac(final Igrac igrac) {
        return igracRepository.save(igrac);
    }

    public Igrac updateIgrac(final Long id, final Igrac updatedIgrac) {
        return igracRepository.findById(id)
                .map(existingIgrac -> {
                    existingIgrac.setIme(updatedIgrac.getIme());
                    existingIgrac.setPrezime(updatedIgrac.getPrezime());
                    existingIgrac.setPozicija(updatedIgrac.getPozicija());
                    existingIgrac.setBrojDresa(updatedIgrac.getBrojDresa());
                    existingIgrac.setGodine(updatedIgrac.getGodine());
                    existingIgrac.setVisina(updatedIgrac.getVisina());
                    existingIgrac.setTezina(updatedIgrac.getTezina());
                    existingIgrac.setDrzava(updatedIgrac.getDrzava());
                    existingIgrac.setGodinaPridruzivanja(updatedIgrac.getGodinaPridruzivanja());
                    return igracRepository.save(existingIgrac);
                })
                .orElseThrow(() -> new RuntimeException("Igrac not found"));
    }

    public void deleteIgrac(final Long id) {
        igracRepository.deleteById(id);
    }
}
