/*******************************************************************************
 * Copyright (c) 2006, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.resource.xml.EFactoryTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.BodySourceWriter;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.TypeBinding;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.TypeRefactoringParticipant;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedType;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jpa.core.internal.context.java.PropertyAccessor;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelSourceType2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.PersistentType2_0;
import org.eclipse.jpt.jpa.core.resource.orm.Attributes;
import org.eclipse.jpt.jpa.core.resource.orm.OrmPackage;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTypeMapping;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * specified <code>orm.xml</code> persistent type:<ul>
 * <li>mapping
 * <li>access
 * <li>attributes
 * <li>super persistent type
 * <li>Java persistent type
 * </ul>
 */
public abstract class SpecifiedOrmPersistentType
	extends AbstractOrmManagedType<EntityMappings>
	implements OrmPersistentType, PersistentType2_0, JavaPersistentType.Parent
{
	protected OrmTypeMapping mapping;  // never null

	protected AccessType specifiedAccess;
	protected AccessType defaultAccess;  // never null

	protected final Vector<OrmSpecifiedPersistentAttribute> specifiedAttributes = new Vector<>();
	protected final SpecifiedAttributeContainerAdapter specifiedAttributeContainerAdapter = new SpecifiedAttributeContainerAdapter();

	protected final Vector<OrmPersistentAttribute> defaultAttributes = new Vector<>();
	
	protected String declaringTypeName;

	protected final MetamodelSourceType2_0.Synchronizer metamodelSynchronizer;

	protected final Vector<OrmPersistentAttribute> structureChildren = new Vector<>();


	protected SpecifiedOrmPersistentType(EntityMappings parent, XmlTypeMapping xmlTypeMapping) {
		super(parent, xmlTypeMapping);
		this.mapping = this.buildMapping(xmlTypeMapping);
		this.specifiedAccess = this.buildSpecifiedAccess();
		this.defaultAccess = AccessType.FIELD;  // keep this non-null
		this.initializeSpecifiedAttributes();
		this.metamodelSynchronizer = this.buildMetamodelSynchronizer();
		this.initializeStructureChildren();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.mapping.synchronizeWithResourceModel();
		this.setSpecifiedAccess_(this.buildSpecifiedAccess());
		this.syncSpecifiedAttributes();
		this.synchronizeModelsWithResourceModel(this.getDefaultAttributes());
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.mapping.update(monitor);
		this.setDefaultAccess(this.buildDefaultAccess());
		this.updateModels(this.getSpecifiedAttributes(), monitor);
		this.updateDefaultAttributes(monitor);
		this.setDeclaringTypeName(this.buildDeclaringTypeName());
		this.updateStructureChildren();
	}

	@Override
	public XmlTypeMapping getXmlManagedType() {
		return (XmlTypeMapping) super.getXmlManagedType();
	}


	// ********** mapping **********

	public OrmTypeMapping getMapping() {
		return this.mapping;
	}

	public String getMappingKey() {
		return this.mapping.getKey();
	}

	public void setMappingKey(String mappingKey) {
		if (ObjectTools.notEquals(this.getMappingKey(), mappingKey)) {
			this.setMappingKey_(mappingKey);
		}
	}

	protected void setMappingKey_(String mappingKey) {
		OrmTypeMapping old = this.mapping;
		OrmTypeMappingDefinition mappingDefinition = this.getMappingFileDefinition().getTypeMappingDefinition(mappingKey);
		String className = this.getClass_();
		this.xmlManagedType = mappingDefinition.buildResourceMapping(this.getResourceModelFactory());
		this.xmlManagedType.setClassName(className);
		this.mapping = this.buildMapping(this.getXmlTypeMapping());
		this.getEntityMappings().changeMapping(this, old, this.mapping);
		this.firePropertyChanged(MAPPING_PROPERTY, old, this.mapping);
	}

	protected OrmTypeMapping buildMapping(XmlTypeMapping xmlTypeMapping) {
		OrmTypeMappingDefinition md = this.getMappingFileDefinition().getTypeMappingDefinition(xmlTypeMapping.getMappingKey());
		return md.buildContextMapping(this, xmlTypeMapping, this.getContextModelFactory());
	}

	protected XmlTypeMapping getXmlTypeMapping() {
		return this.getXmlManagedType();
	}

	public boolean isMapped() {
		return true;
	}


	// ********** Java persistent type **********

	public JavaPersistentType getJavaPersistentType() {
		return (JavaPersistentType) super.getJavaManagedType();
	}

	@Override
	protected JavaManagedType buildJavaManagedType(JavaResourceType jrt) {
		return jrt != null ? this.getJpaFactory().buildJavaPersistentType(this, jrt) : null;
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
		return AccessType.fromOrmResourceModel(this.getXmlTypeMapping().getAccess(), this.getJpaPlatform(), this.getResourceType());
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
			if (this.getJavaPersistentType() != null) {
				if (this.javaPersistentTypeHasSpecifiedAccess()) {
					return this.getJavaPersistentType().getAccess();
				}
			}
			if (getSuperPersistentType() != null) {
				return getSuperPersistentType().getAccess();
			}
		}
		AccessType access = this.getMappingFileRoot().getAccess();
		return (access != null) ? access : AccessType.FIELD;  // default to FIELD if no specified access found
	}

	/**
	 * pre-condition: {@link #getJavaPersistentType()} is not <code>null</code>
	 */
	protected boolean javaPersistentTypeHasSpecifiedAccess() {
		return (this.getJavaPersistentType().getSpecifiedAccess() != null) ||
				this.getJavaPersistentType().hasAnyAnnotatedAttributes();
	}


	// ********** attributes **********

	@SuppressWarnings("unchecked")
	public ListIterable<OrmPersistentAttribute> getAttributes() {
		return IterableTools.concatenate(this.getReadOnlySpecifiedAttributes(), this.getDefaultAttributes());
	}

	public int getAttributesSize() {
		return this.getSpecifiedAttributesSize() + this.getDefaultAttributesSize();
	}

	public Iterable<String> getAttributeNames() {
		return this.convertToNames(this.getAttributes());
	}

	public OrmPersistentAttribute getAttributeNamed(String attributeName) {
		Iterator<OrmPersistentAttribute> stream = this.getAttributesNamed(attributeName).iterator();
		return stream.hasNext() ? stream.next() : null;
	}

	public Iterable<PersistentAttribute> getAllAttributes() {
		return IterableTools.children(this.getInheritanceHierarchy(), PersistentType.ATTRIBUTES_TRANSFORMER);
	}

	public Iterable<String> getAllAttributeNames() {
		return this.convertToNames(this.getAllAttributes());
	}

	protected Iterable<OrmPersistentAttribute> getAttributesNamed(final String attributeName) {
		return IterableTools.filter(this.getAttributes(), new PersistentAttribute.NameEquals(attributeName));
	}

	public PersistentAttribute resolveAttribute(String attributeName) {
		Iterator<OrmPersistentAttribute> attributes = this.getAttributesNamed(attributeName).iterator();
		if (attributes.hasNext()) {
			OrmPersistentAttribute attribute = attributes.next();
			return attributes.hasNext() ? null /* more than one */: attribute;
		}
		// recurse
		return (getSuperPersistentType() == null) ? null : getSuperPersistentType().resolveAttribute(attributeName);
	}

	protected Iterable<String> convertToNames(Iterable<? extends PersistentAttribute> attributes) {
		return IterableTools.transform(attributes, PersistentAttribute.NAME_TRANSFORMER);
	}
	
	public TypeBinding getAttributeTypeBinding(PersistentAttribute attribute) {
		return (this.getJavaPersistentType() == null) ? null : this.getJavaPersistentType().getAttributeTypeBinding(attribute);
	}

	public void attributeChanged(PersistentAttribute attribute) {
		// NOP
	}


	// ********** attribute conversions **********

	public OrmSpecifiedPersistentAttribute addAttributeToXml(OrmPersistentAttribute defaultAttribute) {
		return this.addAttributeToXml(defaultAttribute, defaultAttribute.getMappingKey());
	}

	public OrmSpecifiedPersistentAttribute addAttributeToXml(OrmPersistentAttribute defaultAttribute, String mappingKey) {
		if ( ! defaultAttribute.isVirtual()) {
			throw new IllegalArgumentException("Attribute is already specified: " + defaultAttribute); //$NON-NLS-1$
		}
		if (mappingKey == null) {
			// this typically happens when the default attribute does not have a mapping
			throw new IllegalArgumentException("Use convertAttributeToSpecified(OrmReadOnlyPersistentAttribute, String) instead and specify a mapping key"); //$NON-NLS-1$
		}
		return this.convertAttributeToSpecified_(defaultAttribute, mappingKey);
	}

	/**
	 * <em>Silently</em> remove the default attribute and add specified
	 * attribute before triggering an <em>update</em> or the dangling
	 * default attribute will be removed preemptively.
	 */
	protected OrmSpecifiedPersistentAttribute convertAttributeToSpecified_(OrmPersistentAttribute defaultAttribute, String mappingKey) {
		// silently remove the default attribute
		int defaultIndex = this.defaultAttributes.indexOf(defaultAttribute);
		this.defaultAttributes.remove(defaultIndex);

		// silently add the specified attribute
		OrmAttributeMappingDefinition md = this.getMappingFileDefinition().getAttributeMappingDefinition(mappingKey);
		XmlAttributeMapping xmlMapping = md.buildResourceMapping(this.getResourceModelFactory());

		OrmSpecifiedPersistentAttribute specifiedAttribute = this.buildSpecifiedAttribute(xmlMapping);
		// we need to add the attribute to the right spot in the list - stupid spec...
		int specifiedIndex = this.getSpecifiedAttributeInsertionIndex(specifiedAttribute);
		this.specifiedAttributes.add(specifiedIndex, specifiedAttribute);

		// this will trigger the initial update;
		// no changes to either collection (default or specified) should be detected at this point
		specifiedAttribute.getMapping().setName(defaultAttribute.getName());

		// fire the list change events
		this.fireItemRemoved(DEFAULT_ATTRIBUTES_LIST, defaultIndex, defaultAttribute);
		this.fireItemAdded(SPECIFIED_ATTRIBUTES_LIST, specifiedIndex, specifiedAttribute);

		// it should be safe to update the XML now
		Attributes xmlAttributes = this.getXmlAttributesForUpdate();
		specifiedAttribute.getMapping().addXmlAttributeMappingTo(xmlAttributes);
		// possibly a NOP, but needed when we trigger the creation of a new 'attributes'
		this.getXmlTypeMapping().setAttributes(xmlAttributes);

		// copy over the specified access(?)
		AccessType oldAccess = defaultAttribute.getJavaPersistentAttribute().getSpecifiedAccess();
		if (oldAccess != null) {
			specifiedAttribute.setSpecifiedAccess(oldAccess);
		}
		return specifiedAttribute;
	}

	protected int getSpecifiedAttributeInsertionIndex(OrmSpecifiedPersistentAttribute attribute) {
		return ListTools.insertionIndexOf(this.specifiedAttributes, attribute, this.getAttributeComparator());
	}

	protected Comparator<OrmSpecifiedPersistentAttribute> getAttributeComparator() {
		return ATTRIBUTE_COMPARATOR;
	}

	protected static final Comparator<OrmSpecifiedPersistentAttribute> ATTRIBUTE_COMPARATOR =
		new Comparator<OrmSpecifiedPersistentAttribute>() {
			public int compare(OrmSpecifiedPersistentAttribute attribute1, OrmSpecifiedPersistentAttribute attribute2) {
				int seq1 = attribute1.getMapping().getXmlSequence();
				int seq2 = attribute2.getMapping().getXmlSequence();
				return (seq1 == seq2) ? 0 : (seq1 < seq2) ? -1 : 1;
			}
		};

	/**
	 * <em>Silently</em> add the new default attribute before removing the
	 * specified attribute, or the <em>update</em> will discover the missing
	 * default attribute and add it preemptively.
	 */
	public OrmPersistentAttribute removeAttributeFromXml(OrmSpecifiedPersistentAttribute specifiedAttribute) {
		if (specifiedAttribute.isVirtual()) {
			throw new IllegalArgumentException("Attribute is not specified: " + specifiedAttribute); //$NON-NLS-1$
		}

		int defaultIndex = this.defaultAttributes.size();
		OrmPersistentAttribute defaultAttribute = this.addDefaultAttribute_(defaultIndex, specifiedAttribute);

		this.removeSpecifiedAttribute(specifiedAttribute);  // trigger update

		if (defaultAttribute != null) {
			this.fireItemAdded(DEFAULT_ATTRIBUTES_LIST, defaultIndex, defaultAttribute);
		}
		return defaultAttribute;
	}

	/**
	 * If necessary, <em>silently</em> add a default attribute for the specified
	 * attribute at the specified index.
	 * Return the newly-added default attribute, <code>null</code>
	 * if nothing was added.
	 * <p>
	 * Make sure the corresponding Java resource attribute exists in the
	 * <em>current</em> type; do <em>not</em> take the Java context attribute
	 * directly from the ORM specified attribute we are converting since it may
	 * have come from a superclass. Instead, use the ORM specified attribute's
	 * Java <em>resource</em> attribute (which will match both name and access
	 * type; but we still need to check its parent type).
	 */
	protected OrmPersistentAttribute addDefaultAttribute_(int defaultIndex, OrmSpecifiedPersistentAttribute specifiedAttribute) {
		OrmPersistentAttribute defaultAttribute = null;
		if (specifiedAttribute.getJavaResourceAttribute() != null) {
			if (specifiedAttribute.getJavaResourceAttribute().getAstNodeType() == AstNodeType.FIELD) {
				JavaResourceField javaResourceField = (JavaResourceField) specifiedAttribute.getJavaResourceAttribute();
				if (this.javaResourceFieldWillBeDefault(javaResourceField, specifiedAttribute)) {
					defaultAttribute = this.buildDefaultAttribute(javaResourceField);
					this.defaultAttributes.add(defaultIndex, defaultAttribute);
				}
			} else {
				PropertyAccessor propertyAccessor = (PropertyAccessor) specifiedAttribute.getJavaPersistentAttribute().getAccessor();
				JavaResourceMethod resourceGetter = propertyAccessor.getResourceGetter();
				JavaResourceMethod resourceSetter = propertyAccessor.getResourceSetter();
				if (this.javaResourcePropertyWillBeDefault(resourceGetter, resourceSetter, specifiedAttribute)) {
					defaultAttribute = this.buildDefaultAttribute(resourceGetter, resourceSetter);
					this.defaultAttributes.add(defaultIndex, defaultAttribute);
				}		
			}
		}
		return defaultAttribute;
	}

	/**
	 * Return whether the specified Java resource attribute will be a
	 * <em>default</em> attribute when the specified specified attribute is
	 * removed from the type. The Java resource attribute must be among the
	 * valid Java resource attributes and it must not correspond to any of the
	 * remaining specified attributes.
	 */
	protected boolean javaResourceFieldWillBeDefault(JavaResourceField javaResourceField, OrmSpecifiedPersistentAttribute specifiedAttributeToBeRemoved) {
		return IterableTools.contains(this.getJavaResourceFields(), javaResourceField) &&
				(this.getSpecifiedAttributeFor(javaResourceField, specifiedAttributeToBeRemoved) == null);
	}

	/**
	 * @see #javaResourceFieldWillBeDefault(JavaResourceField, OrmSpecifiedPersistentAttribute)
	 */
	protected boolean javaResourcePropertyWillBeDefault(JavaResourceMethod javaResourceGetter, JavaResourceMethod javaResourceSetter, OrmSpecifiedPersistentAttribute specifiedAttributeToBeRemoved) {
		return IterableTools.contains(this.getJavaResourceMethods(), javaResourceGetter) &&
				IterableTools.contains(this.getJavaResourceMethods(), javaResourceSetter) &&
				(this.getSpecifiedAttributeFor(javaResourceGetter, javaResourceSetter, specifiedAttributeToBeRemoved) == null);
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
		return EFactoryTools.create(this.getResourceModelFactory(), OrmPackage.eINSTANCE.getAttributes(), Attributes.class);
	}

	protected void removeXmlAttributesIfUnset() {
		if (this.getXmlAttributes().isUnset()) {
			this.getXmlTypeMapping().setAttributes(null);
		}
	}


	// ********** specified attributes **********

	public ListIterable<OrmSpecifiedPersistentAttribute> getSpecifiedAttributes() {
		return IterableTools.cloneLive(this.specifiedAttributes);
	}

	protected ListIterable<OrmPersistentAttribute> getReadOnlySpecifiedAttributes() {
		return new SuperListIterableWrapper<>(this.getSpecifiedAttributes());
	}

	public int getSpecifiedAttributesSize() {
		return this.specifiedAttributes.size();
	}

	protected void removeSpecifiedAttribute(OrmSpecifiedPersistentAttribute attribute) {
		this.removeSpecifiedAttribute_(attribute);
		attribute.getMapping().removeXmlAttributeMappingFrom(this.getXmlAttributes());
		this.removeXmlAttributesIfUnset();
	}

	/**
	 * <em>Silently</em> move the specified attribute before triggering an
	 * <em>update</em> or the inconsistent state will cause problems.
	 * @see org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmPersistentAttribute#setMapping(OrmAttributeMapping)
	 */
	public void changeMapping(OrmSpecifiedPersistentAttribute attribute, OrmAttributeMapping oldMapping, OrmAttributeMapping newMapping) {
		// keep the context model in sync with each change to the resource model
		int sourceIndex = this.specifiedAttributes.indexOf(attribute);
		this.specifiedAttributes.remove(sourceIndex);
		oldMapping.removeXmlAttributeMappingFrom(this.getXmlAttributes());

		int targetIndex = this.getSpecifiedAttributeInsertionIndex(attribute);
		this.specifiedAttributes.add(targetIndex, attribute);
		newMapping.addXmlAttributeMappingTo(this.getXmlAttributes());

		// this will trigger the initial update;
		// no changes to either collection (default or specified) should be detected at this point
		newMapping.setName(oldMapping.getName());

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

	protected OrmSpecifiedPersistentAttribute buildSpecifiedAttribute(XmlAttributeMapping xmlMapping) {
		return this.getContextModelFactory().buildOrmPersistentAttribute(this, xmlMapping);
	}

	protected void syncSpecifiedAttributes() {
		ContextContainerTools.synchronizeWithResourceModel(this.specifiedAttributeContainerAdapter);
	}

	protected void moveSpecifiedAttribute_(int index, OrmSpecifiedPersistentAttribute attribute) {
		this.moveItemInList(index, attribute, this.specifiedAttributes, SPECIFIED_ATTRIBUTES_LIST);
	}

	protected void addSpecifiedAttribute_(int index, XmlAttributeMapping xmlMapping) {
		OrmSpecifiedPersistentAttribute attribute = this.buildSpecifiedAttribute(xmlMapping);
		this.addItemToList(index, attribute, this.specifiedAttributes, SPECIFIED_ATTRIBUTES_LIST);
	}

	protected void removeSpecifiedAttribute_(OrmSpecifiedPersistentAttribute attribute) {
		this.removeItemFromList(attribute, this.specifiedAttributes, SPECIFIED_ATTRIBUTES_LIST);
	}

	/**
	 * specified attribute container adapter
	 */
	protected class SpecifiedAttributeContainerAdapter
		implements ContextContainerTools.Adapter<OrmSpecifiedPersistentAttribute, XmlAttributeMapping>
	{
		public Iterable<OrmSpecifiedPersistentAttribute> getContextElements() {
			return SpecifiedOrmPersistentType.this.getSpecifiedAttributes();
		}
		public Iterable<XmlAttributeMapping> getResourceElements() {
			return SpecifiedOrmPersistentType.this.getXmlAttributeMappings();
		}
		public XmlAttributeMapping getResourceElement(OrmSpecifiedPersistentAttribute contextElement) {
			return contextElement.getMapping().getXmlAttributeMapping();
		}
		public void moveContextElement(int index, OrmSpecifiedPersistentAttribute element) {
			SpecifiedOrmPersistentType.this.moveSpecifiedAttribute_(index, element);
		}
		public void addContextElement(int index, XmlAttributeMapping resourceElement) {
			SpecifiedOrmPersistentType.this.addSpecifiedAttribute_(index, resourceElement);
		}
		public void removeContextElement(OrmSpecifiedPersistentAttribute element) {
			SpecifiedOrmPersistentType.this.removeSpecifiedAttribute_(element);
		}
	}


	// ********** default attributes **********

	public ListIterable<OrmPersistentAttribute> getDefaultAttributes() {
		return IterableTools.cloneLive(this.defaultAttributes);
	}

	public int getDefaultAttributesSize() {
		return this.defaultAttributes.size();
	}

	/**
	 * The attributes are synchronized during the <em>update</em> because
	 * the list of resource attributes is determined by the access type
	 * which can be controlled in a number of different places....
	 */
	protected void updateDefaultAttributes(IProgressMonitor monitor) {
		AccessType at = this.getDefaultJavaAccess();
		if (at == AccessType.FIELD) {
			this.syncFieldAccessDefaultAttributes(monitor);
		}
		else if (at == AccessType.PROPERTY) {
			this.syncPropertyAccessDefaultAttributes(monitor);
		}
	}

	/**
	 * Synchronize the default attributes for {@link AccessType#FIELD}:<ol>
	 * <li>all non-transient, non-static fields
	 * <li>all annotated methods (getters/setters)
	 * </ol>
	 */
	private void syncFieldAccessDefaultAttributes(IProgressMonitor monitor) {
		HashSet<OrmPersistentAttribute> contextAttributes = CollectionTools.hashSet(this.getDefaultAttributes());

		this.syncFieldDefaultAttributes(contextAttributes, this.buildResourceFieldIsRelevant(), monitor);
		if ( ! this.mapping.isMetadataComplete()) {
			this.syncAnnotatedPropertyDefaultAttributes(contextAttributes, monitor);
		}

		// remove any leftover context attributes
		for (OrmPersistentAttribute contextAttribute : contextAttributes) {
			this.removeDefaultAttribute(contextAttribute);
		}
	}

	/**
	 * Synchronize the default attributes for {@link AccessType#PROPERTY}:<ol>
	 * <li>all getter/setter JavaBean pairs
	 * <li>all annotated fields
	 * <li>all annotated getter/setter methods that don't have a matching pair
	 * </ol>
	 */
	private void syncPropertyAccessDefaultAttributes(IProgressMonitor monitor) {
		HashSet<OrmPersistentAttribute> contextAttributes = CollectionTools.hashSet(this.getDefaultAttributes());

		if ( ! this.mapping.isMetadataComplete()) {
			this.syncFieldDefaultAttributes(contextAttributes, JavaResourceAnnotatedElement.IS_ANNOTATED, monitor);
		}

		Collection<JavaResourceMethod> resourceMethods = CollectionTools.hashBag(this.getJavaResourceMethods());
		//iterate through all resource methods searching for persistable getters
		for (JavaResourceMethod getterMethod : this.getJavaResourcePropertyGetters()) {
			JavaResourceMethod setterMethod = JavaResourceMethod.SET_METHOD_TRANSFORMER.transform(getterMethod);
			if (javaResourcePropertyIsDefault(getterMethod, setterMethod)) {
				if (this.methodPairIsProperty(getterMethod, setterMethod)) {
					boolean match = false;
					for (Iterator<OrmPersistentAttribute> stream = contextAttributes.iterator(); stream.hasNext();) {
						OrmPersistentAttribute contextAttribute = stream.next();
						if (contextAttribute.isFor(getterMethod, setterMethod)) {
							match = true;
							contextAttribute.update(monitor);
							stream.remove();
							break;
						}
					}
					if ( ! match) {
						this.addDefaultAttribute(getterMethod, setterMethod);
					}
				}
			}
			resourceMethods.remove(getterMethod);
			resourceMethods.remove(setterMethod);
		}
		this.syncRemainingResourceDefaultMethods(contextAttributes, resourceMethods, monitor);

		// remove any leftover context attributes
		for (OrmPersistentAttribute contextAttribute : contextAttributes) {
			this.removeDefaultAttribute(contextAttribute);
		}
	}

	/**
	 * Return whether the pair of Java methods form a "property" (sorta).
	 * An annotated getter with no setter is still brought into the context
	 * model (for validation purposes)....
	 */
	protected boolean methodPairIsProperty(JavaResourceMethod getterMethod, JavaResourceMethod setterMethod) {
		return (setterMethod != null) || getterMethod.isAnnotated();
	}

	private void syncAnnotatedPropertyDefaultAttributes(HashSet<OrmPersistentAttribute> contextAttributes, IProgressMonitor monitor) {
		Collection<JavaResourceMethod> resourceMethods = CollectionTools.hashBag(this.getJavaResourceMethods());
		//iterate through all resource methods searching for persistable getters
		for (JavaResourceMethod getterMethod : this.getJavaResourcePropertyGetters()) {
			JavaResourceMethod setterMethod = JavaResourceMethod.SET_METHOD_TRANSFORMER.transform(getterMethod);
			if (javaResourcePropertyIsDefault(getterMethod, setterMethod)) {
				if (getterMethod.isAnnotated() || (setterMethod != null && setterMethod.isAnnotated())) {
					boolean match = false;
					for (Iterator<OrmPersistentAttribute> stream = contextAttributes.iterator(); stream.hasNext();) {
						OrmPersistentAttribute contextAttribute = stream.next();
						if (contextAttribute.isFor(getterMethod, setterMethod)) {
							match = true;
							contextAttribute.update(monitor);
							stream.remove();
							break;
						}
					}
					if (!match) {
						this.addDefaultAttribute(getterMethod, setterMethod);
					}
				}
			}
			resourceMethods.remove(getterMethod);
			resourceMethods.remove(setterMethod);
		}
		this.syncRemainingResourceDefaultMethods(contextAttributes, resourceMethods, monitor);
	}

	private void syncFieldDefaultAttributes(HashSet<OrmPersistentAttribute> contextAttributes, Predicate<? super JavaResourceField> filter, IProgressMonitor monitor) {
		for (JavaResourceField resourceField : this.getDefaultJavaResourceFields(filter)) {
			boolean match = false;
			for (Iterator<OrmPersistentAttribute> stream = contextAttributes.iterator(); stream.hasNext(); ) {
				OrmPersistentAttribute contextAttribute = stream.next();
				if (contextAttribute.isFor(resourceField)) {
					match = true;
					contextAttribute.update(monitor);
					stream.remove();
					break;
				}
			}
			if (!match) {
				// added elements are sync'ed during construction or will be
				// updated during the next "update" (which is triggered by
				// their addition to the model)
				this.addDefaultAttribute(resourceField);
			}
		}
	}

	private void syncRemainingResourceDefaultMethods(HashSet<OrmPersistentAttribute> contextAttributes, Collection<JavaResourceMethod> resourceMethods, IProgressMonitor monitor) {
		//iterate through remaining resource methods and search for those that are annotated.
		//all getter methods will already be used.
		for (JavaResourceMethod resourceMethod : resourceMethods) {
			if (resourceMethod.isAnnotated()) {
				boolean match = false;
				//annotated setter(or other random method) with no corresponding getter, bring into context model for validation purposes
				for (Iterator<OrmPersistentAttribute> stream = contextAttributes.iterator(); stream.hasNext();) {
					OrmPersistentAttribute contextAttribute = stream.next();
					if (contextAttribute.isFor(null, resourceMethod)) {
						match = true;
						contextAttribute.update(monitor);
						stream.remove();
						break;
					}
				}
				if (!match) {
					this.addDefaultAttribute(null, resourceMethod);
				}
			}
		}
	}

	/**
	 * Return all the Java resource fields that do not have a 
	 * corresponding <code>orm.xml</code> mapping currently
	 * specified in the <code>orm.xml</code> persistent type.
	 */
	protected Iterable<JavaResourceField> getDefaultJavaResourceFields() {
		return IterableTools.filter(this.getJavaResourceFields(), new JavaResourceFieldIsDefault());
	}

	public class JavaResourceFieldIsDefault
		extends PredicateAdapter<JavaResourceField>
	{
		@Override
		public boolean evaluate(JavaResourceField javaResourceField) {
			return SpecifiedOrmPersistentType.this.javaResourceFieldIsDefault(javaResourceField);
		}
	}

	protected Iterable<JavaResourceField> getJavaResourceFields() {
		JavaResourceType javaResourceType = this.getJavaResourceType();
		if (javaResourceType == null) {
			return EmptyListIterable.instance();
		}
		return javaResourceType.getFields();
	}

	protected Iterable<JavaResourceField> getDefaultJavaResourceFields(Predicate<? super JavaResourceField> filter) {
		return IterableTools.filter(getDefaultJavaResourceFields(), filter);
	}

	protected Iterable<JavaResourceMethod> getJavaResourcePropertyGetters() {
		return this.filterJavaResourceMethods(JavaResourceMethod.IS_PROPERTY_GETTER);
	}

	protected Iterable<JavaResourceMethod> filterJavaResourceMethods(Predicate<JavaResourceMethod> predicate) {
		return IterableTools.filter(this.getJavaResourceMethods(), predicate);
	}

	protected Iterable<JavaResourceMethod> getJavaResourceMethods() {
		JavaResourceType javaResourceType = this.getJavaResourceType();
		return (javaResourceType != null) ? javaResourceType.getMethods() : IterableTools.<JavaResourceMethod>emptyListIterable();
	}

	public Predicate<JavaResourceField> buildResourceFieldIsRelevant() {
		return JavaResourceField.IS_RELEVANT_FOR_FIELD_ACCESS;
	}

	/**
	 * Return the access type that determines which Java attributes are to be
	 * used for the <code>orm.xml</code> type's <em>default</em> attributes.
	 */
	protected AccessType getDefaultJavaAccess() {
		if (this.specifiedAccess != null) {
			return this.specifiedAccess;
		}
		if (this.mapping.isMetadataComplete()) {
			return this.defaultAccess;
		}
		AccessType javaAccess = this.getJavaPersistentType() == null ? null : this.getJavaPersistentType().getSpecifiedAccess();
		return (javaAccess != null) ? javaAccess : this.defaultAccess;
	}

	protected boolean javaResourceFieldIsDefault(JavaResourceField javaResourceField) {
		return this.getSpecifiedAttributeFor(javaResourceField) == null;
	}

	protected OrmSpecifiedPersistentAttribute getSpecifiedAttributeFor(JavaResourceField javaResourceField) {
		return this.getSpecifiedAttributeFor(javaResourceField, null);
	}

	/**
	 * Return the specified attribute corresponding to the specified Java
	 * resource field, ignoring the specified excluded attribute (since
	 * there can be more than one specified attribute per Java resource
	 * attribute; albeit erroneously).
	 */
	protected OrmSpecifiedPersistentAttribute getSpecifiedAttributeFor(JavaResourceField javaResourceField, OrmSpecifiedPersistentAttribute exclude) {
		for (OrmSpecifiedPersistentAttribute ormAttribute : this.getSpecifiedAttributes()) {
			if (ormAttribute == exclude) {
				continue;  // skip
			}
			if (ormAttribute.isFor(javaResourceField)) {
				return ormAttribute;
			}
		}
		return null;
	}

	protected boolean javaResourcePropertyIsDefault(JavaResourceMethod javaResourceGetter, JavaResourceMethod javaResourceSetter) {
		return this.getSpecifiedAttributeFor(javaResourceGetter, javaResourceSetter) == null;
	}

	protected OrmSpecifiedPersistentAttribute getSpecifiedAttributeFor(JavaResourceMethod javaResourceGetter, JavaResourceMethod javaResourceSetter) {
		return this.getSpecifiedAttributeFor(javaResourceGetter, javaResourceSetter, null);
	}

	/**
	 * Return the specified attribute corresponding to the specified Java
	 * resource field, ignoring the specified excluded attribute (since
	 * there can be more than one specified attribute per Java resource
	 * attribute; albeit erroneously).
	 */
	protected OrmSpecifiedPersistentAttribute getSpecifiedAttributeFor(JavaResourceMethod javaResourceGetter, JavaResourceMethod javaResourceSetter, OrmSpecifiedPersistentAttribute exclude) {
		for (OrmSpecifiedPersistentAttribute ormAttribute : this.getSpecifiedAttributes()) {
			if (ormAttribute == exclude) {
				continue;  // skip
			}
			if (ormAttribute.isFor(javaResourceGetter, javaResourceSetter)) {
				return ormAttribute;
			}
		}
		return null;
	}

	protected void moveDefaultAttribute(int index, OrmPersistentAttribute defaultAttribute) {
		this.moveItemInList(index, defaultAttribute, this.defaultAttributes, DEFAULT_ATTRIBUTES_LIST);
	}

	protected void addDefaultAttribute(JavaResourceField javaResourceField) {
		this.addDefaultAttribute(this.buildDefaultAttribute(javaResourceField));
	}

	protected void addDefaultAttribute(JavaResourceMethod javaResourceGetter, JavaResourceMethod javaResourceSetter) {
		this.addDefaultAttribute(this.buildDefaultAttribute(javaResourceGetter, javaResourceSetter));
	}

	protected void addDefaultAttribute(OrmPersistentAttribute defaultAttribute) {
		this.addDefaultAttribute(this.defaultAttributes.size(), defaultAttribute);
	}

	protected void addDefaultAttribute(int index, OrmPersistentAttribute defaultAttribute) {
		this.addItemToList(index, defaultAttribute, this.defaultAttributes, DEFAULT_ATTRIBUTES_LIST);
	}

	protected OrmPersistentAttribute buildDefaultAttribute(JavaResourceField javaResourceField) {
		return this.getContextModelFactory().buildVirtualOrmPersistentField(this, javaResourceField);
	}

	protected OrmPersistentAttribute buildDefaultAttribute(JavaResourceMethod javaResourceGetter, JavaResourceMethod javaResourceSetter) {
		return this.getContextModelFactory().buildVirtualOrmPersistentProperty(this, javaResourceGetter, javaResourceSetter);
	}

	protected void removeDefaultAttribute(OrmPersistentAttribute defaultAttribute) {
		this.removeItemFromList(defaultAttribute, this.defaultAttributes, DEFAULT_ATTRIBUTES_LIST);
	}
	
	
	// ***** inheritance *****
	
	public PersistentType getSuperPersistentType() {
		TypeMapping superTypeMapping = this.mapping.getSuperTypeMapping();
		return (superTypeMapping == null) ? null : superTypeMapping.getPersistentType();
	}
	
	public Iterable<PersistentType> getInheritanceHierarchy() {
		return IterableTools.insert(this, getAncestors());
	}
	
	public Iterable<PersistentType> getAncestors() {
		return IterableTools.transform(getMapping().getAncestors(), TypeMapping.PERSISTENT_TYPE_TRANSFORMER);
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
		return (this.getJavaPersistentType() == null) ?
				null : ((PersistentType2_0) this.getJavaPersistentType()).getDeclaringTypeName();
	}


	// ********** metamodel **********

	protected MetamodelSourceType2_0.Synchronizer buildMetamodelSynchronizer() {
		return this.isJpa2_0Compatible() ?
				this.getJpaFactory2_0().buildMetamodelSynchronizer(this) :
				null;
	}

	public IFile getMetamodelFile() {
		return (this.getJavaPersistentType() == null) ? null : this.metamodelSynchronizer.getFile();
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
	public void synchronizeMetamodel(Map<String, Collection<MetamodelSourceType2_0>> memberTypeTree) {
		if (this.getJavaPersistentType() != null) {
			this.metamodelSynchronizer.synchronize(memberTypeTree);
		}
	}

	public void printBodySourceOn(BodySourceWriter pw, Map<String, Collection<MetamodelSourceType2_0>> memberTypeTree) {
		if (this.getJavaPersistentType() != null) {
			this.metamodelSynchronizer.printBodySourceOn(pw, memberTypeTree);
		}
	}

	public void disposeMetamodel() {
		// do nothing - probably shouldn't be called...
	}

	public PersistentType2_0 getMetamodelType() {
		return this;
	}


	// ********** OrmManagedType implementation **********

	public int getXmlSequence() {
		return this.mapping.getXmlSequence();
	}

	public void addXmlManagedTypeTo(XmlEntityMappings entityMappings) {
		this.mapping.addXmlTypeMappingTo(entityMappings);
	}

	public void removeXmlManagedTypeFrom(XmlEntityMappings entityMappings) {
		this.mapping.removeXmlTypeMappingFrom(entityMappings);
	}


	// ********** JpaStructureNode implementation **********

	public ContextType getContextType() {
		return new ContextType(this);
	}

	public Class<PersistentType> getManagedTypeType() {
		return PersistentType.class;
	}

	public Class<OrmPersistentType> getStructureType() {
		return OrmPersistentType.class;
	}

	public void addRootStructureNodesTo(JpaFile jpaFile, Collection<JpaStructureNode> rootStructureNodes) {
		if (this.getJavaPersistentType() != null) {
			this.getJavaPersistentType().addRootStructureNodesTo(jpaFile, rootStructureNodes);
		}
	}

	protected void initializeStructureChildren() {
		this.structureChildren.addAll(this.specifiedAttributes); // default attributes haven't been built yet
	}

	protected void updateStructureChildren() {
		ArrayList<OrmPersistentAttribute> newChildren = new ArrayList<OrmPersistentAttribute>(this.specifiedAttributes.size() + this.defaultAttributes.size());
		CollectionTools.addAll(newChildren, this.getSpecifiedAttributes());
		CollectionTools.addAll(newChildren, this.getDefaultAttributes());
		this.synchronizeCollection(newChildren, this.structureChildren, STRUCTURE_CHILDREN_COLLECTION);
	}

	public Iterable<OrmPersistentAttribute> getStructureChildren() {
		return IterableTools.cloneLive(this.structureChildren);
	}

	public int getStructureChildrenSize() {
		return this.structureChildren.size();
	}


	public TextRange getSelectionTextRange() {
		return this.mapping.getSelectionTextRange();
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		for (JpaStructureNode child : this.getStructureChildren()) {
			if (child.containsOffset(textOffset)) {
				return child;
			}
		}
		return this;
	}


	// ********** JavaPersistentType.Parent implementation **********

	public AccessType getOverridePersistentTypeAccess() {
		if (this.specifiedAccess != null) {
			return this.specifiedAccess;
		}

		if (getSuperPersistentType() instanceof OrmPersistentType) {
			AccessType accessType = ((OrmPersistentType) getSuperPersistentType()).getSpecifiedAccess();
			if (accessType != null) {
				return accessType;
			}
		}

		if (this.mapping.isMetadataComplete()) {
			AccessType accessType = this.getEntityMappings().getDefaultPersistentTypeAccess();
			if (accessType != null) {
				return accessType;
			}
		}

		// no override access type
		return null;
	}

	public AccessType getDefaultPersistentTypeAccess() {
		if (getSuperPersistentType() instanceof OrmPersistentType) {
			AccessType accessType = ((OrmPersistentType) getSuperPersistentType()).getDefaultAccess();
			if (accessType != null) {
				return accessType;
			}
		}

		return this.getEntityMappings().getDefaultPersistentTypeAccess();
	}

	/**
	 * One of the {@link #javaManagedType} attributes changed.
	 * Forward notification to the default attributes, as one of them
	 * will need to sync with the Java attribute.
	 */
	public void attributeChanged(JavaSpecifiedPersistentAttribute javaAttribute) {
		for (OrmPersistentAttribute attribute : this.getDefaultAttributes()) {
			attribute.javaAttributeChanged(javaAttribute);
		}
	}


	//*********** refactoring ***********

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return IterableTools.concatenate(
				super.createRenameTypeEdits(originalType, newName),
				this.mapping.createRenameTypeEdits(originalType, newName),
				this.createSpecifiedAttributesRenameTypeEdits(originalType, newName)
			);
	}

	protected Iterable<ReplaceEdit> createSpecifiedAttributesRenameTypeEdits(IType originalType, String newName) {
		return IterableTools.children(this.getSpecifiedAttributes(), new TypeRefactoringParticipant.RenameTypeEditsTransformer(originalType, newName));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.concatenate(
				super.createMoveTypeEdits(originalType, newPackage),
				this.mapping.createMoveTypeEdits(originalType, newPackage),
				this.createSpecifiedAttributesMoveTypeEdits(originalType, newPackage)
			);
	}

	protected Iterable<ReplaceEdit> createSpecifiedAttributesMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.children(this.getSpecifiedAttributes(), new TypeRefactoringParticipant.MoveTypeEditsTransformer(originalType, newPackage));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.concatenate(
				super.createRenamePackageEdits(originalPackage, newName),
				this.mapping.createRenamePackageEdits(originalPackage, newName),
				this.createSpecifiedAttributesRenamePackageEdits(originalPackage, newName)
			);
	}

	protected Iterable<ReplaceEdit> createSpecifiedAttributesRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.children(this.getSpecifiedAttributes(), new TypeRefactoringParticipant.RenamePackageEditsTransformer(originalPackage, newName));
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateMapping(messages, reporter);
		this.validateAttributes(messages, reporter);
	}

	protected void validateMapping(List<IMessage> messages, IReporter reporter) {
		try {
			this.mapping.validate(messages, reporter);
		} catch(Throwable t) {
			JptJpaCorePlugin.instance().logError(t);
		}
	}

	protected void validateAttributes(List<IMessage> messages, IReporter reporter) {
		for (OrmPersistentAttribute attribute : this.getAttributes()) {
			this.validateAttribute(attribute, messages, reporter);
		}
	}

	protected void validateAttribute(OrmPersistentAttribute attribute, List<IMessage> messages, IReporter reporter) {
		try {
			attribute.validate(messages, reporter);
		} catch(Throwable t) {
			JptJpaCorePlugin.instance().logError(t);
		}
	}

	// ********** completion proposals **********
	
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.mapping.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		for (OrmSpecifiedPersistentAttribute attribute : this.getSpecifiedAttributes()) {
			result = attribute.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
	}


	// ********** misc **********

	public PersistentType getOverriddenPersistentType() {
		return this.mapping.isMetadataComplete() ? null : this.getJavaPersistentType();
	}
}
