/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jaxb.core.AnnotationProvider;
import org.eclipse.jpt.jaxb.core.resource.java.Annotation;
import org.eclipse.jpt.jaxb.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jaxb.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;

/**
 * Delegate to annotation definition providers.
 * The platform factory will build an instance of this annotation provider,
 * passing in the appropriate array of annotation definition providers necessary
 * to build the annotations for the platform (vendor and/or version).
 */
public final class GenericAnnotationProvider
	implements AnnotationProvider
{
	private final AnnotationDefinition[] annotationDefinitions;

	private final NestableAnnotationDefinition[] nestableAnnotationDefinitions;
	
	public GenericAnnotationProvider(AnnotationDefinition[] annotationDefinitions, NestableAnnotationDefinition[] nestableAnnotationDefinitions) {
		super();
		this.annotationDefinitions = annotationDefinitions;
		this.nestableAnnotationDefinitions = nestableAnnotationDefinitions;
	}
	
	
	protected Iterable<AnnotationDefinition> getAnnotationDefinitions() {
		return new ArrayIterable<AnnotationDefinition>(this.annotationDefinitions);
	}
	
	protected AnnotationDefinition getAnnotationDefinition(String annotationName) {
		for (AnnotationDefinition annotationDefinition : this.annotationDefinitions) {
			if (annotationDefinition.getAnnotationName().equals(annotationName)) {
				return annotationDefinition;
			}
		}
		return null;
	}
	
	public Iterable<String> getAnnotationNames() {
		return new TransformationIterable<AnnotationDefinition, String>(getAnnotationDefinitions()) {
			@Override
			protected String transform(AnnotationDefinition annotationDefinition) {
				return annotationDefinition.getAnnotationName();
			}
		};
	}

	public Iterable<String> getContainerAnnotationNames() {
		return new TransformationIterable<NestableAnnotationDefinition, String>(getNestableAnnotationDefinitions()) {
			@Override
			protected String transform(NestableAnnotationDefinition annotationDefinition) {
				return annotationDefinition.getContainerAnnotationName();
			}
		};
	}

	public Iterable<String> getNestableAnnotationNames() {
		return new TransformationIterable<NestableAnnotationDefinition, String>(getNestableAnnotationDefinitions()) {
			@Override
			protected String transform(NestableAnnotationDefinition annotationDefinition) {
				return annotationDefinition.getNestableAnnotationName();
			}
		};
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element, String annotationName) {
		return this.getAnnotationDefinition(annotationName).buildAnnotation(parent, element);
	}
	
	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return this.getAnnotationDefinition(jdtAnnotation.getElementName()).buildAnnotation(parent, jdtAnnotation);
	}
	
	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent, String annotationName) {
		return this.getAnnotationDefinition(annotationName).buildNullAnnotation(parent);
	}

	protected Iterable<NestableAnnotationDefinition> getNestableAnnotationDefinitions() {
		return new ArrayIterable<NestableAnnotationDefinition>(this.nestableAnnotationDefinitions);
	}

	protected NestableAnnotationDefinition getNestableAnnotationDefinition(String annotationName) {
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
		return getNestableAnnotationDefinitionForContainer(containerAnnotationName).getNestableAnnotationName();
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
}
