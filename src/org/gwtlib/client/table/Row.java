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

/**
 * Serializable row.
 *
 * @author Sander Berents
 */
public class Row implements Serializable {
  public interface State {
    public static final int NONE   = 0;
    public static final int SELECT = 1 << 0;
  };

  protected int _id;
//  protected Row[] _children;
  protected Object[] _values;
  protected int _state;

  public Row(int id, Object[] values) {
    this(id, values, State.NONE);
  }

  public Row(int id, Object[] values, int state) {
    _id = id;
    _values = values;
  }

  public int getId() {
    return _id;
  }

  public int getState() {
    return _state;
  }
  
  public boolean hasState(int state) {
    return (_state & state) != 0;
  }

  public void setState(int state) {
    _state = state;
  }

  public Object getValue(int column) {
    return _values[column];
  }
  
  public void setValue(int column, Object value) {
    _values[column] = value;
  }
}
