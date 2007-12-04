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
import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.context.base.IEmbeddable;
import org.eclipse.jpt.core.internal.context.base.ITable;
import org.eclipse.jpt.core.internal.resource.orm.Embeddable;
import org.eclipse.jpt.core.internal.resource.orm.EntityMappings;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;


public class XmlEmbeddable extends XmlTypeMapping implements IEmbeddable
{
	protected Embeddable embeddable;
	
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
	
	@Override
	protected void setAccessOnResource(AccessType newAccess) {
		this.embeddable.setAccess(AccessType.toXmlResourceModel(newAccess));
	}
	
	@Override
	protected void setClassOnResource(String newClass) {
		this.embeddable.setClassName(newClass);
	}
	
	@Override
	protected void setMetadataCompleteOnResource(Boolean newMetadataComplete) {
		this.embeddable.setMetadataComplete(newMetadataComplete);
	}
	
	public void initialize(Embeddable embeddable) {
		this.embeddable = embeddable;
		this.class_ = embeddable.getClassName();
		this.specifiedMetadataComplete = this.metadataComplete(embeddable);
		this.specifiedAccess = AccessType.fromXmlResourceModel(embeddable.getAccess());
		this.defaultMetadataComplete = this.defaultMetadataComplete();
		this.defaultAccess = this.defaultAccess();
	}
	
	public void update(Embeddable embeddable) {
		this.embeddable = embeddable;
		this.setClass(embeddable.getClassName());
		this.setSpecifiedMetadataComplete(this.metadataComplete(embeddable));
		this.setSpecifiedAccess(AccessType.fromXmlResourceModel(embeddable.getAccess()));

		//this.setDefaultName(defaultName());
		this.setDefaultMetadataComplete(this.defaultMetadataComplete());
		this.setDefaultAccess(this.defaultAccess());
	}

	protected Boolean metadataComplete(Embeddable embeddable) {
		return embeddable.getMetadataComplete();
	}
	
	@Override
	public void removeFromResourceModel() {
		this.embeddable.entityMappings().getEmbeddables().remove(this.embeddable);
	}

	@Override
	public void addToResourceModel(EntityMappings entityMappings) {
		this.embeddable = OrmFactory.eINSTANCE.createEmbeddable();
		entityMappings.getEmbeddables().add(this.embeddable);
	}
}
