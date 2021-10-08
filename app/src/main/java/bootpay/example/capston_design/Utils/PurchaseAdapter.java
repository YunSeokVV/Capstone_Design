package bootpay.example.capston_design.Utils;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.capston_design.R;

import java.util.ArrayList;


public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.CustomViewHolder_Purchase> {

    private ArrayList<PurchaseData> mList = null;
    private Activity context = null;

    //리사이클러뷰의 각 아이템을 클릭할 수 있도록 선언한 객체다.
    OnItemClickListener listener;

    //아이템 클릭시 나타나는 이벤트 이 메소드를 사용해서 리사이클러뷰의 각 아이템에 접근할 수 있다.
    public static interface  OnItemClickListener{
        public void onItemClick(CustomViewHolder_Purchase holder,View view, int position);
    }

    public PurchaseAdapter(Activity context, ArrayList<PurchaseData> list) {
        this.context = context;
        this.mList = list;
    }

    public class CustomViewHolder_Purchase extends RecyclerView.ViewHolder {
        //배송 상태를 표현한다.
        protected TextView deliver_status;
        //주문 날짜를 표현한다.
        protected TextView date;
        //총 금액을 표현한다.
        protected TextView total_price;


        public CustomViewHolder_Purchase(View view) {
            super(view);
            this.deliver_status = (TextView) view.findViewById(R.id.deliver_status);
            this.date = (TextView) view.findViewById(R.id.date);
            this.total_price = (TextView) view.findViewById(R.id.total_price);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int position=getBindingAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(CustomViewHolder_Purchase.this,view,position);
                    }
                }
            }); //추가된 사항
        }
    }


    @Override
    public CustomViewHolder_Purchase onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.purchase_list, null);
        CustomViewHolder_Purchase viewHolder = new CustomViewHolder_Purchase(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder_Purchase viewholder, int position) {


        viewholder.deliver_status.setText(mList.get(position).getDeliver_status());
        viewholder.date.setText(mList.get(position).getOrder_time());
        viewholder.total_price.setText(mList.get(position).getTotal_price());
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