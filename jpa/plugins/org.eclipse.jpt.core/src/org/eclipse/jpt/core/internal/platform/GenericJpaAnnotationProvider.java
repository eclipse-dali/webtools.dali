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
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationListIterator;

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


	// ********** annotation definition providers **********

	protected ListIterator<JpaAnnotationDefinitionProvider> annotationDefinitionProviders() {
		return new ArrayListIterator<JpaAnnotationDefinitionProvider>(this.annotationDefinitionProviders);
	}


	// ********** type annotations **********

	@SuppressWarnings("unchecked")
	public ListIterator<String> typeMappingAnnotationNames() {
		return new CompositeListIterator<String>(annotationNames(typeMappingAnnotationDefinitions()));
	}

	protected ListIterator<AnnotationDefinition> typeMappingAnnotationDefinitions() {
		return new CompositeListIterator<AnnotationDefinition> ( 
			new TransformationListIterator<JpaAnnotationDefinitionProvider, ListIterator<AnnotationDefinition>>(this.annotationDefinitionProviders()) {
				@Override
				protected ListIterator<AnnotationDefinition> transform(JpaAnnotationDefinitionProvider annotationDefinitionProvider) {
					return annotationDefinitionProvider.typeMappingAnnotationDefinitions();
				}
			}
		);
	}

	public Annotation buildTypeMappingAnnotation(JavaResourcePersistentType parent, Type type, String annotationName) {
		return this.getTypeMappingAnnotationDefinition(annotationName).buildAnnotation(parent, type);
	}
	
	public Annotation buildTypeMappingAnnotation(JavaResourcePersistentType parent, IAnnotation jdtAnnotation) {
		return this.getTypeMappingAnnotationDefinition(jdtAnnotation.getElementName()).buildAnnotation(parent, jdtAnnotation);
	}
	
	protected AnnotationDefinition getTypeMappingAnnotationDefinition(String annotationName) {
		AnnotationDefinition annotationDefinition = getAnnotationDefinition(annotationName, this.typeMappingAnnotationDefinitions());
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported type mapping annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition;
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<String> typeSupportingAnnotationNames() {
		return new CompositeListIterator<String>(annotationNames(typeSupportingAnnotationDefinitions()));
	}

	protected ListIterator<AnnotationDefinition> typeSupportingAnnotationDefinitions() {
		return new CompositeListIterator<AnnotationDefinition> ( 
			new TransformationListIterator<JpaAnnotationDefinitionProvider, ListIterator<AnnotationDefinition>>(this.annotationDefinitionProviders()) {
				@Override
				protected ListIterator<AnnotationDefinition> transform(JpaAnnotationDefinitionProvider annotationDefinitionProvider) {
					return annotationDefinitionProvider.typeSupportingAnnotationDefinitions();
				}
			}
		);
	}

	public Annotation buildTypeSupportingAnnotation(JavaResourcePersistentType parent, Type type, String annotationName) {
		return this.getTypeSupportingAnnotationDefinition(annotationName).buildAnnotation(parent, type);
	}

	public Annotation buildTypeSupportingAnnotation(JavaResourcePersistentType parent, IAnnotation jdtAnnotation) {
		return this.getTypeSupportingAnnotationDefinition(jdtAnnotation.getElementName()).buildAnnotation(parent, jdtAnnotation);
	}

	public Annotation buildNullTypeSupportingAnnotation(JavaResourcePersistentType parent, String annotationName) {
		return this.getTypeSupportingAnnotationDefinition(annotationName).buildNullAnnotation(parent);
	}
	
	protected AnnotationDefinition getTypeSupportingAnnotationDefinition(String annotationName) {
		AnnotationDefinition annotationDefinition = getAnnotationDefinition(annotationName, this.typeSupportingAnnotationDefinitions());
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported type supporting annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition;
	}


	// ********** attribute annotations **********

	@SuppressWarnings("unchecked")
	public ListIterator<String> attributeMappingAnnotationNames() {
		return new CompositeListIterator<String>(annotationNames(attributeMappingAnnotationDefinitions()));
	}

	protected ListIterator<AnnotationDefinition> attributeMappingAnnotationDefinitions() {
		return new CompositeListIterator<AnnotationDefinition> ( 
			new TransformationListIterator<JpaAnnotationDefinitionProvider, ListIterator<AnnotationDefinition>>(this.annotationDefinitionProviders()) {
				@Override
				protected ListIterator<AnnotationDefinition> transform(JpaAnnotationDefinitionProvider annotationDefinitionProvider) {
					return annotationDefinitionProvider.attributeMappingAnnotationDefinitions();
				}
			}
		);
	}

	public Annotation buildAttributeMappingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName) {
		return this.getAttributeMappingAnnotationDefinition(annotationName).buildAnnotation(parent, attribute);
	}
	
	public Annotation buildAttributeMappingAnnotation(JavaResourcePersistentAttribute parent, IAnnotation jdtAnnotation) {
		return this.getAttributeMappingAnnotationDefinition(jdtAnnotation.getElementName()).buildAnnotation(parent, jdtAnnotation);
	}
	
	public Annotation buildNullAttributeMappingAnnotation(JavaResourcePersistentAttribute parent, String annotationName) {
		return this.getAttributeMappingAnnotationDefinition(annotationName).buildNullAnnotation(parent);
	}
	
	protected AnnotationDefinition getAttributeMappingAnnotationDefinition(String annotationName) {
		AnnotationDefinition annotationDefinition = getAnnotationDefinition(annotationName, this.attributeMappingAnnotationDefinitions());
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported attribute mapping annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition;
	}	
	
	@SuppressWarnings("unchecked")
	public ListIterator<String> attributeSupportingAnnotationNames() {
		return new CompositeListIterator<String>(annotationNames(attributeSupportingAnnotationDefinitions()));
	}

	protected ListIterator<AnnotationDefinition> attributeSupportingAnnotationDefinitions() {
		return new CompositeListIterator<AnnotationDefinition> ( 
			new TransformationListIterator<JpaAnnotationDefinitionProvider, ListIterator<AnnotationDefinition>>(this.annotationDefinitionProviders()) {
				@Override
				protected ListIterator<AnnotationDefinition> transform(JpaAnnotationDefinitionProvider annotationDefinitionProvider) {
					return annotationDefinitionProvider.attributeSupportingAnnotationDefinitions();
				}
			}
		);
	}
	
	public Annotation buildAttributeSupportingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName) {
		return this.getAttributeSupportingAnnotationDefinition(annotationName).buildAnnotation(parent, attribute);
	}
	
	public Annotation buildAttributeSupportingAnnotation(JavaResourcePersistentAttribute parent, IAnnotation jdtAnnotation) {
		return this.getAttributeSupportingAnnotationDefinition(jdtAnnotation.getElementName()).buildAnnotation(parent, jdtAnnotation);
	}
	
	public Annotation buildNullAttributeSupportingAnnotation(JavaResourcePersistentAttribute parent, String annotationName) {
		return this.getAttributeSupportingAnnotationDefinition(annotationName).buildNullAnnotation(parent);
	}
	
	protected AnnotationDefinition getAttributeSupportingAnnotationDefinition(String annotationName) {
		AnnotationDefinition annotationDefinition = getAnnotationDefinition(annotationName, this.attributeSupportingAnnotationDefinitions());
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported attribute supporting annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition;
	}
	

	// ********** static methods **********

	protected static ListIterator<String> annotationNames(ListIterator<AnnotationDefinition> annotationDefinitions) {
		return new TransformationListIterator<AnnotationDefinition, String>(annotationDefinitions) {
			@Override
			protected String transform(AnnotationDefinition annotationDefinition) {
				return annotationDefinition.getAnnotationName();
			}
		};
	}
	
	protected static AnnotationDefinition getAnnotationDefinition(String annotationName, ListIterator<AnnotationDefinition> annotationDefinitions) {
		while (annotationDefinitions.hasNext()) {
			AnnotationDefinition annotationDefinition = annotationDefinitions.next();
			if (annotationDefinition.getAnnotationName().equals(annotationName)) {
				return annotationDefinition;
			}
		}
		return null;
	}

}
