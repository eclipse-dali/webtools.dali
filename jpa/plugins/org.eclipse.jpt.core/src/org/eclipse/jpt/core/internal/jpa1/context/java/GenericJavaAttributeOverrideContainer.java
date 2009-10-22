/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.internal.context.java.VirtualAttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverridesAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaAttributeOverrideContainer extends AbstractJavaJpaContextNode
	implements JavaAttributeOverrideContainer
{
	protected JavaResourcePersistentMember javaResourcePersistentMember;

	protected final List<JavaAttributeOverride> specifiedAttributeOverrides;

	protected final List<JavaAttributeOverride> virtualAttributeOverrides;
	
	protected final Owner owner;
	
	public GenericJavaAttributeOverrideContainer(JavaJpaContextNode parent, Owner owner) {
		super(parent);
		this.owner = owner;
		this.specifiedAttributeOverrides = new ArrayList<JavaAttributeOverride>();
		this.virtualAttributeOverrides = new ArrayList<JavaAttributeOverride>();
	}

	public Owner getOwner() {
		return this.owner;
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<JavaAttributeOverride> attributeOverrides() {
		return new CompositeListIterator<JavaAttributeOverride>(specifiedAttributeOverrides(), virtualAttributeOverrides());
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

	protected JavaAttributeOverride addSpecifiedAttributeOverride(int index) {
		JavaAttributeOverride attributeOverride = getJpaFactory().buildJavaAttributeOverride(this, createAttributeOverrideOwner());
		this.specifiedAttributeOverrides.add(index, attributeOverride);
		AttributeOverrideAnnotation attributeOverrideResource = 
				(AttributeOverrideAnnotation) this.javaResourcePersistentMember.addAnnotation(
					index, AttributeOverrideAnnotation.ANNOTATION_NAME, 
					AttributeOverridesAnnotation.ANNOTATION_NAME);
		attributeOverride.initialize(attributeOverrideResource);
		this.fireItemAdded(SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, attributeOverride);
		return attributeOverride;
	}
	
	protected JavaAttributeOverride setAttributeOverrideVirtual(boolean virtual, JavaAttributeOverride attributeOverride) {
		// Add a new attribute override
		if (virtual) {
			return setAttributeOverrideVirtual(attributeOverride);
		}
		return setAttributeOverrideSpecified(attributeOverride);
	}
	
	protected JavaAttributeOverride setAttributeOverrideVirtual(JavaAttributeOverride attributeOverride) {
		int index = this.specifiedAttributeOverrides.indexOf(attributeOverride);
		this.specifiedAttributeOverrides.remove(index);
		String attributeOverrideName = attributeOverride.getName();
		//add the virtual attribute override so that I can control the order that change notification is sent.
		//otherwise when we remove the annotation from java we will get an update and add the attribute override
		//during the update.  This causes the UI to be flaky, since change notification might not occur in the correct order
		JavaAttributeOverride virtualAttributeOverride = null;
		if (attributeOverrideName != null) {
			for (String name : CollectionTools.iterable(allOverridableAttributeNames())) {
				if (name.equals(attributeOverrideName)) {
					//store the virtualAttributeOverride so we can fire change notification later
					virtualAttributeOverride = buildVirtualAttributeOverride(name);
					this.virtualAttributeOverrides.add(virtualAttributeOverride);
					break;
				}
			}
		}

		this.javaResourcePersistentMember.removeAnnotation(
				index, AttributeOverrideAnnotation.ANNOTATION_NAME, 
				AttributeOverridesAnnotation.ANNOTATION_NAME);
		fireItemRemoved(SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, attributeOverride);
		
		if (virtualAttributeOverride != null) {
			fireItemAdded(VIRTUAL_ATTRIBUTE_OVERRIDES_LIST, virtualAttributeOverridesSize() - 1, virtualAttributeOverride);
		}
		return virtualAttributeOverride;
	}
	
	protected JavaAttributeOverride setAttributeOverrideSpecified(JavaAttributeOverride oldAttributeOverride) {
		int index = specifiedAttributeOverridesSize();
		JavaAttributeOverride newAttributeOverride = getJpaFactory().buildJavaAttributeOverride(this, createAttributeOverrideOwner());
		this.specifiedAttributeOverrides.add(index, newAttributeOverride);
		
		AttributeOverrideAnnotation attributeOverrideResource = 
				(AttributeOverrideAnnotation) this.javaResourcePersistentMember.addAnnotation(
					index, AttributeOverrideAnnotation.ANNOTATION_NAME, 
					AttributeOverridesAnnotation.ANNOTATION_NAME);
		newAttributeOverride.initialize(attributeOverrideResource);
		
		int defaultIndex = this.virtualAttributeOverrides.indexOf(oldAttributeOverride);
		this.virtualAttributeOverrides.remove(defaultIndex);

		newAttributeOverride.setName(oldAttributeOverride.getName());
		newAttributeOverride.getColumn().setSpecifiedName(oldAttributeOverride.getColumn().getName());
		
		this.fireItemRemoved(VIRTUAL_ATTRIBUTE_OVERRIDES_LIST, defaultIndex, oldAttributeOverride);
		this.fireItemAdded(SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, newAttributeOverride);		

		return newAttributeOverride;
	}
	
	protected AttributeOverride.Owner createAttributeOverrideOwner() {
		return new AttributeOverrideOwner();
	}
	
	protected void addSpecifiedAttributeOverride(int index, JavaAttributeOverride attributeOverride) {
		addItemToList(index, attributeOverride, this.specifiedAttributeOverrides, SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	protected void addSpecifiedAttributeOverride(JavaAttributeOverride attributeOverride) {
		this.addSpecifiedAttributeOverride(this.specifiedAttributeOverrides.size(), attributeOverride);
	}
	
	protected void removeSpecifiedAttributeOverride_(JavaAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.specifiedAttributeOverrides, SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}

	public void moveSpecifiedAttributeOverride(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedAttributeOverrides, targetIndex, sourceIndex);
		this.javaResourcePersistentMember.moveAnnotation(
				targetIndex, sourceIndex, AttributeOverridesAnnotation.ANNOTATION_NAME);
		fireItemMoved(SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, targetIndex, sourceIndex);		
	}
	
	protected void addVirtualAttributeOverride(JavaAttributeOverride attributeOverride) {
		addItemToList(attributeOverride, this.virtualAttributeOverrides, VIRTUAL_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	protected void removeVirtualAttributeOverride(JavaAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.virtualAttributeOverrides, VIRTUAL_ATTRIBUTE_OVERRIDES_LIST);
	}

	public JavaAttributeOverride getAttributeOverrideNamed(String name) {
		return (JavaAttributeOverride) getOverrideNamed(name, attributeOverrides());
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

	protected BaseOverride getOverrideNamed(String name, ListIterator<? extends BaseOverride> overrides) {
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

	protected boolean containsOverride(String name, ListIterator<? extends BaseOverride> overrides) {
		return getOverrideNamed(name, overrides) != null;
	}

	protected Iterator<String> allOverridableAttributeNames() {
		TypeMapping overridableTypeMapping = getOwner().getOverridableTypeMapping();
		if (overridableTypeMapping != null) {
			return overridableTypeMapping.allOverridableAttributeNames();
		}
		return EmptyIterator.instance();
	}

	
	public void initialize(JavaResourcePersistentMember resourcePersistentMember) {
		this.javaResourcePersistentMember = resourcePersistentMember;
		this.initializeSpecifiedAttributeOverrides();
		this.initializeVirtualAttributeOverrides();
	}
	
	protected void initializeSpecifiedAttributeOverrides() {
		for (Iterator<NestableAnnotation> stream = 
				this.javaResourcePersistentMember.annotations(
					AttributeOverrideAnnotation.ANNOTATION_NAME, 
					AttributeOverridesAnnotation.ANNOTATION_NAME); 
				stream.hasNext(); ) {
			this.specifiedAttributeOverrides.add(
				buildAttributeOverride((AttributeOverrideAnnotation) stream.next()));
		}
	}
	
	protected void initializeVirtualAttributeOverrides() {
		for (String name : CollectionTools.iterable(allOverridableAttributeNames())) {
			JavaAttributeOverride attributeOverride = getAttributeOverrideNamed(name);
			if (attributeOverride == null) {
				this.virtualAttributeOverrides.add(buildVirtualAttributeOverride(name));
			}
		}
	}
	
	public void update(JavaResourcePersistentMember resourcePersistentMember) {
		this.javaResourcePersistentMember = resourcePersistentMember;
		this.updateSpecifiedAttributeOverrides();
		this.updateVirtualAttributeOverrides();
	}
	
	protected void updateSpecifiedAttributeOverrides() {
		ListIterator<JavaAttributeOverride> attributeOverrides = specifiedAttributeOverrides();
		Iterator<NestableAnnotation> resourceAttributeOverrides = 
				this.javaResourcePersistentMember.annotations(
					AttributeOverrideAnnotation.ANNOTATION_NAME, 
					AttributeOverridesAnnotation.ANNOTATION_NAME);
		
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
			addSpecifiedAttributeOverride(buildAttributeOverride((AttributeOverrideAnnotation) resourceAttributeOverrides.next()));
		}	
	}
	
	protected JavaAttributeOverride buildAttributeOverride(AttributeOverrideAnnotation attributeOverrideResource) {
		JavaAttributeOverride attributeOverride = getJpaFactory().buildJavaAttributeOverride(this, createAttributeOverrideOwner());
		attributeOverride.initialize(attributeOverrideResource);
		return attributeOverride;
	}
	
	protected JavaAttributeOverride buildVirtualAttributeOverride(String attributeOverrideName) {
		return buildAttributeOverride(buildVirtualAttributeOverrideAnnotation(attributeOverrideName));
	}
	
	protected VirtualAttributeOverrideAnnotation buildVirtualAttributeOverrideAnnotation(String attributeOverrideName) {
		Column column = resolveOverridenColumn(attributeOverrideName);
		return new VirtualAttributeOverrideAnnotation(this.javaResourcePersistentMember, attributeOverrideName, column);
	}

	private Column resolveOverridenColumn(String attributeOverrideName) {
		return getOwner().resolveOverridenColumn(attributeOverrideName);
	}
	
	protected void updateVirtualAttributeOverrides() {
		for (String name : CollectionTools.iterable(allOverridableAttributeNames())) {
			JavaAttributeOverride attributeOverride = getAttributeOverrideNamed(name);
			if (attributeOverride == null) {
				addVirtualAttributeOverride(buildVirtualAttributeOverride(name));
			}
			else if (attributeOverride.isVirtual()) {
				attributeOverride.update(buildVirtualAttributeOverrideAnnotation(name));
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
	
	
	//******************** Code Completion *************************

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (JavaAttributeOverride override : CollectionTools.iterable(this.attributeOverrides())) {
			result = override.javaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	
	//********** Validation ********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		for (Iterator<JavaAttributeOverride> stream = this.attributeOverrides(); stream.hasNext();) {
			stream.next().validate(messages, reporter, astRoot);
		}
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.javaResourcePersistentMember.getTextRange(astRoot);
	}

	
	
	// ********** attribute override owner **********

	class AttributeOverrideOwner implements AttributeOverride.Owner {

		public Column resolveOverridenColumn(String attributeName) {
			if (attributeName == null) {
				return null;
			}
			return GenericJavaAttributeOverrideContainer.this.resolveOverridenColumn(attributeName);			
		}

		public boolean isVirtual(BaseOverride override) {
			return GenericJavaAttributeOverrideContainer.this.virtualAttributeOverrides.contains(override);
		}

		public BaseOverride setVirtual(boolean virtual, BaseOverride attributeOverride) {
			return GenericJavaAttributeOverrideContainer.this.setAttributeOverrideVirtual(virtual, (JavaAttributeOverride) attributeOverride);
		}

		public TypeMapping getTypeMapping() {
			return getOwner().getTypeMapping();
		}
	}

}
