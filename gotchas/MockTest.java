package gotchas;

import junit.framework.TestCase;
import static org.mockito.Mockito.*;


class Producer {
    public Product collect() {
        return new Product("Unknown");
    }
}

class Product {
    String name;
    public Product(String name) {
        this.name = name;
    }
    public String is() {
        return name;
    }
}


public class MockTest extends TestCase {
    public MockTest() {
        super();
    }

    protected Producer topic() {
        Producer mocked = mock(Producer.class);
        when(mocked.collect()).thenReturn(new Product("Iron"));
        return mocked;
    }

    public final void test_should_have_product() {
        assertNotNull(topic().collect());
    }
    public final void test_should_collected_iron() {
        assertEquals(topic().collect().is(), "Iron");
    }
}
