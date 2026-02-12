ProductControllerTest.javapackage com.fd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fd.dto.ProductDTO;
import com.fd.model.Product;
import com.fd.repository.ProductRepository;
import com.fd.service.ProductService;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest  {
	@Mock
	private ProductRepository productRepo; 
	@InjectMocks
	private ProductService productService; 
	 // ---------- getProductsFromDatabase ----------
    @Test
    void getProductsFromDatabase_success() {
        Product p1 = new Product(1, "Laptop", 50000.0);
        Product p2 = new Product(2, "Mobile", 20000.0);

        when(productRepo.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<ProductDTO> result = productService.getProductsFromDatabase();

        assertEquals(2, result.size());
        verify(productRepo, times(1)).findAll();
    }
}
