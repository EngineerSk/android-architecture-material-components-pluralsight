package com.engineersk.androidbasicsmenus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.PopupMenu;

import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private TextView mTextView;
    private Button mButton;
    private ActionMode mActionMode;
    final private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.actionmode_contextual_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            int itemId = menuItem.getItemId();
            if(itemId == R.id.action_mode_1)
                Toast.makeText(MainActivity.this, "Action mode 1 clicked",
                        Toast.LENGTH_LONG).show();
            else if(itemId == R.id.action_mode_2)
                Toast.makeText(MainActivity.this, "Action mode 2 clicked",
                        Toast.LENGTH_LONG).show();
            else if(itemId == R.id.action_mode_3)
                Toast.makeText(MainActivity.this, "Action mode 3 clicked",
                        Toast.LENGTH_LONG).show();
            else
                return false;

            actionMode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            mActionMode = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.text_view);
        this.registerForContextMenu(mTextView);

        mButton = findViewById(R.id.button_action_mode_id);
        mButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(mActionMode != null)
                    return false;

                mActionMode = startSupportActionMode(mActionModeCallback);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);

        Intent intent = new Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, "CHECK OUT THE MENU COURSE APP!!!");
        menu.addIntentOptions(
                R.id.intent_group,
                0, 0, this.getComponentName(),
                null, intent, 0, null);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.isChecked())
            item.setChecked(false);
        else
            item.setChecked(true);

        int itemId = item.getItemId();
        if(itemId == R.id.menu_item1)
            Toast.makeText(this, "Item 1 selected", Toast.LENGTH_LONG).show();
        else if(itemId == R.id.menu_item2)
            Toast.makeText(this, "Item 2 selected", Toast.LENGTH_LONG).show();
        else if(itemId == R.id.menu_item3)
            Toast.makeText(this, "Item 3 selected", Toast.LENGTH_LONG).show();
        else
            return super.onOptionsItemSelected(item);

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater  = this.getMenuInflater();
        inflater.inflate(R.menu.floating_contextual_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.context_menu_1)
            Toast.makeText(this, "Context Menu 1 Selected", Toast.LENGTH_LONG).show();
        else if(itemId == R.id.context_menu_2)
            Toast.makeText(this, "Context Menu 2 Selected", Toast.LENGTH_LONG).show();
        else if(itemId == R.id.context_menu_3)
            Toast.makeText(this, "Context Menu 3 Selected", Toast.LENGTH_LONG).show();
        else
            return super.onContextItemSelected(item);

        return true;
    }

    public void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.popup_item1)
            Toast.makeText(this, "Popup item 1 clicked", Toast.LENGTH_LONG).show();
        else if(itemId == R.id.popup_item2)
            Toast.makeText(this, "Popup item 2 clicked", Toast.LENGTH_LONG).show();
        else if(itemId == R.id.popup_item3)
            Toast.makeText(this, "Popup item 3 clicked", Toast.LENGTH_LONG).show();
        else
            return false;
        return true;
    }

    public void showListViewActivity(View view) {
        Intent intent = new Intent(this, ListViewActivity.class);
        startActivity(intent);
        finish();
    }
}