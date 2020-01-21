package ly.bsagar.teccamp2_easyway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SigninActivity extends AppCompatActivity {

    EditText emailEdit;
    EditText passEdit;
    FirebaseAuth fireAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        emailEdit = findViewById(R.id.signinEmail);
        passEdit = findViewById(R.id.signinPassword);

        fireAuth = FirebaseAuth.getInstance();
    }


    public void signin(View view) {
        String username = emailEdit.getText().toString();
        String password = passEdit.getText().toString();

        if (!username.isEmpty() && !password.isEmpty()){
            fireAuth.signInWithEmailAndPassword(username,password).
                    addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = fireAuth.getCurrentUser();
                            Toast.makeText(SigninActivity.this, user.getUid(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SigninActivity.this, AboutMeActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("AUTH ERROR", "signin :failure",
                            e);
                    Toast.makeText(SigninActivity.this, "Authentication " +
                                    "failed.",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
