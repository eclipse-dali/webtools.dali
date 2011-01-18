/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.core.utility.jdt.AnnotatedElement;

/**
 * Used to build annotations discovered in the Java source code.
 * To provide new <code>AnnotationDefinition</code>s, create a new
 * {@link org.eclipse.jpt.core.JpaPlatform}
 * by implementing {@link org.eclipse.jpt.core.JpaPlatform JpaPlatform}
 * and/or extending
 * {@link org.eclipse.jpt.core.internal.GenericJpaPlatform GenericJpaPlatform}.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see Annotation
 * @see org.eclipse.jpt.core.JpaPlatform
 * @see org.eclipse.jpt.core.internal.GenericJpaPlatform
 * 
 * @version 3.0
 * @since 2.0
 */
public interface AnnotationDefinition
{
	/**
	 * Return the name of the annotation the definition will build in the
	 * various #build...(...) methods.
	 */
	String getAnnotationName();
	
	/**
	 * Build and return an annotation for the specified annotated element.
	 */
	Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement);
	
	/**
	 * Build and return an annotation for the specified JDT annotation
	 * on the specified annotated element.
	 */
	Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation);
	
	/**
	 * Build and return a <em>null</em> annotation for the specified element.
	 * Only certain annotations are required to have <em>null</em> implementations;
	 * typically the annotations with reasonably complex default behavior.
	 * The <em>null</em> annotation is used by the corresponding default context model.
	 * The <em>null</em> annotation simplifies the context model code, allowing the
	 * context model to simply set various bits of state (e.g. <code>name</code>) and the
	 * <em>null</em> annotation will create a new <em>real</em> annotation and forward the
	 * new state to it. This reduces the number of <code>null</code> checks in the context
	 * model (hopefully).
	 */
	Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent);
}
