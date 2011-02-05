/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.Iterator;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedPackage;
import org.eclipse.jpt.common.core.utility.jdt.Attribute;
import org.eclipse.jpt.common.core.utility.jdt.Type;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.core.JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;

/**
 * Delegate to annotation definition providers.
 * The platform factory will build an instance of this annotation provider,
 * passing in the appropriate array of annotation definition providers necessary
 * to build the annotations for the platform (vendor and/or version).
 */
public class GenericJpaAnnotationProvider
	implements JpaAnnotationProvider
{
	private final Iterable<JpaAnnotationDefinitionProvider> annotationDefinitionProviders;


	public GenericJpaAnnotationProvider(JpaAnnotationDefinitionProvider... annotationDefinitionProviders) {
		super();
		this.annotationDefinitionProviders = new ArrayIterable<JpaAnnotationDefinitionProvider>(annotationDefinitionProviders);
	}


	// ********** type annotations **********

	public Iterator<String> typeAnnotationNames() {
		return this.convertToNames(this.getTypeAnnotationDefinitions()).iterator();
	}

	protected Iterable<AnnotationDefinition> getTypeAnnotationDefinitions() {
		return new CompositeIterable<AnnotationDefinition> (
			new TransformationIterable<JpaAnnotationDefinitionProvider, Iterable<AnnotationDefinition>>(this.annotationDefinitionProviders) {
				@Override
				protected Iterable<AnnotationDefinition> transform(JpaAnnotationDefinitionProvider annotationDefinitionProvider) {
					return annotationDefinitionProvider.getTypeAnnotationDefinitions();
				}
			}
		);
	}

	public Iterator<String> typeMappingAnnotationNames() {
		return this.convertToNames(this.getTypeMappingAnnotationDefinitions()).iterator();
	}

	protected Iterable<AnnotationDefinition> getTypeMappingAnnotationDefinitions() {
		return new CompositeIterable<AnnotationDefinition> (
			new TransformationIterable<JpaAnnotationDefinitionProvider, Iterable<AnnotationDefinition>>(this.annotationDefinitionProviders) {
				@Override
				protected Iterable<AnnotationDefinition> transform(JpaAnnotationDefinitionProvider annotationDefinitionProvider) {
					return annotationDefinitionProvider.getTypeMappingAnnotationDefinitions();
				}
			}
		);
	}

	public Annotation buildTypeAnnotation(JavaResourcePersistentType parent, Type type, String annotationName) {
		return this.getTypeAnnotationDefinition(annotationName).buildAnnotation(parent, type);
	}

	public Annotation buildTypeAnnotation(JavaResourcePersistentType parent, IAnnotation jdtAnnotation) {
		return this.getTypeAnnotationDefinition(jdtAnnotation.getElementName()).buildAnnotation(parent, jdtAnnotation);
	}

	protected AnnotationDefinition getTypeAnnotationDefinition(String annotationName) {
		AnnotationDefinition annotationDefinition = this.selectAnnotationDefinition(this.getTypeAnnotationDefinitions(), annotationName);
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported type annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition;
	}

	public Annotation buildNullTypeAnnotation(JavaResourcePersistentType parent, String annotationName) {
		return this.getTypeAnnotationDefinition(annotationName).buildNullAnnotation(parent);
	}


	// ********** attribute annotations **********

	public Iterator<String> attributeAnnotationNames() {
		return this.convertToNames(this.getAttributeAnnotationDefinitions()).iterator();
	}

	protected Iterable<AnnotationDefinition> getAttributeAnnotationDefinitions() {
		return new CompositeIterable<AnnotationDefinition> (
			new TransformationIterable<JpaAnnotationDefinitionProvider, Iterable<AnnotationDefinition>>(this.annotationDefinitionProviders) {
				@Override
				protected Iterable<AnnotationDefinition> transform(JpaAnnotationDefinitionProvider annotationDefinitionProvider) {
					return annotationDefinitionProvider.getAttributeAnnotationDefinitions();
				}
			}
		);
	}

	public Annotation buildAttributeAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName) {
		return this.getAttributeAnnotationDefinition(annotationName).buildAnnotation(parent, attribute);
	}

	public Annotation buildAttributeAnnotation(JavaResourcePersistentAttribute parent, IAnnotation jdtAnnotation) {
		return this.getAttributeAnnotationDefinition(jdtAnnotation.getElementName()).buildAnnotation(parent, jdtAnnotation);
	}

	public Annotation buildNullAttributeAnnotation(JavaResourcePersistentAttribute parent, String annotationName) {
		return this.getAttributeAnnotationDefinition(annotationName).buildNullAnnotation(parent);
	}

	protected AnnotationDefinition getAttributeAnnotationDefinition(String annotationName) {
		AnnotationDefinition annotationDefinition = this.selectAnnotationDefinition(this.getAttributeAnnotationDefinitions(), annotationName);
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported attribute annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition;
	}


	// ********** package annotations **********

	public Iterator<String> packageAnnotationNames() {
		return this.convertToNames(this.getPackageAnnotationDefinitions()).iterator();
	}

	protected Iterable<AnnotationDefinition> getPackageAnnotationDefinitions() {
		return new CompositeIterable<AnnotationDefinition> ( 
			new TransformationIterable<JpaAnnotationDefinitionProvider, Iterable<AnnotationDefinition>>(this.annotationDefinitionProviders) {
				@Override
				protected Iterable<AnnotationDefinition> transform(JpaAnnotationDefinitionProvider annotationDefinitionProvider) {
					return annotationDefinitionProvider.getPackageAnnotationDefinitions();
				}
			}
		);
	}

	public Annotation buildPackageAnnotation(JavaResourcePackage parent, AnnotatedPackage pkg, String annotationName) {
		return this.getPackageAnnotationDefinition(annotationName).buildAnnotation(parent, pkg);
	}

	public Annotation buildPackageAnnotation(JavaResourcePackage parent, IAnnotation jdtAnnotation) {
		return this.getPackageAnnotationDefinition(jdtAnnotation.getElementName()).buildAnnotation(parent, jdtAnnotation);
	}

	public Annotation buildNullPackageAnnotation(JavaResourcePackage parent, String annotationName) {
		return this.getPackageAnnotationDefinition(annotationName).buildNullAnnotation(parent);
	}

	protected AnnotationDefinition getPackageAnnotationDefinition(String annotationName) {
		AnnotationDefinition annotationDefinition = this.selectAnnotationDefinition(this.getPackageAnnotationDefinitions(), annotationName);
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported package annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition;
	}


	// ********** convenience methods **********

	protected Iterable<String> convertToNames(Iterable<AnnotationDefinition> annotationDefinitions) {
		return new TransformationIterable<AnnotationDefinition, String>(annotationDefinitions) {
			@Override
			protected String transform(AnnotationDefinition annotationDefinition) {
				return annotationDefinition.getAnnotationName();
			}
		};
	}

	protected AnnotationDefinition selectAnnotationDefinition(Iterable<AnnotationDefinition> annotationDefinitions, String annotationName) {
		for (AnnotationDefinition annotationDefinition : annotationDefinitions) {
			if (annotationDefinition.getAnnotationName().equals(annotationName)) {
				return annotationDefinition;
			}
		}
		return null;
	}
}
