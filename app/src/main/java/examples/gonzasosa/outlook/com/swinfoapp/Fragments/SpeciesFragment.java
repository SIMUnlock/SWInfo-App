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

import examples.gonzasosa.outlook.com.swinfoapp.Models.SWApiSpecies;
import examples.gonzasosa.outlook.com.swinfoapp.Models.SWApiSpeciesHeader;
import examples.gonzasosa.outlook.com.swinfoapp.R;
import examples.gonzasosa.outlook.com.swinfoapp.Utils.DownloadAsyncTask;
import examples.gonzasosa.outlook.com.swinfoapp.Utils.URLS;

public class SpeciesFragment extends Fragment{
    public ArrayList<SWApiSpecies> species = new ArrayList<>();
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

        new DownloadAsyncTask(s -> parseJSON(s)).execute(URLS.SW_API_SPECIES);


    }

    private void parseJSON (String json) {
        SWApiSpeciesHeader especie = new Gson().fromJson (json, SWApiSpeciesHeader.class);
        if (especie == null) return;
        if (especie.next != null) {
            new DownloadAsyncTask (this::parseJSON).execute (especie.next);
        } else {
            species.addAll(especie.results);
            recyclerView.setAdapter (new SpeciesAdapter(species));
        }
        species.addAll (especie.results);
    }

    class SpeciesAdapter extends RecyclerView.Adapter<SpeciesAdapter.SpeciesViewHolder> {
        private ArrayList<SWApiSpecies> data;
        SpeciesAdapter (ArrayList<SWApiSpecies> d) {
            data = d;
        }

        @NonNull
        @Override
        public SpeciesViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from (parent.getContext ());
            View view = inflater.inflate (R.layout.list_item, parent, false);
            return new SpeciesViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SpeciesViewHolder speciesViewHolder, int i) {
            SWApiSpecies specie = data.get (i);
            speciesViewHolder.setData (specie.name, specie.language, specie.hair_colors);
        }

        @Override
        public int getItemCount () {
            return data.size ();
        }

        class SpeciesViewHolder extends RecyclerView.ViewHolder {
            TextView tvData1, tvData2, tvData3;

            SpeciesViewHolder (View itemView) {
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
