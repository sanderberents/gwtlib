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
import org.gwtlib.client.ui.Messages;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

/**
 * A subclass of table which adds paging support using a paging bar.
 * 
 * CSS Style Rules:
 * <ul>
 * <li>(see Table CSS style rules)</li>
 * </ul>
 * 
 * @author Sander Berents
 */
public class PagingTable extends Table {
  private static final String STYLE = "gwtlib-PagingTable";
  protected PagingBar _paging;

  /**
   * Creates a new paging table with the given column layout and paging bar.
   * @param messages
   * @param layout
   * @param paging
   */
  public PagingTable(Messages messages, ColumnLayout layout, PagingBar paging) {
    super(layout, false);
    _messages = messages;
    _paging = paging;
    init(paging);
  }

  /**
   * Creates a new paging table with the given column layout and paging bar.
   * @param layout
   * @param paging
   */
  public PagingTable(ColumnLayout layout, PagingBar paging) {
    super(layout, false);
    _paging = paging;
    init(paging);
  }

  private void init(PagingBar paging) {
    _panel.setWidget(1, 0, paging);
    _panel.getFlexCellFormatter().addStyleName(1, 0, "paging-cell");
    _begin = paging.getPosition();
    _size = paging.getPageSize();
    initWidget(_panel);
    _panel.setStylePrimaryName(STYLE);

    paging.addValueChangeHandler(new ValueChangeHandler<Integer>() {
      @Override
      public void onValueChange(ValueChangeEvent<Integer> event) {
        if(_size == _paging.getPageSize()) {
          setPosition(_paging.getPosition());
        } else {
          clear();
          _begin = _paging.getPosition();
          _size = _paging.getPageSize();
          update();
        }		
      }
    });

    addTableListener(new TableListenerAdapter() {
      public boolean onSortColumn(SourcesTableEvents sender, Column column, boolean ascending) {
        _paging.setPage(0);
        _paging.update();
        return true;
      }

      public void onLoad(SourcesTableEvents sender) {
        _paging.setLoading(true);
      }

      public void onLoaded(SourcesTableEvents sender, boolean success) {
        _paging.setLoading(false);
      }
    });
  }

  /**
   * Returns the paging bar.
   * @return Paging bar or null.
   */
  public PagingBar getPagingBar() {
    return _paging;
  }

  public void reset() {
    super.reset();
    _paging.setPage(0);
    _paging.update();
  }

  public void clear() {
    super.clear();
    _paging.setPage(0);
    _paging.update();
  }
}
