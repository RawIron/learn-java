package gamecommons.engine;


class ExampleActionOnPlayer {
    class Action {
        Player on(Player p) {
            return p;
        }
        Building on(Building b) {
            return b;
        }
    }

    class Valuable {}

    class Transaction {
        Transaction trade(Building b) { return this; }
        void For(Valuable v) {}
    }

    class Player {
        void reward(Valuable v) {}
        void consume(Valuable v) {}
        Transaction trade(Building item) { return null; }
        void take(Valuable v) {}
        void run(Transaction t) {}
        Player watch(Counter c) { return null; }
        Player watch(StoreHouse sh) { return null; }
        void trigger(Action a) {}
    }

    class StoreHouse {}
    class Counter {}
    class XpCounter extends Counter {}

    StoreHouse inventory = new StoreHouse();
    XpCounter xpCounter = new XpCounter();
    Action action = new Action();
    Action levelUp  = new Action();
    Action giveReward  = new Action();
    Action completeQuest  = new Action();
    Valuable xp = new Valuable();
    Valuable energy = new Valuable();
    Valuable gold = new Valuable();
    Player player = new Player();
    Transaction transaction = new Transaction();
    Building building = new Building();


    void example() {
        action.on(player).reward(xp);
        action.on(player).consume(energy);

        action.on(player).run(transaction);
        action.on(player).trade(building).For(gold);

        action.on(player).watch(xpCounter).trigger(levelUp);
        action.on(player).watch(inventory).trigger(giveReward);
        action.on(player).watch(inventory).trigger(completeQuest);
    }
}
