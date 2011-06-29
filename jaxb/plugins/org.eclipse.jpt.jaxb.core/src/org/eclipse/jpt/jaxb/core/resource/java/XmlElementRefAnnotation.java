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
 * @version 3.0
 * @since 3.0
 */
public interface XmlElementRefAnnotation
		extends SchemaComponentRefAnnotation, NestableAnnotation {
	
	String ANNOTATION_NAME = JAXB.XML_ELEMENT_REF;
	
	/**
	 * Corresponds to the 'type' element of the XmlElementRef annotation.
	 * Return null if the element does not exist in Java.
	 * Return the portion of the value preceding ".class".
	 * <pre>
	 *     &#64;XmlElementRef(type=Foo.class)
	 * </pre>
	 * will return "Foo"
	 */
	String getType();
		String TYPE_PROPERTY = "type"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'type' element of the XmlElementRef annotation.
	 * Set to null to remove the element.
	 */
	void setType(String type);
	/**
	 * Return the {@link TextRange} for the 'type' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlElementRef annotation.
	 */
	TextRange getTypeTextRange(CompilationUnit astRoot);

	/**
	 * Return the fully-qualified type name as resolved by the AST's bindings.
	 * <pre>
	 *     &#64;XmlElementRef(type=Foo.class)
	 * </pre>
	 * will return "model.Foo" if there is an import for model.Foo.
	 * @return
	 */
	String getFullyQualifiedTypeName();
		String FULLY_QUALIFIED_TYPE_NAME_PROPERTY = "fullyQualifiedTypeName"; //$NON-NLS-1$
}
