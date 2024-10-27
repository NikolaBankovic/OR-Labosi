SELECT json_agg(
    json_build_object(
        'id', i.id,
        'ime', i.ime,
        'prezime', i.prezime,
        'pozicija', i.pozicija,
        'broj_dresa', i.broj_dresa,
        'godine', i.godine,
        'visina', i.visina,
        'tezina', i.tezina,
        'drzava', i.drzava,
        'godina_pridruzivanja', i.godina_pridruzivanja,
        'prosli_klubovi', (
            SELECT json_agg(
                json_build_object(
                    'klub', pk.klub,
                    'godina_pocetka', pk.godina_pocetka,
                    'godina_zavrsetka', pk.godina_zavrsetka
                )
            )
            FROM prosli_klubovi pk
            WHERE pk.igrac_id = i.id
        )
    )
) AS svi_igraci
    FROM igraci i

