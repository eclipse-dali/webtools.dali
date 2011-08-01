/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.FetchType;

/**
 * Corresponds to the JPA 2.0 annotation
 * javax.persistence.ElementCollection
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
public interface ElementCollection2_0Annotation 
	extends Annotation
{
	String ANNOTATION_NAME = JPA2_0.ELEMENT_COLLECTION;

	/**
	 * Corresponds to the 'targetClass' element of the element collection
	 * annotation.
	 * Return null if the element does not exist in Java.
	 * Return the portion of the value preceding ".class".
	 * <pre>
	 *     &#64;ElementCollection(targetClass=Employee.class)
	 * </pre>
	 * will return "Employee"
	 */
	String getTargetClass();	
		String TARGET_CLASS_PROPERTY = "targetClass"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'targetClass' element of the element collection
	 * annotation.
	 * Set to null to remove the element.
	 */
	void setTargetClass(String targetClass);
	
	/**
	 * Return the {@link TextRange} for the 'targetClass' element. If the element 
	 * does not exist return the {@link TextRange} for the element collection annotation.
	 */
	TextRange getTargetClassTextRange(CompilationUnit astRoot);

	/**
	 * Return the fully-qualified target class name as resolved by the AST's
	 * bindings.
	 * <pre>
	 *     &#64;ElementCollection(targetClass=Employee.class)
	 * </pre>
	 * will return <code>"model.Employee"</code> if there is an import for
	 * <code>model.Employee</code>.
	 */
	String getFullyQualifiedTargetClassName();


	/**
	 * Corresponds to the 'fetch' element of the element collection annotation.
	 * Return null if the element does not exist in Java.
	 */
	FetchType getFetch();
		String FETCH_PROPERTY = "fetch"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'fetch' element of the element collection annotation.
	 * Set to null to remove the element.
	 */
	void setFetch(FetchType fetch);
	
	/**
	 * Return the {@link TextRange} for the 'fetch' element. If the element 
	 * does not exist return the {@link TextRange} for the element collection annotation.
	 */
	TextRange getFetchTextRange(CompilationUnit astRoot);


}
