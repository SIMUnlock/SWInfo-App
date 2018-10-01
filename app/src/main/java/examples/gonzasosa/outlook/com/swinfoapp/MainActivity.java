package examples.gonzasosa.outlook.com.swinfoapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import examples.gonzasosa.outlook.com.swinfoapp.Fragments.BaseApiFragment;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("Star Wars Info App");
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);;
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater ().inflate (R.menu.menu, menu);
        return true;
    }

    @Override

    public boolean onOptionsItemSelected (MenuItem item) {
        Intent intent = new Intent (this, DetailsActivity.class);
        switch (item.getItemId ()) {
            case R.id.mnu_item_one:
                intent.putExtra("btn",1);
                break;
            case R.id.mnu_item_two:
                intent.putExtra("btn",2);
                break;
            case R.id.mnu_item_three:
                intent.putExtra("btn",3);
                break;
            case R.id.mnu_item_four:
                intent.putExtra("btn",4);
                break;
            case R.id.mnu_item_five:
                intent.putExtra("btn",5);
                break;
        }
        startActivity (intent);
        return true;
    }
}
