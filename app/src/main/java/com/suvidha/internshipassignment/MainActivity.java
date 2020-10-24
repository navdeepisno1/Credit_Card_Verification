package com.suvidha.internshipassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Context context = this;
    TextInputEditText editText_card_number,editText_mmyy,editText_code,editText_fName,editText_lName;
    TextInputLayout textInputLayout_card_number,textInputLayout_mmyy,textInputLayout_code,textInputLayout_fName,textInputLayout_lName;
    Button button_submit;
    String updatedText;
    boolean editing=true;
    int pC,cC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        editText_card_number = findViewById(R.id.main_et_card_number);
        editText_code = findViewById(R.id.main_et_code);
        editText_fName = findViewById(R.id.main_et_fname);
        editText_lName = findViewById(R.id.main_et_lname);
        editText_mmyy = findViewById(R.id.main_et_mmyy);

        textInputLayout_card_number = findViewById(R.id.main_tl_card_number);
        textInputLayout_code = findViewById(R.id.main_tl_code);
        textInputLayout_fName = findViewById(R.id.main_tl_fname);
        textInputLayout_lName = findViewById(R.id.main_tl_lname);
        textInputLayout_mmyy = findViewById(R.id.main_tl_mmyy);

        button_submit = findViewById(R.id.main_btn_submit);

        editText_mmyy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pC = charSequence.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e("Tag",charSequence.toString() + "," + pC + "," + cC);
                cC = charSequence.length();
                if(charSequence.toString().contains("/")) return;

                if(pC==2 && cC==3 && charSequence.charAt(2)=='/')
                {
                    updatedText = charSequence.toString();
                    editing = false;
                }

                if(pC==1 && cC==2)
                {
                    updatedText = charSequence + "/";
                    editing = false;
                }
                else if(pC==3 && cC==2)
                {
                    editing=true;
                }
                else if(pC==4 && cC==3)
                {
                    editing = false;
                    updatedText = updatedText.substring(0,2);
                }
                else
                {
                    editing=true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editing) return;
                editable.clear();
                editable.insert(0,updatedText);
            }
        });

        button_submit.setOnClickListener(this);
        Log.e("Check","" + checkCreditCardNumber("12345678903555"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.main_btn_submit:
            {
                if(editText_card_number.getText().toString().trim().isEmpty())
                {
                    textInputLayout_card_number.setError("Card Number Required");
                    return;
                }
                else
                {
                    textInputLayout_card_number.setError(null);
                }

                if(editText_mmyy.getText().toString().trim().isEmpty())
                {
                    textInputLayout_mmyy.setError("Enter Expiry");
                    return;
                }
                else
                {
                    textInputLayout_mmyy.setError(null);
                }

                if(editText_code.getText().toString().trim().isEmpty())
                {
                    textInputLayout_code.setError("Enter Code");
                    return;
                }
                else
                {
                    textInputLayout_code.setError(null);
                }

                if(editText_fName.getText().toString().trim().isEmpty())
                {
                    textInputLayout_fName.setError("Enter First Name");
                    return;
                }
                else
                {
                    textInputLayout_fName.setError(null);
                }
                if(editText_lName.getText().toString().trim().isEmpty())
                {
                    textInputLayout_lName.setError("Enter Last Name");
                    return;
                }
                else
                {
                    textInputLayout_lName.setError(null);
                }

                String cardType = checkCreditCardType(editText_card_number.getText().toString().trim());

                if(cardType.equals("NA"))
                {
                    textInputLayout_card_number.setError("Card is not acceptable");
                    return;
                }
                else if(cardType.equals("American Express"))
                {
                    textInputLayout_card_number.setError(null);
                    if(editText_card_number.getText().toString().trim().length()!=15)
                    {
                        textInputLayout_card_number.setError(null);
                        if(checkCreditCardNumber(editText_card_number.getText().toString().trim()))
                        {
                            textInputLayout_card_number.setError(null);
                            if(checkExpiry(editText_mmyy.getText().toString().trim()))
                            {
                                textInputLayout_mmyy.setError(null);
                                if(editText_code.getText().toString().trim().length()==4)
                                {
                                    textInputLayout_code.setError(null);
                                    showPaymentSuccessDialog();
                                }
                                else
                                {
                                    textInputLayout_code.setError("Invalid Security Code");
                                    return;
                                }
                            }
                            else
                            {
                                textInputLayout_mmyy.setError("Invalid Expiry Date");
                            }
                        }
                        else {
                            textInputLayout_card_number.setError("Invalid Card Number");
                            return;
                        }
                    }
                    else
                    {
                        textInputLayout_card_number.setError("Invalid Card Number");
                        return;
                    }
                }
                else if(cardType.equals("Visa"))
                {
                    textInputLayout_card_number.setError(null);
                    if(editText_card_number.getText().toString().trim().length()==16)
                    {
                        textInputLayout_card_number.setError(null);
                        if(checkCreditCardNumber(editText_card_number.getText().toString().trim()))
                        {
                            textInputLayout_card_number.setError(null);
                            if(checkExpiry(editText_mmyy.getText().toString().trim()))
                            {
                                textInputLayout_mmyy.setError(null);
                                if(editText_code.getText().toString().trim().length()==3)
                                {
                                    textInputLayout_code.setError(null);
                                    showPaymentSuccessDialog();
                                }
                                else
                                {
                                    textInputLayout_code.setError("Invalid Security Code");
                                    return;
                                }
                            }
                            else
                            {
                                textInputLayout_mmyy.setError("Invalid Expiry Date");
                            }
                        }
                        else {
                            textInputLayout_card_number.setError("Invalid Card Number");
                            return;
                        }
                    }
                    else
                    {
                        textInputLayout_card_number.setError("Invalid Card Number");
                        return;
                    }
                }
                else if(cardType.equals("MasterCard"))
                {
                    textInputLayout_card_number.setError(null);
                    if(editText_card_number.getText().toString().trim().length()==16)
                    {
                        textInputLayout_card_number.setError(null);
                        if(checkCreditCardNumber(editText_card_number.getText().toString().trim()))
                        {
                            textInputLayout_card_number.setError(null);
                            if(checkExpiry(editText_mmyy.getText().toString().trim()))
                            {
                                textInputLayout_mmyy.setError(null);
                                if(editText_code.getText().toString().trim().length()==3)
                                {
                                    textInputLayout_code.setError(null);
                                    showPaymentSuccessDialog();
                                }
                                else
                                {
                                    textInputLayout_code.setError("Invalid Security Code");
                                    return;
                                }
                            }
                            else
                            {
                                textInputLayout_mmyy.setError("Invalid Expiry Date");
                            }
                        }
                        else {
                            textInputLayout_card_number.setError("Invalid Card Number");
                            return;
                        }
                    }
                    else
                    {
                        textInputLayout_card_number.setError("Invalid Card Number");
                        return;
                    }
                }
                else if(cardType.equals("Discover"))
                {
                    textInputLayout_card_number.setError(null);
                    if(editText_card_number.getText().toString().trim().length()>15 && editText_card_number.getText().toString().trim().length()<20)
                    {
                        textInputLayout_card_number.setError(null);
                        if(checkCreditCardNumber(editText_card_number.getText().toString().trim()))
                        {
                            textInputLayout_card_number.setError(null);
                            if(checkExpiry(editText_mmyy.getText().toString().trim()))
                            {
                                textInputLayout_mmyy.setError(null);
                                if(editText_code.getText().toString().trim().length()==3)
                                {
                                    textInputLayout_code.setError(null);
                                    showPaymentSuccessDialog();
                                }
                                else
                                {
                                    textInputLayout_code.setError("Invalid Security Code");
                                    return;
                                }
                            }
                            else
                            {
                                textInputLayout_mmyy.setError("Invalid Expiry Date");
                            }
                        }
                        else {
                            textInputLayout_card_number.setError("Invalid Card Number");
                            return;
                        }
                    }
                    else
                    {
                        textInputLayout_card_number.setError("Invalid Card Number");
                        return;
                    }
                }

                break;
            }
        }
    }

    private String checkCreditCardType(String creditCardNumber)
    {
        String cardType="NA";
        if(creditCardNumber.charAt(0) == '3')
        {
            if(creditCardNumber.charAt(1) == '4' || creditCardNumber.charAt(1) == '7')
            {
                cardType = "American Express";
            }
        }
        else if(creditCardNumber.charAt(0) == '4')
        {
            cardType = "Visa";
        }
        else if(creditCardNumber.charAt(0) == '5')
        {
            cardType = "MasterCard";
        }
        else if(creditCardNumber.charAt(0) == '6')
        {
            cardType = "Discover";
        }
        return cardType;
    }

    private boolean checkCreditCardNumber(String str)
    {
        
        int[] ints = new int[str.length()];
    for (int i = 0; i < str.length(); i++) {
        ints[i] = Integer.parseInt(str.substring(i, i + 1));
    }
    for (int i = ints.length - 2; i >= 0; i = i - 2) {
        int j = ints[i];
        j = j * 2;
        if (j > 9) {
            j = j % 10 + 1;
        }
        ints[i] = j;
    }
    int sum = 0;
    for (int i = 0; i < ints.length; i++) {
        sum += ints[i];
    }
    return (sum%10==0);
    }

    private void showPaymentSuccessDialog()
    {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_success);

        TextView textView_ok = dialog.findViewById(R.id.dialog_success_et_ok);
        textView_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
        editText_code.setText("");
        editText_card_number.setText("");
        editText_fName.setText("");
        editText_mmyy.setText("");
        editText_lName.setText("");
    }

    private boolean checkExpiry(String date)
    {
        if(date.length()==5)
        {
            if(date.charAt(2) == '/')
            {
                int month = Integer.parseInt(date.substring(0,2));
                int year = Integer.parseInt(date.substring(3,5));
                return (month<=12 && month>=1 && year>=20);
            }
            else
            {
                return false;
            }
        }
        else {
            return false;
        }
    }
}
