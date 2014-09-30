package com.alcoapps.actionbarextras;

import java.util.HashMap;

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
import android.graphics.drawable.Drawable;
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
	
	public ActionbarextrasModule() {
		super();
	}
	
	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app) {
		// hack taken from:
		// http://stackoverflow.com/questions/9286822/how-to-force-use-of-overflow-menu-on-devices-with-menu-button
		try {
			ViewConfiguration config = ViewConfiguration.get(app);
			java.lang.reflect.Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception ex) {
			// Ignore
		}
	}
	
	/**
	 * message handler
	 * @param message
	 */
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
				handleDisableIcon((Boolean) msg.obj);
				return true;
			}
			default: {
				return super.handleMessage(msg);
			}
		}

	}
	
	/**
	 * Sets Actionbar title
	 * @param obj
	 */
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
	
	/**
	 * Sets Actionbar subtitle
	 * @param obj
	 */
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
	
	/**
	 * Sets Actionbar background color
	 * @param obj
	 */
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
	
	/**
	 * Sets Actionbar title font
	 * @param obj
	 */
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
	
	/**
	 * Sets Actionbar subtitle font
	 * @param obj
	 */
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
	
	/**
	 * Sets Actionbar title color
	 * @param obj
	 */
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
	
	/**
	 * Sets Actionbar subtitle color
	 * @param obj
	 */
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
	
	/**
	 * Disables or enables Actionbar icon
	 * @param obj
	 */
	private void handleDisableIcon(Boolean disabled){
		try {
			TiApplication appContext = TiApplication.getInstance();
			Activity activity = appContext.getCurrentActivity();
			ActionBar actionBar = activity.getActionBar();
			
			if (disabled){
				actionBar.setIcon(new ColorDrawable(TiRHelper
						.getAndroidResource("color.transparent")));
			}else{
				Drawable icon = activity.getPackageManager().getApplicationIcon(appContext.getApplicationContext().getPackageName());
				actionBar.setIcon(icon);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * set multiple properties at once
	 * this method will be removed soon
	 * @param args
	 */
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
	
	/**
	 * this method will be removed soon
	 */
	@Deprecated
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
	
	/**
	 * You can set just the title with setTitle("title")
	 * or title, color and font at once with:
	 * setTitle({
	 *     text: "title",
	 *     color: "#f00",
	 *     font: "MyFont.otf"
	 * })
	 * 
	 * @param obj
	 */
	@Kroll.method @Kroll.setProperty
	public void setTitle(Object obj) {
		
		String title;
		
		if (obj instanceof String){
			title = (String) obj;
		}else if(obj instanceof HashMap){
			@SuppressWarnings("unchecked")
			HashMap<String, String> d = (HashMap<String, String>) obj;
			title = (String) d.get(TiC.PROPERTY_TEXT);
			
			if (d.containsKey(TiC.PROPERTY_COLOR)){
				setTitleColor((String) d.get(TiC.PROPERTY_COLOR));
			}
			
			if (d.containsKey(TiC.PROPERTY_FONT)){
				setTitleFont((String) d.get(TiC.PROPERTY_FONT));
			}
		}else{
			return;
		}
		
		Message message = getMainHandler().obtainMessage(MSG_TITLE, title);
		message.sendToTarget();
	}
	
	/**
	 * You can set just the subtitle with setSubtitle("subtitle")
	 * or subtitle, color and font at once with:
	 * setSubtitle({
	 *     text: "subtitle",
	 *     color: "#f00",
	 *     font: "MyFont.otf"
	 * })
	 * 
	 * @param obj
	 */
	@Kroll.method @Kroll.setProperty
	public void setSubtitle(Object obj) {
		
		String subtitle;
		
		if (obj instanceof String){
			subtitle = (String) obj;
		}else if(obj instanceof HashMap){
			HashMap<String, String> d = (HashMap<String, String>) obj;
			subtitle = (String) d.get(TiC.PROPERTY_TEXT);
			
			if (d.containsKey(TiC.PROPERTY_COLOR)){
				setSubtitleColor((String) d.get(TiC.PROPERTY_COLOR));
			}
			
			if (d.containsKey(TiC.PROPERTY_FONT)){
				setSubtitleFont((String) d.get(TiC.PROPERTY_FONT));
			}
		}else{
			return;
		}
		
		Message message = getMainHandler().obtainMessage(MSG_SUBTITLE, subtitle);
		message.sendToTarget();
	}
	
	/**
	 * Set the Actionbar background color
	 * @param color
	 */
	@Kroll.method @Kroll.setProperty
	public void setBackgroundColor(String color) {
		Message message = getMainHandler().obtainMessage(MSG_BACKGROUND_COLOR, color);
		message.sendToTarget();
	}
	
	/**
	 * Set title and subtitle font at once
	 * @param value
	 */
	@Kroll.method @Kroll.setProperty
	public void setFont(String value) {
		setTitleFont(value);
		setSubtitleFont(value);
	}
	
	/**
	 * set title font
	 * @param value
	 */
	@Kroll.method @Kroll.setProperty
	public void setTitleFont(String value) {
		Message message = getMainHandler().obtainMessage(MSG_TITLE_FONT, value);
		message.sendToTarget();
	}
	
	/**
	 * set subtitle font
	 * @param value
	 */
	@Kroll.method @Kroll.setProperty
	public void setSubtitleFont(String value) {
		Message message = getMainHandler().obtainMessage(MSG_SUBTITLE_FONT, value);
		message.sendToTarget();
	}
	
	/**
	 * Set title and subtitle color at once
	 * @param value
	 */
	@Kroll.method @Kroll.setProperty
	public void setColor(String color) {
		setTitleColor(color);
		setSubtitleColor(color);
	}
	
	/**
	 * set title color
	 * @param color
	 */
	@Kroll.method @Kroll.setProperty
	public void setTitleColor(String color){
		Message message = getMainHandler().obtainMessage(MSG_TITLE_COLOR, color);
		message.sendToTarget();
	}
	
	/**
	 * set subtitle color
	 * @param color
	 */
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
	public void setDisableIcon(@Kroll.argument(optional = true) Boolean arg) {
		
		Boolean disabled = true;
		
		if (arg != null) {
			disabled = TiConvert.toBoolean(arg);
		}
		
		Message message = getMainHandler().obtainMessage(MSG_DISABLE_ICON, disabled);
		message.sendToTarget();
	}
	
	/**
	 * add share action provider to Actionbar
	 * @param args
	 */
	@Kroll.method
	public void addShareAction(KrollDict args) {
		
		ShareActionProvider mShareActionProvider;
		
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
