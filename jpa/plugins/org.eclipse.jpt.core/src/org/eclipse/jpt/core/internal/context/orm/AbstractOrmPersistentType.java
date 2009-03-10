/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
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

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmStructureNodes;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ChainIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractOrmPersistentType
	extends AbstractXmlContextNode
	implements OrmPersistentType
{
	protected final List<OrmPersistentAttribute> specifiedPersistentAttributes;

	protected final List<OrmPersistentAttribute> virtualPersistentAttributes;
	
	protected AccessType defaultAccess;
	
	protected AccessType specifiedAccess;

	protected OrmTypeMapping typeMapping;
	
	protected PersistentType parentPersistentType;
	
	protected JavaPersistentType javaPersistentType;

	
	protected AbstractOrmPersistentType(EntityMappings parent, XmlTypeMapping resourceMapping) {
		super(parent);
		this.specifiedPersistentAttributes = new ArrayList<OrmPersistentAttribute>();
		this.virtualPersistentAttributes = new ArrayList<OrmPersistentAttribute>();
		this.typeMapping = buildTypeMapping(resourceMapping);
		this.specifiedAccess = this.getResourceAccess();
		this.defaultAccess = this.buildDefaultAccess();
		this.initializeJavaPersistentType();
		this.initializeParentPersistentType();	
		this.initializePersistentAttributes();
	}


	// ********** OrmPersistentType implementation **********
	
	@Override
	public EntityMappings getParent() {
		return (EntityMappings) super.getParent();
	}
	
	protected EntityMappings getEntityMappings() {
		return this.getParent();
	}

	public IContentType getContentType() {
		return this.getEntityMappings().getContentType();
	}

	public String getDefaultPackage() {
		return this.getEntityMappings().getDefaultPersistentTypePackage();
	}

	public boolean isDefaultMetadataComplete() {
		return this.getEntityMappings().isDefaultPersistentTypeMetadataComplete();
	}
	
	public String getId() {
		return OrmStructureNodes.PERSISTENT_TYPE_ID;
	}

	public boolean isFor(String typeName) {
		String mappingClassName = this.getMapping().getClass_();
		if (mappingClassName == null) {
			return false;
		}
		if (mappingClassName.equals(typeName)) {
			return true;
		}
		return (this.getDefaultPackage() + '.' +  mappingClassName).equals(typeName);
	}
	
	protected OrmTypeMapping buildTypeMapping(XmlTypeMapping resourceMapping) {
		return this.getJpaPlatform().buildOrmTypeMappingFromMappingKey(this, resourceMapping);
	}

	public OrmTypeMapping getMapping() {
		return this.typeMapping;
	}

	// **************** PersistentType.Owner implementation *****************************
	
	public AccessType getOverridePersistentTypeAccess() {
		if (this.getSpecifiedAccess() != null) {
			return this.getSpecifiedAccess();
		}

		PersistentType ppt = this.getParentPersistentType();
		if (ppt instanceof OrmPersistentType) {
			AccessType accessType = ((OrmPersistentType) ppt).getSpecifiedAccess();
			if (accessType != null) {
				return accessType;
			}
		}

		if (this.getMapping().isMetadataComplete()) {
			AccessType accessType = this.getOwnerDefaultAccess();
			if (accessType != null) {
				return accessType;
			}
		}

		// no override access type
		return null;
	}
	
	public AccessType getDefaultPersistentTypeAccess() {
		PersistentType ppt = this.getParentPersistentType();
		if (ppt instanceof OrmPersistentType) {
			AccessType accessType = ((OrmPersistentType) ppt).getDefaultAccess();
			if (accessType != null) {
				return accessType;
			}
		}

		return this.getOwnerDefaultAccess();
	}

	// ********** PersistentType implementation **********
	
	public void setMappingKey(String newMappingKey) {
		if (this.getMappingKey() == newMappingKey) {
			return;
		}
		OrmTypeMapping oldMapping = getMapping();
		XmlTypeMapping resourceTypeMapping = getJpaPlatform().buildOrmResourceTypeMapping(newMappingKey, getContentType());
		this.typeMapping = buildTypeMapping(resourceTypeMapping);
		this.getEntityMappings().changeMapping(this, oldMapping, this.typeMapping);
		firePropertyChanged(MAPPING_PROPERTY, oldMapping, this.typeMapping);
	}

	public Iterator<PersistentType> inheritanceHierarchy() {
		// using a chain iterator to traverse up the inheritance tree
		return new ChainIterator<PersistentType>(this) {
			@Override
			protected PersistentType nextLink(PersistentType pt) {
				return pt.getParentPersistentType();
			}
		};
	}

	public Iterator<PersistentType> ancestors() {
		// using a chain iterator to traverse up the inheritance tree
		return new ChainIterator<PersistentType>(this.getParentPersistentType()) {
			@Override
			protected PersistentType nextLink(PersistentType pt) {
				return pt.getParentPersistentType();
			}
		};
	}

	public PersistentType getParentPersistentType() {
		return this.parentPersistentType;
	}
	
	public void setParentPersistentType(PersistentType newParentPersistentType) {
		if (attributeValueHasNotChanged(this.parentPersistentType, newParentPersistentType)) {
			return;
		}
		PersistentType oldParentPersistentType = this.parentPersistentType;
		this.parentPersistentType = newParentPersistentType;
		firePropertyChanged(PersistentType.PARENT_PERSISTENT_TYPE_PROPERTY, oldParentPersistentType, newParentPersistentType);
	}
	
	public AccessType getDefaultAccess() {
		return this.defaultAccess;
	}

	protected void setDefaultAccess(AccessType newDefaultAccess) {
		AccessType oldDefaultAccess = this.defaultAccess;
		this.defaultAccess = newDefaultAccess;
		firePropertyChanged(DEFAULT_ACCESS_PROPERTY, oldDefaultAccess, newDefaultAccess);
	}

	public AccessType getSpecifiedAccess() {
		return this.specifiedAccess;
	}

	public void setSpecifiedAccess(AccessType newSpecifiedAccess) {
		AccessType oldSpecifiedAccess = this.specifiedAccess;
		this.specifiedAccess = newSpecifiedAccess;
		this.getResourceTypeMapping().setAccess(AccessType.toOrmResourceModel(newSpecifiedAccess));
		firePropertyChanged(SPECIFIED_ACCESS_PROPERTY, oldSpecifiedAccess, newSpecifiedAccess);
	}

	public AccessType getAccess() {
		return (this.getSpecifiedAccess() == null) ? this.getDefaultAccess() : this.getSpecifiedAccess();
	}
		
	public AccessType getOwnerOverrideAccess() {
		return this.getEntityMappings().getOverridePersistentTypeAccess();
	}

	public AccessType getOwnerDefaultAccess() {
		return this.getEntityMappings().getDefaultPersistentTypeAccess();
	}

	public void changeMapping(OrmPersistentAttribute ormPersistentAttribute, OrmAttributeMapping oldMapping, OrmAttributeMapping newMapping) {
		int sourceIndex = this.specifiedPersistentAttributes.indexOf(ormPersistentAttribute);
		this.specifiedPersistentAttributes.remove(sourceIndex);
		oldMapping.removeFromResourceModel(getResourceAttributes());
		int targetIndex = insertionIndex(ormPersistentAttribute);
		this.specifiedPersistentAttributes.add(targetIndex, ormPersistentAttribute);
		newMapping.addToResourceModel(getResourceAttributes());
		oldMapping.initializeOn(newMapping);
		fireItemMoved(SPECIFIED_ATTRIBUTES_LIST, targetIndex, sourceIndex);
	}

	public void makePersistentAttributeVirtual(OrmPersistentAttribute ormPersistentAttribute) {
		if (ormPersistentAttribute.isVirtual()) {
			throw new IllegalStateException("Attribute is already virtual"); //$NON-NLS-1$
		}
		JavaPersistentAttribute javaPersistentAttribute = ormPersistentAttribute.getJavaPersistentAttribute();
		OrmPersistentAttribute virtualPersistentAttribute = null;
		if (javaPersistentAttribute != null) {
			virtualPersistentAttribute = addVirtualPersistentAttribute(javaPersistentAttribute.getResourcePersistentAttribute());
		}
		this.removeSpecifiedPersistentAttribute(ormPersistentAttribute);
		if (virtualPersistentAttribute != null) {
			fireItemAdded(VIRTUAL_ATTRIBUTES_LIST, virtualAttributesSize() - 1, virtualPersistentAttribute);
		}
	}
	
	public void makePersistentAttributeSpecified(OrmPersistentAttribute ormPersistentAttribute) {
		makePersistentAttributeSpecified(ormPersistentAttribute, ormPersistentAttribute.getMappingKey());		
	}

	public void makePersistentAttributeSpecified(OrmPersistentAttribute ormPersistentAttribute, String mappingKey) {
		if (!ormPersistentAttribute.isVirtual()) {
			throw new IllegalStateException("Attribute is already specified"); //$NON-NLS-1$
		}
		if (mappingKey == MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY) {
			throw new IllegalStateException("Use makePersistentAttributeSpecified(OrmPersistentAttribute, String) instead and specify a mapping type"); //$NON-NLS-1$
		}
		
		Attributes resourceAttributes = getResourceAttributes();
		if (resourceAttributes == null) {
			resourceAttributes = createResourceAttributes();
			getMapping().getResourceTypeMapping().setAttributes(resourceAttributes);
		}
		
		XmlAttributeMapping resourceMapping = getJpaPlatform().buildOrmResourceAttributeMapping(mappingKey, getContentType());
		
		OrmPersistentAttribute newPersistentAttribute = buildSpecifiedOrmPersistentAttribute(resourceMapping);
		int insertionIndex = insertionIndex(newPersistentAttribute);
		this.specifiedPersistentAttributes.add(insertionIndex, newPersistentAttribute);
		newPersistentAttribute.getMapping().addToResourceModel(resourceAttributes);
		
		int removalIndex = this.virtualPersistentAttributes.indexOf(ormPersistentAttribute);
		this.virtualPersistentAttributes.remove(ormPersistentAttribute);
		newPersistentAttribute.getSpecifiedMapping().setName(ormPersistentAttribute.getName());
		if (ormPersistentAttribute.getJavaPersistentAttribute().getSpecifiedAccess() != null) {
			newPersistentAttribute.setSpecifiedAccess(ormPersistentAttribute.getJavaPersistentAttribute().getSpecifiedAccess());
		}
		
		fireItemAdded(PersistentType.SPECIFIED_ATTRIBUTES_LIST, insertionIndex, newPersistentAttribute);
		fireItemRemoved(VIRTUAL_ATTRIBUTES_LIST, removalIndex, ormPersistentAttribute);
	}

	protected XmlTypeMapping getResourceTypeMapping() {
		return this.typeMapping.getResourceTypeMapping();
	}
	
	protected Attributes getResourceAttributes() {
		return this.getResourceTypeMapping().getAttributes();
	}
	
	protected abstract Attributes createResourceAttributes();
	
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
				if (attributeName == null && o.getName() == null) {
					return true;
				}
				if (attributeName != null && attributeName.equals(o.getName())) {
					return true;
				}
				return false;
			}
		};
	}

	public OrmPersistentAttribute getAttributeNamed(String attributeName) {
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
	
	protected OrmPersistentAttribute getSpecifiedAttributeFor(final JavaResourcePersistentAttribute jrpa) {
		for (OrmPersistentAttribute persistentAttribute : CollectionTools.iterable(specifiedAttributes())) {
			if (persistentAttribute.getJavaPersistentAttribute() == null) {
				continue;
			}
			if ( jrpa.equals(persistentAttribute.getJavaPersistentAttribute().getResourcePersistentAttribute())) {
				return persistentAttribute;
			}
		}
		return null;
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
	
	protected void moveVirtualPersistentAttribute_(int index, OrmPersistentAttribute attribute) {
		moveItemInList(index, this.virtualPersistentAttributes.indexOf(attribute), this.virtualPersistentAttributes, OrmPersistentType.VIRTUAL_ATTRIBUTES_LIST);
	}

	public boolean containsVirtualPersistentAttribute(OrmPersistentAttribute ormPersistentAttribute) {
		return this.virtualPersistentAttributes.contains(ormPersistentAttribute);
	}
	
	public OrmPersistentAttribute addSpecifiedPersistentAttribute(String mappingKey, String attributeName) {
		Attributes resourceAttributes = getResourceAttributes();
		if (resourceAttributes == null) {
			resourceAttributes = createResourceAttributes();
			getMapping().getResourceTypeMapping().setAttributes(resourceAttributes);
		}
		
		XmlAttributeMapping resourceMapping = getJpaPlatform().buildOrmResourceAttributeMapping(mappingKey, getContentType());
		OrmPersistentAttribute persistentAttribute = buildSpecifiedOrmPersistentAttribute(resourceMapping);
		int index = insertionIndex(persistentAttribute);
		this.specifiedPersistentAttributes.add(index, persistentAttribute);
		persistentAttribute.getMapping().addToResourceModel(resourceAttributes);
		
		persistentAttribute.getSpecifiedMapping().setName(attributeName);
		fireItemAdded(PersistentType.SPECIFIED_ATTRIBUTES_LIST, index, persistentAttribute);
		return persistentAttribute;
	}

	protected int insertionIndex(OrmPersistentAttribute persistentAttribute) {
		return CollectionTools.insertionIndexOf(this.specifiedPersistentAttributes, persistentAttribute, buildMappingComparator());
	}

	protected Comparator<OrmPersistentAttribute> buildMappingComparator() {
		return new Comparator<OrmPersistentAttribute>() {
			public int compare(OrmPersistentAttribute o1, OrmPersistentAttribute o2) {
				int o1Sequence = o1.getMapping().getXmlSequence();
				int o2Sequence = o2.getMapping().getXmlSequence();
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

	protected void removeSpecifiedPersistentAttribute_(OrmPersistentAttribute ormPersistentAttribute) {
		removeItemFromList(ormPersistentAttribute, this.specifiedPersistentAttributes, PersistentType.SPECIFIED_ATTRIBUTES_LIST);
	}
	
	protected void moveSpecifiedPersistentAttribute_(int index, OrmPersistentAttribute attribute) {
		moveItemInList(index, this.specifiedPersistentAttributes.indexOf(attribute), this.specifiedPersistentAttributes, PersistentType.SPECIFIED_ATTRIBUTES_LIST);
	}

	public void removeSpecifiedPersistentAttribute(OrmPersistentAttribute ormPersistentAttribute) {
		int index = this.specifiedPersistentAttributes.indexOf(ormPersistentAttribute);
		this.specifiedPersistentAttributes.remove(ormPersistentAttribute);
		ormPersistentAttribute.getMapping().removeFromResourceModel(getResourceAttributes());
		if (getResourceAttributes().isUnset()) {
			this.typeMapping.getResourceTypeMapping().setAttributes(null);
		}
		fireItemRemoved(PersistentType.SPECIFIED_ATTRIBUTES_LIST, index, ormPersistentAttribute);		
	}

	public String getName() {
		return getMapping().getClass_();
	}
	
	public String getShortName(){
		return getName() == null ? null : getName().substring(getName().lastIndexOf('.') + 1);
	}

	public void classChanged(String oldClass, String newClass) {
		firePropertyChanged(NAME_PROPERTY, oldClass, newClass);
	}

	public boolean isMapped() {
		return true;
	}

	public String getMappingKey() {
		return getMapping().getKey();
	}
	
	public JavaPersistentType getJavaPersistentType() {
		return this.javaPersistentType;
	}
	
	protected void setJavaPersistentType(JavaPersistentType newJavaPersistentType) {
		JavaPersistentType oldJavaPersistentType = this.javaPersistentType;
		this.javaPersistentType = newJavaPersistentType;
		firePropertyChanged(JAVA_PERSISTENT_TYPE_PROPERTY, oldJavaPersistentType, newJavaPersistentType);
	}

	protected AccessType getResourceAccess() {
		return AccessType.fromOrmResourceModel(this.getResourceTypeMapping().getAccess());
	}

	protected AccessType buildDefaultAccess() {
		if (! getMapping().isMetadataComplete()) {
			if (getJavaPersistentType() != null) {
				if (javaPersistentTypeHasSpecifiedAccess()) {
					return getJavaPersistentType().getAccess();
				}
				if (getParentPersistentType() != null) {
					return getParentPersistentType().getAccess();
				}
			}
		}
		AccessType access = getMappingFileRoot().getAccess();
		return access != null ? access : AccessType.FIELD; //default to FIELD if no specified access found
	}
	
	protected boolean javaPersistentTypeHasSpecifiedAccess() {
		return getJavaPersistentType().getSpecifiedAccess() != null || 
				getJavaPersistentType().hasAnyAttributePersistenceAnnotations();
	}
	
	protected void initializeJavaPersistentType() {
		JavaResourcePersistentType jrpt = getJavaResourcePersistentType();
		if (jrpt != null) {
			this.javaPersistentType = buildJavaPersistentType(jrpt);
		}	
	}

	protected JavaResourcePersistentType getJavaResourcePersistentType() {
		// try to resolve by only the locally specified name
		JavaResourcePersistentType jrpt = this.getJpaProject().getJavaResourcePersistentType(getMapping().getClass_());
		if (jrpt != null) {
			return jrpt;
		}

		// try to resolve by prepending the global package name
		String packageName = this.getDefaultPackage();
		return this.getJpaProject().getJavaResourcePersistentType(packageName + '.' + getMapping().getClass_());
	}
	
	protected JavaPersistentType buildJavaPersistentType(JavaResourcePersistentType jrpt) {
		return getJpaFactory().buildJavaPersistentType(this, jrpt);
	}

	protected void initializePersistentAttributes() {
		this.initializeSpecifiedPersistentAttributes();
		this.initializeVirtualPersistentAttributes();
	}
	
	protected OrmPersistentAttribute buildSpecifiedOrmPersistentAttribute(XmlAttributeMapping resourceMapping) {
		return buildOrmPersistentAttribute(buildSpecifiedPersistentAttributeOwner(), resourceMapping);
	}
		
	protected OrmPersistentAttribute.Owner buildSpecifiedPersistentAttributeOwner() {
		return new SpecifiedPersistentAttributeOwner();
	}
	
	protected AccessType getAccess(@SuppressWarnings("unused") OrmPersistentAttribute ormPersistentAttribute) {
		return getAccess();
	}
	
	private class SpecifiedPersistentAttributeOwner implements OrmPersistentAttribute.Owner {
		
		private JavaPersistentAttribute cachedJavaPersistentAttribute;
		
		public JavaPersistentAttribute findJavaPersistentAttribute(OrmPersistentAttribute ormPersistentAttribute) {
			JavaPersistentType javaPersistentType = getJavaPersistentType();
			if (javaPersistentType == null) {
				return null;
			}
			if (ormPersistentAttribute.getName() == null) {
				return null;
			}
			AccessType ormAccess = AbstractOrmPersistentType.this.getAccess(ormPersistentAttribute); 
			JavaPersistentAttribute javaPersistentAtribute = findExistingJavaPersistentAttribute(javaPersistentType, ormPersistentAttribute);
			if (javaPersistentAtribute == null || ormAccess == javaPersistentAtribute.getAccess()) {
				this.cachedJavaPersistentAttribute = null;  //we only want to cache the persistent attribute if we build it
				return javaPersistentAtribute;
			}
			//if access is different, we won't be able to find the corresponding java persistent attribute, it won't exist so we build it ourselves
			return buildJavaPersistentAttribute(javaPersistentType, ormPersistentAttribute);
		}
		
		protected JavaPersistentAttribute findExistingJavaPersistentAttribute(JavaPersistentType javaPersistentType, OrmPersistentAttribute ormPersistentAttribute) {
			return javaPersistentType.getAttributeNamed(ormPersistentAttribute.getName());
		}
		
		protected JavaPersistentAttribute buildJavaPersistentAttribute(JavaPersistentType javaPersistentType, OrmPersistentAttribute ormPersistentAttribute) {
			AccessType ormAccess = AbstractOrmPersistentType.this.getAccess(ormPersistentAttribute); 
			String ormName = ormPersistentAttribute.getName();
			//check the already cached javaPersistentAttribute to verify that it still matches name and access type
			if (this.cachedJavaPersistentAttribute != null && this.cachedJavaPersistentAttribute.getName().equals(ormName)) {
				if (this.cachedJavaPersistentAttribute.getAccess() == ormAccess) {
					return this.cachedJavaPersistentAttribute;
				}
			}
			this.cachedJavaPersistentAttribute = null;
			Iterator<JavaResourcePersistentAttribute> javaResourceAttributes = javaPersistentType.getResourcePersistentType().persistableFields();
			if (ormAccess == AccessType.PROPERTY) {
				javaResourceAttributes = javaPersistentType.getResourcePersistentType().persistableProperties();
			}
			for (JavaResourcePersistentAttribute jrpa : CollectionTools.iterable(javaResourceAttributes)) {
				if (ormName.equals(jrpa.getName())) {
					this.cachedJavaPersistentAttribute = getJpaFactory().buildJavaPersistentAttribute(AbstractOrmPersistentType.this, jrpa);
					break;
				}
			}
			return this.cachedJavaPersistentAttribute;
		}
	
		public void updateJavaPersistentAttribute() {
			if (this.cachedJavaPersistentAttribute != null) {
				this.cachedJavaPersistentAttribute.update();
			}
			//else {
				//don't update, we don't own the java persistent attribute, 
				//it will be updated through the java context model
			//}
		}
	}
	
	protected OrmPersistentAttribute buildVirtualOrmPersistentAttribute(JavaAttributeMapping javaAttributeMapping, XmlAttributeMapping resourceMapping) {
		return buildOrmPersistentAttribute(buildVirtualPersistentAttributeOwner(javaAttributeMapping.getPersistentAttribute()), resourceMapping);
	}

	protected OrmPersistentAttribute buildOrmPersistentAttribute(OrmPersistentAttribute.Owner owner, XmlAttributeMapping resourceMapping) {
		return getJpaFactory().buildOrmPersistentAttribute(this, owner, resourceMapping);
	}
	
	protected OrmPersistentAttribute.Owner buildVirtualPersistentAttributeOwner(final JavaPersistentAttribute javaPersistentAttribute) {
		return new OrmPersistentAttribute.Owner() {
			public JavaPersistentAttribute findJavaPersistentAttribute(@SuppressWarnings("unused") OrmPersistentAttribute ormPersistentAttribute) {
				return javaPersistentAttribute;
			}
			
			public void updateJavaPersistentAttribute() {
				//update the attribute, since we own it and it will not get updated otherwise
				javaPersistentAttribute.update();
			}			
		};
	}
	
	protected void initializeSpecifiedPersistentAttributes() {
		Attributes attributes = this.getResourceAttributes();
		if (attributes == null) {
			return;
		}
		for (XmlAttributeMapping resourceMapping : attributes.getAttributeMappings()) {
			addSpecifiedPersistentAttribute(resourceMapping);
		}
	}

	protected void initializeVirtualPersistentAttributes() {
		Iterator<JavaResourcePersistentAttribute> javaResourceAttributes = javaPersistentAttributes();
		
		while (javaResourceAttributes.hasNext()) {
			JavaResourcePersistentAttribute javaResourceAttribute = javaResourceAttributes.next();
			if (getSpecifiedAttributeFor(javaResourceAttribute) == null) {
				addVirtualPersistentAttribute(javaResourceAttribute);
			}
		}
	}
	
	protected Iterator<JavaResourcePersistentAttribute> javaPersistentAttributes() {
		JavaPersistentType javaPersistentType = getJavaPersistentType();
		if (javaPersistentType != null) {
			return javaPersistentAttributes(javaPersistentType.getResourcePersistentType());
		}
		return EmptyListIterator.instance();
	}

	protected Iterator<JavaResourcePersistentAttribute> javaPersistentAttributes(JavaResourcePersistentType resourcePersistentType) {
		return (this.getAccess() == AccessType.PROPERTY) ?
				resourcePersistentType.persistableProperties() :
				resourcePersistentType.persistableFields();
	}

	protected void initializeParentPersistentType() {
		JavaPersistentType javaPersistentType = getJavaPersistentType();
		if (javaPersistentType != null) {
			this.parentPersistentType = javaPersistentType.getParentPersistentType();
		}
	}

	public void update() {
		this.setSpecifiedAccess(this.getResourceAccess());
		this.setDefaultAccess(this.buildDefaultAccess());
		this.getMapping().update();
		this.updateJavaPersistentType();
		this.updateParentPersistentType();
		this.updatePersistentAttributes();
	}
	
	protected void updateJavaPersistentType() {
		JavaResourcePersistentType jrpt = getJavaResourcePersistentType();
		if (jrpt == null) {
			setJavaPersistentType(null);
		}
		else { 
			if (getJavaPersistentType() != null) {
				getJavaPersistentType().update(jrpt);
			}
			else {
				setJavaPersistentType(buildJavaPersistentType(jrpt));
			}
		}		
	}
	
	protected void updateParentPersistentType() {
		JavaPersistentType javaPersistentType = getJavaPersistentType();
		PersistentType parentPersistentType = javaPersistentType == null ? null : javaPersistentType.getParentPersistentType();
		if (parentPersistentType == null) {
			setParentPersistentType(null);
		}
		else if (CollectionTools.contains(parentPersistentType.inheritanceHierarchy(), this)) {
			//short-circuit in this case, we have circular inheritance
			setParentPersistentType(null);
		}
		else {
			setParentPersistentType(parentPersistentType);
		}
	}
	
	protected void updatePersistentAttributes() {
		this.updateSpecifiedPersistentAttributes();
		this.updateVirtualPersistentAttributes();
	}

	protected void updateSpecifiedPersistentAttributes() {
		Attributes attributes = this.getResourceAttributes();
		Collection<OrmPersistentAttribute> contextAttributesToRemove = CollectionTools.collection(specifiedAttributes());
		Collection<OrmPersistentAttribute> contextAttributesToUpdate = new ArrayList<OrmPersistentAttribute>();
		int resourceIndex = 0;
		
		if (attributes != null) {
			for (XmlAttributeMapping resourceMapping : attributes.getAttributeMappings()) {
				boolean contextAttributeFound = false;
				for (OrmPersistentAttribute contextAttribute : contextAttributesToRemove) {
					if (contextAttribute.getMapping().getResourceAttributeMapping() == resourceMapping) {
						moveSpecifiedPersistentAttribute_(resourceIndex, contextAttribute);
						contextAttributesToRemove.remove(contextAttribute);
						contextAttributesToUpdate.add(contextAttribute);
						contextAttributeFound = true;
						break;
					}
				}
				if (!contextAttributeFound) {
					OrmPersistentAttribute ormPersistentAttribute = addSpecifiedPersistentAttribute(resourceMapping);
					fireItemAdded(SPECIFIED_ATTRIBUTES_LIST, specifiedAttributesSize(), ormPersistentAttribute);
				}
				resourceIndex++;
			}
		}
		for (OrmPersistentAttribute contextAttribute : contextAttributesToRemove) {
			removeSpecifiedPersistentAttribute_(contextAttribute);
		}
		//first handle adding/removing of the persistent attributes, then update the others last, 
		//this causes less churn in the update process
		for (OrmPersistentAttribute contextAttribute : contextAttributesToUpdate) {
			contextAttribute.update();
		}	
	}
	
	//not firing change notification so this can be reused in initialize and update
	protected OrmPersistentAttribute addSpecifiedPersistentAttribute(XmlAttributeMapping resourceMapping) {
		OrmPersistentAttribute ormPersistentAttribute = buildSpecifiedOrmPersistentAttribute(resourceMapping);
		this.specifiedPersistentAttributes.add(ormPersistentAttribute);
		return ormPersistentAttribute;
	}
	
	protected void updateVirtualPersistentAttributes() {
		Collection<OrmPersistentAttribute> contextAttributesToRemove = CollectionTools.collection(virtualAttributes());
		Collection<OrmPersistentAttribute> contextAttributesToUpdate = new ArrayList<OrmPersistentAttribute>();
		int resourceIndex = 0;
		
		Iterator<JavaResourcePersistentAttribute> javaResourceAttributes = this.javaPersistentAttributes();
		for (JavaResourcePersistentAttribute javaResourceAttribute : CollectionTools.iterable(javaResourceAttributes)) {
			OrmPersistentAttribute specifiedAttribute = getSpecifiedAttributeFor(javaResourceAttribute);
			if (specifiedAttribute == null) {
				JavaPersistentAttribute javaAttribute = getJpaFactory().buildJavaPersistentAttribute(this, javaResourceAttribute);
				JavaAttributeMapping javaAttributeMapping = javaAttribute.getMapping();
				if (getMapping().isMetadataComplete()) {
					javaAttributeMapping = javaAttribute.getDefaultMapping();
				}
				boolean contextAttributeFound = false;
				for (OrmPersistentAttribute contextAttribute : contextAttributesToRemove) {
					JavaPersistentAttribute javaPersistentAttribute = contextAttribute.getJavaPersistentAttribute();
					if (javaPersistentAttribute.getResourcePersistentAttribute() == javaResourceAttribute) {
						if (contextAttribute.getMappingKey() == javaAttributeMapping.getKey()) { 
							//the mapping key would change if metaDataComplete flag changes, rebuild the orm attribute
							moveVirtualPersistentAttribute_(resourceIndex, contextAttribute);
							contextAttributesToRemove.remove(contextAttribute);
							contextAttributesToUpdate.add(contextAttribute);
							contextAttributeFound = true;
							break;
						}
					}
				}
				if (!contextAttributeFound) {
					OrmPersistentAttribute virtualPersistentAttribute = addVirtualPersistentAttribute(javaAttributeMapping);
					fireItemAdded(VIRTUAL_ATTRIBUTES_LIST, virtualAttributesSize() - 1, virtualPersistentAttribute);
				}
				resourceIndex++;
			}
		}
		
		for (OrmPersistentAttribute contextAttribute : contextAttributesToRemove) {
			removeVirtualPersistentAttribute(contextAttribute);
		}
		//first handle adding/removing of the persistent attributes, then update the others last, 
		//this causes less churn in the update process
		for (OrmPersistentAttribute contextAttribute : contextAttributesToUpdate) {
			contextAttribute.update();
		}
	}

	protected OrmPersistentAttribute addVirtualPersistentAttribute(JavaResourcePersistentAttribute resourceAttribute) {
		JavaPersistentAttribute javaAttribute = getJpaFactory().buildJavaPersistentAttribute(this, resourceAttribute);
		
		JavaAttributeMapping javaAttributeMapping = javaAttribute.getMapping();
		if (getMapping().isMetadataComplete()) {
			javaAttributeMapping = javaAttribute.getDefaultMapping();
		}
		return addVirtualPersistentAttribute(javaAttributeMapping);
	}

	//not firing change notification so this can be reused in initialize and update
	protected OrmPersistentAttribute addVirtualPersistentAttribute(JavaAttributeMapping javaAttributeMapping) {
		XmlAttributeMapping resourceMapping = getJpaPlatform().buildVirtualOrmResourceMappingFromMappingKey(javaAttributeMapping.getKey(), getMapping(), javaAttributeMapping);
		OrmPersistentAttribute virtualPersistentAttribute = buildVirtualOrmPersistentAttribute(javaAttributeMapping, resourceMapping);
		this.virtualPersistentAttributes.add(virtualPersistentAttribute);
		return virtualPersistentAttribute;
	}
	
	public PersistentAttribute resolveAttribute(String attributeName) {
		Iterator<OrmPersistentAttribute> attributes = attributesNamed(attributeName);
		if (attributes.hasNext()) {
			OrmPersistentAttribute attribute = attributes.next();
			return attributes.hasNext() ? null /* more than one */: attribute;
		}
		else if (getParentPersistentType() != null) {
			return getParentPersistentType().resolveAttribute(attributeName);
		}
		else {
			return null;
		}
	}
	
	public JpaStructureNode getStructureNode(int textOffset) {
		for (OrmPersistentAttribute attribute : CollectionTools.iterable(specifiedAttributes())) {
			if (attribute.contains(textOffset)) {
				return attribute;
			}
		}
		return this;
	}
	
	public boolean contains(int textOffset) {
		return this.typeMapping.containsOffset(textOffset);
	}
	
	public TextRange getSelectionTextRange() {
		return this.typeMapping.getSelectionTextRange();
	}
	
	//******************** validation **********************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateClass(messages);
		this.validateMapping(messages, reporter);
		this.validateAttributes(messages, reporter);
	}
	
	protected void validateClass(List<IMessage> messages) {
		if (this.javaPersistentType == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENT_TYPE_UNRESOLVED_CLASS,
					new String[] {getMapping().getClass_()},
					this, 
					this.getMapping().getClassTextRange()
				)
			);
		}
	}

	protected void validateMapping(List<IMessage> messages, IReporter reporter) {
		try {
			this.typeMapping.validate(messages, reporter);
		} catch(Throwable t) {
			JptCorePlugin.log(t);
		}
	}
	
	protected void validateAttributes(List<IMessage> messages, IReporter reporter) {
		for (Iterator<OrmPersistentAttribute> stream = this.attributes(); stream.hasNext(); ) {
			this.validateAttribute(stream.next(), messages, reporter);
		}
	}
	
	protected void validateAttribute(OrmPersistentAttribute attribute, List<IMessage> messages, IReporter reporter) {
		try {
			attribute.validate(messages, reporter);
		} catch(Throwable t) {
			JptCorePlugin.log(t);
		}
	}
	
	public TextRange getValidationTextRange() {
		return this.typeMapping.getValidationTextRange();
	}
	
	public void dispose() {
		if (getJavaPersistentType() != null) {
			getJavaPersistentType().dispose();
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}

}
