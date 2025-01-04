package hr.fer.or.repository;

import hr.fer.or.model.Igrac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IgracRepository extends JpaRepository<Igrac, Long> {
    List<Igrac> findByIme(String ime);
    List<Igrac> findByPozicijaContainsIgnoreCase(String pozicija);
    List<Igrac> findByVisinaGreaterThan(Double visina);
}

