package hr.fer.or.service.impl;

import hr.fer.or.model.Igrac;
import hr.fer.or.repository.IgracRepository;
import hr.fer.or.service.DataExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataExportServiceImpl implements DataExportService {

    private final IgracRepository igracRepository;

    String dateString = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

    @Transactional
    public void exportDataToCsvAndJson() throws IOException {
        final String EXPORT_PATH = "src/main/resources/downloads/";

        List<Igrac> igraci = igracRepository.findAll();

        File csvFile = new File(EXPORT_PATH + "igraci_" + dateString + ".csv");
        try (FileWriter csvWriter = new FileWriter(csvFile)) {
            csvWriter.append("ID,ime,prezime,pozicija,brojDresa,godine,visina,tezina,drzava,godinaPridruzivanja\n");
            for (Igrac igrac : igraci) {
                csvWriter.append(igrac.getId() + ",");
                csvWriter.append(igrac.getIme() + ",");
                csvWriter.append(igrac.getPrezime() + ",");
                csvWriter.append(igrac.getPozicija() + ",");
                csvWriter.append(igrac.getBrojDresa() + ",");
                csvWriter.append(igrac.getGodine() + ",");
                csvWriter.append(igrac.getVisina() + ",");
                csvWriter.append(igrac.getTezina() + ",");
                csvWriter.append(igrac.getDrzava() + ",");
                csvWriter.append(igrac.getGodinaPridruzivanja() + "\n");
            }
        }

        File jsonFile = new File(EXPORT_PATH + "igraci_" + dateString + ".json");
        try (FileWriter jsonWriter = new FileWriter(jsonFile)) {
            jsonWriter.write("[\n");
            for (int i = 0; i < igraci.size(); i++) {
                Igrac igrac = igraci.get(i);
                jsonWriter.write("{\n");
                jsonWriter.write("  \"id\": \"" + igrac.getId() + "\",\n");
                jsonWriter.write("  \"ime\": \"" + igrac.getIme() + "\",\n");
                jsonWriter.write("  \"prezime\": \"" + igrac.getPrezime() + "\",\n");
                jsonWriter.write("  \"pozicija\": " + igrac.getPozicija() + ",\n");
                jsonWriter.write("  \"brojDresa\": " + igrac.getBrojDresa() + ",\n");
                jsonWriter.write("  \"godine\": " + igrac.getGodine() + "\n");
                jsonWriter.write("  \"visina\": " + igrac.getVisina() + "\n");
                jsonWriter.write("  \"tezina\": " + igrac.getTezina() + "\n");
                jsonWriter.write("  \"drzava\": " + igrac.getDrzava() + "\n");
                jsonWriter.write("  \"godinaPridruzivanja\": " + igrac.getGodinaPridruzivanja() + "\n");
                jsonWriter.write("}");
                if (i < igraci.size() - 1) {
                    jsonWriter.write(",\n");
                }
            }
            jsonWriter.write("\n]");
        }
    }
}
