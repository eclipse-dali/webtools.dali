/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.Relationship;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.AttributeMappingTools;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.TypeMappingTextRangeResolver;
import org.eclipse.jpt.core.internal.context.TypeMappingTools;
import org.eclipse.jpt.core.internal.jpa1.context.GenericTypeMappingValidator;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.Tools;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
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
	protected final X xmlTypeMapping;

	protected String class_;

	protected Boolean specifiedMetadataComplete;
	protected boolean overrideMetadataComplete;


	protected AbstractOrmTypeMapping(OrmPersistentType parent, X xmlTypeMapping) {
		super(parent);
		this.xmlTypeMapping = xmlTypeMapping;
		this.class_ = xmlTypeMapping.getClassName();
		this.specifiedMetadataComplete = xmlTypeMapping.getMetadataComplete();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setClass_(this.xmlTypeMapping.getClassName());
		this.setSpecifiedMetadataComplete_(this.xmlTypeMapping.getMetadataComplete());
	}

	@Override
	public void update() {
		super.update();
		this.setOverrideMetadataComplete(this.buildOverrideMetadataComplete());
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
		if (this.firePropertyChanged(CLASS_PROPERTY, old, class_)) {
			this.getPersistentType().mappingClassChanged(old, class_);
		}
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

	protected JavaResourcePersistentType getJavaResourcePersistentType() {
		JavaPersistentType javaType = this.getJavaPersistentType();
		return (javaType == null) ? null : javaType.getResourcePersistentType();
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

	public Iterator<AttributeMapping> attributeMappings() {
		return new TransformationIterator<OrmReadOnlyPersistentAttribute, AttributeMapping>(this.getPersistentType().attributes()) {
			@Override
			protected AttributeMapping transform(OrmReadOnlyPersistentAttribute attribute) {
				return attribute.getMapping();
			}
		};
	}

	public Iterator<AttributeMapping> allAttributeMappings() {
		return new CompositeIterator<AttributeMapping>(this.allAttributeMappingsLists());
	}

	protected Iterator<Iterator<AttributeMapping>> allAttributeMappingsLists() {
		return new TransformationIterator<TypeMapping, Iterator<AttributeMapping>>(this.inheritanceHierarchy(), TypeMappingTools.ATTRIBUTE_MAPPINGS_TRANSFORMER);
	}

	public Iterator<String> overridableAttributeNames() {
		return new CompositeIterator<String>(this.overridableAttributeNamesLists());
	}

	protected Iterator<Iterator<String>> overridableAttributeNamesLists() {
		return new TransformationIterator<AttributeMapping, Iterator<String>>(this.attributeMappings(), AttributeMappingTools.ALL_OVERRIDABLE_ATTRIBUTE_MAPPING_NAMES_TRANSFORMER);
	}

	public Iterator<String> allOverridableAttributeNames() {
		return new CompositeIterator<String>(this.allOverridableAttributeNamesLists());
	}

	protected Iterator<Iterator<String>> allOverridableAttributeNamesLists() {
		return new TransformationIterator<TypeMapping, Iterator<String>>(this.inheritanceHierarchy(), TypeMappingTools.OVERRIDABLE_ATTRIBUTE_NAMES_TRANSFORMER);
	}

	public Iterable<AttributeMapping> getAttributeMappings(final String mappingKey) {
		return new FilteringIterable<AttributeMapping>(CollectionTools.collection(this.attributeMappings())) {
			@Override
			protected boolean accept(AttributeMapping o) {
				return Tools.valuesAreEqual(o.getKey(), mappingKey);
			}
		};
	}

	public Iterable<AttributeMapping> getAllAttributeMappings(final String mappingKey) {
		return new FilteringIterable<AttributeMapping>(CollectionTools.collection(this.allAttributeMappings())) {
			@Override
			protected boolean accept(AttributeMapping o) {
				return Tools.valuesAreEqual(o.getKey(), mappingKey);
			}
		};
	}

	public Column resolveOverriddenColumn(String attributeName) {
		for (AttributeMapping attributeMapping : CollectionTools.iterable(this.attributeMappings())) {
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

	public Iterator<String> overridableAssociationNames() {
		return new CompositeIterator<String>(this.overridableAssociationNamesLists());
	}

	protected Iterator<Iterator<String>> overridableAssociationNamesLists() {
		return new TransformationIterator<AttributeMapping, Iterator<String>>(this.attributeMappings(), AttributeMappingTools.ALL_OVERRIDABLE_ASSOCIATION_MAPPING_NAMES_TRANSFORMER);
	}

	public Iterator<String> allOverridableAssociationNames() {
		return new CompositeIterator<String>(this.allOverridableAssociationNamesLists());
	}

	protected Iterator<Iterator<String>> allOverridableAssociationNamesLists() {
		return new TransformationIterator<TypeMapping, Iterator<String>>(this.inheritanceHierarchy(), TypeMappingTools.OVERRIDABLE_ASSOCIATION_NAMES_TRANSFORMER);
	}

	public Relationship resolveOverriddenRelationship(String attributeName) {
		for (AttributeMapping attributeMapping : CollectionTools.iterable(this.attributeMappings())) {
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

	public Iterator<TypeMapping> inheritanceHierarchy() {
		return this.convertToMappings(this.getPersistentType().inheritanceHierarchy());
	}

	protected Iterable<TypeMapping> getInheritanceHierarchy() {
		return CollectionTools.iterable(this.inheritanceHierarchy());
	}

	/**
	 * Return the type mapping's "persistence" ancestors,
	 * <em>excluding</em> the type mapping itself.
	 * The returned iterator will return elements infinitely if the hierarchy
	 * has a loop.
	 */
	protected Iterator<TypeMapping> ancestors() {
		return this.convertToMappings(this.getPersistentType().ancestors());
	}

	protected Iterable<TypeMapping> getAncestors() {
		return CollectionTools.iterable(this.ancestors());
	}

	protected Iterator<TypeMapping> convertToMappings(Iterator<PersistentType> types) {
		return new TransformationIterator<PersistentType, TypeMapping>(types) {
			@Override
			protected TypeMapping transform(PersistentType type) {
				return type.getMapping();
			}
		};
	}


	// ********** misc **********

	public X getXmlTypeMapping() {
		return this.xmlTypeMapping;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getPersistentType().getName());
	}


	// ********** text ranges **********

	public JpaStructureNode getStructureNode(int offset) {
		return this.xmlTypeMapping.containsOffset(offset) ? this.getPersistentType() : null;
	}

	public TextRange getSelectionTextRange() {
		return this.xmlTypeMapping.getSelectionTextRange();
	}

	public TextRange getClassTextRange() {
		return this.xmlTypeMapping.getClassTextRange();
	}

	public TextRange getAttributesTextRange() {
		return this.xmlTypeMapping.getAttributesTextRange();
	}

	public TextRange getNameTextRange() {
		return this.xmlTypeMapping.getNameTextRange();
	}
	
	public boolean containsOffset(int textOffset) {
		return this.xmlTypeMapping.containsOffset(textOffset);
	}


	// ********** refactoring **********

	public DeleteEdit createDeleteEdit() {
		return this.xmlTypeMapping.createDeleteEdit();
	}

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return this.getPersistentType().isFor(originalType.getFullyQualifiedName('.')) ?
				new SingleElementIterable<ReplaceEdit>(this.createRenameTypeEdit(originalType, newName)) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	protected ReplaceEdit createRenameTypeEdit(IType originalType, String newName) {
		return this.xmlTypeMapping.createRenameTypeEdit(originalType, newName);
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.getPersistentType().isFor(originalType.getFullyQualifiedName('.')) ?
				new SingleElementIterable<ReplaceEdit>(this.createRenamePackageEdit(newPackage.getElementName())) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.getPersistentType().isIn(originalPackage) ?
				new SingleElementIterable<ReplaceEdit>(this.createRenamePackageEdit(newName)) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	protected ReplaceEdit createRenamePackageEdit(String newName) {
		return this.xmlTypeMapping.createRenamePackageEdit(newName);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateClass(messages, reporter);
	}

	protected void validateClass(List<IMessage> messages, IReporter reporter) {
		if (StringTools.stringIsEmpty(this.class_)) {
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
		this.buildTypeMappingValidator().validate(messages, reporter);
	}

	protected JptValidator buildTypeMappingValidator() {
		return new GenericTypeMappingValidator(this, this.getJavaResourcePersistentType(), this.buildTextRangeResolver());
	}

	protected TypeMappingTextRangeResolver buildTextRangeResolver() {
		return new OrmTypeMappingTextRangeResolver(this);
	}

	public boolean validatesAgainstDatabase() {
		return this.getPersistenceUnit().validatesAgainstDatabase();
	}

	public TextRange getValidationTextRange() {
		return this.xmlTypeMapping.getValidationTextRange();
	}
}
