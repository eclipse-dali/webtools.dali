/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.source;

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
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkTenantDiscriminatorColumnAnnotation2_3;

/**
 * <code>org.eclipse.persistence.annotations.TenantDiscriminatorColumn</code>
 */
public final class SourceEclipseLinkTenantDiscriminatorColumnAnnotation2_3
	extends SourceBaseDiscriminatorColumnAnnotation
	implements EclipseLinkTenantDiscriminatorColumnAnnotation2_3
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(EclipseLinkTenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME);
	private static final DeclarationAnnotationAdapter CONTAINER_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(EclipseLink.TENANT_DISCRIMINATOR_COLUMNS);

	private DeclarationAnnotationElementAdapter<String> contextPropertyDeclarationAdapter;
	private AnnotationElementAdapter<String> contextPropertyAdapter;
	private String contextProperty;
	private TextRange contextPropertyTextRange;

	private DeclarationAnnotationElementAdapter<String> tableDeclarationAdapter;
	private AnnotationElementAdapter<String> tableAdapter;
	private String table;
	private TextRange tableTextRange;

	private DeclarationAnnotationElementAdapter<Boolean> primaryKeyDeclarationAdapter;
	private AnnotationElementAdapter<Boolean> primaryKeyAdapter;
	private Boolean primaryKey;
	private TextRange primaryKeyTextRange;

	public static SourceEclipseLinkTenantDiscriminatorColumnAnnotation2_3 buildSourceTenantDiscriminatorColumnAnnotation(
			JavaResourceNode parent, 
			AnnotatedElement element) {

		return new SourceEclipseLinkTenantDiscriminatorColumnAnnotation2_3(parent, element, DECLARATION_ANNOTATION_ADAPTER);
	}

	public static SourceEclipseLinkTenantDiscriminatorColumnAnnotation2_3 buildSourceTenantDiscriminatorColumnAnnotation(
			JavaResourceAnnotatedElement parent, 
			AnnotatedElement annotatedElement, 
			int index) {
		IndexedDeclarationAnnotationAdapter idaa = buildTenantDiscriminatorColumnDeclarationAnnotationAdapter(index);
		IndexedAnnotationAdapter iaa = buildTenantDiscriminatorColumnAnnotationAdapter(annotatedElement, idaa);
		return new SourceEclipseLinkTenantDiscriminatorColumnAnnotation2_3(
			parent,
			annotatedElement,
			idaa,
			iaa);
	}


	public static SourceEclipseLinkTenantDiscriminatorColumnAnnotation2_3 buildNestedSourceTenantDiscriminatorColumnAnnotation(
			JavaResourceNode parent, 
			AnnotatedElement element, 
			IndexedDeclarationAnnotationAdapter idaa) {
		
		return new SourceEclipseLinkTenantDiscriminatorColumnAnnotation2_3(parent, element, idaa);
	}

	private SourceEclipseLinkTenantDiscriminatorColumnAnnotation2_3(JavaResourceNode parent, AnnotatedElement element, DeclarationAnnotationAdapter daa) {
		this(parent, element, daa, new ElementAnnotationAdapter(element, daa));
	}

	private SourceEclipseLinkTenantDiscriminatorColumnAnnotation2_3(JavaResourceNode parent, AnnotatedElement element, IndexedDeclarationAnnotationAdapter idaa) {
		this(parent, element, idaa, new ElementIndexedAnnotationAdapter(element, idaa));
	}

	private SourceEclipseLinkTenantDiscriminatorColumnAnnotation2_3(JavaResourceNode parent, AnnotatedElement element, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
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
		this.contextPropertyTextRange = this.buildContextPropertyTextRange(astRoot);

		this.table = this.buildTable(astRoot);
		this.tableTextRange = this.buildTableTextRange(astRoot);

		this.primaryKey = this.buildPrimaryKey(astRoot);
		this.primaryKeyTextRange = this.buildPrimaryKeyTextRange(astRoot);
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);

		this.syncContextProperty(this.buildContextProperty(astRoot));
		this.contextPropertyTextRange = this.buildContextPropertyTextRange(astRoot);

		this.syncTable(this.buildTable(astRoot));
		this.tableTextRange = this.buildTableTextRange(astRoot);

		this.syncPrimaryKey(this.buildPrimaryKey(astRoot));
		this.primaryKeyTextRange = this.buildPrimaryKeyTextRange(astRoot);
	}

	public String getAnnotationName() {
		return EclipseLinkTenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME;
	}


	// ********** SourceNamedColumnAnnotation implementation **********

	@Override
	protected String getNameElementName() {
		return EclipseLink.TENANT_DISCRIMINATOR_COLUMN__NAME;
	}

	@Override
	protected String getColumnDefinitionElementName() {
		return EclipseLink.TENANT_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION;
	}


	//************* SourceBaseDiscriminatorColumnAnnotation implementation *************

	@Override
	protected String getDiscriminatorTypeElementName() {
		return EclipseLink.TENANT_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE;
	}

	@Override
	protected String getLengthElementName() {
		return EclipseLink.TENANT_DISCRIMINATOR_COLUMN__LENGTH;
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

	public TextRange getContextPropertyTextRange() {
		return this.contextPropertyTextRange;
	}

	private TextRange buildContextPropertyTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.contextPropertyDeclarationAdapter, astRoot);
	}

	private DeclarationAnnotationElementAdapter<String> buildContextPropertyDeclarationAdapter() {
		return this.buildStringElementAdapter(EclipseLink.TENANT_DISCRIMINATOR_COLUMN__CONTEXT_PROPERTY);
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

	public TextRange getTableTextRange() {
		return this.tableTextRange;
	}

	private TextRange buildTableTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.tableDeclarationAdapter, astRoot);
	}

	public boolean tableTouches(int pos) {
		return this.textRangeTouches(this.tableTextRange, pos);
	}

	private DeclarationAnnotationElementAdapter<String> buildTableDeclarationAdapter() {
		return this.buildStringElementAdapter(EclipseLink.TENANT_DISCRIMINATOR_COLUMN__TABLE);
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

	public TextRange getPrimaryKeyTextRange() {
		return this.primaryKeyTextRange;
	}

	private TextRange buildPrimaryKeyTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.primaryKeyDeclarationAdapter, astRoot);
	}

	private DeclarationAnnotationElementAdapter<Boolean> buildPrimaryKeyDeclarationAdapter() {
		return this.buildBooleanElementAdapter(EclipseLink.TENANT_DISCRIMINATOR_COLUMN__PRIMARY_KEY);
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
				EclipseLinkTenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME);
		return idaa;
	}
}
