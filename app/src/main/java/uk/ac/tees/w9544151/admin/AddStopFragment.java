package uk.ac.tees.w9544151.admin;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import uk.ac.tees.w9544151.Adapters.CallBackTwice;
import uk.ac.tees.w9544151.Models.StopModel;
import uk.ac.tees.w9544151.Models.Validation;
import uk.ac.tees.w9544151.databinding.FragmentAddStopBinding;


public class AddStopFragment extends Fragment implements CallBackTwice {
    FirebaseFirestore db;
    FragmentAddStopBinding binding;
    static String selectedValue = "";
    public static BottomSheetDialog response;
    private CallBackTwice mAdapterCallback;
    ProgressDialog progressDoalog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getView()).navigateUp();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddStopBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapterCallback = this;
        binding.btnAddStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    if (binding.etAddTrainNumber.getText().toString().isEmpty()) {
                        binding.etAddTrainNumber.setError("Enter a valid train number");
                    } else if (binding.etRoute.getText().toString().isEmpty()) {
                        binding.etRoute.setError("Enter a valid troute");
                    } else if (binding.etStopNumber.getText().toString().isEmpty()) {
                        binding.etStopNumber.setError("Enter a valid stop number");
                    } else if (!binding.etStopName.getText().toString().matches(Validation.text)) {
                        binding.etStopName.setError("Enter a valid stop name");
                    } else {
                        addStopToDatabase();
                    }

            }
        });
        binding.etStopNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(requireContext(), selectedValue, Toast.LENGTH_LONG).show();
                binding.etRoute.setText(selectedValue);
            }
        });
        binding.etRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheet = new BottomSheetFragment(requireContext(), mAdapterCallback, binding.etAddTrainNumber.getText().toString(), "");
                bottomSheet.show(getChildFragmentManager(), "BottomSheet");
            }
        });


    }


    private void addStopToDatabase() {
        progressDoalog=new ProgressDialog(getContext());
        progressDoalog.setMessage("Data adding....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        //path can taken from dropdown from train table and make it in a variable
        String path = binding.etRoute.getText().toString();
        String id = "Stop" + binding.etAddTrainNumber.getText().toString();
        StopModel obj = new StopModel(id, binding.etStopName.getText().toString(), binding.etStopNumber.getText().toString(), binding.etAddTrainNumber.getText().toString(), path);
        db = FirebaseFirestore.getInstance();
        db.collection("Stop").add(obj).
                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Log.d("TAG", "onSuccess: Success");
                        binding.etAddTrainNumber.setText("");
                        binding.etRoute.setText("");
                        binding.etStopName.setText("");
                        binding.etStopNumber.setText("");
                        progressDoalog.dismiss();
                        Snackbar.make(requireView(), "Stop Successfully Added", Snackbar.LENGTH_LONG).show();
                    }
                }).
                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onSuccess: Fail");
                        Toast.makeText(requireContext(), "Stop Creation failed", Toast.LENGTH_SHORT).show();

                    }
                });

    }


    @Override
    public void onStopCallback(String routeName) {
        binding.etRoute.setText(routeName);
    }
}