/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import java.util.Collection;
import java.util.Map;
import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.BodySourceWriter;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaPersistentType;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelSourceType;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaPersistentType2_0;

/**
 * JPA 2.0 Java persistent type.
 * Support for specified access and metamodel generation.
 */
public class GenericJavaPersistentType2_0
	extends AbstractJavaPersistentType
	implements JavaPersistentType2_0
{
	protected String declaringTypeName;

	protected final MetamodelSourceType.Synchronizer metamodelSynchronizer;


	public GenericJavaPersistentType2_0(PersistentType.Owner parent, JavaResourceType jrt) {
		super(parent, jrt);
		this.declaringTypeName = this.buildDeclaringTypeName();
		this.metamodelSynchronizer = this.buildMetamodelSynchronizer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setDeclaringTypeName(this.buildDeclaringTypeName());
	}


	// ********** declaring type name **********

	public String getDeclaringTypeName() {
		return this.declaringTypeName;
	}

	protected void setDeclaringTypeName(String declaringTypeName) {
		String old = this.declaringTypeName;
		this.declaringTypeName = declaringTypeName;
		this.firePropertyChanged(DECLARING_TYPE_NAME_PROPERTY, old, declaringTypeName);
	}

	protected String buildDeclaringTypeName() {
		return this.resourceType.getDeclaringTypeName();
	}


	// ********** metamodel **********

	public IFile getMetamodelFile() {
		return this.metamodelSynchronizer.getFile();
	}

	public void initializeMetamodel() {
		// do nothing - probably shouldn't be called...
	}

	public boolean isManaged() {
		return true;
	}

	public void synchronizeMetamodel(Map<String, Collection<MetamodelSourceType>> memberTypeTree) {
		this.metamodelSynchronizer.synchronize(memberTypeTree);
	}

	public void printBodySourceOn(BodySourceWriter pw, Map<String, Collection<MetamodelSourceType>> memberTypeTree) {
		this.metamodelSynchronizer.printBodySourceOn(pw, memberTypeTree);
	}

	public void disposeMetamodel() {
		// do nothing - probably shouldn't be called...
	}

	protected MetamodelSourceType.Synchronizer buildMetamodelSynchronizer() {
		return this.getJpaFactory2_0().buildMetamodelSynchronizer(this);
	}
}
