/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Corresponds to the JPA 2.0 annotation
 * javax.persistence.MapKeyClass
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface MapKeyClass2_0Annotation 
	extends Annotation
{
	String ANNOTATION_NAME = JPA2_0.MAP_KEY_CLASS;

	/**
	 * Corresponds to the 'value' element of the MapKeyClass annotation.
	 * Return null if the element does not exist in Java.
	 * Return the portion of the value preceding ".class".
	 * <pre>
	 *     &#64;MapKeyClass(value=Employee.class)
	 * </pre>
	 * will return "Employee"
	 */
	String getValue();
		String VALUE_PROPERTY = "value"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'value' element of the MapKeyClass annotation.
	 * Set to null to remove the element.
	 * This will also remove the MapKeyClass annotation itself.
	 */
	void setValue(String value);

	/**
	 * Return the {@link TextRange} for the 'value' element. If the element 
	 * does not exist return the {@link TextRange} for the IdClass annotation.
	 */
	TextRange getValueTextRange(CompilationUnit astRoot);

	/**
	 * Return the fully-qualified MapKeyClass name as resolved by the AST's bindings.
	 * <pre>
	 *     &#64;IdClass(Employee.class)
	 * </pre>
	 * will return "model.Employee" if there is an import for model.Employee.
	 */
	String getFullyQualifiedClassName();
		String FULLY_QUALIFIED_CLASS_NAME_PROPERTY = "fullyQualifiedClassName"; //$NON-NLS-1$

}
