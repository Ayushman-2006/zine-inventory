package com.zine.inventory.Model;



import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="inventory_items")
@Data
@NoArgsConstructor


public class InventoryItems {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id", nullable = false)
    private long id;

    @Column(name = "item_name")
    private String name;


    @Column(name = "item_count")
    private int count;


    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_name")
    private Categories category;






}
