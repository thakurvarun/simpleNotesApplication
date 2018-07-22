package com.plaps.androidcleancode.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.plaps.androidcleancode.R;
import com.plaps.androidcleancode.database.data.Notes;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private final OnItemClickListener listener;
    private final onDeleteNoteListener deleteNoteListener;
    // Contains our notesListData
    private List<Notes> notesData;

    private Context context;

    public HomeAdapter(Context context, List<Notes> data, OnItemClickListener listener, onDeleteNoteListener deleteNoteListener) {
        this.notesData = data;
        this.deleteNoteListener = deleteNoteListener;
        this.listener = listener;
        this.context = context;
    }


    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_for_your_notes, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(HomeAdapter.ViewHolder holder, int position) {
        holder.click(notesData.get(position), listener);
        holder.delete(notesData.get(position), deleteNoteListener);
        holder.tvNotesTitle.setText(notesData.get(position).getTitle());
        holder.tvNotesDescription.setText(notesData.get(position).getDescription());

       /* String images = notesData.get(position).getBackground();

        Glide.with(context)
                .load(images)
                .into(holder.background);
*/
    }


    @Override
    public int getItemCount() {
        return notesData.size();
    }


    public interface OnItemClickListener {
        void onClick(Notes yourNote);
    }

    public interface onDeleteNoteListener {
        void onDeleteClick(Notes yourNote);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNotesTitle, tvNotesDescription;
        ImageView background;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNotesTitle = itemView.findViewById(R.id.noteTitle);
            tvNotesDescription = itemView.findViewById(R.id.notesDescription);
            background = itemView.findViewById(R.id.image);

        }


        public void click(final Notes yourNote, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(yourNote);
                }
            });
        }

        public void delete(final Notes yourNote, final onDeleteNoteListener listener) {

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onDeleteClick(yourNote);
                    return true;
                }
            });
        }
    }


}
