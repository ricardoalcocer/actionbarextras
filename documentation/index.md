# ActionBarExtras - Documentation

## Content

* [1. Installation](#1-instalation)
* [2. Properties](#2-properties)
* [3. Methods](#3-methods)
* [4. Dropdown](#4-dropdown)
* [5. SearchView](#5-searchview)

## 1. Installation

#### Get it [![gitTio](http://gitt.io/badge.png)](http://gitt.io/component/com.alcoapps.actionbarextras)
Download the latest distribution ZIP-file and consult the [Titanium Documentation](http://docs.appcelerator.com/titanium/latest/#!/guide/Using_a_Module) on how install it, or simply use the [gitTio CLI](http://gitt.io/cli):

##### `$ gittio install com.alcoapps.actionbarextras`

#### Using it

Please make sure that your window is open before you access the actionbar.

```javascript
var abextras = require('com.alcoapps.actionbarextras');
```

## 2. Properties

* `title` _(String)_ - sets the Actionbar title
    * instead of a String, you can also pass an Object with these properties:
        * text _(String)_
        * font _(String | Object)_
        * color _(String)_

* `subtitle` _(String)_ - sets the Actionbar subtitle
    * instead of a String, you can also pass an Object with these properties:
        * text _(String)_
        * font _(String | Object)_
        * color _(String)_

* `backgroundColor` _(String)_ - sets the Actionbar backgroundColor (Only used for the primary action bar. If you use a split action bar, you should go for a theme to set the color)

* `titleFont` _(String | Object)_ - sets the Actionbar title font

* `subtitleFont` _(String | Object)_ - sets the Actionbar subtitle font

* `titleColor` _(String)_ - sets the Actionbar title color

* `subtitleColor` _(String)_ - sets the Actionbar subtitle color

* `disableIcon` _(Boolean)_ - disables / enables the Actionbar icon

* `homeAsUpIcon` _(String)_ - sets the homeAsUp icon

* `window` _(Ti.UI.Window)_ - Sets a reference to a window. By default, ActionbarExtras will use
                              the current window, but you may want to define a specific reference.
                              
* `actionbarHeight` _(Number)_ - returns the height of the Actionbar as absolute pixels

* `statusbarColor` _(String)_ - sets the Color of the status bar

* `navigationbarColor` _(String)_ - sets the Color of the navigation bar

* `elevation` _(Number)_ - sets the Elevation of the Actionbar (in dp)

* `upColor` _(String)_ - sets the color of the up icon

* `displayShowHomeEnabled` _(Boolean)_ - Set whether to include the application home affordance in the action bar.

* `displayShowTitleEnabled` _(Boolean)_ - Set whether an activity title/subtitle should be displayed.

* `displayUseLogoEnabled` _(Boolean)_ - Set whether to display the activity logo rather than the activity icon.

## 3. Methods
    
* `setTitle( arg )`  - sets the `title` property
    * arg _(Object | String)_ - title

* `setSubtitle( arg )`  - sets `subtitle` property
    * arg _(Object | String)_ - subtitle

* `setBackgroundColor( arg )` - sets the `backgroundColor` property
    * arg _(String)_ - color

* `setTitleFont( arg )` - sets the `titleFont` property
    * arg _(String | Object)_ - font

* `setSubtitleFont( arg )` - sets the `subtitleFont` property
    * arg _(String | Object)_ - font

* `setTitleColor( arg )` - sets the `titleColor` property
    * arg _(String)_ - color

* `setSubtitleColor( arg )` - sets the `subtitleColor` property
    * arg _(String)_ - color

* `setDisableIcon( arg )` - sets the `disableIcon` property
    * arg _(Boolean)_ (optional) - enabled / disabled

* `setHomeAsUpIcon( arg )` - sets the `homeAsUpIcon` property
    * arg _(String)_ - Url to the icon

* `hideLogo( )` - hides the logo

* `setColor( arg )` - sets the color of title and subtitle
    * arg _(String)_ - color

* `setFont( arg )` - sets the font of title and subtitle
    * arg _(String)_ - color
    
* `setWindow( arg )` - sets the window property
    * arg _(Ti.UI.Window)_ - window
    
* `setLogo( arg )` - sets a custom logo based on a font
	* args _(Object)_:
		* `icon` _(String)_ - The icon to use from the iconfont
		* `fontFamily` _(String)_ - the font to use
		* `color` _(String)_ - the icon color
    
* `setMenuItemIcon( arg )` - sets a custom menu item icon based on a font
	* args _(Object)_:
		* `menu` _(Ti.Android.Menu)_ - a reference to the menu
		* `menuItem` _(Ti.Android.MenuItem)_ - a reference to the menu item (NOTE: You must set an `itemId` on the menu item)
		* `icon` _(String)_ - The icon to use from the iconfont
		* `fontFamily` _(String)_ - the font to use
		* `color` _(String)_ - the icon color
		* `size` _(Number)_ - the icon size __optional__ default: Actionbar icon size ( 24 dp )

* `addShareAction( arg )` - adds a Share action to the Actionbar

    * arg _(Object)_:
        * `menu` _(Ti.Android.Menu)_ - a reference to the menu
        * `intent` _(Ti.Android.Intent)_ - sharing Intent
        * `title` _(String)_ - __optional__ default: "Share"
        * `showAsAction` _(Number)_ - __optional__ default: Ti.Android.SHOW_AS_ACTION_IF_ROOM

* `createDropdown( arg )` - adds a [dropdown](#4-dropdown) menu to the ActionBar

    * arg _(Object)_:
        * `titles` _(String[])_ - an Array of Strings, representing the dropdown items
        * `keepTitle` _(Boolean)_ - if set to true, it shows both, title and dropdown, otherwise the title get replaced by the dropdown (Optional, default: false)
        
* `getActionbarHeight( )`  - returns the height of the Actionbar as absolute pixels

* `setStatusbarColor( arg )` - sets the color of the status bar
    * arg _(String)_ - color
    
* `setNavigationbarColor( arg )` - sets the color of the navigation bar
    * arg _(String)_ - color
        
* `setElevation( arg )` - sets the elevation property
    * arg _(Number)_ - elevation
    
* `setUpColor( arg )` - sets the upColor property
    * arg _(String)_ - color

* `setDisplayShowHomeEnabled( arg )` - sets the displayShowHomeEnabled property
    * arg _(Boolean)_ - showHome

* `setDisplayShowTitleEnabled( arg )` - sets the displayShowTitleEnabled property
    * arg _(Boolean)_ - showTitle

* `setDisplayUseLogoEnabled( arg )` - sets the displayUseLogoEnabled property
    * arg _(Boolean)_ - useLogo

## 4. Dropdown

An ActionBar [Dropdown](http://developer.android.com/guide/topics/ui/actionbar.html#Dropdown) can be created with `createDropdown`. Make sure you pass a String-Array as `titles`
 property. Example:

```javascript
var dropdown = abextras.createDropdown({
    titles: [ "Home", "Search", "Likes", "Settings" ],
    index: 1 // optional (default: 0)
});
```

#### Properties

* `activeItem` _(Number)_ - the index of the current active dropdown item

#### Methods

* `remove` - removes the drop-down from the ActionBar

#### Events

* `change` - fired when dropdown item is changed
    * `index` _(Number)_ - index of the selected dropdown item


## 5. SearchView

You can style an [SearchView](http://docs.appcelerator.com/titanium/latest/#!/api/Titanium.UI.Android.SearchView) with Actionbar Extras. This is done with the method:

* `setSearchView( args )` - enhances an existing search view

    * args _(Object)_:
        * `searchView` _(Ti.UI.Android.SearchView)_ - a reference to the search view
        * `backgroundColor` _(String)_  -  changes the background color of the search view
        * `textColor` _(String)_  - changes the text color of the search view
        * `hintColor` _(String)_  - changes the hint color of the search view
        * `line` _(String)_  -  image path to replace the line drawable used by the search view
        * `cancelIcon` _(String)_ - image path to use a custom close icon
        * `searchIcon` _(String)_ - image path to use a custom magnifier icon
        
An example of how to use this is included in the [example app](https://github.com/ricardoalcocer/actionbarextras/blob/master/example/Resources/app.js).

## 5. Appendix

#### Using [IconFont](https://github.com/FokkeZB/IconFont) with ABX (thanks @casevictor)
 
```js
var icons = require('font-awesome');

activity.onCreateOptionsMenu = function(e) {
  e.menu.clear();

  var createItem = e.menu.add({
    itemId: 101, //unique random number
    showAsAction: Ti.Android.SHOW_AS_ACTION_ALWAYS
  });

  abx.setMenuItemIcon({
    menu: e.menu,
    menuItem: createItem,
    fontFamily: 'FontAwesome',
    icon: icons.check, 
    color: 'white',
    size: 30
  })
};

activity.invalidateOptionsMenu();
```
