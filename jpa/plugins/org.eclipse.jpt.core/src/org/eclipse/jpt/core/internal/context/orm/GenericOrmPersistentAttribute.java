/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmStructureNodes;
import org.eclipse.jpt.core.context.orm.OrmTransientMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.core.resource.orm.XmlBasic;
import org.eclipse.jpt.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddedId;
import org.eclipse.jpt.core.resource.orm.XmlId;
import org.eclipse.jpt.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.core.resource.orm.XmlTransient;
import org.eclipse.jpt.core.resource.orm.XmlVersion;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class GenericOrmPersistentAttribute extends AbstractOrmJpaContextNode
	implements OrmPersistentAttribute
{

	protected List<OrmAttributeMappingProvider> attributeMappingProviders;

	protected OrmAttributeMapping attributeMapping;
	
	public GenericOrmPersistentAttribute(OrmPersistentType parent, String mappingKey) {
		super(parent);
		this.attributeMappingProviders = buildAttributeMappingProviders();
		this.attributeMapping = buildAttributeMapping(mappingKey);
	}

	protected List<OrmAttributeMappingProvider> buildAttributeMappingProviders() {
		List<OrmAttributeMappingProvider> list = new ArrayList<OrmAttributeMappingProvider>();
		list.add(OrmEmbeddedMappingProvider.instance()); //bug 190344 need to test default embedded before basic
		list.add(OrmBasicMappingProvider.instance());
		list.add(OrmTransientMappingProvider.instance());
		list.add(OrmIdMappingProvider.instance());
		list.add(OrmManyToManyMappingProvider.instance());
		list.add(OrmOneToManyMappingProvider.instance());
		list.add(OrmManyToOneMappingProvider.instance());
		list.add(OrmOneToOneMappingProvider.instance());
		list.add(OrmVersionMappingProvider.instance());
		list.add(OrmEmbeddedIdMappingProvider.instance());
		return list;
	}
	
	protected OrmAttributeMappingProvider attributeMappingProvider(String key) {
		for (OrmAttributeMappingProvider provider : this.attributeMappingProviders) {
			if (provider.getKey().equals(key)) {
				return provider;
			}
		}
		return OrmNullAttributeMappingProvider.instance();
	}

	protected OrmAttributeMapping buildAttributeMapping(String key) {
		return this.attributeMappingProvider(key).buildAttributeMapping(getJpaFactory(), this);
	}
	
	public String getId() {
		return OrmStructureNodes.PERSISTENT_ATTRIBUTE_ID;
	}

	public String getName() {
		return getMapping().getName();
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
		return this.getMapping().getKey();
	}

	public String getDefaultMappingKey() {
		return null;
	}

	public void setSpecifiedMappingKey(String newMappingKey) {
		if (this.getMappingKey() == newMappingKey) {
			return;
		}
		OrmAttributeMapping oldMapping = getMapping();
		this.attributeMapping = buildAttributeMapping(newMappingKey);
		getPersistentType().changeMapping(this, oldMapping, this.attributeMapping);
		firePropertyChanged(SPECIFIED_MAPPING_PROPERTY, oldMapping, this.attributeMapping);
	}
	
	protected void setSpecifiedMappingKey_(String newMappingKey) {
		if (this.getMappingKey() == newMappingKey) {
			return;
		}
		OrmAttributeMapping oldMapping = getMapping();
		this.attributeMapping = buildAttributeMapping(newMappingKey);
		firePropertyChanged(SPECIFIED_MAPPING_PROPERTY, oldMapping, this.attributeMapping);
	}

	public Collection<OrmAttributeMappingProvider> attributeMappingProviders() {
		return this.attributeMappingProviders;
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

	public void setVirtual(boolean virtual) {
		getOrmPersistentType().setPersistentAttributeVirtual(this, virtual);
	}

	public String getPrimaryKeyColumnName() {
		return getMapping().getPrimaryKeyColumnName();
	}

//	@Override
//	public ITextRange fullTextRange() {
//		return (this.isVirtual()) ? null : super.fullTextRange();
//	}
//
//	@Override
//	public ITextRange validationTextRange() {
//		return (this.isVirtual()) ? this.persistentType().attributesTextRange() : this.getMapping().validationTextRange();
//	}
//
//	@Override
//	public ITextRange selectionTextRange() {
//		return (isVirtual()) ? null : this.getMapping().selectionTextRange();
//	}
//
//	public ITextRange nameTextRange() {
//		return getMapping().nameTextRange();
//	}

	public boolean isOverridableAttribute() {
		return this.getMapping().isOverridableAttributeMapping();
	}

	public boolean isOverridableAssociation() {
		return this.getMapping().isOverridableAssociationMapping();
	}

	public boolean isIdAttribute() {
		return this.getMapping().isIdMapping();
	}
	
	//TODO is there a way to avoid a method for every mapping type?
	//I am trying to take adavantage of generics here, but it sure is
	//leading to a lot of duplicated code. - KFM
	public void initialize(XmlBasic basic) {
		((OrmBasicMapping) getMapping()).initialize(basic);
	}
	
	public void initialize(XmlEmbedded embedded) {
		((OrmEmbeddedMapping) getMapping()).initialize(embedded);
	}
	
	public void initialize(XmlVersion version) {
		((OrmVersionMapping) getMapping()).initialize(version);
	}
	
	public void initialize(XmlManyToOne manyToOne) {
		((OrmManyToOneMapping) getMapping()).initialize(manyToOne);
	}
	
	public void initialize(XmlOneToMany oneToMany) {
		((OrmOneToManyMapping) getMapping()).initialize(oneToMany);
	}
	
	public void initialize(XmlOneToOne oneToOne) {
		((OrmOneToOneMapping) getMapping()).initialize(oneToOne);
	}
	
	public void initialize(XmlManyToMany manyToMany) {
		((OrmManyToManyMapping) getMapping()).initialize(manyToMany);
	}
	
	public void initialize(XmlId id) {
		((OrmIdMapping) getMapping()).initialize(id);
	}
	
	public void initialize(XmlEmbeddedId embeddedId) {
		((OrmEmbeddedIdMapping) getMapping()).initialize(embeddedId);
	}
	
	public void initialize(XmlTransient transientResource) {
		((OrmTransientMapping) getMapping()).initialize(transientResource);
	}
		
	public void update(XmlId id) {
		if (getMappingKey() == MappingKeys.ID_ATTRIBUTE_MAPPING_KEY) {
			((OrmIdMapping) getMapping()).update(id);
		}
		else {
			setSpecifiedMappingKey_(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
			((OrmIdMapping) getMapping()).initialize(id);				
		}
	}
	
	public void update(XmlEmbeddedId embeddedId) {
		if (getMappingKey() == MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY) {
			((OrmEmbeddedIdMapping) getMapping()).update(embeddedId);
		}
		else {
			setSpecifiedMappingKey_(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
			((OrmEmbeddedIdMapping) getMapping()).initialize(embeddedId);				
		}
	}

	public void update(XmlBasic basic) {
		if (getMappingKey() == MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY) {
			((OrmBasicMapping) getMapping()).update(basic);
		}
		else {
			setSpecifiedMappingKey_(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
			((OrmBasicMapping) getMapping()).initialize(basic);				
		}
	}
	
	public void update(XmlVersion version) {
		if (getMappingKey() == MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY) {
			((OrmVersionMapping) getMapping()).update(version);
		}
		else {
			setSpecifiedMappingKey_(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
			((OrmVersionMapping) getMapping()).initialize(version);				
		}
	}
	public void update(XmlManyToOne manyToOne) {
		if (getMappingKey() == MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			((OrmManyToOneMapping) getMapping()).update(manyToOne);
		}
		else {
			setSpecifiedMappingKey_(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
			((OrmManyToOneMapping) getMapping()).initialize(manyToOne);				
		}
	}
	public void update(XmlOneToMany oneToMany) {
		if (getMappingKey() == MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			((OrmOneToManyMapping) getMapping()).update(oneToMany);
		}
		else {
			setSpecifiedMappingKey_(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
			((OrmOneToManyMapping) getMapping()).initialize(oneToMany);				
		}
	}
	public void update(XmlOneToOne oneToOne) {
		if (getMappingKey() == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			((OrmOneToOneMapping) getMapping()).update(oneToOne);
		}
		else {
			setSpecifiedMappingKey_(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
			((OrmOneToOneMapping) getMapping()).initialize(oneToOne);				
		}
	}
	public void update(XmlManyToMany manyToMany) {
		if (getMappingKey() == MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			((OrmManyToManyMapping) getMapping()).update(manyToMany);
		}
		else {
			setSpecifiedMappingKey_(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
			((OrmManyToManyMapping) getMapping()).initialize(manyToMany);				
		}
	}

	public void update(XmlEmbedded embedded) {
		if (getMappingKey() == MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY) {
			((OrmEmbeddedMapping) getMapping()).update(embedded);
		}
		else {
			setSpecifiedMappingKey_(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
			((OrmEmbeddedMapping) getMapping()).initialize(embedded);				
		}
	}
	
	public void update(XmlTransient transientResource) {
		if (getMappingKey() == MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY) {
			((OrmTransientMapping) getMapping()).update(transientResource);
		}
		else {
			setSpecifiedMappingKey_(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
			((OrmTransientMapping) getMapping()).initialize(transientResource);				
		}
	}
	
	public JpaStructureNode structureNode(int offset) {
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
			return getPersistentType().getSelectionTextRange();
		}
		return this.attributeMapping.getSelectionTextRange();
	}
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		getMapping().addToMessages(messages);
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
}
