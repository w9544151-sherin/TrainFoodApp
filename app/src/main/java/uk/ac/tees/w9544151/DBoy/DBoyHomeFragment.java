package uk.ac.tees.w9544151.DBoy;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.w9544151.Adapters.AdapterCallback;
import uk.ac.tees.w9544151.Adapters.OrdersAdapter;
import uk.ac.tees.w9544151.Models.GPSTracker;
import uk.ac.tees.w9544151.Models.OrderModel;
import uk.ac.tees.w9544151.R;
import uk.ac.tees.w9544151.databinding.FragmentDBoyHomeBinding;


public class DBoyHomeFragment extends Fragment implements AdapterCallback {
FragmentDBoyHomeBinding binding;
    OrdersAdapter adapter;
    GPSTracker gps=new GPSTracker(getContext());
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
        SharedPreferences sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);
        adapter=new OrdersAdapter(this, getContext(), sp.getString("userType", "error"));
        binding.rvdBoyOrders.setLayoutManager(new LinearLayoutManager(requireContext()));
        getDboyDetails();

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
                        SharedPreferences sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("userType", "");
                        editor.putString("userName", "");
                        editor.putString("userMobile", "");
                        editor.putString("userId", "");
                        editor.commit();
                        Navigation.findNavController(getView()).navigate(R.id.action_DBoyHomeFragment_to_loginFragment);

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
    private void getDboyDetails() {
        SharedPreferences sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("##",sp.getString("userId", "error"));
        db.collection("User").whereEqualTo("userId", sp.getString("userId", "error"))
                // .whereEqualTo("path",pathSelected)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String stop=queryDocumentSnapshots.getDocuments().get(0).getString("stop");
                        binding.dpName.setText(queryDocumentSnapshots.getDocuments().get(0).getString("boyName"));
                        binding.dpId.setText(queryDocumentSnapshots.getDocuments().get(0).getString("userId"));
                        try {

                            Glide.with(getContext())
                                    .load(queryDocumentSnapshots.getDocuments().get(0).getString("boyImage"))
                                    .into(binding.profileImage);
                        }
                        catch (Exception e){

                        }

                        SharedPreferences profile = getContext().getSharedPreferences("profile", Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed=profile.edit();
                        ed.putString("image",queryDocumentSnapshots.getDocuments().get(0).getString("boyImage"));
                        ed.putString("id",queryDocumentSnapshots.getDocuments().get(0).getString("userId"));
                        ed.putString("mobile",queryDocumentSnapshots.getDocuments().get(0).getString("boyMobile"));
                        ed.putString("stop",queryDocumentSnapshots.getDocuments().get(0).getString("stop"));
                        ed.putString("username",queryDocumentSnapshots.getDocuments().get(0).getString("username"));
                        ed.commit();
                        showData(stop);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void showData(String stop) {
        final ProgressDialog progressDoalog = new ProgressDialog(requireContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        //Log.d("@", "showData: Called")

        orderList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Orders").whereEqualTo("stop", stop)
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
                        if (orderList.isEmpty()){
                                    Snackbar.make(requireView(),"No Orders Available",Snackbar.LENGTH_LONG).show();
                        }
                        progressDoalog.dismiss();
                        adapter.ordersList=orderList;
                        binding.rvdBoyOrders.setAdapter(adapter);
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

