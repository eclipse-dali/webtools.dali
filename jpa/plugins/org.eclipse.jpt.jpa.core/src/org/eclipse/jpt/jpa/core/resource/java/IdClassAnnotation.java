/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.java;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * This interface corresponds to the JPA annotation
 * <code>javax.persistence.IdClass</code>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.0
 */
public interface IdClassAnnotation
	extends Annotation
{
	final String ANNOTATION_NAME = JPA.ID_CLASS;

	/**
	 * Corresponds to the 'value' element of the IdClass annotation.
	 * Return null if the element does not exist in Java.
	 * Return the portion of the value preceding ".class".
	 * <pre>
	 *     &#64;IdClass(value=Employee.class)
	 * </pre>
	 * will return "Employee"
	 */
	String getValue();
		String VALUE_PROPERTY = "value"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'value' element of the IdClass annotation.
	 * Set to null to remove the element.
	 */
	void setValue(String value);

	/**
	 * Return the {@link TextRange} for the 'value' element. If the element 
	 * does not exist return the {@link TextRange} for the IdClass annotation.
	 */
	TextRange getValueTextRange();

	/**
	 * Return the fully-qualified ID class name as resolved by the
	 * AST's bindings.
	 * <pre>
	 *     &#64;IdClass(Employee.class)
	 * </pre>
	 * will return <code>"model.Employee"</code> if there is an import for
	 * <code>model.Employee</code>.
	 */
	String getFullyQualifiedClassName();
}
