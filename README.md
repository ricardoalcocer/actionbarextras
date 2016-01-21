![](https://raw.githubusercontent.com/ricardoalcocer/actionbarextras/master/documentation/header.png)

# ActionBarExtras 
[![gittio](http://img.shields.io/badge/gittio-1.6.9-00B4CC.svg)](http://gitt.io/component/com.alcoapps.actionbarextras)
[![License](http://img.shields.io/badge/license-MIT-orange.svg)](http://mit-license.org)
[![issues](http://img.shields.io/github/issues/ricardoalcocer/actionbarextras.svg)](https://github.com/ricardoalcocer/actionbarextras/issues)

- [Overview](#overview)
- [Features](#features)
- [Installation](#installation)
- [Example](example/)
- [API docs](documentation/index.md)
- [Apps](#some-apps-using-actionbarextras)
- [Contributors](#contributors)
- [License](#license)

## Overview
This module provides some extra functionality to configure the ActionBar that Titanium doesn't offer.

## Features
  * Title and Subtitle
  * Sharing Action Provider
  * Dropdown menu
  * font: type, color, size, weight
  * FontAwesome support for title, logo and menu
  * and many more...

## Installation
### Get it [![gitTio](http://gitt.io/badge.png)](http://gitt.io/component/com.alcoapps.actionbarextras)
Download the latest distribution ZIP-file and consult the [Titanium Documentation](http://docs.appcelerator.com/titanium/latest/#!/guide/Using_a_Module) on how install it, or simply use the [gitTio CLI](http://gitt.io/cli):

`$ gittio install com.alcoapps.actionbarextras`

### Using it

First require it:

```javascript
var abx = require('com.alcoapps.actionbarextras');
```

At this point the feature for forcing the "menu overflow" has been attached to your Activity, so if that's the only thing you were looking for, you're set.

Now, you can set custom properties like this:
```javascript
// NOTE: make sure that your window is open 
// before you access the actionbar with abx
win.addEventListener('open',function(e){
  // setting extras
  abx.title = "The Title";
  abx.titleFont = "Chunkfive.otf";
  abx.titleColor = "blue";
});
```

To see what else you can do, see:
* [Example project](example/)
* [Documentation](documentation/index.md)

## Some apps using ActionBarExtras

* [Peerby](http://goo.gl/0JwYWj)
* [Collapp](http://goo.gl/zA7KZz)
* [Unit+Size Converter](http://goo.gl/QMWNrK)

> Make sure you send me your app links or a PR with an updated README.md

## Contributors

See [contributors](https://github.com/ricardoalcocer/actionbarextras/graphs/contributors)

## License
MIT License - [http://alco.mit-license.org](http://alco.mit-license.org)
