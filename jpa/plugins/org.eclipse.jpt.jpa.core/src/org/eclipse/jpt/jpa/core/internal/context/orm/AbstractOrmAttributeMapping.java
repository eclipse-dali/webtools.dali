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

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.ColumnMapping;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
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
import org.eclipse.jpt.jpa.core.jpa2.context.PersistentAttribute2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractOrmAttributeMapping<X extends XmlAttributeMapping>
	extends AbstractOrmXmlContextNode
	implements OrmAttributeMapping, AttributeMapping2_0
{
	// never null
	protected final X xmlAttributeMapping;

	protected String name;

	protected String specifiedAttributeType;
	protected String defaultAttributeType;
	protected String fullyQualifiedAttributeType;


	protected AbstractOrmAttributeMapping(OrmPersistentAttribute parent, X xmlAttributeMapping) {
		super(parent);
		this.xmlAttributeMapping = xmlAttributeMapping;
		this.name = xmlAttributeMapping.getName();
		this.specifiedAttributeType = this.buildSpecifiedAttributeType();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setName_(this.xmlAttributeMapping.getName());
		this.setSpecifiedAttributeType_(this.buildSpecifiedAttributeType());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultAttributeType(this.buildDefaultAttributeType());
		this.setFullyQualifiedAttributeType(this.buildFullyQualifiedAttributeType());
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


	// ********** fully-qualified attribute type **********

	public String getFullyQualifiedAttributeType() {
		return this.fullyQualifiedAttributeType;
	}

	protected void setFullyQualifiedAttributeType(String attributeType) {
		String old = this.fullyQualifiedAttributeType;
		this.fullyQualifiedAttributeType = attributeType;
		this.firePropertyChanged(FULLY_QUALIFIED_ATTRIBUTE_TYPE_PROPERTY, old, attributeType);
	}

	protected String buildFullyQualifiedAttributeType() {
		return (this.specifiedAttributeType == null) ?
				this.defaultAttributeType :
				this.getEntityMappings().getFullyQualifiedName(this.specifiedAttributeType);
	}

	// ********** attribute type **********

	public String getAttributeType() {
		return (this.specifiedAttributeType != null) ? this.specifiedAttributeType : this.defaultAttributeType;
	}

	public String getSpecifiedAttributeType() {
		return this.specifiedAttributeType;
	}

	public void setSpecifiedAttributeType(String attributeType) {
		this.setSpecifiedAttributeType_(attributeType);
		this.setSpecifiedAttributeTypeInXml(attributeType);
	}

	protected void setSpecifiedAttributeType_(String attributeType) {
		String old = this.specifiedAttributeType;
		this.specifiedAttributeType = attributeType;
		this.firePropertyChanged(SPECIFIED_ATTRIBUTE_TYPE_PROPERTY, old, attributeType);
	}

	/**
	 * subclasses must override if they support specifying an attribute type
	 */
	protected void setSpecifiedAttributeTypeInXml(@SuppressWarnings("unused") String attributeType) {
		// NOP
	}

	/**
	 * subclasses must override if they support specifying an attribute type
	 */
	protected String buildSpecifiedAttributeType() {
		return null;
	}

	public String getDefaultAttributeType() {
		return this.defaultAttributeType;
	}

	protected void setDefaultAttributeType(String attributeType) {
		String old = this.defaultAttributeType;
		this.defaultAttributeType = attributeType;
		this.firePropertyChanged(DEFAULT_ATTRIBUTE_TYPE_PROPERTY, old, attributeType);
	}

	protected String buildDefaultAttributeType() {
		return (this.getJavaPersistentAttribute() == null) ? null :
				this.getJavaPersistentAttribute().getTypeName(this.getTypeMapping().getPersistentType());
	}

	protected PersistentType getResolvedAttributeType() {
		return (this.fullyQualifiedAttributeType == null) ? null :
				this.getPersistenceUnit().getPersistentType(this.fullyQualifiedAttributeType);
	}


	// ********** morphing mappings **********

	public void initializeFromOrmAttributeMapping(OrmAttributeMapping oldMapping) {
		this.setName(oldMapping.getName());
		this.setSpecifiedAttributeType(oldMapping.getSpecifiedAttributeType());
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

	public TextRange getSelectionTextRange() {
		return this.xmlAttributeMapping.getSelectionTextRange();
	}

	public TextRange getValidationTextRange() {
		// this should never be null; also, the persistent attribute delegates
		// to here, so don't delegate back to it (or we will get a stack overflow)
		return this.xmlAttributeMapping.getValidationTextRange();
	}

	public TextRange getNameTextRange() {
		return this.getValidationTextRange(this.xmlAttributeMapping.getNameTextRange());
	}

	public Iterable<Generator> getGenerators() {
		return EmptyIterable.instance();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** embedded mappings **********

	public Iterable<String> getAllMappingNames() {
		return (this.name != null) ?
				new SingleElementIterable<String>(this.name) :
				EmptyIterable.<String>instance();
	}

	public Iterable<String> getAllOverridableAttributeMappingNames() {
		return ((this.name != null) && this.isOverridableAttributeMapping()) ?
				new SingleElementIterable<String>(this.name) :
				EmptyIterable.<String>instance();
	}

	public Iterable<String> getAllOverridableAssociationMappingNames() {
		return ((this.name != null) && this.isOverridableAssociationMapping()) ?
				new SingleElementIterable<String>(this.name) :
				EmptyIterable.<String>instance();
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
		return ObjectTools.equals(attributeName, this.name) ? this : null;
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
		if(metamodelFieldName == null) {
			return null;
		}
		MetamodelField result = null;
		try {
			result = new SimpleMetamodelField(
				this.getMetamodelFieldModifiers(),
				this.getMetamodelFieldTypeName(),
				this.getMetamodelFieldTypeArgumentNames(),
				metamodelFieldName
			);
		}
		catch(IllegalMetamodelFieldTypeArgumentException e) {
			// the target entity is dynamic
		}
		return result;
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
		String metamodelTypeName = this.getMetamodelTypeName();
		if(metamodelTypeName == null) {		// the attribute is dynamic
			throw this.buildIllegalMetamodelFieldTypeArgumentException();
		}
		typeArgumentNames.add(metamodelTypeName);
	}

	public String getMetamodelTypeName() {
		return ((PersistentAttribute2_0) this.getPersistentAttribute()).getMetamodelTypeName();
	}

	protected String getMetamodelFieldName() {
		return this.name;
	}

	protected IllegalMetamodelFieldTypeArgumentException buildIllegalMetamodelFieldTypeArgumentException() {
		return new IllegalMetamodelFieldTypeArgumentException();
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
		// we just check for a name that is either empty or whitespace here;
		// the name will be null if the 'name' attribute is missing
		// and there will already be an XSD-driven error
		if ((this.name != null) && StringTools.isBlank(this.name)) {
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

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.attributeNameTouches(pos)) {
			return this.getCandidateMappingNames();
		}
		return null;
	}

	/**
	 * @return all the attribute of corresponding Java persistent type
	 */
	// Attributes of the corresponding XML persistent type include these
	// that are already defined in XML so using these attributes as candidates
	// could lead to multiple attribute mappings with the same name;
	// thus using the attribute of the corresponding Java persistent type instead
	private Iterable<String> getCandidateMappingNames() {
		JavaPersistentType jpt = this.getTypeMapping().getPersistentType().getJavaPersistentType();
		return jpt == null ? EmptyIterable.<String>instance() : jpt.getAttributeNames();
	}

	private boolean attributeNameTouches(int pos) {
		return this.getXmlAttributeMapping().nameTouches(pos);
	}
}
