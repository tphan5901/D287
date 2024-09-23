package com.example.demo.bootstrap;
import com.example.demo.DataObjects.InhousePart;
import com.example.demo.DataObjects.OutsourcedPart;
import com.example.demo.DataObjects.Product;
import com.example.demo.repositories.OutsourcedPartRepository;
import com.example.demo.repositories.PartRepository;
import com.example.demo.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class BootStrapData implements CommandLineRunner {
    private final PartRepository partRepository;
    private final ProductRepository productRepository;
    private final OutsourcedPartRepository outsourcedPartRepository;

    public BootStrapData(PartRepository partRepository, ProductRepository productRepository, OutsourcedPartRepository outsourcedPartRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
        this.outsourcedPartRepository=outsourcedPartRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<OutsourcedPart> outsourcedParts=(List<OutsourcedPart>) outsourcedPartRepository.findAll();
        for(OutsourcedPart part:outsourcedParts){
            System.out.println(part.getName()+" "+part.getCompanyName());
        }

        if (partRepository.count() == 0) {
            InhousePart ram = new InhousePart();
            ram.setName("RAM100");
            ram.setPrice(9.99);
            ram.setInv(10);
            ram.setMinimum(2);
            ram.setMaximum(100);
            partRepository.save(ram);
        }

        if (outsourcedPartRepository.count() == 0) {
            OutsourcedPart flashDrive = new OutsourcedPart();
            flashDrive.setName("a7");
            flashDrive.setPrice(9.99);
            flashDrive.setInv(10);
            flashDrive.setMinimum(2);
            flashDrive.setMaximum(100);
            flashDrive.setCompanyName("tek");

            outsourcedPartRepository.save(flashDrive);
        }

        if (productRepository.count() == 0 ) {
            Product r710 = new Product("R710", 99.99, 15);
            Product a320 = new Product("R720", 199.99, 15);
            Product a7 = new Product("R730", 299.99, 15);

            productRepository.save(r710);
            productRepository.save(a320);
            productRepository.save(a7);
        }

    }
}
