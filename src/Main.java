import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input: ");
        String input = scanner.nextLine();

        try {
            System.out.println("Output: " + calc(input));
        } catch (IllegalArgumentException e) {
            System.out.println("Output: " + e.getMessage());
        }
    }

    static String calc(String input) {
        String[] tokens = input.split(" ");

        // Проверка корректности формата ввода
        if (tokens.length != 3) {
            throw new IllegalArgumentException("Invalid input format");
        }

        String num1Str = tokens[0];
        String operator = tokens[1];
        String num2Str = tokens[2];

        boolean isRoman = isRoman(num1Str) && isRoman(num2Str);
        boolean isArabic = !isRoman;

        // Проверка, что числа одного типа
        if (!isRoman && !isArabic) {
            throw new IllegalArgumentException("Numbers must be of the same type");
        }

        // Проверка корректности римских чисел
        if (isRoman) {
            if (!isCorrectRomanNumeral(num1Str) || !isCorrectRomanNumeral(num2Str)) {
                throw new IllegalArgumentException("Incorrect numbering of Roman numerals");
            }
        }

        int num1 = isRoman ? RomanToArabic(num1Str) : Integer.parseInt(num1Str);
        int num2 = isRoman ? RomanToArabic(num2Str) : Integer.parseInt(num2Str);

        // Проверка, что числа в диапазоне от 1 до 10
        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new IllegalArgumentException("Numbers must be between 1 and 10");
        }

        // Вычисление результата в зависимости от оператора
        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    throw new IllegalArgumentException("Division by zero");
                }
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Invalid operator");
        }

        // Для римских чисел проверяем, что результат не меньше 1
        if (isRoman && (result < 1 || result == 0)) {
            throw new IllegalArgumentException("Result must be a positive number in Roman numerals");
        }

        // Возвращение результата
        return isRoman ? ArabicToRoman(result) : String.valueOf(result);
    }

    static boolean isRoman(String input) {
        return input.matches("^[IVXLCDM]+$");
    }

    private static boolean isCorrectRomanNumeral(String roman) {
        return roman.matches("^(?=[MDCLXVI])M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
    }

    private static int RomanToArabic(String roman) {
        java.util.Map<Character, Integer> map = java.util.Map.of(
                'I', 1, 'V', 5, 'X', 10, 'L', 50, 'C', 100, 'D', 500, 'M', 1000
        );

        int result = 0;
        int prevValue = 0;

        // Итерируемся по римским символам с конца строки
        for (int i = roman.length() - 1; i >= 0; i--) {
            int value = map.get(roman.charAt(i));

            // Если текущее значение больше предыдущего, вычитаем его
            // Иначе, прибавляем его к результату
            result += value >= prevValue ? value : -value;

            prevValue = value;
        }

        return result;
    }

    private static String ArabicToRoman(int arabic) {
        // Массив арабских чисел
        int[] values = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };

        // Массив римских чисел
        String[] numerals = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };

        StringBuilder result = new StringBuilder();

        // Проходим по массиву арабских чисел
        for (int i = 0; i < values.length; i++) {
            // Пока арабское число больше текущего элемента массива, добавляем римское число к результату
            while (arabic >= values[i]) {
                result.append(numerals[i]);
                arabic -= values[i];
            }
        }

        return result.toString();
    }
}

