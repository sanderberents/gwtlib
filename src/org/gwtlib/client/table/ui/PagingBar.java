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

import org.gwtlib.client.ui.Messages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.SourcesChangeEvents;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Paging bar.
 * 
 * CSS Style Rules:
 * <ul>
 * <li>.gwtlib-PagingBar { the paging bar itself }</li>
 * <li>.gwtlib-PagingBar-position { current position section }</li>
 * <li>.gwtlib-PagingBar-browser { browser secion }</li>
 * <li>.gwtlib-PagingBar-goto { go to page selection section }</li>
 * <li>.gwtlib-PagingBar-gotoButton { go to button of page selection section }</li>
 * <li>.gwtlib-PagingBar-gotoButton-up { go to button of page selection section }</li>
 * <li>.gwtlib-PagingBar-gotoButton-up-disabled { go to button of page selection section in disabled state }</li>
 * <li>.gwtlib-PagingBar-pagesize { page size section }</li>
 * <li>.gwtlib-PagingBar-browser-first { first page button }</li>
 * <li>.gwtlib-PagingBar-browser-first-up { first page button in enabled state }</li>
 * <li>.gwtlib-PagingBar-browser-first-up-disabled { first page button in disabled state }</li>
 * <li>.gwtlib-PagingBar-browser-prev { previous page button }</li>
 * <li>.gwtlib-PagingBar-browser-prev-up { previous page button in enabled state }</li>
 * <li>.gwtlib-PagingBar-browser-prev-up-disabled { previous page button in disabled state }</li>
 * <li>.gwtlib-PagingBar-browser-next { next page button }</li>
 * <li>.gwtlib-PagingBar-browser-next-up { next page button in enabled state }</li>
 * <li>.gwtlib-PagingBar-browser-next-up-disabled { next page button in disabled state }</li>
 * <li>.gwtlib-PagingBar-browser-last { last page button }</li>
 * <li>.gwtlib-PagingBar-browser-last-up { last page button in enabled state }</li>
 * <li>.gwtlib-PagingBar-browser-last-up-disabled { last page button in disabled state }</li>
 * <li>.gwtlib-PagingBar-browser-page { currently selected page }</li>
 * <li>.gwtlib-PagingBar-browser-page-enabled { other selectable page }</li>
 * <li>.gwtlib-PagingBar-loading { data loading status }</li>
 * <li>.gwtlib-PagingBar-loading-up { data loading status }</li>
 * <li>.gwtlib-PagingBar-loading-up-disabled { data loading status (finished loading) }</li>
 * </ul>
 * 
 * @author Sander Berents
 */
public class PagingBar extends Composite implements SourcesChangeEvents {
  private static final String STYLE             = "gwtlib-PagingBar";
  private static final String STYLE_POSITION    = "gwtlib-PagingBar-position";
  private static final String STYLE_BROWSER     = "gwtlib-PagingBar-browser";
  private static final String STYLE_GOTO        = "gwtlib-PagingBar-goto";
  private static final String STYLE_GOTO_BUTTON = "gwtlib-PagingBar-gotoButton";
  private static final String STYLE_PAGESIZE    = "gwtlib-PagingBar-pagesize";
  private static final String STYLE_PAGE        = "gwtlib-PagingBar-browser-page";
  private static final String STYLE_LOADING     = "gwtlib-PagingBar-loading";
  private static final String STYLE_ENABLED     = "enabled";
  
  private static final String[] STYLES_BROWSER  = {
    "gwtlib-PagingBar-browser-first", "gwtlib-PagingBar-browser-prev", 
    "gwtlib-PagingBar-browser-next", "gwtlib-PagingBar-browser-last"
  };

  private static final int FIRST = 0;
  private static final int PREV  = 1;
  private static final int NEXT  = 2;
  private static final int LAST  = 3;

  private static final int DEFAULT_LIMIT = 60000;

  private int _page; // Zero based current page
  private int _size; // Total number of results
  private int _limit = DEFAULT_LIMIT;
  private int _pageSize;
  private int[] _pageSizes;
  private int _pages;    // Number of pages, computed from size/limit/pageSize
  private boolean _loading;

  private Widget _positionWidget;
  private Widget _loadingWidget;
  private Widget _browserWidget;
  private Widget _gotoWidget;
  private Widget _pageSizesWidget;
  protected ChangeListenerCollection _listeners = new ChangeListenerCollection();
  protected Messages _messages = (Messages)GWT.create(Messages.class);
  
  public PagingBar(int size, int pageSize) {
    this(0, size, pageSize, null);
  }

  public PagingBar(Messages messages, int size, int pageSize) {
    this(0, size, pageSize, null);
    _messages = messages;
  }

  public PagingBar(int page, int size, int pageSize, int[] pageSizes) {
    _page = page;
    _size = size;
    _pageSize = pageSize;
    _pageSizes = pageSizes;
    _pages = computeNumPages();

    // Create widgets in protected methods to provide customization hooks
    _positionWidget = createPositionWidget();
    if(_positionWidget != null) updatePositionWidget(_positionWidget);
    _loadingWidget = createLoadingWidget();
    if(_loadingWidget != null) updateLoadingWidget(_loadingWidget);
    _browserWidget = createBrowserWidget();
    if(_browserWidget != null) updateBrowserWidget(_browserWidget);
    _gotoWidget = createGotoWidget();
    if(_gotoWidget != null) updateGotoWidget(_gotoWidget);
    _pageSizesWidget = createPageSizeWidget();
    if(_pageSizesWidget != null) updatePageSizeWidget(_pageSizesWidget);
    // Combine all the above created widgets
    Widget panel = create();
    initWidget(panel);
    setStyleName(STYLE);
  }

  public PagingBar(Messages messages, int page, int size, int pageSize, int[] pageSizes) {
    this(page, size, pageSize, pageSizes);
    _messages = messages;
  }

  protected Widget create() {
    HorizontalPanel panel = new HorizontalPanel();
    panel.setSpacing(0);
    panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    if(_positionWidget != null) panel.add(_positionWidget);
    if(_loadingWidget != null) panel.add(_loadingWidget);
    if(_browserWidget != null) panel.add(_browserWidget);
    panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
    if(_gotoWidget != null) panel.add(_gotoWidget);
    if(_pageSizesWidget != null) panel.add(_pageSizesWidget);
    return panel;
  }
  
  protected Widget createPositionWidget() {
    Label label = new Label();
    label.setStylePrimaryName(STYLE_POSITION);
    return label;
  }
  
  protected void updatePositionWidget(Widget widget) {
    int pos = getPosition();
    int pos1 = pos + 1;
    String s;

    if(_pageSize > 1) {
      int pos2 = pos + _pageSize > _size ? _size : pos + _pageSize;
      if(_size > _limit) {
        s = _messages.rangelimit(pos1, pos2, _size, _limit);
      } else {
        s = _messages.range(pos1, pos2, _size);
      }
    } else {
      if(_size > _limit) {
        s = _messages.poslimit(pos1, _size, _limit);
      } else {
        s = _messages.pos(pos1, _size);
      }
    }
    ((Label)widget).setText(s);
    widget.setVisible(_size > 0);
  }

  protected Widget createLoadingWidget() {
    PushButton loading = new PushButton();
    loading.setStylePrimaryName(STYLE_LOADING);
    return loading;
  }

  protected void updateLoadingWidget(Widget widget) {
    ((PushButton)widget).setEnabled(_loading);
  }

  protected Widget createBrowserWidget() {
    HorizontalPanel panel = new HorizontalPanel();
    panel.setStylePrimaryName(STYLE_BROWSER);
    return panel;
  }
  
  protected void updateBrowserWidget(Widget widget) {
    HorizontalPanel panel = (HorizontalPanel)widget;
    int minpage = _page > 2 ? _page - 2 : 0;
    int maxpage = minpage + 4;
    if(maxpage >= _pages) {
      maxpage = _pages - 1;
      minpage = _pages > 4 ? _pages - 4 : 0;
    }
    panel.clear();
    panel.add(createBrowserItemWidget(FIRST, _page > 0));
    panel.add(createBrowserItemWidget(PREV, _page > 0));
    for(int i = minpage; i <= maxpage; ++i) {
      panel.add(createBrowserItemWidget(String.valueOf(i + 1), i != _page));
    }
    panel.add(createBrowserItemWidget(NEXT, _page < _pages - 1));
    panel.add(createBrowserItemWidget(LAST, _page < _pages - 1));
    widget.setVisible(_size > 0);
  }

  protected Widget createBrowserItemWidget(final int type, boolean enabled) {
    final ClickListener clickListener = new ClickListener() {
      public void onClick(Widget sender) {
        switch(type) {
          case FIRST:
            _page = 0;
            break;
          case PREV:
            --_page;
            break;
          case NEXT:
            ++_page;
            break;
          case LAST:
            _page = _pages - 1;
            break;
        }
        fireChange(sender);
      }
    };
    PushButton button = new PushButton();
    button.setStylePrimaryName(STYLES_BROWSER[type]);
    button.setEnabled(enabled);
    if(enabled) button.addClickListener(clickListener);
    return button;
  }

  protected Widget createBrowserItemWidget(String text, boolean enabled) {
    Label label = new Label(text);
    label.setStylePrimaryName(STYLE_PAGE);
    if(enabled) {
      label.addStyleDependentName(STYLE_ENABLED);
      label.addClickListener(new ClickListener() {
        public void onClick(Widget sender) {
          String text = ((Label)sender).getText();
          _page = Integer.parseInt(text) - 1;
          fireChange(sender);
        }
      });
    }
    return label;
  }

  protected Widget createGotoWidget() {
    final TextBox gotoPage = new TextBox();
    int maxlen = String.valueOf(computeNumPages()).length();
    gotoPage.setMaxLength(maxlen);
    gotoPage.setVisibleLength(maxlen);
    final PushButton go = new PushButton();
    go.setStylePrimaryName(STYLE_GOTO_BUTTON);
    go.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        setPage(Integer.parseInt(gotoPage.getText()) - 1);
        gotoPage.setText("");
        go.setEnabled(false);
        fireChange(sender);
      }
    });
    go.setEnabled(false);
    gotoPage.addKeyboardListener(new KeyboardListenerAdapter() {
      public void onKeyDown(final Widget sender, final char keyCode, int modifiers) {
        DeferredCommand.addCommand(new Command() {
          public void execute() {
            int page = -1;
            try {
              page = Integer.parseInt(gotoPage.getText()) - 1;
            } catch(NumberFormatException e) {
            }
            go.setEnabled(page >= 0 && page < computeNumPages());
            if(keyCode == KEY_ENTER && go.isEnabled()) {
              setPage(Integer.parseInt(gotoPage.getText()) - 1);
              gotoPage.setText("");
              go.setEnabled(false);
              fireChange(sender);
            }
          }
        });
      }
    });

    HorizontalPanel panel = new HorizontalPanel();
    panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    panel.add(new HTML(_messages.go()));
    panel.add(gotoPage);
    panel.add(go);
    panel.setStylePrimaryName(STYLE_GOTO);
    return panel;
  }

  protected void updateGotoWidget(Widget widget) {
    widget.setVisible(computeNumPages() > 1);
    // Update allowed number of characters in the goto page textbox in case page size has changed (Yuck!)
    if(widget instanceof HorizontalPanel) {
      HorizontalPanel panel = (HorizontalPanel)widget;
      if(panel.getWidgetCount() == 3 && panel.getWidget(1) instanceof TextBox) {
        TextBox gotoPage = (TextBox)panel.getWidget(1);
        int maxlen = String.valueOf(computeNumPages()).length();
        gotoPage.setMaxLength(maxlen);
        gotoPage.setVisibleLength(maxlen);
      }
    }
  }

  protected Widget createPageSizeWidget() {
    HorizontalPanel panel = new HorizontalPanel();
    panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    panel.add(new HTML(_messages.pagesize1()));
    panel.add(new ListBox());
    panel.add(new HTML(_messages.pagesize2()));
    panel.setStylePrimaryName(STYLE_PAGESIZE);
    return panel;
  }

  protected void updatePageSizeWidget(Widget widget) {
    if(_pageSizes != null) {
      final ListBox sizes = (ListBox)((HorizontalPanel)_pageSizesWidget).getWidget(1);
      sizes.clear();
      for(int i = 0; i < _pageSizes.length; ++i) {
        sizes.addItem(String.valueOf(_pageSizes[i]));
        if(_pageSize == _pageSizes[i]) sizes.setSelectedIndex(i);
      }
      sizes.addChangeListener(new ChangeListener() {
        public void onChange(Widget sender) {
          int i = sizes.getSelectedIndex();
          if(i >= 0) {
            setPageSize(Integer.parseInt(sizes.getItemText(i)));
            setPage(0);
            fireChange(sender); 
          }
        }
      });
    }
    widget.setVisible(_size > 0 && _pageSizes != null && _pageSizes.length > 1);
  }

  /**
   * Returns the offset of the first item.
   * @return Zero based offset.
   */
  public int getPosition() {
    int pos = _page * _pageSize;
    pos = Math.min(pos, getLimitedSize() - (_size % _pageSize));
    return pos < 0 ? 0 : pos;
  }

  /**
   * Sets the page number
   * @param page
   */
  public void setPage(int page) {
    _page = page;
  }

  public int getPageSize() {
    return _pageSize;
  }

  /**
   * Sets the Page size (Records per page)
   * @param pageSize
   */
  public void setPageSize(int pageSize) {
    _pageSize = pageSize;
    _pages = computeNumPages();
    update();
  }

  /**
   * Sets the total size of items
   * @param size
   */
  public void setSize(int size) {
    _size = size;
    _pages = computeNumPages();
    update();
  }

  public int getSize() {
    return _size;
  }

  /**
   * Sets the limits for total number items and number of pages
   * @param limit
   */
  public void setLimit(int limit) {
    _limit = limit;
    _pages = computeNumPages();
  }

  /**
   * Sets loading status.
   * @param loading
   */
  public void setLoading(boolean loading) {
    _loading = loading;
    updateLoadingWidget(_loadingWidget);
  }

  /**
   * Updates the status of the PagingBar.
   */
  public void update() {
    if(_positionWidget != null) updatePositionWidget(_positionWidget);
    if(_browserWidget != null) {
      updateBrowserWidget(_browserWidget);
      _browserWidget.setVisible(computeNumPages() > 1);
    }
    if(_gotoWidget != null) updateGotoWidget(_gotoWidget);
    if(_pageSizesWidget != null) _pageSizesWidget.setVisible(_size > 0 && _pageSizes != null && _pageSizes.length > 1);
    setVisible(computeNumPages() > 0);
  }

  /**
   * The fireChange method is invoked when any click event or change event
   * occurs. It fires a change event for all listeners and updates the PagingBar.
   * @param sender
   */
  protected void fireChange(Widget sender) {
    if(_listeners != null) _listeners.fireChange(this);
    update();
  }

  public void addChangeListener(ChangeListener listener) {
    _listeners.add(listener);
  }

  public void removeChangeListener(ChangeListener listener) {
    _listeners.remove(listener);
  }

  /**
   * Returns the computed number of pages
   * @return Number of pages.
   */
  protected int computeNumPages() {
    return (getLimitedSize() + _pageSize - 1) / _pageSize;
  }

  /**
   * Returns the limit or number of items.
   */
  protected int getLimitedSize() {
    return _size > _limit ? _limit : _size;
  }
}
