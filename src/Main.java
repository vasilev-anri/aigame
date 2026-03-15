void main() {

//    List<Integer> list = new LinkedList<>(List.of(2, 1, 5, 4));
    List<Integer> list = new LinkedList<>(List.of(8, 2, 3, 1, 9, 4));
    GameState initialState = new GameState(list, true);

    Game game = new Game(initialState);
    game.play();

}
