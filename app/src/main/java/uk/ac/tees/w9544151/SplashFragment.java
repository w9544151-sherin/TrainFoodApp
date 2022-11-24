package uk.ac.tees.w9544151;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.tees.w9544151.databinding.FragmentSplashBinding;

public class SplashFragment extends Fragment {
FragmentSplashBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding=FragmentSplashBinding.inflate(getLayoutInflater(),container,false);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         //Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_homeFragment);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_homeFragment);
                //the current activity will get finished.
            }
        },3000);
    }
}