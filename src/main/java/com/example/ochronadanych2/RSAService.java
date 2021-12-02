package com.example.ochronadanych2;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RSAService {

    //ascii^d mod n
    public static String encode(RSA rsa) {
            StringBuilder message = new StringBuilder();
            Arrays.stream(rsa.getMessage().split("")).forEach(splitedmessage -> {
                List<String> splittetMessage = Arrays.stream(splitedmessage.split(""))
                        .collect(Collectors.toList());
                if(splittetMessage.size() % 2== 1){
                    splittetMessage.add("0");
                    splittetMessage.add("0");
                    splittetMessage.add("0");
                }
                String keyIndex = rsa.getKeyIndex();
                Map<String, KeyPair> keyPairs = null;
                try {
                    keyPairs = FileService.getKeyPairs();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                KeyPair keyPair = keyPairs.get(keyIndex);
                PublicKey publicKey = keyPair.getPublicKey();
                PrivateKey privateKey = keyPair.getPrivateKey();
                BigInteger result= BigInteger.valueOf(1);

                BigInteger e = BigInteger.valueOf(Integer.parseInt(publicKey.getE()));
                BigInteger n = BigInteger.valueOf(Long.parseLong(privateKey.getN()));
                for (int i = 0; i < splittetMessage.size(); i++) {
                    BigInteger tmp = BigInteger.valueOf(splittetMessage.get(i).charAt(0));
                    result = tmp.modPow(e, n);
                    message.append(result).append(",");
                }
            });
        FileService.saveEncrypted(message.toString());
            return message.toString();
    }

    public static String decode(RSA rsa) {
            List<String> splittetMessage = Arrays.stream(rsa.getMessage().split(","))
                    .collect(Collectors.toList());
            String keyIndex = rsa.getKeyIndex();
        Map<String, KeyPair> keyPairs = null;
        try {
            keyPairs = FileService.getKeyPairs();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        KeyPair keyPair = keyPairs.get(keyIndex);
            PrivateKey privateKey = keyPair.getPrivateKey();
            BigInteger result;
            StringBuilder message = new StringBuilder();
        BigInteger d = BigInteger.valueOf(Long.parseLong(privateKey.getD()));
        BigInteger n = BigInteger.valueOf(Long.parseLong(privateKey.getN()));
        for (int i = 0; i < splittetMessage.size(); i++) {
                BigInteger val = BigInteger.valueOf(Long.parseLong(splittetMessage.get(i)));
                BigInteger tmp = val;
                result = tmp.modPow(d, n);
                message.append((char) result.intValue());
            }
            System.out.println(message.toString());
        FileService.saveDecrypted(message.toString().replaceAll("0", ""));
        return message.toString();
    }

   public static BigInteger pow(BigInteger base, BigInteger exponent) {
        BigInteger result = BigInteger.ONE;
        while (exponent.signum() > 0) {
            if (exponent.testBit(0)) result = result.multiply(base);
            base = base.multiply(base);
            exponent = exponent.shiftRight(1);
        }
        return result;
    }


    public static void main(String[] args) {
        String message = encode(RSA.builder().message("K K").keyIndex(String.valueOf(0)).build());
        String decode = decode(RSA.builder().message(message).keyIndex(String.valueOf(0)).build());
        int a;
    }

}
