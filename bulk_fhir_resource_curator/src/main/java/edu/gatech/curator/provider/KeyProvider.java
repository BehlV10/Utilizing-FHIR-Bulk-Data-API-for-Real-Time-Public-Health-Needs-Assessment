package edu.gatech.curator.provider;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

@Component
public class KeyProvider {

    @Autowired
    private ResourceLoader resourceLoader;

    public Key getPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        Resource fileResource = resourceLoader.getResource("classpath:private_key.der");
//        Path exportOutputJsonPath = fileResource.getFile().toPath();
        byte[] privateKeyBytes = IOUtils.toByteArray(fileResource.getInputStream());
//        byte[] privateKeyBytes = Files.readAllBytes(exportOutputJsonPath);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
}
