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

/**
 * Java source code or binary enum constant
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
public interface JavaResourceEnumConstant
	extends JavaResourceMember
{
	/**
	 * The Java resource enum constant's name does not change.
	 */
	String getName();
	
	/**
	 * Return a null annotation for the specified annotation name.
	 * Return null if the specified annotation name is null.
	 * The corresponding AnnotationDefinition must implement #buildNullAnnotation()
	 * {@link AnnotationDefinition#buildNullAnnotation(JavaResourceMember,
	 * org.eclipse.jpt.core.utility.jdt.Member)}
	 */
	Annotation buildNullAnnotation(String annotationName);

}
