---
title: "Swagger"
addon: "Swagger"
repo: "https://github.com/seedstack/swagger-addon"
author: Pierre THIROUIN
description: "Automatically exposes a Swagger description of application JAX-RS resources."
tags:
    - rest
    - api
zones:
    - Addons
noMenu: true    
---

SeedStack [Swagger](https://swagger.io/) add-on exposes a REST resource describing annotated JAX-RS resources in the 
Swagger format. 

## Dependency

To automatically generate and expose a Swagger descriptor for your application, add the following dependency:

{{< dependency g="org.seedstack.addons.swagger" a="swagger" >}}

## Configuration

No configuration is needed for basic usage, but the options below can be used to tune the generated Swagger. 

{{% config p="swagger" %}}
```yaml
swagger:
  # The title of the published API.
  title: (String)
  
  # The description of the published API.
  description: (String)
  
  # The version of the published API.
  version: (String)
  
  # The URL of the published API terms of service.
  termsOfServiceUrl: (String)
  
  # The published API contact name.
  contactName: (String)
  
  # The contact URL of the published API.
  contactUrl: (String)
  
  # The published API contact email.
  contactEmail: (String)
  
  # The name of the published API license.
  licenseName: (String)
  
  # The URL of the published API license.
  licenseUrl: (String)
  
  # The hostname of the published API.
  host: (String)
  
  # The list schemes the published API is available on.
  schemes: (List<String>)
  
  # The base path of the published API.
  basePath: (String)
  
  # If true, the Swagger output is pretty printed (false by default).
  prettyPrint: (boolean)
  
  # The class implementing SwaggerSpecFilter that will be used to filter the Swagger output.
  filterClass: (String)
```
{{% /config %}}

## Usage

By default, JAX-RS resources are not exposed in the Swagger descriptor. You must annotate each resource you want to
expose with {{< java "io.swagger.annotations.Api" "@" >}}:

```java
@Api
@Path("/some-resource")
public class SomeResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "Hello World!";
    }
}
```

{{% callout info %}}
The Swagger add-on derives a lot of information from standard JAX-RS annotations but you can specify additional 
[Swagger annotations](http://docs.swagger.io/swagger-core/current/apidocs/index.html) to alter the resulting descriptor.
{{% /callout %}}

The application will serve the Swagger descriptor:
 
* In JSON format on `swagger.json`,
* In YAML format on `yaml.json`.

{{% callout info %}}
Those two resources are exposed on the application [base API path]({{< ref "docs/web/rest.md#base-path" >}}).
{{% /callout %}}

## Viewing the Swagger on-line

If you want something to display the descriptor in a graphical user-interface, you can use the 
[Swagger demo UI](http://petstore.swagger.io/) and make it point to the URL of your Swagger descriptor. 

{{% callout tips %}}
The Swagger UI will fetch the descriptor in your browser with JavaScript, meaning that you must enable [CORS]({{< ref "docs/web/cors.md" >}})
to make it work with the following configuration:

```yaml
web:
    cors: true
```
{{% /callout %}}

