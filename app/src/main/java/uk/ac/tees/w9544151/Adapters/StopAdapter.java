package uk.ac.tees.w9544151.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.tees.w9544151.Models.StopModel;
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
//        holder.root.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mAdapterCallback.onMethodCallback();
//            }
//        });


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
           // trainName = binding.tvTrainName;
           // trainNumber = binding.tvTrainNumber;
           // path = binding.tvTrainPath;
           // ivDelete = binding.ivDelete;
           // root=binding.root;

        }
    }
}
