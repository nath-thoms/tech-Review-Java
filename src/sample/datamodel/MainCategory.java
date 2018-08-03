package sample.datamodel;

import java.util.List;

public class MainCategory {

    public List<Product> productList;

    public MainCategory(List<Product> productList) {
        this.productList = productList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
