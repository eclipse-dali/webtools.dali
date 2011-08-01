/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
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
		this.annotationDefinitionProviders = new ArrayIterable<JpaAnnotationDefinitionProvider>(annotationDefinitionProviders);
	}


	// ********** type annotations **********

	
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

	protected Iterable<AnnotationDefinition> getAnnotationDefinitions() {
		return new CompositeIterable<AnnotationDefinition> (
			new TransformationIterable<JpaAnnotationDefinitionProvider, Iterable<AnnotationDefinition>>(this.annotationDefinitionProviders) {
				@Override
				protected Iterable<AnnotationDefinition> transform(JpaAnnotationDefinitionProvider annotationDefinitionProvider) {
					return annotationDefinitionProvider.getAnnotationDefinitions();
				}
			}
		);
	}

	protected Iterable<NestableAnnotationDefinition> getNestableAnnotationDefinitions() {
		return new CompositeIterable<NestableAnnotationDefinition> (
			new TransformationIterable<JpaAnnotationDefinitionProvider, Iterable<NestableAnnotationDefinition>>(this.annotationDefinitionProviders) {
				@Override
				protected Iterable<NestableAnnotationDefinition> transform(JpaAnnotationDefinitionProvider annotationDefinitionProvider) {
					return annotationDefinitionProvider.getNestableAnnotationDefinitions();
				}
			}
		);
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
