package com.example.uberapp_tim18.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uberapp_tim18.R;

import java.util.List;

import DTO.UserDTO;
import model.User;
import retrofit.UserApi;
import tools.Mockup;

public class UserAdapter extends BaseAdapter {
    private Activity activity;
    private UserDTO user;
    private List<UserDTO> allUsers;

    public UserAdapter(Activity _activity) {
        this.activity = _activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<UserDTO> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<UserDTO> allUsers) {
        this.allUsers = allUsers;
    }

    /*
     * Ova metoda vraca ukupan broj elemenata u listi koje treba prikazati
     * */
    @Override
    public int getCount() {
        return allUsers.size();
    }

    /*
     * Ova metoda vraca pojedinacan element na osnovu pozicije
     * */
    @Override
    public UserDTO getItem(int position) {
        return allUsers.get(position);
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
        UserDTO user = allUsers.get(position);

        if(convertView==null)
            vi = activity.getLayoutInflater().inflate(R.layout.activity_passenger_inbox, null);

        TextView from_name = (TextView)vi.findViewById(R.id.from_name_txt_view);
        /*
        TextView from_content = (TextView)vi.findViewById(R.id.from_content_txt_view);
        TextView mess_date = (TextView)vi.findViewById(R.id.from_date_txt_view);
        TextView mess_time = (TextView)vi.findViewById(R.id.from_time_txt_view);
        */
        ImageView image = (ImageView)vi.findViewById(R.id.from_pfp_view);

//        int pfp = Integer.parseInt(user.getProfilePicture());
        String fromName = user.getName() + " " + user.getSurname();


        from_name.setText(fromName);

//        if (pfp != -1){
//            image.setImageResource(pfp);
//        }


        return vi;
    }
}
