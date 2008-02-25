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
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTransientMapping;
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmStructureNodes;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.AbstractJpaContextNode;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.XmlBasic;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddedId;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlId;
import org.eclipse.jpt.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlTransient;
import org.eclipse.jpt.core.resource.orm.AbstractTypeMapping;
import org.eclipse.jpt.core.resource.orm.XmlVersion;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ChainIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;


public class GenericOrmPersistentType extends AbstractJpaContextNode implements OrmPersistentType
{
	protected final List<OrmPersistentAttribute> specifiedPersistentAttributes;

	protected final List<OrmPersistentAttribute> virtualPersistentAttributes;

	protected final Collection<OrmTypeMappingProvider> typeMappingProviders;

	protected OrmTypeMapping<? extends AbstractTypeMapping> ormTypeMapping;
	
	protected PersistentType parentPersistentType;
	
	public GenericOrmPersistentType(EntityMappings parent, String mappingKey) {
		super(parent);
		this.typeMappingProviders = buildTypeMappingProviders();
		this.ormTypeMapping = buildOrmTypeMapping(mappingKey);
		this.specifiedPersistentAttributes = new ArrayList<OrmPersistentAttribute>();
		this.virtualPersistentAttributes = new ArrayList<OrmPersistentAttribute>();
	}
	
	public String getId() {
		return OrmStructureNodes.PERSISTENT_TYPE_ID;
	}

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
	
	protected OrmTypeMapping<? extends AbstractTypeMapping> buildOrmTypeMapping(String key) {
		return typeMappingProvider(key).buildTypeMapping(jpaFactory(), this);
	}

	protected Collection<OrmTypeMappingProvider> buildTypeMappingProviders() {
		Collection<OrmTypeMappingProvider> collection = new ArrayList<OrmTypeMappingProvider>();
		collection.add(new OrmEntityProvider());
		collection.add(new OrmMappedSuperclassProvider());
		collection.add(new OrmEmbeddableProvider());
		return collection;
	}

	protected OrmTypeMappingProvider typeMappingProvider(String key) {
		for (OrmTypeMappingProvider provider : this.typeMappingProviders) {
			if (provider.key().equals(key)) {
				return provider;
			}
		}
		throw new IllegalArgumentException();
	}

	public OrmTypeMapping<? extends AbstractTypeMapping> getMapping() {
		return this.ormTypeMapping;
	}

	public void setMappingKey(String newMappingKey) {
		if (this.mappingKey() == newMappingKey) {
			return;
		}
		OrmTypeMapping<? extends AbstractTypeMapping> oldMapping = getMapping();
		this.ormTypeMapping = buildOrmTypeMapping(newMappingKey);
		entityMappings().changeMapping(this, oldMapping, this.ormTypeMapping);
		firePropertyChanged(MAPPING_PROPERTY, oldMapping, this.ormTypeMapping);
	}
	
	protected void setMappingKey_(String newMappingKey) {
		if (this.mappingKey() == newMappingKey) {
			return;
		}
		OrmTypeMapping<? extends AbstractTypeMapping> oldMapping = getMapping();
		this.ormTypeMapping = buildOrmTypeMapping(newMappingKey);
		firePropertyChanged(MAPPING_PROPERTY, oldMapping, this.ormTypeMapping);
	}

	public Iterator<PersistentType> inheritanceHierarchy() {
		// using a chain iterator to traverse up the inheritance tree
		return new ChainIterator<PersistentType>(this) {
			@Override
			protected PersistentType nextLink(PersistentType pt) {
				return pt.parentPersistentType();
			}
		};
	}

	public PersistentType parentPersistentType() {
		return this.parentPersistentType;
	}

	public AccessType access() {
		return getMapping().getAccess();
	}
	public void changeMapping(OrmPersistentAttribute xmlPersistentAttribute, AbstractOrmAttributeMapping<? extends XmlAttributeMapping> oldMapping, AbstractOrmAttributeMapping<? extends XmlAttributeMapping> newMapping) {
		int sourceIndex = this.specifiedPersistentAttributes.indexOf(xmlPersistentAttribute);
		this.specifiedPersistentAttributes.remove(sourceIndex);
		oldMapping.removeFromResourceModel(getMapping().typeMappingResource());
		if (getMapping().typeMappingResource().getAttributes() == null) {
			getMapping().typeMappingResource().setAttributes(OrmFactory.eINSTANCE.createAttributes());
		}
		int targetIndex = insertionIndex(xmlPersistentAttribute);
		this.specifiedPersistentAttributes.add(targetIndex, xmlPersistentAttribute);
		newMapping.addToResourceModel(getMapping().typeMappingResource());
		oldMapping.initializeOn(newMapping);
		//TODO are the source and target correct in this case, or is target off by one???
		fireItemMoved(SPECIFIED_ATTRIBUTES_LIST, targetIndex, sourceIndex);
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

	public void setPersistentAttributeVirtual(OrmPersistentAttribute xmlPersistentAttribute, boolean virtual) {
		boolean oldVirtual = xmlPersistentAttribute.isVirtual();
		if (oldVirtual == virtual) {
			return;
		}
		if (virtual) {	
			OrmPersistentAttribute virtualPersistentAttribute = createVirtualPersistentAttribute(xmlPersistentAttribute.getMapping().javaPersistentAttribute());
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

	public Iterator<PersistentAttribute> allAttributes() {
		return new CompositeIterator<PersistentAttribute>(new TransformationIterator<PersistentType, Iterator<PersistentAttribute>>(this.inheritanceHierarchy()) {
			@Override
			protected Iterator<PersistentAttribute> transform(PersistentType pt) {
				return pt.attributes();
			}
		});
	}

	protected Iterator<OrmPersistentAttribute> attributesNamed(final String attributeName) {
		return new FilteringIterator<OrmPersistentAttribute, OrmPersistentAttribute>(attributes()) {
			@Override
			protected boolean accept(OrmPersistentAttribute o) {
				return attributeName.equals(o.getName());
			}
		};
	}

	public OrmPersistentAttribute attributeNamed(String attributeName) {
		Iterator<OrmPersistentAttribute> stream = attributesNamed(attributeName);
		return (stream.hasNext()) ? stream.next() : null;
	}

	public Iterator<String> attributeNames() {
		return this.attributeNames(this.attributes());
	}
	
	protected Iterator<String> attributeNames(Iterator<? extends PersistentAttribute> attrs) {
		return new TransformationIterator<PersistentAttribute, String>(attrs) {
			@Override
			protected String transform(PersistentAttribute attribute) {
				return attribute.getName();
			}
		};
	}

	protected OrmPersistentAttribute specifiedAttributeNamed(String attributeName) {
		Iterator<OrmPersistentAttribute> stream = specifiedAttributesNamed(attributeName);
		return (stream.hasNext()) ? stream.next() : null;
		
	}
	
	protected Iterator<OrmPersistentAttribute> specifiedAttributesNamed(final String attributeName) {
		return new FilteringIterator<OrmPersistentAttribute, OrmPersistentAttribute>(specifiedAttributes()) {
			@Override
			protected boolean accept(OrmPersistentAttribute xmlPersistentAttribute) {
				return attributeName.equals(xmlPersistentAttribute.getName());
			}
		};
	}

	@SuppressWarnings("unchecked")
	public ListIterator<OrmPersistentAttribute> attributes() {
		return new CompositeListIterator<OrmPersistentAttribute>(specifiedAttributes(), virtualAttributes());
	}

	public int attributesSize() {
		return specifiedAttributesSize() + virtualAttributesSize();
	}
	
	public ListIterator<OrmPersistentAttribute> specifiedAttributes() {
		return new CloneListIterator<OrmPersistentAttribute>(this.specifiedPersistentAttributes);
	}
	
	public int specifiedAttributesSize() {
		return this.specifiedPersistentAttributes.size();
	}
	
	public ListIterator<OrmPersistentAttribute> virtualAttributes() {
		return new CloneListIterator<OrmPersistentAttribute>(this.virtualPersistentAttributes);		
	}
	
	public int virtualAttributesSize() {
		return this.virtualPersistentAttributes.size();
	}
	
	protected void addVirtualPersistentAttribute(OrmPersistentAttribute xmlPersistentAttribute) {
		addItemToList(xmlPersistentAttribute, this.virtualPersistentAttributes, OrmPersistentType.VIRTUAL_ATTRIBUTES_LIST);
	}

	protected void removeVirtualPersistentAttribute(OrmPersistentAttribute xmlPersistentAttribute) {
		removeItemFromList(xmlPersistentAttribute, this.virtualPersistentAttributes, OrmPersistentType.VIRTUAL_ATTRIBUTES_LIST);
	}
	
	protected boolean containsVirtualPersistentAttribute(OrmPersistentAttribute xmlPersistentAttribute) {
		return this.virtualPersistentAttributes.contains(xmlPersistentAttribute);
	}
	
	public OrmPersistentAttribute addSpecifiedPersistentAttribute(String mappingKey, String attributeName) {
		OrmPersistentAttribute persistentAttribute = jpaFactory().buildXmlPersistentAttribute(this, mappingKey);
		int index = insertionIndex(persistentAttribute);
		if (getMapping().typeMappingResource().getAttributes() == null) {
			getMapping().typeMappingResource().setAttributes(OrmFactory.eINSTANCE.createAttributes());
		}
		this.specifiedPersistentAttributes.add(index, persistentAttribute);
		XmlAttributeMapping attributeMapping = persistentAttribute.getMapping().addToResourceModel(getMapping().typeMappingResource());
		
		attributeMapping.setName(attributeName);
		fireItemAdded(PersistentType.SPECIFIED_ATTRIBUTES_LIST, index, persistentAttribute);
		return persistentAttribute;
	}

	protected int insertionIndex(OrmPersistentAttribute persistentAttribute) {
		return CollectionTools.insertionIndexOf(this.specifiedPersistentAttributes, persistentAttribute, buildMappingComparator());
	}

	protected Comparator<OrmPersistentAttribute> buildMappingComparator() {
		return new Comparator<OrmPersistentAttribute>() {
			public int compare(OrmPersistentAttribute o1, OrmPersistentAttribute o2) {
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

	
	protected void addSpecifiedPersistentAttribute_(OrmPersistentAttribute xmlPersistentAttribute) {
		addItemToList(xmlPersistentAttribute, this.specifiedPersistentAttributes, PersistentType.SPECIFIED_ATTRIBUTES_LIST);
	}

	protected void removeSpecifiedPersistentAttribute_(OrmPersistentAttribute xmlPersistentAttribute) {
		removeItemFromList(xmlPersistentAttribute, this.specifiedPersistentAttributes, PersistentType.SPECIFIED_ATTRIBUTES_LIST);
	}

	public void removeSpecifiedXmlPersistentAttribute(OrmPersistentAttribute xmlPersistentAttribute) {
		int index = this.specifiedPersistentAttributes.indexOf(xmlPersistentAttribute);
		this.specifiedPersistentAttributes.remove(xmlPersistentAttribute);
		xmlPersistentAttribute.getMapping().removeFromResourceModel(this.ormTypeMapping.typeMappingResource());
		fireItemRemoved(PersistentType.SPECIFIED_ATTRIBUTES_LIST, index, xmlPersistentAttribute);		
	}

	public String getName() {
		return getMapping().getClass_();
	}

	public void classChanged(String oldClass, String newClass) {
		firePropertyChanged(NAME_PROPERTY, oldClass, newClass);
	}

	public boolean isMapped() {
		return true;
	}

	public String mappingKey() {
		return getMapping().getKey();
	}
	
	public JavaPersistentType javaPersistentType() {
		return getMapping().getJavaPersistentType();
	}
	
	
	//TODO is there a way to avoid a method for every mapping type?
	//I am trying to take adavantage of generics here, but it sure is
	//leading to a lot of duplicated code. - KFM
	public void initialize(XmlEntity entity) {
		((GenericOrmEntity) getMapping()).initialize(entity);
		this.initializeParentPersistentType();	
		this.initializePersistentAttributes(entity);
	}
	
	public void initialize(XmlMappedSuperclass mappedSuperclass) {
		((GenericOrmMappedSuperclass) getMapping()).initialize(mappedSuperclass);
		this.initializeParentPersistentType();
		this.initializePersistentAttributes(mappedSuperclass);
	}
		
	public void initialize(XmlEmbeddable embeddable) {
		((GenericOrmEmbeddable) getMapping()).initialize(embeddable);
		this.initializeParentPersistentType();		
		this.initializePersistentAttributes(embeddable);
	}
	
	protected void initializePersistentAttributes(AbstractTypeMapping typeMapping) {
		Attributes attributes = typeMapping.getAttributes();
		if (attributes != null) {
			this.initializeSpecifiedPersistentAttributes(attributes);
		}
		this.initializeVirtualPersistentAttributes();
	}
	
	protected void initializeSpecifiedPersistentAttributes(Attributes attributes) {
		for (XmlId id : attributes.getIds()) {
			OrmPersistentAttribute xmlPersistentAttribute = jpaFactory().buildXmlPersistentAttribute(this, MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
			xmlPersistentAttribute.initialize(id);
			this.specifiedPersistentAttributes.add(xmlPersistentAttribute);
		}
		for (XmlEmbeddedId embeddedId : attributes.getEmbeddedIds()) {
			OrmPersistentAttribute xmlPersistentAttribute = jpaFactory().buildXmlPersistentAttribute(this, MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
			xmlPersistentAttribute.initialize(embeddedId);
			this.specifiedPersistentAttributes.add(xmlPersistentAttribute);
		}
		for (XmlBasic basic : attributes.getBasics()) {
			OrmPersistentAttribute xmlPersistentAttribute = jpaFactory().buildXmlPersistentAttribute(this, MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
			xmlPersistentAttribute.initialize(basic);
			this.specifiedPersistentAttributes.add(xmlPersistentAttribute);
		}
		for (XmlVersion version : attributes.getVersions()) {
			OrmPersistentAttribute xmlPersistentAttribute = jpaFactory().buildXmlPersistentAttribute(this, MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
			xmlPersistentAttribute.initialize(version);
			this.specifiedPersistentAttributes.add(xmlPersistentAttribute);
		}
		for (XmlManyToOne manyToOne : attributes.getManyToOnes()) {
			OrmPersistentAttribute xmlPersistentAttribute = jpaFactory().buildXmlPersistentAttribute(this, MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
			xmlPersistentAttribute.initialize(manyToOne);
			this.specifiedPersistentAttributes.add(xmlPersistentAttribute);
		}
		for (XmlOneToMany oneToMany : attributes.getOneToManys()) {
			OrmPersistentAttribute xmlPersistentAttribute = jpaFactory().buildXmlPersistentAttribute(this, MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
			xmlPersistentAttribute.initialize(oneToMany);
			this.specifiedPersistentAttributes.add(xmlPersistentAttribute);
		}
		for (XmlOneToOne oneToOne : attributes.getOneToOnes()) {
			OrmPersistentAttribute xmlPersistentAttribute = jpaFactory().buildXmlPersistentAttribute(this, MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
			xmlPersistentAttribute.initialize(oneToOne);
			this.specifiedPersistentAttributes.add(xmlPersistentAttribute);
		}
		for (XmlManyToMany manyToMany : attributes.getManyToManys()) {
			OrmPersistentAttribute xmlPersistentAttribute = jpaFactory().buildXmlPersistentAttribute(this, MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
			xmlPersistentAttribute.initialize(manyToMany);
			this.specifiedPersistentAttributes.add(xmlPersistentAttribute);
		}
		for (XmlEmbedded embedded : attributes.getEmbeddeds()) {
			OrmPersistentAttribute xmlPersistentAttribute = jpaFactory().buildXmlPersistentAttribute(this, MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
			xmlPersistentAttribute.initialize(embedded);
			this.specifiedPersistentAttributes.add(xmlPersistentAttribute);
		}
		for (XmlTransient transientResource : attributes.getTransients()) {
			OrmPersistentAttribute xmlPersistentAttribute = jpaFactory().buildXmlPersistentAttribute(this, MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
			xmlPersistentAttribute.initialize(transientResource);
			this.specifiedPersistentAttributes.add(xmlPersistentAttribute);
		}
	}
	
	protected void initializeVirtualPersistentAttributes() {
		ListIterator<JavaPersistentAttribute> javaAttributes = javaPersistentAttributes();
		
		while (javaAttributes.hasNext()) {
			JavaPersistentAttribute javaPersistentAttribute = javaAttributes.next();
			if (specifiedAttributeNamed(javaPersistentAttribute.getName()) == null) {
				OrmPersistentAttribute xmlPersistentAttribute = createVirtualPersistentAttribute(javaPersistentAttribute);
				this.virtualPersistentAttributes.add(xmlPersistentAttribute);
			}
		}
	}
	
	protected ListIterator<JavaPersistentAttribute> javaPersistentAttributes() {
		JavaPersistentType javaPersistentType = javaPersistentType();
		if (javaPersistentType != null) {
			return javaPersistentType.attributes();
		}
		return EmptyListIterator.instance();
	}

	protected void initializeParentPersistentType() {
		JavaPersistentType javaPersistentType = javaPersistentType();
		if (javaPersistentType != null) {
			this.parentPersistentType = javaPersistentType.parentPersistentType();
		}
	}

	public void update(XmlEntity entity) {
		if (mappingKey() == MappingKeys.ENTITY_TYPE_MAPPING_KEY) {
			((GenericOrmEntity) getMapping()).update(entity);
		}
		else {
			setMappingKey_(MappingKeys.ENTITY_TYPE_MAPPING_KEY);
			((GenericOrmEntity) getMapping()).initialize(entity);					
		}
		this.updateParentPersistentType();
		this.updatePersistentAttributes(entity);
	}
	
	public void update(XmlMappedSuperclass mappedSuperclass) {
		if (mappingKey() == MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY) {
			((GenericOrmMappedSuperclass) getMapping()).update(mappedSuperclass);
		}
		else {
			setMappingKey_(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
			((GenericOrmMappedSuperclass) getMapping()).initialize(mappedSuperclass);
		}
		this.updateParentPersistentType();
		this.updatePersistentAttributes(mappedSuperclass);
	}
	
	public void update(XmlEmbeddable embeddable) {
		if (mappingKey() == MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY) {
			((GenericOrmEmbeddable) getMapping()).update(embeddable);
		}
		else {
			setMappingKey_(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
			((GenericOrmEmbeddable) getMapping()).initialize(embeddable);				
		}
		this.updateParentPersistentType();
		this.updatePersistentAttributes(embeddable);
	}
	
	protected void updateParentPersistentType() {
		JavaPersistentType javaPersistentType = javaPersistentType();
		if (javaPersistentType == null) {
			//TODO change notification for this?
			this.parentPersistentType = null;
			return;
		}
		this.parentPersistentType = javaPersistentType.parentPersistentType();
	}

	protected void updatePersistentAttributes(org.eclipse.jpt.core.resource.orm.AbstractTypeMapping typeMapping) {
		ListIterator<OrmPersistentAttribute> xmlPersistentAttributes = this.specifiedAttributes();
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
	
	protected void updateVirtualAttribute(OrmPersistentAttribute xmlAttribute, JavaPersistentAttribute javaAttribute) {
		if (javaAttribute.mappingKey() == MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualBasic((JavaBasicMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualEmbedded((JavaEmbeddedMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualEmbeddedId((JavaEmbeddedIdMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.ID_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualId((JavaIdMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualManyToMany((JavaManyToManyMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualManyToOne((JavaManyToOneMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualOneToMany((JavaOneToManyMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualOneToOne((JavaOneToOneMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualTransient((JavaTransientMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualVersion((JavaVersionMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}		
	}
	
	protected void updateVirtualPersistentAttributes() {
		ListIterator<JavaPersistentAttribute> javaAttributes = this.javaPersistentAttributes();
		ListIterator<OrmPersistentAttribute> xmlVirtualAttributes = this.virtualAttributes();
		for (JavaPersistentAttribute javaAttribute : CollectionTools.iterable(javaAttributes)) {
			if (specifiedAttributeNamed(javaAttribute.getName()) == null) {
				if (xmlVirtualAttributes.hasNext()) {
					updateVirtualAttribute(xmlVirtualAttributes.next(), javaAttribute);
				}
				else {
					OrmPersistentAttribute xmlPersistentAttribute = createVirtualPersistentAttribute(javaAttribute);
					addVirtualPersistentAttribute(xmlPersistentAttribute);
				}
			}
		}
		
		while (xmlVirtualAttributes.hasNext()) {
			this.removeVirtualPersistentAttribute(xmlVirtualAttributes.next());
		}	

	}

	protected void addVirtualPersistentAttribute(JavaPersistentAttribute javaAttribute) {
		addVirtualPersistentAttribute(createVirtualPersistentAttribute(javaAttribute));
	}
	
	protected OrmPersistentAttribute createVirtualPersistentAttribute(JavaPersistentAttribute javaAttribute) {
		OrmPersistentAttribute xmlPersistentAttribute = new OrmPersistentAttribute(this, javaAttribute.mappingKey());
		if (javaAttribute.mappingKey() == MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY) {
			xmlPersistentAttribute.initialize(new VirtualBasic((JavaBasicMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY) {
			xmlPersistentAttribute.initialize(new VirtualEmbeddedId((JavaEmbeddedIdMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY) {
			xmlPersistentAttribute.initialize(new VirtualEmbedded((JavaEmbeddedMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.ID_ATTRIBUTE_MAPPING_KEY) {
			xmlPersistentAttribute.initialize(new VirtualId((JavaIdMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			xmlPersistentAttribute.initialize(new VirtualManyToMany((JavaManyToManyMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			xmlPersistentAttribute.initialize(new VirtualManyToOne((JavaManyToOneMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			xmlPersistentAttribute.initialize(new VirtualOneToMany((JavaOneToManyMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			xmlPersistentAttribute.initialize(new VirtualOneToOne((JavaOneToOneMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY) {
			xmlPersistentAttribute.initialize(new VirtualTransient((JavaTransientMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY) {
			xmlPersistentAttribute.initialize(new VirtualVersion((JavaVersionMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		return xmlPersistentAttribute;
	}

	protected void updateIds(org.eclipse.jpt.core.resource.orm.Attributes attributes, ListIterator<OrmPersistentAttribute> xmlPersistentAttributes) {
		for (XmlId id : attributes.getIds()) {
			if (xmlPersistentAttributes.hasNext()) {
				xmlPersistentAttributes.next().update(id);
			}
			else {
				OrmPersistentAttribute xmlPersistentAttribute = jpaFactory().buildXmlPersistentAttribute(this, MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
				xmlPersistentAttribute.initialize(id);
				addSpecifiedPersistentAttribute_(xmlPersistentAttribute);
			}
		}
	}
	
	protected void updateEmbeddedIds(org.eclipse.jpt.core.resource.orm.Attributes attributes, ListIterator<OrmPersistentAttribute> xmlPersistentAttributes) {
		for (XmlEmbeddedId embeddedId : attributes.getEmbeddedIds()) {
			if (xmlPersistentAttributes.hasNext()) {
				xmlPersistentAttributes.next().update(embeddedId);
			}
			else {
				OrmPersistentAttribute xmlPersistentAttribute = jpaFactory().buildXmlPersistentAttribute(this, MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
				xmlPersistentAttribute.initialize(embeddedId);
				addSpecifiedPersistentAttribute_(xmlPersistentAttribute);
			}
		}
	}
	
	protected void updateBasics(org.eclipse.jpt.core.resource.orm.Attributes attributes, ListIterator<OrmPersistentAttribute> xmlPersistentAttributes) {
		for (XmlBasic basic : attributes.getBasics()) {
			if (xmlPersistentAttributes.hasNext()) {
				xmlPersistentAttributes.next().update(basic);
			}
			else {
				OrmPersistentAttribute xmlPersistentAttribute = jpaFactory().buildXmlPersistentAttribute(this, MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
				xmlPersistentAttribute.initialize(basic);
				addSpecifiedPersistentAttribute_(xmlPersistentAttribute);
			}
		}
	}
	
	protected void updateVersions(org.eclipse.jpt.core.resource.orm.Attributes attributes, ListIterator<OrmPersistentAttribute> xmlPersistentAttributes) {
		for (XmlVersion version : attributes.getVersions()) {
			if (xmlPersistentAttributes.hasNext()) {
				xmlPersistentAttributes.next().update(version);
			}
			else {
				OrmPersistentAttribute xmlPersistentAttribute = jpaFactory().buildXmlPersistentAttribute(this, MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
				xmlPersistentAttribute.initialize(version);
				addSpecifiedPersistentAttribute_(xmlPersistentAttribute);
			}
		}
	}
	protected void updateManyToOnes(org.eclipse.jpt.core.resource.orm.Attributes attributes, ListIterator<OrmPersistentAttribute> xmlPersistentAttributes) {
		for (XmlManyToOne manyToOne : attributes.getManyToOnes()) {
			if (xmlPersistentAttributes.hasNext()) {
				xmlPersistentAttributes.next().update(manyToOne);
			}
			else {
				OrmPersistentAttribute xmlPersistentAttribute = jpaFactory().buildXmlPersistentAttribute(this, MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
				xmlPersistentAttribute.initialize(manyToOne);
				addSpecifiedPersistentAttribute_(xmlPersistentAttribute);
			}
		}
	}
	protected void updateOneToManys(org.eclipse.jpt.core.resource.orm.Attributes attributes, ListIterator<OrmPersistentAttribute> xmlPersistentAttributes) {
		for (XmlOneToMany oneToMany : attributes.getOneToManys()) {
			if (xmlPersistentAttributes.hasNext()) {
				xmlPersistentAttributes.next().update(oneToMany);
			}
			else {
				OrmPersistentAttribute xmlPersistentAttribute = jpaFactory().buildXmlPersistentAttribute(this, MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
				xmlPersistentAttribute.initialize(oneToMany);
				addSpecifiedPersistentAttribute_(xmlPersistentAttribute);
			}
		}
	}
	protected void updateOneToOnes(org.eclipse.jpt.core.resource.orm.Attributes attributes, ListIterator<OrmPersistentAttribute> xmlPersistentAttributes) {
		for (XmlOneToOne oneToOne : attributes.getOneToOnes()) {
			if (xmlPersistentAttributes.hasNext()) {
				xmlPersistentAttributes.next().update(oneToOne);
			}
			else {
				OrmPersistentAttribute xmlPersistentAttribute = jpaFactory().buildXmlPersistentAttribute(this, MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
				xmlPersistentAttribute.initialize(oneToOne);
				addSpecifiedPersistentAttribute_(xmlPersistentAttribute);
			}
		}
	}
	protected void updateManyToManys(org.eclipse.jpt.core.resource.orm.Attributes attributes, ListIterator<OrmPersistentAttribute> xmlPersistentAttributes) {
		for (XmlManyToMany manyToMany : attributes.getManyToManys()) {
			if (xmlPersistentAttributes.hasNext()) {
				xmlPersistentAttributes.next().update(manyToMany);
			}
			else {
				OrmPersistentAttribute xmlPersistentAttribute = jpaFactory().buildXmlPersistentAttribute(this, MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
				xmlPersistentAttribute.initialize(manyToMany);
				addSpecifiedPersistentAttribute_(xmlPersistentAttribute);
			}
		}
	}

	protected void updateEmbeddeds(org.eclipse.jpt.core.resource.orm.Attributes attributes, ListIterator<OrmPersistentAttribute> xmlPersistentAttributes) {
		for (XmlEmbedded embedded : attributes.getEmbeddeds()) {
			if (xmlPersistentAttributes.hasNext()) {
				xmlPersistentAttributes.next().update(embedded);
			}
			else {
				OrmPersistentAttribute xmlPersistentAttribute = jpaFactory().buildXmlPersistentAttribute(this, MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
				xmlPersistentAttribute.initialize(embedded);
				addSpecifiedPersistentAttribute_(xmlPersistentAttribute);
			}
		}
	}
	
	protected void updateTransients(org.eclipse.jpt.core.resource.orm.Attributes attributes, ListIterator<OrmPersistentAttribute> xmlPersistentAttributes) {
		for (XmlTransient transientResource : attributes.getTransients()) {
			if (xmlPersistentAttributes.hasNext()) {
				xmlPersistentAttributes.next().update(transientResource);
			}
			else {
				OrmPersistentAttribute xmlPersistentAttribute = jpaFactory().buildXmlPersistentAttribute(this, MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
				xmlPersistentAttribute.initialize(transientResource);
				addSpecifiedPersistentAttribute_(xmlPersistentAttribute);
			}
		}
	}
	
	public PersistentAttribute resolveAttribute(String attributeName) {
		Iterator<OrmPersistentAttribute> attributes = attributesNamed(attributeName);
		if (attributes.hasNext()) {
			OrmPersistentAttribute attribute = attributes.next();
			return attributes.hasNext() ? null /* more than one */: attribute;
		}
		else if (parentPersistentType() != null) {
			return parentPersistentType().resolveAttribute(attributeName);
		}
		else {
			return null;
		}
	}
	
	@Override
	public OrmPersistentType ormPersistentType() {
		return this;
	}
	
	public JpaStructureNode structureNode(int textOffset) {
		for (OrmPersistentAttribute attribute : CollectionTools.iterable(specifiedAttributes())) {
			if (attribute.containsOffset(textOffset)) {
				return attribute;
			}
		}
		return this;
	}
	
	public boolean containsOffset(int textOffset) {
		return this.ormTypeMapping.containsOffset(textOffset);
	}
	
	public TextRange selectionTextRange() {
		return this.ormTypeMapping.selectionTextRange();
	}
}
