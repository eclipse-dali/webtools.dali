/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlAttributeOverride;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlAttributeOverrideColumn;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlColumn;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverrideContainer;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmAttributeOverrideContainer extends AbstractOrmXmlContextNode
	implements OrmAttributeOverrideContainer
{
	private final XmlAttributeOverrideContainer resourceAttributeOverrideContainer;
	
	protected final List<OrmAttributeOverride> specifiedAttributeOverrides;

	protected final List<OrmAttributeOverride> virtualAttributeOverrides;


	public GenericOrmAttributeOverrideContainer(XmlContextNode parent, XmlAttributeOverrideContainer resource) {
		super(parent);
		this.resourceAttributeOverrideContainer = resource;
		this.specifiedAttributeOverrides = new ArrayList<OrmAttributeOverride>();
		this.virtualAttributeOverrides = new ArrayList<OrmAttributeOverride>();
		this.initializeSpecifiedAttributeOverrides();
		this.initializeVirtualAttributeOverrides();
	}
	
	//TODO need a Owner object since the parent could be an Embedded or an Entity
	@Override
	public OrmEntity getParent() {
		return (OrmEntity) super.getParent();
	}
	
	protected JavaAttributeOverride getJavaAttributeOverrideName(String attributeName) {
		return getParent().getJavaEntity().getAttributeOverrideContainer().getAttributeOverrideNamed(attributeName);
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<OrmAttributeOverride> attributeOverrides() {
		return new CompositeListIterator<OrmAttributeOverride>(specifiedAttributeOverrides(), virtualAttributeOverrides());
	}

	public int attributeOverridesSize() {
		return this.specifiedAttributeOverridesSize() + this.virtualAttributeOverridesSize();
	}
	
	public ListIterator<OrmAttributeOverride> virtualAttributeOverrides() {
		return new CloneListIterator<OrmAttributeOverride>(this.virtualAttributeOverrides);
	}
	
	public int virtualAttributeOverridesSize() {
		return this.virtualAttributeOverrides.size();
	}
	
	protected void addVirtualAttributeOverride(OrmAttributeOverride attributeOverride) {
		addItemToList(attributeOverride, this.virtualAttributeOverrides, VIRTUAL_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	protected void removeVirtualAttributeOverride(OrmAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.virtualAttributeOverrides, VIRTUAL_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	protected OrmAttributeOverride setAttributeOverrideVirtual(boolean virtual, OrmAttributeOverride attributeOverride) {
		if (virtual) {
			return setAttributeOverrideVirtual(attributeOverride);
		}
		return setAttributeOverrideSpecified(attributeOverride);
	}
	
	protected OrmAttributeOverride setAttributeOverrideVirtual(OrmAttributeOverride attributeOverride) {
		int index = this.specifiedAttributeOverrides.indexOf(attributeOverride);
		this.specifiedAttributeOverrides.remove(index);
		String attributeOverrideName = attributeOverride.getName();
		//add the virtual attribute override so that I can control the order that change notification is sent.
		//otherwise when we remove the annotation from java we will get an update and add the attribute override
		//during the udpate.  This causes the UI to be flaky, since change notification might not occur in the correct order
		OrmAttributeOverride virtualAttributeOverride = null;
		if (attributeOverrideName != null) {
			for (PersistentAttribute persistentAttribute : CollectionTools.iterable(getParent().allOverridableAttributes())) {
				if (persistentAttribute.getName().equals(attributeOverrideName)) {
					JavaAttributeOverride javaAttributeOverride = null;
					if (getParent().getJavaEntity() != null) {
						javaAttributeOverride = getJavaAttributeOverrideName(attributeOverrideName);
					}
					//store the virtualAttributeOverride so we can fire change notification later
					virtualAttributeOverride = buildVirtualAttributeOverride(persistentAttribute, javaAttributeOverride);
					this.virtualAttributeOverrides.add(virtualAttributeOverride);
				}
			}
		}

		this.resourceAttributeOverrideContainer.getAttributeOverrides().remove(index);
		fireItemRemoved(SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, attributeOverride);
		
		if (virtualAttributeOverride != null) {
			fireItemAdded(VIRTUAL_ATTRIBUTE_OVERRIDES_LIST, virtualAttributeOverridesSize() - 1, virtualAttributeOverride);
		}
		return virtualAttributeOverride;
	}
	
	protected OrmAttributeOverride setAttributeOverrideSpecified(OrmAttributeOverride oldAttributeOverride) {
		int index = specifiedAttributeOverridesSize();
		XmlAttributeOverride xmlAttributeOverride = OrmFactory.eINSTANCE.createXmlAttributeOverride();
		OrmAttributeOverride newAttributeOverride = getXmlContextNodeFactory().buildOrmAttributeOverride(this, createAttributeOverrideOwner(), xmlAttributeOverride);
		this.specifiedAttributeOverrides.add(index, newAttributeOverride);
		
		this.resourceAttributeOverrideContainer.getAttributeOverrides().add(xmlAttributeOverride);
		
		int defaultIndex = this.virtualAttributeOverrides.indexOf(oldAttributeOverride);
		this.virtualAttributeOverrides.remove(defaultIndex);

		newAttributeOverride.setName(oldAttributeOverride.getName());
		newAttributeOverride.getColumn().setSpecifiedName(oldAttributeOverride.getColumn().getName());
		
		this.fireItemRemoved(VIRTUAL_ATTRIBUTE_OVERRIDES_LIST, defaultIndex, oldAttributeOverride);
		this.fireItemAdded(SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, newAttributeOverride);		

		return newAttributeOverride;
	}

	public ListIterator<OrmAttributeOverride> specifiedAttributeOverrides() {
		return new CloneListIterator<OrmAttributeOverride>(this.specifiedAttributeOverrides);
	}

	public int specifiedAttributeOverridesSize() {
		return this.specifiedAttributeOverrides.size();
	}

	protected void addSpecifiedAttributeOverride(int index, OrmAttributeOverride attributeOverride) {
		addItemToList(index, attributeOverride, this.specifiedAttributeOverrides, SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	protected void addSpecifiedAttributeOverride(OrmAttributeOverride attributeOverride) {
		this.addSpecifiedAttributeOverride(this.specifiedAttributeOverrides.size(), attributeOverride);
	}
	
	protected void removeSpecifiedAttributeOverride_(OrmAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.specifiedAttributeOverrides, SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	public void moveSpecifiedAttributeOverride(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedAttributeOverrides, targetIndex, sourceIndex);
		this.resourceAttributeOverrideContainer.getAttributeOverrides().move(targetIndex, sourceIndex);
		fireItemMoved(SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, targetIndex, sourceIndex);		
	}

	public OrmAttributeOverride getAttributeOverrideNamed(String name) {
		return (OrmAttributeOverride) getOverrideNamed(name, attributeOverrides());
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
	
	
	
	
	protected void initializeVirtualAttributeOverrides() {
		for (PersistentAttribute persistentAttribute : CollectionTools.iterable(getParent().allOverridableAttributes())) {
			OrmAttributeOverride ormAttributeOverride = getAttributeOverrideNamed(persistentAttribute.getName());
			if (ormAttributeOverride == null) {
				JavaAttributeOverride javaAttributeOverride = null;
				if (getParent().getJavaEntity() != null) {
					javaAttributeOverride = getJavaAttributeOverrideName(persistentAttribute.getName());
				}
				this.virtualAttributeOverrides.add(buildVirtualAttributeOverride(persistentAttribute, javaAttributeOverride));
			}
		}
	}
	
	protected void initializeSpecifiedAttributeOverrides() {
		for (XmlAttributeOverride attributeOverride : this.resourceAttributeOverrideContainer.getAttributeOverrides()) {
			this.specifiedAttributeOverrides.add(buildAttributeOverride(attributeOverride));
		}
	}
	
	public void update() {
		this.updateSpecifiedAttributeOverrides();
		this.updateVirtualAttributeOverrides();
	}

	protected OrmAttributeOverride buildVirtualAttributeOverride(PersistentAttribute persistentAttribute, JavaAttributeOverride javaAttributeOverride) {
		return buildAttributeOverride(buildVirtualXmlAttributeOverride(persistentAttribute, javaAttributeOverride));
	}
	
	protected XmlAttributeOverride buildVirtualXmlAttributeOverride(PersistentAttribute persistentAttribute, JavaAttributeOverride javaAttributeOverride) {
		XmlColumn xmlColumn;
		if (javaAttributeOverride == null) {
			ColumnMapping columnMapping = (ColumnMapping) persistentAttribute.getMapping();
			xmlColumn = new VirtualXmlAttributeOverrideColumn(columnMapping.getColumn());
		}
		else {
			xmlColumn = new VirtualXmlColumn(getParent(), javaAttributeOverride.getColumn());
		}
		return new VirtualXmlAttributeOverride(persistentAttribute.getName(), xmlColumn);
	}


	protected void updateSpecifiedAttributeOverrides() {
		// make a copy of the XML overrides (to prevent ConcurrentModificationException)
		Iterator<XmlAttributeOverride> xmlOverrides = new CloneIterator<XmlAttributeOverride>(this.resourceAttributeOverrideContainer.getAttributeOverrides());
		
		for (Iterator<OrmAttributeOverride> contextOverrides = this.specifiedAttributeOverrides(); contextOverrides.hasNext(); ) {
			OrmAttributeOverride contextOverride = contextOverrides.next();
			if (xmlOverrides.hasNext()) {
				contextOverride.update(xmlOverrides.next());
			}
			else {
				removeSpecifiedAttributeOverride_(contextOverride);
			}
		}
		
		while (xmlOverrides.hasNext()) {
			addSpecifiedAttributeOverride(buildAttributeOverride(xmlOverrides.next()));
		}
	}
	
	protected void updateVirtualAttributeOverrides() {
		Iterator<PersistentAttribute> overridableAttributes = getParent().allOverridableAttributes();
		ListIterator<OrmAttributeOverride> virtualAttributeOverridesCopy = virtualAttributeOverrides();
		
		for (PersistentAttribute persistentAttribute : CollectionTools.iterable(overridableAttributes)) {
			OrmAttributeOverride ormAttributeOverride = getAttributeOverrideNamed(persistentAttribute.getName());
			if (ormAttributeOverride != null && !ormAttributeOverride.isVirtual()) {
				continue;
			}
			JavaAttributeOverride javaAttributeOverride = null;
			if (getParent().getJavaEntity() != null) {
				javaAttributeOverride = getJavaAttributeOverrideName(persistentAttribute.getName());
			}
			if (ormAttributeOverride != null) {
				if (virtualAttributeOverridesCopy.hasNext()) {
					OrmAttributeOverride virtualAttributeOverride = virtualAttributeOverridesCopy.next();
					virtualAttributeOverride.update(buildVirtualXmlAttributeOverride(persistentAttribute, javaAttributeOverride));
				}
				else {
					addVirtualAttributeOverride(buildVirtualAttributeOverride(persistentAttribute, javaAttributeOverride));
				}
			}
			else {
				addVirtualAttributeOverride(buildVirtualAttributeOverride(persistentAttribute, javaAttributeOverride));
			}
		}
		for (OrmAttributeOverride virtualAttributeOverride : CollectionTools.iterable(virtualAttributeOverridesCopy)) {
			removeVirtualAttributeOverride(virtualAttributeOverride);
		}
	}
	
	protected OrmAttributeOverride buildAttributeOverride(XmlAttributeOverride attributeOverride) {
		return getXmlContextNodeFactory().buildOrmAttributeOverride(this, createAttributeOverrideOwner(), attributeOverride);
	}

	protected AttributeOverride.Owner createAttributeOverrideOwner() {
		return new AttributeOverrideOwner();
	}
	//************ validation ***************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		for (Iterator<OrmAttributeOverride> stream = this.attributeOverrides(); stream.hasNext(); ) {
			stream.next().validate(messages, reporter);
		}
	}

	public TextRange getValidationTextRange() {
		return this.resourceAttributeOverrideContainer.getValidationTextRange();
	}
	
	
	
	
	class AttributeOverrideOwner implements AttributeOverride.Owner {

		public ColumnMapping getColumnMapping(String attributeName) {
			if (attributeName == null) {
				return null;
			}
			for (Iterator<PersistentAttribute> stream = getParent().getPersistentType().allAttributes(); stream.hasNext();) {
				PersistentAttribute persAttribute = stream.next();
				if (attributeName.equals(persAttribute.getName())) {
					if (persAttribute.getMapping() instanceof ColumnMapping) {
						return (ColumnMapping) persAttribute.getMapping();
					}
				}
			}
			return null;
		}

		public boolean isVirtual(BaseOverride override) {
			return GenericOrmAttributeOverrideContainer.this.virtualAttributeOverrides.contains(override);
		}

		public BaseOverride setVirtual(boolean virtual, BaseOverride override) {
			return GenericOrmAttributeOverrideContainer.this.setAttributeOverrideVirtual(virtual, (OrmAttributeOverride) override);
		}

		public TypeMapping getTypeMapping() {
			return getParent();
		}
		
	}

}
