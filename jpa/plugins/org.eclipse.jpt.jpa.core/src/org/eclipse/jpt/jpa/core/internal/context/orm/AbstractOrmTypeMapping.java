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

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.JavaProjectTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.IdTypeMapping;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.SpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.TypeMappingTools;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.GenericTypeMappingValidator;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> type mapping
 */
public abstract class AbstractOrmTypeMapping<X extends XmlTypeMapping>
		extends AbstractOrmXmlContextModel<OrmPersistentType>
		implements OrmTypeMapping {
	
	// never null
	protected final X xmlTypeMapping;
	
	protected Boolean specifiedMetadataComplete;
	protected boolean overrideMetadataComplete;
	
	protected String specifiedParentClass;
	protected String defaultParentClass;
	protected String fullyQualifiedParentClass;
	
	protected AbstractOrmTypeMapping(OrmPersistentType parent, X xmlTypeMapping) {
		super(parent);
		this.xmlTypeMapping = xmlTypeMapping;
		this.specifiedMetadataComplete = xmlTypeMapping.getMetadataComplete();
		this.specifiedParentClass = this.buildSpecifiedParentClass();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		setSpecifiedMetadataComplete_(xmlTypeMapping.getMetadataComplete());
		setSpecifiedParentClass_(buildSpecifiedParentClass());
	}
	
	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		setOverrideMetadataComplete(buildOverrideMetadataComplete());
		setDefaultParentClass(buildDefaultParentClass());
		setFullyQualifiedParentClass(buildFullyQualifiedParentClass());
	}
	
	
	// ********** metadata complete **********

	/**
	 * If <code>entity-mappings/persistence-unit-metadata/xml-mapping-metadata-complete</code>
	 * is specified, it overrides anything set here.
	 */
	public boolean isMetadataComplete() {
		if (this.overrideMetadataComplete) {
			return true;
		}
		return (this.specifiedMetadataComplete != null) ? this.specifiedMetadataComplete.booleanValue() : false;
	}

	public Boolean getSpecifiedMetadataComplete() {
		return this.specifiedMetadataComplete;
	}

	public void setSpecifiedMetadataComplete(Boolean metadataComplete) {
		this.setSpecifiedMetadataComplete_(metadataComplete);
		this.xmlTypeMapping.setMetadataComplete(metadataComplete);
	}

	protected void setSpecifiedMetadataComplete_(Boolean metadataComplete) {
		Boolean old = this.specifiedMetadataComplete;
		this.specifiedMetadataComplete = metadataComplete;
		this.firePropertyChanged(SPECIFIED_METADATA_COMPLETE_PROPERTY, old, metadataComplete);
	}

	public boolean isOverrideMetadataComplete() {
		return this.overrideMetadataComplete;
	}

	protected void setOverrideMetadataComplete(boolean metadataComplete) {
		boolean old = this.overrideMetadataComplete;
		this.overrideMetadataComplete = metadataComplete;
		this.firePropertyChanged(OVERRIDE_METADATA_COMPLETE_PROPERTY, old, metadataComplete);
	}

	protected boolean buildOverrideMetadataComplete() {
		return this.getPersistenceUnit().isXmlMappingMetadataComplete();
	}


	// ********** fully-qualified parent class **********

	public String getFullyQualifiedParentClass() {
		return this.fullyQualifiedParentClass;
	}

	protected void setFullyQualifiedParentClass(String parentClass) {
		String old = this.fullyQualifiedParentClass;
		this.fullyQualifiedParentClass = parentClass;
		this.firePropertyChanged(FULLY_QUALIFIED_PARENT_CLASS_PROPERTY, old, parentClass);
	}

	protected String buildFullyQualifiedParentClass() {
		return (this.specifiedParentClass == null) ?
				this.defaultParentClass :
				this.getEntityMappings().qualify(this.specifiedParentClass);
	}

	// ********** parent class **********

	public String getParentClass() {
		return (this.specifiedParentClass != null) ? this.specifiedParentClass : this.defaultParentClass;
	}

	public String getSpecifiedParentClass() {
		return this.specifiedParentClass;
	}

	public void setSpecifiedParentClass(String parentClass) {
		this.setSpecifiedParentClass_(parentClass);
		this.setSpecifiedParentClassInXml(parentClass);
	}

	protected void setSpecifiedParentClass_(String parentClass) {
		String old = this.specifiedParentClass;
		this.specifiedParentClass = parentClass;
		this.firePropertyChanged(SPECIFIED_PARENT_CLASS_PROPERTY, old, parentClass);
	}

	/**
	 * subclasses must override if they support specifying a parent class
	 */
	protected void setSpecifiedParentClassInXml(@SuppressWarnings("unused") String parentClass) {
		// NOP
	}

	/**
	 * subclasses must override if they support specifying a parent class
	 */
	protected String buildSpecifiedParentClass() {
		return null;
	}

	public String getDefaultParentClass() {
		return this.defaultParentClass;
	}

	protected void setDefaultParentClass(String parentClass) {
		String old = this.defaultParentClass;
		this.defaultParentClass = parentClass;
		this.firePropertyChanged(DEFAULT_PARENT_CLASS_PROPERTY, old, parentClass);
	}

	protected String buildDefaultParentClass() {
		JavaResourceType javaResourceType = this.getJavaResourceType();
		return (javaResourceType == null) ? null : javaResourceType.getSuperclassQualifiedName();
	}

	protected PersistentType getResolvedParentClass() {
		if (this.fullyQualifiedParentClass == null) {
			return null;
		}
		return this.getPersistenceUnit().getPersistentType(this.fullyQualifiedParentClass);
	}


	// ********** Java type mapping **********

	public JavaTypeMapping getJavaTypeMapping() {
		JavaPersistentType javaType = this.getJavaPersistentType();
		if (javaType == null) {
			return null;
		}
		return (javaType.getMappingKey() == this.getKey()) ? javaType.getMapping() : null;
	}

	public JavaTypeMapping getJavaTypeMappingForDefaults() {
		return this.isMetadataComplete() ? null : this.getJavaTypeMapping();
	}
	
	
	// ********** misc **********

	public OrmPersistentType getPersistentType() {
		return this.parent;
	}

	public String getName() {
		return this.getPersistentType().getName();
	}

	protected JavaPersistentType getJavaPersistentType() {
		return this.getPersistentType().getJavaPersistentType();
	}

	public JavaResourceType getJavaResourceType() {
		JavaPersistentType javaType = this.getJavaPersistentType();
		return (javaType == null) ? null : javaType.getJavaResourceType();
	}

	protected EntityMappings getEntityMappings() {
		return this.getPersistentType().getParent();
	}

	public boolean isMapped() {
		return true;
	}

	/**
	 * A type's mapping is being changed. Copy the common settings from the old
	 * mapping to the new (this).
	 */
	public void initializeFrom(OrmTypeMapping oldMapping) {
		this.setSpecifiedMetadataComplete(oldMapping.getSpecifiedMetadataComplete());
		this.setOverrideMetadataComplete(oldMapping.isOverrideMetadataComplete());
	}

	public X getXmlTypeMapping() {
		return this.xmlTypeMapping;
	}

	public boolean attributeIsDerivedId(String attributeName) {
		return TypeMappingTools.attributeIsDerivedId(this, attributeName);
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getPersistentType().getName());
	}


	// ********** tables **********

	public String getPrimaryTableName() {
		return null;
	}

	public Table getPrimaryDbTable() {
		return null;
	}

	public Table resolveDbTable(String tableName) {
		return null;
	}

	public Schema getDbSchema() {
		return null;
	}


	// ********** attribute mappings **********
	
	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		return true;
	}
	
	public Iterable<AttributeMapping> getAttributeMappings() {
		return IterableTools.transform(getPersistentType().getAttributes(), OrmPersistentAttribute.MAPPING_TRANSFORMER);
	}
	
	public Iterable<AttributeMapping> getAllAttributeMappings() {
		return IterableTools.transform(getPersistentType().getAllAttributes(), PersistentAttribute.MAPPING_TRANSFORMER);
	}
	
	public Iterable<String> getOverridableAttributeNames() {
		return IterableTools.children(getAttributeMappings(), AttributeMapping.ALL_OVERRIDABLE_ATTRIBUTE_MAPPING_NAMES_TRANSFORMER);
	}
	
	public Iterable<String> getAllOverridableAttributeNames() {
		return IterableTools.children(getInheritanceHierarchy(), TypeMappingTools.OVERRIDABLE_ATTRIBUTE_NAMES_TRANSFORMER);
	}
	
	public Iterable<AttributeMapping> getAttributeMappings(final String mappingKey) {
		return IterableTools.filter(getAttributeMappings(), new AttributeMapping.KeyEquals(mappingKey));
	}
	
	public Iterable<AttributeMapping> getAllAttributeMappings(final String mappingKey) {
		return IterableTools.filter(getAllAttributeMappings(), new AttributeMapping.KeyEquals(mappingKey));
	}
	
	public Iterable<AttributeMapping> getNonTransientAttributeMappings() {
		return new FilteringIterable<AttributeMapping>(getAllAttributeMappings(), AttributeMapping.IS_NOT_TRANSIENT);
	}
	
	public Iterable<AttributeMapping> getIdAttributeMappings() {
		return IterableTools.filter(getAllAttributeMappings(), new IdTypeMapping.MappingIsIdMapping());
	}
	
	public AttributeMapping getIdAttributeMapping() {
		Iterable<AttributeMapping> idMappings = getIdAttributeMappings();
		if (IterableTools.size(idMappings) == 1) {
			return IterableTools.get(idMappings, 0);
		}
		return null;
	}
	
	public SpecifiedColumn resolveOverriddenColumn(String attributeName) {
		for (AttributeMapping attributeMapping : getAttributeMappings()) {
			SpecifiedColumn column = attributeMapping.resolveOverriddenColumn(attributeName);
			if (column != null) {
				return column;
			}
		}
		if ( ! isMetadataComplete()) {
			JavaPersistentType javaPersistentType = getJavaPersistentType();
			if (javaPersistentType != null) {
				return javaPersistentType.getMapping().resolveOverriddenColumn(attributeName);
			}
		}
		return null;
	}
	
	public Iterable<String> getOverridableAssociationNames() {
		return IterableTools.children(getAttributeMappings(), AttributeMapping.ALL_OVERRIDABLE_ASSOCIATION_MAPPING_NAMES_TRANSFORMER);
	}
	
	public Iterable<String> getAllOverridableAssociationNames() {
		return IterableTools.children(getInheritanceHierarchy(), TypeMappingTools.OVERRIDABLE_ASSOCIATION_NAMES_TRANSFORMER);
	}
	
	public SpecifiedRelationship resolveOverriddenRelationship(String attributeName) {
		for (AttributeMapping attributeMapping : getAttributeMappings()) {
			SpecifiedRelationship relationship = attributeMapping.resolveOverriddenRelationship(attributeName);
			if (relationship != null) {
				return relationship;
			}
		}
		if ( ! isMetadataComplete()) {
			JavaPersistentType javaPersistentType = getJavaPersistentType();
			if (javaPersistentType != null) {
				return javaPersistentType.getMapping().resolveOverriddenRelationship(attributeName);
			}
		}
		return null;
	}
	
	
	// ********** text ranges **********

	public TextRange getSelectionTextRange() {
		return this.xmlTypeMapping.getSelectionTextRange();
	}

	public TextRange getAttributesTextRange() {
		return this.getValidationTextRange(this.xmlTypeMapping.getAttributesTextRange());
	}


	// ********** refactoring **********

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return IterableTools.<ReplaceEdit>emptyIterable();
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.<ReplaceEdit>emptyIterable();
	}

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.<ReplaceEdit>emptyIterable();
	}


	// ********** generators **********

	public Iterable<Generator> getGenerators() {
		return IterableTools.children(this.getAttributeMappings(), AttributeMapping.GENERATORS_TRANSFORMER);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateClass(messages, reporter);
	}

	protected void validateClass(List<IMessage> messages, IReporter reporter) {
		if (this.getJavaResourceType() != null) {
			this.buildTypeMappingValidator().validate(messages, reporter);
		}
	}

	/**
	 * Pre-condition: the mapping's {@link #getJavaResourceType() Java resource
	 * type} is not <code>null</code>.
	 */
	protected JpaValidator buildTypeMappingValidator() {
		return new GenericTypeMappingValidator(this);
	}

	public boolean validatesAgainstDatabase() {
		return this.getPersistenceUnit().validatesAgainstDatabase();
	}

	public TextRange getValidationTextRange() {
		return this.getPersistentType().getValidationTextRange();
	}

	protected Iterable<String> getCandidateClassNames() {
		return JavaProjectTools.getJavaClassNames(this.getJavaProject());
	}

}
