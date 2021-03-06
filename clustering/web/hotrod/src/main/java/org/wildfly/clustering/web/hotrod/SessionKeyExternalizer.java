/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2016, Red Hat, Inc., and individual contributors
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

package org.wildfly.clustering.web.hotrod;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.function.Function;

import org.wildfly.clustering.infinispan.client.Key;
import org.wildfly.clustering.marshalling.Externalizer;
import org.wildfly.clustering.web.cache.SessionIdentifierSerializer;

/**
 * @author Paul Ferraro
 */
public class SessionKeyExternalizer<K extends Key<String>> implements Externalizer<K> {

    private final Class<K> targetClass;
    private final Function<String, K> resolver;

    public SessionKeyExternalizer(Class<K> targetClass, Function<String, K> resolver) {
        this.targetClass = targetClass;
        this.resolver = resolver;
    }

    @Override
    public void writeObject(ObjectOutput output, K key) throws IOException {
        SessionIdentifierSerializer.INSTANCE.write(output, key.getId());
    }

    @Override
    public K readObject(ObjectInput input) throws IOException, ClassNotFoundException {
        String id = SessionIdentifierSerializer.INSTANCE.read(input);
        return this.resolver.apply(id);
    }

    @Override
    public Class<K> getTargetClass() {
        return this.targetClass;
    }
}
