/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.internal.context.AbstractJpaContextModel;

public abstract class AbstractJavaContextModel<P extends JpaContextModel>
	extends AbstractJpaContextModel<P>
{
	protected AbstractJavaContextModel(P parent) {
		super(parent);
	}

	@Override
	public JptResourceType getResourceType() {
		return JavaSourceFileDefinition.instance().getResourceType();
	}
}
