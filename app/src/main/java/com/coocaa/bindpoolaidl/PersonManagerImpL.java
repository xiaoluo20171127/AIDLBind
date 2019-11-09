package com.coocaa.bindpoolaidl;

import android.os.RemoteException;

import java.util.List;

public class PersonManagerImpL extends IPersonManage.Stub {
    List<Person> mPersonList;
    PersonManagerImpL(List<Person> personList){
        mPersonList = personList;
    }
    @Override
    public void add(Person p) throws RemoteException {
        if(mPersonList != null&&!mPersonList.contains(p)) mPersonList.add(p);
    }

    @Override
    public void delete(Person p) throws RemoteException {
        if(mPersonList!=null&&mPersonList.contains(p)) mPersonList.remove(p);
    }

    @Override
    public List<Person> getAllPerson() throws RemoteException {
        return mPersonList;
    }
}
