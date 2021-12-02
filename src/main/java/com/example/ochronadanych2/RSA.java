package com.example.ochronadanych2;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RSA {
    String message;
    String keyIndex;
}
