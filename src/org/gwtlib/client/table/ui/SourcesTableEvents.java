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

/**
 * A widget that implements this interface sources the events defined by the 
 * TableListener interface in the <code>org.gwtlib.client.table.ui</code> package.
 * 
 * @author Sander Berents
 */
public interface SourcesTableEvents {
  /**
   * Adds a listener interface to receive table events.
   * @param listener
   */
  public void addTableListener(TableListener listener);
  
  /**
   * Removes a previously added listener interface.
   * @param listener
   */
  public void removeTableListener(TableListener listener);
}
