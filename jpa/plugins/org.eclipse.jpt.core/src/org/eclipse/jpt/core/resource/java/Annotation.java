/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * Common Java resource annotation behavior
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface Annotation
	extends JavaResourceNode
{
	/**
	 * Return the annotation's fully qualified name, as opposed to the value of
	 * the annotation's 'name' element. For example:
	 *     @com.foo.Bar(name="Thomas")
	 * #getAnnotationName() will return "com.foo.Bar".
	 * In typical subclasses, #getName() would return "Thomas".
	 * @see JPA
	 */
	String getAnnotationName();

	/**
	 * Return the corresponding JDT DOM annotation from the specified
	 * AST compilation unit.
	 */
	org.eclipse.jdt.core.dom.Annotation getJdtAnnotation(CompilationUnit astRoot);

	/**
	 * Create and add the corresponding Java annotation to the JDT DOM.
	 */
	void newAnnotation();

	/**
	 * Remove the corresponding Java annotation from the JDT DOM.
	 */
	void removeAnnotation();

}
