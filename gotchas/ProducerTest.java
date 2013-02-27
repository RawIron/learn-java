package gotchas;

import junit.framework.TestCase;
import static org.mockito.Mockito.*;

import gotchas.Producer;
import gotchas.Product;


public class ProducerTest extends TestCase {
    public ProducerTest() {
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
