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
package org.gwtlib.samples.list.client.ui;

import org.gwtlib.client.list.ui.ColorListBox;
import org.gwtlib.client.list.ui.StyleListBox;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * An example demonstrating the styled listbox and color listbox.
 * 
 * @author Sander Berents
 */
public class ListEntryPoint implements EntryPoint {
  private static final String ID = "gwtlib-list";

  public void onModuleLoad() {
    RootPanel root = RootPanel.get(ID);
    if(root != null) init(root);
  }

  private void init(RootPanel root) {
    FlowPanel panel = new FlowPanel();
    StyleListBox styles = new StyleListBox();
    styles.setWidth("80px");
    for(int i = 0; i < 10; ++i) {
      styles.addItem("item " + (i + 1));
      styles.addStyleName(i, i % 2 == 0 ? "gwtlib-StyleListBox-even" : "gwtlib-StyleListBox-odd");
    }
    ColorListBox colors = new ColorListBox();
    colors.setWidth("80px");
    String[] cols = { "red", "green", "blue", "red", "green", "blue" };
    colors.setColors(cols);
    panel.add(styles);
    panel.add(colors);
    root.add(panel);
  }
}