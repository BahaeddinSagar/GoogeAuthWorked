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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AboutMeActivity extends AppCompatActivity {
    EditText nameEdit;
    EditText emailEdit;
    EditText phoneEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        nameEdit = findViewById(R.id.firebaseusername);
        emailEdit = findViewById(R.id.firebaseemail);
        phoneEdit = findViewById(R.id.firebasephone);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.getDisplayName() != null) {
            nameEdit.setText(user.getDisplayName());
        } else {
            nameEdit.setText("N/A");
        }
        emailEdit.setText(user.getEmail());
        phoneEdit.setText(user.getPhoneNumber());

    }

    public void UPDATE(View view) {
        String name = nameEdit.getText().toString();
        String email = emailEdit.getText().toString();
        String phone = phoneEdit.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name).build();
        user.updateProfile(profileUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AboutMeActivity.this, "IT\"s DONE", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Toast.makeText(AboutMeActivity.this, "IT\"s NOT DONE", Toast.LENGTH_SHORT).show();

            }
        });

        user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AboutMeActivity.this, "IT\"s DONE", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Toast.makeText(AboutMeActivity.this, "IT\"s NOT DONE", Toast.LENGTH_SHORT).show();

            }
        });



    }

    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        // this flag removes intermediate activities between this activity and main activity.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void WRITE(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        ref.child("TestData").setValue(new ChatMessage("1","first message",
                "this is body","12367v","Today")).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AboutMeActivity.this, "IT\"S DONE", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Toast.makeText(AboutMeActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void READ(View view) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("TestData");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("TESTDATA", "onDataChange: "+dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("NOGOOD", "onCancelled: "+ databaseError.getDetails());
            }
        });

    }

    public void Chat(View view) {
        startActivity(new Intent(this,ChatActivity.class));
    }
}
