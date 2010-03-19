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
package org.gwtlib.client.ui;

/**
 * GWTLib messages.
 *
 * @author Sander Berents
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {
  String pos(int pos, int size);
  String poslimit(int pos, int size, int limit);
  String range(int from, int to, int size);
  String rangelimit(int from, int to, int size, int limit);
  String none();
  String error(String message);
  String page(int page, int numPages);
  String go();
  String pagesize1();
  String pagesize2();
}
