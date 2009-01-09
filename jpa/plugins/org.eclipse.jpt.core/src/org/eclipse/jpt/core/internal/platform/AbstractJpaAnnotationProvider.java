/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationListIterator;

/**
 * 
 */
public abstract class AbstractJpaAnnotationProvider
	implements JpaAnnotationProvider
{
	/**
	 * Ordered list of possible type mapping annotations. Ordered because this
	 * is used to determine the mapping in the case where 2 mapping annotations exist
	 */
	private AnnotationDefinition[] typeMappingAnnotationDefinitions;
	
	private AnnotationDefinition[] typeSupportingAnnotationDefinitions;
	
	/**
	 * Ordered list of possible attribute mapping annotations. Ordered because this
	 * is used to determine the mapping in the case where 2 mapping annotations exist
	 */
	private AnnotationDefinition[] attributeMappingAnnotationDefinitions;
	
	private AnnotationDefinition[] attributeSupportingAnnotationDefinitions;
	
	private JpaAnnotationProvider[] delegateAnnotationProviders;
	
	protected AbstractJpaAnnotationProvider() {
		super();
	}
	
	// ********** delegate annotation providers **********
	protected synchronized ListIterator<JpaAnnotationProvider> delegateAnnotationProviders() {
		if (this.delegateAnnotationProviders == null) {
			this.delegateAnnotationProviders = this.buildDelegateAnnotationProviders();
		}
		return new ArrayListIterator<JpaAnnotationProvider>(this.delegateAnnotationProviders);
	}
	
	protected JpaAnnotationProvider[] buildDelegateAnnotationProviders() {
		ArrayList<JpaAnnotationProvider> providers = new ArrayList<JpaAnnotationProvider>();
		this.addDelegateAnnotationProvidersTo(providers);
		return providers.toArray(new JpaAnnotationProvider[providers.size()]);
	}
	
	/**
	 * Subclasses override this and provide annotationProviders they
	 * want to delegate to.  The delegating annotationProviders 
	 * and then this will be checked for building annotations.
	 */
	protected abstract void addDelegateAnnotationProvidersTo(List<JpaAnnotationProvider> providers);

	
	// ********** type annotation definitions **********

	protected synchronized ListIterator<AnnotationDefinition> typeMappingAnnotationDefinitions() {
		if (this.typeMappingAnnotationDefinitions == null) {
			this.typeMappingAnnotationDefinitions = this.buildTypeMappingAnnotationDefinitions();
		}
		return new ArrayListIterator<AnnotationDefinition>(this.typeMappingAnnotationDefinitions);
	}
	
	protected AnnotationDefinition[] buildTypeMappingAnnotationDefinitions() {
		ArrayList<AnnotationDefinition> definitions = new ArrayList<AnnotationDefinition>();
		this.addTypeMappingAnnotationDefinitionsTo(definitions);
		return definitions.toArray(new AnnotationDefinition[definitions.size()]);
	}
	
	/**
	 * Subclasses must override this to specify type mapping annotation definitions.
	 */
	protected abstract void addTypeMappingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions);
	
	protected synchronized ListIterator<AnnotationDefinition> typeSupportingAnnotationDefinitions() {
		if (this.typeSupportingAnnotationDefinitions == null) {
			this.typeSupportingAnnotationDefinitions = this.buildTypeSupportingAnnotationDefinitions();
		}
		return new ArrayListIterator<AnnotationDefinition>(this.typeSupportingAnnotationDefinitions);
	}
	
	protected AnnotationDefinition[] buildTypeSupportingAnnotationDefinitions() {
		ArrayList<AnnotationDefinition> definitions = new ArrayList<AnnotationDefinition>();
		this.addTypeSupportingAnnotationDefinitionsTo(definitions);
		return definitions.toArray(new AnnotationDefinition[definitions.size()]);
	}
	
	/**
	 * Subclasses must override this to specify type supporting annotation definitions.
	 */
	protected abstract void addTypeSupportingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions);
	

	// ********** attribute annotation definitions **********

	protected synchronized ListIterator<AnnotationDefinition> attributeMappingAnnotationDefinitions() {
		if (this.attributeMappingAnnotationDefinitions == null) {
			this.attributeMappingAnnotationDefinitions = this.buildAttributeMappingAnnotationDefinitions();
		}
		return new ArrayListIterator<AnnotationDefinition>(this.attributeMappingAnnotationDefinitions);
	}
	
	protected AnnotationDefinition[] buildAttributeMappingAnnotationDefinitions() {
		ArrayList<AnnotationDefinition> definitions = new ArrayList<AnnotationDefinition>();
		this.addAttributeMappingAnnotationDefinitionsTo(definitions);
		return definitions.toArray(new AnnotationDefinition[definitions.size()]);
	}
	
	/**
	 * Subclasses must override this to specify  attribute mapping annotation definitions.
	 */
	protected abstract void addAttributeMappingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions);
	
	protected synchronized ListIterator<AnnotationDefinition> attributeSupportingAnnotationDefinitions() {
		if (this.attributeSupportingAnnotationDefinitions == null) {
			this.attributeSupportingAnnotationDefinitions = this.buildAttributeSupportingAnnotationDefinitions();
		}
		return new ArrayListIterator<AnnotationDefinition>(this.attributeSupportingAnnotationDefinitions);
	}
	
	protected AnnotationDefinition[] buildAttributeSupportingAnnotationDefinitions() {
		ArrayList<AnnotationDefinition> definitions = new ArrayList<AnnotationDefinition>();
		this.addAttributeSupportingAnnotationDefinitionsTo(definitions);
		return definitions.toArray(new AnnotationDefinition[definitions.size()]);
	}
	
	/**
	 * Subclasses must override this to specify attribute supporting annotation definitions.
	 */
	protected abstract void addAttributeSupportingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions);
	

	// ********** type annotations **********

	@SuppressWarnings("unchecked")
	public ListIterator<String> typeMappingAnnotationNames() {
		return new CompositeListIterator<String>(delegateTypeMappingAnnotationNames(), annotationNames(typeMappingAnnotationDefinitions()));
	}
	
	protected ListIterator<String> delegateTypeMappingAnnotationNames() {
		return new CompositeListIterator<String> ( 
			new TransformationListIterator<JpaAnnotationProvider, ListIterator<String>>(this.delegateAnnotationProviders()) {
				@Override
				protected ListIterator<String> transform(JpaAnnotationProvider annotationProvider) {
					return annotationProvider.typeMappingAnnotationNames();
				}
			}
		);
	}
	
	public Annotation buildTypeMappingAnnotation(JavaResourcePersistentType parent, Type type, String annotationName) {
		for (JpaAnnotationProvider annotationProvider : CollectionTools.iterable(delegateAnnotationProviders())) {
			if (CollectionTools.contains(annotationProvider.typeMappingAnnotationNames(), annotationName)) {
				return annotationProvider.buildTypeMappingAnnotation(parent, type, annotationName);
			}
		}
		AnnotationDefinition annotationDefinition = this.getTypeMappingAnnotationDefinition(annotationName);
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported type mapping annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition.buildAnnotation(parent, type);
	}
	
	public Annotation buildNullTypeMappingAnnotation(JavaResourcePersistentType parent, Type type, String annotationName) {
		for (JpaAnnotationProvider annotationProvider : CollectionTools.iterable(delegateAnnotationProviders())) {
			if (CollectionTools.contains(annotationProvider.typeMappingAnnotationNames(), annotationName)) {
				return annotationProvider.buildNullTypeMappingAnnotation(parent, type, annotationName);
			}
		}
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
		return new CompositeListIterator<String>(delegateTypeSupportingAnnotationNames(), annotationNames(typeSupportingAnnotationDefinitions()));
	}
	
	protected ListIterator<String> delegateTypeSupportingAnnotationNames() {
		return 
			new CompositeListIterator<String> ( 
				new TransformationListIterator<JpaAnnotationProvider, ListIterator<String>>(this.delegateAnnotationProviders()) {
					@Override
					protected ListIterator<String> transform(JpaAnnotationProvider annotationProvider) {
						return annotationProvider.typeSupportingAnnotationNames();
					}
				}
			);
	}	
	
	public Annotation buildTypeSupportingAnnotation(JavaResourcePersistentType parent, Type type, String annotationName) {
		for (JpaAnnotationProvider annotationProvider : CollectionTools.iterable(delegateAnnotationProviders())) {
			if (CollectionTools.contains(annotationProvider.typeSupportingAnnotationNames(), annotationName)) {
				return annotationProvider.buildTypeSupportingAnnotation(parent, type, annotationName);
			}
		}
		AnnotationDefinition annotationDefinition = this.getTypeSupportingAnnotationDefinition(annotationName);
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported type supporting annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition.buildAnnotation(parent, type);
	}

	public Annotation buildNullTypeSupportingAnnotation(JavaResourcePersistentType parent, Type type, String annotationName) {
		for (JpaAnnotationProvider annotationProvider : CollectionTools.iterable(delegateAnnotationProviders())) {
			if (CollectionTools.contains(annotationProvider.typeSupportingAnnotationNames(), annotationName)) {
				return annotationProvider.buildNullTypeSupportingAnnotation(parent, type, annotationName);
			}
		}
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
		return new CompositeListIterator<String>(delegateAttributeMappingAnnotationNames(), annotationNames(attributeMappingAnnotationDefinitions()));
	}
	
	protected ListIterator<String> delegateAttributeMappingAnnotationNames() {
		return 
			new CompositeListIterator<String> ( 
				new TransformationListIterator<JpaAnnotationProvider, ListIterator<String>>(this.delegateAnnotationProviders()) {
					@Override
					protected ListIterator<String> transform(JpaAnnotationProvider annotationProvider) {
						return annotationProvider.attributeMappingAnnotationNames();
					}
				}
			);
	}	
	
	public Annotation buildAttributeMappingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName) {
		for (JpaAnnotationProvider annotationProvider : CollectionTools.iterable(delegateAnnotationProviders())) {
			if (CollectionTools.contains(annotationProvider.attributeMappingAnnotationNames(), annotationName)) {
				return annotationProvider.buildAttributeMappingAnnotation(parent, attribute, annotationName);
			}
		}
		AnnotationDefinition annotationDefinition = this.getAttributeMappingAnnotationDefinition(annotationName);
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported attribute mapping annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition.buildAnnotation(parent, attribute);
	}
	
	public Annotation buildNullAttributeMappingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName) {
		for (JpaAnnotationProvider annotationProvider : CollectionTools.iterable(delegateAnnotationProviders())) {
			if (CollectionTools.contains(annotationProvider.attributeMappingAnnotationNames(), annotationName)) {
				return annotationProvider.buildNullAttributeMappingAnnotation(parent, attribute, annotationName);
			}
		}
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
		return new CompositeListIterator<String>(delegateAttributeSupportingAnnotationNames(), annotationNames(attributeSupportingAnnotationDefinitions()));
	}
	
	protected ListIterator<String> delegateAttributeSupportingAnnotationNames() {
		return 
			new CompositeListIterator<String> ( 
				new TransformationListIterator<JpaAnnotationProvider, ListIterator<String>>(this.delegateAnnotationProviders()) {
					@Override
					protected ListIterator<String> transform(JpaAnnotationProvider annotationProvider) {
						return annotationProvider.attributeSupportingAnnotationNames();
					}
				}
			);
	}	
	
	public Annotation buildAttributeSupportingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName) {
		for (JpaAnnotationProvider annotationProvider : CollectionTools.iterable(delegateAnnotationProviders())) {
			if (CollectionTools.contains(annotationProvider.attributeSupportingAnnotationNames(), annotationName)) {
				return annotationProvider.buildAttributeSupportingAnnotation(parent, attribute, annotationName);
			}
		}
		AnnotationDefinition annotationDefinition = this.getAttributeSupportingAnnotationDefinition(annotationName);
		if (annotationDefinition == null) {
			throw new IllegalArgumentException("unsupported attribute supporting annotation: " + annotationName); //$NON-NLS-1$
		}
		return annotationDefinition.buildAnnotation(parent, attribute);
	}
	
	public Annotation buildNullAttributeSupportingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName) {
		for (JpaAnnotationProvider annotationProvider : CollectionTools.iterable(delegateAnnotationProviders())) {
			if (CollectionTools.contains(annotationProvider.attributeSupportingAnnotationNames(), annotationName)) {
				return annotationProvider.buildNullAttributeSupportingAnnotation(parent, attribute, annotationName);
			}
		}
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
