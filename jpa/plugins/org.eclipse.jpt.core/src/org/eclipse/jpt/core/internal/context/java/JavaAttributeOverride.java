/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.context.base.IAttributeOverride;
import org.eclipse.jpt.core.internal.context.base.IColumnMapping;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverride;
import org.eclipse.jpt.core.internal.resource.java.Column;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.Filter;


public class JavaAttributeOverride extends JavaOverride<AttributeOverride>
	implements IJavaAttributeOverride
{

	protected final IJavaColumn column;
	

	public JavaAttributeOverride(IJavaJpaContextNode parent, IAttributeOverride.Owner owner) {
		super(parent, owner);
		this.column = jpaFactory().createJavaColumn(this, this);
	}
	
	@Override
	public IAttributeOverride.Owner owner() {
		return (IAttributeOverride.Owner) super.owner();
	}
	
	public Column columnResource() {
		return this.getOverrideResource().getNonNullColumn();
	}

	public String defaultColumnName() {
		IColumnMapping columnMapping = columnMapping();
		if (columnMapping == null) {
			return null;
		}
		return columnMapping.getColumn().getName();
	}
	
	public String defaultTableName() {
		IColumnMapping columnMapping = columnMapping();
		if (columnMapping == null) {
			return null;
		}
		String tableName = columnMapping.getColumn().getSpecifiedTable();
		if (tableName != null) {
			return tableName;
		}
		return owner().typeMapping().tableName();
	}
	
	protected IColumnMapping columnMapping() {
		return owner().columnMapping(getName());
	}

	//************* IColumn.Owner implementation **************
	public ITypeMapping typeMapping() {
		return this.owner().typeMapping();
	}
	
	public Table dbTable(String tableName) {
		return this.typeMapping().dbTable(tableName);
	}
	
	//************* IAttributeOverride implementation **************
	
	public IJavaColumn getColumn() {
		return this.column;
	}

	//************* JavaOverride implementation **************
	
	@Override
	protected Iterator<String> candidateNames() {
		return this.owner().typeMapping().allOverridableAttributeNames();
	}
	
	//************* java resource model -> java context model **************	
	@Override
	public void initializeFromResource(AttributeOverride attributeOverrideResource) {
		super.initializeFromResource(attributeOverrideResource);
		this.column.initializeFromResource(this.columnResource());
	}

	@Override
	public void update(AttributeOverride attributeOverrideResource) {
		super.update(attributeOverrideResource);
		this.column.update(this.columnResource());		
	}

	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.getColumn().candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}
	


	// ********** static methods **********
//	static JavaAttributeOverride createAttributeOverride(Owner owner, Member member, int index) {
//		return JpaJavaMappingsFactory.eINSTANCE.createJavaAttributeOverride(owner, member, buildAnnotationAdapter(index));
//	}
//
//	private static IndexedDeclarationAnnotationAdapter buildAnnotationAdapter(int index) {
//		return new CombinationIndexedDeclarationAnnotationAdapter(SINGLE_DECLARATION_ANNOTATION_ADAPTER, MULTIPLE_DECLARATION_ANNOTATION_ADAPTER, index, JPA.ATTRIBUTE_OVERRIDE);
//	}

}
