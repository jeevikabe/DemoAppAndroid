package ApiStoreRoomdb;

import com.example.demoapp.domains.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("posts")
    Call<List<UserModel>> getPosts();
}
