/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.content.java.IJavaTypeMapping;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaEntity;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaTable;
import org.eclipse.jpt.core.internal.content.orm.XmlEntity;
import org.eclipse.jpt.core.internal.content.orm.XmlTable;
import org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping;
import org.eclipse.jpt.core.internal.mappings.IAssociationOverride;
import org.eclipse.jpt.core.internal.mappings.IAttributeOverride;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.ISecondaryTable;
import org.eclipse.jpt.core.internal.platform.XmlAttributeOverrideContext.ParentContext;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class XmlEntityContext extends XmlTypeContext implements XmlSecondaryTableContext.ParentContext
{
	private TableContext tableContext;
	
	private Collection<XmlSecondaryTableContext> secondaryTableContexts;
	
	private JavaTable javaTable;
	
	private Collection<XmlAttributeOverrideContext> attributeOverrideContexts;

	private Collection<AssociationOverrideContext> associationOverrideContexts;

	public XmlEntityContext(MappingFileContext parentContext, XmlEntity xmlEntity) {
		super(parentContext, (XmlTypeMapping) xmlEntity);
		this.attributeOverrideContexts = buildAttributeOverrideContexts();
		this.associationOverrideContexts = buildAssociationOverrideContexts();
		this.secondaryTableContexts = buildSecondaryTableContexts();
	}
	
	protected Collection<XmlAttributeOverrideContext> buildAttributeOverrideContexts() {
		Collection<XmlAttributeOverrideContext> contexts = new ArrayList<XmlAttributeOverrideContext>();
		for (IAttributeOverride attributeOverride : getEntity().getAttributeOverrides()) {
			contexts.add(new XmlAttributeOverrideContext(buildParentContext(), attributeOverride));
		}
		
		return contexts;
	}
	
	private ParentContext buildParentContext() {
		return new XmlAttributeOverrideContext.ParentContext() {
			public void refreshDefaults(DefaultsContext defaults, IProgressMonitor monitor) {
				XmlEntityContext.this.refreshDefaults(defaults, monitor);
			}
			public IJpaPlatform getPlatform() {
				return XmlEntityContext.this.getPlatform();
			}
			public IContext getParentContext() {
				return XmlEntityContext.this.getParentContext();
			}
			public void addToMessages(List<IMessage> messages) {
				XmlEntityContext.this.addToMessages(messages);
			}
			public IAttributeOverride javaAttributeOverride(String overrideName) {
				JavaEntity javaEntity = getJavaEntity();
				if (javaEntity == null) {
					return null;
				}
				return javaEntity.attributeOverrideNamed(overrideName);
			}
		};
	}
	
	//only support default joinColumn information for the default association overrides,
	//AssociationOverride has no defaults, the name and joinColumns must be specified
	protected Collection<AssociationOverrideContext> buildAssociationOverrideContexts() {
		Collection<AssociationOverrideContext> contexts = new ArrayList<AssociationOverrideContext>();
		for (IAssociationOverride associationOverride : getEntity().getDefaultAssociationOverrides()) {
			contexts.add(new AssociationOverrideContext(this, associationOverride));
		}
		
		return contexts;
	}

	protected Collection<XmlSecondaryTableContext> buildSecondaryTableContexts() {
		Collection<XmlSecondaryTableContext> contexts = new ArrayList<XmlSecondaryTableContext>();
		for (ISecondaryTable secondaryTable : getEntity().getSecondaryTables()) {
			contexts.add(new XmlSecondaryTableContext(this, secondaryTable));
		}
		
		return contexts;
	}
	
	public ISecondaryTable javaSecondaryTable(int index) {
		JavaEntity javaEntity = getJavaEntity();
		return javaEntity == null ? null : javaEntity.getSpecifiedSecondaryTables().get(index);
	}

	protected XmlEntity getEntity() {
		return (XmlEntity) getXmlTypeMapping();
	}

	protected JavaEntity getJavaEntity() {
		IJavaTypeMapping javaTypeMapping = javaTypeMapping();
		if (javaTypeMapping != null
				&& javaTypeMapping.getKey() == IMappingKeys.ENTITY_TYPE_MAPPING_KEY) {
			return (JavaEntity) javaTypeMapping;
		}
		return null;
	}
	
	@Override
	public void initialize() {
		super.initialize();
		IJavaTypeMapping javaTypeMapping = javaTypeMapping();
		XmlTable xmlTable = (XmlTable)((XmlEntity) getXmlTypeMapping()).getTable();
		if (javaTypeMapping instanceof IEntity) {
			this.javaTable = (JavaTable) ((IEntity) javaTypeMapping).getTable();
		}
		this.tableContext = new TableContext(this, xmlTable);
	}
	
	/**
	 * XmlIdContexts will populate the generatorRepository themselves.
	 * XmlAttributeContexts are not built until the initialize method, so we 
	 * don't have any yet to populate the generatorRepository.
	 */
	protected void populateGeneratorRepository(GeneratorRepository generatorRepository) {
		if (getEntity().getTableGenerator() != null)  {
			generatorRepository.addGenerator(getEntity().getTableGenerator());
		}
		if (getEntity().getSequenceGenerator() != null)  {
			generatorRepository.addGenerator(getEntity().getSequenceGenerator());
		}
	}
	
	public PersistenceUnitContext getPersistenceUnitContext() {
		return ((MappingFileContext) getParentContext()).getPersistenceUnitContext();
	}

	@Override
	public void refreshDefaults(DefaultsContext parentDefaults, IProgressMonitor monitor) {
		super.refreshDefaults(parentDefaults, monitor);
		if (monitor.isCanceled()) {
			return;
		}
		DefaultsContext defaultsContext = wrapDefaultsContext(parentDefaults);
		refreshDefaultAttributeOverrides();
		refreshDefaultAssociationOverrides();
		refreshDefaultSecondaryTables();
		for (XmlSecondaryTableContext context : this.secondaryTableContexts) {
			context.refreshDefaults(defaultsContext, monitor);
		}
		for (XmlAttributeOverrideContext context : this.attributeOverrideContexts) {
			context.refreshDefaults(defaultsContext, monitor);
		}
		for (AssociationOverrideContext context : this.associationOverrideContexts) {
			context.refreshDefaults(defaultsContext, monitor);
		}
	}
	
	protected void refreshTableContext(DefaultsContext defaultsContext, IProgressMonitor monitor) {
		this.tableContext.refreshDefaults(defaultsContext, monitor);
	}
	
	protected void refreshDefaultAttributeOverrides() {
		
		JavaEntity javaEntity = getJavaEntity();
		if (javaEntity != null && !getXmlTypeMapping().isXmlMetadataComplete()) {
			for (IAttributeOverride attributeOverride : javaEntity.getAttributeOverrides()) {
				if (!getEntity().containsAttributeOverride(attributeOverride.getName())) {
					IAttributeOverride defaultAttributeOverride = getEntity().createAttributeOverride(0);
					defaultAttributeOverride.setName(attributeOverride.getName());
					getEntity().getDefaultAttributeOverrides().add(defaultAttributeOverride);
				}
			}
		}
		else {
			for (Iterator<String> i = getEntity().allOverridableAttributeNames(); i.hasNext(); ) {
				String override = i.next();
				if (!getEntity().containsAttributeOverride(override)) {
					IAttributeOverride defaultAttributeOverride = getEntity().createAttributeOverride(0);
					defaultAttributeOverride.setName(override);
					getEntity().getDefaultAttributeOverrides().add(defaultAttributeOverride);
				}
				
			}
		}
		
		Collection<String> attributeNames = CollectionTools.collection(getEntity().allOverridableAttributeNames());
	
		//remove any default mappings that are not included in the attributeNames collection
		Collection<IAttributeOverride> overridesToRemove = new ArrayList<IAttributeOverride>();
		for (IAttributeOverride attributeOverride : getEntity().getDefaultAttributeOverrides()) {
			if (getEntity().containsSpecifiedAttributeOverride(attributeOverride.getName())) {
				overridesToRemove.add(attributeOverride);					
			}
			else if (!attributeNames.contains(attributeOverride.getName())) {
				if (javaEntity == null) {
					overridesToRemove.add(attributeOverride);					
				}
				else if (getXmlTypeMapping().isXmlMetadataComplete()) {
					if (!javaEntity.containsDefaultAttributeOverride(attributeOverride.getName())) {
						overridesToRemove.add(attributeOverride);											
					}
				}
				else if (!javaEntity.containsSpecifiedAttributeOverride(attributeOverride.getName())) {
					overridesToRemove.add(attributeOverride);
				}
			}
		}
		
		getEntity().getDefaultAttributeOverrides().removeAll(overridesToRemove);
	}
	
	protected void refreshDefaultAssociationOverrides() {
		
		JavaEntity javaEntity = getJavaEntity();
		if (javaEntity != null && !getXmlTypeMapping().isXmlMetadataComplete()) {
			for (IAssociationOverride associationOverride : javaEntity.getAssociationOverrides()) {
				if (!getEntity().containsAssociationOverride(associationOverride.getName())) {
					IAssociationOverride defaultAssociationOverride = getEntity().createAssociationOverride(0);
					defaultAssociationOverride.setName(associationOverride.getName());
					getEntity().getDefaultAssociationOverrides().add(defaultAssociationOverride);
				}
			}
		}
		else {
			for (Iterator<String> i = getEntity().allOverridableAssociationNames(); i.hasNext(); ) {
				String override = i.next();
				if (!getEntity().containsAssociationOverride(override)) {
					IAssociationOverride defaultAssociationOverride = getEntity().createAssociationOverride(0);
					defaultAssociationOverride.setName(override);
					getEntity().getDefaultAssociationOverrides().add(defaultAssociationOverride);
				}
				
			}
		}
		
		Collection<String> attributeNames = CollectionTools.collection(getEntity().allOverridableAssociationNames());
	
		//remove any default mappings that are not included in the attributeNames collection
		Collection<IAssociationOverride> overridesToRemove = new ArrayList<IAssociationOverride>();
		for (IAssociationOverride associationOverride : getEntity().getDefaultAssociationOverrides()) {
			if (getEntity().containsSpecifiedAssociationOverride(associationOverride.getName())) {
				overridesToRemove.add(associationOverride);					
			}
			else if (!attributeNames.contains(associationOverride.getName())) {
				if (javaEntity == null) {
					overridesToRemove.add(associationOverride);					
				}
				else if (getXmlTypeMapping().isXmlMetadataComplete()) {
					if (!javaEntity.containsDefaultAssociationOverride(associationOverride.getName())) {
						overridesToRemove.add(associationOverride);											
					}
				}
				else if (!javaEntity.containsSpecifiedAssociationOverride(associationOverride.getName())) {
					overridesToRemove.add(associationOverride);
				}
			}
		}
		
		getEntity().getDefaultAssociationOverrides().removeAll(overridesToRemove);
	}
	
	protected void refreshDefaultSecondaryTables() {
		JavaEntity javaEntity = getJavaEntity();
		if (javaEntity != null && !getXmlTypeMapping().isXmlMetadataComplete()) {
			for (ISecondaryTable secondaryTable : javaEntity.getSecondaryTables()) {
				if (secondaryTable.getName() != null) {
					if (!getEntity().containsSecondaryTable(secondaryTable.getName())) {
						ISecondaryTable defaultSecondaryTable = getEntity().createSecondaryTable(0);
						defaultSecondaryTable.setSpecifiedName(secondaryTable.getName());
						getEntity().getVirtualSecondaryTables().add(defaultSecondaryTable);
					}
				}
			}
		}
		Collection<ISecondaryTable> secondaryTablesToRemove = new ArrayList<ISecondaryTable>();
		
		for (Iterator<ISecondaryTable> i = getEntity().getVirtualSecondaryTables().iterator(); i.hasNext(); ) {
			ISecondaryTable secondaryTable = i.next();
			
			if (getXmlTypeMapping().isXmlMetadataComplete() || getEntity().containsSpecifiedSecondaryTable(secondaryTable.getName())) {
				secondaryTablesToRemove.add(secondaryTable);
				continue;
			}
			if (javaEntity == null) {
				secondaryTablesToRemove.add(secondaryTable);
			}
			else if (!javaEntity.containsSecondaryTable(secondaryTable.getName())) {
				secondaryTablesToRemove.add(secondaryTable);			
			}
		}
		getEntity().getVirtualSecondaryTables().removeAll(secondaryTablesToRemove);
		
	}
	
	@Override
	public DefaultsContext wrapDefaultsContext(DefaultsContext defaultsContext) {
		return super.wrapDefaultsContext(this.tableContext.wrapDefaultsContext(defaultsContext));
	}

	@Override
	protected Object getDefault(String key, DefaultsContext defaultsContext) {
		if (key.equals(BaseJpaPlatform.DEFAULT_ENTITY_NAME_KEY)) {
			String className = getPersistentType().getClass_();
			if (className != null) {
				return ClassTools.shortNameForClassNamed(className);
			}
		}
		
		XmlTable xmlTable = getXmlTable();
		if (javaTable != null) {
			if (getXmlTypeMapping().isXmlMetadataComplete() || xmlTable.getNode() != null) {
				if (key.equals(BaseJpaPlatform.DEFAULT_TABLE_SCHEMA_KEY)) {
					return javaTable.getDefaultSchema();
				}
				if (key.equals(BaseJpaPlatform.DEFAULT_TABLE_CATALOG_KEY)) {
					return javaTable.getDefaultCatalog();
				}
				if (key.equals(BaseJpaPlatform.DEFAULT_TABLE_NAME_KEY)) {
					return javaTable.getDefaultName();
				}
			}
			else {
				if (key.equals(BaseJpaPlatform.DEFAULT_TABLE_SCHEMA_KEY)) {
					return javaTable.getSchema();
				}
				if (key.equals(BaseJpaPlatform.DEFAULT_TABLE_CATALOG_KEY)) {
					return javaTable.getCatalog();
				}
				if (key.equals(BaseJpaPlatform.DEFAULT_TABLE_NAME_KEY)) {
					return javaTable.getName();
				}
			}
		}
		else if (key.equals(BaseJpaPlatform.DEFAULT_TABLE_NAME_KEY)) {
			if (getEntity().rootEntity().getInheritanceStrategy().isSingleTable()) {
				IEntity rootEntity = getEntity().rootEntity();
				if (rootEntity == getEntity()) {
					return rootEntity.getName();
				}
				return rootEntity.getTable().getName();
			}
			return getEntity().getName();
		}
		return super.getDefault(key, defaultsContext);
	}

	private XmlTable getXmlTable() {
		return (XmlTable) ((XmlEntity) getXmlTypeMapping()).getTable();
	}
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		tableContext.addToMessages(messages);	
		addIdMessages(messages);
		
		for (SecondaryTableContext context : secondaryTableContexts) {
			context.addToMessages(messages);
		}

		for (XmlAttributeOverrideContext aoContext : attributeOverrideContexts) {
			aoContext.addToMessages(messages);
		}
		
		for (AssociationOverrideContext aoContext : associationOverrideContexts) {
			aoContext.addToMessages(messages);
		}
	}
	
	protected void addIdMessages(List<IMessage> messages) {
		addNoIdMessage(messages);
		
	}
	
	protected void addNoIdMessage(List<IMessage> messages) {
		IEntity entity = getEntity();
		if (entityHasNoId()) {
			messages.add(
				JpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					IJpaValidationMessages.ENTITY_NO_ID,
					new String[] {entity.getName()},
					entity, entity.validationTextRange())
			);
		}
	}
	
	private boolean entityHasNoId() {
		return ! this.entityHasId();
	}

	private boolean entityHasId() {
		for (Iterator<IPersistentAttribute> stream = this.getEntity().getPersistentType().allAttributes(); stream.hasNext(); ) {
			if (stream.next().isIdAttribute()) {
				return true;
			}
		}
		return false;
	}

}
