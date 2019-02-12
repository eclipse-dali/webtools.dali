/*******************************************************************************
 *  Copyright (c) 2010, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.resource.java;

import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Corresponds to the JAXB annotation
 * javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter
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
public interface XmlJavaTypeAdapterAnnotation
		extends NestableAnnotation {
	
	/**
	 * Corresponds to the 'value' element of the XmlJavaTypeAdapter annotation.
	 * Return null if the element does not exist in Java.
	 * Return the portion of the value preceding ".class".
	 * <pre>
	 *     &#64;XmlJavaTypeAdapter(value=FooAdapter.class)
	 * </pre>
	 * will return "FooAdapter"
	 */
	String getValue();
		String VALUE_PROPERTY = "value"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'value' element of the XmlJavaTypeAdapter annotation.
	 * Set to null to remove the element.
	 */
	void setValue(String value);
	
	/**
	 * Return the {@link TextRange} for the 'value' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlJavaTypeAdapter annotation.
	 */
	TextRange getValueTextRange();
	
	/**
	 * Return the value's fully-qualified class name as resolved by the AST's bindings.
	 * <pre>
	 *     &#64;XmlJavaTypeAdapter(FooAdapter.class)
	 * </pre>
	 * will return "example.FooAdapter" if there is an import for example.FooAdapter.
	 */
	String getFullyQualifiedValue();
		String FULLY_QUALIFIED_VALUE_PROPERTY = "fullyQualifiedValue"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'type' element of the XmlJavaTypeAdapter annotation.
	 * Return null if the element does not exist in Java.
	 * Return the portion of the value preceding ".class".
	 * <pre>
	 *     &#64;XmlJavaTypeAdapter(type=Foo.class)
	 * </pre>
	 * will return "Foo"
	 */
	String getType();
		String TYPE_PROPERTY = "type"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'type' element of the XmlJavaTypeAdapter annotation.
	 * Set to null to remove the element.
	 */
	void setType(String type);
	
	/**
	 * Return the {@link TextRange} for the 'type' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlJavaTypeAdapter annotation.
	 */
	TextRange getTypeTextRange();
	
	/**
	 * Return the type's fully-qualified class name as resolved by the AST's bindings.
	 * <pre>
	 *     &#64;XmlJavaTypeAdapter(type=Foo.class)
	 * </pre>
	 * will return "example.Foo" if there is an import for example.Foo.
	 */
	String getFullyQualifiedType();
		String FULLY_QUALIFIED_TYPE_PROPERTY = "fullyQualifiedType"; //$NON-NLS-1$
}
