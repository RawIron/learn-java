package gamecommons.engine;


/**
 *  Items are traded.
 *  Prices are based on offers and bids.
 */
class ExampleTrader {
    class Building {}
    Building building = new Building();

    class Trader {
        int ask(int p) { return p; }
        void place(Offer o) {}
        void place(Order o) {}
        void accept(Order o) {}
    }
    Trader trader = new Trader();

    class Price {
        int of(Building b) { return 0; }
    }
    Price price = new Price();

    class Offer {
        Offer of(Building b) { return null; }
    }
    Offer offer = new Offer();

    class Order {
        Order of(Building b) { return null; }
    }
    Order order = new Order();


    void example() {
        trader.ask(price.of(building));
        trader.place(offer.of(building));
        trader.place(order.of(building));
        trader.accept(order.of(building));
    }
}
