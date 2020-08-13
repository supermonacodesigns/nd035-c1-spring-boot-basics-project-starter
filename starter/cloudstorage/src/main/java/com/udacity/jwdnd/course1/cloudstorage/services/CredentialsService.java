package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {

    private final CredentialsMapper credentialsMapper;
    private final UserMapper userMapper;
    private final EncryptionService encryptionService;

    public CredentialsService(CredentialsMapper credentialsMapper, UserMapper userMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.userMapper = userMapper;
        this.encryptionService = encryptionService;
    }

    // POST
    public Integer addCredentials(Credentials credentials) {
        credentials.setPassword(encryptPassword(credentials).getPassword());


        return credentialsMapper.insertCredentials(new Credentials(null,
                credentials.getUrl(),
                credentials.getUsername(),
                credentials.getKey(),
                credentials.getPassword(),
                credentials.getUserId()));
    }

    // GET
    public List<Credentials> getCredentialsByUsername(String username) {
        List<Credentials> credentialsList = credentialsMapper.getAllCredentials(userMapper.getUser(username).getUserId());


        for (Credentials credentials : credentialsList) {
            credentials.setDecryptedPassword(decryptPassword(credentials));
        }

        return  credentialsList;
    }

    // PUT

    public Integer updateCredentials(Credentials credentials) {
        return credentialsMapper.updateCredentials(encryptPassword(credentials));
    }

    private Credentials encryptPassword(Credentials credentials) {

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        credentials.setKey(Base64.getEncoder().encodeToString(key));
        String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(),credentials.getKey());

        credentials.setPassword(encryptedPassword);

        return credentials;
    }

    private String decryptPassword(Credentials credentials) {
        return  encryptionService.decryptValue(credentials.getPassword(),credentials.getKey());
    }

    // DELETE

    public void deleteCredentials(Integer credentialId) {
        credentialsMapper.delete(credentialId);
    }
}
