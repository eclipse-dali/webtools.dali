/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
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
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingDefinition;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmStructureNodes;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingDefinition;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.core.jpa2.context.PersistentType2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmPersistentType2_0;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.core.resource.xml.EmfTools;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Tools;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.utility.internal.iterators.ChainIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * ORM persistent type:<ul>
 * <li>mapping
 * <li>access
 * <li>attributes
 * <li>super persistent type
 * <li>Java persistent type
 * </ul>
 */
public class GenericOrmPersistentType
	extends AbstractOrmXmlContextNode
	implements OrmPersistentType2_0
{
	protected OrmTypeMapping mapping;

	protected AccessType defaultAccess;

	protected AccessType specifiedAccess;

	protected final Vector<OrmPersistentAttribute> specifiedAttributes = new Vector<OrmPersistentAttribute>();

	protected final Vector<OrmPersistentAttribute> virtualAttributes = new Vector<OrmPersistentAttribute>();

	protected PersistentType superPersistentType;

	protected JavaPersistentType javaPersistentType;

	protected final PersistentType2_0.MetamodelSynchronizer metamodelSynchronizer;


	public GenericOrmPersistentType(EntityMappings parent, XmlTypeMapping resourceMapping) {
		super(parent);
		this.mapping = this.buildMapping(resourceMapping);
		this.specifiedAccess = this.buildSpecifiedAccess();
		this.defaultAccess = this.buildDefaultAccess();
		this.javaPersistentType = this.buildJavaPersistentType();
		this.superPersistentType = this.buildSuperPersistentType();
		this.initializeAttributes();
		this.metamodelSynchronizer = this.buildMetamodelSynchronizer();
	}

	protected PersistentType2_0.MetamodelSynchronizer buildMetamodelSynchronizer() {
		return ((JpaFactory2_0) this.getJpaFactory()).buildPersistentTypeMetamodelSynchronizer(this);
	}


	// ********** update **********

	public void update() {
		this.setSpecifiedAccess(this.buildSpecifiedAccess());
		this.setDefaultAccess(this.buildDefaultAccess());
		this.mapping.update();
		this.updateJavaPersistentType();
		this.updateSuperPersistentType();
		this.updateAttributes();
	}

	@Override
	public void postUpdate() {
		super.postUpdate();
		if (this.javaPersistentType != null) {
			this.javaPersistentType.postUpdate();
		}
		this.mapping.postUpdate();
		for (PersistentAttribute attribute : this.getAttributes()) {
			attribute.postUpdate();
		}
	}


	// ********** name **********

	public String getName() {
		return this.mapping.getClass_();
	}

	public String getShortName(){
		String className = this.getName();
		return (className == null) ? null : ClassTools.shortNameForClassNamed(className);
	}


	// ********** mapping **********

	public OrmTypeMapping getMapping() {
		return this.mapping;
	}

	public void setMappingKey(String newMappingKey) {
		if (this.valuesAreEqual(this.getMappingKey(), newMappingKey)) {
			return;
		}
		OrmTypeMapping oldMapping = this.mapping;
		OrmTypeMappingDefinition mappingDefinition = this.getMappingFileDefinition().getOrmTypeMappingDefinition(newMappingKey);
		XmlTypeMapping resourceTypeMapping = mappingDefinition.buildResourceMapping(this.getResourceNodeFactory());
		this.mapping = this.buildMapping(resourceTypeMapping);
		this.getEntityMappings().changeMapping(this, oldMapping, this.mapping);
		this.firePropertyChanged(MAPPING_PROPERTY, oldMapping, this.mapping);
	}

	protected OrmTypeMapping buildMapping(XmlTypeMapping resourceMapping) {
		OrmTypeMappingDefinition mappingDefintion = this.getMappingFileDefinition().getOrmTypeMappingDefinition(resourceMapping.getMappingKey());
		return mappingDefintion.buildContextMapping(this, resourceMapping, this.getXmlContextNodeFactory());
	}


	// ********** access **********

	public AccessType getAccess() {
		return (this.specifiedAccess != null) ? this.specifiedAccess : this.defaultAccess;
	}

	public AccessType getDefaultAccess() {
		return this.defaultAccess;
	}

	protected void setDefaultAccess(AccessType defaultAccess) {
		AccessType old = this.defaultAccess;
		this.defaultAccess = defaultAccess;
		this.firePropertyChanged(DEFAULT_ACCESS_PROPERTY, old, defaultAccess);
	}

	public AccessType getSpecifiedAccess() {
		return this.specifiedAccess;
	}

	public void setSpecifiedAccess(AccessType specifiedAccess) {
		AccessType old = this.specifiedAccess;
		this.specifiedAccess = specifiedAccess;
		this.getResourceTypeMapping().setAccess(AccessType.toOrmResourceModel(specifiedAccess));
		this.firePropertyChanged(SPECIFIED_ACCESS_PROPERTY, old, specifiedAccess);
	}

	protected AccessType buildDefaultAccess() {
		if ( ! this.mapping.isMetadataComplete()) {
			if (this.javaPersistentType != null) {
				if (this.javaPersistentTypeHasSpecifiedAccess()) {
					return this.javaPersistentType.getAccess();
				}
				if (this.superPersistentType != null) {
					return this.superPersistentType.getAccess();
				}
			}
		}
		AccessType access = this.getMappingFileRoot().getAccess();
		return (access != null) ? access : AccessType.FIELD; //default to FIELD if no specified access found
	}

	protected boolean javaPersistentTypeHasSpecifiedAccess() {
		return (this.javaPersistentType.getSpecifiedAccess() != null) || 
				this.javaPersistentType.hasAnyAnnotatedAttributes();
	}

	public AccessType getOwnerOverrideAccess() {
		return this.getEntityMappings().getOverridePersistentTypeAccess();
	}

	public AccessType getOwnerDefaultAccess() {
		return this.getEntityMappings().getDefaultPersistentTypeAccess();
	}

	protected AccessType buildSpecifiedAccess() {
		return AccessType.fromOrmResourceModel(this.getResourceTypeMapping().getAccess());
	}


	// ********** attributes **********

	@SuppressWarnings("unchecked")
	public ListIterator<OrmPersistentAttribute> attributes() {
		return new CompositeListIterator<OrmPersistentAttribute>(this.specifiedAttributes(), this.virtualAttributes());
	}

	@SuppressWarnings("unchecked")
	protected Iterable<OrmPersistentAttribute> getAttributes() {
		return new CompositeIterable<OrmPersistentAttribute>(this.getSpecifiedAttributes(), this.getVirtualAttributes());
	}

	public int attributesSize() {
		return this.specifiedAttributesSize() + this.virtualAttributesSize();
	}

	public Iterator<String> allAttributeNames() {
		return this.attributeNames(this.allAttributes());
	}

	public Iterator<PersistentAttribute> allAttributes() {
		return new CompositeIterator<PersistentAttribute>(
				new TransformationIterator<PersistentType, Iterator<PersistentAttribute>>(this.inheritanceHierarchy()) {
					@Override
					protected Iterator<PersistentAttribute> transform(PersistentType pt) {
						return pt.attributes();
					}
				}
			);
	}

	protected Iterator<OrmPersistentAttribute> attributesNamed(final String attributeName) {
		return new FilteringIterator<OrmPersistentAttribute, OrmPersistentAttribute>(this.attributes()) {
			@Override
			protected boolean accept(OrmPersistentAttribute o) {
				return Tools.valuesAreEqual(attributeName, o.getName());
			}
		};
	}

	public OrmPersistentAttribute getAttributeNamed(String attributeName) {
		Iterator<OrmPersistentAttribute> stream = this.attributesNamed(attributeName);
		return (stream.hasNext()) ? stream.next() : null;
	}

	public PersistentAttribute resolveAttribute(String attributeName) {
		Iterator<OrmPersistentAttribute> attributes = this.attributesNamed(attributeName);
		if (attributes.hasNext()) {
			OrmPersistentAttribute attribute = attributes.next();
			return attributes.hasNext() ? null /* more than one */: attribute;
		}
		return (this.superPersistentType == null) ? null : this.superPersistentType.resolveAttribute(attributeName);
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

	protected void initializeAttributes() {
		this.initializeSpecifiedAttributes();
		this.initializeVirtualAttributes();
	}

	protected void updateAttributes() {
		this.updateSpecifiedAttributes();
		this.updateVirtualAttributes();
	}

	protected Iterator<JavaResourcePersistentAttribute> javaPersistentAttributes() {
		return (this.javaPersistentType == null) ?
				EmptyListIterator.<JavaResourcePersistentAttribute>instance() :
				this.javaPersistentAttributes(this.javaPersistentType.getResourcePersistentType());
	}

	protected Iterator<JavaResourcePersistentAttribute> javaPersistentAttributes(JavaResourcePersistentType resourcePersistentType) {
		AccessType access = this.specifiedAccess;
		if (access == null && ! this.mapping.isMetadataComplete()) {
			access = this.getJavaPersistentType().getSpecifiedAccess();
		}
		if (access == null) {
			access = this.defaultAccess;
		}
		return resourcePersistentType.persistableAttributes(AccessType.toJavaResourceModel(access));
	}


	// ********** specified attributes **********

	public ListIterator<OrmPersistentAttribute> specifiedAttributes() {
		return new CloneListIterator<OrmPersistentAttribute>(this.specifiedAttributes);
	}

	protected Iterable<OrmPersistentAttribute> getSpecifiedAttributes() {
		return new LiveCloneIterable<OrmPersistentAttribute>(this.specifiedAttributes);
	}

	public int specifiedAttributesSize() {
		return this.specifiedAttributes.size();
	}

	protected OrmPersistentAttribute getSpecifiedAttributeFor(final JavaResourcePersistentAttribute jrpa) {
		for (OrmPersistentAttribute attribute : this.getSpecifiedAttributes()) {
			JavaPersistentAttribute javaAttribute = attribute.getJavaPersistentAttribute();
			if ((javaAttribute != null) && (javaAttribute.getResourcePersistentAttribute() == jrpa)) {
				return attribute;
			}
		}
		return null;
	}

	public OrmPersistentAttribute addSpecifiedAttribute(String mappingKey, String attributeName) {
		Attributes resourceAttributes = this.getResourceAttributes();
		if (resourceAttributes == null) {
			resourceAttributes = this.createResourceAttributes();
			this.mapping.getResourceTypeMapping().setAttributes(resourceAttributes);
		}

		OrmAttributeMappingDefinition mappingDefintion = this.getMappingFileDefinition().getOrmAttributeMappingDefinition(mappingKey);
		XmlAttributeMapping resourceMapping = mappingDefintion.buildResourceMapping(getResourceNodeFactory());
		OrmPersistentAttribute persistentAttribute = this.buildSpecifiedAttribute(resourceMapping);
		int index = this.getSpecifiedAttributeInsertionIndex(persistentAttribute);
		this.specifiedAttributes.add(index, persistentAttribute);
		persistentAttribute.getMapping().addToResourceModel(resourceAttributes);

		persistentAttribute.getSpecifiedMapping().setName(attributeName);
		this.fireItemAdded(ATTRIBUTES_LIST, index, persistentAttribute);
		return persistentAttribute;
	}

	public void changeMapping(OrmPersistentAttribute ormPersistentAttribute, OrmAttributeMapping oldMapping, OrmAttributeMapping newMapping) {
		int sourceIndex = this.specifiedAttributes.indexOf(ormPersistentAttribute);
		this.specifiedAttributes.remove(sourceIndex);
		oldMapping.removeFromResourceModel(this.getResourceAttributes());
		int targetIndex = getSpecifiedAttributeInsertionIndex(ormPersistentAttribute);
		this.specifiedAttributes.add(targetIndex, ormPersistentAttribute);
		newMapping.addToResourceModel(this.getResourceAttributes());
		oldMapping.initializeOn(newMapping);
		this.fireItemMoved(ATTRIBUTES_LIST, targetIndex, sourceIndex);
	}

	public void makeAttributeSpecified(OrmPersistentAttribute ormPersistentAttribute) {
		this.makeAttributeSpecified(ormPersistentAttribute, ormPersistentAttribute.getMappingKey());
	}

	public void makeAttributeSpecified(OrmPersistentAttribute ormPersistentAttribute, String mappingKey) {
		if ( ! ormPersistentAttribute.isVirtual()) {
			throw new IllegalStateException("Attribute is already specified"); //$NON-NLS-1$
		}
		if (this.valuesAreEqual(mappingKey, MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY)) {
			throw new IllegalStateException("Use makePersistentAttributeSpecified(OrmPersistentAttribute, String) instead and specify a mapping type"); //$NON-NLS-1$
		}

		Attributes resourceAttributes = this.getResourceAttributes();
		if (resourceAttributes == null) {
			resourceAttributes = this.createResourceAttributes();
			this.mapping.getResourceTypeMapping().setAttributes(resourceAttributes);
		}

		OrmAttributeMappingDefinition mappingDefintion = this.getMappingFileDefinition().getOrmAttributeMappingDefinition(mappingKey);
		XmlAttributeMapping resourceMapping = mappingDefintion.buildResourceMapping(getResourceNodeFactory());

		OrmPersistentAttribute newAttribute = this.buildSpecifiedAttribute(resourceMapping);
		int insertionIndex = this.getSpecifiedAttributeInsertionIndex(newAttribute);
		this.specifiedAttributes.add(insertionIndex, newAttribute);
		newAttribute.getMapping().addToResourceModel(resourceAttributes);

		int removalIndex = this.virtualAttributes.indexOf(ormPersistentAttribute);
		this.virtualAttributes.remove(ormPersistentAttribute);
		newAttribute.getSpecifiedMapping().setName(ormPersistentAttribute.getName());
		if (ormPersistentAttribute.getJavaPersistentAttribute().getSpecifiedAccess() != null) {
			newAttribute.setSpecifiedAccess(ormPersistentAttribute.getJavaPersistentAttribute().getSpecifiedAccess());
		}

		this.fireItemAdded(ATTRIBUTES_LIST, insertionIndex, newAttribute);
		this.fireItemRemoved(VIRTUAL_ATTRIBUTES_LIST, removalIndex, ormPersistentAttribute);
	}

	protected int getSpecifiedAttributeInsertionIndex(OrmPersistentAttribute specifiedAttribute) {
		return CollectionTools.insertionIndexOf(this.specifiedAttributes, specifiedAttribute, this.getAttributeComparator());
	}

	protected Comparator<OrmPersistentAttribute> getAttributeComparator() {
		return ATTRIBUTE_COMPARATOR;
	}

	protected static final Comparator<OrmPersistentAttribute> ATTRIBUTE_COMPARATOR = 
		new Comparator<OrmPersistentAttribute>() {
			public int compare(OrmPersistentAttribute o1, OrmPersistentAttribute o2) {
				int o1Sequence = o1.getMapping().getXmlSequence();
				int o2Sequence = o2.getMapping().getXmlSequence();
				return (o1Sequence == o2Sequence) ? 0 : (o1Sequence < o2Sequence) ? -1 : 1;
			}
		};

	protected Attributes getResourceAttributes() {
		return this.getResourceTypeMapping().getAttributes();
	}

	protected Attributes createResourceAttributes() {
		return EmfTools.create(this.getResourceNodeFactory(), OrmPackage.eINSTANCE.getAttributes(), Attributes.class);
	}

	protected void initializeSpecifiedAttributes() {
		Attributes attributes = this.getResourceAttributes();
		if (attributes == null) {
			return;
		}
		for (XmlAttributeMapping resourceMapping : attributes.getAttributeMappings()) {
			this.addSpecifiedAttribute(resourceMapping);
		}
	}

	protected void updateSpecifiedAttributes() {
		Attributes attributes = this.getResourceAttributes();
		Collection<OrmPersistentAttribute> contextAttributesToRemove = CollectionTools.collection(this.specifiedAttributes());
		Collection<OrmPersistentAttribute> contextAttributesToUpdate = new ArrayList<OrmPersistentAttribute>();
		int resourceIndex = 0;

		if (attributes != null) {
			for (XmlAttributeMapping resourceMapping : attributes.getAttributeMappings()) {
				boolean contextAttributeFound = false;
				for (OrmPersistentAttribute contextAttribute : contextAttributesToRemove) {
					if (contextAttribute.getMapping().getResourceAttributeMapping() == resourceMapping) {
						this.moveSpecifiedAttribute_(resourceIndex, contextAttribute);
						contextAttributesToRemove.remove(contextAttribute);
						contextAttributesToUpdate.add(contextAttribute);
						contextAttributeFound = true;
						break;
					}
				}
				if ( ! contextAttributeFound) {
					OrmPersistentAttribute ormPersistentAttribute = this.addSpecifiedAttribute(resourceMapping);
					this.fireItemAdded(ATTRIBUTES_LIST, specifiedAttributesSize(), ormPersistentAttribute);
				}
				resourceIndex++;
			}
		}
		for (OrmPersistentAttribute contextAttribute : contextAttributesToRemove) {
			this.removeSpecifiedAttribute_(contextAttribute);
		}
		//first handle adding/removing of the persistent attributes, then update the others last, 
		//this causes less churn in the update process
		for (OrmPersistentAttribute contextAttribute : contextAttributesToUpdate) {
			contextAttribute.update();
		}
	}

	protected void removeSpecifiedAttribute_(OrmPersistentAttribute ormPersistentAttribute) {
		this.removeItemFromList(ormPersistentAttribute, this.specifiedAttributes, ATTRIBUTES_LIST);
	}

	//not firing change notification so this can be reused in initialize and update
	protected OrmPersistentAttribute addSpecifiedAttribute(XmlAttributeMapping resourceMapping) {
		OrmPersistentAttribute ormPersistentAttribute = this.buildSpecifiedAttribute(resourceMapping);
		this.specifiedAttributes.add(ormPersistentAttribute);
		return ormPersistentAttribute;
	}

	protected void moveSpecifiedAttribute_(int index, OrmPersistentAttribute attribute) {
		this.moveItemInList(index, this.specifiedAttributes.indexOf(attribute), this.specifiedAttributes, ATTRIBUTES_LIST);
	}

	public void removeSpecifiedAttribute(OrmPersistentAttribute ormPersistentAttribute) {
		int index = this.specifiedAttributes.indexOf(ormPersistentAttribute);
		this.specifiedAttributes.remove(ormPersistentAttribute);
		ormPersistentAttribute.getMapping().removeFromResourceModel(this.getResourceAttributes());
		if (this.getResourceAttributes().isUnset()) {
			this.mapping.getResourceTypeMapping().setAttributes(null);
		}
		this.fireItemRemoved(ATTRIBUTES_LIST, index, ormPersistentAttribute);
	}

	protected OrmPersistentAttribute buildSpecifiedAttribute(XmlAttributeMapping resourceMapping) {
		return this.buildOrmPersistentAttribute(this.buildSpecifiedAttributeOwner(), resourceMapping);
	}

	protected OrmPersistentAttribute.Owner buildSpecifiedAttributeOwner() {
		return new SpecifiedAttributeOwner();
	}

	protected JavaPersistentAttribute buildJavaPersistentAttribute(JavaResourcePersistentAttribute jrpa) {
		return this.getJpaFactory().buildJavaPersistentAttribute(this, jrpa);
	}


	// ********** virtual attributes **********

	public ListIterator<OrmPersistentAttribute> virtualAttributes() {
		return new CloneListIterator<OrmPersistentAttribute>(this.virtualAttributes);
	}

	protected Iterable<OrmPersistentAttribute> getVirtualAttributes() {
		return new LiveCloneIterable<OrmPersistentAttribute>(this.virtualAttributes);
	}

	public int virtualAttributesSize() {
		return this.virtualAttributes.size();
	}

	protected void addVirtualAttribute(OrmPersistentAttribute virtualAttribute) {
		this.addItemToList(virtualAttribute, this.virtualAttributes, VIRTUAL_ATTRIBUTES_LIST);
	}

	protected void removeVirtualAttribute(OrmPersistentAttribute virtualAttribute) {
		this.removeItemFromList(virtualAttribute, this.virtualAttributes, VIRTUAL_ATTRIBUTES_LIST);
	}

	protected void moveVirtualAttribute_(int index, OrmPersistentAttribute virtualAttribute) {
		this.moveItemInList(index, this.virtualAttributes.indexOf(virtualAttribute), this.virtualAttributes, VIRTUAL_ATTRIBUTES_LIST);
	}

	public boolean containsVirtualAttribute(OrmPersistentAttribute ormPersistentAttribute) {
		return this.virtualAttributes.contains(ormPersistentAttribute);
	}

	public void makeAttributeVirtual(OrmPersistentAttribute ormPersistentAttribute) {
		if (ormPersistentAttribute.isVirtual()) {
			throw new IllegalStateException("Attribute is already virtual"); //$NON-NLS-1$
		}
		JavaPersistentAttribute javaPersistentAttribute = ormPersistentAttribute.getJavaPersistentAttribute();
		OrmPersistentAttribute virtualAttribute = null;
		if (javaPersistentAttribute != null) {
			virtualAttribute = this.addVirtualAttribute(javaPersistentAttribute.getResourcePersistentAttribute());
		}
		this.removeSpecifiedAttribute(ormPersistentAttribute);
		if (virtualAttribute != null) {
			this.fireItemAdded(VIRTUAL_ATTRIBUTES_LIST, virtualAttributesSize() - 1, virtualAttribute);
		}
	}

	protected void initializeVirtualAttributes() {
		for (Iterator<JavaResourcePersistentAttribute> stream = this.javaPersistentAttributes(); stream.hasNext(); ) {
			JavaResourcePersistentAttribute javaResourceAttribute = stream.next();
			if (this.getSpecifiedAttributeFor(javaResourceAttribute) == null) {
				this.addVirtualAttribute(javaResourceAttribute);
			}
		}
	}

	protected void updateVirtualAttributes() {
		Collection<OrmPersistentAttribute> contextAttributesToRemove = CollectionTools.collection(this.virtualAttributes());
		Collection<OrmPersistentAttribute> contextAttributesToUpdate = new ArrayList<OrmPersistentAttribute>();
		int resourceIndex = 0;

		for (Iterator<JavaResourcePersistentAttribute> stream = this.javaPersistentAttributes(); stream.hasNext(); ) {
			JavaResourcePersistentAttribute javaResourceAttribute = stream.next();
			OrmPersistentAttribute specifiedAttribute = this.getSpecifiedAttributeFor(javaResourceAttribute);
			if (specifiedAttribute == null) {
				JavaPersistentAttribute javaAttribute = this.getJpaFactory().buildJavaPersistentAttribute(this, javaResourceAttribute);
				JavaAttributeMapping javaAttributeMapping = javaAttribute.getMapping();
				if (this.mapping.isMetadataComplete()) {
					javaAttributeMapping = javaAttribute.getDefaultMapping();
				}
				boolean contextAttributeFound = false;
				for (OrmPersistentAttribute contextAttribute : contextAttributesToRemove) {
					JavaPersistentAttribute javaPersistentAttribute = contextAttribute.getJavaPersistentAttribute();
					if (javaPersistentAttribute.getResourcePersistentAttribute() == javaResourceAttribute) {
						if (this.valuesAreEqual(contextAttribute.getMappingKey(), javaAttributeMapping.getKey())) { 
							//the mapping key would change if metaDataComplete flag changes, rebuild the orm attribute
							this.moveVirtualAttribute_(resourceIndex, contextAttribute);
							contextAttributesToRemove.remove(contextAttribute);
							contextAttributesToUpdate.add(contextAttribute);
							contextAttributeFound = true;
							break;
						}
					}
				}
				if ( ! contextAttributeFound) {
					OrmPersistentAttribute virtualAttribute = this.addVirtualAttribute(javaAttributeMapping);
					this.fireItemAdded(VIRTUAL_ATTRIBUTES_LIST, virtualAttributesSize() - 1, virtualAttribute);
				}
				resourceIndex++;
			}
		}

		for (OrmPersistentAttribute contextAttribute : contextAttributesToRemove) {
			this.removeVirtualAttribute(contextAttribute);
		}
		//first handle adding/removing of the persistent attributes, then update the others last, 
		//this causes less churn in the update process
		for (OrmPersistentAttribute contextAttribute : contextAttributesToUpdate) {
			contextAttribute.update();
		}
	}

	protected OrmPersistentAttribute addVirtualAttribute(JavaResourcePersistentAttribute resourceAttribute) {
		JavaPersistentAttribute javaAttribute = this.getJpaFactory().buildJavaPersistentAttribute(this, resourceAttribute);
		JavaAttributeMapping javaAttributeMapping = javaAttribute.getMapping();
		if (this.mapping.isMetadataComplete()) {
			javaAttributeMapping = javaAttribute.getDefaultMapping();
		}
		return this.addVirtualAttribute(javaAttributeMapping);
	}

	//not firing change notification so this can be reused in initialize and update
	protected OrmPersistentAttribute addVirtualAttribute(JavaAttributeMapping javaAttributeMapping) {
		OrmAttributeMappingDefinition mappingDefintion = this.getMappingFileDefinition().getOrmAttributeMappingDefinition(javaAttributeMapping.getKey());
		XmlAttributeMapping resourceMapping = mappingDefintion.buildVirtualResourceMapping(this.mapping, javaAttributeMapping, this.getXmlContextNodeFactory());
		OrmPersistentAttribute virtualAttribute = this.buildVirtualOrmPersistentAttribute(javaAttributeMapping, resourceMapping);
		this.virtualAttributes.add(virtualAttribute);
		return virtualAttribute;
	}

	protected OrmPersistentAttribute buildVirtualOrmPersistentAttribute(JavaAttributeMapping javaAttributeMapping, XmlAttributeMapping resourceMapping) {
		return this.buildOrmPersistentAttribute(this.buildVirtualAttributeOwner(javaAttributeMapping.getPersistentAttribute()), resourceMapping);
	}

	protected OrmPersistentAttribute buildOrmPersistentAttribute(OrmPersistentAttribute.Owner owner, XmlAttributeMapping resourceMapping) {
		return this.getXmlContextNodeFactory().buildOrmPersistentAttribute(this, owner, resourceMapping);
	}

	protected OrmPersistentAttribute.Owner buildVirtualAttributeOwner(final JavaPersistentAttribute javaPersistentAttribute) {
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


	// ********** Java persistent type **********

	public JavaPersistentType getJavaPersistentType() {
		return this.javaPersistentType;
	}

	protected void setJavaPersistentType(JavaPersistentType javaPersistentType) {
		JavaPersistentType old = this.javaPersistentType;
		this.javaPersistentType = javaPersistentType;
		this.firePropertyChanged(JAVA_PERSISTENT_TYPE_PROPERTY, old, javaPersistentType);
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


	// ********** super persistent type **********

	public PersistentType getSuperPersistentType() {
		return this.superPersistentType;
	}

	protected void setSuperPersistentType(PersistentType superPersistentType) {
		PersistentType old = this.superPersistentType;
		this.superPersistentType = superPersistentType;
		this.firePropertyChanged(SUPER_PERSISTENT_TYPE_PROPERTY, old, superPersistentType);
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

	protected PersistentType buildSuperPersistentType() {
		return (this.javaPersistentType == null) ? null : this.javaPersistentType.getSuperPersistentType();
	}


	// ********** inheritance **********

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


	// ********** metamodel **********

	public IFile getMetamodelFile() {
		return (this.javaPersistentType == null) ? null : this.metamodelSynchronizer.getFile();
	}

	public void initializeMetamodel() {
		// do nothing - probably shouldn't be called...
	}

	/**
	 * All orm.xml persistent types must be able to generate a static metamodel
	 * because 1.0 orm.xml files can be referenced from 2.0 persistence.xml files.
	 */
	public void synchronizeMetamodel() {
		if (this.javaPersistentType != null) {
			this.metamodelSynchronizer.synchronize();
		}
	}

	public void disposeMetamodel() {
		// do nothing - probably shouldn't be called...
	}


	// ********** JpaStructureNode implementation **********

	public String getId() {
		return OrmStructureNodes.PERSISTENT_TYPE_ID;
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		for (OrmPersistentAttribute attribute : this.getSpecifiedAttributes()) {
			if (attribute.contains(textOffset)) {
				return attribute;
			}
		}
		return this;
	}

	public TextRange getSelectionTextRange() {
		return this.mapping.getSelectionTextRange();
	}

	public void dispose() {
		if (this.javaPersistentType != null) {
			this.javaPersistentType.dispose();
		}
	}


	// ********** PersistentType.Owner implementation **********

	public AccessType getOverridePersistentTypeAccess() {
		if (this.specifiedAccess != null) {
			return this.specifiedAccess;
		}

		if (this.superPersistentType instanceof OrmPersistentType) {
			AccessType accessType = ((OrmPersistentType) this.superPersistentType).getSpecifiedAccess();
			if (accessType != null) {
				return accessType;
			}
		}

		if (this.mapping.isMetadataComplete()) {
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


	// ********** validation **********

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
					this.mapping.getClassTextRange()
				)
			);
		}
	}

	protected void validateMapping(List<IMessage> messages, IReporter reporter) {
		try {
			this.mapping.validate(messages, reporter);
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
		return this.mapping.getValidationTextRange();
	}


	// ********** misc **********

	@Override
	public EntityMappings getParent() {
		return (EntityMappings) super.getParent();
	}

	public String getDefaultPackage() {
		return this.getEntityMappings().getDefaultPersistentTypePackage();
	}

	public boolean isDefaultMetadataComplete() {
		return this.getEntityMappings().isDefaultPersistentTypeMetadataComplete();
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

	public boolean contains(int textOffset) {
		return this.mapping.containsOffset(textOffset);
	}

	public void classChanged(String oldClass, String newClass) {
		this.firePropertyChanged(NAME_PROPERTY, oldClass, newClass);
	}

	public boolean isMapped() {
		return true;
	}

	public String getMappingKey() {
		return this.mapping.getKey();
	}

	protected EntityMappings getEntityMappings() {
		return this.getParent();
	}

	protected XmlTypeMapping getResourceTypeMapping() {
		return this.mapping.getResourceTypeMapping();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}


	// ********** specified persistent attribute owner **********

	protected class SpecifiedAttributeOwner
		implements OrmPersistentAttribute.Owner
	{
		private JavaPersistentAttribute cachedJavaPersistentAttribute;

		public SpecifiedAttributeOwner() {
			super();
		}

		public JavaPersistentAttribute findJavaPersistentAttribute(OrmPersistentAttribute ormPersistentAttribute) {
			if (GenericOrmPersistentType.this.javaPersistentType == null) {
				return null;
			}
			String ormName = ormPersistentAttribute.getName();
			if (ormName == null) {
				return null;
			}
			AccessType ormAccess = ormPersistentAttribute.getAccess();

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
			return GenericOrmPersistentType.this.javaPersistentType.getAttributeNamed(attributeName);
		}

		protected JavaPersistentAttribute buildJavaPersistentAttribute(String ormName, AccessType ormAccess) {
			JavaResourcePersistentAttribute jrpa = this.getJavaResourcePersistentAttribute(this.getJavaResourcePersistentType(), ormName, ormAccess);
			if (this.cachedJavaPersistentAttribute != null &&
					this.cachedJavaPersistentAttribute.getResourcePersistentAttribute() == jrpa) {
				return this.cachedJavaPersistentAttribute;
			}
			return this.cachedJavaPersistentAttribute = (jrpa == null) ? null : GenericOrmPersistentType.this.buildJavaPersistentAttribute(jrpa);
		}

		protected JavaResourcePersistentType getJavaResourcePersistentType() {
			return GenericOrmPersistentType.this.javaPersistentType.getResourcePersistentType();
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
			JavaResourcePersistentType superclass = GenericOrmPersistentType.this.getJavaResourcePersistentType(superclassName);
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

}
