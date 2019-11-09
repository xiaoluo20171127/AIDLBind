// IPersonManage.aidl
package com.coocaa.bindpoolaidl;
import com.coocaa.bindpoolaidl.Person;
// Declare any non-default types here with import statements

interface IPersonManage {
    void add(inout Person p);
    void delete(inout Person p);
    List<Person> getAllPerson();
}
