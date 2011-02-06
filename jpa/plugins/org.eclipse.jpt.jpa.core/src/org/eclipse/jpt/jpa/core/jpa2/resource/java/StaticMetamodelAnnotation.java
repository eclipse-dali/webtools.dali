/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.resource.java;

import org.eclipse.jpt.jpa.core.resource.java.Annotation;

/**
 * Corresponds to the JPA 2.0 annotation
 * <code>javax.persistence.metamodel.StaticMetamodel</code>
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
public interface StaticMetamodelAnnotation
	extends Annotation
{
	String ANNOTATION_NAME = JPA2_0.STATIC_METAMODEL;

	/**
	 * Corresponds to the <code>value</code> element of the <code>StaticMetamodel</code> annotation.
	 * Return <code>null</code> if the element does not exist in Java.
	 * Return the portion of the value preceding <code>".class"</code>.
	 * <pre>
	 *     &#64;StaticMetamodel(value=Employee.class)
	 * </pre>
	 * will return <code>"Employee"</code>.
	 */
	String getValue();
		String VALUE_PROPERTY = "value"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the <code>value</code> element of the <code>StaticMetamodel</code> annotation.
	 * Set to <code>null</code> to remove the element.
	 */
	void setValue(String value);
	
	/**
	 * Return the static metamodel's fully-qualified class name as resolved by
	 * the AST's bindings.
	 * <pre>
	 *     &#64;StaticMetamodel(Employee.class)
	 * </pre>
	 * will return <code>"model.Employee"</code> if there is an import for
	 * <code>model.Employee</code>.
	 */
	String getFullyQualifiedClassName();
}
