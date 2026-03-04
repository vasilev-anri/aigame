void main() {

    List<Integer> list = new LinkedList<>(List.of(2, 1, 5, 4));

//    Scanner scanner = new Scanner(System.in);
//    int pairFirstIndex = scanner.nextInt();

//    func(pairFirstIndex, list);

    int[] total = {0};
    int[] bank = {0};
    boolean[] isPlayer = {true};

//    System.out.println(list);
    System.out.println(list + "; total: " + total[0] + "; bank: " + bank[0] + "; turn: " + (isPlayer[0] ? "player" : "comp"));

    play(list, total, bank, isPlayer);

    System.out.println(list);

}

public static void play(List<Integer> list, int[] total, int[] bank, boolean[] isPlayer) {



    Scanner scanner = new Scanner(System.in);

    boolean startedGame = isPlayer[0];

    isPlayer[0] = !isPlayer[0];


    while (list.size() > 1) {
        int pairFirstIndex = scanner.nextInt();
        func(pairFirstIndex, list, total, bank);
        System.out.println(list + "; total: " + total[0] + "; bank: " + bank[0] + "; turn: " + (isPlayer[0] ? "player" : "comp"));
        isPlayer[0] = !isPlayer[0];
    }

    if (total[0] % 2 == 0 && bank[0] % 2 == 0) System.out.println("winner: " + ((startedGame) ? "player" : "comp"));
    else if (total[0] % 2 != 0 && bank[0] % 2 != 0) System.out.println("winner: " + ((startedGame) ? "comp" : "player"));
    else System.out.println("draw");

}

public static void func(int pairFirstIndex, List<Integer> list, int[] total, int[] bank) {




    int a = list.get(pairFirstIndex);
    int b = list.get(pairFirstIndex + 1);

    int sum = a + b;

    switch (Integer.compare(sum, 7)) {
        case 1:
            replacePair(list, pairFirstIndex, 1);
            total[0] += 1;
            break;
        case -1:
            replacePair(list, pairFirstIndex, 3);
            total[0] -= 1;
            break;
        case 0:
            bank[0] += 1;
            replacePair(list, pairFirstIndex, 2);
            break;
    }


}

public static void replacePair(List<Integer> list, int idx, int val) {
    ListIterator<Integer> it = list.listIterator();

    for (int i = 0; i < idx; i++) {
        it.next();
    }

    if (!it.hasNext()) return;

    it.next();
    it.set(val);

    if (!it.hasNext()) return;
    it.next();
    it.remove();


}
