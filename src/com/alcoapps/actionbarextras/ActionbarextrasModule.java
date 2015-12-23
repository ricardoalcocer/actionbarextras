package com.alcoapps.actionbarextras;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.TiC;
import org.appcelerator.titanium.proxy.IntentProxy;
import org.appcelerator.titanium.proxy.MenuItemProxy;
import org.appcelerator.titanium.proxy.MenuProxy;
import org.appcelerator.titanium.proxy.TiWindowProxy;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.titanium.util.TiRHelper;
import org.appcelerator.titanium.util.TiRHelper.ResourceNotFoundException;
import org.appcelerator.titanium.util.TiUIHelper;

import ti.modules.titanium.ui.android.SearchViewProxy;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.PorterDuff;
import android.os.Message;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.os.Build;
import android.view.ViewConfiguration;
import android.widget.EditText;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;

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
	private static final int MSG_SEARCHVIEW = MSG_FIRST_ID + 111;
	private static final int MSG_LOGO = MSG_FIRST_ID + 112;
	private static final int MSG_MENU_ICON = MSG_FIRST_ID + 113;
	private static final int MSG_STATUSBAR_COLOR = MSG_FIRST_ID + 114;
	private static final int MSG_ELEVATION = MSG_FIRST_ID + 115;
	private static final int MSG_HIDE_OFFSET = MSG_FIRST_ID + 116;
	private static final int MSG_NAVIGATIONBAR_COLOR = MSG_FIRST_ID + 117;
	private static final int MSG_UPICON_COLOR = MSG_FIRST_ID + 118;
	private static final int MSG_DISPLAY_HOME = MSG_FIRST_ID + 119;
	private static final int MSG_DISPLAY_TITLE = MSG_FIRST_ID + 120;
	private static final int MSG_DISPLAY_USELOGO = MSG_FIRST_ID + 121;
	private static final int MSG_TOOLBAR_TOP_PADDING = MSG_FIRST_ID + 122;

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

	@Kroll.getProperty @Kroll.method
    	public String getTitle()
    	{
        	return getActionBar().getTitle().toString();
    	}
	
	private ActionBar getActionBar(){
		AppCompatActivity activity;
		
		if (window != null){
			activity = (AppCompatActivity) window.getActivity();
		} else {
			TiApplication appContext = TiApplication.getInstance();
			activity = (AppCompatActivity) appContext.getCurrentActivity();
		}

		try {
			ActionBar actionBar = activity.getSupportActionBar();
			return actionBar;
		} catch (NullPointerException e) {
			Log.e(TAG, "ActionBar is null (not found)");
			return null;
		}

	}

	private IconDrawable getDrawableFromFont(HashMap args) {
		Typeface iconFontTypeface = TiUIHelper.toTypeface(TiApplication.getInstance(), (String) args.get(TiC.PROPERTY_FONTFAMILY));
		return new IconDrawable(TiApplication.getInstance(), (String) args.get(TiC.PROPERTY_ICON), iconFontTypeface).actionBarSize().color(TiConvert.toColor((String) args.get(TiC.PROPERTY_COLOR)));
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
			case MSG_STATUSBAR_COLOR: {
				handleSetStatusbarColor((String) msg.obj);
				return true;
			}
			case MSG_NAVIGATIONBAR_COLOR: {
				handleSetNavigationBarColor((String) msg.obj);
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
				handleSetHomeAsUpIcon(msg.obj);
				return true;
			}
			case MSG_LOGO: {
				handleSetLogo(msg.obj);
				return true;
			}
			case MSG_MENU_ICON: {
				handleSetMenuItemIcon( msg.obj );
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
			case MSG_SEARCHVIEW: {
				handleSetSearchView(msg.obj);
				return true;
			}
			case MSG_ELEVATION: {
				handleSetElevation(msg.obj);
				return true;
			}
			case MSG_HIDE_OFFSET: {
				handleSetHideOffset(msg.obj);
				return true;
			}
			case MSG_UPICON_COLOR: {
				handleSetUpColor((String) msg.obj);
				return true;
			}
			case MSG_DISPLAY_HOME: {
				handleDisplayShowHomeEnabled((Boolean) msg.obj);
				return true;
			}
			case MSG_DISPLAY_TITLE: {
				handleDisplayShowTitleEnabled((Boolean) msg.obj);
				return true;
			}
			case MSG_DISPLAY_USELOGO: {
				handleDisplayUseLogoEnabled((Boolean) msg.obj);
				return true;
			}
			case MSG_TOOLBAR_TOP_PADDING: {
				handleSetToolbarTopPadding(msg.obj);
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
		ActionBar actionBar = getActionBar();
		
		if (actionBar == null){
			return;
		}
		
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
	}
	
	/**
	 * Sets Actionbar subtitle
	 * @param obj
	 */
	private void handleSetSubtitle(Object obj){
		ActionBar actionBar = getActionBar();
		
		if (actionBar == null){
			return;
		}
		
		SpannableStringBuilder ssb;
		
		if (obj == null){
			actionBar.setSubtitle(null);
			return;
		}
		
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
	}
	
	/**
	 * Sets Actionbar background color
	 * @param obj
	 */
	private void handleSetBackgroundColor(String color){
		ActionBar actionBar = getActionBar();
		
		if (actionBar == null){
			return;
		}
		
		actionBar.setBackgroundDrawable(new ColorDrawable(TiConvert.toColor(color)));
	}

	/**
	 * Sets StatusbarColor for andoid 5.x / materialDesign
	 * @param obj
	 */
	private void handleSetStatusbarColor(String color){

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			AppCompatActivity activity;
			if (window != null){
				activity = (AppCompatActivity) window.getActivity();
			} else {
				TiApplication appContext = TiApplication.getInstance();
				activity = (AppCompatActivity) appContext.getCurrentActivity();
			}
			Window win = activity.getWindow();
			win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			win.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			win.setStatusBarColor(TiConvert.toColor(color));
		}
	}
	
	/**
	 * Sets NavigationBarColor for Android 5.x / materialDesign
	 * @param obj
	 */
	private void handleSetNavigationBarColor(String color){

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			AppCompatActivity activity;
			if (window != null){
				activity = (AppCompatActivity) window.getActivity();
			} else {
				TiApplication appContext = TiApplication.getInstance();
				activity = (AppCompatActivity) appContext.getCurrentActivity();
			}
			Window win = activity.getWindow();
			win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			win.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			win.setNavigationBarColor(TiConvert.toColor(color));
		}
	}
	
	/**
	 * Sets Actionbar title font
	 * @param obj
	 */
	private void handleSetTitleFont(Object font){
		TiApplication appContext = TiApplication.getInstance();
		ActionBar actionBar = getActionBar();
		
		if (actionBar == null){
			return;
		}
		
		SpannableStringBuilder ssb;
		
		if (actionBar.getTitle() instanceof SpannableStringBuilder){
			ssb = (SpannableStringBuilder) actionBar.getTitle();
			ssb.removeSpan(titleFont);
		} else {
			String abTitle = TiConvert.toString(actionBar.getTitle());
			ssb = new SpannableStringBuilder(abTitle);
		}
		
		if (font instanceof String){
			titleFont = new TypefaceSpan(appContext, ((String) font).replaceAll("\\.(ttf|otf|fnt)$", ""));
			ssb.setSpan(titleFont, 0, ssb.length(),
					Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		}
		
		if (font instanceof HashMap) {
			@SuppressWarnings("unchecked")
			HashMap<String, String> d = (HashMap<String, String>) font;
			
			ssb = applyFontProperties(appContext, d, ssb, titleFont);
		}

		actionBar.setTitle(ssb);
	}
	
	/**
	 * Sets Actionbar subtitle font
	 * @param obj
	 */
	private void handleSetSubtitleFont(Object font){
		TiApplication appContext = TiApplication.getInstance();
		ActionBar actionBar = getActionBar();
		
		if (actionBar == null){
			return;
		}

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
				subtitleFont = new TypefaceSpan(appContext, ((String) font).replaceAll("\\.(ttf|otf|fnt)$", ""));
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
	}
	
	/**
	 * Sets Actionbar title color
	 * @param obj
	 */
	private void handleSetTitleColor(String color){
		
		titleColor = color;
		
		ActionBar actionBar = getActionBar();
		
		if (actionBar == null){
			return;
		}

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
	}
	
	/**
	 * Sets Actionbar subtitle color
	 * @param obj
	 */
	private void handleSetSubtitleColor(String color){
		
		subtitleColor = color;
		
		ActionBar actionBar = getActionBar();
		
		if (actionBar == null){
			return;
		}
		
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
	}
	
	/**
	 * Disables or enables Actionbar icon
	 * @param obj
	 */
	private void handleDisableIcon(Boolean disabled){
		ActionBar actionBar = getActionBar();
		
		if (actionBar == null){
			return;
		}
		
		TiApplication appContext = TiApplication.getInstance();
		AppCompatActivity activity = (AppCompatActivity) appContext.getCurrentActivity();
		
		if (disabled){
			try {
				actionBar.setIcon(new ColorDrawable(TiRHelper
						.getAndroidResource("color.transparent")));
			} catch (ResourceNotFoundException e) {
				e.printStackTrace();
			}
		}else{
			try {
				Drawable icon;
				icon = activity.getPackageManager().getApplicationIcon(appContext.getApplicationContext().getPackageName());
				actionBar.setIcon(icon);
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sets the homeAsUp icon
	 * @param icon
	 */
	private void handleSetHomeAsUpIcon(Object obj) {
		ActionBar actionBar = getActionBar();

		if (actionBar == null) {
			return;
		}

		if (obj instanceof HashMap) {
			HashMap args = (HashMap) obj;
			actionBar.setHomeAsUpIndicator(getDrawableFromFont(args));
		} else if (obj instanceof String) {
			int resId = TiUIHelper.getResourceId(resolveUrl(null, (String)obj));
			if (resId != 0) {
				actionBar.setHomeAsUpIndicator(resId);
			} else {
				Log.e(TAG, "Couldn't resolve " + (String)obj);
			}
		} else {
			Log.e(TAG, "Please pass an Object or String to handleSetHomeAsUpIcon");
		}

	}
	
	/**
	 * Set whether to include the application home affordance in the action bar.
	 * @param icon
	 */
	private void handleDisplayShowHomeEnabled(Boolean showHome){
		ActionBar actionBar = getActionBar();
		
		if (actionBar == null){
			return;
		}
		
		actionBar.setDisplayShowHomeEnabled(showHome);
	}
	
	/**
	 * Set whether an activity title/subtitle should be displayed.
	 * @param icon
	 */
	private void handleDisplayShowTitleEnabled(Boolean showTitle){
		ActionBar actionBar = getActionBar();
		
		if (actionBar == null){
			return;
		}
		
		actionBar.setDisplayShowTitleEnabled(showTitle);
	}
	
	/**
	 * Set whether to display the activity logo rather than the activity icon.
	 * @param icon
	 */
	private void handleDisplayUseLogoEnabled(Boolean useLogo){
		ActionBar actionBar = getActionBar();
		
		if (actionBar == null){
			return;
		}
		
		actionBar.setDisplayUseLogoEnabled(useLogo);
	}
	
	/**
	 * Sets the logo with a custom icon font
	 * @param logo
	 */
	private void handleSetLogo(Object obj){
		HashMap args;
		
		if (obj instanceof HashMap){
			args = (HashMap) obj;
		} else {
			Log.e(TAG, "Please pass an Object to setLogo");
			return;
		}
		
		ActionBar actionBar = getActionBar();
		
		if (actionBar == null){
			return;
		}

		actionBar.setLogo(getDrawableFromFont(args));
	}
	
	/**
	 * Sets a custom icon font for a given menu
	 * @param logo
	 */
	private void handleSetMenuItemIcon(Object obj){
		HashMap args;
		
		if (obj instanceof HashMap){
			args = (HashMap) obj;
		} else {
			Log.e(TAG, "Please pass an Object to setMenuItem");
			return;
		}
		
		MenuItemProxy menuItem;
		MenuProxy menuProxy;
		
		if( args.get("menuItem") instanceof MenuItemProxy )
		{
			menuItem = (MenuItemProxy)args.get("menuItem");
		} else {
			Log.e(TAG, "Please provide a valid menuItem");
			return;
		}		
		
		if( args.get( TiC.PROPERTY_MENU ) instanceof MenuProxy )
		{
			menuProxy = (MenuProxy)args.get( TiC.PROPERTY_MENU );
		} else {
			Log.e(TAG, "Please provide a valid menu");
			return;
		}		
		
		Menu mMenu = menuProxy.getMenu();
		
		IconDrawable icon = getDrawableFromFont(args);
		
		if( args.containsKey(TiC.PROPERTY_SIZE) && TiConvert.toInt( args.get( TiC.PROPERTY_SIZE ) )  > 0 )
		{
			icon.sizeDp( TiConvert.toInt( args.get( TiC.PROPERTY_SIZE ) ) );
		} else {
			icon.actionBarSize();
		}
		
		MenuItem item = mMenu.findItem( menuItem.getItemId() );
		if (item != null){
			item.setIcon( icon );
		}
	}
	
	/**
	 * Hides the logo
	 */
	private void handleHideLogo(){
		ActionBar actionBar = getActionBar();
		
		if (actionBar == null){
			return;
		}
		
		try {
			actionBar.setLogo(new ColorDrawable(TiRHelper
					.getAndroidResource("color.transparent")));
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void handleSetWindow(Object obj){
		if (obj instanceof TiWindowProxy){
			window = (TiWindowProxy) obj;
		}
	}
	
	private void handleSetSearchView(Object obj){
		
		SearchView searchView;
		HashMap args;
		
		if (obj instanceof HashMap){
			args = (HashMap) obj;
		} else {
			Log.e(TAG, "Please pass an Object to setSearchViewBackground");
			return;
		}
		
		if (args.containsKey("searchView")){
			SearchViewProxy svp = (SearchViewProxy) args.get("searchView");
			searchView = (SearchView) svp.getOrCreateView().getOuterView();
		} else {
			Log.e(TAG, "Please pass a searchView reference to setSearchViewBackground");
			return;
		}
		
		if (args.containsKey(TiC.PROPERTY_BACKGROUND_COLOR)){
			searchView.setBackgroundColor(TiConvert.toColor((String) args.get(TiC.PROPERTY_BACKGROUND_COLOR)));
		}
		
		if (args.containsKey("line")){
			View searchPlate = null;
			try {
				searchPlate = searchView.findViewById(TiRHelper.getResource("id.search_plate", true));
			} catch (ResourceNotFoundException e) {
				e.printStackTrace();
			}
			
			
			if (searchPlate != null){
				int resId = TiUIHelper.getResourceId(resolveUrl(null, (String) args.get("line")));
				if (resId != 0) {
					searchPlate.setBackgroundResource(resId);
				} else {
					Log.e(TAG, "Couldn't resolve " + args.get("line"));
				}
			}
		}
		
		if (args.containsKey("textColor")){
			try {
				((EditText)searchView
					.findViewById(TiRHelper.getResource("id.search_src_text", true)))
					.setTextColor((TiConvert.toColor((String) args.get("textColor"))));
			} catch (ResourceNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		if (args.containsKey("hintColor")){
			try {
				((EditText)searchView
					.findViewById(TiRHelper.getResource("id.search_src_text", true)))
					.setHintTextColor((TiConvert.toColor((String) args.get("hintColor"))));
			} catch (ResourceNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		if (args.containsKey("cancelIcon")){
			ImageView searchCloseIcon = null;
			try {
				searchCloseIcon = (ImageView) searchView.findViewById(TiRHelper.getResource("id.search_close_btn", true));
			    
			} catch (ResourceNotFoundException e) {
				e.printStackTrace();
			}
			
			
			if (searchCloseIcon != null){
				int resId = TiUIHelper.getResourceId(resolveUrl(null, (String) args.get("cancelIcon")));
				if (resId != 0) {
					searchCloseIcon.setImageResource(resId);
				} else {
					Log.e(TAG, "Couldn't resolve " + args.get("cancelIcon"));
				}
			}
		}
		
		if (args.containsKey("searchIcon")){
			
			// Hack taken from: http://nlopez.io/how-to-style-the-actionbar-searchview-programmatically/
			// but modified ;)
			
			try{
				// Accessing the SearchAutoComplete
				View autoComplete = searchView.findViewById(TiRHelper.getResource("id.search_src_text", true));

				Class<?> clazz = Class.forName("android.widget.SearchView$SearchAutoComplete");

				SpannableStringBuilder stopHint = new SpannableStringBuilder("   ");  
				stopHint.append(searchView.getQueryHint());

				// Add the icon as an spannable
				Drawable searchIcon = TiUIHelper.getResourceDrawable(resolveUrl(null, (String) args.get("searchIcon")));
				if (searchIcon != null){
					Method textSizeMethod = clazz.getMethod("getTextSize");  
					Float rawTextSize = (Float)textSizeMethod.invoke(autoComplete);  
					int textSize = (int) (rawTextSize * 1.25);  
					searchIcon.setBounds(0, 0, textSize, textSize);
					stopHint.setSpan(new ImageSpan(searchIcon), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}

				// Set the new hint text
				Method setHintMethod = clazz.getMethod("setHint", CharSequence.class);  
				setHintMethod.invoke(autoComplete, stopHint);	
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * Sets the Actionbar elevation
	 * See http://developer.android.com/reference/android/support/v7/app/ActionBar.html#setElevation(float)
	 * 
	 * @param Integer -	value
	 */
	private void handleSetElevation(Object value){
		getActionBar().setElevation(TiConvert.toFloat(value));
	}
	
	/**
	 * Set the current hide offset of the action bar
	 * See http://developer.android.com/reference/android/support/v7/app/ActionBar.html#setHideOffset(int)
	 * 
	 * @param Integer -	value
	 */
	private void handleSetHideOffset(Object value){
		
		try{
			getActionBar().setHideOffset(TiConvert.toInt(value));
		}catch(Exception e){
			Log.e(TAG, e.toString());
		}
		
	}
	
	/**
	 * Sets Up icon color
	 * @param obj
	 */
	private void handleSetUpColor(String color){
		
		ActionBar actionBar = getActionBar();
		
		try{
			final Drawable upArrow = TiUIHelper.getResourceDrawable(TiRHelper.getResource("drawable.abc_ic_ab_back_mtrl_am_alpha", true));
			upArrow.setColorFilter(TiConvert.toColor(color), PorterDuff.Mode.SRC_ATOP);
			actionBar.setHomeAsUpIndicator(upArrow);
		}catch(Exception e){
			Log.e(TAG, e.toString());
		}
	}
	
	/**
	 * Set the padding of toolbar
	 * See http://developer.android.com/reference/android/support/v7/app/ActionBar.html#setHideOffset(int)
	 * 
	 * @param Integer -	value
	 */
	private void handleSetToolbarTopPadding(Object value){
		
		try{
			AppCompatActivity activity;
			
			if (window != null){
				activity = (AppCompatActivity) window.getActivity();
			} else {
				TiApplication appContext = TiApplication.getInstance();
				activity = (AppCompatActivity) appContext.getCurrentActivity();
			}
			
			// Retrieve the AppCompact Toolbar
	    Toolbar toolbar = (Toolbar) activity.findViewById(TiRHelper.getResource("id.toolbar", true));
	    activity.setSupportActionBar(toolbar);

	  	// Set the padding 
	    toolbar.setPadding(0, TiConvert.toInt(value), 0, 0);
		}catch(Exception e){
			Log.e(TAG, e.toString());
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
			String fontFamily = d.get(TiC.PROPERTY_FONTFAMILY).replaceAll("\\.(ttf|otf|fnt)$", "");
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
		}else if(obj == null){
			subtitle = null;
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
	 * Set the Statusbar background color
	 * @param color
	 */
	@Kroll.method @Kroll.setProperty
	public void setStatusbarColor(String color) {
		Message message = getMainHandler().obtainMessage(MSG_STATUSBAR_COLOR, color);
		message.sendToTarget();
	}
	
	/**
	 * Set the Navigationbar background color
	 * @param color
	 */
	@Kroll.method @Kroll.setProperty
	public void setNavigationbarColor(String color) {
		Message message = getMainHandler().obtainMessage(MSG_NAVIGATIONBAR_COLOR, color);
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
	public void setHomeAsUpIcon(Object arg) {
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
	 * sets the logo
	 */
	@Kroll.method @Kroll.setProperty
	public void setLogo(Object arg) {
		Message message = getMainHandler().obtainMessage(MSG_LOGO, arg);
		message.sendToTarget();
	}


	/**
	 * sets the logo
	 */
	@Kroll.method @Kroll.setProperty
	public void setMenuItemIcon(Object arg) {
		Message message = getMainHandler().obtainMessage(MSG_MENU_ICON, arg);
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
	 * sets options for the searchview that was passed
	 * @param arg
	 */
	@Kroll.method @Kroll.setProperty
	public void setSearchView(Object arg) {
		Message message = getMainHandler().obtainMessage(MSG_SEARCHVIEW, arg);
		message.sendToTarget();
	}
	
	/**
	 * returns the height of the Statusbar as absolute pixels
	 * @return int	statusbar height
	 */
	@Kroll.getProperty @Kroll.method
	public int getStatusbarHeight() {
		AppCompatActivity activity;
		
		if (window != null){
			activity = (AppCompatActivity) window.getActivity();
		} else {
			TiApplication appContext = TiApplication.getInstance();
			activity = (AppCompatActivity) appContext.getCurrentActivity();
		}
		
		int result = 0;
    int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
        result = activity.getResources().getDimensionPixelSize(resourceId);
    }
    return result;
	}
	
	/**
	 * returns the height of the Actionbar as absolute pixels
	 * @return int	actionbar height
	 */
	@Kroll.getProperty @Kroll.method
	public int getActionbarHeight() {
		TiApplication appContext = TiApplication.getInstance();
		final TypedArray styledAttributes = appContext.getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.actionBarSize }
        );
		int mActionBarSize = (int) styledAttributes.getDimension(0, 0);
		styledAttributes.recycle();
		return mActionBarSize;
	}
	
	/**
	 * sets the Actionbar elevation
	 * @param arg
	 */
	@Kroll.method @Kroll.setProperty
	public void setElevation(Object arg) {
		Message message = getMainHandler().obtainMessage(MSG_ELEVATION, arg);
		message.sendToTarget();
	}
	
	/**
	 * sets the Actionbar hideOffset
	 * @param arg
	 */
	@Kroll.method @Kroll.setProperty
	public void setHideOffset(Object arg) {
		Message message = getMainHandler().obtainMessage(MSG_HIDE_OFFSET, arg);
		message.sendToTarget();
	}
	
	/**
	 * set up icon color
	 * @param color
	 */
	@Kroll.method @Kroll.setProperty
	public void setUpColor(String color){
		Message message = getMainHandler().obtainMessage(MSG_UPICON_COLOR, color);
		message.sendToTarget();
	}
	
	/**
	 * exposes setDisplayShowHomeEnabled
	 * @param boolean
	 */
	@Kroll.method @Kroll.setProperty
	public void setDisplayShowHomeEnabled(boolean showHome){
		Message message = getMainHandler().obtainMessage(MSG_DISPLAY_HOME, showHome);
		message.sendToTarget();
	}
	
	/**
	 * exposes setDisplayShowTitleEnabled
	 * @param boolean
	 */
	@Kroll.method @Kroll.setProperty
	public void setDisplayShowTitleEnabled(boolean showTitle){
		Message message = getMainHandler().obtainMessage(MSG_DISPLAY_TITLE, showTitle);
		message.sendToTarget();
	}
	
	/**
	 * exposes setDisplayUseLogoEnabled
	 * @param boolean
	 */
	@Kroll.method @Kroll.setProperty
	public void setDisplayUseLogoEnabled(boolean useLogo){
		Message message = getMainHandler().obtainMessage(MSG_DISPLAY_USELOGO, useLogo);
		message.sendToTarget();
	}
	
	/**
	 * exposes sets the Toolbar top padding
	 * @param arg
	 */
	@Kroll.method @Kroll.setProperty
	public void setToolbarTopPadding(Object arg) {
		Message message = getMainHandler().obtainMessage(MSG_TOOLBAR_TOP_PADDING, arg);
		message.sendToTarget();
	}
	
	/**
	 * add share action provider to Actionbar
	 * @param args
	 */
	@Kroll.method
	public void addShareAction(KrollDict args) {
		
		ShareActionProvider mShareActionProvider;
		
		MenuItem item = null;
		MenuProxy menu_proxy = (MenuProxy) args.get(TiC.PROPERTY_MENU);
		IntentProxy intent_proxy = (IntentProxy) args.get(TiC.PROPERTY_INTENT);
		String title = "Share";
		int show_as_action = MenuItem.SHOW_AS_ACTION_IF_ROOM;
		Menu menu = menu_proxy.getMenu();

		if (args.containsKey(TiC.PROPERTY_TITLE)) {
			title = TiConvert.toString(args, TiC.PROPERTY_TITLE);
		}

		if (args.containsKey(TiC.PROPERTY_SHOW_AS_ACTION)) {
			show_as_action = TiConvert.toInt(args, TiC.PROPERTY_SHOW_AS_ACTION);
		}
		
		if (args.containsKey("menuItem")) {
			MenuItemProxy mip = (MenuItemProxy) args.get("menuItem");
			item = menu.findItem( mip.getItemId() );
		}

		try {
			TiApplication appContext = TiApplication.getInstance();
			AppCompatActivity activity = (AppCompatActivity) appContext.getCurrentActivity();

			mShareActionProvider = new ShareActionProvider(activity);

			if (item == null){
				item = menu.add(title);
			}

			item.setShowAsAction(show_as_action);
			MenuItemCompat.setActionProvider(item, mShareActionProvider);

			mShareActionProvider.setShareIntent(intent_proxy.getIntent());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
