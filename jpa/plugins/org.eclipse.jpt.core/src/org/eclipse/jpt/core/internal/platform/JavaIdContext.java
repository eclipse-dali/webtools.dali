/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.List;
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

public class JavaIdContext extends JavaAttributeContext
{
	private ColumnContext columnContext;
	
	public JavaIdContext(IContext parentContext, JavaId javaId) {
		super(parentContext, javaId);
		this.columnContext = new ColumnContext(this, javaId.getColumn());
	}
	
	protected JavaId getId() {
		return (JavaId) attributeMapping;
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
	public void refreshDefaultsInternal(DefaultsContext defaultsContext) {
		super.refreshDefaultsInternal(defaultsContext);
		this.columnContext.refreshDefaults(defaultsContext);
		ITableGenerator tableGenerator = getId().getTableGenerator();
		if (tableGenerator != null) {
			tableGenerator.refreshDefaults(defaultsContext);
		}
	}
	
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		
		addColumnMessages(messages);
		addGeneratorMessages(messages);
	}
		
	protected void addColumnMessages(List<IMessage> messages) {
		JavaId id = getId();
		ITypeMapping typeMapping = id.typeMapping();
		IColumn column = id.getColumn();
		String table = column.getTable();
		boolean doContinue = entityOwned() && column.isConnected();
		
		if (doContinue && typeMapping.tableNameIsInvalid(table)) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.COLUMN_UNRESOLVED_TABLE,
						new String[] {table, column.getName()}, 
						column, column.tableTextRange())
				);
			doContinue = false;
		}
		
		if (doContinue && ! column.isResolved()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.COLUMN_UNRESOLVED_NAME,
						new String[] {column.getName()}, 
						column, column.nameTextRange())
				);
		}
	}
	
	protected void addGeneratorMessages(List<IMessage> messages) {
		JavaId id = getId();
		IGeneratedValue generatedValue = id.getGeneratedValue();
		if (generatedValue == null) {
			return;
		}
		String generatorName = generatedValue.getGenerator();
		if (generatorName == null) {
			return;
		}
		IGeneratorRepository generatorRepository = persistenceUnitContext().getGeneratorRepository();		
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
