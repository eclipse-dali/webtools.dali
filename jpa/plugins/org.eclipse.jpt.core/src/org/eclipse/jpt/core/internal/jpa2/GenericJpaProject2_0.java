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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.internal.AbstractJpaProject;
import org.eclipse.jpt.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.core.jpa2.StaticMetamodelSynchronizer;
import org.eclipse.jpt.core.jpa2.context.JpaRootContextNode2_0;

public class GenericJpaProject2_0
	extends AbstractJpaProject
	implements JpaProject2_0
{
	protected final StaticMetamodelSynchronizer staticMetamodelSynchronizer;

	public GenericJpaProject2_0(JpaProject.Config config) throws CoreException {
		super(config);
		this.staticMetamodelSynchronizer = this.buildStaticMetamodelSynchronizer();
	}

	protected StaticMetamodelSynchronizer buildStaticMetamodelSynchronizer() {
		return this.getJpaFactory().buildStaticMetamodelSynchronizer(this);
	}

	@Override
	protected JpaFactory2_0 getJpaFactory() {
		return (JpaFactory2_0) super.getJpaFactory();
	}

	@Override
	public JpaRootContextNode2_0 getRootContextNode() {
		return (JpaRootContextNode2_0) super.getRootContextNode();
	}

	public void synchronizeStaticMetamodel() {
		this.getRootContextNode().synchronizeStaticMetamodel();
	}

	@Override
	protected void update_(IProgressMonitor monitor) {
		super.update_(monitor);
//		this.synchronizeStaticMetamodel();
	}


	// ********** Static Metamodel **********

	public void synchronizeStaticMetamodel(PersistentType persistentType) {
		this.staticMetamodelSynchronizer.synchronize(persistentType);
	}

}
