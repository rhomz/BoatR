package com.da.bfar.apps.boatr.tabFragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.da.bfar.apps.boatr.BoatRActivity;
import com.da.bfar.apps.boatr.BoatRegistration;
import com.da.bfar.apps.boatr.BoatrSingleton;
import com.da.bfar.apps.boatr.DAO.MySingleton;
import com.da.bfar.apps.boatr.PagerAdapter;
import com.da.bfar.apps.boatr.R;
import com.da.bfar.apps.boatr.fragment.FragmentRegistration;
import com.squareup.otto.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.da.bfar.apps.boatr.tabFragment.BoatRegistrationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link com.da.bfar.apps.boatr.tabFragment.BoatRegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoatRegistrationFragment extends Fragment {

    private String TAG = BoatRegistrationFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private TextView DateOfApplication;
    private Spinner YearBuilt;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String dateToday = new SimpleDateFormat("MMMM dd, yyyy").format(new Date());
    private String yearToday = new SimpleDateFormat("yyyy").format(new Date());
    private LinearLayout linearLayoutMaterialUsed;
    private String selectedMaterial = "";
    private TextView ownerName, ownerAddress;
    private EditText placeBuilt, homeport, nameOfFishingVessel;
    private RadioGroup VesselType, TypeOfRegistration;
    private RadioButton rbVesselType,rbTypeOfRegistration;
    private Button btnNext,btnCancel;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public boolean true_false = true;
    String warning="";


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BoatRegistrationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BoatRegistrationFragment newInstance(String param1, String param2) {
        BoatRegistrationFragment fragment = new BoatRegistrationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BoatRegistrationFragment() {
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
        return inflater.inflate(R.layout.fragment_boat_registration, container, false);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Init(view);
        DateOfApplication.setText(dateToday.toString());
        populateSpinner(YearBuilt, 1990, Integer.parseInt(yearToday));

        ownerName.setText(BoatrSingleton.getInstance().getFisherFolkName());
        ownerAddress.setText(BoatrSingleton.getInstance().getFisherFolkAddress());
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (selectedMaterial.length() > 4) {
                    selectedMaterial = selectedMaterial.substring(0, selectedMaterial.lastIndexOf(", "));
                }*/
                Validate();

            }
        });
        addCheckBox(linearLayoutMaterialUsed);

        if(!getBoat().getBoatNameOfVessel().toString().equals("null")){
            setData();
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearBoatrSingleTon();
                ((BoatRActivity)getActivity()).onNavigationDrawerItemSelected(1);
            }
        });

        /*Init();*/
//        Log.e("TAG",""+linearLayoutMaterialUsed.getChildCount());
        /*Toast.makeText(getActivity(),linearLayoutMaterialUsed.getChildCount(),Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
    public void clearBoatrSingleTon(){
        getBoat().setFisherFolkName(null);
        getBoat().setBoatNameOfVessel("null");
        getBoat().setBoatTonLength("null");
        getBoat().setBoatMaterialUsed("null");
        getBoat().setBoatFishingGear("null");
        getBoat().setBoatEngineMake("null");
    }
    public void setData(){
        DateOfApplication.setText(getBoat().getBoatDate());
        nameOfFishingVessel.setText(getBoat().getBoatNameOfVessel());
        homeport.setText(getBoat().getBoatHomeport());
        placeBuilt.setText(getBoat().getBoatPlaceBuilt());
        selectedMaterial = BoatrSingleton.getInstance().getBoatMaterialUsed();
        ((RadioButton)TypeOfRegistration.getChildAt(((BoatRActivity)getActivity()).getTypeOfRegistrationIndex())).setChecked(true);
        ((RadioButton)VesselType.getChildAt(((BoatRActivity)getActivity()).getVesselTypeIndex())).setChecked(true);
        YearBuilt.setSelection(((ArrayAdapter)YearBuilt.getAdapter()).getPosition(BoatrSingleton.getInstance().getBoatYearBuilt()));

    }



    private  void Validate(){
        warning="";
        if(TypeOfRegistration.getCheckedRadioButtonId() ==-1){
            warning+="Type of Registration \n";
        }
        if (ownerName.getText().toString().matches("")){
            warning+="Owner Name \n";
        }
        if(ownerAddress.getText().toString().matches("")){
            warning+="Owner Address \n";
        }

        if(homeport.getText().toString().matches("")){
            warning+="Homeport \n";
        }

        if(nameOfFishingVessel.getText().toString().matches("")){
            warning+="Name of Fishing Vessel \n";
        }
        if(placeBuilt.getText().toString().matches("")){
            warning+="Place Built \n";
        }
        if(VesselType.getCheckedRadioButtonId() ==-1){
            warning+="Vessel Type \n";
        }

        if(selectedMaterial.isEmpty())
        {
            warning+="Material Used \n";

        }
        PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager());
        pagerAdapter.Warning(warning,getActivity());
        if (warning.matches("")){
            rbTypeOfRegistration = (RadioButton)getActivity().findViewById(TypeOfRegistration.getCheckedRadioButtonId());
            rbVesselType = (RadioButton)getActivity().findViewById(VesselType.getCheckedRadioButtonId());
            getBoat().setBoatDate(DateOfApplication.getText().toString());
            getBoat().setBoatTypeOfRegistration(rbTypeOfRegistration.getText().toString());
            getBoat().setBoatHomeport(homeport.getText().toString());
            getBoat().setBoatNameOfVessel(nameOfFishingVessel.getText().toString());
            getBoat().setBoatPlaceBuilt(placeBuilt.getText().toString());
            getBoat().setBoatVesselType(rbVesselType.getText().toString());
            getBoat().setBoatMaterialUsed(selectedMaterial);
            getBoat().setBoatYearBuilt(YearBuilt.getSelectedItem().toString());
            true_false = false;
            Bundle temp = new Bundle();
            temp.putBoolean("status", true_false);
            temp.putString("message",warning);
            temp.putInt("fragment", 1);
            temp.putBoolean("isFromFragment",true);

            MySingleton.getInstance().getFragmentTabBus().post(temp);

        }

        else {
            ((BoatRActivity) getActivity()).onInvalid(warning);
            true_false = true;
        }

    }

    public boolean getValidOrInvalid(){
        return this.true_false;
    }
    private BoatrSingleton getBoat(){
        return BoatrSingleton.getInstance();
    }
    private void Init(View view) {
        /*DateOfApplication = (TextView)getActivity().findViewById(R.id.txtDateOfApplication);
        YearBuilt = (Spinner)getActivity().findViewById(R.id.spinner_yearBuilt);
        linearLayoutMaterialUsed = (LinearLayout)getActivity().findViewById(R.id.cbMaterialUsed);
        btnNext = (Button)getActivity().findViewById(R.id.btn_boat_registration_next);
        ownerName = (TextView)getActivity().findViewById(R.id.editText_NameOfOwner);
        ownerAddress = (TextView)getActivity().findViewById(R.id.txtOwnerAddress);
        nameOfFishingVessel = (EditText)getActivity().findViewById(R.id.editText_NameFV);
        placeBuilt = (EditText)getActivity().findViewById(R.id.editText_PlaceBuilt);
        TypeOfRegistration = (RadioGroup)getActivity().findViewById(R.id.rgTypeOfRegistration);
        VesselType = (RadioGroup)getActivity().findViewById(R.id.rgVesselType);
        rbVesselType = (RadioButton)getActivity().findViewById(VesselType.getCheckedRadioButtonId());
        homeport = (EditText)getActivity().findViewById(R.id.editText_Homeport);
        btnCancel = (Button)getActivity().findViewById(R.id.btnCancel);*/
        DateOfApplication = (TextView)view.findViewById(R.id.txtDateOfApplication);
        YearBuilt = (Spinner)view.findViewById(R.id.spinner_yearBuilt);
        linearLayoutMaterialUsed = (LinearLayout)view.findViewById(R.id.cbMaterialUsed);
        btnNext = (Button)view.findViewById(R.id.btn_boat_registration_next);
        ownerName = (TextView)view.findViewById(R.id.editText_NameOfOwner);
        ownerAddress = (TextView)view.findViewById(R.id.txtOwnerAddress);
        nameOfFishingVessel = (EditText)view.findViewById(R.id.editText_NameFV);
        placeBuilt = (EditText)view.findViewById(R.id.editText_PlaceBuilt);
        TypeOfRegistration = (RadioGroup)view.findViewById(R.id.rgTypeOfRegistration);
        VesselType = (RadioGroup)view.findViewById(R.id.rgVesselType);
        rbVesselType = (RadioButton)view.findViewById(VesselType.getCheckedRadioButtonId());
        homeport = (EditText)view.findViewById(R.id.editText_Homeport);
        btnCancel = (Button)view.findViewById(R.id.btnCancel);

    }
    private void populateSpinner(Spinner spinner, int start, int end){
        ArrayList<String> array = new ArrayList<String>();
        for (int i=start; i <= end; i++){
            array.add(i + "");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item,array);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onResume() {
        MySingleton.getInstance().getBoatRegistrationFragmentBus().register(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        MySingleton.getInstance().getBoatRegistrationFragmentBus().unregister(this);
        super.onPause();
    }
    @Subscribe
    public void receivedFromPager(String from_pager){
        Log.e(TAG, "receivedFromPager" + from_pager);

        Validate();

        Bundle temp = new Bundle();
        temp.putBoolean("status", isEmptyFields());
        temp.putString("message",warning);
        temp.putInt("fragment", 1);


        MySingleton.getInstance().getFragmentTabBus().post(temp);
    }

    private boolean isEmptyFields(){
        if(true_false==true){
            return true;
        }
        /*if(true_false ==true){
            return true;
        }*/
        return false;
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


    private void addCheckBox(LinearLayout container){
        container.addView(createCheckbox("Wood"));
        container.addView(createCheckbox("Fiber Glass"));
        container.addView(createCheckbox("Composite"));
    }

    private CheckBox createCheckbox(String title){
        CheckBox temp = new CheckBox(getActivity());
        temp.setText(title);
        if (BoatrSingleton.getInstance().getBoatMaterialUsed() != null) {
            if (BoatrSingleton.getInstance().getBoatMaterialUsed().contains(title)){
                temp.setChecked(true);
            }
        }
        temp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedMaterial += ((CheckBox) buttonView).getText().toString() +", ";
                }
                else{

                    selectedMaterial = selectedMaterial.replaceAll(((CheckBox) buttonView).getText().toString() + ", ","");

                }
            }
        });
        return temp;
    }
}
