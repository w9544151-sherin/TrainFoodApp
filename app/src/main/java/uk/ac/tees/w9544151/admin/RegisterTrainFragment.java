package uk.ac.tees.w9544151.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import uk.ac.tees.w9544151.Models.TrainModel;
import uk.ac.tees.w9544151.Models.UserModel;
import uk.ac.tees.w9544151.Models.Validation;
import uk.ac.tees.w9544151.R;
import uk.ac.tees.w9544151.databinding.FragmentRegisterBinding;
import uk.ac.tees.w9544151.databinding.FragmentRegisterTrainBinding;


public class RegisterTrainFragment extends Fragment {
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseFirestore db;
    ProgressDialog progressDoalog;
    FragmentRegisterTrainBinding binding;

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
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        // Inflate the layout for this fragment
        binding = FragmentRegisterTrainBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnAddTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                progressDoalog=new ProgressDialog(getContext());
                progressDoalog.setMessage("Data adding....");
                progressDoalog.setTitle("Please wait");
                progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDoalog.show();
                String id, trainName, trainNumber, start, destiny;
                trainName = binding.etTrainName.getText().toString();
                id = "T" + binding.etTrainNumber.getText().toString();
                trainNumber = binding.etTrainNumber.getText().toString();
                start = binding.etStart.getText().toString();
                destiny = binding.etDestiny.getText().toString();
                String path = start + "-" + destiny;
                if (trainNumber.isEmpty()) {
                    binding.etTrainNumber.setError("Enter a valid train number");
                }
                else if (!trainName.matches(Validation.text)) {
                    binding.etTrainName.setError("Enter a valid train name");
                } else if (!start.matches(Validation.text)) {
                    binding.etStart.setError("Enter a valid starting place");
                } else if (!destiny.matches(Validation.text)) {
                    binding.etDestiny.setError("Enter a Ending place");
                } else {
                    fireStoreDatabase:
                    FirebaseFirestore.getInstance();
                    TrainModel obj = new TrainModel(id, trainNumber, trainName, start, destiny, path);
                    db = FirebaseFirestore.getInstance();
                    db.collection("Train").add(obj).
                            addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    binding.etTrainName.getText().clear();
                                    binding.etTrainNumber.getText().clear();
                                    binding.etStart.getText().clear();
                                    binding.etDestiny.getText().clear();
                                    progressDoalog.dismiss();
                                    Snackbar.make(requireView(), "Train added Successfully", Snackbar.LENGTH_LONG).show();
                                }
                            }).
                            addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(requireContext(), "Creation failed", Toast.LENGTH_SHORT).show();
                                }
                            });

                }

            }
        });
    }

}