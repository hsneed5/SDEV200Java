public class Exercise12_09 {


    public static void main(String[] args) {
        try {
            System.out.println(bin2Dec("1011"));  
            System.out.println(bin2Dec("10201")); 
        } catch (BinaryFormatException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }


    public static int bin2Dec(String binaryString) throws BinaryFormatException {

        for (int i = 0; i < binaryString.length(); i++) {
            char ch = binaryString.charAt(i);
            if (ch != '0' && ch != '1') {
                throw new BinaryFormatException("Invalid binary string: " + binaryString);
            }
        }


        int decimalValue = 0;
        for (int i = 0; i < binaryString.length(); i++) {
            decimalValue = decimalValue * 2 + (binaryString.charAt(i) - '0');
        }

        return decimalValue;
    }
}


class BinaryFormatException extends Exception {
    public BinaryFormatException(String message) {
        super(message);
    }
}
