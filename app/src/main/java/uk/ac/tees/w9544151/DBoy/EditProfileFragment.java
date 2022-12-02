package uk.ac.tees.w9544151.DBoy;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;

import uk.ac.tees.w9544151.R;
import uk.ac.tees.w9544151.databinding.FragmentEditProfileBinding;


public class EditProfileFragment extends Fragment {
    FragmentEditProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentEditProfileBinding.inflate(getLayoutInflater(),container,false);
        return binding.getRoot();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback( this,new OnBackPressedCallback(true){
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getView()).navigateUp();
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences profile = getContext().getSharedPreferences("profile", Context.MODE_PRIVATE);

        try {

            Glide.with(getContext())
                    .load(profile.getString("image","error"))
                    .into(binding.image);
        }
        catch (Exception e){

        }
        binding.tvHeadingName.setText(profile.getString("username","error"));
        binding.tvDBoyId.setText("ID - "+profile.getString("id","error"));
        binding.tvDBoyPoint.setText("STOP - "+profile.getString("stop","error"));
        binding.tvDBoyMob.setText("MOBILE - "+profile.getString("mobile","error"));

        binding.btnAddBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigateUp();
            }
        });
    }
}