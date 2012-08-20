/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaProject;

/**
 * Factory to build Dali adapters for an {@link IFile}
 * (The comments for the adapters produced by {@link ProjectAdapterFactory}
 * are applicable here also):<ul>
 * <li>{@link org.eclipse.jpt.jpa.core.JpaFile.Reference JpaFile.Reference}
 * <li>{@link org.eclipse.jpt.jpa.core.JpaFile JpaFile}
 * <li>{@link JptXmlResource} -
 *     Like the JPA file adapter, this adapter will only return a JPA XML
 *     resource if it is immediately available.
 *     This adapter should be used by any process that can ignore
 *     any uncreated JPA XML resources because it is demand-driven and re-queries
 *     for the JPA XML resource every time it executes and its results are only
 *     temporary (e.g. UI menus).
 * </ul>
 * See <code>org.eclipse.jpt.jpa.core/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 * 
 * @see ProjectAdapterFactory
 */
public class FileAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] {
			JpaFile.Reference.class,
			JpaFile.class,
			JptXmlResource.class
		};

	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}

	public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
		if (adaptableObject instanceof IFile) {
			return this.getAdapter((IFile) adaptableObject, adapterType);
		}
		return null;
	}
	
	private Object getAdapter(IFile file, Class<?> adapterType) {
		if (adapterType == JpaFile.Reference.class) {
			return this.getJpaFileReference(file);
		}
		if (adapterType == JpaFile.class) {
			return this.getJpaFile(file);
		}
		if (adapterType == JptXmlResource.class) {
			return this.getJpaXmlResource(file);
		}
		return null;
	}
	
	private JpaFile.Reference getJpaFileReference(IFile file) {
		return new JpaFileReference(file);
	}

	private JpaFile getJpaFile(IFile file) {
		JpaProject jpaProject = this.getJpaProject(file.getProject());
		return (jpaProject == null) ? null : jpaProject.getJpaFile(file);
	}

	private JptXmlResource getJpaXmlResource(IFile file) {
		JpaFile jpaFile = this.getJpaFile(file);
		if (jpaFile != null) {
			JptResourceModel resourceModel = jpaFile.getResourceModel();
			if (resourceModel instanceof JptXmlResource) {
				return (JptXmlResource) resourceModel;
			}
		}
		return null;
	}

	private JpaProject getJpaProject(IProject project) {
		return (JpaProject) project.getAdapter(JpaProject.class);
	}


	// ********** JPA file reference **********

	/**
	 * @see ProjectAdapterFactory.JpaProjectReference
	 */
	/* CU private */ static class JpaFileReference
		implements JpaFile.Reference
	{
		private final IFile file;

		JpaFileReference(IFile file) {
			super();
			this.file = file;
		}

		public JpaFile getValue() throws InterruptedException {
			JpaProject jpaProject = this.getJpaProject();
			return (jpaProject == null) ? null : jpaProject.getJpaFile(this.file);
		}

		private JpaProject getJpaProject() throws InterruptedException {
			return this.getJpaProjectReference().getValue();
		}

		private JpaProject.Reference getJpaProjectReference() {
			return ((JpaProject.Reference) this.file.getProject().getAdapter(JpaProject.Reference.class));
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, this.file);
		}
	}
}
