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

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * TextBox renderer.
 * 
 * @author Sander Berents
 */
public class TextBoxRenderer implements Renderer {
  protected int _maxLength;
  protected int _visibleLength;
  protected String _title;
  
  public TextBoxRenderer() {
  }

  public TextBoxRenderer(String title) {
    _title = title;
  }

  public TextBoxRenderer(int maxLength, int visibleLength, String title) {
    _maxLength = maxLength;
    _visibleLength = visibleLength;
    _title = title;
  }

  public Widget render(Row row, Column column, Object value) {
    if(value == null || !(value instanceof String)) {
      return null;
    } else {
      TextBox textbox = new TextBox();
      textbox.setText((String)value);
      if(_maxLength > 0) textbox.setMaxLength(_maxLength);
      if(_visibleLength > 0) textbox.setVisibleLength(_visibleLength);
      if(_title != null) textbox.setTitle(_title);
      return textbox;
    }
  }
}
