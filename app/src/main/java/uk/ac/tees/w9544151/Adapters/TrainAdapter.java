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

import uk.ac.tees.w9544151.Models.TrainModel;
import uk.ac.tees.w9544151.R;
import uk.ac.tees.w9544151.databinding.TrainCardBinding;

public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.MyviewHolder> {
    public List<TrainModel> trainList;
    TrainCardBinding binding;
    private AdapterCallback mAdapterCallback;

    public TrainAdapter(AdapterCallback callback) {
        this.mAdapterCallback = callback;
    }

    @NonNull
    @Override
    public TrainAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = TrainCardBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new TrainAdapter.MyviewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull TrainAdapter.MyviewHolder holder, int position) {
        TrainModel dm = trainList.get(position);
        holder.trainNumber.setText(dm.getTrainNumber());
        holder.trainName.setText(dm.getTrainName());
        holder.path.setText(dm.getStartPoint() + "-" + dm.getEndPoint());
        //  holder.foodImage.setImageResource(dm.getItemImage());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapterCallback.onMethodCallback();
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox=new AlertDialog.Builder(view.getRootView().getContext());
                alertbox.setMessage("Do you  wants to Delete this Train ?");
                alertbox.setTitle("Delete!!");

                alertbox.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteTrain(dm.getTrainId(),view);

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
    private void deleteTrain(String doc_name, View view) {
        //Log.d("@", "showData: Called")

        final ProgressDialog progressDoalog = new ProgressDialog(view.getRootView().getContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Train").document(doc_name).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Navigation.findNavController(view).navigate(R.id.action_trainListFragment_self);
                        Toast.makeText(view.getRootView().getContext(), "Train removed successfully", Toast.LENGTH_SHORT).show();
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
        return trainList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView trainId, trainNumber, trainName, path;
        ImageView ivDelete;
        ConstraintLayout root;

        public MyviewHolder(@NonNull TrainCardBinding binding) {
            super(binding.getRoot());
            trainName = binding.tvTrainName;
            trainNumber = binding.tvTrainNumber;
            path = binding.tvTrainPath;
            ivDelete = binding.ivDelete;
            root=binding.root;

        }
    }
}
