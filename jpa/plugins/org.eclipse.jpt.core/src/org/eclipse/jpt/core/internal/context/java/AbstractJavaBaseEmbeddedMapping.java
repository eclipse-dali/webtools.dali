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
import org.eclipse.jpt.core.context.BaseEmbeddedMapping;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaBaseEmbeddedMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.resource.java.NullColumn;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverrides;
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


public abstract class AbstractJavaBaseEmbeddedMapping<T extends JavaResourceNode> extends AbstractJavaAttributeMapping<T>
	implements JavaBaseEmbeddedMapping
{
	protected final List<JavaAttributeOverride> specifiedAttributeOverrides;

	protected final List<JavaAttributeOverride> virtualAttributeOverrides;

	private Embeddable embeddable;

	protected AbstractJavaBaseEmbeddedMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.specifiedAttributeOverrides = new ArrayList<JavaAttributeOverride>();
		this.virtualAttributeOverrides = new ArrayList<JavaAttributeOverride>();
	}

	//****************** JavaAttributeMapping implemenation *******************

	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.ATTRIBUTE_OVERRIDE,
			JPA.ATTRIBUTE_OVERRIDES);
	}
	
	//****************** AttributeOverride.Owner implemenation *******************
	
	public ColumnMapping columnMapping(String attributeName) {
		return AbstractJavaBaseEmbeddedMapping.columnMapping(attributeName, embeddable());
	}

	public boolean isVirtual(BaseOverride override) {
		return this.virtualAttributeOverrides.contains(override);
	}
	
	public BaseOverride setVirtual(boolean virtual, BaseOverride override) {
		// Add a new attribute override
		if (virtual) {
			return setAttributeOverrideVirtual((JavaAttributeOverride) override);
		}
		return setAttributeOverrideSpecified((JavaAttributeOverride) override);
	}
	
	protected JavaAttributeOverride setAttributeOverrideVirtual(JavaAttributeOverride attributeOverride) {
		int index = this.specifiedAttributeOverrides.indexOf(attributeOverride);
		this.specifiedAttributeOverrides.remove(index);
		String attributeOverrideName = attributeOverride.getName();
		//add the virtual attribute override so that I can control the order that change notification is sent.
		//otherwise when we remove the annotation from java we will get an update and add the attribute override
		//during the udpate.  This causes the UI to be flaky, since change notification might not occur in the correct order
		JavaAttributeOverride virtualAttributeOverride = null;
		if (attributeOverrideName != null) {
			for (PersistentAttribute persistentAttribute : CollectionTools.iterable(allOverridableAttributes())) {
				if (persistentAttribute.getName().equals(attributeOverrideName)) {
					//store the virtualAttributeOverride so we can fire change notification later
					virtualAttributeOverride = buildVirtualAttributeOverride(getResourcePersistentAttribute(), persistentAttribute.getName());
					this.virtualAttributeOverrides.add(virtualAttributeOverride);
					break;
				}
			}
		}

		getResourcePersistentAttribute().removeAnnotation(index, AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		fireItemRemoved(Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, attributeOverride);
		
		if (virtualAttributeOverride != null) {
			fireItemAdded(Entity.VIRTUAL_ATTRIBUTE_OVERRIDES_LIST, virtualAttributeOverridesSize() - 1, virtualAttributeOverride);
		}
		return virtualAttributeOverride;
	}
	
	protected JavaAttributeOverride setAttributeOverrideSpecified(JavaAttributeOverride oldAttributeOverride) {
		int index = specifiedAttributeOverridesSize();
		JavaAttributeOverride newAttributeOverride = jpaFactory().buildJavaAttributeOverride(this, this);
		this.specifiedAttributeOverrides.add(index, newAttributeOverride);
		
		AttributeOverrideAnnotation attributeOverrideResource = (AttributeOverrideAnnotation) getResourcePersistentAttribute().addAnnotation(index, AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		newAttributeOverride.initializeFromResource(attributeOverrideResource);
		
		int defaultIndex = this.virtualAttributeOverrides.indexOf(oldAttributeOverride);
		this.virtualAttributeOverrides.remove(defaultIndex);

		newAttributeOverride.setName(oldAttributeOverride.getName());
		newAttributeOverride.getColumn().setSpecifiedName(oldAttributeOverride.getColumn().getName());
		
		this.fireItemRemoved(Entity.VIRTUAL_ATTRIBUTE_OVERRIDES_LIST, defaultIndex, oldAttributeOverride);
		this.fireItemAdded(Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, newAttributeOverride);		

		return newAttributeOverride;
	}

	
	//****************** BaseEmbeddedMapping implemenation *******************

	@SuppressWarnings("unchecked")
	public ListIterator<JavaAttributeOverride> attributeOverrides() {
		return new CompositeListIterator<JavaAttributeOverride>(this.specifiedAttributeOverrides(), this.virtualAttributeOverrides());
	}
	
	public int attributeOverridesSize() {
		return this.specifiedAttributeOverridesSize() + this.virtualAttributeOverridesSize();
	}
	
	public ListIterator<JavaAttributeOverride> virtualAttributeOverrides() {
		return new CloneListIterator<JavaAttributeOverride>(this.virtualAttributeOverrides);
	}
	
	public int virtualAttributeOverridesSize() {
		return this.virtualAttributeOverrides.size();
	}
	
	public ListIterator<JavaAttributeOverride> specifiedAttributeOverrides() {
		return new CloneListIterator<JavaAttributeOverride>(this.specifiedAttributeOverrides);
	}
	
	public int specifiedAttributeOverridesSize() {
		return this.specifiedAttributeOverrides.size();
	}
	
	protected void addSpecifiedAttributeOverride(int index, JavaAttributeOverride attributeOverride) {
		addItemToList(index, attributeOverride, this.specifiedAttributeOverrides, BaseEmbeddedMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}

	protected void removeSpecifiedAttributeOverride_(JavaAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.specifiedAttributeOverrides, BaseEmbeddedMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}

	public void moveSpecifiedAttributeOverride(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedAttributeOverrides, targetIndex, sourceIndex);
		getResourcePersistentAttribute().move(targetIndex, sourceIndex, AttributeOverrides.ANNOTATION_NAME);
		fireItemMoved(BaseEmbeddedMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, targetIndex, sourceIndex);		
	}
	
	protected void addVirtualAttributeOverride(JavaAttributeOverride attributeOverride) {
		addItemToList(attributeOverride, this.virtualAttributeOverrides, BaseEmbeddedMapping.VIRTUAL_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	protected void removeVirtualAttributeOverride(JavaAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.virtualAttributeOverrides, BaseEmbeddedMapping.VIRTUAL_ATTRIBUTE_OVERRIDES_LIST);
	}

	public JavaAttributeOverride attributeOverrideNamed(String name) {
		return (JavaAttributeOverride) overrideNamed(name, attributeOverrides());
	}

	public boolean containsAttributeOverride(String name) {
		return containsOverride(name, attributeOverrides());
	}

	public boolean containsDefaultAttributeOverride(String name) {
		return containsOverride(name, virtualAttributeOverrides());
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
	public void initializeFromResource(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		super.initializeFromResource(resourcePersistentAttribute);
		this.initializeAttributeOverrides(resourcePersistentAttribute);
		this.initializeDefaultAttributeOverrides(resourcePersistentAttribute);
		this.embeddable = embeddableFor(persistentAttribute());
	}
	
	protected void initializeAttributeOverrides(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		ListIterator<JavaResourceNode> annotations = resourcePersistentAttribute.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		
		while(annotations.hasNext()) {
			JavaAttributeOverride attributeOverride = jpaFactory().buildJavaAttributeOverride(this, this);
			attributeOverride.initializeFromResource((AttributeOverrideAnnotation) annotations.next());
			this.specifiedAttributeOverrides.add(attributeOverride);
		}
	}
	
	protected void initializeDefaultAttributeOverrides(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		for (Iterator<String> i = allOverridableAttributeNames(); i.hasNext(); ) {
			String attributeName = i.next();
			JavaAttributeOverride attributeOverride = attributeOverrideNamed(attributeName);
			if (attributeOverride == null) {
				this.virtualAttributeOverrides.add(buildVirtualAttributeOverride(resourcePersistentAttribute, attributeName));
			}
		}
	}	@Override
	public void update(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		super.update(resourcePersistentAttribute);
		this.embeddable = embeddableFor(persistentAttribute());
		this.updateSpecifiedAttributeOverrides(resourcePersistentAttribute);
		this.updateVirtualAttributeOverrides(resourcePersistentAttribute);
		
	}
	protected void updateSpecifiedAttributeOverrides(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		ListIterator<JavaAttributeOverride> attributeOverrides = specifiedAttributeOverrides();
		ListIterator<JavaResourceNode> resourceAttributeOverrides = resourcePersistentAttribute.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		
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
			addSpecifiedAttributeOverride(specifiedAttributeOverridesSize(), buildAttributeOverride((AttributeOverrideAnnotation) resourceAttributeOverrides.next()));
		}	
	}
	
	protected JavaAttributeOverride buildAttributeOverride(AttributeOverrideAnnotation attributeOverrideResource) {
		JavaAttributeOverride attributeOverride = jpaFactory().buildJavaAttributeOverride(this, this);
		attributeOverride.initializeFromResource(attributeOverrideResource);
		return attributeOverride;
	}
	
	protected JavaAttributeOverride buildVirtualAttributeOverride(JavaResourcePersistentAttribute resourcePersistentAttribute, String attributeName) {
		return buildAttributeOverride(buildVirtualAttributeOverrideResource(resourcePersistentAttribute, attributeName));
	}

	protected VirtualAttributeOverride buildVirtualAttributeOverrideResource(JavaResourcePersistentAttribute resourcePersistentAttribute, String attributeName) {
		ColumnMapping columnMapping = (ColumnMapping) this.embeddable().persistentType().attributeNamed(attributeName).getMapping();
		return new VirtualAttributeOverride(resourcePersistentAttribute, attributeName, columnMapping.getColumn());
	}

	protected void updateVirtualAttributeOverrides(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		for (Iterator<String> i = allOverridableAttributeNames(); i.hasNext(); ) {
			String attributeName = i.next();
			JavaAttributeOverride attributeOverride = attributeOverrideNamed(attributeName);
			if (attributeOverride == null) {
				addVirtualAttributeOverride(buildVirtualAttributeOverride(resourcePersistentAttribute, attributeName));
			}
			else if (attributeOverride.isVirtual()) {
				attributeOverride.getColumn().update(new NullColumn(resourcePersistentAttribute));
			}
		}
		
		Collection<String> attributeNames = CollectionTools.collection(allOverridableAttributeNames());
	
		//remove any default mappings that are not included in the attributeNames collection
		for (JavaAttributeOverride attributeOverride : CollectionTools.iterable(virtualAttributeOverrides())) {
			if (!attributeNames.contains(attributeOverride.getName())
				|| containsSpecifiedAttributeOverride(attributeOverride.getName())) {
				removeVirtualAttributeOverride(attributeOverride);
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
		PersistentType persistentType = persistentAttribute.getPersistenceUnit().persistentType(qualifiedTypeName);
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