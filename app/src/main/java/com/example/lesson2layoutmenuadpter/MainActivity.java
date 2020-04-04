package com.example.lesson2layoutmenuadpter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lesson2layoutmenuadpter.productview.ProductGridFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            String token = this.getToken();
            if(token != null && !token.isEmpty())
            {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, new ProductGridFragment())
                        .commit();
            }
            else{
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, new LoginFragment())
                        .commit();
            }
        }
//        new MaterialAlertDialogBuilder(MainActivity.this)
//                .setTitle("Title")
//                .setMessage("Message")
//                .setPositiveButton("Ok", null)
//                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch(item.getItemId())
        {
            case R.id.table:
                this.navigateTo(new ProductGridFragment(), true);
                //Toast.makeText(this, "Зробити фото", Toast.LENGTH_LONG).show();
                //intent = new Intent(this, TableActivity.class);
                //startActivity(intent);
                return true;
            case R.id.home:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.listviewitem:
                intent = new Intent(this, ListViewActivity.class);
                startActivity(intent);
                return true;
            case R.id.listviewsimpleitem:
                intent = new Intent(this, ListViewSimpleActivity.class);
                startActivity(intent);
                return true;
            case R.id.edit:
                Toast.makeText(this, "Редагування", Toast.LENGTH_LONG).show();
                return true;
            case R.id.edit_copy:
                Toast.makeText(this, "Копіювати", Toast.LENGTH_LONG).show();
                return true;
            case R.id.edit_past:
                Toast.makeText(this, "Вставити", Toast.LENGTH_LONG).show();
                return true;
            case R.id.logout:
                this.removeToken();
                this.navigateTo(new LoginFragment(), false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
