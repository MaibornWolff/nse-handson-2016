package de.maibornwolff.microservices.shout.adapter;

import java.io.IOException;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.maibornwolff.microservices.shout.model.Account;
import feign.Feign;
import feign.FeignException;
import feign.Retryer;

/**
 * Created by Philipp Lamp, MaibornWolff GmbH
 */
@Component
public class AccountServiceAdapter {

    private static final String ACCOUNT_SERVICE_NAME = "account-8080";

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceAdapter.class);

    @Autowired
    private ConsulAdapter consulAdapter;


    /**
     *
     *
     * @param badgeNumber
     * @return Account for badgeNumber or null if no account for that badgeNumber exists.
     */
    public Account getAccountForBadge(String badgeNumber) {
        String accountUrl = consulAdapter.getServiceUrl(ACCOUNT_SERVICE_NAME);
        AccountServiceClient restClient = Feign.builder()
                .retryer(new Retryer.Default(5, 5, 3))
                .target(AccountServiceClient.class, accountUrl);
        LOGGER.info("Chosen AccountService: " + accountUrl + ". BadgeNumber:" + badgeNumber);
        try {
            String accountStr = restClient.getAccountForBadge(badgeNumber);
            Account account = mapToAccount(accountStr);
            LOGGER.info("Retrieved Account: " + accountStr);
            return account;
        }catch(FeignException e) {
            if (e.status() == Response.Status.NOT_FOUND.getStatusCode()) {
                //Return null if account was not found.
                return null;
            }
            throw e;
        }

    }

    private Account mapToAccount(String json){
        ObjectMapper mapper = new ObjectMapper();
        try {
            Account account = mapper.readValue(json, Account.class);
            return account;
        } catch (IOException e) {
            throw new RuntimeException("Could not map json to Account object");
        }
    }
}
