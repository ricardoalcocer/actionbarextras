# ActionBarExtras [![gittio](http://img.shields.io/badge/gittio-1.4.1-00B4CC.svg)](http://gitt.io/component/com.alcoapps.actionbarextras)

This module provides some extra functionality to configure the ActionBar that Titanium doesn't offer.

![example](documentation/example.png)

## Features
  * Title and Subtitle
  * Sharing Action Provider
  * Dropdown menu
  * force overflow
  * backgroundColor
  * font: type, color, size, weight
  * disable icon
  * custom Up icon

## Get it [![gitTio](http://gitt.io/badge.png)](http://gitt.io/component/com.alcoapps.actionbarextras)
Download the latest distribution ZIP-file and consult the [Titanium Documentation](http://docs.appcelerator.com/titanium/latest/#!/guide/Using_a_Module) on how install it, or simply use the [gitTio CLI](http://gitt.io/cli):

`$ gittio install com.alcoapps.actionbarextras`

## Using it

First require it:

```javascript
var abx = require('com.alcoapps.actionbarextras');
```

At this point the feature for forcing the "menu overflow" has been attached to your Activity, so if that's the only thing you were looking for, you're set.

Now, you can set custom properties like this:
```javascript
// setting extras
abx.title = "The Title";
abx.titleFont = "Chunkfive.otf";
abx.titleColor = "blue";
```

To see what else you can do, see:
* [Example project](example/)
* [Documentation](documentation/index.md)

## Some apps using ActionBarExtras

<table>
  <tr>
    <td align="center"><img src="http://drops.ricardoalcocer.com/drops/screen_peerby.png" width="250"/><br/><a href="http://goo.gl/0JwYWj">Peerby</a></td>
    <td align="center"><img src="http://drops.ricardoalcocer.com/drops/screen_collapp.png" width="250"/><br/><a href="http://goo.gl/zA7KZz">Collapp</a></td>
  </tr>
  <tr>
    <td align="center"><img src="http://drops.ricardoalcocer.com/drops/screen_unitconverter.png" width="250"/><br/><a href="http://goo.gl/QMWNrK">Unit+Size Converter</a></td>
    <td align="center"/>
  </tr>
</table>

> Make sure you send me your app links or a PR with an updated README.md

## Contribuitors

* [Ricardo Alcocer](https://github.com/ricardoalcocer)
* [Timan Rebel](https://github.com/timanrebel)
* [Manuel Lehner](https://github.com/manumaticx)

## License
MIT License - [http://alco.mit-license.org](http://alco.mit-license.org)
