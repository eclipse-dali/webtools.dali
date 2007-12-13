/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.core.internal.context.base.JpaContextNode;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.resource.orm.AttributeMapping;
import org.eclipse.jpt.core.internal.resource.orm.Attributes;
import org.eclipse.jpt.core.internal.resource.orm.Basic;
import org.eclipse.jpt.core.internal.resource.orm.Embeddable;
import org.eclipse.jpt.core.internal.resource.orm.Embedded;
import org.eclipse.jpt.core.internal.resource.orm.Entity;
import org.eclipse.jpt.core.internal.resource.orm.Id;
import org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.Transient;
import org.eclipse.jpt.core.internal.resource.orm.TypeMapping;
import org.eclipse.jpt.core.internal.resource.orm.Version;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ChainIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;


public class XmlPersistentType extends JpaContextNode implements IPersistentType
{
	protected final List<XmlPersistentAttribute> specifiedPersistentAttributes;

	protected final List<XmlPersistentAttribute> virtualPersistentAttributes;

	protected final Collection<IXmlTypeMappingProvider> typeMappingProviders;

	protected XmlTypeMapping<? extends TypeMapping> xmlTypeMapping;
	
	protected IPersistentType parentPersistentType;
	
	public XmlPersistentType(EntityMappings parent, String mappingKey) {
		super(parent);
		this.typeMappingProviders = buildTypeMappingProviders();
		this.xmlTypeMapping = buildXmlTypeMapping(mappingKey);
		this.specifiedPersistentAttributes = new ArrayList<XmlPersistentAttribute>();
		this.virtualPersistentAttributes = new ArrayList<XmlPersistentAttribute>();
	}

//	/* @see IJpaContentNode#getId() */
//	public Object getId() {
//		return IXmlContentNodes.PERSISTENT_TYPE_ID;
//	}
	
	public boolean isFor(String fullyQualifiedTypeName) {
		String className = getMapping().getClass_();
		if (className == null) {
			return false;
		}
		if (className.equals(fullyQualifiedTypeName)) {
			return true;
		}
		if ((entityMappings().getPackage() + className).equals(fullyQualifiedTypeName)) {
			return true;
		}
		return false;
	}
	
	protected XmlTypeMapping<? extends TypeMapping> buildXmlTypeMapping(String key) {
		return typeMappingProvider(key).buildTypeMapping(jpaFactory(), this);
	}

	protected Collection<IXmlTypeMappingProvider> buildTypeMappingProviders() {
		Collection<IXmlTypeMappingProvider> collection = new ArrayList<IXmlTypeMappingProvider>();
		collection.add(new XmlEntityProvider());
		collection.add(new XmlMappedSuperclassProvider());
		collection.add(new XmlEmbeddableProvider());
		return collection;
	}

	protected IXmlTypeMappingProvider typeMappingProvider(String key) {
		for (IXmlTypeMappingProvider provider : this.typeMappingProviders) {
			if (provider.key().equals(key)) {
				return provider;
			}
		}
		throw new IllegalArgumentException();
	}

	public XmlTypeMapping<? extends TypeMapping> getMapping() {
		return this.xmlTypeMapping;
	}

	public void setMappingKey(String newMappingKey) {
		if (this.mappingKey() == newMappingKey) {
			return;
		}
		XmlTypeMapping<? extends TypeMapping> oldMapping = getMapping();
		this.xmlTypeMapping = buildXmlTypeMapping(newMappingKey);
		entityMappings().changeMapping(this, oldMapping, this.xmlTypeMapping);
		firePropertyChanged(MAPPING_PROPERTY, oldMapping, this.xmlTypeMapping);
	}
	
	protected void setMappingKey_(String newMappingKey) {
		if (this.mappingKey() == newMappingKey) {
			return;
		}
		XmlTypeMapping<? extends TypeMapping> oldMapping = getMapping();
		this.xmlTypeMapping = buildXmlTypeMapping(newMappingKey);
		firePropertyChanged(MAPPING_PROPERTY, oldMapping, this.xmlTypeMapping);
	}

	public Iterator<IPersistentType> inheritanceHierarchy() {
		// using a chain iterator to traverse up the inheritance tree
		return new ChainIterator<IPersistentType>(this) {
			@Override
			protected IPersistentType nextLink(IPersistentType pt) {
				return pt.parentPersistentType();
			}
		};
	}

	public IPersistentType parentPersistentType() {
		return this.parentPersistentType;
	}

	public AccessType access() {
		return getMapping().getAccess();
	}

//	protected void changeMapping(XmlAttributeMapping oldMapping, String newMappingKey) {
//		boolean virtual = oldMapping.isVirtual();
//		XmlAttributeMapping newAttributeMapping = buildAttributeMapping(oldMapping.getPersistentAttribute().attributeMappingProviders(), newMappingKey);
//		// we can't set the attribute to null, but we can set it to a dummy placeholder one
//		// we do this to get the translators to *wake up* and remove adapters from the attribute
//		XmlPersistentAttribute nullAttribute = OrmFactory.eINSTANCE.createXmlPersistentAttribute();
//		XmlPersistentAttribute attribute = oldMapping.getPersistentAttribute();
//		oldMapping.setPersistentAttribute(nullAttribute);
//		if (virtual) {
//			getVirtualPersistentAttributes().remove(attribute);
//			getVirtualAttributeMappings().remove(oldMapping);
//		}
//		else {
//			getSpecifiedPersistentAttributes().remove(attribute);
//			getSpecifiedAttributeMappings().remove(oldMapping);
//		}
//		newAttributeMapping.setPersistentAttribute(attribute);
//		oldMapping.initializeOn(newAttributeMapping);
//		if (virtual) {
//			insertAttributeMapping(newAttributeMapping, getVirtualAttributeMappings());
//		}
//		else {
//			insertAttributeMapping(newAttributeMapping, getSpecifiedAttributeMappings());
//		}
//	}
//
//	protected void setMappingVirtual(XmlAttributeMapping attributeMapping, boolean virtual) {
//		boolean oldVirtual = attributeMapping.isVirtual();
//		if (oldVirtual == virtual) {
//			return;
//		}
//		if (virtual) {
//			getSpecifiedAttributeMappings().remove(attributeMapping);
//			insertAttributeMapping(attributeMapping, getVirtualAttributeMappings());
//		}
//		else {
//			getVirtualAttributeMappings().remove(attributeMapping);
//			insertAttributeMapping(attributeMapping, getSpecifiedAttributeMappings());
//		}
//	}



	public Iterator<String> allAttributeNames() {
		return this.attributeNames(this.allAttributes());
	}

	public Iterator<IPersistentAttribute> allAttributes() {
		return new CompositeIterator<IPersistentAttribute>(new TransformationIterator<IPersistentType, Iterator<IPersistentAttribute>>(this.inheritanceHierarchy()) {
			@Override
			protected Iterator<IPersistentAttribute> transform(IPersistentType pt) {
				return pt.attributes();
			}
		});
	}

	protected Iterator<XmlPersistentAttribute> attributesNamed(final String attributeName) {
		return new FilteringIterator<XmlPersistentAttribute>(attributes()) {
			@Override
			protected boolean accept(Object o) {
				return attributeName.equals(((XmlPersistentAttribute) o).getName());
			}
		};
	}

	public XmlPersistentAttribute attributeNamed(String attributeName) {
		Iterator<XmlPersistentAttribute> stream = attributesNamed(attributeName);
		return (stream.hasNext()) ? stream.next() : null;
	}

	public Iterator<String> attributeNames() {
		return this.attributeNames(this.attributes());
	}
	
	protected Iterator<String> attributeNames(Iterator<? extends IPersistentAttribute> attrs) {
		return new TransformationIterator<IPersistentAttribute, String>(attrs) {
			@Override
			protected String transform(IPersistentAttribute attribute) {
				return attribute.getName();
			}
		};
	}

	@SuppressWarnings("unchecked")
	public ListIterator<XmlPersistentAttribute> attributes() {
		return new CompositeListIterator<XmlPersistentAttribute>(specifiedAttributes(), virtualAttributes());
	}

	public int attributesSize() {
		return CollectionTools.size(attributes());
	}
	
	public ListIterator<XmlPersistentAttribute> specifiedAttributes() {
		return new CloneListIterator<XmlPersistentAttribute>(this.specifiedPersistentAttributes);
	}
	
	public ListIterator<XmlPersistentAttribute> virtualAttributes() {
		return new CloneListIterator<XmlPersistentAttribute>(this.virtualPersistentAttributes);		
	}
	
	public XmlPersistentAttribute addSpecifiedPersistentAttribute(String mappingKey, String attributeName) {
		XmlPersistentAttribute persistentAttribute = jpaFactory().createXmlPersistentAttribute(this, mappingKey);
		int index = insertionIndex(persistentAttribute);
		if (getMapping().typeMapping.getAttributes() == null) {
			getMapping().typeMapping.setAttributes(OrmFactory.eINSTANCE.createAttributes());
		}
		this.specifiedPersistentAttributes.add(index, persistentAttribute);
		AttributeMapping attributeMapping = getMapping().createAndAddOrmResourceAttributeMapping(persistentAttribute, mappingKey);
		attributeMapping.setName(attributeName);
		fireItemAdded(ATTRIBUTES_LIST, index, persistentAttribute);
		return persistentAttribute;
	}

	protected int insertionIndex(XmlPersistentAttribute persistentAttribute) {
		return CollectionTools.insertionIndexOf(this.specifiedPersistentAttributes, persistentAttribute, buildMappingComparator());
	}

	protected Comparator<XmlPersistentAttribute> buildMappingComparator() {
		return new Comparator<XmlPersistentAttribute>() {
			public int compare(XmlPersistentAttribute o1, XmlPersistentAttribute o2) {
				int o1Sequence = o1.getMapping().xmlSequence();
				int o2Sequence = o2.getMapping().xmlSequence();
				if (o1Sequence < o2Sequence) {
					return -1;
				}
				if (o1Sequence == o2Sequence) {
					return 0;
				}
				return 1;
			}
		};
	}

	
	protected void addSpecifiedPersistentAttribute_(XmlPersistentAttribute xmlPersistentAttribute) {
		//TODO SPECIFIED_ATTRIBUTES_LIST and VIRTUAL_ATTRIBUTES_LIST ???
		addItemToList(xmlPersistentAttribute, this.specifiedPersistentAttributes, ATTRIBUTES_LIST);
	}

	protected void removeSpecifiedPersistentAttribute_(XmlPersistentAttribute xmlPersistentAttribute) {
		removeItemFromList(xmlPersistentAttribute, this.specifiedPersistentAttributes, ATTRIBUTES_LIST);
	}

	public void removeSpecifiedXmlPersistentAttribute(XmlPersistentAttribute xmlPersistentAttribute) {
		int index = this.specifiedPersistentAttributes.indexOf(xmlPersistentAttribute);
		this.specifiedPersistentAttributes.remove(xmlPersistentAttribute);
		xmlPersistentAttribute.getMapping().removeFromResourceModel(this.xmlTypeMapping.typeMappingResource());
		fireItemRemoved(ATTRIBUTES_LIST, index, xmlPersistentAttribute);		
	}

	public String getName() {
		return getMapping().getClass_();
	}

	protected void classChanged(String oldClass, String newClass) {
		firePropertyChanged(NAME_PROPERTY, oldClass, newClass);
	}

	public boolean isMapped() {
		return true;
	}

	public String mappingKey() {
		return getMapping().getKey();
	}
	
	public IJavaPersistentType javaPersistentType() {
		return getMapping().getJavaPersistentType();
	}
	
	public void initialize(Entity entity) {
		((XmlEntity) getMapping()).initialize(entity);
		this.initializeParentPersistentType();	
		this.initializeSpecifiedPersistentAttributes(entity);
	}
	
	public void initialize(MappedSuperclass mappedSuperclass) {
		((XmlMappedSuperclass) getMapping()).initialize(mappedSuperclass);
		this.initializeParentPersistentType();
		this.initializeSpecifiedPersistentAttributes(mappedSuperclass);
	}
		
	public void initialize(Embeddable embeddable) {
		((XmlEmbeddable) getMapping()).initialize(embeddable);
		this.initializeParentPersistentType();		
		this.initializeSpecifiedPersistentAttributes(embeddable);
	}
	
	protected void initializeSpecifiedPersistentAttributes(TypeMapping typeMapping) {
		Attributes attributes = typeMapping.getAttributes();
		if (attributes == null) {
			return;
		}
		initializeSpecifiedPersistentAttributes(attributes);
	}
	
	protected void initializeSpecifiedPersistentAttributes(Attributes attributes) {
		for (Id id : attributes.getIds()) {
			XmlPersistentAttribute xmlPersistentAttribute = jpaFactory().createXmlPersistentAttribute(this, IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
			xmlPersistentAttribute.initialize(id);
			this.specifiedPersistentAttributes.add(xmlPersistentAttribute);
		}
		for (Basic basic : attributes.getBasics()) {
			XmlPersistentAttribute xmlPersistentAttribute = jpaFactory().createXmlPersistentAttribute(this, IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
			xmlPersistentAttribute.initialize(basic);
			this.specifiedPersistentAttributes.add(xmlPersistentAttribute);
		}
		for (Version version : attributes.getVersions()) {
			XmlPersistentAttribute xmlPersistentAttribute = jpaFactory().createXmlPersistentAttribute(this, IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
			xmlPersistentAttribute.initialize(version);
			this.specifiedPersistentAttributes.add(xmlPersistentAttribute);
		}
		for (Embedded embedded : attributes.getEmbeddeds()) {
			XmlPersistentAttribute xmlPersistentAttribute = jpaFactory().createXmlPersistentAttribute(this, IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
			xmlPersistentAttribute.initialize(embedded);
			this.specifiedPersistentAttributes.add(xmlPersistentAttribute);
		}
		for (Transient transientResource : attributes.getTransients()) {
			XmlPersistentAttribute xmlPersistentAttribute = jpaFactory().createXmlPersistentAttribute(this, IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
			xmlPersistentAttribute.initialize(transientResource);
			this.specifiedPersistentAttributes.add(xmlPersistentAttribute);
		}
	}
	
	protected void initializeParentPersistentType() {
		IJavaPersistentType javaPersistentType = javaPersistentType();
		if (javaPersistentType != null) {
			this.parentPersistentType = javaPersistentType.parentPersistentType();
		}
	}

	public void update(Entity entity) {
		if (mappingKey() == IMappingKeys.ENTITY_TYPE_MAPPING_KEY) {
			((XmlEntity) getMapping()).update(entity);
		}
		else {
			setMappingKey_(IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
			((XmlEntity) getMapping()).initialize(entity);					
		}
		this.updateParentPersistentType();
		this.updatePersistentAttributes(entity);
	}
	
	public void update(MappedSuperclass mappedSuperclass) {
		if (mappingKey() == IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY) {
			((XmlMappedSuperclass) getMapping()).update(mappedSuperclass);
		}
		else {
			setMappingKey_(IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
			((XmlMappedSuperclass) getMapping()).initialize(mappedSuperclass);
		}
		this.updateParentPersistentType();
		this.updatePersistentAttributes(mappedSuperclass);
	}
	
	public void update(Embeddable embeddable) {
		if (mappingKey() == IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY) {
			((XmlEmbeddable) getMapping()).update(embeddable);
		}
		else {
			setMappingKey_(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
			((XmlEmbeddable) getMapping()).initialize(embeddable);				
		}
		this.updateParentPersistentType();
		this.updatePersistentAttributes(embeddable);
	}
	
	protected void updateParentPersistentType() {
		IJavaPersistentType javaPersistentType = javaPersistentType();
		if (javaPersistentType == null) {
			//TODO change notification for this?
			this.parentPersistentType = null;
			return;
		}
		this.parentPersistentType = javaPersistentType.parentPersistentType();
	}

	protected void updatePersistentAttributes(org.eclipse.jpt.core.internal.resource.orm.TypeMapping typeMapping) {
		ListIterator<XmlPersistentAttribute> xmlPersistentAttributes = this.specifiedAttributes();
		if (typeMapping.getAttributes() != null) {
			this.updateIds(typeMapping.getAttributes(), xmlPersistentAttributes);
			this.updateBasics(typeMapping.getAttributes(), xmlPersistentAttributes);
			this.updateVersions(typeMapping.getAttributes(), xmlPersistentAttributes);
			this.updateEmbeddeds(typeMapping.getAttributes(), xmlPersistentAttributes);		
			this.updateTransients(typeMapping.getAttributes(), xmlPersistentAttributes);		
		}
		while (xmlPersistentAttributes.hasNext()) {
			this.removeSpecifiedPersistentAttribute_(xmlPersistentAttributes.next());
		}		
	}
	
	protected void updateIds(org.eclipse.jpt.core.internal.resource.orm.Attributes attributes, ListIterator<XmlPersistentAttribute> xmlPersistentAttributes) {
		for (Id id : attributes.getIds()) {
			if (xmlPersistentAttributes.hasNext()) {
				xmlPersistentAttributes.next().update(id);
			}
			else {
				XmlPersistentAttribute xmlPersistentAttribute = jpaFactory().createXmlPersistentAttribute(this, IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
				xmlPersistentAttribute.initialize(id);
				addSpecifiedPersistentAttribute_(xmlPersistentAttribute);
			}
		}
	}
	
	protected void updateBasics(org.eclipse.jpt.core.internal.resource.orm.Attributes attributes, ListIterator<XmlPersistentAttribute> xmlPersistentAttributes) {
		for (Basic basic : attributes.getBasics()) {
			if (xmlPersistentAttributes.hasNext()) {
				xmlPersistentAttributes.next().update(basic);
			}
			else {
				XmlPersistentAttribute xmlPersistentAttribute = jpaFactory().createXmlPersistentAttribute(this, IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
				xmlPersistentAttribute.initialize(basic);
				addSpecifiedPersistentAttribute_(xmlPersistentAttribute);
			}
		}
	}
	
	protected void updateVersions(org.eclipse.jpt.core.internal.resource.orm.Attributes attributes, ListIterator<XmlPersistentAttribute> xmlPersistentAttributes) {
		for (Version version : attributes.getVersions()) {
			if (xmlPersistentAttributes.hasNext()) {
				xmlPersistentAttributes.next().update(version);
			}
			else {
				XmlPersistentAttribute xmlPersistentAttribute = jpaFactory().createXmlPersistentAttribute(this, IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
				xmlPersistentAttribute.initialize(version);
				addSpecifiedPersistentAttribute_(xmlPersistentAttribute);
			}
		}
	}

	protected void updateEmbeddeds(org.eclipse.jpt.core.internal.resource.orm.Attributes attributes, ListIterator<XmlPersistentAttribute> xmlPersistentAttributes) {
		for (Embedded embedded : attributes.getEmbeddeds()) {
			if (xmlPersistentAttributes.hasNext()) {
				xmlPersistentAttributes.next().update(embedded);
			}
			else {
				XmlPersistentAttribute xmlPersistentAttribute = jpaFactory().createXmlPersistentAttribute(this, IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
				xmlPersistentAttribute.initialize(embedded);
				addSpecifiedPersistentAttribute_(xmlPersistentAttribute);
			}
		}
	}
	
	protected void updateTransients(org.eclipse.jpt.core.internal.resource.orm.Attributes attributes, ListIterator<XmlPersistentAttribute> xmlPersistentAttributes) {
		for (Transient transientResource : attributes.getTransients()) {
			if (xmlPersistentAttributes.hasNext()) {
				xmlPersistentAttributes.next().update(transientResource);
			}
			else {
				XmlPersistentAttribute xmlPersistentAttribute = jpaFactory().createXmlPersistentAttribute(this, IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
				xmlPersistentAttribute.initialize(transientResource);
				addSpecifiedPersistentAttribute_(xmlPersistentAttribute);
			}
		}
	}
	
//	public IJpaContentNode getContentNode(int offset) {
//		for (XmlAttributeMapping mapping : this.getSpecifiedAttributeMappings()) {
//			if (mapping.getNode().contains(offset)) {
//				return mapping.getContentNode(offset);
//			}
//		}
//		return this;
//	}
//
//	public IPersistentAttribute resolveAttribute(String attributeName) {
//		Iterator<XmlPersistentAttribute> attributes = attributesNamed(attributeName);
//		if (attributes.hasNext()) {
//			XmlPersistentAttribute attribute = attributes.next();
//			return attributes.hasNext() ? null /* more than one */: attribute;
//		}
//		else if (parentPersistentType() != null) {
//			return parentPersistentType().resolveAttribute(attributeName);
//		}
//		else {
//			return null;
//		}
//	}
//
//	@Override
//	public ITextRange validationTextRange() {
//		return selectionTextRange();
//	}
//
//	@Override
//	public ITextRange selectionTextRange() {
//		return getMapping().selectionTextRange();
//	}
//
//	public ITextRange classTextRange() {
//		return getMapping().classTextRange();
//	}
//
//	public ITextRange attributesTextRange() {
//		return getMapping().attributesTextRange();
//	}
}
