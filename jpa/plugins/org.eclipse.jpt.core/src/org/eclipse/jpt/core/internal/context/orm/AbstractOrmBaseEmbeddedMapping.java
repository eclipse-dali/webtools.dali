/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.context.BaseEmbeddedMapping;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmBaseEmbeddedMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaBaseEmbeddedMapping;
import org.eclipse.jpt.core.resource.orm.BaseXmlEmbedded;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;


public abstract class AbstractOrmBaseEmbeddedMapping<T extends BaseXmlEmbedded> extends AbstractOrmAttributeMapping<T> implements OrmBaseEmbeddedMapping
{
	protected final List<OrmAttributeOverride> specifiedAttributeOverrides;
	
	protected final List<OrmAttributeOverride> virtualAttributeOverrides;

	private Embeddable embeddable;
	
	protected AbstractOrmBaseEmbeddedMapping(OrmPersistentAttribute parent) {
		super(parent);
		this.specifiedAttributeOverrides = new ArrayList<OrmAttributeOverride>();
		this.virtualAttributeOverrides = new ArrayList<OrmAttributeOverride>();
	}

	@Override
	public void initializeFromOrmBaseEmbeddedMapping(OrmBaseEmbeddedMapping oldMapping) {
		super.initializeFromOrmBaseEmbeddedMapping(oldMapping);
		int index = 0;
		for (OrmAttributeOverride attributeOverride : CollectionTools.iterable(oldMapping.specifiedAttributeOverrides())) {
		OrmAttributeOverride newAttributeOverride = addSpecifiedAttributeOverride(index++);
			newAttributeOverride.setName(attributeOverride.getName());
			newAttributeOverride.getColumn().initializeFrom(attributeOverride.getColumn());
		}
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
	protected void addVirtualAttributeOverride(OrmAttributeOverride attributeOverride) {
		addItemToList(attributeOverride, this.virtualAttributeOverrides, BaseEmbeddedMapping.VIRTUAL_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	protected void removeVirtualAttributeOverride(OrmAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.virtualAttributeOverrides, BaseEmbeddedMapping.VIRTUAL_ATTRIBUTE_OVERRIDES_LIST);
	}

	public int virtualAttributeOverridesSize() {
		return this.virtualAttributeOverrides.size();
	}
	
	public ListIterator<OrmAttributeOverride> specifiedAttributeOverrides() {
		return new CloneListIterator<OrmAttributeOverride>(this.specifiedAttributeOverrides);
	}

	public int specifiedAttributeOverridesSize() {
		return this.specifiedAttributeOverrides.size();
	}

	protected OrmAttributeOverride addSpecifiedAttributeOverride(int index) {
		XmlAttributeOverride xmlAttributeOverride = OrmFactory.eINSTANCE.createXmlAttributeOverrideImpl();
		OrmAttributeOverride attributeOverride = buildAttributeOverride(xmlAttributeOverride);
		this.specifiedAttributeOverrides.add(index, attributeOverride);
		this.getAttributeMapping().getAttributeOverrides().add(index, xmlAttributeOverride);
		this.fireItemAdded(BaseEmbeddedMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, attributeOverride);
		return attributeOverride;
	}

	protected void addSpecifiedAttributeOverride(int index, OrmAttributeOverride attributeOverride) {
		addItemToList(index, attributeOverride, this.specifiedAttributeOverrides, BaseEmbeddedMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}
		
	protected void addSpecifiedAttributeOverride(OrmAttributeOverride attributeOverride) {
		this.addSpecifiedAttributeOverride(this.specifiedAttributeOverrides.size(), attributeOverride);
	}
		
	protected void removeSpecifiedAttributeOverride_(OrmAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.specifiedAttributeOverrides, BaseEmbeddedMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	public void moveSpecifiedAttributeOverride(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedAttributeOverrides, targetIndex, sourceIndex);
		this.getAttributeMapping().getAttributeOverrides().move(targetIndex, sourceIndex);
		fireItemMoved(BaseEmbeddedMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, targetIndex, sourceIndex);		
	}

	public OrmAttributeOverride getAttributeOverrideNamed(String name) {
		return (OrmAttributeOverride) getOverrideNamed(name, attributeOverrides());
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

	//****************** AttributeOverride.Owner implemenation *******************

	public boolean isVirtual(BaseOverride override) {
		return this.virtualAttributeOverrides.contains(override);
	}

	public BaseOverride setVirtual(boolean virtual, BaseOverride override) {
		// Add a new attribute override
		if (virtual) {
			return setVirtual((OrmAttributeOverride) override);
		}
		return setSpecified((OrmAttributeOverride) override);
	}
	
	protected OrmAttributeOverride setVirtual(OrmAttributeOverride attributeOverride) {
		int index = this.specifiedAttributeOverrides.indexOf(attributeOverride);
		this.specifiedAttributeOverrides.remove(index);
		String attributeOverrideName = attributeOverride.getName();
		//add the virtual attribute override so that I can control the order that change notification is sent.
		//otherwise when we remove the annotation from java we will get an update and add the attribute override
		//during the udpate.  This causes the UI to be flaky, since change notification might not occur in the correct order
		OrmAttributeOverride virtualAttributeOverride = null;
		if (attributeOverrideName != null) {
			for (PersistentAttribute persistentAttribute : CollectionTools.iterable(allOverridableAttributes())) {
				if (persistentAttribute.getName().equals(attributeOverrideName)) {
					JavaAttributeOverride javaAttributeOverride = null;
					if (getJavaEmbeddedMapping() != null) {
						javaAttributeOverride = getJavaEmbeddedMapping().getAttributeOverrideNamed(attributeOverrideName);
					}
					//store the virtualAttributeOverride so we can fire change notification later
					virtualAttributeOverride = buildVirtualAttributeOverride(persistentAttribute, javaAttributeOverride);
					this.virtualAttributeOverrides.add(virtualAttributeOverride);
				}
			}
		}

		getAttributeMapping().getAttributeOverrides().remove(index);
		fireItemRemoved(BaseEmbeddedMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, attributeOverride);
		
		if (virtualAttributeOverride != null) {
			fireItemAdded(BaseEmbeddedMapping.VIRTUAL_ATTRIBUTE_OVERRIDES_LIST, virtualAttributeOverridesSize() - 1, virtualAttributeOverride);
		}
		return virtualAttributeOverride;
	}
	
	protected OrmAttributeOverride setSpecified(OrmAttributeOverride oldAttributeOverride) {
		int index = specifiedAttributeOverridesSize();
		XmlAttributeOverride xmlAttributeOverride = OrmFactory.eINSTANCE.createXmlAttributeOverrideImpl();
		OrmAttributeOverride newAttributeOverride = getJpaFactory().buildOrmAttributeOverride(this, this, xmlAttributeOverride);
		this.specifiedAttributeOverrides.add(index, newAttributeOverride);
		
		getAttributeMapping().getAttributeOverrides().add(xmlAttributeOverride);
		
		int defaultIndex = this.virtualAttributeOverrides.indexOf(oldAttributeOverride);
		this.virtualAttributeOverrides.remove(defaultIndex);

		newAttributeOverride.setName(oldAttributeOverride.getName());
		newAttributeOverride.getColumn().setSpecifiedName(oldAttributeOverride.getColumn().getName());
		
		this.fireItemRemoved(BaseEmbeddedMapping.VIRTUAL_ATTRIBUTE_OVERRIDES_LIST, defaultIndex, oldAttributeOverride);
		this.fireItemAdded(BaseEmbeddedMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, newAttributeOverride);		

		return newAttributeOverride;
	}

	public ColumnMapping getColumnMapping(String attributeName) {
		return MappingTools.getColumnMapping(attributeName, getEmbeddable());
	}

	public Embeddable getEmbeddable() {
		return this.embeddable;
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
		if (this.getEmbeddable() == null) {
			return EmptyIterator.instance();
		}
		return new FilteringIterator<PersistentAttribute, PersistentAttribute>(this.getEmbeddable().getPersistentType().attributes()) {
			@Override
			protected boolean accept(PersistentAttribute o) {
				return o.isOverridableAttribute();
			}
		};
	}

	public AbstractJavaBaseEmbeddedMapping<?> getJavaEmbeddedMapping() {
		JavaPersistentAttribute javaPersistentAttribute = getJavaPersistentAttribute();
		if (javaPersistentAttribute != null && javaPersistentAttribute.getMappingKey() == getKey()) {
			return (AbstractJavaBaseEmbeddedMapping<?>) javaPersistentAttribute.getMapping();
		}
		return null;
	}
	
	@Override
	public void initialize(T embedded) {
		super.initialize(embedded);
		this.embeddable = embeddableFor(findJavaPersistentAttribute());
		this.initializeSpecifiedAttributeOverrides(embedded);
		this.initializeVirtualAttributeOverrides();
	}
	
	protected void initializeSpecifiedAttributeOverrides(T embedded) {
		for (XmlAttributeOverride attributeOverride : embedded.getAttributeOverrides()) {
			this.specifiedAttributeOverrides.add(buildAttributeOverride(attributeOverride));
		}
	}
	
	protected void initializeVirtualAttributeOverrides() {
		if (getPersistentAttribute().isVirtual()) {
			//specifiedAttributeOverrides are used if the persistentAttribute is virtual
			return;
		}
		for (PersistentAttribute persistentAttribute : CollectionTools.iterable(allOverridableAttributes())) {
			OrmAttributeOverride attributeOverride = getAttributeOverrideNamed(persistentAttribute.getName());
			if (attributeOverride == null) {
				JavaAttributeOverride javaAttributeOverride = null;
				if (getJavaEmbeddedMapping() != null) {
					javaAttributeOverride = getJavaEmbeddedMapping().getAttributeOverrideNamed(persistentAttribute.getName());
				}
				this.virtualAttributeOverrides.add(buildVirtualAttributeOverride(persistentAttribute, javaAttributeOverride));
			}
		}
	}

	protected OrmAttributeOverride buildVirtualAttributeOverride(PersistentAttribute persistentAttribute, JavaAttributeOverride javaAttributeOverride) {
		return buildAttributeOverride(buildVirtualXmlAttributeOverride(persistentAttribute, javaAttributeOverride));
	}
	
	protected XmlAttributeOverride buildVirtualXmlAttributeOverride(PersistentAttribute persistentAttribute, JavaAttributeOverride javaAttributeOverride) {
		XmlColumn xmlColumn;
		if (javaAttributeOverride == null) {
			ColumnMapping columnMapping = (ColumnMapping) persistentAttribute.getMapping();
			xmlColumn = new VirtualXmlColumn(getTypeMapping(), columnMapping.getColumn(), false);			
		}
		else {
			xmlColumn = new VirtualXmlColumn(getTypeMapping(), javaAttributeOverride.getColumn(), true);
		}
		return new VirtualXmlAttributeOverride(persistentAttribute.getName(), xmlColumn);
	}
	
	protected OrmAttributeOverride buildAttributeOverride(XmlAttributeOverride attributeOverride) {
		return getJpaFactory().buildOrmAttributeOverride(this, this, attributeOverride);
	}
	
	@Override
	public void update(T embedded) {
		super.update(embedded);
		this.embeddable = embeddableFor(findJavaPersistentAttribute());
		this.updateSpecifiedAttributeOverrides(embedded);
		this.updateVirtualAttributeOverrides();
	}
	
	protected void updateSpecifiedAttributeOverrides(T embedded) {
		ListIterator<OrmAttributeOverride> attributeOverrides = specifiedAttributeOverrides();
		ListIterator<XmlAttributeOverride> resourceAttributeOverrides = new CloneListIterator<XmlAttributeOverride>(embedded.getAttributeOverrides());//prevent ConcurrentModificiationException
		
		while (attributeOverrides.hasNext()) {
			OrmAttributeOverride attributeOverride = attributeOverrides.next();
			if (resourceAttributeOverrides.hasNext()) {
				attributeOverride.update(resourceAttributeOverrides.next());
			}
			else {
				removeSpecifiedAttributeOverride_(attributeOverride);
			}
		}
		
		while (resourceAttributeOverrides.hasNext()) {
			addSpecifiedAttributeOverride(buildAttributeOverride(resourceAttributeOverrides.next()));
		}
	}

	protected void updateVirtualAttributeOverrides() {
		Iterator<PersistentAttribute> overridableAttributes = allOverridableAttributes();
		ListIterator<OrmAttributeOverride> virtualAttributeOverrides = virtualAttributeOverrides();
		
		if (!getPersistentAttribute().isVirtual()) {
			//specifiedAttributeOverrides are used if the persistentAttribute is virtual
			for (PersistentAttribute persistentAttribute : CollectionTools.iterable(overridableAttributes)) {
				OrmAttributeOverride ormAttributeOverride = getAttributeOverrideNamed(persistentAttribute.getName());
				if (ormAttributeOverride != null && !ormAttributeOverride.isVirtual()) {
					continue;
				}
				JavaAttributeOverride javaAttributeOverride = null;
				if (getJavaEmbeddedMapping() != null) {
					javaAttributeOverride = getJavaEmbeddedMapping().getAttributeOverrideNamed(persistentAttribute.getName());
				}
				if (ormAttributeOverride != null) {
					if (virtualAttributeOverrides.hasNext()) {
						OrmAttributeOverride virtualAttributeOverride = virtualAttributeOverrides.next();
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
		}
		for (OrmAttributeOverride virtualAttributeOverride : CollectionTools.iterable(virtualAttributeOverrides)) {
			removeVirtualAttributeOverride(virtualAttributeOverride);
		}
		
	}
	
	//************ static methods ************
	
	public static Embeddable embeddableFor(JavaPersistentAttribute javaPersistentAttribute) {
		if (javaPersistentAttribute == null) {
			return null;
		}
		return MappingTools.getEmbeddableFor(javaPersistentAttribute);
	}
}
