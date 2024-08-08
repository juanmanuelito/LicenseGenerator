package org.example.generator;

import java.util.function.BiFunction;
import org.example.Generator;
import org.example.utils.Utils;

public class LicensePlateGenerator implements Generator {
    private final String ZERO = "0";
    private final int    positions;

    public LicensePlateGenerator(int positions) {
        this.positions = positions;
    }

    public record LicenseInfo(int letters, int numberOfLicenses) {
    }

    private static final BiFunction<Integer, Integer, Integer> combinations = (numberPositions, characterPositions) ->
        (int) (Math.pow(10, numberPositions) * Math.pow(26, characterPositions));

    private void addNumberToLicense(int remainNumber, char[] licensePlate, int position) {
        char[] digits = String.valueOf(remainNumber).toCharArray();
        if (position - digits.length >= 0) {
            System.arraycopy(digits, 0, licensePlate, position - digits.length, digits.length);
        }
    }

    private LicenseInfo getLicenseInfo(int num, int positions) {
        int numberOfLicenses = 0;
        for (int letters = 0; letters < positions; letters++) {
            int calculated = numberOfLicenses + combinations.apply(positions - letters, letters);
            if (num < calculated) {
                return new LicenseInfo(letters, numberOfLicenses);
            }
            numberOfLicenses = calculated;
        }
        throw new IllegalArgumentException("Not Possible to Calculate License Number");
    }

    private int generateLetters(int positions, int remainNumber, int letters, char[] licencePlate) {
        int remainingValue = remainNumber;
        for (int j = letters; j > 0; j--) {
            int groupSize = combinations.apply(positions - letters, j - 1);
            int divisionResult = remainNumber / groupSize;
            char r = Utils.toAlphabetChar(divisionResult % 26);
            licencePlate[positions - j] = r;
            remainingValue = remainNumber - divisionResult * groupSize;
        }
        return remainingValue;
    }

    @Override
    public String generateSequence(int baseNumber) {
        char[] licencePlate = ZERO.repeat(positions).toCharArray();
        LicenseInfo licenseInfo = getLicenseInfo(baseNumber, positions);
        int remainNumber = baseNumber - licenseInfo.numberOfLicenses;
        remainNumber = generateLetters(positions, remainNumber, licenseInfo.letters, licencePlate);
        addNumberToLicense(remainNumber, licencePlate, positions - licenseInfo.letters);
        return String.valueOf(licencePlate);
    }

}
