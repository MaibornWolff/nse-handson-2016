package de.maibornwolff.microservices.account.controller;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import de.maibornwolff.microservices.account.exception.NotFoundException;
import de.maibornwolff.microservices.account.model.Account;
import de.maibornwolff.microservices.account.repository.AccountRepository;

@RestController
@RequestMapping(value = "/account")
public class AccountController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountRepository accountRepository;


    @RequestMapping(
            value = "{badgeNumber}",
            method = RequestMethod.GET,
            produces = "application/json")
    public Account get(@PathVariable String badgeNumber) {
        LOGGER.info("Search Account for badgeNumber: " + badgeNumber);
        Account account = accountRepository.findOne(badgeNumber);
        if (account == null) {
            throw new NotFoundException("No account for badgeNumber: " + badgeNumber);
        }
        LOGGER.info("Returning Account: " + account);
        return account;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = "application/json")
    public Collection<Account> list() {
        return accountRepository.findAll();
    }


    /*
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public Account save(@RequestBody Account account) {
        account = accountRepository.save(account);
        return account;
    }



    @RequestMapping(
            value = "/{badgeNumber}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    public Account delete(@PathVariable String badgeNumber) {
        Account account = get(badgeNumber);
        accountRepository.delete(account);
        return account;
    }
    */
}