/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.core.JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.jdt.Attribute;
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
	
	
	// ********** static methods **********
	
	protected static Iterator<String> annotationNames(
			Iterator<AnnotationDefinition> annotationDefinitions) {
		return new TransformationIterator<AnnotationDefinition, String>(annotationDefinitions) {
			@Override
			protected String transform(AnnotationDefinition annotationDefinition) {
				return annotationDefinition.getAnnotationName();
			}
		};
	}
	
	protected static AnnotationDefinition getAnnotationDefinition(
			String annotationName, Iterator<AnnotationDefinition> annotationDefinitions) {
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
	
	@SuppressWarnings("unchecked")
	public Iterator<String> typeAnnotationNames() {
		return new CompositeIterator<String>(
				annotationNames(typeAnnotationDefinitions()));
	}
	
	protected Iterator<AnnotationDefinition> typeAnnotationDefinitions() {
		return new CompositeIterator<AnnotationDefinition> ( 
			new TransformationIterator<JpaAnnotationDefinitionProvider, Iterator<AnnotationDefinition>>(
					this.annotationDefinitionProviders()) {
				@Override
				protected Iterator<AnnotationDefinition> transform(
						JpaAnnotationDefinitionProvider annotationDefinitionProvider) {
					return annotationDefinitionProvider.typeAnnotationDefinitions();
				}
			});
	}
	
	public Annotation buildTypeAnnotation(
			JavaResourcePersistentType parent, Type type, String annotationName) {
		return this.getTypeAnnotationDefinition(annotationName).buildAnnotation(parent, type);
	}
	
	public Annotation buildTypeAnnotation(
			JavaResourcePersistentType parent, IAnnotation jdtAnnotation) {
		return this.getTypeAnnotationDefinition(jdtAnnotation.getElementName()).
				buildAnnotation(parent, jdtAnnotation);
	}
	
	protected AnnotationDefinition getTypeAnnotationDefinition(String annotationName) {
		AnnotationDefinition annotationDefinition = 
				getAnnotationDefinition(annotationName, this.typeAnnotationDefinitions());
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported type annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition;
	}
	
	public Annotation buildNullTypeAnnotation(
			JavaResourcePersistentType parent, String annotationName) {
		return this.getTypeAnnotationDefinition(annotationName).buildNullAnnotation(parent);
	}
	
	
	// ********** attribute annotations **********
	
	@SuppressWarnings("unchecked")
	public Iterator<String> attributeAnnotationNames() {
		return new CompositeIterator<String>(
				annotationNames(attributeAnnotationDefinitions()));
	}
	
	protected Iterator<AnnotationDefinition> attributeAnnotationDefinitions() {
		return new CompositeIterator<AnnotationDefinition> ( 
			new TransformationIterator<JpaAnnotationDefinitionProvider, Iterator<AnnotationDefinition>>(
					this.annotationDefinitionProviders()) {
				@Override
				protected Iterator<AnnotationDefinition> transform(
						JpaAnnotationDefinitionProvider annotationDefinitionProvider) {
					return annotationDefinitionProvider.attributeAnnotationDefinitions();
				}
			});
	}
	
	public Annotation buildAttributeAnnotation(
			JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName) {
		return this.getAttributeAnnotationDefinition(annotationName).buildAnnotation(parent, attribute);
	}
	
	public Annotation buildAttributeAnnotation(
			JavaResourcePersistentAttribute parent, IAnnotation jdtAnnotation) {
		return this.getAttributeAnnotationDefinition(jdtAnnotation.getElementName()).
				buildAnnotation(parent, jdtAnnotation);
	}
	
	public Annotation buildNullAttributeAnnotation(
			JavaResourcePersistentAttribute parent, String annotationName) {
		return this.getAttributeAnnotationDefinition(annotationName).buildNullAnnotation(parent);
	}
	
	protected AnnotationDefinition getAttributeAnnotationDefinition(String annotationName) {
		AnnotationDefinition annotationDefinition = 
				getAnnotationDefinition(annotationName, this.attributeAnnotationDefinitions());
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported attribute annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition;
	}
}
