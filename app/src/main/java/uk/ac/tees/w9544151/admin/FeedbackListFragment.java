package uk.ac.tees.w9544151.admin;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.w9544151.Adapters.AdapterCallback;
import uk.ac.tees.w9544151.Adapters.FeedbackAdapter;
import uk.ac.tees.w9544151.Adapters.TrainAdapter;
import uk.ac.tees.w9544151.Models.FeedbackModel;
import uk.ac.tees.w9544151.Models.TrainModel;
import uk.ac.tees.w9544151.R;
import uk.ac.tees.w9544151.databinding.FragmentFeedbackListBinding;
import uk.ac.tees.w9544151.databinding.FragmentTrainListBinding;


public class FeedbackListFragment extends Fragment  implements AdapterCallback {
FragmentFeedbackListBinding binding;
    FeedbackAdapter adapter=new FeedbackAdapter(this);
    List<FeedbackModel> feedbackList = new ArrayList();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback( this,new OnBackPressedCallback(true){
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getView()).navigateUp();
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentFeedbackListBinding.inflate(getLayoutInflater(),container,false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for(int i=0;i<10;i++) {
            feedbackList.add(new FeedbackModel("f01","Krithika","Awesome service and delicious food","5"));
        }
        binding.rvFeedbacks.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter.feedbackList=feedbackList;
        binding.rvFeedbacks.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onMethodCallback() {

    }
}