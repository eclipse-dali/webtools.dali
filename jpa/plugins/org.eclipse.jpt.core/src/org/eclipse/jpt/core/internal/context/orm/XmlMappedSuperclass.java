/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IMappedSuperclass;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.ITable;
import org.eclipse.jpt.core.internal.resource.orm.EntityMappings;
import org.eclipse.jpt.core.internal.resource.orm.IdClass;
import org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;


public class XmlMappedSuperclass extends XmlTypeMapping<MappedSuperclass>
	implements IMappedSuperclass
{
	protected String idClass;
	
	public XmlMappedSuperclass(XmlPersistentType parent) {
		super(parent);
	}

	public String getIdClass() {
		return this.idClass;
	}
	
	public void setIdClass(String newIdClass) {
		String oldIdClass = this.idClass;
		this.idClass = newIdClass;
		if (oldIdClass != newIdClass) {
			if (this.idClassResource() != null) {
				this.idClassResource().setClassName(newIdClass);						
				if (this.idClassResource().isAllFeaturesUnset()) {
					removeIdClassResource();
				}
			}
			else if (newIdClass != null) {
				addIdClassResource();
				idClassResource().setClassName(newIdClass);
			}
		}
		firePropertyChanged(IEntity.ID_CLASS_PROPERTY, oldIdClass, newIdClass);
	}
	
	protected void setIdClass_(String newIdClass) {
		String oldIdClass = this.idClass;
		this.idClass = newIdClass;
		firePropertyChanged(IEntity.ID_CLASS_PROPERTY, oldIdClass, newIdClass);
	}

	protected IdClass idClassResource() {
		return typeMappingResource().getIdClass();
	}
	
	protected void addIdClassResource() {
		typeMappingResource().setIdClass(OrmFactory.eINSTANCE.createIdClass());		
	}
	
	protected void removeIdClassResource() {
		typeMappingResource().setIdClass(null);
	}

	public String getKey() {
		return IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY;
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
	public Iterator<String> overridableAttributeNames() {
		return this.namesOf(this.overridableAttributes());
	}

	public Iterator<IPersistentAttribute> overridableAttributes() {
		return new FilteringIterator<XmlPersistentAttribute, IPersistentAttribute>(this.persistentType().attributes()) {
			@Override
			protected boolean accept(XmlPersistentAttribute o) {
				return o.isOverridableAttribute();
			}
		};
	}

	@Override
	public Iterator<String> overridableAssociationNames() {
		return this.namesOf(this.overridableAssociations());
	}

	public Iterator<IPersistentAttribute> overridableAssociations() {
		return new FilteringIterator<XmlPersistentAttribute, IPersistentAttribute>(this.persistentType().attributes()) {
			@Override
			protected boolean accept(XmlPersistentAttribute o) {
				return o.isOverridableAssociation();
			}
		};
	}

	private Iterator<String> namesOf(Iterator<IPersistentAttribute> attributes) {
		return new TransformationIterator<IPersistentAttribute, String>(attributes) {
			@Override
			protected String transform(IPersistentAttribute attribute) {
				return attribute.getName();
			}
		};
	}

	@Override
	public int xmlSequence() {
		return 0;
	}

	protected Boolean metadataComplete(MappedSuperclass mappedSuperclass) {
		return mappedSuperclass.getMetadataComplete();
	}
	
	@Override
	public void removeFromResourceModel(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		entityMappings.getMappedSuperclasses().remove(this.typeMappingResource());
	}
	
	@Override
	public MappedSuperclass addToResourceModel(EntityMappings entityMappings) {
		MappedSuperclass mappedSuperclass = OrmFactory.eINSTANCE.createMappedSuperclass();
		persistentType().initialize(mappedSuperclass);
		entityMappings.getMappedSuperclasses().add(mappedSuperclass);
		return mappedSuperclass;
	}

	
	
	@Override
	public void initialize(MappedSuperclass mappedSuperclass) {
		super.initialize(mappedSuperclass);
		this.initializeIdClass(this.idClassResource());
	}
	
	protected void initializeIdClass(IdClass idClassResource) {
		this.idClass = this.idClass(idClassResource);	
	}
	
	@Override
	public void update(MappedSuperclass mappedSuperclass) {
		super.update(mappedSuperclass);
		this.updateIdClass(this.idClassResource());
	}
	
	protected void updateIdClass(IdClass idClassResource) {
		this.setIdClass_(this.idClass(idClassResource));
	}

	protected String idClass(IdClass idClassResource) {
		return idClassResource == null ? null : idClassResource.getClassName();
	}

}
