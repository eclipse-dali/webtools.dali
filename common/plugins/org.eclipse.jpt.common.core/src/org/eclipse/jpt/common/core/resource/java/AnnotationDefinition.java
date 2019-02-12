/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Used to build Annotations discovered in the Java source code.
 * Use an implementation of AnnotationProvider to provide AnnotationDefinitions.
 * If the annotation is part of a container/nestable annotation pair
 * use NestableAnnotationDefinition instead.
 * 
 * @see Annotation
 * @see org.eclipse.jpt.common.core.AnnotationProvider
 * @see NestableAnnotationDefinition
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface AnnotationDefinition
{
	/**
	 * Return the name of the annotation the definition will build in the
	 * various #build...(...) methods.
	 */
	String getAnnotationName();
	Transformer<AnnotationDefinition, String> ANNOTATION_NAME_TRANSFORMER = new AnnotationNameTransformer();
	class AnnotationNameTransformer
		extends TransformerAdapter<AnnotationDefinition, String>
	{
		@Override
		public String transform(AnnotationDefinition def) {
			return def.getAnnotationName();
		}
	}
	
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
	 * Build and return a "null" annotation for the specified annotated element.
	 * Only certain annotations are required to have "null" implementations;
	 * typically the annotations with reasonably complex default behavior.
	 * The "null" annotation is used by the corresponding default context model.
	 * The "null" annotation simplifies the context model code, allowing the
	 * context model to simply set various bits of state (e.g. 'name') and the
	 * "null" annotation will create a new "real" annotation and forward the
	 * new state to it. This reduces the number of null checks in the context
	 * model (hopefully).
	 */
	Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent);

}
