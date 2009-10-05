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

import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.core.jpa2.MetamodelSynchronizer;

/**
 * 
 */
public class NullMetamodelSynchronizer
	implements MetamodelSynchronizer
{
	protected final JpaProject2_0 jpaProject;

	public NullMetamodelSynchronizer(JpaProject2_0 jpaProject) {
		super();
		this.jpaProject = jpaProject;
	}

	public void synchronize(PersistentType persistentType) {
		//no-op
	}

	public JpaProject2_0 getJpaProject() {
		return this.jpaProject;
	}

	public IPackageFragmentRoot getSourceFolder() {
		return this.jpaProject.getMetamodelPackageFragmentRoot();
	}

}
