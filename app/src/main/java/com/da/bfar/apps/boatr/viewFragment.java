package com.da.bfar.apps.boatr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.da.bfar.apps.boatr.DAO.BoatRegistrationDBConnection;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.da.bfar.apps.boatr.viewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link viewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class viewFragment extends Fragment {
    private ListView listView;
    private TextView tvSearch;
    private Button btnSearch;
    private EditText etSearch;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private long start = 0;
    private long end = 8;
    private ListViewAdapter list;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment viewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static viewFragment newInstance(String param1, String param2) {
        viewFragment fragment = new viewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public viewFragment() {
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
        return inflater.inflate(R.layout.fragment_view, container, false);

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
   /*  searchFisherFolkData(etSearch);*/
        if (BoatrSingleton.getInstance().getFisherFolkName() == null){
            getFisherFolkData();
            etSearch = (EditText)getActivity().findViewById(R.id.etSearch);
            btnSearch = (Button)getActivity().findViewById(R.id.btnSearch);
            btnSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(etSearch.getText().toString().matches("")){
                        getFisherFolkData();
                    }
                    else
                        searchFisherFolkData(etSearch.getText().toString());
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    list = (ListViewAdapter) parent.getAdapter();
                    list.getSelectedRow((position));
                    goToRegistration();
                }
            });

        }
        else {
           goToRegistration();
        }
      /*  FisherFolk fisherFolk = new FisherFolk();
        new GlobalAsyncTask(fisherFolk,getActivity()).execute();*/

    }
    private void goToRegistration(){
        ((BoatRActivity)getActivity()).onNavigationDrawerItemSelected(6);

    }
    public void searchFisherFolkData(String id){
        BoatRegistrationDBConnection ormliteDbhelper = new BoatRegistrationDBConnection(getActivity());
        try {
            /*ArrayList<Fisherfolk> fisherFolkArrayList =(ArrayList<Fisherfolk>) ormliteDbhelper.getDaoFisherFolk().queryForEq("fname",id);*/
            QueryBuilder<FisherFolk, String> qb = ormliteDbhelper.getDaoFisherFolk().queryBuilder().limit(end);
            qb.where().like("Fullname","%"+id+"%").or().like("CFRNO","%"+id+"%").or().like("Address","%"+id+"%")
                    .query();
            PreparedQuery<FisherFolk> pq =  qb.prepare();
            ArrayList<FisherFolk> fisherFolkArrayList =(ArrayList<FisherFolk>) ormliteDbhelper.getDaoFisherFolk().query(pq);
            ListViewAdapter listViewAdapter = new ListViewAdapter(getActivity(),fisherFolkArrayList,new ListviewCallback() {
                @Override
                public void onDelete() {
                }
            });
            listView=(ListView)getActivity().findViewById(R.id.listView);
            listView.setAdapter(listViewAdapter);
            listViewAdapter.notifyDataSetChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void getFisherFolkData(){
        BoatRegistrationDBConnection ormliteDbhelper = new BoatRegistrationDBConnection(getActivity());
        try {
            QueryBuilder<FisherFolk, String> qb = ormliteDbhelper.getDaoFisherFolk().queryBuilder();
            qb.query();
            PreparedQuery<FisherFolk> pq =  qb.prepare();
            ArrayList<FisherFolk> fisherFolkArrayList =(ArrayList<FisherFolk>) ormliteDbhelper.getDaoFisherFolk().query(pq);
            ListViewAdapter listViewAdapter = new ListViewAdapter(getActivity(),fisherFolkArrayList,new ListviewCallback() {
                @Override
                public void onDelete() {
                }
            });
            listView=(ListView)getActivity().findViewById(R.id.listView);
            listView.setAdapter(listViewAdapter);
            listViewAdapter.notifyDataSetChanged();

        } catch (SQLException e) {
            e.printStackTrace();
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




}
