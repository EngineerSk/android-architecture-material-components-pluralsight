package com.engineersk.androidbasicsmenus;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class ListViewActivity extends AppCompatActivity {

    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;
    ArrayList<String> planetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        // Find the ListView resource.
        mainListView = (ListView) findViewById( R.id.mainListView );

        // Create and populate a List of planet names.
        String[] planets = new String[] { "Mercury", "Venus", "Earth", "Mars",
                "Jupiter", "Saturn", "Uranus", "Neptune"};
        planetList = new ArrayList<>();
        planetList.addAll( Arrays.asList(planets) );

        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<>(this, R.layout.list_simplerow, planetList);

        // Set the ArrayAdapter as the ListView's adapter.
        mainListView.setAdapter( listAdapter );
        mainListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        mainListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate(R.menu.listview_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if(itemId == R.id.listDelete)
                    deleteSelectedItems();
                else if(itemId == R.id.listRefresh)
                    deleteSelectedItems();
                else
                    return false;
                actionMode.finish();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }

            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

            }
        });
    }

    // delete selected items from list view
    private void deleteSelectedItems() {
        /** Getting the checked items from the listview */
        SparseBooleanArray checkedItemPositions = mainListView.getCheckedItemPositions();
        int itemCount = mainListView.getCount();

        for (int i = itemCount - 1; i >= 0; i--) {
            if (checkedItemPositions.get(i)) {
                listAdapter.remove(planetList.get(i));
            }
        }
        checkedItemPositions.clear();
        listAdapter.notifyDataSetChanged();
    }

}
