package org.example;

import org.example.generator.LicensePlateGenerator;

public class Main {

    public static void main(String[] args) {
        Generator licenseGenerator = new LicensePlateGenerator(6);
        System.out.println(licenseGenerator.generateSequence(8276335));
    }

}