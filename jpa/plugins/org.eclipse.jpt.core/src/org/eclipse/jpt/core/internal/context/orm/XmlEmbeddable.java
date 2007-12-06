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
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.IEmbeddable;
import org.eclipse.jpt.core.internal.context.base.ITable;
import org.eclipse.jpt.core.internal.resource.orm.Embeddable;
import org.eclipse.jpt.core.internal.resource.orm.EntityMappings;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;


public class XmlEmbeddable extends XmlTypeMapping<Embeddable> implements IEmbeddable
{
	public XmlEmbeddable(XmlPersistentType parent) {
		super(parent);
	}
	
	public String getKey() {
		return IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY;
	}

	public boolean tableNameIsInvalid(String tableName) {
		return false;
	}

	public Iterator<String> associatedTableNamesIncludingInherited() {
		return EmptyIterator.instance();
	}

	public Iterator<ITable> associatedTables() {
		return EmptyIterator.instance();
	}

	public Iterator<ITable> associatedTablesIncludingInherited() {
		return EmptyIterator.instance();
	}

	@Override
	public int xmlSequence() {
		return 2;
	}

	@Override
	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		return attributeMappingKey == IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY || attributeMappingKey == IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY;
	}

	protected Boolean metadataComplete(Embeddable embeddable) {
		return embeddable.getMetadataComplete();
	}
	
	@Override
	public void removeFromResourceModel(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		entityMappings.getEmbeddables().remove(this.typeMappingResource());
	}

	@Override
	public Embeddable addToResourceModel(EntityMappings entityMappings) {
		Embeddable embeddable = OrmFactory.eINSTANCE.createEmbeddable();
		entityMappings.getEmbeddables().add(embeddable);
		return embeddable;
	}
}
