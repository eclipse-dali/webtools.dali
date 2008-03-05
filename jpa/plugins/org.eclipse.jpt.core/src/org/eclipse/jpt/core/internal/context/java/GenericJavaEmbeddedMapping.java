/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.resource.java.NullAttributeOverride;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverrides;
import org.eclipse.jpt.core.resource.java.Embedded;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class GenericJavaEmbeddedMapping extends AbstractJavaAttributeMapping implements JavaEmbeddedMapping
{
	protected final List<JavaAttributeOverride> specifiedAttributeOverrides;

	protected final List<JavaAttributeOverride> defaultAttributeOverrides;

	private Embeddable embeddable;

	public GenericJavaEmbeddedMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.specifiedAttributeOverrides = new ArrayList<JavaAttributeOverride>();
		this.defaultAttributeOverrides = new ArrayList<JavaAttributeOverride>();
	}
	
	@Override
	public void initializeFromResource(JavaResourcePersistentAttribute persistentAttributeResource) {
		super.initializeFromResource(persistentAttributeResource);
		this.embeddable = embeddableFor(persistentAttribute());
		this.initializeSpecifiedAttributeOverrides(persistentAttributeResource);
		this.initializeDefaultAttributeOverrides(persistentAttributeResource);
	}
	
	protected void initializeSpecifiedAttributeOverrides(JavaResourcePersistentAttribute persistentAttributeResource) {
		ListIterator<JavaResourceNode> annotations = persistentAttributeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		
		while(annotations.hasNext()) {
			JavaAttributeOverride attributeOverride = jpaFactory().buildJavaAttributeOverride(this, this);
			attributeOverride.initializeFromResource((AttributeOverrideAnnotation) annotations.next());
			this.specifiedAttributeOverrides.add(attributeOverride);
		}
	}
	
	protected void initializeDefaultAttributeOverrides(JavaResourcePersistentAttribute persistentAttributeResource) {
		for (Iterator<String> i = allOverridableAttributeNames(); i.hasNext(); ) {
			String attributeName = i.next();
			JavaAttributeOverride attributeOverride = attributeOverrideNamed(attributeName);
			if (attributeOverride == null) {
				attributeOverride = createAttributeOverride(new NullAttributeOverride(persistentAttributeResource, attributeName));
				this.defaultAttributeOverrides.add(attributeOverride);
			}
		}
	}
	//****************** IOverride.Owner implemenation *******************
	public ColumnMapping columnMapping(String attributeName) {
		return columnMapping(attributeName, embeddable());
	}

	public boolean isVirtual(BaseOverride override) {
		return this.defaultAttributeOverrides.contains(override);
	}

	//****************** IJavaAttributeMapping implemenation *******************

	public String getKey() {
		return MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String annotationName() {
		return Embedded.ANNOTATION_NAME;
	}
	
	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.ATTRIBUTE_OVERRIDE,
			JPA.ATTRIBUTE_OVERRIDES);
	}
	
	@Override
	protected Embedded mappingResource() {
		return (Embedded) this.persistentAttributeResource.nonNullMappingAnnotation(annotationName());
	}
	
	//****************** IEmbeddedMapping implemenation *******************

	@SuppressWarnings("unchecked")
	public ListIterator<JavaAttributeOverride> attributeOverrides() {
		return new CompositeListIterator<JavaAttributeOverride>(specifiedAttributeOverrides(), defaultAttributeOverrides());
	}
		
	public int attributeOverridesSize() {
		return this.specifiedAttributeOverridesSize() + this.defaultAttributeOverridesSize();
	}

	public ListIterator<JavaAttributeOverride> defaultAttributeOverrides() {
		return new CloneListIterator<JavaAttributeOverride>(this.defaultAttributeOverrides);
	}
	
	public int defaultAttributeOverridesSize() {
		return this.defaultAttributeOverrides.size();
	}
	
	public ListIterator<JavaAttributeOverride> specifiedAttributeOverrides() {
		return new CloneListIterator<JavaAttributeOverride>(this.specifiedAttributeOverrides);
	}
	
	public int specifiedAttributeOverridesSize() {
		return this.specifiedAttributeOverrides.size();
	}

	public JavaAttributeOverride addSpecifiedAttributeOverride(int index) {
		JavaAttributeOverride attributeOverride = jpaFactory().buildJavaAttributeOverride(this, this);
		this.specifiedAttributeOverrides.add(index, attributeOverride);
		AttributeOverrideAnnotation attributeOverrideResource = (AttributeOverrideAnnotation) this.persistentAttributeResource.addAnnotation(index, AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		attributeOverride.initializeFromResource(attributeOverrideResource);
		this.fireItemAdded(EmbeddedMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, attributeOverride);
		return attributeOverride;
	}
	
	protected void addSpecifiedAttributeOverride(int index, JavaAttributeOverride attributeOverride) {
		addItemToList(index, attributeOverride, this.specifiedAttributeOverrides, EmbeddedMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	public void removeSpecifiedAttributeOverride(AttributeOverride attributeOverride) {
		removeSpecifiedAttributeOverride(this.specifiedAttributeOverrides.indexOf(attributeOverride));
	}
	
	public void removeSpecifiedAttributeOverride(int index) {
		JavaAttributeOverride removedAttributeOverride = this.specifiedAttributeOverrides.remove(index);

		//add the default attribute override so that I can control the order that change notification is sent.
		//otherwise when we remove the annotation from java we will get an update and add the attribute override
		//during the udpate.  This causes the UI to be flaky, since change notification might not occur in the correct order
		JavaAttributeOverride defaultAttributeOverride = null;
		if (removedAttributeOverride.getName() != null) {
			if (CollectionTools.contains(allOverridableAttributeNames(), removedAttributeOverride.getName())) {
				defaultAttributeOverride = createAttributeOverride(new NullAttributeOverride(this.persistentAttributeResource, removedAttributeOverride.getName()));
				this.defaultAttributeOverrides.add(defaultAttributeOverride);
			}
		}

		this.persistentAttributeResource.removeAnnotation(index, AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		fireItemRemoved(EmbeddedMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, removedAttributeOverride);
		
		if (defaultAttributeOverride != null) {
			fireItemAdded(EmbeddedMapping.DEFAULT_ATTRIBUTE_OVERRIDES_LIST, defaultAttributeOverridesSize() - 1, defaultAttributeOverride);
		}
	}
	
	protected void removeSpecifiedAttributeOverride_(JavaAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.specifiedAttributeOverrides, EmbeddedMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}

	public void moveSpecifiedAttributeOverride(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedAttributeOverrides, targetIndex, sourceIndex);
		this.persistentAttributeResource.move(targetIndex, sourceIndex, AttributeOverrides.ANNOTATION_NAME);
		fireItemMoved(EmbeddedMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, targetIndex, sourceIndex);		
	}
	
	protected void addDefaultAttributeOverride(JavaAttributeOverride attributeOverride) {
		addItemToList(attributeOverride, this.defaultAttributeOverrides, EmbeddedMapping.DEFAULT_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	protected void removeDefaultAttributeOverride(JavaAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.defaultAttributeOverrides, EmbeddedMapping.DEFAULT_ATTRIBUTE_OVERRIDES_LIST);
	}

	public JavaAttributeOverride attributeOverrideNamed(String name) {
		return (JavaAttributeOverride) overrideNamed(name, attributeOverrides());
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
	
	private BaseOverride overrideNamed(String name, ListIterator<? extends BaseOverride> overrides) {
		for (BaseOverride override : CollectionTools.iterable(overrides)) {
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

	private boolean containsOverride(String name, ListIterator<? extends BaseOverride> overrides) {
		return overrideNamed(name, overrides) != null;
	}

	public Embeddable embeddable() {
		return this.embeddable;
	}

	@Override
	public void update(JavaResourcePersistentAttribute persistentAttributeResource) {
		super.update(persistentAttributeResource);
		this.embeddable = embeddableFor(persistentAttribute());
		this.updateSpecifiedAttributeOverrides(persistentAttributeResource);
		this.updateDefaultAttributeOverrides(persistentAttributeResource);
		
	}
	protected void updateSpecifiedAttributeOverrides(JavaResourcePersistentAttribute persistentAttributeResource) {
		ListIterator<JavaAttributeOverride> attributeOverrides = specifiedAttributeOverrides();
		ListIterator<JavaResourceNode> resourceAttributeOverrides = persistentAttributeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		
		while (attributeOverrides.hasNext()) {
			JavaAttributeOverride attributeOverride = attributeOverrides.next();
			if (resourceAttributeOverrides.hasNext()) {
				attributeOverride.update((AttributeOverrideAnnotation) resourceAttributeOverrides.next());
			}
			else {
				removeSpecifiedAttributeOverride_(attributeOverride);
			}
		}
		
		while (resourceAttributeOverrides.hasNext()) {
			addSpecifiedAttributeOverride(specifiedAttributeOverridesSize(), createAttributeOverride((AttributeOverrideAnnotation) resourceAttributeOverrides.next()));
		}	
	}
	
	protected JavaAttributeOverride createAttributeOverride(AttributeOverrideAnnotation attributeOverrideResource) {
		JavaAttributeOverride attributeOverride = jpaFactory().buildJavaAttributeOverride(this, this);
		attributeOverride.initializeFromResource(attributeOverrideResource);
		return attributeOverride;
	}
	
	protected void updateDefaultAttributeOverrides(JavaResourcePersistentAttribute persistentAttributeResource) {
		for (Iterator<String> i = allOverridableAttributeNames(); i.hasNext(); ) {
			String attributeName = i.next();
			JavaAttributeOverride attributeOverride = attributeOverrideNamed(attributeName);
			if (attributeOverride == null) {
				attributeOverride = createAttributeOverride(new NullAttributeOverride(persistentAttributeResource, attributeName));
				addDefaultAttributeOverride(attributeOverride);
			}
			else if (attributeOverride.isVirtual()) {
				attributeOverride.update(new NullAttributeOverride(persistentAttributeResource, attributeName));
			}
		}
		
		Collection<String> attributeNames = CollectionTools.collection(allOverridableAttributeNames());
	
		//remove any default mappings that are not included in the attributeNames collection
		for (JavaAttributeOverride attributeOverride : CollectionTools.iterable(defaultAttributeOverrides())) {
			if (!attributeNames.contains(attributeOverride.getName())
				|| containsSpecifiedAttributeOverride(attributeOverride.getName())) {
				removeDefaultAttributeOverride(attributeOverride);
			}
		}
	}

	public Iterator<String> allOverridableAttributeNames() {
		return new TransformationIterator<PersistentAttribute, String>(this.allOverridableAttributes()) {
			@Override
			protected String transform(PersistentAttribute attribute) {
				return attribute.getName();
			}
		};
	}

	public Iterator<PersistentAttribute> allOverridableAttributes() {
		if (this.embeddable() == null) {
			return EmptyIterator.instance();
		}
		return new FilteringIterator<PersistentAttribute, PersistentAttribute>(this.embeddable().persistentType().attributes()) {
			@Override
			protected boolean accept(PersistentAttribute o) {
				return o.isOverridableAttribute();
			}
		};
	}

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (AttributeOverride override : CollectionTools.iterable(this.attributeOverrides())) {
			result = ((JavaAttributeOverride) override).javaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	
	
	//******** Validation ******************
	
	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
		
		for (Iterator<JavaAttributeOverride> stream = attributeOverrides(); stream.hasNext();) {
			stream.next().addToMessages(messages, astRoot);
		}
	}

	//******* static methods *********
	
	public static Embeddable embeddableFor(JavaPersistentAttribute persistentAttribute) {
		String qualifiedTypeName = persistentAttribute.getResourcePersistentAttribute().getQualifiedTypeName();
		if (qualifiedTypeName == null) {
			return null;
		}
		PersistentType persistentType = persistentAttribute.persistenceUnit().persistentType(qualifiedTypeName);
		if (persistentType != null) {
			if (persistentType.mappingKey() == MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY) {
				return (Embeddable) persistentType.getMapping();
			}
		}
		return null;
	}
	
	public static ColumnMapping columnMapping(String attributeName, Embeddable embeddable) {
		if (attributeName == null || embeddable == null) {
			return null;
		}
		for (Iterator<PersistentAttribute> stream = embeddable.persistentType().allAttributes(); stream.hasNext();) {
			PersistentAttribute persAttribute = stream.next();
			if (attributeName.equals(persAttribute.getName())) {
				if (persAttribute.getMapping() instanceof ColumnMapping) {
					return (ColumnMapping) persAttribute.getMapping();
				}
			}
		}
		return null;		
	}

}
