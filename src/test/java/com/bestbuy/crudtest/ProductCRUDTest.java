package com.bestbuy.crudtest;

import com.bestbuy.productinfo.ProductSteps;
import com.bestbuy.utils.TestUtils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class ProductCRUDTest extends ProductSteps {




        static String name = "Kodak + AA Batteries" + TestUtils.getRandomValue();
        static String type = "Hard good";
        static String upc = "123456789";
        static double price = 3.99 ;
        static String description = "Losg Lasting" + TestUtils.getRandomValue();
        static String model = "K5698";
        static int productId;
        @Steps
        ProductSteps productSteps;
        @Title("this will create a new product")
        @Test
        public void test001(){
            productSteps.createProduct(name,type,price,upc,description,model).statusCode(201);
        }
        @Title("verify if the product was added successfully")
        @Test
        public void test002(){
            HashMap<String, Object> productMap = productSteps.getProductInfoByName(name);
            Assert.assertThat(productMap, hasValue(name));
            productId = (int) productMap.get("id");
        }
        @Title("Update the product information and verify the updated information")
        @Test
        public void test003(){
            name = name + "_updated";
            productSteps.updateProduct(productId,name,type, upc, price, description, model);

            HashMap<String,Object> productMap = productSteps.getProductInfoByName(name);
            Assert.assertThat(productMap, hasValue(name));
        }

        @Title("Delete the product and verify if the product is deleted")
        @Test
        public void test004(){
            productSteps.deleteProduct(productId).statusCode(204);
            productSteps.getProductById(productId).statusCode(404);
        }
}
