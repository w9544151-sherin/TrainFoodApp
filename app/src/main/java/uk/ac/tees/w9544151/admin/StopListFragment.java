
package uk.ac.tees.w9544151.admin;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.w9544151.Adapters.AdapterCallback;
import uk.ac.tees.w9544151.Adapters.CallBackTwice;
import uk.ac.tees.w9544151.Adapters.StopAdapter;
import uk.ac.tees.w9544151.Models.StopModel;
import uk.ac.tees.w9544151.databinding.FragmentStopListBinding;

public class StopListFragment extends Fragment implements CallBackTwice, AdapterCallback {
    FragmentStopListBinding binding;
    StopAdapter adapter;
    private CallBackTwice mAdapterCallback;
    List<StopModel> stopList = new ArrayList();

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
        // Inflate the layout for this fragment
        binding = FragmentStopListBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new StopAdapter(this);
        mAdapterCallback = this;

        binding.rvStop.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.btnFindStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData();
            }
        });
        binding.trainPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheet = new BottomSheetFragment(requireContext(), mAdapterCallback, binding.etTrainNumber.getText().toString(), "");
                bottomSheet.show(getChildFragmentManager(), "BottomSheet");
            }
        });
    }


    private void showData() {
        //Log.d("@", "showData: Called")
        String enteredNumber = binding.etTrainNumber.getText().toString();
        String pathSelected = binding.trainPath.getText().toString();
        stopList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Stop").whereEqualTo("trainNumber", enteredNumber).whereEqualTo("path", pathSelected)
                // .whereEqualTo("path",pathSelected)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("@", queryDocumentSnapshots + "");
                        int i;
                        for (i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
                            stopList.add(new StopModel("",
                                    queryDocumentSnapshots.getDocuments().get(i).getString("stopName"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("stopNumber"),
                                    "", ""

                            ));
                        }
                        adapter.stopList = stopList;
                        binding.rvStop.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onMethodCallback() {

    }

    @Override
    public void onStopCallback(String routeName) {
        binding.trainPath.setText(routeName);
    }


//Log.d("!", queryDocumentSnapshots.getDocuments().get(i).getId());

}

