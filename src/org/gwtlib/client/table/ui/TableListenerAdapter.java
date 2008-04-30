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
 * An adapter to simplify table event listeners that do not need all events 
 * defined on the TableListener interface.
 *
 * @author Sander Berents
 */
public class TableListenerAdapter implements TableListener {
  public void onCellClicked(SourcesTableEvents sender, int row, int column) {
  }

  public void onRowClicked(SourcesTableEvents sender, Row row) {
  }

  public void onClick(SourcesTableEvents sender, Row row, Column column, Widget widget) {
  }

  public void onHeaderSelected(SourcesTableEvents sender, boolean select) {
  }

  public void onRowSelected(SourcesTableEvents sender, Row row, boolean select) {
  }

  public boolean onShowColumn(SourcesTableEvents sender, Column column, boolean visible) {
    return true;
  }

  public boolean onSortColumn(SourcesTableEvents sender, Column column, boolean ascending) {
    return true;
  }

  public void onLoad(SourcesTableEvents sender) {
  }

  public void onLoaded(SourcesTableEvents sender, boolean success) {
  }

  public void onRender(SourcesTableEvents sender) {
  }

  public void onRendered(SourcesTableEvents sender) {
  }

  public void onRenderCell(SourcesTableEvents sender, Row row, Column column, Widget widget) {
  }
}
