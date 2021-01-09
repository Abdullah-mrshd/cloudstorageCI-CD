package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.Mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.Mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.Model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.Model.NoteForm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService
{
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;
    private UserService userService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService, UserService userService)
    {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }

    // Add credential to DB after encrypting plain password
    public int addCredential(String username, CredentialForm credentialForm)
    {
        // Retrieve logged in user's id key in DB
        Integer userid = userService.getUserid(username);

        // Encrypt plain-text password
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);

        return credentialMapper.insert( new Credential(null, credentialForm.getUrl(), credentialForm.getUsername(),
                encodedKey, encryptedPassword, userid));
    }

    public List<Credential> getCredentials(String username)
    {
        // Retrieve logged in user's id key in DB
        Integer userid = userService.getUserid(username);
        // Retrieve credentials and decrypt passwords
        List<Credential> credentials = credentialMapper.retrieve(userid);

        return credentials;
    }

    public List<String> getUnencryptedPasswords(String username)
    {
        // Retrieve logged in user's id key in DB
        Integer userid = userService.getUserid(username);

        // Retrieve credentials and get unencrypted passwords
        List<Credential> credentials = credentialMapper.retrieve(userid);
        List<String> plainPasswords = new ArrayList<>();
        for(Credential credential: credentials)
        {
            plainPasswords.add(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        }
        return  plainPasswords;
    }

    public int removeCredential(Integer credentialId)
    {
        return credentialMapper.delete(credentialId);
    }

    public int changeCredential(CredentialForm credentialForm)
    {
        // Encrypt plain-text password
        String key = credentialMapper.retrieveKey(credentialForm.getCredentialId());
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(),key);

        return credentialMapper.update(credentialForm.getCredentialId(), credentialForm.getUrl(),
                credentialForm.getUsername(), encryptedPassword);
    }
}
