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
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.IdClass;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlIdClass;
import org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;


public class GenericOrmMappedSuperclass extends AbstractOrmTypeMapping<XmlMappedSuperclass>
	implements OrmMappedSuperclass
{
	protected String idClass;
	
	public GenericOrmMappedSuperclass(OrmPersistentType parent) {
		super(parent);
	}
	
	public JavaMappedSuperclass getJavaMappedSuperclass() {
		if (this.javaPersistentType != null && this.javaPersistentType.getMappingKey() == MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY) {
			return (JavaMappedSuperclass) this.javaPersistentType.getMapping();
		}
		return null;
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
		firePropertyChanged(IdClass.ID_CLASS_PROPERTY, oldIdClass, newIdClass);
	}
	
	protected void setIdClass_(String newIdClass) {
		String oldIdClass = this.idClass;
		this.idClass = newIdClass;
		firePropertyChanged(IdClass.ID_CLASS_PROPERTY, oldIdClass, newIdClass);
	}

	protected XmlIdClass idClassResource() {
		return getTypeMappingResource().getIdClass();
	}
	
	protected void addIdClassResource() {
		getTypeMappingResource().setIdClass(OrmFactory.eINSTANCE.createXmlIdClass());		
	}
	
	protected void removeIdClassResource() {
		getTypeMappingResource().setIdClass(null);
	}

	public String getKey() {
		return MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY;
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

	@Override
	public Iterator<String> overridableAttributeNames() {
		return this.namesOf(this.overridableAttributes());
	}

	@Override
	public Iterator<OrmPersistentAttribute> overridableAttributes() {
		return new FilteringIterator<OrmPersistentAttribute, OrmPersistentAttribute>(this.getPersistentType().attributes()) {
			@Override
			protected boolean accept(OrmPersistentAttribute o) {
				return o.isOverridableAttribute();
			}
		};
	}

	@Override
	public Iterator<String> overridableAssociationNames() {
		return this.namesOf(this.overridableAssociations());
	}

	@Override
	public Iterator<OrmPersistentAttribute> overridableAssociations() {
		return new FilteringIterator<OrmPersistentAttribute, OrmPersistentAttribute>(this.getPersistentType().attributes()) {
			@Override
			protected boolean accept(OrmPersistentAttribute o) {
				return o.isOverridableAssociation();
			}
		};
	}

	private Iterator<String> namesOf(Iterator<OrmPersistentAttribute> attributes) {
		return new TransformationIterator<OrmPersistentAttribute, String>(attributes) {
			@Override
			protected String transform(OrmPersistentAttribute attribute) {
				return attribute.getName();
			}
		};
	}

	public int getXmlSequence() {
		return 0;
	}

	protected Boolean metadataComplete(XmlMappedSuperclass mappedSuperclass) {
		return mappedSuperclass.getMetadataComplete();
	}
	
	public void removeFromResourceModel(XmlEntityMappings entityMappings) {
		entityMappings.getMappedSuperclasses().remove(this.getTypeMappingResource());
	}
	
	public XmlMappedSuperclass addToResourceModel(XmlEntityMappings entityMappings) {
		XmlMappedSuperclass mappedSuperclass = OrmFactory.eINSTANCE.createXmlMappedSuperclass();
		getPersistentType().initialize(mappedSuperclass);
		entityMappings.getMappedSuperclasses().add(mappedSuperclass);
		return mappedSuperclass;
	}

	
	
	@Override
	public void initialize(XmlMappedSuperclass mappedSuperclass) {
		super.initialize(mappedSuperclass);
		this.initializeIdClass(this.idClassResource());
	}
	
	protected void initializeIdClass(XmlIdClass idClassResource) {
		this.idClass = this.idClass(idClassResource);	
	}
	
	@Override
	public void update(XmlMappedSuperclass mappedSuperclass) {
		super.update(mappedSuperclass);
		this.updateIdClass(this.idClassResource());
	}
	
	protected void updateIdClass(XmlIdClass idClassResource) {
		this.setIdClass_(this.idClass(idClassResource));
	}

	protected String idClass(XmlIdClass idClassResource) {
		return idClassResource == null ? null : idClassResource.getClassName();
	}

}
