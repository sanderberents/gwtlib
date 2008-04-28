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
package org.gwtlib.samples.table.client.ui;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import org.gwtlib.client.table.ColumnLayout;
import org.gwtlib.client.table.ContentProvider;
import org.gwtlib.client.table.Row;
import org.gwtlib.client.table.Rows;
import org.gwtlib.client.table.ui.AbstractContentProvider;
import org.gwtlib.client.table.ui.Column;
import org.gwtlib.client.table.ui.PagingBar;
import org.gwtlib.client.table.ui.PagingTable;
import org.gwtlib.client.table.ui.SourcesTableEvents;
import org.gwtlib.client.table.ui.TableListenerAdapter;
import org.gwtlib.client.table.ui.renderer.CheckBoxRenderer;
import org.gwtlib.client.table.ui.renderer.DateTimeRenderer;
import org.gwtlib.client.table.ui.renderer.NumberRenderer;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Paging table demo.
 * 
 * @author Sander Berents
 */
public class PagingTableEntryPoint implements EntryPoint {
  private static final String ID = "gwtlib-table";
  private static final Date NOW = new Date();
  private static final int TOTAL_SIZE = 25;

  public void onModuleLoad() {
    RootPanel root = RootPanel.get(ID);
    if(root != null) init(root);
  }

  private void init(RootPanel root) {
    // Set up the columns we want to be displayed
    final CheckBox checkAll = new CheckBox();
    Column[] columns = {
      new Column(0, false, checkAll, "20", new CheckBoxRenderer()),
      new Column(1, true, "Text (StringRenderer)", "60%"),
      new Column(2, false, "Date (StringRenderer)", "10%"),
      new Column(3, false, "Number (StringRenderer)", "10%"),
      new Column(4, true, "Date (DateTimeRenderer)", "10%", new DateTimeRenderer(DateTimeFormat.getFormat("yyyy-MM-dd"))),
      new Column(5, true, "Number (NumberRenderer)", "10%", new NumberRenderer(NumberFormat.getDecimalFormat())),
    };
    // Generate some semi-random data for our example
    final Row[] rows = new Row[TOTAL_SIZE];
    for(int i = 0; i < rows.length; ++i) {
      Boolean check = new Boolean(false);
      StringBuffer label = new StringBuffer();
      for(int j = 0; j < 5; ++j) label.append((char)('a' + i));
      Date date = new Date(NOW.getTime() + Random.nextInt(365 * 24 * 3600 * 1000));
      Integer number = new Integer(Random.nextInt(10000));
      rows[i] = new Row(i, new Object[] { check, label.toString(), date, number, date, number });
    }
    // Now configure the table
    ColumnLayout layout = new ColumnLayout(columns);
    final PagingTable table = new PagingTable(layout, new PagingBar(TOTAL_SIZE, 10));
    ContentProvider provider = new AbstractContentProvider(table) {
      // Simulate retrieval of sample data, in requested sort order
      public void load(int begin, int end, final int sortId, boolean ascending) {
        final int sign = ascending ? 1 : -1;
        Row[] tmp = new Row[rows.length];
        for(int i = 0; i < rows.length; ++i) tmp[i] = rows[i];
        switch(sortId) {
          case 1:
            Arrays.sort(tmp, new Comparator() {
              public int compare(Object o1, Object o2) {
                String v1 = (String)((Row)o1).getValue(sortId);
                String v2 = (String)((Row)o2).getValue(sortId);
                return sign * (v1.compareTo(v2));
              }
            });
            break;
          case 4:
            Arrays.sort(tmp, new Comparator() {
              public int compare(Object o1, Object o2) {
                Date v1 = (Date)((Row)o1).getValue(sortId);
                Date v2 = (Date)((Row)o2).getValue(sortId);
                return sign * (v1.compareTo(v2));
              }
            });
            break;
          case 5:
            Arrays.sort(tmp, new Comparator() {
              public int compare(Object o1, Object o2) {
                int v1 = ((Integer)((Row)o1).getValue(sortId)).intValue();
                int v2 = ((Integer)((Row)o2).getValue(sortId)).intValue();
                return sign * (v1 < v2 ? -1 : (v1 == v2 ? 0 : 1));
              }
            });
            break;
          default:
            break;
        }
        Row[] srows = new Row[Math.min(end - begin, tmp.length - begin)];
        for(int i = 0; i < srows.length; ++i) srows[i] = tmp[begin + i];
        _callback.onSuccess(new Rows(srows, begin, sortId, ascending));
      }
    };
    table.setContentProvider(provider);
    table.addTableListener(new TableListenerAdapter() {
      public void onRowClicked(SourcesTableEvents sender, Row row) {
        GWT.log("Row clicked (id " + row.getId() + ")", null);
        for(int i = 0; i < rows.length; ++i) {
          rows[i].setState(Row.State.NONE);
          row.setState(Row.State.SELECT);
        }
        table.refreshRowState();
      }

      public void onClick(SourcesTableEvents sender, Row row, Column column, Widget widget) {
        GWT.log("Renderer widget clicked", null);
        if(widget instanceof CheckBox) {
          row.setValue(0, new Boolean(((CheckBox)widget).isChecked()));
        }
      }
    });
    checkAll.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        for(int i = 0; i < rows.length; ++i) rows[i].setValue(0, new Boolean(checkAll.isChecked()));
        table.update();
      }
    });
    table.setSize(10);
    root.add(table);
  }
}