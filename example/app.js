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

    // setting other stuff via TiAPI
    var activity=win.activity;
    if(activity){
        var ab=activity.actionBar;
        if (ab){
            ab.displayHomeAsUp=true;
        }else{
            alert('no actionbar');
        }
    }else{
        alert('no activity');
    }
    //
});

win.open();
