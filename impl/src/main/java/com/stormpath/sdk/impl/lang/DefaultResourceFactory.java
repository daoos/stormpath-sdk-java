package com.stormpath.sdk.impl.lang;

import com.stormpath.sdk.lang.Assert;

public class DefaultResourceFactory implements ResourceFactory {

    @Override
    public Resource createResource(String location) {
        Assert.hasText(location, "location argument cannot be null or empty.");

        if (location.startsWith(ClasspathResource.SCHEME_PREFIX)) {
            return new ClasspathResource(location);
        }

        if (location.startsWith(UrlResource.SCHEME_PREFIX)) {
            return new UrlResource(location);
        }

        //otherwise we assume a file:
        return new FileResource(location);
    }
}
