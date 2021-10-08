package bootpay.example.capston_design.Utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.capston_design.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bootpay.example.capston_design.Dictionary;

public class PurchaseDetailAdapter extends RecyclerView.Adapter<PurchaseDetailAdapter.CustomViewHolder_PurchaseDetail> {

    private ArrayList<PurchaseDetailData> mList = null;
    private Activity context = null;

    //리사이클러뷰의 각 아이템을 클릭할 수 있도록 선언한 객체다.
    OnItemClickListener listener;

    //아이템 클릭시 나타나는 이벤트 이 메소드를 사용해서 리사이클러뷰의 각 아이템에 접근할 수 있다.
    public static interface  OnItemClickListener{
        public void onItemClick(CustomViewHolder_PurchaseDetail holder, View view, int position);
    }

    public PurchaseDetailAdapter(Activity context, ArrayList<PurchaseDetailData> list) {
        this.context = context;
        this.mList = list;
    }

    public class CustomViewHolder_PurchaseDetail extends RecyclerView.ViewHolder {

        //상품 이미지를 표현한다
        protected ImageView product_img;

        //사용자가 선택한 상품명을 표현한다
        protected TextView product_name;

        //상품의 총 가격을 표현한다
        protected TextView product_price;

        //상품의 개수를 표현하기 위한 TextView
        protected TextView item_count;


        public CustomViewHolder_PurchaseDetail(View view) {
            super(view);
            this.product_name = (TextView) view.findViewById(R.id.id_listitem);
            this.product_price = (TextView) view.findViewById(R.id.content);
            this.product_img = (ImageView) view.findViewById(R.id.image);
            this.item_count = (TextView) view.findViewById(R.id.product_number);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int position=getBindingAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(CustomViewHolder_PurchaseDetail.this,view,position);
                    }
                }
            }); //추가된 사항
        }
    }


    @Override
    public CustomViewHolder_PurchaseDetail onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.purchase_detail_list, null);
        CustomViewHolder_PurchaseDetail viewHolder = new CustomViewHolder_PurchaseDetail(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder_PurchaseDetail viewholder, int position) {
        final PurchaseDetailData purchaseDetailData=mList.get(position);

        viewholder.product_name.setText(mList.get(position).getProduct_name());
        viewholder.product_price.setText(mList.get(position).getProduct_price());
        viewholder.item_count.setText(mList.get(position).getProduct_number());
        Glide.with(viewholder.itemView.getContext())
                .load(purchaseDetailData.getProduct_img_url())
                .into(viewholder.product_img);
    }

    //아이템 클릭시 나타나는 이벤트 이 메소드를 사용해서 리사이클러뷰의 각 아이템에 접근할 수 있다.
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener=listener;
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
