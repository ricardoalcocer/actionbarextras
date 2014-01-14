# ActionBarExtras

Titanium only exposes the Title property of the ActionBar.  With this module, you can set a Title and Subtitle.  Also, Titanium's implementation of the ActionBar shows the ActionBar menu overflow icon ONLY on devices with no hardware menu button, like the Nexus.  This module forces the ActionBar to show the overflow.

![example](http://s9.postimg.org/w9wwqbu3z/abextras.png)

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
            
            if (!TiApplication.isUIThread()) {
                
                if (args.containsKey("title")){
                    actionBar.setTitle((String) args.get("title"));
                }
                
                if (args.containsKey("subtitle")){
                    actionBar.setSubtitle((String) args.get("subtitle"));
                }
                
                if (args.containsKey("backgroundColor")) {
                    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor((String) args.get("backgroundColor"))));
                }
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
            subtitle:'This is the subtitle',
            backgroundColor:'#ff4f00'
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

