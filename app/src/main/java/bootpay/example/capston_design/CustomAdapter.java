package bootpay.example.capston_design;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.capston_design.R;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    MainActivity mainActivity=new MainActivity();

    private ArrayList<Dictionary> mList;

    //토스트메세지를 사용하기위해 필요한 context 이다. MainActivity 의 context 를 갖고와서 여기서 사용한다.
    Context context;

    //상품목록리사이클러뷰에서 아이템을 삭제하는 메소드
    public void RemoveItem(int position, int price){
        Log.v("CustomAdataper","position : "+position);
        //여기서 가격은 총 금액이다. 상품 낮개의 가격이 아니다.
        Log.v("CustomAdataper","price : "+price);
        mList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();

        //todo : 더 이상 시간을 지체할 수 없어서 static 을 사용함. 여유되면 고칠것.
        mainActivity.customer_total_price=mainActivity.customer_total_price-price;
        Log.v("CustomAdapter","mainActivity.customer_total_price "+mainActivity.customer_total_price);
        MainActivity.total_price.setText(String.valueOf(mainActivity.customer_total_price));
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{


        //상품 이미지를 표현한다
        protected ImageView btnTest;

        //사용자가 선택한 상품명을 표현한다
        protected TextView product_name;

        //상품의 총 가격을 표현한다
        protected TextView product_total_price;

        //상품 이미지를 표현한다
        protected ImageView product_img;

        //상품의 개수를 빼기위한 버튼
        protected TextView item_decrease;

        //상품의 개수를 추가하기 위한 버튼
        protected TextView item_increase;

        //상품의 개수를 표현하기 위한 TextView
        protected TextView item_count;

        //상품을 낱개로 선택하기 위한 CheckBox 이다.
        protected CheckBox check_product;

        public CustomViewHolder(View view) {
            super(view);
            this.btnTest  = (ImageView)itemView.findViewById(R.id.homeBtnTest);
            this.product_name = (TextView) view.findViewById(R.id.id_listitem);
            this.product_total_price = (TextView) view.findViewById(R.id.content);
            this.product_img = (ImageView) view.findViewById(R.id.image);
            this.item_decrease = (TextView) view.findViewById(R.id.textView5);
            this.item_increase = (TextView) view.findViewById(R.id.item_plus);
            this.item_count = (TextView) view.findViewById(R.id.item_count);
            this.check_product = (CheckBox) view.findViewById(R.id.checkbox);
        }

        public void sendImageRequest(String url) {

            ImageLoadTask task = new ImageLoadTask(url,product_img);
            task.execute();
        }



    }


    public CustomAdapter(ArrayList<Dictionary> list) {
        this.mList = list;

    }



    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        final Dictionary dictionary=mList.get(position);

        viewholder.check_product.setOnCheckedChangeListener(null);
        viewholder.check_product.setChecked(dictionary.getCheck_box());

        Glide.with(viewholder.itemView.getContext())
                .load(dictionary.getKorean())
                .into(viewholder.product_img);

        viewholder.product_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewholder.product_total_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        //viewholder.korean.setImageResource(dictionary.getImage());



        viewholder.product_name.setGravity(Gravity.CENTER);
        viewholder.product_total_price.setGravity(Gravity.CENTER);
        //viewholder.korean.setGravity(Gravity.CENTER);


        Log.v("CustomAdapter","mList.get(position).getImage() :"+mList.get(position).getImage());

        //상품명을 TextView 에 입력
        viewholder.product_name.setText(mList.get(position).getProduct_name());

        //상품의 총 가격을 TextView 에 입력
        viewholder.product_total_price.setText(mList.get(position).getProduct_price());

        //상품이미지를 ImageView 에 입력
        viewholder.sendImageRequest(mList.get(position).getImage());

        //상품의 총 개수를 TextView 에 입력
        viewholder.item_count.setText(String.valueOf(mList.get(position).getitem_count()));

        Log.v("CustomAdapter","mList.get(position).getImage() :"+mList.get(position).getImage());




        // X 표시 버튼으로 사용자가 장바구니에서 없애고 싶은 상품을 제거한다.
        viewholder.btnTest.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Log.v("CustomAdapter","position의 값 :"+position);

                RemoveItem(position,Integer.parseInt(mList.get(position).getProduct_price()));
                viewholder.check_product.setChecked(false);

            }
        });


        //사용자가 상품을 개별선택할 수 있게끔 해주는 체크박스
        viewholder.check_product.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //dictionary.setCheck_box(b);
                if(b){
                    Log.v("CustomAdapter","b 에서 position의 값 :"+position);
                    Log.v("CustomAdapter","true 인 경우 "+b);

                    dictionary.setCheck_box(b);
                    //Dictionary dict2=new Dictionary(mList.get(position).getImage(),mList.get(position).getProduct_name(), mList.get(position).getProduct_price(),mList.get(position).getKorean(), mList.get(position).getitem_count(),true);
                    //mList.set(position, dict2);
                    //notifyItemChanged(position);
                }
                else{
                    Log.v("CustomAdapter","false 인 경우 "+b);

                    dictionary.setCheck_box(b);

                    //Dictionary dict2=new Dictionary(mList.get(position).getImage(),mList.get(position).getProduct_name(), mList.get(position).getProduct_price(),mList.get(position).getKorean(), mList.get(position).getitem_count(),false);
                    //mList.set(position, dict2);
                    //notifyItemChanged(position);

                }
            }
        });


        //사용자가 선택한 상품의 개수를 하나 낮추는 버튼
        viewholder.item_decrease.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if(mList.get(position).getitem_count()-1<=0){
                    Dictionary dict=new Dictionary(mList.get(position).getImage(),mList.get(position).getProduct_name(), mList.get(position).getProduct_price(),mList.get(position).getKorean(), 1,mList.get(position).getCheck_box());
                    mList.set(position, dict);
                    notifyItemChanged(position);
                    Toast.makeText(context, "최소 하나이상의 물품을 선택하셔야 합니다", Toast.LENGTH_SHORT).show();

                }
                else{

                    int price=Integer.parseInt(mList.get(position).getProduct_price());
                    Log.v("CustomAdapter","price "+price);

                    //상품 하나의 가격
                    int product_price=price/mList.get(position).getitem_count();

                    //사용자가 설정한 상품의 개수
                    int product_count=mList.get(position).getitem_count()-1;

                    String total_price=String.valueOf(product_count*product_price);

                    Dictionary dict2=new Dictionary(mList.get(position).getImage(),mList.get(position).getProduct_name(),total_price,mList.get(position).getKorean(), product_count,mList.get(position).getCheck_box());

                    mList.set(position, dict2);
                    notifyItemChanged(position);

                    //todo : 더 이상 시간을 지체할 수 없어서 static 을 사용함. 여유되면 고칠것.
                    mainActivity.customer_total_price=mainActivity.customer_total_price-product_price;
                    Log.v("CustomAdapter","mainActivity.customer_total_price "+mainActivity.customer_total_price);
                    MainActivity.total_price.setText(String.valueOf(mainActivity.customer_total_price));
                }


            }
        });


        //사용자가 선택한 상품의 개수를 하나 올리는 버튼
        viewholder.item_increase.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {

                int price=Integer.parseInt(mList.get(position).getProduct_price());
                Log.v("CustomAdapter","price "+price);

                //상품 하나의 가격
                int product_price=price/mList.get(position).getitem_count();

                //사용자가 설정한 상품의 개수
                int product_count=mList.get(position).getitem_count()+1;

                String total_price=String.valueOf(product_count*product_price);

                    Dictionary dict=new Dictionary(mList.get(position).getImage(),mList.get(position).getProduct_name(), total_price,mList.get(position).getKorean(), product_count,mList.get(position).getCheck_box());
                    mList.set(position, dict);
                    notifyItemChanged(position);

                //todo : 더 이상 시간을 지체할 수 없어서 static 을 사용함. 여유되면 고칠것.
                mainActivity.customer_total_price=mainActivity.customer_total_price+product_price;
                Log.v("CustomAdapter","mainActivity.customer_total_price "+mainActivity.customer_total_price);
                MainActivity.total_price.setText(String.valueOf(mainActivity.customer_total_price));

            }
        });

    }       //onBindViewHolder end

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    //토스트 메세지를 사용하기위해 선언한 메소드다.
    public void ToastMessage(Context context){
        this.context=context;
    }



}