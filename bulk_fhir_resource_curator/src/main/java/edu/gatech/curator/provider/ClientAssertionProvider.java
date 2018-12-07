package edu.gatech.curator.provider;

import edu.gatech.curator.entity.SourceSystemEntity;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class ClientAssertionProvider {

    @Autowired
    private KeyProvider keyProvider;

    @Autowired
    private DateProvider dateProvider;

    public String create(SourceSystemEntity sourceSystem) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        Key key = keyProvider.getPrivateKey();

        Map<String, Object> header = new HashMap<String, Object>() {{
            put("alg", key.getAlgorithm());
            put("typ", "JWT");
            put("kid", sourceSystem.getKid());
            put("jku", sourceSystem.getJku());
        }};
        String jws = Jwts.builder()
                .setHeader(header)
                .setSubject(sourceSystem.getClientId())
                .setIssuer(sourceSystem.getClientId())
                .setExpiration(dateProvider.fifteenMinutesFromNow())
                .setAudience(sourceSystem.getBaseUrl() + sourceSystem.getTokenPath()
                )
                .setId(String.valueOf(UUID.randomUUID()))
                .signWith(key)
                .compact();

        return jws;
    }


}

