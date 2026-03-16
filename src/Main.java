void main() {

//    List<Integer> list = new LinkedList<>(List.of(2, 1, 5, 4));
//    List<Integer> list = new LinkedList<>(List.of(8, 2, 3, 1, 9, 4));
//    List<Integer> list = new LinkedList<>(List.of(9, 9, 2, 2, 1, 9, 5, 7, 8, 4, 4, 6, 9, 4, 7, 5, 1, 8, 8));

    Scanner scanner = new Scanner(System.in);
    Random random = new Random();

    System.out.print("Enter length of number string (15-25): ");
    int length = scanner.nextInt();

    while (length < 15 || length > 25) {
        System.out.print("Invalid! Enter length between 15 and 25: ");
        length = scanner.nextInt();
    }

    List<Integer> list = new LinkedList<>();

    for (int i = 0; i < length; i++) {
        list.add(random.nextInt(9) + 1);
    }

//    System.out.println("Generated numbers: " + list);




    GameState initialState = new GameState(list, true);

    Game game = new Game(initialState);
    game.play();

}
