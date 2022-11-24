package uk.ac.tees.w9544151;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import uk.ac.tees.w9544151.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment {
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    FragmentLoginBinding binding;
    private EditText emailTextView, passwordTextView, nameTextView, mobileTextView;
    private AppCompatTextView Btn;
    //private ProgressBar progressbar;
    // private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        binding = FragmentLoginBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });


        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.etUsername.getText().toString().isEmpty()) {
                    binding.etUsername.setError("Please Enter Username");
                } else if (binding.etPassword.getText().toString().isEmpty()) {
                    binding.etUsername.setError("Enter Password");
                } else {


                    final ProgressDialog progressDoalog = new ProgressDialog(requireContext());
                    progressDoalog.setMessage("Checking....");
                    progressDoalog.setTitle("Please wait");
                    progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDoalog.show();
                    String username = binding.etUsername.getText().toString();
                    String password = binding.etPassword.getText().toString();
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    if (username.equals("admin") && password.equals("admin")) {
                        sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);
                        editor = sp.edit();
                        editor.putString("userType", "admin");
                        editor.commit();
                        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_adminHomeFragment);
                        progressDoalog.dismiss();
                    } else {
                        try {
                            db.collection("User").whereEqualTo("username", username).whereEqualTo("password", password)
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                           // Log.d("##", queryDocumentSnapshots.getDocuments().get(0).getString("type") + "");
                                            try {
                                                if (queryDocumentSnapshots.getDocuments().size()==0) {

                                                    Toast.makeText(requireContext(), "invalid  credentials", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);
                                                    editor = sp.edit();
                                                    Log.d("##", queryDocumentSnapshots.getDocuments().get(0).getString("name") + "");
                                                    editor.putString("userType", queryDocumentSnapshots.getDocuments().get(0).getString("type"));
                                                    editor.putString("userName", queryDocumentSnapshots.getDocuments().get(0).getString("username"));
                                                    editor.putString("userMobile", queryDocumentSnapshots.getDocuments().get(0).getString("mobile"));
                                                    editor.putString("userId", queryDocumentSnapshots.getDocuments().get(0).getString("userId"));
                                                    editor.commit();
                                                    Log.d("##", queryDocumentSnapshots.getDocuments().get(0).getString("username"));
                                                    String type = queryDocumentSnapshots.getDocuments().get(0).getString("type");
                                                    Log.d("##", type);
                                                    switch (type) {
                                                        case "dboy":
                                                            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_DBoyHomeFragment);
                                                            progressDoalog.dismiss();
                                                            break;
                                                        case "user":
                                                            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_passengerHome);
                                                            progressDoalog.dismiss();
                                                            break;
                                                        default:
                                                            Toast.makeText(requireContext(), "Invalid Data", Toast.LENGTH_SHORT).show();
                                                    }


                                                }
                                                binding.etUsername.setText("");
                                                binding.etPassword.setText("");
                                                progressDoalog.dismiss();
                                            } catch (Exception e) {
                                                progressDoalog.dismiss();
                                                Log.d("exception: ", e.toString());
                                            }


                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDoalog.dismiss();

                                            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } catch (Exception e) {
                            Log.d("exception: ", e.toString());
                        }

                    }

                }

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getView()).navigateUp();
            }
        });
    }
}


/**
 * MD5: 61:55:AE:63:CD:D1:34:1E:C2:C3:17:6C:84:2D:D3:D9
 * SHA1: DB:EB:79:F4:BB:86:51:DA:AE:01:A6:50:B9:5F:E5:2C:50:19:79:BF
 * SHA-256: E2:9F:45:9C:B8:CF:86:A4:1B:38:34:23:32:82:B5:5F:D4:02:E7:97:96:66:5F:CB:B2:0D:57:D3:90:90:3C:7C
 */