/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.GenericJavaEmbeddedMapping;
import org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddedId;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;


public class GenericOrmEmbeddedIdMapping extends AbstractOrmAttributeMapping<XmlEmbeddedId> implements OrmEmbeddedIdMapping
{
	protected final List<OrmAttributeOverride> specifiedAttributeOverrides;
	
	protected final List<OrmAttributeOverride> defaultAttributeOverrides;

	private Embeddable embeddable;
	
	public GenericOrmEmbeddedIdMapping(OrmPersistentAttribute parent) {
		super(parent);
		this.specifiedAttributeOverrides = new ArrayList<OrmAttributeOverride>();
		this.defaultAttributeOverrides = new ArrayList<OrmAttributeOverride>();
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmEmbeddedIdMapping(this);
	}

	@Override
	public void initializeFromOrmEmbeddedMapping(OrmEmbeddedMapping oldMapping) {
		super.initializeFromOrmEmbeddedMapping(oldMapping);
		int index = 0;
		for (AttributeOverride attributeOverride : CollectionTools.iterable(oldMapping.specifiedAttributeOverrides())) {
			OrmAttributeOverride newAttributeOverride = addSpecifiedAttributeOverride(index++);
			newAttributeOverride.setName(attributeOverride.getName());
			newAttributeOverride.getColumn().initializeFrom(attributeOverride.getColumn());
		}
	}
	
	public int xmlSequence() {
		return 7;
	}
	
	//*************** AttributeMapping implementation *********************
	
	public String getKey() {
		return MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	public boolean isIdMapping() {
		return true;
	}

	
	//*************** EmbeddedIdMapping implementation *********************
	public ListIterator<OrmAttributeOverride> attributeOverrides() {
		//TODO attribute overrides
		return EmptyListIterator.instance();
	}

	public int attributeOverridesSize() {
		// TODO attributes overrides size
		return 0;
	}
	
	public ListIterator<OrmAttributeOverride> defaultAttributeOverrides() {
		return new CloneListIterator<OrmAttributeOverride>(this.defaultAttributeOverrides);
	}
	
	public int defaultAttributeOverridesSize() {
		return this.defaultAttributeOverrides.size();
	}
	
	public ListIterator<OrmAttributeOverride> specifiedAttributeOverrides() {
		return new CloneListIterator<OrmAttributeOverride>(this.specifiedAttributeOverrides);
	}

	public int specifiedAttributeOverridesSize() {
		return this.specifiedAttributeOverrides.size();
	}

	public OrmAttributeOverride addSpecifiedAttributeOverride(int index) {
		XmlAttributeOverride xmlAttributeOverride = OrmFactory.eINSTANCE.createXmlAttributeOverrideImpl();
		OrmAttributeOverride attributeOverride = buildAttributeOverride(xmlAttributeOverride);
		this.specifiedAttributeOverrides.add(index, attributeOverride);
		this.attributeMapping().getAttributeOverrides().add(index, xmlAttributeOverride);
		this.fireItemAdded(EmbeddedIdMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, attributeOverride);
		return attributeOverride;
	}

	protected void addSpecifiedAttributeOverride(int index, OrmAttributeOverride attributeOverride) {
		addItemToList(index, attributeOverride, this.specifiedAttributeOverrides, EmbeddedIdMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	public void removeSpecifiedAttributeOverride(AttributeOverride attributeOverride) {
		removeSpecifiedAttributeOverride(this.specifiedAttributeOverrides.indexOf(attributeOverride));
	}
	
	public void removeSpecifiedAttributeOverride(int index) {
		OrmAttributeOverride removedAttributeOverride = this.specifiedAttributeOverrides.remove(index);
		this.attributeMapping().getAttributeOverrides().remove(index);
		fireItemRemoved(EmbeddedIdMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, removedAttributeOverride);
	}
	
	protected void removeSpecifiedAttributeOverride_(OrmAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.specifiedAttributeOverrides, EmbeddedIdMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	public void moveSpecifiedAttributeOverride(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedAttributeOverrides, targetIndex, sourceIndex);
		this.attributeMapping().getAttributeOverrides().move(targetIndex, sourceIndex);
		fireItemMoved(EmbeddedIdMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, targetIndex, sourceIndex);		
	}

	public boolean isVirtual(BaseOverride override) {
		return this.defaultAttributeOverrides.contains(override);
	}

	public ColumnMapping columnMapping(String attributeName) {
		return GenericJavaEmbeddedMapping.columnMapping(attributeName, embeddable());
	}

//
//	public EList<IAttributeOverride> getAttributeOverrides() {
//		EList<IAttributeOverride> list = new EObjectEList<IAttributeOverride>(IAttributeOverride.class, this, OrmPackage.XML_EMBEDDED__ATTRIBUTE_OVERRIDES);
//		list.addAll(getSpecifiedAttributeOverrides());
//		list.addAll(getDefaultAttributeOverrides());
//		return list;
//	}
//
//	public EList<IAttributeOverride> getSpecifiedAttributeOverrides() {
//		if (specifiedAttributeOverrides == null) {
//			specifiedAttributeOverrides = new EObjectContainmentEList<IAttributeOverride>(IAttributeOverride.class, this, OrmPackage.XML_EMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES);
//		}
//		return specifiedAttributeOverrides;
//	}
//
//	public EList<IAttributeOverride> getDefaultAttributeOverrides() {
//		if (defaultAttributeOverrides == null) {
//			defaultAttributeOverrides = new EObjectContainmentEList<IAttributeOverride>(IAttributeOverride.class, this, OrmPackage.XML_EMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES);
//		}
//		return defaultAttributeOverrides;
//	}
//
//	public IEmbeddable embeddable() {
//		return this.embeddable;
//	}
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
//
//	public Iterator<String> allOverridableAttributeNames() {
//		return new TransformationIterator<IPersistentAttribute, String>(this.allOverridableAttributes()) {
//			@Override
//			protected String transform(IPersistentAttribute attribute) {
//				return attribute.getName();
//			}
//		};
//	}
//
//	public Iterator<IPersistentAttribute> allOverridableAttributes() {
//		if (this.embeddable() == null) {
//			return EmptyIterator.instance();
//		}
//		return new FilteringIterator<IPersistentAttribute>(this.embeddable().getPersistentType().attributes()) {
//			@Override
//			protected boolean accept(Object o) {
//				return ((IPersistentAttribute) o).isOverridableAttribute();
//			}
//		};
//	}
//
//	public IAttributeOverride createAttributeOverride(int index) {
//		return OrmFactory.eINSTANCE.createXmlAttributeOverride(new AttributeOverrideOwner(this));
//	}
//

	
	public Embeddable embeddable() {
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
	public void initialize(XmlEmbeddedId embeddedId) {
		super.initialize(embeddedId);
		this.initializeSpecifiedAttributeOverrides(embeddedId);
	}
	
	protected void initializeSpecifiedAttributeOverrides(XmlEmbeddedId embeddedId) {
		for (XmlAttributeOverride attributeOverride : embeddedId.getAttributeOverrides()) {
			this.specifiedAttributeOverrides.add(buildAttributeOverride(attributeOverride));
		}
	}

	protected OrmAttributeOverride buildAttributeOverride(XmlAttributeOverride attributeOverride) {
		return jpaFactory().buildOrmAttributeOverride(this, this, attributeOverride);
	}

	
	@Override
	public void update(XmlEmbeddedId embeddedId) {
		super.update(embeddedId);
		this.updateSpecifiedAttributeOverrides(embeddedId);
	}
	
	protected void updateSpecifiedAttributeOverrides(XmlEmbeddedId embeddedId) {
		ListIterator<OrmAttributeOverride> attributeOverrides = specifiedAttributeOverrides();
		ListIterator<XmlAttributeOverride> resourceAttributeOverrides = embeddedId.getAttributeOverrides().listIterator();
		
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
			addSpecifiedAttributeOverride(specifiedAttributeOverridesSize(), buildAttributeOverride(resourceAttributeOverrides.next()));
		}
	}

	public XmlEmbeddedId addToResourceModel(AbstractXmlTypeMapping typeMapping) {
		XmlEmbeddedId embeddedId = OrmFactory.eINSTANCE.createXmlEmbeddedIdImpl();
		persistentAttribute().initialize(embeddedId);
		typeMapping.getAttributes().getEmbeddedIds().add(embeddedId);
		return embeddedId;
	}
	
	public void removeFromResourceModel(AbstractXmlTypeMapping typeMapping) {
		typeMapping.getAttributes().getEmbeddedIds().remove(this.attributeMapping());
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}
}
