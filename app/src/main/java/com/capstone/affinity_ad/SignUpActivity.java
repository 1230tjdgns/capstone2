package com.capstone.affinity_ad;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.capstone.affinity_ad.databinding.ActivitySingUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySingUpBinding binding;
    FirebaseAuth firebaseAuth;
    private String email;
    private String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySingUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        itemClick();
    }

    private void itemClick() {
        binding.btNext.setOnClickListener(v -> {
            email = binding.edEmail.getText().toString();
            password = binding.edPassword.getText().toString();
            String passwordCheck = binding.edPasswordCheck.getText().toString();
            if (!password.equals(passwordCheck)) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                return;
            }

            if (!email.isEmpty() || !password.isEmpty()) {
                binding.layoutContainer.setVisibility(View.GONE);
                binding.layoutNickName.getRoot().setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
            }
        });


        // 회원가입 버튼
        binding.layoutNickName.btJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("##INFO", "onClick(): click");
                if (!binding.edEmail.getText().toString().equals("") && !binding.edPassword.getText().toString().equals("")) {
                    // 이메일과 비밀번호가 공백이 아닌 경우
                    createUser(binding.edEmail.getText().toString(), binding.edPassword.getText().toString());
                } else {
                    // 이메일과 비밀번호가 공백인 경우
                    Toast.makeText(SignUpActivity.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void createUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공시
                            Toast.makeText(SignUpActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            UserModel userModel = new UserModel();
                            userModel.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            userModel.email = binding.edEmail.getText().toString();
                            userModel.password = binding.edPassword.getText().toString();
                            userModel.nickName = binding.layoutNickName.edNickName.getText().toString();

                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("users").document(userModel.uid).set(userModel).addOnSuccessListener(aVoid -> {
                                Log.d("SignUpActivity", "onSuccess: ");
                                finish();
                            }).addOnFailureListener(e -> {
                                Log.e("SignUpActivity", "onFailure: " + e.getMessage());
                            });

                        } else {
                            task.addOnFailureListener(e -> {
                                Log.e("SignUpActivity", "onFailure: " + e.getMessage());
                            });
//                            // 계정이 중복된 경우
//                            Toast.makeText(SignUpActivity.this, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
