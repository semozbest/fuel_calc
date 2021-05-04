package com.tech.semo.fuel;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static java.sql.Types.NULL;

public class MainActivity extends Activity {

    Button calc;
    EditText b4,af,sg,trk1,trk2,trk3,ltankb4value,rtankb4value,ctankb4value,ltankafvalue,rtankafvalue,ctankafvalue;
    TextView difflt,diffkg,sum,delta;

    double diffkgval,diffltval,sumval,val,sgval,after,before;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calc=(Button) findViewById(R.id.btncalc);

        b4=(EditText) findViewById(R.id.b4value);
        af=(EditText) findViewById(R.id.afvalue);
        sg=(EditText) findViewById(R.id.sgvalue);

        trk1=(EditText) findViewById(R.id.trk1value);
        trk2=(EditText) findViewById(R.id.trk2value);
        trk3=(EditText) findViewById(R.id.trk3value);

        ltankafvalue=(EditText) findViewById(R.id.ltankafvalue);
        rtankafvalue=(EditText) findViewById(R.id.rtankafvalue);
        ctankafvalue=(EditText) findViewById(R.id.ctankafvalue);
        ltankb4value=(EditText) findViewById(R.id.ltankb4value);
        rtankb4value=(EditText) findViewById(R.id.rtankb4value);
        ctankb4value=(EditText) findViewById(R.id.ctankb4value);


        difflt=(TextView) findViewById(R.id.deltavaluelt);
        diffkg=(TextView) findViewById(R.id.deltavaluekg);
        sum=(TextView) findViewById(R.id.trksumvalue);
        delta=(TextView) findViewById(R.id.deltapercentvalue);

        trk3.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    calc(trk3);
                    handled = true;
                }
                return handled;
            }
        });

    }

    public void calc(android.view.View v)
    {
        boolean sgok,b4ok,afok;
        sgok=false;
        b4ok=false;
        afok=false;
        try {
            //before value checks
            if(this.isEmpty(ltankb4value)||this.isEmpty(rtankb4value)||this.isEmpty(ctankb4value))
            {
                if (this.isEmpty(b4))
                {
                    ltankb4value.setError("Please Enter");
                    rtankb4value.setError("Please Enter");
                    ctankb4value.setError("Please Enter");
                    b4.setError("Please Enter");
                }
                else
                    {
                    before = Double.parseDouble(b4.getText().toString());
                    b4.setText(String.format("%.02f",before));
                    b4ok=true;
                    }
            }
            else
                {
                    before=(Double.parseDouble(ltankb4value.getText().toString()))+(Double.parseDouble(rtankb4value.getText().toString()))+(Double.parseDouble((ctankb4value.getText().toString())));
                    b4.setText(String.format("%.02f",before));
                    b4ok=true;
                }

                //after value checks
            if(this.isEmpty(ltankafvalue)||this.isEmpty(rtankafvalue)||this.isEmpty(ctankafvalue))
            {
                if (this.isEmpty(af))
                {
                    ltankafvalue.setError("Please Enter");
                    rtankafvalue.setError("Please Enter");
                    ctankafvalue.setError("Please Enter");
                    af.setError("Please Enter");
                }
                else
                {
                    after = Double.parseDouble(af.getText().toString());
                    af.setText(String.format("%.02f",after));
                    afok=true;
                }

            }
            else
            {
                after=(Double.parseDouble(ltankafvalue.getText().toString()))+(Double.parseDouble(ctankafvalue.getText().toString()))+(Double.parseDouble(rtankafvalue.getText().toString()));
                af.setText(String.format("%.02f",after));
                afok=true;
            }


                //track value checks
                if((!(this.isEmpty(trk1)))||(!(this.isEmpty(trk2)))||(!(this.isEmpty(trk3))))
                {
                    if(this.isEmpty(trk1))
                        trk1.setText("0");
                    if(this.isEmpty(trk2))
                        trk2.setText("0");
                    if(this.isEmpty(trk3))
                        trk3.setText("0");
                    sumval = (Double.parseDouble(trk1.getText().toString()) + Double.parseDouble(trk2.getText().toString()) + Double.parseDouble(trk3.getText().toString()));
                }
                else
                {
                    trk1.setError("Please Enter");
                    trk2.setError("Please Enter");
                    trk3.setError("Please Enter");
                }


                //sg value
            if (this.isEmpty(sg))
                sg.setError("Please Enter S.G Value");
            else
                {
                sgval = Double.parseDouble(sg.getText().toString());
                sgok=true;
                }


                //final calculations
                if((b4ok)&&(afok)&&(sgok))
                {
                    diffkgval = (after - before);
                    diffltval = diffkgval / sgval;
                    if (diffltval > sumval)
                    {
                        val = ((diffltval - sumval) / diffltval) * 100;
                    } else
                        val = ((sumval - diffltval) / sumval) * 100;
                    difflt.setText(String.format("%.02f", diffltval));
                    diffkg.setText(String.format("%.02f", diffkgval));
                    sum.setText(String.format("%.02f", sumval));
                    delta.setText(String.format("%.02f", val));
                }
        }
        catch(Exception e)
        {
        }
        //to hide the keyboard when the calculations are done
        try{
            InputMethodManager imm=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (Exception e)
        {}
    }
    boolean isEmpty(EditText t)
    {
        if(TextUtils.isEmpty(t.getText()))
            return true;
        else
            return false;
    }

    public void clr(android.view.View v)
    {
        b4.setText("");
        b4.setError(null);

        af.setText("");
        af.setError(null);

        sg.setText("");
        sg.setError(null);  //clear the errors

        trk1.setText("");
        trk1.setError(null);

        trk2.setText("");
        trk2.setError(null);

        trk3.setText("");
        trk3.setError(null);

        difflt.setText("");
        diffkg.setText("");
        sum.setText("");
        delta.setText("");

        ltankb4value.setText("");
        ltankb4value.setError(null);

        rtankb4value.setText("");
        rtankb4value.setError(null);

        ctankb4value.setText("");
        ctankb4value.setError(null);

        ltankafvalue.setText("");
        ltankafvalue.setError(null);

        rtankafvalue.setText("");
        rtankafvalue.setError(null);

        ctankafvalue.setText("");
        ctankafvalue.setError(null);
        //to hide the keyboard from the screen when clear is pressed
        try{
            InputMethodManager imm=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (Exception e)
        {}
    }

}
