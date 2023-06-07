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

	// ��ε�ĳ��Ʈ ���ù��� �ް� �Ǹ� ���⸦ ȣ���Ѵ�.
	// ����Ʈ�� action�� TOAST_ACTION���� Ȯ���ؼ�
	// ������ �佺Ʈ �޼����� ����.
	
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
		// �� ������ remote adapter�� ������Ʈ �Ѵ�.
		for(int i=0; i< appWidgetIds.length; i++)
		{
			Log.e(TAG, "appWidgetId: " + appWidgetIds[i]);
			SharedPreferences sharedPreferences = context.getSharedPreferences(AlbumTYPE, Activity.MODE_PRIVATE);
			type = sharedPreferences.getString(AlbumTYPE, "Photo");
			SharedPreferences.Editor e = sharedPreferences.edit();
			e.putString(AlbumTYPE, type);
			e.commit();
			Log.e(TAG, "AlbumType, type:" + AlbumTYPE + "," + type);

			// StackViewService�� ���� intent�� �����.
			// StackViewService�� collection view�� ����� ��Ȱ�� �Ѵ�.
			Intent intent = new Intent(context, StackWidgetService.class);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds[i]);
			// �׳� ������ extra�� ���õǴµ�,
			// �Ʒ��� ���� data�� ���������� extra�� ���õ��� �ʴ´�.
			intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
			// ���� ���̾ƿ� �ʱ�ȭ
			RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
			// ��͸� ���δ�.
			// �� ��ʹ� RemoteViewsService�� ����Ǿ� �ִ�.
			// �� �ڵ带 ���������μ� �����Ϳ� �����ϰ� �ȴ�.
			rv.setRemoteAdapter(appWidgetIds[i], R.id.stack_view, intent);

			// collection�� �����Ͱ� ������ ��view�� �����ش�.
			// collection view�� sibling(���� ���̾ƿ��� �����־��)�̾�� �Ѵ�.
			rv.setEmptyView(R.id.stack_view, R.id.empty_view);
			
			// ���⼭ pending intent ���ø��� �����Ѵ�.
			// ���� item���� pending intent�� ������ �� ���� ������
			// collection���� pending intent template�� �����
			// ���Ŀ� �� item���� fill-in intent�� �����.
			Intent toastIntent = new Intent(context, StackWidgetProvider.class);
			// ������ ��ġ�ϸ� TOAST_ACTION ��ε�ĳ��Ʈ�� ������ �ȴ�.
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
