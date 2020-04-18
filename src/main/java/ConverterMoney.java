import java.math.BigDecimal;

public class ConverterMoney {

    private static BigDecimal mult(BigDecimal amount, String coeff) {
        return amount.multiply(new BigDecimal(coeff));
    }

    public static BigDecimal convert(BigDecimal amount, String accCodeOld, String accCodeNew) {
        if (accCodeOld.equals("RUB") && accCodeNew.equals("USD")) {
            return mult(amount, "0.014");
        } else if (accCodeOld.equals("USD") && accCodeNew.equals("RUB")) {
            return mult(amount, "74.02");
        } else if (accCodeOld.equals("RUB") && accCodeNew.equals("EUR")) {
            return mult(amount, "0.012");
        } else if(accCodeOld.equals("EUR") && accCodeNew.equals("RUB")) {
            return mult(amount,  "80.39");
        } else if(accCodeOld.equals("RUB") && accCodeNew.equals("CNY")) {
            return mult(amount, "0.095");
        } else if(accCodeOld.equals("CNY") && accCodeNew.equals("RUB")) {
            return mult(amount, "10.56");
        } else if (accCodeOld.equals("USD") && accCodeNew.equals("EUR")) {
            return mult(amount, "0.91");
        } else if (accCodeOld.equals("EUR") && accCodeNew.equals("USD")) {
            return mult(amount, "1.087");
        } else if (accCodeOld.equals("USD") && accCodeNew.equals("CNY")) {
            return mult(amount, "7.074");
        } else if (accCodeOld.equals("CNY") && accCodeNew.equals("USD")) {
            return mult(amount, "0.141");
        } else if(accCodeOld.equals("EUR") && accCodeNew.equals("CNY")) {
            return mult(amount, "7.69");
        } else if (accCodeOld.equals("CNY") && accCodeNew.equals("EUR")) {
            return mult(amount, "0.129");
        }
        return null;
    }
}