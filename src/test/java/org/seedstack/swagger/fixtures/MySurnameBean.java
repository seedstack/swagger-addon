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
