package org.example.utils;



import java.time.LocalDate;
import java.util.Scanner;

public class ScannerUtils {
    Scanner scanner = new Scanner(System.in);

    public String nextLine(String s) {
        System.out.print(s);
        String str = scanner.nextLine();
        return str;
    }
    public String nextLineWithColor(String s, String colorB, String color) {
        System.out.println(colorB+color+s);
        String str = scanner.nextLine();
        return str;
    }

    public int nextInt(String s) {
        int number;
        do {
            try {
                System.out.print(s);
                number = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Enter only numbers");
                scanner.nextLine();
            }
        } while (true);
        return number;
    }

    public double nextDouble(String s){
        double number;
        do {
            try {
                System.out.print(s);
                number = scanner.nextDouble();
                scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Enter only numbers");
                scanner.nextLine();
            }
        } while (true);
        return number;
    }

    public LocalDate nextLocalDate(String s) {
        LocalDate result=null;
        do {
            try {
                System.out.print(s);
                result = LocalDate.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Please enter the date in the format" +" \"yyyy-mm-dd\"!" );
            }
        }while (result==null);
        return result;
    }
}
