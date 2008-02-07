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
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IAttributeMapping;
import org.eclipse.jpt.core.internal.context.base.IJpaStructureNode;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.JpaContextNode;
import org.eclipse.jpt.core.internal.resource.orm.AttributeMapping;
import org.eclipse.jpt.core.internal.resource.orm.Basic;
import org.eclipse.jpt.core.internal.resource.orm.Embedded;
import org.eclipse.jpt.core.internal.resource.orm.EmbeddedId;
import org.eclipse.jpt.core.internal.resource.orm.Id;
import org.eclipse.jpt.core.internal.resource.orm.ManyToMany;
import org.eclipse.jpt.core.internal.resource.orm.ManyToOne;
import org.eclipse.jpt.core.internal.resource.orm.OneToMany;
import org.eclipse.jpt.core.internal.resource.orm.OneToOne;
import org.eclipse.jpt.core.internal.resource.orm.Transient;
import org.eclipse.jpt.core.internal.resource.orm.Version;


public class XmlPersistentAttribute extends JpaContextNode
	implements IPersistentAttribute
{

	protected List<IXmlAttributeMappingProvider> attributeMappingProviders;

	protected XmlAttributeMapping<? extends AttributeMapping> attributeMapping;
	
	public XmlPersistentAttribute(XmlPersistentType parent, String mappingKey) {
		super(parent);
		this.attributeMappingProviders = buildAttributeMappingProviders();
		this.attributeMapping = buildAttributeMapping(mappingKey);
	}

	protected List<IXmlAttributeMappingProvider> buildAttributeMappingProviders() {
		List<IXmlAttributeMappingProvider> list = new ArrayList<IXmlAttributeMappingProvider>();
		list.add(XmlEmbeddedMappingProvider.instance()); //bug 190344 need to test default embedded before basic
		list.add(XmlBasicMappingProvider.instance());
		list.add(XmlTransientMappingProvider.instance());
		list.add(XmlIdMappingProvider.instance());
		list.add(XmlManyToManyMappingProvider.instance());
		list.add(XmlOneToManyMappingProvider.instance());
		list.add(XmlManyToOneMappingProvider.instance());
		list.add(XmlOneToOneMappingProvider.instance());
		list.add(XmlVersionMappingProvider.instance());
		list.add(XmlEmbeddedIdMappingProvider.instance());
		return list;
	}
	
	protected IXmlAttributeMappingProvider attributeMappingProvider(String key) {
		for (IXmlAttributeMappingProvider provider : this.attributeMappingProviders) {
			if (provider.key().equals(key)) {
				return provider;
			}
		}
		return XmlNullAttributeMappingProvider.instance();
	}

	protected XmlAttributeMapping<? extends AttributeMapping> buildAttributeMapping(String key) {
		return this.attributeMappingProvider(key).buildAttributeMapping(jpaFactory(), this);
	}
	
	public String getId() {
		return IOrmStructureNodes.PERSISTENT_ATTRIBUTE_ID;
	}

	public String getName() {
		return getMapping().getName();
	}

	protected void nameChanged(String oldName, String newName) {
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	public IAttributeMapping getSpecifiedMapping() {
		return this.attributeMapping;
	}
	
	public XmlAttributeMapping<? extends AttributeMapping> getMapping() {
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
		XmlAttributeMapping<? extends AttributeMapping> oldMapping = getMapping();
		this.attributeMapping = buildAttributeMapping(newMappingKey);
		//TODO persistentType().changeMapping(this, oldMapping, this.attributeMapping);
		firePropertyChanged(SPECIFIED_MAPPING_PROPERTY, oldMapping, this.attributeMapping);
	}
	
	protected void setSpecifiedMappingKey_(String newMappingKey) {
		if (this.mappingKey() == newMappingKey) {
			return;
		}
		XmlAttributeMapping<? extends AttributeMapping> oldMapping = getMapping();
		this.attributeMapping = buildAttributeMapping(newMappingKey);
		firePropertyChanged(SPECIFIED_MAPPING_PROPERTY, oldMapping, this.attributeMapping);
	}

	public Collection<IXmlAttributeMappingProvider> attributeMappingProviders() {
		return this.attributeMappingProviders;
	}

	public XmlPersistentType persistentType() {
		return (XmlPersistentType) parent();
	}

	public XmlTypeMapping<?> typeMapping() {
		return persistentType().getMapping();
	}

	public boolean isVirtual() {
		return persistentType().containsVirtualPersistentAttribute(this);
	}

	public void setVirtual(boolean virtual) {
		xmlPersistentType().setPersistentAttributeVirtual(this, virtual);
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
	public void initialize(Basic basic) {
		((XmlBasicMapping) getMapping()).initialize(basic);
	}
	
	public void initialize(Embedded embedded) {
		((XmlEmbeddedMapping) getMapping()).initialize(embedded);
	}
	
	public void initialize(Version version) {
		((XmlVersionMapping) getMapping()).initialize(version);
	}
	
	public void initialize(ManyToOne manyToOne) {
		((XmlManyToOneMapping) getMapping()).initialize(manyToOne);
	}
	
	public void initialize(OneToMany oneToMany) {
		((XmlOneToManyMapping) getMapping()).initialize(oneToMany);
	}
	
	public void initialize(OneToOne oneToOne) {
		((XmlOneToOneMapping) getMapping()).initialize(oneToOne);
	}
	
	public void initialize(ManyToMany manyToMany) {
		((XmlManyToManyMapping) getMapping()).initialize(manyToMany);
	}
	
	public void initialize(Id id) {
		((XmlIdMapping) getMapping()).initialize(id);
	}
	
	public void initialize(EmbeddedId embeddedId) {
		((XmlEmbeddedIdMapping) getMapping()).initialize(embeddedId);
	}
	
	public void initialize(Transient transientResource) {
		((XmlTransientMapping) getMapping()).initialize(transientResource);
	}
		
	public void update(Id id) {
		if (mappingKey() == IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY) {
			((XmlIdMapping) getMapping()).update(id);
		}
		else {
			setSpecifiedMappingKey_(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
			((XmlIdMapping) getMapping()).initialize(id);				
		}
	}
	
	public void update(EmbeddedId embeddedId) {
		if (mappingKey() == IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY) {
			((XmlEmbeddedIdMapping) getMapping()).update(embeddedId);
		}
		else {
			setSpecifiedMappingKey_(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
			((XmlEmbeddedIdMapping) getMapping()).initialize(embeddedId);				
		}
	}

	public void update(Basic basic) {
		if (mappingKey() == IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY) {
			((XmlBasicMapping) getMapping()).update(basic);
		}
		else {
			setSpecifiedMappingKey_(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
			((XmlBasicMapping) getMapping()).initialize(basic);				
		}
	}
	
	public void update(Version version) {
		if (mappingKey() == IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY) {
			((XmlVersionMapping) getMapping()).update(version);
		}
		else {
			setSpecifiedMappingKey_(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
			((XmlVersionMapping) getMapping()).initialize(version);				
		}
	}
	public void update(ManyToOne manyToOne) {
		if (mappingKey() == IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			((XmlManyToOneMapping) getMapping()).update(manyToOne);
		}
		else {
			setSpecifiedMappingKey_(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
			((XmlManyToOneMapping) getMapping()).initialize(manyToOne);				
		}
	}
	public void update(OneToMany oneToMany) {
		if (mappingKey() == IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			((XmlOneToManyMapping) getMapping()).update(oneToMany);
		}
		else {
			setSpecifiedMappingKey_(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
			((XmlOneToManyMapping) getMapping()).initialize(oneToMany);				
		}
	}
	public void update(OneToOne oneToOne) {
		if (mappingKey() == IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			((XmlOneToOneMapping) getMapping()).update(oneToOne);
		}
		else {
			setSpecifiedMappingKey_(IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
			((XmlOneToOneMapping) getMapping()).initialize(oneToOne);				
		}
	}
	public void update(ManyToMany manyToMany) {
		if (mappingKey() == IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			((XmlManyToManyMapping) getMapping()).update(manyToMany);
		}
		else {
			setSpecifiedMappingKey_(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
			((XmlManyToManyMapping) getMapping()).initialize(manyToMany);				
		}
	}

	public void update(Embedded embedded) {
		if (mappingKey() == IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY) {
			((XmlEmbeddedMapping) getMapping()).update(embedded);
		}
		else {
			setSpecifiedMappingKey_(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
			((XmlEmbeddedMapping) getMapping()).initialize(embedded);				
		}
	}
	
	public void update(Transient transientResource) {
		if (mappingKey() == IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY) {
			((XmlTransientMapping) getMapping()).update(transientResource);
		}
		else {
			setSpecifiedMappingKey_(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
			((XmlTransientMapping) getMapping()).initialize(transientResource);				
		}
	}
	
	public IJpaStructureNode structureNode(int offset) {
		return this;
	}

	public boolean containsOffset(int textOffset) {
		if (isVirtual()) {
			return false;
		}
		return attributeMapping.containsOffset(textOffset);
	}
	
	public ITextRange selectionTextRange() {
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
