package uk.ac.tees.w9544151.DBoy;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.w9544151.Adapters.AdapterCallback;
import uk.ac.tees.w9544151.Adapters.OrdersAdapter;
import uk.ac.tees.w9544151.Models.OrderModel;
import uk.ac.tees.w9544151.R;
import uk.ac.tees.w9544151.databinding.FragmentDBoyBinding;
import uk.ac.tees.w9544151.databinding.FragmentDBoyHomeBinding;


public class DBoyHomeFragment extends Fragment implements AdapterCallback {
FragmentDBoyHomeBinding binding;
    OrdersAdapter adapter=new OrdersAdapter(this);
    List<OrderModel> orderList = new ArrayList();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback( this,new OnBackPressedCallback(true){
            @Override
            public void handleOnBackPressed() {
                Toast.makeText(requireContext(), "please logout", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentDBoyHomeBinding.inflate(getLayoutInflater(),container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for(int i=0;i<10;i++) {
            orderList.add(new OrderModel(
                    "1","Chicken Fry", "200", "2","Nithin","9747062356","16649/s6/45","400","R.drawable.foodmenu2"));
        }
        binding.rvdBoyOrders.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter.ordersList=orderList;
        binding.rvdBoyOrders.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.editBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_DBoyHomeFragment_to_editProfileFragment);
            }
        });
        binding.ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox=new AlertDialog.Builder(requireContext());
                alertbox.setMessage("Do you really wants to logout from this app?");
                alertbox.setTitle("Logout!!");

                alertbox.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Navigation.findNavController(getView()).navigateUp();

                    }
                });
                alertbox.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertbox.show();
            }
        });
    }

    @Override
    public void onMethodCallback() {

    }
}