/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmBaseEmbeddedMapping;
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmColumnMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmMultiRelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmSingleRelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmTransientMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.core.internal.jpa2.context.SimpleMetamodelField;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.AttributeMapping2_0;
import org.eclipse.jpt.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.core.jpa2.context.java.JavaPersistentAttribute2_0;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractOrmAttributeMapping<T extends XmlAttributeMapping>
	extends AbstractOrmXmlContextNode
	implements OrmAttributeMapping, AttributeMapping2_0
{
	protected String name;
	
	protected final T resourceAttributeMapping;

	protected AbstractOrmAttributeMapping(OrmPersistentAttribute parent, T resourceAttributeMapping) {
		super(parent);
		this.resourceAttributeMapping = resourceAttributeMapping;
		this.name = this.getResourceMappingName();
	}	
	
	protected JavaPersistentAttribute getJavaPersistentAttribute() {
		return this.getPersistentAttribute().getJavaPersistentAttribute();
	}
	
	protected JavaResourcePersistentAttribute getJavaResourcePersistentAttribute() {
		return this.getJavaPersistentAttribute().getResourcePersistentAttribute();
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		String old = this.name;
		this.name = name;
		this.resourceAttributeMapping.setName(name);
		this.firePropertyChanged(NAME_PROPERTY, old, name);
		this.getPersistentAttribute().nameChanged(old, name);
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
		this.getPersistentAttribute().nameChanged(old, name);
	}
	
	public OrmPersistentAttribute getPersistentAttribute() {
		return (OrmPersistentAttribute) getParent();
	}

	public boolean isDefault() {
		return false;
	}
	
	public void initializeFromOrmAttributeMapping(OrmAttributeMapping oldMapping) {
		setName(oldMapping.getName());
	}

	public void initializeFromOrmColumnMapping(OrmColumnMapping oldMapping) {
		initializeFromOrmAttributeMapping((OrmAttributeMapping) oldMapping);
	}

	public void initializeFromOrmBasicMapping(OrmBasicMapping oldMapping) {
		initializeFromOrmColumnMapping(oldMapping);
	}

	public void initializeFromOrmIdMapping(OrmIdMapping oldMapping) {
		initializeFromOrmColumnMapping(oldMapping);
	}

	public void initializeFromOrmTransientMapping(OrmTransientMapping oldMapping) {
		initializeFromOrmAttributeMapping(oldMapping);
	}

	public void initializeFromOrmBaseEmbeddedMapping(OrmBaseEmbeddedMapping oldMapping) {
		initializeFromOrmAttributeMapping(oldMapping);
	}

	public void initializeFromOrmEmbeddedMapping(OrmEmbeddedMapping oldMapping) {
		initializeFromOrmBaseEmbeddedMapping(oldMapping);
	}

	public void initializeFromOrmEmbeddedIdMapping(OrmEmbeddedIdMapping oldMapping) {
		initializeFromOrmBaseEmbeddedMapping(oldMapping);
	}

	public void initializeFromOrmVersionMapping(OrmVersionMapping oldMapping) {
		initializeFromOrmColumnMapping(oldMapping);
	}

	public void initializeFromOrmRelationshipMapping(OrmRelationshipMapping oldMapping) {
		initializeFromOrmAttributeMapping(oldMapping);
	}

	public void initializeFromOrmMultiRelationshipMapping(OrmMultiRelationshipMapping oldMapping) {
		initializeFromOrmRelationshipMapping(oldMapping);
	}

	public void initializeFromOrmSingleRelationshipMapping(OrmSingleRelationshipMapping oldMapping) {
		initializeFromOrmRelationshipMapping(oldMapping);
	}

	public void initializeFromOrmOneToManyMapping(OrmOneToManyMapping oldMapping) {
		initializeFromOrmMultiRelationshipMapping(oldMapping);
	}

	public void initializeFromOrmManyToOneMapping(OrmManyToOneMapping oldMapping) {
		initializeFromOrmSingleRelationshipMapping(oldMapping);
	}

	public void initializeFromOrmOneToOneMapping(OrmOneToOneMapping oldMapping) {
		initializeFromOrmSingleRelationshipMapping(oldMapping);
	}

	public void initializeFromOrmManyToManyMapping(OrmManyToManyMapping oldMapping) {
		initializeFromOrmMultiRelationshipMapping(oldMapping);
	}

	public String getPrimaryKeyColumnName() {
		return null;
	}

	public OrmTypeMapping getTypeMapping() {
		return this.getPersistentAttribute().getTypeMapping();
	}


	public boolean isOverridableAttributeMapping() {
		return false;
	}

	public boolean isOverridableAssociationMapping() {
		return false;
	}

	public boolean isIdMapping() {
		return false;
	}
	
	public boolean isOwnedBy(RelationshipMapping mapping) {
		// Default implementation - override where needed
		return false;
	}
	
	public Iterator<String> allMappingNames() {
		return new SingleElementIterator<String>(getName());
	}
	
	public AttributeMapping resolveAttributeMapping(String name) {
		if (getName().equals(name)) {
			return this;
		}
		return null;
	}
	
	public Iterator<String> allOverrideableMappingNames() {
		if (isOverridableAttributeMapping()) {
			return allMappingNames();
		}
		return EmptyIterator.<String> instance();
	}
	
	public Column resolveOverridenColumn(String attributeName) {
		ColumnMapping columnMapping = this.resolveColumnMapping(attributeName);
		return columnMapping == null ? null : columnMapping.getColumn();
	}
	
	public ColumnMapping resolveColumnMapping(String name) {
		AttributeMapping attributeMapping = resolveAttributeMapping(name);
		if (attributeMapping != null && attributeMapping.isOverridableAttributeMapping()) {
			return (ColumnMapping) attributeMapping;
		}
		return null;
	}	

	public T getResourceAttributeMapping() {
		return this.resourceAttributeMapping;
	}
	
	public void update() {
		this.setName_(this.getResourceMappingName());
	}
	
	protected String getResourceMappingName() {
		return this.resourceAttributeMapping.getName();
	}
	
	public boolean shouldValidateAgainstDatabase() {
		return this.getTypeMapping().shouldValidateAgainstDatabase();
	}

	public boolean contains(int textOffset) {
		return this.resourceAttributeMapping.containsOffset(textOffset);
	}
	
	public TextRange getSelectionTextRange() {
		return this.resourceAttributeMapping.getSelectionTextRange();
	}	
	
	public TextRange getValidationTextRange() {
		return (this.getPersistentAttribute().isVirtual()) ? this.getTypeMapping().getAttributesTextRange() : this.resourceAttributeMapping.getValidationTextRange();
	}
	
	public TextRange getNameTextRange() {
		return this.resourceAttributeMapping.getNameTextRange();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
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

	protected void addMetamodelFieldTypeArgumentNamesTo(ArrayList<String> typeArgumentNames) {
		typeArgumentNames.add(this.getMetamodelTypeName());
	}

	public String getMetamodelTypeName() {
		JavaPersistentAttribute2_0 jpa = (JavaPersistentAttribute2_0) this.getPersistentAttribute().getJavaPersistentAttribute();
		return (jpa == null) ? MetamodelField.DEFAULT_TYPE_NAME : jpa.getMetamodelTypeName();
	}

	protected String getMetamodelFieldName() {
		return this.getName();
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
			return;
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
