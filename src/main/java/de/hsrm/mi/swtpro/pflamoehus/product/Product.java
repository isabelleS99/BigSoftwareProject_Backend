package de.hsrm.mi.swtpro.pflamoehus.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.hsrm.mi.swtpro.pflamoehus.tags.Tag;
import de.hsrm.mi.swtpro.pflamoehus.validation.ValidPicture;
import de.hsrm.mi.swtpro.pflamoehus.validation.ValidProductType;
import de.hsrm.mi.swtpro.pflamoehus.validation.ValidRoomType;

/**
 * 1 Object = 1 group of products
 */
@Entity
@Table(name = "Product")
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long articlenr;


    @Version
    @JsonIgnore
    private long version;

    @NotEmpty
    @Size(max = 90)
    private String name;

    @NotEmpty
    @ValidProductType
    @Column(name="producttype")
    private String productType;

    @ValidRoomType
    @Column(name="room")
    private String roomType;

    @NotEmpty
    @Positive @Digits(integer = 5, fraction = 2)
    private double price=0.0;

    @ValidPicture
    private String picture;

    @PositiveOrZero @Digits(integer=3, fraction=2)
    private double height=0.0;

    @PositiveOrZero @Digits(integer=3, fraction=2)
    private double width=0.0;

    @PositiveOrZero @Digits(integer=3, fraction=2)
    private double depth=0.0;

    @OneToMany(mappedBy = "product")
    private List<Tag> allTags = new ArrayList<Tag>();

    @Column(name="available")
    @PositiveOrZero
    private long nrAvailableItems = 0;

    
    /** 
     * @return String
     */
    //Getter & Setter
    public String getName() {
        return name;
    }

    
    /** 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /** 
     * @return String
     */
    public String getProductType() {
        return productType;
    }

    
    /** 
     * @param productType
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    
    /** 
     * @return String
     */
    public String getRoomType() {
        return roomType;
    }

    
    /** 
     * @param roomType
     */
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    
    /** 
     * @return Double
     */
    public Double getPrice() {
        return price;
    }

    
    /** 
     * @param price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    
    /** 
     * @return String
     */
    public String getPicture() {
        return picture;
    }

    
    /** 
     * @param picture
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }


    public long getArtikelnr() {
        return articlenr;
    }


    public double getHeight() {
        return height;
    }

    
  
    public void setHeight(double height) {
        this.height = height;
    }


    public double getWidth() {
        return width;
    }

    
   
    public void setWidth(double width) {
        this.width = width;

    }

    public double getDepth() {
        return depth;
    }

   
    public void setDepth(double depth) {
        this.depth = depth;
    }

    public List<Tag> getAllTags() {
        return allTags;
    }

    public void setAllTags(List<Tag> allTags) {
        this.allTags = allTags;
    }

    public long getNrAvailableItems() {
        return nrAvailableItems;
    }

    public void setNrAvailableItems(long nrAvailableItems) {
        this.nrAvailableItems = nrAvailableItems;
    }

   

    
    
}
