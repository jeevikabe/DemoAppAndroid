package networkapi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AuthResponse implements Serializable {

    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("permittedFeatures")
    @Expose
    private List<String> permittedFeatures = null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getPermittedFeatures() {
        return permittedFeatures;
    }

    public void setPermittedFeatures(List<String> permittedFeatures) {
        this.permittedFeatures = permittedFeatures;
    }
}