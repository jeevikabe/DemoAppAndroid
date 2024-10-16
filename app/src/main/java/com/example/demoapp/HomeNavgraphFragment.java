//package com.example.demoapp;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.navigation.NavController;
//import androidx.navigation.Navigation;
//
//public class HomeNavgraphFragment extends Fragment {
//
//@Nullable
//@Override
//public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//    View view = inflater.inflate(R.layout.fragment_home_navgraph, container, false);
//
//    Button button = view.findViewById(R.id.buttonToFragmentTwo);
//    button.setOnClickListener(v -> {
//        NavController navController = Navigation.findNavController(view);
//        navController.navigate(R.id.action_fragmentOne_to_fragmentTwo);
//    });
//
//    return view;
//}
//}

package com.example.demoapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.activity.OnBackPressedCallback;

public class HomeNavgraphFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_navgraph, container, false);

        Button button = view.findViewById(R.id.buttonToFragmentTwo);
        button.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_fragmentOne_to_fragmentTwo);
        });

        // Handle back press when the fragment is visible
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavController navController = Navigation.findNavController(view);
                if (navController.getCurrentDestination().getId() == R.id.fragmentOne) {
                    // If already on HomeNavgraphFragment, show a message or handle accordingly
                    Toast.makeText(requireContext(), "exit", Toast.LENGTH_SHORT).show();
                    requireActivity().finish(); // Optionally close the activity or pop back
                } else {
                    navController.popBackStack();
                }
            }
        });

        return view;
    }
}
