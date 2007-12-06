/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.context.base.IMappedSuperclass;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.ITable;
import org.eclipse.jpt.core.internal.resource.orm.EntityMappings;
import org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;


public class XmlMappedSuperclass extends XmlTypeMapping<MappedSuperclass>
	implements IMappedSuperclass
{
//	protected String idClass;
//
//	protected XmlIdClass idClassForXml;
	
	
	public XmlMappedSuperclass(XmlPersistentType parent) {
		super(parent);
	}

//
//	protected void notifyChanged(Notification notification) {
//		switch (notification.getFeatureID(IMappedSuperclass.class)) {
//			case JpaCoreMappingsPackage.IMAPPED_SUPERCLASS__ID_CLASS :
//				idClassChanged();
//				break;
//			default :
//				break;
//		}
//		switch (notification.getFeatureID(XmlMappedSuperclass.class)) {
//			case OrmPackage.XML_MAPPED_SUPERCLASS__ID_CLASS_FOR_XML :
//				xmlIdClassChanged();
//				break;
//			default :
//				break;
//		}
//	}
//
//	protected void idClassChanged() {
//		if (getIdClass() == null) {
//			setIdClassForXml(null);
//		}
//		else {
//			if (getIdClassForXml() == null) {
//				setIdClassForXml(OrmFactory.eINSTANCE.createXmlIdClass());
//			}
//			getIdClassForXml().setValue(getIdClass());
//		}
//	}
//
//	protected void xmlIdClassChanged() {
//		if (getIdClassForXml() == null) {
//			setIdClass(null);
//		}
//	}
//
//	public String getIdClass() {
//		return idClass;
//	}
//
//	public void setIdClass(String newIdClass) {
//		String oldIdClass = idClass;
//		idClass = newIdClass;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MAPPED_SUPERCLASS__ID_CLASS, oldIdClass, idClass));
//	}
//
//	public XmlIdClass getIdClassForXml() {
//		return idClassForXml;
//	}


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

	public Iterator<String> overridableAttributeNames() {
		return this.namesOf(this.overridableAttributes());
	}

	public Iterator<IPersistentAttribute> overridableAttributes() {
		return new FilteringIterator<IPersistentAttribute>(this.persistentType().attributes()) {
			@Override
			protected boolean accept(Object o) {
				return ((IPersistentAttribute) o).isOverridableAttribute();
			}
		};
	}

	public Iterator<String> overridableAssociationNames() {
		return this.namesOf(this.overridableAssociations());
	}

	public Iterator<IPersistentAttribute> overridableAssociations() {
		return new FilteringIterator<IPersistentAttribute>(this.persistentType().attributes()) {
			@Override
			protected boolean accept(Object o) {
				return ((IPersistentAttribute) o).isOverridableAssociation();
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
	
	@Override
	public void initialize(MappedSuperclass mappedSuperclass) {
		super.initialize(mappedSuperclass);
	}
	
	@Override
	public void update(MappedSuperclass mappedSuperclass) {
		super.update(mappedSuperclass);
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
		entityMappings.getMappedSuperclasses().add(mappedSuperclass);
		return mappedSuperclass;
	}
}
