package gamecommons.balanceSettings;


class DataItemRepeater {
	public String name;
	public String produce;
	public int productionTime;
	public int xpValue;
	public int quantity;

    public DataItemRepeater() {}
    public DataItemRepeater(String name, String produce, int productionTime, int xpValue, int quantity) {
        this.name = name;
        this.produce = produce;
        this.productionTime = productionTime;
        this.xpValue = xpValue;
        this.quantity = quantity;
    }
}


public class DataItemProducer extends DataItemRepeater {
    public int requires;

    public DataItemProducer() {}
    public DataItemProducer(String name, String produce, int productionTime, int xpValue, int quantity, int requires) {
        super(name, produce, productionTime, xpValue, quantity);
        this.requires = requires;
    }
}
