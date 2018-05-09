package com.leysin.cocoplay.model;

 interface ITaskCallback{
		
 void OnStateChanged(String songName ,String artistName,String totleTime,String currentTime , boolean isPlayer);
 
}