package com.example.ochronadanych2;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class KeyService {
    public static KeyPair generate() {
        Double pow = Math.pow(10, 9);

        long p = (long) new Random().nextInt(pow.intValue());
        long q = (long) new Random().nextInt(pow.intValue());
        long e = generateE(p, q);
        long n = p*q;
        long d = generateD(e, p, q, n);
        while(d == 0){
            p = (long) new Random().nextInt(Integer.MAX_VALUE);
            q = (long) new Random().nextInt(Integer.MAX_VALUE);
            e = generateE(p, q);
            n = p*q;
            d = generateD(e, p, q, n);
        }
        PrivateKey privateKey = getPrivateKey(e, n);
        PublicKey publicKey = getPublicKey(d, n);
        return KeyPair.builder()
                .privateKey(privateKey)
                .publicKey(publicKey)
                .build();
    }

    private static PrivateKey getPrivateKey(long e, long n) {
        return PrivateKey.builder()
                .e(String.valueOf(e))
                .n(String.valueOf(n))
                .build();
    }

    private static PublicKey getPublicKey(long d, long n) {
        return PublicKey.builder()
                .d(String.valueOf(d))
                .n(String.valueOf(n))
                .build();
    }

    private static long generateD(long e, long p, long q, long n) {
       long gprev = n;
       long g = e;
       long vprev = 0;
       long v = 1;
       long i = 1;
       long y;
       long gnext;
       long vnext;
       long x;
       long invA;
       long nwd;
       do {
           y = gprev/g;
           gnext = gprev - y * g;
           gprev = g;
           g = gnext;
           vnext = vprev - y * v;
           vprev = v;
           v = vnext;
           i = i+1;
       } while(g != 0);
       x = vprev;
       if( x >= 0){
           invA = x;
       }
       else {
           invA = x+n;
       }
       nwd = gprev;
       if(nwd == 1) {
           return invA;
       }
       return 0;

    }

    private static Long generateE(long p, long q) {
        boolean flag = true;
        Long pq = (p -1)*(q -1);
        Long e = null;
        while(flag){
            e = (long) new Random().nextInt(1000);
            if(NWD_2(e, pq) == 1){
               break;
            }
        }
        return e;

    }

    public static Long NWD_2(Long pierwsza, Long druga)
    {
        if (druga == 0)
        {
            return pierwsza;
        }
        else
        {
            return NWD_2(druga, pierwsza%druga);
        }
    }

    public static void main(String[] args) {
        generate();
    }

}
