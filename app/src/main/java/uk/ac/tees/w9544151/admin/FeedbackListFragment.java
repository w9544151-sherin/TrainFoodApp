package uk.ac.tees.w9544151.admin;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.w9544151.Adapters.AdapterCallback;
import uk.ac.tees.w9544151.Adapters.CallBackTwice;
import uk.ac.tees.w9544151.Adapters.FeedbackAdapter;
import uk.ac.tees.w9544151.Models.FeedbackModel;
import uk.ac.tees.w9544151.Models.Foodmodel;
import uk.ac.tees.w9544151.databinding.FragmentFeedbackListBinding;


public class FeedbackListFragment extends Fragment  implements AdapterCallback, CallBackTwice {
FragmentFeedbackListBinding binding;
    FeedbackAdapter adapter=new FeedbackAdapter(this);
    List<FeedbackModel> feedbackList = new ArrayList();
    ProgressDialog progressDoalog;
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
        binding.rvFeedbacks.setLayoutManager(new LinearLayoutManager(requireContext()));
        showData();

    }

    @Override
    public void onMethodCallback() {

    }

    @Override
    public void onStopCallback(String routeName) {

    }

    private void showData() {
        //Log.d("@", "showData: Called")
        progressDoalog = new ProgressDialog(requireContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        feedbackList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Feedback")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("@", queryDocumentSnapshots + "");
                        int i;
                        for (i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
                            feedbackList.add(new FeedbackModel(
                                    queryDocumentSnapshots.getDocuments().get(i).getId(),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("userName")
                                    , queryDocumentSnapshots.getDocuments().get(i).getString("feedback")
                                    , queryDocumentSnapshots.getDocuments().get(i).getString("ratingValue")));
                        }
                        if(feedbackList.isEmpty()){
                            Snackbar.make(requireView(), "No Feedbacks Available", Snackbar.LENGTH_LONG).show();
                        }
                        adapter.feedbackList=feedbackList;
                        binding.rvFeedbacks.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        progressDoalog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}