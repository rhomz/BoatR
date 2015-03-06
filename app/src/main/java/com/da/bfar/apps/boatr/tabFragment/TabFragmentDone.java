package com.da.bfar.apps.boatr.tabFragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.da.bfar.apps.boatr.AndroidMultiPartEntity;
import com.da.bfar.apps.boatr.BoatRActivity;
import com.da.bfar.apps.boatr.BoatRegistration;
import com.da.bfar.apps.boatr.BoatrSingleton;
import com.da.bfar.apps.boatr.DAO.MySingleton;
import com.da.bfar.apps.boatr.GlobalAsyncTask;
import com.da.bfar.apps.boatr.R;
import com.da.bfar.apps.boatr.SignatureActivity;
import com.da.bfar.apps.boatr.StatusSingleton;
import com.da.bfar.apps.boatr.fragment.FragmentHome;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.squareup.otto.Subscribe;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.lang.System.*;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabFragmentDone.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabFragmentDone#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabFragmentDone extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private boolean isPicture = false,isSignature = false;

    private String mParam1;
    private String mParam2;

    private SignaturePad mSignaturePad;
    private Button mClearButton;
    private Button mSaveButton;

    private ProgressBar progressBar;
    private Button btnTakePic;
    private Button btnTakeSign;
    private Button btnUpload;
    private ImageView imgPreview,imgSignaturePreview;
    private TextView tvPath;
    private TextView txtPercentage;
    private Uri fileUri;
    private String signPath;
    private String imageName;
    private String signName;

    public static final int MEDIA_TYPE_IMAGE = 1;

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_SIGNATURE_REQUEST_CODE = 200;

    private static String TAG = TabFragmentDone.class.getSimpleName();

    private static String IMAGE_DIRECTORY_NAME = "Android File Upload";
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabFragmentDone.
     */
    // TODO: Rename and change types and number of parameters
    public static TabFragmentDone newInstance(String param1, String param2) {
        TabFragmentDone fragment = new TabFragmentDone();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TabFragmentDone() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.tab_fragment_done, container, false);


        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);



        btnTakePic = (Button)view.findViewById(R.id.btnTakePicture);
        btnTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage("file_name");
            }
        });
        btnTakeSign = (Button)view.findViewById(R.id.btnTakeSignature);
        btnTakeSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SignatureActivity.class);
                startActivityForResult(intent,CAMERA_CAPTURE_SIGNATURE_REQUEST_CODE);
//                startActivity(intent);
            }
        });


        btnUpload = (Button)view.findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoatRegistration boatRegistration = new BoatRegistration();
               //  new UploadFileToServer().execute(fileUri.getPath());
              //  new UploadFileToServer().execute(signPath);

                File img = new File(fileUri.getPath());
                File sign = new File(signPath);
                 String imgName = img.getAbsolutePath().substring(img.getAbsolutePath().lastIndexOf("/")+1);
                String signName = sign.getAbsolutePath().substring(sign.getAbsolutePath().lastIndexOf("/")+1);

                new GlobalAsyncTask(boatRegistration,getActivity(),0,"null",BoatrSingleton.getInstance().getBoatDate(),BoatrSingleton.getInstance().getBoatTypeOfRegistration(),BoatrSingleton.getInstance().getBoatCFRNO(),
                        BoatrSingleton.getInstance().getBoatHomeport(),BoatrSingleton.getInstance().getBoatNameOfVessel(),BoatrSingleton.getInstance().getBoatVesselType(),BoatrSingleton.getInstance().getBoatPlaceBuilt(),
                        BoatrSingleton.getInstance().getBoatYearBuilt(),BoatrSingleton.getInstance().getBoatMaterialUsed(),BoatrSingleton.getInstance().getBoatRegLength(),BoatrSingleton.getInstance().getBoatTonLength(),
                        BoatrSingleton.getInstance().getBoatRegBreadth(),BoatrSingleton.getInstance().getBoatTonBreadth(),BoatrSingleton.getInstance().getBoatRegDepth(),BoatrSingleton.getInstance().getBoatTonDepth(),
                        BoatrSingleton.getInstance().getBoatGrossTonnage(),BoatrSingleton.getInstance().getBoatNetTonnage(),BoatrSingleton.getInstance().getBoatEngineMake(),BoatrSingleton.getInstance().getBoatSerialNumber(),
                        BoatrSingleton.getInstance().getBoatHorsepower(),BoatrSingleton.getInstance().getBoatFishingGear(),BoatrSingleton.getInstance().getBoatMunicipalCode(),BoatrSingleton.getInstance().getBoatProvinceCode(),
                        BoatrSingleton.getInstance().getBoatRegionCode(),BoatrSingleton.getInstance().getBoatRegistered_By(),BoatrSingleton.getInstance().getBoatLastUpdated_By(),BoatrSingleton.getInstance().getBoatDateUpdated(),
                        BoatrSingleton.getInstance().getBoatEngineMake2(),BoatrSingleton.getInstance().getBoatSerialNumber2(),BoatrSingleton.getInstance().getBoatHorsepower2(),imgName,signName).execute().getStatus();

                BoatRegistrationFragment boatRegistrationFragment = new BoatRegistrationFragment();
                boatRegistrationFragment.clearBoatrSingleTon();

                Log.e(TAG,fileUri.getPath().toString()+signPath.toString());
                Toast.makeText(getActivity(),"upload",Toast.LENGTH_SHORT).show();



                Intent intent = new Intent(getActivity(),BoatRActivity.class);
                startActivity(intent);




               // startActivityForResult(intent,CAMERA_CAPTURE_SIGNATURE_REQUEST_CODE);
            }
        });

        String dataStatus = StatusSingleton.getInstance().getDataStatus();
        if(dataStatus == "OK"){

            Log.e(TAG,"****************************OK"+"***************************************************************************");
        }
        btnUpload.setEnabled(false);

        imgPreview = (ImageView)view.findViewById(R.id.imgResult);
        imgSignaturePreview = (ImageView)view.findViewById(R.id.signResult);

        tvPath = (TextView)view.findViewById(R.id.tvPath);
        txtPercentage = (TextView)view.findViewById(R.id.txtPercentage);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        MySingleton.getInstance().getTabFragmentDoneBus().register(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        MySingleton.getInstance().getTabFragmentDoneBus().unregister(this);
        super.onPause();
    }
    @Subscribe
    public void receivedFromPager(String from_pager){
        Log.e("boat registration Fragment", "receivedFromPager" + from_pager);
        Bundle temp = new Bundle();
        temp.putBoolean("status", false);
        temp.putInt("fragment", 6);
        MySingleton.getInstance().getFragmentTabBus().post(temp);
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    /*public boolean addSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
            File photo = new File(getAlbumStorageDir("Bfar_Signature"), String.format("bfar_%d.jpg", System.currentTimeMillis()));
            saveBitmapToJPG(signature, photo);
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(photo);
            mediaScanIntent.setData(contentUri);
            getActivity().sendBroadcast(mediaScanIntent);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }*/

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("kiko", "Directory not created");
        }
        return file;
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

    private void captureImage(String fileName) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(fileName);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    public Uri getOutputMediaFileUri(String fileName) {
        return Uri.fromFile(getOutputMediaFile(fileName));
    }

    private static File getOutputMediaFile(String fileName) {

        // External sdcard location
        /*File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),IMAGE_DIRECTORY_NAME);
*/
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }



        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());


        Log.e(TAG,"mediaStorageDir:"+ mediaStorageDir.getPath());

        File mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + timeStamp + ".jpg");


        return mediaFile;
    }

    private void previewImage(){
        imgPreview.setVisibility(View.VISIBLE);
        // bimatp factory
        BitmapFactory.Options options = new BitmapFactory.Options();

        // down sizing image as it throws OutOfMemory Exception for larger
        // images
        options.inSampleSize = 8;

        Log.e(TAG,"previewImage:"+fileUri.getPath());
        final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);

        tvPath.setText(fileUri.getPath());
        imgPreview.setImageBitmap(bitmap);
    }

    private void previewSignatureImage(String path){
        //imgSignaturePreview.setVisibility(View.VISIBLE);
        // bimatp factory
        BitmapFactory.Options options = new BitmapFactory.Options();

        // down sizing image as it throws OutOfMemory Exception for larger
        // images
        options.inSampleSize = 8;

//        Log.e(TAG,"previewImage:"+fileUri.getPath());
        final Bitmap bitmap = BitmapFactory.decodeFile(path, options);

//        tvPath.setText(fileUri.getPath());
        imgSignaturePreview.setImageBitmap(bitmap);

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                isPicture = true;
                previewImage();
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getActivity(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getActivity(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }else if (requestCode == CAMERA_CAPTURE_SIGNATURE_REQUEST_CODE){
//                Log.e(TAG,data.getStringExtra("test"));
                isSignature = true;
                previewSignatureImage(data.getStringExtra("path"));
            signPath = data.getStringExtra("path");
        }else{
            //If not camera capture
        }

        isImageAndSignature(isPicture,isSignature);
    }
    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     *//*
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
        previewImage();
    }
    *//**
     * Uploading the file to server
     * */
    private class UploadFileToServer extends AsyncTask<String, Integer, String> {

        private String FILE_UPLOAD_URL = "http://192.168.1.124:82/api/fileUpload.php";

        private String filePath = null;

        long totalSize = 0;

        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            progressBar.setProgress(0);
            super.onPreExecute();

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            progressBar.setProgress(progress[0]);

            // updating percentage value
            txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(String... params) {
            return uploadFile(params[0]);
        }

        @SuppressWarnings("deprecation")
        private String uploadFile(String path) {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(FILE_UPLOAD_URL);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                File sourceFile = new File(path);

                // Adding file data to http body
                entity.addPart("image", new FileBody(sourceFile));


                try {  // Extra parameters if you want to pass to server
                    entity.addPart("website",
                            new StringBody("www.androidhive.info"));

                    entity.addPart("email", new StringBody("abc@gmail.com"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(TAG, "Response from server: " + result);

            // showing the server response in an alert dialog
            showAlert(result);

            super.onPostExecute(result);
        }

    }



    public void  showAlert(String result){
        Log.e(TAG, "Response from server: " + result);
    }

    private void isImageAndSignature(boolean ispicture,boolean issignature){
        if(ispicture && issignature){
            btnUpload.setEnabled(true);
        }else {
            btnUpload.setEnabled(false);
        }
    }

    /*public void letsGo(Context context){
        Intent intent = new Intent(context,BoatRActivity.class);
        startActivity(intent);
    }*/


}
