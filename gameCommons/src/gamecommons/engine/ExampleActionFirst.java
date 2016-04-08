package gamecommons.engine;


class ExampleActionFirst {
    interface IPlayerAction {
        void on(Player p);
    }

    interface IBuildingAction {
        void on(Building b);
    }

    class RewardAction implements IPlayerAction {
        Valuable v;
        RewardAction(Valuable v) { this.v = v; }
        public void on(Player p) { p.reward(v); }
    }

    class ConsumeAction implements IPlayerAction {
        Valuable v;
        ConsumeAction(Valuable v) { this.v = v; }
        public void on(Player p) { p.consume(v); }
    }

    class Actions {
        RewardAction reward(Valuable v) {
            return new RewardAction(v);
        }

        ConsumeAction consume(Valuable v) {
            return new ConsumeAction(v);
        }

        Transaction trade(Building item) {
            return null;
        }

        void take(Valuable v) {}

        IPlayerAction run(Transaction t) {
            return this;
        }
    }

    class Valuable {}

    class Transaction {
        Transaction trade(Building b) { return this; }
        IPlayerAction For(Valuable v) { return null; }
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
