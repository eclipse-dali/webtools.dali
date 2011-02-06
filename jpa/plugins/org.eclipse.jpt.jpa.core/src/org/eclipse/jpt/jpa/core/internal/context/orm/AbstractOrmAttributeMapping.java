/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.Transformer;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.common.utility.internal.iterators.SingleElementIterator;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.ColumnMapping;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmBaseEmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmColumnMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmMultiRelationshipMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmSingleRelationshipMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmTransientMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.SimpleMetamodelField;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmPersistentAttribute2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractOrmAttributeMapping<X extends XmlAttributeMapping>
	extends AbstractOrmXmlContextNode
	implements OrmAttributeMapping, AttributeMapping2_0
{
	protected final X xmlAttributeMapping;

	protected String name;


	protected AbstractOrmAttributeMapping(OrmPersistentAttribute parent, X xmlAttributeMapping) {
		super(parent);
		this.xmlAttributeMapping = xmlAttributeMapping;
		this.name = xmlAttributeMapping.getName();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setName_(this.xmlAttributeMapping.getName());
	}


	// ********** name **********

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.setName_(name);
		this.xmlAttributeMapping.setName(name);
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		if (this.firePropertyChanged(NAME_PROPERTY, old, name)) {
			// tell our attribute its name has, effectively, changed
			this.getPersistentAttribute().nameChanged(old, name);
		}
	}


	// ********** morphing mappings **********

	public void initializeFromOrmAttributeMapping(OrmAttributeMapping oldMapping) {
		this.setName(oldMapping.getName());
	}

	protected void initializeFromOrmColumnMapping(OrmColumnMapping oldMapping) {
		this.initializeFromOrmAttributeMapping(oldMapping);
	}

	public void initializeFromOrmBasicMapping(OrmBasicMapping oldMapping) {
		this.initializeFromOrmColumnMapping(oldMapping);
	}

	public void initializeFromOrmIdMapping(OrmIdMapping oldMapping) {
		this.initializeFromOrmColumnMapping(oldMapping);
	}

	public void initializeFromOrmTransientMapping(OrmTransientMapping oldMapping) {
		this.initializeFromOrmAttributeMapping(oldMapping);
	}

	protected void initializeFromOrmBaseEmbeddedMapping(OrmBaseEmbeddedMapping oldMapping) {
		this.initializeFromOrmAttributeMapping(oldMapping);
	}

	public void initializeFromOrmEmbeddedMapping(OrmEmbeddedMapping oldMapping) {
		this.initializeFromOrmBaseEmbeddedMapping(oldMapping);
	}

	public void initializeFromOrmEmbeddedIdMapping(OrmEmbeddedIdMapping oldMapping) {
		this.initializeFromOrmBaseEmbeddedMapping(oldMapping);
	}

	public void initializeFromOrmVersionMapping(OrmVersionMapping oldMapping) {
		this.initializeFromOrmColumnMapping(oldMapping);
	}

	protected void initializeFromOrmRelationshipMapping(OrmRelationshipMapping oldMapping) {
		this.initializeFromOrmAttributeMapping(oldMapping);
	}

	protected void initializeFromOrmMultiRelationshipMapping(OrmMultiRelationshipMapping oldMapping) {
		this.initializeFromOrmRelationshipMapping(oldMapping);
	}

	protected void initializeFromOrmSingleRelationshipMapping(OrmSingleRelationshipMapping oldMapping) {
		this.initializeFromOrmRelationshipMapping(oldMapping);
	}

	public void initializeFromOrmOneToManyMapping(OrmOneToManyMapping oldMapping) {
		this.initializeFromOrmMultiRelationshipMapping(oldMapping);
	}

	public void initializeFromOrmManyToOneMapping(OrmManyToOneMapping oldMapping) {
		this.initializeFromOrmSingleRelationshipMapping(oldMapping);
	}

	public void initializeFromOrmOneToOneMapping(OrmOneToOneMapping oldMapping) {
		this.initializeFromOrmSingleRelationshipMapping(oldMapping);
	}

	public void initializeFromOrmManyToManyMapping(OrmManyToManyMapping oldMapping) {
		this.initializeFromOrmMultiRelationshipMapping(oldMapping);
	}


	// ********** misc **********

	public X getXmlAttributeMapping() {
		return this.xmlAttributeMapping;
	}

	@Override
	public OrmPersistentAttribute getParent() {
		return (OrmPersistentAttribute) super.getParent();
	}

	public OrmPersistentAttribute getPersistentAttribute() {
		return this.getParent();
	}

	public OrmTypeMapping getTypeMapping() {
		return this.getPersistentAttribute().getOwningTypeMapping();
	}

	protected JavaPersistentAttribute getJavaPersistentAttribute() {
		return this.getPersistentAttribute().getJavaPersistentAttribute();
	}

	// TODO remove this method - it will always return false...
	// subclass member classes like this method
	public boolean isVirtual() {
		return false;
	}

	protected EntityMappings getEntityMappings() {
		return this.getPersistentAttribute().getOwningPersistentType().getParent();
	}

	protected PersistentType resolvePersistentType(String className) {
		return this.getEntityMappings().resolvePersistentType(className);
	}

	public boolean isDefault() {
		return false;
	}

	public String getPrimaryKeyColumnName() {
		return null;
	}

	public boolean isOverridableAttributeMapping() {
		return false;
	}

	public boolean isOverridableAssociationMapping() {
		return false;
	}

	public boolean isRelationshipOwner() {
		return false;
	}

	public boolean isOwnedBy(AttributeMapping mapping) {
		return false;
	}

	public boolean validatesAgainstDatabase() {
		return this.getTypeMapping().validatesAgainstDatabase();
	}

	public boolean contains(int textOffset) {
		return this.xmlAttributeMapping.containsOffset(textOffset);
	}

	public TextRange getSelectionTextRange() {
		return this.xmlAttributeMapping.getSelectionTextRange();
	}

	public TextRange getValidationTextRange() {
		return this.xmlAttributeMapping.getValidationTextRange();
	}

	public TextRange getNameTextRange() {
		return this.xmlAttributeMapping.getNameTextRange();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** embedded mappings **********

	public Iterator<String> allMappingNames() {
		return (this.name != null) ?
				new SingleElementIterator<String>(this.name) :
				EmptyIterator.<String>instance();
	}

	public Iterator<String> allOverridableAttributeMappingNames() {
		return ((this.name != null) && this.isOverridableAttributeMapping()) ?
				new SingleElementIterator<String>(this.name) :
				EmptyIterator.<String>instance();
	}

	public Iterator<String> allOverridableAssociationMappingNames() {
		return ((this.name != null) && this.isOverridableAssociationMapping()) ?
				new SingleElementIterator<String>(this.name) :
				EmptyIterator.<String>instance();
	}

	public Column resolveOverriddenColumn(String attributeName) {
		ColumnMapping mapping = this.resolveColumnMapping(attributeName);
		return (mapping == null) ? null : mapping.getColumn();
	}

	protected ColumnMapping resolveColumnMapping(String attributeName) {
		AttributeMapping mapping = this.resolveAttributeMapping(attributeName);
		return ((mapping != null) && mapping.isOverridableAttributeMapping()) ? (ColumnMapping) mapping : null;
	}

	public Relationship resolveOverriddenRelationship(String attributeName) {
		RelationshipMapping mapping = this.resolveRelationshipMapping(attributeName);
		return (mapping == null) ? null : mapping.getRelationship();
	}

	protected RelationshipMapping resolveRelationshipMapping(String attributeName) {
		AttributeMapping mapping = this.resolveAttributeMapping(attributeName);
		return ((mapping != null) && mapping.isOverridableAssociationMapping()) ?
			(RelationshipMapping) mapping :
			null;
	}

	public AttributeMapping resolveAttributeMapping(String attributeName) {
		return Tools.valuesAreEqual(attributeName, this.name) ? this : null;
	}

	protected Transformer<String, String> buildQualifierTransformer() {
		return new MappingTools.QualifierTransformer(this.name);
	}

	protected String unqualify(String attributeName) {
		return MappingTools.unqualify(this.name, attributeName);
	}


	// ********** metamodel **********

	public MetamodelField getMetamodelField() {
		// if we don't have a name we can't build a metamodel field...
		String metamodelFieldName = this.getMetamodelFieldName();
		return (metamodelFieldName == null) ? null :
					new SimpleMetamodelField(
						this.getMetamodelFieldModifiers(),
						this.getMetamodelFieldTypeName(),
						this.getMetamodelFieldTypeArgumentNames(),
						metamodelFieldName
					);
	}

	protected Iterable<String> getMetamodelFieldModifiers() {
		return STANDARD_METAMODEL_FIELD_MODIFIERS;
	}

	/**
	 * most mappings are "singular"
	 */
	protected String getMetamodelFieldTypeName() {
		return JPA2_0.SINGULAR_ATTRIBUTE;
	}

	protected final Iterable<String> getMetamodelFieldTypeArgumentNames() {
		ArrayList<String> typeArgumentNames = new ArrayList<String>(3);
		typeArgumentNames.add(this.getTypeMapping().getPersistentType().getName());
		this.addMetamodelFieldTypeArgumentNamesTo(typeArgumentNames);
		return typeArgumentNames;
	}

	/**
	 * by default, we add only the mapping's attribute type name;
	 * but collection relationship mappings will also need to add the key type
	 * name if the "collection" is of type java.util.Map
	 */
	protected void addMetamodelFieldTypeArgumentNamesTo(ArrayList<String> typeArgumentNames) {
		typeArgumentNames.add(this.getMetamodelTypeName());
	}

	public String getMetamodelTypeName() {
		return ((OrmPersistentAttribute2_0) this.getPersistentAttribute()).getMetamodelTypeName();
	}

	protected String getMetamodelFieldName() {
		return this.name;
	}


	// ********** refactoring **********

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return EmptyIterable.instance();
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return EmptyIterable.instance();
	}

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return EmptyIterable.instance();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateAttribute(messages);
		this.validateMapping(messages);
	}

	protected void validateAttribute(List<IMessage> messages) {
		if (StringTools.stringIsEmpty(this.name)) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENT_ATTRIBUTE_UNSPECIFIED_NAME,
					this,
					this.getValidationTextRange()
				)
			);
		}
	}

	//TODO validation message - i think more info is needed in this message.  include type mapping type?
	protected void validateMapping(List<IMessage> messages) {
		if ( ! this.getTypeMapping().attributeMappingKeyAllowed(this.getKey())) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_MAPPING,
					new String[] {this.name},
					this,
					this.getValidationTextRange()
				)
			);
		}
	}
}
