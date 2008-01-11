/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.IAttributeOverride;
import org.eclipse.jpt.core.internal.context.base.IColumnMapping;
import org.eclipse.jpt.core.internal.context.base.IEmbeddable;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IOverride;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverride;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverrides;
import org.eclipse.jpt.core.internal.resource.java.Embedded;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.internal.resource.java.NullAttributeOverride;
import org.eclipse.jpt.core.internal.resource.java.NullColumn;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;


public class JavaEmbeddedMapping extends JavaAttributeMapping implements IJavaEmbeddedMapping
{
	protected final List<IJavaAttributeOverride> specifiedAttributeOverrides;

	protected final List<IJavaAttributeOverride> defaultAttributeOverrides;

	private IEmbeddable embeddable;

	public JavaEmbeddedMapping(IJavaPersistentAttribute parent) {
		super(parent);
		this.specifiedAttributeOverrides = new ArrayList<IJavaAttributeOverride>();
		this.defaultAttributeOverrides = new ArrayList<IJavaAttributeOverride>();
	}
	
	@Override
	public void initializeFromResource(JavaPersistentAttributeResource persistentAttributeResource) {
		super.initializeFromResource(persistentAttributeResource);
		this.initializeAttributeOverrides(persistentAttributeResource);
		this.initializeDefaultAttributeOverrides(persistentAttributeResource);
		this.embeddable = embeddableFor(persistentAttribute());
	}
	
	protected void initializeAttributeOverrides(JavaPersistentAttributeResource persistentAttributeResource) {
		ListIterator<JavaResource> annotations = persistentAttributeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		
		while(annotations.hasNext()) {
			IJavaAttributeOverride attributeOverride = jpaFactory().createJavaAttributeOverride(this, this);
			attributeOverride.initializeFromResource((AttributeOverride) annotations.next());
			this.specifiedAttributeOverrides.add(attributeOverride);
		}
	}
	
	protected void initializeDefaultAttributeOverrides(JavaPersistentAttributeResource persistentAttributeResource) {
		for (Iterator<String> i = allOverridableAttributeNames(); i.hasNext(); ) {
			String attributeName = i.next();
			IJavaAttributeOverride attributeOverride = attributeOverrideNamed(attributeName);
			if (attributeOverride == null) {
				attributeOverride = createAttributeOverride(new NullAttributeOverride(persistentAttributeResource));
				attributeOverride.setName(attributeName);
				this.defaultAttributeOverrides.add(attributeOverride);
			}
		}
	}
	//****************** IOverride.Owner implemenation *******************
	public IColumnMapping columnMapping(String attributeName) {
		if (attributeName == null || embeddable() == null) {
			return null;
		}
		for (Iterator<IPersistentAttribute> stream = embeddable().persistentType().allAttributes(); stream.hasNext();) {
			IPersistentAttribute persAttribute = stream.next();
			if (attributeName.equals(persAttribute.getName())) {
				if (persAttribute.getMapping() instanceof IColumnMapping) {
					return (IColumnMapping) persAttribute.getMapping();
				}
			}
		}
		return null;
	}

	public boolean isVirtual(IOverride override) {
		return this.defaultAttributeOverrides.contains(override);
	}

	//****************** IJavaAttributeMapping implemenation *******************

	public String getKey() {
		return IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String annotationName() {
		return Embedded.ANNOTATION_NAME;
	}
	
	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.ATTRIBUTE_OVERRIDE,
			JPA.ATTRIBUTE_OVERRIDES);
	}
	
	//****************** IEmbeddedMapping implemenation *******************

	@SuppressWarnings("unchecked")
	public ListIterator<IJavaAttributeOverride> attributeOverrides() {
		return new CompositeListIterator<IJavaAttributeOverride>(specifiedAttributeOverrides(), defaultAttributeOverrides());
	}
	
	public ListIterator<IJavaAttributeOverride> defaultAttributeOverrides() {
		return new CloneListIterator<IJavaAttributeOverride>(this.defaultAttributeOverrides);
	}
	
	public ListIterator<IJavaAttributeOverride> specifiedAttributeOverrides() {
		return new CloneListIterator<IJavaAttributeOverride>(this.specifiedAttributeOverrides);
	}
	
	public int specifiedAttributeOverridesSize() {
		return this.specifiedAttributeOverrides.size();
	}

	public IJavaAttributeOverride addSpecifiedAttributeOverride(int index) {
		IJavaAttributeOverride attributeOverride = jpaFactory().createJavaAttributeOverride(this, this);
		this.specifiedAttributeOverrides.add(index, attributeOverride);
		this.persistentAttributeResource.addAnnotation(index, AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		this.fireItemAdded(IEntity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, attributeOverride);
		return attributeOverride;
	}
	
	protected void addSpecifiedAttributeOverride(int index, IJavaAttributeOverride attributeOverride) {
		addItemToList(index, attributeOverride, this.specifiedAttributeOverrides, IEntity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	public void removeSpecifiedAttributeOverride(int index) {
		IJavaAttributeOverride removedAttributeOverride = this.specifiedAttributeOverrides.remove(index);
		this.persistentAttributeResource.removeAnnotation(index, AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		fireItemRemoved(IEntity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, removedAttributeOverride);
	}
	
	protected void removeSpecifiedAttributeOverride(IJavaAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.specifiedAttributeOverrides, IEntity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}

	public void moveSpecifiedAttributeOverride(int targetIndex, int sourceIndex) {
		this.persistentAttributeResource.move(targetIndex, sourceIndex, AttributeOverrides.ANNOTATION_NAME);
		moveItemInList(targetIndex, sourceIndex, this.specifiedAttributeOverrides, IEntity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	protected void addDefaultAttributeOverride(IJavaAttributeOverride attributeOverride) {
		addItemToList(attributeOverride, this.defaultAttributeOverrides, IEntity.DEFAULT_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	protected void removeDefaultAttributeOverride(IJavaAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.defaultAttributeOverrides, IEntity.DEFAULT_ATTRIBUTE_OVERRIDES_LIST);
	}

	public IJavaAttributeOverride attributeOverrideNamed(String name) {
		return (IJavaAttributeOverride) overrideNamed(name, attributeOverrides());
	}

	public boolean containsAttributeOverride(String name) {
		return containsOverride(name, attributeOverrides());
	}

	public boolean containsDefaultAttributeOverride(String name) {
		return containsOverride(name, defaultAttributeOverrides());
	}

	public boolean containsSpecifiedAttributeOverride(String name) {
		return containsOverride(name, specifiedAttributeOverrides());
	}
	
	private IOverride overrideNamed(String name, ListIterator<? extends IOverride> overrides) {
		for (IOverride override : CollectionTools.iterable(overrides)) {
			String overrideName = override.getName();
			if (overrideName == null && name == null) {
				return override;
			}
			if (overrideName != null && overrideName.equals(name)) {
				return override;
			}
		}
		return null;
	}

	private boolean containsOverride(String name, ListIterator<? extends IOverride> overrides) {
		return overrideNamed(name, overrides) != null;
	}

	public IEmbeddable embeddable() {
		return this.embeddable;
	}

	@Override
	public void update(JavaPersistentAttributeResource persistentAttributeResource) {
		super.update(persistentAttributeResource);
		this.embeddable = embeddableFor(persistentAttribute());
		this.updateSpecifiedAttributeOverrides(persistentAttributeResource);
		this.updateDefaultAttributeOverrides(persistentAttributeResource);
		
	}
	protected void updateSpecifiedAttributeOverrides(JavaPersistentAttributeResource persistentAttributeResource) {
		ListIterator<IJavaAttributeOverride> attributeOverrides = specifiedAttributeOverrides();
		ListIterator<JavaResource> resourceAttributeOverrides = persistentAttributeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		
		while (attributeOverrides.hasNext()) {
			IJavaAttributeOverride attributeOverride = attributeOverrides.next();
			if (resourceAttributeOverrides.hasNext()) {
				attributeOverride.update((AttributeOverride) resourceAttributeOverrides.next());
			}
			else {
				removeSpecifiedAttributeOverride(attributeOverride);
			}
		}
		
		while (resourceAttributeOverrides.hasNext()) {
			addSpecifiedAttributeOverride(specifiedAttributeOverridesSize(), createAttributeOverride((AttributeOverride) resourceAttributeOverrides.next()));
		}	
	}
	
	protected IJavaAttributeOverride createAttributeOverride(AttributeOverride attributeOverrideResource) {
		IJavaAttributeOverride attributeOverride = jpaFactory().createJavaAttributeOverride(this, this);
		attributeOverride.initializeFromResource(attributeOverrideResource);
		return attributeOverride;
	}
	
	protected void updateDefaultAttributeOverrides(JavaPersistentAttributeResource persistentAttributeResource) {
		for (Iterator<String> i = allOverridableAttributeNames(); i.hasNext(); ) {
			String attributeName = i.next();
			IJavaAttributeOverride attributeOverride = attributeOverrideNamed(attributeName);
			if (attributeOverride == null) {
				attributeOverride = createAttributeOverride(new NullAttributeOverride(persistentAttributeResource));
				addDefaultAttributeOverride(attributeOverride);
				attributeOverride.setName(attributeName);
			}
			else if (attributeOverride.isVirtual()) {
				attributeOverride.getColumn().update(new NullColumn(persistentAttributeResource));
			}
		}
		
		Collection<String> attributeNames = CollectionTools.collection(allOverridableAttributeNames());
	
		//remove any default mappings that are not included in the attributeNames collection
		for (IJavaAttributeOverride attributeOverride : CollectionTools.iterable(defaultAttributeOverrides())) {
			if (!attributeNames.contains(attributeOverride.getName())
				|| containsSpecifiedAttributeOverride(attributeOverride.getName())) {
				removeDefaultAttributeOverride(attributeOverride);
			}
		}
	}

//
//	public IAttributeOverride attributeOverrideNamed(String name) {
//		return (IAttributeOverride) overrideNamed(name, getAttributeOverrides());
//	}
//
//	public boolean containsAttributeOverride(String name) {
//		return containsOverride(name, getAttributeOverrides());
//	}
//
//	public boolean containsSpecifiedAttributeOverride(String name) {
//		return containsOverride(name, getSpecifiedAttributeOverrides());
//	}
//
//	private IOverride overrideNamed(String name, List<? extends IOverride> overrides) {
//		for (IOverride override : overrides) {
//			String overrideName = override.getName();
//			if (overrideName == null && name == null) {
//				return override;
//			}
//			if (overrideName != null && overrideName.equals(name)) {
//				return override;
//			}
//		}
//		return null;
//	}
//
//	private boolean containsOverride(String name, List<? extends IOverride> overrides) {
//		return overrideNamed(name, overrides) != null;
//	}

	public Iterator<String> allOverridableAttributeNames() {
		return new TransformationIterator<IPersistentAttribute, String>(this.allOverridableAttributes()) {
			@Override
			protected String transform(IPersistentAttribute attribute) {
				return attribute.getName();
			}
		};
	}

	public Iterator<IPersistentAttribute> allOverridableAttributes() {
		if (this.embeddable() == null) {
			return EmptyIterator.instance();
		}
		return new FilteringIterator<IPersistentAttribute>(this.embeddable().persistentType().attributes()) {
			@Override
			protected boolean accept(Object o) {
				return ((IPersistentAttribute) o).isOverridableAttribute();
			}
		};
	}

	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (IAttributeOverride override : CollectionTools.iterable(this.attributeOverrides())) {
			result = ((IJavaAttributeOverride) override).candidateValuesFor(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	//******* static methods *********
	
	public static IEmbeddable embeddableFor(IJavaPersistentAttribute persistentAttribute) {
		String qualifiedTypeName = persistentAttribute.getPersistentAttributeResource().getQualifiedTypeName();
		if (qualifiedTypeName == null) {
			return null;
		}
		IPersistentType persistentType = persistentAttribute.persistenceUnit().persistentType(qualifiedTypeName);
		if (persistentType != null) {
			if (persistentType.mappingKey() == IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY) {
				return (IEmbeddable) persistentType.getMapping();
			}
		}
		return null;
	}

}
