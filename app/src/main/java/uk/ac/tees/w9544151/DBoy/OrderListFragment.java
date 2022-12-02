package uk.ac.tees.w9544151.DBoy;

import android.app.ProgressDialog;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.w9544151.Adapters.AdapterCallback;
import uk.ac.tees.w9544151.Adapters.OrdersAdapter;
import uk.ac.tees.w9544151.Models.CartModel;
import uk.ac.tees.w9544151.Models.OrderModel;
import uk.ac.tees.w9544151.R;
import uk.ac.tees.w9544151.databinding.FragmentOrderListBinding;


public class OrderListFragment extends Fragment implements AdapterCallback {
    FragmentOrderListBinding binding;
    OrdersAdapter adapter;
    ProgressDialog progressDoalog;
    List<OrderModel> orderList = new ArrayList();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                SharedPreferences sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);
                if (sp.getString("userType", "").equals("admin")) {
                    Navigation.findNavController(getView()).navigate(R.id.action_orderListFragment_to_cafeFragment);
                } else if (sp.getString("userType", "").equals("dboy")) {
                    Navigation.findNavController(getView()).navigate(R.id.action_orderListFragment_to_passengerHome);
                } else {
                    Navigation.findNavController(getView()).navigate(R.id.action_orderListFragment_to_passengerHome);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOrderListBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);
        Log.d("in userhome q", sp.getString("userType", "error"));
        adapter = new OrdersAdapter(this, getContext(), sp.getString("userType", "error"));

//        for(int i=0;i<10;i++) {
//            orderList.add(new OrderModel("01","Chicken Fry", "200", "2","Nithin","","9747062356","16350/s6/45","400","wdw"));
//        }
        binding.rvOrders.setLayoutManager(new LinearLayoutManager(requireContext()));
        showData();

    }

    @Override
    public void onMethodCallback() {

    }

    private void showData() {
        progressDoalog = new ProgressDialog(requireContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        //Log.d("@", "showData: Called")
        SharedPreferences sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);
        String uid = sp.getString("userId", "error");
        String type = sp.getString("userType", "error");
        orderList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (type.equals("admin")) {
            db.collection("Orders")
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
                                orderList.add(new OrderModel(
                                        queryDocumentSnapshots.getDocuments().get(i).getId(), queryDocumentSnapshots.getDocuments().get(i).getString("itemName"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("itemPrice"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("itemQty"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("username"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("userid"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("mobile"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("trainNumber"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("seatNumber"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("coachNumber"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("totalAmount"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("itemImage"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("address"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("status"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("stop")));
                            }
                            if (orderList.isEmpty()) {
                               // binding.tvHeading.setText("Active Orders Not Available");
                                Snackbar.make(requireView(), "Orders Not Available", Snackbar.LENGTH_LONG).show();
                            }
                            //binding.tvHeading.setText("Placed Orders");
                            //binding.labelNoData.setVisibility(View.GONE);
                            adapter.ordersList = orderList;
                            binding.rvOrders.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            progressDoalog.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                        }
                    });

        } else {
            db.collection("Orders").whereEqualTo("userid", uid)
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
                                orderList.add(new OrderModel(
                                        queryDocumentSnapshots.getDocuments().get(i).getId(), queryDocumentSnapshots.getDocuments().get(i).getString("itemName"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("itemPrice"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("itemQty"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("username"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("userid"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("mobile"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("trainNumber"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("seatNumber"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("coachNumber"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("totalAmount"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("itemImage"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("address"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("status"),
                                        queryDocumentSnapshots.getDocuments().get(i).getString("stop")));
                            }
                            if (orderList.isEmpty()) {
                                binding.tvHeading.setText("Active Orders Not Available");
                                binding.labelNoData.setVisibility(View.VISIBLE);
                                Snackbar.make(requireView(), "Orders Not Available", Snackbar.LENGTH_LONG).show();
                            }
                            else {
                                binding.tvHeading.setText("Placed Orders");
                                binding.labelNoData.setVisibility(View.GONE);
                               // Snackbar.make(requireView(), "Orders Not Available", Snackbar.LENGTH_LONG).show();
                                binding.labelNoData.setVisibility(View.GONE);
                                adapter.ordersList = orderList;
                                binding.rvOrders.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                            //binding.tvHeading.setText("Placed Orders");

                            progressDoalog.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                        }
                    });

        }


    }

}