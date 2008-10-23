/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;

public abstract class VirtualXmlAttributeMapping<T extends JavaAttributeMapping> extends AbstractJpaEObject implements XmlAttributeMapping
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected final T javaAttributeMapping;
	
	protected VirtualXmlAttributeMapping(OrmTypeMapping ormTypeMapping, T javaAttributeMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaAttributeMapping;
	}
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}
	
	protected T getJavaAttributeMapping() {
		return this.javaAttributeMapping;
	}
	
	public String getMappingKey() {
		return MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY;//TODO unsure about this
	}
}
