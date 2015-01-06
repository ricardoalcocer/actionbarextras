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

## 4. Dropdown

An ActionBar [Dropdown](http://developer.android.com/guide/topics/ui/actionbar.html#Dropdown) can be created with `createDropdown`. Make sure you pass a String-Array as `titles`
 property. Example:

```javascript
var dropdown = abextras.createDropdown({
    titles: [ "Home", "Search", "Likes", "Settings" ]
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
        
An example of how to use this is included in the [example app](https://github.com/ricardoalcocer/actionbarextras/blob/master/example/Resources/app.js).