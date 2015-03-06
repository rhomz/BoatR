package com.da.bfar.apps.boatr.tabFragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.da.bfar.apps.boatr.BoatRActivity;
import com.da.bfar.apps.boatr.BoatrSingleton;
import com.da.bfar.apps.boatr.DAO.MySingleton;
import com.da.bfar.apps.boatr.R;
import com.squareup.otto.Subscribe;

import org.apache.commons.net.telnet.TelnetOption;
import org.w3c.dom.Text;

import java.text.DecimalFormat;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.da.bfar.apps.boatr.tabFragment.FishingVesselDimension.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FishingVesselDimension#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FishingVesselDimension extends Fragment {
    private EditText EDITTEXT_tonnageLength,EDITTEXT_TonnageDepth,EDITTEXT_TonnageBreadth,EDITTEXT_l0,EDITTEXT_l1,EDITTEXT_l2, RegLength,RegDepth,RegBreadth;
    private ImageView imgSpoonPlumb, imgRakeStem,imgRakeStem_TransformStem,imgSkiff,img_formula_rakestem,img_formula_transom,img_formula_transom101;
    private TextView TEXTVIEW_GrossTonnage_sysGen,TEXTVIEW_formulaGrossTonnage,over,two,TEXTVIEW_lblTonnageDepth,TEXTVIEW_lblTonnageBreadth,gt,TEXTVIEW_chooseShape,TEXTVIEW_netTonnage,nt,l0,l1,l2,Title_RegDepth,Title_RegBreadth,Title_RegLength;
    private Button NEXT_btnFVDimension, btnCalculate, btnEnter;
    private ScrollView scrollViewVertical;

    private double tl,tb,td,result,net,len0,len1,len2,lc;
    private String TAG = FishingVesselDimension.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private  String warning="";
    private boolean true_false=true;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InitializeComponents();
        //Enter Length of Boat
        EDITTEXT_tonnageLength.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                InitializeComponents();
                return false;
            }
        });
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validate()) {
                    BoatLength();
                }
            }
        });
        NEXT_btnFVDimension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((Validate())){
                    validateGT();
                }
            }
        });

        if(!BoatrSingleton.getInstance().getBoatTonLength().toString().equals("null")){
            setData();
        }

    }
    public void setData(){
        EDITTEXT_tonnageLength.setText(BoatrSingleton.getInstance().getBoatTonLength());
        EDITTEXT_TonnageDepth.setText(BoatrSingleton.getInstance().getBoatTonDepth());
        EDITTEXT_TonnageBreadth.setText(BoatrSingleton.getInstance().getBoatTonBreadth());
        RegLength.setText(BoatrSingleton.getInstance().getBoatRegLength());
        RegDepth.setText(BoatrSingleton.getInstance().getBoatRegDepth());
        RegBreadth.setText(BoatrSingleton.getInstance().getBoatTonBreadth());
        TEXTVIEW_GrossTonnage_sysGen.setText(BoatrSingleton.getInstance().getBoatGrossTonnage());
        TEXTVIEW_netTonnage.setText(BoatrSingleton.getInstance().getBoatNetTonnage());
        btnEnter.callOnClick();
    }

    public void InitializeComponents()
    {
        EDITTEXT_tonnageLength=(EditText)getActivity().findViewById(R.id.EDITTEXT_tonnageLength);
        EDITTEXT_TonnageDepth=(EditText)getActivity().findViewById(R.id.EDITTEXT_TonnageDepth);
        EDITTEXT_TonnageBreadth=(EditText)getActivity().findViewById(R.id.EDITTEXT_TonnageBreadth);
        EDITTEXT_l0=(EditText)getActivity().findViewById(R.id.EDITTEXT_l0);
        EDITTEXT_l1=(EditText)getActivity().findViewById(R.id.EDITTEXT_l1);
        EDITTEXT_l2=(EditText)getActivity().findViewById(R.id.EDITTEXT_l2);
        imgSpoonPlumb=(ImageView)getActivity().findViewById(R.id.imgSpoonPlumb);
        imgRakeStem=(ImageView)getActivity().findViewById(R.id.imgRakeStem);
        imgRakeStem_TransformStem=(ImageView)getActivity().findViewById(R.id.imgRakeStem_TransformStem);
        imgSkiff=(ImageView)getActivity().findViewById(R.id.imgSkiff);
        img_formula_rakestem=(ImageView)getActivity().findViewById(R.id.img_formula_rakestem);
        img_formula_transom=(ImageView)getActivity().findViewById(R.id.img_formula_transom);
        img_formula_transom101=(ImageView)getActivity().findViewById(R.id.img_formula_transom101);
        TEXTVIEW_GrossTonnage_sysGen=(TextView)getActivity().findViewById(R.id.TEXTVIEW_GrossTonnage_sysGen);
        TEXTVIEW_formulaGrossTonnage=(TextView)getActivity().findViewById(R.id.TEXTVIEW_formulaGrossTonnage);
        TEXTVIEW_lblTonnageDepth=(TextView)getActivity().findViewById(R.id.TEXTVIEW_lblTonnageDepth);
        TEXTVIEW_lblTonnageBreadth=(TextView)getActivity().findViewById(R.id.TEXTVIEW_lblTonnageBreadth);
        TEXTVIEW_chooseShape=(TextView)getActivity().findViewById(R.id.TEXTVIEW_chooseShape);
        TEXTVIEW_netTonnage=(TextView)getActivity().findViewById(R.id.TEXTVIEW_netTonnage);
        Title_RegDepth=(TextView)getActivity().findViewById(R.id.Title_RegDepth);
        Title_RegBreadth=(TextView)getActivity().findViewById(R.id.Title_RegBreath);
        Title_RegLength=(TextView)getActivity().findViewById(R.id.Title_RegLength);
        over=(TextView)getActivity().findViewById(R.id.over);
        two=(TextView)getActivity().findViewById(R.id.two);
        gt=(TextView)getActivity().findViewById(R.id.gt);
        nt=(TextView)getActivity().findViewById(R.id.nt);
        l0=(TextView)getActivity().findViewById(R.id.l0);
        l1=(TextView)getActivity().findViewById(R.id.l1);
        l2=(TextView)getActivity().findViewById(R.id.l2);
        NEXT_btnFVDimension=(Button)getActivity().findViewById(R.id.NEXT_btnFVDimension);
        btnCalculate=(Button)getActivity().findViewById(R.id.btnCalculate);
        btnEnter=(Button)getActivity().findViewById(R.id.btnEnter);
        scrollViewVertical=(ScrollView)getActivity().findViewById(R.id.scrollViewVertical);
        scrollViewVertical.setVerticalScrollBarEnabled(false);
        scrollViewVertical.setHorizontalScrollBarEnabled(false);
        RegLength = (EditText)getActivity().findViewById(R.id.txtRegLength);
        RegBreadth = (EditText)getActivity().findViewById(R.id.txtRegBreadth);
        RegDepth = (EditText)getActivity().findViewById(R.id.txtRegDepth);
        imgSpoonPlumb.setVisibility(View.GONE);
        imgRakeStem.setVisibility(View.GONE);
        imgRakeStem_TransformStem.setVisibility(View.GONE);
        imgSkiff.setVisibility(View.GONE);
        img_formula_rakestem.setVisibility(View.GONE);
        img_formula_transom.setVisibility(View.GONE);
        img_formula_transom101.setVisibility(View.GONE);
        TEXTVIEW_formulaGrossTonnage.setVisibility(View.GONE);
        over.setVisibility(View.GONE);
        two.setVisibility(View.GONE);
        TEXTVIEW_lblTonnageDepth.setVisibility(View.GONE);
        TEXTVIEW_lblTonnageBreadth.setVisibility(View.GONE);
        TEXTVIEW_GrossTonnage_sysGen.setVisibility(View.GONE);
        nt.setVisibility(View.GONE);
        gt.setVisibility(View.GONE);
        l0.setVisibility(View.GONE);
        l1.setVisibility(View.GONE);
        l2.setVisibility(View.GONE);
        NEXT_btnFVDimension.setVisibility(View.GONE);
        EDITTEXT_TonnageBreadth.setVisibility(View.GONE);
        EDITTEXT_TonnageDepth.setVisibility(View.GONE);
        EDITTEXT_l0.setVisibility(View.GONE);
        EDITTEXT_l1.setVisibility(View.GONE);
        EDITTEXT_l2.setVisibility(View.GONE);
        TEXTVIEW_chooseShape.setVisibility(View.GONE);
        btnCalculate.setVisibility(View.GONE);
        TEXTVIEW_netTonnage.setVisibility(View.GONE);
        RegLength.setVisibility(View.GONE);
        RegDepth.setVisibility(View.GONE);
        RegBreadth.setVisibility(View.GONE);
        Title_RegDepth.setVisibility(View.GONE);
        Title_RegBreadth.setVisibility(View.GONE);
        Title_RegLength.setVisibility(View.GONE);
    }

    //for categorizing boat length
    public void BoatLength()
    {
        tl = Double.parseDouble(EDITTEXT_tonnageLength.getText().toString());

         if (tl>=1 && tl<=7){
            TEXTVIEW_formulaGrossTonnage.setVisibility(View.VISIBLE);
            over.setVisibility(View.VISIBLE);
            two.setVisibility(View.VISIBLE);
            EDITTEXT_TonnageBreadth.setVisibility(View.VISIBLE);
            EDITTEXT_TonnageDepth.setVisibility(View.VISIBLE);
            TEXTVIEW_lblTonnageDepth.setVisibility(View.VISIBLE);
            TEXTVIEW_lblTonnageBreadth.setVisibility(View.VISIBLE);
            TEXTVIEW_GrossTonnage_sysGen.setVisibility(View.VISIBLE);
             TEXTVIEW_netTonnage.setVisibility(View.VISIBLE);
            gt.setVisibility(View.VISIBLE);
            btnCalculate.setVisibility(View.VISIBLE);
             nt.setVisibility(View.VISIBLE);
            NEXT_btnFVDimension.setVisibility(View.VISIBLE);
            //ung mga invisible
            TEXTVIEW_chooseShape.setVisibility(View.GONE);
            imgSpoonPlumb.setVisibility(View.GONE);
            imgRakeStem.setVisibility(View.GONE);
            imgRakeStem_TransformStem.setVisibility(View.GONE);
            imgSkiff.setVisibility(View.GONE);
             RegLength.setVisibility(View.VISIBLE);
             RegDepth.setVisibility(View.VISIBLE);
             RegBreadth.setVisibility(View.VISIBLE);
             Title_RegDepth.setVisibility(View.VISIBLE);
             Title_RegBreadth.setVisibility(View.VISIBLE);
             Title_RegLength.setVisibility(View.VISIBLE);
             btnCalculate.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if (Validate()) {
                         TEXTVIEW_GrossTonnage_sysGen.setText(computation());
                     }
                 }
             });
        }
        else if (tl>7){
            TEXTVIEW_chooseShape.setVisibility(View.VISIBLE);
            imgSpoonPlumb.setVisibility(View.VISIBLE);
            imgRakeStem.setVisibility(View.VISIBLE);
             TEXTVIEW_netTonnage.setVisibility(View.VISIBLE);
            imgRakeStem_TransformStem.setVisibility(View.VISIBLE);
            imgSkiff.setVisibility(View.VISIBLE);
            TEXTVIEW_formulaGrossTonnage.setVisibility(View.VISIBLE);
            over.setVisibility(View.VISIBLE);
            two.setVisibility(View.VISIBLE);
            EDITTEXT_TonnageBreadth.setVisibility(View.VISIBLE);
            EDITTEXT_TonnageDepth.setVisibility(View.VISIBLE);
            TEXTVIEW_lblTonnageDepth.setVisibility(View.VISIBLE);
            TEXTVIEW_lblTonnageBreadth.setVisibility(View.VISIBLE);
            TEXTVIEW_GrossTonnage_sysGen.setVisibility(View.VISIBLE);
            gt.setVisibility(View.VISIBLE);
             nt.setVisibility(View.VISIBLE);
            btnCalculate.setVisibility(View.VISIBLE);
             RegLength.setVisibility(View.VISIBLE);
             RegDepth.setVisibility(View.VISIBLE);
             RegBreadth.setVisibility(View.VISIBLE);
             Title_RegDepth.setVisibility(View.VISIBLE);
             Title_RegBreadth.setVisibility(View.VISIBLE);
             Title_RegLength.setVisibility(View.VISIBLE);
            NEXT_btnFVDimension.setVisibility(View.VISIBLE);
             ImageClick();
        }
        else{ }
    }

//for image selection
    public void ImageClick()
    {
        imgSpoonPlumb.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),"Spoon/Plumb",Toast.LENGTH_SHORT).show();
            TEXTVIEW_formulaGrossTonnage.setText("Formula:   GT =     TD x TL x TB x 0.70");
            over.setText("______________________________");
            l0.setVisibility(View.GONE);
            l1.setVisibility(View.GONE);
            l2.setVisibility(View.GONE);
            EDITTEXT_l0.setVisibility(View.GONE);
            EDITTEXT_l1.setVisibility(View.GONE);
            EDITTEXT_l2.setVisibility(View.GONE);
            img_formula_rakestem.setVisibility(View.GONE);
            img_formula_transom.setVisibility(View.GONE);
            img_formula_transom101.setVisibility(View.GONE);
            clearInfo();
            btnCalculate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Validate()) {
                        TEXTVIEW_GrossTonnage_sysGen.setText(computation());
                    }
                }
            });
        }
    });

        imgRakeStem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Rake Stem - Rake Stern",Toast.LENGTH_SHORT).show();
                TEXTVIEW_formulaGrossTonnage.setText("Formula:   GT =     TD x TL(c) x TB x 0.70");
                over.setText("__________________________________");
                l0.setVisibility(View.VISIBLE);
                l1.setVisibility(View.VISIBLE);
                l2.setVisibility(View.VISIBLE);
                EDITTEXT_l0.setVisibility(View.VISIBLE);
                EDITTEXT_l1.setVisibility(View.VISIBLE);
                EDITTEXT_l2.setVisibility(View.VISIBLE);
                img_formula_rakestem.setVisibility(View.VISIBLE);
                img_formula_transom.setVisibility(View.GONE);
                img_formula_transom101.setVisibility(View.GONE);
                clearInfo();
                btnCalculate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Validate()) {
                            TEXTVIEW_GrossTonnage_sysGen.setText(computationRake());
                        }
                    }
                });
            }
        });
        imgRakeStem_TransformStem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l0.setVisibility(View.VISIBLE);
                l1.setVisibility(View.VISIBLE);
                l2.setVisibility(View.INVISIBLE);
                EDITTEXT_l0.setVisibility(View.VISIBLE);
                EDITTEXT_l1.setVisibility(View.VISIBLE);
                EDITTEXT_l2.setVisibility(View.GONE);
                img_formula_rakestem.setVisibility(View.GONE);
                Toast.makeText(getActivity(),"Rake Stem - Transom Stern",Toast.LENGTH_SHORT).show();
                TEXTVIEW_formulaGrossTonnage.setText("Formula:   GT =     TD x TL(c) x TB x 0.70");
                over.setText("__________________________________");
                clearInfo();
                btnCalculate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Validate()) {
                            TEXTVIEW_GrossTonnage_sysGen.setText(computationTransom());
                        }
                    }
                });
            }
        });
        imgSkiff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l0.setVisibility(View.GONE);
                l1.setVisibility(View.GONE);
                l2.setVisibility(View.GONE);
                EDITTEXT_l0.setVisibility(View.GONE);
                EDITTEXT_l1.setVisibility(View.GONE);
                EDITTEXT_l2.setVisibility(View.GONE);
                img_formula_rakestem.setVisibility(View.GONE);
                img_formula_transom.setVisibility(View.GONE);
                img_formula_transom101.setVisibility(View.GONE);
                Toast.makeText(getActivity(),"Skiff",Toast.LENGTH_SHORT).show();
                TEXTVIEW_formulaGrossTonnage.setText("Formula:   GT =     TD x TL x TB x 0.86");
                over.setText("______________________________");
                clearInfo();
                btnCalculate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Validate()) {
                            TEXTVIEW_GrossTonnage_sysGen.setText(computationSkiff());
                        }
                    }
                });
            }
        });
    }
    public Boolean Validate(){
        Boolean valid = false;
        warning ="";
        if (EDITTEXT_tonnageLength.getText().toString().isEmpty()){
            warning +="Tonnage Length \n";
        }
        if (EDITTEXT_TonnageDepth.getText().toString().isEmpty() && EDITTEXT_TonnageDepth.getVisibility() == View.VISIBLE){
            warning +="Tonnage Depth \n";
        }
        if (EDITTEXT_TonnageBreadth.getText().toString().isEmpty() && EDITTEXT_TonnageBreadth.getVisibility() == View.VISIBLE){
            warning +="Tonnage Breadth \n";
        }
        if(EDITTEXT_l0.getText().toString().isEmpty() && EDITTEXT_l0.getVisibility() == View.VISIBLE ){
            warning +="L0 \n";
        }
        if(EDITTEXT_l1.getText().toString().isEmpty() && EDITTEXT_l1.getVisibility() == View.VISIBLE ){
            warning +="L1 \n";
        }
        if(EDITTEXT_l2.getText().toString().isEmpty() && EDITTEXT_l2.getVisibility() == View.VISIBLE ){
            warning +="L2 \n";
        }

        if(warning.isEmpty()){
           valid = true;
            true_false=false;
        }

        else{
            ((BoatRActivity) getActivity()).onInvalid(warning);
            true_false = true;
        }
        return valid;
    }
   public void validateGT(){
       String warning="";
       if(Double.parseDouble(TEXTVIEW_GrossTonnage_sysGen.getText().toString())>3){
           warning+="Gross Tonnage exceeds 3 \n";
       }
       if(RegBreadth.getText().toString().isEmpty()){
           warning+="Registered Breadth \n";
       }
       if(RegDepth.getText().toString().isEmpty()){
           warning+="Registered Depth \n";
       }
       if(RegLength.getText().toString().isEmpty()){
           warning+="Registered Length \n";
       }
       if(TEXTVIEW_GrossTonnage_sysGen.getText().toString().matches("0.00")){
           warning+="Click calculate button \n";
       }
       if (warning.isEmpty()){
           BoatrSingleton.getInstance().setBoatTonLength(EDITTEXT_tonnageLength.getText().toString());
           BoatrSingleton.getInstance().setBoatTonDepth(EDITTEXT_TonnageDepth.getText().toString());
           BoatrSingleton.getInstance().setBoatTonBreadth(EDITTEXT_TonnageBreadth.getText().toString());
           BoatrSingleton.getInstance().setBoatRegBreadth(RegBreadth.getText().toString());
           BoatrSingleton.getInstance().setBoatRegDepth(RegDepth.getText().toString());
           BoatrSingleton.getInstance().setBoatRegLength(RegLength.getText().toString());
           BoatrSingleton.getInstance().setBoatGrossTonnage(TEXTVIEW_GrossTonnage_sysGen.getText().toString());
           BoatrSingleton.getInstance().setBoatNetTonnage(TEXTVIEW_netTonnage.getText().toString());
           true_false = false;
           Bundle temp = new Bundle();
           temp.putBoolean("status", true_false);
           temp.putString("message",warning);
           temp.putInt("fragment", 2);
           temp.putBoolean("isFromFragment",true);
           MySingleton.getInstance().getFragmentTabBus().post(temp);

       }
       else{
           ((BoatRActivity) getActivity()).onInvalid(warning);
           this.warning = warning;
           true_false = true;
       }

    }
    //computation process spoon/plumb
    private String computation() {
        DecimalFormat df = new DecimalFormat("#.##");
        String stringResult="0.00";
        if(EDITTEXT_tonnageLength.getText().toString() != "" && EDITTEXT_tonnageLength.getText().length() > 0) {
            tl = Double.parseDouble(EDITTEXT_tonnageLength.getText().toString());
        } else {
            tl = 0;
        }
        //tonnage depth
        if(EDITTEXT_TonnageDepth.getText().toString() != "" && EDITTEXT_TonnageDepth.getText().length() > 0) {
            td = Double.parseDouble(EDITTEXT_TonnageDepth.getText().toString());
        } else {
            td = 0;
        }
        //tonnage breadth
        if(EDITTEXT_TonnageBreadth.getText().toString() !="" && EDITTEXT_TonnageBreadth.getText().length() >0){
            tb= Double.parseDouble(EDITTEXT_TonnageBreadth.getText().toString());
        }else{
            tb=0;
        }
        result=((tl * td * tb * 0.70)/2.83);
        net=result*0.68;
        String netString = df.format(net);
        TEXTVIEW_netTonnage.setText(netString);
        stringResult=df.format(result);
        return (stringResult);
    }

    //RakeStem Computation
    private String computationRake() {
        DecimalFormat df = new DecimalFormat("#.##");
        String stringResult="0.00";
        //tonnage depth
        if(EDITTEXT_TonnageDepth.getText().toString() != "" && EDITTEXT_TonnageDepth.getText().length() > 0) {
            td = Double.parseDouble(EDITTEXT_TonnageDepth.getText().toString());
        } else {
            td = 0;
        }
        //tonnage breadth
        if(EDITTEXT_TonnageBreadth.getText().toString() !="" && EDITTEXT_TonnageBreadth.getText().length() >0){
            tb= Double.parseDouble(EDITTEXT_TonnageBreadth.getText().toString());
        }else{
            tb=0;
        }
        //for length:
        if(EDITTEXT_l0.getText().toString() !="" && EDITTEXT_l0.getText().length() >0){
            len0= Double.parseDouble(EDITTEXT_l0.getText().toString());
        }else{
            len0=0;
        }
        if(EDITTEXT_l1.getText().toString() !="" && EDITTEXT_l1.getText().length() >0){
            len1= Double.parseDouble(EDITTEXT_l1.getText().toString());
        }else{
            len1=0;
        }
        if(EDITTEXT_l2.getText().toString() !="" && EDITTEXT_l2.getText().length() >0){
            len2= Double.parseDouble(EDITTEXT_l2.getText().toString());
        }else{
            len2=0;
        }

        lc=(len0 + ((len1/2)+(len2/2)));
        result=(td * tb * lc *0.70)/2.83;
        net=result*0.68;
        String netString = df.format(net);
        TEXTVIEW_netTonnage.setText(netString);
        stringResult=df.format(result);
        return (stringResult);
    }

    //computation process for transom stern
    private String computationTransom() {
        DecimalFormat df = new DecimalFormat("#.##");
        String stringResult="0.00";
        if(EDITTEXT_tonnageLength.getText().toString() != "" && EDITTEXT_tonnageLength.getText().length() > 0) {
            tl = Double.parseDouble(EDITTEXT_tonnageLength.getText().toString());
        } else {
            tl = 0;
        }
        //tonnage depth
        if(EDITTEXT_TonnageDepth.getText().toString() != "" && EDITTEXT_TonnageDepth.getText().length() > 0) {
            td = Double.parseDouble(EDITTEXT_TonnageDepth.getText().toString());
        } else {
            td = 0;
        }
        //tonnage breadth
        if(EDITTEXT_TonnageBreadth.getText().toString() !="" && EDITTEXT_TonnageBreadth.getText().length() >0){
            tb= Double.parseDouble(EDITTEXT_TonnageBreadth.getText().toString());
        }else{
            tb=0;
        }
        if(EDITTEXT_l0.getText().toString() !="" && EDITTEXT_l0.getText().length() >0){
            len0= Double.parseDouble(EDITTEXT_l0.getText().toString());
        }else{
            len0=0;
        }
        if(EDITTEXT_l1.getText().toString() !="" && EDITTEXT_l1.getText().length() >0){
            len1= Double.parseDouble(EDITTEXT_l1.getText().toString());
            if(len1 > 1.3){
                img_formula_transom.setVisibility(View.VISIBLE);
                img_formula_transom101.setVisibility(View.INVISIBLE);
                lc=(len0 + (len1/2));
            }
            else{
                img_formula_transom.setVisibility(View.INVISIBLE);
                img_formula_transom101.setVisibility(View.VISIBLE);
                lc=(len0+len1) ;
            }
        }else{
            len1=0;
        }
        result=(td * tb * lc *0.70)/2.83;
        net=result*0.68;
        String netString = df.format(net);
        TEXTVIEW_netTonnage.setText(netString);
        stringResult=df.format(result);
        return (stringResult);
    }

    //computation process skiff
    private String computationSkiff() {
        DecimalFormat df = new DecimalFormat("#.##");
        String stringResult="0.00";
        if(EDITTEXT_tonnageLength.getText().toString() != "" && EDITTEXT_tonnageLength.getText().length() > 0) {
            tl = Double.parseDouble(EDITTEXT_tonnageLength.getText().toString());
        } else {
            tl = 0;
        }
        //tonnage depth
        if(EDITTEXT_TonnageDepth.getText().toString() != "" && EDITTEXT_TonnageDepth.getText().length() > 0) {
            td = Double.parseDouble(EDITTEXT_TonnageDepth.getText().toString());
        } else {
            td = 0;
        }
        //tonnage breadth
        if(EDITTEXT_TonnageBreadth.getText().toString() !="" && EDITTEXT_TonnageBreadth.getText().length() >0){
            tb= Double.parseDouble(EDITTEXT_TonnageBreadth.getText().toString());
        }else{
            tb=0;
        }
        result=((tl * td * tb * 0.86)/2.83);
        net=result*0.68;
        String netString = df.format(net);
        TEXTVIEW_netTonnage.setText(netString);
        stringResult=df.format(result);
        return (stringResult);
    }

    public void clearInfo()
    {
        EDITTEXT_l0.setText("");
        EDITTEXT_l1.setText("");
        EDITTEXT_l2.setText("");
        EDITTEXT_TonnageBreadth.setText("");
        EDITTEXT_TonnageDepth.setText("");
        TEXTVIEW_GrossTonnage_sysGen.setText("0.00");
        TEXTVIEW_netTonnage.setText("0.00");
    }





    public static FishingVesselDimension newInstance(String param1, String param2) {
        FishingVesselDimension fragment = new FishingVesselDimension();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FishingVesselDimension() {
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
        return inflater.inflate(R.layout.fragment_fishing_vessel_dimension, container, false);
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
        super.onResume();
        MySingleton.getInstance().getFishingVesselDimensionBus().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MySingleton.getInstance().getFishingVesselDimensionBus().unregister(this);
    }

    @Subscribe
    public void receivedFromPager(String from_pager){
        Log.e(TAG, "receivedFromPager" + from_pager);

        if ((Validate())){
            validateGT();
            BoatLength();
        }
        Bundle temp = new Bundle();
        temp.putBoolean("status", isEmptyFields());
        temp.putString("message",warning);
        temp.putInt("fragment", 2);
        MySingleton.getInstance().getFragmentTabBus().post(temp);
    }

    private boolean isEmptyFields(){
        if(true_false ==true){
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

}
