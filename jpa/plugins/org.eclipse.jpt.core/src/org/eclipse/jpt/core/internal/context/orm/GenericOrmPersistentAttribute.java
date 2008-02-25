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
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmStructureNodes;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.AbstractJpaContextNode;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
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


public class GenericOrmPersistentAttribute extends AbstractJpaContextNode
	implements OrmPersistentAttribute
{

	protected List<OrmAttributeMappingProvider> attributeMappingProviders;

	protected AbstractOrmAttributeMapping<? extends XmlAttributeMapping> attributeMapping;
	
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
			if (provider.key().equals(key)) {
				return provider;
			}
		}
		return OrmNullAttributeMappingProvider.instance();
	}

	protected AbstractOrmAttributeMapping<? extends XmlAttributeMapping> buildAttributeMapping(String key) {
		return this.attributeMappingProvider(key).buildAttributeMapping(jpaFactory(), this);
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

	public AbstractOrmAttributeMapping getSpecifiedMapping() {
		return this.attributeMapping;
	}
	
	public AbstractOrmAttributeMapping<? extends XmlAttributeMapping> getMapping() {
		return this.attributeMapping;
	}

	public String mappingKey() {
		return this.getMapping().getKey();
	}

	public String defaultMappingKey() {
		return null;
	}

	public void setSpecifiedMappingKey(String newMappingKey) {
		if (this.mappingKey() == newMappingKey) {
			return;
		}
		AbstractOrmAttributeMapping<? extends XmlAttributeMapping> oldMapping = getMapping();
		this.attributeMapping = buildAttributeMapping(newMappingKey);
		persistentType().changeMapping(this, oldMapping, this.attributeMapping);
		firePropertyChanged(SPECIFIED_MAPPING_PROPERTY, oldMapping, this.attributeMapping);
	}
	
	protected void setSpecifiedMappingKey_(String newMappingKey) {
		if (this.mappingKey() == newMappingKey) {
			return;
		}
		AbstractOrmAttributeMapping<? extends XmlAttributeMapping> oldMapping = getMapping();
		this.attributeMapping = buildAttributeMapping(newMappingKey);
		firePropertyChanged(SPECIFIED_MAPPING_PROPERTY, oldMapping, this.attributeMapping);
	}

	public Collection<OrmAttributeMappingProvider> attributeMappingProviders() {
		return this.attributeMappingProviders;
	}

	public OrmPersistentType persistentType() {
		return (OrmPersistentType) parent();
	}

	public OrmTypeMapping<?> typeMapping() {
		return persistentType().getMapping();
	}

	public boolean isVirtual() {
		return persistentType().containsVirtualPersistentAttribute(this);
	}

	public void setVirtual(boolean virtual) {
		ormPersistentType().setPersistentAttributeVirtual(this, virtual);
	}

	public String primaryKeyColumnName() {
		return getMapping().primaryKeyColumnName();
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
		((GenericOrmBasicMapping) getMapping()).initialize(basic);
	}
	
	public void initialize(XmlEmbedded embedded) {
		((GenericOrmEmbeddedMapping) getMapping()).initialize(embedded);
	}
	
	public void initialize(XmlVersion version) {
		((GenericOrmVersionMapping) getMapping()).initialize(version);
	}
	
	public void initialize(XmlManyToOne manyToOne) {
		((GenericOrmManyToOneMapping) getMapping()).initialize(manyToOne);
	}
	
	public void initialize(XmlOneToMany oneToMany) {
		((GenericOrmOneToManyMapping) getMapping()).initialize(oneToMany);
	}
	
	public void initialize(XmlOneToOne oneToOne) {
		((GenericOrmOneToOneMapping) getMapping()).initialize(oneToOne);
	}
	
	public void initialize(XmlManyToMany manyToMany) {
		((GenericOrmManyToManyMapping) getMapping()).initialize(manyToMany);
	}
	
	public void initialize(XmlId id) {
		((GenericOrmIdMapping) getMapping()).initialize(id);
	}
	
	public void initialize(XmlEmbeddedId embeddedId) {
		((GenericOrmEmbeddedIdMapping) getMapping()).initialize(embeddedId);
	}
	
	public void initialize(XmlTransient transientResource) {
		((GenericOrmTransientMapping) getMapping()).initialize(transientResource);
	}
		
	public void update(XmlId id) {
		if (mappingKey() == MappingKeys.ID_ATTRIBUTE_MAPPING_KEY) {
			((GenericOrmIdMapping) getMapping()).update(id);
		}
		else {
			setSpecifiedMappingKey_(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
			((GenericOrmIdMapping) getMapping()).initialize(id);				
		}
	}
	
	public void update(XmlEmbeddedId embeddedId) {
		if (mappingKey() == MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY) {
			((GenericOrmEmbeddedIdMapping) getMapping()).update(embeddedId);
		}
		else {
			setSpecifiedMappingKey_(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
			((GenericOrmEmbeddedIdMapping) getMapping()).initialize(embeddedId);				
		}
	}

	public void update(XmlBasic basic) {
		if (mappingKey() == MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY) {
			((GenericOrmBasicMapping) getMapping()).update(basic);
		}
		else {
			setSpecifiedMappingKey_(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
			((GenericOrmBasicMapping) getMapping()).initialize(basic);				
		}
	}
	
	public void update(XmlVersion version) {
		if (mappingKey() == MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY) {
			((GenericOrmVersionMapping) getMapping()).update(version);
		}
		else {
			setSpecifiedMappingKey_(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
			((GenericOrmVersionMapping) getMapping()).initialize(version);				
		}
	}
	public void update(XmlManyToOne manyToOne) {
		if (mappingKey() == MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			((GenericOrmManyToOneMapping) getMapping()).update(manyToOne);
		}
		else {
			setSpecifiedMappingKey_(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
			((GenericOrmManyToOneMapping) getMapping()).initialize(manyToOne);				
		}
	}
	public void update(XmlOneToMany oneToMany) {
		if (mappingKey() == MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			((GenericOrmOneToManyMapping) getMapping()).update(oneToMany);
		}
		else {
			setSpecifiedMappingKey_(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
			((GenericOrmOneToManyMapping) getMapping()).initialize(oneToMany);				
		}
	}
	public void update(XmlOneToOne oneToOne) {
		if (mappingKey() == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			((GenericOrmOneToOneMapping) getMapping()).update(oneToOne);
		}
		else {
			setSpecifiedMappingKey_(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
			((GenericOrmOneToOneMapping) getMapping()).initialize(oneToOne);				
		}
	}
	public void update(XmlManyToMany manyToMany) {
		if (mappingKey() == MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			((GenericOrmManyToManyMapping) getMapping()).update(manyToMany);
		}
		else {
			setSpecifiedMappingKey_(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
			((GenericOrmManyToManyMapping) getMapping()).initialize(manyToMany);				
		}
	}

	public void update(XmlEmbedded embedded) {
		if (mappingKey() == MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY) {
			((GenericOrmEmbeddedMapping) getMapping()).update(embedded);
		}
		else {
			setSpecifiedMappingKey_(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
			((GenericOrmEmbeddedMapping) getMapping()).initialize(embedded);				
		}
	}
	
	public void update(XmlTransient transientResource) {
		if (mappingKey() == MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY) {
			((GenericOrmTransientMapping) getMapping()).update(transientResource);
		}
		else {
			setSpecifiedMappingKey_(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
			((GenericOrmTransientMapping) getMapping()).initialize(transientResource);				
		}
	}
	
	public JpaStructureNode structureNode(int offset) {
		return this;
	}

	public boolean contains(int textOffset) {
		if (isVirtual()) {
			return false;
		}
		return attributeMapping.containsOffset(textOffset);
	}
	
	public TextRange selectionTextRange() {
		if (isVirtual()) {
			return persistentType().selectionTextRange();
		}
		return attributeMapping.selectionTextRange();
	}
	
	
	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(getName());
	}
}
