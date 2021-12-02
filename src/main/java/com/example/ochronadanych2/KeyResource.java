package com.example.ochronadanych2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Key;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/getKeys")
    public ResponseEntity<Map<String,KeyPair>> getKeys(){
        try {
            return new ResponseEntity<>(FileService.getKeyPairs(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(Collections.emptyMap(), HttpStatus.OK);
    }

    @PostMapping("/encode")
    public ResponseEntity<List<String>> encode(@RequestBody RSA rsa){
            return new ResponseEntity<>(Collections.singletonList(RSAService.encode(rsa)), HttpStatus.OK);
    }

    @PostMapping("/decode")
    public ResponseEntity<List<String>> decode(@RequestBody RSA rsa){
        return new ResponseEntity<>(Collections.singletonList(RSAService.decode(rsa)), HttpStatus.OK);
    }


}
