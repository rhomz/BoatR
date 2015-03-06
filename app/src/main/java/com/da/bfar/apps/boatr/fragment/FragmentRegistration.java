package com.da.bfar.apps.boatr.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.da.bfar.apps.boatr.BoatRActivity;
import com.da.bfar.apps.boatr.BoatRCustomizeViewpager;
import com.da.bfar.apps.boatr.DAO.MySingleton;
import com.da.bfar.apps.boatr.PagerAdapter;
import com.da.bfar.apps.boatr.R;
import com.da.bfar.apps.boatr.tabFragment.BasicInfoDone;
import com.da.bfar.apps.boatr.tabFragment.BoatRFormEngine;
import com.da.bfar.apps.boatr.tabFragment.BoatRegistrationFragment;
import com.da.bfar.apps.boatr.tabFragment.FishingGearsFragment;
import com.da.bfar.apps.boatr.tabFragment.FishingVesselDimension;
import com.da.bfar.apps.boatr.tabFragment.TabFragmentDone;
import com.da.bfar.apps.boatr.utils.PagerSlidingTabStripNoViewPager;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentRegistration.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentRegistration#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRegistration extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static String TAG = FragmentRegistration.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private BoatRCustomizeViewpager pager;

    PagerSlidingTabStripNoViewPager tabs;
    private int SELECTED_ID=0; //the just click index
    private int CURRENT_ID =0; //the previous index
    private String CURRENT_TITLE="";
    private String SELECTED_TITLE="";

    private boolean ISTAB_PRESSED=false;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Registration.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRegistration newInstance(String param1, String param2) {
        FragmentRegistration fragment = new FragmentRegistration();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentRegistration() {
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
        return inflater.inflate(R.layout.fragment_registration, container, false);
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

    @Override
    public void onResume() {
        MySingleton.getInstance().getFragmentTabBus().register(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        MySingleton.getInstance().getFragmentTabBus().unregister(this);
        super.onPause();
    }

    /*@Override
    public void onDestroy() {
        MySingleton.getInstance().getFragmentTabBus().unregister(this);
        super.onDestroy();
    }
*/
    @Subscribe
    public void receivedFeedback(Bundle bundle){
        Log.e("Fragment Registration Status:", "Fragment");
        if(!bundle.getBoolean("status")){

            Log.e(TAG, "receivedFromPager-" + (bundle.getBoolean("isFromFragment")));
            if(bundle.getBoolean("isFromFragment")){
                Log.e("Fragment Registration Status:", "isFromFragment");
                this.CURRENT_ID = bundle.getInt("fragment"); //initialize the new value of current selected id from button of the fragment next
            }else{
                Log.e("Fragment Registration Status:", "asdf");
                this.CURRENT_ID = SELECTED_ID; //initialize the new value of current selected id from tab pressed
            }

            loadSubFragment(CURRENT_ID,CURRENT_TITLE); //set the title
            tabs.setInvalidate(CURRENT_ID);
//            pager.loadNextFragment(bundle.getInt("fragment"));
        }else{
            //((BoatRActivity) getActivity()).onInvalid(bundle.getString("message").toString());
            //Toast.makeText(getActivity(), "Please input all fields", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        @+id/container
       /* PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) view
                .findViewById(R.id.tabs);

       pager = (BoatRCustomizeViewpager) view.findViewById(R.id.pager);
        pager.setPagingEnabled(false);
        PagerAdapter adapter = new PagerAdapter(getFragmentManager());
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);*/

      /*  "Basic Info", "Dimension & Tonnage", "Propulsion System","Particulars and Classification","Gear Classification","Done*/
        /*Set the tabs title*/
        ArrayList<String> title = new ArrayList<>();
        title.add("Basic Info");
        title.add("Dimension & Tonnage");
        title.add("Propulsion System");
        title.add("Particulars and Classification");
        title.add("Gear Classification");
        title.add("Done");

        tabs = (PagerSlidingTabStripNoViewPager) view.findViewById(R.id.tabs);
        tabs.setTabViewTitle(title,new PagerSlidingTabStripNoViewPager.PagerOnTabClickListener() {

            @Override
            public void onTabClick(int position,String title) {
                usedCaseValidation(position);
            }
        });
        loadSubFragment(0,title.get(0)); //set the default fragment to load
        tabs.setTextColor(Color.parseColor("#FFFFFF"));
        tabs.setIndicatorColor(Color.parseColor("#FFF60120"));
        tabs.setTextSize(View.getDefaultSize(20, 20));


    }
    /*
        *Send verification request to the fragment if verification/trapping is meet
        * */
    private void sendRequestToFragment(int item){
        switch (item){

            case 1:
                MySingleton.getInstance().getBoatRegistrationFragmentBus().post("Boat Registration Bus");
                Log.e(TAG, "index:getBoatRegistrationFragmentBus" + item);
                break;

            case 2:
                MySingleton.getInstance().getFishingVesselDimensionBus().post("Fishing Vessel Dimension Bus");
                Log.e(TAG, "index:getFishingVesselDimensionBus-no");
                break;

            case 3:
                MySingleton.getInstance().getBoatRFormEngineBus().post("Boat Form Engine Bus");
                break;
            case 4:
                MySingleton.getInstance().getFishingGearsFragmentBus().post("Boat Form Engine Bus");
                break;

            case 5:
                MySingleton.getInstance().getBasicInfoDoneBus().post("Basic Info Bus");
                break;
            case 6:
                MySingleton.getInstance().getTabFragmentDoneBus().post("Basic Info Bus");
                break;
        }

    }
    //holds the validation/use test case
    private void usedCaseValidation(int position){

        if(!checkIfBackpress(position,CURRENT_ID)){
            if(checkLimit(position,CURRENT_ID)){
                sendRequestToFragment(position);
            }
        }else{
            loadSubFragment(CURRENT_ID, CURRENT_TITLE);
            tabs.setInvalidate(CURRENT_ID);
        }
    }

    //Check if selected tab is not the next tab
    private boolean checkLimit(int SELECTED_ID,int CURRENT_ID){
        Log.e("Fragment checkLimit :"," --SELECTED_ID-"+SELECTED_ID+ " -- " + "CURRENT_ID-"+CURRENT_ID +"--- RES-"+(SELECTED_ID - CURRENT_ID));
        if((SELECTED_ID - CURRENT_ID) > 1){
            Log.e("Fragment checkLimit :", "not" + "  --SELECTED_ID-"+SELECTED_ID+ " -- " + "CURRENT_ID-"+CURRENT_ID);
            return false;
        }
        this.SELECTED_ID = SELECTED_ID;
        Log.e("Fragment checkLimit :", "ok" + "  --SELECTED_ID-"+SELECTED_ID+ " -- " + "CURRENT_ID-"+CURRENT_ID);
        return true;
    }

    //Check if back pressed/go to previous form
    private boolean checkIfBackpress(int SELECTED_ID,int CURRENT_ID){

        if(SELECTED_ID < CURRENT_ID ){
            this.CURRENT_ID = SELECTED_ID;
            Log.e("Fragment checkIfBackpress :", "yes" + "  --SELECTED_ID-"+SELECTED_ID+ " -- " + "CURRENT_ID-"+CURRENT_ID);
            return true;
        }
//        this.CURRENT_ID = SELECTED_ID;
        Log.e("Fragment checkIfBackpress :not", "SELECTED_ID-"+SELECTED_ID+ " -- " + "CURRENT_ID-"+CURRENT_ID);
        return false;
    }

    /*
    * This method will load the selected fragment on the TAB
    * */
    private void loadSubFragment(int position,String mtitle){
        Fragment fragment = null;

        switch (position){

            case 0:
                fragment = BoatRegistrationFragment.newInstance("", "");
                break;

            case 1:
                fragment = FishingVesselDimension.newInstance("", "");
                break;

            case 2:
                fragment = BoatRFormEngine.newInstance("", "");
                break;

            case 3:
                fragment = FishingGearsFragment.newInstance("", "");
                break;
            case 4:
                fragment = BasicInfoDone.newInstance("", "");
                break;

            case 5:
                fragment = TabFragmentDone.newInstance("", "");
                break;


        }

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_, fragment, "" + mtitle)
                .commit();
    }
}
