package com.example.demoapp;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class DataTransferFragment extends Fragment {
    private SharedViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_transfer, container, false);

        TextView receivedMessageTextView = view.findViewById(R.id.received_message_text_view);

        // Observe LiveData from ViewModel to get data from Activity
        viewModel.getSelected().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                // Display the message received from Activity
                receivedMessageTextView.setText(message);
            }
        });

        Button sendDataButton = view.findViewById(R.id.send_data_button);
        sendDataButton.setOnClickListener(v -> {
            String dataToPass = "Hello from Fragment!";
            viewModel.select(dataToPass); // Pass data back to MainActivity
        });

        return view;
    }
}
