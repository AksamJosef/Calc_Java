import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    public static String runMenu() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Введите пример:");
        String inputed = scan.nextLine();
        return inputed;
    }


    public static int transferCharToInt(char[] array) {
        String inStr = new String(array);
        int result = 0;
        try {
            result = Integer.valueOf(inStr);
        } catch (NumberFormatException e){
            System.out.println("Вводите целые числа");
            System.exit(0);
        }

        return result;
    }


    // все вычисления будут производиться в араби, поэтому нужно преобразовывать числа для арабской сс.
    public static int transferRomToArabic(char[] chArray) {
        String s = new String(chArray);
        int result = 0;
        // необходимо создать словарь соответствия римских и арабских цифр. тк. входные значения не более 10 вкл, то макс число - Х.
        TreeMap<Character, Integer> pair = new TreeMap<>();

        pair.put('I', 1);
        pair.put('V', 5);
        pair.put('X', 10);

        int len = s.length();
        int t0;
        char[] arr = s.toCharArray();
        result = pair.get(arr[len - 1]);

        for (int i = len - 2; i >= 0; i--) {
            t0 = pair.get(arr[i]);

            if (t0 < pair.get(arr[i + 1])) {
                result -= t0;
            } else {
                result += t0;
            }
        }
        return result;
    }

    public static String transferArabicToRom(int num) {

        String result = "";
        TreeMap<Integer, String> pair = new TreeMap<>();
// максимальное выходное значение -- 100
        pair.put(1, "I");
        pair.put(4, "IV");
        pair.put(5, "V");
        pair.put(9, "IX");
        pair.put(10, "X");
        pair.put(40, "XL");
        pair.put(50, "L");
        pair.put(90, "XC");
        pair.put(100, "C");

        int key;

        do {
            key = pair.floorKey(num); // ищем наиболее близкое не превосходящее число, записываем в строку и вычитаем
            result += pair.get(key);
            num -= key;
        } while (num != 0);


        return result;
    }

    public static String calc(String input) {
        String formattedStr = input.replaceAll(" ", "");
        char[] charIn = formattedStr.toCharArray();
        int a = 0, b = 0, result = 0, pos = 0, counter = 0;
        int bothRom = -1;

        for (int i = 0; i < charIn.length; i++) {
            switch (charIn[i]) {
                case '+', '-', '*', '/' -> {
                    counter++;
                }
            }
        }

        if (counter != 1) {
            System.out.println("Неправильный формат операции");
            System.exit(0);
        }

        for (char x : charIn) {
            if (x == 'L' | x == 'C' | x == 'M') {
                System.out.println("Калькулятор работает с числами в диапазоне от 1 до 10");
                System.exit(0);
            }
        }


        for (int i = 0; i < charIn.length; i++) {
            switch (charIn[i]) {
                case '+', '-', '*', '/' -> {

                    for (char x : Arrays.copyOfRange(charIn, 0, i)) {
                        if (x == 'I' | x == 'V' | x == 'X') {
                            bothRom++;
                            break;
                        }
                    }

                    for (char x : Arrays.copyOfRange(charIn, i + 1, charIn.length)) {
                        if (x == 'I' | x == 'V' | x == 'X') {
                            bothRom++;
                            break;
                        }
                    }


                    if (bothRom == -1) {
                        a = transferCharToInt(Arrays.copyOfRange(charIn, 0, i));
                        b = transferCharToInt(Arrays.copyOfRange(charIn, i + 1, charIn.length));
                    } else if (bothRom == 1) {
                        a = transferRomToArabic(Arrays.copyOfRange(charIn, 0, i));
                        b = transferRomToArabic(Arrays.copyOfRange(charIn, i + 1, charIn.length));
                    } else if (bothRom == 0) {
                        System.out.println("Оба числа должны быть в одной системе счисления");
                        System.exit(0);
                    }
                    pos = i;
                }
            }
        }
        if (a > 10 || b > 10) {
            System.out.println("Калькулятор работает с числами в диапазоне от 1 до 10");
            System.exit(0);
        }

// не получилось реализовать через sw case, тк он применял только для последнего case, игнорируя остальные

        if (charIn[pos] == '+') {
            result = a + b;
        } else if (charIn[pos] == '-') {
            result = a - b;
        } else if (charIn[pos] == '*') {
            result = a * b;
        } else if (charIn[pos] == '/') {
            result = a / b;
        }

        if (bothRom == 1 && result < 1) {
            System.out.println("Результатом работы калькулятора с римскими числами могут быть только положительные числа");
            System.exit(0);
        }


        if (bothRom == 1) {
            return (transferArabicToRom(result));
        } else {
            return Integer.toString(result);
        }


    }





    public static void main(String[] args) {
        String userInput = runMenu();
        String mainResult = calc(userInput);

        System.out.println("Результат: " + mainResult);
    }
}

