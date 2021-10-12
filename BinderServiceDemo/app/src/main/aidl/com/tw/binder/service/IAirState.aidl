// IAirState.aidl
package com.tw.binder.service;

interface IAirState {

    void onLeftTemp(float temp);

    void onRightTemp(float temp);

    void onWindType(int type);

    void onWindLevel(int level);

    void onLoop(int type);
}
