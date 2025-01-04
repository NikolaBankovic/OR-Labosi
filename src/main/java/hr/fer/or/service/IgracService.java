package hr.fer.or.service;

import hr.fer.or.model.Igrac;

import java.util.List;
import java.util.Optional;

public interface IgracService {

    List<Igrac> getAllIgraci();


    Optional<Igrac> getIgracById(Long id);


    List<Igrac> searchByIme(String ime);


    List<Igrac> filterByPozicija(String pozicija);


    List<Igrac> filterByVisina(Double visina);


    Igrac addIgrac(Igrac igrac);


    Igrac updateIgrac(Long id, Igrac updatedIgrac);


    void deleteIgrac(Long id);

}
