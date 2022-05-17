package com.example.android.roomwordssample;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;



@Dao
public interface WordDao {


    @Query("SELECT * from word_table ORDER BY word ASC")
    LiveData<List<Word>> getAlphabetizedWords();

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    @Query("DELETE FROM word_table")
    void deleteAll();

    @Query("DELETE FROM word_table where word =:name ")
    void deleteByName(String name);

    @Query("update word_table set word=:name where word =:name ")
    void update(String name);
}
