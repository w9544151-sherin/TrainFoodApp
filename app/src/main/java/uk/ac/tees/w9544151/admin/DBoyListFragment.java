package uk.ac.tees.w9544151.admin;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

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
import uk.ac.tees.w9544151.Adapters.DBoyAdapter;
import uk.ac.tees.w9544151.Models.DBoyModel;
import uk.ac.tees.w9544151.databinding.FragmentDBoyListBinding;


public class DBoyListFragment extends Fragment implements AdapterCallback , CallBackTwice {
    FragmentDBoyListBinding binding;
    DBoyAdapter adapter=new DBoyAdapter(this);
    List<DBoyModel> boyList = new ArrayList();
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
        binding=FragmentDBoyListBinding.inflate(getLayoutInflater(),container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*for(int i=0;i<10;i++) {
            boyList.add(new DBoyModel("B01","Rajashegar","9787890099","Kollam","nil"));
        }*/
        showData();
        binding.rvBoys.setLayoutManager(new GridLayoutManager(requireContext(),2));

    }

    @Override
    public void onMethodCallback() {

    }

    private void showData() {
        //Log.d("@", "showData: Called")

        boyList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Delivery_Boys")
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
                            boyList.add(new DBoyModel(
                                    queryDocumentSnapshots.getDocuments().get(i).getString("boyId"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("boyName"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("boyMobile"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("stop"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("boyImage"),
                                    "","",
                                    queryDocumentSnapshots.getDocuments().get(i).getString("type")
                            ));
                        }
                        adapter.boyList=boyList;
                        binding.rvBoys.setAdapter(adapter);
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