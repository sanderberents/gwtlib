/*
 * Copyright 2009 Sander Berents
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
package org.gwtlib.client.ui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Different message boxes.
 * 
 * CSS Style Rules:
 * <ul>
 * <li>.gwtlib-MessageBox { the message box itself }</li>
 * </ul>
 * 
 * @author Sander Berents
 */
public class MessageBox extends DialogBox {
  private enum Type {
    ALERT, CONFIRM, PROMPT, MULTI_PROMPT, WAIT, PROGRESSS
  }

  public enum ButtonType {
    OK, CANCEL, YES, NO
  }

  private static final String STYLE                = "gwtlib-MessageBox";
  private static final String STYLE_INFO_IMAGE     = "gwtlib-MessageBox-InfoImage";
  private static final String STYLE_ALERT_IMAGE    = "gwtlib-MessageBox-AlertImage";
  private static final String STYLE_ERROR_IMAGE    = "gwtlib-MessageBox-ErrorImage";
  private static final String STYLE_QUESTION_IMAGE = "gwtlib-MessageBox-QuestionImage";

  public static final int BTN_OK            = 1;
  public static final int BTN_CANCEL        = 2;
  public static final int BTN_YES           = 4;
  public static final int BTN_NO            = 8;
  public static final int BTN_OK_CANCEL     = BTN_OK | BTN_CANCEL;
  public static final int BTN_YES_NO        = BTN_YES | BTN_NO;
  public static final int BTN_YES_NO_CANCEL = BTN_YES_NO | BTN_CANCEL;
  
  private DockPanel _dockPanel;
  private HorizontalPanel _buttonPanel;
  private Type _type;
  private String _imageStyleName;
  private String _title;
  private String _message;
  private String _progressText;
  private int _buttons;
  private double _progress;
  private TextBox _textBox;
  private TextArea _textArea;
  
  public MessageBox() {
    super(false, true);
    _dockPanel = new DockPanel();
    _buttonPanel = new HorizontalPanel();
    _buttonPanel.setSpacing(5);
    _dockPanel.add(_buttonPanel, DockPanel.SOUTH);
    _dockPanel.setCellHorizontalAlignment(_buttonPanel,  HasHorizontalAlignment.ALIGN_CENTER);
    setWidget(_dockPanel);
    //setStyleName(STYLE);
  }

  /**
   * Creates an information message box with the given title, message and an OK button.
   * @param caption
   * @param message
   * @param listener
   * @return
   */
  public static MessageBox info(String caption, String message, final MessageBoxListener listener) {
    return info(caption, message, BTN_OK, listener);
  }

  /**
   * Creates an information message box with the given title, message and buttons.
   * @param caption
   * @param message
   * @param buttons
   * @param listener
   * @return
   */
  public static MessageBox info(String caption, String message, int buttons, final MessageBoxListener listener) {
    return info(caption, message, false, buttons, listener);
  }

  /**
   * Creates an information message box with the given title, message and buttons.
   * @param caption
   * @param message
   * @param asHTML
   * @param buttons
   * @param listener
   * @return
   */
  public static MessageBox info(String caption, String message, boolean asHTML, int buttons, final MessageBoxListener listener) {
    return message(caption, message, asHTML, buttons, listener, STYLE_INFO_IMAGE);
  }

  /**
   * Creates an alert message box with the given title, message and an OK button.
   * @param caption
   * @param message
   * @param listener
   * @return
   */
  public static MessageBox alert(String caption, String message, MessageBoxListener listener) {
    return alert(caption, message, BTN_OK, listener);
  }
  
  /**
   * Creates an alert message box with the given title, message and buttons.
   * @param caption
   * @param message
   * @param buttons
   * @param listener
   * @return
   */
  public static MessageBox alert(String caption, String message, int buttons, MessageBoxListener listener) {
    return alert(caption, message, false, buttons, listener);
  }
  
  /**
   * Creates an alert message box with the given title, message and buttons.
   * @param caption
   * @param message
   * @param asHTML
   * @param buttons
   * @param listener
   * @return
   */
  public static MessageBox alert(String caption, String message, boolean asHTML, int buttons, MessageBoxListener listener) {
    return message(caption, message, asHTML, buttons, listener, STYLE_ALERT_IMAGE);
  }
  
  /**
   * Creates an error message box with the given title, message and an OK button.
   * @param caption
   * @param message
   * @param listener
   * @return
   */
  public static MessageBox error(String caption, String message, MessageBoxListener listener) {
    return error(caption, message, BTN_OK, listener);
  }
  
  /**
   * Creates an error message box with the given title, message and buttons.
   * @param caption
   * @param message
   * @param buttons
   * @param listener
   * @return
   */
  public static MessageBox error(String caption, String message, int buttons, MessageBoxListener listener) {
    return error(caption, message, false, buttons, listener);
  }
  
  /**
   * Creates an error message box with the given title, message and buttons.
   * @param caption
   * @param message
   * @param asHTML
   * @param buttons
   * @param listener
   * @return
   */
  public static MessageBox error(String caption, String message, boolean asHTML, int buttons, MessageBoxListener listener) {
    return message(caption, message, asHTML, buttons, listener, STYLE_ERROR_IMAGE);
  }
  
  /**
   * Creates an error message box with the given title, message and YES/NO buttons.
   * @param caption
   * @param message
   * @param listener
   * @return
   */
  public static MessageBox confirm(String caption, String message, MessageBoxListener listener) {
    return confirm(caption, message, BTN_YES_NO, listener);
  }

  /**
   * Creates an error message box with the given title, message and buttons.
   * @param caption
   * @param message
   * @param buttons
   * @param listener
   * @return
   */
  public static MessageBox confirm(String caption, String message, int buttons, MessageBoxListener listener) {
    return confirm(caption, message, false, buttons, listener);
  }

  /**
   * Creates an error message box with the given title, message and buttons.
   * @param caption
   * @param message
   * @param asHTML
   * @param buttons
   * @param listener
   * @return
   */
  public static MessageBox confirm(String caption, String message, boolean asHTML, int buttons, MessageBoxListener listener) {
    return message(caption, message, asHTML, buttons, listener, STYLE_QUESTION_IMAGE);
  }

  public static MessageBox prompt(String caption, String message, MessageBoxListener listener) {
    return prompt(caption, message, false, BTN_OK_CANCEL, listener);
  }

  public static MessageBox prompt(String caption, String message, boolean multiline, 
                                  MessageBoxListener listener) {
    return prompt(caption, message, multiline, BTN_OK_CANCEL, listener);
  }

  public static MessageBox prompt(String caption, String message, boolean multiline, 
                                  int buttons, MessageBoxListener listener) {
    return prompt(caption, message, false, multiline, buttons, listener);
  }

  public static MessageBox prompt(String caption, String message, boolean asHTML, 
                                  boolean multiline, int buttons, MessageBoxListener listener) {
    final MessageBox mb = new MessageBox();
    mb.setText(caption);
    mb.setButtons(buttons, listener);
    mb._dockPanel.add(asHTML ? new HTML(message) : new Label(message), DockPanel.NORTH);
    if(multiline) {
      mb._textArea = new TextArea();
      mb._dockPanel.add(mb._textArea, DockPanel.CENTER);
      mb.center();
      mb._textArea.setFocus(true);
    } else {
      mb._textBox = new TextBox();
      mb._dockPanel.add(mb._textBox, DockPanel.CENTER);
      mb.center();
      mb._textBox.setFocus(true);
    }
    return mb;
  }

  public static MessageBox prompt(String caption, String message, Widget widget, 
                                  MessageBoxListener listener) {
    return prompt(caption, message, widget, BTN_OK_CANCEL, listener);
  }

  public static MessageBox prompt(String caption, String message, Widget widget, 
                                  int buttons, MessageBoxListener listener) {
    return prompt(caption, message, false, widget, buttons, listener);
  }

  public static MessageBox prompt(String caption, String message, boolean asHTML, 
                                  Widget widget, int buttons, MessageBoxListener listener) {
    final MessageBox mb = new MessageBox();
    mb.setText(caption);
    mb.setButtons(buttons, listener);
    mb._dockPanel.add(asHTML ? new HTML(message) : new Label(message), DockPanel.NORTH);
    mb._dockPanel.add(widget, DockPanel.CENTER);
    mb.center();
    if(widget instanceof FocusWidget) ((FocusWidget)widget).setFocus(true);
    return mb;
  }

  public static MessageBox password(String caption, String message, MessageBoxListener listener) {
    final MessageBox mb = new MessageBox();
    mb.setText(caption);
    mb.setButtons(BTN_OK_CANCEL, listener);
    mb._dockPanel.add(new Label(message), DockPanel.NORTH);
    mb._textBox = new PasswordTextBox();
    mb._dockPanel.add(mb._textBox, DockPanel.CENTER);
    mb.center();
    mb._textBox.setFocus(true);
    return mb;
  }

  public static MessageBox wait(String caption, String message, java.lang.String progressText) {
    MessageBox mb = new MessageBox();
    return mb;
  }

  public static MessageBox progress(String caption, String message, String progressText)  {
    MessageBox mb = new MessageBox();
    return mb;
  }
  
  private static MessageBox message(String caption, String message, boolean asHTML, int buttons, 
                                    final MessageBoxListener listener, String imageStyleName) {
    final MessageBox mb = new MessageBox();
    mb.setText(caption);
    mb.setButtons(buttons, listener);
    HTML imageHTML = new HTML("");
    imageHTML.setStyleName(imageStyleName);
    mb._dockPanel.add(imageHTML, DockPanel.WEST);
    mb._dockPanel.add(asHTML ? new HTML(message) : new Label(message), DockPanel.CENTER);
    mb.center();
    return mb;
  }

  private void setButtons(int buttons, final MessageBoxListener listener) {
    final MessageBox self = this;
    _buttonPanel.clear();

    if((buttons & BTN_YES) != 0) {
      Button btn = new Button("Yes");
      _buttonPanel.add(btn);
      btn.addClickListener(new ClickListener() {
        public void onClick(Widget sender) {
          hide();
          listener.onMessageBoxClosed(self, ButtonType.YES);
        }
      });
    }
    if((buttons & BTN_NO) != 0) {
      Button btn = new Button("No");
      _buttonPanel.add(btn);
      btn.addClickListener(new ClickListener() {
        public void onClick(Widget sender) {
          hide();
          listener.onMessageBoxClosed(self, ButtonType.NO);
        }
      });
    }
    if((buttons & BTN_OK) != 0) {
      Button btn = new Button("OK");
      _buttonPanel.add(btn);
      btn.addClickListener(new ClickListener() {
        public void onClick(Widget sender) {
          hide();
          listener.onMessageBoxClosed(self, ButtonType.OK);
        }
      });
    }
    if((buttons & BTN_CANCEL) != 0) {
      Button btn = new Button("Cancel");
      _buttonPanel.add(btn);
      btn.addClickListener(new ClickListener() {
        public void onClick(Widget sender) {
          hide();
          listener.onMessageBoxClosed(self, ButtonType.CANCEL);
        }
      });
    }
  }

  public String getTitle() {
    return _title;
  }

  public String getMessage() {
    return _message;    
  }
  
/*
  public String getProgressText() {
    
  }
*/
  public TextBox getTextBox() {
    return _textBox;
  }
  
  public TextArea getTextArea() {
    return _textArea;
  }
  
  public String getText() {
    if(_textBox == null) {
      if(_textArea == null) {
        return null;
      } else {
        return _textArea.getText();
      }
    } else {
      return _textBox.getText();
    }
  }
/*
  public ProgressBar getProgressBar() {
    
  }
  
  void setProgress(double value, String text) {
    
  }
*/
  /*
   * <div class="gwt-HTML"><br>Additional Progress Bars:<br></div><div class="gwt-ProgressBar-shell" style="position: relative;">
   * <div class="gwt-ProgressBar-bar" style="height: 100%; width: 76%;"></div>
   * <div class="gwt-ProgressBar-text gwt-ProgressBar-text-secondHalf" style="position: absolute; top: 0px; left: 177px;">76%</div>
   * </div><div class="gwt-HTML"><br></div><div class="gwt-ProgressBar-shell gwt-ProgressBar-thin" style="position: relative;">
   * <div class="gwt-ProgressBar-bar" style="height: 100%; width: 76%;"></div>
   * <div class="gwt-ProgressBar-text gwt-ProgressBar-text-secondHalf" style="position: absolute; top: 0px; display: none;">76%</div>
   * </div></body></html>
   */
}

