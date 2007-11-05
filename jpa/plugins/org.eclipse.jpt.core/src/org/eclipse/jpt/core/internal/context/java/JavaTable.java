/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.resource.java.Table;


public class JavaTable extends AbstractJavaTable
{

	public JavaTable(IJpaContextNode parent) {
		super(parent);
	}

	@Override
	protected String annotationName() {
		return Table.ANNOTATION_NAME;
	}
	
	@Override
	public IJavaEntity parent() {
		return (IJavaEntity) super.parent();
	}
	
	protected IJavaEntity javaEntity() {
		return parent();
	}
	
	protected IEntity rootEntity() {
		return javaEntity().rootEntity();
	}
	
	@Override
	protected String defaultName() {
		//TODO default name after inheritanceStrategy is added
//		if (rootEntity().getInheritanceStrategy().isSingleTable()) {
//			if (rootEntity() != javaEntity()) {
//				return rootEntity().getTable().getName();
//			}
//		}
		return javaEntity().getName();

	}
//	@Override
//	protected JavaUniqueConstraint createJavaUniqueConstraint(int index) {
//		return JavaUniqueConstraint.createTableUniqueConstraint(new UniqueConstraintOwner(this), this.getMember(), index);
//	}
}
