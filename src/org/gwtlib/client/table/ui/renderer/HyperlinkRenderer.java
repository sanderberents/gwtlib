/*
 * Copyright 2009 Sander Berents
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
package org.gwtlib.client.table.ui.renderer;

import org.gwtlib.client.table.Row;
import org.gwtlib.client.table.ui.Column;

import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

/**
 * Hyperlink renderer.
 * 
 * @author Sander Berents
 */
public class HyperlinkRenderer implements Renderer {
  protected boolean _asHTML;
  protected String _targetHistoryToken; 
  protected String _title;
  
  public HyperlinkRenderer() {
  }

  public HyperlinkRenderer(String title) {
    _title = title;
  }

  public HyperlinkRenderer(String targetHistoryToken, String title) {
    _targetHistoryToken = targetHistoryToken;
    _title = title;
  }

  public HyperlinkRenderer(boolean asHTML, String targetHistoryToken, String title) {
    _asHTML = asHTML;
    _targetHistoryToken = targetHistoryToken;
    _title = title;
  }

  public Widget render(Row row, Column column, Object value) {
    if(value == null || !(value instanceof String)) {
      return null;
    } else {
      Hyperlink hyperlink = new Hyperlink((String)value, _asHTML, _targetHistoryToken);
      if(_title != null) hyperlink.setTitle(_title);
      return hyperlink;
    }
  }
}
