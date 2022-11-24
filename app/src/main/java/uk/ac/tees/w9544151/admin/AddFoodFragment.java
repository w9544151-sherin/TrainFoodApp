package uk.ac.tees.w9544151.admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import uk.ac.tees.w9544151.Models.Foodmodel;
import uk.ac.tees.w9544151.Models.TrainModel;
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
                selectImage();

            }
        });

        binding.btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    addFoodToDatabase();
                }

            }
        });

    }

    private void selectImage() {
        //final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        final CharSequence[] items = {"Take Photo", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Upload your documents");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                // boolean result = Utility.checkPermission(Register.this);
                if (items[item].equals("Take Photo")) {
                    userChosenTask = "Take Photo";
                    // if (result)
                    cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChosenTask = "Choose from Library";
                    // if (result)
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                    Log.d("dialog dismiss ", "true");
                }
            }
        });
        builder.show();
    }


    private void galleryIntent() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)

                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }


    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), data.getData());
                int nh = (int) (bm.getHeight() * (512.0 / bm.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bm, 102, nh, true);
                reZize(bm);
                bitmapProfile = bm;
                if (bitmapProfile != null) {
                    getStringImage(bitmapProfile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    void reZize(Bitmap bp) {
        int width = bp.getWidth();
        int height = bp.getHeight();
        Matrix matrix = new Matrix();
        scaleWidth = ((float) newWidth) / width;
        scaleHeight = ((float) newHeight) / height;
        matrix.postScale(scaleWidth, scaleHeight);
        resizedBitmap = Bitmap.createBitmap(bp, 0, 0, width, height, matrix, true);
        outputStream = new ByteArrayOutputStream();
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        if (resizedBitmap != null) {
            getStringImage(resizedBitmap);
        }
    }

    public void getStringImage(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();

        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        binding.image.setImageBitmap(bmp);
        Toast.makeText(getContext(), encodedImage + "", Toast.LENGTH_SHORT).show();
        //return encodedImage;
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        //Toast.makeText(getContext(), "" + destination, Toast.LENGTH_SHORT).show();
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        bitmapProfile = thumbnail;
        if (bitmapProfile != null) {
            getStringImage(bitmapProfile);
        }


    }

    private void addFoodToDatabase() {
        String id, foodName, price;
        foodName = binding.etFoodName.getText().toString();
        price = binding.etFoodPrice.getText().toString();
        fireStoreDatabase:
        FirebaseFirestore.getInstance();
        Foodmodel obj = new Foodmodel("f", foodName, price, encodedImage);
        db = FirebaseFirestore.getInstance();
        db.collection("Food_Menu").add(obj).
                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        binding.etFoodPrice.getText().clear();
                        binding.etFoodName.getText().clear();
                        binding.image.setImageResource(R.drawable.healthy_drink);
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

    private boolean validate(){
        if (binding.etFoodName.getText().equals("")|| binding.etFoodPrice.getText().equals(""))
        {
            Toast.makeText(requireActivity(),"All fields are mandatory",Toast.LENGTH_LONG);
            return false;
        }
        else {
            return true;
        }
    }

    /*private void uploadImage(){
        if(encodedImage != null){
            StorageReference reference;
            reference=storageReference.child("Food_Image/"+UUID.randomUUID().toString());
            //ref = storageReference.child("food_Images/" + UUID.randomUUID().toString());
            UploadTask uploadTask;
            uploadTask = reference.putFile(Uri.parse(encodedImage));
            Log.d("url", reference.toString());
            //encodedstring= ref.toString()

        }else{
            Toast.makeText(getContext(), "Please Upload an Image", Toast.LENGTH_SHORT).show();
        }
    }*/
}