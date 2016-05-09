package de.maibornwolff.microservices.account.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import de.maibornwolff.microservices.account.model.Account;

/**
 * Created by Philipp Lamp, MaibornWolff GmbH
 */
@Component
public class AccountRepository  {
    private Map<String, Account> accounts = new HashMap<>();

    public Account findOne(String badgeNumber) {
        return accounts.get(badgeNumber);
    }

    public Collection<Account> findAll() {
        return accounts.values();
    }

    public void save(Iterable<Account> accounts) {
        for (Account account : accounts) {
            save(account);
        }
    }
    public void save(Account account) {
        if (account.getBadgeNumber() == null) {
            throw new IllegalArgumentException("BadgeNumber is null");
        }
        accounts.put(account.getBadgeNumber(), account);
    }
}
