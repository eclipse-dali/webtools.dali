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
import org.eclipse.jdt.core.dom.CompilationUnit;
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
		super.populateGeneratorRepository(generatorRepository);
		ITableGenerator tableGenerator = getEntity().getTableGenerator();
		if (tableGenerator != null)  {
			generatorRepository.addGenerator(tableGenerator);
		}
		ISequenceGenerator sequenceGenerator = getEntity().getSequenceGenerator();
		if (sequenceGenerator != null)  {
			generatorRepository.addGenerator(sequenceGenerator);
		}
	}

	public void refreshDefaults(DefaultsContext defaultsContext, IProgressMonitor monitor) {
		defaultsContext = wrapDefaultsContext(defaultsContext);
		super.refreshDefaults(defaultsContext, monitor);
		if (monitor.isCanceled()) {
			return;
		}
		
		if (this.tableContext != null) {
			this.tableContext.refreshDefaults(defaultsContext, monitor);
		}
		refreshDefaultAttributeOverrides();
		refreshDefaultAssociationOverrides();
		for (SecondaryTableContext context : this.secondaryTableContexts) {
			context.refreshDefaults(defaultsContext, monitor);
		}
		for (AttributeOverrideContext context : this.attributeOverrideContexts) {
			context.refreshDefaults(defaultsContext, monitor);
		}
		for (AssociationOverrideContext context : this.associationOverrideContexts) {
			context.refreshDefaults(defaultsContext, monitor);
		}
		for (PrimaryKeyJoinColumnContext context : this.pkJoinColumnContexts) {
			context.refreshDefaults(defaultsContext, monitor);
		}
	}
	
	//TODO the relationship between this class and JavaTypeContext is very confused
	//we end up wrapping the defaults context multiple times.  Maybe we should
	//make this more like JavaAttributeContext.  or maybe we need a JavaPersistentTypeContext
	//I tried to minimize the change so as not to break the defaults calculations
	private DefaultsContext wrapDefaultsContext(final DefaultsContext defaultsContext) {
		DefaultsContext wrappedDefaultsContext = new DefaultsContext() {
			public Object getDefault(String key) {
				if (key.equals(BaseJpaPlatform.DEFAULT_TABLE_NAME_KEY)) {
					if (getEntity().rootEntity().getInheritanceStrategy().isSingleTable()) {
						IEntity rootEntity = getEntity().rootEntity();
						if (rootEntity == getEntity()) {
							return getPlatform().convertJavaIdentifierToDatabaseIdentifier(rootEntity.getName());
						}
						return rootEntity.getTable().getName();
					}
					return getPlatform().convertJavaIdentifierToDatabaseIdentifier(getEntity().getName());
				}
				return defaultsContext.getDefault(key);
			}
		
			public IPersistentType persistentType(String fullyQualifiedTypeName) {
				return defaultsContext.persistentType(fullyQualifiedTypeName);
			}
			public CompilationUnit astRoot() {
				return getAstRoot();
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
				getEntity().getDefaultAssociationOverrides().add(associationOverride);
				associationOverride.setName(override);
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
						table, table.schemaTextRange())
				);
			doContinue = false;
		}
		
		if (doContinue && ! table.isResolved()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.TABLE_UNRESOLVED_NAME,
						new String[] {table.getName()}, 
						table, table.nameTextRange())
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
