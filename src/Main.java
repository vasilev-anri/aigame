void main() {

//    List<Integer> list = new LinkedList<>(List.of(2, 1, 5, 4));
//    List<Integer> list = new LinkedList<>(List.of(8, 2, 3, 1, 9, 4));
    List<Integer> list = new LinkedList<>(List.of(9, 9, 2, 2, 1, 9, 5, 7, 8, 4, 4, 6, 9, 4, 7, 5, 1, 8, 8));
    GameState initialState = new GameState(list, true);

    Game game = new Game(initialState);
    game.play();

}
