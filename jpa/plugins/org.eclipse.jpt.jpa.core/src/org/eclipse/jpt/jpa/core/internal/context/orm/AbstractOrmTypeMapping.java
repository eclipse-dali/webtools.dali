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

import java.util.List;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.InheritanceType;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.jpa.core.internal.context.AttributeMappingTools;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.TypeMappingTools;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.GenericTypeMappingValidator;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> type mapping
 */
public abstract class AbstractOrmTypeMapping<X extends XmlTypeMapping>
	extends AbstractOrmXmlContextNode
	implements OrmTypeMapping
{
	// never null
	protected final X xmlTypeMapping;

	protected String class_;

	protected Boolean specifiedMetadataComplete;
	protected boolean overrideMetadataComplete;

	protected String specifiedParentClass;
	protected String defaultParentClass;
	protected String fullyQualifiedParentClass;

	protected AbstractOrmTypeMapping(OrmPersistentType parent, X xmlTypeMapping) {
		super(parent);
		this.xmlTypeMapping = xmlTypeMapping;
		this.class_ = xmlTypeMapping.getClassName();
		this.specifiedMetadataComplete = xmlTypeMapping.getMetadataComplete();
		this.specifiedParentClass = this.buildSpecifiedParentClass();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setClass_(this.xmlTypeMapping.getClassName());
		this.setSpecifiedMetadataComplete_(this.xmlTypeMapping.getMetadataComplete());
		this.setSpecifiedParentClass_(this.buildSpecifiedParentClass());
	}

	@Override
	public void update() {
		super.update();
		this.setOverrideMetadataComplete(this.buildOverrideMetadataComplete());
		this.setDefaultParentClass(this.buildDefaultParentClass());
		this.setFullyQualifiedParentClass(this.buildFullyQualifiedParentClass());
	}


	// ********** class **********

	public String getClass_() {
		return this.class_;
	}

	public void setClass(String class_) {
		this.setClass_(class_);
		this.xmlTypeMapping.setClassName(class_);
	}

	protected void setClass_(String class_) {
		String old = this.class_;
		this.class_ = class_;
		this.firePropertyChanged(CLASS_PROPERTY, old, class_);
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
				this.getEntityMappings().getFullyQualifiedName(this.specifiedParentClass);
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

	@Override
	public OrmPersistentType getParent() {
		return (OrmPersistentType) super.getParent();
	}

	public OrmPersistentType getPersistentType() {
		return this.getParent();
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
		return getPersistentType().getParent();
	}

	public boolean isMapped() {
		return true;
	}

	/**
	 * A type's mapping is being changed. Copy the common settings from the old
	 * mapping to the new (this).
	 */
	public void initializeFrom(OrmTypeMapping oldMapping) {
		this.setClass(oldMapping.getClass_());
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
		return IterableTools.transform(this.getPersistentType().getAttributes(), OrmReadOnlyPersistentAttribute.MAPPING_TRANSFORMER);
	}

	public Iterable<AttributeMapping> getAllAttributeMappings() {
		return IterableTools.children(this.getInheritanceHierarchy(), TypeMappingTools.ATTRIBUTE_MAPPINGS_TRANSFORMER);
	}

	public Iterable<String> getOverridableAttributeNames() {
		return IterableTools.children(this.getAttributeMappings(), AttributeMappingTools.ALL_OVERRIDABLE_ATTRIBUTE_MAPPING_NAMES_TRANSFORMER);
	}

	public Iterable<String> getAllOverridableAttributeNames() {
		return IterableTools.children(this.getInheritanceHierarchy(), TypeMappingTools.OVERRIDABLE_ATTRIBUTE_NAMES_TRANSFORMER);
	}

	public Iterable<AttributeMapping> getAttributeMappings(final String mappingKey) {
		return IterableTools.filter(this.getAttributeMappings(), new AttributeMapping.KeyEquals(mappingKey));
	}

	public Iterable<AttributeMapping> getAllAttributeMappings(final String mappingKey) {
		return IterableTools.filter(this.getAllAttributeMappings(), new AttributeMapping.KeyEquals(mappingKey));
	}

	public Iterable<AttributeMapping> getNonTransientAttributeMappings() {
		return new FilteringIterable<AttributeMapping>(this.getAllAttributeMappings(), AttributeMapping.IS_NOT_TRANSIENT);
	}

	public Column resolveOverriddenColumn(String attributeName) {
		for (AttributeMapping attributeMapping : this.getAttributeMappings()) {
			Column column = attributeMapping.resolveOverriddenColumn(attributeName);
			if (column != null) {
				return column;
			}
		}
		if ( ! this.isMetadataComplete()) {
			JavaPersistentType javaPersistentType = this.getJavaPersistentType();
			if (javaPersistentType != null) {
				return javaPersistentType.getMapping().resolveOverriddenColumn(attributeName);
			}
		}
		return null;
	}

	public Iterable<String> getOverridableAssociationNames() {
		return IterableTools.children(this.getAttributeMappings(), AttributeMappingTools.ALL_OVERRIDABLE_ASSOCIATION_MAPPING_NAMES_TRANSFORMER);
	}

	public Iterable<String> getAllOverridableAssociationNames() {
		return IterableTools.children(this.getInheritanceHierarchy(), TypeMappingTools.OVERRIDABLE_ASSOCIATION_NAMES_TRANSFORMER);
	}

	public Relationship resolveOverriddenRelationship(String attributeName) {
		for (AttributeMapping attributeMapping : this.getAttributeMappings()) {
			Relationship relationship = attributeMapping.resolveOverriddenRelationship(attributeName);
			if (relationship != null) {
				return relationship;
			}
		}
		if ( ! this.isMetadataComplete()) {
			JavaPersistentType javaPersistentType = this.getJavaPersistentType();
			if (javaPersistentType != null) {
				return javaPersistentType.getMapping().resolveOverriddenRelationship(attributeName);
			}
		}
		return null;
	}


	// ********** inheritance hierarchy **********

	public TypeMapping getSuperTypeMapping() {
		PersistentType superPersistentType = this.getPersistentType().getSuperPersistentType();
		return (superPersistentType == null) ? null : superPersistentType.getMapping();
	}

	public Iterable<TypeMapping> getInheritanceHierarchy() {
		return this.convertToMappings(this.getPersistentType().getInheritanceHierarchy());
	}

	/**
	 * Return the type mapping's "persistence" ancestors,
	 * <em>excluding</em> the type mapping itself.
	 * The returned iterator will return elements infinitely if the hierarchy
	 * has a loop.
	 */
	protected Iterable<TypeMapping> getAncestors() {
		return this.convertToMappings(this.getPersistentType().getAncestors());
	}

	protected Iterable<TypeMapping> convertToMappings(Iterable<PersistentType> types) {
		return IterableTools.transform(types, PersistentType.MAPPING_TRANSFORMER);
	}

	public InheritanceType getInheritanceStrategy() {
		return null;
	}

	public boolean isRootEntity() {
		return false;
	}

	public Entity getRootEntity() {
		return null;
	}

	// ********** text ranges **********

	public JpaStructureNode getStructureNode(int offset) {
		return this.xmlTypeMapping.containsOffset(offset) ? this.getPersistentType() : null;
	}

	public TextRange getSelectionTextRange() {
		return this.xmlTypeMapping.getSelectionTextRange();
	}

	public TextRange getClassTextRange() {
		return this.getValidationTextRange(this.xmlTypeMapping.getClassTextRange());
	}

	public TextRange getAttributesTextRange() {
		return this.getValidationTextRange(this.xmlTypeMapping.getAttributesTextRange());
	}

	public TextRange getNameTextRange() {
		return this.getValidationTextRange(this.xmlTypeMapping.getNameTextRange());
	}


	// ********** refactoring **********

	public DeleteEdit createDeleteEdit() {
		return this.xmlTypeMapping.createDeleteEdit();
	}

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return this.getPersistentType().isFor(originalType.getFullyQualifiedName('.')) ?
				IterableTools.singletonIterable(this.createRenameTypeEdit(originalType, newName)) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	protected ReplaceEdit createRenameTypeEdit(IType originalType, String newName) {
		return this.xmlTypeMapping.createRenameTypeEdit(originalType, newName);
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.getPersistentType().isFor(originalType.getFullyQualifiedName('.')) ?
				IterableTools.singletonIterable(this.createRenamePackageEdit(newPackage.getElementName())) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.getPersistentType().isIn(originalPackage) ?
				IterableTools.singletonIterable(this.createRenamePackageEdit(newName)) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	protected ReplaceEdit createRenamePackageEdit(String newName) {
		return this.xmlTypeMapping.createRenamePackageEdit(newName);
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
		if (StringTools.isBlank(this.class_)) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENT_TYPE_UNSPECIFIED_CLASS,
					this,
					this.getClassTextRange()
				)
			);
			return;
		}
		if (this.getJavaResourceType() != null) {
			this.buildTypeMappingValidator().validate(messages, reporter);
		}
	}

	/**
	 * Pre-condition: the mapping's {@link #getJavaResourceType() Java resource
	 * type} is not <code>null</code>.
	 */
	protected JptValidator buildTypeMappingValidator() {
		return new GenericTypeMappingValidator(this);
	}

	public boolean validatesAgainstDatabase() {
		return this.getPersistenceUnit().validatesAgainstDatabase();
	}

	public TextRange getValidationTextRange() {
		// this should never be null; also, the persistent type delegates
		// to here, so don't delegate back to it (or we will get a stack overflow)  bug 355415
		TextRange textRange = this.xmlTypeMapping.getValidationTextRange();
		//*return an Empty text range because validation sometimes run concurrently
		//with the code adding the type mapping to xml; the IDOMNode might not
		//be set when this is called. Brian's batch update changes in 3.2 should
		//fix this problem.  bug 358745
		return (textRange != null) ? textRange : TextRange.Empty.instance();
	}

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.classNameTouches(pos)) {
			return this.getCandidateClassNames();
		}
		return null;
	}

	protected Iterable<String> getCandidateClassNames() {
		return MappingTools.getSortedJavaClassNames(this.getJavaProject());
	}

	protected boolean classNameTouches(int pos) {
		return this.getXmlTypeMapping().classNameTouches(pos);
	}
}
