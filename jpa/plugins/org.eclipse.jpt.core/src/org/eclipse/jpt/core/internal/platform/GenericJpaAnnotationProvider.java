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
 * 
 */
public class GenericJpaAnnotationProvider
	implements JpaAnnotationProvider
{
	private JpaAnnotationDefinitionProvider[] annotationDefinitionProviders;
	
	public GenericJpaAnnotationProvider(JpaAnnotationDefinitionProvider... annotationDefinitionProviders) {
		super();
		this.annotationDefinitionProviders = annotationDefinitionProviders;
	}
	
	// ********** annotation definition providers **********
	protected synchronized ListIterator<JpaAnnotationDefinitionProvider> annotationDefinitionProviders() {
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
		AnnotationDefinition annotationDefinition = this.getTypeMappingAnnotationDefinition(annotationName);
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported type mapping annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition.buildAnnotation(parent, type);
	}
	
	public Annotation buildNullTypeMappingAnnotation(JavaResourcePersistentType parent, Type type, String annotationName) {
		AnnotationDefinition annotationDefinition = this.getTypeMappingAnnotationDefinition(annotationName);
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported type mapping annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition.buildNullAnnotation(parent, type);
	}

	protected AnnotationDefinition getTypeMappingAnnotationDefinition(String annotationName) {
		return getAnnotationDefinition(annotationName, this.typeMappingAnnotationDefinitions());
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
		AnnotationDefinition annotationDefinition = this.getTypeSupportingAnnotationDefinition(annotationName);
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported type supporting annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition.buildAnnotation(parent, type);
	}

	public Annotation buildNullTypeSupportingAnnotation(JavaResourcePersistentType parent, Type type, String annotationName) {
		AnnotationDefinition annotationDefinition = this.getTypeSupportingAnnotationDefinition(annotationName);
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported type supporting annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition.buildNullAnnotation(parent, type);
	}
	
	protected AnnotationDefinition getTypeSupportingAnnotationDefinition(String annotationName) {
		return getAnnotationDefinition(annotationName, this.typeSupportingAnnotationDefinitions());
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
		AnnotationDefinition annotationDefinition = this.getAttributeMappingAnnotationDefinition(annotationName);
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported attribute mapping annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition.buildAnnotation(parent, attribute);
	}
	
	public Annotation buildNullAttributeMappingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName) {
		AnnotationDefinition annotationDefinition = this.getAttributeMappingAnnotationDefinition(annotationName);
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported attribute mapping annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition.buildNullAnnotation(parent, attribute);
	}
	
	protected AnnotationDefinition getAttributeMappingAnnotationDefinition(String annotationName) {
		return getAnnotationDefinition(annotationName, this.attributeMappingAnnotationDefinitions());
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
		AnnotationDefinition annotationDefinition = this.getAttributeSupportingAnnotationDefinition(annotationName);
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported attribute supporting annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition.buildAnnotation(parent, attribute);
	}
	
	public Annotation buildNullAttributeSupportingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName) {
		AnnotationDefinition annotationDefinition = this.getAttributeSupportingAnnotationDefinition(annotationName);
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported attribute supporting annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition.buildNullAnnotation(parent, attribute);
	}
	
	protected AnnotationDefinition getAttributeSupportingAnnotationDefinition(String annotationName) {
		return getAnnotationDefinition(annotationName, this.attributeSupportingAnnotationDefinitions());
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
