package examples.gonzasosa.outlook.com.swinfoapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import examples.gonzasosa.outlook.com.swinfoapp.Models.SWApiPeople;
import examples.gonzasosa.outlook.com.swinfoapp.Models.SWApiPeopleHeader;
import examples.gonzasosa.outlook.com.swinfoapp.R;
import examples.gonzasosa.outlook.com.swinfoapp.Utils.DownloadAsyncTask;
import examples.gonzasosa.outlook.com.swinfoapp.Utils.URLS;

public class PeopleFragment extends Fragment{
    public ArrayList<SWApiPeople> peo = new ArrayList<>();
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.fragment_baseapi, container, false);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentActivity activity = getActivity ();
        if (activity == null) return;

        recyclerView = activity.findViewById(R.id.baseApiList);
        recyclerView.setLayoutManager (new LinearLayoutManager(getActivity()));

        new DownloadAsyncTask(s -> parseJSON(s)).execute(URLS.SW_API_PEOPLE_URL);


    }

    private void parseJSON (String json) {
        //los datos descargados en formato JSON se convierten en su representación java utilizando la clase especificada

        SWApiPeopleHeader people = new Gson().fromJson (json, SWApiPeopleHeader.class);
        if (people == null) return;
        if (people.next != null) {
            //si existe otro conjunto de resultados, se descarga de forma asíncrona
            new DownloadAsyncTask (this::parseJSON).execute (people.next);
        } else {
            //cuando ya no hay más conjuntos de resultados en el campo next, los obtenidos se pasan al adapter del recyclerview
            peo.addAll(people.results);
            recyclerView.setAdapter (new PeopleAdapter(peo));
        }
        peo.addAll (people.results);

        //Log.i (URLS.TAG, "" + planets.results.size ());
    }

    class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder> {

        private ArrayList<SWApiPeople> data;

        PeopleAdapter (ArrayList<SWApiPeople> d) {
            data = d;
        }

        @NonNull
        @Override
        public PeopleViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from (parent.getContext ());
            View view = inflater.inflate (R.layout.list_item, parent, false);
            return new PeopleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PeopleViewHolder peopleViewHolder, int i) {
            SWApiPeople persona = data.get (i);
            peopleViewHolder.setData (persona.name, persona.birth_year, persona.height);
        }

//        @Override
//        public void onBindViewHolder (@NonNull BaseApiFragment.BaseApiAdapter.BaseApiViewHolder holder, int position) {
//            switch (boton){
//                case 1:
//
//                    break;
//                case 2:
//                    SWApiStarships nave = (SWApiStarships) data.get (position);
//                    holder.setData (nave.name, nave.cargo_capacity, nave.model);
//                    break;
//                case 3:
//                    SWApiFilms peli = (SWApiFilms) data.get (position);
//                    holder.setData (peli.title, peli.director, peli.producer);
//                    break;
//                case 4:
//                    SWApiVehicles vehi = (SWApiVehicles) data.get (position);
//                    holder.setData (vehi.name, vehi.length, vehi.cargo_capacity);
//                    break;
//                default:
//                    SWApiSpecies especie = (SWApiSpecies) data.get (position);
//                    holder.setData (especie.name, especie.language, especie.classification);
//                    break;
//            }
//        }

        @Override
        public int getItemCount () {
            return data.size ();
        }

        class PeopleViewHolder extends RecyclerView.ViewHolder {
            TextView tvData1, tvData2, tvData3;

            PeopleViewHolder (View itemView) {
                super (itemView);
                tvData1 = itemView.findViewById (R.id.tvData1);
                tvData2 = itemView.findViewById (R.id.tvData2);
                tvData3 = itemView.findViewById (R.id.tvData3);
            }

            void setData (String data1, String data2, String data3) {
                tvData1.setText (data1);
                tvData2.setText (data2);
                tvData3.setText (data3);
            }
        }
    }
}
