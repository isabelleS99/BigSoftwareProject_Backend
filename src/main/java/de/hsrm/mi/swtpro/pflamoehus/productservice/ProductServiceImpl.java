package de.hsrm.mi.swtpro.pflamoehus.productservice;
import java.util.List;
import java.util.Optional;
import javax.persistence.OptimisticLockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.swtpro.pflamoehus.exceptions.ProductServiceException;
import de.hsrm.mi.swtpro.pflamoehus.product.Product;
import de.hsrm.mi.swtpro.pflamoehus.product.ProductRepository;
import de.hsrm.mi.swtpro.pflamoehus.product.ProductType;

/*
 * ProductServiceImpl for implementing the interface 'ProductService'.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian
 * @version 1
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepo;
    Logger productServiceLogger = LoggerFactory.getLogger(ProductServiceImpl.class);

    /**
     * 
     * @return list of all products saved in the database
     */
    @Override
    public List<Product> allProducts() {

        return productRepo.findAll();
    }

    /**
     * Finds the product by its given articlenr.
     * 
     * @param articleNr wanted articlenr
     * @return optional of type product
     */
    @Override
    public Product searchProductwithArticleNr(long articleNr) {
        Optional<Product> product = productRepo.findById(articleNr);
        if (product.isEmpty()) {
            throw new ProductServiceException("Product is not in the database.");
        }
        return product.get();
    }

    /**
     * To edit and save a given (new) product.
     * 
     * @param editedProduct given product that has to be saved
     * @return product
     */
    @Override
    public Product editProduct(Product editedProduct) {
        try {
            editedProduct = productRepo.save(editedProduct);
        } catch (OptimisticLockException oLE) {
            productServiceLogger.error("Products can only be edited by one person at a time.");
            throw new ProductServiceException();
        }
        return editedProduct;
    }

    /**
     * Deleting a product by its given id.
     * 
     * @param id product-id that has to be deleted
     */
    @Override
    public void deleteProduct(long id) throws ProductServiceException {
        Optional<Product> opt = productRepo.findById(id);
        if (!opt.isPresent()) {
            productServiceLogger.info("Product was not deleted, articleNr not found");
            throw new ProductServiceException("Product could not be deleted. Product wasn't found in the database.");
        } else {
            productRepo.delete(opt.get());
        }

    }

    /**
     * Find all products with a certain type of product.
     * 
     * @param type wanted producttype
     * @return list of products
     */
    @Override
    public List<Product> findAllProductsWithProductType(ProductType type) {
        return productRepo.findByProductType(type.toString());
    }

}
