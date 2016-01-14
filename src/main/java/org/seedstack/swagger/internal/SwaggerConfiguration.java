/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.swagger.internal;

import com.google.common.collect.Lists;
import org.apache.commons.configuration.Configuration;

import java.util.ArrayList;
import java.util.List;

public class SwaggerConfiguration {
    private static final String PREFIX = "org.seedstack.swagger";

    private String title;
    private String description;
    private String version;
    private String termsOfServiceUrl;
    private String contactName;
    private String contactUrl;
    private String contactEmail;
    private String licenseName;
    private String licenseUrl;
    private String host;
    private List<String> schemes = new ArrayList<String>();
    private String basePath;
    private boolean prettyPrint;
    private String filterClass;

    public String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    void setVersion(String version) {
        this.version = version;
    }

    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }

    void setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public String getContactName() {
        return contactName;
    }

    void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactUrl() {
        return contactUrl;
    }

    void setContactUrl(String contactUrl) {
        this.contactUrl = contactUrl;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getLicenseName() {
        return licenseName;
    }

    void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public String getHost() {
        return host;
    }

    void setHost(String host) {
        this.host = host;
    }

    public List<String> getSchemes() {
        return schemes;
    }

    void setSchemes(String... schemes) {
        this.schemes = arrayToList(schemes);
    }

    public String getBasePath() {
        return basePath;
    }

    void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public boolean isPrettyPrint() {
        return prettyPrint;
    }

    void setPrettyPrint(boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
    }

    public String getFilterClass() {
        return filterClass;
    }

    void setFilterClass(String filterClass) {
        this.filterClass = filterClass;
    }

    SwaggerConfiguration init(Configuration configuration) {
        Configuration swaggerConfiguration = configuration.subset(PREFIX);
        this.title = swaggerConfiguration.getString("title");
        this.description = swaggerConfiguration.getString("description");
        this.version = swaggerConfiguration.getString("version");
        this.host = swaggerConfiguration.getString("host");
        this.schemes = arrayToList(swaggerConfiguration.getStringArray("schemes"));
        this.basePath = swaggerConfiguration.getString("base-path");
        this.prettyPrint = swaggerConfiguration.getBoolean("pretty-print", false);
        this.termsOfServiceUrl = swaggerConfiguration.getString("terms-of-service-url");
        this.contactName = swaggerConfiguration.getString("contact.name");
        this.contactUrl = swaggerConfiguration.getString("contact.url");
        this.contactEmail = swaggerConfiguration.getString("contact.email");
        this.licenseName = swaggerConfiguration.getString("license.name");
        this.licenseUrl = swaggerConfiguration.getString("license.url");
        this.filterClass = swaggerConfiguration.getString("filterClass");
        return this;
    }

    private List<String> arrayToList(String[] stringArray) {
        if (stringArray.length > 0) {
            return Lists.newArrayList(stringArray);
        }
        else {
            return new ArrayList<String>();
        }
    }
}
