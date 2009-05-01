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
package org.gwtlib.samples.menu.client.ui;

import org.gwtlib.client.menu.ui.MenuBar;
import org.gwtlib.client.menu.ui.MenuItem;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * An example demonstrating the menu with toggle support.
 * 
 * @author Sander Berents
 */
public class MenuBarEntryPoint implements EntryPoint {
  private static final String ID = "gwtlib-menubar";

  public void onModuleLoad() {
    RootPanel root = RootPanel.get(ID);
    if(root != null) init(root);
  }

  private void init(RootPanel root) {
    MenuItem item;
    Command command = new Command() {
      public void execute() {
        Window.alert("Click");
      }
    };
    MenuBar menu = new MenuBar();
    MenuBar file = new MenuBar(true);
    MenuBar edit = new MenuBar(true);
    MenuBar view = new MenuBar(true);
    menu.addItem("File", file);
    item = (MenuItem)menu.addItem("Edit", edit);
    item.setEnabled(false);
    menu.addItem("View", view);
    item = (MenuItem)menu.addItem("Help", command);
    item.setEnabled(false);
    
    file.addItem("Open", command);
    file.addItem("Close", command);
    file.addItem("Save", command);
    file.addItem("Exit", command);
    edit.addItem("Undo", command);
    edit.addItem("Redo", command);
    edit.addItem("Cut", command);
    edit.addItem("Copy", command);
    edit.addItem("Paste", command);
    view.addItem("Date", true, command, MenuItem.CHECK, false);
    view.addItem("Time", true, command, MenuItem.CHECK, true);
    view.addSeparator();
    view.addItem("Description", command, MenuItem.CHECK, false);
    view.addItem("Quantity", command, MenuItem.CHECK, true);
    view.addSeparator();
    item = (MenuItem)view.addItem("Disabled", (Command)command, MenuItem.INDENT);
    item.setEnabled(false);
    item = (MenuItem)view.addItem("Enabled", (Command)command, MenuItem.INDENT);
    item.setEnabled(false);
    item.setEnabled(true);

    root.add(menu);
  }
}