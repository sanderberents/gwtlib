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

import java.util.Date;

import org.gwtlib.client.table.Row;
import org.gwtlib.client.table.ui.Column;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Date/time renderer.
 * 
 * @author Sander Berents
 */
public class DateTimeRenderer implements Renderer {
  protected DateTimeFormat _format;

  public DateTimeRenderer(DateTimeFormat format) {
    _format = format;
  }

  public Widget render(Row row, Column column, Object value) {
    String text = _format.format((Date)value);
    return new Label(text);
  }
}