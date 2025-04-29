
//This is a guessing using Lambda Expressions
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Predicate;

public class GuessTheNumber {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String CYAN = "\u001B[36m";
    public static final String YELLOW = "\u001B[33m";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        boolean playAgain = true;
        int level = 1;

        System.out.println(CYAN + "🎮 Welcome to Multi-Level Guess the Number with Timer and Score Saving!" + RESET);
        System.out.print("👤 Enter your name: ");
        String playerName = scanner.nextLine();

        while (playAgain) {
            int range = level * 50;
            int secretNumber = random.nextInt(range) + 1;
            int lives = 5;

            System.out.println("\n🔢 " + YELLOW + "Level " + level + RESET + " - Guess a number between 1 and " + range);
            System.out.println("⏳ " + RED + "You have 10 seconds per guess!" + RESET);
            System.out.println("❤️ Lives: " + lives);

            Predicate<Integer> isTooLow = n -> n < secretNumber;
            Predicate<Integer> isTooHigh = n -> n > secretNumber;

            while (lives > 0) {
                System.out.print("👉 Enter your guess: ");
                long startTime = System.currentTimeMillis();

                int guess;
                try {
                    guess = scanner.nextInt();
                } catch (Exception e) {
                    System.out.println(RED + "⚠️ Invalid input! Please enter a number." + RESET);
                    scanner.next(); // clear buffer
                    continue;
                }

                long timeTaken = (System.currentTimeMillis() - startTime) / 1000;
                if (timeTaken > 10) {
                    lives--;
                    System.out.println(RED + "⏰ Too slow! You took " + timeTaken + " seconds. Lives left: " + lives + RESET);
                    continue;
                }

                if (isTooLow.test(guess)) {
                    lives--;
                    System.out.println("📉 " + RED + "Too low! Lives left: " + lives + RESET);
                } else if (isTooHigh.test(guess)) {
                    lives--;
                    System.out.println("📈 " + RED + "Too high! Lives left: " + lives + RESET);
                } else {
                    System.out.println(GREEN + "🎉 Correct! You guessed the number!" + RESET);
                    level++;
                    break;
                }

                if (lives == 0) {
                    System.out.println(RED + "💀 Game Over! The number was: " + secretNumber + RESET);

                    // Save to file
                    saveScore(playerName, level);

                    System.out.print("🔁 Do you want to play again? (yes/no): ");
                    scanner.nextLine(); // consume newline
                    String answer = scanner.nextLine();

                    if (answer.equalsIgnoreCase("yes")) {
                        level = 1;
                    } else {
                        playAgain = false;
                        System.out.println(CYAN + "👋 Thanks for playing, " + playerName + "!" + RESET);
                    }
                }
            }
        }
        scanner.close();
    }

    public static void saveScore(String name, int level) {
        try (FileWriter writer = new FileWriter("scores.txt", true)) {
            writer.write("Player: " + name + ", Level Reached: " + level + ", Time: " + LocalTime.now() + "\n");
            System.out.println(GREEN + "✅ Score saved!" + RESET);
        } catch (IOException e) {
            System.out.println(RED + "❌ Failed to save score." + RESET);
        }
    }
}








/*import java.util.Random;
import java.util.Scanner;
import java.util.function.Predicate;

public class GuessTheNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        boolean playAgain = true;
        int level = 1;

        System.out.println("🎮 Welcome to Multi-Level Guess the Number!");

        while (playAgain) {
            int range = level * 50; // e.g., level 1 = 50, level 2 = 100...
            int secretNumber = random.nextInt(range) + 1;
            int lives = 5;

            System.out.println("\n🔢 Level " + level + " - Guess a number between 1 and " + range);
            System.out.println("❤️ You have " + lives + " lives!");

            Predicate<Integer> isTooLow = n -> n < secretNumber;
            Predicate<Integer> isTooHigh = n -> n > secretNumber;

            while (lives > 0) {
                System.out.print("👉 Enter your guess: ");
                int guess = scanner.nextInt();

                if (isTooLow.test(guess)) {
                    lives--;
                    System.out.println("📉 Too low! Lives left: " + lives);
                } else if (isTooHigh.test(guess)) {
                    lives--;
                    System.out.println("📈 Too high! Lives left: " + lives);
                } else {
                    System.out.println("🎉 Correct! You guessed the number!");
                    level++; // advance to next level
                    break;
                }

                if (lives == 0) {
                    System.out.println("💀 Game Over! The number was: " + secretNumber);
                    System.out.print("🔁 Do you want to play again? (yes/no): ");
                    String answer = scanner.next();

                    if (answer.equalsIgnoreCase("yes")) {
                        level = 1; // reset level
                    } else {
                        playAgain = false;
                        System.out.println("👋 Thanks for playing!");
                    }
                }
            }
        }

        scanner.close();
    }
}*/






/*import java.util.Scanner;
import java.util.Random;
import java.util.function.Predicate;

public class GuessTheNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int secretNumber = random.nextInt(100) + 1; // Random number between 1 and 100
        int guess;

        // Lambda expressions to check guess
        Predicate<Integer> isTooLow = n -> n < secretNumber;
        Predicate<Integer> isTooHigh = n -> n > secretNumber;

        System.out.println("🎮 Welcome to Guess the Number!");
        System.out.println("I'm thinking of a number between 1 and 100...");

        while (true) {
            System.out.print("Enter your guess: ");
            guess = scanner.nextInt(); // ✅ Use this line only

            if (isTooLow.test(guess)) {
                System.out.println("📉 Too low! Try again.");
            } else if (isTooHigh.test(guess)) {
                System.out.println("📈 Too high! Try again.");
            } else {
                System.out.println("🎉 Correct! You guessed the number!");
                break;
            }
        }

        scanner.close();
    }
}*/