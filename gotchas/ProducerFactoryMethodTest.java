package gotchas;

import junit.framework.TestCase;
import static org.mockito.Mockito.*;

import gotchas.Producer;
import gotchas.Product;


class MockedProducer extends Producer {
    protected Product create() {
        Product mocked = mock(Product.class);
        when(mocked.is()).thenReturn("Iron");
        return mocked;
    }
}


public class ProducerFactoryMethodTest extends TestCase {
    public ProducerFactoryMethodTest() {
        super();
    }

    protected Producer topic() {
        return new MockedProducer();
    }

    public final void test_should_have_product() {
        assertNotNull(topic().collect());
    }
    public final void test_should_collected_iron() {
        assertEquals(topic().collect().is(), "Iron");
    }
}
