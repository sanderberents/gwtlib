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
package org.gwtlib.client.table.ui.renderer;

import org.gwtlib.client.table.Row;
import org.gwtlib.client.table.ui.Column;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * String renderer. This is the default renderer.
 * 
 * @author Sander Berents
 */
public class StringRenderer implements Renderer {
  protected boolean _wordWrap;
  protected boolean _asHTML;

  public StringRenderer() {
    this(true);
  }

  public StringRenderer(boolean wordWrap) {
    this(wordWrap, false);
  }

  public StringRenderer(boolean wordWrap, boolean asHTML) {
    _wordWrap = wordWrap;
    _asHTML = asHTML;
  }

  public Widget render(Row row, Column column, Object value) {
    if(value == null) {
      return null;
    } else {
      String text = value.toString();
      return _asHTML ? new HTML(text, _wordWrap) : new Label(text, _wordWrap);
    }
  }
}
