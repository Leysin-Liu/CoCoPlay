/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/leysin/workspace/CoCoPlay/src/com/leysin/cocoplay/model/ITaskCallback.aidl
 */
package com.leysin.cocoplay.model;
public interface ITaskCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.leysin.cocoplay.model.ITaskCallback
{
private static final java.lang.String DESCRIPTOR = "com.leysin.cocoplay.model.ITaskCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.leysin.cocoplay.model.ITaskCallback interface,
 * generating a proxy if needed.
 */
public static com.leysin.cocoplay.model.ITaskCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.leysin.cocoplay.model.ITaskCallback))) {
return ((com.leysin.cocoplay.model.ITaskCallback)iin);
}
return new com.leysin.cocoplay.model.ITaskCallback.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_OnStateChanged:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
java.lang.String _arg3;
_arg3 = data.readString();
boolean _arg4;
_arg4 = (0!=data.readInt());
this.OnStateChanged(_arg0, _arg1, _arg2, _arg3, _arg4);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.leysin.cocoplay.model.ITaskCallback
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void OnStateChanged(java.lang.String songName, java.lang.String artistName, java.lang.String totleTime, java.lang.String currentTime, boolean isPlayer) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(songName);
_data.writeString(artistName);
_data.writeString(totleTime);
_data.writeString(currentTime);
_data.writeInt(((isPlayer)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_OnStateChanged, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_OnStateChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public void OnStateChanged(java.lang.String songName, java.lang.String artistName, java.lang.String totleTime, java.lang.String currentTime, boolean isPlayer) throws android.os.RemoteException;
}
