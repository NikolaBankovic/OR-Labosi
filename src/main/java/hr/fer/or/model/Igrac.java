package hr.fer.or.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "igraci")
public class Igrac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String ime;
    @NotBlank
    private String prezime;
    private String pozicija;
    private Integer brojDresa;
    @NotNull
    @Min(0)
    private Integer godine;
    private Double visina;
    private Double tezina;
    private String drzava;
    @NotNull
    @Min(1892)
    private Integer godinaPridruzivanja;

}
