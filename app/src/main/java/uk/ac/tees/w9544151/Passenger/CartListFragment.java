package uk.ac.tees.w9544151.Passenger;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.w9544151.Adapters.AdapterCallback;
import uk.ac.tees.w9544151.Adapters.CartAdapter;
import uk.ac.tees.w9544151.Models.CartModel;
import uk.ac.tees.w9544151.Models.TrainModel;
import uk.ac.tees.w9544151.R;
import uk.ac.tees.w9544151.databinding.FragmentCartListBinding;


public class CartListFragment extends Fragment implements AdapterCallback {
    FragmentCartListBinding binding;
    CartAdapter adapter = new CartAdapter(this);
    List<CartModel> cartList = new ArrayList();
String items="",qty="",image="",price="";
float total=0.0f;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getView()).navigate(R.id.action_cartListFragment_to_passengerHome);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartListBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showData();
        binding.rvCarts.setLayoutManager(new LinearLayoutManager(requireContext()));


        binding.btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cartList.isEmpty()){
                    Toast.makeText(requireContext(),"Please add atleast an item to ypur cart",Toast.LENGTH_LONG).show();
                }
                else {
                    Bundle ed = new Bundle();
                    ed.putString("itemname",items);
                    ed.putString("itemqty",qty);
                    ed.putString("total",total+"");
                    ed.putString("image",image);
                    ed.putString("itemprice","");
                    Navigation.findNavController(getView()).navigate(R.id.action_cartListFragment_to_placeOrderFragment, ed);
                }
            }
        });
    }

    @Override
    public void onMethodCallback() {

    }

    private void showData() {
        final ProgressDialog progressDoalog = new ProgressDialog(requireContext());
        progressDoalog.setMessage("loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        //Log.d("@", "showData: Called")
        SharedPreferences sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);
        String uid = sp.getString("userId", "error");
        cartList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Cart").whereEqualTo("userid", uid)
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
                            cartList.add(new CartModel(
                                    queryDocumentSnapshots.getDocuments().get(i).getId(), "",
                                    queryDocumentSnapshots.getDocuments().get(i).getString("image"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("itemName"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("itemQuantity"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("itemPrice"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("totalAmount")

                            ));
                            items=items+queryDocumentSnapshots.getDocuments().get(i).getString("itemName")+",";
                            qty=qty+queryDocumentSnapshots.getDocuments().get(i).getString("itemQuantity")+",";
                            total=total+(Float.parseFloat(queryDocumentSnapshots.getDocuments().get(i).getString("itemPrice"))*Float.parseFloat(queryDocumentSnapshots.getDocuments().get(i).getString("itemQuantity")));
                            image=queryDocumentSnapshots.getDocuments().get(i).getString("image");
                            price=price+(Float.parseFloat(queryDocumentSnapshots.getDocuments().get(i).getString("itemPrice")))+",";

                        }

if (cartList.isEmpty()){
    binding.labelNoData.setVisibility(View.VISIBLE);
}
else {
    binding.labelNoData.setVisibility(View.GONE);
}
                        progressDoalog.dismiss();
                        adapter.cartList = cartList;
                        binding.rvCarts.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });


    }


}