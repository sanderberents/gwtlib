## Introduction ##

`Table` uses the `ContentProvider` interface for data retrieval. Its

```
  public void load(int begin, int end, int sortId, boolean ascending)
```

method is called by the table whenever it needs to retrieve additional data. After the data has been retrieved, the table's `onSuccess` method must be called, or `onFailure` upon an error.

The `MinimalTable` example shows how to display three rows of data:

![http://gwtlib.googlepages.com/MinimalTable.gif](http://gwtlib.googlepages.com/MinimalTable.gif)

Even though `Rows` is `Serializable`, when using RPC it is recommened to transfer RPC domain objects and convert them on the client. The `RPCTable` example demonstrates this. It displays data retrieved from a service:

![http://gwtlib.googlepages.com/RPCTable.gif](http://gwtlib.googlepages.com/RPCTable.gif)