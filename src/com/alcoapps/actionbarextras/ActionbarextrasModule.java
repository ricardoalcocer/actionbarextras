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
import org.appcelerator.titanium.proxy.TiWindowProxy;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.titanium.util.TiRHelper;
import org.appcelerator.titanium.util.TiUIHelper;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
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
	private static final int MSG_HOMEASUP_ICON = MSG_FIRST_ID + 108;
	private static final int MSG_HIDE_LOGO = MSG_FIRST_ID + 109;
	private static final int MSG_WINDOW = MSG_FIRST_ID + 110;

	protected static final int MSG_LAST_ID = MSG_FIRST_ID + 999;

	private TypefaceSpan titleFont;
	private TypefaceSpan subtitleFont;
	private String titleColor;
	private String subtitleColor;
	private TiWindowProxy window;
	
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
	
	private ActionBar getActionBar(){
		ActionBarActivity activity;
		
		if (window != null){
			activity = (ActionBarActivity) window.getActivity();
		} else {
			TiApplication appContext = TiApplication.getInstance();
			activity = (ActionBarActivity) appContext.getCurrentActivity();
		}
		
		ActionBar actionBar = activity.getSupportActionBar();
		return actionBar;
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
				handleSetTitleFont(msg.obj);
				return true;
			}
			case MSG_SUBTITLE_FONT: {
				handleSetSubtitleFont(msg.obj);
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
			case MSG_HOMEASUP_ICON: {
				handleSetHomeAsUpIcon((String) msg.obj);
				return true;
			}
			case MSG_HIDE_LOGO: {
				handleHideLogo();
				return true;
			}
			case MSG_WINDOW: {
				handleSetWindow(msg.obj);
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
			ActionBar actionBar = getActionBar();
			
			SpannableStringBuilder ssb;
			
			if (actionBar.getTitle() instanceof SpannableStringBuilder){
				ssb = (SpannableStringBuilder) actionBar.getTitle();
				ssb.clear();
				ssb.append((String) obj);
			} else {
				ssb = new SpannableStringBuilder((String) obj);
			}
			
			if (titleFont != null){
				ssb.setSpan(titleFont, 0, ssb.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			}
			
			if (titleColor != null){
				ssb.setSpan(new ForegroundColorSpan(TiConvert.toColor(titleColor)),
						0, ssb.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			}
			
			actionBar.setTitle(ssb);
			
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
			ActionBar actionBar = getActionBar();
			
			SpannableStringBuilder ssb;
			
			if (actionBar.getSubtitle() != null && actionBar.getSubtitle() instanceof SpannableStringBuilder){
				ssb = (SpannableStringBuilder) actionBar.getSubtitle();
				ssb.clear();
				ssb.append((String) obj);
			} else {
				ssb = new SpannableStringBuilder((String) obj);
			}
			
			if (subtitleFont != null){
				ssb.setSpan(subtitleFont, 0, ssb.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			}
			
			if (subtitleColor != null){
				ssb.setSpan(new ForegroundColorSpan(TiConvert.toColor(subtitleColor)),
						0, ssb.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			}
			
			actionBar.setSubtitle(ssb);
			
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
			ActionBar actionBar = getActionBar();
			
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets Actionbar title font
	 * @param obj
	 */
	private void handleSetTitleFont(Object font){
		try {
			TiApplication appContext = TiApplication.getInstance();
			ActionBar actionBar = getActionBar();
			
			SpannableStringBuilder ssb;
			
			if (actionBar.getTitle() instanceof SpannableStringBuilder){
				ssb = (SpannableStringBuilder) actionBar.getTitle();
				ssb.removeSpan(titleFont);
			} else {
				String abTitle = TiConvert.toString(actionBar.getTitle());
				ssb = new SpannableStringBuilder(abTitle);
			}
			
			if (font instanceof String){
				titleFont = new TypefaceSpan(appContext, (String) font);
				ssb.setSpan(titleFont, 0, ssb.length(),
						Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			}
			
			if (font instanceof HashMap) {
				@SuppressWarnings("unchecked")
				HashMap<String, String> d = (HashMap<String, String>) font;
				
				ssb = applyFontProperties(appContext, d, ssb, titleFont);
			}

			actionBar.setTitle(ssb);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets Actionbar subtitle font
	 * @param obj
	 */
	private void handleSetSubtitleFont(Object font){
		try {
			TiApplication appContext = TiApplication.getInstance();
			ActionBar actionBar = getActionBar();

			String abSubtitle = TiConvert.toString(actionBar.getSubtitle());
			if (abSubtitle != null) {
				SpannableStringBuilder ssb;
				
				if (actionBar.getSubtitle() instanceof SpannableStringBuilder){
					ssb = (SpannableStringBuilder) actionBar.getSubtitle();
					ssb.removeSpan(subtitleFont);
				} else {
					ssb = new SpannableStringBuilder(abSubtitle);
				}
				
				if (font instanceof String){
					subtitleFont = new TypefaceSpan(appContext, (String) font);
					ssb.setSpan(subtitleFont, 0, ssb.length(),
							Spannable.SPAN_INCLUSIVE_INCLUSIVE);
				}
				
				if (font instanceof HashMap) {
					@SuppressWarnings("unchecked")
					HashMap<String, String> d = (HashMap<String, String>) font;
					
					ssb = applyFontProperties(appContext, d, ssb, subtitleFont);
				}
				
				actionBar.setSubtitle(ssb);
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
		
		titleColor = color;
		
		try {
			ActionBar actionBar = getActionBar();

			SpannableStringBuilder ssb;
			
			if (actionBar.getTitle() instanceof SpannableStringBuilder){
				ssb = (SpannableStringBuilder) actionBar.getTitle();
			} else {
				String abTitle = TiConvert.toString(actionBar.getTitle());
				ssb = new SpannableStringBuilder(abTitle);
			}
			
			if (titleColor != null){
				ssb.setSpan(new ForegroundColorSpan(TiConvert.toColor(titleColor)),
						0, ssb.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			}
			
			actionBar.setTitle(ssb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets Actionbar subtitle color
	 * @param obj
	 */
	private void handleSetSubtitleColor(String color){
		
		subtitleColor = color;
		
		try {
			ActionBar actionBar = getActionBar();
			
			String abSubtitle = TiConvert.toString(actionBar.getSubtitle());
			if (abSubtitle != null) {
				SpannableStringBuilder ssb;
				
				if (actionBar.getSubtitle() instanceof SpannableStringBuilder){
					ssb = (SpannableStringBuilder) actionBar.getSubtitle();
				} else {
					ssb = new SpannableStringBuilder(abSubtitle);
				}
				
				if (subtitleColor != null){
					ssb.setSpan(new ForegroundColorSpan(TiConvert.toColor(subtitleColor)),
							0, ssb.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
				}

				actionBar.setSubtitle(ssb);
			}

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
			ActionBar actionBar = getActionBar();
			TiApplication appContext = TiApplication.getInstance();
			ActionBarActivity activity = (ActionBarActivity) appContext.getCurrentActivity();
			
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
	 * Sets the homeAsUp icon
	 * @param icon
	 */
	private void handleSetHomeAsUpIcon(String icon){
		try {
			ActionBar actionBar = getActionBar();
			
			int resId = TiUIHelper.getResourceId(resolveUrl(null, icon));
			if (resId != 0) {
				actionBar.setHomeAsUpIndicator(resId);
			} else {
				Log.e(TAG, "Couldn't resolve " + icon);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Hides the logo
	 */
	private void handleHideLogo(){
		try {
			ActionBar actionBar = getActionBar();
			
			actionBar.setLogo(new ColorDrawable(TiRHelper
					.getAndroidResource("color.transparent")));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void handleSetWindow(Object obj){
		if (obj instanceof TiWindowProxy){
			window = (TiWindowProxy) obj;
		}
	}
	
	/**
	 * Helper function to process font objects used for title and subtitle
	 * 
	 * @param Context - TiApplication context
	 * @param Object - the properties as hashmap
	 * @param Text - SpannableStringBuilder that should get the properties applied
	 * @param TypefaceSpan - font reference (for title or subtitle)
	 */
	private SpannableStringBuilder applyFontProperties(TiApplication appContext, HashMap<String, String> d, SpannableStringBuilder ssb, TypefaceSpan font){
		
		if (d.containsKey(TiC.PROPERTY_FONTFAMILY)){
			String fontFamily = d.get(TiC.PROPERTY_FONTFAMILY);
			font = new TypefaceSpan(appContext, fontFamily);
			ssb.setSpan(font, 0, ssb.length(),
					Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		}
		
		if (d.containsKey(TiC.PROPERTY_FONTSIZE)){
			Object value = d.get(TiC.PROPERTY_FONTSIZE);
			boolean dip = false;
			int fontSize;
			
			if (value instanceof String){
				// is there a better way to convert Strings ("16px", "22sp" etc.) to dip?
				fontSize = (int) TiUIHelper.getRawSize(
						TiUIHelper.getSizeUnits((String) value), 
						TiUIHelper.getSize((String) value), 
						appContext
				);
			}else {
				fontSize = (Integer) value;
				dip = true;
			}
			
			ssb.setSpan(new AbsoluteSizeSpan(fontSize, dip), 0, ssb.length(),
					Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		}
		
		if (d.containsKey(TiC.PROPERTY_FONTWEIGHT)){
			String fontWeight = d.get(TiC.PROPERTY_FONTWEIGHT);
			ssb.setSpan(new StyleSpan(TiUIHelper.toTypefaceStyle(fontWeight, null)), 0, ssb.length(),
					Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		}
		
		return ssb;
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
				setTitleFont(d.get(TiC.PROPERTY_FONT));
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
			@SuppressWarnings("unchecked")
			HashMap<String, String> d = (HashMap<String, String>) obj;
			subtitle = (String) d.get(TiC.PROPERTY_TEXT);
			
			if (d.containsKey(TiC.PROPERTY_COLOR)){
				setSubtitleColor((String) d.get(TiC.PROPERTY_COLOR));
			}
			
			if (d.containsKey(TiC.PROPERTY_FONT)){
				setSubtitleFont(d.get(TiC.PROPERTY_FONT));
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
	public void setFont(Object value) {
		setTitleFont(value);
		setSubtitleFont(value);
	}
	
	/**
	 * set title font
	 * @param value
	 */
	@Kroll.method @Kroll.setProperty
	public void setTitleFont(Object obj) {
		Message message = getMainHandler().obtainMessage(MSG_TITLE_FONT, obj);
		message.sendToTarget();
	}
	
	/**
	 * set subtitle font
	 * @param value
	 */
	@Kroll.method @Kroll.setProperty
	public void setSubtitleFont(Object obj) {
		Message message = getMainHandler().obtainMessage(MSG_SUBTITLE_FONT, obj);
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
	 * sets the homeAsUp icon
	 * @param arg
	 */
	@Kroll.method @Kroll.setProperty
	public void setHomeAsUpIcon(String arg) {
		Message message = getMainHandler().obtainMessage(MSG_HOMEASUP_ICON, arg);
		message.sendToTarget();
	}
	
	/**
	 * hides the logo
	 */
	@Kroll.method
	public void hideLogo() {
		Message message = getMainHandler().obtainMessage(MSG_HIDE_LOGO);
		message.sendToTarget();
	}
	
	/**
	 * sets a reference to a window
	 * @param arg
	 */
	@Kroll.method @Kroll.setProperty
	public void setWindow(Object arg) {
		Message message = getMainHandler().obtainMessage(MSG_WINDOW, arg);
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
			ActionBarActivity activity = (ActionBarActivity) appContext.getCurrentActivity();

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
