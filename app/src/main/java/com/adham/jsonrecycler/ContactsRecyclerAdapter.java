package com.adham.jsonrecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Adham on 2017 1126.
 */

public class ContactsRecyclerAdapter extends RecyclerView.Adapter<ContactsRecyclerAdapter.ContactViewHolder> implements Filterable {

    ArrayList<Contact> Contacts;
    ArrayList<Contact> ContactsOrginal;
    Context context;

    public ContactsRecyclerAdapter(ArrayList<Contact> contacts, Context context) {
        this.Contacts = new ArrayList<Contact>();
        this.Contacts.addAll(contacts);
        this.ContactsOrginal = new ArrayList<Contact>();
        this.ContactsOrginal.addAll(contacts);
        this.context = context;
    }

    @Override
    public ContactsRecyclerAdapter.ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.contact_recycler_item, parent, false);
        ContactViewHolder viewHolder = new ContactViewHolder(itemView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        final Contact contact = Contacts.get(position);
        holder.id.setText(contact.getId());
        holder.name.setText(contact.getName());
        holder.email.setText(contact.getEmail());
        holder.address.setText(contact.getAddress());
        holder.gender.setText(contact.getGender());
        holder.mobile.setText(contact.getMobile());
        holder.office.setText(contact.getOffice());
        holder.home.setText(contact.getHome());
    }

    @Override
    public int getItemCount() {
        return Contacts.size();
    }

    @Override
    public Filter getFilter() {
        return new MyFilter();
    }

    private class MyFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Contacts.clear();
            String query = constraint.toString().toLowerCase();
            if (query.isEmpty()) {
                Contacts.addAll(ContactsOrginal);
            } else {

                for (Contact contact : ContactsOrginal) {
                    if (contact.getName().toLowerCase().contains(query)) {
                        Contacts.add(contact);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = Contacts;
            results.count = Contacts.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            notifyDataSetChanged();
        }
    }

    // inner class ViewHolder used by the parent Adapter class
      class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView name;
        TextView email;
        TextView address;
        TextView gender;
        TextView mobile;
        TextView home;
        TextView office;
        View view;

         ContactViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;

            id = view.findViewById(R.id.tvId);
            name = view.findViewById(R.id.tvName);
            email = view.findViewById(R.id.tvEmail);
            address = view.findViewById(R.id.tvAddress);
            gender = view.findViewById(R.id.tvGender);
            mobile = view.findViewById(R.id.tvMobile);
            home = view.findViewById(R.id.tvHome);
            office = view.findViewById(R.id.tvOffice);



        }
    }
}
