/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IQueryHint;
import org.eclipse.jpt.core.internal.resource.java.QueryHint;

public class JavaQueryHint extends JavaContextModel implements IJavaQueryHint
{
	protected String name;

	protected String value;

	protected QueryHint queryHint;
	
	public JavaQueryHint(IJavaQuery<?> parent) {
		super(parent);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.queryHint.setName(newName);
		firePropertyChanged(IQueryHint.NAME_PROPERTY, oldName, newName);
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String newValue) {
		String oldValue = this.value;
		this.value = newValue;
		this.queryHint.setValue(newValue);
		firePropertyChanged(IQueryHint.VALUE_PROPERTY, oldValue, newValue);
	}


	public void initializeFromResource(QueryHint queryHint) {
		this.queryHint = queryHint;
		this.name = queryHint.getName();
		this.value = queryHint.getValue();
	}
	
	public void update(QueryHint queryHint) {
		this.queryHint = queryHint;
		this.setName(queryHint.getName());
		this.setValue(queryHint.getValue());
	}

	
	public ITextRange validationTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}
}
