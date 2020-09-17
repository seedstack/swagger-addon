/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.swagger;

import io.swagger.core.filter.SwaggerSpecFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.seedstack.coffig.Config;

@Config("swagger")
public class SwaggerConfig {
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
    private List<String> schemes = new ArrayList<>();
    private String basePath;
    private boolean prettyPrint;
    private Class<? extends SwaggerSpecFilter> filterClass;

    public String getTitle() {
        return title;
    }

    public SwaggerConfig setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SwaggerConfig setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public SwaggerConfig setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }

    public SwaggerConfig setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
        return this;
    }

    public String getContactName() {
        return contactName;
    }

    public SwaggerConfig setContactName(String contactName) {
        this.contactName = contactName;
        return this;
    }

    public String getContactUrl() {
        return contactUrl;
    }

    public SwaggerConfig setContactUrl(String contactUrl) {
        this.contactUrl = contactUrl;
        return this;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public SwaggerConfig setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        return this;
    }

    public String getLicenseName() {
        return licenseName;
    }

    public SwaggerConfig setLicenseName(String licenseName) {
        this.licenseName = licenseName;
        return this;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public SwaggerConfig setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
        return this;
    }

    public String getHost() {
        return host;
    }

    public SwaggerConfig setHost(String host) {
        this.host = host;
        return this;
    }

    public List<String> getSchemes() {
        return Collections.unmodifiableList(schemes);
    }

    public SwaggerConfig addScheme(String scheme) {
        this.schemes.add(scheme);
        return this;
    }

    public String getBasePath() {
        return basePath;
    }

    public SwaggerConfig setBasePath(String basePath) {
        this.basePath = basePath;
        return this;
    }

    public boolean isPrettyPrint() {
        return prettyPrint;
    }

    public SwaggerConfig setPrettyPrint(boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
        return this;
    }

    public Class<? extends SwaggerSpecFilter> getFilterClass() {
        return filterClass;
    }

    public SwaggerConfig setFilterClass(Class<? extends SwaggerSpecFilter> filterClass) {
        this.filterClass = filterClass;
        return this;
    }
}
