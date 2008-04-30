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

import org.gwtlib.client.table.Row;

import com.google.gwt.user.client.ui.Widget;

/**
 * Event listener interface for table events.
 * 
 * @author Sander Berents
 */
public interface TableListener {
  /**
   * Fired when a header or body cell is clicked.
   */
  public void onCellClicked(SourcesTableEvents sender, int row, int column);

  /**
   * Fired when a body row is clicked.
   */
  public void onRowClicked(SourcesTableEvents sender, Row row);

  /**
   * Fired when a renderer widget is clicked.
   */
  public void onClick(SourcesTableEvents sender, Row row, Column column, Widget widget);

  /**
   * Fired when the header is selected or deselected by clicking on the optional checkbox.
   * @param sender
   * @param select
   */
  public void onHeaderSelected(SourcesTableEvents sender, boolean select);

  /**
   * Fired when a row is selected or deselected by clicking on the optional checkbox.
   * @param sender
   * @param row Row
   * @param select
   */
  public void onRowSelected(SourcesTableEvents sender, Row row, boolean select);

  /**
   * Fired before a column is to be shown or hidden.
   * @param sender
   * @param column
   * @param visible
   * @return false to cancel.
   */
  public boolean onShowColumn(SourcesTableEvents sender, Column column, boolean visible);

  /**
   * Fired before a column is to be sorted.
   * @param sender
   * @param column
   * @param ascending
   * @return false to cancel.
   */
  public boolean onSortColumn(SourcesTableEvents sender, Column column, boolean ascending);

  /**
   * Fired before data is being loaded.
   * @param sender
   */
  public void onLoad(SourcesTableEvents sender);

  /**
   * Fired after data has been loaded or an error occurred during loading.
   * @param sender
   * @param success
   */
  public void onLoaded(SourcesTableEvents sender, boolean success);

  /**
   * Fired before a table is about to be redrawn.
   * @param sender
   */
  public void onRender(SourcesTableEvents sender);

  /**
   * Fired after a table has been redrawn.
   * @param sender
   */
  public void onRendered(SourcesTableEvents sender);
  
  /**
   * Fired before a widget is about to be redrawn in a cell.
   * @param sender
   * @param row
   * @param column
   * @param widget
   */
  public void onRenderCell(SourcesTableEvents sender, Row row, Column column, Widget widget);
}
