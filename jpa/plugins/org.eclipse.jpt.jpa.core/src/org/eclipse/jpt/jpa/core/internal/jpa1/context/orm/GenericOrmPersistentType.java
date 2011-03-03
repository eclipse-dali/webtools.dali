/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.utility.BodySourceWriter;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ClassName;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.ChainIterable;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SnapshotCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.common.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmStructureNodes;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelSourceType;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaPersistentType2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmPersistentType2_0;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpa.core.resource.orm.Attributes;
import org.eclipse.jpt.jpa.core.resource.orm.OrmPackage;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.jpa.core.resource.xml.EmfTools;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> persistent type:<ul>
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
	protected OrmTypeMapping mapping;  // never null

	protected JavaPersistentType javaPersistentType;

	protected AccessType specifiedAccess;
	protected AccessType defaultAccess;  // never null

	protected final Vector<OrmPersistentAttribute> specifiedAttributes = new Vector<OrmPersistentAttribute>();
	protected final SpecifiedAttributeContainerAdapter specifiedAttributeContainerAdapter = new SpecifiedAttributeContainerAdapter();

	protected final Vector<OrmReadOnlyPersistentAttribute> virtualAttributes = new Vector<OrmReadOnlyPersistentAttribute>();
	protected final VirtualAttributeContainerAdapter virtualAttributeContainerAdapter = new VirtualAttributeContainerAdapter();

	protected PersistentType superPersistentType;

	protected String declaringTypeName;

	protected final MetamodelSourceType.Synchronizer metamodelSynchronizer;


	public GenericOrmPersistentType(EntityMappings parent, XmlTypeMapping xmlTypeMapping) {
		super(parent);
		this.mapping = this.buildMapping(xmlTypeMapping);
		// 'javaPersistentType' is resolved in the update
		this.specifiedAccess = this.buildSpecifiedAccess();
		this.defaultAccess = AccessType.FIELD;  // keep this non-null
		this.initializeSpecifiedAttributes();
		this.metamodelSynchronizer = this.buildMetamodelSynchronizer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.mapping.synchronizeWithResourceModel();
		this.syncJavaPersistentType();
		this.setSpecifiedAccess_(this.buildSpecifiedAccess());
		this.syncSpecifiedAttributes();
		this.synchronizeNodesWithResourceModel(this.getVirtualAttributes());
	}

	@Override
	public void update() {
		super.update();
		this.mapping.update();
		this.updateJavaPersistentType();
		this.setDefaultAccess(this.buildDefaultAccess());
		this.updateNodes(this.getSpecifiedAttributes());
		this.updateVirtualAttributes();
		this.setSuperPersistentType(this.buildSuperPersistentType());
		this.setDeclaringTypeName(this.buildDeclaringTypeName());
	}


	// ********** mapping **********

	public OrmTypeMapping getMapping() {
		return this.mapping;
	}

	public String getMappingKey() {
		return this.mapping.getKey();
	}

	public void setMappingKey(String mappingKey) {
		if (this.valuesAreDifferent(this.getMappingKey(), mappingKey)) {
			this.setMappingKey_(mappingKey);
		}
	}

	protected void setMappingKey_(String mappingKey) {
		OrmTypeMapping old = this.mapping;
		OrmTypeMappingDefinition mappingDefinition = this.getMappingFileDefinition().getTypeMappingDefinition(mappingKey);
		XmlTypeMapping xmlTypeMapping = mappingDefinition.buildResourceMapping(this.getResourceNodeFactory());
		this.mapping = this.buildMapping(xmlTypeMapping);
		this.getEntityMappings().changeMapping(this, old, this.mapping);
		this.firePropertyChanged(MAPPING_PROPERTY, old, this.mapping);
	}

	protected OrmTypeMapping buildMapping(XmlTypeMapping xmlTypeMapping) {
		OrmTypeMappingDefinition md = this.getMappingFileDefinition().getTypeMappingDefinition(xmlTypeMapping.getMappingKey());
		return md.buildContextMapping(this, xmlTypeMapping, this.getContextNodeFactory());
	}

	protected XmlTypeMapping getXmlTypeMapping() {
		return this.mapping.getXmlTypeMapping();
	}

	public boolean isMapped() {
		return true;
	}


	// ********** name **********

	public String getName() {
		if (this.javaPersistentType != null) {
			return this.javaPersistentType.getName();
		}
		return this.convertMappingClassName(this.mapping.getClass_());
	}

	public String getSimpleName(){
		String className = this.getName();
		return StringTools.stringIsEmpty(className) ? null : ClassName.getSimpleName(className);
	}

	/**
	 * We clear out {@link #javaPersistentType} here because we cannot compare its name
	 * to the mapping's class name, since it may have been prefixed by the entity
	 * mappings package.
	 */
	public void mappingClassChanged(String oldClass, String newClass) {
		this.firePropertyChanged(NAME_PROPERTY, this.convertMappingClassName(oldClass), this.convertMappingClassName(newClass));
		// clear out the Java type here, it will be rebuilt during "update"
		if (this.javaPersistentType != null) {
			this.javaPersistentType.dispose();
			this.setJavaPersistentType(null);
		}
	}

	/**
	 * Nested class names are specified with a <code>'$'</code>
	 * in <code>orm.xml</code>.
	 */
	protected String convertMappingClassName(String name) {
		return (name == null) ? null : name.replace('$', '.');
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

	/**
	 * If the persistent type's mapping's class (name) changes during
	 * <em>sync</em>, the Java persistent type will be cleared out in
	 * {@link #mappingClassChanged(String, String)}. If we get here and
	 * the Java persistent type is still present, we can
	 * <em>sync</em> it. Of course, it might still be obsolete if the
	 * entity mappings's package has changed....
	 *
	 * @see #updateJavaPersistentType()
	 */
	protected void syncJavaPersistentType() {
		if (this.javaPersistentType != null) {
			this.javaPersistentType.synchronizeWithResourceModel();
		}
	}

	/**
	 * @see #syncJavaPersistentType()
	 */
	protected void updateJavaPersistentType() {
		JavaResourcePersistentType resourceType = this.resolveJavaResourcePersistentType();
		if (resourceType == null) {
			if (this.javaPersistentType != null) {
				this.javaPersistentType.dispose();
				this.setJavaPersistentType(null);
			}
		} else {
			if (this.javaPersistentType == null) {
				this.setJavaPersistentType(this.buildJavaPersistentType(resourceType));
			} else {
				if (this.javaPersistentType.getResourcePersistentType() == resourceType) {
					this.javaPersistentType.update();
				} else {
					this.javaPersistentType.dispose();
					this.setJavaPersistentType(this.buildJavaPersistentType(resourceType));
				}
			}
		}
	}

	protected JavaResourcePersistentType resolveJavaResourcePersistentType() {
		return this.getEntityMappings().resolveJavaResourcePersistentType(this.getName());
	}

	protected JavaPersistentType buildJavaPersistentType(JavaResourcePersistentType jrpt) {
		return this.getJpaFactory().buildJavaPersistentType(this, jrpt);
	}


	// ********** access **********

	public AccessType getAccess() {
		return (this.specifiedAccess != null) ? this.specifiedAccess : this.defaultAccess;
	}

	public AccessType getSpecifiedAccess() {
		return this.specifiedAccess;
	}

	public void setSpecifiedAccess(AccessType access) {
		this.setSpecifiedAccess_(access);
		this.getXmlTypeMapping().setAccess(AccessType.toOrmResourceModel(access));
	}

	protected void setSpecifiedAccess_(AccessType access) {
		AccessType old = this.specifiedAccess;
		this.specifiedAccess = access;
		this.firePropertyChanged(SPECIFIED_ACCESS_PROPERTY, old, access);
	}

	protected AccessType buildSpecifiedAccess() {
		return AccessType.fromOrmResourceModel(this.getXmlTypeMapping().getAccess());
	}

	public AccessType getDefaultAccess() {
		return this.defaultAccess;
	}

	protected void setDefaultAccess(AccessType access) {
		AccessType old = this.defaultAccess;
		this.defaultAccess = access;
		this.firePropertyChanged(DEFAULT_ACCESS_PROPERTY, old, access);
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
		return (access != null) ? access : AccessType.FIELD;  // default to FIELD if no specified access found
	}

	/**
	 * pre-condition: {@link #javaPersistentType} is not <code>null</code>
	 */
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


	// ********** attributes **********

	public ListIterator<OrmReadOnlyPersistentAttribute> attributes() {
		return this.getAttributes().iterator();
	}

	@SuppressWarnings("unchecked")
	public ListIterable<OrmReadOnlyPersistentAttribute> getAttributes() {
		return new CompositeListIterable<OrmReadOnlyPersistentAttribute>(this.getReadOnlySpecifiedAttributes(), this.getVirtualAttributes());
	}

	public int attributesSize() {
		return this.specifiedAttributesSize() + this.virtualAttributesSize();
	}

	public Iterator<String> attributeNames() {
		return this.getAttributeNames().iterator();
	}

	public Iterable<String> getAttributeNames() {
		return this.convertToNames(this.getAttributes());
	}

	public OrmReadOnlyPersistentAttribute getAttributeNamed(String attributeName) {
		Iterator<OrmReadOnlyPersistentAttribute> stream = this.getAttributesNamed(attributeName).iterator();
		return stream.hasNext() ? stream.next() : null;
	}

	public Iterator<ReadOnlyPersistentAttribute> allAttributes() {
		return this.getAllAttributes().iterator();
	}

	public Iterable<ReadOnlyPersistentAttribute> getAllAttributes() {
		return new CompositeIterable<ReadOnlyPersistentAttribute>(
				new TransformationIterable<PersistentType, Iterable<ReadOnlyPersistentAttribute>>(this.getInheritanceHierarchy()) {
					@Override
					protected Iterable<ReadOnlyPersistentAttribute> transform(PersistentType pt) {
						return new SnapshotCloneIterable<ReadOnlyPersistentAttribute>(pt.attributes());
					}
				}
			);
	}

	public Iterator<String> allAttributeNames() {
		return this.getAllAttributeNames().iterator();
	}

	public Iterable<String> getAllAttributeNames() {
		return this.convertToNames(this.getAllAttributes());
	}

	protected Iterable<OrmReadOnlyPersistentAttribute> getAttributesNamed(final String attributeName) {
		return new FilteringIterable<OrmReadOnlyPersistentAttribute>(this.getAttributes()) {
			@Override
			protected boolean accept(OrmReadOnlyPersistentAttribute attribute) {
				return Tools.valuesAreEqual(attributeName, attribute.getName());
			}
		};
	}

	public ReadOnlyPersistentAttribute resolveAttribute(String attributeName) {
		Iterator<OrmReadOnlyPersistentAttribute> attributes = this.getAttributesNamed(attributeName).iterator();
		if (attributes.hasNext()) {
			OrmReadOnlyPersistentAttribute attribute = attributes.next();
			return attributes.hasNext() ? null /* more than one */: attribute;
		}
		// recurse
		return (this.superPersistentType == null) ? null : this.superPersistentType.resolveAttribute(attributeName);
	}

	protected Iterable<String> convertToNames(Iterable<? extends ReadOnlyPersistentAttribute> attributes) {
		return new TransformationIterable<ReadOnlyPersistentAttribute, String>(attributes) {
			@Override
			protected String transform(ReadOnlyPersistentAttribute attribute) {
				return attribute.getName();
			}
		};
	}


	// ********** attribute conversions **********

	public OrmPersistentAttribute convertAttributeToSpecified(OrmReadOnlyPersistentAttribute virtualAttribute) {
		return this.convertAttributeToSpecified(virtualAttribute, virtualAttribute.getMappingKey());
	}

	public OrmPersistentAttribute convertAttributeToSpecified(OrmReadOnlyPersistentAttribute virtualAttribute, String mappingKey) {
		if ( ! virtualAttribute.isVirtual()) {
			throw new IllegalArgumentException("Attribute is already specified: " + virtualAttribute); //$NON-NLS-1$
		}
		if (mappingKey == null) {
			// this typically happens when the virtual attribute does not have a mapping
			throw new IllegalArgumentException("Use convertAttributeToSpecified(OrmReadOnlyPersistentAttribute, String) instead and specify a mapping key"); //$NON-NLS-1$
		}
		return this.convertAttributeToSpecified_(virtualAttribute, mappingKey);
	}

	/**
	 * <em>Silently</em> remove the virtual attribute and add specified
	 * attribute before triggering an <em>update</em> or the dangling
	 * virtual attribute will be removed preemptively.
	 */
	protected OrmPersistentAttribute convertAttributeToSpecified_(OrmReadOnlyPersistentAttribute virtualAttribute, String mappingKey) {
		// silently remove the virtual attribute
		int virtualIndex = this.virtualAttributes.indexOf(virtualAttribute);
		this.virtualAttributes.remove(virtualIndex);
		virtualAttribute.dispose();

		// silently add the specified attribute
		OrmAttributeMappingDefinition md = this.getMappingFileDefinition().getAttributeMappingDefinition(mappingKey);
		XmlAttributeMapping xmlMapping = md.buildResourceMapping(this.getResourceNodeFactory());

		OrmPersistentAttribute specifiedAttribute = this.buildSpecifiedAttribute(xmlMapping);
		// we need to add the attribute to the right spot in the list - stupid spec...
		int specifiedIndex = this.getSpecifiedAttributeInsertionIndex(specifiedAttribute);
		this.specifiedAttributes.add(specifiedIndex, specifiedAttribute);

		// this will trigger the initial update;
		// no changes to either collection (virtual or specified) should be detected at this point
		specifiedAttribute.getMapping().setName(virtualAttribute.getName());

		// fire the list change events
		this.fireItemRemoved(VIRTUAL_ATTRIBUTES_LIST, virtualIndex, virtualAttribute);
		this.fireItemAdded(SPECIFIED_ATTRIBUTES_LIST, specifiedIndex, specifiedAttribute);

		// it should be safe to update the XML now
		Attributes xmlAttributes = this.getXmlAttributesForUpdate();
		specifiedAttribute.getMapping().addXmlAttributeMappingTo(xmlAttributes);
		// possibly a NOP, but needed when we trigger the creation of a new 'attributes'
		this.getXmlTypeMapping().setAttributes(xmlAttributes);

		// copy over the specified access(?)
		AccessType oldAccess = virtualAttribute.getJavaPersistentAttribute().getSpecifiedAccess();
		if (oldAccess != null) {
			specifiedAttribute.setSpecifiedAccess(oldAccess);
		}
		return specifiedAttribute;
	}

	// TODO this is used only by our tests...
	// we cannot delegate to getAttributeNamed(String).convertToSpecified()
	// because the tests use this method to add "orphan" xml attributes (that
	// do not have a corresponding java attribute :( )
	public OrmPersistentAttribute addSpecifiedAttribute(String mappingKey, String attributeName) {
		// force the creation of an empty xml attribute container beforehand or it will trigger
		// a sync and, if we do this after adding the attribute, clear out our context attributes
		Attributes xmlAttributes = this.getXmlAttributesForUpdate();
		this.getXmlTypeMapping().setAttributes(xmlAttributes);  // possibly a NOP

		OrmAttributeMappingDefinition md = this.getMappingFileDefinition().getAttributeMappingDefinition(mappingKey);
		XmlAttributeMapping xmlMapping = md.buildResourceMapping(this.getResourceNodeFactory());

		OrmPersistentAttribute specifiedAttribute = this.buildSpecifiedAttribute(xmlMapping);
		// we need to add the attribute to the right spot in the list - stupid spec...
		int specifiedIndex = this.getSpecifiedAttributeInsertionIndex(specifiedAttribute);
		// the virtual attributes list should remain unchanged since the specified attribute has no name
		this.addItemToList(specifiedIndex, specifiedAttribute, this.specifiedAttributes, SPECIFIED_ATTRIBUTES_LIST);
		specifiedAttribute.getMapping().addXmlAttributeMappingTo(xmlAttributes);

		// this will trigger the update of the virtual attributes list
		specifiedAttribute.getMapping().setName(attributeName);

		return specifiedAttribute;
	}

	protected int getSpecifiedAttributeInsertionIndex(OrmPersistentAttribute attribute) {
		return CollectionTools.insertionIndexOf(this.specifiedAttributes, attribute, this.getAttributeComparator());
	}

	protected Comparator<OrmPersistentAttribute> getAttributeComparator() {
		return ATTRIBUTE_COMPARATOR;
	}

	protected static final Comparator<OrmPersistentAttribute> ATTRIBUTE_COMPARATOR =
		new Comparator<OrmPersistentAttribute>() {
			public int compare(OrmPersistentAttribute attribute1, OrmPersistentAttribute attribute2) {
				int seq1 = attribute1.getMapping().getXmlSequence();
				int seq2 = attribute2.getMapping().getXmlSequence();
				return (seq1 == seq2) ? 0 : (seq1 < seq2) ? -1 : 1;
			}
		};

	/**
	 * <em>Silently</em> add the new virtual attribute before removing the
	 * specified attribute, or the <em>update</em> will discover the missing
	 * virtual attribute and add it preemptively.
	 */
	public OrmReadOnlyPersistentAttribute convertAttributeToVirtual(OrmPersistentAttribute specifiedAttribute) {
		if (specifiedAttribute.isVirtual()) {
			throw new IllegalArgumentException("Attribute is already virtual: " + specifiedAttribute); //$NON-NLS-1$
		}

		int virtualIndex = this.virtualAttributes.size();
		OrmReadOnlyPersistentAttribute virtualAttribute = null;
		// make sure the corresponding resource Java attribute actually exists in the *current* type;
		// do *not* take the context Java attribute directly from the specified ORM
		// attribute we are converting since it may have come from a superclass;
		// instead, use its resource Java attribute (which will match both name and access type,
		// but we still need to check its parent type)
		JavaResourcePersistentAttribute javaResourceAttribute = specifiedAttribute.getJavaResourcePersistentAttribute();
		if ((javaResourceAttribute != null) && this.javaResourceAttributeWillBeVirtual(javaResourceAttribute, specifiedAttribute)) {
			virtualAttribute = this.buildVirtualAttribute(javaResourceAttribute);
			this.virtualAttributes.add(virtualIndex, virtualAttribute);
		}

		this.removeSpecifiedAttribute(specifiedAttribute);  // trigger update

		if (virtualAttribute != null) {
			this.fireItemAdded(VIRTUAL_ATTRIBUTES_LIST, virtualIndex, virtualAttribute);
		}
		return virtualAttribute;
	}

	/**
	 * Return whether the specified Java resource attribute will be a
	 * <em>virtual</em> attribute when the specified specified attribute is
	 * removed from the type. The Java resource attribute must be among the
	 * valid Java resource attributes and it must not correspond to any of the
	 * remaining specified attributes.
	 */
	protected boolean javaResourceAttributeWillBeVirtual(JavaResourcePersistentAttribute javaResourceAttribute, OrmPersistentAttribute specifiedAttributeToBeRemoved) {
		return CollectionTools.contains(this.getJavaResourceAttributes(), javaResourceAttribute) &&
				(this.getSpecifiedAttributeFor(javaResourceAttribute, specifiedAttributeToBeRemoved) == null);
	}


	// ********** xml attributes **********

	/**
	 * Return <code>null</code> if the <code>attributes</code> element is missing.
	 */
	protected Attributes getXmlAttributes() {
		return this.getXmlTypeMapping().getAttributes();
	}

	/**
	 * Build a new <code>attributes</code> element if it is not present;
	 * but do <em>not</em> add it to the XML type mapping. Callers will have
	 * to add it to the XML type mapping when appropriate.
	 */
	protected Attributes getXmlAttributesForUpdate() {
		Attributes xmlAttributes = this.getXmlAttributes();
		return (xmlAttributes != null) ? xmlAttributes : this.buildXmlAttributes();
	}

	protected Attributes buildXmlAttributes() {
		return EmfTools.create(this.getResourceNodeFactory(), OrmPackage.eINSTANCE.getAttributes(), Attributes.class);
	}

	protected void removeXmlAttributesIfUnset() {
		if (this.getXmlAttributes().isUnset()) {
			this.getXmlTypeMapping().setAttributes(null);
		}
	}


	// ********** specified attributes **********

	public ListIterator<OrmPersistentAttribute> specifiedAttributes() {
		return this.getSpecifiedAttributes().iterator();
	}

	protected ListIterable<OrmPersistentAttribute> getSpecifiedAttributes() {
		return new LiveCloneListIterable<OrmPersistentAttribute>(this.specifiedAttributes);
	}

	protected ListIterable<OrmReadOnlyPersistentAttribute> getReadOnlySpecifiedAttributes() {
		return new SuperListIterableWrapper<OrmReadOnlyPersistentAttribute>(this.getSpecifiedAttributes());
	}

	public int specifiedAttributesSize() {
		return this.specifiedAttributes.size();
	}

	protected void removeSpecifiedAttribute(OrmPersistentAttribute attribute) {
		this.removeSpecifiedAttribute_(attribute);
		attribute.getMapping().removeXmlAttributeMappingFrom(this.getXmlAttributes());
		this.removeXmlAttributesIfUnset();
	}

	public void changeMapping(OrmPersistentAttribute attribute, OrmAttributeMapping oldMapping, OrmAttributeMapping newMapping) {
		// keep the context model in sync with each change to the resource model
		int sourceIndex = this.specifiedAttributes.indexOf(attribute);
		this.specifiedAttributes.remove(sourceIndex);
		oldMapping.removeXmlAttributeMappingFrom(this.getXmlAttributes());

		int targetIndex = this.getSpecifiedAttributeInsertionIndex(attribute);
		this.specifiedAttributes.add(targetIndex, attribute);
		newMapping.addXmlAttributeMappingTo(this.getXmlAttributes());

		oldMapping.initializeOn(newMapping);

		this.fireItemMoved(SPECIFIED_ATTRIBUTES_LIST, targetIndex, sourceIndex);
	}

	protected void initializeSpecifiedAttributes() {
		for (XmlAttributeMapping xmlMapping : this.getXmlAttributeMappings()) {
			this.specifiedAttributes.add(this.buildSpecifiedAttribute(xmlMapping));
		}
	}

	protected Iterable<XmlAttributeMapping> getXmlAttributeMappings() {
		Attributes xmlAttributes = this.getXmlAttributes();
		return (xmlAttributes != null) ? xmlAttributes.getAttributeMappings() : EmptyIterable.<XmlAttributeMapping>instance();
	}

	protected OrmPersistentAttribute buildSpecifiedAttribute(XmlAttributeMapping xmlMapping) {
		return this.getContextNodeFactory().buildOrmPersistentAttribute(this, xmlMapping);
	}

	protected void syncSpecifiedAttributes() {
		ContextContainerTools.synchronizeWithResourceModel(this.specifiedAttributeContainerAdapter);
	}

	protected void moveSpecifiedAttribute_(int index, OrmPersistentAttribute attribute) {
		this.moveItemInList(index, attribute, this.specifiedAttributes, SPECIFIED_ATTRIBUTES_LIST);
	}

	protected void addSpecifiedAttribute_(int index, XmlAttributeMapping xmlMapping) {
		OrmPersistentAttribute attribute = this.buildSpecifiedAttribute(xmlMapping);
		this.addItemToList(index, attribute, this.specifiedAttributes, SPECIFIED_ATTRIBUTES_LIST);
	}

	protected void removeSpecifiedAttribute_(OrmPersistentAttribute attribute) {
		this.removeItemFromList(attribute, this.specifiedAttributes, SPECIFIED_ATTRIBUTES_LIST);
	}

	/**
	 * specified attribute container adapter
	 */
	protected class SpecifiedAttributeContainerAdapter
		implements ContextContainerTools.Adapter<OrmPersistentAttribute, XmlAttributeMapping>
	{
		public Iterable<OrmPersistentAttribute> getContextElements() {
			return GenericOrmPersistentType.this.getSpecifiedAttributes();
		}
		public Iterable<XmlAttributeMapping> getResourceElements() {
			return GenericOrmPersistentType.this.getXmlAttributeMappings();
		}
		public XmlAttributeMapping getResourceElement(OrmPersistentAttribute contextElement) {
			return contextElement.getMapping().getXmlAttributeMapping();
		}
		public void moveContextElement(int index, OrmPersistentAttribute element) {
			GenericOrmPersistentType.this.moveSpecifiedAttribute_(index, element);
		}
		public void addContextElement(int index, XmlAttributeMapping resourceElement) {
			GenericOrmPersistentType.this.addSpecifiedAttribute_(index, resourceElement);
		}
		public void removeContextElement(OrmPersistentAttribute element) {
			GenericOrmPersistentType.this.removeSpecifiedAttribute_(element);
		}
	}


	// ********** virtual attributes **********

	public ListIterator<OrmReadOnlyPersistentAttribute> virtualAttributes() {
		return this.getVirtualAttributes().iterator();
	}

	public ListIterable<OrmReadOnlyPersistentAttribute> getVirtualAttributes() {
		return new LiveCloneListIterable<OrmReadOnlyPersistentAttribute>(this.virtualAttributes);
	}

	public int virtualAttributesSize() {
		return this.virtualAttributes.size();
	}

	protected void updateVirtualAttributes() {
		ContextContainerTools.update(this.virtualAttributeContainerAdapter);
	}

	/**
	 * Return all the Java resource attributes that have the appropriate access
	 * and do not have a corresponding <code>orm.xml</code> mapping currently
	 * specified in the <code>orm.xml</code> persistent type.
	 */
	protected Iterable<JavaResourcePersistentAttribute> getVirtualJavaResourceAttributes() {
		return new FilteringIterable<JavaResourcePersistentAttribute>(this.getJavaResourceAttributes()) {
				@Override
				protected boolean accept(JavaResourcePersistentAttribute javaResourceAttribute) {
					return GenericOrmPersistentType.this.javaResourceAttributeIsVirtual(javaResourceAttribute);
				}
			};
	}

	protected Iterable<JavaResourcePersistentAttribute> getJavaResourceAttributes() {
		return CollectionTools.iterable(this.javaResourceAttributes());
	}

	/**
	 * Return the Java resource attributes with the appropriate access type.
	 */
	protected Iterator<JavaResourcePersistentAttribute> javaResourceAttributes() {
		JavaResourcePersistentType javaResourceType = this.getJavaResourceType();
		return (javaResourceType != null) ?
				javaResourceType.persistableAttributes(this.getVirtualJavaAccess().getJavaAccessType()) :
				EmptyListIterator.<JavaResourcePersistentAttribute>instance();
	}

	protected JavaResourcePersistentType getJavaResourceType() {
		return (this.javaPersistentType == null) ? null : this.javaPersistentType.getResourcePersistentType();
	}

	/**
	 * Return the access type that determines which Java attributes are to be
	 * used for the <code>orm.xml</code> type's <em>virtual</em> attributes.
	 */
	protected AccessType getVirtualJavaAccess() {
		if (this.specifiedAccess != null) {
			return this.specifiedAccess;
		}
		if (this.mapping.isMetadataComplete()) {
			return this.defaultAccess;
		}
		AccessType javaAccess = this.javaPersistentType.getSpecifiedAccess();
		return (javaAccess != null) ? javaAccess : this.defaultAccess;
	}

	protected boolean javaResourceAttributeIsVirtual(JavaResourcePersistentAttribute javaResourceAttribute) {
		return this.getSpecifiedAttributeFor(javaResourceAttribute) == null;
	}

	protected OrmPersistentAttribute getSpecifiedAttributeFor(JavaResourcePersistentAttribute javaResourceAttribute) {
		return this.getSpecifiedAttributeFor(javaResourceAttribute, null);
	}

	/**
	 * Return the specified attribute corresponding to the specified Java
	 * resource attribute, ignoring the specified excluded attribute (since
	 * there can be more than one specified attribute per Java resource
	 * attribute; albeit erroneously).
	 */
	protected OrmPersistentAttribute getSpecifiedAttributeFor(JavaResourcePersistentAttribute javaResourceAttribute, OrmPersistentAttribute exclude) {
		for (OrmPersistentAttribute ormAttribute : this.getSpecifiedAttributes()) {
			if (ormAttribute == exclude) {
				continue;  // skip
			}
			if (ormAttribute.getJavaResourcePersistentAttribute() == javaResourceAttribute) {
				return ormAttribute;
			}
		}
		return null;
	}

	protected void moveVirtualAttribute(int index, OrmReadOnlyPersistentAttribute virtualAttribute) {
		this.moveItemInList(index, virtualAttribute, this.virtualAttributes, VIRTUAL_ATTRIBUTES_LIST);
	}

	protected void addVirtualAttribute(int index, JavaResourcePersistentAttribute javaResourceAttribute) {
		OrmReadOnlyPersistentAttribute virtualAttribute = this.buildVirtualAttribute(javaResourceAttribute);
		this.addItemToList(index, virtualAttribute, this.virtualAttributes, VIRTUAL_ATTRIBUTES_LIST);
	}

	protected OrmReadOnlyPersistentAttribute buildVirtualAttribute(JavaResourcePersistentAttribute javaResourceAttribute) {
		return this.getContextNodeFactory().buildVirtualOrmPersistentAttribute(this, javaResourceAttribute);
	}

	protected void removeVirtualAttribute(OrmReadOnlyPersistentAttribute virtualAttribute) {
		virtualAttribute.dispose();
		this.removeItemFromList(virtualAttribute, this.virtualAttributes, VIRTUAL_ATTRIBUTES_LIST);
	}

	/**
	 * virtual attribute container adapter
	 */
	protected class VirtualAttributeContainerAdapter
		implements ContextContainerTools.Adapter<OrmReadOnlyPersistentAttribute, JavaResourcePersistentAttribute>
	{
		public Iterable<OrmReadOnlyPersistentAttribute> getContextElements() {
			return GenericOrmPersistentType.this.getVirtualAttributes();
		}
		public Iterable<JavaResourcePersistentAttribute> getResourceElements() {
			return GenericOrmPersistentType.this.getVirtualJavaResourceAttributes();
		}
		public JavaResourcePersistentAttribute getResourceElement(OrmReadOnlyPersistentAttribute contextElement) {
			return contextElement.getJavaResourcePersistentAttribute();
		}
		public void moveContextElement(int index, OrmReadOnlyPersistentAttribute element) {
			GenericOrmPersistentType.this.moveVirtualAttribute(index, element);
		}
		public void addContextElement(int index, JavaResourcePersistentAttribute resourceElement) {
			GenericOrmPersistentType.this.addVirtualAttribute(index, resourceElement);
		}
		public void removeContextElement(OrmReadOnlyPersistentAttribute element) {
			GenericOrmPersistentType.this.removeVirtualAttribute(element);
		}
	}


	// ********** super persistent type **********

	public PersistentType getSuperPersistentType() {
		return this.superPersistentType;
	}

	protected void setSuperPersistentType(PersistentType persistentType) {
		PersistentType old = this.superPersistentType;
		this.superPersistentType = persistentType;
		this.firePropertyChanged(SUPER_PERSISTENT_TYPE_PROPERTY, old, persistentType);
	}

	protected PersistentType buildSuperPersistentType() {
		PersistentType spt = this.buildSuperPersistentType_();
		if (spt == null) {
			return null;
		}
		// check for circular inheritance
		return CollectionTools.contains(spt.inheritanceHierarchy(), this) ? null : spt;
	}

	protected PersistentType buildSuperPersistentType_() {
		return (this.javaPersistentType == null) ? null : this.javaPersistentType.getSuperPersistentType();
	}


	// ********** inheritance **********

	public Iterator<PersistentType> inheritanceHierarchy() {
		return this.getInheritanceHierarchy().iterator();
	}

	public Iterable<PersistentType> getInheritanceHierarchy() {
		return this.getInheritanceHierarchyOf(this);
	}

	public Iterator<PersistentType> ancestors() {
		return this.getAncestors().iterator();
	}

	public Iterable<PersistentType> getAncestors() {
		return this.getInheritanceHierarchyOf(this.superPersistentType);
	}

	protected Iterable<PersistentType> getInheritanceHierarchyOf(PersistentType start) {
		// using a chain iterable to traverse up the inheritance tree
		return new ChainIterable<PersistentType>(start) {
			@Override
			protected PersistentType nextLink(PersistentType persistentType) {
				return persistentType.getSuperPersistentType();
			}
		};
	}


	// ********** declaring type name **********

	public String getDeclaringTypeName() {
		return this.declaringTypeName;
	}

	protected void setDeclaringTypeName(String name) {
		String old = this.declaringTypeName;
		this.declaringTypeName = name;
		this.firePropertyChanged(DECLARING_TYPE_NAME_PROPERTY, old, name);
	}

	protected String buildDeclaringTypeName() {
		return this.isJpa2_0Compatible() ? this.buildDeclaringTypeName_() : null;
	}

	protected String buildDeclaringTypeName_() {
		return (this.javaPersistentType == null) ?
				null : ((JavaPersistentType2_0) this.javaPersistentType).getDeclaringTypeName();
	}


	// ********** metamodel **********

	protected MetamodelSourceType.Synchronizer buildMetamodelSynchronizer() {
		return this.isJpa2_0Compatible() ?
				this.getJpaFactory2_0().buildMetamodelSynchronizer(this) :
				null;
	}

	public IFile getMetamodelFile() {
		return (this.javaPersistentType == null) ? null : this.metamodelSynchronizer.getFile();
	}

	public void initializeMetamodel() {
		// do nothing - probably shouldn't be called...
	}

	public boolean isManaged() {
		return true;
	}

	/**
	 * All <code>orm.xml</code> persistent types must be able to generate a static metamodel
	 * because 1.0 <code>orm.xml</code> files can be referenced from 2.0 persistence.xml files.
	 */
	public void synchronizeMetamodel(Map<String, Collection<MetamodelSourceType>> memberTypeTree) {
		if (this.javaPersistentType != null) {
			this.metamodelSynchronizer.synchronize(memberTypeTree);
		}
	}

	public void printBodySourceOn(BodySourceWriter pw, Map<String, Collection<MetamodelSourceType>> memberTypeTree) {
		if (this.javaPersistentType != null) {
			this.metamodelSynchronizer.printBodySourceOn(pw, memberTypeTree);
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
		for (OrmReadOnlyPersistentAttribute virtualAttribute : this.getVirtualAttributes()) {
			virtualAttribute.dispose();
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


	//*********** refactoring ***********

	public Iterable<DeleteEdit> createDeleteTypeEdits(IType type) {
		return this.isFor(type.getFullyQualifiedName('.')) ?
				new SingleElementIterable<DeleteEdit>(this.mapping.createDeleteEdit()) :
				EmptyIterable.<DeleteEdit>instance();
	}

	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return new CompositeIterable<ReplaceEdit>(
				this.mapping.createRenameTypeEdits(originalType, newName),
				this.createSpecifiedAttributesRenameTypeEdits(originalType, newName)
			);
	}

	protected Iterable<ReplaceEdit> createSpecifiedAttributesRenameTypeEdits(final IType originalType, final String newName) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<OrmPersistentAttribute, Iterable<ReplaceEdit>>(this.getSpecifiedAttributes()) {
				@Override
				protected Iterable<ReplaceEdit> transform(OrmPersistentAttribute persistentAttribute) {
					return persistentAttribute.createRenameTypeEdits(originalType, newName);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return new CompositeIterable<ReplaceEdit>(
				this.mapping.createMoveTypeEdits(originalType, newPackage),
				this.createSpecifiedAttributesMoveTypeEdits(originalType, newPackage)
			);
	}

	protected Iterable<ReplaceEdit> createSpecifiedAttributesMoveTypeEdits(final IType originalType, final IPackageFragment newPackage) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<OrmPersistentAttribute, Iterable<ReplaceEdit>>(this.getSpecifiedAttributes()) {
				@Override
				protected Iterable<ReplaceEdit> transform(OrmPersistentAttribute persistentAttribute) {
					return persistentAttribute.createMoveTypeEdits(originalType, newPackage);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return new CompositeIterable<ReplaceEdit>(
				this.mapping.createRenamePackageEdits(originalPackage, newName),
				this.createSpecifiedAttributesRenamePackageEdits(originalPackage, newName)
			);
	}

	protected Iterable<ReplaceEdit> createSpecifiedAttributesRenamePackageEdits(final IPackageFragment originalPackage, final String newName) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<OrmPersistentAttribute, Iterable<ReplaceEdit>>(this.getSpecifiedAttributes()) {
				@Override
				protected Iterable<ReplaceEdit> transform(OrmPersistentAttribute persistentAttribute) {
					return persistentAttribute.createRenamePackageEdits(originalPackage, newName);
				}
			}
		);
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
			JptJpaCorePlugin.log(t);
		}
	}

	protected void validateAttributes(List<IMessage> messages, IReporter reporter) {
		for (Iterator<OrmReadOnlyPersistentAttribute> stream = this.attributes(); stream.hasNext(); ) {
			this.validateAttribute(stream.next(), messages, reporter);
		}
	}

	protected void validateAttribute(OrmReadOnlyPersistentAttribute attribute, List<IMessage> messages, IReporter reporter) {
		try {
			attribute.validate(messages, reporter);
		} catch(Throwable t) {
			JptJpaCorePlugin.log(t);
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

	protected EntityMappings getEntityMappings() {
		return this.getParent();
	}

	public String getDefaultPackage() {
		return this.getEntityMappings().getPackage();
	}

	public boolean isFor(String typeName) {
		String name = this.getName();
		if (name == null) {
			return false;
		}
		if (name.equals(typeName)) {
			return true;
		}
		String defaultPackage = this.getDefaultPackage();
		if (defaultPackage == null) {
			return false;
		}
		return (defaultPackage + '.' +  name).equals(typeName);
	}

	public boolean isIn(IPackageFragment packageFragment) {
		String packageName = this.getPackageName();
		if (Tools.valuesAreEqual(packageName, packageFragment.getElementName())) {
			return true;
		}
		String defaultPackage = this.getDefaultPackage();
		if (defaultPackage == null) {
			return false;
		}
		packageName = (packageName == null) ? defaultPackage : defaultPackage + '.' + packageName;
		return packageName.equals(packageFragment.getElementName());
	}

	protected String getPackageName() {
		String className = this.getName();
		if (className == null) {
			return null;
		}
		int lastPeriod = className.lastIndexOf('.');
		return (lastPeriod == -1) ? null : className.substring(0, lastPeriod);
	}

	public boolean contains(int textOffset) {
		return this.mapping.containsOffset(textOffset);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}
}
