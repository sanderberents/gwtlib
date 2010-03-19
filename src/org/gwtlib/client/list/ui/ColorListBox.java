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
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Widget;

/**
 * A Color ListBox.
 *
 * @author Sander Berents
 */
public class ColorListBox extends StyleListBox {
  ChangeListener _listener = new ChangeListener() {
    public void onChange(Widget sender) {
      int sel = getSelectedIndex();
      if(sel == -1) {
        DOM.removeElementAttribute(getElement(), "style");
      } else {
        Element child = DOM.getChild(getElement(), sel);
        String style = DOM.getElementAttribute(child, "style");
        if(style != null) DOM.setElementAttribute(getElement(), "style", style);
      }
    }
  };

  public ColorListBox() {
    this(false);
  }

  public ColorListBox(boolean isMultipleSelect) {
    super(isMultipleSelect);
    addChangeListener(_listener);
  }
  
  public void setColors(String[] colors) {
    clear();
    for(int i = 0; i < colors.length; ++i) {
      addItem("", String.valueOf(i));
      Element child = DOM.getChild(getElement(), i);
//      DOM.setStyleAttribute(child, "background-color", colors[i]);
      DOM.setElementAttribute(child, "style", "background-color:" + colors[i]);
    }
    int sel = getSelectedIndex();
    if(sel != -1) DOM.setElementAttribute(getElement(), "style", "background-color:" + colors[sel]);
  }
  
  public void setItemSelected(int index, boolean selected) {
    super.setItemSelected(index, selected);
    _listener.onChange(this);
  }

  public void setSelectedIndex(int index) {
    super.setSelectedIndex(index);
    _listener.onChange(this);
  }
}
