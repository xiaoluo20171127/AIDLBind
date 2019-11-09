package com.coocaa.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.coocaa.bindpoolaidl.IBindPool;

import java.util.concurrent.CountDownLatch;

public class BinderPool {
    //上下文
    private Context mContext;
    //同步辅助类
    private CountDownLatch mCountDownLatch;
    //单例
    private static BinderPool mInstance;
    //获取AIDL代理对象的标识
    public static final int TYPE_BOOK = 0;
    public static final int TYPE_PERSON = 1;

    private IBindPool mBindPool;

    private BinderPool(Context context) {
        //获取上下文
        mContext = context.getApplicationContext();
        //连接远程服务
        connectBinderPoolService();
    }

    /**
     * 单例模式
     *
     * @param context
     * @return
     */
    public static BinderPool getInstance(Context context) {
        if (mInstance == null) {
            synchronized (BinderPool.class) {
                if (mInstance == null) {
                    mInstance = new BinderPool(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 提供该类的一个查询方法
     *
     * @param binderCode
     * @return
     */
    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        try {
            if ( mBindPool!= null) {
                binder = mBindPool.queryBinder(binderCode);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;
    }

    /**
     * 开启远程服务，并保持同步
     */
    private synchronized void connectBinderPoolService() {
        //同步辅助器，值为1
        mCountDownLatch = new CountDownLatch(1);
        //开启远程服务
        Intent intent = new Intent();
        intent.setPackage("com.coocaa.bindpoolaidl");
        intent.setAction("com.coocaa.bindservice");
        mContext.bindService(intent, conn, mContext.BIND_AUTO_CREATE);
        try {
            //同步辅助器
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接服务器接口
     */
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBindPool = IBindPool.Stub.asInterface(service);
            //绑定死亡监听
            try {
                mBindPool.asBinder().linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            //同步辅助器，值减1
            mCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    /**
     * 死亡监听接口，如果Binder对象在使用过程中突然停止服务，就会返回这个接口
     */
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            //取消死亡监听
            mBindPool.asBinder().unlinkToDeath(mDeathRecipient, 0);
            //释放资源
            mBindPool = null;
            //重新连接服务
            connectBinderPoolService();
        }
    };
}
