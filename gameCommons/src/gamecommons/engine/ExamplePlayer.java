package gamecommons.engine;


class ExamplePlayer {
    class GameItem { }

    class Level { }

    class Player {
        void earned(GameItem item) { }
        void completed(Level level) { }
    }

    Player player = new Player();
    GameItem premium = new GameItem();
    Level level = new Level();

    public void example() {
        player.earned(premium);
        player.completed(level);
    }
}
