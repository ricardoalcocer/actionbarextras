var abextras = require('com.alcoapps.actionbarextras');

var win = Titanium.UI.createWindow({
    backgroundColor:'#fff',
    navBarHidden: false
});

win.addEventListener('open',function(e){

    // setting title, subtitle, font and color
    abextras.setExtras({
        title:'This is the title',
        subtitle:'This is the subtitle',
        font: 'burnstown_dam.otf',
        color: 'red'
    });

    // setting the different fonts for title and subtitle
    // fonts are taken from
    // http://docs.appcelerator.com/titanium/latest/#!/guide/Custom_Fonts
    abextras.setTitleFont('burnstown_dam.otf'); // actually redundant, because it's already set in setExtras
    abextras.setSubtitleFont('SpicyRice-Regular.ttf');

    
    var activity=win.activity;
    if(activity){
        
        // This is how you add a basic Share Action to your ActionBar
        // this should be done within the onCreateOptionsMenu
        // because we need to pass a reference to the menu
        
        activity.onCreateOptionsMenu = function(e){
            
            // at first, create a basic share intent
            var intent = Ti.Android.createIntent({
               action: Ti.Android.ACTION_SEND,
               type: 'text/plain'
            });
            intent.putExtra(Ti.Android.EXTRA_TEXT, 'Hello world!');
            
            // now pass over the menu and the intent like this
            abextras.addShareAction({
                menu: e.menu,
                intent: intent,
                showAsAction: Ti.Android.SHOW_AS_ACTION_ALWAYS, // optional - default: SHOW_AS_ACTION_IF_ROOM
                title: "Share Hello World" // optional - default: "Share"
            });
        };
    }
});

win.open();
