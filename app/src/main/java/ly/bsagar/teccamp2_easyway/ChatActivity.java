package ly.bsagar.teccamp2_easyway;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    EditText editText;
    ListView listView;
    FirebaseUser currentUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        editText = findViewById(R.id.editText);
        listView = findViewById(R.id.listview);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("TestData");

    }

    @Override
    protected void onStart() {
        super.onStart();
        final ArrayList<ChatMessage> chatMessages = new ArrayList<>();
        final ChatArrayAdapter arrayAdapter = new ChatArrayAdapter(this,
                R.layout.item_layout,chatMessages);
        listView.setAdapter(arrayAdapter);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatMessages.clear();
                for ( DataSnapshot kid : dataSnapshot.getChildren()){
                    ChatMessage ch = kid.getValue(ChatMessage.class);
                    chatMessages.add(ch);
                }
                arrayAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void send(View view) {
        String message = editText.getText().toString();
        if (message.isEmpty()){
            Toast.makeText(this, "Empty message", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = currentUser.getUid();
        String date = LocalDateTime.now().getDayOfMonth()+"-"
                +LocalDateTime.now().getMonth()+"-"+
                LocalDateTime.now().getYear();
        String id = ref.push().getKey();
        ChatMessage chatMessage = new ChatMessage(id,"title",message,uid,date);
        ref.child(id).setValue(chatMessage);
        editText.setText("");
    }

}
