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


    public PurchaseAdapter(Activity context, ArrayList<PurchaseData> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder_Purchase extends RecyclerView.ViewHolder {
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

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}