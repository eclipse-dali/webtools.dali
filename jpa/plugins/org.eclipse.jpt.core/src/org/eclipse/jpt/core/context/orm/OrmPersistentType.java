/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.java.JavaPersistentType;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmPersistentType
	extends PersistentType, PersistentType.Owner, XmlContextNode
{
	/**
	 * covariant override
	 */
	EntityMappings getParent();

	/**
	 * "covariant" override
	 */
	@SuppressWarnings("unchecked")
	ListIterator<OrmPersistentAttribute> attributes();
	
	/**
	 * covariant override
	 */
	OrmPersistentAttribute getAttributeNamed(String attributeName);

	/**
	 * covariant override
	 */
	OrmTypeMapping getMapping();
	
	
	//***************** specified attributes ***********************************
	
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
	
	
	//***************** virtual attributes *************************************
	
	String VIRTUAL_ATTRIBUTES_LIST = "virtualAttributes"; //$NON-NLS-1$

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
	 * Remove the given specified orm persistent attribute from the orm.xml. The attribute 
	 * will be removed from the orm.xml and moved from the list of specified attributes 
	 * to the list of virtual attributes.
	 */
	void makePersistentAttributeVirtual(OrmPersistentAttribute ormPersistentAttribute);
		
	/**
	 * Add the given virtual orm persistent attribute to the orm.xml. The attribute will
	 * be added to the orm.xml and moved from the list of virtual attributes to the list
	 * of specified attributes
	 */
	void makePersistentAttributeSpecified(OrmPersistentAttribute ormPersistentAttribute);

	/**
	 * Add the given virtual orm persistent attribute to the orm.xml with a mapping of 
	 * type mappingKey. The attribute will be added to the orm.xml and moved from 
	 * the list of virtual attributes to the list of specified attributes
	 */
	void makePersistentAttributeSpecified(OrmPersistentAttribute ormPersistentAttribute, String mappingKey);


	//******************* mapping morphing *******************

	void changeMapping(OrmPersistentAttribute ormPersistentAttribute, OrmAttributeMapping oldMapping, OrmAttributeMapping newMapping);
	
	
	//******************* updating *******************

	/**
	 * Update the OrmPersistentType context model object to match the 
	 * resource model object. see {@link org.eclipse.jpt.core.JpaProject#update()}
	 */
	void update();
	

	boolean contains(int textOffset);
	
	/**
	 * Return whether the persistent type applies to the
	 * specified type.
	 */
	boolean isFor(String typeName);
	
	void classChanged(String oldClass, String newClass);
	
	/**
	 * Return the Java persistent type that is referred to by this orm.xml persistent type.
	 * If there is no underlying java persistent type, then null is returned.
	 * @return
	 */
	JavaPersistentType getJavaPersistentType();
		String JAVA_PERSISTENT_TYPE_PROPERTY = "javaPersistentType"; //$NON-NLS-1$

	/**
	 * Return the persistent type's default package.
	 */
	String getDefaultPackage();

	/**
	 * Return whether the persistent type is default metadata complete.
	 */
	boolean isDefaultMetadataComplete();

}
