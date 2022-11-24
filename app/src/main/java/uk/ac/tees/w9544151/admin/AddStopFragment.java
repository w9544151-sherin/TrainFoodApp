package uk.ac.tees.w9544151.admin;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import uk.ac.tees.w9544151.Adapters.CallBackTwice;
import uk.ac.tees.w9544151.Models.StopModel;
import uk.ac.tees.w9544151.databinding.FragmentAddStopBinding;


public class AddStopFragment extends Fragment  implements  CallBackTwice {
    FirebaseFirestore db;
    FragmentAddStopBinding binding;
    static String selectedValue="";
    public static BottomSheetDialog response;
    private CallBackTwice mAdapterCallback;
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
        binding = FragmentAddStopBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapterCallback=this;
        binding.btnAddStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    addStopToDatabase();
                }

            }
        });
binding.etStopNumber.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(requireContext(),selectedValue,Toast.LENGTH_LONG).show();
        binding.etRoute.setText(selectedValue);
    }
});
        binding.etRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheet = new BottomSheetFragment(requireContext(),mAdapterCallback,binding.etAddTrainNumber.getText().toString(), "");
                bottomSheet.show(getChildFragmentManager(),"BottomSheet");
            }
        });


    }


    private void addStopToDatabase(){
        //path can taken from dropdown from train table and make it in a variable
        String path=binding.etRoute.getText().toString();
        String id="Stop"+binding.etAddTrainNumber.getText().toString();
        StopModel obj=new StopModel(id,binding.etStopName.getText().toString(),binding.etStopNumber.getText().toString(),binding.etAddTrainNumber.getText().toString(),path);
        db = FirebaseFirestore.getInstance();
        db.collection("Stop").add(obj).
                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Log.d("TAG", "onSuccess: Success");
                        binding.etAddTrainNumber.setText("");
                        binding.etStopName.setText("");
                        binding.etStopNumber.setText("");
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

    private boolean validate(){
        if (binding.etAddTrainNumber.getText().equals("")|| binding.etStopNumber.getText().equals("")||binding.etStopName.getText().equals(""))
        {
            Toast.makeText(requireActivity(),"All fields are mandatory",Toast.LENGTH_LONG);
            return false;
        }
        else {
            return true;
        }
    }


    @Override
    public void onStopCallback(String routeName) {
        binding.etRoute.setText(routeName);
    }
}