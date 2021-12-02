package com.example.ochronadanych2;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Random;

@Service
public class KeyService {
    public static KeyPair generate() {
        Double pow = Math.pow(10, 9);

        long p = generatePrime(pow);
        long q = generatePrime(pow);
        long phi = (p - 1) * (q - 1);
        long e = generateE(p, q);
        long n = p*q;
        long d = generateD2(e,phi);

        while(d == 0){
            p = generatePrime(pow);
            q = generatePrime(pow);
            e = generateE(p, q);
            n = p*q;
            d = generateD(e, p, q, n);
        }
        PrivateKey privateKey = getPrivateKey(d, n);
        PublicKey publicKey = getPublicKey(e, n);
        return KeyPair.builder()
                .privateKey(privateKey)
                .publicKey(publicKey)
                .build();
    }

    private static long generatePrime(Double pow) {
        boolean isPrime = false;
        long result = 0;
        while(!isPrime){
            result = (long) new Random().nextInt(pow.intValue());
            isPrime = isPrime(result);
        }
        return result;
    }

    public static boolean isPrime(long num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    private static PrivateKey getPrivateKey(long e, long n) {
        return PrivateKey.builder()
                .d(String.valueOf(e))
                .n(String.valueOf(n))
                .build();
    }

    private static PublicKey getPublicKey(long d, long n) {
        return PublicKey.builder()
                .e(String.valueOf(d))
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

    private static long generateD2(long a,  long n) {
        long a0,n0,p0,p1,q,r,t;

        p0 = 0; p1 = 1; a0 = a; n0 = n;
        q  = n0 / a0;
        r  = n0 % a0;
        while(r > 0)
        {
            t = p0 - q * p1;
            if(t >= 0)
                t = t % n;
            else
                t = n - ((-t) % n);
            p0 = p1; p1 = t;
            n0 = a0; a0 = r;
            q  = n0 / a0;
            r  = n0 % a0;
        }
        return p1;
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
