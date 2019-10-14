package com.example.travelexpertsandroidapp.views.login;
import com.example.travelexpertsandroidapp.MainActivity;
import com.example.travelexpertsandroidapp.R;
import com.example.travelexpertsandroidapp.models.Customer;
import com.example.travelexpertsandroidapp.models.TravelExpertsApp;
import com.example.travelexpertsandroidapp.viewmodels.LoginViewModel;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        //get the view model
        loginViewModel = ViewModelProviders.of(LoginActivity.this).get(LoginViewModel.class);

        //monitor changes in the login form state
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                //enable login button if user entries are valid
                loginButton.setEnabled(loginFormState.isDataValid());
                //if username or password entry is valid, show error message on the tip of the text box
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        //text change listener to monitor changes in user input for username and password
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                loadingProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };

        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //when user presses done in his virtual keyboard
                //get username and password from input fields and pass it to view model
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    handleAuthentication(loadingProgressBar, usernameEditText, passwordEditText);
                }
                return false;
            }
        });

        //handle user authentication when
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                handleAuthentication(loadingProgressBar, usernameEditText, passwordEditText);
            }
        });
    }

    //this method handles user login authentication
    private void handleAuthentication(ProgressBar loadingProgressBar, EditText usernameEditText, EditText passwordEditText) {
        loadingProgressBar.setVisibility(View.VISIBLE);
        loginViewModel.login(usernameEditText.getText().toString(),
                passwordEditText.getText().toString());

        loginViewModel.getLoggedInUser().observe(LoginActivity.this, new Observer<Customer>() {
            @Override
            public void onChanged(Customer customer) {
                if (customer == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);

                ((TravelExpertsApp)getApplication()).setLoggedInUser(customer);

                Bundle bundle = new Bundle();
                bundle.putSerializable("user", customer);
                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                mainIntent.putExtras(bundle); //Optional parameters
                LoginActivity.this.startActivity(mainIntent);
                //Complete and destroy login activity once successful
               finish();
            }
        });

        loginViewModel.getFeedbackMessage().observe(LoginActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            }
        });
    }

}
