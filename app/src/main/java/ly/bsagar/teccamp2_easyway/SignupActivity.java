package ly.bsagar.teccamp2_easyway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    EditText emailEdit;
    EditText passEdit;
    EditText repeatEdit;
    FirebaseAuth fireAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        emailEdit = findViewById(R.id.signupEmail);
        passEdit = findViewById(R.id.signupPassword);
        repeatEdit = findViewById(R.id.signupRPassword);
        fireAuth = FirebaseAuth.getInstance();
    }

    public void signup(View view) {
        String username = emailEdit.getText().toString();
        String password = passEdit.getText().toString();
        String rPassword = repeatEdit.getText().toString();

        if (checkCredintials(username,password, rPassword)){
            fireAuth.createUserWithEmailAndPassword(username,password).
                    addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser user = fireAuth.getCurrentUser();
                                Toast.makeText(SignupActivity.this, user.getUid(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignupActivity.this, AboutMeActivity.class));
                            } else {
                                Log.w("AUTH ERROR", "createUserWithEmail:failure",
                                        task.getException());
                                Toast.makeText(SignupActivity.this, "Authentication " +
                                                "failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private  boolean  checkCredintials(String email, String password, String rPassword) {
        if (!email.contains("@") || !email.endsWith(".com")){
            Toast.makeText(this, "please input a valid email that ends with .com", Toast.LENGTH_SHORT).show();
            emailEdit.setError("please input a valid email");
            return false;
        }
        if (password.length() < 8) {
            Toast.makeText(this, "password must be >= 8", Toast.LENGTH_SHORT).show();
            passEdit.setError("password must be >= 8");
            return false;
        }
        if (!password.equals(rPassword)){
            Toast.makeText(this, "passwords must match", Toast.LENGTH_SHORT).show();
            repeatEdit.setError("passwords must match");
            return false;
        }
        return true;
    }

}
