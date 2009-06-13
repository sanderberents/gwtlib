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

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A widget used to show GWTLib examples.
 * 
 * @author Sander Berents
 */
public abstract class ContentWidget extends Composite {
  private static final String STYLE = "gwtlib-showcase-ContentWidget";

  private boolean _initialized;
  private VerticalPanel _panel;

  public ContentWidget() {
    _panel = new VerticalPanel();
    initWidget(_panel);
    setStyleName(STYLE);
  }

  public abstract String getName();

  public abstract String getDescription();

  public abstract Widget onInit();

  public boolean hasSource() {
    return true;
  }

  /**
   * Initialize this widget by creating the elements that should be added to the
   * page.
   */
  public void init() {
    if(!_initialized) {
      _initialized = true;
      VerticalPanel vPanel = new VerticalPanel();

      HTML nameWidget = new HTML(getName());
      nameWidget.setStyleName(STYLE + "-name");
      vPanel.add(nameWidget);
      HTML descWidget = new HTML(getDescription());
      descWidget.setStyleName(STYLE + "-description");
      vPanel.add(descWidget);
      Widget widget = onInit();
      if(widget != null) vPanel.add(widget);

      _panel.add(vPanel);
    }
  }
}
