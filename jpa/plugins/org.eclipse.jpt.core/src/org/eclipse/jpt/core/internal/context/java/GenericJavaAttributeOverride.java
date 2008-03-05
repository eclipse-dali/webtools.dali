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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.Filter;


public class GenericJavaAttributeOverride extends AbstractJavaOverride
	implements JavaAttributeOverride
{

	protected final JavaColumn column;
	

	public GenericJavaAttributeOverride(JavaJpaContextNode parent, AttributeOverride.Owner owner) {
		super(parent, owner);
		this.column = jpaFactory().buildJavaColumn(this, this);
	}
	
	@Override
	protected AttributeOverrideAnnotation getOverrideResource() {
		return (AttributeOverrideAnnotation) super.getOverrideResource();
	}
	
	@Override
	public AttributeOverride.Owner owner() {
		return (AttributeOverride.Owner) super.owner();
	}
	
	public ColumnAnnotation columnResource() {
		return this.getOverrideResource().getNonNullColumn();
	}

	public String defaultColumnName() {
		ColumnMapping columnMapping = columnMapping();
		if (columnMapping == null) {
			return null;
		}
		return columnMapping.getColumn().getName();
	}
	
	public String defaultTableName() {
		ColumnMapping columnMapping = columnMapping();
		if (columnMapping == null) {
			return null;
		}
		String tableName = columnMapping.getColumn().getSpecifiedTable();
		if (tableName != null) {
			return tableName;
		}
		return owner().typeMapping().tableName();
	}
	
	protected ColumnMapping columnMapping() {
		return owner().columnMapping(getName());
	}

	//************* IColumn.Owner implementation **************
	public TypeMapping typeMapping() {
		return this.owner().typeMapping();
	}
	
	public Table dbTable(String tableName) {
		return this.typeMapping().dbTable(tableName);
	}
	
	//************* IAttributeOverride implementation **************
	
	public JavaColumn getColumn() {
		return this.column;
	}

	//************* JavaOverride implementation **************
	
	@Override
	protected Iterator<String> candidateNames() {
		return this.owner().typeMapping().allOverridableAttributeNames();
	}
	
	//************* java resource model -> java context model **************	
	public void initializeFromResource(AttributeOverrideAnnotation attributeOverrideResource) {
		super.initializeFromResource(attributeOverrideResource);
		this.column.initializeFromResource(this.columnResource());
	}

	public void update(AttributeOverrideAnnotation attributeOverrideResource) {
		super.update(attributeOverrideResource);
		this.column.update(this.columnResource());		
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
	


	// ********** static methods **********
//	static JavaAttributeOverride createAttributeOverride(Owner owner, Member member, int index) {
//		return JpaJavaMappingsFactory.eINSTANCE.createJavaAttributeOverride(owner, member, buildAnnotationAdapter(index));
//	}
//
//	private static IndexedDeclarationAnnotationAdapter buildAnnotationAdapter(int index) {
//		return new CombinationIndexedDeclarationAnnotationAdapter(SINGLE_DECLARATION_ANNOTATION_ADAPTER, MULTIPLE_DECLARATION_ANNOTATION_ADAPTER, index, JPA.ATTRIBUTE_OVERRIDE);
//	}

}
