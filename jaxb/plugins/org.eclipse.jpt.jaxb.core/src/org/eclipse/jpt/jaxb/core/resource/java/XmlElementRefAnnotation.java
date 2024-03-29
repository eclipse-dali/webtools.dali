/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.resource.java;

import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Corresponds to the JAXB annotation
 * javax.xml.bind.annotation.XmlElementRef
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
public interface XmlElementRefAnnotation
		extends QNameAnnotation, NestableAnnotation {
	
	// ***** required *****
	
	/**
	 * String associated with changes to the 'required' property of this annotation.
	 */
	String REQUIRED_PROPERTY = "required"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'required' element of this annotation.
	 * Return null if the element does not exist in Java.
	 */
	Boolean getRequired();
	
	/**
	 * Corresponds to the 'required' element of this annotation.
	 * Set to null to remove the element.
	 */
	void setRequired(Boolean required);
	
	/**
	 * Return the {@link TextRange} for the 'required' element. If the element
	 * does not exist return the {@link TextRange} for the annotation itself.
	 */
	TextRange getRequiredTextRange();
	
	
	// ***** type *****
	
	/**
	 * String associated with changes to the 'type' property of this annotation.
	 */
	String TYPE_PROPERTY = "type"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'type' element of this annotation.
	 * Return null if the element does not exist in Java.
	 * Return the portion of the value preceding ".class".
	 * <pre>
	 *     &#64;XmlElementRef(type=Foo.class)
	 * </pre>
	 * will return "Foo"
	 */
	String getType();
	
	/**
	 * Corresponds to the 'type' element of this annotation.
	 * Set to null to remove the element.
	 */
	void setType(String type);
	
	/**
	 * Return the {@link TextRange} for the 'type' element. If the element 
	 * does not exist return the {@link TextRange} for the annotation itself.
	 */
	TextRange getTypeTextRange();
	
	/**
	 * String associated with changes to the 'fullyQualifiedName' property of this annotation.
	 */
	String FULLY_QUALIFIED_TYPE_NAME_PROPERTY = "fullyQualifiedTypeName"; //$NON-NLS-1$
	
	/**
	 * Return the fully-qualified type name as resolved by the AST's bindings.
	 * <pre>
	 *     &#64;XmlElementRef(type=Foo.class)
	 * </pre>
	 * will return "model.Foo" if there is an import for model.Foo.
	 * @return
	 */
	String getFullyQualifiedTypeName();
}
