/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverrideContainer;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmAssociationOverrideContainer extends AbstractOrmXmlContextNode
	implements OrmAssociationOverrideContainer
{
	protected final XmlAssociationOverrideContainer resourceAssociationOverrideContainer;
	
	protected final List<OrmAssociationOverride> specifiedAssociationOverrides;

	protected final List<OrmAssociationOverride> virtualAssociationOverrides;

	protected final OrmAssociationOverrideContainer.Owner owner;
	
	public GenericOrmAssociationOverrideContainer(XmlContextNode parent, OrmAssociationOverrideContainer.Owner owner, XmlAssociationOverrideContainer resource) {
		super(parent);
		this.owner = owner;
		this.resourceAssociationOverrideContainer = resource;
		this.specifiedAssociationOverrides = new ArrayList<OrmAssociationOverride>();
		this.virtualAssociationOverrides = new ArrayList<OrmAssociationOverride>();
		this.initializeSpecifiedAssociationOverrides();
		this.initializeVirtualAssociationOverrides();
	}

	public Owner getOwner() {
		return this.owner;
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<OrmAssociationOverride> associationOverrides() {
		return new CompositeListIterator<OrmAssociationOverride>(specifiedAssociationOverrides(), virtualAssociationOverrides());
	}

	public int associationOverridesSize() {
		return this.specifiedAssociationOverridesSize() + this.virtualAssociationOverridesSize();
	}

	public ListIterator<OrmAssociationOverride> virtualAssociationOverrides() {
		return new CloneListIterator<OrmAssociationOverride>(this.virtualAssociationOverrides);
	}
	
	public int virtualAssociationOverridesSize() {
		return this.virtualAssociationOverrides.size();
	}
	
	protected void addVirtualAssociationOverride(OrmAssociationOverride associationOverride) {
		addItemToList(associationOverride, this.virtualAssociationOverrides, VIRTUAL_ASSOCIATION_OVERRIDES_LIST);
	}
	
	protected void removeVirtualAssociationOverride(OrmAssociationOverride associationOverride) {
		removeItemFromList(associationOverride, this.virtualAssociationOverrides, VIRTUAL_ASSOCIATION_OVERRIDES_LIST);
	}
	
	protected OrmAssociationOverride setAssociationOverrideVirtual(boolean virtual, OrmAssociationOverride associationOverride) {
		if (virtual) {
			return setAssociationOverrideVirtual(associationOverride);
		}
		return setAssociationOverrideSpecified(associationOverride);
	}
	
	protected OrmAssociationOverride setAssociationOverrideVirtual(OrmAssociationOverride associationOverride) {
		int index = this.specifiedAssociationOverrides.indexOf(associationOverride);
		this.specifiedAssociationOverrides.remove(index);
		String associationOverrideName = associationOverride.getName();
		//add the virtual attribute override so that I can control the order that change notification is sent.
		//otherwise when we remove the annotation from java we will get an update and add the attribute override
		//during the udpate.  This causes the UI to be flaky, since change notification might not occur in the correct order
		OrmAssociationOverride virtualAssociationOverride = null;
		if (associationOverrideName != null) {
			for (String name : CollectionTools.iterable(allOverridableAssociationNames())) {
				if (name.equals(associationOverrideName)) {
					//store the virtualAssociationOverride so we can fire change notification later
					virtualAssociationOverride = buildVirtualAssociationOverride(name);
					this.virtualAssociationOverrides.add(virtualAssociationOverride);
				}
			}
		}

		this.resourceAssociationOverrideContainer.getAssociationOverrides().remove(index);
		fireItemRemoved(SPECIFIED_ASSOCIATION_OVERRIDES_LIST, index, associationOverride);
		
		if (virtualAssociationOverride != null) {
			fireItemAdded(VIRTUAL_ASSOCIATION_OVERRIDES_LIST, virtualAssociationOverridesSize() - 1, virtualAssociationOverride);
		}
		return virtualAssociationOverride;
	}
	
	protected OrmAssociationOverride setAssociationOverrideSpecified(OrmAssociationOverride oldAssociationOverride) {
		int index = specifiedAssociationOverridesSize();
		XmlAssociationOverride xmlAssociationOverride = buildResourceAssociationOverride();
		OrmAssociationOverride newAssociationOverride = buildAssociationOverride(xmlAssociationOverride);
		this.specifiedAssociationOverrides.add(index, newAssociationOverride);
		
		this.resourceAssociationOverrideContainer.getAssociationOverrides().add(xmlAssociationOverride);
		
		int defaultIndex = this.virtualAssociationOverrides.indexOf(oldAssociationOverride);
		this.virtualAssociationOverrides.remove(defaultIndex);

		newAssociationOverride.initializeFrom(oldAssociationOverride);
		
		this.fireItemRemoved(VIRTUAL_ASSOCIATION_OVERRIDES_LIST, defaultIndex, oldAssociationOverride);
		this.fireItemAdded(SPECIFIED_ASSOCIATION_OVERRIDES_LIST, index, newAssociationOverride);		

		return newAssociationOverride;
	}
	
	public ListIterator<OrmAssociationOverride> specifiedAssociationOverrides() {
		return new CloneListIterator<OrmAssociationOverride>(this.specifiedAssociationOverrides);
	}

	public int specifiedAssociationOverridesSize() {
		return this.specifiedAssociationOverrides.size();
	}

	protected void addSpecifiedAssociationOverride(int index, OrmAssociationOverride associationOverride) {
		addItemToList(index, associationOverride, this.specifiedAssociationOverrides, SPECIFIED_ASSOCIATION_OVERRIDES_LIST);
	}
	
	protected void addSpecifiedAssociationOverride(OrmAssociationOverride associationOverride) {
		this.addSpecifiedAssociationOverride(this.specifiedAssociationOverrides.size(), associationOverride);
	}
	
	protected void removeSpecifiedAssociationOverride_(OrmAssociationOverride associationOverride) {
		removeItemFromList(associationOverride, this.specifiedAssociationOverrides, SPECIFIED_ASSOCIATION_OVERRIDES_LIST);
	}
	
	public void moveSpecifiedAssociationOverride(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedAssociationOverrides, targetIndex, sourceIndex);
		this.resourceAssociationOverrideContainer.getAssociationOverrides().move(targetIndex, sourceIndex);
		fireItemMoved(SPECIFIED_ASSOCIATION_OVERRIDES_LIST, targetIndex, sourceIndex);		
	}

	public OrmAssociationOverride getAssociationOverrideNamed(String name) {
		return (OrmAssociationOverride) getOverrideNamed(name, associationOverrides());
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

	private BaseOverride getOverrideNamed(String name, ListIterator<? extends BaseOverride> overrides) {
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
		return getOverrideNamed(name, overrides) != null;
	}
	

	protected Iterator<String> allOverridableAssociationNames() {
		TypeMapping overridableTypeMapping = getOwner().getOverridableTypeMapping();
		if (overridableTypeMapping != null) {
			return overridableTypeMapping.allOverridableAssociationNames();
		}
		return EmptyIterator.instance();
	}
	
	protected void initializeVirtualAssociationOverrides() {
		for (String name : CollectionTools.iterable(allOverridableAssociationNames())) {
			OrmAssociationOverride ormAssociationOverride = getAssociationOverrideNamed(name);
			if (ormAssociationOverride == null) {
				this.virtualAssociationOverrides.add(buildVirtualAssociationOverride(name));
			}
		}
	}

	protected OrmAssociationOverride buildVirtualAssociationOverride(String name) {
		return buildAssociationOverride(buildVirtualXmlAssociationOverride(name));
	}
	
	protected XmlAssociationOverride buildVirtualXmlAssociationOverride(String name) {
		RelationshipReference relationshipReference = this.resolveAssociationOverrideRelationshipReference(name);
		return buildVirtualXmlAssociationOverride(name, relationshipReference.getPredominantJoiningStrategy());
	}
	
	protected XmlAssociationOverride buildVirtualXmlAssociationOverride(String name, JoiningStrategy joiningStrategy) {
		return getXmlContextNodeFactory().buildVirtualXmlAssociationOverride(name, getOwner().getTypeMapping(), joiningStrategy);
	}
	
	private RelationshipReference resolveAssociationOverrideRelationshipReference(String associationOverrideName) {
		return getOwner().resolveRelationshipReference(associationOverrideName);
	}


	protected void initializeSpecifiedAssociationOverrides() {
		for (XmlAssociationOverride associationOverride : this.resourceAssociationOverrideContainer.getAssociationOverrides()) {
			this.specifiedAssociationOverrides.add(buildAssociationOverride(associationOverride));
		}
	}
	
	public void update() {
		this.updateSpecifiedAssociationOverrides();
		this.updateVirtualAssociationOverrides();
	}

	protected void updateSpecifiedAssociationOverrides() {
		// make a copy of the XML overrides (to prevent ConcurrentModificationException)
		Iterator<XmlAssociationOverride> xmlOverrides = new CloneIterator<XmlAssociationOverride>(this.resourceAssociationOverrideContainer.getAssociationOverrides());
		
		for (Iterator<OrmAssociationOverride> contextOverrides = this.specifiedAssociationOverrides(); contextOverrides.hasNext(); ) {
			OrmAssociationOverride contextOverride = contextOverrides.next();
			if (xmlOverrides.hasNext()) {
				contextOverride.update(xmlOverrides.next());
			}
			else {
				removeSpecifiedAssociationOverride_(contextOverride);
			}
		}
		
		while (xmlOverrides.hasNext()) {
			addSpecifiedAssociationOverride(buildAssociationOverride(xmlOverrides.next()));
		}
	}
	
	protected void updateVirtualAssociationOverrides() {
		Iterator<String> overridableAssociations = allOverridableAssociationNames();
		ListIterator<OrmAssociationOverride> virtualAssociationOverridesCopy = virtualAssociationOverrides();
		
		for (String name : CollectionTools.iterable(overridableAssociations)) {
			OrmAssociationOverride ormAssociationOverride = getAssociationOverrideNamed(name);
			if (ormAssociationOverride != null && !ormAssociationOverride.isVirtual()) {
				continue;
			}
			if (ormAssociationOverride != null) {
				if (virtualAssociationOverridesCopy.hasNext()) {
					OrmAssociationOverride virtualAssociationOverride = virtualAssociationOverridesCopy.next();
					virtualAssociationOverride.update(buildVirtualXmlAssociationOverride(name));
				}
				else {
					addVirtualAssociationOverride(buildVirtualAssociationOverride(name));
				}
			}
			else {
				addVirtualAssociationOverride(buildVirtualAssociationOverride(name));
			}
		}
		for (OrmAssociationOverride virtualAssociationOverride : CollectionTools.iterable(virtualAssociationOverridesCopy)) {
			removeVirtualAssociationOverride(virtualAssociationOverride);
		}
	}
	
	protected OrmAssociationOverride buildAssociationOverride(XmlAssociationOverride associationOverride) {
		return getXmlContextNodeFactory().buildOrmAssociationOverride(this, buildAssociationOverrideOwner(), associationOverride);
	}

	protected XmlAssociationOverride buildResourceAssociationOverride() {
		return OrmFactory.eINSTANCE.createXmlAssociationOverride();
	}
	
	protected AssociationOverride.Owner buildAssociationOverrideOwner() {
		return new AssociationOverrideOwner();
	}
	
	//************ validation ***************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		for (Iterator<OrmAssociationOverride> stream = this.associationOverrides(); stream.hasNext(); ) {
			stream.next().validate(messages, reporter);
		}
	}

	public TextRange getValidationTextRange() {
		return this.resourceAssociationOverrideContainer.getValidationTextRange();
	}
	
	
	
	class AssociationOverrideOwner implements AssociationOverride.Owner {

		public RelationshipMapping getRelationshipMapping(String attributeName) {
			return MappingTools.getRelationshipMapping(attributeName, getOwner().getOverridableTypeMapping());
		}

		public boolean isVirtual(BaseOverride override) {
			return GenericOrmAssociationOverrideContainer.this.virtualAssociationOverrides.contains(override);
		}

		public BaseOverride setVirtual(boolean virtual, BaseOverride override) {
			return GenericOrmAssociationOverrideContainer.this.setAssociationOverrideVirtual(virtual, (OrmAssociationOverride) override);
		}

		public TypeMapping getTypeMapping() {
			return getOwner().getTypeMapping();
		}

		public boolean tableIsAllowed() {
			return getOwner().tableIsAllowed();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return getOwner().tableNameIsInvalid(tableName);
		}

		public String getDefaultTableName() {
			return getOwner().getDefaultTableName();
		}

		public Table getDbTable(String tableName) {
			return getOwner().getDbTable(tableName);
		}
	}
}
