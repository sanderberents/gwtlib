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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A continuous array of Serializable rows.
 * 
 * @author Sander Berents
 */
public class Rows implements Serializable {
  protected int _begin;
  protected int _sortId;
  protected boolean _ascending;
  /** @gwt.typeArgs <org.gwtlib.client.table.Row> */
  protected List _rows;
  
  public Rows() {
    _sortId = -1;
    _rows = new ArrayList();
  }
  
  public Rows(Row[] rows, int begin) {
    this(new ArrayList(Arrays.asList(rows)), begin, -1, false);
  }

  public Rows(Row[] rows, int begin, int sortId, boolean ascending) {
    this(new ArrayList(Arrays.asList(rows)), begin, sortId, ascending);
  }

  Rows(List rows, int begin, int sortId, boolean ascending) {
    _begin = begin;
    _sortId = sortId;
    _ascending = ascending;
    _rows = rows;
  }

  public int getBegin() {
    return _begin;
  }

  public int getEnd() {
    return _begin + _rows.size();
  }
  
  public int size() {
    return _rows.size();
  }

  public int getSortId() {
    return _sortId;
  }
  
  public boolean isAscending() {
    return _ascending;
  }
  
  Row[] getRows() {
    return (Row[])_rows.toArray(new Row[_rows.size()]);
  }

  public Row getRow(int row) {
    return (Row)_rows.get(row);
  }

  /**
   * Tests if a position lies inside this Rows object.
   * @param pos
   * @return
   */
  public boolean inside(int pos) {
    return pos >= _begin && pos < _begin + _rows.size();
  }
  
  /**
   * Merge this Rows object with the given one, if they may be merged. 
   * @param other
   * @return Returns true if rows were merged, or false if not.
   */
  public boolean merge(Rows other) {
    if(_begin < other._begin) {
      int start = getEnd() - other._begin; // Skip overlapping ones
      if(start < 0) return false;
      for(int i = start; i < other._rows.size(); ++i) _rows.add(other._rows.get(i));
    } else {
      int start = other.getEnd() - _begin; // Skip overlapping ones
      if(start < 0) return false;
      List tmp = new ArrayList(other._rows);
      for(int i = start; i < _rows.size(); ++i) tmp.add(_rows.get(i));
      _begin = other._begin;
      _rows = tmp;
    }
    return true;
  }
  
  public Row find(int id) {
    for(int i = 0; i < _rows.size(); ++i) {
      Row row = (Row)_rows.get(i);
      if(id == row.getId()) return row;
    }
    return null;
  }
}
