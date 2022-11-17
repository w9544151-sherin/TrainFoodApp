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
import uk.ac.tees.w9544151.Adapters.DBoyAdapter;
import uk.ac.tees.w9544151.Adapters.OrdersAdapter;
import uk.ac.tees.w9544151.Models.DBoyModel;
import uk.ac.tees.w9544151.Models.OrderModel;
import uk.ac.tees.w9544151.R;
import uk.ac.tees.w9544151.databinding.FragmentDBoyListBinding;
import uk.ac.tees.w9544151.databinding.FragmentOrderListBinding;


public class DBoyListFragment extends Fragment implements AdapterCallback {
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
        for(int i=0;i<10;i++) {
            boyList.add(new DBoyModel("B01","Rajashegar","9787890099","Kollam","nil"));
        }
        binding.rvBoys.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter.boyList=boyList;
        binding.rvBoys.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onMethodCallback() {

    }
}