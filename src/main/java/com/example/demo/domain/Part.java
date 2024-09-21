package com.example.demo.domain;
import com.example.demo.validators.ValidDeletePart;
import com.example.demo.validators.ValidPartInventory;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@ValidDeletePart
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="part_type",discriminatorType = DiscriminatorType.INTEGER)
@Table(name="Parts")

public abstract class Part implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String name;
    double price;
    int inv;
    int minimum;
    int maximum;

    @ManyToMany
    @JoinTable(name="product_part", joinColumns = @JoinColumn(name="part_id"),
            inverseJoinColumns=@JoinColumn(name="product_id"))
    Set<Product> products= new HashSet<>();

    public Part() {
    }

    public Part(String name, double price, int inv, int minimum, int maximum) {
        this.name = name;
        this.price = price;
        this.inv = inv;
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() { return price; }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getInv() {
        return inv;
    }
    public void setInv(int inv) {
        this.inv = inv;
    }

    public Set<Product> getProducts() {
        return products;
    }
    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void setMinimum(int minimum) { this.minimum = minimum; }
    public int getMinimum() { return this.minimum; }

    public void setMaximum(int maximum) { this.maximum = maximum; }
    public int getMaximum() { return this.maximum; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Part part = (Part) o;
        return id == part.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

}
