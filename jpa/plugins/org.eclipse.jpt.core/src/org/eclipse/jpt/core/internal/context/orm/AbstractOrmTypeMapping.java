/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractOrmTypeMapping<T extends XmlTypeMapping>
	extends AbstractOrmXmlContextNode 
	implements OrmTypeMapping
{
	protected String class_;
	
	public boolean defaultMetadataComplete;
	
	protected Boolean specifiedMetadataComplete;
	
	protected final T resourceTypeMapping;
	
	
	protected AbstractOrmTypeMapping(OrmPersistentType parent, T resourceMapping) {
		super(parent);
		this.resourceTypeMapping = resourceMapping;
		this.class_ = this.getResourceClassName();
		this.specifiedMetadataComplete = this.getResourceMetadataComplete();
		this.defaultMetadataComplete = this.getPersistentType().isDefaultMetadataComplete();
	}	
	
	// **************** Type Mapping implementation *****************************

	@Override
	public OrmPersistentType getParent() {
		return (OrmPersistentType) super.getParent();
	}

	public OrmPersistentType getPersistentType() {
		return this.getParent();
	}
	
	protected JavaPersistentType getJavaPersistentType() {
		return this.getPersistentType().getJavaPersistentType();
	}
	
	public boolean isMapped() {
		return true;
	}
	
	/* default implementation */
	public JavaPersistentType getIdClass() {
		return null;
	}
	
	public String getPrimaryTableName() {
		return null;
	}

	public String getClass_() {
		return this.class_;
	}

	public void setClass(String newClass) {
		String oldClass = this.class_;
		this.class_ = newClass;
		this.resourceTypeMapping.setClassName(newClass);
		firePropertyChanged(CLASS_PROPERTY, oldClass, newClass);
		getPersistentType().classChanged(oldClass, newClass);
	}
	

	public boolean isMetadataComplete() {
		if (isDefaultMetadataComplete()) {
			//entity-mappings/persistence-unit-metadata/xml-mapping-metadata-complete is specified, then it overrides
			//anything set here
			return true;
		}
		return (this.getSpecifiedMetadataComplete() == null) ? this.isDefaultMetadataComplete() : this.getSpecifiedMetadataComplete().booleanValue();
	}

	public boolean isDefaultMetadataComplete() {
		return this.defaultMetadataComplete;
	}
	
	protected void setDefaultMetadataComplete(boolean newDefaultMetadataComplete) {
		boolean oldMetadataComplete = this.defaultMetadataComplete;
		this.defaultMetadataComplete = newDefaultMetadataComplete;
		firePropertyChanged(DEFAULT_METADATA_COMPLETE_PROPERTY, oldMetadataComplete, newDefaultMetadataComplete);
	}
	
	public Boolean getSpecifiedMetadataComplete() {
		return this.specifiedMetadataComplete;
	}
	
	public void setSpecifiedMetadataComplete(Boolean newSpecifiedMetadataComplete) {
		Boolean oldMetadataComplete = this.specifiedMetadataComplete;
		this.specifiedMetadataComplete = newSpecifiedMetadataComplete;
		this.resourceTypeMapping.setMetadataComplete(newSpecifiedMetadataComplete);
		firePropertyChanged(SPECIFIED_METADATA_COMPLETE_PROPERTY, oldMetadataComplete, newSpecifiedMetadataComplete);
	}

	/**
	 * ITypeMapping is changed and various ITypeMappings may have
	 * common settings.  In this method initialize the new ITypeMapping (this)
	 * fromthe old ITypeMapping (oldMapping)
	 */
	public void initializeFrom(OrmTypeMapping oldMapping) {
		this.setClass(oldMapping.getClass_());
		this.setSpecifiedMetadataComplete(oldMapping.getSpecifiedMetadataComplete());
		this.setDefaultMetadataComplete(oldMapping.isDefaultMetadataComplete());
	}
	
	public Table getPrimaryDbTable() {
		return null;
	}

	public Table getDbTable(String tableName) {
		return null;
	}

	public Schema getDbSchema() {
		return null;
	}

	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		return true;
	}

	public Iterator<OrmAttributeMapping> attributeMappings() {
		return new TransformationIterator<OrmPersistentAttribute, OrmAttributeMapping>(getPersistentType().attributes()) {
			@Override
			protected OrmAttributeMapping transform(OrmPersistentAttribute attribute) {
				return attribute.getMapping();
			}
		};
	}
	
	public Iterable<OrmAttributeMapping> getAttributeMappings(final String mappingKey) {
		return new FilteringIterable<OrmAttributeMapping>(CollectionTools.collection(attributeMappings())) {
			@Override
			protected boolean accept(OrmAttributeMapping o) {
				return StringTools.stringsAreEqual(o.getKey(), mappingKey);
			}
		};
	}

	public Iterator<AttributeMapping> allAttributeMappings() {
		return new CompositeIterator<AttributeMapping>(
			new TransformationIterator<TypeMapping, Iterator<AttributeMapping>>(this.inheritanceHierarchy()) {
				@Override
				protected Iterator<AttributeMapping> transform(TypeMapping typeMapping) {
					return typeMapping.attributeMappings();
				}
			});
	}
	
	public Iterable<AttributeMapping> getAllAttributeMappings(final String mappingKey) {
		return new FilteringIterable<AttributeMapping>(CollectionTools.collection(allAttributeMappings())) {
			@Override
			protected boolean accept(AttributeMapping o) {
				return StringTools.stringsAreEqual(o.getKey(), mappingKey);
			}
		};
	}
	
	public TypeMapping getSuperTypeMapping() {
		return (getPersistentType().getSuperPersistentType() == null) ?
				null 
				: getPersistentType().getSuperPersistentType().getMapping();
	}

	/**
	 * Return an iterator of TypeMappings, each which inherits from the one before,
	 * and terminates at the root entity (or at the point of cyclicity).
	 */
	public Iterator<TypeMapping> inheritanceHierarchy() {
		return new TransformationIterator<PersistentType, TypeMapping>(getPersistentType().inheritanceHierarchy()) {
			@Override
			protected TypeMapping transform(PersistentType type) {
				return type.getMapping();
			}
		};
	}
	
	public Iterator<String> overridableAttributeNames() {
		return new CompositeIterator<String>(
			new TransformationIterator<AttributeMapping, Iterator<String>>(this.attributeMappings()) {
				@Override
				protected Iterator<String> transform(AttributeMapping mapping) {
					return mapping.allOverrideableAttributeMappingNames();
				}
			});
	}

	public Iterator<String> allOverridableAttributeNames() {
		return new CompositeIterator<String>(new TransformationIterator<TypeMapping, Iterator<String>>(this.inheritanceHierarchy()) {
			@Override
			protected Iterator<String> transform(TypeMapping mapping) {
				return mapping.overridableAttributeNames();
			}
		});
	}
	
	public Column resolveOverriddenColumn(String attributeName) {
		for (AttributeMapping attributeMapping : CollectionTools.iterable(attributeMappings())) {
			Column resolvedColumn = attributeMapping.resolveOverriddenColumn(attributeName);
			if (resolvedColumn != null) {
				return resolvedColumn;
			}
		}
		if (!isMetadataComplete()) {
			JavaPersistentType javaPersistentType = getJavaPersistentType();
			if (javaPersistentType != null) {
				return javaPersistentType.getMapping().resolveOverriddenColumn(attributeName);
			}
		}
		return null;
	}
	
	public Iterator<String> overridableAssociationNames() {
		return new CompositeIterator<String>(
			new TransformationIterator<AttributeMapping, Iterator<String>>(this.attributeMappings()) {
				@Override
				protected Iterator<String> transform(AttributeMapping mapping) {
					return mapping.allOverrideableAssociationMappingNames();
				}
			});
	}

	public Iterator<String> allOverridableAssociationNames() {
		return new CompositeIterator<String>(new TransformationIterator<TypeMapping, Iterator<String>>(this.inheritanceHierarchy()) {
			@Override
			protected Iterator<String> transform(TypeMapping mapping) {
				return mapping.overridableAssociationNames();
			}
		});
	}
	
	public RelationshipReference resolveRelationshipReference(String attributeName) {
		for (AttributeMapping attributeMapping : CollectionTools.iterable(attributeMappings())) {
			RelationshipReference resolvedRelationshipReference = attributeMapping.resolveRelationshipReference(attributeName);
			if (resolvedRelationshipReference != null) {
				return resolvedRelationshipReference;
			}
		}
		if (!isMetadataComplete()) {
			JavaPersistentType javaPersistentType = getJavaPersistentType();
			if (javaPersistentType != null) {
				return javaPersistentType.getMapping().resolveRelationshipReference(attributeName);
			}
		}
		return null;
	}

	public T getResourceTypeMapping() {
		return this.resourceTypeMapping;
	}
	
	public void update() {
		this.setClass(this.getResourceClassName());
		this.setSpecifiedMetadataComplete(this.getResourceMetadataComplete());
		this.setDefaultMetadataComplete(this.getPersistentType().isDefaultMetadataComplete());
	}
	
	protected String getResourceClassName() {
		return this.resourceTypeMapping.getClassName();
	}
	
	protected Boolean getResourceMetadataComplete() {
		return this.resourceTypeMapping.getMetadataComplete();
	}

	
	// *************************************************************************
	
	public JpaStructureNode getStructureNode(int offset) {
		if (this.resourceTypeMapping.containsOffset(offset)) {
			return getPersistentType();
		}
		return null;
	}
	
	public TextRange getSelectionTextRange() {
		return this.resourceTypeMapping.getSelectionTextRange();
	}
	
	public TextRange getClassTextRange() {
		return this.resourceTypeMapping.getClassTextRange();
	}
	
	public TextRange getAttributesTextRange() {
		return this.resourceTypeMapping.getAttributesTextRange();
	}

	public boolean containsOffset(int textOffset) {
		return this.resourceTypeMapping.containsOffset(textOffset);
	}


	//************************* validation ************************
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateClass(messages);
	}

	protected void validateClass(List<IMessage> messages) {
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
	}
	
	public boolean shouldValidateAgainstDatabase() {
		return getPersistenceUnit().shouldValidateAgainstDatabase();
	}
	
	public TextRange getValidationTextRange() {
		return this.resourceTypeMapping.getValidationTextRange();
	}


	//************************* refactoring ************************

	public DeleteEdit createDeleteEdit() {
		return this.resourceTypeMapping.createDeleteEdit();
	}

	public Iterable<ReplaceEdit> createReplaceTypeEdits(IType originalType, String newName) {
		if (getPersistentType().isFor(originalType.getFullyQualifiedName('.'))) {
			return new SingleElementIterable<ReplaceEdit>(this.createReplaceTypeEdit(originalType, newName));
		}
		return EmptyIterable.instance();
	}

	protected ReplaceEdit createReplaceTypeEdit(IType originalType, String newName) {
		return this.resourceTypeMapping.createReplaceTypeEdit(originalType, newName);
	}

	public Iterable<ReplaceEdit> createMoveTypeReplaceEdits(IType originalType, IPackageFragment newPackage) {
		if (getPersistentType().isFor(originalType.getFullyQualifiedName('.'))) {
			return new SingleElementIterable<ReplaceEdit>(this.createReplacePackageEdit(newPackage.getElementName()));
		}
		return EmptyIterable.instance();
	}

	public Iterable<ReplaceEdit> createReplacePackageEdits(IPackageFragment originalPackage, String newName) {
		if (getPersistentType().isIn(originalPackage)) {
			return new SingleElementIterable<ReplaceEdit>(this.createReplacePackageEdit(newName));
		}
		return EmptyIterable.instance();
	}

	protected ReplaceEdit createReplacePackageEdit(String newName) {
		return this.resourceTypeMapping.createReplacePackageEdit(newName);
	}


	// ********** misc **********

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getPersistentType().getName());
	}
}
