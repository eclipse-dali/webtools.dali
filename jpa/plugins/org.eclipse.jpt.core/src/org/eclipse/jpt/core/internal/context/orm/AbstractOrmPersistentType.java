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
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmStructureNodes;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.ClassTools;
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
	extends AbstractOrmXmlContextNode
	implements OrmPersistentType
{
	protected final List<OrmPersistentAttribute> specifiedPersistentAttributes;

	protected final List<OrmPersistentAttribute> virtualPersistentAttributes;
	
	protected AccessType defaultAccess;
	
	protected AccessType specifiedAccess;

	protected OrmTypeMapping typeMapping;
	
	protected PersistentType superPersistentType;
	
	protected JavaPersistentType javaPersistentType;

	
	protected AbstractOrmPersistentType(EntityMappings parent, XmlTypeMapping resourceMapping) {
		super(parent);
		this.specifiedPersistentAttributes = new ArrayList<OrmPersistentAttribute>();
		this.virtualPersistentAttributes = new ArrayList<OrmPersistentAttribute>();
		this.typeMapping = buildTypeMapping(resourceMapping);
		this.specifiedAccess = this.getResourceAccess();
		this.defaultAccess = this.buildDefaultAccess();
		this.javaPersistentType = this.buildJavaPersistentType();
		this.superPersistentType = this.buildSuperPersistentType();	
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
		String className = this.getName();
		if (className == null) {
			return false;
		}
		if (className.equals(typeName)) {
			return true;
		}
		String defaultPackage = this.getDefaultPackage();
		if (defaultPackage == null) {
			return false;
		}
		return (defaultPackage + '.' +  className).equals(typeName);
	}

	protected OrmTypeMapping buildTypeMapping(XmlTypeMapping resourceMapping) {
		OrmTypeMappingProvider mappingProvider = 
				getMappingFileDefinition().getOrmTypeMappingProvider(resourceMapping.getMappingKey());
		return mappingProvider.buildMapping(this, resourceMapping, getXmlContextNodeFactory());
	}

	public OrmTypeMapping getMapping() {
		return this.typeMapping;
	}
	
	
	// **************** PersistentType.Owner implementation *****************************
	
	public AccessType getOverridePersistentTypeAccess() {
		if (this.getSpecifiedAccess() != null) {
			return this.getSpecifiedAccess();
		}

		if (this.superPersistentType instanceof OrmPersistentType) {
			AccessType accessType = ((OrmPersistentType) this.superPersistentType).getSpecifiedAccess();
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
		if (this.superPersistentType instanceof OrmPersistentType) {
			AccessType accessType = ((OrmPersistentType) this.superPersistentType).getDefaultAccess();
			if (accessType != null) {
				return accessType;
			}
		}

		return this.getOwnerDefaultAccess();
	}

	// ********** PersistentType implementation **********
	
	public void setMappingKey(String newMappingKey) {
		if (this.valuesAreEqual(this.getMappingKey(), newMappingKey)) {
			return;
		}
		OrmTypeMapping oldMapping = getMapping();
		OrmTypeMappingProvider mappingProvider = 
				getMappingFileDefinition().getOrmTypeMappingProvider(newMappingKey);
		XmlTypeMapping resourceTypeMapping = mappingProvider.buildResourceMapping();
		this.typeMapping = buildTypeMapping(resourceTypeMapping);
		this.getEntityMappings().changeMapping(this, oldMapping, this.typeMapping);
		firePropertyChanged(MAPPING_PROPERTY, oldMapping, this.typeMapping);
	}

	public Iterator<PersistentType> inheritanceHierarchy() {
		return this.inheritanceHierarchyOf(this);
	}

	public Iterator<PersistentType> ancestors() {
		return this.inheritanceHierarchyOf(this.superPersistentType);
	}

	protected Iterator<PersistentType> inheritanceHierarchyOf(PersistentType start) {
		// using a chain iterator to traverse up the inheritance tree
		return new ChainIterator<PersistentType>(start) {
			@Override
			protected PersistentType nextLink(PersistentType persistentType) {
				return persistentType.getSuperPersistentType();
			}
		};
	}

	public PersistentType getSuperPersistentType() {
		return this.superPersistentType;
	}
	
	protected void setSuperPersistentType(PersistentType superPersistentType) {
		PersistentType old = this.superPersistentType;
		this.superPersistentType = superPersistentType;
		this.firePropertyChanged(SUPER_PERSISTENT_TYPE_PROPERTY, old, superPersistentType);
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
		fireItemMoved(ATTRIBUTES_LIST, targetIndex, sourceIndex);
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
		if (this.valuesAreEqual(mappingKey, MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY)) {
			throw new IllegalStateException("Use makePersistentAttributeSpecified(OrmPersistentAttribute, String) instead and specify a mapping type"); //$NON-NLS-1$
		}
		
		Attributes resourceAttributes = getResourceAttributes();
		if (resourceAttributes == null) {
			resourceAttributes = createResourceAttributes();
			getMapping().getResourceTypeMapping().setAttributes(resourceAttributes);
		}
		
		OrmAttributeMappingProvider mappingProvider = 
				getMappingFileDefinition().getOrmAttributeMappingProvider(mappingKey);
		XmlAttributeMapping resourceMapping = mappingProvider.buildResourceMapping();
		
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
		
		fireItemAdded(ATTRIBUTES_LIST, insertionIndex, newPersistentAttribute);
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
		addItemToList(ormPersistentAttribute, this.virtualPersistentAttributes, VIRTUAL_ATTRIBUTES_LIST);
	}

	protected void removeVirtualPersistentAttribute(OrmPersistentAttribute ormPersistentAttribute) {
		removeItemFromList(ormPersistentAttribute, this.virtualPersistentAttributes, VIRTUAL_ATTRIBUTES_LIST);
	}
	
	protected void moveVirtualPersistentAttribute_(int index, OrmPersistentAttribute attribute) {
		moveItemInList(index, this.virtualPersistentAttributes.indexOf(attribute), this.virtualPersistentAttributes, VIRTUAL_ATTRIBUTES_LIST);
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
		
		OrmAttributeMappingProvider mappingProvider = 
				getMappingFileDefinition().getOrmAttributeMappingProvider(mappingKey);
		XmlAttributeMapping resourceMapping = mappingProvider.buildResourceMapping();
		OrmPersistentAttribute persistentAttribute = buildSpecifiedOrmPersistentAttribute(resourceMapping);
		int index = insertionIndex(persistentAttribute);
		this.specifiedPersistentAttributes.add(index, persistentAttribute);
		persistentAttribute.getMapping().addToResourceModel(resourceAttributes);
		
		persistentAttribute.getSpecifiedMapping().setName(attributeName);
		fireItemAdded(ATTRIBUTES_LIST, index, persistentAttribute);
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
		removeItemFromList(ormPersistentAttribute, this.specifiedPersistentAttributes, ATTRIBUTES_LIST);
	}
	
	protected void moveSpecifiedPersistentAttribute_(int index, OrmPersistentAttribute attribute) {
		moveItemInList(index, this.specifiedPersistentAttributes.indexOf(attribute), this.specifiedPersistentAttributes, ATTRIBUTES_LIST);
	}

	public void removeSpecifiedPersistentAttribute(OrmPersistentAttribute ormPersistentAttribute) {
		int index = this.specifiedPersistentAttributes.indexOf(ormPersistentAttribute);
		this.specifiedPersistentAttributes.remove(ormPersistentAttribute);
		ormPersistentAttribute.getMapping().removeFromResourceModel(getResourceAttributes());
		if (getResourceAttributes().isUnset()) {
			this.typeMapping.getResourceTypeMapping().setAttributes(null);
		}
		fireItemRemoved(ATTRIBUTES_LIST, index, ormPersistentAttribute);		
	}

	public String getName() {
		return this.getMapping().getClass_();
	}
	
	public String getShortName(){
		String className = this.getName();
		return (className == null) ? null : ClassTools.shortNameForClassNamed(className);
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
			if (this.javaPersistentType != null) {
				if (javaPersistentTypeHasSpecifiedAccess()) {
					return this.javaPersistentType.getAccess();
				}
				if (this.superPersistentType != null) {
					return this.superPersistentType.getAccess();
				}
			}
		}
		AccessType access = getMappingFileRoot().getAccess();
		return access != null ? access : AccessType.FIELD; //default to FIELD if no specified access found
	}
	
	protected boolean javaPersistentTypeHasSpecifiedAccess() {
		return this.javaPersistentType.getSpecifiedAccess() != null || 
				this.javaPersistentType.hasAnyAnnotatedAttributes();
	}

	protected JavaPersistentType buildJavaPersistentType() {
		JavaResourcePersistentType jrpt = this.getJavaResourcePersistentType();
		return (jrpt == null) ? null : this.buildJavaPersistentType(jrpt);
	}

	protected JavaResourcePersistentType getJavaResourcePersistentType() {
		String className = this.getName();
		if (className == null) {
			return null;
		}
		className = className.replace('$', '.');

		// first try to resolve using only the locally specified name...
		JavaResourcePersistentType jrpt = this.getJavaResourcePersistentType(className);
		if (jrpt != null) {
			return jrpt;
		}

		// ...then try to resolve by prepending the global package name
		String defaultPackage = this.getDefaultPackage();
		if (defaultPackage == null) {
			return null;
		}
		return this.getJavaResourcePersistentType(defaultPackage + '.' +  className);
	}
	
	protected JavaResourcePersistentType getJavaResourcePersistentType(String className) {
		return this.getJpaProject().getJavaResourcePersistentType(className);
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

	/**
	 * All orm.xml persistent types must be able to generate a static metamodel
	 * because 1.0 orm.xml files can be referenced from 2.0 persistence.xml files.
	 */
	public void synchronizeStaticMetamodel() {
		// if we get here, it's safe to assume the JPA project is 2.0
		((JpaProject2_0) this.getJpaProject()).synchronizeStaticMetamodel(this);
	}

	protected class SpecifiedPersistentAttributeOwner implements OrmPersistentAttribute.Owner {
		
		private JavaPersistentAttribute cachedJavaPersistentAttribute;
		
		public JavaPersistentAttribute findJavaPersistentAttribute(OrmPersistentAttribute ormPersistentAttribute) {
			if (AbstractOrmPersistentType.this.javaPersistentType == null) {
				return null;
			}
			String ormName = ormPersistentAttribute.getName();
			if (ormName == null) {
				return null;
			}
			AccessType ormAccess = AbstractOrmPersistentType.this.getAccess(ormPersistentAttribute);

			JavaPersistentAttribute javaPersistentAttribute = this.findExistingJavaPersistentAttribute(ormName);
			if ((javaPersistentAttribute != null) && (javaPersistentAttribute.getAccess() == ormAccess)) {
				this.cachedJavaPersistentAttribute = null;  // we only want to cache the persistent attribute if we build it
				return javaPersistentAttribute;
			}

			// if 'javaPersistentAttribute' is null, it might exist in a superclass that is not persistent, we need to build it ourselves.
			// if access is different, we won't be able to find the corresponding java persistent attribute, it won't exist so we build it ourselves
			return this.buildJavaPersistentAttribute(ormName, ormAccess);
		}
		
		protected JavaPersistentAttribute findExistingJavaPersistentAttribute(String attributeName) {
			return AbstractOrmPersistentType.this.javaPersistentType.getAttributeNamed(attributeName);
		}

		protected JavaPersistentAttribute buildJavaPersistentAttribute(String ormName, AccessType ormAccess) {
			JavaResourcePersistentAttribute jrpa = this.getJavaResourcePersistentAttribute(this.getJavaResourcePersistentType(), ormName, ormAccess);
			if (this.cachedJavaPersistentAttribute != null &&
					this.cachedJavaPersistentAttribute.getResourcePersistentAttribute() == jrpa) {
				return this.cachedJavaPersistentAttribute;
			}
			return this.cachedJavaPersistentAttribute = (jrpa == null) ? null : AbstractOrmPersistentType.this.buildJavaPersistentAttribute(jrpa);
		}

		protected JavaResourcePersistentType getJavaResourcePersistentType() {
			return AbstractOrmPersistentType.this.javaPersistentType.getResourcePersistentType();
		}

		protected JavaResourcePersistentAttribute getJavaResourcePersistentAttribute(JavaResourcePersistentType javaResourcePersistentType, String ormName, AccessType ormAccess) {
			for (Iterator<JavaResourcePersistentAttribute> stream = this.attributes(javaResourcePersistentType, ormAccess); stream.hasNext(); ) {
				JavaResourcePersistentAttribute jrpa = stream.next();
				if (jrpa.getName().equals(ormName)) {
					return jrpa;
				}
			}
			// climb up inheritance hierarchy
			String superclassName = javaResourcePersistentType.getSuperclassQualifiedName();
			if (superclassName == null) {
				return null;
			}
			JavaResourcePersistentType superclass = AbstractOrmPersistentType.this.getJavaResourcePersistentType(superclassName);
			if (superclass == null) {
				return null;
			}
			// recurse
			return this.getJavaResourcePersistentAttribute(superclass, ormName, ormAccess);
		}

		protected Iterator<JavaResourcePersistentAttribute> attributes(JavaResourcePersistentType javaResourcePersistentType, AccessType ormAccess) {
			return (ormAccess == AccessType.PROPERTY) ? javaResourcePersistentType.persistableProperties() : javaResourcePersistentType.persistableFields();
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
	
	protected JavaPersistentAttribute buildJavaPersistentAttribute(JavaResourcePersistentAttribute jrpa) {
		return this.getJpaFactory().buildJavaPersistentAttribute(this, jrpa);
	}
	
	protected OrmPersistentAttribute buildVirtualOrmPersistentAttribute(JavaAttributeMapping javaAttributeMapping, XmlAttributeMapping resourceMapping) {
		return buildOrmPersistentAttribute(buildVirtualPersistentAttributeOwner(javaAttributeMapping.getPersistentAttribute()), resourceMapping);
	}

	protected OrmPersistentAttribute buildOrmPersistentAttribute(OrmPersistentAttribute.Owner owner, XmlAttributeMapping resourceMapping) {
		return getXmlContextNodeFactory().buildOrmPersistentAttribute(this, owner, resourceMapping);
	}
	
	protected OrmPersistentAttribute.Owner buildVirtualPersistentAttributeOwner(final JavaPersistentAttribute javaPersistentAttribute) {
		return new OrmPersistentAttribute.Owner() {
			public JavaPersistentAttribute findJavaPersistentAttribute(OrmPersistentAttribute ormPersistentAttribute) {
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
		return (this.javaPersistentType == null) ? EmptyListIterator.<JavaResourcePersistentAttribute>instance() :
			this.javaPersistentAttributes(this.javaPersistentType.getResourcePersistentType());
	}

	protected Iterator<JavaResourcePersistentAttribute> javaPersistentAttributes(JavaResourcePersistentType resourcePersistentType) {
		return (this.getAccess() == AccessType.PROPERTY) ?
				resourcePersistentType.persistableProperties() :
				resourcePersistentType.persistableFields();
	}

	protected PersistentType buildSuperPersistentType() {
		return (this.javaPersistentType == null) ? null : this.javaPersistentType.getSuperPersistentType();
	}

	public void update() {
		this.setSpecifiedAccess(this.getResourceAccess());
		this.setDefaultAccess(this.buildDefaultAccess());
		this.getMapping().update();
		this.updateJavaPersistentType();
		this.updateSuperPersistentType();
		this.updatePersistentAttributes();
	}
	
	protected void updateJavaPersistentType() {
		JavaResourcePersistentType jrpt = this.getJavaResourcePersistentType();
		if (jrpt == null) {
			this.setJavaPersistentType(null);
		} else { 
			if (this.javaPersistentType == null) {
				this.setJavaPersistentType(this.buildJavaPersistentType(jrpt));
			} else {
				this.javaPersistentType.update(jrpt);
			}
		}		
	}
	
	protected void updateSuperPersistentType() {
		PersistentType spt = this.buildSuperPersistentType();
		// check for circular inheritance
		if ((spt == null) || CollectionTools.contains(spt.inheritanceHierarchy(), this)) {
			this.setSuperPersistentType(null);
		} else {
			this.setSuperPersistentType(spt);
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
					fireItemAdded(ATTRIBUTES_LIST, specifiedAttributesSize(), ormPersistentAttribute);
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
						if (this.valuesAreEqual(contextAttribute.getMappingKey(), javaAttributeMapping.getKey())) { 
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
		OrmAttributeMappingProvider mappingProvider = 
				getMappingFileDefinition().getOrmAttributeMappingProvider(javaAttributeMapping.getKey());
		XmlAttributeMapping resourceMapping = 
				mappingProvider.buildVirtualResourceMapping(getMapping(), javaAttributeMapping, getXmlContextNodeFactory());
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
		return (this.superPersistentType == null) ? null : this.superPersistentType.resolveAttribute(attributeName);
	}

	@Override
	public void postUpdate() {
		super.postUpdate();
		if (this.javaPersistentType != null) {
			this.javaPersistentType.postUpdate();
		}
		getMapping().postUpdate();
		for (PersistentAttribute attribute : CollectionTools.iterable(attributes())) {
			attribute.postUpdate();
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
					new String[] {this.getName()},
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
		if (this.javaPersistentType != null) {
			this.javaPersistentType.dispose();
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}

}
