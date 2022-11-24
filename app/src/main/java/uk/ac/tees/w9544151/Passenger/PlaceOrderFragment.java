package uk.ac.tees.w9544151.Passenger;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavArgs;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;

import io.grpc.NameResolver;
import uk.ac.tees.w9544151.Adapters.CallBackTwice;
import uk.ac.tees.w9544151.Models.CartModel;
import uk.ac.tees.w9544151.Models.OrderModel;
import uk.ac.tees.w9544151.Models.StopModel;
import uk.ac.tees.w9544151.R;
import uk.ac.tees.w9544151.admin.BottomSheetFragment;
import uk.ac.tees.w9544151.databinding.FragmentOrderListBinding;
import uk.ac.tees.w9544151.databinding.FragmentPlaceOrderBinding;


public class PlaceOrderFragment extends Fragment  implements CallBackTwice {
    FragmentPlaceOrderBinding binding;
    String foodname,foodimage,quantity,price,total,username,mobile,stop,address,userid;
    private CallBackTwice mAdapterCallback;
    public String selectedValue="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getView()).navigateUp();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPlaceOrderBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Random random = new Random();
        int number = random.nextInt(99999999);
        String orderId="Order"+number;
        mAdapterCallback=this;
        foodname = getArguments().getString("itemname");
        Log.d("#", "onViewCreated: " + foodname);
        binding.tvTotalAmount.setText(getArguments().getString("total"));

        binding.etStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheet = new BottomSheetFragment(requireContext(),mAdapterCallback,binding.etTrain.getText().toString(),"stop");
                bottomSheet.show(getChildFragmentManager(),"BottomSheet");
            }
        });

        binding.labelGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedValue="General";
             binding.labelGeneral.setBackgroundResource(R.color.blue);
                binding.labelReserved.setBackgroundResource(R.color.ocean_blue);
            }
        });
        binding.labelReserved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedValue="Reserved";
                binding.labelReserved.setBackgroundResource(R.color.blue);
                binding.labelGeneral.setBackgroundResource(R.color.ocean_blue);
            }
        });

        binding.btnBuy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String seat = binding.etSeat.getText().toString();
                String coach = binding.etCoachName.getText().toString();
                String trainNo = binding.etTrain.getText().toString();
                if (trainNo.isEmpty() || seat.isEmpty() || binding.etStop.getText().toString().isEmpty() || coach.isEmpty()) {
                    Toast.makeText(requireContext(), "All fields are mandatory", Toast.LENGTH_LONG).show();
                } else {
                    SharedPreferences sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);
                    username = sp.getString("userName", "");
                    mobile = sp.getString("userMobile", "");
                    userid = sp.getString("userId", "");
                    address = binding.etTrain.getText().toString() + "/" + binding.etStop.getText().toString() + "/" +
                            coach + "/" + seat;
                    price = getArguments().getString("itemprice");
                    quantity = getArguments().getString("itemqty");
                    total = getArguments().getString("total");
                    foodimage = getArguments().getString("image");
                    Log.d("aq", "order check-: " + username + "," + mobile + "," + userid + "," + address + "," + seat + "," + coach + "\n,(stop)" + binding.etStop.getText().toString());

                    FirebaseFirestore db;
                    db = FirebaseFirestore.getInstance();
                    OrderModel obj = new OrderModel(orderId, foodname, price, quantity, username, userid, mobile,
                            trainNo, seat, coach, total, foodimage, address, "ordered");
                    db.collection("Orders").add(obj).
                            addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
binding.etTrain.setText("");binding.etStop.setText("");
binding.etCoachName.setText("");binding.etSeat.setText("");
                                    Snackbar.make(requireView(), "Order Placed", Snackbar.LENGTH_LONG).show();
                                }
                            }).
                            addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "onSuccess: Fail");
                                    Toast.makeText(requireContext(), "Order Creation failed", Toast.LENGTH_SHORT).show();

                                }
                            });
                }
            }
        });
    }



    @Override
    public void onStopCallback(String routeName) {
        Log.d("@", "onStopCallback: "+routeName);
        binding.etStop.setText(routeName+"");
    }

}