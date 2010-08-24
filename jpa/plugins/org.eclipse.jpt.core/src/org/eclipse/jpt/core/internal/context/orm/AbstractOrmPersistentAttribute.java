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

import java.util.List;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.CollectionMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingDefinition;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmStructureNodes;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.PersistentAttributeTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.core.jpa2.context.java.JavaPersistentAttribute2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmPersistentAttribute2_0;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * ORM persistent attribute
 */
public abstract class AbstractOrmPersistentAttribute
	extends AbstractOrmXmlContextNode
	implements OrmPersistentAttribute2_0
{
	protected final Owner owner;

	protected OrmAttributeMapping attributeMapping;

	protected JavaPersistentAttribute javaPersistentAttribute;	
	
	protected AccessType defaultAccess;
	
	protected AbstractOrmPersistentAttribute(OrmPersistentType parent, Owner owner, XmlAttributeMapping resourceMapping) {
		super(parent);
		this.owner = owner;
		this.attributeMapping = buildAttributeMapping(resourceMapping);
		this.javaPersistentAttribute = findJavaPersistentAttribute();
		this.defaultAccess = buildDefaultAccess();
	}
	
	public XmlAttributeMapping getResourceAttributeMapping() {
		return this.attributeMapping.getResourceAttributeMapping();
	}
	
	public JavaPersistentAttribute getJavaPersistentAttribute() {
		return this.javaPersistentAttribute;
	}
	
	protected void setJavaPersistentAttribute(JavaPersistentAttribute javaPersistentAttribute) {
		JavaPersistentAttribute old = this.javaPersistentAttribute;
		this.javaPersistentAttribute = javaPersistentAttribute;
		this.firePropertyChanged(JAVA_PERSISTENT_ATTRIBUTE_PROPERTY, old, javaPersistentAttribute);
	}

	protected OrmAttributeMapping buildAttributeMapping(XmlAttributeMapping resourceMapping) {
		OrmAttributeMappingDefinition mappingDefinition = 
				getMappingFileDefinition().getOrmAttributeMappingDefinition(resourceMapping.getMappingKey());
		return mappingDefinition.buildContextMapping(this, resourceMapping, getXmlContextNodeFactory());
	}
	
	public String getId() {
		return OrmStructureNodes.PERSISTENT_ATTRIBUTE_ID;
	}
	
	public AccessType getAccess() {
		 return getSpecifiedAccess() != null ? getSpecifiedAccess() : getDefaultAccess();
	}
	
	public AccessType getDefaultAccess() {
		return this.defaultAccess;
	}

	protected void setDefaultAccess(AccessType newAccess) {
		AccessType old = this.defaultAccess;
		this.defaultAccess = newAccess;
		firePropertyChanged(DEFAULT_ACCESS_PROPERTY, old, this.defaultAccess);
	}
	
	public String getName() {
		return this.attributeMapping.getName();
	}

	public void nameChanged(String oldName, String newName) {
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	public OrmAttributeMapping getSpecifiedMapping() {
		return this.attributeMapping;
	}
	
	public OrmAttributeMapping getMapping() {
		return this.attributeMapping;
	}

	public String getMappingKey() {
		return this.attributeMapping.getKey();
	}

	public String getDefaultMappingKey() {
		return null;
	}

	public void setSpecifiedMappingKey(String newMappingKey) {
		if (this.valuesAreEqual(this.getMappingKey(), newMappingKey)) {
			return;
		}
		OrmAttributeMapping oldMapping = this.attributeMapping;
		OrmAttributeMappingDefinition mappingDefinition = 
				getMappingFileDefinition().getOrmAttributeMappingDefinition(newMappingKey);
		XmlAttributeMapping resourceAttributeMapping = 
				mappingDefinition.buildResourceMapping(getResourceNodeFactory());
		this.attributeMapping = buildAttributeMapping(resourceAttributeMapping);
		
		getOwningPersistentType().changeMapping(this, oldMapping, this.attributeMapping);
		firePropertyChanged(SPECIFIED_MAPPING_PROPERTY, oldMapping, this.attributeMapping);
	}

	public OrmPersistentType getOwningPersistentType() {
		return (OrmPersistentType) getParent();
	}

	public OrmTypeMapping getOwningTypeMapping() {
		return getOwningPersistentType().getMapping();
	}

	public boolean isVirtual() {
		return getOwningPersistentType().containsVirtualAttribute(this);
	}

	public void makeVirtual() {
		if (isVirtual()) {
			throw new IllegalStateException("Attribute is already virtual"); //$NON-NLS-1$
		}
		getOwningPersistentType().makeAttributeVirtual(this);
	}
	
	public void makeSpecified() {
		if (!isVirtual()) {
			throw new IllegalStateException("Attribute is already specified"); //$NON-NLS-1$
		}
		if (getMappingKey() == MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY) {
			throw new IllegalStateException("Use makeSpecified(String) instead and specify a mapping type"); //$NON-NLS-1$
		}
		getOwningPersistentType().makeAttributeSpecified(this);
	}
	
	public void makeSpecified(String mappingKey) {
		if (!isVirtual()) {
			throw new IllegalStateException("Attribute is already specified"); //$NON-NLS-1$
		}
		getOwningPersistentType().makeAttributeSpecified(this, mappingKey);
	}
	
	public String getPrimaryKeyColumnName() {
		return this.attributeMapping.getPrimaryKeyColumnName();
	}
	
	public String getTypeName() {
		JavaPersistentAttribute javaAttribute = getJavaPersistentAttribute();
		return (javaAttribute == null) ? null : javaAttribute.getTypeName();
	}
	
	public void update() {
		this.attributeMapping.update();
		this.setJavaPersistentAttribute(findJavaPersistentAttribute());
		this.owner.updateJavaPersistentAttribute();
		this.setDefaultAccess(buildDefaultAccess());
	}

	@Override
	public void postUpdate() {
		super.postUpdate();
		getMapping().postUpdate();
	}
	
	protected JavaPersistentAttribute findJavaPersistentAttribute() {
		return this.owner.findJavaPersistentAttribute(this);
	}
	
	protected AccessType buildDefaultAccess() {
		return getOwningPersistentType().getAccess();
	}


	// ********** JpaStructureNode implementation **********

	public JpaStructureNode getStructureNode(int offset) {
		return this;
	}

	public boolean contains(int textOffset) {
		if (isVirtual()) {
			return false;
		}
		return this.attributeMapping.contains(textOffset);
	}
	
	public TextRange getSelectionTextRange() {
		if (isVirtual()) {
			return null;
		}
		return this.attributeMapping.getSelectionTextRange();
	}
	
	public void dispose() {
		//nothing to dispose
	}


	// ********** misc overrides **********

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}


	// ********** refactoring **********

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return this.attributeMapping.createRenameTypeEdits(originalType, newName);
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.attributeMapping.createMoveTypeEdits(originalType, newPackage);
	}

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.attributeMapping.createRenamePackageEdits(originalPackage, newName);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateAttribute(messages, reporter);
		this.attributeMapping.validate(messages, reporter);
	}
	
	protected void validateAttribute(List<IMessage> messages, IReporter reporter) {
		if (this.javaPersistentAttribute == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENT_ATTRIBUTE_UNRESOLVED_NAME,
					new String[] {this.getName(), this.getOwningPersistentType().getMapping().getClass_()},
					this.attributeMapping, 
					this.attributeMapping.getNameTextRange()
				)
			);
		}
		else {
			this.buildAttibuteValidator().validate(messages, reporter);
		}
	}
	
	protected PersistentAttributeTextRangeResolver buildTextRangeResolver() {
		return new OrmPersistentAttributeTextRangeResolver(this);
	}

	protected abstract JptValidator buildAttibuteValidator();

	public TextRange getValidationTextRange() {
		if (isVirtual()) {
			return getOwningPersistentType().getMapping().getAttributesTextRange();
		}
		return this.attributeMapping.getValidationTextRange();
	}


	// ********** metamodel **********
	
	public String getMetamodelContainerFieldTypeName() {
		return this.getJpaContainer().getMetamodelContainerFieldTypeName();
	}

	public String getMetamodelContainerFieldMapKeyTypeName() {
		return this.getJpaContainer().getMetamodelContainerFieldMapKeyTypeName((CollectionMapping) this.getMapping());
	}

	public String getMetamodelTypeName() {
		JavaPersistentAttribute2_0 javaAttribute = (JavaPersistentAttribute2_0) this.getJavaPersistentAttribute();
		return (javaAttribute == null) ? MetamodelField.DEFAULT_TYPE_NAME : javaAttribute.getMetamodelTypeName();
	}

	protected JavaPersistentAttribute.JpaContainer getJpaContainer() {
		JavaPersistentAttribute2_0 javaAttribute = (JavaPersistentAttribute2_0) getJavaPersistentAttribute();
		return javaAttribute == null ? JavaPersistentAttribute.JpaContainer.Null.instance() : javaAttribute.getJpaContainer();
	}

}
