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

import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IAttributeOverride;
import org.eclipse.jpt.core.internal.context.base.IColumnMapping;
import org.eclipse.jpt.core.internal.context.base.IEmbeddedMapping;
import org.eclipse.jpt.core.internal.context.base.IOverride;
import org.eclipse.jpt.core.internal.resource.orm.AttributeMapping;
import org.eclipse.jpt.core.internal.resource.orm.Embedded;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.TypeMapping;


public class XmlEmbeddedMapping extends XmlAttributeMapping implements IEmbeddedMapping
{
//	protected EList<IAttributeOverride> specifiedAttributeOverrides;
//
//	protected EList<IAttributeOverride> defaultAttributeOverrides;
//
//	private IEmbeddable embeddable;

	protected Embedded embedded;
	
	protected XmlEmbeddedMapping(XmlPersistentAttribute parent) {
		super(parent);
	}

//	@Override
//	protected void addInsignificantFeatureIdsTo(Set<Integer> insignificantFeatureIds) {
//		super.addInsignificantFeatureIdsTo(insignificantFeatureIds);
//		insignificantFeatureIds.add(OrmPackage.XML_EMBEDDED__ATTRIBUTE_OVERRIDES);
//	}

	@Override
	protected void initializeOn(XmlAttributeMapping newMapping) {
		newMapping.initializeFromXmlEmbeddedMapping(this);
	}

	@Override
	public int xmlSequence() {
		return 7;
	}

	public String getKey() {
		return IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY;
	}

	public IAttributeOverride addSpecifiedAttributeOverride(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T extends IAttributeOverride> ListIterator<T> attributeOverrides() {
		// TODO Auto-generated method stub
		return null;
	}

	public <T extends IAttributeOverride> ListIterator<T> defaultAttributeOverrides() {
		// TODO Auto-generated method stub
		return null;
	}

	public void moveSpecifiedAttributeOverride(int oldIndex, int newIndex) {
		// TODO Auto-generated method stub
		
	}

	public void removeSpecifiedAttributeOverride(int index) {
		// TODO Auto-generated method stub
		
	}

	public <T extends IAttributeOverride> ListIterator<T> specifiedAttributeOverrides() {
		// TODO Auto-generated method stub
		return null;
	}

	public int specifiedAttributeOverridesSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public IColumnMapping columnMapping(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isVirtual(IOverride override) {
		// TODO Auto-generated method stub
		return false;
	}

	public ITextRange validationTextRange(CompilationUnit astRoot) {
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
//
//	@Override
//	public void refreshDefaults(DefaultsContext defaultsContext) {
//		super.refreshDefaults(defaultsContext);
//		refreshEmbeddable(defaultsContext);
//	}
//
//	private void refreshEmbeddable(DefaultsContext defaultsContext) {
//		this.embeddable = embeddableFor(getPersistentAttribute().getAttribute(), defaultsContext);
//	}
//
//	//******* static methods *********
//	public static IEmbeddable embeddableFor(Attribute attribute, DefaultsContext defaultsContext) {
//		if (attribute == null) {
//			return null;
//		}
//		return JavaEmbedded.embeddableFor(attribute, defaultsContext);
//	}
	
//	@Override
	public void initialize(Embedded embedded) {
		this.embedded = embedded;
//		super.initialize(embedded);
//		this.specifiedName = entity.getName();
//		this.defaultName = defaultName();
//		this.initializeInheritance(this.inheritanceResource());
//		this.table.initialize(entity);
//		this.initializeSpecifiedSecondaryTables(entity);
//		this.initializeVirtualSecondaryTables();
	}
	
	public void update(Embedded embedded) {
		this.embedded = embedded;
	}
	
	@Override
	public AttributeMapping addToResourceModel(TypeMapping typeMapping) {
		Embedded embedded = OrmFactory.eINSTANCE.createEmbedded();
		if (typeMapping.getAttributes() == null) {
			typeMapping.setAttributes(OrmFactory.eINSTANCE.createAttributes());
		}
		typeMapping.getAttributes().getEmbeddeds().add(embedded);
		return embedded;
	}
	
	@Override
	public void removeFromResourceModel(TypeMapping typeMapping) {
		typeMapping.getAttributes().getEmbeddeds().remove(this.embedded);
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}
}
