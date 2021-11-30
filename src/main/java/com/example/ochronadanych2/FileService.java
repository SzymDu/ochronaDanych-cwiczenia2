package com.example.ochronadanych2;

import java.io.*;

public class FileService {

    public static void savePublicKey(PublicKey publicKey){
        try{
            // Create new file
            String d = "D" + "-" +publicKey.getD();
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

    public static void savePrivateKey(PrivateKey privateKey){
        try{
            // Create new file
            String d = "E" + "-" +privateKey.getE();
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
    }
}
