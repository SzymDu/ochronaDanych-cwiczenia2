package com.example.ochronadanych2;

import java.io.*;
import java.util.*;

public class FileService {

    public static void savePublicKey(PublicKey publicKey){
        try{
            // Create new file
            String d = "E" + "-" +publicKey.getE();
            String n = "N" + "-" + publicKey.getN();
            String path="public.key";
            File file = new File(path);

            // If file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);

            // Write in file
            bw.append(d);
            bw.append(System.lineSeparator());
            bw.append(n);
            bw.append(System.lineSeparator());


            // Close connection
            bw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void saveEncrypted(String  text){
        try{
            // Create new file
            String path="tekst.enc";
            File file = new File(path);

            // If file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            // Write in file
           bw.append(text);


            // Close connection
            bw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void saveDecrypted(String  text){
        try{
            // Create new file
            String path="tekst.dec";
            File file = new File(path);

            // If file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            // Write in file
            bw.append(text);


            // Close connection
            bw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void savePrivateKey(PrivateKey privateKey){
        try{
            // Create new file
            String d = "D" + "-" +privateKey.getD();
            String n = "N" + "-" + privateKey.getN();
            String path="private.key";
            File file = new File(path);

            // If file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);

            // Write in file
            bw.append(d);
            bw.append(System.lineSeparator());
            bw.append(n);
            bw.append(System.lineSeparator());


            // Close connection
            bw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
     savePublicKey(PublicKey.builder().build());
     savePrivateKey(PrivateKey.builder().build());
        try {
            getKeyPairs();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, KeyPair> getKeyPairs() throws IOException {
        List<PrivateKey> privateKeys = getPrivateKeys();
        List<PublicKey> publicKeys = getPublicKeys();
        List<KeyPair> keyPairs = new ArrayList<>();
        Map<String, KeyPair> map = new HashMap<>();
        if(privateKeys.size() == publicKeys.size()){
            for (int i = 0; i < privateKeys.size(); i++) {
                KeyPair.KeyPairBuilder builder = KeyPair.builder();
                builder.publicKey(publicKeys.get(i));
                builder.privateKey(privateKeys.get(i));
//                keyPairs.add(builder.build());
                map.put(String.valueOf(i), builder.build());
            }
            return map;
        }


        return Collections.emptyMap();
    }

    private static List<PrivateKey> getPrivateKeys() throws IOException {
        String fileLocation = "private.key";

        FileReader fileReader = new FileReader(fileLocation);

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        int index = 1;
        List<String> tmp = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            tmp.add(line.split("-")[1]);
            index++;
        }
        List<PrivateKey> privateKeys = new ArrayList<>();
        PrivateKey.PrivateKeyBuilder builder = PrivateKey.builder();
        for (int i = 0; i < tmp.size(); i+=2) {
            builder.d(tmp.get(i));
            builder.n(tmp.get(i+1));
            privateKeys.add(builder.build());
        }
        return privateKeys;
    }

    private static List<PublicKey> getPublicKeys() throws IOException {
        String fileLocation = "public.key";

        FileReader fileReader = new FileReader(fileLocation);

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        int index = 1;
        List<String> tmp = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            tmp.add(line.split("-")[1]);
            index++;
        }
        List<PublicKey> publicKeys = new ArrayList<>();
        PublicKey.PublicKeyBuilder builder = PublicKey.builder();
        for (int i = 0; i < tmp.size(); i+=2) {
            builder.e(tmp.get(i));
            builder.n(tmp.get(i+1));
            publicKeys.add(builder.build());
        }
        return publicKeys;
    }
}
