/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
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


public abstract class AbstractOrmMappedSuperclass extends AbstractOrmTypeMapping<XmlMappedSuperclass>
	implements OrmMappedSuperclass
{
	protected String idClass;
	
	protected AbstractOrmMappedSuperclass(OrmPersistentType parent, XmlMappedSuperclass resourceMapping) {
		super(parent, resourceMapping);
		this.idClass = this.getResourceIdClassName(this.getResourceIdClass());
	}
	
	public JavaMappedSuperclass getJavaMappedSuperclass() {
		JavaPersistentType javaPersistentType = this.getJavaPersistentType();
		if (javaPersistentType != null && javaPersistentType.getMappingKey() == MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY) {
			return (JavaMappedSuperclass) javaPersistentType.getMapping();
		}
		return null;
	}

	/**
	 * This checks metaDataComplete before returning the JavaMappedSuperclass.
	 * As far as defaults are concerned, if metadataComplete is true, the JavaMappedSuperclass is ignored.
	 */
	protected JavaMappedSuperclass getJavaMappedSuperclassForDefaults() {
		if (isMetadataComplete()) {
			return null;
		}
		return getJavaMappedSuperclass();
	}

	
	public String getIdClass() {
		return this.idClass;
	}
	
	public void setIdClass(String newIdClass) {
		String oldIdClass = this.idClass;
		this.idClass = newIdClass;
		if (oldIdClass != newIdClass) {
			if (this.getResourceIdClass() != null) {
				this.getResourceIdClass().setClassName(newIdClass);						
				if (this.getResourceIdClass().isUnset()) {
					removeResourceIdClass();
				}
			}
			else if (newIdClass != null) {
				addResourceIdClass();
				getResourceIdClass().setClassName(newIdClass);
			}
		}
		firePropertyChanged(ID_CLASS_PROPERTY, oldIdClass, newIdClass);
	}
	
	protected void setIdClass_(String newIdClass) {
		String oldIdClass = this.idClass;
		this.idClass = newIdClass;
		firePropertyChanged(ID_CLASS_PROPERTY, oldIdClass, newIdClass);
	}

	protected XmlIdClass getResourceIdClass() {
		return this.resourceTypeMapping.getIdClass();
	}
	
	protected void addResourceIdClass() {
		this.resourceTypeMapping.setIdClass(OrmFactory.eINSTANCE.createXmlIdClass());		
	}
	
	protected void removeResourceIdClass() {
		this.resourceTypeMapping.setIdClass(null);
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
	
	public void addToResourceModel(XmlEntityMappings entityMappings) {
		entityMappings.getMappedSuperclasses().add(this.resourceTypeMapping);
	}
	
	public void removeFromResourceModel(XmlEntityMappings entityMappings) {
		entityMappings.getMappedSuperclasses().remove(this.resourceTypeMapping);
	}
	
	@Override
	public void update() {
		super.update();
		this.setIdClass_(this.getResourceIdClassName(this.getResourceIdClass()));
	}

	protected String getResourceIdClassName(XmlIdClass idClassResource) {
		return idClassResource == null ? null : idClassResource.getClassName();
	}
}
