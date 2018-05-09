package com.leysin.cocoplay.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.widget.Toast;

/**
 * 2016.10.25
 * @author leysin
 *
 */
public class LogUtils {
	
	public static final String TAG = "LogUtils";
	public static boolean isShow = true;
	
	public static void showLogE(String tag , String msg){
		if(isShow){
			Log.e(tag, msg);
		}
	}

	public static void showLogI(String tag , String msg){
		if(isShow){
			Log.i(tag, msg);
		}
	}
	public static void showToast(Context context ,String msg){
		if(isShow){
			showToast(context,msg,0);
		}
	}
	
	public static void showToast(Context context ,String msg,int duration){
		if(isShow){
			Toast.makeText(context, msg, duration).show();
		}
	}
	private static AlertDialog.Builder builder = null;

	private static OnDialogButtonClick listener;

	/**
	 * Dialog Click CallBack
	 * @author leysin
	 *
	 */
	public interface OnDialogButtonClick{

		public void onDialogButton1Click(DialogInterface dialog);

		public void onDialogButton2Click(DialogInterface dialog);
	}
	
	
	public void setOnDialogButtonClickListener(OnDialogButtonClick listener){
		
				LogUtils.listener =listener;
	}		


	/**
	 * SingleButton Dialog
	 * @param context 
	 * @param Title
	 * @param content
	 * @param btTitle
	 */
	public static void showDialog(Context context,String title,String content,String btTitle){

		if(builder == null){

			builder = new AlertDialog.Builder(context);
		}
		builder.setTitle(title).
		setMessage(content).
		setCancelable(false).
		setPositiveButton(btTitle, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				if(listener!=null){

					listener.onDialogButton1Click(dialog);
				}else{

				}
			}
		}).
		create().show();
	}
	public static void showDialog(Context context,String title,String content,String btTitle,int iconId){

		if(builder == null){

			builder = new AlertDialog.Builder(context);
		}
		builder.setTitle(title).
		setIcon(iconId).
		setMessage(content).
		setCancelable(false).
		setPositiveButton(btTitle, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(listener!=null){
					listener.onDialogButton1Click(dialog);
				}else{

				}
			}

		}).
		create().show();
	}
	public static void showDialog(Context context,String title,String content ,String btTitle1 , String btTitle2 ,int iconId){

		if(builder == null){
			builder = new AlertDialog.Builder(context);
		}
		builder.setTitle(title).
		setIcon(iconId).
		setMessage(content).
		setCancelable(false).
		setPositiveButton(btTitle1, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				if(listener!=null){

					listener.onDialogButton1Click(dialog);
				}
			}

		}).setNegativeButton(btTitle2, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				if(listener != null){

					listener.onDialogButton2Click(dialog);
				}
			}
		}).
		create().show();

	}

}
