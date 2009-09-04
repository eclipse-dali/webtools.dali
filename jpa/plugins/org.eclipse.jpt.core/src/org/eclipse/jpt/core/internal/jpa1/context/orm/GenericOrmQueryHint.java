/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.context.QueryHint;
import org.eclipse.jpt.core.context.orm.OrmQuery;
import org.eclipse.jpt.core.context.orm.OrmQueryHint;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.resource.orm.XmlQueryHint;
import org.eclipse.jpt.core.utility.TextRange;


public class GenericOrmQueryHint extends AbstractOrmXmlContextNode implements OrmQueryHint
{

	protected String name;

	protected String value;

	protected XmlQueryHint resourceQueryHint;
	
	public GenericOrmQueryHint(OrmQuery parent, XmlQueryHint resourceQueryHint) {
		super(parent);
		this.initialize(resourceQueryHint);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.resourceQueryHint.setName(newName);
		firePropertyChanged(QueryHint.NAME_PROPERTY, oldName, newName);
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String newValue) {
		String oldValue = this.value;
		this.value = newValue;
		this.resourceQueryHint.setValue(newValue);
		firePropertyChanged(QueryHint.VALUE_PROPERTY, oldValue, newValue);
	}

	protected void initialize(XmlQueryHint resourceQueryHint) {
		this.resourceQueryHint = resourceQueryHint;
		this.name = resourceQueryHint.getName();
		this.value = resourceQueryHint.getValue();
	}
	
	public void update(XmlQueryHint resourceQueryHint) {
		this.resourceQueryHint = resourceQueryHint;
		this.setName(resourceQueryHint.getName());
		this.setValue(resourceQueryHint.getValue());
	}
	
	public TextRange getValidationTextRange() {
		return this.resourceQueryHint.getValidationTextRange();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}

}
