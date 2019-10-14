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
                    String[] name = (txtName.getText().toString()).split(" ", 2);
                    customer.setCustFirstName(name[0].trim());
                    customer.setCustLastName(name[1].trim());
                    customer.setCustAddress(txtAddress.getText().toString().trim());
                    customer.setCustCity(txtCity.getText().toString().trim());
                    customer.setCustEmail(txtEmail.getText().toString().trim());
                    customer.setCustHomePhone(txtPhone.getText().toString().trim());
                    String[] prov_post = (txtProvPostalCode.getText().toString()).split(",", 2);
                    customer.setCustProv(prov_post[0].trim());
                    customer.setCustPostal(prov_post[1].trim());
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