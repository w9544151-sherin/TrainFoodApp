package uk.ac.tees.w9544151.Adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import java.util.List;

import uk.ac.tees.w9544151.Models.OrderModel;
import uk.ac.tees.w9544151.Models.StopModel;
import uk.ac.tees.w9544151.R;
import uk.ac.tees.w9544151.databinding.OrderCardBinding;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyviewHolder> {
    public List<OrderModel> ordersList;
    OrderCardBinding binding;
    private AdapterCallback mAdapterCallback;
    String type;
Context ctx;
    public OrdersAdapter(AdapterCallback callback, Context context, String type) {
        this.mAdapterCallback = callback;
        this.ctx=context;
        this.type=type;
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
        SharedPreferences sp = ctx.getSharedPreferences("logDetails", Context.MODE_PRIVATE);
        if (type.equals("admin") || type.equals("dboy")){
            holder.dboyImage.setVisibility(View.GONE);
          //  holder.btnbuy.setVisibility(View.GONE);
        }
        holder.foodname.setText(dm.getItemName());
        holder.foodprice.setText("Price\t:\t"+dm.getItemPrice());
        //holder.foodImage.setImageResource(dm.getItemImage());
        holder.foodtotal.setText("Total Amount\t:\t"+dm.getTotalAmount());
        holder.foodqty.setText("Qty\t:\t"+dm.getItemQty());
        holder.username.setText(dm.getUsername());
        holder.mobile.setText(dm.getMobile());
        holder.seatinfo.setText(dm.getAddress());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes = baos.toByteArray();
        imageBytes = Base64.decode(dm.getItemImage(), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.foodImage.setImageBitmap(decodedImage);
        holder.btnstatus.setText(dm.getStatus());
      //  holder.foodImage.setImageResource(dm.getItemImage());
        holder.dboyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               getDboyDetails(view,dm.getStop());
            }
        });
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox=new AlertDialog.Builder(view.getRootView().getContext());
                alertbox.setMessage("Do you  wants to Cancel this Order?");
                alertbox.setTitle("Cancel order!!");

                alertbox.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteOrder(dm.getOrderId(),view);
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
        return ordersList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView foodname, foodprice,foodtotal,foodqty,username,seatinfo,mobile,btnstatus;
        ImageView foodImage,dboyImage;

        public MyviewHolder(@NonNull OrderCardBinding binding) {
            super(binding.getRoot());
            btnstatus=binding.btnDelivered;
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
    private void getDboyDetails(View view, String stop) {
        final ProgressDialog progressDoalog = new ProgressDialog(view.getRootView().getContext());
        progressDoalog.setMessage("Checking....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Delivery_Boys").whereEqualTo("stop", stop)
                // .whereEqualTo("path",pathSelected)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("@", queryDocumentSnapshots + "");
                            AlertDialog.Builder alertbox=new AlertDialog.Builder(view.getRootView().getContext());
                            alertbox.setMessage(queryDocumentSnapshots.getDocuments().get(0).getString("boyName")+"\n"+
                                    queryDocumentSnapshots.getDocuments().get(0).getString("boyMobile"));
                            alertbox.setTitle("Contact DeliveryBoy!!");

                            alertbox.setPositiveButton("call", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + queryDocumentSnapshots.getDocuments().get(0).getString("boyMobile")));
                                  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    view.getRootView().getContext().startActivity(intent);
                                    dialogInterface.dismiss();
                                }
                            });


                            alertbox.show();
                        }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });
        progressDoalog.dismiss();
    }
    private void deleteOrder(String doc_name, View view) {
        //Log.d("@", "showData: Called")

        final ProgressDialog progressDoalog = new ProgressDialog(view.getRootView().getContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Orders").document(doc_name).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Navigation.findNavController(view).navigate(R.id.action_orderListFragment_self);
                        Toast.makeText(view.getRootView().getContext(), "Order cancelled successfully", Toast.LENGTH_SHORT).show();
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
