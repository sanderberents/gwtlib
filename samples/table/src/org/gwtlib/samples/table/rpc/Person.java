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
package org.gwtlib.samples.table.rpc;

import java.io.Serializable;

/**
 * RPC Serializable Person.
 * 
 * @author Sander Berents
 */
public class Person implements Serializable {
  protected String _first;
  protected String _last;

  public Person() {
  }

  public Person(String first, String last) {
    _first = first;
    _last = last;
  }
  
  public String getFirst() {
    return _first;
  }
  
  public String getLast() {
    return _last;
  }
}
