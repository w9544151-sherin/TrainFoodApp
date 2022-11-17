package uk.ac.tees.w9544151.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kotlin.Unit;
import uk.ac.tees.w9544151.Models.Foodmodel;
import uk.ac.tees.w9544151.databinding.FoodCardBinding;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyviewHolder> {
    public List<Foodmodel> fooodList;
    FoodCardBinding binding;
    private AdapterCallback mAdapterCallback;

    public HomeAdapter(AdapterCallback callback) {
        this.mAdapterCallback = callback;
    }
    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = FoodCardBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new MyviewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        Foodmodel dm = fooodList.get(position);
        holder.tvfoodname.setText(dm.getFoodName());
        holder.tvprice.setText(dm.getFoodPrice());
       // holder.ivphoto.setImageResource(dm.getFoodImage());
        holder.btnbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapterCallback.onMethodCallback();
            }
        });


    }

    @Override
    public int getItemCount() {
        return fooodList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView tvfoodname, tvprice,btnbuy;
        ImageView ivphoto,ivdelete;

        public MyviewHolder(@NonNull FoodCardBinding binding) {
            super(binding.getRoot());
            tvfoodname = binding.tvFoodName;
            tvprice = binding.tvFoodPrice;
            ivdelete = binding.ivDelete;
            btnbuy = binding.btnBuy;
            ivphoto = binding.ivImage;
        }
    }
}
