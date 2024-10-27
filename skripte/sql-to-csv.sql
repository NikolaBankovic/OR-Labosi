COPY (
    SELECT 
        i.id,
        i.ime,
        i.prezime,
        i.pozicija,
        i.broj_dresa,
        i.godine,
        i.visina,
        i.tezina,
        i.drzava,
        i.godina_pridruzivanja,
        pk.klub,
        pk.godina_pocetka,
        pk.godina_zavrsetka
    FROM 
        igraci i
    JOIN 
        prosli_klubovi pk ON i.id = pk.igrac_id
) TO 'F:\Faks\Diplomski\OR\igraci_liverpoola.csv' DELIMITER ',' CSV HEADER;
