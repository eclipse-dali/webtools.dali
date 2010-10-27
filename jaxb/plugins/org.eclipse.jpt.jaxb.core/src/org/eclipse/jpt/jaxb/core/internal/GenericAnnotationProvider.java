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
import org.eclipse.jpt.core.utility.jdt.AnnotatedPackage;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.jaxb.core.AnnotationDefinitionProvider;
import org.eclipse.jpt.jaxb.core.AnnotationProvider;
import org.eclipse.jpt.jaxb.core.resource.java.Annotation;
import org.eclipse.jpt.jaxb.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.utility.internal.iterables.ArrayListIterable;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;
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
	private final AnnotationDefinitionProvider[] annotationDefinitionProviders;
	
	public GenericAnnotationProvider(AnnotationDefinitionProvider... annotationDefinitionProviders) {
		super();
		this.annotationDefinitionProviders = annotationDefinitionProviders;
	}
	
	
	// ********** convenience methods **********
	
	protected Iterable<String> getAnnotationNames(Iterable<AnnotationDefinition> annotationDefinitions) {
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
	
	
	// ********** annotation definition providers **********

	protected ListIterable<AnnotationDefinitionProvider> getAnnotationDefinitionProviders() {
		return new ArrayListIterable<AnnotationDefinitionProvider>(this.annotationDefinitionProviders);
	}


	// ********** type annotations **********
	
	public Iterable<String> getTypeAnnotationNames() {
		return this.getAnnotationNames(this.getTypeAnnotationDefinitions());
	}
	
	protected Iterable<AnnotationDefinition> getTypeAnnotationDefinitions() {
		return new CompositeIterable<AnnotationDefinition> ( 
			new TransformationIterable<AnnotationDefinitionProvider, Iterable<AnnotationDefinition>>(this.getAnnotationDefinitionProviders()) {
				@Override
				protected Iterable<AnnotationDefinition> transform(AnnotationDefinitionProvider annotationDefinitionProvider) {
					return annotationDefinitionProvider.getTypeAnnotationDefinitions();
				}
			}
		);
	}
	
	public Iterable<String> getTypeMappingAnnotationNames() {
		return this.getAnnotationNames(getTypeMappingAnnotationDefinitions());
	}
	
	protected Iterable<AnnotationDefinition> getTypeMappingAnnotationDefinitions() {
		return new CompositeIterable<AnnotationDefinition> ( 
			new TransformationIterable<AnnotationDefinitionProvider, Iterable<AnnotationDefinition>>(this.getAnnotationDefinitionProviders()) {
				@Override
				protected Iterable<AnnotationDefinition> transform(AnnotationDefinitionProvider annotationDefinitionProvider) {
					return annotationDefinitionProvider.getTypeMappingAnnotationDefinitions();
				}
			}
		);
	}
	
	public Annotation buildTypeAnnotation(JavaResourceType parent, Type type, String annotationName) {
		return this.getTypeAnnotationDefinition(annotationName).buildAnnotation(parent, type);
	}
	
	public Annotation buildTypeAnnotation(JavaResourceType parent, IAnnotation jdtAnnotation) {
		return this.getTypeAnnotationDefinition(jdtAnnotation.getElementName()).buildAnnotation(parent, jdtAnnotation);
	}
	
	protected AnnotationDefinition getTypeAnnotationDefinition(String annotationName) {
		AnnotationDefinition annotationDefinition = this.selectAnnotationDefinition(this.getTypeAnnotationDefinitions(), annotationName);
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported type annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition;
	}
	
	public Annotation buildNullTypeAnnotation(JavaResourceType parent, String annotationName) {
		return this.getTypeAnnotationDefinition(annotationName).buildNullAnnotation(parent);
	}
	
	
	// ********** attribute annotations **********
	
	public Iterable<String> getAttributeAnnotationNames() {
		return this.getAnnotationNames(getAttributeAnnotationDefinitions());
	}
	
	protected Iterable<AnnotationDefinition> getAttributeAnnotationDefinitions() {
		return new CompositeIterable<AnnotationDefinition> ( 
			new TransformationIterable<AnnotationDefinitionProvider, Iterable<AnnotationDefinition>>(this.getAnnotationDefinitionProviders()) {
				@Override
				protected Iterable<AnnotationDefinition> transform(AnnotationDefinitionProvider annotationDefinitionProvider) {
					return annotationDefinitionProvider.getAttributeAnnotationDefinitions();
				}
			}
		);
	}
	
	public Annotation buildAttributeAnnotation(JavaResourceAttribute parent, Attribute attribute, String annotationName) {
		return this.getAttributeAnnotationDefinition(annotationName).buildAnnotation(parent, attribute);
	}
	
	public Annotation buildAttributeAnnotation(JavaResourceAttribute parent, IAnnotation jdtAnnotation) {
		return this.getAttributeAnnotationDefinition(jdtAnnotation.getElementName()).buildAnnotation(parent, jdtAnnotation);
	}
	
	public Annotation buildNullAttributeAnnotation(JavaResourceAttribute parent, String annotationName) {
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

	public Iterable<String> getPackageAnnotationNames() {
		return getAnnotationNames(getPackageAnnotationDefinitions());
	}

	protected Iterable<AnnotationDefinition> getPackageAnnotationDefinitions() {
		return new CompositeIterable<AnnotationDefinition> ( 
			new TransformationIterable<AnnotationDefinitionProvider, Iterable<AnnotationDefinition>>(this.getAnnotationDefinitionProviders()) {
				@Override
				protected Iterable<AnnotationDefinition> transform(AnnotationDefinitionProvider annotationDefinitionProvider) {
					return annotationDefinitionProvider.getPackageAnnotationDefinitions();
				}
			}
		);
	}

	public Annotation buildPackageAnnotation(JavaResourcePackage parent, AnnotatedPackage pack, String annotationName) {
		return this.getPackageAnnotationDefinition(annotationName).buildAnnotation(parent, pack);
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
			throw new IllegalArgumentException("unsupported package mapping annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition;
	}

}
