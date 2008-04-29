/*
 * Copyright 2008 Sander Berents
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.gwtlib.client.table.ui;

import java.util.ArrayList;
import java.util.List;

import org.gwtlib.client.table.ColumnLayout;
import org.gwtlib.client.table.ContentProvider;
import org.gwtlib.client.table.Row;
import org.gwtlib.client.table.Rows;
import org.gwtlib.client.table.RowsCache;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Table.
 * CSS Style Rules
 * <ul>
 * <li>.gwtlib-Table { the table itself }</li>
 * <li>.gwtlib-Header { the table header cells }</li>
 * </ul>
 *
 * @author Sander Berents
 */
public class Table extends AbstractComposite implements SourcesTableEvents {
  private static final String STYLE            = "gwtlib-Table";
  private static final String STYLE_SCROLL     = "gwtlib-Table-scroll";
  private static final String STYLE_HEADER     = "gwtlib-Header";
  private static final String STYLE_SORTABLE   = "gwtlib-Header-sortable";
  private static final String STYLE_ASCENDING  = "gwtlib-Column-ascending";
  private static final String STYLE_DESCENDING = "gwtlib-Column-descending";
  private static final String STYLE_ROW        = "gwtlib-Row";
  private static final String STYLE_ROW_EVEN   = "gwtlib-Row-even";
  private static final String STYLE_ROW_ODD    = "gwtlib-Row-odd";
  private static final String STYLE_ROW_EMPTY  = "gwtlib-Row-rempty";
  private static final String STYLE_ROW_SELECT = "gwtlib-Row-selected";

  public interface Style {
    public static final int SINGLE_SELECT = 1 << 0;
    public static final int MULTI_SELECT  = 1 << 1;
  };

  protected VerticalPanel _panel;
  protected FlexTable _table;
  protected ScrollPanel _scroll;
  protected ColumnLayout _layout;
  protected ContentProvider _provider;
  protected RowsCache _cache;
  protected int _begin = 0;
  protected int _size = 10;
  protected List _listeners;

  public Table(ColumnLayout layout) {
    this(layout, true);
  }

  protected Table(ColumnLayout layout, boolean initWidget) {
    super(Style.SINGLE_SELECT);
    _layout = layout;
    _cache = new RowsCache();
    _listeners = new ArrayList();
    _panel = new VerticalPanel();
    _table = new FlexTable();
/*
    _scroll = new ScrollPanel(_table);
    _scroll.setStylePrimaryName(STYLE_SCROLL);
    panel.add(_scroll);
*/
    _panel.add(_table);
    if(initWidget) initWidget(_panel);
    _table.setStylePrimaryName(STYLE);

    _table.addTableListener(new com.google.gwt.user.client.ui.TableListener() {
      public void onCellClicked(com.google.gwt.user.client.ui.SourcesTableEvents sender, int row, int col) {
        GWT.log("onCellClicked " + row + "," + col, null);
        fireCellClickedEvent(row, col);
        if(row == 0) {
          Column column = _layout.getColumn(col);
          if(column.isSortable()) {
            sort(col, column.getSortDirection() != Column.Sort.ASCENDING);
          }
        } else {
          Row r = _cache.getRow(_begin + row - 1);
          if(r != null) fireRowClickedEvent(r);
        }
      }
    });
  }
  
  public void setContentProvider(ContentProvider provider) {
    _provider = provider;
  }
  
  public void setSize(int size) {
    _size = size;
    update();
  }

  public void setPosition(int pos) {
    _begin = pos;
    update();
  }

  public void refreshRowState() {
    Rows rows = getRows(_begin);
    int r = 1;
    for(int i = 0; i < rows.size(); ++i, ++r) {
      Row row = rows.getRow(i);
      _table.getRowFormatter().setStyleName(r, STYLE_ROW); 
      _table.getRowFormatter().addStyleName(r, i % 2 == 0 ? STYLE_ROW_EVEN : STYLE_ROW_ODD);
      if(row.hasState(Row.State.SELECT)) _table.getRowFormatter().addStyleName(r, STYLE_ROW_SELECT);
    }
    int nclear = Math.min(_size - rows.size(), _table.getRowCount() - rows.size() - 1);
    while(nclear-- > 0) { 
      for(int j = 0; j < _layout.getTotalColumnCount(); ++j) {
        Column column = _layout.getColumn(j);
        if(column.isVisible()) {
          _table.getRowFormatter().setStyleName(r, STYLE_ROW); 
          _table.getRowFormatter().addStyleName(r, STYLE_ROW_EMPTY); 
          _table.clearCell(r, j);
        }
      }
      ++r;
    }
  }

  public void update() {
    fetch(_begin, _begin + _size);
  }

  public void reset() {
    _begin = 0;
    _cache.clear();
    update();
  }

  protected void fetch(int begin, int end) {
    int sortId = -1;
    boolean ascending = false;
    int sortColumn = _layout.getSortColumn();
    if(sortColumn >= 0) {
      sortId = _layout.getColumn(sortColumn).getId();
      ascending = _layout.getColumn(sortColumn).getSortDirection() == Column.Sort.ASCENDING;
    }
    Rows rows = _cache.getRows(begin, end, sortId, ascending);
    if(rows.size() == end - begin) {
      onSuccess(rows);
    } else {
      _provider.load(begin, end, sortId, ascending);
    }
  }

  public void show(int column, boolean visible) {
    Column col = _layout.getColumn(column);
    if(fireShowColumnEvent(col, visible)) {
      col.setVisible(visible);
    }
  }

  public void sort(int column, boolean ascending) {
    Column col = _layout.getColumn(column);
    if(fireSortColumnEvent(col, ascending)) {
      int sortColumn = _layout.getSortColumn();
      if(sortColumn != -1) {
        setColumnSortStyle(sortColumn, Column.Sort.NONE);
        _layout.getColumn(sortColumn).setSortDirection(Column.Sort.NONE);
      }
      int dir = ascending ? Column.Sort.ASCENDING : Column.Sort.DESCENDING;
      _layout.getColumn(column).setSortDirection(dir);
      _begin = 0;
      fetch(_begin, _size);
    }
  }

  /**
   * Returns displayed rows.
   * @return
   */
  public Rows getRows() {
    return getRows(_begin);
  }

  protected void renderHeader() {
    for(int i = 0; i < _layout.getTotalColumnCount(); ++i) {
      Column column = _layout.getColumn(i);
      if(column.isVisible()) {
        _table.setWidget(0, i, column);
        _table.getCellFormatter().setStylePrimaryName(0, i, STYLE_HEADER);
        if(column.isSortable()) _table.getCellFormatter().addStyleName(0, i, STYLE_SORTABLE);
        setColumnSortStyle(i, column.getSortDirection());
      }
    }
  }

  protected void render(int pos) {
    Rows rows = getRows(pos);
    GWT.log("Rendering " + rows.size() + " rows", null);
    int r = 1;
    for(int i = 0; i < rows.size(); ++i, ++r) {
      final Row row = rows.getRow(i);
      for(int j = 0; j < _layout.getTotalColumnCount(); ++j) {
        int c = j;
        final Column column = _layout.getColumn(j);
        if(column.isVisible()) {
          final Widget widget = column.getRenderer().render(row, column, row.getValue(j));
          if(widget instanceof SourcesClickEvents) {
            ((SourcesClickEvents)widget).addClickListener(new ClickListener() {
              public void onClick(Widget sender) {
                fireClickEvent(row, column, widget);
              }
            });
          }
          fireRenderCellEvent(row, column, widget);
          _table.setWidget(r, c, widget);
        }
      }
    }
    for(; r < _size; ++r) {
      for(int c = 0; c < _layout.getTotalColumnCount(); ++c) {
        _table.setWidget(r, c, new Label());
      }      
    }
    refreshRowState();
    _begin = pos;
  }

  protected Rows getRows(int begin) {
    int sortId = -1;
    boolean ascending = false;
    int sortColumn = _layout.getSortColumn();
    if(sortColumn >= 0) {
      sortId = _layout.getColumn(sortColumn).getId();
      ascending = _layout.getColumn(sortColumn).getSortDirection() == Column.Sort.ASCENDING;
    }
    Rows rows = _cache.getRows(begin, begin + _size, sortId, ascending);
    return rows;
  }

  public void onSuccess(Rows rows) {
    _cache.merge(rows);
    fireRenderEvent();
    renderHeader();
    render(rows.getBegin());
    fireRenderedEvent();
  }

  public void onFailure(Throwable caught) {
  }

  public void addTableListener(TableListener listener) {
    _listeners.add(listener);
  }

  public void removeTableListener(TableListener listener) {
    _listeners.remove(listener);
  }

  protected void fireCellClickedEvent(int row, int column) {
    for(int i = 0; i < _listeners.size(); ++i) {
      TableListener listener = (TableListener)_listeners.get(i);
      listener.onCellClicked(this, row, column);
    }
  }

  protected void fireRowClickedEvent(Row row) {
    for(int i = 0; i < _listeners.size(); ++i) {
      TableListener listener = (TableListener)_listeners.get(i);
      listener.onRowClicked(this, row);
    }
  }

  protected void fireClickEvent(Row row, Column column, Widget widget) {
    for(int i = 0; i < _listeners.size(); ++i) {
      TableListener listener = (TableListener)_listeners.get(i);
      listener.onClick(this, row, column, widget);
    }
  }

  protected boolean fireShowColumnEvent(Column column, boolean visible) {
    for(int i = 0; i < _listeners.size(); ++i) {
      TableListener listener = (TableListener)_listeners.get(i);
      if(!listener.onShowColumn(this, column, visible)) return false;
    }
    return true;
  }

  protected boolean fireSortColumnEvent(Column column, boolean ascending) {
    for(int i = 0; i < _listeners.size(); ++i) {
      TableListener listener = (TableListener)_listeners.get(i);
      if(!listener.onSortColumn(this, column, ascending)) return false;
    }
    return true;
  }

  protected void fireRenderEvent() {
    for(int i = 0; i < _listeners.size(); ++i) {
      TableListener listener = (TableListener)_listeners.get(i);
      listener.onRender(this);
    }
  }

  protected void fireRenderedEvent() {
    for(int i = 0; i < _listeners.size(); ++i) {
      TableListener listener = (TableListener)_listeners.get(i);
      listener.onRendered(this);
    }
  }

  protected void fireRenderCellEvent(Row row, Column column, Widget widget) {
    for(int i = 0; i < _listeners.size(); ++i) {
      TableListener listener = (TableListener)_listeners.get(i);
      listener.onRenderCell(this, row, column, widget);
    }
  }

  private void setColumnSortStyle(int col, int dir) {
    int row = 0;
    FlexTable.FlexCellFormatter formatter = _table.getFlexCellFormatter();
    switch(dir) {
      case Column.Sort.ASCENDING:
        formatter.removeStyleName(row, col, STYLE_DESCENDING);
        formatter.addStyleName(row, col, STYLE_ASCENDING);
      break;
      case Column.Sort.DESCENDING:
        formatter.removeStyleName(row, col, STYLE_ASCENDING);
        formatter.addStyleName(row, col, STYLE_DESCENDING);
      break;
      default:
        formatter.removeStyleName(row, col, STYLE_ASCENDING);
        formatter.removeStyleName(row, col, STYLE_DESCENDING);
      break;
    }
  }
}