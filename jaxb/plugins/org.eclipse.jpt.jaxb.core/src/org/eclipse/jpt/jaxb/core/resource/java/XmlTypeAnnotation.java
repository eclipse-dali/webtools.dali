/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.resource.java;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;

/**
 * Corresponds to the JAXB annotation
 * javax.xml.bind.annotation.XmlType
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.0
 */
public interface XmlTypeAnnotation
		extends QNameAnnotation {
	
	// ***** factory class *****
	
	String FACTORY_CLASS_PROPERTY = "factoryClass"; //$NON-NLS-1$
	
	String DEFAULT_FACTORY_CLASS = JAXB.XML_TYPE__DEFAULT_FACTORY_CLASS;
	
	/**
	 * Corresponds to the 'factoryClass' element of the XmlType annotation.
	 * Return null if the element does not exist in Java.
	 * Return the portion of the value preceding ".class".
	 * <pre>
	 *     &#64;XmlType(name="USAddressType", factoryClass=USAddressFactory.class)
	 * </pre>
	 * will return "USAddressFactory"
	 */
	String getFactoryClass();
	
	/**
	 * Corresponds to the 'factoryClass' element of the XmlType annotation.
	 * Set to null to remove the element.
	 */
	void setFactoryClass(String factoryClass);
	
	/**
	 * Return the {@link TextRange} for the 'factoryClass' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlType annotation.
	 */
	TextRange getFactoryClassTextRange();
	
	String FULLY_QUALIFIED_FACTORY_CLASS_NAME_PROPERTY = "fullyQualifiedFactoryClassName"; //$NON-NLS-1$
	
	/**
	 * Return the fully-qualified factory class name as resolved by the AST's bindings.
	 * <pre>
	 *     &#64;XmlType(name="USAddressType", factoryClass=USAddressFactory.class)
	 * </pre>
	 * will return "model.USAddressFactory" if there is an import for model.USAddressFactory.
	 * @return
	 */
	String getFullyQualifiedFactoryClassName();
	
	
	// ***** factory method *****
	
	String FACTORY_METHOD_PROPERTY = "factoryMethod"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'factoryMethod' element of the XmlType annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getFactoryMethod();
	
	/**
	 * Corresponds to the 'factoryMethod' element of the XmlType annotation.
	 * Set to null to remove the element.
	 */
	void setFactoryMethod(String factoryMethod);
	
	/**
	 * Return the {@link TextRange} for the 'factoryMethod' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlType annotation.
	 */
	TextRange getFactoryMethodTextRange();
	
	
	// ***** prop order *****
	
	String PROP_ORDER_LIST = "propOrder"; //$NON-NLS-1$
	
	/**
	 * A single element empty string array should be interpreted as unspecified
	 */
	String[] DEFAULT_PROP_ORDER = new String[] { "" };
	
	/**
	 * Corresponds to the 'propOrder' element of the XmlType annotation.
	 * Return an empty iterator if the element does not exist in Java.
	 */
	ListIterable<String> getPropOrder();
	
	/**
	 * Corresponds to the 'propOrder' element of the XmlType annotation.
	 */
	int getPropOrderSize();
	
	/**
	 * Corresponds to the 'propOrder' element of the XmlType annotation.
	 */
	void addProp(String prop);
	
	/**
	 * Corresponds to the 'propOrder' element of the XmlType annotation.
	 */
	void addProp(int index, String prop);
	
	/**
	 * Corresponds to the 'propOrder' element of the XmlType annotation.
	 */
	void moveProp(int targetIndex, int sourceIndex);
	
	/**
	 * Corresponds to the 'propOrder' element of the XmlType annotation.
	 */
	void removeProp(String prop);
	
	/**
	 * Corresponds to the 'propOrder' element of the XmlType annotation.
	 */
	void removeProp(int index);
	
	/**
	 * Return the text range of the 'propOrder' element of the XmlType annotation.
	 */
	TextRange getPropOrderTextRange();
	
	/**
	 * Return whether the specified position touches the 'propOrder' element.
	 * Return false if the element does not exist.
	 */
	boolean propOrderTouches(int pos);
	
	/**
	 * Return the text range of the prop at the specified index
	 * @throws ArrayIndexOutOfBoundsException if the index is out of range
	 */
	TextRange getPropTextRange(int index);
	
	/**
	 * Return whether the specified position touches the prop at the specified index.
	 * @throws ArrayIndexOutOfBoundsException if the index is out of range
	 */
	boolean propTouches(int index, int pos);
}
