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

import org.gwtlib.client.ui.MessageBox;
import org.gwtlib.client.ui.MessageBoxListener;
import org.gwtlib.client.ui.MessageBox.ButtonType;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * MessageBox examples.
 * 
 * @author Sander Berents
 */
public class CwMessageBox extends ContentWidget {
  public String getName() {
    return "MessageBox";
  }

  public String getDescription() {
    return "MessageBox can be used to display alerts, errors, confirmations, progress and prompts for simple text entry, passwords or any other widget.";
  }

  public Widget onInit() {
    HorizontalPanel hPanel = new HorizontalPanel();
    hPanel.setSpacing(10);
    
    hPanel.add(new Button("Info", new ClickHandler() {
      public void onClick(ClickEvent event) {
        MessageBox.info("Info", "Permission is allowed", new MessageBoxListener() {
          public void onMessageBoxClosed(MessageBox sender, ButtonType buttonClicked) {
            //log("Closed message box");
          }
        }).show();
      }
    }));
    hPanel.add(new Button("Alert", new ClickHandler() {
      public void onClick(ClickEvent event) {
        MessageBox.alert("Alert", "This document has not been saved", new MessageBoxListener() {
          public void onMessageBoxClosed(MessageBox sender, ButtonType buttonClicked) {
            //log("Closed message box");
          }
        }).show();
      }
    }));
    hPanel.add(new Button("Error", new ClickHandler() {
      public void onClick(ClickEvent event) {
        MessageBox.error("Error", "An error has occurred", new MessageBoxListener() {
          public void onMessageBoxClosed(MessageBox sender, ButtonType buttonClicked) {
            //log("Closed message box");
          }
        }).show();
      }
    }));
    hPanel.add(new Button("Confirm", new ClickHandler() {
      public void onClick(ClickEvent event) {
        MessageBox.confirm("Confirm", "Are you sure you want to do that?", new MessageBoxListener() {
          public void onMessageBoxClosed(MessageBox sender, ButtonType buttonClicked) {
            //log("Closed message box");
          }
        }).show();
      }
    }));
    hPanel.add(new Button("Confirm/Cancel", new ClickHandler() {
      public void onClick(ClickEvent event) {
        MessageBox.confirm("Confirm", "Are you sure you want to do that?", 
                           MessageBox.BTN_YES | MessageBox.BTN_NO | MessageBox.BTN_CANCEL, 
                           new MessageBoxListener() {
          public void onMessageBoxClosed(MessageBox sender, ButtonType buttonClicked) {
            //log("Closed message box");
          }
        }).show();
      }
    }));
    hPanel.add(new Button("Prompt", new ClickHandler() {
      public void onClick(ClickEvent event) {
        MessageBox.prompt("Prompt", "Please enter your name", new MessageBoxListener() {
          public void onMessageBoxClosed(MessageBox sender, ButtonType buttonClicked) {
            //Window.alert("You entered: " + sender.getText());
            //log("Closed message box");
          }
        }).show();
      }
    }));
    hPanel.add(new Button("Multiline Prompt", new ClickHandler() {
      public void onClick(ClickEvent event) {
        MessageBox.prompt("Prompt", "Please enter your name", true, new MessageBoxListener() {
          public void onMessageBoxClosed(MessageBox sender, ButtonType buttonClicked) {
            //Window.alert("You entered: " + sender.getText());
            //log("Closed message box");
          }
        }).show();
      }
    }));
    hPanel.add(new Button("Password", new ClickHandler() {
      public void onClick(ClickEvent event) {
        MessageBox.password("Password", "Please enter your password", new MessageBoxListener() {
          public void onMessageBoxClosed(MessageBox sender, ButtonType buttonClicked) {
            //Window.alert("You entered: " + sender.getText());
            //log("Closed message box");
          }
        }).show();
      }
    }));
    hPanel.add(new Button("Widget", new ClickHandler() {
      public void onClick(ClickEvent event) {
        ListBox lb = new ListBox();
        lb.addItem("foo");
        lb.addItem("bar");
        lb.addItem("baz");
        MessageBox.prompt("Any widget", "Please select one of the following items", lb, new MessageBoxListener() {
          public void onMessageBoxClosed(MessageBox sender, ButtonType buttonClicked) {
            //Window.alert("You entered: " + sender.getText());
            //log("Closed message box");
          }
        }).show();
      }
    }));

    return hPanel;
  }
}
