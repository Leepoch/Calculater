import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String[] inputExpression = scanner.nextLine().split("");
        ExpressionInfo returnValue = new ExpressionInfo();
        String mathSymbol = returnValue.getMathSymbol(inputExpression);
        String firstNumber = returnValue.getOperators(inputExpression, mathSymbol)[0].trim();
        String secondNumber = returnValue.getOperators(inputExpression, mathSymbol)[1].trim();
        String[] operators = returnValue.getOperators(inputExpression, mathSymbol);
        if(mathSymbol.isEmpty() || operators.length > 2) {
            throw new IOException("Выражение не удовлетворяет условиям");
        }
        String firstNumberType = returnValue.getNumberType(firstNumber);
        String secondNumberType = returnValue.getNumberType(firstNumber);

        if (firstNumberType.equals("arabicNumber") && secondNumberType.equals("arabicNumber")) {
            System.out.println(returnValue.getResultInArabicNumber(firstNumber, secondNumber, mathSymbol));
        } else if(firstNumberType.equals("romeNumber") && secondNumberType.equals("romeNumber")) {
            System.out.println(returnValue.getResultInRomeNumber(firstNumber, secondNumber, mathSymbol));
        } else {
            throw new IOException("Операнды разных типов");
        }
    }
}

class ExpressionInfo {
    String getResultInRomeNumber(String firstNumber,String secondNumber,String mathSymbol) throws IOException {
        String[] romeNumbers = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX",
                "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX", "XXX", "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI", "XXXVII", "XXXVIII", "XXXIX", "XL",
                "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII", "XLIX", "L", "LI", "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX",
                "LXI", "LXII", "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX", "LXX",
                "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX",
                "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV", "LXXXVI", "LXXXVII", "LXXXVIII", "LXXXIX", "XC",
                "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI", "XCVII", "XCVIII", "XCIX", "C"
        };   //                Ничего лучше не придумал :)

        DigitConverter firstRomeNumber = DigitConverter.valueOf(firstNumber);
        DigitConverter secondRomeNumber = DigitConverter.valueOf(secondNumber);
        String firstArabicNumber = firstRomeNumber.getConversion();
        String secondArabicNumber = secondRomeNumber.getConversion();
        int result = getResultInArabicNumber(firstArabicNumber, secondArabicNumber, mathSymbol);
        if(result < 1) {
            throw new IOException("Итоговое выражение меньше единицы");
        }
        return romeNumbers[result-1];
    }

    int getResultInArabicNumber(String firstNumber, String secondNumber, String mathSymbol) throws IOException {
      int intFirstNumber = Integer.parseInt(firstNumber);
      int intSecondNumber = Integer.parseInt(secondNumber);
      if(intFirstNumber < 1 || intFirstNumber > 10 ) throw new IOException("Первое число не удовлетворяет условиям");
      if(intSecondNumber < 1 || intSecondNumber > 10) throw new IOException("Второе число не удовлетворяет условиям");
      switch (mathSymbol) {
          case "-":
              return intFirstNumber - intSecondNumber;
          case "/":
              return intFirstNumber / intSecondNumber;
          case "\\+":
              return intFirstNumber + intSecondNumber;
          case "\\*":
              return intFirstNumber * intSecondNumber;
      }
      throw new IOException("Выражение не удовлетворяет условиям");
    }

    String getNumberType(String expressionNumber) {
        String[] arabicNumbers = {"1","2","3","4","5","6","7","8","9","10"};
        String numbersType = "";
        try {
            for(String arabicNumber:arabicNumbers) {
                DigitConverter romeNumber = DigitConverter.valueOf(expressionNumber);
                String romeToArabicNumber = romeNumber.getConversion();
                if(arabicNumber.equals(romeToArabicNumber)) {
                    numbersType = "romeNumber";
                }
            }
        } catch (IllegalArgumentException e) {
            for(String arabicNumber:arabicNumbers){
                if(expressionNumber.equals(arabicNumber)) {
                    return "arabicNumber";
                }
            }
            throw new IllegalArgumentException("Выражение не удовлетворяет условиям");
        }
        return numbersType;
    }

    String[] getOperators(String[] input, String mathSymbol) {
        String newInput = String.join("", input);
        return newInput.split(mathSymbol);
    }

    String  getMathSymbol(String[] input) {
        for(String item:input) {
            if(item.equals("+")) return "\\+";
            if(item.equals("*")) return "\\*";
            if(item.equals("-")) return "-";
            if(item.equals("/")) return "/";
        }
        return "";
    }
}
