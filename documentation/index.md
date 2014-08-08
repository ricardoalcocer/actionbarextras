# ActionBarExtras - Documentation

## Content

* [1. Installation](#instalation)
* [2. Properties](#properties)
* [3. Methods](#methods)

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

* `subtitle` _(String)_ - sets the Actionbar subtitle

* `backgroundColor` _(String)_ - sets the Actionbar backgroundColor

* `titleFont` _(String)_ - sets the Actionbar title font

* `subtitleFont` _(String)_ - sets the Actionbar subtitle font

* `titleColor` _(String)_ - sets the Actionbar title color

* `subtitleColor` _(String)_ - sets the Actionbar subtitle color

* `disableIcon` _(Boolean)_ - disables / enables the Actionbar icon


## 3. Methods

* `setTitle(  )`  - sets the `title` property

* `setSubtitle(  )`  - sets `subtitle` property

* `setBackgroundColor(  )` - sets the `backgroundColor` property

* `setTitleFont(  )` - sets the `titleFont` property

* `setSubtitleFont(  )` - sets the `subtitleFont` property

* `setTitleColor(  )` - sets the `titleColor` property

* `setSubtitleColor(  )` - sets the `subtitleColor` property

* `setDisableIcon(  )` - sets the `disableIcon` property

* `setColor( )` - sets the color of title and subtitle

* `setFont( )` - sets the font of title and subtitle

* ~~`setExtras( )` - _DEPRECATED_~~

* `addShareAction( (Object) parameters )` - adds a Share action to the Actionbar

    _parameters:_
    - `menu` _(Ti.Android.Menu)_ - a reference to the menu
    - `intent` _(Ti.Android.Intent)_ - sharing Intent
    - `title` _(String)_ - __optional__ default: "Share"
    - `showAsAction` _(Number)_ - __optional__ default: Ti.Android.SHOW_AS_ACTION_IF_ROOM
