/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceSequenceGeneratorAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.SequenceGeneratorAnnotation2_0;

/**
 * JPA 2.0
 * <code>javax.persistence.SequenceGenerator</code>
 */
public final class SourceSequenceGeneratorAnnotation2_0
	extends SourceSequenceGeneratorAnnotation
	implements SequenceGeneratorAnnotation2_0
{
	private static final DeclarationAnnotationElementAdapter<String> CATALOG_ADAPTER = buildAdapter(JPA2_0.SEQUENCE_GENERATOR__CATALOG);
	private final AnnotationElementAdapter<String> catalogAdapter;
	private String catalog;
	private TextRange catalogTextRange;
	
	private static final DeclarationAnnotationElementAdapter<String> SCHEMA_ADAPTER = buildAdapter(JPA2_0.SEQUENCE_GENERATOR__SCHEMA);
	private final AnnotationElementAdapter<String> schemaAdapter;
	private String schema;
	private TextRange schemaTextRange;


	// ********** constructor **********
	public SourceSequenceGeneratorAnnotation2_0(JavaResourceModel parent, AnnotatedElement element) {
		super(parent, element);
		this.catalogAdapter = this.buildAdapter(CATALOG_ADAPTER);
		this.schemaAdapter = this.buildAdapter(SCHEMA_ADAPTER);
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.catalog = this.buildCatalog(astAnnotation);
		this.catalogTextRange = this.buildCatalogTextRange(astAnnotation);
		this.schema = this.buildSchema(astAnnotation);
		this.schemaTextRange = this.buildSchemaTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncCatalog(this.buildCatalog(astAnnotation));
		this.catalogTextRange = this.buildCatalogTextRange(astAnnotation);
		this.syncSchema(this.buildSchema(astAnnotation));
		this.schemaTextRange = this.buildSchemaTextRange(astAnnotation);
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
		if (ObjectTools.notEquals(this.catalog, catalog)) {
			this.catalog = catalog;
			this.catalogAdapter.setValue(catalog);
		}
	}

	private void syncCatalog(String astCatalog) {
		String old = this.catalog;
		this.catalog = astCatalog;
		this.firePropertyChanged(CATALOG_PROPERTY, old, astCatalog);
	}

	private String buildCatalog(Annotation astAnnotation) {
		return this.catalogAdapter.getValue(astAnnotation);
	}

	public TextRange getCatalogTextRange() {
		return this.catalogTextRange;
	}

	private TextRange buildCatalogTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(CATALOG_ADAPTER, astAnnotation);
	}

	public boolean catalogTouches(int pos) {
		return this.textRangeTouches(this.catalogTextRange, pos);
	}

	// ********** schema **********
	public String getSchema() {
		return this.schema;
	}

	public void setSchema(String schema) {
		if (ObjectTools.notEquals(this.schema, schema)) {
			this.schema = schema;
			this.schemaAdapter.setValue(schema);
		}
	}

	private void syncSchema(String astSchema) {
		String old = this.schema;
		this.schema = astSchema;
		this.firePropertyChanged(SCHEMA_PROPERTY, old, astSchema);
	}

	private String buildSchema(Annotation astAnnotation) {
		return this.schemaAdapter.getValue(astAnnotation);
	}

	public TextRange getSchemaTextRange() {
		return this.schemaTextRange;
	}

	private TextRange buildSchemaTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(SCHEMA_ADAPTER, astAnnotation);
	}

	public boolean schemaTouches(int pos) {
		return this.textRangeTouches(this.schemaTextRange, pos);
	}
}
