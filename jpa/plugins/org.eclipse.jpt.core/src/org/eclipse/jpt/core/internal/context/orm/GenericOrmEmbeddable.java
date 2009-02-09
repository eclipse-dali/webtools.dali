/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;


public class GenericOrmEmbeddable extends AbstractOrmTypeMapping<XmlEmbeddable> implements OrmEmbeddable
{
	public GenericOrmEmbeddable(OrmPersistentType parent, XmlEmbeddable resourceMapping) {
		super(parent, resourceMapping);
	}
	
	public JavaEmbeddable getJavaEmbeddable() {
		JavaPersistentType javaPersistentType = this.getJavaPersistentType();
		if (javaPersistentType != null && javaPersistentType.getMappingKey() == MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY) {
			return (JavaEmbeddable) javaPersistentType.getMapping();
		}
		return null;
	}

	/**
	 * This checks metaDataComplete before returning the JavaEmbeddable.
	 * As far as defaults are concerned, if metadataComplete is true, the JavaEmbeddable is ignored.
	 */
	protected JavaEmbeddable getJavaEmbeddableForDefaults() {
		if (isMetadataComplete()) {
			return null;
		}
		return getJavaEmbeddable();
	}
	
	public String getKey() {
		return MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY;
	}

	public boolean tableNameIsInvalid(String tableName) {
		return false;
	}

	public Iterator<String> associatedTableNamesIncludingInherited() {
		return EmptyIterator.instance();
	}

	public Iterator<Table> associatedTables() {
		return EmptyIterator.instance();
	}

	public Iterator<Table> associatedTablesIncludingInherited() {
		return EmptyIterator.instance();
	}

	public int getXmlSequence() {
		return 2;
	}

	@Override
	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		return attributeMappingKey == MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY || attributeMappingKey == MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY;
	}

	public void addToResourceModel(XmlEntityMappings entityMappings) {
		entityMappings.getEmbeddables().add(this.resourceTypeMapping);
	}
	
	public void removeFromResourceModel(XmlEntityMappings entityMappings) {
		entityMappings.getEmbeddables().remove(this.resourceTypeMapping);
	}
}
