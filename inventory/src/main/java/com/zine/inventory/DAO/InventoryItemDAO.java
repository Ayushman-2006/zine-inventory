package com.zine.inventory.DAO;


import com.zine.inventory.Model.InventoryItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryItemDAO extends JpaRepository<InventoryItems,Long> {

    @Query("SELECT i FROM InventoryItems i WHERE i.category.category = :categoryName")
    List<InventoryItems> findByCategoryName(@Param("categoryName") String categoryName);
}
