package com.group04.shop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group04.shop.Domain.UserDomain;
import com.group04.shop.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextFullName, editTextEmail, editTextPassword, editAddress, editTextPhone, editTextConfirmPassword;
    private Button buttonSignUp;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private TextView signInTextView;

    private long nextUserId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance("https://shopping-56bed-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();

        editTextFullName = findViewById(R.id.fullNameEditText);
        editTextEmail = findViewById(R.id.emailEditText);
        editTextPassword = findViewById(R.id.passwordEditText);
        editAddress = findViewById(R.id.addressEditText);
        editTextPhone = findViewById(R.id.phoneEditText);
        editTextConfirmPassword = findViewById(R.id.reEnterPasswordEditText);
        buttonSignUp = findViewById(R.id.signUpButton);
        signInTextView = findViewById(R.id.signInTextView);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });

        signInTextView.setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this, LoginActivity.class)));
        fetchNextUserId();
    }

    private void fetchNextUserId() {
        databaseReference.child("users").orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    nextUserId = Long.parseLong(dataSnapshot.getChildren().iterator().next().getKey()) + 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SignUpActivity.this, "Failed to get user ID: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
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

        // Create user authentication
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            // Create a UserDomain object with required fields
                            UserDomain user = new UserDomain(nextUserId, fullName, address, phone, email, password);

                            // Save user to Firebase Realtime Database
                            databaseReference.child("users").child(String.valueOf(nextUserId)).setValue(user)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(SignUpActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish(); // Close SignUpActivity
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(SignUpActivity.this, "Failed to register user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            Toast.makeText(SignUpActivity.this, "Failed to get current user", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(SignUpActivity.this, "Authentication failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }
}