/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.osgi.service.debug.DebugOptions;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Common Dali UI plug-in behavior.
 * See JptPlugin
 */
public class JptUIPlugin
	extends AbstractUIPlugin
{
	protected BundleContext bundleContext;
	protected ServiceTracker<DebugOptions, DebugOptions> debugOptionsServiceTracker;


	protected JptUIPlugin() {
		super();
	}


	// ********** logging **********

	/**
	 * Log the specified message with a severity of
	 * {@link IStatus#ERROR ERROR}.
	 * @see #log(int, String)
	 * @see IStatus
	 */
	public void logError(String message) {
        this.log(IStatus.ERROR, message);
    }

	/**
	 * Log the specified message with the specified severity.
	 * @see IStatus#getSeverity()
	 */
	public void log(int severity, String message) {
        this.log(severity, message, null);
    }

	/**
	 * Log the specified exception or error with a severity of
	 * {@link IStatus#ERROR ERROR}.
	 * @see #log(int, Throwable)
	 * @see IStatus
	 */
	public void logError(Throwable throwable) {
        this.log(IStatus.ERROR, throwable);
	}

	/**
	 * Log the specified exception or error with the specified severity.
	 * @see IStatus#getSeverity()
	 */
	public void log(int severity, Throwable throwable) {
		this.log(severity, throwable.getLocalizedMessage(), throwable);
	}

	/**
	 * Log the specified message and exception or error with a severity of
	 * {@link IStatus#ERROR ERROR}.
	 * @see #log(int, String, Throwable)
	 * @see IStatus
	 */
	public void logError(String msg, Throwable throwable) {
		this.log(IStatus.ERROR, msg, throwable);
	}

	/**
	 * Log the specified message and exception or error
	 * with the specified severity.
	 * @see IStatus#getSeverity()
	 * @see IStatus#getCode()
	 */
	public void log(int severity, String msg, Throwable throwable) {
		this.log(severity, IStatus.OK, msg, throwable);
	}

	/**
	 * Log the specified message and exception or error
	 * with the specified severity and code.
	 * @see IStatus#getSeverity()
	 * @see IStatus#getCode()
	 */
	public void log(int severity, int code, String msg, Throwable throwable) {
		this.getLog().log(new Status(severity, this.getPluginID(), code, msg, throwable));
	}


	// ********** debug options **********

	/**
	 * Return the specified debug option as a <code>boolean</code> value.
	 * Return <code>false</code> if no such option is found.
	 * <p>
	 * The debug option is within the scope of the plug-in's debug options
	 * (e.g. for the plug-in <code>"org.eclipse.jpt.common.core"</code>,
	 * the specified option <code>"foo"</code> will be mapped to
	 * the {@link Platform} option
	 * <code>"org.eclipse.jpt.common.core/debug/foo"</code>).
	 */
	public boolean getBooleanDebugOption(String option) {
		return this.getBooleanDebugOption(option, false);
	}

	/**
	 * Return the specified debug option as a <code>boolean</code> value.
	 * Return the specified default value if no such option is found.
	 * <p>
	 * The debug option is within the scope of the plug-in's debug options
	 * (e.g. for the plug-in <code>"org.eclipse.jpt.common.core"</code>,
	 * the specified option <code>"foo"</code> will be mapped to
	 * the {@link Platform} option
	 * <code>"org.eclipse.jpt.common.core/debug/foo"</code>).
	 */
	public boolean getBooleanDebugOption(String option, boolean defaultValue) {
		String value = this.getDebugOption(option);
		return (value == null) ? defaultValue : Boolean.parseBoolean(value.trim());
	}

	/**
	 * Return the specified debug option as an <code>int</code> value.
	 * Return <code>-1</code> if no such option is found.
	 * <p>
	 * The debug option is within the scope of the plug-in's debug options
	 * (e.g. for the plug-in <code>"org.eclipse.jpt.common.core"</code>,
	 * the specified option <code>"foo"</code> will be mapped to
	 * the {@link Platform} option
	 * <code>"org.eclipse.jpt.common.core/debug/foo"</code>).
	 */
	public int getIntegerDebugOption(String option) {
		return this.getIntegerDebugOption(option, -1);
	}

	/**
	 * Return the specified debug option as an <code>int</code> value.
	 * Return the specified default value if no such option is found.
	 * <p>
	 * The debug option is within the scope of the plug-in's debug options
	 * (e.g. for the plug-in <code>"org.eclipse.jpt.common.core"</code>,
	 * the specified option <code>"foo"</code> will be mapped to
	 * the {@link Platform} option
	 * <code>"org.eclipse.jpt.common.core/debug/foo"</code>).
	 */
	public int getIntegerDebugOption(String option, int defaultValue) {
		String value = this.getDebugOption(option);
		return (value == null) ? defaultValue : Integer.parseInt(value.trim());
	}

	/**
	 * Return the specified debug option.
	 * Return <code>null</code> if no such option is found.
	 * <p>
	 * The debug option is within the scope of the plug-in's debug options
	 * (e.g. for the plug-in <code>"org.eclipse.jpt.common.core"</code>,
	 * the specified option <code>"foo"</code> will be mapped to
	 * the {@link Platform} option
	 * <code>"org.eclipse.jpt.common.core/debug/foo"</code>).
	 */
	public String getDebugOption(String option) {
		return this.getDebugOption(option, null);
	}

	/**
	 * Return the specified debug option.
	 * Return the specified default value if no such option is found.
	 * <p>
	 * The debug option is within the scope of the plug-in's debug options
	 * (e.g. for the plug-in <code>"org.eclipse.jpt.common.core"</code>,
	 * the specified option <code>"foo"</code> will be mapped to
	 * the {@link Platform} option
	 * <code>"org.eclipse.jpt.common.core/debug/foo"</code>).
	 */
	public String getDebugOption(String option, String defaultValue) {
		return this.isDebugging() ? this.getDebugOption_(option, defaultValue) : defaultValue;
	}

	protected String getDebugOption_(String option, String defaultValue) {
		if (StringTools.stringIsEmpty(option)) {
			throw new IllegalArgumentException("debug option cannot be blank"); //$NON-NLS-1$
		}
		String value = this.getDebugOption_(option);
		return (value != null) ? value : defaultValue;
	}

	protected String getDebugOption_(String option) {
		DebugOptions debugOptions = this.getDebugOptions();
		return (debugOptions == null) ? null : debugOptions.getOption(this.getPluginDebugOption() + option);
	}

	protected DebugOptions getDebugOptions() {
		ServiceTracker<DebugOptions, DebugOptions> tracker = this.getDebugOptionsServiceTracker();
		return (tracker == null) ? null : tracker.getService();
	}

	private synchronized ServiceTracker<DebugOptions, DebugOptions> getDebugOptionsServiceTracker() {
		if (this.isActive() && (this.debugOptionsServiceTracker == null)) {
			this.debugOptionsServiceTracker = this.buildDebugOptionsServiceTracker();
			this.debugOptionsServiceTracker.open();
		}
		return this.debugOptionsServiceTracker;
	}

	private ServiceTracker<DebugOptions, DebugOptions> buildDebugOptionsServiceTracker() {
		return new ServiceTracker<DebugOptions, DebugOptions>(this.bundleContext, DebugOptions.class, null);
	}

	/**
	 * Return the plug-in's debug option path.
	 */
	protected String getPluginDebugOption() {
		return this.getPluginID() + DEBUG_OPTION_SCOPE;
	}
	protected static final String DEBUG_OPTION_SCOPE = "/debug/"; //$NON-NLS-1$


	// ********** plug-in lifecycle **********

	@Override
	public synchronized void start(BundleContext context) throws Exception {
		super.start(context);
		this.bundleContext = context;
	}

	@Override
	public synchronized void stop(BundleContext context) throws Exception {
		try {
			this.stop_();
		} finally {
			this.bundleContext = null;
			super.stop(context);
		}
	}

	protected void stop_() throws Exception {
		if (this.debugOptionsServiceTracker != null) {
			this.debugOptionsServiceTracker.close();
			this.debugOptionsServiceTracker = null;
		}
	}


	// ********** misc **********

	public String getPluginID() {
		return this.getBundle().getSymbolicName();
	}

	/**
	 * Return whether the plug-in is active; i.e. it has been
	 * {@link #start(BundleContext) started}.
	 */
	public synchronized boolean isActive() {
		return this.bundleContext != null;
	}

	/**
	 * Return whether the plug-in is inactive; i.e. it has been
	 * {@link #stop(BundleContext) stopped} or not yet
	 * {@link #start(BundleContext) started}.
	 */
	public boolean isInactive() {
		return ! this.isActive();
	}
}
