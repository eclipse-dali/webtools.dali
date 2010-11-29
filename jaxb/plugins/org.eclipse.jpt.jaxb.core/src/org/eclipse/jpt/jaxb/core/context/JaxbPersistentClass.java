/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;

/**
 * Represents a JAXB persistent class.  
 * (A class with either an explicit or implicit @XmlType annotation)
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface JaxbPersistentClass
		extends JaxbType, XmlAccessTypeHolder, XmlAccessOrderHolder {
	
	JaxbPersistentClass getSuperPersistentClass();
		String SUPER_PERSISTENT_CLASS_PROPERTY = "superPersistentClass"; //$NON-NLS-1$

	/**
	 * Return the persistent type's "persistence" inheritance hierarchy,
	 * <em>including</em> the persistent type itself.
	 * The returned iterator will return elements infinitely if the hierarchy
	 * has a loop.
	 */
	Iterable<JaxbPersistentClass> getInheritanceHierarchy();

	/**
	 * Return the persistent type's "persistence" inheritance hierarchy,
	 * <em>excluding</em> the persistent type itself.
	 * The returned iterator will return elements infinitely if the hierarchy
	 * has a loop.
	 */
	Iterable<JaxbPersistentClass> getAncestors();


	/**************** factory class *****************/

	/**
	 * factory class corresponds to the XmlType annotation factoryClass element
	 */
	String getFactoryClass();
	void setFactoryClass(String factoryClass);
		String FACTORY_CLASS_PROPERTY = "factoryClass"; //$NON-NLS-1$


	/**************** factory method *****************/

	/**
	 * factory method corresponds to the XmlType annotation factoryMethod element
	 */
	String getFactoryMethod();
	void setFactoryMethod(String factoryMethod);
		String FACTORY_METHOD_PROPERTY = "factoryMethod"; //$NON-NLS-1$


	/**************** name *****************/

	/**
	 * Schema type name corresponds to the XmlType annotation name element
	 */
	String getSchemaTypeName();
	void setSchemaTypeName(String schemaTypeName);
		String SCHEMA_TYPE_NAME_PROPERTY = "schemaTypeName"; //$NON-NLS-1$


	/**************** namespace *****************/

	/**
	 * namespace corresponds to the XmlType annotation namespace element
	 */
	String getNamespace();
	void setNamespace(String namespace);
		String NAMESPACE_PROPERTY = "namespace"; //$NON-NLS-1$


	// ********** propOrder **********

	ListIterable<String> getPropOrder();
	int getPropOrderSize();
	void addProp(int index, String prop);
	void removeProp(int index);
	void removeProp(String prop);
	void moveProp(int targetIndex, int sourceIndex);
		String PROP_ORDER_LIST = "propOrder"; //$NON-NLS-1$


	// ********** root element ************
	/**
	 * Return whether this class is a root element (has the XmlRootElement annotation)
	 */
	boolean isRootElement();

	/**
	 * Return the root element or null if it is not a root element.
	 */
	XmlRootElement getRootElement();

	/**
	 * Set the root element name, this will add the XmlRootElement annotation
	 * and set its name to the specified name.
	 * To remove the XmlRootElement annotation, pass in null.
	 * To set the name when the class is already a root element,
	 * set it directly on the XmlRootElement.
	 */
	XmlRootElement setRootElement(String name);
		String ROOT_ELEMENT = "rootElement"; //$NON-NLS-1$

}
