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

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

/**
 * Button renderer.
 * 
 * @author Sander Berents
 */
public class ImageRenderer implements Renderer {
  protected int _left;
  protected int _top;
  protected int _width;
  protected int _height;
  protected String _title;
  
  public ImageRenderer() {
  }

  public ImageRenderer(String title) {
    _title = title;
  }

  public ImageRenderer(int left, int top, int width, int height, String title) {
    _left = left;
    _top = top;
    _width = width;
    _height = height;
    _title = title;
  }

  public Widget render(Row row, Column column, Object value) {
    if(value == null || !(value instanceof String)) {
      return null;
    } else {
      Image image;
      if(_width == 0 || _height == 0) {
        image = new Image((String)value);
      } else {
        image = new Image((String)value, _left, _top, _width, _height);
      }
      if(_title != null) image.setTitle(_title);
      return image;
    }
  }
}
