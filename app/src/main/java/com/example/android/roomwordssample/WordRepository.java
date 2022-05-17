package com.example.android.roomwordssample;



import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */

public class WordRepository {

    private final WordDao mWordDao;
    private final LiveData<List<Word>> mAllWords;


    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAlphabetizedWords();
    }


    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }


    public void insert (Word word) {
        new insertAsyncTask(mWordDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {

        private final WordDao mAsyncTaskDao;

        insertAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    public void deleteByName (Word word) {
        new deleteAsyncTask(mWordDao).execute(word);
    }

    private static class deleteAsyncTask extends AsyncTask<Word, Void, Void> {

        private final WordDao mAsyncTaskDao;

        deleteAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.deleteByName(params[0].getWord());
            return null;
        }
    }

    public void update (Word word) {
        new updateAsyncTask(mWordDao).execute(word);
    }

    private static class updateAsyncTask extends AsyncTask<Word, Void, Void> {

        private final WordDao mAsyncTaskDao;

        updateAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.update(params[0].getWord());
            return null;
        }
    }
}
