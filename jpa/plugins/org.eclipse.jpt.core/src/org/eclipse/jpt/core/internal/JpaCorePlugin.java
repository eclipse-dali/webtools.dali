/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.internal.content.orm.OrmInit;
import org.eclipse.jpt.core.internal.content.persistence.PersistenceInit;
import org.osgi.framework.BundleContext;

public class JpaCorePlugin extends Plugin 
{
	private static JpaCorePlugin INSTANCE;
	
	/**
	 * The plug-in identifier of the persistence support
	 * (value <code>"org.eclipse.jpt.core"</code>).
	 */
	public final static String PLUGIN_ID = "org.eclipse.jpt.core";  //$NON-NLS-1$
	
	/**
	 * The identifier for the JPA facet
	 * (value <code>"jpt.jpa"</code>).
	 */
	public final static String FACET_ID = "jpt.jpa";  //$NON-NLS-1$
	
	/**
	 * Value of the content-type for orm.xml mappings files. Use this value to retrieve 
	 * the ORM xml content type from the content type manager, and to add new 
	 * orm.xml-like extensions to this content type.
	 * 
	 * @see org.eclipse.core.runtime.content.IContentTypeManager#getContentType(String)
	 */
	public static final String ORM_XML_CONTENT_TYPE = PLUGIN_ID + ".content.orm"; //$NON-NLS-1$
	
	public static final String PERSISTENCE_XML_CONTENT_TYPE = PLUGIN_ID + ".content.persistence"; //$NON-NLS-1$

	public static final String JAVA_CONTENT_TYPE = JavaCore.JAVA_SOURCE_CONTENT_TYPE;
	
	/**
	 * Returns the singular IJpaModel corresponding to the current workspace.
	 * 
	 * @return the singular IJpaModel corresponding to the current workspace.
	 */
	public static IJpaModel getJpaModel() {
		return JpaModelManager.instance().getJpaModel();
	}
	
	/**
	 * Returns the IJpaProject corresponding to the given IProject, 
	 * or <code>null</code> if unable to associate the given project with an 
	 * IJpaProject.
	 * 
	 * @param project the given project
	 * @return the IJpaProject corresponding to the given project, 
	 * or <code>null</code> if unable to associate the given project with an 
	 * IJpaProject
	 */
	public static IJpaProject getJpaProject(IProject project) {
		return JpaModelManager.instance().getJpaProject(project);
	}
	
	/**
	 * Returns the IJpaFile corresponding to the given IFile, 
	 * or <code>null</code> if unable to associate the given file with an 
	 * IJpaFile.
	 * 
	 * @param file the given file
	 * @return the IJpaFile corresponding to the given file, 
	 * or <code>null</code> if unable to associate the given file with an 
	 * IJpaFile
	 */
	public static IJpaFile getJpaFile(IFile file) {
		return JpaModelManager.instance().getJpaFile(file);
	}
	
	/**
	 * Returns the singleton DaliPlugin
	 */
	public static JpaCorePlugin getPlugin() {
		return INSTANCE;
	}
	
	public static void log(IStatus status) {
        INSTANCE.getLog().log(status);
    }
	
	public static void log(String msg) {
        log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, msg, null));
    }
	
	public static void log(Throwable throwable) {
		log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, throwable.getLocalizedMessage(), throwable));
	}
	
	
	public JpaCorePlugin() {
		super();
		INSTANCE = this;
	}
	
	
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		JpaModelManager.instance().startup();
		OrmInit.init();
		PersistenceInit.init();
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		try {
			JpaModelManager.instance().shutdown();
		}
		finally {
			super.stop(context);
		}
	}

}
