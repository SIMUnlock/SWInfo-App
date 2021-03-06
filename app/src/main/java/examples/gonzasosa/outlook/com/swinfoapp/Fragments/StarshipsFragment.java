package examples.gonzasosa.outlook.com.swinfoapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import examples.gonzasosa.outlook.com.swinfoapp.Models.*;
import examples.gonzasosa.outlook.com.swinfoapp.R;
import examples.gonzasosa.outlook.com.swinfoapp.Utils.DownloadAsyncTask;
import examples.gonzasosa.outlook.com.swinfoapp.Utils.URLS;

public class StarshipsFragment extends Fragment {
    public ArrayList<SWApiStarships> peo = new ArrayList<>();
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.fragment_baseapi, container, false);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentActivity activity = getActivity();
        if (activity == null) return;

        recyclerView = activity.findViewById(R.id.baseApiList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        new DownloadAsyncTask(s -> parseJSON(s)).execute(URLS.SW_API_STARSHIPS_URL);


    }

    private void parseJSON (String json) {
        //los datos descargados en formato JSON se convierten en su representación java utilizando la clase especificada

        SWApiStarshipsHeader naves = new Gson().fromJson (json, SWApiStarshipsHeader.class);
        if (naves == null) return;
        if (naves.next != null) {
            //si existe otro conjunto de resultados, se descarga de forma asíncrona
            new DownloadAsyncTask (this::parseJSON).execute (naves.next);
        } else {
            //cuando ya no hay más conjuntos de resultados en el campo next, los obtenidos se pasan al adapter del recyclerview
            peo.addAll(naves.results);
            recyclerView.setAdapter (new StarshipsAdapter(peo));
        }
        peo.addAll (naves.results);

        //Log.i (URLS.TAG, "" + planets.results.size ());
    }

    class StarshipsAdapter extends RecyclerView.Adapter<StarshipsAdapter.StarshipsViewHolder> {

        private ArrayList<SWApiStarships> data;

        StarshipsAdapter (ArrayList<SWApiStarships> d) {
            data = d;
        }

        @NonNull
        @Override
        public StarshipsViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from (parent.getContext ());
            View view = inflater.inflate (R.layout.list_item, parent, false);
            return new StarshipsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StarshipsViewHolder starshipsViewHolder, int i) {
            SWApiStarships persona = data.get (i);
            starshipsViewHolder.setData (persona.name, persona.consumables, persona.cargo_capacity);
        }

        @Override
        public int getItemCount () {
            return data.size ();
        }

        class StarshipsViewHolder extends RecyclerView.ViewHolder {
            TextView tvData1, tvData2, tvData3;

            StarshipsViewHolder (View itemView) {
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
