package ApiStoreRoomdb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

    @Entity(tableName = "users")
    public class Post {
        @PrimaryKey(autoGenerate = true)
    private int userId;
    private int id;
    private String title;
    private String body;

        public Post(String title, String body, int i, String s) {

        }

        // Getters and setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
}
