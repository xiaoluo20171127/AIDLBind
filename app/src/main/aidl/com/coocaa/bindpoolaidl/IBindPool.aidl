// IBindPool.aidl
package com.coocaa.bindpoolaidl;

// Declare any non-default types here with import statements

interface IBindPool {
    IBinder queryBinder(int binderCode);
}
