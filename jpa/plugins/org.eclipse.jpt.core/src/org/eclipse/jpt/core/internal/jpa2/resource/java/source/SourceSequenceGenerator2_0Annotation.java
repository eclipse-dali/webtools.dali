/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.core.internal.resource.java.source.SourceSequenceGeneratorAnnotation;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.SequenceGenerator2_0Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;

/**
 *  SourceSequenceGenerator2_0Annotation
 */
public final class SourceSequenceGenerator2_0Annotation
	extends SourceSequenceGeneratorAnnotation
	implements SequenceGenerator2_0Annotation
{
	private static final DeclarationAnnotationElementAdapter<String> CATALOG_ADAPTER = buildAdapter(JPA2_0.SEQUENCE_GENERATOR__CATALOG);
	private final AnnotationElementAdapter<String> catalogAdapter;
	private String catalog;
	
	private static final DeclarationAnnotationElementAdapter<String> SCHEMA_ADAPTER = buildAdapter(JPA2_0.SEQUENCE_GENERATOR__SCHEMA);
	private final AnnotationElementAdapter<String> schemaAdapter;
	private String schema;


	// ********** constructor **********
	public SourceSequenceGenerator2_0Annotation(JavaResourceNode parent, Member member) {
		super(parent, member);
		this.catalogAdapter = this.buildAdapter(CATALOG_ADAPTER);
		this.schemaAdapter = this.buildAdapter(SCHEMA_ADAPTER);
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.catalog = this.buildCatalog(astRoot);
		this.schema = this.buildSchema(astRoot);
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncCatalog(this.buildCatalog(astRoot));
		this.syncSchema(this.buildSchema(astRoot));
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.catalog == null) &&
				(this.schema == null);
	}


	// ********** catalog **********
	public String getCatalog() {
		return this.catalog;
	}

	public void setCatalog(String catalog) {
		if (this.attributeValueHasChanged(this.catalog, catalog)) {
			this.catalog = catalog;
			this.catalogAdapter.setValue(catalog);
		}
	}

	private void syncCatalog(String astCatalog) {
		String old = this.catalog;
		this.catalog = astCatalog;
		this.firePropertyChanged(CATALOG_PROPERTY, old, astCatalog);
	}

	private String buildCatalog(CompilationUnit astRoot) {
		return this.catalogAdapter.getValue(astRoot);
	}

	public TextRange getCatalogTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(CATALOG_ADAPTER, astRoot);
	}

	public boolean catalogTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(CATALOG_ADAPTER, pos, astRoot);
	}

	// ********** schema **********
	public String getSchema() {
		return this.schema;
	}

	public void setSchema(String schema) {
		if (this.attributeValueHasChanged(this.schema, schema)) {
			this.schema = schema;
			this.schemaAdapter.setValue(schema);
		}
	}

	private void syncSchema(String astSchema) {
		String old = this.schema;
		this.schema = astSchema;
		this.firePropertyChanged(SCHEMA_PROPERTY, old, astSchema);
	}

	private String buildSchema(CompilationUnit astRoot) {
		return this.schemaAdapter.getValue(astRoot);
	}

	public TextRange getSchemaTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(SCHEMA_ADAPTER, astRoot);
	}

	public boolean schemaTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(SCHEMA_ADAPTER, pos, astRoot);
	}

}