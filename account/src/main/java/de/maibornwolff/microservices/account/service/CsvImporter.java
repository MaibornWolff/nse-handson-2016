package de.maibornwolff.microservices.account.service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import de.maibornwolff.microservices.account.model.Account;
import de.maibornwolff.microservices.account.repository.AccountRepository;

/**
 * Created by Philipp Lamp, MaibornWolff GmbH
 */
@Component
public class CsvImporter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvImporter.class);

    private static final String BADGE_NUMBER = "BadgeNumber";
    private static final String FIRST_NAME = "Vorname";
    private static final String LAST_NAME = "Nachname";
    private static final String COMPANY = "Firma";
    private static final String EMAIL = "Email";

    @Value("${account.csv}")
    private String csvFile;

    @Autowired
    private AccountRepository accountRepository;

    @PostConstruct
    public void importOnStartup() {
        importAccounts(Paths.get(csvFile));
    }


    public void importAccounts(Path csvFile) {
        try {
            List<Account> accounts = readAccountsFromCsv(csvFile);
            accountRepository.save(accounts);
            LOGGER.info("Imported " + accounts.size() + " Accounts.");
        } catch (IOException e) {
            throw new IllegalStateException("Failed to parse CSV File: " + csvFile);
        }
    }

    private List<Account> readAccountsFromCsv(Path csvFile) throws IOException {
        List<Account> accounts = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(csvFile)) {
            CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withDelimiter(';').withHeader());
            Iterator<CSVRecord> it = parser.iterator();
            while (it.hasNext()) {
                accounts.add(parse(it.next()));
            }
        }
        return accounts;
    }

    private Account parse(CSVRecord record) {
        Account account = new Account();
        account.setBadgeNumber(record.get(BADGE_NUMBER));
        account.setFirstName(record.get(FIRST_NAME));
        account.setLastName(record.get(LAST_NAME));
        account.setCompany(record.get(COMPANY));
        account.setEmail(record.get(EMAIL));
        return account;
    }
}
