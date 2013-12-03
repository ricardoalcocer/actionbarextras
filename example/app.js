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