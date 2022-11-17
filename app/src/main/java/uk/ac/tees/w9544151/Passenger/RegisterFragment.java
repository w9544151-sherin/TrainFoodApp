package uk.ac.tees.w9544151.Passenger;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.tees.w9544151.R;
import uk.ac.tees.w9544151.databinding.FragmentRegisterBinding;


public class RegisterFragment extends Fragment {
    FragmentRegisterBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etName.getText().toString().isEmpty())
                    binding.etName.setError("Enter your name");
                else if (binding.etMobile.getText().toString().isEmpty())
                    binding.etMobile.setError("Enter mobile number");
                else if (binding.etEmail.getText().toString().isEmpty())
                    binding.etEmail.setError("Enter your username or password");
                else if (binding.etPassword.getText().toString().isEmpty())
                    binding.etPassword.setError("Enter ypur passwprd");
                else {
                    Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
                }
            }
        });
    }
}