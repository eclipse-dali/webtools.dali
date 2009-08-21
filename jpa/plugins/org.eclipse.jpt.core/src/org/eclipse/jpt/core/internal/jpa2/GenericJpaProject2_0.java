/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.internal.AbstractJpaProject;
import org.eclipse.jpt.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.core.jpa2.context.JpaRootContextNode2_0;

public class GenericJpaProject2_0
	extends AbstractJpaProject
	implements JpaProject2_0
{
	public GenericJpaProject2_0(JpaProject.Config config) throws CoreException {
		super(config);
	}

	@Override
	public JpaRootContextNode2_0 getRootContextNode() {
		return (JpaRootContextNode2_0) super.getRootContextNode();
	}

	public void synchronizeStaticMetaModel() {
		this.getRootContextNode().synchronizeStaticMetaModel();
	}

}
