/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.source.SourceSequenceGeneratorAnnotation;
import org.eclipse.jpt.core.jpa2.resource.java.JPA;
import org.eclipse.jpt.core.jpa2.resource.java.SequenceGenerator2_0Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;

/**
 *  SourceSequenceGenerator2_0Annotation
 */
public final class SourceSequenceGenerator2_0Annotation
	extends SourceSequenceGeneratorAnnotation
	implements SequenceGenerator2_0Annotation
{
	private static final DeclarationAnnotationElementAdapter<String> CATALOG_ADAPTER = buildAdapter(JPA.SEQUENCE_GENERATOR__CATALOG);
	private final AnnotationElementAdapter<String> catalogAdapter;
	private String catalog;
	
	private static final DeclarationAnnotationElementAdapter<String> SCHEMA_ADAPTER = buildAdapter(JPA.SEQUENCE_GENERATOR__SCHEMA);
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
	public void update(CompilationUnit astRoot) {
		super.update(astRoot);

		this.setCatalog(this.buildCatalog(astRoot));
		this.setSchema(this.buildSchema(astRoot));
	}

	// ********** catalog **********
	public String getCatalog() {
		return this.catalog;
	}

	public void setCatalog(String catalog) {
		if (this.attributeValueHasNotChanged(this.catalog, catalog)) {
			return;
		}
		String old = this.catalog;
		this.catalog = catalog;
		this.catalogAdapter.setValue(catalog);
		this.firePropertyChanged(CATALOG_PROPERTY, old, catalog);
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
		if (this.attributeValueHasNotChanged(this.schema, schema)) {
			return;
		}
		String old = this.schema;
		this.schema = schema;
		this.schemaAdapter.setValue(schema);
		this.firePropertyChanged(SCHEMA_PROPERTY, old, schema);
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