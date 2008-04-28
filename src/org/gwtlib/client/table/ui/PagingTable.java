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

import org.gwtlib.client.table.ColumnLayout;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Widget;

/**
 * A subclass of table which adds paging support.
 * 
 * @author Sander Berents
 */
public class PagingTable extends Table {
  protected PagingBar _paging;

  public PagingTable(ColumnLayout layout, PagingBar paging) {
    super(layout, false);
    _paging = paging;
    _panel.add(paging);
    initWidget(_panel);

    paging.addChangeListener(new ChangeListener() {
      public void onChange(Widget sender) {
        GWT.log("Position update to " + _paging.getPosition(), null);
        setPosition(_paging.getPosition());
      }
    });
    
    addTableListener(new TableListenerAdapter() {
      public boolean onSortColumn(SourcesTableEvents sender, Column column, boolean ascending) {
        _paging.setPage(0);
        _paging.update();
        return true;
      }
    });
  }
}