package com.example.ochronadanych2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class KeyResource {

    @GetMapping("/keyGenerator")
    public KeyPair generateKeys(){
        KeyPair pairs = KeyService.generate();
        FileService.savePublicKey(pairs.getPublicKey());
        FileService.savePrivateKey(pairs.getPrivateKey());
        return pairs;
    }
}
