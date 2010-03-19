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
import org.gwtlib.client.table.ui.Column;
import org.gwtlib.client.table.ui.PagingBar;
import org.gwtlib.client.table.ui.PagingTable;
import org.gwtlib.client.table.ui.SourcesTableEvents;
import org.gwtlib.client.table.ui.TableListenerAdapter;
import org.gwtlib.client.table.ui.renderer.ButtonRenderer;
import org.gwtlib.client.table.ui.renderer.CheckBoxRenderer;
import org.gwtlib.client.table.ui.renderer.DateTimeRenderer;
import org.gwtlib.client.table.ui.renderer.HyperlinkRenderer;
import org.gwtlib.client.table.ui.renderer.ImageRenderer;
import org.gwtlib.client.table.ui.renderer.ListBoxRenderer;
import org.gwtlib.client.table.ui.renderer.NumberRenderer;
import org.gwtlib.client.table.ui.renderer.TextBoxRenderer;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * A demonstration of many paging table features.
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
    /*
    TabPanel tabPanel = new TabPanel();
    tabPanel.setSize("100%", "100%");
    tabPanel.add(createGrid(), "First");
    tabPanel.add(createGrid(), "Second");
    tabPanel.selectTab(0);
    root.add(tabPanel);
     */
    root.add(createGrid());
  }

  private Grid createGrid() {
    final Grid grid = new Grid(3, 1);
    final PagingTable table = createTable();
    table.setSize("100%", "100%");
    grid.setWidget(0, 0, table);
    grid.getRowFormatter().setVerticalAlign(0, HasVerticalAlignment.ALIGN_TOP);
    grid.getCellFormatter().setWidth(0, 0, "100%");
    grid.getCellFormatter().setHeight(0, 0, "100%");
    HorizontalPanel hpanel = new HorizontalPanel();
    hpanel.add(new Label("Show Column:"));
    for(int i = 0; i < table.getColumnLayout().getTotalColumnCount(); ++i) {
      final CheckBox checkbox = new CheckBox(String.valueOf(i));
      checkbox.setValue(true);
      hpanel.add(checkbox);
      final int ii = i;
      checkbox.addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
          table.show(ii, checkbox.getValue());
          table.update();
        }
      });
    }
    grid.setWidget(1, 0, hpanel);
    hpanel = new HorizontalPanel();
    hpanel.add(new Button("Clear", new ClickHandler() {
      public void onClick(ClickEvent event) {
        table.clear();
      }      
    }));
    hpanel.add(new Button("Reset", new ClickHandler() {
      public void onClick(ClickEvent event) {
        table.reset();
      }      
    }));
    hpanel.add(new Button("Simulate Failure", new ClickHandler() {
      public void onClick(ClickEvent event) {
        table.onFailure(null);
      }      
    }));
    grid.setWidget(2, 0, hpanel);
    grid.setSize("100%", "100%");
    return grid;
  }

  private PagingTable createTable() {
    // Set up the columns we want to be displayed
    final CheckBox checkAll = new CheckBox();
    final Column[] columns = {
        new Column(0, false, checkAll, "20", new CheckBoxRenderer()),
        new Column(1, true, "Text (StringRenderer)", "10%"),
        new Column(2, false, "Date (StringRenderer)", "10%"),
        new Column(3, false, "Number (StringRenderer)", "10%"),
        new Column(4, true, "Date (DateTimeRenderer)", "10%", new DateTimeRenderer(DateTimeFormat.getFormat("yyyy-MM-dd"))),
        new Column(5, true, "Number (NumberRenderer)", "10%", new NumberRenderer(NumberFormat.getDecimalFormat())),
        new Column(6, false, "(ListBoxRenderer)", "10%", new ListBoxRenderer(new String[] { "One", "Two", "Three" }, 
        "Select an item")),
        new Column(7, false, "(ButtonRenderer)", "10%", new ButtonRenderer("Click here")),
        new Column(8, false, "(TextBoxRenderer)", "10%", new TextBoxRenderer(50, 5, "Enter your message")),
        new Column(9, false, "(HyperlinkRenderer)", "10%", new HyperlinkRenderer("A Hyperlink")),
        new Column(10, false, "(ImageRenderer)", "10%", new ImageRenderer("An Image")),
    };
    // Generate some semi-random data for our example
    final Row[] rows = new Row[TOTAL_SIZE];
    for(int i = 0; i < rows.length; ++i) {
      Boolean check = new Boolean(false);
      StringBuffer label = new StringBuffer();
      for(int j = 0; j < 25; ++j) label.append((char)('a' + i));
      Date date = new Date(NOW.getTime() + Random.nextInt(365 * 24 * 3600 * 1000));
      Integer number = new Integer(Random.nextInt(10000));
      rows[i] = new Row(i, new Object[] {
          check, label.toString(), date, number, date, number, "One", number.toString(), 
          number.toString(), "Hyperlink", "img/down.gif"
      });
    }
    // Now configure the table
    ColumnLayout layout = new ColumnLayout(columns);
    final PagingTable table = new PagingTable(layout, new PagingBar(0, TOTAL_SIZE, 10, new int[] { 5, 10, 20, 50, 100 }));
    ContentProvider provider = new ContentProvider() {
      // Simulate retrieval of sample data, in requested sort order
      public void load(int begin, int end, final int sortId, boolean ascending) {
        final int sign = ascending ? 1 : -1;
        Row[] tmp = new Row[rows.length];
        for(int i = 0; i < rows.length; ++i) tmp[i] = rows[i];
        switch(sortId) {
          case 1:
            Arrays.sort(tmp, new Comparator<Row>() {
              public int compare(Row o1, Row o2) {
                String v1 = (String)o1.getValue(sortId);
                String v2 = (String)o2.getValue(sortId);
                return sign * (v1.compareTo(v2));
              }
            });
            break;
          case 4:
            Arrays.sort(tmp, new Comparator<Row>() {
              public int compare(Row o1, Row o2) {
                Date v1 = (Date)o1.getValue(sortId);
                Date v2 = (Date)o2.getValue(sortId);
                return sign * (v1.compareTo(v2));
              }
            });
            break;
          case 5:
            Arrays.sort(tmp, new Comparator<Row>() {
              public int compare(Row o1, Row o2) {
                int v1 = ((Integer)o1.getValue(sortId)).intValue();
                int v2 = ((Integer)o2.getValue(sortId)).intValue();
                return sign * (v1 < v2 ? -1 : (v1 == v2 ? 0 : 1));
              }
            });
            break;
          default:
            break;
        }
        Row[] srows = new Row[Math.min(end - begin, tmp.length - begin)];
        for(int i = 0; i < srows.length; ++i) srows[i] = tmp[begin + i];
        table.onSuccess(new Rows(srows, begin, sortId, ascending));
      }
    };
    table.setContentProvider(provider);
    table.addTableListener(new TableListenerAdapter() {
      public void onCellClicked(SourcesTableEvents sender, Row row, Column column) {
        for(int i = 0; i < columns.length; ++i) columns[i].setState(Column.State.NONE);
        column.setState(Column.State.SELECT);
      }

      public void onRowClicked(SourcesTableEvents sender, Row row) {
        GWT.log("Row clicked (id " + row.getId() + ")", null);
        for(int i = 0; i < rows.length; ++i) rows[i].setState(Row.State.NONE);
        row.setState(Row.State.SELECT);
        table.refreshRowState();
      }

      public void onClick(SourcesTableEvents sender, Row row, Column column, Widget widget) {
        GWT.log("Renderer widget clicked", null);
        if(widget instanceof CheckBox) {
          row.setValue(0, new Boolean(((CheckBox)widget).getValue()));
        } else if (widget instanceof Button) {
          Window.alert(((Button)widget).getHTML());
        } else if (widget instanceof Hyperlink) {
          Window.alert(((Hyperlink)widget).getHTML());
        } else if (widget instanceof Image) {
          Window.alert(((Image)widget).getUrl());
        }
      }

      public void onChange(SourcesTableEvents sender, Row row, Column column, Widget widget) {
        GWT.log("Renderer widget changed", null);
        if(widget instanceof ListBox) {
          ListBox listBox = (ListBox)widget;
          row.setValue(6, listBox.getValue(listBox.getSelectedIndex()));
        } else if(widget instanceof TextBox) {
          row.setValue(8, ((TextBox)widget).getText());
        }
      }
    });
    checkAll.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        for(int i = 0; i < rows.length; ++i) rows[i].setValue(0, new Boolean(checkAll.getValue()));
        table.update();
      }
    });
    table.update();
    return table;
  }
}