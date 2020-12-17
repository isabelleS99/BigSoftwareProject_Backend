package de.hsrm.mi.swtpro.pflamoehus.product;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.swtpro.pflamoehus.product.picture.Picture;
import de.hsrm.mi.swtpro.pflamoehus.tags.Tag;
import de.hsrm.mi.swtpro.pflamoehus.validation.product_db.*;

/*
 * Product-Entitiy for its database.
 * One Object = one group of products.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian
 * @version 6
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

    @NotNull
    @Size(max = 90)
    @Column(unique = true)
    private String name;

    @NotNull
    @ValidProductType
    @Column(name = "producttype")
    private String productType;

    @ValidRoomType
    @Column(name = "room")
    private String roomType;

    @NotNull
    @Positive
    @Digits(integer = 5, fraction = 2)
    private double price = 0.0;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Picture> allPictures = new HashSet<>();

    @PositiveOrZero
    @Digits(integer = 3, fraction = 2)
    private double height = 0.0;

    @PositiveOrZero
    @Digits(integer = 3, fraction = 2)
    private double width = 0.0;

    @PositiveOrZero
    @Digits(integer = 3, fraction = 2)
    private double depth = 0.0;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonIgnore
    @JoinTable(name = "Product_Tags", joinColumns = @JoinColumn(name = "articlenr"), inverseJoinColumns = @JoinColumn(name = "tagID"))
    private Set<Tag> allTags = new HashSet<>();

    @Column(name = "available")
    @PositiveOrZero
    private int nrAvailableItems = 0;

    @NotNull
    @Size(min = 10, max = 180)
    private String description;

    @NotNull
    @Size(min = 10, max = 180)
    private String information;

    /**
     * Get information.
     * 
     * @return information
     */
    public String getInformation() {
        return this.information;
    }

    /**
     * Set information.
     * 
     * @param information -> information that has to be set
     */
    public void setInformation(String information) {
        this.information = information;
    }

    /**
     * Get description.
     * 
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set description.
     * 
     * @param description -> description that has to be set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get name.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name.
     * 
     * @param name -> name that has to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get producttype.
     * 
     * @return producttype
     */
    public String getProductType() {
        return productType;
    }

    /**
     * Set producttype.
     * 
     * @param productType -> producttype that has to be set
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     * Get roomtype.
     * 
     * @return roomtype
     */
    public String getRoomType() {
        return roomType;
    }

    /**
     * Set roomtype.
     * 
     * @param roomType -> roomtype that has to be set
     */
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    /**
     * Get price.
     * 
     * @return price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Set price.
     * 
     * @param price -> price that has to be set
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Get all pictures.
     * 
     * @return pictures
     */
    public Set<Picture> getAllPictures() {
        return allPictures;
    }

    /**
     * Add a picture to the list allPictures.
     * 
     * @param picture -> picture that has to be added
     */
    public void addPicture(Picture picture) {
        if(!allPictures.contains(picture)){
            allPictures.add(picture);
        }
        
    }

    /**
     * Remove a picture from the list of all pictures.
     * 
     * @param picture picture that should be deleted
     */
    public void removePicture(Picture picture){
        if (allPictures != null){
            allPictures.remove(picture);
        }
    }

    /**
     * Set allPicutres.
     * 
     * @param allPictures -> pictures that have to be set
     */
    public void setAllPictures(Set<Picture> allPictures) {
        this.allPictures = allPictures;
    }

    /**
     * Get articlenr.
     * 
     * @return articlenr
     */
    public long getArticlenr() {
        return articlenr;
    }

    /**
     * Get height.
     * 
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Set height.
     * 
     * @param height -> height that has to be set
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Get width.
     * 
     * @return width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Set width.
     * 
     * @param width -> width that has to be set
     */
    public void setWidth(double width) {
        this.width = width;

    }

    /**
     * Get depth.
     * 
     * @return depth
     */
    public double getDepth() {
        return depth;
    }

    /**
     * Set depth.
     * 
     * @param depth -> depth that has to be set
     */
    public void setDepth(double depth) {
        this.depth = depth;
    }

    /**
     * Get all tags.
     * 
     * @return all tags
     */
    public Set<Tag> getAllTags() {
        return allTags;
    }

    /**
     * Set all tags.
     * 
     * @param allTags -> tags that have to be set
     */
    public void setAllTags(Set<Tag> allTags) {
        this.allTags = allTags;
    }

    /**
     * Add tags to allTags.
     * 
     * @param tag -> tag that has to be added
     */
    public void addTag(Tag tag) {
        if (!allTags.contains(tag)) {
            allTags.add(tag);
        }
    }

    /**
     * Remove tag from allTags.
     * 
     * @param tag tag that has to be removed
     */
    public void removeTag(Tag tag){
        if (!allTags.contains(tag)) {
            allTags.remove(tag);
        }
    }

    /**
     * Get number of available items.
     * 
     * @return available items
     */
    public int getNrAvailableItems() {
        return nrAvailableItems;
    }

    /**
     * Set number of available items.
     * 
     * @param nrAvailableItems -> number of available items that has to be set
     */
    public void setNrAvailableItems(int nrAvailableItems) {
        this.nrAvailableItems = nrAvailableItems;
    }

    

    /**
     * To generate a product as a string.
     * 
     * @return string
     */
    @Override
    public String toString() {
        return "Product [allTags=" + allTags + ", articlenr=" + articlenr + ", depth=" + depth + ", height=" + height
                + ", name=" + name + ", nrAvailableItems=" + nrAvailableItems + ", pictures =" + allPictures.toString()
                + ", price=" + price + ", productType=" + productType + ", roomType=" + roomType + ", version="
                + version + ", width=" + width + ", description=" + description + ", information=" + information + "]";
    }

    /**
     * Get version.
     * 
     * @return version
     */
    public long getVersion() {
        return version;
    }

}
