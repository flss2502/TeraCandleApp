package com.group04.shop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group04.shop.Models.Role;
import com.group04.shop.Models.User;
import com.group04.shop.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextFullName, editTextEmail, editTextPassword, editAddress, editTextPhone, editTextConfirmPassword;
    private Button buttonSignUp;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance("https://shopping-56bed-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("User");

        // Initialize views
        editTextFullName = findViewById(R.id.fullNameEditText);
        editTextEmail = findViewById(R.id.emailEditText);
        editAddress = findViewById(R.id.addressEditText);
        editTextPhone = findViewById(R.id.phoneEditText);
        editTextPassword = findViewById(R.id.passwordEditText);
        editTextConfirmPassword = findViewById(R.id.reEnterPasswordEditText);
        buttonSignUp = findViewById(R.id.signUpButton);

        // Handle sign up button click
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });

        // Apply padding for system bars (status bar and navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signup), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void signUpUser() {
        String fullName = editTextFullName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String address = editAddress.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        // Validate input fields
        if (TextUtils.isEmpty(fullName)) {
            editTextFullName.setError("Full Name is required");
            editTextFullName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Valid email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            editTextPassword.setError("Password must be at least 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(address)) {
            editAddress.setError("Address is required");
            editAddress.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phone) || phone.length() != 10) {
            editTextPhone.setError("Phone number must be 10 digits");
            editTextPhone.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(confirmPassword) || !password.equals(confirmPassword)) {
            editTextConfirmPassword.setError("Passwords do not match");
            editTextConfirmPassword.requestFocus();
            return;
        }

        // Fetch the last userId and increment it by 1
        databaseReference.child("users").orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long userId = 1; // Default userId if no users exist
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    userId = Long.parseLong(snapshot.getKey()) + 1;
                }

                // Create a User object with required fields
                User user = new User(String.valueOf(userId), fullName, Role.CUSTOMER, address, phone, email, password);

                // Save user to Firebase Realtime Database
                databaseReference.child("users").child(String.valueOf(userId)).setValue(user)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(SignUpActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish(); // Close SignUpActivity
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(SignUpActivity.this, "Failed to register user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SignUpActivity.this, "Failed to get user ID: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
