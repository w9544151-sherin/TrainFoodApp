package uk.ac.tees.w9544151.Adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import java.util.List;

import uk.ac.tees.w9544151.Models.StopModel;
import uk.ac.tees.w9544151.R;
import uk.ac.tees.w9544151.databinding.StopCardBinding;

public class StopAdapter extends RecyclerView.Adapter<StopAdapter.MyviewHolder> {
    public List<StopModel> stopList;
    StopCardBinding binding;
    private AdapterCallback mAdapterCallback;

    public StopAdapter(AdapterCallback callback) {
        this.mAdapterCallback = callback;
    }

    @NonNull
    @Override
    public StopAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = StopCardBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new StopAdapter.MyviewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull StopAdapter.MyviewHolder holder, int position) {
        StopModel dm = stopList.get(position);
        //holder.trainNumber.setText(dm.getTrainNumber());
        holder.stopName.setText(dm.getStopName());
        holder.stopNumber.setText(dm.getStopNumber());

        //  holder.foodImage.setImageResource(dm.getItemImage());
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox=new AlertDialog.Builder(view.getRootView().getContext());
                alertbox.setMessage("Do you  wants to Delete this Train ?");
                alertbox.setTitle("Delete!!");

                alertbox.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteTrainStop(dm.getStopId(),view);

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
    private void deleteTrainStop(String doc_name, View view) {
        //Log.d("@", "showData: Called")

        final ProgressDialog progressDoalog = new ProgressDialog(view.getRootView().getContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Stop").document(doc_name).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Navigation.findNavController(view).navigate(R.id.action_stopListFragment_self);
                        Toast.makeText(view.getRootView().getContext(), "stop removed successfully", Toast.LENGTH_SHORT).show();
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
        return stopList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView Id, stopName, stopNumber;
        ImageView ivDelete;
        ConstraintLayout root;

        public MyviewHolder(@NonNull StopCardBinding binding) {
            super(binding.getRoot());
            stopNumber= binding.tvStopNumber;
            stopName= binding.tvStopName;
            ivDelete=binding.ivDelete;
           // trainName = binding.tvTrainName;
           // trainNumber = binding.tvTrainNumber;
           // path = binding.tvTrainPath;
           // ivDelete = binding.ivDelete;
           // root=binding.root;

        }
    }
}
