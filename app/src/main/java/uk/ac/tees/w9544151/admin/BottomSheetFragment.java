package uk.ac.tees.w9544151.admin;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.w9544151.Adapters.AdapterCallback;
import uk.ac.tees.w9544151.Adapters.CallBackTwice;
import uk.ac.tees.w9544151.Adapters.DataAdapter;
import uk.ac.tees.w9544151.Models.DataModel;
import uk.ac.tees.w9544151.databinding.FragmentBottomSheetBinding;

public class BottomSheetFragment extends BottomSheetDialogFragment implements AdapterCallback, CallBackTwice {
    FragmentBottomSheetBinding binding;
    DataAdapter adapter;
    List<DataModel> routeList = new ArrayList();
    List<DataModel> stopList = new ArrayList();
    private CallBackTwice mAdapterCallback;
    String trainNumber, sheetType;
    Context ctx;

    public BottomSheetFragment(Context context, CallBackTwice callback, String s, String stop) {
        this.mAdapterCallback = callback;
        this.trainNumber = s;
        this.ctx = context;
        this.sheetType = stop;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBottomSheetBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.rvData.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new DataAdapter(this);
        Log.d("@", "showData: Called");
        if (sheetType.equals("stop")) {
            showStop();
        } else if(sheetType.equals("allstop")) {
            showAllStop();
        }
        else {
            showData();
        }
    }


    private void showData() {
        //Log.d("@", "showData: Called")

        routeList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Train").whereEqualTo("trainNumber", trainNumber)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.getDocuments().size() > 0) {
                            Log.d("@", queryDocumentSnapshots.getDocuments().size() + "");
                            int i;
                            for (i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
                                routeList.add(new DataModel(
                                        queryDocumentSnapshots.getDocuments().get(i).getString("path")

                                ));
                                Log.d("@", queryDocumentSnapshots.getDocuments().get(i).getString("path") + "");
                            }
                            adapter.routeList = routeList;
                            binding.rvData.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    dismiss();
                                    //Snackbar.make(requireView(), "Invalid Train Number", Snackbar.LENGTH_SHORT).show();
                                }
                            }, 600);
                            Snackbar.make(requireView(), "Invalid Train Number", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("@q", e + "");
                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void showStop() {
        //Log.d("@", "showData: Called")

        routeList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Stop").whereEqualTo("trainNumber", trainNumber)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.getDocuments().size() > 0) {
                            Log.d("@", queryDocumentSnapshots.getDocuments().size() + "");
                            int i;
                            for (i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
                                routeList.add(new DataModel(
                                        queryDocumentSnapshots.getDocuments().get(i).getString("stopName")

                                ));
                               // Log.d("@", queryDocumentSnapshots.getDocuments().get(i).getString("path") + "");
                            }
                            adapter.routeList = routeList;
                            binding.rvData.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else {
                            Snackbar.make(requireView(), "Invalid Train Number", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("@q", e + "");
                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void showAllStop() {
        //Log.d("@", "showData: Called")

        routeList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Stop")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.getDocuments().size() > 0) {
                            Log.d("@", queryDocumentSnapshots.getDocuments().size() + "");
                            int i;
                            for (i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
                                routeList.add(new DataModel(
                                        queryDocumentSnapshots.getDocuments().get(i).getString("stopName")

                                ));
                                // Log.d("@", queryDocumentSnapshots.getDocuments().get(i).getString("path") + "");
                            }
                            adapter.routeList = routeList;
                            binding.rvData.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "Unable to find stops", Toast.LENGTH_SHORT).show();
                            //Snackbar.make(requireView(), "Invalid Train Number", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("@q", e + "");
                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onMethodCallback() {

    }

    @Override
    public void onStopCallback(String routeName) {
        AddStopFragment.selectedValue = routeName;
        mAdapterCallback.onStopCallback(routeName);
        dismiss();
        //  Toast.makeText(requireActivity(),"click",Toast.LENGTH_LONG).show();
    }
}