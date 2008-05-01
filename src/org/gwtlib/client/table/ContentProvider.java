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
package org.gwtlib.client.table;

/**
 * Content provider interface.
 *
 * @author Sander Berents
 */
public interface ContentProvider {
  /**
   * Invoked by <code>Table</code> for retrieving data.
   * @param begin Zero based begin offset.
   * @param end Zero based end offset (size = <code>end - begin</code>).
   * @param sortId Sort column identifier or null. 
   * @param ascending True for ascending sorting, false for descending sorting. 
   *   Should be ignored if <code>sortId</code> is null.
   */
  public void load(int begin, int end, int sortId, boolean ascending);
}