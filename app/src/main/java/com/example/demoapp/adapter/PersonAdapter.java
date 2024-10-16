package com.example.demoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demoapp.R;
import com.example.demoapp.domains.Person;

import java.util.List;

import networkapi.APIServer;
import networkapi.Links;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    private Context context;
    private List<Person> personList;

    public PersonAdapter(Context context, List<Person> personList) {
        this.context = context;
        this.personList = personList;
    }

    public PersonAdapter(List<Person> personList) {
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_person, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        Person person = personList.get(position);
        // Setting text for TextViews
        holder.nameTextView.setText(person.getName());
        holder.phoneTextView.setText(person.getPhoneNumber());
        holder.addressTextView.setText(person.getAddress());
        holder.genderTextView.setText(person.getGender());
        holder.categoryTextView.setText(person.getCategory());
        holder.sectorTextView.setText(person.getSector());
        holder.tpIdTextView.setText(person.getTpId());
        if (person.getPhoto() != null) {
//            try {
            String imageName = person.getPhoto();
            String url = Links.POI_IMAGE + "/" + imageName;
            Glide.with(context)
                    .load(new APIServer(context).loadImageFromServer(url))
                    //.placeholder(R.drawable.place_holder_image)
                    .into(holder.personImageView);
        }
        // Loading the profile image using Glide

    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        ImageView personImageView;
        TextView nameTextView, phoneTextView, addressTextView, genderTextView, categoryTextView, sectorTextView, tpIdTextView;
        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            personImageView = itemView.findViewById(R.id.person_image);
            nameTextView = itemView.findViewById(R.id.person_name);
            phoneTextView = itemView.findViewById(R.id.person_phone);
            addressTextView = itemView.findViewById(R.id.person_address);
                    genderTextView=itemView.findViewById(R.id.person_gender);
            categoryTextView=itemView.findViewById(R.id.person_category);
                    sectorTextView=itemView.findViewById(R.id.person_sector);
            tpIdTextView=itemView.findViewById(R.id.person_tp_id);
        }
    }
}
