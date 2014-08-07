package com.alcoapps.actionbarextras;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.TiC;
import org.appcelerator.titanium.proxy.IntentProxy;
import org.appcelerator.titanium.proxy.MenuProxy;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.titanium.util.TiRHelper;
import org.appcelerator.titanium.util.TiUIHelper;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.TextView;
@Kroll.module(name = "Actionbarextras", id = "com.alcoapps.actionbarextras")
public class ActionbarextrasModule extends KrollModule {

	private static final String TAG = "ActionbarextrasModule";

	private static final int MSG_FIRST_ID = KrollModule.MSG_LAST_ID + 1;

	private static final int MSG_TITLE = MSG_FIRST_ID + 100;
	private static final int MSG_SUBTITLE = MSG_FIRST_ID + 101;
	private static final int MSG_BACKGROUND_COLOR = MSG_FIRST_ID + 102;
	private static final int MSG_TITLE_FONT = MSG_FIRST_ID + 103;
	private static final int MSG_SUBTITLE_FONT = MSG_FIRST_ID + 104;
	private static final int MSG_TITLE_COLOR = MSG_FIRST_ID + 105;
	private static final int MSG_SUBTITLE_COLOR = MSG_FIRST_ID + 106;
	private static final int MSG_DISABLE_ICON = MSG_FIRST_ID + 107;

	protected static final int MSG_LAST_ID = MSG_FIRST_ID + 999;

	private ShareActionProvider mShareActionProvider;

	public ActionbarextrasModule() {
		super();
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
			case MSG_TITLE: {
				handleSetTitle(msg.obj);
				return true;
			}
			case MSG_SUBTITLE: {
				handleSetSubtitle(msg.obj);
				return true;
			}
			case MSG_BACKGROUND_COLOR: {
				handleSetBackgroundColor((String) msg.obj);
				return true;
			}
			case MSG_TITLE_FONT: {
				handleSetTitleFont((String) msg.obj);
				return true;
			}
			case MSG_SUBTITLE_FONT: {
				handleSetSubtitleFont((String) msg.obj);
				return true;
			}
			case MSG_TITLE_COLOR: {
				handleSetTitleColor((String) msg.obj);
				return true;
			}
			case MSG_SUBTITLE_COLOR: {
				handleSetSubtitleColor((String) msg.obj);
				return true;
			}
			case MSG_DISABLE_ICON: {
				handleDisableIcon();
				return true;
			}
			default: {
				return super.handleMessage(msg);
			}
		}

	}
	
	private void handleSetTitle(Object obj){
		try {
			TiApplication appContext = TiApplication.getInstance();
			Activity activity = appContext.getCurrentActivity();
			ActionBar actionBar = activity.getActionBar();

			actionBar.setTitle((String) obj);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void handleSetSubtitle(Object obj){
		try {
			TiApplication appContext = TiApplication.getInstance();
			Activity activity = appContext.getCurrentActivity();
			ActionBar actionBar = activity.getActionBar();
			
			actionBar.setSubtitle((String) obj);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void handleSetBackgroundColor(String color){
		try {
			TiApplication appContext = TiApplication.getInstance();
			Activity activity = appContext.getCurrentActivity();
			ActionBar actionBar = activity.getActionBar();
			
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void handleSetTitleFont(String font){
		try {
			TiApplication appContext = TiApplication.getInstance();
			Activity activity = appContext.getCurrentActivity();
			ActionBar actionBar = activity.getActionBar();

			String abTitle = TiConvert.toString(actionBar.getTitle());
			SpannableString s = new SpannableString(abTitle);
			s.setSpan(new TypefaceSpan(appContext, font), 0, s.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

			actionBar.setTitle(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void handleSetSubtitleFont(String font){
		try {
			TiApplication appContext = TiApplication.getInstance();
			Activity activity = appContext.getCurrentActivity();
			ActionBar actionBar = activity.getActionBar();

			String abSubtitle = TiConvert.toString(actionBar.getSubtitle());
			if (abSubtitle != null) {
				SpannableString s = new SpannableString(abSubtitle);
				s.setSpan(new TypefaceSpan(appContext, font), 0, s.length(),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

				actionBar.setSubtitle(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void handleSetTitleColor(String color){
		try {
			TiApplication appContext = TiApplication.getInstance();
			Activity activity = appContext.getCurrentActivity();

			int titleId = activity.getResources().getIdentifier(
					"action_bar_title", "id", "android");
			TextView abTitle = (TextView) activity.findViewById(titleId);
			abTitle.setTextColor(TiConvert.toColor(color));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void handleSetSubtitleColor(String color){
		try {
			TiApplication appContext = TiApplication.getInstance();
			Activity activity = appContext.getCurrentActivity();

			int subtitleId = activity.getResources().getIdentifier(
					"action_bar_subtitle", "id", "android");
			TextView abSubTitle = (TextView) activity.findViewById(subtitleId);
			abSubTitle.setTextColor(TiConvert.toColor(color));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void handleDisableIcon(){
		try {
			TiApplication appContext = TiApplication.getInstance();
			Activity activity = appContext.getCurrentActivity();
			ActionBar actionBar = activity.getActionBar();

			actionBar.setIcon(new ColorDrawable(TiRHelper
					.getAndroidResource("color.transparent")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Kroll.method
	@Deprecated
	public void setExtras(final KrollDict args) {
		Log.d(TAG, "called the setextras method");

		// declare stuff
		TiApplication appContext = TiApplication.getInstance();
		final Activity activity = appContext.getCurrentActivity();

		TiUIHelper.runUiDelayedIfBlock(new Runnable() {
			@Override
			public void run() {
				processExtrasProperties(args, activity);
			}
		});
	}

	private void processExtrasProperties(KrollDict args, Activity activity) {

		if (args.containsKey(TiC.PROPERTY_TITLE)) {
			setTitle((String) args.get(TiC.PROPERTY_TITLE));
		}

		if (args.containsKey(TiC.PROPERTY_SUBTITLE)) {
			setSubtitle((String) args.get(TiC.PROPERTY_SUBTITLE));
		}

		if (args.containsKey(TiC.PROPERTY_BACKGROUND_COLOR)) {
			setBackgroundColor((String) args.get(TiC.PROPERTY_BACKGROUND_COLOR));
		}

		if (args.containsKey(TiC.PROPERTY_FONT)) {
			setFont(TiConvert.toString(args.get(TiC.PROPERTY_FONT)));
		}

		if (args.containsKey(TiC.PROPERTY_COLOR)) {
			setColor(TiConvert.toString(args.get(TiC.PROPERTY_COLOR)));
		}
	}
	
	@Kroll.method @Kroll.setProperty
	public void setTitle(String value) {
		Message message = getMainHandler().obtainMessage(MSG_TITLE, value);
		message.sendToTarget();
	}
	
	@Kroll.method @Kroll.setProperty
	public void setSubtitle(String value) {
		Message message = getMainHandler().obtainMessage(MSG_SUBTITLE, value);
		message.sendToTarget();
	}
	
	@Kroll.method @Kroll.setProperty
	public void setBackgroundColor(String color) {
		Message message = getMainHandler().obtainMessage(MSG_BACKGROUND_COLOR, color);
		message.sendToTarget();
	}
	
	@Kroll.method @Kroll.setProperty
	public void setFont(String value) {
		setTitleFont(value);
		setSubtitleFont(value);
	}
	
	@Kroll.method @Kroll.setProperty
	public void setTitleFont(String value) {
		Message message = getMainHandler().obtainMessage(MSG_TITLE_FONT, value);
		message.sendToTarget();
	}

	@Kroll.method @Kroll.setProperty
	public void setSubtitleFont(String value) {
		Message message = getMainHandler().obtainMessage(MSG_SUBTITLE_FONT, value);
		message.sendToTarget();
	}

	@Kroll.method @Kroll.setProperty
	public void setColor(String color) {
		setTitleColor(color);
		setSubtitleColor(color);
	}
	
	@Kroll.method @Kroll.setProperty
	public void setTitleColor(String color){
		Message message = getMainHandler().obtainMessage(MSG_TITLE_COLOR, color);
		message.sendToTarget();
	}
	
	@Kroll.method @Kroll.setProperty
	public void setSubtitleColor(String color){
		Message message = getMainHandler().obtainMessage(MSG_SUBTITLE_COLOR, color);
		message.sendToTarget();
	}
	
	/**
	 * disables or enables the icon
	 * @param arg
	 */
	@Kroll.method @Kroll.setProperty
	public void disableIcon(@Kroll.argument(optional = true) Boolean arg) {
		
		Boolean disabled = true;
		
		if (arg != null) {
			disabled = TiConvert.toBoolean(arg);
		}
		
		Message message = getMainHandler().obtainMessage(MSG_DISABLE_ICON, disabled);
		message.sendToTarget();
	}

	@Kroll.method
	public void addShareAction(KrollDict args) {
		Log.d(TAG, "addShareAction: " + args.toString());

		MenuProxy menu_proxy = (MenuProxy) args.get(TiC.PROPERTY_MENU);
		IntentProxy intent_proxy = (IntentProxy) args.get(TiC.PROPERTY_INTENT);
		String title = "Share";
		int show_as_action = MenuItem.SHOW_AS_ACTION_IF_ROOM;

		if (args.containsKey(TiC.PROPERTY_TITLE)) {
			title = TiConvert.toString(args, TiC.PROPERTY_TITLE);
		}

		if (args.containsKey(TiC.PROPERTY_SHOW_AS_ACTION)) {
			show_as_action = TiConvert.toInt(args, TiC.PROPERTY_SHOW_AS_ACTION);
		}

		try {
			TiApplication appContext = TiApplication.getInstance();
			Activity activity = appContext.getCurrentActivity();

			mShareActionProvider = new ShareActionProvider(activity);

			Menu menu = menu_proxy.getMenu();
			MenuItem item = menu.add(title);

			item.setShowAsAction(show_as_action);
			MenuItemCompat.setActionProvider(item, mShareActionProvider);

			mShareActionProvider.setShareIntent(intent_proxy.getIntent());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
