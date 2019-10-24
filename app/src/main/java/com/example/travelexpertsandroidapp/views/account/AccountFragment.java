package com.example.travelexpertsandroidapp.views.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.travelexpertsandroidapp.R;
import com.example.travelexpertsandroidapp.models.Customer;
import com.example.travelexpertsandroidapp.models.TravelExpertsApp;
import com.example.travelexpertsandroidapp.utilities.InputValidator;
import com.example.travelexpertsandroidapp.viewmodels.AccountViewModel;
import com.example.travelexpertsandroidapp.views.login.LoginActivity;

public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;
    EditText txtName,txtEmail,txtPhone,txtAddress,txtCity,txtProvPostalCode;
    Button btnSignOut, btnUpdate;
    Customer user = new Customer();
    TravelExpertsApp app;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel =
                ViewModelProviders.of(this).get(AccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        txtName = root.findViewById(R.id.txtName);
        txtEmail = root.findViewById(R.id.txtEmail);
        txtPhone = root.findViewById(R.id.txtPhone);
        txtAddress = root.findViewById(R.id.txtAddress);
        txtCity = root.findViewById(R.id.txtCity);
        txtProvPostalCode = root.findViewById(R.id.txtProvPostalCode);
        btnSignOut = root.findViewById(R.id.btnSignOut);
        btnUpdate = root.findViewById(R.id.btnUpdate);

        app = ((TravelExpertsApp)this.getActivity().getApplication());

        //if(user == null) {
            accountViewModel.getLoggedInUser().observe(this, new Observer<Customer>() {
                @Override
                public void onChanged(@Nullable Customer customer) {
                    displayUserData(customer);
                    app.setLoggedInUser(customer);
                }
            });
        //}
        accountViewModel.getFeedbackMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.setLoggedInUser(null);
                Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(loginIntent);
                getActivity().finish();

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtName.isEnabled()){
                    Customer customer = app.getLoggedInUser();
                    //boolean isInputsValid = false;
                    //verify name field
                    if(InputValidator.isPresent(txtName) &&
                            InputValidator.isTextOnly(txtName.getText().toString())){
                        //isInputsValid = true;
                        String[] name = (txtName.getText().toString()).split(" ", 2);
                        customer.setCustFirstName(name[0].trim());
                        customer.setCustLastName(name[1].trim());
                    }else{
                        Toast.makeText(getContext(),"Please verify the name.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //check if address is entered
                    if(InputValidator.isPresent(txtAddress)){
                        //isInputsValid = true;
                        customer.setCustAddress(txtAddress.getText().toString().trim());
                    }else{
                        Toast.makeText(getContext(),"Please specify address.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //check if city is entered
                    if(InputValidator.isPresent(txtCity) &&
                            InputValidator.isTextOnly(txtCity.getText().toString())){
                        //isInputsValid = true;
                        customer.setCustCity(txtCity.getText().toString().trim());
                    }else{
                        Toast.makeText(getContext(),"Please check the city name entered.",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //verify email
                    if(InputValidator.isPresent(txtEmail) &&
                            InputValidator.validateEmail(txtEmail.getText().toString().trim())){
                        //isInputsValid = true;
                        customer.setCustEmail(txtEmail.getText().toString().trim());
                    }else{
                        Toast.makeText(getContext(),"Please verify the email id provided.",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(InputValidator.isPresent(txtPhone) &&
                            InputValidator.validatePhoneNumber(txtPhone.getText().toString().trim())){
                        //isInputsValid = true;
                        customer.setCustHomePhone(txtPhone.getText().toString().trim());
                    }else{
                        Toast.makeText(getContext(),"Please verify the phone number.",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String[] prov_post = null;
                    if(InputValidator.isPresent(txtProvPostalCode)){
                        prov_post = (txtProvPostalCode.getText().toString()).split(",", 2);
                        //isInputsValid = true;
                    }else{
                        Toast.makeText(getContext(),"Please enter the province and postal code (eg: AB T2L 3M1).",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(InputValidator.isTextOnly(prov_post[0].trim())){
                        customer.setCustProv(prov_post[0].trim());
                    }else{
                        Toast.makeText(getContext(),"Please enter the province and postal code (eg: AB T2L 3M1).",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(InputValidator.validatePostalCode(prov_post[1].trim())){
                        customer.setCustPostal(prov_post[1].trim());
                    }else{
                        Toast.makeText(getContext(),"Please enter a valid postal code.",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    accountViewModel.updateUserData(customer);
                    enableEditTextFields(false);
                }else{
                    enableEditTextFields(true);
                }
            }
        });

        return root;
    }

    private void enableEditTextFields(boolean b) {
        txtName.setEnabled(b);
        txtAddress.setEnabled(b);
        txtCity.setEnabled(b);
        txtEmail.setEnabled(b);
        txtPhone.setEnabled(b);
        txtProvPostalCode.setEnabled(b);
    }

    private void displayUserData(Customer customer) {
        txtName.setText(customer.getCustFirstName()+" "+customer.getCustLastName());
        txtAddress.setText(customer.getCustAddress().trim());
        txtCity.setText(customer.getCustCity().trim());
        txtEmail.setText(customer.getCustEmail().trim());
        txtPhone.setText(customer.getCustHomePhone());
        txtProvPostalCode.setText(customer.getCustProv()+", "+customer.getCustPostal());
    }

}