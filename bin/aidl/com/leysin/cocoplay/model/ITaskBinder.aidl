package com.leysin.cocoplay.model;

import com.leysin.cocoplay.model.ITaskCallback;

				interface ITaskBinder {  
    boolean isCallbackRunning();   
    void stopRunningTask();   
    void registerCallback(ITaskCallback cb);   
    void unregisterCallback(ITaskCallback cb);
    void start();
    void stop();
    void pause();
    void pre();
    void next();
    void seekTo(int position);
    void play(int positon);  
    void setPlayModle(int modle);
    
	}