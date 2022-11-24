package uk.ac.tees.w9544151.Passenger;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.w9544151.Adapters.AdapterCallback;
import uk.ac.tees.w9544151.Adapters.FoodAdapter;
import uk.ac.tees.w9544151.Adapters.HomeAdapter;
import uk.ac.tees.w9544151.Models.Foodmodel;
import uk.ac.tees.w9544151.R;
import uk.ac.tees.w9544151.databinding.FragmentFoodHomeBinding;
//
//public abstract class FoodHomeFragment extends Fragment implements AdapterCallback {
//    FragmentFoodHomeBinding binding;
//
//    List<Foodmodel> foodList = new ArrayList();
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        binding=FragmentFoodHomeBinding.inflate(getLayoutInflater(),container,false);
//        return binding.getRoot();
//    }
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        SharedPreferences sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);
//        Log.d("in userhome q", sp.getString("userType","error") );
//        for(int i=0;i<10;i++) {
//            foodList.add(new Foodmodel("i","Chicken Biriyani", "260", "R.drawable.foodmenu2"));
//        }
//        FoodAdapter adapter=new FoodAdapter(this, getContext(), sp.getString("userType", "error"));
//        binding.rvFoodMenu.setLayoutManager(new LinearLayoutManager(requireContext()));
//        adapter.fooodList=foodList;
//        binding.rvFoodMenu.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//
//        binding.userFeedback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//             //   Navigation.findNavController(view).navigate(R.id.action_foodHomeFragment2_to_feedbackFragments);
//            }
//        });
//        binding.usertrackOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               // Navigation.findNavController(view).navigate(R.id.action_foodHomeFragment2_to_orderListFragment2);
//            }
//        });
//        binding.userlog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(view).navigateUp();
//            }
//        });
//        binding.ivcart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               // Navigation.findNavController(view).navigate(R.id.action_foodHomeFragment2_to_cartListFragment);
//            }
//        });
//
//    }
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requireActivity().getOnBackPressedDispatcher().addCallback( this,new OnBackPressedCallback(true){
//            @Override
//            public void handleOnBackPressed() {
//                AlertDialog.Builder alertbox=new AlertDialog.Builder(requireContext());
//                alertbox.setMessage("Do you really wants to logout from this app?");
//                alertbox.setTitle("Logout!!");
//
//                alertbox.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        Navigation.findNavController(getView()).navigateUp();
//
//                    }
//                });
//                alertbox.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//                alertbox.show();
//
//
//
//            }
//        });
//    }
//
//    @Override
//    public void onMethodCallback() {
//        // TODO write action aganist add cart
//    }
//}