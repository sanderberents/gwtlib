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

import com.google.gwt.user.client.Command;

/**
 * MenuItem with toggle support.
 * 
 * @author Sander Berents
 */
public class MenuItem extends com.google.gwt.user.client.ui.MenuItem {
  public static final int NORMAL = 0;
  public static final int CHECK  = 1;
// private static final int RADIO  = 2;

  private static final String CHECK_TRUE  = checkmark(); 
  private static final String CHECK_FALSE = "\u00a0\u00a0\u00a0"; // Non-breaking space
  private static final String CHECK_FALSE_HTML = "&nbsp;&nbsp;&nbsp;"; // Non-breaking space

  private int _type;
  private boolean _check;
  private boolean _asHTML;
  
  public class ToggleCommand implements Command {
    private Command _command;

    ToggleCommand(Command cmd) {
      _command = cmd;
    }

    public void execute() {
      if(_type == CHECK) {
        String s = _asHTML ? getHTML() : getText();
        _check = !_check;
        if(_asHTML) setHTML(s); else setText(s);
      }
      _command.execute();
    }
  };

  public MenuItem(String text, boolean asHTML, Command cmd) {
    super(text, asHTML, cmd);
    _asHTML = asHTML;
    if(asHTML) setHTML(text); else setText(text);
  }

  public MenuItem(String text, boolean asHTML, Command cmd, int type, boolean check) {
    super(text, asHTML, cmd);
    _asHTML = asHTML;
    _type = type;
    _check = check;
    if(asHTML) setHTML(text); else setText(text);
  }

  public MenuItem(String text, boolean asHTML, MenuBar subMenu) {
    super(text, asHTML, subMenu);
    _asHTML = asHTML;
    if(asHTML) setHTML(text); else setText(text);
  }

  public MenuItem(String text, Command cmd) {
    super(text, cmd);
    setText(text);
  }

  public MenuItem(String text, Command cmd, int type, boolean check) {
    super(text, cmd);
    _type = type;
    _check = check;
    setText(text);
  }

  public MenuItem(String text, MenuBar subMenu) {
    super(text, subMenu);
    setText(text);
  }
  
  public String getHTML() {
    return removeLead(super.getHTML(), true);    
  }

  public String getText() {
    return removeLead(super.getText(), false);    
  }

  public void setHTML(String html) {
    super.setHTML(getSubMenu() == null ? addLead(html, true) : html);
  }

  public void setText(String text) {
    super.setText(getSubMenu() == null ? addLead(text, false) : text);
  }

  private String addLead(String s, boolean asHTML) {
    return addLead(s, asHTML, _type, _check);
  }

  private static String addLead(String s, boolean asHTML, int type, boolean check) {
    if(type == CHECK && check) {
      return CHECK_TRUE + s;
    } else if(asHTML) {
      return CHECK_FALSE_HTML + s;
    } else {
      return CHECK_FALSE + s;
    }
  }

  private String removeLead(String s, boolean asHTML) {
    return removeLead(s, asHTML, _type, _check);
  }

  private static String removeLead(String s, boolean asHTML, int type, boolean check) {
    if(type == CHECK && check) {
      return s.substring(CHECK_TRUE.length());
    } else if(asHTML) {
      return s.substring(CHECK_FALSE_HTML.length());
    } else {
      return s.substring(CHECK_FALSE.length());
    }
  }
  
  public Command getCommand() {
    return super.getCommand();
  }

  public void setCommand(Command cmd) {
    super.setCommand(new ToggleCommand(cmd));
  }
  
  public boolean isChecked() {
    return _check;
  }
  
  private static native String getUserAgent() /*-{
    return navigator.userAgent.toLowerCase();
  }-*/;
  
  private static String checkmark() {
    if(getUserAgent().contains("msie 6")) {
      return "\u221a "; // IE6 doesn't support real checkmark so use square root symbol
    } else if(getUserAgent().contains("msie")) {
      return "\u2713";  // Real Unicode checkmark
    } else {
      return "\u2713 "; // Real Unicode checkmark and extra spacing
    }
  }
}
