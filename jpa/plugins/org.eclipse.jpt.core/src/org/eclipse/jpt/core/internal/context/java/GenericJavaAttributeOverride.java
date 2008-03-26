/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class GenericJavaAttributeOverride extends AbstractJavaOverride
	implements JavaAttributeOverride
{

	protected final JavaColumn column;
	

	public GenericJavaAttributeOverride(JavaJpaContextNode parent, AttributeOverride.Owner owner) {
		super(parent, owner);
		this.column = getJpaFactory().buildJavaColumn(this, this);
	}
	
	@Override
	public JavaAttributeOverride setVirtual(boolean virtual) {
		return (JavaAttributeOverride) super.setVirtual(virtual);
	}
	
	@Override
	protected AttributeOverrideAnnotation getOverrideResource() {
		return (AttributeOverrideAnnotation) super.getOverrideResource();
	}
	
	@Override
	public AttributeOverride.Owner getOwner() {
		return (AttributeOverride.Owner) super.getOwner();
	}
	
	public ColumnAnnotation getColumnResource() {
		return this.getOverrideResource().getNonNullColumn();
	}

	public String getDefaultColumnName() {
		ColumnMapping columnMapping = getColumnMapping();
		if (columnMapping == null) {
			return null;
		}
		return columnMapping.getColumn().getName();
	}
	
	public String getDefaultTableName() {
		ColumnMapping columnMapping = getColumnMapping();
		if (columnMapping == null) {
			return null;
		}
		String tableName = columnMapping.getColumn().getSpecifiedTable();
		if (tableName != null) {
			return tableName;
		}
		return getOwner().getTypeMapping().getTableName();
	}
	
	protected ColumnMapping getColumnMapping() {
		return getOwner().getColumnMapping(getName());
	}

	//************* IColumn.Owner implementation **************
	public TypeMapping getTypeMapping() {
		return this.getOwner().getTypeMapping();
	}
	
	public Table getDbTable(String tableName) {
		return this.getTypeMapping().getDbTable(tableName);
	}
	
	//************* IAttributeOverride implementation **************
	
	public JavaColumn getColumn() {
		return this.column;
	}

	//************* JavaOverride implementation **************
	
	@Override
	protected Iterator<String> candidateNames() {
		return this.getOwner().getTypeMapping().allOverridableAttributeNames();
	}
	
	//************* java resource model -> java context model **************	
	public void initializeFromResource(AttributeOverrideAnnotation attributeOverrideResource) {
		super.initializeFromResource(attributeOverrideResource);
		this.column.initializeFromResource(this.getColumnResource());
	}

	public void update(AttributeOverrideAnnotation attributeOverrideResource) {
		super.update(attributeOverrideResource);
		this.column.update(this.getColumnResource());		
	}

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.getColumn().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}
	
	
	//******************** validation **********************
	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
	
		addColumnMessages(messages, astRoot);
	}
	
	protected void addColumnMessages(List<IMessage> messages, CompilationUnit astRoot) {
		String table = getColumn().getTable();
		boolean doContinue = connectionProfileIsActive();
		
		if (doContinue && getTypeMapping().tableNameIsInvalid(table)) {
			if (isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_TABLE,
						new String[] {getName(), table, getColumn().getName()},
						getColumn(), 
						getColumn().getTableTextRange(astRoot))
				);
			}
			else {
				messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.COLUMN_UNRESOLVED_TABLE,
							new String[] {table, getColumn().getName()}, 
							getColumn(), 
							getColumn().getTableTextRange(astRoot))
					);
			}
			doContinue = false;
		}
		
		if (doContinue && ! getColumn().isResolved()) {
			if (isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME,
						new String[] {getName(), getColumn().getName()}, 
						getColumn(), 
						getColumn().getNameTextRange(astRoot))
				);
			}
			else {
				messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.COLUMN_UNRESOLVED_NAME,
							new String[] {getColumn().getName()}, 
							getColumn(), 
							getColumn().getNameTextRange(astRoot))
					);
			}
		}
	}

}
