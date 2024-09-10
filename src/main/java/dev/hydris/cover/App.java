package dev.hydris.cover;

import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("A calculator that can do +-*/ for integers.");
        System.out.println("q - quit");
        System.out.println("t - toggle displaying ast tree");

        Calculator calc = new Calculator();
        boolean displayTree = false;
        Scanner reader = new Scanner(System.in);

        while (true) {
            String expression = reader.nextLine();
            if (expression.equals("q")) {
                break;
            } else if (expression.equals("t")) {
                displayTree = !displayTree;
                System.out.println("Toggled ast printing.");
                continue;
            }
            // String expression = "1+5-2*6/3";
            Integer value; 

            try {
                value = calc.eval(expression, displayTree);
                System.out.println("= " + value);
            } catch (TokenException e) {
                System.out.println("Invalid token found: " + e.getMessage());
            } catch (ParsingException e) {
                System.out.println("Parsing issue found: " + e.getMessage());
            }

        }

        reader.close();
    }
}
