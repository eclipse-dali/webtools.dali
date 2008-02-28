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
import org.eclipse.jpt.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmPersistentType extends PersistentType, OrmJpaContextNode
{
	/**
	 * Overriden to return {@link OrmPersistentAttribute}s
	 */
	@SuppressWarnings("unchecked")
	ListIterator<OrmPersistentAttribute> attributes();
	
	/**
	 * Overriden to return an {@link OrmPersistentAttribute}
	 */
	OrmPersistentAttribute attributeNamed(String attributeName);

	/**
	 * Overriden to return an {@link OrmTypeMapping}
	 */
	OrmTypeMapping getMapping();

	//******************* specified attributes *******************

	/**
	 * Return a read only iterator of the specified {@link OrmPersistentAttribute}s.
	 */
	ListIterator<OrmPersistentAttribute> specifiedAttributes();
	
	/**
	 * Return the number of specified {@link OrmPersistentAttribute}s.
	 */
	int specifiedAttributesSize();

	//TODO these are currently only used by tests, possibly remove them.  OrmPersistenAttributes.setVirtual(boolean) is used by the UI
	OrmPersistentAttribute addSpecifiedPersistentAttribute(String mappingKey, String attributeName);
	void removeSpecifiedPersistentAttribute(OrmPersistentAttribute ormPersistentAttribute);

	
	//******************* vritual attributes *******************
	String VIRTUAL_ATTRIBUTES_LIST = "virtualAttributesList";

	/**
	 * Return a read only iterator of the virtual orm persistent attributes.  These
	 * are attributes that exist in the underyling java class, but are not specified
	 * in the orm.xml
	 */
	ListIterator<OrmPersistentAttribute> virtualAttributes();
	
	/**
	 * Return the number of virtual orm persistent attributes.  These are attributes that 
	 * exist in the underyling java class, but are not specified in the orm.xml
	 */
	int virtualAttributesSize();
	
	/**
	 * Return whether this persistent type contains the given virtual persistent attribute.
	 */
	boolean containsVirtualPersistentAttribute(OrmPersistentAttribute ormPersistentAttribute);
	
	/**
	 * Add/Remove the given orm persistent attribute to/from the orm.xml. If the virtual
	 * flag is set to true, the attribute will be removed from the orm.xml and moved from the
	 * list of specifed attributes to the list of virtual attributes.
	 */
	void setPersistentAttributeVirtual(OrmPersistentAttribute ormPersistentAttribute, boolean virtual);
		
	//******************* mapping morphing *******************
	void changeMapping(OrmPersistentAttribute ormPersistentAttribute, OrmAttributeMapping oldMapping, OrmAttributeMapping newMapping);
	
	
	//******************* initialization/updating *******************

	void initialize(XmlEntity entity);
	
	void initialize(XmlMappedSuperclass mappedSuperclass);
		
	void initialize(XmlEmbeddable embeddable);
	
	void update(XmlEntity entity);
	
	void update(XmlMappedSuperclass mappedSuperclass);
	
	void update(XmlEmbeddable embeddable);

	
	
	boolean contains(int textOffset);
	
	/**
	 * Return whether this {@link OrmPersistentType) applies to the
	 * given fullyQualifiedTypeName.
	 */
	boolean isFor(String fullyQualifiedTypeName);
	
	void classChanged(String oldClass, String newClass);
	
	/**
	 * Return the Java persistent type that is referred to by this orm.xml persistent type.
	 * If there is no underlying java persistent type, then null is returned.
	 * @return
	 */
	JavaPersistentType javaPersistentType();

}
