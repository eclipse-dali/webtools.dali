/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;

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
 * @version 3.0
 * @since 3.0
 */
public interface XmlTypeAnnotation
		extends Annotation {
	
	String ANNOTATION_NAME = JAXB.XML_TYPE;

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
		String FACTORY_CLASS_PROPERTY = "factoryClass"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'factoryClass' element of the XmlType annotation.
	 * Set to null to remove the element.
	 */
	void setFactoryClass(String factoryClass);

	/**
	 * Return the {@link TextRange} for the 'factoryClass' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlType annotation.
	 */
	TextRange getFactoryClassTextRange(CompilationUnit astRoot);

	/**
	 * Return the fully-qualified factory class name as resolved by the AST's bindings.
	 * <pre>
	 *     &#64;XmlType(name="USAddressType", factoryClass=USAddressFactory.class)
	 * </pre>
	 * will return "model.USAddressFactory" if there is an import for model.USAddressFactory.
	 * @return
	 */
	String getFullyQualifiedFactoryClassName();
		String FULLY_QUALIFIED_FACTORY_CLASS_NAME_PROPERTY = "fullyQualifiedFactoryClassName"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'factoryMethod' element of the XmlType annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getFactoryMethod();
		String FACTORY_METHOD_PROPERTY = "factoryMethod"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'factoryMethod' element of the XmlType annotation.
	 * Set to null to remove the element.
	 */
	void setFactoryMethod(String factoryMethod);

	/**
	 * Return the {@link TextRange} for the 'factoryMethod' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlType annotation.
	 */
	TextRange getFactoryMethodTextRange(CompilationUnit astRoot);

	/**
	 * Corresponds to the 'name' element of the XmlType annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getName();
		String NAME_PROPERTY = "name"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'name' element of the XmlType annotation.
	 * Set to null to remove the element.
	 */
	void setName(String name);

	/**
	 * Return the {@link TextRange} for the 'name' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlType annotation.
	 */
	TextRange getNameTextRange(CompilationUnit astRoot);
	
	/**
	 * Return whether the specified position touches the 'name' element.
	 * Return false if the element does not exist.
	 */
	boolean nameTouches(int pos, CompilationUnit astRoot);
	
	/**
	 * Corresponds to the 'namespace' element of the XmlType annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getNamespace();
		String NAMESPACE_PROPERTY = "namespace"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'namespace' element of the XmlType annotation.
	 * Set to null to remove the element.
	 */
	void setNamespace(String namespace);

	/**
	 * Return the {@link TextRange} for the 'namespace' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlType annotation.
	 */
	TextRange getNamespaceTextRange(CompilationUnit astRoot);
	
	/**
	 * Return whether the specified position touches the 'namespace' element.
	 * Return false if the element does not exist.
	 */
	boolean namespaceTouches(int pos, CompilationUnit astRoot);
	
	/**
	 * Corresponds to the 'propOrder' element of the XmlType annotation.
	 * Return an empty iterator if the element does not exist in Java.
	 */
	ListIterable<String> getPropOrder();
		String PROP_ORDER_LIST = "propOrder"; //$NON-NLS-1$
	
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

}
