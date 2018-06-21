/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.swagger.fixtures;

import java.util.List;

import javax.ws.rs.QueryParam;

public class MySurnameBean {

  @QueryParam("surname")
  private List<String> surnames;

  public List<String> getSurnames() {
    return surnames;
  }

}
