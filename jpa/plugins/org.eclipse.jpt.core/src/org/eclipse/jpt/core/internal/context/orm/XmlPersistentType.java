/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.core.internal.context.base.JpaContextNode;
import org.eclipse.jpt.core.internal.context.java.IJavaBasicMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaEmbeddedIdMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaEmbeddedMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaIdMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaManyToManyMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaManyToOneMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaOneToManyMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaOneToOneMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.IJavaTransientMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaVersionMapping;
import org.eclipse.jpt.core.internal.resource.orm.AttributeMapping;
import org.eclipse.jpt.core.internal.resource.orm.Attributes;
import org.eclipse.jpt.core.internal.resource.orm.Basic;
import org.eclipse.jpt.core.internal.resource.orm.Embeddable;
import org.eclipse.jpt.core.internal.resource.orm.Embedded;
import org.eclipse.jpt.core.internal.resource.orm.EmbeddedId;
import org.eclipse.jpt.core.internal.resource.orm.Entity;
import org.eclipse.jpt.core.internal.resource.orm.Id;
import org.eclipse.jpt.core.internal.resource.orm.ManyToMany;
import org.eclipse.jpt.core.internal.resource.orm.ManyToOne;
import org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass;
import org.eclipse.jpt.core.internal.resource.orm.OneToMany;
import org.eclipse.jpt.core.internal.resource.orm.OneToOne;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.Transient;
import org.eclipse.jpt.core.internal.resource.orm.TypeMapping;
import org.eclipse.jpt.core.internal.resource.orm.Version;
import org.eclipse.jpt.core.internal.resource.orm.VirtualBasic;
import org.eclipse.jpt.core.internal.resource.orm.VirtualEmbedded;
import org.eclipse.jpt.core.internal.resource.orm.VirtualEmbeddedId;
import org.eclipse.jpt.core.internal.resource.orm.VirtualId;
import org.eclipse.jpt.core.internal.resource.orm.VirtualManyToMany;
import org.eclipse.jpt.core.internal.resource.orm.VirtualManyToOne;
import org.eclipse.jpt.core.internal.resource.orm.VirtualOneToMany;
import org.eclipse.jpt.core.internal.resource.orm.VirtualOneToOne;
import org.eclipse.jpt.core.internal.resource.orm.VirtualTransient;
import org.eclipse.jpt.core.internal.resource.orm.VirtualVersion;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ChainIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
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

	protected void setPersistentAttributeVirtual(XmlPersistentAttribute xmlPersistentAttribute, boolean virtual) {
		boolean oldVirtual = xmlPersistentAttribute.isVirtual();
		if (oldVirtual == virtual) {
			return;
		}
		if (virtual) {	
			XmlPersistentAttribute virtualPersistentAttribute = createVirtualPersistentAttribute(xmlPersistentAttribute.getMapping().javaPersistentAttribute());
			this.virtualPersistentAttributes.add(virtualPersistentAttribute);
			this.removeSpecifiedXmlPersistentAttribute(xmlPersistentAttribute);
			fireItemAdded(VIRTUAL_ATTRIBUTES_LIST, virtualAttributesSize(), virtualPersistentAttribute);
		}
		else {
			int index = this.virtualPersistentAttributes.indexOf(xmlPersistentAttribute);
			this.virtualPersistentAttributes.remove(xmlPersistentAttribute);
			addSpecifiedPersistentAttribute(xmlPersistentAttribute.mappingKey(), xmlPersistentAttribute.getName());
			fireItemRemoved(VIRTUAL_ATTRIBUTES_LIST, index, xmlPersistentAttribute);
		}
	}



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
		return new FilteringIterator<XmlPersistentAttribute, XmlPersistentAttribute>(attributes()) {
			@Override
			protected boolean accept(XmlPersistentAttribute o) {
				return attributeName.equals(o.getName());
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

	protected XmlPersistentAttribute specifiedAttributeNamed(String attributeName) {
		Iterator<XmlPersistentAttribute> stream = specifiedAttributesNamed(attributeName);
		return (stream.hasNext()) ? stream.next() : null;
		
	}
	
	protected Iterator<XmlPersistentAttribute> specifiedAttributesNamed(final String attributeName) {
		return new FilteringIterator<XmlPersistentAttribute, XmlPersistentAttribute>(specifiedAttributes()) {
			@Override
			protected boolean accept(XmlPersistentAttribute xmlPersistentAttribute) {
				return attributeName.equals(xmlPersistentAttribute.getName());
			}
		};
	}

	@SuppressWarnings("unchecked")
	public ListIterator<XmlPersistentAttribute> attributes() {
		return new CompositeListIterator<XmlPersistentAttribute>(specifiedAttributes(), virtualAttributes());
	}

	public int attributesSize() {
		return specifiedAttributesSize() + virtualAttributesSize();
	}
	
	public ListIterator<XmlPersistentAttribute> specifiedAttributes() {
		return new CloneListIterator<XmlPersistentAttribute>(this.specifiedPersistentAttributes);
	}
	
	public int specifiedAttributesSize() {
		return this.specifiedPersistentAttributes.size();
	}
	
	public ListIterator<XmlPersistentAttribute> virtualAttributes() {
		return new CloneListIterator<XmlPersistentAttribute>(this.virtualPersistentAttributes);		
	}
	
	public int virtualAttributesSize() {
		return this.virtualPersistentAttributes.size();
	}
	
	protected void addVirtualPersistentAttribute(XmlPersistentAttribute xmlPersistentAttribute) {
		addItemToList(xmlPersistentAttribute, this.virtualPersistentAttributes, IPersistentType.VIRTUAL_ATTRIBUTES_LIST);
	}

	protected void removeVirtualPersistentAttribute(XmlPersistentAttribute xmlPersistentAttribute) {
		removeItemFromList(xmlPersistentAttribute, this.virtualPersistentAttributes, IPersistentType.VIRTUAL_ATTRIBUTES_LIST);
	}
	
	protected boolean containsVirtualPersistentAttribute(XmlPersistentAttribute xmlPersistentAttribute) {
		return this.virtualPersistentAttributes.contains(xmlPersistentAttribute);
	}
	
	public XmlPersistentAttribute addSpecifiedPersistentAttribute(String mappingKey, String attributeName) {
		XmlPersistentAttribute persistentAttribute = jpaFactory().createXmlPersistentAttribute(this, mappingKey);
		int index = insertionIndex(persistentAttribute);
		if (getMapping().typeMappingResource().getAttributes() == null) {
			getMapping().typeMappingResource().setAttributes(OrmFactory.eINSTANCE.createAttributes());
		}
		this.specifiedPersistentAttributes.add(index, persistentAttribute);
		AttributeMapping attributeMapping = persistentAttribute.getMapping().addToResourceModel(getMapping().typeMappingResource());
		
		attributeMapping.setName(attributeName);
		fireItemAdded(IPersistentType.SPECIFIED_ATTRIBUTES_LIST, index, persistentAttribute);
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
		addItemToList(xmlPersistentAttribute, this.specifiedPersistentAttributes, IPersistentType.SPECIFIED_ATTRIBUTES_LIST);
	}

	protected void removeSpecifiedPersistentAttribute_(XmlPersistentAttribute xmlPersistentAttribute) {
		removeItemFromList(xmlPersistentAttribute, this.specifiedPersistentAttributes, IPersistentType.SPECIFIED_ATTRIBUTES_LIST);
	}

	public void removeSpecifiedXmlPersistentAttribute(XmlPersistentAttribute xmlPersistentAttribute) {
		int index = this.specifiedPersistentAttributes.indexOf(xmlPersistentAttribute);
		this.specifiedPersistentAttributes.remove(xmlPersistentAttribute);
		xmlPersistentAttribute.getMapping().removeFromResourceModel(this.xmlTypeMapping.typeMappingResource());
		fireItemRemoved(IPersistentType.SPECIFIED_ATTRIBUTES_LIST, index, xmlPersistentAttribute);		
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
	
	
	//TODO is there a way to avoid a method for every mapping type?
	//I am trying to take adavantage of generics here, but it sure is
	//leading to a lot of duplicated code. - KFM
	public void initialize(Entity entity) {
		((XmlEntity) getMapping()).initialize(entity);
		this.initializeParentPersistentType();	
		this.initializePersistentAttributes(entity);
	}
	
	public void initialize(MappedSuperclass mappedSuperclass) {
		((XmlMappedSuperclass) getMapping()).initialize(mappedSuperclass);
		this.initializeParentPersistentType();
		this.initializePersistentAttributes(mappedSuperclass);
	}
		
	public void initialize(Embeddable embeddable) {
		((XmlEmbeddable) getMapping()).initialize(embeddable);
		this.initializeParentPersistentType();		
		this.initializePersistentAttributes(embeddable);
	}
	
	protected void initializePersistentAttributes(TypeMapping typeMapping) {
		Attributes attributes = typeMapping.getAttributes();
		if (attributes != null) {
			this.initializeSpecifiedPersistentAttributes(attributes);
		}
		this.initializeVirtualPersistentAttributes();
	}
	
	protected void initializeSpecifiedPersistentAttributes(Attributes attributes) {
		for (Id id : attributes.getIds()) {
			XmlPersistentAttribute xmlPersistentAttribute = jpaFactory().createXmlPersistentAttribute(this, IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
			xmlPersistentAttribute.initialize(id);
			this.specifiedPersistentAttributes.add(xmlPersistentAttribute);
		}
		for (EmbeddedId embeddedId : attributes.getEmbeddedIds()) {
			XmlPersistentAttribute xmlPersistentAttribute = jpaFactory().createXmlPersistentAttribute(this, IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
			xmlPersistentAttribute.initialize(embeddedId);
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
		for (ManyToOne manyToOne : attributes.getManyToOnes()) {
			XmlPersistentAttribute xmlPersistentAttribute = jpaFactory().createXmlPersistentAttribute(this, IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
			xmlPersistentAttribute.initialize(manyToOne);
			this.specifiedPersistentAttributes.add(xmlPersistentAttribute);
		}
		for (OneToMany oneToMany : attributes.getOneToManys()) {
			XmlPersistentAttribute xmlPersistentAttribute = jpaFactory().createXmlPersistentAttribute(this, IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
			xmlPersistentAttribute.initialize(oneToMany);
			this.specifiedPersistentAttributes.add(xmlPersistentAttribute);
		}
		for (OneToOne oneToOne : attributes.getOneToOnes()) {
			XmlPersistentAttribute xmlPersistentAttribute = jpaFactory().createXmlPersistentAttribute(this, IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
			xmlPersistentAttribute.initialize(oneToOne);
			this.specifiedPersistentAttributes.add(xmlPersistentAttribute);
		}
		for (ManyToMany manyToMany : attributes.getManyToManys()) {
			XmlPersistentAttribute xmlPersistentAttribute = jpaFactory().createXmlPersistentAttribute(this, IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
			xmlPersistentAttribute.initialize(manyToMany);
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
	
	protected void initializeVirtualPersistentAttributes() {
		ListIterator<IJavaPersistentAttribute> javaAttributes = javaPersistentAttributes();
		
		while (javaAttributes.hasNext()) {
			IJavaPersistentAttribute javaPersistentAttribute = javaAttributes.next();
			if (specifiedAttributeNamed(javaPersistentAttribute.getName()) == null) {
				XmlPersistentAttribute xmlPersistentAttribute = createVirtualPersistentAttribute(javaPersistentAttribute);
				this.virtualPersistentAttributes.add(xmlPersistentAttribute);
			}
		}
	}
	
	protected ListIterator<IJavaPersistentAttribute> javaPersistentAttributes() {
		IJavaPersistentType javaPersistentType = javaPersistentType();
		if (javaPersistentType != null) {
			return javaPersistentType.attributes();
		}
		return EmptyListIterator.instance();
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
			this.updateEmbeddedIds(typeMapping.getAttributes(), xmlPersistentAttributes);
			this.updateBasics(typeMapping.getAttributes(), xmlPersistentAttributes);
			this.updateVersions(typeMapping.getAttributes(), xmlPersistentAttributes);
			this.updateManyToOnes(typeMapping.getAttributes(), xmlPersistentAttributes);
			this.updateOneToManys(typeMapping.getAttributes(), xmlPersistentAttributes);
			this.updateOneToOnes(typeMapping.getAttributes(), xmlPersistentAttributes);
			this.updateManyToManys(typeMapping.getAttributes(), xmlPersistentAttributes);
			this.updateEmbeddeds(typeMapping.getAttributes(), xmlPersistentAttributes);		
			this.updateTransients(typeMapping.getAttributes(), xmlPersistentAttributes);		
		}
		while (xmlPersistentAttributes.hasNext()) {
			this.removeSpecifiedPersistentAttribute_(xmlPersistentAttributes.next());
		}	
		this.updateVirtualPersistentAttributes();
	}
	
	protected void updateVirtualAttribute(XmlPersistentAttribute xmlAttribute, IJavaPersistentAttribute javaAttribute) {
		if (javaAttribute.mappingKey() == IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualBasic((IJavaBasicMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualEmbedded((IJavaEmbeddedMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualEmbeddedId((IJavaEmbeddedIdMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualId((IJavaIdMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualManyToMany((IJavaManyToManyMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualManyToOne((IJavaManyToOneMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualOneToMany((IJavaOneToManyMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualOneToOne((IJavaOneToOneMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualTransient((IJavaTransientMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualVersion((IJavaVersionMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}		
	}
	
	protected void updateVirtualPersistentAttributes() {
		ListIterator<IJavaPersistentAttribute> javaAttributes = this.javaPersistentAttributes();
		ListIterator<XmlPersistentAttribute> xmlVirtualAttributes = this.virtualAttributes();
		for (IJavaPersistentAttribute javaAttribute : CollectionTools.iterable(javaAttributes)) {
			if (specifiedAttributeNamed(javaAttribute.getName()) == null) {
				if (xmlVirtualAttributes.hasNext()) {
					updateVirtualAttribute(xmlVirtualAttributes.next(), javaAttribute);
				}
				else {
					XmlPersistentAttribute xmlPersistentAttribute = createVirtualPersistentAttribute(javaAttribute);
					addVirtualPersistentAttribute(xmlPersistentAttribute);
				}
			}
		}
		
		while (xmlVirtualAttributes.hasNext()) {
			this.removeVirtualPersistentAttribute(xmlVirtualAttributes.next());
		}	

	}

	protected void addVirtualPersistentAttribute(IJavaPersistentAttribute javaAttribute) {
		addVirtualPersistentAttribute(createVirtualPersistentAttribute(javaAttribute));
	}
	
	protected XmlPersistentAttribute createVirtualPersistentAttribute(IJavaPersistentAttribute javaAttribute) {
		XmlPersistentAttribute xmlPersistentAttribute = new XmlPersistentAttribute(this, javaAttribute.mappingKey());
		if (javaAttribute.mappingKey() == IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY) {
			xmlPersistentAttribute.initialize(new VirtualBasic((IJavaBasicMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY) {
			xmlPersistentAttribute.initialize(new VirtualEmbeddedId((IJavaEmbeddedIdMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY) {
			xmlPersistentAttribute.initialize(new VirtualEmbedded((IJavaEmbeddedMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY) {
			xmlPersistentAttribute.initialize(new VirtualId((IJavaIdMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			xmlPersistentAttribute.initialize(new VirtualManyToMany((IJavaManyToManyMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			xmlPersistentAttribute.initialize(new VirtualManyToOne((IJavaManyToOneMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			xmlPersistentAttribute.initialize(new VirtualOneToMany((IJavaOneToManyMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			xmlPersistentAttribute.initialize(new VirtualOneToOne((IJavaOneToOneMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY) {
			xmlPersistentAttribute.initialize(new VirtualTransient((IJavaTransientMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY) {
			xmlPersistentAttribute.initialize(new VirtualVersion((IJavaVersionMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		return xmlPersistentAttribute;
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
	
	protected void updateEmbeddedIds(org.eclipse.jpt.core.internal.resource.orm.Attributes attributes, ListIterator<XmlPersistentAttribute> xmlPersistentAttributes) {
		for (EmbeddedId embeddedId : attributes.getEmbeddedIds()) {
			if (xmlPersistentAttributes.hasNext()) {
				xmlPersistentAttributes.next().update(embeddedId);
			}
			else {
				XmlPersistentAttribute xmlPersistentAttribute = jpaFactory().createXmlPersistentAttribute(this, IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
				xmlPersistentAttribute.initialize(embeddedId);
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
	protected void updateManyToOnes(org.eclipse.jpt.core.internal.resource.orm.Attributes attributes, ListIterator<XmlPersistentAttribute> xmlPersistentAttributes) {
		for (ManyToOne manyToOne : attributes.getManyToOnes()) {
			if (xmlPersistentAttributes.hasNext()) {
				xmlPersistentAttributes.next().update(manyToOne);
			}
			else {
				XmlPersistentAttribute xmlPersistentAttribute = jpaFactory().createXmlPersistentAttribute(this, IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
				xmlPersistentAttribute.initialize(manyToOne);
				addSpecifiedPersistentAttribute_(xmlPersistentAttribute);
			}
		}
	}
	protected void updateOneToManys(org.eclipse.jpt.core.internal.resource.orm.Attributes attributes, ListIterator<XmlPersistentAttribute> xmlPersistentAttributes) {
		for (OneToMany oneToMany : attributes.getOneToManys()) {
			if (xmlPersistentAttributes.hasNext()) {
				xmlPersistentAttributes.next().update(oneToMany);
			}
			else {
				XmlPersistentAttribute xmlPersistentAttribute = jpaFactory().createXmlPersistentAttribute(this, IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
				xmlPersistentAttribute.initialize(oneToMany);
				addSpecifiedPersistentAttribute_(xmlPersistentAttribute);
			}
		}
	}
	protected void updateOneToOnes(org.eclipse.jpt.core.internal.resource.orm.Attributes attributes, ListIterator<XmlPersistentAttribute> xmlPersistentAttributes) {
		for (OneToOne oneToOne : attributes.getOneToOnes()) {
			if (xmlPersistentAttributes.hasNext()) {
				xmlPersistentAttributes.next().update(oneToOne);
			}
			else {
				XmlPersistentAttribute xmlPersistentAttribute = jpaFactory().createXmlPersistentAttribute(this, IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
				xmlPersistentAttribute.initialize(oneToOne);
				addSpecifiedPersistentAttribute_(xmlPersistentAttribute);
			}
		}
	}
	protected void updateManyToManys(org.eclipse.jpt.core.internal.resource.orm.Attributes attributes, ListIterator<XmlPersistentAttribute> xmlPersistentAttributes) {
		for (ManyToMany manyToMany : attributes.getManyToManys()) {
			if (xmlPersistentAttributes.hasNext()) {
				xmlPersistentAttributes.next().update(manyToMany);
			}
			else {
				XmlPersistentAttribute xmlPersistentAttribute = jpaFactory().createXmlPersistentAttribute(this, IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
				xmlPersistentAttribute.initialize(manyToMany);
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
	
	@Override
	public IJpaContextNode contextNode(int offset) {
		if (this.xmlTypeMapping.contextNode(offset) == null) {
			return null;
		}
		for (XmlPersistentAttribute attribute : CollectionTools.iterable(this.attributes())) {
			IJpaContextNode contextNode = attribute.contextNode(offset);
			if (contextNode != null) {
				return contextNode;
			}
		}
		return this;
	}

	public IPersistentAttribute resolveAttribute(String attributeName) {
		Iterator<XmlPersistentAttribute> attributes = attributesNamed(attributeName);
		if (attributes.hasNext()) {
			XmlPersistentAttribute attribute = attributes.next();
			return attributes.hasNext() ? null /* more than one */: attribute;
		}
		else if (parentPersistentType() != null) {
			return parentPersistentType().resolveAttribute(attributeName);
		}
		else {
			return null;
		}
	}
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
	
	@Override
	public XmlPersistentType xmlPersistentType() {
		return this;
	}
}
