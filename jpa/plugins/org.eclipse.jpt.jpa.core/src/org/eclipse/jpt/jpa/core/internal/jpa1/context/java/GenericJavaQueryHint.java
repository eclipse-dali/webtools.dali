/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.jpa.core.context.QueryHint;
import org.eclipse.jpt.jpa.core.context.java.JavaQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryHint;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.core.resource.java.QueryHintAnnotation;

/**
 * Java query hing
 */
public class GenericJavaQueryHint
	extends AbstractJavaJpaContextNode
	implements JavaQueryHint
{
	protected final QueryHintAnnotation queryHintAnnotation;

	protected String name;
	protected String value;


	public GenericJavaQueryHint(JavaQuery parent, QueryHintAnnotation queryHintAnnotation) {
		super(parent);
		this.queryHintAnnotation = queryHintAnnotation;
		this.name = queryHintAnnotation.getName();
		this.value = queryHintAnnotation.getValue();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setName_(this.queryHintAnnotation.getName());
		this.setValue_(this.queryHintAnnotation.getValue());
	}


	// ********** name **********

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.queryHintAnnotation.setName(name);
		this.setName_(name);
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}


	// ********** value **********

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.queryHintAnnotation.setValue(value);
		this.setValue_(value);
	}

	protected void setValue_(String value) {
		String old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		TextRange textRange = this.queryHintAnnotation.getTextRange();
		return (textRange != null) ? textRange : this.getQuery().getValidationTextRange();
	}

	public boolean isEquivalentTo(QueryHint hint) {
		return Tools.valuesAreEqual(this.name, hint.getName()) &&
				Tools.valuesAreEqual(this.value, hint.getValue()) ;
	}

	// ********** misc **********

	@Override
	public JavaQuery getParent() {
		return (JavaQuery) super.getParent();
	}

	protected JavaQuery getQuery() {
		return this.getParent();
	}

	public QueryHintAnnotation getQueryHintAnnotation() {
		return this.queryHintAnnotation;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
}
