/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;



public class NullJoinColumn extends NullAbstractColumn implements JoinColumn, Annotation
{	
	public NullJoinColumn(JavaResource parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return JoinColumn.ANNOTATION_NAME;
	}

	@Override
	protected JoinColumn createColumnResource() {
		return (JoinColumn) super.createColumnResource();
	}

	public String getReferencedColumnName() {
		return null;
	}

	public void setReferencedColumnName(String referencedColumnName) {
		if (referencedColumnName != null) {
			createColumnResource().setReferencedColumnName(referencedColumnName);
		}		
	}

	public ITextRange referencedColumnNameTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	public boolean referencedColumnNameTouches(int pos, CompilationUnit astRoot) {
		return false;
	}
}
