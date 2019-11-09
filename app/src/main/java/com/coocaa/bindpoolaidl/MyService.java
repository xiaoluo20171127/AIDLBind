package com.coocaa.bindpoolaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import java.time.chrono.MinguoChronology;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntBinaryOperator;

public class MyService extends Service {
    public static final int BOOK_TYPE = 1;
    public static final int PERSON_TYPE = 0;
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"connect is success",Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
       return new IBindPoolImpL();
    }

    public class IBindPoolImpL extends IBindPool.Stub {
        IBinder mIBinder;
        List<Book> mBookList;
        List<Person> mPersonList;
        public IBindPoolImpL(){
            mBookList = new ArrayList<>();
            Book book = new Book("tianlongbabu",110);
            mBookList.add(book);
            mPersonList = new ArrayList<>();
            Person person = new Person();
            person.setName("qiaofeng");
            person.setSex("man");
            mPersonList.add(person);
        }
        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            switch (binderCode){
                case PERSON_TYPE:
                    mIBinder = new BookManageImpL(mBookList);
                    break;
                case BOOK_TYPE:
                    mIBinder = new PersonManagerImpL(mPersonList);
                    break;
                default:
                    mIBinder = null;
            }
            return mIBinder;
        }
    }
}
