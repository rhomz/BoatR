package com.da.bfar.apps.boatr.tabFragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.da.bfar.apps.boatr.BoatRActivity;
import com.da.bfar.apps.boatr.BoatrSingleton;
import com.da.bfar.apps.boatr.DAO.MySingleton;
import com.da.bfar.apps.boatr.R;
import com.squareup.otto.Subscribe;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.da.bfar.apps.boatr.tabFragment.BoatRFormEngine.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BoatRFormEngine#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoatRFormEngine extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btnAddnew,btnNext;
    private Spinner spinnerEngine2,spinnerHorsePower2,spinnerEngine,spinnerHorsePower;
    private TextView tvEngine2,tvserial2,tvHorsePower2,tvCancel;
    private EditText etSerial2,etSerial;
    private LinearLayout layout2;
    private Context context;

    private  String warning="";
    private  boolean true_false=true;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BoatRFormEngine.
     */
    // TODO: Rename and change types and number of parameters
    public static BoatRFormEngine newInstance(String param1, String param2) {
        BoatRFormEngine fragment = new BoatRFormEngine();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BoatRFormEngine() {
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
        //return inflater.inflate(R.layout.fragment_boat_rform_engine, container, false);


        final View view = inflater.inflate(R.layout.fragment_boat_rform_engine, null);

        btnAddnew = (Button)view.findViewById(R.id.btnAddNew);
        tvEngine2 = (TextView)view.findViewById(R.id.tvEngine2);
        tvHorsePower2 = (TextView)view.findViewById(R.id.tvHorsePower2);
        etSerial2 = (EditText)view.findViewById(R.id.etSerial2);
        etSerial = (EditText)view.findViewById(R.id.etSerial);
        spinnerEngine2 = (Spinner)view.findViewById(R.id.spinnerEngine2);
        spinnerHorsePower2 = (Spinner)view.findViewById(R.id.spinnerHorsePower2);
        spinnerEngine = (Spinner)view.findViewById(R.id.spinnerEngine);
        spinnerHorsePower = (Spinner)view.findViewById(R.id.spinnerHorsePower);
        tvCancel = (TextView)view.findViewById(R.id.tvCancel);
        btnNext = (Button)view.findViewById(R.id.btnNext);

        //Log.e("First Step","*************");
        HorsePower();
        engine();
        AddNewEngine();
        btnNext();
        btnCancel();
        if(!BoatrSingleton.getInstance().getBoatEngineMake().toString().equals("null")){
            setData();
        }

        return view;
    }
    public void setData(){
        spinnerEngine.setSelection(((ArrayAdapter)spinnerEngine.getAdapter()).getPosition(BoatrSingleton.getInstance().getBoatEngineMake()));
        spinnerEngine2.setSelection(((ArrayAdapter)spinnerEngine2.getAdapter()).getPosition(BoatrSingleton.getInstance().getBoatEngineMake2()));
        spinnerHorsePower.setSelection(((ArrayAdapter)spinnerHorsePower.getAdapter()).getPosition(BoatrSingleton.getInstance().getBoatHorsepower()));
        spinnerHorsePower2.setSelection(((ArrayAdapter)spinnerHorsePower2.getAdapter()).getPosition(BoatrSingleton.getInstance().getBoatHorsepower2()));
        etSerial.setText(getBoat().getBoatSerialNumber());
        etSerial2.setText(getBoat().getBoatSerialNumber2());
    }

    /*private void Validate(){

        warning ="";

        if (etSerial.getText().toString().matches("")){
            warning+="Serial Number \n";
        }
        if (etSerial2.getVisibility()==View.VISIBLE){
            if(etSerial2.getText().toString().matches("")){
                warning+= "Serial Number 2\n";
            }
        }
        if(warning.matches("")){
            getBoat().setBoatEngineMake(spinnerEngine.getSelectedItem().toString());
            getBoat().setBoatEngineMake2(spinnerEngine2.getSelectedItem().toString());
            getBoat().setBoatSerialNumber(etSerial.getText().toString());
            getBoat().setBoatSerialNumber2(etSerial2.getText().toString());
            getBoat().setBoatHorsepower(spinnerHorsePower.getSelectedItem().toString());
            getBoat().setBoatHorsepower2(spinnerHorsePower2.getSelectedItem().toString());
            Toast.makeText(getActivity(), "SUCCESS",Toast.LENGTH_SHORT).show();
            true_false=false;
        }
        else
            ((BoatRActivity) getActivity()).onInvalid(warning);
        true_false=true;

    }*/

    private void Validate(){

        warning ="";
        if (etSerial.getText().toString().matches("")){
            warning+="Serial Number \n";
        }
        if (etSerial2.getVisibility()==View.VISIBLE){
            if(etSerial2.getText().toString().matches("")){
                warning+= "Serial Number 2\n";
            }
        }
        if(warning.matches("")){
            getBoat().setBoatEngineMake(spinnerEngine.getSelectedItem().toString());
            getBoat().setBoatEngineMake2(spinnerEngine2.getSelectedItem().toString());
            getBoat().setBoatSerialNumber(etSerial.getText().toString());
            getBoat().setBoatSerialNumber2(etSerial2.getText().toString());
            getBoat().setBoatHorsepower(spinnerHorsePower.getSelectedItem().toString());
            getBoat().setBoatHorsepower2(spinnerHorsePower2.getSelectedItem().toString());
            Toast.makeText(getActivity(), "SUCCESS",Toast.LENGTH_SHORT).show();
            true_false=false;
            Bundle temp = new Bundle();
            temp.putBoolean("status", true_false);
            temp.putString("message",warning);
            temp.putInt("fragment", 3);
            temp.putBoolean("isFromFragment",true);
            MySingleton.getInstance().getFragmentTabBus().post(temp);
        }
        else
            ((BoatRActivity) getActivity()).onInvalid(warning);
true_false=true;

    }
    public BoatrSingleton getBoat(){
        return BoatrSingleton.getInstance();
    }
    private void HorsePower(){
        String[] hp = {"4.5","5","5.5","6","6.5","7","7.5","8","8.5","9","9.5","10","10.5","11","11.5","12","12.5","13","13.5","14","14.5","15","15.5","16","16.5","17","17.5","18"};
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, hp);
        spinnerHorsePower.setAdapter(adapter);
        spinnerHorsePower2.setAdapter(adapter);
    }
    private void engine(){
        String[] list= {"Kawasaki","Izusu"};
        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
        spinnerEngine.setAdapter(adapter);
        spinnerEngine2.setAdapter(adapter);
    }
    private void btnNext(){
       btnNext.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                Validate();
              /* final String Serial1 = etSerial.getText().toString();
               final String Serial2 = etSerial2.getText().toString();
               if(Serial1.length()==0){
                   etSerial.requestFocus();
                   etSerial.setError("FIELD CANNOT BE EMPTY");
               }
               else if(Serial2.length()==0){
                   etSerial2.requestFocus();
                   etSerial2.setError("FIELD CANNOT BE EMPTY");

               }*/
           }
       });
    }
    private void AddNewEngine(){
        btnAddnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "TOAST",Toast.LENGTH_SHORT).show();
                tvEngine2.setVisibility(getView().VISIBLE);
                tvHorsePower2.setVisibility(getView().VISIBLE);
                etSerial2.setVisibility(getView().VISIBLE);
                spinnerEngine2.setVisibility(getView().VISIBLE);
                spinnerHorsePower2.setVisibility(getView().VISIBLE);
                tvCancel.setVisibility(getView().VISIBLE);

            }
        });
    }
    private void btnCancel(){
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvEngine2.setVisibility(getView().GONE);
                tvHorsePower2.setVisibility(getView().GONE);
                etSerial2.setVisibility(getView().GONE);
                spinnerEngine2.setVisibility(getView().GONE);
                spinnerHorsePower2.setVisibility(getView().GONE);
                tvCancel.setVisibility(getView().GONE);
            }
        });
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
        MySingleton.getInstance().getBoatRFormEngineBus().register(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        MySingleton.getInstance().getBoatRFormEngineBus().unregister(this);
        super.onPause();
    }

    @Subscribe
    public void receivedFromPager(String from_pager){
        Log.e("boat registration Fragment", "receivedFromPager" + from_pager);
        Bundle temp = new Bundle();
        temp.putBoolean("status", isEmptyFields());
        temp.putString("message",warning);
        temp.putInt("fragment", 3);
        MySingleton.getInstance().getFragmentTabBus().post(temp);
    }
    private boolean isEmptyFields(){
        if(etSerial.getText().toString().length() == 0){
            return  true;
        }

        /*if(true_false == true){
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

}
