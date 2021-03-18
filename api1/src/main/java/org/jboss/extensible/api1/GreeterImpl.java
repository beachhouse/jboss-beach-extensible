/*
 * JBoss, Home of Professional Open Source.
 * Copyright (c) 2021, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.extensible.api1;

import org.jboss.extensible.WrapperFactory;

/**
 * This one should be able to used with only API1 present, it can not have any direct dependency on other APIs.
 *
 * It can however support any number of APIs through the wrapper factory.
 */
public class GreeterImpl implements API1 {
    private static WrapperFactory<GreeterImpl>[] WRAPPERS = WrapperFactory.findWrappersFor(GreeterImpl.class);

    private String prompt = "Hi";

    @Override
    public String greet(final String name) {
        return prompt + " " + name;
    }

    public void setPrompt(final String prompt) {
        this.prompt = prompt;
    }

    @Override
    public <T> T unwrap(final Class<T> cls) {
        return WrapperFactory.wrapTo(WRAPPERS, cls, this);
    }
}
