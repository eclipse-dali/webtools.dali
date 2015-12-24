/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.context.QueryHint;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryHint;
import org.eclipse.jpt.jpa.core.context.orm.OrmQuery;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryHint;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.core.resource.orm.XmlQueryHint;

/**
 * <code>orm.xml</code> query hint
 */
public class GenericOrmQueryHint
	extends AbstractOrmXmlContextModel<OrmQuery>
	implements OrmQueryHint
{
	protected final XmlQueryHint xmlQueryHint;

	protected String name;
	protected String value;


	public GenericOrmQueryHint(OrmQuery parent, XmlQueryHint xmlQueryHint) {
		super(parent);
		this.xmlQueryHint = xmlQueryHint;
		this.name = xmlQueryHint.getName();
		this.value = xmlQueryHint.getValue();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setName_(this.xmlQueryHint.getName());
		this.setValue_(this.xmlQueryHint.getValue());
	}


	// ********** name **********

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.setName_(name);
		this.xmlQueryHint.setName(name);
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
		this.setValue_(value);
		this.xmlQueryHint.setValue(value);
	}

	protected void setValue_(String value) {
		String old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}

	// ********** metadata conversion **********
 
	public void convertFrom(JavaQueryHint javaHint) {
		this.setName(javaHint.getName());
		this.setValue(javaHint.getValue());
	}

	// ********** validation **********

	public TextRange getValidationTextRange() {
		TextRange textRange = this.xmlQueryHint.getValidationTextRange();
		return (textRange != null) ? textRange : this.getQuery().getValidationTextRange();
	}

	public boolean isEquivalentTo(QueryHint hint) {
		return ObjectTools.equals(this.name, hint.getName()) &&
				ObjectTools.equals(this.value, hint.getValue()) ;
	}

	// ********** misc **********

	protected OrmQuery getQuery() {
		return this.parent;
	}

	public XmlQueryHint getXmlQueryHint() {
		return this.xmlQueryHint;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
}
