package ly.bsagar.teccamp2_easyway;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onResume() {
        super.onResume();
        // check if user is authenticated
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            startActivity(new Intent(this, AboutMeActivity.class));
        }
    }
    // login with firebase UI
    public void Login(View view) {
        // add the list of providers ( don't forget to add them in the authentication portal
        List<AuthUI.IdpConfig> loginMethods = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());
        //make an intent with the given providers and display it to the user
        AuthUI ui = AuthUI.getInstance();
        Intent i = ui.createSignInIntentBuilder().
                setAvailableProviders(loginMethods).build();
        startActivityForResult(i, LOGINCODE);
    }
    // intent call back, we should check if auth is successful or not
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGINCODE) {
            // if auth is successful read the loged in user
            if (resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(this, "uer is : " + user.getUid(), Toast.LENGTH_SHORT).show();
                // if there is an error display an error message
            } else {
                Toast.makeText(this, "Error login", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // using firebase AUTH manually
    public void SignUP(View view) {
        startActivity(new Intent(this, SignupActivity.class));
    }

    public void SignIN(View view) {
        startActivity(new Intent(this, SigninActivity.class));
    }
}
