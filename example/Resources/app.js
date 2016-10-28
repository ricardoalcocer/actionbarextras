var abx = require('com.alcoapps.actionbarextras'),
    opts = {
        shareAction: false,
        titleFont: false,
        subtitleFont: false,
        disableIcon: false,
        dropdown: false,
        homeup: false,
        menuIcon: false,
        elevation: true,
        material: false,
        upColor: false
    },
    dropdown,
    IconicFont = require('/lib/IconicFont'),
    fa = new IconicFont({
        font: '/lib/FontAwesome'
    });;

var win = Titanium.UI.createWindow();
abx.window = win;

var searchView = Ti.UI.Android.createSearchView({
    hintText: "Search ABX..."
});

var table = Ti.UI.createTableView({
  width: Ti.UI.FILL,
  height: Ti.UI.FILL,
  search: searchView,
  searchAsChild: false
});

var data = [
  { title: 'Title Font', action: "titleFont" },
  { title: 'Subtitle Font', action: "subtitleFont" },
  { title: 'Title Color', action: "titleColor" },
  { title: 'Subtitle Color', action: "subtitleColor" },
  { title: 'Background Color', action: "backgroundColor" },
  { title: 'Disable Icon', action: "disableIcon" },
  { title: 'Hide Logo', action: "hideLogo" },
  { title: 'Sharing Action', action: "sharing" },
  { title: 'Dropdown', action: "dropdown" },
  { title: 'Custom Up icon', action: "customUp" },
  { title: 'Set font object', action: "fontObject" },
  { title: '2nd window', action: "secondWindow" },
  { title: 'Hide Subtitle', action: "hideSubtitle" },
  { title: 'Get Actionbar height', action: "actionbarHeight" },
  { title: 'Set Logo (FontAwesome)', action: "logoFont" },
  { title: 'Set MenuItem icon (FontAwesome)', action: "menuIcon" },
  { title: 'Set MenuItem icon (MaterialIcons)', action: "materialIcon"},
  { title: 'Set Title (FontAwesome)', action: "iconTitle" },
  { title: 'Set statusbarColor', action: "statusbarColor" },
  { title: 'Set navigationColor', action: "navigationbarColor" },
  { title: 'Set elevation', action: "elevation" },
  { title: 'Set Up Icon Color', action: "upColor" },
  { title: 'Set Actionbar image: Titan Logo', action: "actionbarImage", params: { image: "images/ic_titan_logo.png" } },
  { title: 'Set Actionbar image: Appc Emblem', action: "actionbarImage", params: { image: "images/ic_appc_emblem.png" } },
  { title: 'Set Actionbar image: Appc Logo', action: "actionbarImage", params: { image: "images/ic_appc_logo.png" } },
  { title: 'Set Actionbar image: Alloy Logo', action: "actionbarImage", params: { image: "images/ic_alloy_logo.png" } },
  { title: 'Disable Actionbar image', action: "actionbarImage" }
];

var actions = {
  titleFont: function(){
      abx.setTitleFont(opts.titleFont ? "Aller" : "Chunkfive");
      opts.titleFont = !opts.titleFont;
  },
  subtitleFont: function(){
      abx.setSubtitleFont(opts.subtitleFont ? "Aller" : "Chunkfive");
      opts.subtitleFont = !opts.subtitleFont;
  },
  titleColor: function(){
      abx.setTitleColor('#'+Math.floor(Math.random()*16777215).toString(16));
  },
  subtitleColor: function(){
      abx.setSubtitleColor('#'+Math.floor(Math.random()*16777215).toString(16));
  },
  backgroundColor: function(){
      abx.setBackgroundColor('#'+Math.floor(Math.random()*16777215).toString(16));
  },
  disableIcon: function(){
      opts.disableIcon = !opts.disableIcon;
      abx.setDisableIcon( opts.disableIcon );
  },
  hideLogo: function(){
      abx.hideLogo();
  },
  sharing: function(){
      opts.shareAction = !opts.shareAction;
      win.activity.invalidateOptionsMenu();
  },
  dropdown: function(){
      opts.dropdown = !opts.dropdown;
      
      if (opts.dropdown){
          dropdown = abx.createDropdown({
              titles: ["First", "Second", "Third"]
          });
          
          dropdown.addEventListener('change', function(e){
              Ti.API.info("dropdown changed to: " + e.index);
          });
      }else{
          dropdown.remove();
      }
      
  },
  customUp: function(){
      opts.homeup = !opts.homeup;
      win.activity.invalidateOptionsMenu();
  },
  fontObject: function(){
      abx.setTitle({
        text: "Font Object",
        font: {
          fontSize: 26,
          fontFamily: 'Chunkfive'
        }
      });
      
      abx.setSubtitle({
        text: "Subtitle",
        font: {
          fontSize: '14dp',
          fontWeight: 'bold',
          fontFamily: 'Chunkfive'
        }
      });
  },
  secondWindow: function(){
      var win2 = Ti.UI.createWindow({ backgroundColor: 'yellow' });
      win2.addEventListener('open', function(){
        
        // here we want to test if abx updates the first window
        // as we set abx.window earlier
        abx.title = "Changed title";
        abx.subtitle = "while other win was in foreground";
        
        // now we can close this
        setTimeout(function(){
          win2.close();
        }, 2000);
      });
      
      win2.open();
  },
  hideSubtitle: function(){
    abx.setSubtitle(null);
  },
  actionbarHeight: function(){
    alert( abx.getActionbarHeight() );
  },
  logoFont: function(){
    abx.setLogo({
      icon: fa.icon("fa-smile-o"),
      fontFamily: fa.fontfamily,
      color: "yellow"
    });
  },
  menuIcon: function(){
    opts.menuIcon = !opts.menuIcon;
    win.activity.invalidateOptionsMenu();
  },
  iconTitle: function(){
    abx.setTitle({
      text: fa.icon("fa-camera-retro")+"  "+fa.icon("fa-paw")+"  "+fa.icon("fa-android"),
      font: fa.fontfamily,
      color: "green"
    });
  },
  statusbarColor: function(){
    abx.setStatusbarColor('#'+Math.floor(Math.random()*16777215).toString(16));
  },
  navigationbarColor: function(){
    abx.setNavigationbarColor('#'+Math.floor(Math.random()*16777215).toString(16));
  },
  elevation: function(){
    // default Actionbar elevation is 4 dp according to the material design guide
    abx.setElevation(opts.elevation ? 0 : (4 * Ti.Platform.displayCaps.dpi / 160));
    opts.elevation = !opts.elevation;
  },
  materialIcon: function(){
    opts.material = !opts.material;
    win.activity.invalidateOptionsMenu();
  },
  upColor: function(){
    opts.upColor = true;
    win.activity.invalidateOptionsMenu();
  },
  actionbarImage: function(params){
    if ((params == null) || (params == {})) {
      abx.disableActionbarImage();
    } else {
      abx.setActionbarImage({
        image: params.image
      });
    }
  }
};

table.setData((function(_rows){
  var rows = [];
  _rows.forEach(function(row){
    rows.push(Ti.UI.createTableViewRow({
      title: row.title,
      action: row.action,
      params: row.params || null,
      height: '48dp',
      color: '#000',
      backgroundColor: '#fff',
      left: 20
    }));
  });
  return rows;
})(data));

table.addEventListener('click', function(e){
  actions[e.row.action](e.row.params || null);
});

win.addEventListener('open',function(e){

    // set initial values
    abx.title = "ActionbarExtras";
    abx.titleFont = "Aller";
    abx.subtitle = "for some extra action";
    abx.subtitleFont = "Aller";

    var activity = win.getActivity();

    if(activity){

        activity.onCreateOptionsMenu = function(e){

            e.menu.clear();

            e.menu.add({
                title: 'Search',
                actionView : searchView,
                icon: (Ti.Android.R.drawable.ic_menu_search ? Ti.Android.R.drawable.ic_menu_search : "my_search.png"),
                showAsAction: Ti.Android.SHOW_AS_ACTION_IF_ROOM | Ti.Android.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW
            });

            // changing a searchview
            abx.setSearchView({
              searchView: searchView,
              backgroundColor: '#777',
              textColor: "yellow",
              hintColor: "orange",
              line: "/images/my_textfield_activated_holo_light.9.png",
              cancelIcon: "/images/cancel.png",
              searchIcon: "/images/search.png",
              maxWidth: Number.MAX_VALUE
            });

            if (opts.shareAction){
                // This is how you add a basic Share Action to your ActionBar
                // this should be done within the onCreateOptionsMenu
                // because we need to pass a reference to the menu

                // at first, create a basic share intent
                var intent = Ti.Android.createIntent({
                   action: Ti.Android.ACTION_SEND,
                   type: 'text/plain'
                });
                intent.putExtra(Ti.Android.EXTRA_TEXT, 'Hello world!');

                // now pass over the menu and the intent like this
                abx.addShareAction({
                    menu: e.menu,
                    intent: intent,
                    showAsAction: Ti.Android.SHOW_AS_ACTION_ALWAYS, // optional - default: SHOW_AS_ACTION_IF_ROOM
                    title: "Share Hello World" // optional - default: "Share"
                });
            }
            
            if (opts.homeup) {
                activity.actionBar.displayHomeAsUp = true;
                abx.setHomeAsUpIcon("/images/menu.png");
            }
            
            if (opts.menuIcon){
              
              // Using FontAwesome for MenuItems
              
              // first, create the item...
              var settingsItem = e.menu.add({
                itemId: 101, // don't forget to set an id here
                title: "Settings",
                showAsAction : Ti.Android.SHOW_AS_ACTION_ALWAYS
              });
              settingsItem.addEventListener('click', function(){
                alert('Settings clicked');
              });

              // ...then, let abx apply the custom font
              abx.setMenuItemIcon({
                menu: e.menu,
                menuItem: settingsItem,
                fontFamily: fa.fontfamily,
                icon: fa.icon("fa-gear"),
                color: "#333333",
                size: 30
              });

            }

            if (opts.material){

              // Using MaterialIcons for MenuItems

              // first, create the item...
              var settingsItem = e.menu.add({
                itemId: 101, // don't forget to set an id here
                title: "Settings",
                showAsAction : Ti.Android.SHOW_AS_ACTION_ALWAYS
              });
              settingsItem.addEventListener('click', function(){
                alert('Settings clicked');
              });

              // ...then, let abx apply the custom font
              abx.setMenuItemIcon({
                menu: e.menu,
                menuItem: settingsItem,
                fontFamily: 'MaterialIcons-Regular',
                icon: String.fromCharCode(0xe86a),
                color: "#fff",
                size: 30
              });

            }
            
            if (opts.upColor){
              activity.actionBar.displayHomeAsUp = true;
              abx.setUpColor('#'+Math.floor(Math.random()*16777215).toString(16));
            }
        };
    }
});

win.add(table);
win.open();
