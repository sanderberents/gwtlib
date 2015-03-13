GWTLib is a lightweight GWT widget library that currently includes `MenuBar`, `PagingBar`, `Table`, `PagingTable` and `StyleListBox` widgets. It does not rely on any external Javascript libraries and has no dependencies except GWT itself. GWTLib is compatible with GWT 2.x and higher.


The `MenuBar` widget is an extension of the standard GWT `MenuBar` that adds checkmark and radio menu item toggling and enabling/disabling.

![http://sites.google.com/site/gwtlib/menubar/MenuBarSample.png](http://sites.google.com/site/gwtlib/menubar/MenuBarSample.png)

The `PagingBar` widget can be used to navigate through any given number of items. The total number of items, the page size (which may be one) and the page size presets can all be configured.

![http://gwtlib.googlepages.com/PagingBarSample.png](http://gwtlib.googlepages.com/PagingBarSample.png)

The `Table` widget has the following features:

  * `ContentProvider` interface for data retrieval.
  * `Renderer` interface for rendering different types of data. Currently included implementations are `StringRenderer` (the default), `NumberRenderer`, `DateTimeRenderer`, `CheckBoxRenderer`, `ListBoxRenderer`, `ButtonRenderer`, `TextBoxRenderer`, `HyperlinkRenderer` and `ImageRenderer`. The sample demonstrates all nine.
  * Sorting support.
  * Client side caching.
  * Row, column and cell selection.

In its most simple form, it just renders strings:

![http://gwtlib.googlepages.com/MinimalTable.gif](http://gwtlib.googlepages.com/MinimalTable.gif)

The `PagingTable` combines the `Table` and `PagingBar` widgets and adds pagination to the standard table. Like `Table` itself, it is not limited to displaying strings, but can display any widget in its cells using the included or custom `Renderer` implementations.

![http://gwtlib.googlepages.com/PagingTableSample.gif](http://gwtlib.googlepages.com/PagingTableSample.gif)

![http://gwtlib.googlepages.com/PagingTableSample2.png](http://gwtlib.googlepages.com/PagingTableSample2.png)

The `StyleListBox` widget adds style support to individual items of the standard GWT `ListBox`.

### News ###

  * Thursday, July 14, 2011
> > GWTLib 1.1.1: Bug fixes.

  * Tuesday, March 23, 2010
> > GWTLib 1.1.0: Support for radio menu items.

  * Friday, March 19, 2010
> > GWTLib 1.0.0: Supports the new GWT event system (thanks Alan). I switched to GWT 2.x a few months back, but it probably still works with GWT 1.7.x as well.

  * Saturday, May 2, 2009
> > GWTLib 0.1.6: Adds more default renderers, renderer tooltips and the column select state.

  * Friday, May 1, 2009
> > GWTLib 0.1.5: This release requires at least GWT 1.5 and adds menu item enabling/disabling to the standard GWT menu.

  * Tuesday, November 4, 2008
> > GWTLib 0.1.4: This release adds toggle menu item support to the standard GWT menu. Please note that this is the last version compatible with GWT 1.4.x.

  * Tuesday, October 21, 2008
> > GWTLib 0.1.3: Besides bug fixes, this release adds basic scrolling support to the table. Please see CHANGES.TXT for more details.

  * Wednesday, May 7, 2008
> > GWTLib 0.1.2: The focus of this release is  bug fixes. Please see CHANGES.TXT for the change log.

  * Thursday, May 1, 2008
> > GWTLib 0.1.1: Please see CHANGES.TXT for the change log.

  * Monday, April 28, 2008
> > GWTLib 0.1.0: In addition to adding the build script and tweaking the resources, I have also added two more table examples. One demonstrates a minimalistic table (a good starting point), the other shows how to use a RPC service with the table.

  * Sunday, April 27, 2008
> > The initial code has been checked in!
