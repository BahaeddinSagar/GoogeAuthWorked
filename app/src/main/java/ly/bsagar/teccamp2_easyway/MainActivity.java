package ly.bsagar.teccamp2_easyway;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final int LOGINCODE = 9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void Login(View view) {
        List<AuthUI.IdpConfig> loginMethods = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());
        AuthUI ui = AuthUI.getInstance();
        Intent i = ui.createSignInIntentBuilder().
                setAvailableProviders(loginMethods).build();
        startActivityForResult(i, LOGINCODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGINCODE) {
            if (resultCode == RESULT_OK){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(this, "uer is : " + user.getUid(), Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Error login", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
