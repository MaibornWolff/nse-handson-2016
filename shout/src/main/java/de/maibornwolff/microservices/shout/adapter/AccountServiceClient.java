package de.maibornwolff.microservices.shout.adapter;

import feign.Param;
import feign.RequestLine;

/**
 * Created by Andreas Jochem, MaibornWolff GmbH
 */
public interface AccountServiceClient {

    @RequestLine("GET /account/{badgeNumber}")
    String getAccountForBadge(@Param("badgeNumber") String badgeNumber);
}
