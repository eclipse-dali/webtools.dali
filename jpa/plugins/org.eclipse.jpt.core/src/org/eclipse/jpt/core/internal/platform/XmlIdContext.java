/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.List;
import org.eclipse.jpt.core.internal.IAttributeMapping;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaId;
import org.eclipse.jpt.core.internal.mappings.IColumn;
import org.eclipse.jpt.core.internal.mappings.IGeneratedValue;
import org.eclipse.jpt.core.internal.mappings.IGenerator;
import org.eclipse.jpt.core.internal.mappings.ISequenceGenerator;
import org.eclipse.jpt.core.internal.mappings.ITableGenerator;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class XmlIdContext extends XmlAttributeContext
{

	private ColumnContext columnContext;
	
	public XmlIdContext(IContext parentContext, XmlId xmlId) {
		super(parentContext, xmlId);
		this.columnContext = new ColumnContext(this, xmlId.getColumn());
		populateGeneratorRepository((GeneratorRepository) getPersistenceUnitContext().getGeneratorRepository());
	}
	
	protected PersistenceUnitContext getPersistenceUnitContext() {
		return ((MappingFileContext) getParentContext().getParentContext()).getPersistenceUnitContext();
	}
	
	protected void populateGeneratorRepository(GeneratorRepository generatorRepository) {
		ITableGenerator tableGenerator = getId().getTableGenerator();
		if (tableGenerator != null)  {
			generatorRepository.addGenerator(tableGenerator);
		}
		ISequenceGenerator sequenceGenerator = getId().getSequenceGenerator();
		if (sequenceGenerator != null)  {
			generatorRepository.addGenerator(sequenceGenerator);
		}
	}

	@Override
	public void refreshDefaults(DefaultsContext defaultsContext) {
		super.refreshDefaults(defaultsContext);
		this.columnContext.refreshDefaults(defaultsContext);
		ITableGenerator tableGenerator = getId().getTableGenerator();
		if (tableGenerator != null) {
			tableGenerator.refreshDefaults(defaultsContext);
		}
	}
	
	@Override
	protected Object getDefault(String key, DefaultsContext defaultsContext) {
		if (key.equals(BaseJpaPlatform.DEFAULT_COLUMN_NAME_KEY)) {
			if (attributeMapping().getPersistentAttribute().isVirtual()) {
				if (javaIdMapping() != null) {
					if (!attributeMapping().getPersistentType().getMapping().isXmlMetadataComplete()) {
						return javaIdMapping().getColumn().getName();
					}
					return javaIdMapping().getColumn().getDefaultName();
				}
			}
			//doesn't matter what's in the java @Column annotation because it is completely
			//overriden as soon as you specify the attribute in xml.
			return attributeMapping().getPersistentAttribute().getName();
		}

		return super.getDefault(key, defaultsContext);
	}
	
	protected JavaId javaIdMapping() {
		IAttributeMapping attributeMapping = javaAttributeMapping();
		if (attributeMapping.getKey() == IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY) {
			return (JavaId) attributeMapping;
		}
		return null;
	}
	
	protected XmlId getId() {
		return (XmlId) attributeMapping();
	}
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		
		if (entityOwned()) {
			addColumnMessages(messages);
		}
		addGeneratorMessages(messages);
	}
	
	protected void addColumnMessages(List<IMessage> messages) {
		XmlId id = getId();
		ITypeMapping typeMapping = id.typeMapping();
		IColumn column = id.getColumn();
		String table = column.getTable();
		boolean doContinue = column.isConnected();
		
		if (doContinue && typeMapping.tableNameIsInvalid(table)) {
			if (id.isVirtual()) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_TABLE,
						new String[] {id.getPersistentAttribute().getName(), table, column.getName()},
						column, column.tableTextRange())
				);
			}
			else {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.COLUMN_UNRESOLVED_TABLE,
						new String[] {table, column.getName()}, 
						column, column.tableTextRange())
				);
			}
			doContinue = false;
		}
		
		if (doContinue && ! column.isResolved()) {
			if (id.isVirtual()) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_NAME,
						new String[] {id.getPersistentAttribute().getName(), column.getName()}, 
						column, column.nameTextRange())
				);
			}
			else {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.COLUMN_UNRESOLVED_NAME,
						new String[] {column.getName()}, 
						column, column.nameTextRange())
				);
			}
		}
	}
	
	protected void addGeneratorMessages(List<IMessage> messages) {
		XmlId id = getId();
		IGeneratedValue generatedValue = id.getGeneratedValue();
		if (generatedValue == null) {
			return;
		}
		String generatorName = generatedValue.getGenerator();
		if (generatorName == null) {
			return;
		}
		IGeneratorRepository generatorRepository = getPersistenceUnitContext().getGeneratorRepository();		
		IGenerator generator = generatorRepository.generator(generatorName);
		
		if (generator == null) {
			messages.add(
				JpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					IJpaValidationMessages.GENERATED_VALUE_UNRESOLVED_GENERATOR,
					new String[] {generatorName}, 
					generatedValue, generatedValue.validationTextRange())
			);
		}
	}

}
