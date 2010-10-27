/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * Java source code or binary persistent member.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 2.0
 */
public interface JavaResourceMember
	extends JavaResourceAnnotatedElement
{

	// ********** annotations **********
	
	/**
	 * Sets the specified primary annotation as the first annotation, and removes all known 
	 * annotations (i.e. does not remove non-persistence annotations) which are not included
	 * in the supporting annotations.
	 */
	Annotation setPrimaryAnnotation(String primaryAnnotationName, Iterable<String> supportingAnnotationNames);


	// ********** queries **********
	
	/**
	 * Return whether the underlying JDT member is persistable according to
	 * the JPA spec.
	 */
	boolean isPersistable();
		String PERSISTABLE_PROPERTY = "persistable"; //$NON-NLS-1$

	/**
	 * Return whether the type is final.
	 */
	boolean isFinal();
		String FINAL_PROPERTY = "final"; //$NON-NLS-1$
	
	/**
	 * Return whether the Java resource persistent member is for the specified
	 * member.
	 */
	boolean isFor(String memberName, int occurrence);


	// ********** behavior **********
	
	/**
	 * Resolve type information that could be dependent on changes elsewhere
	 * in the workspace.
	 */
	void resolveTypes(CompilationUnit astRoot);

}
