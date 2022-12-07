package uk.ac.tees.w9544151.Passenger;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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

import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.w9544151.Adapters.ActionCallback;
import uk.ac.tees.w9544151.Adapters.FoodAdapter;
import uk.ac.tees.w9544151.Models.CartModel;
import uk.ac.tees.w9544151.Models.Foodmodel;
import uk.ac.tees.w9544151.R;
import uk.ac.tees.w9544151.databinding.FragmentFoodHomeBinding;

public class PassengerHome extends Fragment implements ActionCallback {
    FragmentFoodHomeBinding binding;
    ProgressDialog progressDoalog;
    FoodAdapter adapter;
    String total;
    public List<Foodmodel> exampleListFull= new ArrayList();
    private ActionCallback action;
    // public List<Foodmodel> foodList;
    List<Foodmodel> foodList = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFoodHomeBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);
        String name = sp.getString("userName", "Welcome");
        String heading = "Welcome " + name;
        binding.tvHeading.setText(heading);
        getCartCount();
        Log.d("in userhome q", sp.getString("userType", "error"));
        /*for(int i=0;i<10;i++) {
            foodList.add(new Foodmodel("i","Chicken Biriyani", "260", "R.drawable.foodmenu2"));
        }*/
        adapter = new FoodAdapter(this, getContext(), sp.getString("userType", "error"));
        binding.rvFoodMenu.setLayoutManager(new LinearLayoutManager(requireContext()));
        showData();


        binding.userFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_passengerHome_to_feedbackFragments);
            }
        });
        binding.usertrackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_passengerHome_to_orderListFragment);
            }
        });
        binding.userlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(requireContext());
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
                        Navigation.findNavController(getView()).navigate(R.id.action_passengerHome_to_loginFragment);

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
        binding.ivcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_passengerHome_to_cartListFragment);
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(requireContext());
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
                        Navigation.findNavController(getView()).navigate(R.id.action_passengerHome_to_loginFragment);

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


    private void showData() {
        //Log.d("@", "showData: Called")
        progressDoalog = new ProgressDialog(requireContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setCancelable(true);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        foodList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Food_Menu")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("@", queryDocumentSnapshots + "");
                        int i;
                        for (i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
                            Log.d("!", queryDocumentSnapshots.getDocuments().get(i).getId());
                            Log.d("!", queryDocumentSnapshots.getDocuments().get(i).getString("foodName"));
                            Log.d("!", queryDocumentSnapshots.getDocuments().get(i).getString("foodPrice"));
                            foodList.add(new Foodmodel(queryDocumentSnapshots.getDocuments().get(i).getId(), queryDocumentSnapshots.getDocuments().get(i).getString("foodName")
                                    , queryDocumentSnapshots.getDocuments().get(i).getString("foodPrice")
                                    , queryDocumentSnapshots.getDocuments().get(i).getString("foodImage")));
                        }
                        if (foodList.isEmpty()){
                            binding.labelNoData.setVisibility(View.VISIBLE);
                        }
                        else {
                            binding.labelNoData.setVisibility(View.GONE);
                        }
                        adapter.fooodList = foodList;
                        exampleListFull=foodList;
                        adapter.exampleListFull=exampleListFull;
                        binding.rvFoodMenu.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        binding.etSearch.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(s.toString().isEmpty()){
                                    showData();
                                }
                                //after the change calling the method and passing the search input
                                adapter.getFilter().filter(s.toString());

                            }
                        });
                        progressDoalog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void getCartCount() {

        //Log.d("@", "showData: Called")
        SharedPreferences sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);
        String uid = sp.getString("userId", "error");
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Cart").whereEqualTo("userid", uid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("@", queryDocumentSnapshots + "");
                      String count=String.valueOf(queryDocumentSnapshots.getDocuments().size());
                       // Toast.makeText(getContext(), count, Toast.LENGTH_SHORT).show();
                        binding.labelCount.setText(count);
                        }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });


    }
    @Override
    public void onBuyCallback(String s, String foodName, String foodImage, String foodPrice, String type) {
        if (type.equals("cart")) {
            SharedPreferences sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);
            String uid = sp.getString("userId", "error");
            //String uname=sp.getString("userName","error");
            String name = sp.getString("userName", "Welcome");
            // String umobile=sp.getString("userMobile","error");

            total = String.valueOf(Integer.parseInt(s) * Integer.parseInt(foodPrice));
            Log.d("price", "total price" + total);
            //to db
            final ProgressDialog progressDoalog = new ProgressDialog(requireContext());
            progressDoalog.setMessage("Adding to cart....");
            progressDoalog.setTitle("Please wait");
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDoalog.show();
            FirebaseFirestore db;
            db = FirebaseFirestore.getInstance();
            CartModel obj = new CartModel(uid, name, foodImage, foodName, s, foodPrice, total);
            db.collection("Cart").add(obj).
                    addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            progressDoalog.dismiss();
                            Snackbar.make(requireView(), "Item Added to Cart", Snackbar.LENGTH_LONG).show();
                            getCartCount();
                        }
                    }).
                    addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", "onSuccess: Fail");
                            Toast.makeText(requireContext(), "Cart Creation failed", Toast.LENGTH_SHORT).show();

                        }
                    });
           // Toast.makeText(requireContext(), "Qunatity" + total, Toast.LENGTH_SHORT).show();

        } else {
            String amount;
            amount = String.valueOf(Integer.parseInt(s) * Integer.parseInt(foodPrice));
            Bundle b = new Bundle();
            b.putString("itemname", foodName);
            b.putString("itemprice", foodPrice);
            b.putString("itemqty", s);
            b.putString("total", amount);
            b.putString("image", foodImage);
            Navigation.findNavController(getView()).navigate(R.id.action_passengerHome_to_placeOrderFragment, b);
        }
    }


}