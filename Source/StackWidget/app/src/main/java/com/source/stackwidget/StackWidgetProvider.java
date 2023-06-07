package com.source.stackwidget;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;


public class StackWidgetProvider extends AppWidgetProvider {

	public static final String TOAST_ACTION = "com.source.stackwidget.TOAST_ACTION";
	public static final String EXTRA_ITEM = "com.source.stackwidget.EXTRA_ITEM";
	private static final String TAG = "StackWidgetProvider";
	private static String AlbumTYPE = null;
	private static String type = null;

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
	}
	
	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
	}
	
	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
	}

	// 브로드캐스트 리시버가 받게 되면 여기를 호출한다.
	// 인텐트의 action이 TOAST_ACTION인지 확인해서
	// 맞으면 토스트 메세지를 띄운다.
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG,"onReceive");
		AppWidgetManager mgr = AppWidgetManager.getInstance(context);

		if(type != null) {
			Log.e(TAG, "type" + type);
			// Update Widgets
			this.onUpdate(context, mgr, mgr.getAppWidgetIds(new ComponentName(context, StackWidgetProvider.class)));
		}

		if(intent.getAction().equals(TOAST_ACTION)) {
			int appWidgetid = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
			int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
			Toast.makeText(context, "Touched view "+viewIndex, Toast.LENGTH_SHORT).show();

		} super.onReceive(context, intent);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.d(TAG,"onUpdate");
		// 각 위젯은 remote adapter로 업데이트 한다.
		for(int i=0; i< appWidgetIds.length; i++)
		{
			Log.e(TAG, "appWidgetId: " + appWidgetIds[i]);
			SharedPreferences sharedPreferences = context.getSharedPreferences(AlbumTYPE, Activity.MODE_PRIVATE);
			type = sharedPreferences.getString(AlbumTYPE, "Photo");
			SharedPreferences.Editor e = sharedPreferences.edit();
			e.putString(AlbumTYPE, type);
			e.commit();
			Log.e(TAG, "AlbumType, type:" + AlbumTYPE + "," + type);

			// StackViewService로 가는 intent를 만든다.
			// StackViewService는 collection view를 만드는 역활을 한다.
			Intent intent = new Intent(context, StackWidgetService.class);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds[i]);
			// 그냥 보내면 extra가 무시되는데,
			// 아래와 같이 data에 끼워넣으면 extra가 무시되지 않는다.
			intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
			// 위젯 레이아웃 초기화
			RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
			// 어뎁터를 붙인다.
			// 이 어뎁터는 RemoteViewsService와 연결되어 있다.
			// 이 코드를 실행함으로서 데이터와 연결하게 된다.
			rv.setRemoteAdapter(appWidgetIds[i], R.id.stack_view, intent);

			// collection에 데이터가 없으면 빈view를 보여준다.
			// collection view의 sibling(같은 레이아웃에 속해있어야)이어야 한다.
			rv.setEmptyView(R.id.stack_view, R.id.empty_view);
			
			// 여기서 pending intent 템플릿을 설정한다.
			// 개별 item에서 pending intent를 설정할 수 없기 때문에
			// collection에서 pending intent template를 만들고
			// 그후에 각 item에서 fill-in intent를 만든다.
			Intent toastIntent = new Intent(context, StackWidgetProvider.class);
			// 위젯을 터치하면 TOAST_ACTION 브로드캐스트를 날리게 된다.
			toastIntent.setAction(StackWidgetProvider.TOAST_ACTION);
			toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
			intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
			PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, 
					PendingIntent.FLAG_UPDATE_CURRENT);
			rv.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);
			
			appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
			
		}

		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
}
