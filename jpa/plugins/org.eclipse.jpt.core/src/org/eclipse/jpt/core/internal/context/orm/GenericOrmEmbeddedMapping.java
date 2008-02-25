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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.GenericJavaEmbeddedMapping;
import org.eclipse.jpt.core.resource.orm.AbstractTypeMapping;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;


public class GenericOrmEmbeddedMapping extends AbstractOrmAttributeMapping<XmlEmbedded> implements EmbeddedMapping
{
	protected final List<GenericOrmAttributeOverride> specifiedAttributeOverrides;
	
	protected final List<GenericOrmAttributeOverride> defaultAttributeOverrides;

	private Embeddable embeddable;
	
	protected GenericOrmEmbeddedMapping(OrmPersistentAttribute parent) {
		super(parent);
		this.specifiedAttributeOverrides = new ArrayList<GenericOrmAttributeOverride>();
		this.defaultAttributeOverrides = new ArrayList<GenericOrmAttributeOverride>();
	}

	@Override
	protected void initializeOn(AbstractOrmAttributeMapping<? extends XmlAttributeMapping> newMapping) {
		newMapping.initializeFromXmlEmbeddedMapping(this);
	}

	@Override
	public void initializeFromXmlEmbeddedIdMapping(GenericOrmEmbeddedIdMapping oldMapping) {
		super.initializeFromXmlEmbeddedIdMapping(oldMapping);
		int index = 0;
		for (AttributeOverride attributeOverride : CollectionTools.iterable(oldMapping.specifiedAttributeOverrides())) {
			GenericOrmAttributeOverride newAttributeOverride = addSpecifiedAttributeOverride(index++);
			newAttributeOverride.setName(attributeOverride.getName());
			newAttributeOverride.getColumn().initializeFrom(attributeOverride.getColumn());
		}
	}

	@Override
	public int xmlSequence() {
		return 7;
	}

	public String getKey() {
		return MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY;
	}

	@SuppressWarnings("unchecked")
	public ListIterator<GenericOrmAttributeOverride> attributeOverrides() {
		return new CompositeListIterator<GenericOrmAttributeOverride>(specifiedAttributeOverrides(), defaultAttributeOverrides());
	}

	public int attributeOverridesSize() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<GenericOrmAttributeOverride> defaultAttributeOverrides() {
		return new CloneListIterator<GenericOrmAttributeOverride>(this.defaultAttributeOverrides);
	}
	
	public int defaultAttributeOverridesSize() {
		return this.defaultAttributeOverrides.size();
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<GenericOrmAttributeOverride> specifiedAttributeOverrides() {
		return new CloneListIterator<GenericOrmAttributeOverride>(this.specifiedAttributeOverrides);
	}

	public int specifiedAttributeOverridesSize() {
		return this.specifiedAttributeOverrides.size();
	}

	public GenericOrmAttributeOverride addSpecifiedAttributeOverride(int index) {
		GenericOrmAttributeOverride attributeOverride = new GenericOrmAttributeOverride(this, this);
		this.specifiedAttributeOverrides.add(index, attributeOverride);
		XmlAttributeOverride attributeOverrideResource = OrmFactory.eINSTANCE.createAttributeOverrideImpl();
		this.attributeMapping().getAttributeOverrides().add(index, attributeOverrideResource);
		attributeOverride.initialize(attributeOverrideResource);
		this.fireItemAdded(EmbeddedMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, attributeOverride);
		return attributeOverride;
	}

	protected void addSpecifiedAttributeOverride(int index, GenericOrmAttributeOverride attributeOverride) {
		addItemToList(index, attributeOverride, this.specifiedAttributeOverrides, EmbeddedMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	public void removeSpecifiedAttributeOverride(AttributeOverride attributeOverride) {
		removeSpecifiedAttributeOverride(this.specifiedAttributeOverrides.indexOf(attributeOverride));
	}
	
	public void removeSpecifiedAttributeOverride(int index) {
		GenericOrmAttributeOverride removedAttributeOverride = this.specifiedAttributeOverrides.remove(index);
		this.attributeMapping().getAttributeOverrides().remove(index);
		fireItemRemoved(EmbeddedMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, removedAttributeOverride);
	}
	
	protected void removeSpecifiedAttributeOverride_(GenericOrmAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.specifiedAttributeOverrides, EmbeddedMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	public void moveSpecifiedAttributeOverride(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedAttributeOverrides, targetIndex, sourceIndex);
		this.attributeMapping().getAttributeOverrides().move(targetIndex, sourceIndex);
		fireItemMoved(EmbeddedMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, targetIndex, sourceIndex);		
	}

	public boolean isVirtual(BaseOverride override) {
		return this.defaultAttributeOverrides.contains(override);
	}

	public ColumnMapping columnMapping(String attributeName) {
		return GenericJavaEmbeddedMapping.columnMapping(attributeName, embeddable());
	}

	public TextRange validationTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
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
	public void initialize(XmlEmbedded embedded) {
		super.initialize(embedded);
		this.embeddable = embeddableFor(javaPersistentAttribute());
		this.initializeSpecifiedAttributeOverrides(embedded);
	}
	
	protected void initializeSpecifiedAttributeOverrides(XmlEmbedded embedded) {
		for (XmlAttributeOverride attributeOverride : embedded.getAttributeOverrides()) {
			this.specifiedAttributeOverrides.add(createAttributeOverride(attributeOverride));
		}
	}
//	
//	protected void initializeDefaultAttributeOverrides(JavaPersistentAttributeResource persistentAttributeResource) {
//		for (Iterator<String> i = allOverridableAttributeNames(); i.hasNext(); ) {
//			String attributeName = i.next();
//			XmlAttributeOverride attributeOverride = attributeOverrideNamed(attributeName);
//			if (attributeOverride == null) {
//				attributeOverride = createAttributeOverride(new NullAttributeOverride(persistentAttributeResource));
//				attributeOverride.setName(attributeName);
//				this.defaultAttributeOverrides.add(attributeOverride);
//			}
//		}
//	}

	protected GenericOrmAttributeOverride createAttributeOverride(XmlAttributeOverride attributeOverride) {
		GenericOrmAttributeOverride xmlAttributeOverride = new GenericOrmAttributeOverride(this, this);
		xmlAttributeOverride.initialize(attributeOverride);
		return xmlAttributeOverride;
	}

	
	@Override
	public void update(XmlEmbedded embedded) {
		super.update(embedded);
		this.embeddable = embeddableFor(javaPersistentAttribute());
		this.updateSpecifiedAttributeOverrides(embedded);
	}
	
	protected void updateSpecifiedAttributeOverrides(XmlEmbedded embedded) {
		ListIterator<GenericOrmAttributeOverride> attributeOverrides = specifiedAttributeOverrides();
		ListIterator<XmlAttributeOverride> resourceAttributeOverrides = embedded.getAttributeOverrides().listIterator();
		
		while (attributeOverrides.hasNext()) {
			GenericOrmAttributeOverride attributeOverride = attributeOverrides.next();
			if (resourceAttributeOverrides.hasNext()) {
				attributeOverride.update(resourceAttributeOverrides.next());
			}
			else {
				removeSpecifiedAttributeOverride_(attributeOverride);
			}
		}
		
		while (resourceAttributeOverrides.hasNext()) {
			addSpecifiedAttributeOverride(specifiedAttributeOverridesSize(), createAttributeOverride(resourceAttributeOverrides.next()));
		}
	}

	@Override
	public XmlEmbedded addToResourceModel(AbstractTypeMapping typeMapping) {
		XmlEmbedded embedded = OrmFactory.eINSTANCE.createEmbeddedImpl();
		persistentAttribute().initialize(embedded);
		typeMapping.getAttributes().getEmbeddeds().add(embedded);
		return embedded;
	}
	
	@Override
	public void removeFromResourceModel(AbstractTypeMapping typeMapping) {
		typeMapping.getAttributes().getEmbeddeds().remove(this.attributeMapping());
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}
	
	//******* static methods *********
	public static Embeddable embeddableFor(JavaPersistentAttribute javaPersistentAttribute) {
		if (javaPersistentAttribute == null) {
			return null;
		}
		return GenericJavaEmbeddedMapping.embeddableFor(javaPersistentAttribute);
	}

}
