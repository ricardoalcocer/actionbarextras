# ActionBarExtras

This module provides some extra functionality to configure the ActionBar that Titanium doesn't offer. It lets you set a subtitle to the ActionBar title, it forces to show the Overflow menu button on devices with hardware menu buttons and gives you the oppertunity to change the ActionBar font (of both, title and subtitle or seperatly).

![example](http://s15.postimg.org/bqpkegtsb/20140415_143615.jpg)

## Features
  * Title and Subtitle
  * force overflow
  * backgroundColor
  * custom fonts

## Get it [![gitTio](http://gitt.io/badge.png)](http://gitt.io/component/com.alcoapps.actionbarextras)
Download the latest distribution ZIP-file and consult the [Titanium Documentation](http://docs.appcelerator.com/titanium/latest/#!/guide/Using_a_Module) on how install it, or simply use the [gitTio CLI](http://gitt.io/cli):

`$ gittio install com.alcoapps.actionbarextras`

## Using it

```javascript
var abextras = require('com.alcoapps.actionbarextras');

// setting extras
abextras.setExtras({
  title:'This is the title',
  subtitle:'This is the subtitle',
  font: 'my_custom_font.otf',
  backgroundColor:'#ff4f00'
});

```
