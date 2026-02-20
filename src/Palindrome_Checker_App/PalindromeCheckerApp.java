package Palindrome_Checker_App;

import java.util.*;

/**
 * =====================================================
 * MAIN CLASS - PalindromeCheckerApp
 * =====================================================
 *
 * All palindrome validation techniques combined.
 * Now accepts input from user.
 *
 * @author Developer
 * @version 9.0
 */

public class PalindromeCheckerApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("===========================================");
        System.out.println("             PALINDROME CHECKER            ");
        System.out.println("===========================================");
        System.out.println();
        System.out.println("===========================================");
        System.out.println("               Version: 9.0                ");
        System.out.println("    Welcome to Palindrome Application!     ");
        System.out.println("     Application started successfully.     ");
        System.out.println("===========================================");
        System.out.println();

        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        System.out.println("===========================================");

        System.out.println("UC2 - Half Loop            : " + checkHalfLoop(input));
        System.out.println("UC3 - Reverse String       : " + checkReverse(input));
        System.out.println("UC4 - Two Pointer          : " + checkTwoPointer(input));
        System.out.println("UC5 - Stack                : " + checkStack(input));
        System.out.println("UC6 - Queue + Stack        : " + checkQueueStack(input));
        System.out.println("UC7 - Deque Optimized      : " + checkDeque(input));


        scanner.close();
    }

    // UC2 - Half Loop
    public static boolean checkHalfLoop(String input) {
        for (int i = 0; i < input.length() / 2; i++) {
            if (input.charAt(i) != input.charAt(input.length() - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    // UC3 - Reverse String
    public static boolean checkReverse(String input) {
        StringBuilder reversed = new StringBuilder();
        for (int i = input.length() - 1; i >= 0; i--) {
            reversed.append(input.charAt(i));
        }
        return input.contentEquals(reversed);
    }

    // UC4 - Two Pointer
    public static boolean checkTwoPointer(String input) {
        char[] chars = input.toCharArray();
        int start = 0;
        int end = chars.length - 1;

        while (start < end) {
            if (chars[start] != chars[end]) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    // UC5 - Stack
    public static boolean checkStack(String input) {
        Stack<Character> stack = new Stack<>();
        for (char c : input.toCharArray()) {
            stack.push(c);
        }

        for (char c : input.toCharArray()) {
            if (c != stack.pop()) {
                return false;
            }
        }
        return true;
    }

    // UC6 - Queue + Stack
    public static boolean checkQueueStack(String input) {
        Queue<Character> queue = new LinkedList<>();
        Stack<Character> stack = new Stack<>();

        for (char c : input.toCharArray()) {
            queue.add(c);
            stack.push(c);
        }

        while (!queue.isEmpty()) {
            if (queue.remove() != stack.pop()) {
                return false;
            }
        }
        return true;
    }

    // UC7 - Deque
    public static boolean checkDeque(String input) {
        Deque<Character> deque = new LinkedList<>();

        for (char c : input.toCharArray()) {
            deque.addLast(c);
        }

        while (deque.size() > 1) {
            if (deque.removeFirst() != deque.removeLast()) {
                return false;
            }
        }
        return true;
    }
}
