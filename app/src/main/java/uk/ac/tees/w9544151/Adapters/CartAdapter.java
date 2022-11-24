package uk.ac.tees.w9544151.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.List;

import uk.ac.tees.w9544151.Models.CartModel;
import uk.ac.tees.w9544151.Models.DBoyModel;
import uk.ac.tees.w9544151.databinding.CartCardBinding;
import uk.ac.tees.w9544151.databinding.ProfileCardBinding;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyviewHolder> {
    public List<CartModel> cartList;
    CartCardBinding binding;
    private AdapterCallback mAdapterCallback;

    public CartAdapter(AdapterCallback callback) {
        this.mAdapterCallback = callback;
    }
    @NonNull
    @Override
    public CartAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = CartCardBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new CartAdapter.MyviewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyviewHolder holder, int position) {
        CartModel dm = cartList.get(position);

        holder.itemName.setText(dm.getItemName());
        holder.price.setText(dm.getItemPrice()+"*"+dm.getItemQuantity()+"="+dm.getTotalAmount());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes = baos.toByteArray();
        imageBytes = Base64.decode(dm.getImage(), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.image.setImageBitmap(decodedImage);
        //holder.image.setImageBitmap(dm.getImage());

       /* holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapterCallback.onMethodCallback();
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView itemName,price;
        ImageView image,delete;

        public MyviewHolder(@NonNull CartCardBinding binding) {
            super(binding.getRoot());
            itemName= binding.tvFoodName;
            price =binding.tvFoodPrice;
            delete=binding.ivRound;
            image=binding.ivImage;

        }
    }
}

