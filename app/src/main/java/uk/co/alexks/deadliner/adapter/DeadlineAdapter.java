package uk.co.alexks.deadliner.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import uk.co.alexks.deadliner.R;
import uk.co.alexks.deadliner.model.ListItem;
import uk.co.alexks.deadliner.ui.AddDeadlineActivity;

/**
 * Created by ashaw on 08/03/2017.
 */

public class DeadlineAdapter extends RecyclerView.Adapter<DeadlineAdapter.DeadlineHolder>{

    class DeadlineHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView deadlineName;
        private TextView deadlineType;
        private TextView deadlineNotes;
        private TextView deadlineTime;
        private TextView deadlineDate;
        private TextView deadlineNotifyTime;
        private TextView deadlineNotifyUnits;
        private TextView deadlineId;
        private View container;

        public DeadlineHolder(View itemView) {
            super(itemView);

            deadlineName = (TextView)itemView.findViewById(R.id.deadlineNameText);
            deadlineType = (TextView)itemView.findViewById(R.id.deadlineTypeText);
            deadlineNotes = (TextView)itemView.findViewById(R.id.deadlineNotesText);
            deadlineDate = (TextView)itemView.findViewById(R.id.deadlineDateText);
            deadlineTime = (TextView)itemView.findViewById(R.id.deadlineTimeText);
            deadlineNotifyTime = (TextView)itemView.findViewById(R.id.timeAmmountText);
            deadlineNotifyUnits = (TextView)itemView.findViewById(R.id.timeUnitText);
            deadlineId = (TextView)itemView.findViewById(R.id.dbId);
            container = itemView.findViewById(R.id.deadline_card);
            container.setOnClickListener(this);

        }

        @Override
        public void onClick(View v){
            editDeadline();
        }

        private void editDeadline(){
            //Intent edit = new Intent(itemView.getContext(), AddDeadlineActivity.class);
            //itemView.getContext().startActivity(edit);
        }
    }

    private List<ListItem> listData;
    private LayoutInflater inflater;

    public DeadlineAdapter(List<ListItem> listData, Context c){
        inflater = LayoutInflater.from(c);
        this.listData = listData;
    }

    @Override
    public DeadlineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.deadline_card, parent, false);
        return new DeadlineHolder(view);
    }

    @Override
    public void onBindViewHolder(DeadlineHolder holder, int position) {
        ListItem item = listData.get(position);
        holder.deadlineName.setText(item.getName());
        holder.deadlineType.setText(item.getType());
        holder.deadlineNotes.setText(item.getNote());
        holder.deadlineDate.setText(item.getDate());
        holder.deadlineTime.setText(item.getTime());
        holder.deadlineNotifyTime.setText(item.getNotifyTime());
        holder.deadlineNotifyUnits.setText(item.getNotifyUnit());
        holder.deadlineId.setText(item.getDbId());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

}
