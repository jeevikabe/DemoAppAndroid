package RoomApi;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserEntity.class}, version = 1)
public abstract class ApiDatabase extends RoomDatabase {
    private static volatile ApiDatabase INSTANCE;

    public abstract ApiDao apiDao();

    public static ApiDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ApiDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ApiDatabase.class, "user_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

