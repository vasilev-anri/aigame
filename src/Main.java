//void main() {
//
////    List<Integer> list = new LinkedList<>(List.of(2, 1, 5, 4));
////    List<Integer> list = new LinkedList<>(List.of(8, 2, 3, 1, 9, 4));
////    List<Integer> list = new LinkedList<>(List.of(9, 9, 2, 2, 1, 9, 5, 7, 8, 4, 4, 6, 9, 4, 7, 5, 1, 8, 8));
//
//    Scanner scanner = new Scanner(System.in);
//    Random random = new Random();
//
//    boolean playing = true;
//
//    while (playing) {
//        System.out.print("Enter length of number string (15-25): ");
//        int length = scanner.nextInt();
//
////        while (length < 15 || length > 25) {
//        while (length < 4 || length > 25) {
//            System.out.print("Invalid! Enter length between 15 and 25: ");
//            length = scanner.nextInt();
//        }
//
//        List<Integer> list = new LinkedList<>();
//
//        for (int i = 0; i < length; i++) {
//            list.add(random.nextInt(9) + 1);
//        }
//
//        System.out.println("Choose who starts the game. 1 - Player, 2 - Computer");
//        int startChoice = scanner.nextInt();
//        while (startChoice != 1 && startChoice != 2) {
//            System.out.println("Invalid! Enter 1 or 2: ");
//            startChoice = scanner.nextInt();
//        }
//
//        boolean playerStarts = (startChoice == 1);
//
//        GameState initialState = new GameState(list, playerStarts);
//        Game game = new Game(initialState);
//        game.play();
//
//        System.out.println("Play again? (y/n): ");
//        String again = scanner.next().toLowerCase();
//        playing = again.equals("y");
//
//    }
//
//    scanner.close();
//
//}







import java.util.*;

public class Main {
    public static void main(String[] args) {

        //    List<Integer> list = new LinkedList<>(List.of(2, 1, 5, 4));
        //    List<Integer> list = new LinkedList<>(List.of(8, 2, 3, 1, 9, 4));
        //    List<Integer> list = new LinkedList<>(List.of(9, 9, 2, 2, 1, 9, 5, 7, 8, 4, 4, 6, 9, 4, 7, 5, 1, 8, 8));

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        boolean playing = true;

        while (playing) {
            System.out.print("Enter length of number string (4-25): ");
            int length = scanner.nextInt();

            while (length < 4 || length > 25) {
                System.out.print("Invalid! Enter length between 4 and 25: ");
                length = scanner.nextInt();
            }

            System.out.println("Choose input type: 1 - Random, 2 - Custom");
            int inputChoice = scanner.nextInt();

            while (inputChoice != 1 && inputChoice != 2) {
                System.out.println("Invalid! Enter 1 or 2: ");
                inputChoice = scanner.nextInt();
            }

            List<Integer> list = new LinkedList<>();

            if (inputChoice == 1) {

                for (int i = 0; i < length; i++) {
                    list.add(random.nextInt(9) + 1);
                }

            } else {

                System.out.println("Enter " + length + " numbers (1-9):");

                for (int i = 0; i < length; i++) {
                    int num = scanner.nextInt();

                    while (num < 1 || num > 9) {
                        System.out.print("Invalid! Enter number between 1 and 9: ");
                        num = scanner.nextInt();
                    }

                    list.add(num);
                }
            }

            System.out.println("Your list: " + list);

            System.out.println("Choose who starts the game. 1 - Player, 2 - Computer");
            int startChoice = scanner.nextInt();

            while (startChoice != 1 && startChoice != 2) {
                System.out.println("Invalid! Enter 1 or 2: ");
                startChoice = scanner.nextInt();
            }

            boolean playerStarts = (startChoice == 1);

            GameState initialState = new GameState(list, playerStarts);
            Game game = new Game(initialState);
            game.play();

            System.out.println("Play again? (y/n): ");
            String again = scanner.next().toLowerCase();
            playing = again.equals("y");
        }

        scanner.close();
    }
}
