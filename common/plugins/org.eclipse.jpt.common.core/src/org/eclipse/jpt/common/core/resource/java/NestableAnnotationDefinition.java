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
 * Used to build NestableAnnotations discovered in the Java source code.
 * Nestable annotations are part of a container/nestable annotation pair.
 * Use an implementation of AnnotationProvider to provide NestableAnnotationDefinitions
 * If the annotation is only a standalone annotation then use AnnotationDefinition
 * instead.
 * 
 * 
 * TODO provide an example. Somewhere in this api we need to explain how the container/nestable/standalone
 * annotations are return via JavaResourceAnnotationElement.
 * @Container(value = {@NestableAnnotation, @NestableAnnotation})
 * 
 * @see NestableAnnotation
 * @see org.eclipse.jpt.common.core.AnnotationProvider
 * @see AnnotationDefinition
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.0
 */
public interface NestableAnnotationDefinition
{
	/**
	 * Return the name of the annotation the definition will build in the
	 * various #build...(...) methods.
	 */
	String getNestableAnnotationName();
	Transformer<NestableAnnotationDefinition, String> NESTABLE_ANNOTATION_NAME_TRANSFORMER = new NestableAnnotationNameTransformer();
	class NestableAnnotationNameTransformer
		extends TransformerAdapter<NestableAnnotationDefinition, String>
	{
		@Override
		public String transform(NestableAnnotationDefinition def) {
			return def.getNestableAnnotationName();
		}
	}

	/**
	 * Return the name of the container annotation which will be used 
	 * when multiple NestableAnnotations are created
	 */
	String getContainerAnnotationName();
	Transformer<NestableAnnotationDefinition, String> CONTAINER_ANNOTATION_NAME_TRANSFORMER = new ContainerAnnotationNameTransformer();
	class ContainerAnnotationNameTransformer
		extends TransformerAdapter<NestableAnnotationDefinition, String>
	{
		@Override
		public String transform(NestableAnnotationDefinition def) {
			return def.getContainerAnnotationName();
		}
	}

	/**
	 * Return the element name used when the NestableAnnotation is nested
	 * inside of the container annotation. Typically "value".
	 */
	String getElementName();

	/**
	 * Build and return an annotation for the specified annotated element.
	 */
	NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, int index);

	/**
	 * Build and return an annotation for the specified JDT annotation
	 * on the specified annotated element.
	 */
	NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation, int index);
}
