package uk.ac.tees.w9544151.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.tees.w9544151.Models.Foodmodel;
import uk.ac.tees.w9544151.Models.OrderModel;
import uk.ac.tees.w9544151.databinding.FoodCardBinding;
import uk.ac.tees.w9544151.databinding.OrderCardBinding;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyviewHolder> {
    public List<OrderModel> ordersList;
    OrderCardBinding binding;
    private AdapterCallback mAdapterCallback;

    public OrdersAdapter(AdapterCallback callback) {
        this.mAdapterCallback = callback;
    }
    @NonNull
    @Override
    public OrdersAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = OrderCardBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new OrdersAdapter.MyviewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.MyviewHolder holder, int position) {
        OrderModel dm = ordersList.get(position);
        holder.foodname.setText(dm.getItemName());
        holder.foodprice.setText(dm.getItemPrice());
        //holder.foodImage.setImageResource(dm.getItemImage());
        holder.foodtotal.setText(dm.getTotalAmount());
        holder.foodqty.setText(dm.getItemQty());
        holder.username.setText(dm.getUsername());
        holder.mobile.setText(dm.getMobile());
        holder.seatinfo.setText(dm.getSeatNumber());

      //  holder.foodImage.setImageResource(dm.getItemImage());
        holder.dboyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapterCallback.onMethodCallback();
            }
        });


    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView foodname, foodprice,foodtotal,foodqty,username,seatinfo,mobile;
        ImageView foodImage,dboyImage;

        public MyviewHolder(@NonNull OrderCardBinding binding) {
            super(binding.getRoot());
            foodname = binding.tvFoodname;
            foodprice = binding.tvFoodPrice;
            foodtotal = binding.tvTotalPrice;
            foodqty = binding.tvQuantity;
            username = binding.tvUsername;
            mobile=binding.tvUserPhone;
            seatinfo=binding.tvSeatInfo;
            foodImage=binding.image;
            dboyImage=binding.dboyimage;
        }
    }
}
