package uk.ac.tees.w9544151.Passenger;

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
import uk.ac.tees.w9544151.Adapters.CartAdapter;
import uk.ac.tees.w9544151.Adapters.TrainAdapter;
import uk.ac.tees.w9544151.Models.CartModel;
import uk.ac.tees.w9544151.Models.TrainModel;
import uk.ac.tees.w9544151.R;
import uk.ac.tees.w9544151.databinding.FragmentCartListBinding;
import uk.ac.tees.w9544151.databinding.FragmentTrainListBinding;


public class CartListFragment extends Fragment implements AdapterCallback {
    FragmentCartListBinding binding;
    CartAdapter adapter=new CartAdapter(this);
    List<CartModel> cartList = new ArrayList();
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
        binding=FragmentCartListBinding.inflate(getLayoutInflater(),container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for(int i=0;i<10;i++) {
            cartList.add(new CartModel("nil","Burger","2","50","100"));

        }
        binding.rvCarts.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter.cartList=cartList;
        binding.rvCarts.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onMethodCallback() {

    }
}