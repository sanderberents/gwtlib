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

import org.gwtlib.client.table.ContentProvider;
import org.gwtlib.client.table.Rows;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Abstract provider base class.
 *
 * @author Sander Berents
 */
public abstract class AbstractContentProvider implements ContentProvider {
  protected Table _table;
  protected AsyncCallback _callback;
  
  public AbstractContentProvider(Table table) {
    _table = table;
    _callback = new AsyncCallback() {
      public void onSuccess(Object result) {
        Rows rows = (Rows)result;
        _table.onSuccess(rows);
      }

      public void onFailure(Throwable caught) {
        _table.onFailure(caught);
      }
    };
  }
  
  public AbstractContentProvider(Table table, AsyncCallback callback) {
    _table = table;
    _callback = callback;
  }

  public abstract void load(int begin, int end, int sortId, boolean ascending);
}