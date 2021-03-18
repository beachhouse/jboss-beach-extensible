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
package org.jboss.extensible;

import java.util.ServiceLoader;

public abstract class WrapperFactory<W> {
    public abstract boolean canWrap(final Class<?> cls);

    public static <W> WrapperFactory<W>[] findWrappersFor(final Class<W> cls) {
        final ServiceLoader<WrapperFactory> loader = ServiceLoader.load(WrapperFactory.class);
        return loader.stream().map(ServiceLoader.Provider::get).filter(s -> s.canWrap(cls)).toArray(value -> new WrapperFactory[value]);
    }

    public abstract <T> T wrap(final Class<T> cls, final W instance);

    public static <T, W> T wrapTo(final WrapperFactory<W>[] wrappers, final Class<T> cls, W instance) {
        for (WrapperFactory<W> wrapper : wrappers) {
            if (wrapper.wrapsTo(cls))
                return wrapper.wrap(cls, instance);
        }
        throw new IllegalStateException("No wrapper factory found for " + cls);
    }

    public abstract boolean wrapsTo(final Class<?> cls);
}
