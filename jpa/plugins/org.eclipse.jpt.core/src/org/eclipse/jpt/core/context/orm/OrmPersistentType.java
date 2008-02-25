/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.core.resource.orm.AbstractTypeMapping;

public interface OrmPersistentType extends PersistentType
{
	String VIRTUAL_ATTRIBUTES_LIST = "virtualAttributesList";
		
	@SuppressWarnings("unchecked")
	ListIterator<OrmPersistentAttribute> attributes();
	
	OrmPersistentAttribute attributeNamed(String attributeName);

	OrmPersistentAttribute addSpecifiedPersistentAttribute(String mappingKey, String attributeName);

	void removeSpecifiedXmlPersistentAttribute(OrmPersistentAttribute xmlPersistentAttribute);

	ListIterator<OrmPersistentAttribute> specifiedAttributes();
	
	int specifiedAttributesSize();
	
	ListIterator<OrmPersistentAttribute> virtualAttributes();
	
	int virtualAttributesSize();
	
	OrmTypeMapping<? extends AbstractTypeMapping> getMapping();
	
	void initialize(XmlEntity entity);
	
	void initialize(XmlMappedSuperclass mappedSuperclass);
		
	void initialize(XmlEmbeddable embeddable);
	
	void update(XmlEntity entity);
	
	void update(XmlMappedSuperclass mappedSuperclass);
	
	void update(XmlEmbeddable embeddable);
	
	boolean containsOffset(int textOffset);
	
	boolean isFor(String fullyQualifiedTypeName);
	
	void classChanged(String oldClass, String newClass);
	
	void setPersistentAttributeVirtual(OrmPersistentAttribute xmlPersistentAttribute, boolean virtual);
	
	public JavaPersistentType javaPersistentType();

}
