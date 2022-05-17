package com.example.android.roomwordssample;



import static androidx.core.app.ActivityCompat.startActivityForResult;

import static com.example.android.roomwordssample.MainActivity.NEW_WORD_ACTIVITY_REQUEST_CODE;

import android.app.AlertDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    AlertDialog.Builder builder;
    private Context context ;
    static class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private WordViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Word> mWords; // Cached copy of words
    private WordRepository wordRepository;

    WordListAdapter(Context context, WordRepository wordRepository) {
        mInflater = LayoutInflater.from(context);
        this.wordRepository = wordRepository;
        this.context=context;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        Word current = mWords.get(position);
        holder.wordItemView.setText(current.getWord());
        holder.itemView.setOnClickListener(view ->  {
            builder = new AlertDialog.Builder(context);
            builder.setMessage("chose one ")
                    .setCancelable(false)
                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            NewWordActivity.update = true;
                            NewWordActivity.name = current.getWord();

                            Intent intent = new Intent(context, NewWordActivity.class);

                           context.startActivity(intent);

                        }
                    })
                    .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.dismiss();
                            wordRepository.deleteByName(current);
                            Toast.makeText(context," 1 row deleted ",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("AlertDialogExample");
            alert.show();

        }  );
    }

    void setWords(List<Word> words) {
        mWords = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }
}


