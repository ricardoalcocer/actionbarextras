# ActionBarExtras - Documentation

## Content

* [1. Installation](#1-instalation)
* [2. Properties](#2-properties)
* [3. Methods](#3-methods)

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
        * font _(String)_
        * color _(String)_

* `subtitle` _(String)_ - sets the Actionbar subtitle
    * instead of a String, you can also pass an Object with these properties:
        * text _(String)_
        * font _(String)_
        * color _(String)_

* `backgroundColor` _(String)_ - sets the Actionbar backgroundColor

* `titleFont` _(String)_ - sets the Actionbar title font

* `subtitleFont` _(String)_ - sets the Actionbar subtitle font

* `titleColor` _(String)_ - sets the Actionbar title color

* `subtitleColor` _(String)_ - sets the Actionbar subtitle color

* `disableIcon` _(Boolean)_ - disables / enables the Actionbar icon


## 3. Methods

* `setTitle( arg )`  - sets the `title` property
    * arg _(Object | String)_ - title

* `setSubtitle( arg )`  - sets `subtitle` property
    * arg _(Object | String)_ - subtitle

* `setBackgroundColor( arg )` - sets the `backgroundColor` property
    * arg _(String)_ - color

* `setTitleFont( arg )` - sets the `titleFont` property
    * arg _(String)_ - font

* `setSubtitleFont( arg )` - sets the `subtitleFont` property
    * arg _(String)_ - font

* `setTitleColor( arg )` - sets the `titleColor` property
    * arg _(String)_ - color

* `setSubtitleColor( arg )` - sets the `subtitleColor` property
    * arg _(String)_ - color

* `setDisableIcon( arg )` - sets the `disableIcon` property
    * arg _(Boolean)_ (optional) - enabled / disabled

* `setColor( arg )` - sets the color of title and subtitle
    * arg _(String)_ - color

* `setFont( arg )` - sets the font of title and subtitle
    * arg _(String)_ - color

* ~~`setExtras( arg )` - _DEPRECATED_~~
    * arg _(Object)_ - extras

* `addShareAction( arg )` - adds a Share action to the Actionbar

    * arg _(Object)_:
        * `menu` _(Ti.Android.Menu)_ - a reference to the menu
        * `intent` _(Ti.Android.Intent)_ - sharing Intent
        * `title` _(String)_ - __optional__ default: "Share"
        * `showAsAction` _(Number)_ - __optional__ default: Ti.Android.SHOW_AS_ACTION_IF_ROOM
