package com.example.ochronadanych2;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PublicKey {
    String d;
    String n;
}
