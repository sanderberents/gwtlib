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
 * MenuItem with toggle, enable and disable support.
 * 
 * @author Sander Berents
 */
public class MenuItem extends com.google.gwt.user.client.ui.MenuItem {
	
  /** Menu item type */
  public enum Type { NORMAL, INDENT, CHECK, RADIO };  

  private static final String CHECK_TRUE  = checkmark(); 
  private static final String CHECK_FALSE = "\u00a0\u00a0\u00a0"; // Non-breaking space
  private static final String CHECK_FALSE_HTML = "&nbsp;&nbsp;&nbsp;"; // Non-breaking space
  private static final String RADIO_TRUE  = radio(); 

  private Type _type;
  private boolean _check;
  private boolean _asHTML;
  private Command _disabledCommand;
  private int _group; // Used for radio items only
  
  private static final Command DUMMY_COMMAND = new Command() {
    public void execute() {
      // Do nothing
    }
  }; 
  
  public class ToggleCommand implements Command {
    private Command _command;

    ToggleCommand(Command cmd) {
      _command = cmd;
    }

    public void execute() {
      setChecked(!_check);
      if(_command != null) _command.execute();
    }
  }

  public MenuItem(String text, boolean asHTML, Command cmd) {
    super(text, asHTML, cmd);
    _asHTML = asHTML;
    if(asHTML) setHTML(text); else setText(text);
  }

  public MenuItem(String text, boolean asHTML, Command cmd, Type type, boolean check) {
    super(text, asHTML, cmd);
    _asHTML = asHTML;
    _type = type;
    _check = check;
    if(asHTML) setHTML(text); else setText(text);
  }

  public MenuItem(String text, boolean asHTML, Command cmd, Type type, boolean check, int group) {
    this(text, asHTML, cmd, type, check);
    _group = group;
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

  public MenuItem(String text, Command cmd, Type type, boolean check) {
    super(text, cmd);
    _type = type;
    _check = check;
    setText(text);
  }

  public MenuItem(String text, Command cmd, Type type, boolean check, int group) {
    this(text, cmd, type, check);
    _group = group;
  }

  public MenuItem(String text, MenuBar subMenu) {
    super(text, subMenu);
    setText(text);
  }

  public void setEnabled(boolean enabled) {
    Command cmd = getCommand();
    if(enabled) {
      setStyleName("gwt-MenuItem");
      if((cmd instanceof ToggleCommand && ((ToggleCommand)cmd)._command == DUMMY_COMMAND)) {
        setCommand(_disabledCommand);
      }
    } else {
      setStyleName("gwt-MenuItemDisabled"); // No using dependent style name for proper mouseover style
      if((cmd instanceof ToggleCommand && ((ToggleCommand)cmd)._command != DUMMY_COMMAND) || !(cmd instanceof ToggleCommand)) {
        _disabledCommand = getCommand();
        setCommand(DUMMY_COMMAND);
      }
    }
    // Enable/disable children, if any
    MenuBar submenu = (MenuBar)getSubMenu();
    if(submenu != null) {
      for(int i = 0; i < submenu.getItems().size(); ++i) {
        ((MenuItem)submenu.getItems().get(i)).setEnabled(enabled);
      }
    }
  }
  
  public void setChecked(boolean checked) {
    if(_check != checked ) {
      if(_type == Type.CHECK) {
        String s = _asHTML ? getHTML() : getText();
        _check = !_check;
        if(_asHTML) setHTML(s); else setText(s);
      } else if(_type == Type.RADIO && !_check) {
        MenuBar parentMenu = (MenuBar)getParentMenu();
        for(com.google.gwt.user.client.ui.MenuItem gitem : parentMenu.getItems()) {
          if(gitem instanceof MenuItem) {
            MenuItem item = (MenuItem)gitem;
            if(item._type == Type.RADIO && item._group == _group && item._check) {
              String s = item._asHTML ? item.getHTML() : item.getText();
              item._check = false;
              if(item._asHTML) item.setHTML(s); else item.setText(s);
            }
          }
        }
        String s = _asHTML ? getHTML() : getText();
        _check = !_check;
        if(_asHTML) setHTML(s); else setText(s);
      }
    }
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

  private static String addLead(String s, boolean asHTML, Type type, boolean check) {
    if(type == Type.NORMAL) {
      return s;
    } else if(type == Type.CHECK && check) {
      return CHECK_TRUE + s;
    } else if(type == Type.RADIO && check) {
      return RADIO_TRUE + s;
    } else if(asHTML) {
      return CHECK_FALSE_HTML + s;
    } else {
      return CHECK_FALSE + s;
    }
  }

  private String removeLead(String s, boolean asHTML) {
    return removeLead(s, asHTML, _type, _check);
  }

  private static String removeLead(String s, boolean asHTML, Type type, boolean check) {
    if(type == Type.NORMAL) {
      return s;
    } else if(type == Type.CHECK && check) {
      return s.substring(CHECK_TRUE.length());
    } else if(type == Type.RADIO && check) {
      return s.substring(RADIO_TRUE.length());
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
  
  public Type isType() {
    return _type;
  }
  
  private static native String getUserAgent() /*-{
    return navigator.userAgent.toLowerCase();
  }-*/;
  
  protected static String checkmark() {
    if(getUserAgent().contains("msie 6")) {
      return "\u221a "; // IE6 doesn't support real checkmark so use square root symbol
    } else if(getUserAgent().contains("msie 7")) {
      return "\u221a "; // IE7 acting up too
    } else if(getUserAgent().contains("msie")) {
      return "\u2713";  // Real Unicode checkmark
    } else {
      return "\u2713 "; // Real Unicode checkmark and extra spacing
    }
  }

  protected static String radio() {
    return "\u25cf ";
  }
}
