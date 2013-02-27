package gotchas;

import junit.framework.TestCase;
import static org.mockito.Mockito.*;
import gotchas.Product;


public class ProductTest extends TestCase {
    public ProductTest() {
        super();
    }

    protected Product topic() {
        Product mocked = mock(Product.class);
        when(mocked.is()).thenReturn("Iron");
        return mocked;
    }

    public final void test_should_have_product() {
        assertNotNull(topic());
    }
    public final void test_should_collected_iron() {
        //assertEquals(topic().name, "Iron");
        assertEquals(topic().is(), "Iron");
    }
}
