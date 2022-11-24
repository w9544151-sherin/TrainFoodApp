package uk.ac.tees.w9544151.admin;

import android.content.Context;
import android.content.SharedPreferences;
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

import uk.ac.tees.w9544151.Adapters.ActionCallback;
import uk.ac.tees.w9544151.Adapters.FoodAdapter;
import uk.ac.tees.w9544151.Models.Foodmodel;
import uk.ac.tees.w9544151.databinding.FragmentFoodListBinding;


public class FoodListFragment extends Fragment implements ActionCallback {
    FragmentFoodListBinding binding;
    FoodAdapter adapter;
    List<Foodmodel> foodList = new ArrayList();
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
        binding=FragmentFoodListBinding.inflate(getLayoutInflater(),container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);
        Log.d("in admin home q", sp.getString("userType","error") );
        adapter=new FoodAdapter(this, getContext(),sp.getString("userType","error"));
        showData();
        Log.d("size", "onViewCreated: "+foodList.size());
      /*  for(int i=0;i<5;i++) {
            foodList.add(new Foodmodel("f001","Coffee","20","nil"));

        }*/
        binding.rvFood.setLayoutManager(new LinearLayoutManager(requireContext()));

    }



    private void showData() {
        //Log.d("@", "showData: Called")

        foodList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Food_Menu")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("@", queryDocumentSnapshots + "");
                        int i;
                        for (i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
                            Log.d("!", queryDocumentSnapshots.getDocuments().get(i).getId());
                            Log.d("!", queryDocumentSnapshots.getDocuments().get(i).getString("foodName"));
                            Log.d("!", queryDocumentSnapshots.getDocuments().get(i).getString("foodPrice"));
                            foodList.add(new Foodmodel(queryDocumentSnapshots.getDocuments().get(i).getId(), queryDocumentSnapshots.getDocuments().get(i).getString("foodName")
                                    , queryDocumentSnapshots.getDocuments().get(i).getString("foodPrice")
                                    , queryDocumentSnapshots.getDocuments().get(i).getString("foodImage")));
                        }
                        adapter.fooodList=foodList;
                        binding.rvFood.setAdapter(adapter);
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
    public void onBuyCallback(String s, String foodName, String foodImage, String foodPrice, String cart) {

    }
}