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
import java.util.ListIterator;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.core.JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.AnnotatedPackage;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.utility.internal.iterators.ArrayListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 * Delegate to annotation definition providers.
 * The platform factory will build an instance of this annotation provider,
 * passing in the appropriate array of annotation definition providers necessary
 * to build the annotations for the platform (vendor and/or version).
 */
public class GenericJpaAnnotationProvider
	implements JpaAnnotationProvider
{
	private final JpaAnnotationDefinitionProvider[] annotationDefinitionProviders;
	
	public GenericJpaAnnotationProvider(JpaAnnotationDefinitionProvider... annotationDefinitionProviders) {
		super();
		this.annotationDefinitionProviders = annotationDefinitionProviders;
	}
	
	
	// ********** convenience methods **********
	
	protected Iterator<String> annotationNames(Iterator<AnnotationDefinition> annotationDefinitions) {
		return new TransformationIterator<AnnotationDefinition, String>(annotationDefinitions) {
			@Override
			protected String transform(AnnotationDefinition annotationDefinition) {
				return annotationDefinition.getAnnotationName();
			}
		};
	}
	
	protected AnnotationDefinition selectAnnotationDefinition(Iterator<AnnotationDefinition> annotationDefinitions, String annotationName) {
		while (annotationDefinitions.hasNext()) {
			AnnotationDefinition annotationDefinition = annotationDefinitions.next();
			if (annotationDefinition.getAnnotationName().equals(annotationName)) {
				return annotationDefinition;
			}
		}
		return null;
	}
	
	
	// ********** annotation definition providers **********

	protected ListIterator<JpaAnnotationDefinitionProvider> annotationDefinitionProviders() {
		return new ArrayListIterator<JpaAnnotationDefinitionProvider>(this.annotationDefinitionProviders);
	}


	// ********** type annotations **********
	
	public Iterator<String> typeAnnotationNames() {
		return this.annotationNames(this.typeAnnotationDefinitions());
	}
	
	protected Iterator<AnnotationDefinition> typeAnnotationDefinitions() {
		return new CompositeIterator<AnnotationDefinition> ( 
			new TransformationIterator<JpaAnnotationDefinitionProvider, Iterator<AnnotationDefinition>>(this.annotationDefinitionProviders()) {
				@Override
				protected Iterator<AnnotationDefinition> transform(JpaAnnotationDefinitionProvider annotationDefinitionProvider) {
					return annotationDefinitionProvider.typeAnnotationDefinitions();
				}
			}
		);
	}
	
	public Iterator<String> typeMappingAnnotationNames() {
		return this.annotationNames(typeMappingAnnotationDefinitions());
	}
	
	protected Iterator<AnnotationDefinition> typeMappingAnnotationDefinitions() {
		return new CompositeIterator<AnnotationDefinition> ( 
			new TransformationIterator<JpaAnnotationDefinitionProvider, Iterator<AnnotationDefinition>>(this.annotationDefinitionProviders()) {
				@Override
				protected Iterator<AnnotationDefinition> transform(JpaAnnotationDefinitionProvider annotationDefinitionProvider) {
					return annotationDefinitionProvider.typeMappingAnnotationDefinitions();
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
		AnnotationDefinition annotationDefinition = this.selectAnnotationDefinition(this.typeAnnotationDefinitions(), annotationName);
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
		return this.annotationNames(attributeAnnotationDefinitions());
	}
	
	protected Iterator<AnnotationDefinition> attributeAnnotationDefinitions() {
		return new CompositeIterator<AnnotationDefinition> ( 
			new TransformationIterator<JpaAnnotationDefinitionProvider, Iterator<AnnotationDefinition>>(this.annotationDefinitionProviders()) {
				@Override
				protected Iterator<AnnotationDefinition> transform(JpaAnnotationDefinitionProvider annotationDefinitionProvider) {
					return annotationDefinitionProvider.attributeAnnotationDefinitions();
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
		AnnotationDefinition annotationDefinition = this.selectAnnotationDefinition(this.attributeAnnotationDefinitions(), annotationName);
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported attribute annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition;
	}


	// ********** package annotations **********

	public Iterator<String> packageAnnotationNames() {
		return annotationNames(packageAnnotationDefinitions());
	}

	protected Iterator<AnnotationDefinition> packageAnnotationDefinitions() {
		return new CompositeIterator<AnnotationDefinition> ( 
			new TransformationIterator<JpaAnnotationDefinitionProvider, Iterator<AnnotationDefinition>>(this.annotationDefinitionProviders()) {
				@Override
				protected Iterator<AnnotationDefinition> transform(JpaAnnotationDefinitionProvider annotationDefinitionProvider) {
					return annotationDefinitionProvider.packageAnnotationDefinitions();
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
		AnnotationDefinition annotationDefinition = this.selectAnnotationDefinition(this.packageAnnotationDefinitions(), annotationName);
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported package mapping annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition;
	}

}
