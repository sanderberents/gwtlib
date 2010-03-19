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
package org.gwtlib.client.table;

import org.gwtlib.client.table.ui.Column;

/**
 * Column layout.
 *
 * @author Sander Berents
 */
public class ColumnLayout {
  public interface Style {
    public static final int SIMPLE = 1 << 0;
  };

  protected Column[] _columns;

  public ColumnLayout(Column[] columns) {
    _columns = columns;
  }

  public Column getColumn(int column) {
    return _columns[column];
  }

  public int indexOf(Column column) {
    for(int i = 0; i < _columns.length; ++i) if(column.getId() == _columns[i].getId()) return i;
    return -1;
  }
  
  public int getSortColumn() {
    for(int i = 0; i < _columns.length; ++i) {
      Column column = _columns[i];
      if(column.getSortDirection() != Column.Sort.NONE) return i;
    }
    return -1;
  }
 
  /**
   * Returns the total number of columns.
   * @return Total number of columns.
   */
  public int getTotalColumnCount() {
    return _columns.length;
  }

  /**
   * Returns the number of visible columns.
   * @return Number of visible columns.
   */
  public int getVisibleColumnCount() {
    int n = 0;
    for(int i = 0; i < _columns.length; ++i) if(_columns[i].isVisible()) ++n;
    return n;
  }
}
