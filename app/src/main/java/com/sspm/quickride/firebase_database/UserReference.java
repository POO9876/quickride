package com.sspm.quickride.firebase_database;

import android.location.Location;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.sspm.quickride.pojo.User;
import com.sspm.quickride.ui.interfaces.Callback_v2;
import com.sspm.quickride.util.Constants;

/**
 * Created by Siddhesh on 25-01-2017.
 */

public class UserReference extends AbstractDatabaseReference {
    private DatabaseReference mUserRef;
    private ValueEventListener listener;
    private Callback_v2 callback;

    @Override
    public void initiateDatabase(Callback_v2 callback) {
        this.callback = callback;
        mUserRef = mDatabase.getReference(Constants.USER_REFERENCE + getMyMobile());
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataChange(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mUserRef.addValueEventListener(listener);
    }

    public void addMyUser(User user){
        mUserRef.setValue(user);
    }

    public void changeLocation(Location location){
        mUserRef.child("location").setValue(location);
    }

    public void becameActive(){
        mUserRef.child("isActive").setValue(true);
    }

    public void becameInActive(){
        mUserRef.child("isActive").setValue(false);
    }

    @Override
    public void dataChange(DataSnapshot dataSnapshot) {
        callback.invoke(dataSnapshot);
        Log.d("Listening","dataSnapshot : "+dataSnapshot);
    }
}
