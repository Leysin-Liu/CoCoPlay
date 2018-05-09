package com.leysin.cocoplay.entity;

import java.io.Serializable;

public class Mp3Info implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String songName;
	
	public long totleTime;
	
	public long currentTime;
	
	public String artistName ;
	
	public boolean isPlay;
	
	public int playModle;

	public int albumId;
	
	public String url;
	
	public int position;
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	public int getPlayModle() {
		return playModle;
	}

	public void setPlayModle(int playModle) {
		this.playModle = playModle;
	}

	public boolean isPlay() {
		return isPlay;
	}

	public void setPlay(boolean isPlay) {
		this.isPlay = isPlay;
	}

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public long getTotleTime() {
		return totleTime;
	}

	public void setTotleTime(long totleTime) {
		this.totleTime = totleTime;
	}

	public long getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(long currentTime) {
		this.currentTime = currentTime;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
 