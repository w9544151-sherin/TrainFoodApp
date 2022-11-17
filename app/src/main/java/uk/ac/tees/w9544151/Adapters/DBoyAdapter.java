package uk.ac.tees.w9544151.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import uk.ac.tees.w9544151.Models.DBoyModel;
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
        holder.boyId.setText(dm.getBoyId());
      //  holder.boyImage.setImageBitmap(dm.getBoyImage());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapterCallback.onMethodCallback();
            }
        });


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
            delete=binding.ivDelete;

        }
    }
}
