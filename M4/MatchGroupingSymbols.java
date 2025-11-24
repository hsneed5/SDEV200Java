import java.io;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class MatchGroupingSymbols {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java MatchGroupingSymbols filename.java");
            return;
        }

        File file = new File(args[0]);

        if (!file.exists()) {
            System.out.println("File not found: " + args[0]);
            return;
        }

        try (Scanner input = new Scanner(file)) {
            Stack<Character> stack = new Stack<>();
            int lineNumber = 0;

            while (input.hasNextLine()) {
                String line = input.nextLine();
                lineNumber++;

                for (char ch : line.toCharArray()) {
                    if (ch == '(' || ch == '{' || ch == '[') {
                        stack.push(ch);
                    } else if (ch == ')' || ch == '}' || ch == ']') {
                        if (stack.isEmpty()) {
                            System.out.println("Error: unmatched closing '" + ch + "' at line " + lineNumber);
                            return;
                        }

                        char top = stack.pop();
                        if (!isMatchingPair(top, ch)) {
                            System.out.println("Error: mismatched '" + top + "' with '" + ch + "' at line " + lineNumber);
                            return;
                        }
                    }
                }
            }

            if (stack.isEmpty()) {
                System.out.println("The file has correct grouping symbols.");
            } else {
                System.out.println("Error: unmatched opening symbol(s) remain: " + stack);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error reading file.");
        }
    }

    private static boolean isMatchingPair(char open, char close) {
        return (open == '(' && close == ')') ||
               (open == '{' && close == '}') ||
               (open == '[' && close == ']');
    }
}
