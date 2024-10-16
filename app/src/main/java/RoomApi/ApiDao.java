package RoomApi;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ApiDao {
    @Insert
    void insert(UserEntity user);

    @Insert
    void insert(List<UserEntity> users); // Batch insert

    @Update
    void update(UserEntity user);

    @Query("SELECT * FROM user_table")
    List<UserEntity> getAllUsers();

    @Query("SELECT * FROM user_table WHERE id = :userId")
    UserEntity getUserById(int userId);
}
