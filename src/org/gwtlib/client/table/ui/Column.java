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
package org.gwtlib.client.table.ui;

import org.gwtlib.client.table.ui.renderer.Renderer;
import org.gwtlib.client.table.ui.renderer.StringRenderer;

import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Column.
 * 
 * CSS Style Rules:
 * <ul>
 * <li>.gwtlib-Column { the column header without sorting }</li>
 * <li>.gwtlib-Column .gwtlib-Column-sortable { column is sortable }</li>
 * </ul>
 *
 * @author Sander Berents
 */
public class Column extends AbstractComposite {
  private static final String STYLE           = "gwtlib-Column";
  private static final String STYLE_SORTABLE  = "sortable";

  public interface State {
    public static final int NONE   = 0;
    public static final int SELECT = 1 << 0;
  };

  public interface Sort {
    public static final int NONE       = 0;
    public static final int ASCENDING  = 1;
    public static final int DESCENDING = 2;
  };

  protected int _id;
  protected int _dir = Sort.NONE;
  protected Renderer _renderer;
  protected boolean _sortable;
  protected int _state;
  
  public Column(int id, boolean sortable, String label, String width) {
    this(id, sortable, label, false, width, new StringRenderer());
  }

  public Column(int id, boolean sortable, String label, String width, Renderer renderer) {
    this(id, sortable, label, false, width, renderer);
  }

  public Column(int id, boolean sortable, String label, boolean asHTML, String width) {
    this(id, sortable, label, asHTML, width, new StringRenderer());
  }

  public Column(int id, boolean sortable, String label, boolean asHTML, String width, Renderer renderer) {
    this(id, sortable, asHTML ? new HTML(label) : new Label(label), width, renderer);
  }

  public Column(int id, boolean sortable, Widget label, String width) {
    this(id, sortable, label, width, new StringRenderer());
  }

  public Column(int id, boolean sortable, Widget label, String width, Renderer renderer) {
    _id = id;
    _sortable = sortable;
    _renderer = renderer;
    FocusPanel panel = new FocusPanel(label);
    panel.setWidth(width);
    initWidget(panel);
    setStylePrimaryName(STYLE);
    if(sortable) addStyleDependentName(STYLE_SORTABLE);
  }
  
  public int getId() {
    return _id;
  }

  public int getState() {
    return _state;
  }
  
  public boolean hasState(int state) {
    return (_state & state) != 0;
  }

  public void setState(int state) {
    _state = state;
  }

  public boolean isSortable() {
    return _sortable;
  }

  public void setSortDirection(int dir) {
    _dir = dir;
  }

  public int getSortDirection() {
    return _dir;
  }

  public Renderer getRenderer() {
    return _renderer;
  }
}
