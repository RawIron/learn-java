package gotchas;

import junit.framework.TestCase;
import static org.mockito.Mockito.*;


class Producer {
    public Product collect() {
        return new Product();
    }
}

class Product {}


public class MockTest extends TestCase {
    public MockTest() {
        super();
    }

    protected Producer topic() {
        Producer mocked = mock(Producer.class);
        when(mocked.collect()).thenReturn(new Product());
        return mocked;
    }

    public final void test_should_have_product() {
        assertNotNull(topic().collect());
    }
}
