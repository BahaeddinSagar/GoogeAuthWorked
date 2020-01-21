package ly.bsagar.teccamp2_easyway;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.List;

public class ChatArrayAdapter extends ArrayAdapter<ChatMessage> {
    public ChatArrayAdapter(@NonNull Context context, int resource, @NonNull List<ChatMessage> objects) {
        super(context, resource, objects);
    }
    FirebaseUser user;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.item_layout, parent, false);
        }

        LinearLayout linearLayout = convertView.findViewById(R.id.PARENT);
        TextView uidText = convertView.findViewById(R.id.uid);
        TextView dateText = convertView.findViewById(R.id.date);
        TextView message = convertView.findViewById(R.id.body);

        ChatMessage ch = getItem(position);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.getUid().equals(ch.uid)){
            linearLayout.setGravity(Gravity.END);
            linearLayout.setBackgroundColor(Color.DKGRAY);
        }

        uidText.setText(ch.uid);
        dateText.setText(ch.date);
        message.setText(ch.body);




        return  convertView;
    }
}
