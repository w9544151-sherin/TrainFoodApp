package uk.ac.tees.w9544151.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.List;

import uk.ac.tees.w9544151.Models.Foodmodel;
import uk.ac.tees.w9544151.databinding.FoodCardBinding;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyviewHolder> {
    public List<Foodmodel> fooodList;
    FoodCardBinding binding;
    Context ctx;
    String type;
    private AdapterCallback mAdapterCallback;

    int i=0;

    public HomeAdapter(AdapterCallback callback, Context context, String type) {
        this.mAdapterCallback = callback;
        this.ctx = context;
        this.type = type;
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
        try {


            if (type.equals("admin") || type.equals("dboy")) {
                holder.clQuantity.setVisibility(View.GONE);
                holder.btnbuy.setVisibility(View.GONE);
            }
            else
            {
                holder.clQuantity.setVisibility(View.VISIBLE);
                holder.btnbuy.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            //  SharedPreferences sp = ctx.getSharedPreferences("logDetails", Context.MODE_PRIVATE);
            //   Log.d("q", sp.getString("userType","error") );
            Log.d("q", e.toString());
        }
        holder.tvfoodname.setText(dm.getFoodName());
        holder.tvprice.setText(dm.getFoodPrice());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes = baos.toByteArray();
        imageBytes = Base64.decode(dm.getFoodImage(), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.ivphoto.setImageBitmap(decodedImage);

        holder.btnbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapterCallback.onMethodCallback();
            }
        });

        holder.ivplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // binding.valueCount.setText(i++);
            }
        });
        holder.ivminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //binding.valueCount.setText(i--);
            }
        });


    }

    @Override
    public int getItemCount() {
        return fooodList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView tvfoodname, tvprice, btnbuy;
        ImageView ivphoto, ivdelete, ivminus, ivplus;
        ConstraintLayout clQuantity;

        public MyviewHolder(@NonNull FoodCardBinding binding) {
            super(binding.getRoot());
            ivminus = binding.ivMinus;
            ivplus = binding.ivPlus;
            tvfoodname = binding.tvFoodName;
            tvprice = binding.tvFoodPrice;
            ivdelete = binding.ivRound;
            btnbuy = binding.btnBuy;
            ivphoto = binding.ivImage;
            clQuantity = binding.clQuantity;

        }
    }
}
