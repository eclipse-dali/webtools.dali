/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.core.internal.JptPlugin;
import org.eclipse.jpt.common.core.internal.libval.LibraryValidatorManager;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.common.core.libval.LibraryValidator;
import org.osgi.framework.BundleContext;

// TODO bjv move to *private* internal package and move all public stuff to adapters etc.
public class JptCommonCorePlugin
	extends JptPlugin
{

	// ********** public constants **********

	/**
	 * The plug-in identifier of the jpt common core support
	 * (value <code>"org.eclipse.jpt.common.core"</code>).
	 */
	public static final String PLUGIN_ID = "org.eclipse.jpt.common.core";  //$NON-NLS-1$
	public static final String PLUGIN_ID_ = PLUGIN_ID + '.';


	/**
	 * The content type for Java source code files.
	 */
	public static final IContentType JAVA_SOURCE_CONTENT_TYPE = getContentType(JavaCore.JAVA_SOURCE_CONTENT_TYPE);
	
	/**
	 * The resource type for Java source code files
	 */
	public static final JptResourceType JAVA_SOURCE_RESOURCE_TYPE = new JptResourceType(JAVA_SOURCE_CONTENT_TYPE);
	
	/**
	 * The content type for Java archives (JARs).
	 */
	public static final IContentType JAR_CONTENT_TYPE = getJptContentType("jar"); //$NON-NLS-1$
	
	/**
	 * The resource type for Java archives (JARs).
	 */
	public static final JptResourceType JAR_RESOURCE_TYPE = new JptResourceType(JptCommonCorePlugin.JAR_CONTENT_TYPE);

	/**
	 * The content type for package-info Java code files.
	 */
	public static final IContentType JAVA_SOURCE_PACKAGE_INFO_CONTENT_TYPE = getJptContentType("javaPackageInfo"); //$NON-NLS-1$

	/**
	 * The resource type for package-info Java code files
	 */
	public static final JptResourceType JAVA_SOURCE_PACKAGE_INFO_RESOURCE_TYPE = new JptResourceType(JAVA_SOURCE_PACKAGE_INFO_CONTENT_TYPE);
	

	private static final String JPT_CONTENT_PREFIX = PLUGIN_ID_ + "content"; //$NON-NLS-1$
	
	private static final String JPT_CONTENT_PREFIX_ = JPT_CONTENT_PREFIX + '.';
	
	private static IContentType getJptContentType(String jptContentType) {
		return getContentType(JPT_CONTENT_PREFIX_ + jptContentType);
	}
	
	private static IContentType getContentType(String contentType) {
		return Platform.getContentTypeManager().getContentType(contentType);
	}
	
	public static Iterable<LibraryValidator> getLibraryValidators(
			JptLibraryProviderInstallOperationConfig config) {
		return LibraryValidatorManager.instance().getLibraryValidators(config);
	}

	// ********** singleton **********

	private static JptCommonCorePlugin INSTANCE;

	/**
	 * Return the singleton Dali common core plug-in.
	 */
	public static JptCommonCorePlugin instance() {
		return INSTANCE;
	}


	// ********** public static methods **********

	/**
	 * Log the specified message.
	 */
	public static void log(String msg) {
		INSTANCE.logError(msg);
    }

	/**
	 * Log the specified exception or error.
	 */
	public static void log(Throwable throwable) {
		INSTANCE.logError(throwable);
	}

	/**
	 * Log the specified message and exception or error.
	 */
	public static void log(String msg, Throwable throwable) {
		INSTANCE.logError(msg, throwable);
	}


	// ********** plug-in implementation **********

	public JptCommonCorePlugin() {
		super();
		if (INSTANCE != null) {
			throw new IllegalStateException();
		}
		// this convention is *wack*...  ~bjv
		INSTANCE = this;
	}

	@Override
	public synchronized void start(BundleContext context) throws Exception {
		super.start(context);
		// nothing yet...
	}

	@Override
	public synchronized void stop(BundleContext context) throws Exception {
		try {
			// nothing yet...
		} finally {
			super.stop(context);
		}
	}
}
