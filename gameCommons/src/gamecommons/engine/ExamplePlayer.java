package gamecommons.engine;


class ExamplePlayer {
    class GameItem { }
    class Level { }
    class Valuable {}

    class Player {
        void earned(GameItem item) { }
        void earned(Valuable v) { }

        void completed(Level level) { }

        Player give(GameItem g) { return this; }
        Player take(Valuable v) { return this; }
    }

    Player player = new Player();
    GameItem premium = new GameItem();
    GameItem building = new GameItem();
    Level level = new Level();
    Valuable gold = new Valuable();


    public void example() {
        player.earned(premium);
        player.completed(level);
        player.give(building).take(gold);
        player.take(gold).give(building);
    }
}
