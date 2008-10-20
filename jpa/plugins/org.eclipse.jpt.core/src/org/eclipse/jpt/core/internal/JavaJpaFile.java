/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.internal.resource.java.JpaCompilationUnitImpl;
import org.eclipse.jpt.core.internal.utility.jdt.DefaultAnnotationEditFormatter;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.JpaCompilationUnit;

/**
 * JPA file for a compilation unit.
 */
public class JavaJpaFile
	extends AbstractJpaFile
{
	private final JpaCompilationUnit jpaCompilationUnit;


	public JavaJpaFile(JpaProject jpaProject, IFile file) {
		super(jpaProject, file);
		this.jpaCompilationUnit = this.buildJpaCompilationUnit();
	}

	protected JpaCompilationUnit buildJpaCompilationUnit() {
		return new JpaCompilationUnitImpl(
					JavaCore.createCompilationUnitFrom(this.getFile()),
					this.getJpaPlatform().getAnnotationProvider(),
					this.getJpaProject().getModifySharedDocumentCommandExecutorProvider(),
					DefaultAnnotationEditFormatter.instance(),
					this.getResourceModelListener()
				);
	}

	public Iterator<JavaResourcePersistentType> persistableTypes() {
		return this.jpaCompilationUnit.persistableTypes();
	}

	public String getResourceType() {
		return JAVA_RESOURCE_TYPE;
	}

	public void jpaFilesChanged() {
		this.jpaCompilationUnit.resolveTypes();
	}


	// ********** events **********

	@Override
	protected void resourceModelChanged() {
		if (this.jpaCompilationUnit == null) {
			throw new IllegalStateException("Change events should not be fired during construction."); //$NON-NLS-1$
		}
		super.resourceModelChanged();
	}

	public void javaElementChanged(ElementChangedEvent event) {
		this.jpaCompilationUnit.javaElementChanged(event);
	}

}
