/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmStructureNodes;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.persistence.AbstractXmlContextNode;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class GenericOrmPersistentAttribute extends AbstractXmlContextNode
	implements OrmPersistentAttribute
{
	protected OrmAttributeMapping attributeMapping;
	
	public GenericOrmPersistentAttribute(OrmPersistentType parent, String mappingKey) {
		super(parent);
		this.attributeMapping = buildAttributeMapping(mappingKey);
	}

	protected OrmAttributeMapping buildAttributeMapping(String key) {
		return getJpaPlatform().buildOrmAttributeMappingFromMappingKey(key, this);
	}
	
	public String getId() {
		return OrmStructureNodes.PERSISTENT_ATTRIBUTE_ID;
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
		if (this.getMappingKey() == newMappingKey) {
			return;
		}
		OrmAttributeMapping oldMapping = this.attributeMapping;
		this.attributeMapping = buildAttributeMapping(newMappingKey);
		getPersistentType().changeMapping(this, oldMapping, this.attributeMapping);
		firePropertyChanged(SPECIFIED_MAPPING_PROPERTY, oldMapping, this.attributeMapping);
	}
	
	protected void setSpecifiedMappingKey_(String newMappingKey) {
		if (this.getMappingKey() == newMappingKey) {
			return;
		}
		OrmAttributeMapping oldMapping = this.attributeMapping;
		this.attributeMapping = buildAttributeMapping(newMappingKey);
		firePropertyChanged(SPECIFIED_MAPPING_PROPERTY, oldMapping, this.attributeMapping);
	}

	public OrmPersistentType getPersistentType() {
		return (OrmPersistentType) getParent();
	}

	public OrmTypeMapping getTypeMapping() {
		return getPersistentType().getMapping();
	}

	public boolean isVirtual() {
		return getPersistentType().containsVirtualPersistentAttribute(this);
	}

	public void makeVirtual() {
		if (isVirtual()) {
			throw new IllegalStateException("Attribute is already virtual"); //$NON-NLS-1$
		}
		getPersistentType().makePersistentAttributeVirtual(this);
	}
	
	public void makeSpecified() {
		if (!isVirtual()) {
			throw new IllegalStateException("Attribute is already specified"); //$NON-NLS-1$
		}
		if (getMappingKey() == MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY) {
			throw new IllegalStateException("Use makeSpecified(String) instead and specify a mapping type"); //$NON-NLS-1$
		}
		getPersistentType().makePersistentAttributeSpecified(this);
	}
	
	public void makeSpecified(String mappingKey) {
		if (!isVirtual()) {
			throw new IllegalStateException("Attribute is already specified"); //$NON-NLS-1$
		}
		getPersistentType().makePersistentAttributeSpecified(this, mappingKey);
	}
	
	public String getPrimaryKeyColumnName() {
		return this.attributeMapping.getPrimaryKeyColumnName();
	}

	public boolean isOverridableAttribute() {
		return this.attributeMapping.isOverridableAttributeMapping();
	}

	public boolean isOverridableAssociation() {
		return this.attributeMapping.isOverridableAssociationMapping();
	}

	public boolean isIdAttribute() {
		return this.attributeMapping.isIdMapping();
	}
	
	public void initialize(XmlAttributeMapping attributeMapping) {
		this.attributeMapping.initialize(attributeMapping);
	}
	
	public void update() {
		this.attributeMapping.update();
	}
	
	public JpaStructureNode getStructureNode(@SuppressWarnings("unused") int offset) {
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
	
	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		this.attributeMapping.validate(messages);
	}
	
	public TextRange getValidationTextRange() {
		if (isVirtual()) {
			return getPersistentType().getMapping().getAttributesTextRange();
		}
		return this.attributeMapping.getValidationTextRange();
	}

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(getName());
	}
	
	public void dispose() {
		//nothing to dispose
	}
}
