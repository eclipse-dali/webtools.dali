/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.resource.java.binary.BinarySequenceGeneratorAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.SequenceGenerator2_0Annotation;

/**
 * JPA 2.0
 * <code>javax.persistence.SequenceGenerator</code>
 */
public final class BinarySequenceGenerator2_0Annotation
	extends BinarySequenceGeneratorAnnotation
	implements SequenceGenerator2_0Annotation
{
	private String catalog;
	private String schema;


	public BinarySequenceGenerator2_0Annotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.catalog = this.buildCatalog();
		this.schema = this.buildSchema();
	}

	@Override
	public void update() {
		super.update();
		this.setCatalog_(this.buildCatalog());
		this.setSchema_(this.buildSchema());
	}

	// ********** SequenceGenerator2_0Annotation implementation **********

	// ********** catalog **********

	public String getCatalog() {
		return this.catalog;
	}

	public void setCatalog(String catalog) {
		throw new UnsupportedOperationException();
	}

	private void setCatalog_(String catalog) {
		String old = this.catalog;
		this.catalog = catalog;
		this.firePropertyChanged(CATALOG_PROPERTY, old, catalog);
	}

	private String buildCatalog() {
		return (String) this.getJdtMemberValue(JPA2_0.SEQUENCE_GENERATOR__CATALOG);
	}

	public TextRange getCatalogTextRange() {
		throw new UnsupportedOperationException();
	}

	public boolean catalogTouches(int pos) {
		throw new UnsupportedOperationException();
	}

	// ********** schema **********

	public String getSchema() {
		return this.schema;
	}

	public void setSchema(String schema) {
		throw new UnsupportedOperationException();
	}

	private void setSchema_(String schema) {
		String old = this.schema;
		this.schema = schema;
		this.firePropertyChanged(SCHEMA_PROPERTY, old, schema);
	}

	private String buildSchema() {
		return (String) this.getJdtMemberValue(JPA2_0.SEQUENCE_GENERATOR__SCHEMA);
	}

	public TextRange getSchemaTextRange() {
		throw new UnsupportedOperationException();
	}

	public boolean schemaTouches(int pos) {
		throw new UnsupportedOperationException();
	}
}
