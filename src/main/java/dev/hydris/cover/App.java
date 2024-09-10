package dev.hydris.cover;

import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        Calculator calc = new Calculator();
        Scanner reader = new Scanner(System.in);

        while (true) {
            String expression = reader.nextLine();
            Integer value; 

            try {
                value = calc.eval(expression);
                System.out.println(" = " + value);
            } catch (TokenException e) {
                System.out.println("Invalid token found: " + e.getMessage());
            } catch (ParsingException e) {
                System.out.println("Parsing issue found: " + e.getMessage());
            }

        }

    }
}
