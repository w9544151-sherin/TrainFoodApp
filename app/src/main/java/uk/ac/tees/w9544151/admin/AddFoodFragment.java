package uk.ac.tees.w9544151.admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.internal.StorageReferenceUri;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.UUID;

import uk.ac.tees.w9544151.Models.Foodmodel;
import uk.ac.tees.w9544151.Models.TrainModel;
import uk.ac.tees.w9544151.Models.Validation;
import uk.ac.tees.w9544151.R;
import uk.ac.tees.w9544151.databinding.FragmentAddFoodBinding;


public class AddFoodFragment extends Fragment {
    String mediaPath = null;
    FirebaseFirestore db;

    String encodedImage = "";
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Bitmap bitmapProfile = null;
    private static int RESULT_LOAD_IMAGE = 1;
    private String userChosenTask;
    String x;
    int pictureCode = 101;
    int cameracode = 100;
    ProgressDialog progressDoalog;
    Uri filePath=null;

    StorageReference storageReference=null;
    int newWidth = 500;
    String gen;
    int newHeight = 500;

    Matrix matrix;

    Bitmap resizedBitmap;

    private int mYear, mMonth, mDay, mHour, mMinute;
    float scaleWidth;

    float scaleHeight;

    ByteArrayOutputStream outputStream;

    FragmentAddFoodBinding binding;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddFoodBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.cameraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //selectImage();
                PselectImage();

            }
        });

        binding.btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //keyboard hiding
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                if (binding.etFoodName.getText().toString().isEmpty() || !binding.etFoodName.getText().toString().matches(Validation.text)) {
                    binding.etFoodName.setError("Enter a food name");
                } else if (binding.etFoodPrice.getText().toString().isEmpty()) {
                    binding.etFoodPrice.setError("enter the price amount");
                }
                else if(encodedImage.equals("")){
                    Toast.makeText(getContext(),"please choose an image",Toast.LENGTH_SHORT).show();
                }
                else {
                    addFoodToDatabase();
                }

            }
        });

    }
    private void PselectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        //final CharSequence[] items = {"Take Photo", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Upload your documents");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                // boolean result = Utility.checkPermission(Register.this);
                if (items[item].equals("Take Photo")) {
                    userChosenTask = "Take Photo";
                    // if (result)
                    // cameraIntent();
                    selectCamera();
                } else if (items[item].equals("Choose from Library")) {
                    userChosenTask = "Choose from Library";
                    // if (result)
                    // galleryIntent();
                    selectImage();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                    Log.d("dialog dismiss ", "true");
                }
            }
        });
        builder.show();
    }
   private void selectImage(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Pic"),pictureCode);
    }
    private void selectCamera(){
        Intent intent=new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,cameracode);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pictureCode && resultCode == Activity.RESULT_OK) {

            if(data==null ||data.getData().equals(null)){
                return;
            }
            filePath = data.getData();
            try {
                Bitmap bitmap=MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),filePath);
                binding.image.setImageBitmap(bitmap);
                uploadImage();
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if(requestCode == cameracode && resultCode == Activity.RESULT_OK){
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            try {
                binding.image.setImageBitmap(thumbnail);
                filePath=  getImageUri(getContext(),thumbnail);

                uploadImage();
            }
            catch (Exception e){

            }
        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void uploadImage(){
        Log.d("## filepath: inside", filePath+"");
        if(filePath != null){
            progressDoalog = new ProgressDialog(requireContext());
            progressDoalog.setMessage("Uploading....");
            progressDoalog.setTitle("Please wait");
            progressDoalog.setCancelable(false);
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDoalog.show();
            Log.d("## filepath:", filePath+"");
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
             storageReference = storageReference.child("food_Images/" + UUID.randomUUID().toString());
            UploadTask uploadTask=storageReference.putFile(filePath);
           // encodedImage= storageReference.toString();
            Log.d("## encodedImage:", encodedImage+"");
           // Toast.makeText(requireContext(), encodedImage, Toast.LENGTH_SHORT).show();
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            String uploadedImageUrl = task.getResult().toString();
                            Log.d("##", uploadedImageUrl);
                            encodedImage=uploadedImageUrl;
                            progressDoalog.dismiss();
                        }
                    });
                }
            });

        }else{
            Toast.makeText(requireContext(), "Please Upload an Image", Toast.LENGTH_SHORT).show();

        }
    }

    private void addFoodToDatabase() {

        progressDoalog = new ProgressDialog(requireContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        String id, foodName, price;
        foodName = binding.etFoodName.getText().toString();
        price = binding.etFoodPrice.getText().toString();
        fireStoreDatabase:
        FirebaseFirestore.getInstance();
        Foodmodel obj = new Foodmodel("f0", foodName, price, encodedImage);
        db = FirebaseFirestore.getInstance();
        db.collection("Food_Menu").add(obj).
                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        binding.etFoodPrice.getText().clear();
                        binding.etFoodName.getText().clear();
                        binding.image.setImageResource(R.drawable.healthy_drink);
                        progressDoalog.dismiss();
                        Snackbar.make(requireView(), "Food added Successfully", Snackbar.LENGTH_LONG).show();
                    }
                }).
                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), "Creation failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}