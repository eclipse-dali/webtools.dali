/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
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
import org.eclipse.jpt.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;


public class GenericOrmEmbeddable extends AbstractOrmTypeMapping<XmlEmbeddable> implements OrmEmbeddable
{
	public GenericOrmEmbeddable(OrmPersistentType parent) {
		super(parent);
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

	protected Boolean metadataComplete(XmlEmbeddable embeddable) {
		return embeddable.getMetadataComplete();
	}
	
	public void removeFromResourceModel(XmlEntityMappings entityMappings) {
		entityMappings.getEmbeddables().remove(this.getTypeMappingResource());
	}

	public XmlEmbeddable addToResourceModel(XmlEntityMappings entityMappings) {
		XmlEmbeddable embeddable = OrmFactory.eINSTANCE.createXmlEmbeddable();
		getPersistentType().initialize(embeddable);
		entityMappings.getEmbeddables().add(embeddable);
		return embeddable;
	}
}
