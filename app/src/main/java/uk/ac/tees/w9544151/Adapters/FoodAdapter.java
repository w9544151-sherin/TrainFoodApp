package uk.ac.tees.w9544151.Adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.util.List;

import uk.ac.tees.w9544151.Models.Foodmodel;
import uk.ac.tees.w9544151.R;
import uk.ac.tees.w9544151.databinding.FoodCardBinding;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyviewHolder> {
    public List<Foodmodel> fooodList;
    FoodCardBinding binding;
    Context ctx;
    String type;
    private AdapterCallback mAdapterCallback;
    private ActionCallback action;
    public int i = 0;

    public FoodAdapter(ActionCallback callback, Context context, String type) {
        this.action = callback;
        this.ctx = context;
        this.type = type;
    }

    @NonNull
    @Override
    public FoodAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = FoodCardBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new FoodAdapter.MyviewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.MyviewHolder holder, int position) {
        Foodmodel dm = fooodList.get(holder.getAdapterPosition());
        int q = 0;
        try {
            if (type.equals("admin") || type.equals("dboy")) {
                holder.clQuantity.setVisibility(View.GONE);
                holder.btnbuy.setVisibility(View.GONE);
                binding.btnPurchase.setVisibility(View.GONE);
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
        if(type.equals("admin")){
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertbox=new AlertDialog.Builder(view.getRootView().getContext());
                    alertbox.setMessage("Do you  wants to Delete this Food from your menu?");
                    alertbox.setTitle("Delete!!");

                    alertbox.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteTrainFood(dm.getFoodId(),view);

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

        holder.btnbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action.onBuyCallback(holder.valueCount.getText().toString(),dm.getFoodName(),dm.getFoodImage(),dm.getFoodPrice(),"cart");
            }
        });

        holder.btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action.onBuyCallback(holder.valueCount.getText().toString(),dm.getFoodName(),dm.getFoodImage(),dm.getFoodPrice(),"purchase");

            }
        });

        holder.ivplus.setOnClickListener(new View.OnClickListener() {
            int x = 0;

            @Override
            public void onClick(View v) {
                x = Integer.parseInt(holder.valueCount.getText().toString()) + 1;
                holder.valueCount.setText(x + "");
                Log.d("pos", "check + position: " + holder.getAdapterPosition());

                Log.d("##", String.valueOf(x));

            }
        });
        holder.ivminus.setOnClickListener(new View.OnClickListener() {
            int x = 0;

            @Override
            public void onClick(View v) {
                if (Integer.parseInt(holder.valueCount.getText().toString()) > 1) {
                    x = Integer.parseInt(holder.valueCount.getText().toString()) - 1;
                    holder.valueCount.setText(x + "");
                } else {
                    holder.valueCount.setText(1 + "");
                }

                Log.d("pos", "total(i)" + String.valueOf(i));
                Log.d("pos", "initial=" + String.valueOf(x));
                Log.d("pos", "check - position: " + holder.getAdapterPosition());
                Log.d("##Total :-", "Total=" + String.valueOf(i));

            }
        });


    }
    private void deleteTrainFood(String doc_name, View view) {
        //Log.d("@", "showData: Called")

        final ProgressDialog progressDoalog = new ProgressDialog(view.getRootView().getContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Food_Menu").document(doc_name).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Navigation.findNavController(view).navigate(R.id.action_foodListFragment_self);
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
    @Override
    public int getItemCount() {
        return fooodList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView tvfoodname, tvprice, btnbuy, valueCount,btnPurchase;
        ImageView ivphoto, ivdelete, ivminus, ivplus;
        ConstraintLayout clQuantity;

        public MyviewHolder(@NonNull FoodCardBinding binding) {
            super(binding.getRoot());
            ivminus = binding.ivMinus;
            ivplus = binding.ivPlus;
            valueCount = binding.valueCount;
            tvfoodname = binding.tvFoodName;
            tvprice = binding.tvFoodPrice;
            ivdelete = binding.ivRound;
            btnbuy = binding.btnBuy;
            ivphoto = binding.ivImage;
            clQuantity = binding.clQuantity;
            btnPurchase = binding.btnPurchase;

        }
    }
}
