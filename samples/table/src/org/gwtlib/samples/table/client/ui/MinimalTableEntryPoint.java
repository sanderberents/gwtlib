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

import org.gwtlib.client.table.ColumnLayout;
import org.gwtlib.client.table.ContentProvider;
import org.gwtlib.client.table.Row;
import org.gwtlib.client.table.Rows;
import org.gwtlib.client.table.ui.Column;
import org.gwtlib.client.table.ui.Table;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * A minimalistic example demonstrating a very basic table.
 * 
 * @author Sander Berents
 */
public class MinimalTableEntryPoint implements EntryPoint {
  private static final String ID = "gwtlib-table";

  public void onModuleLoad() {
    RootPanel root = RootPanel.get(ID);
    if(root != null) init(root);
  }

  private void init(RootPanel root) {
    Column[] columns = {
      new Column(0, false, "First column", "50%"),
      new Column(1, false, "Second column", "50%")
    };
    final Row[] rows = {
      new Row(0, new Object[] { "First row, first column" , "First row, second column" }),
      new Row(1, new Object[] { "Second row, first column", "Second row, second column" }),
      new Row(2, new Object[] { "Third row, first column" , "Third row, second column" })
    };
    ColumnLayout layout = new ColumnLayout(columns);
    final Table table = new Table(layout);
    ContentProvider provider = new ContentProvider() {
      public void load(int begin, int end, final int sortId, boolean ascending) {
        table.onSuccess(new Rows(rows, 0, -1, false));
      }
    };
    table.setContentProvider(provider);
    table.setSize(3);
    table.setSize("100%", "100%");
    root.add(table);
  }
}