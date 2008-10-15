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
package org.gwtlib.samples.table.client.ui;

import org.gwtlib.client.table.ColumnLayout;
import org.gwtlib.client.table.ContentProvider;
import org.gwtlib.client.table.Row;
import org.gwtlib.client.table.Rows;
import org.gwtlib.client.table.ui.Column;
import org.gwtlib.client.table.ui.Table;
import org.gwtlib.samples.table.rpc.Person;
import org.gwtlib.samples.table.rpc.PersonService;
import org.gwtlib.samples.table.rpc.PersonServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Table demonstrating using data via an RPC call.
 * 
 * @author Sander Berents
 */
public class RPCTableEntryPoint implements EntryPoint {
  private static final String ID = "gwtlib-table";
  private PersonServiceAsync _service = (PersonServiceAsync)GWT.create(PersonService.class);

  private class PersonContentProvider implements ContentProvider {
    private Table _table;
    private AsyncCallback _callback;

    PersonContentProvider(Table table) {
      _table = table;
      _callback = new AsyncCallback() {
        public void onSuccess(Object result) {
          Person[] persons = (Person[])result;
          Rows rows = transform(persons);
          _table.onSuccess(rows);
        }

        public void onFailure(Throwable caught) {
          _table.onFailure(caught);
        }
      };
    }

    public void load(int begin, int end, int sortId, boolean ascending) {
      _service.getPersons(begin, end, sortId, ascending, _callback);
    }
    
    private Rows transform(Person[] persons) {
      Row[] rows = new Row[persons.length];
      for(int i = 0; i < persons.length; ++i) {
        rows[i] = new Row(i, new Object[] { persons[i].getFirst(), persons[i].getLast() });
      }
      return new Rows(rows, 0);
    }
  };

  public void onModuleLoad() {
    RootPanel root = RootPanel.get(ID);
    if(root != null) init(root);
  }

  private void init(RootPanel root) {
    ServiceDefTarget endpoint = (ServiceDefTarget)_service;
    endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "person-service");

    Column[] columns = {
      new Column(0, false, "First Name", "50%"),
      new Column(1, false, "Last Name", "50%")
    };
    Table table = new Table(new ColumnLayout(columns));
    table.setSize("100%", "100%");
    table.setContentProvider(new PersonContentProvider(table));
    table.update();
    root.add(table);
  }
}