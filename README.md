# ActionBarExtras

Titanium only exposes the Title property of the ActionBar.  With this module, you can set a Title and Subtitle.

![example](http://s17.postimg.org/bzh4h1fmn/Screen_Shot_2013_12_02_at_4_51_24_PM.png)

The module is so simple that I can actually write the function in the README:

     public void setExtras(KrollDict args)
     {
            Log.d(TAG, "called the setextras method");
            
            // Get the app context
            TiApplication appContext = TiApplication.getInstance();
            
            // from the context get the activity
            Activity activity = appContext.getCurrentActivity();
            
            // from the activity get the actionbar
            ActionBar actionBar = activity.getActionBar();
            
            String mTitle="";
            String mSubtitle="";
            
            // get arguments from Javascript
            
            if (args.containsKey("title")){
            	mTitle=(String) args.get("title");
            }
            
            if (args.containsKey("subtitle")){
            	mSubtitle=(String) args.get("subtitle");
            }
            
            // apply new values to actionbar
            if (!TiApplication.isUIThread()) {
				actionBar.setTitle(mTitle);
				actionBar.setSubtitle(mSubtitle);
            }
    }
    
This module is a great starting point for anyone how'd like to get into Native Android Development for Titanium.

# Using it

Download the module ZIP file, decompress it and drop the module folder in your app's folder, at the same level as the Resources folder.  Go all the way into the folder structure and delete the two jar files inside the lib folder.

Edit your tiapp.xml to add a reference to the module:

     <modules>
          <module version="1.0" platform="android">com.alcoapps.actionbarextras</module>
     </modules>

In your app.js (or any Window for that matter)

    var abextras = require('com.alcoapps.actionbarextras');

    var win = Titanium.UI.createWindow({  
        backgroundColor:'#fff',
        navBarHidden: false
    });

    win.addEventListener('open',function(e){
	    // setting title and subtitle via module
        abextras.setExtras({
		    title:'This is the title',
            subtitle:'This is the subtitle'
        });
        //
    
        // setting other stuff via TiAPI
        var activity=win.activity;
        if(activity){
            var ab=activity.actionBar;
            if (ab){
    	            ab.displayHomeAsUp=true
            }else{
    		        alert('no actionbar');
            }
        }else{
    	        alert('no activity');
        }
        //
    })

    win.open();

