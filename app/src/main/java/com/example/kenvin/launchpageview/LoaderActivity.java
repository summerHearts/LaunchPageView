package com.example.kenvin.launchpageview;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kenvin on 2017/11/6.
 */


public class LoaderActivity extends AppCompatActivity {

    @BindView(R.id.listView)
    ListView listView;

    private LoaderManager loaderManager;
    private ArrayAdapter<String> arrayAdapter ;
    private List<String> contactList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        ButterKnife.bind(this);

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contactList);
        listView.setAdapter(arrayAdapter);
        /**
         * 1.获得LoaderManager对象。
         * 2.通过LoaderManager初始化Loader。
         * 3.实现LoaderCallbacks接口。
         * 4.在onCreateLoader方法中，创建CursorLoader。
         * 5.在onLoadFinished方法中，获得加载数据，更新UI。
         */
        loaderManager = getSupportLoaderManager();

        //动态申请权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    0);
        } else {
            loaderManager.initLoader(0, null, mLoaderCallback);
        }

    }


    //callBack 实现
    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {

        private final String[] Contact_PROJECTION = {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            CursorLoader cursorLoader =
                    new CursorLoader(LoaderActivity.this,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, Contact_PROJECTION,
                            null, null, null);
            return cursorLoader;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

            //首先要清空数据源，避免重复数据
            contactList.clear();
            while (data.moveToNext()) {
                String displayName = data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                //分两行展示
                contactList.add(displayName + "\n" + number);
            }
            arrayAdapter.notifyDataSetChanged();
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            contactList.clear();
        }
    };



    @Override
    protected void onResume() {
        super.onResume();
        //loaderManager.initLoader(0,null,mLoaderCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loaderManager.destroyLoader(0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 0)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                loaderManager.initLoader(0, null, mLoaderCallback);
            }
        }
    }
}
