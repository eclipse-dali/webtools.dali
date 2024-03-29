/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.binary;

import java.util.Vector;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.resource.java.BaseTableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.UniqueConstraintAnnotation;

/**
 * <code><ul>
 * <li>javax.persistence.Table
 * <li>javax.persistence.JoinTable
 * <li>javax.persistence.SecondaryTable
 * <li>javax.persistence.CollectionTable
 * </ul></code>
 */
public abstract class BinaryBaseTableAnnotation
	extends BinaryAnnotation
	implements BaseTableAnnotation
{
	String name;
	String schema;
	String catalog;
	final Vector<UniqueConstraintAnnotation> uniqueConstraints;


	protected BinaryBaseTableAnnotation(JavaResourceModel parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.name = this.buildName();
		this.schema = this.buildSchema();
		this.catalog = this.buildCatalog();
		this.uniqueConstraints = this.buildUniqueConstraints();
	}

	@Override
	public void update() {
		super.update();
		this.setName_(this.buildName());
		this.setSchema_(this.buildSchema());
		this.setCatalog_(this.buildCatalog());
		this.updateUniqueConstraints();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** BaseTableAnnotation implementation **********

	public boolean isSpecified() {
		return true;
	}
	
	// ***** name
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		throw new UnsupportedOperationException();
	}

	private void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	private String buildName() {
		return (String) this.getJdtMemberValue(this.getNameElementName());
	}

	protected abstract String getNameElementName();
	
	public TextRange getNameTextRange() {
		throw new UnsupportedOperationException();
	}

	public TextRange getNameValidationTextRange() {
		throw new UnsupportedOperationException();
	}

	public boolean nameTouches(int pos) {
		throw new UnsupportedOperationException();
	}

	public boolean nameValidationTouches(int pos) {
		throw new UnsupportedOperationException();
	}

	// ***** schema
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
		return (String) this.getJdtMemberValue(this.getSchemaElementName());
	}

	protected abstract String getSchemaElementName();
	
	public TextRange getSchemaTextRange() {
		throw new UnsupportedOperationException();
	}

	public TextRange getSchemaValidationTextRange() {
		throw new UnsupportedOperationException();
	}

	public boolean schemaTouches(int pos) {
		throw new UnsupportedOperationException();
	}

	public boolean schemaValidationTouches(int pos) {
		throw new UnsupportedOperationException();
	}

	// ***** catalog
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
		return (String) this.getJdtMemberValue(this.getCatalogElementName());
	}

	protected abstract String getCatalogElementName();
	
	public TextRange getCatalogTextRange() {
		throw new UnsupportedOperationException();
	}

	public TextRange getCatalogValidationTextRange() {
		throw new UnsupportedOperationException();
	}

	public boolean catalogTouches(int pos) {
		throw new UnsupportedOperationException();
	}

	public boolean catalogValidationTouches(int pos) {
		throw new UnsupportedOperationException();
	}

	// ***** unique constraints

	public ListIterable<UniqueConstraintAnnotation> getUniqueConstraints() {
		return IterableTools.cloneLive(this.uniqueConstraints);
	}

	public int getUniqueConstraintsSize() {
		return this.uniqueConstraints.size();
	}

	public UniqueConstraintAnnotation uniqueConstraintAt(int index) {
		return this.uniqueConstraints.get(index);
	}

	public UniqueConstraintAnnotation addUniqueConstraint(int index) {
		throw new UnsupportedOperationException();
	}

	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public void removeUniqueConstraint(int index) {
		throw new UnsupportedOperationException();
	}

	private Vector<UniqueConstraintAnnotation> buildUniqueConstraints() {
		Object[] jdtUniqueConstraints = this.getJdtMemberValues(this.getUniqueConstraintElementName());
		Vector<UniqueConstraintAnnotation> result = new Vector<UniqueConstraintAnnotation>(jdtUniqueConstraints.length);
		for (Object jdtUniqueConstraint : jdtUniqueConstraints) {
			result.add(new BinaryUniqueConstraintAnnotation(this, (IAnnotation) jdtUniqueConstraint));
		}
		return result;
	}

	protected abstract String getUniqueConstraintElementName();

	// TODO
	private void updateUniqueConstraints() {
		throw new UnsupportedOperationException();
	}

}
