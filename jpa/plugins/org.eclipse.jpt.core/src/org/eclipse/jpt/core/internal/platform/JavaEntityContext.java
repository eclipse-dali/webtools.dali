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
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaAssociationOverride;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaAttributeOverride;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaEntity;
import org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsFactory;
import org.eclipse.jpt.core.internal.mappings.IAssociationOverride;
import org.eclipse.jpt.core.internal.mappings.IAttributeOverride;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.mappings.ISecondaryTable;
import org.eclipse.jpt.core.internal.mappings.ISequenceGenerator;
import org.eclipse.jpt.core.internal.mappings.ITable;
import org.eclipse.jpt.core.internal.mappings.ITableGenerator;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class JavaEntityContext extends JavaTypeContext
{
	private TableContext tableContext;

	private Collection<SecondaryTableContext> secondaryTableContexts;

	private Collection<AttributeOverrideContext> attributeOverrideContexts;
	
	private Collection<AssociationOverrideContext> associationOverrideContexts;
	
	private Collection<PrimaryKeyJoinColumnContext> pkJoinColumnContexts;
	
	public JavaEntityContext(IContext parentContext, JavaEntity javaEntity) {
		super(parentContext, javaEntity);
		this.tableContext = buildTableContext(javaEntity);
		this.attributeOverrideContexts = buildAttributeOverrideContexts();
		this.associationOverrideContexts = buildAssociationOverrideContexts();
		this.secondaryTableContexts = buildSecondaryTableContexts();
		this.pkJoinColumnContexts = buildPkJoinColumnContexts();
	}
	
	protected JavaEntity getEntity() {
		return (JavaEntity) getTypeMapping();
	}
	
	protected TableContext buildTableContext(JavaEntity javaEntity) {
		return new TableContext(this, javaEntity.getTable());
	}

	protected Collection<AttributeOverrideContext> buildAttributeOverrideContexts() {
		Collection<AttributeOverrideContext> contexts = new ArrayList<AttributeOverrideContext>();
		for (IAttributeOverride attributeOverride : getEntity().getAttributeOverrides()) {
			contexts.add(new AttributeOverrideContext(this, attributeOverride));
		}
		
		return contexts;
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
	
	protected Collection<SecondaryTableContext> buildSecondaryTableContexts() {
		Collection<SecondaryTableContext> contexts = new ArrayList<SecondaryTableContext>();
		for (ISecondaryTable secondaryTable : getEntity().getSecondaryTables()) {
			contexts.add(new SecondaryTableContext(this, secondaryTable));
		}
		
		return contexts;
	}
	
	protected Collection<PrimaryKeyJoinColumnContext> buildPkJoinColumnContexts() {
		Collection<PrimaryKeyJoinColumnContext> contexts = new ArrayList<PrimaryKeyJoinColumnContext>();
		for (IPrimaryKeyJoinColumn pkJoinColumn : getEntity().getPrimaryKeyJoinColumns()) {
			contexts.add(new PrimaryKeyJoinColumnContext(this, pkJoinColumn));
		}
		
		return contexts;
	}
	
	/**
	 * Mappings files will populate the generator repository after Java files.
	 * This will cause the mapping files to override any generators with the same name
	 * in the java
	 */
	protected void populateGeneratorRepository(GeneratorRepository generatorRepository) {
		ITableGenerator tableGenerator = getEntity().getTableGenerator();
		if (tableGenerator != null)  {
			generatorRepository.addGenerator(tableGenerator);
		}
		ISequenceGenerator sequenceGenerator = getEntity().getSequenceGenerator();
		if (sequenceGenerator != null)  {
			generatorRepository.addGenerator(sequenceGenerator);
		}
	}

	public void refreshDefaults(DefaultsContext defaultsContext) {
		defaultsContext = wrapDefaultsContext(defaultsContext);
		super.refreshDefaults(defaultsContext);
		
		if (this.tableContext != null) {
			this.tableContext.refreshDefaults(defaultsContext);
		}
		refreshDefaultAttributeOverrides();
		refreshDefaultAssociationOverrides();
		for (SecondaryTableContext context : this.secondaryTableContexts) {
			context.refreshDefaults(defaultsContext);
		}
		for (AttributeOverrideContext context : this.attributeOverrideContexts) {
			context.refreshDefaults(defaultsContext);
		}
		for (AssociationOverrideContext context : this.associationOverrideContexts) {
			context.refreshDefaults(defaultsContext);
		}
		for (PrimaryKeyJoinColumnContext context : this.pkJoinColumnContexts) {
			context.refreshDefaults(defaultsContext);
		}
	}
	
	public DefaultsContext wrapDefaultsContext(final DefaultsContext defaultsContext) {
		DefaultsContext wrappedDefaultsContext = new DefaultsContext() {
			public Object getDefault(String key) {
				if (key.equals(BaseJpaPlatform.DEFAULT_TABLE_NAME_KEY)) {
					if (getEntity().rootEntity().getInheritanceStrategy().isSingleTable()) {
						IEntity rootEntity = getEntity().rootEntity();
						if (rootEntity == getEntity()) {
							return rootEntity.getName();
						}
						return rootEntity.getTable().getName();
					}
					return getEntity().getName();
				}
				return defaultsContext.getDefault(key);
			}
		
			public IPersistentType persistentType(String fullyQualifiedTypeName) {
				return defaultsContext.persistentType(fullyQualifiedTypeName);
			}
		};
		if (this.tableContext != null) {
			return this.tableContext.wrapDefaultsContext(wrappedDefaultsContext);
		}
		return wrappedDefaultsContext;
	}
	
	protected void refreshDefaultAttributeOverrides() {
		for (Iterator<String> i = getEntity().allOverridableAttributeNames(); i.hasNext(); ) {
			String override = i.next();
			if (!getEntity().containsAttributeOverride(override)) {
				JavaAttributeOverride attributeOverride = JpaJavaMappingsFactory.eINSTANCE.createJavaAttributeOverride(new IEntity.AttributeOverrideOwner(getEntity()), getEntity().getType());
				getEntity().getDefaultAttributeOverrides().add(attributeOverride);
				attributeOverride.setName(override);
			}
			
		}
		
		Collection<String> attributeNames = CollectionTools.collection(getEntity().allOverridableAttributeNames());
	
		//remove any default mappings that are not included in the attributeNames collection
		Collection<IAttributeOverride> overridesToRemove = new ArrayList<IAttributeOverride>();
		for (IAttributeOverride attributeOverride : getEntity().getDefaultAttributeOverrides()) {
			if (!attributeNames.contains(attributeOverride.getName())
				|| getEntity().containsSpecifiedAttributeOverride(attributeOverride.getName())) {
				overridesToRemove.add(attributeOverride);
			}
		}
		
		getEntity().getDefaultAttributeOverrides().removeAll(overridesToRemove);
	}

	protected void refreshDefaultAssociationOverrides() {
		for (Iterator<String> i = getEntity().allOverridableAssociationNames(); i.hasNext(); ) {
			String override = i.next();
			if (!getEntity().containsAssociationOverride(override)) {
				JavaAssociationOverride associationOverride = JpaJavaMappingsFactory.eINSTANCE.createJavaAssociationOverride(new IEntity.AssociationOverrideOwner(getEntity()), getEntity().getType());
				associationOverride.setName(override);
				getEntity().getDefaultAssociationOverrides().add(associationOverride);
			}
			
		}
		
		Collection<String> attributeNames = CollectionTools.collection(getEntity().allOverridableAssociationNames());
	
		//remove any default mappings that are not included in the attributeNames collection
		Collection<IAssociationOverride> overridesToRemove = new ArrayList<IAssociationOverride>();
		for (IAssociationOverride associationOverride : getEntity().getDefaultAssociationOverrides()) {
			if (!attributeNames.contains(associationOverride.getName())
				|| getEntity().containsSpecifiedAssociationOverride(associationOverride.getName())) {
				overridesToRemove.add(associationOverride);
			}
		}
		
		getEntity().getDefaultAssociationOverrides().removeAll(overridesToRemove);
	}
	
	
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		addTableMessages(messages);
		addIdMessages(messages);
		
		for (SecondaryTableContext context : secondaryTableContexts) {
			context.addToMessages(messages);
		}

		for (AttributeOverrideContext aoContext : attributeOverrideContexts) {
			aoContext.addToMessages(messages);
		}
		
		for (AssociationOverrideContext aoContext : associationOverrideContexts) {
			aoContext.addToMessages(messages);
		}
	}
	
	protected void addTableMessages(List<IMessage> messages) {
		ITable table = getEntity().getTable();
		boolean doContinue = table.isConnected();
		String schema = table.getSchema();
		
		if (doContinue && ! table.hasResolvedSchema()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.TABLE_UNRESOLVED_SCHEMA,
						new String[] {schema, table.getName()}, 
						table, table.getSchemaTextRange())
				);
			doContinue = false;
		}
		
		if (doContinue && ! table.isResolved()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.TABLE_UNRESOLVED_NAME,
						new String[] {table.getName()}, 
						table, table.getNameTextRange())
				);
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
					entity, entity.getTextRange())
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
