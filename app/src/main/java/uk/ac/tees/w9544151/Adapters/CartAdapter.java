package uk.ac.tees.w9544151.Adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.util.List;

import uk.ac.tees.w9544151.Models.CartModel;
import uk.ac.tees.w9544151.Models.DBoyModel;
import uk.ac.tees.w9544151.R;
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
        try {
            Log.d("##", dm.getImage());
            Glide.with(holder.image.getContext())
                    .load(dm.getImage())
                    .into(holder.image);
        }
        catch (Exception e){

        }
        //holder.image.setImageBitmap(dm.getImage());

       /* holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapterCallback.onMethodCallback();
            }
        });*/

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox=new AlertDialog.Builder(view.getRootView().getContext());
                alertbox.setMessage("Do you  wants to Delete this Food from your Cart?");
                alertbox.setTitle("Delete!!");

                alertbox.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteTrainFood(dm.getUserid(),view);

                    }
                });
                alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertbox.show();
            }
        });
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
    private void deleteTrainFood(String doc_name, View view) {
        //Log.d("@", "showData: Called")

        final ProgressDialog progressDoalog = new ProgressDialog(view.getRootView().getContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Cart").document(doc_name).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Navigation.findNavController(view).navigate(R.id.action_cartListFragment_self);
                        Toast.makeText(view.getRootView().getContext(), "food removed successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getRootView().getContext(), "Technical error occured", Toast.LENGTH_SHORT).show();

                    }
                });

        progressDoalog.dismiss();

    }
}

