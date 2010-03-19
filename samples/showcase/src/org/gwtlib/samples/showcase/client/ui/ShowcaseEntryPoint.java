/*
 * Copyright 2008-2009 Sander Berents
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
package org.gwtlib.samples.showcase.client.ui;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

/**
 * Showcase.
 * 
 * @author Sander Berents
 */
public class ShowcaseEntryPoint implements EntryPoint {
  private static final String ID = "gwtlib-showcase";
  
  private Tree _tree;
  private DeckPanel _deckPanel;

  public void onModuleLoad() {
    RootPanel root = RootPanel.get(ID);
    if(root != null) init(root);

    // Show initial content widget
    _tree.getItem(2).setState(true, true);
    _tree.setSelectedItem(_tree.getItem(2).getChild(0), true);
  }

  private void init(RootPanel root) {
    _deckPanel = new DeckPanel();
    _tree = createTree();
    _tree.addSelectionHandler(new SelectionHandler<TreeItem>() {
      public void onSelection(SelectionEvent<TreeItem> event) {
        TreeItem item = event.getSelectedItem();
        if(item.getUserObject() != null) {
          ContentWidget widget = (ContentWidget)item.getUserObject();
          widget.init();
          int i = _deckPanel.getWidgetIndex(widget);
          if(i != -1) _deckPanel.showWidget(i);
        }
      }
    });

    FlexTable table = new FlexTable();
    table.setWidget(0, 0, _tree);
    table.setWidget(0, 1, _deckPanel);
    table.getRowFormatter().setVerticalAlign(0, HasVerticalAlignment.ALIGN_TOP);

    root.add(table);
  }
  
  private Tree createTree() {
    Tree tree = new Tree();
    /*TreeItem widgets =*/ tree.addItem("Widgets");
    /*TreeItem lists =*/ tree.addItem("Lists and Menus");
    TreeItem popups = tree.addItem("Popups");
    popups.addItem(createTreeItem("Message Box", new CwMessageBox()));
    /*TreeItem tables =*/ tree.addItem("Tables");
    
    return tree;
  }

  private TreeItem createTreeItem(String itemText, ContentWidget widget) {
    TreeItem item = new TreeItem(itemText);
    item.setUserObject(widget);
    _deckPanel.add(widget);
    return item;
  }
}