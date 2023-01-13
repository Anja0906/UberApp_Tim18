package com.example.uberapp_tim18.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uberapp_tim18.R;

import model.User;
import tools.Mockup;

public class UserAdapter extends BaseAdapter {
    private Activity activity;
    private User user;

    public UserAdapter(Activity _activity, User _user) {

        this.activity = _activity;
        this.user = _user;
    }

    /*
     * Ova metoda vraca ukupan broj elemenata u listi koje treba prikazati
     * */
    @Override
    public int getCount() {
        return Mockup.getUsers().size();
    }

    /*
     * Ova metoda vraca pojedinacan element na osnovu pozicije
     * */
    @Override
    public User getItem(int position) {
        return Mockup.getUsers().get(position);
    }


    /*
     * Ova metoda vraca jedinstveni identifikator, za adaptere koji prikazuju
     * listu ili niz, pozicija je dovoljno dobra. Naravno mozemo iskoristiti i
     * jedinstveni identifikator objekta, ako on postoji.
     * */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
     * Ova metoda popunjava pojedinacan element ListView-a podacima.
     * Ako adapter cuva listu od n elemenata, adapter ce u petlji ici
     * onoliko puta koliko getCount() vrati. Prilikom svake iteracije
     * uzece java objekat sa odredjene poziciuje (model) koji cuva podatke,
     * i layout koji treba da prikaze te podatke (view) npr R.layout.cinema_list.
     * Kada adapter ima model i view, prosto ce uzeti podatke iz modela,
     * popuniti view podacima i poslati listview da prikaze, i nastavice
     * sledecu iteraciju.
     * */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        User user = Mockup.getUsers().get(position);

        if(convertView==null)
            vi = activity.getLayoutInflater().inflate(R.layout.activity_passenger_inbox, null);

        TextView from_name = (TextView)vi.findViewById(R.id.from_name_txt_view);
        /*
        TextView from_content = (TextView)vi.findViewById(R.id.from_content_txt_view);
        TextView mess_date = (TextView)vi.findViewById(R.id.from_date_txt_view);
        TextView mess_time = (TextView)vi.findViewById(R.id.from_time_txt_view);
        */
        ImageView image = (ImageView)vi.findViewById(R.id.from_pfp_view);

        int pfp = user.getProfilePicture();
        String fromName = user.getName() + " " + user.getSurname();


        from_name.setText(fromName);

        if (pfp != -1){
            image.setImageResource(pfp);
        }


        return vi;
    }
}
