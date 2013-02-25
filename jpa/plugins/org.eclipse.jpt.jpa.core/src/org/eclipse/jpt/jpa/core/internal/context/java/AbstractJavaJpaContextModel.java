/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.internal.context.AbstractJpaContextModel;

public abstract class AbstractJavaJpaContextModel
	extends AbstractJpaContextModel
{
	protected AbstractJavaJpaContextModel(JpaContextModel parent) {
		super(parent);
	}

	@Override
	public JptResourceType getResourceType() {
		return JavaSourceFileDefinition.instance().getResourceType();
	}
}
