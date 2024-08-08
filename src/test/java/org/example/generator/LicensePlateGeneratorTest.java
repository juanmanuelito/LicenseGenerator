package org.example.generator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LicensePlateGeneratorTest {

    private       LicensePlateGenerator licensePlateGenerator = new LicensePlateGenerator(6);
    private final String                ZERO                  = "0";

    private Method getaMethodByName(String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        Method method = LicensePlateGenerator.class.getDeclaredMethod(name, parameterTypes);
        method.setAccessible(true);
        return method;
    }

    @Test
    void addNumberToLicenseSuccessfully() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        char[] license = ZERO.repeat(6).toCharArray();
        int number = 1234;
        int position = 4;
        getaMethodByName("addNumberToLicense", int.class, char[].class, int.class)
            .invoke(licensePlateGenerator, number, license, position);
        Assertions.assertEquals(String.format("%d00", number), String.valueOf(license));
    }

    @Test
    void addNumberToLicenseInvalidEntry() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        char[] license = ZERO.repeat(6).toCharArray();
        int number = 1234;
        int position = 2;
        getaMethodByName("addNumberToLicense", int.class, char[].class, int.class)
            .invoke(licensePlateGenerator, number, license, position);
        Assertions.assertEquals("000000", String.valueOf(license));
    }

    @Test
    void getLicenseInfoSuccessfully() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object result = getaMethodByName("getLicenseInfo", int.class, int.class)
            .invoke(licensePlateGenerator, 8276335, 6);
        if (!(result instanceof LicensePlateGenerator.LicenseInfo)) {
            fail("invalid Result");
        }
        LicensePlateGenerator.LicenseInfo licenseInfo = (LicensePlateGenerator.LicenseInfo) result;
        assertEquals(3600000, licenseInfo.numberOfLicenses());
        assertEquals(2, licenseInfo.letters());
    }

    @Test
    void getLicenseInfoInvalidEntry() throws NoSuchMethodException, IllegalAccessException {
        try {
            Object result = getaMethodByName("getLicenseInfo", int.class, int.class)
                .invoke(licensePlateGenerator, 998276335, 6);
            fail("Should throw an exception");
        } catch (InvocationTargetException | IllegalArgumentException ignored) {
        }
    }

    @Test
    void generateLettersSuccessfully() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        char[] license = ZERO.repeat(6).toCharArray();
        getaMethodByName("generateLetters", int.class, int.class, int.class, char[].class)
            .invoke(licensePlateGenerator, 6, 4676335, 2, license);
        assertEquals("0000RZ", String.valueOf(license));
    }
}