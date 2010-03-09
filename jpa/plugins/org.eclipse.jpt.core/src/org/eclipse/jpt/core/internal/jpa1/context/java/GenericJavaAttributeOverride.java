/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaOverride;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class GenericJavaAttributeOverride extends AbstractJavaOverride
	implements JavaAttributeOverride
{

	protected final JavaColumn column;
	

	public GenericJavaAttributeOverride(JavaAttributeOverrideContainer parent, JavaAttributeOverride.Owner owner) {
		super(parent, owner);
		this.column = getJpaFactory().buildJavaColumn(this, this);
	}
	
	@Override
	public JavaAttributeOverride setVirtual(boolean virtual) {
		return (JavaAttributeOverride) super.setVirtual(virtual);
	}
	
	@Override
	public AttributeOverrideAnnotation getOverrideAnnotation() {
		return (AttributeOverrideAnnotation) super.getOverrideAnnotation();
	}
	
	@Override
	public JavaAttributeOverride.Owner getOwner() {
		return (JavaAttributeOverride.Owner) super.getOwner();
	}
	
	public ColumnAnnotation getResourceColumn() {
		return this.getOverrideAnnotation().getNonNullColumn();
	}
	
	public ColumnAnnotation getResourceColumnOrNull() {
		return this.getOverrideAnnotation().getColumn();
	}

	//************* NamedColumn.Owner implementation **************
	public TypeMapping getTypeMapping() {
		return this.getOwner().getTypeMapping();
	}
	
	public Table getDbTable(String tableName) {
		return this.getOwner().getDbTable(tableName);
	}
	
	public String getDefaultColumnName() {
		Column column = resolveOverriddenColumn();
		if (column == null) {
			return null;
		}
		return column.getName();
	}
	
	//************* BaseColumn.Owner implementation **************

	public String getDefaultTableName() {
		Column column = resolveOverriddenColumn();
		if (column == null) {
			return null;
		}
		String tableName = column.getSpecifiedTable();
		if (tableName != null) {
			return tableName;
		}
		return getOwner().getDefaultTableName();
	}

	protected Column resolveOverriddenColumn() {
		return getOwner().resolveOverriddenColumn(getName());
	}

	public boolean tableNameIsInvalid(String tableName) {
		return getOwner().tableNameIsInvalid(tableName);
	}
	
	public Iterator<String> candidateTableNames() {
		return getOwner().candidateTableNames();
	}
	
	//************* AttributeOverride implementation **************
	
	public JavaColumn getColumn() {
		return this.column;
	}

	//************* JavaOverride implementation **************
	
	@Override
	protected Iterator<String> candidateNames() {
		return this.getOwner().allOverridableAttributeNames();
	}
	
	//************* java resource model -> java context model **************	
	public void initialize(AttributeOverrideAnnotation attributeOverrideResource) {
		super.initialize(attributeOverrideResource);
		this.column.initialize(this.getResourceColumn());
	}

	public void update(AttributeOverrideAnnotation attributeOverrideResource) {
		super.update(attributeOverrideResource);
		this.column.update(this.getResourceColumn());		
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


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		getColumn().validate(messages, reporter, astRoot);
	}

	public IMessage buildUnresolvedNameMessage(NamedColumn column, TextRange textRange) {
		return getOwner().buildColumnUnresolvedNameMessage(this, column, textRange);
	}

	public IMessage buildTableNotValidMessage(BaseColumn column, TextRange textRange) {
		return getOwner().buildColumnTableNotValidMessage(this, column, textRange);
	}
}
