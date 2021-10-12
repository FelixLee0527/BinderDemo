// IAirController.aidl
package com.tw.binder.service;

import com.tw.binder.service.IAirState;
interface IAirController {

    void registerAirCallBack(in IAirState iAirState);

    void setLeftTemp(float temp);

    void setRightTemp(float temp);

    void setWindType(int type);

    void setWindLevel(int level);

    void setLoop(int type);

    void unRegisterAirCallBack(in IAirState iAirState);
}
