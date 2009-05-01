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
package org.gwtlib.client.menu.ui;

import java.util.List;

import com.google.gwt.user.client.Command;

/**
 * MenuBar with toggle menu item support.
 * 
 * @author Sander Berents
 */
public class MenuBar extends com.google.gwt.user.client.ui.MenuBar {
  public MenuBar() {
  }

  public MenuBar(boolean vertical) {
    super(vertical);
  }

  public com.google.gwt.user.client.ui.MenuItem addItem(java.lang.String text, boolean asHTML, Command cmd) {
    MenuItem item = new MenuItem(text, asHTML, cmd);
    addItem(item);
    return item;
  }

  public com.google.gwt.user.client.ui.MenuItem addItem(java.lang.String text, boolean asHTML, Command cmd, int type) {
    MenuItem item = new MenuItem(text, asHTML, cmd, type, false);
    addItem(item);
    return item;
  }

  public com.google.gwt.user.client.ui.MenuItem addItem(java.lang.String text, boolean asHTML, Command cmd, int type, boolean check) {
    MenuItem item = new MenuItem(text, asHTML, cmd, type, check);
    addItem(item);
    return item;
  }

  public com.google.gwt.user.client.ui.MenuItem addItem(java.lang.String text, boolean asHTML, MenuBar popup) {
    MenuItem item = new MenuItem(text, asHTML, popup);
    addItem(item);
    return item;
  }

  public com.google.gwt.user.client.ui.MenuItem addItem(java.lang.String text, Command cmd) {
    MenuItem item = new MenuItem(text, cmd);
    addItem(item);
    return item;
  }

  public com.google.gwt.user.client.ui.MenuItem addItem(java.lang.String text, Command cmd, int type) {
    MenuItem item = new MenuItem(text, cmd, type, false);
    addItem(item);
    return item;
  }

  public com.google.gwt.user.client.ui.MenuItem addItem(java.lang.String text, Command cmd, int type, boolean check) {
    MenuItem item = new MenuItem(text, cmd, type, check);
    addItem(item);
    return item;
  }

  public com.google.gwt.user.client.ui.MenuItem addItem(java.lang.String text, MenuBar popup) {
    MenuItem item = new MenuItem(text, popup);
    addItem(item);
    return item;
  }
  
  public List<com.google.gwt.user.client.ui.MenuItem> getItems() {
    return super.getItems();
  }
}


