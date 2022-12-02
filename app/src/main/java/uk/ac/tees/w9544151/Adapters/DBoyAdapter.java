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
import uk.ac.tees.w9544151.Models.DBoyModel;
import uk.ac.tees.w9544151.R;
import uk.ac.tees.w9544151.databinding.ProfileCardBinding;

public class DBoyAdapter extends RecyclerView.Adapter<DBoyAdapter.MyviewHolder> {
    public List<DBoyModel> boyList;
    ProfileCardBinding binding;
    private AdapterCallback mAdapterCallback;

    public DBoyAdapter(AdapterCallback callback) {
        this.mAdapterCallback = callback;
    }
    @NonNull
    @Override
    public DBoyAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = ProfileCardBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new DBoyAdapter.MyviewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull DBoyAdapter.MyviewHolder holder, int position) {
        DBoyModel dm = boyList.get(position);
        holder.boyName.setText(dm.getBoyName());
        holder.boyMobile.setText(dm.getBoyMobile());
        holder.pickup.setText(dm.getStop());
        holder.boyId.setText(dm.getUserId());
        try {
            Log.d("##", dm.getBoyImage());
            Glide.with(holder.boyImage.getContext())
                    .load(dm.getBoyImage())
                    .into(holder.boyImage);
        }
        catch (Exception e){

        }
      //  holder.boyImage.setImageBitmap(dm.getBoyImage());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox=new AlertDialog.Builder(view.getRootView().getContext());
                alertbox.setMessage("Do you  wants to Delete this Delivery boy?");
                alertbox.setTitle("Delete!!");

                alertbox.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteDboy(dm.getUserId(),view);

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
    private void deleteDboy(String doc_name, View view) {
        //Log.d("@", "showData: Called")

        final ProgressDialog progressDoalog = new ProgressDialog(view.getRootView().getContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Delivery_Boys").document(doc_name).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Navigation.findNavController(view).navigate(R.id.action_DBoyListFragment_self);
                        Toast.makeText(view.getRootView().getContext(), "Boy removed successfully", Toast.LENGTH_SHORT).show();
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
        return boyList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView boyId,boyName,boyMobile,pickup;
        ImageView boyImage,delete;

        public MyviewHolder(@NonNull ProfileCardBinding binding) {
            super(binding.getRoot());
            boyName = binding.tvBoyName;
            boyMobile = binding.tvBoyNumber;
            pickup = binding.tvStopName;
            boyId = binding.tvBoyId;
            boyImage=binding.ivBoy;
            delete=binding.ivRound;

        }
    }
}
