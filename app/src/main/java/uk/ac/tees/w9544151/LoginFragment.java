package uk.ac.tees.w9544151;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.tees.w9544151.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
                    if (binding.etUsername.getText().toString().equals("admin"))
                        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_adminHomeFragment);
                    else if (binding.etUsername.getText().toString().equals("passenger"))
                        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_foodHomeFragment2);
                    else if (binding.etUsername.getText().toString().equals("dboy"))
                        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_DBoyHomeFragment);


                    progressDoalog.dismiss();
                    binding.etUsername.setText("");
                    binding.etPassword.setText("");
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