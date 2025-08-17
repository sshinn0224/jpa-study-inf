package com.jade.infjpastudy.domain.item;

import com.jade.infjpastudy.domain.Category;
import com.jade.infjpastudy.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stackQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    public void addStock(int quantity) {
        this.stackQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int resetStock = this.stackQuantity - quantity;
        if(resetStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stackQuantity = resetStock;
    }

}
