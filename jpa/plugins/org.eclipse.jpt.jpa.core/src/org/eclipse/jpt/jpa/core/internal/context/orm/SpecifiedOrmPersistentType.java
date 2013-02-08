/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.xml.EmfTools;
import org.eclipse.jpt.common.core.utility.BodySourceWriter;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.TypeBinding;
import org.eclipse.jpt.common.utility.filter.Filter;
import org.eclipse.jpt.common.utility.internal.ClassNameTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.filter.FilterAdapter;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.TypeRefactoringParticipant;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.java.PropertyAccessor;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelSourceType;
import org.eclipse.jpt.jpa.core.jpa2.context.PersistentType2_0;
import org.eclipse.jpt.jpa.core.resource.orm.Attributes;
import org.eclipse.jpt.jpa.core.resource.orm.OrmPackage;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.text.edits.DeleteEdit;
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
		extends AbstractOrmXmlContextNode
		implements OrmPersistentType, PersistentType2_0 {
	
	protected OrmTypeMapping mapping;  // never null

	protected String name;

	protected JavaPersistentType javaPersistentType;

	protected AccessType specifiedAccess;
	protected AccessType defaultAccess;  // never null

	protected final Vector<OrmPersistentAttribute> specifiedAttributes = new Vector<OrmPersistentAttribute>();
	protected final SpecifiedAttributeContainerAdapter specifiedAttributeContainerAdapter = new SpecifiedAttributeContainerAdapter();

	protected final Vector<OrmReadOnlyPersistentAttribute> defaultAttributes = new Vector<OrmReadOnlyPersistentAttribute>();

	protected PersistentType superPersistentType;

	protected String declaringTypeName;

	protected final MetamodelSourceType.Synchronizer metamodelSynchronizer;

	protected final Vector<OrmReadOnlyPersistentAttribute> children = new Vector<OrmReadOnlyPersistentAttribute>();


	protected SpecifiedOrmPersistentType(EntityMappings parent, XmlTypeMapping xmlTypeMapping) {
		super(parent);
		this.mapping = this.buildMapping(xmlTypeMapping);
		this.name = this.buildName(); 
		// 'javaPersistentType' is resolved in the update
		this.specifiedAccess = this.buildSpecifiedAccess();
		this.defaultAccess = AccessType.FIELD;  // keep this non-null
		this.initializeSpecifiedAttributes();
		this.metamodelSynchronizer = this.buildMetamodelSynchronizer();
		this.initializeChildren();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.mapping.synchronizeWithResourceModel();
		this.setName(this.buildName());
		this.syncJavaPersistentType();
		this.setSpecifiedAccess_(this.buildSpecifiedAccess());
		this.syncSpecifiedAttributes();
		this.synchronizeNodesWithResourceModel(this.getDefaultAttributes());
	}

	@Override
	public void update() {
		super.update();
		this.mapping.update();
		this.updateJavaPersistentType();
		this.setDefaultAccess(this.buildDefaultAccess());
		this.updateNodes(this.getSpecifiedAttributes());
		this.updateDefaultAttributes();
		this.setSuperPersistentType(this.buildSuperPersistentType());
		this.setDeclaringTypeName(this.buildDeclaringTypeName());
		this.updateChildren();
	}

	public void gatherRootStructureNodes(JpaFile jpaFile, Collection<JpaStructureNode> rootStructureNodes) {
		if (this.javaPersistentType != null) {
			this.javaPersistentType.gatherRootStructureNodes(jpaFile, rootStructureNodes);
		}
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
		return this.name;
	}

	protected void setName(String name) {
		String old = this.name;
		this.name = name;
		if (this.firePropertyChanged(NAME_PROPERTY, old, name)) {
			// clear out the Java persistent type here, it will be rebuilt during "update"
			if (this.javaPersistentType != null) {
				this.javaPersistentType.dispose();
				this.setJavaPersistentType(null);
			}
		}
	}

	protected String buildName() {
		return this.getEntityMappings().qualify(this.getMappingClassName());		
	}

	public String getSimpleName(){
		String className = this.getName();
		return StringTools.isBlank(className) ? null : ClassNameTools.simpleName(className);
	}

	public String getTypeQualifiedName() {
		String className = this.getMappingClassName();
		if (className == null) {
			return null;
		}
		int lastPeriod = className.lastIndexOf('.');
		className = (lastPeriod == -1) ? className : className.substring(lastPeriod + 1);
		className = className.replace('$', '.');
		return className;
	}

	protected String getMappingClassName() {
		return this.mapping.getClass_();
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
	 * If the persistent type's name changes during <em>update</em>, 
	 * the Java persistent type will be cleared out in
	 * {@link #setName(String)}. If we get here and
	 * the Java persistent type is present, we can
	 * <em>sync</em> it. In some circumstances it will be obsolete
	 * since the name is changed during update (the mapping class name or
	 * the entity mapping's package affect the name)
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
		if (this.getName() == null) {
			if (this.javaPersistentType != null) {
				this.javaPersistentType.dispose();
				this.setJavaPersistentType(null);
			}			
		}
		else {
			JavaResourceType resourceType = this.resolveJavaResourceType();
			if (this.javaPersistentType == null) {
				this.setJavaPersistentType(this.buildJavaPersistentType(resourceType));
			}
			else {
				// bug 379051 using == here because it is possible that the names are the same, 
				// but the location has changed: the java resource type has moved from "external" 
				// to part of the jpa project's jpa files. 
				if (this.javaPersistentType.getJavaResourceType() == resourceType) {
					this.javaPersistentType.update();
				} else {
					this.javaPersistentType.dispose();
					this.setJavaPersistentType(this.buildJavaPersistentType(resourceType));
				}
			}
		}
	}

	/**
	 * Return null it's an enum; don't build a JavaPersistentType
	 * @see #updateJavaPersistentType()
	 */
	protected JavaResourceType resolveJavaResourceType() {
		if (this.name == null) {
			return null;
		}
		return (JavaResourceType) this.getJpaProject().getJavaResourceType(this.name, AstNodeType.TYPE);
	}

	protected JavaPersistentType buildJavaPersistentType(JavaResourceType jrt) {
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
			if (this.javaPersistentType != null) {
				if (this.javaPersistentTypeHasSpecifiedAccess()) {
					return this.javaPersistentType.getAccess();
				}
			}
			if (this.superPersistentType != null) {
				return this.superPersistentType.getAccess();
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

	@SuppressWarnings("unchecked")
	public ListIterable<OrmReadOnlyPersistentAttribute> getAttributes() {
		return IterableTools.concatenate(this.getReadOnlySpecifiedAttributes(), this.getDefaultAttributes());
	}

	public int getAttributesSize() {
		return this.getSpecifiedAttributesSize() + this.getDefaultAttributesSize();
	}

	public Iterable<String> getAttributeNames() {
		return this.convertToNames(this.getAttributes());
	}

	public OrmReadOnlyPersistentAttribute getAttributeNamed(String attributeName) {
		Iterator<OrmReadOnlyPersistentAttribute> stream = this.getAttributesNamed(attributeName).iterator();
		return stream.hasNext() ? stream.next() : null;
	}

	public Iterable<ReadOnlyPersistentAttribute> getAllAttributes() {
		return IterableTools.children(this.getInheritanceHierarchy(), PersistentType.ATTRIBUTES_TRANSFORMER);
	}

	public Iterable<String> getAllAttributeNames() {
		return this.convertToNames(this.getAllAttributes());
	}

	protected Iterable<OrmReadOnlyPersistentAttribute> getAttributesNamed(final String attributeName) {
		return IterableTools.filter(this.getAttributes(), new ReadOnlyPersistentAttribute.NameEquals(attributeName));
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
		return IterableTools.transform(attributes, ReadOnlyPersistentAttribute.NAME_TRANSFORMER);
	}
	
	public TypeBinding getAttributeTypeBinding(ReadOnlyPersistentAttribute attribute) {
		return (this.javaPersistentType == null) ? null : this.javaPersistentType.getAttributeTypeBinding(attribute);
	}
	
	
	// ********** attribute conversions **********

	public OrmPersistentAttribute addAttributeToXml(OrmReadOnlyPersistentAttribute defaultAttribute) {
		return this.addAttributeToXml(defaultAttribute, defaultAttribute.getMappingKey());
	}

	public OrmPersistentAttribute addAttributeToXml(OrmReadOnlyPersistentAttribute defaultAttribute, String mappingKey) {
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
	protected OrmPersistentAttribute convertAttributeToSpecified_(OrmReadOnlyPersistentAttribute defaultAttribute, String mappingKey) {
		// silently remove the default attribute
		int defaultIndex = this.defaultAttributes.indexOf(defaultAttribute);
		this.defaultAttributes.remove(defaultIndex);
		defaultAttribute.dispose();

		// silently add the specified attribute
		OrmAttributeMappingDefinition md = this.getMappingFileDefinition().getAttributeMappingDefinition(mappingKey);
		XmlAttributeMapping xmlMapping = md.buildResourceMapping(this.getResourceNodeFactory());

		OrmPersistentAttribute specifiedAttribute = this.buildSpecifiedAttribute(xmlMapping);
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

	protected int getSpecifiedAttributeInsertionIndex(OrmPersistentAttribute attribute) {
		return ListTools.insertionIndexOf(this.specifiedAttributes, attribute, this.getAttributeComparator());
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
	 * <em>Silently</em> add the new default attribute before removing the
	 * specified attribute, or the <em>update</em> will discover the missing
	 * default attribute and add it preemptively.
	 */
	public OrmReadOnlyPersistentAttribute removeAttributeFromXml(OrmPersistentAttribute specifiedAttribute) {
		if (specifiedAttribute.isVirtual()) {
			throw new IllegalArgumentException("Attribute is not specified: " + specifiedAttribute); //$NON-NLS-1$
		}

		int defaultIndex = this.defaultAttributes.size();
		OrmReadOnlyPersistentAttribute defaultAttribute = null;
		// make sure the corresponding resource Java attribute actually exists in the *current* type;
		// do *not* take the context Java attribute directly from the specified ORM
		// attribute we are converting since it may have come from a superclass;
		// instead, use its resource Java attribute (which will match both name and access type,
		// but we still need to check its parent type)
		if (specifiedAttribute.getJavaResourceAttribute() != null) {
			if (specifiedAttribute.getJavaResourceAttribute().getAstNodeType() == AstNodeType.FIELD) {
				JavaResourceField javaResourceField = (JavaResourceField) specifiedAttribute.getJavaResourceAttribute();
				if (this.javaResourceFieldWillBeDefault(javaResourceField, specifiedAttribute)) {
					defaultAttribute = this.buildDefaultAttribute(javaResourceField);
					this.defaultAttributes.add(defaultIndex, defaultAttribute);
				}
			}
			else {
				PropertyAccessor propertyAccessor = (PropertyAccessor) specifiedAttribute.getJavaPersistentAttribute().getAccessor();
				JavaResourceMethod resourceGetter = propertyAccessor.getResourceGetter();
				JavaResourceMethod resourceSetter = propertyAccessor.getResourceSetter();
				
				if (this.javaResourcePropertyWillBeDefault(resourceGetter, resourceSetter, specifiedAttribute)) {
					defaultAttribute = this.buildVirtualAttribute(resourceGetter, resourceSetter);
					this.defaultAttributes.add(defaultIndex, defaultAttribute);
				}		
			}
		}

		this.removeSpecifiedAttribute(specifiedAttribute);  // trigger update

		if (defaultAttribute != null) {
			this.fireItemAdded(DEFAULT_ATTRIBUTES_LIST, defaultIndex, defaultAttribute);
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
	protected boolean javaResourceFieldWillBeDefault(JavaResourceField javaResourceField, OrmPersistentAttribute specifiedAttributeToBeRemoved) {
		return IterableTools.contains(this.getJavaResourceFields(), javaResourceField) &&
				(this.getSpecifiedAttributeFor(javaResourceField, specifiedAttributeToBeRemoved) == null);
	}

	/**
	 * Return whether the specified Java resource attribute will be a
	 * <em>default</em> attribute when the specified specified attribute is
	 * removed from the type. The Java resource attribute must be among the
	 * valid Java resource attributes and it must not correspond to any of the
	 * remaining specified attributes.
	 */
	protected boolean javaResourcePropertyWillBeDefault(JavaResourceMethod javaResourceGetter, JavaResourceMethod javaResourceSetter, OrmPersistentAttribute specifiedAttributeToBeRemoved) {
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
		return EmfTools.create(this.getResourceNodeFactory(), OrmPackage.eINSTANCE.getAttributes(), Attributes.class);
	}

	protected void removeXmlAttributesIfUnset() {
		if (this.getXmlAttributes().isUnset()) {
			this.getXmlTypeMapping().setAttributes(null);
		}
	}


	// ********** specified attributes **********

	public ListIterable<OrmPersistentAttribute> getSpecifiedAttributes() {
		return IterableTools.cloneLive(this.specifiedAttributes);
	}

	protected ListIterable<OrmReadOnlyPersistentAttribute> getReadOnlySpecifiedAttributes() {
		return new SuperListIterableWrapper<OrmReadOnlyPersistentAttribute>(this.getSpecifiedAttributes());
	}

	public int getSpecifiedAttributesSize() {
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
			return SpecifiedOrmPersistentType.this.getSpecifiedAttributes();
		}
		public Iterable<XmlAttributeMapping> getResourceElements() {
			return SpecifiedOrmPersistentType.this.getXmlAttributeMappings();
		}
		public XmlAttributeMapping getResourceElement(OrmPersistentAttribute contextElement) {
			return contextElement.getMapping().getXmlAttributeMapping();
		}
		public void moveContextElement(int index, OrmPersistentAttribute element) {
			SpecifiedOrmPersistentType.this.moveSpecifiedAttribute_(index, element);
		}
		public void addContextElement(int index, XmlAttributeMapping resourceElement) {
			SpecifiedOrmPersistentType.this.addSpecifiedAttribute_(index, resourceElement);
		}
		public void removeContextElement(OrmPersistentAttribute element) {
			SpecifiedOrmPersistentType.this.removeSpecifiedAttribute_(element);
		}
	}


	// ********** default attributes **********

	public ListIterable<OrmReadOnlyPersistentAttribute> getDefaultAttributes() {
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
	protected void updateDefaultAttributes() {
		if (getDefaultJavaAccess() == AccessType.FIELD) {
			this.syncFieldAccessDefaultAttributes();
		}
		else if (getDefaultJavaAccess() == AccessType.PROPERTY) {
			this.syncPropertyAccessDefaultAttributes();
		}
	}

	/**
	 * Initialize the attributes for AccessType.FIELD
	 * 1. all non-transient, non-static fields
	 * 2. all annotated methods(getters/setters)
	 */
	private void syncFieldAccessDefaultAttributes() {
		HashSet<OrmReadOnlyPersistentAttribute> contextAttributes = CollectionTools.set(this.getDefaultAttributes());

		this.syncFieldDefaultAttributes(contextAttributes, buildNonTransientNonStaticResourceFieldsFilter());
		if (!getMapping().isMetadataComplete()) {
			this.syncAnnotatedPropertyDefaultAttributes(contextAttributes);
		}

		// remove any leftover context attributes
		for (OrmReadOnlyPersistentAttribute contextAttribute : contextAttributes) {
			this.removeDefaultAttribute(contextAttribute);
		}
	}

	/**
	 * Initialize the attributes for XmlAccessType.PROPERTY
	 * 1. all getter/setter javabeans pairs
	 * 2. all annotated fields
	 * 3. all annotated methods getters/setters that don't have a matching pair
	 */
	private void syncPropertyAccessDefaultAttributes() {
		HashSet<OrmReadOnlyPersistentAttribute> contextAttributes = CollectionTools.set(this.getDefaultAttributes());

		if (!getMapping().isMetadataComplete()) {
			this.syncFieldDefaultAttributes(contextAttributes, AbstractJavaPersistentType.ANNOTATED_RESOURCE_FIELDS_FILTER);
		}

		Collection<JavaResourceMethod> resourceMethods = CollectionTools.collection(this.getJavaResourceMethods());
		//iterate through all resource methods searching for persistable getters
		for (JavaResourceMethod getterMethod : this.getJavaResourceMethods(this.buildPersistablePropertyGetterMethodsFilter())) {
			JavaResourceMethod setterMethod = AbstractJavaPersistentType.getValidSiblingSetMethod(getterMethod, resourceMethods);
			if (javaResourcePropertyIsDefault(getterMethod, setterMethod)) {
				if (AbstractJavaPersistentType.methodsArePersistableProperties(getterMethod, setterMethod)) {
					boolean match = false;
					for (Iterator<OrmReadOnlyPersistentAttribute> stream = contextAttributes.iterator(); stream.hasNext();) {
						OrmReadOnlyPersistentAttribute contextAttribute = stream.next();
						if (contextAttribute.isFor(getterMethod, setterMethod)) {
							match = true;
							contextAttribute.update();
							stream.remove();
							break;
						}
					}
					if (!match) {
						this.addDefaultAttribute(getDefaultAttributesSize(), this.buildVirtualAttribute(getterMethod, setterMethod));
					}
				}
			}
			resourceMethods.remove(getterMethod);
			resourceMethods.remove(setterMethod);
		}
		this.syncRemainingResourceDefaultMethods(contextAttributes, resourceMethods);

		// remove any leftover context attributes
		for (OrmReadOnlyPersistentAttribute contextAttribute : contextAttributes) {
			this.removeDefaultAttribute(contextAttribute);
		}
	}

	private void syncAnnotatedPropertyDefaultAttributes(HashSet<OrmReadOnlyPersistentAttribute> contextAttributes) {
		Collection<JavaResourceMethod> resourceMethods = CollectionTools.collection(this.getJavaResourceMethods());
		//iterate through all resource methods searching for persistable getters
		for (JavaResourceMethod getterMethod : this.getJavaResourceMethods(buildPersistablePropertyGetterMethodsFilter())) {
			JavaResourceMethod setterMethod = AbstractJavaPersistentType.getValidSiblingSetMethod(getterMethod, resourceMethods);
			if (javaResourcePropertyIsDefault(getterMethod, setterMethod)) {
				if (getterMethod.isAnnotated() || (setterMethod != null && setterMethod.isAnnotated())) {
					boolean match = false;
					for (Iterator<OrmReadOnlyPersistentAttribute> stream = contextAttributes.iterator(); stream.hasNext();) {
						OrmReadOnlyPersistentAttribute contextAttribute = stream.next();
						if (contextAttribute.isFor(getterMethod, setterMethod)) {
							match = true;
							contextAttribute.update();
							stream.remove();
							break;
						}
					}
					if (!match) {
						this.addDefaultAttribute(getDefaultAttributesSize(), this.buildVirtualAttribute(getterMethod, setterMethod));
					}
				}
			}
			resourceMethods.remove(getterMethod);
			resourceMethods.remove(setterMethod);
		}
		this.syncRemainingResourceDefaultMethods(contextAttributes, resourceMethods);
	}

	private void syncFieldDefaultAttributes(HashSet<OrmReadOnlyPersistentAttribute> contextAttributes, Filter<JavaResourceField> filter) {
		for (JavaResourceField resourceField : this.getDefaultJavaResourceFields(filter)) {
			boolean match = false;
			for (Iterator<OrmReadOnlyPersistentAttribute> stream = contextAttributes.iterator(); stream.hasNext(); ) {
				OrmReadOnlyPersistentAttribute contextAttribute = stream.next();
				if (contextAttribute.isFor(resourceField)) {
					match = true;
					contextAttribute.update();
					stream.remove();
					break;
				}
			}
			if (!match) {
				// added elements are sync'ed during construction or will be
				// updated during the next "update" (which is triggered by
				// their addition to the model)
				this.addDefaultAttribute(this.getDefaultAttributesSize(), this.buildDefaultAttribute(resourceField));
			}
		}
	}

	private void syncRemainingResourceDefaultMethods(HashSet<OrmReadOnlyPersistentAttribute> contextAttributes, Collection<JavaResourceMethod> resourceMethods) {
		//iterate through remaining resource methods and search for those that are annotated.
		//all getter methods will already be used.
		for (JavaResourceMethod resourceMethod : resourceMethods) {
			if (resourceMethod.isAnnotated()) {
				boolean match = false;
				//annotated setter(or other random method) with no corresponding getter, bring into context model for validation purposes
				for (Iterator<OrmReadOnlyPersistentAttribute> stream = contextAttributes.iterator(); stream.hasNext();) {
					OrmReadOnlyPersistentAttribute contextAttribute = stream.next();
					if (contextAttribute.isFor(null, resourceMethod)) {
						match = true;
						contextAttribute.update();
						stream.remove();
						break;
					}
				}
				if (!match) {
					this.addDefaultAttribute(getDefaultAttributesSize(), this.buildVirtualAttribute(null, resourceMethod));
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
		extends FilterAdapter<JavaResourceField>
	{
		@Override
		public boolean accept(JavaResourceField javaResourceField) {
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

	protected Iterable<JavaResourceField> getDefaultJavaResourceFields(Filter<JavaResourceField> filter) {
		return IterableTools.filter(getDefaultJavaResourceFields(), filter);
	}

	protected Iterable<JavaResourceMethod> getJavaResourceMethods(Filter<JavaResourceMethod> filter) {
		return IterableTools.filter(getJavaResourceMethods(), filter);
	}

	protected Iterable<JavaResourceMethod> getJavaResourceMethods() {
		JavaResourceType javaResourceType = this.getJavaResourceType();
		if (javaResourceType == null) {
			return EmptyListIterable.instance();
		}
		return javaResourceType.getMethods();
	}

	public static Filter<JavaResourceField> buildNonTransientNonStaticResourceFieldsFilter() {
		return AbstractJavaPersistentType.buildNonTransientNonStaticResourceFieldsFilter();
	}

	protected Filter<JavaResourceMethod> buildPersistablePropertyGetterMethodsFilter() {
		return new Filter<JavaResourceMethod>() {
			public boolean accept(JavaResourceMethod resourceMethod) {
				return AbstractJavaPersistentType.methodIsPersistablePropertyGetter(resourceMethod, getJavaResourceMethods());
			}
		};
	}

	protected JavaResourceType getJavaResourceType() {
		return (this.javaPersistentType == null) ? null : this.javaPersistentType.getJavaResourceType();
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
		AccessType javaAccess = this.javaPersistentType == null ? null : this.javaPersistentType.getSpecifiedAccess();
		return (javaAccess != null) ? javaAccess : this.defaultAccess;
	}

	protected boolean javaResourceFieldIsDefault(JavaResourceField javaResourceField) {
		return this.getSpecifiedAttributeFor(javaResourceField) == null;
	}

	protected OrmPersistentAttribute getSpecifiedAttributeFor(JavaResourceField javaResourceField) {
		return this.getSpecifiedAttributeFor(javaResourceField, null);
	}

	/**
	 * Return the specified attribute corresponding to the specified Java
	 * resource field, ignoring the specified excluded attribute (since
	 * there can be more than one specified attribute per Java resource
	 * attribute; albeit erroneously).
	 */
	protected OrmPersistentAttribute getSpecifiedAttributeFor(JavaResourceField javaResourceField, OrmPersistentAttribute exclude) {
		for (OrmPersistentAttribute ormAttribute : this.getSpecifiedAttributes()) {
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

	protected OrmPersistentAttribute getSpecifiedAttributeFor(JavaResourceMethod javaResourceGetter, JavaResourceMethod javaResourceSetter) {
		return this.getSpecifiedAttributeFor(javaResourceGetter, javaResourceSetter, null);
	}

	/**
	 * Return the specified attribute corresponding to the specified Java
	 * resource field, ignoring the specified excluded attribute (since
	 * there can be more than one specified attribute per Java resource
	 * attribute; albeit erroneously).
	 */
	protected OrmPersistentAttribute getSpecifiedAttributeFor(JavaResourceMethod javaResourceGetter, JavaResourceMethod javaResourceSetter, OrmPersistentAttribute exclude) {
		for (OrmPersistentAttribute ormAttribute : this.getSpecifiedAttributes()) {
			if (ormAttribute == exclude) {
				continue;  // skip
			}
			if (ormAttribute.isFor(javaResourceGetter, javaResourceSetter)) {
				return ormAttribute;
			}
		}
		return null;
	}

	protected void moveDefaultAttribute(int index, OrmReadOnlyPersistentAttribute defaultAttribute) {
		this.moveItemInList(index, defaultAttribute, this.defaultAttributes, DEFAULT_ATTRIBUTES_LIST);
	}

	protected void addDefaultAttribute(int index, OrmReadOnlyPersistentAttribute defaultAttribute) {
		this.addItemToList(index, defaultAttribute, this.defaultAttributes, DEFAULT_ATTRIBUTES_LIST);
	}

	protected OrmReadOnlyPersistentAttribute buildDefaultAttribute(JavaResourceField javaResourceField) {
		return this.getContextNodeFactory().buildVirtualOrmPersistentField(this, javaResourceField);
	}

	protected OrmReadOnlyPersistentAttribute buildVirtualAttribute(JavaResourceMethod javaResourceGetter, JavaResourceMethod javaResourceSetter) {
		return this.getContextNodeFactory().buildVirtualOrmPersistentProperty(this, javaResourceGetter, javaResourceSetter);
	}

	protected void removeDefaultAttribute(OrmReadOnlyPersistentAttribute defaultAttribute) {
		defaultAttribute.dispose();
		this.removeItemFromList(defaultAttribute, this.defaultAttributes, DEFAULT_ATTRIBUTES_LIST);
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
		return IterableTools.contains(spt.getInheritanceHierarchy(), this) ? null : spt;
	}

	protected PersistentType buildSuperPersistentType_() {
		return (this.javaPersistentType == null) ? null : this.javaPersistentType.getSuperPersistentType();
	}


	// ********** inheritance **********

	public Iterable<PersistentType> getInheritanceHierarchy() {
		return this.buildInheritanceHierarchy(this);
	}

	public Iterable<PersistentType> getAncestors() {
		return this.buildInheritanceHierarchy(this.superPersistentType);
	}

	protected Iterable<PersistentType> buildInheritanceHierarchy(PersistentType start) {
		// using a chain iterable to traverse up the inheritance tree
		return ObjectTools.chain(start, SUPER_PERSISTENT_TYPE_TRANSFORMER);
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
				null : ((PersistentType2_0) this.javaPersistentType).getDeclaringTypeName();
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

	public PersistentType2_0 getMetamodelType() {
		return this;
	}


	// ********** JpaStructureNode implementation **********

	public ContextType getContextType() {
		return new ContextType(this);
	}

	public Class<OrmPersistentType> getType() {
		return OrmPersistentType.class;
	}

	protected void initializeChildren() {
		this.children.addAll(this.specifiedAttributes); //defaultAttributes haven't been built yet
	}

	protected void updateChildren() {
		Vector<OrmReadOnlyPersistentAttribute> newChildren = new Vector<OrmReadOnlyPersistentAttribute>();
		newChildren.addAll(this.specifiedAttributes);
		newChildren.addAll(this.defaultAttributes);
		this.synchronizeCollection(newChildren, this.children, CHILDREN_COLLECTION);
	}

	public Iterable<OrmReadOnlyPersistentAttribute> getChildren() {
		return IterableTools.cloneLive(this.children);
	}

	public int getChildrenSize() {
		return this.children.size();
	}

	public TextRange getFullTextRange() {
		return this.getXmlTypeMapping().getFullTextRange();
	}

	public boolean containsOffset(int textOffset) {
		return this.getXmlTypeMapping().containsOffset(textOffset);
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		for (JpaStructureNode child : this.getChildren()) {
			if (child.containsOffset(textOffset)) {
				return child;
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
		for (OrmReadOnlyPersistentAttribute defaultAttribute : this.getDefaultAttributes()) {
			defaultAttribute.dispose();
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
				IterableTools.singletonIterable(this.mapping.createDeleteEdit()) :
				IterableTools.<DeleteEdit>emptyIterable();
	}

	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return IterableTools.concatenate(
				this.mapping.createRenameTypeEdits(originalType, newName),
				this.createSpecifiedAttributesRenameTypeEdits(originalType, newName)
			);
	}

	protected Iterable<ReplaceEdit> createSpecifiedAttributesRenameTypeEdits(IType originalType, String newName) {
		return IterableTools.children(this.getSpecifiedAttributes(), new TypeRefactoringParticipant.RenameTypeEditsTransformer(originalType, newName));
	}

	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.concatenate(
				this.mapping.createMoveTypeEdits(originalType, newPackage),
				this.createSpecifiedAttributesMoveTypeEdits(originalType, newPackage)
			);
	}

	protected Iterable<ReplaceEdit> createSpecifiedAttributesMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.children(this.getSpecifiedAttributes(), new TypeRefactoringParticipant.MoveTypeEditsTransformer(originalType, newPackage));
	}

	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.concatenate(
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
		this.validateClass(messages);
		this.validateMapping(messages, reporter);
		this.validateAttributes(messages, reporter);
	}

	protected void validateClass(List<IMessage> messages) {
		if (this.javaPersistentType == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JptJpaCoreValidationMessages.PERSISTENT_TYPE_UNRESOLVED_CLASS,
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
			JptJpaCorePlugin.instance().logError(t);
		}
	}

	protected void validateAttributes(List<IMessage> messages, IReporter reporter) {
		for (OrmReadOnlyPersistentAttribute attribute : this.getAttributes()) {
			this.validateAttribute(attribute, messages, reporter);
		}
	}

	protected void validateAttribute(OrmReadOnlyPersistentAttribute attribute, List<IMessage> messages, IReporter reporter) {
		try {
			attribute.validate(messages, reporter);
		} catch(Throwable t) {
			JptJpaCorePlugin.instance().logError(t);
		}
	}

	public TextRange getValidationTextRange() {
		return this.mapping.getValidationTextRange();
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
		for (OrmPersistentAttribute attribute : this.getSpecifiedAttributes()) {
			result = attribute.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
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
		return this.getEntityMappings().getDefaultPersistentTypePackage();
	}

	public boolean isFor(String typeName) {
		return ObjectTools.equals(typeName, this.getName());
	}

	public boolean isIn(IPackageFragment packageFragment) {
		String packageName = this.getPackageName();
		if (ObjectTools.equals(packageName, packageFragment.getElementName())) {
			return true;
		}
		return false;
	}

	protected String getPackageName() {
		String className = this.getMappingClassName();
		if (className == null) {
			return null;
		}
		int lastPeriod = className.lastIndexOf('.');
		return (lastPeriod == -1) ? this.getDefaultPackage() : className.substring(0, lastPeriod);
	}

	public PersistentType getOverriddenPersistentType() {
		return this.mapping.isMetadataComplete() ? null : this.javaPersistentType;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}
}
