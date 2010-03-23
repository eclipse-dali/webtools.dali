/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.QueryHint;
import org.eclipse.jpt.core.context.java.JavaQuery;
import org.eclipse.jpt.core.context.java.JavaQueryHint;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.QueryHintAnnotation;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericJavaQueryHint extends AbstractJavaJpaContextNode implements JavaQueryHint
{
	protected String name;

	protected String value;

	protected QueryHintAnnotation resourceQueryHint;
	
	public GenericJavaQueryHint(JavaQuery parent) {
		super(parent);
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


	public void initialize(QueryHintAnnotation resourceQueryHint) {
		this.resourceQueryHint = resourceQueryHint;
		this.name = resourceQueryHint.getName();
		this.value = resourceQueryHint.getValue();
	}
	
	public void update(QueryHintAnnotation resourceQueryHint) {
		this.resourceQueryHint = resourceQueryHint;
		this.setName(resourceQueryHint.getName());
		this.setValue(resourceQueryHint.getValue());
	}

	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.resourceQueryHint.getTextRange(astRoot);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
	
}
