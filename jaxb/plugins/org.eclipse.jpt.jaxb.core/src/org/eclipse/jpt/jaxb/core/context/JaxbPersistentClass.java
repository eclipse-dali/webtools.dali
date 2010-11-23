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
		extends JaxbContextNode, XmlAccessTypeHolder, XmlAccessOrderHolder {
	
	/**
	 * Returns the fully qualified name of this class,
	 * including qualification for any containing types and packages.
	 */
	String getFullyQualifiedName();
	
	/**
	 * Return the name of the class' package.  Empty string if none.
	 */
	String getPackageName();
	
	/**
	 * Returns the type-qualified name of this type,
	 * including qualification for any enclosing types,
	 * but not including package qualification.
	 */
	String getTypeQualifiedName();
	
	/**
	 * Return the name of the class without any package or type qualifiers
	 */
	String getSimpleName();
	
	JavaResourceType getJaxbResourceType();

	JaxbPersistentClass getSuperPersistentClass();
		String SUPER_PERSISTENT_CLASS_PROPERTY = "superPersistentClass"; //$NON-NLS-1$


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
}
