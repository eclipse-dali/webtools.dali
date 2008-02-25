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
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.resource.java.NullAttributeOverride;
import org.eclipse.jpt.core.internal.resource.java.NullColumn;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverrides;
import org.eclipse.jpt.core.resource.java.EmbeddedId;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class GenericJavaEmbeddedIdMapping extends AbstractJavaAttributeMapping
	implements JavaEmbeddedIdMapping
{
	protected final List<JavaAttributeOverride> specifiedAttributeOverrides;

	protected final List<JavaAttributeOverride> defaultAttributeOverrides;

	private Embeddable embeddable;

	public GenericJavaEmbeddedIdMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.specifiedAttributeOverrides = new ArrayList<JavaAttributeOverride>();
		this.defaultAttributeOverrides = new ArrayList<JavaAttributeOverride>();
	}

	//****************** IJavaAttributeMapping implemenation *******************

	public String getKey() {
		return MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String annotationName() {
		return EmbeddedId.ANNOTATION_NAME;
	}
	
	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.ATTRIBUTE_OVERRIDE,
			JPA.ATTRIBUTE_OVERRIDES);
	}

	@Override
	public boolean isIdMapping() {
		return true;
	}
	
	//****************** IOverride.Owner implemenation *******************
	public ColumnMapping columnMapping(String attributeName) {
		return GenericJavaEmbeddedMapping.columnMapping(attributeName, embeddable());
	}

	public boolean isVirtual(BaseOverride override) {
		return this.defaultAttributeOverrides.contains(override);
	}
	
	
	//****************** IEmbeddedMapping implemenation *******************

	@SuppressWarnings("unchecked")
	public ListIterator<JavaAttributeOverride> attributeOverrides() {
		return new CompositeListIterator<JavaAttributeOverride>(this.specifiedAttributeOverrides(), this.defaultAttributeOverrides());
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
		this.fireItemAdded(EmbeddedIdMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, attributeOverride);
		return attributeOverride;
	}
	
	protected void addSpecifiedAttributeOverride(int index, JavaAttributeOverride attributeOverride) {
		addItemToList(index, attributeOverride, this.specifiedAttributeOverrides, EmbeddedIdMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
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
		fireItemRemoved(EmbeddedIdMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, removedAttributeOverride);
	
		if (defaultAttributeOverride != null) {
			fireItemAdded(EmbeddedIdMapping.DEFAULT_ATTRIBUTE_OVERRIDES_LIST, defaultAttributeOverridesSize() - 1, defaultAttributeOverride);
		}
	}
	
	protected void removeSpecifiedAttributeOverride_(JavaAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.specifiedAttributeOverrides, EmbeddedIdMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}

	public void moveSpecifiedAttributeOverride(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedAttributeOverrides, targetIndex, sourceIndex);
		this.persistentAttributeResource.move(targetIndex, sourceIndex, AttributeOverrides.ANNOTATION_NAME);
		fireItemMoved(EmbeddedIdMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, targetIndex, sourceIndex);		
	}
	
	protected void addDefaultAttributeOverride(JavaAttributeOverride attributeOverride) {
		addItemToList(attributeOverride, this.defaultAttributeOverrides, EmbeddedIdMapping.DEFAULT_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	protected void removeDefaultAttributeOverride(JavaAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.defaultAttributeOverrides, EmbeddedIdMapping.DEFAULT_ATTRIBUTE_OVERRIDES_LIST);
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
	public void initializeFromResource(JavaResourcePersistentAttribute persistentAttributeResource) {
		super.initializeFromResource(persistentAttributeResource);
		this.initializeAttributeOverrides(persistentAttributeResource);
		this.initializeDefaultAttributeOverrides(persistentAttributeResource);
		this.embeddable = embeddableFor(persistentAttribute());
	}
	
	protected void initializeAttributeOverrides(JavaResourcePersistentAttribute persistentAttributeResource) {
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
	}	@Override
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
				attributeOverride.getColumn().update(new NullColumn(persistentAttributeResource));
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
	}
	
	//******* static methods *********
	
	protected static Embeddable embeddableFor(JavaPersistentAttribute persistentAttribute) {
		return GenericJavaEmbeddedMapping.embeddableFor(persistentAttribute);
	}

}