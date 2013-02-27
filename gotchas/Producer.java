package gotchas;


class Producer {
    public Product collect() {
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

