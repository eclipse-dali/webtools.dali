/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.utility.Filter;

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
public interface JavaResourcePersistentMember
	extends JavaResourceAnnotatedElement
{

	// ********** annotations **********
	
	/**
	 * Set the specified primary annotation as the first annotation and remove
	 * all known persistence annotations (i.e. do not remove non-persistence
	 * annotations) not included in the specified list of supporting annotations.
	 * The specified primary annotation name can be <code>null</code> and, if
	 * the list of supporting annotations is empty, <em>all</em> the persistence
	 * annotations will be removed.
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


	// ********** persistable member filter **********

	Filter<JavaResourcePersistentMember> PERSISTABLE_MEMBER_FILTER =
		new Filter<JavaResourcePersistentMember>() {
			public boolean accept(JavaResourcePersistentMember member) {
				return member.isPersistable();
			}
		};
}
