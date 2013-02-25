/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.resource.java;

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
	extends JavaResourceModel
{

	/**
	 * Initialize the [source] node from the specified AST.
	 */
	//TODO potentially remove this, or leave it for backwards compatibility for adopters
	void initialize(CompilationUnit astRoot);

	/**
	 * Synchronize the [source] node with the specified AST.
	 */
	//TODO potentially remove this, or leave it for backwards compatibility for adopters
	void synchronizeWith(CompilationUnit astRoot);

	/**
	 * Initialize the [source] node from the specified AST annotation.
	 */
	void initialize(org.eclipse.jdt.core.dom.Annotation astAnnotation);

	/**
	 * Synchronize the [source] node with the specified AST annotation.
	 */
	void synchronizeWith(org.eclipse.jdt.core.dom.Annotation astAnnotation);

	/**
	 * Return the annotation's fully qualified name, as opposed to the value of
	 * the annotation's 'name' element. For example:
	 *     @com.foo.Bar(name="Thomas")
	 * #getAnnotationName() will return "com.foo.Bar".
	 * In typical subclasses, #getName() would return "Thomas".
	 */
	String getAnnotationName();

	/**
	 * Return the corresponding JDT DOM annotation from the specified
	 * AST compilation unit.
	 */
	org.eclipse.jdt.core.dom.Annotation getAstAnnotation(CompilationUnit astRoot);

	/**
	 * Create and add the corresponding Java annotation to the JDT DOM.
	 * <br>
	 * Internal to the java resource model.
	 * 
	 * @see JavaResourceAnnotatedElement#addAnnotation(String)
	 */
	//TODO have a separate interface so that the context model does not have access to this method
	void newAnnotation();

	/**
	 * Remove the corresponding Java annotation from the JDT DOM.
	 * <br>
	 * Internal to the java resource model.
	 * @see JavaResourceAnnotatedElement#removeAnnotation(String)
	 */
	//TODO have a separate interface so that the context model does not have access to this method
	void removeAnnotation();

	/**
	 * Return whether all the annotation's member values are <code>null</code>;
	 * implying the annotation can be removed if it has no semantic value as a
	 * marker annotation.
	 */
	boolean isUnset();
}
