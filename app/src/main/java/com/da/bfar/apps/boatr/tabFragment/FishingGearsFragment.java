package com.da.bfar.apps.boatr.tabFragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.da.bfar.apps.boatr.BoatRegistration;
import com.da.bfar.apps.boatr.BoatrSingleton;
import com.da.bfar.apps.boatr.DAO.MySingleton;
import com.da.bfar.apps.boatr.R;
import com.squareup.otto.Subscribe;


/**
 * A simple {@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.da.bfar.apps.boatr.tabFragment.FishingGearsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FishingGearsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FishingGearsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btnNext;
    private LinearLayout checklist1;
    private LinearLayout checklist2;
    private String selectedGear="";
    private String warning="";

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FishingGearsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FishingGearsFragment newInstance(String param1, String param2) {
        FishingGearsFragment fragment = new FishingGearsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FishingGearsFragment() {
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
        return inflater.inflate(R.layout.fragment_fishing_gears, container, false);
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
        MySingleton.getInstance().getFishingGearsFragmentBus().register(this);
        super.onResume();
    }

    @Override
    public void onPause() {
       MySingleton.getInstance().getFishingGearsFragmentBus().unregister(this);
        super.onPause();
    }

    @Subscribe
    public void receivedFromPager(String from_pager){
        Log.e("boat registration Fragment", "receivedFromPager" + from_pager);
        Bundle temp = new Bundle();
        temp.putBoolean("status", false);
        temp.putString("message",warning);
        temp.putInt("fragment", 4);
        MySingleton.getInstance().getFragmentTabBus().post(temp);
    }
/*    private boolean isEmptyFields(){
        if(true_false == true){
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


    public void Init(){
        btnNext = (Button)getActivity().findViewById(R.id.btnNext_fishingGear);
        checklist1 = (LinearLayout)getActivity().findViewById(R.id.checklist1);
        checklist2 = (LinearLayout)getActivity().findViewById(R.id.checklist2);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Init();
        addCheckBox1(checklist1);
        addCheckBox2(checklist2);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoatrSingleton.getInstance().setBoatFishingGear(selectedGear);
                Bundle temp = new Bundle();
                temp.putBoolean("status", false);
                temp.putString("message","");
                temp.putInt("fragment", 4);
                temp.putBoolean("isFromFragment",true);
                MySingleton.getInstance().getFragmentTabBus().post(temp);
                Toast.makeText(getActivity(), selectedGear, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addCheckBox1(LinearLayout container){
        container.addView(createCheckbox("New Look or Zapra"));
        container.addView(createCheckbox("Barrier New (Likus)"));
        container.addView(createCheckbox("Bottom Set Gill Net"));
        container.addView(createCheckbox("Bottom Set Long Line"));
        container.addView(createCheckbox("Cash Net"));
        container.addView(createCheckbox("Crab Lift Net"));
        container.addView(createCheckbox("Crab Pots"));
        container.addView(createCheckbox("Drift Gill Net"));
        container.addView(createCheckbox("Drift Long Line"));
        container.addView(createCheckbox("Encircling Gill Net"));
        container.addView(createCheckbox("Fish Coral(Baclad)"));
        container.addView(createCheckbox("Set Net(Lambaklad)"));
        container.addView(createCheckbox("Shrimp Lift Net"));
        container.addView(createCheckbox("Simple-Handline"));
        container.addView(createCheckbox("Squid Pots"));
    }

    private void addCheckBox2(LinearLayout container){
        container.addView(createCheckbox("Fish Lift Nets"));
        container.addView(createCheckbox("Fry Dozer or Gatherer"));
        container.addView(createCheckbox("Fyke Nets/Filter Nets"));
        container.addView(createCheckbox("Gaff Hook"));
        container.addView(createCheckbox("Jig"));
        container.addView(createCheckbox("Lever Net"));
        container.addView(createCheckbox("Mash Push Net"));
        container.addView(createCheckbox("Multiple-Handline"));
        container.addView(createCheckbox("Spear"));
        container.addView(createCheckbox("Octopus/Squid Device"));
        container.addView(createCheckbox("Scoop Net"));
        container.addView(createCheckbox("Surface Set Gill Net"));
        container.addView(createCheckbox("Trammel Net"));
        container.addView(createCheckbox("Troll Net"));
    }

    private CheckBox createCheckbox(final String title) {
        Log.e("", "created");
        CheckBox temp = new CheckBox(getActivity());
        temp.setText(title);
        if (BoatrSingleton.getInstance().getBoatFishingGear() != null) {
            if (BoatrSingleton.getInstance().getBoatFishingGear().contains(title)){
                temp.setChecked(true);
                selectedGear= BoatrSingleton.getInstance().getBoatFishingGear();
            }
        }
        temp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedGear += title +", ";

                }
                else{
                    selectedGear = selectedGear.replace(title + ", ", "");

                }
            }
        });
        return temp;
    }

}
