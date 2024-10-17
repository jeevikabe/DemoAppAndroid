package Slider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.demoapp.R;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.ViewHolder> {
      //new branch
private final int[] new resources;
    
    private final int[] imageResources;

    public CarouselAdapter(int[] imageResources) {
        this.imageResources = imageResources;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carousel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(imageResources[position]);

        // Adjust ImageView size programmatically if needed
        ViewGroup.LayoutParams params = holder.imageView.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT; // Set width to match parent
        params.height = 800; // Set a fixed height in pixels (or use dp)
        holder.imageView.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return imageResources.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
