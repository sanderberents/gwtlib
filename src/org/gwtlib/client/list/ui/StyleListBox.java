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
package org.gwtlib.client.list.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ListBox;

/**
 * A ListBox with styles attached to each item.
 *
 * @author Sander Berents
 */
public class StyleListBox extends ListBox {
  public StyleListBox() {
  }

  public StyleListBox(boolean isMultipleSelect) {
    super(isMultipleSelect);
  }

  /**
   * Clears all of the item's style names and sets it to the given style. 
   * @param index
   * @param style
   */
  public void setStyleName(int index, String style) {
    Element child = DOM.getChild(getElement(), index);
    setStyleName(child, style);
  }

  /**
   * Adds a secondary or dependent style name to an item.
   * @param index
   * @param style
   */
  public void addStyleName(int index, String style) {
    Element child = DOM.getChild(getElement(), index);
    setStyleName(child, style, true);
  }

  /**
   * Removes a style name from an item.
   * @param index
   * @param style
   */
  public void removeStyleName(int index, String style) {
    Element child = DOM.getChild(getElement(), index);
    setStyleName(child, style, false);
  }
}
