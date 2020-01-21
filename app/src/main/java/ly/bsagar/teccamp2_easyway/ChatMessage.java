package ly.bsagar.teccamp2_easyway;

public class ChatMessage {
    public String id;
    public String title;
    public String body;
    public String uid;
    public String date;

    public ChatMessage() {
    }

    public ChatMessage(String id, String title, String body, String uid, String date) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.uid = uid;
        this.date = date;
    }
}
