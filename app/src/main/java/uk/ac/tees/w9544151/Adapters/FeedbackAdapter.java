package uk.ac.tees.w9544151.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import uk.ac.tees.w9544151.Models.FeedbackModel;
import uk.ac.tees.w9544151.databinding.FeedbackTileBinding;


public class FeedbackAdapter extends  RecyclerView.Adapter<FeedbackAdapter.MyviewHolder> {
    public List<FeedbackModel> feedbackList;
    FeedbackTileBinding binding;
    private AdapterCallback mAdapterCallback;

    public FeedbackAdapter(AdapterCallback callback) {
        this.mAdapterCallback = callback;
    }

    @NonNull
    @Override
    public FeedbackAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = FeedbackTileBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new FeedbackAdapter.MyviewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackAdapter.MyviewHolder holder, int position) {
        FeedbackModel dm = feedbackList.get(position);
        holder.passengerName.setText(dm.getUserName());
        holder.feedback.setText(dm.getFeedback());
        //holder.rating.setText("Rating :- "+dm.getRatingValue());
        holder.rate.setRating(Float.parseFloat(dm.getRatingValue()));
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView passengerName,feedback;
        RatingBar rate;


        public MyviewHolder(@NonNull FeedbackTileBinding binding) {
            super(binding.getRoot());
            passengerName=binding.tvPassengerName;
            feedback=binding.tvFeedback;
            rate=binding.rateStar;


        }
    }
}
