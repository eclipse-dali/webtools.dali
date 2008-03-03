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
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmStructureNodes;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
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
import org.eclipse.jpt.core.resource.orm.XmlTransient;
import org.eclipse.jpt.core.resource.orm.XmlVersion;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ChainIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class GenericOrmPersistentType extends AbstractOrmJpaContextNode implements OrmPersistentType
{
	protected final List<OrmPersistentAttribute> specifiedPersistentAttributes;

	protected final List<OrmPersistentAttribute> virtualPersistentAttributes;

	protected final Collection<OrmTypeMappingProvider> typeMappingProviders;

	protected OrmTypeMapping ormTypeMapping;
	
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
	
	protected OrmTypeMapping buildOrmTypeMapping(String key) {
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

	public OrmTypeMapping getMapping() {
		return this.ormTypeMapping;
	}

	public void setMappingKey(String newMappingKey) {
		if (this.mappingKey() == newMappingKey) {
			return;
		}
		OrmTypeMapping oldMapping = getMapping();
		this.ormTypeMapping = buildOrmTypeMapping(newMappingKey);
		entityMappings().changeMapping(this, oldMapping, this.ormTypeMapping);
		firePropertyChanged(MAPPING_PROPERTY, oldMapping, this.ormTypeMapping);
	}
	
	protected void setMappingKey_(String newMappingKey) {
		if (this.mappingKey() == newMappingKey) {
			return;
		}
		OrmTypeMapping oldMapping = getMapping();
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
	
	public void changeMapping(OrmPersistentAttribute ormPersistentAttribute, OrmAttributeMapping oldMapping, OrmAttributeMapping newMapping) {
		int sourceIndex = this.specifiedPersistentAttributes.indexOf(ormPersistentAttribute);
		this.specifiedPersistentAttributes.remove(sourceIndex);
		oldMapping.removeFromResourceModel(getMapping().typeMappingResource());
		if (getMapping().typeMappingResource().getAttributes() == null) {
			getMapping().typeMappingResource().setAttributes(OrmFactory.eINSTANCE.createAttributes());
		}
		int targetIndex = insertionIndex(ormPersistentAttribute);
		this.specifiedPersistentAttributes.add(targetIndex, ormPersistentAttribute);
		newMapping.addToResourceModel(getMapping().typeMappingResource());
		oldMapping.initializeOn(newMapping);
		//TODO are the source and target correct in this case, or is target off by one???
		fireItemMoved(SPECIFIED_ATTRIBUTES_LIST, targetIndex, sourceIndex);
	}

	public void setPersistentAttributeVirtual(OrmPersistentAttribute ormPersistentAttribute, boolean virtual) {
		boolean oldVirtual = ormPersistentAttribute.isVirtual();
		if (oldVirtual == virtual) {
			return;
		}
		if (virtual) {	
			OrmPersistentAttribute virtualPersistentAttribute = createVirtualPersistentAttribute(ormPersistentAttribute.getMapping().getJavaPersistentAttribute());
			this.virtualPersistentAttributes.add(virtualPersistentAttribute);
			this.removeSpecifiedPersistentAttribute(ormPersistentAttribute);
			fireItemAdded(VIRTUAL_ATTRIBUTES_LIST, virtualAttributesSize(), virtualPersistentAttribute);
		}
		else {
			int index = this.virtualPersistentAttributes.indexOf(ormPersistentAttribute);
			this.virtualPersistentAttributes.remove(ormPersistentAttribute);
			addSpecifiedPersistentAttribute(ormPersistentAttribute.mappingKey(), ormPersistentAttribute.getName());
			fireItemRemoved(VIRTUAL_ATTRIBUTES_LIST, index, ormPersistentAttribute);
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
			protected boolean accept(OrmPersistentAttribute ormPersistentAttribute) {
				return attributeName.equals(ormPersistentAttribute.getName());
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
	
	protected void addVirtualPersistentAttribute(OrmPersistentAttribute ormPersistentAttribute) {
		addItemToList(ormPersistentAttribute, this.virtualPersistentAttributes, OrmPersistentType.VIRTUAL_ATTRIBUTES_LIST);
	}

	protected void removeVirtualPersistentAttribute(OrmPersistentAttribute ormPersistentAttribute) {
		removeItemFromList(ormPersistentAttribute, this.virtualPersistentAttributes, OrmPersistentType.VIRTUAL_ATTRIBUTES_LIST);
	}
	
	public boolean containsVirtualPersistentAttribute(OrmPersistentAttribute ormPersistentAttribute) {
		return this.virtualPersistentAttributes.contains(ormPersistentAttribute);
	}
	
	public OrmPersistentAttribute addSpecifiedPersistentAttribute(String mappingKey, String attributeName) {
		OrmPersistentAttribute persistentAttribute = jpaFactory().buildOrmPersistentAttribute(this, mappingKey);
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

	
	protected void addSpecifiedPersistentAttribute_(OrmPersistentAttribute ormPersistentAttribute) {
		addItemToList(ormPersistentAttribute, this.specifiedPersistentAttributes, PersistentType.SPECIFIED_ATTRIBUTES_LIST);
	}

	protected void removeSpecifiedPersistentAttribute_(OrmPersistentAttribute ormPersistentAttribute) {
		removeItemFromList(ormPersistentAttribute, this.specifiedPersistentAttributes, PersistentType.SPECIFIED_ATTRIBUTES_LIST);
	}

	public void removeSpecifiedPersistentAttribute(OrmPersistentAttribute ormPersistentAttribute) {
		int index = this.specifiedPersistentAttributes.indexOf(ormPersistentAttribute);
		this.specifiedPersistentAttributes.remove(ormPersistentAttribute);
		ormPersistentAttribute.getMapping().removeFromResourceModel(this.ormTypeMapping.typeMappingResource());
		fireItemRemoved(PersistentType.SPECIFIED_ATTRIBUTES_LIST, index, ormPersistentAttribute);		
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
		((OrmEntity) getMapping()).initialize(entity);
		this.initializeParentPersistentType();	
		this.initializePersistentAttributes(entity);
	}
	
	public void initialize(XmlMappedSuperclass mappedSuperclass) {
		((OrmMappedSuperclass) getMapping()).initialize(mappedSuperclass);
		this.initializeParentPersistentType();
		this.initializePersistentAttributes(mappedSuperclass);
	}
		
	public void initialize(XmlEmbeddable embeddable) {
		((OrmEmbeddable) getMapping()).initialize(embeddable);
		this.initializeParentPersistentType();		
		this.initializePersistentAttributes(embeddable);
	}
	
	protected void initializePersistentAttributes(AbstractXmlTypeMapping typeMapping) {
		Attributes attributes = typeMapping.getAttributes();
		if (attributes != null) {
			this.initializeSpecifiedPersistentAttributes(attributes);
		}
		this.initializeVirtualPersistentAttributes();
	}
	
	protected void initializeSpecifiedPersistentAttributes(Attributes attributes) {
		for (XmlId id : attributes.getIds()) {
			OrmPersistentAttribute ormPersistentAttribute = jpaFactory().buildOrmPersistentAttribute(this, MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
			ormPersistentAttribute.initialize(id);
			this.specifiedPersistentAttributes.add(ormPersistentAttribute);
		}
		for (XmlEmbeddedId embeddedId : attributes.getEmbeddedIds()) {
			OrmPersistentAttribute ormPersistentAttribute = jpaFactory().buildOrmPersistentAttribute(this, MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
			ormPersistentAttribute.initialize(embeddedId);
			this.specifiedPersistentAttributes.add(ormPersistentAttribute);
		}
		for (XmlBasic basic : attributes.getBasics()) {
			OrmPersistentAttribute ormPersistentAttribute = jpaFactory().buildOrmPersistentAttribute(this, MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
			ormPersistentAttribute.initialize(basic);
			this.specifiedPersistentAttributes.add(ormPersistentAttribute);
		}
		for (XmlVersion version : attributes.getVersions()) {
			OrmPersistentAttribute ormPersistentAttribute = jpaFactory().buildOrmPersistentAttribute(this, MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
			ormPersistentAttribute.initialize(version);
			this.specifiedPersistentAttributes.add(ormPersistentAttribute);
		}
		for (XmlManyToOne manyToOne : attributes.getManyToOnes()) {
			OrmPersistentAttribute ormPersistentAttribute = jpaFactory().buildOrmPersistentAttribute(this, MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
			ormPersistentAttribute.initialize(manyToOne);
			this.specifiedPersistentAttributes.add(ormPersistentAttribute);
		}
		for (XmlOneToMany oneToMany : attributes.getOneToManys()) {
			OrmPersistentAttribute ormPersistentAttribute = jpaFactory().buildOrmPersistentAttribute(this, MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
			ormPersistentAttribute.initialize(oneToMany);
			this.specifiedPersistentAttributes.add(ormPersistentAttribute);
		}
		for (XmlOneToOne oneToOne : attributes.getOneToOnes()) {
			OrmPersistentAttribute ormPersistentAttribute = jpaFactory().buildOrmPersistentAttribute(this, MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
			ormPersistentAttribute.initialize(oneToOne);
			this.specifiedPersistentAttributes.add(ormPersistentAttribute);
		}
		for (XmlManyToMany manyToMany : attributes.getManyToManys()) {
			OrmPersistentAttribute ormPersistentAttribute = jpaFactory().buildOrmPersistentAttribute(this, MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
			ormPersistentAttribute.initialize(manyToMany);
			this.specifiedPersistentAttributes.add(ormPersistentAttribute);
		}
		for (XmlEmbedded embedded : attributes.getEmbeddeds()) {
			OrmPersistentAttribute ormPersistentAttribute = jpaFactory().buildOrmPersistentAttribute(this, MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
			ormPersistentAttribute.initialize(embedded);
			this.specifiedPersistentAttributes.add(ormPersistentAttribute);
		}
		for (XmlTransient transientResource : attributes.getTransients()) {
			OrmPersistentAttribute ormPersistentAttribute = jpaFactory().buildOrmPersistentAttribute(this, MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
			ormPersistentAttribute.initialize(transientResource);
			this.specifiedPersistentAttributes.add(ormPersistentAttribute);
		}
	}
	
	protected void initializeVirtualPersistentAttributes() {
		ListIterator<JavaPersistentAttribute> javaAttributes = javaPersistentAttributes();
		
		while (javaAttributes.hasNext()) {
			JavaPersistentAttribute javaPersistentAttribute = javaAttributes.next();
			if (specifiedAttributeNamed(javaPersistentAttribute.getName()) == null) {
				OrmPersistentAttribute ormPersistentAttribute = createVirtualPersistentAttribute(javaPersistentAttribute);
				this.virtualPersistentAttributes.add(ormPersistentAttribute);
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
			((OrmEntity) getMapping()).update(entity);
		}
		else {
			setMappingKey_(MappingKeys.ENTITY_TYPE_MAPPING_KEY);
			((OrmEntity) getMapping()).initialize(entity);					
		}
		this.updateParentPersistentType();
		this.updatePersistentAttributes(entity);
	}
	
	public void update(XmlMappedSuperclass mappedSuperclass) {
		if (mappingKey() == MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY) {
			((OrmMappedSuperclass) getMapping()).update(mappedSuperclass);
		}
		else {
			setMappingKey_(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
			((OrmMappedSuperclass) getMapping()).initialize(mappedSuperclass);
		}
		this.updateParentPersistentType();
		this.updatePersistentAttributes(mappedSuperclass);
	}
	
	public void update(XmlEmbeddable embeddable) {
		if (mappingKey() == MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY) {
			((OrmEmbeddable) getMapping()).update(embeddable);
		}
		else {
			setMappingKey_(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
			((OrmEmbeddable) getMapping()).initialize(embeddable);				
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

	protected void updatePersistentAttributes(AbstractXmlTypeMapping typeMapping) {
		ListIterator<OrmPersistentAttribute> ormPersistentAttributes = this.specifiedAttributes();
		if (typeMapping.getAttributes() != null) {
			this.updateIds(typeMapping.getAttributes(), ormPersistentAttributes);
			this.updateEmbeddedIds(typeMapping.getAttributes(), ormPersistentAttributes);
			this.updateBasics(typeMapping.getAttributes(), ormPersistentAttributes);
			this.updateVersions(typeMapping.getAttributes(), ormPersistentAttributes);
			this.updateManyToOnes(typeMapping.getAttributes(), ormPersistentAttributes);
			this.updateOneToManys(typeMapping.getAttributes(), ormPersistentAttributes);
			this.updateOneToOnes(typeMapping.getAttributes(), ormPersistentAttributes);
			this.updateManyToManys(typeMapping.getAttributes(), ormPersistentAttributes);
			this.updateEmbeddeds(typeMapping.getAttributes(), ormPersistentAttributes);		
			this.updateTransients(typeMapping.getAttributes(), ormPersistentAttributes);		
		}
		while (ormPersistentAttributes.hasNext()) {
			this.removeSpecifiedPersistentAttribute_(ormPersistentAttributes.next());
		}	
		this.updateVirtualPersistentAttributes();
	}
	
	protected void updateVirtualAttribute(OrmPersistentAttribute xmlAttribute, JavaPersistentAttribute javaAttribute) {
		if (javaAttribute.mappingKey() == MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualXmlBasic(getMapping(), (JavaBasicMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualXmlEmbedded(getMapping(), (JavaEmbeddedMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualXmlEmbeddedId(getMapping(), (JavaEmbeddedIdMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.ID_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualXmlId(getMapping(), (JavaIdMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualXmlManyToMany((JavaManyToManyMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualXmlManyToOne((JavaManyToOneMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualXmlOneToMany((JavaOneToManyMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualXmlOneToOne((JavaOneToOneMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualXmlTransient((JavaTransientMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY) {
			xmlAttribute.update(new VirtualXmlVersion(getMapping(), (JavaVersionMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
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
					OrmPersistentAttribute ormPersistentAttribute = createVirtualPersistentAttribute(javaAttribute);
					addVirtualPersistentAttribute(ormPersistentAttribute);
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
		OrmPersistentAttribute ormPersistentAttribute = jpaFactory().buildOrmPersistentAttribute(this, javaAttribute.mappingKey());
		if (javaAttribute.mappingKey() == MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY) {
			ormPersistentAttribute.initialize(new VirtualXmlBasic(getMapping(), (JavaBasicMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY) {
			ormPersistentAttribute.initialize(new VirtualXmlEmbeddedId(getMapping(), (JavaEmbeddedIdMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY) {
			ormPersistentAttribute.initialize(new VirtualXmlEmbedded(getMapping(), (JavaEmbeddedMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.ID_ATTRIBUTE_MAPPING_KEY) {
			ormPersistentAttribute.initialize(new VirtualXmlId(getMapping(), (JavaIdMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			ormPersistentAttribute.initialize(new VirtualXmlManyToMany((JavaManyToManyMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			ormPersistentAttribute.initialize(new VirtualXmlManyToOne((JavaManyToOneMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			ormPersistentAttribute.initialize(new VirtualXmlOneToMany((JavaOneToManyMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			ormPersistentAttribute.initialize(new VirtualXmlOneToOne((JavaOneToOneMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY) {
			ormPersistentAttribute.initialize(new VirtualXmlTransient((JavaTransientMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		else if (javaAttribute.mappingKey() == MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY) {
			ormPersistentAttribute.initialize(new VirtualXmlVersion(getMapping(), (JavaVersionMapping) javaAttribute.getMapping(), getMapping().isMetadataComplete()));
		}
		return ormPersistentAttribute;
	}

	protected void updateIds(Attributes attributes, ListIterator<OrmPersistentAttribute> ormPersistentAttributes) {
		for (XmlId id : attributes.getIds()) {
			if (ormPersistentAttributes.hasNext()) {
				ormPersistentAttributes.next().update(id);
			}
			else {
				OrmPersistentAttribute ormPersistentAttribute = jpaFactory().buildOrmPersistentAttribute(this, MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
				ormPersistentAttribute.initialize(id);
				addSpecifiedPersistentAttribute_(ormPersistentAttribute);
			}
		}
	}
	
	protected void updateEmbeddedIds(Attributes attributes, ListIterator<OrmPersistentAttribute> ormPersistentAttributes) {
		for (XmlEmbeddedId embeddedId : attributes.getEmbeddedIds()) {
			if (ormPersistentAttributes.hasNext()) {
				ormPersistentAttributes.next().update(embeddedId);
			}
			else {
				OrmPersistentAttribute ormPersistentAttribute = jpaFactory().buildOrmPersistentAttribute(this, MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
				ormPersistentAttribute.initialize(embeddedId);
				addSpecifiedPersistentAttribute_(ormPersistentAttribute);
			}
		}
	}
	
	protected void updateBasics(Attributes attributes, ListIterator<OrmPersistentAttribute> ormPersistentAttributes) {
		for (XmlBasic basic : attributes.getBasics()) {
			if (ormPersistentAttributes.hasNext()) {
				ormPersistentAttributes.next().update(basic);
			}
			else {
				OrmPersistentAttribute ormPersistentAttribute = jpaFactory().buildOrmPersistentAttribute(this, MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
				ormPersistentAttribute.initialize(basic);
				addSpecifiedPersistentAttribute_(ormPersistentAttribute);
			}
		}
	}
	
	protected void updateVersions(Attributes attributes, ListIterator<OrmPersistentAttribute> ormPersistentAttributes) {
		for (XmlVersion version : attributes.getVersions()) {
			if (ormPersistentAttributes.hasNext()) {
				ormPersistentAttributes.next().update(version);
			}
			else {
				OrmPersistentAttribute ormPersistentAttribute = jpaFactory().buildOrmPersistentAttribute(this, MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
				ormPersistentAttribute.initialize(version);
				addSpecifiedPersistentAttribute_(ormPersistentAttribute);
			}
		}
	}
	protected void updateManyToOnes(Attributes attributes, ListIterator<OrmPersistentAttribute> ormPersistentAttributes) {
		for (XmlManyToOne manyToOne : attributes.getManyToOnes()) {
			if (ormPersistentAttributes.hasNext()) {
				ormPersistentAttributes.next().update(manyToOne);
			}
			else {
				OrmPersistentAttribute ormPersistentAttribute = jpaFactory().buildOrmPersistentAttribute(this, MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
				ormPersistentAttribute.initialize(manyToOne);
				addSpecifiedPersistentAttribute_(ormPersistentAttribute);
			}
		}
	}
	protected void updateOneToManys(Attributes attributes, ListIterator<OrmPersistentAttribute> ormPersistentAttributes) {
		for (XmlOneToMany oneToMany : attributes.getOneToManys()) {
			if (ormPersistentAttributes.hasNext()) {
				ormPersistentAttributes.next().update(oneToMany);
			}
			else {
				OrmPersistentAttribute ormPersistentAttribute = jpaFactory().buildOrmPersistentAttribute(this, MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
				ormPersistentAttribute.initialize(oneToMany);
				addSpecifiedPersistentAttribute_(ormPersistentAttribute);
			}
		}
	}
	protected void updateOneToOnes(Attributes attributes, ListIterator<OrmPersistentAttribute> ormPersistentAttributes) {
		for (XmlOneToOne oneToOne : attributes.getOneToOnes()) {
			if (ormPersistentAttributes.hasNext()) {
				ormPersistentAttributes.next().update(oneToOne);
			}
			else {
				OrmPersistentAttribute ormPersistentAttribute = jpaFactory().buildOrmPersistentAttribute(this, MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
				ormPersistentAttribute.initialize(oneToOne);
				addSpecifiedPersistentAttribute_(ormPersistentAttribute);
			}
		}
	}
	protected void updateManyToManys(Attributes attributes, ListIterator<OrmPersistentAttribute> ormPersistentAttributes) {
		for (XmlManyToMany manyToMany : attributes.getManyToManys()) {
			if (ormPersistentAttributes.hasNext()) {
				ormPersistentAttributes.next().update(manyToMany);
			}
			else {
				OrmPersistentAttribute ormPersistentAttribute = jpaFactory().buildOrmPersistentAttribute(this, MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
				ormPersistentAttribute.initialize(manyToMany);
				addSpecifiedPersistentAttribute_(ormPersistentAttribute);
			}
		}
	}

	protected void updateEmbeddeds(Attributes attributes, ListIterator<OrmPersistentAttribute> ormPersistentAttributes) {
		for (XmlEmbedded embedded : attributes.getEmbeddeds()) {
			if (ormPersistentAttributes.hasNext()) {
				ormPersistentAttributes.next().update(embedded);
			}
			else {
				OrmPersistentAttribute ormPersistentAttribute = jpaFactory().buildOrmPersistentAttribute(this, MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
				ormPersistentAttribute.initialize(embedded);
				addSpecifiedPersistentAttribute_(ormPersistentAttribute);
			}
		}
	}
	
	protected void updateTransients(Attributes attributes, ListIterator<OrmPersistentAttribute> ormPersistentAttributes) {
		for (XmlTransient transientResource : attributes.getTransients()) {
			if (ormPersistentAttributes.hasNext()) {
				ormPersistentAttributes.next().update(transientResource);
			}
			else {
				OrmPersistentAttribute ormPersistentAttribute = jpaFactory().buildOrmPersistentAttribute(this, MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
				ormPersistentAttribute.initialize(transientResource);
				addSpecifiedPersistentAttribute_(ormPersistentAttribute);
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
			if (attribute.contains(textOffset)) {
				return attribute;
			}
		}
		return this;
	}
	
	public boolean contains(int textOffset) {
		return this.ormTypeMapping.containsOffset(textOffset);
	}
	
	public TextRange selectionTextRange() {
		return this.ormTypeMapping.selectionTextRange();
	}
	
	//******************** validation **********************
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);

		getMapping().addToMessages(messages);
		
		for (OrmPersistentAttribute persistentAttribute : CollectionTools.iterable(this.attributes())) {
			persistentAttribute.addToMessages(messages);
		}
	}
	
	public TextRange validationTextRange() {
		return this.ormTypeMapping.validationTextRange();
	}
}
