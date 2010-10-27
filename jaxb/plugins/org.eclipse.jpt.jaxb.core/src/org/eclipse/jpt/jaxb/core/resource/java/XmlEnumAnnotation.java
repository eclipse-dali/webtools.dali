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

/**
 * Corresponds to the JAXB annotation
 * javax.xml.bind.annotation.XmlEnum
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
public interface XmlEnumAnnotation
	extends Annotation
{
	String ANNOTATION_NAME = JAXB.XML_ENUM;

	/**
	 * Corresponds to the 'value' element of the XmlEnum annotation.
	 * Return null if the element does not exist in Java.
	 * Return the portion of the value preceding ".class".
	 * <pre>
	 *     &#64;XmlEnum(value=EnumClass.class)
	 * </pre>
	 * will return "EnumClass"
	 */
	String getValue();
		String VALUE_PROPERTY = "value"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'value' element of the XmlEnum annotation.
	 * Set to null to remove the element.
	 */
	void setValue(String value);
	/**
	 * Return the {@link TextRange} for the 'value' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlEnum annotation.
	 */
	TextRange getValueTextRange(CompilationUnit astRoot);

	/**
	 * Return the fully-qualified value class name as resolved by the AST's bindings.
	 * <pre>
	 *     &#64;XmlEnum(value=EnumClass.class)
	 * </pre>
	 * will return "model.EnumClass" if there is an import for model.EnumClass.
	 * @return
	 */
	String getFullyQualifiedValueClassName();
		String FULLY_QUALIFIED_VALUE_CLASS_NAME_PROPERTY = "fullyQualifiedValueClassName"; //$NON-NLS-1$

}
