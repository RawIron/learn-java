package gotchas;


class Producer {
    Product produces = null;
    public Producer() {
        this.produces = create();
    }

    public Product collect() {
        return produces;
    }
    protected Product create() {
        return new Product("Unknown");
    }
}

class Product {
    public String name;
    public Product(String name) {
        this.name = name;
    }
    public String is() {
        return name;
    }
}

