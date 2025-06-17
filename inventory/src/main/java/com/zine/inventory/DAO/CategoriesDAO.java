package com.zine.inventory.DAO;


import com.zine.inventory.Model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesDAO extends JpaRepository<Categories,String> {

}
