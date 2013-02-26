package gameCommons.data;


public class DataItemRepeater {
	public String produce;
	public int productionTime;
	public int xpValue;
	public int quantity;
}

public class DataItemProducer extends DataItemRepeater {
    public int requires;
}
