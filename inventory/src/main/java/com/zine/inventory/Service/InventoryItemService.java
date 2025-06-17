package com.zine.inventory.Service;


import com.zine.inventory.DAO.CategoriesDAO;
import com.zine.inventory.DAO.InventoryItemDAO;
import com.zine.inventory.Model.Categories;
import com.zine.inventory.Model.InventoryItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryItemService {
    @Autowired
    private InventoryItemDAO inventoryRepository;

    @Autowired
    private CategoriesDAO categoryDAO;

    public List<InventoryItems> getAllItems() {
        return inventoryRepository.findAll();
    }

    public Optional<InventoryItems> getItemById(Long id) {
        return inventoryRepository.findById(id);
    }

    public List<InventoryItems> getItemByCategory(String category){
        return inventoryRepository.findByCategoryName(category);
    }

    public InventoryItems addItem(InventoryItems inventory) {

        String categoryName = inventory.getCategory().getCategory();

        Categories category = categoryDAO.findById(categoryName)
                .orElseGet(() -> {
                    Categories newCategory = new Categories();
                    newCategory.setCategory(categoryName);
                    newCategory.setDescription(inventory.getCategory().getDescription());
                    return categoryDAO.save(newCategory);
                });

        inventory.setCategory(category);

        return inventoryRepository.save(inventory);
    }

    public InventoryItems updateItem(Long id,InventoryItems updatedInventory) {


        String categoryName = updatedInventory.getCategory().getCategory();


        Categories managedCategory = categoryDAO.findById(categoryName)
                .map(existingCategory -> {
                    //for updating the description
                    if (!existingCategory.getDescription().equals(updatedInventory.getCategory().getDescription())) {
                        existingCategory.setDescription(updatedInventory.getCategory().getDescription());
                        categoryDAO.save(existingCategory);
                    }
                    return existingCategory;
                })
                .orElseGet(() -> {
                    // Category doesn't exist, create a new one
                    return categoryDAO.save(updatedInventory.getCategory());
                });
        return inventoryRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedInventory.getName());
                    existing.setCount(updatedInventory.getCount());


                    // Set the managed category
                    existing.setCategory(managedCategory);

                    return inventoryRepository.save(existing);
                })
                .orElseGet(() -> {
                    updatedInventory.setId(id);

                    // Use the managed category to avoid inserting duplicate
                    updatedInventory.setCategory(managedCategory);


                    return inventoryRepository.save(updatedInventory);
    });}

    public void deleteItem(Long id) {
        inventoryRepository.deleteById(id);
    }
}
