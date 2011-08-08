/*******************************************************************************
 *  Copyright (c) 2011  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.CombinationIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceBaseDiscriminatorColumnAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.EclipseLink2_3;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.EclipseLinkTenantDiscriminatorColumnAnnotation;

/**
 * org.eclipse.persistence.annotations.TenantDiscriminatorColumn
 */
public class SourceEclipseLinkTenantDiscriminatorColumnAnnotation
	extends SourceBaseDiscriminatorColumnAnnotation
	implements EclipseLinkTenantDiscriminatorColumnAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME);
	private static final DeclarationAnnotationAdapter CONTAINER_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMNS);

	protected DeclarationAnnotationElementAdapter<String> contextPropertyDeclarationAdapter;
	protected AnnotationElementAdapter<String> contextPropertyAdapter;
	protected String contextProperty;

	protected DeclarationAnnotationElementAdapter<String> tableDeclarationAdapter;
	protected AnnotationElementAdapter<String> tableAdapter;
	protected String table;

	protected DeclarationAnnotationElementAdapter<Boolean> primaryKeyDeclarationAdapter;
	protected AnnotationElementAdapter<Boolean> primaryKeyAdapter;
	protected Boolean primaryKey;

	public static SourceEclipseLinkTenantDiscriminatorColumnAnnotation buildSourceTenantDiscriminatorColumnAnnotation(
			JavaResourceNode parent, 
			AnnotatedElement element) {

		return new SourceEclipseLinkTenantDiscriminatorColumnAnnotation(parent, element, DECLARATION_ANNOTATION_ADAPTER);
	}

	public static SourceEclipseLinkTenantDiscriminatorColumnAnnotation buildSourceTenantDiscriminatorColumnAnnotation(
			JavaResourceAnnotatedElement parent, 
			AnnotatedElement annotatedElement, 
			int index) {
		IndexedDeclarationAnnotationAdapter idaa = buildTenantDiscriminatorColumnDeclarationAnnotationAdapter(index);
		IndexedAnnotationAdapter iaa = buildTenantDiscriminatorColumnAnnotationAdapter(annotatedElement, idaa);
		return new SourceEclipseLinkTenantDiscriminatorColumnAnnotation(
			parent,
			annotatedElement,
			idaa,
			iaa);
	}


	public static SourceEclipseLinkTenantDiscriminatorColumnAnnotation buildNestedSourceTenantDiscriminatorColumnAnnotation(
			JavaResourceNode parent, 
			AnnotatedElement element, 
			IndexedDeclarationAnnotationAdapter idaa) {
		
		return new SourceEclipseLinkTenantDiscriminatorColumnAnnotation(parent, element, idaa);
	}

	private SourceEclipseLinkTenantDiscriminatorColumnAnnotation(JavaResourceNode parent, AnnotatedElement element, DeclarationAnnotationAdapter daa) {
		this(parent, element, daa, new ElementAnnotationAdapter(element, daa));
	}

	private SourceEclipseLinkTenantDiscriminatorColumnAnnotation(JavaResourceNode parent, AnnotatedElement element, IndexedDeclarationAnnotationAdapter idaa) {
		this(parent, element, idaa, new ElementIndexedAnnotationAdapter(element, idaa));
	}

	private SourceEclipseLinkTenantDiscriminatorColumnAnnotation(JavaResourceNode parent, AnnotatedElement element, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
		this.contextPropertyDeclarationAdapter = this.buildContextPropertyDeclarationAdapter();
		this.contextPropertyAdapter = this.buildContextPropertyAdapter();
		this.tableDeclarationAdapter = this.buildTableDeclarationAdapter();
		this.tableAdapter = this.buildTableAdapter();
		this.primaryKeyDeclarationAdapter = this.buildPrimaryKeyDeclarationAdapter();
		this.primaryKeyAdapter = this.buildPrimaryKeyAdapter();
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.contextProperty = this.buildContextProperty(astRoot);
		this.table = this.buildTable(astRoot);
		this.primaryKey = this.buildPrimaryKey(astRoot);
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncContextProperty(this.buildContextProperty(astRoot));
		this.syncTable(this.buildTable(astRoot));
		this.syncPrimaryKey(this.buildPrimaryKey(astRoot));
	}

	public String getAnnotationName() {
		return EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME;
	}


	// ********** SourceNamedColumnAnnotation implementation **********

	@Override
	protected String getNameElementName() {
		return EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN__NAME;
	}

	@Override
	protected String getColumnDefinitionElementName() {
		return EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION;
	}


	//************* SourceBaseDiscriminatorColumnAnnotation implementation *************

	@Override
	protected String getDiscriminatorTypeElementName() {
		return EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE;
	}

	@Override
	protected String getLengthElementName() {
		return EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN__LENGTH;
	}


	//************* TenantDiscriminatorColumnAnnotation implementation *************

	// ***** contextProperty
	public String getContextProperty() {
		return this.contextProperty;
	}

	public void setContextProperty(String contextProperty) {
		if (this.attributeValueHasChanged(this.contextProperty, contextProperty)) {
			this.contextProperty = contextProperty;
			this.contextPropertyAdapter.setValue(contextProperty);
		}
	}

	private void syncContextProperty(String astContextProperty) {
		String old = this.contextProperty;
		this.contextProperty = astContextProperty;
		this.firePropertyChanged(CONTEXT_PROPERTY_PROPERTY, old, astContextProperty);
	}

	private String buildContextProperty(CompilationUnit astRoot) {
		return this.contextPropertyAdapter.getValue(astRoot);
	}

	public TextRange getContextPropertyTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.contextPropertyDeclarationAdapter, astRoot);
	}

	private DeclarationAnnotationElementAdapter<String> buildContextPropertyDeclarationAdapter() {
		return this.buildStringElementAdapter(EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN__CONTEXT_PROPERTY);
	}

	private AnnotationElementAdapter<String> buildContextPropertyAdapter() {
		return this.buildStringElementAdapter(this.contextPropertyDeclarationAdapter);
	}

	// ***** table
	public String getTable() {
		return this.table;
	}

	public void setTable(String table) {
		if (this.attributeValueHasChanged(this.table, table)) {
			this.table = table;
			this.tableAdapter.setValue(table);
		}
	}

	private void syncTable(String astTable) {
		String old = this.table;
		this.table = astTable;
		this.firePropertyChanged(TABLE_PROPERTY, old, astTable);
	}

	private String buildTable(CompilationUnit astRoot) {
		return this.tableAdapter.getValue(astRoot);
	}

	public TextRange getTableTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.tableDeclarationAdapter, astRoot);
	}

	public boolean tableTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.tableDeclarationAdapter, pos, astRoot);
	}

	private DeclarationAnnotationElementAdapter<String> buildTableDeclarationAdapter() {
		return this.buildStringElementAdapter(EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN__TABLE);
	}

	private AnnotationElementAdapter<String> buildTableAdapter() {
		return this.buildStringElementAdapter(this.tableDeclarationAdapter);
	}

	// ***** primaryKey
	public Boolean getPrimaryKey() {
		return this.primaryKey;
	}

	public void setPrimaryKey(Boolean primaryKey) {
		if (this.attributeValueHasChanged(this.primaryKey, primaryKey)) {
			this.primaryKey = primaryKey;
			this.primaryKeyAdapter.setValue(primaryKey);
		}
	}

	private void syncPrimaryKey(Boolean astPrimaryKey) {
		Boolean old = this.primaryKey;
		this.primaryKey = astPrimaryKey;
		this.firePropertyChanged(PRIMARY_KEY_PROPERTY, old, astPrimaryKey);
	}

	private Boolean buildPrimaryKey(CompilationUnit astRoot) {
		return this.primaryKeyAdapter.getValue(astRoot);
	}

	public TextRange getPrimaryKeyTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.primaryKeyDeclarationAdapter, astRoot);
	}

	private DeclarationAnnotationElementAdapter<Boolean> buildPrimaryKeyDeclarationAdapter() {
		return this.buildBooleanElementAdapter(EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN__PRIMARY_KEY);
	}

	private AnnotationElementAdapter<Boolean> buildPrimaryKeyAdapter() {
		return this.buildBooleanElementAdapter(this.primaryKeyDeclarationAdapter);
	}


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.contextProperty == null) &&
				(this.primaryKey == null) &&
				(this.table == null);
	}

	// ********** static methods **********

	private static IndexedAnnotationAdapter buildTenantDiscriminatorColumnAnnotationAdapter(AnnotatedElement annotatedElement, IndexedDeclarationAnnotationAdapter idaa) {
		return new ElementIndexedAnnotationAdapter(annotatedElement, idaa);
	}

	private static IndexedDeclarationAnnotationAdapter buildTenantDiscriminatorColumnDeclarationAnnotationAdapter(int index) {
		IndexedDeclarationAnnotationAdapter idaa = 
			new CombinationIndexedDeclarationAnnotationAdapter(
				DECLARATION_ANNOTATION_ADAPTER,
				CONTAINER_DECLARATION_ANNOTATION_ADAPTER,
				index,
				EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME);
		return idaa;
	}
}
