package uk.co.alexks.deadliner.ui;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;

import java.util.List;

import uk.co.alexks.deadliner.Database.DeadlineDBHelper;
import uk.co.alexks.deadliner.adapter.DeadlineAdapter;
import uk.co.alexks.deadliner.R;
import uk.co.alexks.deadliner.Database.DeadlineData;
import uk.co.alexks.deadliner.model.ListItem;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recView;
    private DeadlineAdapter adapter;
    private DeadlineDBHelper DbHelper;
    private SQLiteDatabase db;
    private ItemTouchHelper itemTouchHelper;
    private DeadlineData deadlineData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DbHelper = new DeadlineDBHelper(this);
        db = DbHelper.getReadableDatabase();
        deadlineData = new DeadlineData(DbHelper, db);
        List<ListItem> data = deadlineData.getAllData();

        if(data.toString()=="[]")
        {
            TextView noData = (TextView)findViewById(R.id.no_data_label);
            noData.setVisibility(View.VISIBLE);
            //No data
        }
        else
        {
            recView = (RecyclerView)findViewById(R.id.recList);
            recView.setLayoutManager(new LinearLayoutManager(this));

            adapter = new DeadlineAdapter(data, this);
            recView.setAdapter(adapter);

            itemTouchHelper = new ItemTouchHelper(createHelperCallback());
            itemTouchHelper.attachToRecyclerView(recView);
        }
    }

    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        //moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                        return true;
                    }

                    @Override
                    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        //Get ID of card uo here and pass in?
                        TextView idView = (TextView)viewHolder.itemView.findViewById(R.id.dbId);
                        String id = idView.getText().toString();
                        deleteItem(viewHolder.getAdapterPosition(), id);
                    }
                };
        return simpleItemTouchCallback;
    }

    private void deleteItem(final int pos, String id){
        deadlineData.removeRow(id);
        adapter.notifyItemRemoved(pos);
    }

    public void loadAddIntent(View view){
        Intent addIntent = new Intent(this, AddDeadlineActivity.class);
        startActivity(addIntent);
    }

}
