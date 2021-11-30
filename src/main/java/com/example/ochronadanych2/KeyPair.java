package com.example.ochronadanych2;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class KeyPair {
    PrivateKey privateKey;
    PublicKey publicKey;
}
