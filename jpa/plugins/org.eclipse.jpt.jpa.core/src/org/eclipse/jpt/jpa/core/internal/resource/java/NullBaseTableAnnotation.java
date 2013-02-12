/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java;

import org.eclipse.jpt.common.core.internal.resource.java.NullAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.resource.java.BaseTableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.UniqueConstraintAnnotation;

/**
 * <ul>
 * <li><code>javax.persistence.Table</code>
 * <li><code>javax.persistence.JoinTable</code>
 * <li><code>javax.persistence.CollectionTable</code>
 * <ul>
 */
public abstract class NullBaseTableAnnotation<A extends BaseTableAnnotation>
	extends NullAnnotation<A>
	implements BaseTableAnnotation
{
	protected NullBaseTableAnnotation(JavaResourceNode parent) {
		super(parent);
	}
	
	public boolean isSpecified() {
		return false;
	}
	
	// ***** name
	public String getName() {
		return null;
	}

	public void setName(String name) {
		if (name != null) {
			this.addAnnotation().setName(name);
		}
	}
	
	public TextRange getNameTextRange() {
		return null;
	}

	public TextRange getNameValidationTextRange() {
		return null;
	}

	public boolean nameTouches(int pos) {
		return false;
	}

	public boolean nameValidationTouches(int pos) {
		return false;
	}

	// ***** schema
	public String getSchema() {
		return null;
	}

	public void setSchema(String schema) {
		if (schema != null) {
			this.addAnnotation().setSchema(schema);
		}
	}
	
	public TextRange getSchemaTextRange() {
		return null;
	}

	public TextRange getSchemaValidationTextRange() {
		return null;
	}

	public boolean schemaTouches(int pos) {
		return false;
	}

	public boolean schemaValidationTouches(int pos) {
		return false;
	}

	// ***** catalog
	public String getCatalog() {
		return null;
	}

	public void setCatalog(String catalog) {
		if (catalog != null) {
			this.addAnnotation().setCatalog(catalog);
		}
	}
	
	public TextRange getCatalogTextRange() {
		return null;
	}

	public TextRange getCatalogValidationTextRange() {
		return null;
	}

	public boolean catalogTouches(int pos) {
		return false;
	}

	public boolean catalogValidationTouches(int pos) {
		return false;
	}

	// ***** unique constraints
	public ListIterable<UniqueConstraintAnnotation> getUniqueConstraints() {
		return EmptyListIterable.instance();
	}

	public int getUniqueConstraintsSize() {
		return 0;
	}

	public UniqueConstraintAnnotation uniqueConstraintAt(int index) {
		throw new UnsupportedOperationException();
	}

	public UniqueConstraintAnnotation addUniqueConstraint(int index) {
		return this.addAnnotation().addUniqueConstraint(index);
	}

	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public void removeUniqueConstraint(int index) {
		throw new UnsupportedOperationException();
	}
}
