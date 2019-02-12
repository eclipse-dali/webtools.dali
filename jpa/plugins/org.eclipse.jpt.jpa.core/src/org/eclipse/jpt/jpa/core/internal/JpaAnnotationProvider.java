/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.AnnotationProvider;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.JpaAnnotationDefinitionProvider;

/**
 * Delegate to annotation definition providers.
 * The platform factory will build an instance of this annotation provider,
 * passing in the appropriate array of annotation definition providers necessary
 * to build the annotations for the platform (vendor and/or version).
 */
public class JpaAnnotationProvider
	implements AnnotationProvider
{
	private final Iterable<JpaAnnotationDefinitionProvider> annotationDefinitionProviders;


	public JpaAnnotationProvider(JpaAnnotationDefinitionProvider... annotationDefinitionProviders) {
		super();
		this.annotationDefinitionProviders = IterableTools.iterable(annotationDefinitionProviders);
	}


	// ********** type annotations **********

	
	public Iterable<String> getAnnotationNames() {
		return IterableTools.transform(this.getAnnotationDefinitions(), AnnotationDefinition.ANNOTATION_NAME_TRANSFORMER);
	}

	public Iterable<String> getContainerAnnotationNames() {
		return IterableTools.transform(this.getNestableAnnotationDefinitions(), NestableAnnotationDefinition.CONTAINER_ANNOTATION_NAME_TRANSFORMER);
	}

	public Iterable<String> getNestableAnnotationNames() {
		return IterableTools.transform(this.getNestableAnnotationDefinitions(), NestableAnnotationDefinition.NESTABLE_ANNOTATION_NAME_TRANSFORMER);
	}

	protected Iterable<AnnotationDefinition> getAnnotationDefinitions() {
		return IterableTools.children(this.annotationDefinitionProviders, JpaAnnotationDefinitionProvider.ANNOTATION_DEFINITIONS_TRANSFORMER);
	}

	protected Iterable<NestableAnnotationDefinition> getNestableAnnotationDefinitions() {
		return IterableTools.children(this.annotationDefinitionProviders, JpaAnnotationDefinitionProvider.NESTABLE_ANNOTATION_DEFINITIONS_TRANSFORMER);
	}


	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element, String annotationName) {
		return this.getAnnotationDefinition(annotationName).buildAnnotation(parent, element);
	}

	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent, String annotationName) {
		return this.getAnnotationDefinition(annotationName).buildNullAnnotation(parent);
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return this.getAnnotationDefinition(jdtAnnotation.getElementName()).buildAnnotation(parent, jdtAnnotation);
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation, int index) {
		return this.getNestableAnnotationDefinition(jdtAnnotation.getElementName()).buildAnnotation(parent, jdtAnnotation, index);
	}

	protected AnnotationDefinition getAnnotationDefinition(String annotationName) {
		AnnotationDefinition annotationDefinition = this.selectAnnotationDefinition(this.getAnnotationDefinitions(), annotationName);
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition;
	}


	protected NestableAnnotationDefinition getNestableAnnotationDefinition(String annotationName) {
		NestableAnnotationDefinition annotationDefinition = this.selectNestableAnnotationDefinition(this.getNestableAnnotationDefinitions(), annotationName);
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported nsetable annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition;
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element, String annotationName, int index) {
		return this.getNestableAnnotationDefinition(annotationName).buildAnnotation(parent, element, index);
	}

	public String getNestableAnnotationName(String containerAnnotationName) {
		return getNestableAnnotationDefinitionForContainer(containerAnnotationName).getNestableAnnotationName();
	}

	public String getContainerAnnotationName(String nestableAnnotationName) {
		return getNestableAnnotationDefinition(nestableAnnotationName).getContainerAnnotationName();
	}

	public String getNestableElementName(String nestableAnnotationName) {
		return getNestableAnnotationDefinition(nestableAnnotationName).getElementName();		
	}

	private NestableAnnotationDefinition getNestableAnnotationDefinitionForContainer(String containerAnnotationName) {
		for (NestableAnnotationDefinition nestableAnnotationDefinition : getNestableAnnotationDefinitions()) {
			if (nestableAnnotationDefinition.getContainerAnnotationName().equals(containerAnnotationName)) {
				return nestableAnnotationDefinition;
			}
		}
		return null;
	}


	// ********** convenience methods **********

	protected AnnotationDefinition selectAnnotationDefinition(Iterable<AnnotationDefinition> annotationDefinitions, String annotationName) {
		for (AnnotationDefinition annotationDefinition : annotationDefinitions) {
			if (annotationDefinition.getAnnotationName().equals(annotationName)) {
				return annotationDefinition;
			}
		}
		return null;
	}

	protected NestableAnnotationDefinition selectNestableAnnotationDefinition(Iterable<NestableAnnotationDefinition> annotationDefinitions, String annotationName) {
		for (NestableAnnotationDefinition annotationDefinition : annotationDefinitions) {
			if (annotationDefinition.getNestableAnnotationName().equals(annotationName)) {
				return annotationDefinition;
			}
		}
		return null;
	}
}
