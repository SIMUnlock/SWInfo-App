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

public class VehiclesFragment extends Fragment{
    public ArrayList<SWApiVehicles> peo = new ArrayList<>();
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

        new DownloadAsyncTask(s -> parseJSON(s)).execute(URLS.SW_API_VEHICLES);


    }

    private void parseJSON (String json) {
        SWApiVehiclesHeader vehicle = new Gson().fromJson (json, SWApiVehiclesHeader.class);
        if (vehicle == null) return;
        if (vehicle.next != null) {
            new DownloadAsyncTask (this::parseJSON).execute (vehicle.next);
        } else {
            peo.addAll(vehicle.results);
            recyclerView.setAdapter (new VehiclesAdapter(peo));
        }
        peo.addAll (vehicle.results);
    }

    class VehiclesAdapter extends RecyclerView.Adapter<VehiclesAdapter.VehiclesViewHolder> {
        private ArrayList<SWApiVehicles> data;
        VehiclesAdapter(ArrayList<SWApiVehicles> d) {
            data = d;
        }

        @NonNull
        @Override
        public VehiclesViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from (parent.getContext ());
            View view = inflater.inflate (R.layout.list_item, parent, false);
            return new VehiclesViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull VehiclesViewHolder filmsViewHolder, int i) {
            SWApiVehicles vehiculo = data.get (i);
            filmsViewHolder.setData (vehiculo.model, vehiculo.length, vehiculo.consumables);
        }

        @Override
        public int getItemCount () {
            return data.size ();
        }

        class VehiclesViewHolder extends RecyclerView.ViewHolder {
            TextView tvData1, tvData2, tvData3;

            VehiclesViewHolder (View itemView) {
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
