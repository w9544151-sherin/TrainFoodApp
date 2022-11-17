package uk.ac.tees.w9544151.DBoy;

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
import uk.ac.tees.w9544151.Models.Foodmodel;
import uk.ac.tees.w9544151.Models.OrderModel;
import uk.ac.tees.w9544151.R;
import uk.ac.tees.w9544151.databinding.FragmentOrderListBinding;


public class OrderListFragment extends Fragment implements AdapterCallback {
    FragmentOrderListBinding binding;
    OrdersAdapter adapter=new OrdersAdapter(this);
    List<OrderModel> orderList = new ArrayList();
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
        binding=FragmentOrderListBinding.inflate(getLayoutInflater(),container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for(int i=0;i<10;i++) {
            orderList.add(new OrderModel("01","Chicken Fry", "200", "2","Nithin","9747062356","16350/s6/45","400","wdw"));
        }
        binding.rvOrders.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter.ordersList=orderList;
        binding.rvOrders.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onMethodCallback() {

    }
}