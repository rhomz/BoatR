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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.da.bfar.apps.boatr.BoatrSingleton;
import com.da.bfar.apps.boatr.DAO.MySingleton;
import com.da.bfar.apps.boatr.R;
import com.squareup.otto.Subscribe;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.da.bfar.apps.boatr.tabFragment.BasicInfoDone.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link com.da.bfar.apps.boatr.tabFragment.BasicInfoDone#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BasicInfoDone extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
private Button done;

    private TextView
            mfvrNo, typeOfRegistration, dateOfApplication,
            nameOfOwner, address, homeport, nameOfFishingVessel,
            placeBuilt, yearBuilt, vesselType, materialUsed,
            engineMake, engineMake2, serialNumber, serialnumber2,
            horsePower, horsePower2,fishingGears,tonnageLength,
            tonnageBreadth, tonnageDepth, registeredLength, registeredBreadth,
            registeredDepth, grossTonnage, netTonnage;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BasicInfoDone.
     */
    // TODO: Rename and change types and number of parameters
    public static BasicInfoDone newInstance(String param1, String param2) {
        BasicInfoDone fragment = new BasicInfoDone();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BasicInfoDone() {
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
        View view = inflater.inflate(R.layout.fragment_basic_info_done, container, false);
        done  = (Button)view.findViewById(R.id.btnNext);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle temp = new Bundle();
                temp.putBoolean("status", false);
                temp.putInt("fragment", 5);
                MySingleton.getInstance().getFragmentTabBus().post(temp);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*
        * May-error nullpointer dito
        * */
      //try{

//           setData();
       //}
       /*catch (Exception e){

       }*/
        Toast.makeText(getActivity(), BoatrSingleton.getInstance().getBoatTonBreadth().toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Init(view);
    }

    public void Init(View view){
        /*mfvrNo = (TextView)getActivity().findViewById(R.id.txtview_mfvrno);
        typeOfRegistration = (TextView)getActivity().findViewById(R.id.txtview_typeofregistration);
        dateOfApplication = (TextView)getActivity().findViewById(R.id.txtview_date);
        nameOfOwner = (TextView)getActivity().findViewById(R.id.txtview_nameofowner);
        address = (TextView)getActivity().findViewById(R.id.txtview_address);
        homeport = (TextView)getActivity().findViewById(R.id.txtview_homeport);
        nameOfFishingVessel = (TextView)getActivity().findViewById(R.id.txtview_fishingvessel);
        placeBuilt = (TextView)getActivity().findViewById(R.id.txtview_placebuilt);
        yearBuilt = (TextView)getActivity().findViewById(R.id.txtview_yearbuilt);
        vesselType = (TextView)getActivity().findViewById(R.id.txtview_vesseltype);
        materialUsed = (TextView)getActivity().findViewById(R.id.txtview_materialused);
        engineMake = (TextView)getActivity().findViewById(R.id.txtview_enginemake);
        serialNumber = (TextView)getActivity().findViewById(R.id.txtview_serialnumber);
        horsePower = (TextView)getActivity().findViewById(R.id.txtview_horsepower);
        fishingGears = (TextView)getActivity().findViewById(R.id.txtview_classification);
        tonnageLength = (TextView)getActivity().findViewById(R.id.txtview_tlength);
        tonnageBreadth = (TextView)getActivity().findViewById(R.id.txtview_tbreadth);
        tonnageDepth = (TextView)getActivity().findViewById(R.id.txtview_tdepth);
        registeredLength = (TextView)getActivity().findViewById(R.id.txtview_rlength);
        registeredBreadth = (TextView)getActivity().findViewById(R.id.txtview_rbreadth);
        registeredDepth = (TextView)getActivity().findViewById(R.id.txtview_rdepth);
        grossTonnage = (TextView)getActivity().findViewById(R.id.txtview_gtonnage);
        netTonnage = (TextView)getActivity().findViewById(R.id.txtview_ntonnage);*/

        mfvrNo = (TextView)view.findViewById(R.id.txtview_mfvrno);
        typeOfRegistration = (TextView)view.findViewById(R.id.txtview_typeofregistration);
        dateOfApplication = (TextView)view.findViewById(R.id.txtview_date);
        nameOfOwner = (TextView)view.findViewById(R.id.txtview_nameofowner);
        address = (TextView)view.findViewById(R.id.txtview_address);
        homeport = (TextView)view.findViewById(R.id.txtview_homeport);
        nameOfFishingVessel = (TextView)view.findViewById(R.id.txtview_fishingvessel);
        placeBuilt = (TextView)view.findViewById(R.id.txtview_placebuilt);
        yearBuilt = (TextView)view.findViewById(R.id.txtview_yearbuilt);
        vesselType = (TextView)view.findViewById(R.id.txtview_vesseltype);
        materialUsed = (TextView)view.findViewById(R.id.txtview_materialused);
        engineMake = (TextView)view.findViewById(R.id.txtview_enginemake);
        serialNumber = (TextView)view.findViewById(R.id.txtview_serialnumber);
        horsePower = (TextView)view.findViewById(R.id.txtview_horsepower);
        fishingGears = (TextView)view.findViewById(R.id.txtview_classification);
        tonnageLength = (TextView)view.findViewById(R.id.txtview_tlength);
        tonnageBreadth = (TextView)view.findViewById(R.id.txtview_tbreadth);
        tonnageDepth = (TextView)view.findViewById(R.id.txtview_tdepth);
        registeredLength = (TextView)view.findViewById(R.id.txtview_rlength);
        registeredBreadth = (TextView)view.findViewById(R.id.txtview_rbreadth);
        registeredDepth = (TextView)view.findViewById(R.id.txtview_rdepth);
        grossTonnage = (TextView)view.findViewById(R.id.txtview_gtonnage);
        netTonnage = (TextView)view.findViewById(R.id.txtview_ntonnage);

        mfvrNo.setText("WEHEHEH :)");
    }
    public void setData(){
        typeOfRegistration.setText(BoatrSingleton.getInstance().getBoatTypeOfRegistration());
        dateOfApplication.setText(BoatrSingleton.getInstance().getBoatDate());
        nameOfOwner.setText(BoatrSingleton.getInstance().getFisherFolkName());
        address.setText(BoatrSingleton.getInstance().getFisherFolkAddress());
        homeport.setText(BoatrSingleton.getInstance().getBoatHomeport());
        nameOfFishingVessel.setText(BoatrSingleton.getInstance().getBoatNameOfVessel());
        placeBuilt.setText(BoatrSingleton.getInstance().getBoatPlaceBuilt());
        yearBuilt.setText(BoatrSingleton.getInstance().getBoatYearBuilt());
        vesselType.setText(BoatrSingleton.getInstance().getBoatVesselType());
        materialUsed.setText(BoatrSingleton.getInstance().getBoatMaterialUsed().substring(0, BoatrSingleton.getInstance().getBoatMaterialUsed().length() -2));//remove the comma on last item
        engineMake.setText(BoatrSingleton.getInstance().getBoatEngineMake());
        serialNumber.setText(BoatrSingleton.getInstance().getBoatSerialNumber());
        horsePower.setText(BoatrSingleton.getInstance().getBoatHorsepower());
        fishingGears.setText(BoatrSingleton.getInstance().getBoatFishingGear().substring(0, BoatrSingleton.getInstance().getBoatFishingGear().length() -2));//remove the comma on last item
        grossTonnage.setText(BoatrSingleton.getInstance().getBoatGrossTonnage());
        netTonnage.setText(BoatrSingleton.getInstance().getBoatNetTonnage());
        tonnageLength.setText(BoatrSingleton.getInstance().getBoatTonLength());
        tonnageDepth.setText(BoatrSingleton.getInstance().getBoatTonDepth());
        tonnageBreadth.setText(BoatrSingleton.getInstance().getBoatTonBreadth());
        registeredLength.setText(BoatrSingleton.getInstance().getBoatRegLength());
        registeredDepth.setText(BoatrSingleton.getInstance().getBoatRegDepth());
        registeredBreadth.setText(BoatrSingleton.getInstance().getBoatTonBreadth());
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
        MySingleton.getInstance().getBasicInfoDoneBus().register(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        MySingleton.getInstance().getBasicInfoDoneBus().unregister(this);
        super.onPause();
    }
    @Subscribe
    public void receivedFromPager(String from_pager){
        Log.e("boat registration Fragment", "receivedFromPager" + from_pager);
        Bundle temp = new Bundle();
        temp.putBoolean("status", false);
        temp.putInt("fragment", 5);
        temp.putBoolean("isFromFragment",true);
        MySingleton.getInstance().getFragmentTabBus().post(temp);
    }

   /* private boolean isEmptyFields(){
        if(homeport.getText().length() ==0){
            return true;
        }
        return false;
    }*/

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

}
