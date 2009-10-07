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
import org.eclipse.jpt.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.core.jpa2.PersistentTypeMetamodelSynchronizer;
import org.eclipse.jpt.core.jpa2.MetamodelSynchronizer;

/**
 * 
 */
public class GenericMetamodelSynchronizer
	implements MetamodelSynchronizer
{
	protected final JpaProject2_0 jpaProject;
	protected final PersistentTypeMetamodelSynchronizer.Owner ptmsOwner;

	public GenericMetamodelSynchronizer(JpaProject2_0 jpaProject) {
		super();
		this.jpaProject = jpaProject;
		this.ptmsOwner = new PersistentTypeMetamodelSynchronizerOwner();
	}

	protected JpaFactory2_0 getJpaFactory() {
		return (JpaFactory2_0) this.jpaProject.getJpaPlatform().getJpaFactory();
	}

	public void synchronize(PersistentType persistentType) {
		this.buildPersistentTypeMetamodelSynchronizer(persistentType).synchronize();
	}

	protected PersistentTypeMetamodelSynchronizer buildPersistentTypeMetamodelSynchronizer(PersistentType persistentType) {
		return this.getJpaFactory().buildPersistentTypeMetamodelSynchronizer(this.ptmsOwner, persistentType);
	}

	protected IPackageFragmentRoot getSourceFolder() {
		return this.jpaProject.getMetamodelPackageFragmentRoot();
	}

	protected class PersistentTypeMetamodelSynchronizerOwner
		implements PersistentTypeMetamodelSynchronizer.Owner
	{
		public IPackageFragmentRoot getSourceFolder() {
			return GenericMetamodelSynchronizer.this.getSourceFolder();
		}
	}
}
