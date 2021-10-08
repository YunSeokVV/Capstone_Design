package bootpay.example.capston_design.Utils;

public class PurchaseDetailData {
    String product_name;
    String product_price;
    String product_number;
    String product_img_url;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_number() {
        return product_number;
    }

    public void setProduct_number(String product_number) {
        this.product_number = product_number;
    }

    public String getProduct_img_url() {
        return product_img_url;
    }

    public void setProduct_img_url(String product_img_url) {
        this.product_img_url = product_img_url;
    }

    public PurchaseDetailData() {
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_number = product_number;
        this.product_img_url = product_img_url;
    }
}
