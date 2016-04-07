package gamecommons.engine;


class ExampleActionFirst {
    abstract class Action {
        abstract void run(Player p);
    }

    class RewardAction extends Action {
        Valuable v;
        RewardAction(Valuable v) { this.v = v; }
        void run(Player p) { p.reward(v); }
    }

    class ConsumeAction extends Action {
        Valuable v;
        ConsumeAction(Valuable v) { this.v = v; }
        void run(Player p) { p.consume(v); }
    }

    class Actions {
        Action action = null;

        void on(Player p) { this.action.run(p); }
        void on(Building b) { }

        Actions reward(Valuable v) {
            this.action = new RewardAction(v);
            return this;
        }

        Actions consume(Valuable v) {
            this.action = new ConsumeAction(v);
            return this;
        }

        Transaction trade(Building item) {
            return null;
        }

        void take(Valuable v) {}

        Actions run(Transaction t) {
            return this;
        }
    }

    class Valuable {}

    class Transaction {
        Transaction trade(Building b) { return this; }
        Actions For(Valuable v) { return null; }
        void doIt() {}
    }

    class Player {
        void reward(Valuable v) {}
        void consume(Valuable v) {}
        Transaction trade(Building item) {
            return null;
        }
        void take(Valuable v) {}
        void run(Transaction t) {}
    }

    Actions action = new Actions();
    Valuable xp = new Valuable();
    Valuable energy = new Valuable();
    Valuable gold = new Valuable();
    Player player = new Player();
    Transaction transaction = new Transaction();
    Building building = new Building();


    void example() {
        action.reward(xp).on(player);
        action.consume(energy).on(player);

        action.run(transaction).on(player);
        action.trade(building).For(gold).on(player);
//        action.listenOn(xpCounter).trigger(levelUp).on(player);
    }
}
