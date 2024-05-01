package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;

public class UnitTests {

    @Test
    void test() {
        var string = KeyGenerators.secureRandom(16);
        System.out.println(new String(string.generateKey()));
    }
}
