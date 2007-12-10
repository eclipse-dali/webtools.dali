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
import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.core.internal.context.base.JpaContextNode;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.resource.orm.Embeddable;
import org.eclipse.jpt.core.internal.resource.orm.Entity;
import org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass;
import org.eclipse.jpt.core.internal.resource.orm.TypeMapping;
import org.eclipse.jpt.utility.internal.iterators.ChainIterator;


public class XmlPersistentType extends JpaContextNode implements IPersistentType
{
//	protected EList<XmlAttributeMapping> specifiedAttributeMappings;
//
//	protected EList<XmlAttributeMapping> virtualAttributeMappings;
//
//	protected EList<XmlPersistentAttribute> specifiedPersistentAttributes;
//
//	protected EList<XmlPersistentAttribute> virtualPersistentAttributes;

	protected final Collection<IXmlTypeMappingProvider> typeMappingProviders;

	protected XmlTypeMapping<? extends TypeMapping> xmlTypeMapping;
	
	protected IPersistentType parentPersistentType;

	protected org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings;
	
	public XmlPersistentType(EntityMappings parent, String mappingKey, org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappingsResource) {
		super(parent);
		this.typeMappingProviders = buildTypeMappingProviders();
		this.entityMappings = entityMappingsResource;
		this.xmlTypeMapping = buildXmlTypeMapping(mappingKey);
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
		for (IXmlTypeMappingProvider provider : this.typeMappingProviders) {
			if (provider.key().equals(key)) {
				return provider.buildTypeMapping(jpaFactory(), this);
			}
		}
		throw new IllegalArgumentException();
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
	
	protected void createAndAddOrmResourceMapping(String mappingKey, String className) {
		IXmlTypeMappingProvider xmlTypeMappingProvider = typeMappingProvider(mappingKey);
		xmlTypeMappingProvider.createAndAddOrmResourceMapping(this, this.entityMappings, className);
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

//	public EList<XmlAttributeMapping> getAttributeMappings() {
//		EList<XmlAttributeMapping> list = new EObjectEList<XmlAttributeMapping>(XmlAttributeMapping.class, this, OrmPackage.XML_PERSISTENT_TYPE__ATTRIBUTE_MAPPINGS);
//		list.addAll(getSpecifiedAttributeMappings());
//		list.addAll(getVirtualAttributeMappings());
//		return list;
//	}
//
//	public EList<XmlAttributeMapping> getSpecifiedAttributeMappingsGen() {
//		if (specifiedAttributeMappings == null) {
//			specifiedAttributeMappings = new EObjectContainmentEList<XmlAttributeMapping>(XmlAttributeMapping.class, this, OrmPackage.XML_PERSISTENT_TYPE__SPECIFIED_ATTRIBUTE_MAPPINGS);
//		}
//		return specifiedAttributeMappings;
//	}
//
//	public EList<XmlAttributeMapping> getSpecifiedAttributeMappings() {
//		if (specifiedAttributeMappings == null) {
//			specifiedAttributeMappings = new SpecifiedAttributeMappingsList<XmlAttributeMapping>();
//		}
//		return getSpecifiedAttributeMappingsGen();
//	}
//
//	public EList<XmlAttributeMapping> getVirtualAttributeMappingsGen() {
//		if (virtualAttributeMappings == null) {
//			virtualAttributeMappings = new EObjectContainmentEList<XmlAttributeMapping>(XmlAttributeMapping.class, this, OrmPackage.XML_PERSISTENT_TYPE__VIRTUAL_ATTRIBUTE_MAPPINGS);
//		}
//		return virtualAttributeMappings;
//	}
//
//	public EList<XmlAttributeMapping> getVirtualAttributeMappings() {
//		if (virtualAttributeMappings == null) {
//			virtualAttributeMappings = new VirtualAttributeMappingsList<XmlAttributeMapping>();
//		}
//		return getVirtualAttributeMappingsGen();
//	}
//
//	public EList<XmlPersistentAttribute> getPersistentAttributes() {
//		EList<XmlPersistentAttribute> list = new EObjectEList<XmlPersistentAttribute>(XmlPersistentAttribute.class, this, OrmPackage.XML_PERSISTENT_TYPE__PERSISTENT_ATTRIBUTES);
//		list.addAll(getSpecifiedPersistentAttributes());
//		list.addAll(getVirtualPersistentAttributes());
//		return list;
//	}
//
//	public EList<XmlPersistentAttribute> getSpecifiedPersistentAttributes() {
//		if (specifiedPersistentAttributes == null) {
//			specifiedPersistentAttributes = new EObjectEList<XmlPersistentAttribute>(XmlPersistentAttribute.class, this, OrmPackage.XML_PERSISTENT_TYPE__SPECIFIED_PERSISTENT_ATTRIBUTES);
//		}
//		return specifiedPersistentAttributes;
//	}
//
//	public EList<XmlPersistentAttribute> getVirtualPersistentAttributes() {
//		if (virtualPersistentAttributes == null) {
//			virtualPersistentAttributes = new EObjectEList<XmlPersistentAttribute>(XmlPersistentAttribute.class, this, OrmPackage.XML_PERSISTENT_TYPE__VIRTUAL_PERSISTENT_ATTRIBUTES);
//		}
//		return virtualPersistentAttributes;
//	}
//
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
//	private XmlAttributeMapping buildAttributeMapping(Collection<IXmlAttributeMappingProvider> providers, String key) {
//		for (IXmlAttributeMappingProvider provider : providers) {
//			if (provider.key().equals(key)) {
//				return provider.buildAttributeMapping();
//			}
//		}
//		return OrmFactory.eINSTANCE.createXmlNullAttributeMapping();
//	}
//
//	public Collection<IXmlTypeMappingProvider> typeMappingProviders() {
//		return this.typeMappingProviders;
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
//
//	private void insertAttributeMapping(XmlAttributeMapping newMapping, List<XmlAttributeMapping> attributeMappings) {
//		int newIndex = CollectionTools.insertionIndexOf(attributeMappings, newMapping, buildMappingComparator());
//		attributeMappings.add(newIndex, newMapping);
//	}
//
//	private Comparator<XmlAttributeMapping> buildMappingComparator() {
//		return new Comparator<XmlAttributeMapping>() {
//			public int compare(XmlAttributeMapping o1, XmlAttributeMapping o2) {
//				int o1Sequence = o1.xmlSequence();
//				int o2Sequence = o2.xmlSequence();
//				if (o1Sequence < o2Sequence) {
//					return -1;
//				}
//				if (o1Sequence == o2Sequence) {
//					return 0;
//				}
//				return 1;
//			}
//		};
//	}
//
//	public Iterator<XmlPersistentAttribute> attributes() {
//		return new ReadOnlyIterator<XmlPersistentAttribute>(getPersistentAttributes());
//	}
//
//	public Iterator<String> attributeNames() {
//		return this.attributeNames(this.attributes());
//	}
//
//	private Iterator<String> attributeNames(Iterator<? extends IPersistentAttribute> attrs) {
//		return new TransformationIterator<IPersistentAttribute, String>(attrs) {
//			@Override
//			protected String transform(IPersistentAttribute attribute) {
//				return attribute.getName();
//			}
//		};
//	}
//
//	public Iterator<IPersistentAttribute> allAttributes() {
//		return new CompositeIterator<IPersistentAttribute>(new TransformationIterator<IPersistentType, Iterator<IPersistentAttribute>>(this.inheritanceHierarchy()) {
//			@Override
//			protected Iterator<IPersistentAttribute> transform(IPersistentType pt) {
//				//TODO how to remove this warning?
//				return (Iterator<IPersistentAttribute>) pt.attributes();
//			}
//		});
//	}
//
//	public Iterator<String> allAttributeNames() {
//		return this.attributeNames(this.allAttributes());
//	}

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


	public Iterator<String> allAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}


	public Iterator<IPersistentAttribute> allAttributes() {
		// TODO Auto-generated method stub
		return null;
	}


	public IPersistentAttribute attributeNamed(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}


	public Iterator<String> attributeNames() {
		// TODO Auto-generated method stub
		return null;
	}


	public <T extends IPersistentAttribute> ListIterator<T> attributes() {
		// TODO Auto-generated method stub
		return null;
	}


	public int attributesSize() {
		// TODO Auto-generated method stub
		return 0;
	}


	public String getName() {
		// TODO Auto-generated method stub
		return null;
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
	}
	
	public void initialize(MappedSuperclass mappedSuperclass) {
		((XmlMappedSuperclass) getMapping()).initialize(mappedSuperclass);
		this.initializeParentPersistentType();		
	}
	
	public void initialize(Embeddable embeddable) {
		((XmlEmbeddable) getMapping()).initialize(embeddable);
		this.initializeParentPersistentType();		
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


//	public IJpaContentNode getContentNode(int offset) {
//		for (XmlAttributeMapping mapping : this.getSpecifiedAttributeMappings()) {
//			if (mapping.getNode().contains(offset)) {
//				return mapping.getContentNode(offset);
//			}
//		}
//		return this;
//	}
//
//	public void refreshDefaults(DefaultsContext context) {
//		refreshParentPersistentType(context);
//	}
//
//	private void refreshParentPersistentType(DefaultsContext context) {
//		JavaPersistentType javaPersistentType = findJavaPersistentType();
//		if (javaPersistentType == null) {
//			this.parentPersistentType = null;
//			return;
//		}
//		//TODO need to fix the performance issue that results here
//		//setting this back for now because of bug 200957 in the M1 release
//		ITypeBinding typeBinding = javaPersistentType.getType().typeBinding(javaPersistentType.getType().astRoot());
//		IPersistentType parentPersistentType = JavaPersistentType.parentPersistentType(context, typeBinding);
//		this.parentPersistentType = parentPersistentType;
//		return;
//	}
//
//	protected Iterator<XmlPersistentAttribute> attributesNamed(final String attributeName) {
//		return new FilteringIterator<XmlPersistentAttribute>(getPersistentAttributes().iterator()) {
//			@Override
//			protected boolean accept(Object o) {
//				return attributeName.equals(((XmlPersistentAttribute) o).getName());
//			}
//		};
//	}
//
//	public XmlPersistentAttribute attributeNamed(String attributeName) {
//		Iterator<XmlPersistentAttribute> attributes = attributesNamed(attributeName);
//		return attributes.hasNext() ? attributes.next() : null;
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
//	private abstract class AttributeMappingsList<E>
//		extends EObjectContainmentEList<XmlAttributeMapping>
//	{
//		AttributeMappingsList(int feature) {
//			super(XmlAttributeMapping.class, XmlPersistentType.this, feature);
//		}
//
//		protected abstract EList<XmlPersistentAttribute> persistentAttributes();
//
//		@Override
//		protected void didAdd(int index, XmlAttributeMapping newObject) {
//			if (newObject.getPersistentAttribute() == null) {
//				throw new IllegalStateException("Must set the PersistentAttribute during creation");
//			}
//			persistentAttributes().add(index, newObject.getPersistentAttribute());
//		}
//
//		@Override
//		protected void didChange() {
//			// TODO Auto-generated method stub
//			super.didChange();
//		}
//
//		@Override
//		protected void didClear(int len, Object[] oldObjects) {
//			persistentAttributes().clear();
//		}
//
//		@Override
//		protected void didMove(int index, XmlAttributeMapping movedObject, int oldIndex) {
//			persistentAttributes().move(index, movedObject.getPersistentAttribute());
//		}
//
//		@Override
//		protected void didRemove(int index, XmlAttributeMapping oldObject) {
//			persistentAttributes().remove(oldObject.getPersistentAttribute());
//		}
//
//		@Override
//		protected void didSet(int index, XmlAttributeMapping newObject, XmlAttributeMapping oldObject) {
//			persistentAttributes().set(index, newObject.getPersistentAttribute());
//		}
//	}
//	private class SpecifiedAttributeMappingsList<E>
//		extends AttributeMappingsList<XmlAttributeMapping>
//	{
//		SpecifiedAttributeMappingsList() {
//			super(OrmPackage.XML_PERSISTENT_TYPE__SPECIFIED_ATTRIBUTE_MAPPINGS);
//		}
//
//		@Override
//		protected EList<XmlPersistentAttribute> persistentAttributes() {
//			return getSpecifiedPersistentAttributes();
//		}
//	}
//	private class VirtualAttributeMappingsList<E>
//		extends AttributeMappingsList<XmlAttributeMapping>
//	{
//		VirtualAttributeMappingsList() {
//			super(OrmPackage.XML_PERSISTENT_TYPE__VIRTUAL_ATTRIBUTE_MAPPINGS);
//		}
//
//		@Override
//		protected EList<XmlPersistentAttribute> persistentAttributes() {
//			return getVirtualPersistentAttributes();
//		}
//	}
}
