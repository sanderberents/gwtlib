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

import java.util.ArrayList;

/**
 * Table rows cache.
 * 
 * @author Sander Berents
 */
public class RowsCache {
  private ArrayList _cache = new ArrayList(); // List of Rows objects (Rows are merged if possible)

  /**
   * Store rows with the given starting position in the cache, merging 
   * Rows objects if possible. If the sort order of the new rows
   * differs from those of the cached rows, the cache is erased and 
   * replaced with the new rows.
   * @param rows
   */
  public void merge(Rows rows) {
    if(equalsSort(rows.getSortId(), rows.isAscending())) {
      // First just add it to the cache
      _cache.add(rows);
      // Now merge it with existing Rows. This ensures that initial gaps are filled as well.
      for(int i = 0; i < _cache.size(); ++i) {
        Rows r1 = (Rows)_cache.get(i);
        for(int j = i + 1; j < _cache.size(); ++j) {
          Rows r2 = (Rows)_cache.get(j);
          if(r2 != null && r1.merge(r2)) _cache.set(j, null);   
        }
      }
      // Remove cleared Rows
      while(_cache.remove(null));
    } else {
      _cache.clear();
      _cache.add(rows);
    }
  }

  /**
   * Retrieves a continuous set of rows from the cache.
   * @param pos Offset of first row.
   * @param end Offset of last row minus 1.
   * @param sortId
   * @param ascending
   * @return The requested rows, or less if the cache did not contain 
   *   more continuous rows.
   */
  public Rows getRows(int begin, int end, int sortId, boolean ascending) {
    if(!equalsSort(sortId, ascending)) return new Rows();
    for(int i = 0; i < _cache.size(); i++) {
      Rows b = (Rows)_cache.get(i);
      if(b.inside(begin)) {
        ArrayList tmp = new ArrayList();
        Row[] rows = b.getRows();
        for(int j = begin - b.getBegin(); j < end - b.getBegin() && j < rows.length; ++j) tmp.add(rows[j]);
        return new Rows(tmp, begin, b.getSortId(), b.isAscending());
      }
    }
    return new Rows();
  }
  
  /**
   * Returns the row at the given position, if any.
   * @param pos
   * @return
   */
  public Row getRow(int pos) {
    for(int i = 0; i < _cache.size(); i++) {
      Rows rows = (Rows)_cache.get(i);
      if(rows.inside(pos)) return (Row)rows.getRow(pos - rows.getBegin());
    }
    return null;
  }

  /**
   * Returns the row with the given id, if any.
   * @param id
   * @return
   */
  Row findRow(int id) {
    for(int i = 0; i < _cache.size(); i++) {
      Rows rows = (Rows)_cache.get(i);
      Row row = rows.find(id);
      if(row != null) return row;
    }
    return null;
  }
  
  /**
   * Empties the row cache.
   */
  public void clear() {
    _cache.clear();
  }

  /**
   * Returns true if the given sort order equals to sort order of the cached rows.
   * @param sort
   * @return
   */
  private boolean equalsSort(int sortId, boolean ascending) {
    if(_cache.isEmpty()) return false;
    Rows rows = (Rows)_cache.get(0);
    return (sortId == -1 && rows.getSortId() == -1) || 
           (sortId >= 0 && sortId == rows.getSortId() && ascending == rows.isAscending());
  }
}
