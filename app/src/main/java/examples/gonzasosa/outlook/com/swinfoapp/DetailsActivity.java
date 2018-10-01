package examples.gonzasosa.outlook.com.swinfoapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import examples.gonzasosa.outlook.com.swinfoapp.Fragments.*;

public class DetailsActivity extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        int boton = getIntent().getIntExtra("btn", -1);

        switch (boton) {
            case 1:
                PeopleFragment PF = new PeopleFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.rootContainer, PF)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
                break;
            case 2:
                StarshipsFragment  SF = new StarshipsFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.rootContainer, SF)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
                break;
            case 3:
                FilmsFragment FF = new FilmsFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.rootContainer, FF)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
                break;
            case 4:
                VehiclesFragment VF = new VehiclesFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.rootContainer, VF)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
                break;
            default:
                SpeciesFragment SPF = new SpeciesFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.rootContainer, SPF)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
                break;
        }
    }

}
