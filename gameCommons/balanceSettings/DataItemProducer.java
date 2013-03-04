package gameCommons.balanceSettings;


public class DataItemRepeater {
	public String produce;
	public int productionTime;
	public int xpValue;
	public int quantity;

    public DataItemRepeater(String produce, int productionTime, int xpValue, int quantity) {
        this.produce = produce;
        this.productionTime = productionTime;
        this.xpValue = xpValue;
        this.quantity = quantity;
}


public class DataItemProducer extends DataItemRepeater {
    public int requires;

    public DataItemProducer(String produce, int productionTime, int xpValue, int quantity, int requires) {
        super(produce, productionTime, xpValue, quantity);
        this.requires = requires;
    }
}
