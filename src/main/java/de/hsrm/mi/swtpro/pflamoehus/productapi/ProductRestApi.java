package de.hsrm.mi.swtpro.pflamoehus.productapi;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hsrm.mi.swtpro.pflamoehus.exceptions.ProductServiceException;
import de.hsrm.mi.swtpro.pflamoehus.product.Product;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.Picture;
import de.hsrm.mi.swtpro.pflamoehus.productservice.ProductService;

/*
 * ProductRestApi for communication between front- and backend.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian
 * @version 3
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductRestApi {

    @Autowired
    ProductService productService;

    Logger productRestApiLogger = LoggerFactory.getLogger(ProductRestApi.class);

    /**
     * Return a list of all products in the database.
     * 
     * @return list of products
     */
    @GetMapping("/products")
    public List<Product> allProducts() {
        return productService.allProducts();
    }

    /**
     * Return the product with the given id.
     * 
     * @param articleNr given articlenr
     * @return product
     */
    @GetMapping("/product/{articleNr}")
    public Product getProductWithID(@PathVariable long articleNr) {
        Product found = null;
        try{
            found = productService.searchProductwithArticleNr(articleNr);
        }catch(ProductServiceException pse){
            productRestApiLogger.error(pse.getMessage());
        }
        
        return found;

    }

    /**
     * Delete a product with the given id.
     * 
     * @param articleNr product that should get deleted
     */
    @DeleteMapping(value="/product/{articleNr}")
    public boolean deleteProductWithArticleNr(@PathVariable long articleNr) {
        try{
            productService.deleteProduct(articleNr);
        }catch(ProductServiceException pse){
            productRestApiLogger.error(pse.getMessage());
            return false;
        }
        
        return true;
    }

    /**
     * Create new product.
     * 
     * @param newProduct the new product that has du get saved
     * @return new product
     */
    @PostMapping(value="/product/new", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> postNewProduct(@Valid @RequestBody Product newProduct, BindingResult result) {
        
    
        productRestApiLogger.info("Neues Produkt erhalten!");
        Product product = null;
        ProductResponse response = new ProductResponse(product);
        
        
        if(result.hasErrors()){
            List<Errormessage> allErrors = new ArrayList<>();
            productRestApiLogger.info("Validationsfehler");
            
            for(FieldError error: result.getFieldErrors()){
               allErrors.add(new Errormessage(error.getField(),error.getDefaultMessage()));
            }
            response.setAllErrors(allErrors);
            productRestApiLogger.info("Errorrrs: "+allErrors);
            return ResponseEntity.ok().body(response);

        }else{
            try {
            product = productService.editProduct(newProduct);

        } catch (ProductServiceException pse) {
            productRestApiLogger.error("Failed to save the product.");
            response.addErrormessage(new Errormessage(null,"SAVING_ERROR"));
            return ResponseEntity.badRequest().body(response);
        }
            productRestApiLogger.info(product.toJSON());
            return ResponseEntity.ok().body(response);
        }

    
       

    }


    /**
     * Get all pictures of an product.
     * 
     * @param articleNr articlenr of the wanted product
     * @return all pictures
     */
    @GetMapping("/{articleNr}/pictures")
    public Set<Picture> getAllPicturesOfAProduct(@PathVariable long articleNr) {
        Set<Picture> allPictures = null;
        try {
             allPictures= productService.searchProductwithArticleNr(articleNr).getAllPictures();
        
        } catch (ProductServiceException pse) {
            productRestApiLogger.error(pse.getMessage());
        }
        return allPictures;
    }

    @PostMapping(value="/product/{articleNr}/newpicture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean postPicturedata(@PathVariable Long articleNr, @RequestPart(required = true) MultipartFile picture){
        
        Product newProduct;
        FileOutputStream fileOutStream;

        try{
            //Produkt suchen 
            newProduct = productService.searchProductwithArticleNr(articleNr);
        }catch(ProductServiceException pse){
            productRestApiLogger.error(pse.getMessage());
            return false;
        }
        
       //TODO: wie kriege ich einzelne Bilder raus wenn es mehrere sind?
        try{
            //Bild speichern
            String path = "/"+newProduct.getProductType().toLowerCase()+"s/"+newProduct.getName()+".jpg";
            fileOutStream = new FileOutputStream(path);
            fileOutStream.write(picture.getBytes());
            fileOutStream.close();
            Picture newPicture = new Picture();
            newPicture.setPath(path);

       }catch(FileNotFoundException fnoe){
          productRestApiLogger.error(fnoe.getMessage());
          return false;
       }catch(IOException ioe){
          productRestApiLogger.error(ioe.getMessage());
          return false;
       }
        return true;
    }

    @InitBinder
    protected void initBinder(ServletRequestDataBinder binder) {
    binder.registerCustomEditor(byte[].class,
            new ByteArrayMultipartFileEditor());
}

}
