package bootpay.example.capston_design;

import android.util.Log;

public class Dictionary {

    //상품명
    String product_name;

    //상품의 가격
    String product_price;

    //처음에 리사이클러뷰를 생각없이 만들어서 원인도 모른체 방치된 변수다. korean 과 연관된 녀석은 쓰지마..
    //로그를 찍어서 확인해본 결과 이 변수값은 리사이블러뷰에서 사요될 때 그저 null 처리됐다. 즉 없어도 된다는 것.
    String Korean;

    //사용자가 구매하려고 하는 물품의 개수다.
    int item_count;

    //상품의 이미지 url 을 표현한다.
    String image;

    //사용자가 상품을 개별선택하는 CheckBox 를 표현한다.
    Boolean check_box;

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public String getKorean() {
        //Log.v("Dictionary","getKorean : "+Korean);
        return Korean;
    }

    public Boolean getCheck_box() {return check_box;}

    public void setCheck_box(boolean check_box)
    {
        this.check_box = check_box;
    }

    public String getImage() {
        Log.v("Dictionary","getImage : "+image);
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getitem_count() {
        return item_count;
    }

    public Dictionary(String image,String product_name, String product_price, String korean,int item_count,boolean check_box) {
        this.image=image;
        this.product_name = product_name;
        this.product_price = product_price;
        this.check_box = check_box;
        this.item_count=item_count;
    }
}

