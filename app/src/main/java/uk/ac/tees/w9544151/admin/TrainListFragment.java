package uk.ac.tees.w9544151.admin;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
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
import uk.ac.tees.w9544151.Adapters.OrdersAdapter;
import uk.ac.tees.w9544151.Adapters.TrainAdapter;
import uk.ac.tees.w9544151.Models.OrderModel;
import uk.ac.tees.w9544151.Models.TrainModel;
import uk.ac.tees.w9544151.R;
import uk.ac.tees.w9544151.databinding.FragmentOrderListBinding;
import uk.ac.tees.w9544151.databinding.FragmentTrainListBinding;


public class TrainListFragment extends Fragment  implements AdapterCallback  {
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
        for(int i=0;i<10;i++) {
            trainList.add(new TrainModel("Rajyarani","16350","Rajya Rani Express","Nilambur","Kochuveli"));
            trainList.add(new TrainModel("Amritha","16234","Amritha Express","Trivandrum","Calicut"));
            trainList.add(new TrainModel("Parasuram","16150","Parasuram Express","Nagarcoil","Mangalore"));
        }
        binding.rvTrain.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter.trainList=trainList;
        binding.rvTrain.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onMethodCallback() {

    }
}