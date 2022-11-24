package uk.ac.tees.w9544151.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import uk.ac.tees.w9544151.Models.TrainModel;
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
                FirebaseFirestore db = FirebaseFirestore.getInstance();
            }
        });


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
