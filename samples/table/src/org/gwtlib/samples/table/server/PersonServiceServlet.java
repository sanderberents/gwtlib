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
package org.gwtlib.samples.table.server;

import org.gwtlib.samples.table.rpc.Person;
import org.gwtlib.samples.table.rpc.PersonService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Person RPC service implementation.
 * 
 * @author Sander Berents
 */
@SuppressWarnings("serial")
public class PersonServiceServlet extends RemoteServiceServlet implements PersonService {
  private static final Person[] PERSONS = {
    new Person("Galileo", "Galilei"),
    new Person("Nicolaus", "Copernicus"),
    new Person("Isaac", "Newton"),
    new Person("Johannes", "Kepler"),
    new Person("Albert", "Einstein"),
    new Person("James", "Maxwell"),
    new Person("Antony", "van Leeuwenhoek"),
    new Person("Louis", "Pasteur"),
    new Person("Charles", "Darwin"),
    new Person("Marie", "Curie")
  };

  public Person[] getPersons(int begin, int end, int sortId, boolean ascending) {
    return PERSONS;
  }
}
