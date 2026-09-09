package com.alcoapps.actionbarextras;

import java.util.HashMap;
import java.util.List;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollPropertyChange;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.KrollProxyListener;
import org.appcelerator.kroll.annotations.Kroll;
import android.os.Message;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;


@Kroll.proxy(creatableInModule=ActionbarextrasModule.class)
public class DropdownProxy extends KrollProxy implements KrollProxyListener  {
	
	private static final int MSG_FIRST_ID = KrollProxy.MSG_LAST_ID + 1;
	private static final int MSG_ACTIVE_ITEM = MSG_FIRST_ID + 100;
	private static final int MSG_REMOVE = MSG_FIRST_ID + 101;
	private static final int MSG_ADD = MSG_FIRST_ID + 102;
	
	@SuppressWarnings("deprecation")
	ActionBar.OnNavigationListener navigationListener = new ActionBar.OnNavigationListener() {

        public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        	
        	setProperty("activeItem", itemPosition);
            
        	if (hasListeners("change")) {
    			HashMap<String, Object> event = new HashMap<String, Object>();
    			event.put("index", itemPosition);
    			fireEvent("change", event);
    		}
        	
            return false;
        }
    };
	
	public DropdownProxy() {
		super();
	}
	
	/**
	 * message handler
	 * @param msg
	 */
	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
			case MSG_ACTIVE_ITEM: {
				handleSetActiveItem((Integer) msg.obj);
				return true;
			}
			case MSG_REMOVE: {
				handleRemove();
				return true;
			}
			case MSG_ADD: {
				handleAdd((Boolean) msg.obj);
				return true;
			}
			default: {
				return super.handleMessage(msg);
			}
		}

	}
	
	@Override
	public void handleCreationDict(KrollDict options) {
		
		final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
		final boolean keepTitle;
		
		if (options.containsKey("keepTitle")) {
			keepTitle = options.getBoolean("keepTitle");
		}else{
			keepTitle = false;
		}
		
		add(keepTitle);
	    
	    if (options.containsKey("titles")) {
	    	final String[] dropdownValues = options.getStringArray("titles");
	    	
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(actionBar.getThemedContext(),
	            android.R.layout.simple_spinner_item, android.R.id.text1,
	            dropdownValues);

	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

	        actionBar.setListNavigationCallbacks(adapter, navigationListener);
		}
	    
	    if (options.containsKey("index")) {
	    	int activeItem = options.getInt("index");
	    	setActiveItem(activeItem);
	    }

		super.handleCreationDict(options);
	}
	
	@Kroll.method @Kroll.setProperty
	public void setActiveItem(int activeItem){
		getMainHandler().obtainMessage(MSG_ACTIVE_ITEM, activeItem).sendToTarget();
	}
	
	@Kroll.method
	public void remove(){
		getMainHandler().obtainMessage(MSG_REMOVE).sendToTarget();
	}
	
	public void add(boolean keepTitle){
		getMainHandler().obtainMessage(MSG_ADD, keepTitle).sendToTarget();
	}
	
	private void handleSetActiveItem(int activeItem){
		ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
		actionBar.setSelectedNavigationItem(activeItem);
	}
	
	private void handleRemove(){
		ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	}
	
	private void handleAdd(boolean keepTitle){
		ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(keepTitle);
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	}
    
	@Override
	public void propertyChanged(String key, Object oldValue, Object newValue, KrollProxy proxy) {
        if ((oldValue == newValue) ||
            ((oldValue != null) && oldValue.equals(newValue))) {
            return;
        }
        
        Log.d("ABX", "propertyChanged ---> " + key + ": " + newValue);
        
        if (key.equals("activeItem")){
        	int activeItem = (Integer) newValue;
        	getMainHandler().obtainMessage(MSG_ACTIVE_ITEM, activeItem).sendToTarget();

        }
	}

	@Override
	public void listenerAdded(String type, int count, KrollProxy proxy) {
		
	}

	@Override
	public void listenerRemoved(String type, int count, KrollProxy proxy) {
		
	}

	@Override
	public void processProperties(KrollDict dict) {
		
	}

	@Override
	public void propertiesChanged(List<KrollPropertyChange> changes, KrollProxy proxy) {
		
	}

}
