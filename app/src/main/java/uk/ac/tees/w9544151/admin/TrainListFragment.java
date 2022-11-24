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
import uk.ac.tees.w9544151.Adapters.TrainAdapter;
import uk.ac.tees.w9544151.Models.TrainModel;
import uk.ac.tees.w9544151.databinding.FragmentTrainListBinding;


public class TrainListFragment extends Fragment  implements AdapterCallback , CallBackTwice {
    FragmentTrainListBinding binding;
    TrainAdapter adapter=new TrainAdapter(this);
    List<TrainModel> trainList = new ArrayList();
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
        // Inflate the layout for this fragment
        binding=FragmentTrainListBinding.inflate(getLayoutInflater(),container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showData();
        /*for(int i=0;i<10;i++) {
            trainList.add(new TrainModel("Rajyarani","16350","Rajya Rani Express","Nilambur","Kochuveli"));
            trainList.add(new TrainModel("Amritha","16234","Amritha Express","Trivandrum","Calicut"));
            trainList.add(new TrainModel("Parasuram","16150","Parasuram Express","Nagarcoil","Mangalore"));
        }*/
        binding.rvTrain.setLayoutManager(new LinearLayoutManager(requireContext()));


    }

    @Override
    public void onMethodCallback() {

    }

    private void showData() {
        //Log.d("@", "showData: Called")

        trainList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Train")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("@", queryDocumentSnapshots + "");
                        int i;
                        for (i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
                            /*Log.d("!", queryDocumentSnapshots.getDocuments().get(i).getId());
                            Log.d("!", queryDocumentSnapshots.getDocuments().get(i).getString("foodName"));
                            Log.d("!", queryDocumentSnapshots.getDocuments().get(i).getString("foodPrice"));*/
                            trainList.add(new TrainModel(
                                    "",
                                    queryDocumentSnapshots.getDocuments().get(i).getString("trainNumber"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("trainName"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("startPoint"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("endPoint"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("path")

                                    ));
                        }
                        adapter.trainList=trainList;
                        binding.rvTrain.setAdapter(adapter);
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
    public void onStopCallback(String routeName) {

    }
}