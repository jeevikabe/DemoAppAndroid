package Roomdatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   void insertOrUpdateUsers(List<User> users);

 //@Insert(onConflict = OnConflictStrategy.REPLACE)
 //void insertOrUpdateApiData(List<ApiData> apiData);
    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Query("SELECT * FROM users WHERE id = :userId")
    User getUserById(int userId);

    @Query("SELECT * FROM users WHERE name = :username")
    User getUserByName(String username);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

}
