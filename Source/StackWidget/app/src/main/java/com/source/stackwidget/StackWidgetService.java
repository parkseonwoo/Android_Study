package com.source.stackwidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import java.util.ArrayList;
import java.util.List;


public class StackWidgetService extends RemoteViewsService {

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
	}

}

class StackRemoteViewsFactory implements RemoteViewsFactory
{
	private Context mContext;
	private int mAppWidgetId;
	private List<WidgetItem> mWidgetItems = new ArrayList<WidgetItem>();
	private static final int mCount = 10;

	public StackRemoteViewsFactory(Context context, Intent intent)
	{
		mContext = context;
		mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
				AppWidgetManager.INVALID_APPWIDGET_ID);
	}

	@Override
	public int getCount() {
		return mCount;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public RemoteViews getLoadingView() {
		return null;
	}

	@Override
	public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        //rv.setTextViewText(R.id.widget_item, mWidgetItems.get(position).text);
		rv.setImageViewResource(R.id.widget_item, R.drawable.test);
        Bundle extras = new Bundle();
        extras.putInt(StackWidgetProvider.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.widget_item, fillInIntent);

        try {
            System.out.println("Loading view " + position);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return rv;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}


	@Override
	public void onCreate() {
        for (int i = 0; i < mCount ; i++) {
            mWidgetItems.add(new WidgetItem(i + "!"));
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public void onDataSetChanged() {
		
	}

	@Override
	public void onDestroy() {
        mWidgetItems.clear();
		
	}
	
}
