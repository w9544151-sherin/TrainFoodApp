package uk.ac.tees.w9544151.admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import uk.ac.tees.w9544151.Adapters.CallBackTwice;
import uk.ac.tees.w9544151.Models.DBoyModel;
import uk.ac.tees.w9544151.Models.Foodmodel;
import uk.ac.tees.w9544151.Models.LoginModel;
import uk.ac.tees.w9544151.Models.Validation;
import uk.ac.tees.w9544151.R;
import uk.ac.tees.w9544151.databinding.FragmentAddBoyBinding;
import uk.ac.tees.w9544151.databinding.FragmentAddFoodBinding;


public class AddBoyFragment extends Fragment implements CallBackTwice {
    String mediaPath = null;
    FirebaseFirestore db;
    int pictureCode = 101;
    int cameracode = 100;
    ProgressDialog progressDoalog;
    Uri filePath=null;
    private CallBackTwice mAdapterCallback;
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
    static String selectedValue = "";
    ByteArrayOutputStream outputStream;
    FragmentAddBoyBinding binding;

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
        binding = FragmentAddBoyBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapterCallback = this;
        Random random = new Random();
        int number = random.nextInt(100000);
        binding.etBoyId.setText("Id: "+number+"");
        binding.cameraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PselectImage();

            }
        });

        //picking stop names from bottomsheet
        binding.etStopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheet = new BottomSheetFragment(requireContext(), mAdapterCallback, "", "allstop");
                bottomSheet.show(getChildFragmentManager(), "BottomSheet");
            }
        });

        binding.btnAddBoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.etUsername.getText().toString().isEmpty()|| binding.etBoyName.getText().toString().isEmpty()||binding.etBoyId.getText().toString().isEmpty()||
                        binding.etBoyMobile.getText().toString().isEmpty()||binding.etPassword.getText().toString().isEmpty()||binding.etStopName.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(),"All fields are mandatory",Toast.LENGTH_SHORT).show();
                }
                else if(encodedImage.equals("")){
                    Toast.makeText(getContext(),"please choose an image",Toast.LENGTH_SHORT).show();
                }
                else if(!binding.etBoyName.getText().toString().matches(Validation.text)){
                    binding.etBoyName.setError("Enter a valid name");
                }
                else if(!binding.etBoyMobile.getText().toString().matches(Validation.mobile)){
                    binding.etBoyMobile.setError("Enter a valid mobile number");
                }
                else {
                    final ProgressDialog progressDoalog = new ProgressDialog(requireContext());
                    progressDoalog.setMessage("Checking....");
                    progressDoalog.setTitle("Please wait");
                    progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDoalog.show();
                    String username;
                    username = binding.etUsername.getText().toString();
                    db = FirebaseFirestore.getInstance();
                    try {

                        db.collection("User").whereEqualTo("username", username).get().
                                addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        if (queryDocumentSnapshots.getDocuments().isEmpty()) {
                                            addBoyToDataBase(number);
                                        } else {
                                            Toast.makeText(requireContext(), "Please Take Another Username", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).
                                addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //userRegistration();
                                        Toast.makeText(requireContext(), "Creation failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } catch (Exception e) {
                        Log.d("exception", "Exception" + e.toString());
                    }
                    progressDoalog.dismiss();
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
            progressDoalog.setCancelable(true);
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
    private void addBoyToDataBase(int number) {
        final ProgressDialog progressDoalog = new ProgressDialog(requireContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        String name,point,mobile,image,username,password;
        name = binding.etBoyName.getText().toString();
        point = binding.etStopName.getText().toString();
        mobile = binding.etBoyMobile.getText().toString();
        username = binding.etUsername.getText().toString();
        password = binding.etPassword.getText().toString();

        image = encodedImage;
        fireStoreDatabase: FirebaseFirestore.getInstance();
        FirebaseFirestore.getInstance();
        DBoyModel obj = new DBoyModel(number+"",name,mobile,point,image,username,password,"dboy");
        DBoyModel obj1 = new DBoyModel(number+"",name,mobile,point,image,username,password,"dboy");
        db = FirebaseFirestore.getInstance();
        db.collection("User").add(obj1).
                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        binding.etBoyName.getText().clear();
                        binding.etBoyMobile.getText().clear();
                        binding.etStopName.getText().clear();
                        binding.etUsername.getText().clear();
                        binding.etPassword.getText().clear();
                        binding.etBoyId.setText("");
                        binding.image.setImageResource(R.drawable.add);
                        encodedImage="";
                        progressDoalog.dismiss();
                        Snackbar.make(requireView(), "DeliveryBoy added Successfully", Snackbar.LENGTH_LONG).show();

                    }
                }).
                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), "Creation failed", Toast.LENGTH_SHORT).show();
                    }
                });
        //add data to login Table
          db.collection("Delivery_Boys").add(obj).
                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "onSuccess: Success");
                        Navigation.findNavController(getView()).navigateUp();
                    }
                }).
                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onSuccess: Fail");
                    }
                });


    }


    @Override
    public void onStopCallback(String routeName) {
        binding.etStopName.setText(routeName);
    }
}