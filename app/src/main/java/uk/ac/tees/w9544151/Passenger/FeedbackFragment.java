package uk.ac.tees.w9544151.Passenger;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import uk.ac.tees.w9544151.Models.FeedbackModel;
import uk.ac.tees.w9544151.databinding.FragmentFeedbackBinding;


public class FeedbackFragment extends Fragment {

    FirebaseFirestore db;
    FragmentFeedbackBinding binding;
    RatingBar ratingbar;
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
        binding = FragmentFeedbackBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnAddComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                if(binding.etFeedback.getText().toString().isEmpty()){
                    binding.etFeedback.setError("Enter your feedback");
                }
                else {

                    addFeedbacktoDatabase(binding.ratingStar.getRating());
                }
            }
        });
    }

    private void addFeedbacktoDatabase(float ratingStar) {
        SharedPreferences sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);
        FeedbackModel obj=new FeedbackModel("fid",sp.getString("userName",""),binding.etFeedback.getText().toString(),ratingStar+"");
        db = FirebaseFirestore.getInstance();
        db.collection("Feedback").add(obj).
                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Log.d("TAG", "onSuccess: Success");
                        binding.etFeedback.setText("");
                        Snackbar.make(requireView(), "Feedback Successfully Submitted", Snackbar.LENGTH_LONG).show();
                    }
                }).
                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onSuccess: Fail");
                        Toast.makeText(requireContext(), "Feedback Creation failed", Toast.LENGTH_SHORT).show();

                    }
                });


    }

    }