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
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AssociationOverridesAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaAssociationOverrideContainer extends AbstractJavaJpaContextNode
	implements JavaAssociationOverrideContainer
{
	protected JavaResourcePersistentMember javaResourcePersistentMember;

	protected final AssociationOverrideContainer.Owner owner;
	
	protected final List<JavaAssociationOverride> specifiedAssociationOverrides;

	protected final List<JavaAssociationOverride> virtualAssociationOverrides;
	
	public GenericJavaAssociationOverrideContainer(JavaJpaContextNode parent, AssociationOverrideContainer.Owner owner) {
		super(parent);
		this.owner = owner;
		this.specifiedAssociationOverrides = new ArrayList<JavaAssociationOverride>();
		this.virtualAssociationOverrides = new ArrayList<JavaAssociationOverride>();
	}

	public Owner getOwner() {
		return this.owner;
	}

	public JavaAssociationOverride getAssociationOverrideNamed(String name) {
		return (JavaAssociationOverride) getOverrideNamed(name, associationOverrides());
	}

	public boolean containsAssociationOverride(String name) {
		return containsOverride(name, associationOverrides());
	}

	public boolean containsSpecifiedAssociationOverride(String name) {
		return containsOverride(name, specifiedAssociationOverrides());
	}

	public boolean containsDefaultAssociationOverride(String name) {
		return containsOverride(name, virtualAssociationOverrides());
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


	@SuppressWarnings("unchecked")
	public ListIterator<JavaAssociationOverride> associationOverrides() {
		return new CompositeListIterator<JavaAssociationOverride>(specifiedAssociationOverrides(), virtualAssociationOverrides());
	}
	
	public int associationOverridesSize() {
		return this.specifiedAssociationOverridesSize() + this.virtualAssociationOverridesSize();
	}

	public  ListIterator<JavaAssociationOverride> virtualAssociationOverrides() {
		return new CloneListIterator<JavaAssociationOverride>(this.virtualAssociationOverrides);
	}
	
	public int virtualAssociationOverridesSize() {
		return this.virtualAssociationOverrides.size();
	}
	
	public ListIterator<JavaAssociationOverride> specifiedAssociationOverrides() {
		return new CloneListIterator<JavaAssociationOverride>(this.specifiedAssociationOverrides);
	}
	
	public int specifiedAssociationOverridesSize() {
		return this.specifiedAssociationOverrides.size();
	}

	public JavaAssociationOverride addSpecifiedAssociationOverride(int index) {
		JavaAssociationOverride associationOverride = getJpaFactory().buildJavaAssociationOverride(this, createAssociationOverrideOwner());
		this.specifiedAssociationOverrides.add(index, associationOverride);
		AssociationOverrideAnnotation associationOverrideResource = 
				(AssociationOverrideAnnotation) this.javaResourcePersistentMember.addAnnotation(
					index, AssociationOverrideAnnotation.ANNOTATION_NAME, 
					AssociationOverridesAnnotation.ANNOTATION_NAME);
		associationOverride.initialize(associationOverrideResource);
		this.fireItemAdded(SPECIFIED_ASSOCIATION_OVERRIDES_LIST, index, associationOverride);
		return associationOverride;
	}
	
	protected AssociationOverride.Owner createAssociationOverrideOwner() {
		return new AssociationOverrideOwner();
	}
	
	protected void addSpecifiedAssociationOverride(int index, JavaAssociationOverride associationOverride) {
		addItemToList(index, associationOverride, this.specifiedAssociationOverrides, SPECIFIED_ASSOCIATION_OVERRIDES_LIST);
	}
	
	protected void addSpecifiedAssociationOverride(JavaAssociationOverride associationOverride) {
		this.addSpecifiedAssociationOverride(this.specifiedAssociationOverrides.size(), associationOverride);
	}
	
	protected void removeSpecifiedAssociationOverride_(JavaAssociationOverride associationOverride) {
		removeItemFromList(associationOverride, this.specifiedAssociationOverrides, SPECIFIED_ASSOCIATION_OVERRIDES_LIST);
	}
	
	public void moveSpecifiedAssociationOverride(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedAssociationOverrides, targetIndex, sourceIndex);
		this.javaResourcePersistentMember.moveAnnotation(
				targetIndex, sourceIndex, AssociationOverridesAnnotation.ANNOTATION_NAME);
		fireItemMoved(SPECIFIED_ASSOCIATION_OVERRIDES_LIST, targetIndex, sourceIndex);		
	}

	protected JavaAssociationOverride setAssociationOverrideVirtual(boolean virtual, JavaAssociationOverride associationOverride) {
		// Add a new attribute override
		if (virtual) {
			return setAssociationOverrideVirtual(associationOverride);
		}
		return setAssociationOverrideSpecified(associationOverride);
	}
	
	protected JavaAssociationOverride setAssociationOverrideVirtual(JavaAssociationOverride associationOverride) {
		int index = this.specifiedAssociationOverrides.indexOf(associationOverride);
		this.specifiedAssociationOverrides.remove(index);
		String associationOverrideName = associationOverride.getName();
		//add the virtual attribute override so that I can control the order that change notification is sent.
		//otherwise when we remove the annotation from java we will get an update and add the attribute override
		//during the update.  This causes the UI to be flaky, since change notification might not occur in the correct order
		JavaAssociationOverride virtualAssociationOverride = null;
		if (associationOverrideName != null) {
			for (RelationshipMapping overridableAssociation : CollectionTools.iterable(getOwner().allOverridableAssociations())) {
				if (overridableAssociation.getName().equals(associationOverrideName)) {
					//store the virtualAttributeOverride so we can fire change notification later
					virtualAssociationOverride = buildVirtualAssociationOverride(overridableAssociation);
					this.virtualAssociationOverrides.add(virtualAssociationOverride);
					break;
				}
			}
		}

		this.javaResourcePersistentMember.removeAnnotation(
				index, AssociationOverrideAnnotation.ANNOTATION_NAME, 
				AssociationOverridesAnnotation.ANNOTATION_NAME);
		fireItemRemoved(SPECIFIED_ASSOCIATION_OVERRIDES_LIST, index, associationOverride);
		
		if (virtualAssociationOverride != null) {
			fireItemAdded(VIRTUAL_ASSOCIATION_OVERRIDES_LIST, virtualAssociationOverridesSize() - 1, virtualAssociationOverride);
		}
		return virtualAssociationOverride;
	}
	
	protected JavaAssociationOverride setAssociationOverrideSpecified(JavaAssociationOverride oldAssociationOverride) {
		int index = specifiedAssociationOverridesSize();
		JavaAssociationOverride newAssociationOverride = getJpaFactory().buildJavaAssociationOverride(this, createAssociationOverrideOwner());
		this.specifiedAssociationOverrides.add(index, newAssociationOverride);
		
		AssociationOverrideAnnotation attributeOverrideResource = 
				(AssociationOverrideAnnotation) this.javaResourcePersistentMember.addAnnotation(
					index, AssociationOverrideAnnotation.ANNOTATION_NAME, 
					AssociationOverridesAnnotation.ANNOTATION_NAME);
		newAssociationOverride.initialize(attributeOverrideResource);
		
		int virtualIndex = this.virtualAssociationOverrides.indexOf(oldAssociationOverride);
		this.virtualAssociationOverrides.remove(virtualIndex);

		newAssociationOverride.initializeFrom(oldAssociationOverride);
		
		this.fireItemRemoved(VIRTUAL_ASSOCIATION_OVERRIDES_LIST, virtualIndex, oldAssociationOverride);
		this.fireItemAdded(SPECIFIED_ASSOCIATION_OVERRIDES_LIST, index, newAssociationOverride);		

		return newAssociationOverride;
	}
	
	protected void addVirtualAssociationOverride(JavaAssociationOverride associationOverride) {
		addItemToList(associationOverride, this.virtualAssociationOverrides, VIRTUAL_ASSOCIATION_OVERRIDES_LIST);
	}
	
	protected void removeVirtualAssociationOverride(JavaAssociationOverride associationOverride) {
		removeItemFromList(associationOverride, this.virtualAssociationOverrides, VIRTUAL_ASSOCIATION_OVERRIDES_LIST);
	}
	

	
	public void initialize(JavaResourcePersistentMember resourcePersistentMember) {
		this.javaResourcePersistentMember = resourcePersistentMember;
		this.initializeSpecifiedAssociationOverrides();
		this.initializeVirtualAssociationOverrides();
	}	
	
	protected void initializeSpecifiedAssociationOverrides() {
		for (Iterator<NestableAnnotation> stream = 
				this.javaResourcePersistentMember.annotations(
					AssociationOverrideAnnotation.ANNOTATION_NAME, 
					AssociationOverridesAnnotation.ANNOTATION_NAME); 
				stream.hasNext(); ) {
			this.specifiedAssociationOverrides.add(
				buildAssociationOverride((AssociationOverrideAnnotation) stream.next()));
		}
	}
	
	protected void initializeVirtualAssociationOverrides() {
		for (RelationshipMapping overridableAssociation : CollectionTools.iterable(getOwner().allOverridableAssociations())) {
			JavaAssociationOverride associationOverride = getAssociationOverrideNamed(overridableAssociation.getName());
			if (associationOverride == null) {
				this.virtualAssociationOverrides.add(buildVirtualAssociationOverride(overridableAssociation));
			}
		}
	}
	
	public void update(JavaResourcePersistentMember resourcePersistentMember) {
		this.javaResourcePersistentMember = resourcePersistentMember;
		this.updateSpecifiedAssociationOverrides();
	}
	
	@Override
	public void postUpdate() {
		super.postUpdate();
		
		//In postUpdate because the joiningStrategy is not initialized on relationship mappings.
		//if we fix the issue that we do not initialize java mappings, but they instead get initialized
		//during the first update, then we can probably move this.
		updateVirtualAssociationOverrides(); 
	}
	

	protected void updateSpecifiedAssociationOverrides() {
		ListIterator<JavaAssociationOverride> associationOverrides = specifiedAssociationOverrides();
		Iterator<NestableAnnotation> resourceAssociationOverrides = 
				this.javaResourcePersistentMember.annotations(
					AssociationOverrideAnnotation.ANNOTATION_NAME, 
					AssociationOverridesAnnotation.ANNOTATION_NAME);
		
		while (associationOverrides.hasNext()) {
			JavaAssociationOverride associationOverride = associationOverrides.next();
			if (resourceAssociationOverrides.hasNext()) {
				associationOverride.update((AssociationOverrideAnnotation) resourceAssociationOverrides.next());
			}
			else {
				removeSpecifiedAssociationOverride_(associationOverride);
			}
		}
		
		while (resourceAssociationOverrides.hasNext()) {
			addSpecifiedAssociationOverride(buildAssociationOverride((AssociationOverrideAnnotation) resourceAssociationOverrides.next()));
		}	
	}
	
	protected JavaAssociationOverride buildAssociationOverride(AssociationOverrideAnnotation associationOverrideResource) {
		JavaAssociationOverride associationOverride = getJpaFactory().buildJavaAssociationOverride(this, createAssociationOverrideOwner());
		associationOverride.initialize(associationOverrideResource);
		return associationOverride;
	}
	
	protected JavaAssociationOverride buildVirtualAssociationOverride(RelationshipMapping overridableAssociation) {
		return buildAssociationOverride(buildVirtualAssociationOverrideAnnotation(overridableAssociation));
	}
	
	protected AssociationOverrideAnnotation buildVirtualAssociationOverrideAnnotation(RelationshipMapping overridableAssociation) {
		JoiningStrategy joiningStrategy = overridableAssociation.getRelationshipReference().getPredominantJoiningStrategy();
		return getJpaFactory().buildJavaVirtualAssociationOverrideAnnotation(this.javaResourcePersistentMember, overridableAssociation.getName(), joiningStrategy);
	}

	protected void updateVirtualAssociationOverrides() {
		for (RelationshipMapping overridableAssociation : CollectionTools.iterable(getOwner().allOverridableAssociations())) {
			JavaAssociationOverride associationOverride = getAssociationOverrideNamed(overridableAssociation.getName());
			if (associationOverride == null) {
				addVirtualAssociationOverride(buildVirtualAssociationOverride(overridableAssociation));
			}
			else if (associationOverride.isVirtual()) {
				associationOverride.update(buildVirtualAssociationOverrideAnnotation(overridableAssociation));
			}
		}
		
		Collection<String> associationNames = CollectionTools.collection(getOwner().allOverridableAssociationNames());
	
		//remove any default mappings that are not included in the associationNames collection
		for (JavaAssociationOverride associationOverride : CollectionTools.iterable(virtualAssociationOverrides())) {
			if (!associationNames.contains(associationOverride.getName())
				|| containsSpecifiedAssociationOverride(associationOverride.getName())) {
				removeVirtualAssociationOverride(associationOverride);
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
		for (JavaAssociationOverride override : CollectionTools.iterable(this.associationOverrides())) {
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
		
		for (Iterator<JavaAssociationOverride> stream = this.associationOverrides(); stream.hasNext();) {
			stream.next().validate(messages, reporter, astRoot);
		}
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.javaResourcePersistentMember.getTextRange(astRoot);
	}

	
	// ********** association override owner **********

	class AssociationOverrideOwner implements AssociationOverride.Owner {

		public RelationshipMapping getRelationshipMapping(String attributeName) {
			return MappingTools.getRelationshipMapping(attributeName, getOwner().getOverridableTypeMapping());
		}

		public boolean isVirtual(BaseOverride override) {
			return GenericJavaAssociationOverrideContainer.this.virtualAssociationOverrides.contains(override);
		}
		
		public BaseOverride setVirtual(boolean virtual, BaseOverride attributeOverride) {
			return GenericJavaAssociationOverrideContainer.this.setAssociationOverrideVirtual(virtual, (JavaAssociationOverride) attributeOverride);
		}

		public TypeMapping getTypeMapping() {
			return getOwner().getTypeMapping();
		}
	}

}
