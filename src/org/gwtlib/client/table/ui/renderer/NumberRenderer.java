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

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Number renderer.
 * 
 * @author Sander Berents
 */
public class NumberRenderer implements Renderer {
  protected NumberFormat _format;

  public NumberRenderer(NumberFormat format) {
    _format = format;
  }

  public Widget render(Row row, Column column, Object value) {
    if(value instanceof Double) {
      String text = _format.format(((Double)value).doubleValue());
      return new Label(text);
    } else if(value instanceof Float) {
      String text = _format.format(((Float)value).floatValue());
      return new Label(text);
    } else if(value instanceof Long) {
      String text = _format.format(((Long)value).longValue());
      return new Label(text);
    } else if(value instanceof Integer) {
      String text = _format.format(((Integer)value).intValue());
      return new Label(text);
    }
    return null;
  }
}
