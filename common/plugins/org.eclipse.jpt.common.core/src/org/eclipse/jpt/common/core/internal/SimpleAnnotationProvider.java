/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.AnnotationProvider;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;

/**
 * Annotation provider constructed with an array of annotation definitions and
 * an array of nestable annotation definitions.
 * <p>
 * An annotation definition is needed if the annotation is only ever used as a
 * stand-alone top-level annotation. A nestable annotation definition is needed
 * if the annotation can be either stand-alone or nested within a container
 * annotation. A definition is not needed if the annotation is only ever nested
 * within another annotation.
 * <p>
 * The platform will build an instance of this annotation provider,
 * passing in the appropriate array of annotation definition providers necessary
 * to build the annotations for the platform (vendor and/or version).
 *
 * @see AnnotationDefinition
 * @see NestableAnnotationDefinition
 */
public final class SimpleAnnotationProvider
	implements AnnotationProvider
{
	private final AnnotationDefinition[] annotationDefinitions;

	private final NestableAnnotationDefinition[] nestableAnnotationDefinitions;

	public SimpleAnnotationProvider(AnnotationDefinition[] annotationDefinitions, NestableAnnotationDefinition[] nestableAnnotationDefinitions) {
		super();
		this.annotationDefinitions = annotationDefinitions;
		this.nestableAnnotationDefinitions = nestableAnnotationDefinitions;
	}


	private Iterable<AnnotationDefinition> getAnnotationDefinitions() {
		return IterableTools.iterable(this.annotationDefinitions);
	}

	private AnnotationDefinition getAnnotationDefinition(String annotationName) {
		for (AnnotationDefinition annotationDefinition : this.annotationDefinitions) {
			if (annotationDefinition.getAnnotationName().equals(annotationName)) {
				return annotationDefinition;
			}
		}
		return null;
	}

	public Iterable<String> getAnnotationNames() {
		return IterableTools.transform(this.getAnnotationDefinitions(), AnnotationDefinition.ANNOTATION_NAME_TRANSFORMER);
	}

	public Iterable<String> getContainerAnnotationNames() {
		return IterableTools.transform(this.getNestableAnnotationDefinitions(), NestableAnnotationDefinition.CONTAINER_ANNOTATION_NAME_TRANSFORMER);
	}

	public Iterable<String> getNestableAnnotationNames() {
		return IterableTools.transform(this.getNestableAnnotationDefinitions(), NestableAnnotationDefinition.NESTABLE_ANNOTATION_NAME_TRANSFORMER);
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element, String annotationName) {
		return this.getAnnotationDefinition(annotationName).buildAnnotation(parent, element);
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return this.getAnnotationDefinition(jdtAnnotation.getElementName()).buildAnnotation(parent, jdtAnnotation);
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation, int index) {
		return this.getNestableAnnotationDefinition(jdtAnnotation.getElementName()).buildAnnotation(parent, jdtAnnotation, index);
	}

	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent, String annotationName) {
		return this.getAnnotationDefinition(annotationName).buildNullAnnotation(parent);
	}

	private Iterable<NestableAnnotationDefinition> getNestableAnnotationDefinitions() {
		return IterableTools.iterable(this.nestableAnnotationDefinitions);
	}

	private NestableAnnotationDefinition getNestableAnnotationDefinition(String annotationName) {
		for (NestableAnnotationDefinition annotationDefinition : this.nestableAnnotationDefinitions) {
			if (annotationDefinition.getNestableAnnotationName().equals(annotationName)) {
				return annotationDefinition;
			}
		}
		return null;
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element, String annotationName, int index) {
		return this.getNestableAnnotationDefinition(annotationName).buildAnnotation(parent, element, index);
	}

	public String getNestableAnnotationName(String containerAnnotationName) {
		return this.getNestableAnnotationDefinitionForContainer(containerAnnotationName).getNestableAnnotationName();
	}

	public String getContainerAnnotationName(String nestableAnnotationName) {
		return this.getNestableAnnotationDefinition(nestableAnnotationName).getContainerAnnotationName();
	}

	public String getNestableElementName(String nestableAnnotationName) {
		return this.getNestableAnnotationDefinition(nestableAnnotationName).getElementName();
	}

	private NestableAnnotationDefinition getNestableAnnotationDefinitionForContainer(String containerAnnotationName) {
		for (NestableAnnotationDefinition nestableAnnotationDefinition : this.nestableAnnotationDefinitions) {
			if (nestableAnnotationDefinition.getContainerAnnotationName().equals(containerAnnotationName)) {
				return nestableAnnotationDefinition;
			}
		}
		return null;
	}
}
