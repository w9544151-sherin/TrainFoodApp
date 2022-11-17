package uk.ac.tees.w9544151.Passenger;

import android.os.Bundle;

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
import uk.ac.tees.w9544151.Adapters.HomeAdapter;
import uk.ac.tees.w9544151.Models.Foodmodel;
import uk.ac.tees.w9544151.R;
import uk.ac.tees.w9544151.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment implements AdapterCallback {
FragmentHomeBinding binding;
    HomeAdapter adapter=new HomeAdapter(this);
    List<Foodmodel> foodList = new ArrayList();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(getLayoutInflater(),container,false);
        return binding.getRoot();
    }
    @Override
    public void onMethodCallback() {
       Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_loginFragment);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for(int i=0;i<10;i++) {
            foodList.add(new Foodmodel("1","Chicken Fry", "200", "R.drawable.foodmenu2"));
        }
        binding.rvFoodMenu.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter.fooodList=foodList;
        binding.rvFoodMenu.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.ivloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_loginFragment);
            }
        });

    }
}