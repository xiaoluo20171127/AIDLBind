package com.coocaa.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.coocaa.bindpoolaidl.Book;
import com.coocaa.bindpoolaidl.IBookManage;
import com.coocaa.bindpoolaidl.IPersonManage;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    IPersonManage mPersonManage;
    IBookManage mBookManage;
    Button mAddBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //如果这里不用子线程，会导致ANR错误，因为服务器的连接是个耗时的任务
                        startBinderPool();
                    }
                }).start();
            }
        });
        findViewById(R.id.addBook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mBookManage.addBook(new Book("shediaoyingxiongzhuang",123));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void startBinderPool() {
        BinderPool binderPool = BinderPool.getInstance(this);
        //第一个模块
        IBinder bookBinder = binderPool.queryBinder(BinderPool.TYPE_BOOK);
        mBookManage = IBookManage.Stub.asInterface(bookBinder);
        try {
            String name = mBookManage.getAllBook().get(0).getName();
            Log.e(TAG,name);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        //第二个模块
        IBinder binder = binderPool.queryBinder(BinderPool.TYPE_PERSON);
        mPersonManage = IPersonManage.Stub.asInterface(binder);
        try {
            String name = mPersonManage.getAllPerson().get(0).getName();
            Log.e(TAG,name);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
