/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Map;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jpt.common.core.internal.JptCommonCoreMessages;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.utility.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ExceptionHandlerAdapter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.osgi.service.debug.DebugOptions;
import org.eclipse.osgi.service.debug.DebugTrace;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Common Dali plug-in behavior:<ul>
 * <li>plug-in lifecycle
 * <li>content types
 * <li>resource (project) persistent properties
 * <li>preferences
 * <li>logging
 * <li>statuses
 * <li>exception handler
 * <li>debug options
 * <li>tracing
 * </ul>
 * <strong>NB:</strong> The following plug-in state is read-write and any change
 * to the {@link #getPluginID() plug-in's ID} must consider backward and forward
 * compatibility (i.e. {@link #getOriginalPluginID_() it may be wise to continue
 * using the original plug-in ID} - In particular, forward-compatibility is necessary
 * for a team of developers to use different versions of the same plug-in when
 * reading and writing this state.):<ul>
 * <li>resource (project) persistent properties
 * <li>preferences
 * </ul>
 */
public abstract class JptPlugin
	implements BundleActivator
{
	private volatile Bundle bundle;

	// NB: the plug-in must be synchronized whenever accessing any of this state
	private ExceptionHandler exceptionHandler;
	private ServiceTracker<DebugOptions, DebugOptions> debugOptionsTracker;
	private DebugTrace debugTrace;


	/**
	 * Default constructor is required. Of course, subclass constructors must
	 * be <code>public</code>.
	 */
	protected JptPlugin() {
		super();
	}


	// ********** plug-in lifecycle **********

	/**
	 * Subclass should call <code>super.start(context)</code> at the beginning
	 * of its override implementation.
	 * <p>
	 * <strong>NB:</strong> Most state should be built lazily....
	 */
	public void start(BundleContext context) throws Exception {
		this.bundle = context.getBundle();
		// make the instance available immediately; although nothing *should*
		// retrieve it during start-up, as most state should be populated lazily...
		this.setInstance(this);
	}

	/**
	 * Set the plug-in's singleton instance.
	 */
	protected abstract void setInstance(JptPlugin plugin);

	/**
	 * Subclass should call <code>super.stop(context)</code> at the end
	 * of its override implementation.
	 * <p>
	 * <strong>NB:</strong> the plug-in should not return from this method
	 * until all its outstanding processes are finished.
	 */
	public void stop(BundleContext context) throws Exception {
		// the service tracker must be rebuilt with a valid bundle context if the plug-in is restarted
		this.closeDebugOptionsTracker();
		// seems like we can leave 'exceptionHandler' in place...
	}


	// ********** content type **********

	/**
	 * Return the content type corresponding to the
	 * specified context type within the scope of the plug-in; i.e prefix the
	 * specified content type with the
	 * {@link #getContentTypeScope() plug-in's content type scope}.
	 * Return <code>null</code> if the plug-in has no bundle.
	 */
	public IContentType getContentType(String contentType) {
		StringBuilder scope = this.getContentTypeScope();
		return (scope == null) ? null : Platform.getContentTypeManager().getContentType(scope.append(contentType).toString());
	}

	/**
	 * Return the name of the plug-in's {@link IContentType content type} scope.
	 * Include an appended <code>'.'</code>.
	 * By default, this is in the form
	 * <em>&lt;plug-in ID&gt;.content.</em>
	 * (e.g. <code>"org.eclipse.jpt.common.core.content."</code>).
	 * Return <code>null</code> if the plug-in has no bundle.
	 */
	protected StringBuilder getContentTypeScope() {
		String id = this.getPluginID();
		if (id == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder(id);
		sb.append('.');
		sb.append(this.getRelativeContentTypeScope());
		sb.append('.');
		return sb;
	}

	/**
	 * @see #RELATIVE_CONTENT_TYPE_SCOPE
	 */
	protected String getRelativeContentTypeScope() {
		return RELATIVE_CONTENT_TYPE_SCOPE;
	}
	/**
	 * Value: <code>{@value}</code>
	 */
	protected static final String RELATIVE_CONTENT_TYPE_SCOPE = "content"; //$NON-NLS-1$


	// ********** resource persistent properties **********

	/**
	 * Return the specified {@link IResource#getPersistentProperty(QualifiedName)
	 * resource's persistent property}, relative to the plug-in's ID.
	 * Return <code>null</code> if the plug-in has no bundle
	 * or if there are any problems retrieving the property.
	 */
	public String getPersistentProperty(IResource resource, String key) {
		QualifiedName qName = this.buildPersistentPropertyQualifiedName(key);
		try {
			return (qName == null) ? null : resource.getPersistentProperty(qName);
		} catch (CoreException ex) {
			this.logError(ex);
			return null;
		}
	}

	/**
	 * Set the specified {@link IResource#setPersistentProperty(QualifiedName, String)
	 * resource's persistent property}, relative to the plug-in's ID.
	 */
	public void setPersistentProperty(IResource resource, String key, String value) {
		QualifiedName qName = this.buildPersistentPropertyQualifiedName(key);
		try {
			if (qName != null) {
				resource.setPersistentProperty(qName, value);
			}
		} catch (CoreException ex) {
			this.logError(ex);
		}
	}

	/**
	 * Remove the specified {@link IResource#getPersistentProperties()
	 * resource's persistent properties}, relative to the plug-in's ID.
	 */
	public void removePersistentProperties(IResource resource) {
		try {
			this.removePersistentProperties_(resource);
		} catch (CoreException ex) {
			this.logError(ex);
		}
	}

	protected void removePersistentProperties_(IResource resource) throws CoreException {
		Map<QualifiedName, String> props = resource.getPersistentProperties();
		for (QualifiedName key : props.keySet()) {
			resource.setPersistentProperty(key, null);
		}
	}

	/**
	 * Qualify the specified relative name with the plug-in's ID.
	 * Return <code>null</code> if the plug-in has no bundle.
	 */
	protected QualifiedName buildPersistentPropertyQualifiedName(String relativeName) {
		String id = this.getPersistentPropertyPluginID();
		return (id == null) ? null : new QualifiedName(id, relativeName);
	}

	/**
	 * @see #getOriginalPluginID_()
	 */
	protected String getPersistentPropertyPluginID() {
		return this.getOriginalPluginID();
	}


	// ********** preferences **********

	/**
	 * Return the value of the specified preference, searching the plug-in's
	 * workspace and default preferences.
	 * Return <code>null</code> if the specified preference is not found.
	 */
	public String getPreference(String key) {
		return this.getPreference(key, null);
	}

	/**
	 * Return the value of the specified preference, searching the plug-in's
	 * workspace and default preferences.
	 * Return the specified default value if the specified preference is not found.
	 */
	public String getPreference(String key, String defaultValue) {
		return this.searchPreferences(key, this.getPreferenceSearchPath(), defaultValue);
	}

	/**
	 * Return the value of the plug-in's specified project preference.
	 * Return <code>null</code> if the specified preference is not found.
	 */
	public String getPreference(IProject project, String key) {
		return this.getPreference(project, key, null);
	}

	/**
	 * Return the value of the plug-in's specified project preference.
	 * Return the specified default value if the specified preference is not found.
	 */
	public String getPreference(IProject project, String key, String defaultValue) {
		return this.searchPreferences(key, this.getPreferenceSearchPath(project), defaultValue);
	}

	/**
	 * Return the value of the specified preference, searching the plug-in's
	 * workspace and default preferences.
	 * Return <code>false</code> if the specified preference is not found.
	 */
	public boolean getBooleanPreference(String key) {
		return this.getBooleanPreference(key, false);
	}

	/**
	 * Return the value of the specified preference, searching the plug-in's
	 * workspace and default preferences.
	 * Return the specified default value if the specified preference is not found.
	 */
	public boolean getBooleanPreference(String key, boolean defaultValue) {
		return this.searchBooleanPreferences(key, this.getPreferenceSearchPath(), defaultValue);
	}

	/**
	 * Return the value of the plug-in's specified project preference.
	 * Return <code>false</code> if the specified preference is not found.
	 */
	public boolean getBooleanPreference(IProject project, String key) {
		return this.getBooleanPreference(project, key, false);
	}

	/**
	 * Return the value of the plug-in's specified project preference.
	 * Return the specified default value if the specified preference is not found.
	 */
	public boolean getBooleanPreference(IProject project, String key, boolean defaultValue) {
		return this.searchBooleanPreferences(key, this.getPreferenceSearchPath(project), defaultValue);
	}

	/**
	 * Search the specified sets of preferences and return the value of the
	 * first occurrence of the specified key. Return the specified default
	 * value if none of the specified preference trees contain the specified
	 * key.
	 */
	protected boolean searchBooleanPreferences(String key, IEclipsePreferences[] searchPath, boolean defaultValue) {
		String value = this.searchPreferences(key, searchPath);
		return (value == null) ? defaultValue : Boolean.parseBoolean(value);
	}

	/**
	 * Return the value of the specified preference, searching the plug-in's
	 * workspace and default preferences.
	 * Return <code>-1</code> if the specified preference is not found.
	 */
	public int getIntPreference(String key) {
		return this.getIntPreference(key, -1);
	}

	/**
	 * Return the value of the specified preference, searching the plug-in's
	 * workspace and default preferences.
	 * Return the specified default value if the specified preference is not found.
	 */
	public int getIntPreference(String key, int defaultValue) {
		return this.searchIntPreferences(key, this.getPreferenceSearchPath(), defaultValue);
	}

	/**
	 * Return the value of the plug-in's specified project preference.
	 * Return <code>-1</code> if the specified preference is not found.
	 */
	public int getIntPreference(IProject project, String key) {
		return this.getIntPreference(project, key, -1);
	}

	/**
	 * Return the value of the plug-in's specified project preference.
	 * Return the specified default value if the specified preference is not found.
	 */
	public int getIntPreference(IProject project, String key, int defaultValue) {
		return this.searchIntPreferences(key, this.getPreferenceSearchPath(project), defaultValue);
	}

	/**
	 * Search the specified sets of preferences and return the value of the
	 * first occurrence of the specified key. Return the specified default
	 * value if none of the specified preference trees contain the specified
	 * key.
	 */
	protected int searchIntPreferences(String key, IEclipsePreferences[] searchPath, int defaultValue) {
		String value = this.searchPreferences(key, searchPath);
		if (value == null) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * Search the specified sets of preferences and return the value of the
	 * first occurrence of the specified key. Return the specified default
	 * value if none of the specified preference trees contain the specified
	 * key.
	 */
	protected String searchPreferences(String key, IEclipsePreferences[] searchPath, String defaultValue) {
		String value = this.searchPreferences(key, searchPath);
		return (value != null) ? value : defaultValue;
	}

	/**
	 * Search the specified sets of preferences and return the value of the
	 * first occurrence of the specified key. Return <code>null</code> if none
	 * of the specified preference trees contain the specified key.
	 */
	protected String searchPreferences(String key, IEclipsePreferences[] searchPath) {
		for (IEclipsePreferences prefs : searchPath) {
			if (prefs != null) {  // prefs can be null if the plug-in is inactive
				String value = prefs.get(key, null);
				if (value != null) {
					return value;
				}
			}
		}
		return null;
	}

	/**
	 * Return the plug-in's preference search path (typically, the plug-in's
	 * workspace preferences followed by its default preferences).
	 */
	protected IEclipsePreferences[] getPreferenceSearchPath() {
		return new IEclipsePreferences[] {
				this.getWorkspacePreferences(),
				this.getDefaultPreferences(),  // "original" preferences
				this.getCurrentDefaultPreferences()  // may be the same as the "original" preferences
			};
	}

	/**
	 * Return the plug-in's preference search path (typically, only the
	 * plug-in's project preferences).
	 */
	protected IEclipsePreferences[] getPreferenceSearchPath(IProject project) {
		return ArrayTools.add(this.getPreferenceSearchPath(), 0, this.getProjectPreferences(project));
	}

	/**
	 * Set the value of the plug-in's specified project preference.
	 * If the new value is <code>null</code>, remove the preference.
	 */
	public void setPreference(IProject project, String key, String value) {
		IEclipsePreferences prefs = this.getProjectPreferences(project);
		if (prefs != null) {
			if (value == null) {
				prefs.remove(key);
			} else {
				prefs.put(key, value);
			}
			this.flushPreferences(prefs);
		}
	}

	/**
	 * Set the value of the plug-in's specified workspace preference.
	 * If the new value is <code>null</code>, remove the preference.
	 */
	public void setPreference(String key, String value) {
		IEclipsePreferences prefs = this.getWorkspacePreferences();
		if (prefs != null) {
			if (value == null) {
				prefs.remove(key);
			} else {
				prefs.put(key, value);
			}
			this.flushPreferences(prefs);
		}
	}

	/**
	 * Set the value of the plug-in's specified default preference.
	 * Typically called during default preferences initialization
	 * (as configured by an extension of
	 * <code>org.eclipse.core.runtime.preferences</code>).
	 * If the new value is <code>null</code>, remove the preference.
	 */
	public void setDefaultPreference(String key, String value) {
		IEclipsePreferences prefs = this.getCurrentDefaultPreferences();
		if (prefs != null) {
			if (value == null) {
				prefs.remove(key);
			} else {
				prefs.put(key, value);
			}
			this.flushPreferences(prefs);
		}
	}

	/**
	 * Set the value of the plug-in's specified project preference.
	 */
	public void setBooleanPreference(IProject project, String key, boolean value) {
		IEclipsePreferences prefs = this.getProjectPreferences(project);
		if (prefs != null) {
			prefs.putBoolean(key, value);
			this.flushPreferences(prefs);
		}
	}

	/**
	 * Set the value of the plug-in's specified workspace preference.
	 */
	public void setBooleanPreference(String key, boolean value) {
		IEclipsePreferences prefs = this.getWorkspacePreferences();
		if (prefs != null) {
			prefs.putBoolean(key, value);
			this.flushPreferences(prefs);
		}
	}

	/**
	 * Set the value of the plug-in's specified default preference.
	 * Typically called during default preferences initialization
	 * (as configured by an extension of
	 * <code>org.eclipse.core.runtime.preferences</code>).
	 */
	public void setBooleanDefaultPreference(String key, boolean value) {
		IEclipsePreferences prefs = this.getCurrentDefaultPreferences();
		if (prefs != null) {
			prefs.putBoolean(key, value);
			this.flushPreferences(prefs);
		}
	}

	/**
	 * Set the value of the plug-in's specified project preference.
	 */
	public void setIntPreference(IProject project, String key, int value) {
		IEclipsePreferences prefs = this.getProjectPreferences(project);
		if (prefs != null) {
			prefs.putInt(key, value);
			this.flushPreferences(prefs);
		}
	}

	/**
	 * Set the value of the plug-in's specified workspace preference.
	 */
	public void setIntPreference(String key, int value) {
		IEclipsePreferences prefs = this.getWorkspacePreferences();
		if (prefs != null) {
			prefs.putInt(key, value);
			this.flushPreferences(prefs);
		}
	}

	/**
	 * Set the value of the plug-in's specified default preference.
	 * Typically called during default preferences initialization
	 * (as configured by an extension of
	 * <code>org.eclipse.core.runtime.preferences</code>).
	 */
	public void setIntDefaultPreference(String key, int value) {
		IEclipsePreferences prefs = this.getCurrentDefaultPreferences();
		if (prefs != null) {
			prefs.putInt(key, value);
			this.flushPreferences(prefs);
		}
	}

	/**
	 * Remove the value of the plug-in's specified project preference.
	 */
	public void removePreference(IProject project, String key) {
		IEclipsePreferences prefs = this.getProjectPreferences(project);
		if (prefs != null) {
			prefs.remove(key);
			this.flushPreferences(prefs);
		}
	}

	/**
	 * Remove the value of the plug-in's specified workspace preference.
	 */
	public void removePreference(String key) {
		IEclipsePreferences prefs = this.getWorkspacePreferences();
		if (prefs != null) {
			prefs.remove(key);
			this.flushPreferences(prefs);
		}
	}

	/**
	 * Remove the plug-in's preferences for the specified project.
	 */
	public void removePreferences(IProject project) {
		this.removePreferences(this.getProjectPreferences(project));
	}

	/**
	 * Remove the plug-in's workspace preferences.
	 */
	public void removePreferences() {
		this.removePreferences(this.getWorkspacePreferences());
	}

	protected void removePreferences(IEclipsePreferences prefs) {
		try {
			if (prefs != null) {
				prefs.removeNode();
			}
		} catch (BackingStoreException ex) {
			this.logError(ex);
		}
	}

	/**
	 * Return the plug-in's workspace preferences. These preferences are written
	 * to disk when the workspace is closed.
	 * Return <code>null</code> if the plug-in has no bundle.
	 * These preferences are stored the file<br>
	 * <code>
	 * <em>&lt;workspace dir&gt;</em>/<em>&lt;project dir&gt;</em>/.settings/<em>&lt;plug-in ID&gt;</em>.prefs
	 * </code>
	 */
	protected IEclipsePreferences getProjectPreferences(IProject project) {
		return this.getPreferences(new ProjectScope(project));
	}

	/**
	 * Return the plug-in's workspace preferences. These preferences are written
	 * to disk when the workspace is closed.
	 * Return <code>null</code> if the plug-in has no bundle.
	 * These preferences are stored the file<br>
	 * <code>
	 * <em>&lt;workspace dir&gt;</em>/.metadata/.plugins/org.eclipse.core.runtime/.settings/<em>&lt;plug-in ID&gt;</em>.prefs
	 * </code>
	 */
	protected IEclipsePreferences getWorkspacePreferences() {
		return this.getPreferences(InstanceScope.INSTANCE);
	}

	/**
	 * Return the plug-in's <em>current</em> default preferences.
	 * These preferences are initialized
	 * during default preferences initialization and are not saved to disk.
	 * Return <code>null</code> if the plug-in has no bundle.
	 * <p>
	 * These preferences must use the <em>current</em> plug-in ID as the
	 * extension is associated with the plug-in.
	 * <p>
	 * Default preferences are calculated upon first reference. The precedence
	 * is:<ul>
	 * <li>command-line
	 * <li>product
	 * <li>bundle
	 * <li>run-time
	 * </ul>
	 * @see org.eclipse.core.internal.preferences.DefaultPreferences#load()
	 * @see <a href="http://www.eclipse.org/eclipse/platform-core/documents/user_settings/plugin_customization.html">
	 *      Plug-in Customization documentation</a>
	 */
	@SuppressWarnings("restriction")
	protected IEclipsePreferences getCurrentDefaultPreferences() {
		String qualifier = this.getPluginID();
		return (qualifier == null) ? null : DefaultScope.INSTANCE.getNode(qualifier);
	}

	/**
	 * Return the plug-in's default preferences, as determined by the
	 * {@link #getPreferencesPluginID() "preferences plug-in ID"} which,
	 * by default, is the {@link #getOriginalPluginID() "original plug-in ID"}.
	 * This is used only to <em>read</em> default preferences that may be
	 * set via product customization using the original plug-in ID.
	 */
	protected IEclipsePreferences getDefaultPreferences() {
		return this.getPreferences(DefaultScope.INSTANCE);
	}

	/**
	 * Return the plug-in's preferences for the specified context.
	 * Return <code>null</code> if the plug-in has no bundle.
	 */
	protected IEclipsePreferences getPreferences(IScopeContext context) {
		String qualifier = this.getPreferencesPluginID();
		return (qualifier == null) ? null : context.getNode(qualifier);
	}

	/**
	 * Return <code>null</code> if the plug-in has no bundle.
	 * @see #getOriginalPluginID_()
	 */
	protected String getPreferencesPluginID() {
		return this.getOriginalPluginID();
	}

	/**
	 * Flush the specified preferences in an asynchronous
	 * {@link FlushPreferencesJob job}
	 * because the flush request can trigger a lock on the project,
	 * which can cause deadlocks (e.g. when deleting the metamodel source
	 * folder).
	 * <p>
	 * Note: The flush will also remove the preferences node if it is empty.
	 */
	protected void flushPreferences(IEclipsePreferences prefs) {
		if (FlushPreferences) {
			new FlushPreferencesJob(prefs).schedule();
		}
	}

	/**
	 * This flag is set to <code>false</code> when the test plug-in is loaded.
	 * The preferences end up getting flushed after the test case has deleted
	 * its project, resulting in resource exceptions in the log, e.g.
	 * <pre>
	 *     Resource '/JpaProjectManagerTests' is not open.
	 * </pre>
	 * See <code>JptJpaCoreTestsPlugin.start_()</code>
	 */
	public static volatile boolean FlushPreferences = true;

	protected static class FlushPreferencesJob
		extends Job
	{
		private final IEclipsePreferences prefs;

		FlushPreferencesJob(IEclipsePreferences prefs) {
			super(NLS.bind(JptCommonCoreMessages.PREFERENCES_FLUSH_JOB_NAME, prefs.absolutePath()));
			this.prefs = prefs;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			try {
				this.prefs.flush();
			} catch(BackingStoreException ex) {
				return JptCommonCorePlugin.instance().logError(ex);
			}
			return Status.OK_STATUS;
		}
	}


	// ********** logging **********

	/**
	 * Log the specified message with a severity of
	 * {@link IStatus#ERROR ERROR}.
	 * Return the logged status.
	 * @see #log(int, String)
	 * @see IStatus
	 */
	public IStatus logError(String message) {
		return this.log(IStatus.ERROR, message);
	}

	/**
	 * Log the specified message with the specified severity.
	 * Return the logged status.
	 * @see IStatus#getSeverity()
	 */
	public IStatus log(int severity, String message) {
		return this.log(severity, message, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Log the specified message with a severity of
	 * {@link IStatus#ERROR ERROR}.
	 * Bind the message to the specified arguments.
	 * Return the logged status.
	 * @see #log(int, String, Object[])
	 * @see IStatus
	 */
	public IStatus logError(String message, Object... args) {
		return this.log(IStatus.ERROR, message, args);
	}

	/**
	 * Log the specified message with the specified severity.
	 * Bind the message to the specified arguments.
	 * Return the logged status.
	 * @see IStatus#getSeverity()
	 */
	public IStatus log(int severity, String message, Object... args) {
		return this.log(severity, (Throwable) null, message, args);
	}

	/**
	 * Log the specified exception with a severity of
	 * {@link IStatus#ERROR ERROR}.
	 * Return the logged status.
	 * @see #log(int, Throwable)
	 * @see IStatus
	 */
	public IStatus logError(Throwable throwable) {
		return this.log(IStatus.ERROR, throwable);
	}

	/**
	 * Log the specified exception with the specified severity.
	 * Return the logged status.
	 * @see IStatus#getSeverity()
	 */
	public IStatus log(int severity, Throwable throwable) {
		return this.log(severity, throwable, throwable.getLocalizedMessage());
	}

	/**
	 * Log the specified message and exception with a severity of
	 * {@link IStatus#ERROR ERROR}.
	 * Return the logged status.
	 * @see #log(int, Throwable, String)
	 * @see IStatus
	 */
	public IStatus logError(Throwable throwable, String message) {
		return this.log(IStatus.ERROR, throwable, message);
	}

	/**
	 * Log the specified message and exception with the specified severity.
	 * Return the logged status.
	 * @see IStatus#getSeverity()
	 */
	public IStatus log(int severity, Throwable throwable, String message) {
		return this.log(severity, IStatus.OK, throwable, message);
	}

	/**
	 * Log the specified message and exception with a severity of
	 * {@link IStatus#ERROR ERROR}.
	 * Bind the message to the specified arguments.
	 * Return the logged status.
	 * @see #log(int, Throwable, String, Object[])
	 * @see IStatus
	 */
	public IStatus logError(Throwable throwable, String message, Object... args) {
		return this.log(IStatus.ERROR, throwable, message, args);
	}

	/**
	 * Log the specified message and exception with the specified severity.
	 * Bind the message to the specified arguments.
	 * Return the logged status.
	 * @see IStatus#getSeverity()
	 */
	public IStatus log(int severity, Throwable throwable, String message, Object... args) {
		return this.log(severity, IStatus.OK, throwable, message, args);
	}

	/**
	 * Log the specified message and exception with the specified severity
	 * and code.
	 * If the plug-in has no {@link #getBundle() bundle}, log the information
	 * to the appropriate Java system log (instead of the Eclise platform log).
	 * Return the logged status.
	 * @see IStatus#getSeverity()
	 * @see IStatus#getCode()
	 */
	public IStatus log(int severity, int code, Throwable throwable, String message) {
		return this.log(severity, code, throwable, message, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Log the specified message and exception with the specified severity
	 * and code. Bind the message to the specified arguments.
	 * If the plug-in has no {@link #getBundle() bundle}, log the information
	 * to the appropriate Java system log (instead of the Eclise platform log).
	 * Return the logged status.
	 * @see IStatus#getSeverity()
	 * @see IStatus#getCode()
	 */
	public IStatus log(int severity, int code, Throwable throwable, String message, Object... args) {
		if (args.length > 0) {
			message = NLS.bind(message, args);
		}
		String id = this.getPluginID();
		IStatus status = new Status(severity, ((id != null) ? id : UNKNOWN_PLUGIN_ID), code, message, throwable);
		if (id != null) {
			ILog log = this.getLog();
			if (log != null) {
				log.log(status);
			} else {
				this.log_(status);
			}
		} else {
			this.log_(status);
		}
		return status;
	}
	protected static final String UNKNOWN_PLUGIN_ID = "unknown"; //$NON-NLS-1$

	/**
	 * Return the plug-in's log.
	 * Return <code>null</code> if the plug-in has no bundle.
	 * @see Platform#getLog(Bundle)
	 */
	public ILog getLog() {
		Bundle b = this.getBundle();
		return (b == null) ? null : Platform.getLog(b);
	}

	/**
	 * Log the specified message and exception with the specified severity
	 * and code to the appropriate Java system log.
	 * This method is called when the plug-in is inactive or the Eclipse
	 * platform log is unavailable.
	 * @see IStatus#getSeverity()
	 * @see IStatus#getCode()
	 */
	protected void log_(IStatus status) {
		PrintStream stream = System.out;
		int severity = status.getSeverity();
		if ((severity == IStatus.ERROR) || (severity == IStatus.WARNING)) {
			stream = System.err; // ???
		}
		synchronized (stream) {
			this.log_(new PrintWriter(stream), status);
		}
	}

	protected void log_(PrintWriter writer, IStatus status) {
		writer.print(this.getClass().getName());
		writer.print(':');
		writer.println();
		int severity = status.getSeverity();
		switch (severity) {
			case IStatus.CANCEL:
				writer.print("CANCEL"); //$NON-NLS-1$
				break;
			case IStatus.ERROR:
				writer.print("ERROR"); //$NON-NLS-1$
				break;
			case IStatus.WARNING:
				writer.print("WARNING"); //$NON-NLS-1$
				break;
			case IStatus.INFO:
				writer.print("INFO"); //$NON-NLS-1$
				break;
			case IStatus.OK:
				writer.print("OK"); //$NON-NLS-1$
				break;
			default:
				writer.print("SEVERITY "); //$NON-NLS-1$
				writer.print(severity);
				break;
		}
		int code = status.getCode();
		if (code != IStatus.OK) {
			writer.print(" (code="); //$NON-NLS-1$
			writer.print(code);
			writer.print(")"); //$NON-NLS-1$
		}
		String message = status.getMessage();
		if (message != null) {
			writer.print(": "); //$NON-NLS-1$
			writer.print(message);
		}
		writer.println();
		Throwable throwable = status.getException();
		if (throwable != null) {
			throwable.printStackTrace(writer);
		}
	}


	// ********** statuses **********

	/**
	 * Build a status with a severity of
	 * {@link IStatus#ERROR ERROR}.
	 * @see #buildStatus(int)
	 * @see IStatus
	 */
	public IStatus buildErrorStatus() {
		return this.buildStatus(IStatus.ERROR);
	}

	/**
	 * Build a status with a severity of
	 * {@link IStatus#OK OK}.
	 * @see #buildStatus(int)
	 * @see IStatus
	 */
	public IStatus buildOKStatus() {
		return this.buildStatus(IStatus.OK);
	}

	/**
	 * Build a status with the specified severity.
	 * @see IStatus#getSeverity()
	 */
	public IStatus buildStatus(int severity) {
		return this.buildStatus(severity, (String) null);  // message can be null
	}

	/**
	 * Build a status with the specified message with a severity of
	 * {@link IStatus#ERROR ERROR}.
	 * @see #buildStatus(int, String)
	 * @see IStatus
	 */
	public IStatus buildErrorStatus(String message) {
		return this.buildStatus(IStatus.ERROR, message);
	}

	/**
	 * Build a status with the specified message with a severity of
	 * {@link IStatus#WARNING WARNING}.
	 * @see #buildStatus(int, String)
	 * @see IStatus
	 */
	public IStatus buildWarningStatus(String message) {
		return this.buildStatus(IStatus.WARNING, message);
	}

	/**
	 * Build a status with the specified message with a severity of
	 * {@link IStatus#INFO INFO}.
	 * @see #buildStatus(int, String)
	 * @see IStatus
	 */
	public IStatus buildInfoStatus(String message) {
		return this.buildStatus(IStatus.INFO, message);
	}

	/**
	 * Build a status with the specified severity and message.
	 * @see IStatus#getSeverity()
	 */
	public IStatus buildStatus(int severity, String message) {
		return this.buildStatus(severity, message, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Build a status with the specified message with a severity of
	 * {@link IStatus#ERROR ERROR}.
	 * @see #buildStatus(int, String, Object[])
	 * @see IStatus
	 */
	public IStatus buildErrorStatus(String message, Object... args) {
		return this.buildStatus(IStatus.ERROR, message, args);
	}

	/**
	 * Build a status with the specified message with a severity of
	 * {@link IStatus#WARNING WARNING}.
	 * @see #buildStatus(int, String, Object[])
	 * @see IStatus
	 */
	public IStatus buildWarningStatus(String message, Object... args) {
		return this.buildStatus(IStatus.WARNING, message, args);
	}

	/**
	 * Build a status with the specified message with a severity of
	 * {@link IStatus#INFO INFO}.
	 * @see #buildStatus(int, String, Object[])
	 * @see IStatus
	 */
	public IStatus buildInfoStatus(String message, Object... args) {
		return this.buildStatus(IStatus.INFO, message, args);
	}

	/**
	 * Build a status with the specified severity and message.
	 * Bind the message to the specified arguments.
	 * @see IStatus#getSeverity()
	 */
	public IStatus buildStatus(int severity, String message, Object... args) {
		return this.buildStatus(severity, (Throwable) null, message, args); // exception can be null
	}

	/**
	 * Build a status with the specified exception with a severity of
	 * {@link IStatus#ERROR ERROR}.
	 * @see #buildStatus(int, Throwable)
	 * @see IStatus
	 */
	public IStatus buildErrorStatus(Throwable throwable) {
		return this.buildStatus(IStatus.ERROR, throwable);
	}

	/**
	 * Build a status with the specified severity and exception.
	 * @see IStatus#getSeverity()
	 */
	public IStatus buildStatus(int severity, Throwable throwable) {
		return this.buildStatus(severity, throwable, throwable.getLocalizedMessage());
	}

	/**
	 * Build a status with the specified exception and message with a
	 * severity of {@link IStatus#ERROR ERROR}.
	 * @see #buildStatus(int, Throwable, String)
	 * @see IStatus
	 */
	public IStatus buildErrorStatus(Throwable throwable, String message) {
		return this.buildStatus(IStatus.ERROR, throwable, message);
	}

	/**
	 * Build a status with the specified severity, exception, and message.
	 * @see IStatus#getSeverity()
	 */
	public IStatus buildStatus(int severity, Throwable throwable, String message) {
		return this.buildStatus(severity, throwable, message, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Build a status with the specified exception and message with a
	 * severity of {@link IStatus#ERROR ERROR}.
	 * Bind the message to the specified arguments.
	 * @see #buildStatus(int, Throwable, String, Object[])
	 * @see IStatus
	 */
	public IStatus buildErrorStatus(Throwable throwable, String message, Object... args) {
		return this.buildStatus(IStatus.ERROR, throwable, message, args);
	}

	/**
	 * Build a status with the specified severity, exception, and message.
	 * Bind the message to the specified arguments.
	 * @see IStatus#getSeverity()
	 */
	public IStatus buildStatus(int severity, Throwable throwable, String message, Object... args) {
		return this.buildStatus(severity, IStatus.OK, throwable, message, args);
	}

	/**
	 * Build a status with the specified severity, code, exception, and message.
	 * @see IStatus#getSeverity()
	 * @see IStatus#getCode()
	 */
	public IStatus buildStatus(int severity, int code, Throwable throwable, String message) {
		return this.buildStatus(severity, code, throwable, message, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Build a status with the specified severity, code, exception, and message.
	 * Bind the message to the specified arguments.
	 * @see IStatus#getSeverity()
	 * @see IStatus#getCode()
	 */
	public IStatus buildStatus(int severity, int code, Throwable throwable, String message, Object... args) {
		if (args.length > 0) {
			message = NLS.bind(message, args);
		}
		String id = this.getPluginID();
		if (id == null) {
			id = this.getClass().getName(); // seems reasonable
		}
		return new Status(severity, id, code, message, throwable);
	}


	// ********** exception handler **********

	/**
	 * Return an exception handler that logs any exceptions with the plug-in
	 * with the appropriate severity; by default, {@link IStatus#ERROR error}.
	 */
	public synchronized ExceptionHandler getExceptionHandler() {
		if (this.exceptionHandler == null) {
			this.exceptionHandler = this.buildExceptionHandler();
		}
		return this.exceptionHandler;
	}

	/**
	 * By default the plug-in's exception handler will log any exceptions
	 * as {@link IStatus#ERROR errors}.
	 */
	public ExceptionHandler buildExceptionHandler() {
		return this.buildExceptionHandler(IStatus.ERROR);
	}

	/**
	 * This public method can be used by code that would like its own exception
	 * handler, as opposed to using the plug-in's exception handler.
	 * @see #getExceptionHandler()
	 * @see PluginExceptionHandler
	 * @see IStatus#getSeverity()
	 */
	public ExceptionHandler buildExceptionHandler(int severity) {
		return new PluginExceptionHandler(severity);
	}

	/**
	 * Handle any exception by logging it with the plug-in with the configured
	 * severity.
	 */
	protected class PluginExceptionHandler
		extends ExceptionHandlerAdapter
	{
		private final int severity;
		protected PluginExceptionHandler(int severity) {
			super();
			this.severity = severity;
		}
		@Override
		public void handleException(Throwable t) {
			JptPlugin.this.log(this.severity, t);
		}
	}


	// ********** debug options **********

	/**
	 * Return whether the plug-in is in debug mode.
	 * <p>
	 * To allow a user to enable a plug-in's <em>debug</em> mode
	 * (or any other debug options)
	 * add the appropriate entry to the plug-in's trace-options file.
	 * <p>
	 * For example, to allow the user to enable the <em>debug</em> mode for
	 * the plug-in <code>org.eclipse.jpt.common.core</code>,
	 * add the following entry to the file
	 * <code>org.eclipse.jpt.common.core/.options</code>:
	 * <pre>
	 * org.eclipse.jpt.common.core/debug=true
	 * </pre>
	 * The flag can be set via either:<ul>
	 * <li>The Host Workspace's Run > Run Configurations > Tracing settings
	 * </ul>
	 * or
	 * <p><ul>
	 * <li>The Target Workspace's Window > Preferences > General > Tracing settings
	 * (This will be present because all Dali debug options have been activated
	 * via the <code>org.eclipse.jpt.common.ui</code> extension
	 * to the <code>org.eclipse.ui.trace.traceComponents</code> extension point.
	 * See <code>org.eclipse.jpt.common.ui/plugin.xml</code>.)
	 * </ul>
	 */
	public boolean isDebugEnabled() {
		DebugOptions debugOptions = this.getDebugOptions();
		if (debugOptions == null) {
			return false;
		}
		StringBuilder option = this.getDebugOptionName();
		return (option != null) && debugOptions.getBooleanOption(option.toString(), false);
	}

	/**
	 * Set whether the plug-in is in debug mode.
	 * 
	 * @see #isDebugEnabled()
	 */
	public void setDebugEnabled(boolean debug) {
		DebugOptions debugOptions = this.getDebugOptions();
		if (debugOptions != null) {
			StringBuilder option = this.getDebugOptionName();
			if (option != null) {
				if ( ! debugOptions.isDebugEnabled()) {
					debugOptions.setDebugEnabled(true);
				}
				debugOptions.setOption(option.toString(), Boolean.toString(debug));
			}
		}
	}

	/**
	 * Return the name of the plug-in's debug option.
	 * By default, this is in the form
	 * <em>&lt;plug-in ID&gt;/debug</em>
	 * (e.g. <code>org.eclipse.jpt.common.core/debug</code>).
	 * Return <code>null</code> if the plug-in has no bundle.
	 */
	protected StringBuilder getDebugOptionName() {
		String id = this.getPluginID();
		if (id == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder(id);
		sb.append('/');
		sb.append(this.getRelativeDebugOptionName());
		return sb;
	}

	/**
	 * @see #RELATIVE_DEBUG_OPTION_NAME
	 */
	protected String getRelativeDebugOptionName() {
		return RELATIVE_DEBUG_OPTION_NAME;
	}
	/**
	 * Value: <code>{@value}</code>
	 */
	protected static final String RELATIVE_DEBUG_OPTION_NAME = "debug"; //$NON-NLS-1$

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
	 * Set the specified debug option to the specified <code>boolean</code>
	 * value.
	 * <p>
	 * The debug option is within the scope of the plug-in's debug options
	 * (e.g. for the plug-in <code>"org.eclipse.jpt.common.core"</code>,
	 * the specified option <code>"foo"</code> will be mapped to
	 * the {@link Platform} option
	 * <code>"org.eclipse.jpt.common.core/debug/foo"</code>).
	 */
	public void setDebugOption(String option, boolean value) {
		this.setDebugOption(option, Boolean.toString(value));
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
	 * Set the specified debug option to the specified <code>int</code>
	 * value.
	 * <p>
	 * The debug option is within the scope of the plug-in's debug options
	 * (e.g. for the plug-in <code>"org.eclipse.jpt.common.core"</code>,
	 * the specified option <code>"foo"</code> will be mapped to
	 * the {@link Platform} option
	 * <code>"org.eclipse.jpt.common.core/debug/foo"</code>).
	 */
	public void setDebugOption(String option, int value) {
		this.setDebugOption(option, Integer.toString(value));
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
		this.checkDebugOption(option);
		return this.isDebugEnabled() ? this.getDebugOption_(option, defaultValue) : defaultValue;
	}

	/**
	 * Pre-condition: the specified option is not blank.
	 */
	protected String getDebugOption_(String option, String defaultValue) {
		String value = this.getDebugOption_(option);
		return (value != null) ? value : defaultValue;
	}

	protected String getDebugOption_(String option) {
		DebugOptions debugOptions = this.getDebugOptions();
		if (debugOptions == null) {
			return null;
		}
		StringBuilder scope = this.getDebugOptionScope();
		return (scope == null) ? null : debugOptions.getOption(scope.append(option).toString());
	}

	/**
	 * Set the specified debug option to the specified value.
	 * <p>
	 * The debug option is within the scope of the plug-in's debug options
	 * (e.g. for the plug-in <code>"org.eclipse.jpt.common.core"</code>,
	 * the specified option <code>"foo"</code> will be mapped to
	 * the {@link Platform} option
	 * <code>"org.eclipse.jpt.common.core/debug/foo"</code>).
	 * If the plug-in is not in {@link #isDebugEnabled() debug mode},
	 * the specified option is not changed.
	 */
	public void setDebugOption(String option, String value) {
		this.checkDebugOption(option);
		if (this.isDebugEnabled()) {
			this.setDebugOption_(option, value);
		}
	}

	/**
	 * Pre-condition: the specified option is not blank.
	 */
	protected void setDebugOption_(String option, String value) {
		DebugOptions debugOptions = this.getDebugOptions();
		if (debugOptions != null) {
			StringBuilder scope = this.getDebugOptionScope();
			if (scope != null) {
				debugOptions.setOption(scope.append(option).toString(), value);
			}
		}
	}

	/**
	 * Remove the specified debug option.
	 * <p>
	 * The debug option is within the scope of the plug-in's debug options
	 * (e.g. for the plug-in <code>"org.eclipse.jpt.common.core"</code>,
	 * the specified option <code>"foo"</code> will be mapped to
	 * the {@link Platform} option
	 * <code>"org.eclipse.jpt.common.core/debug/foo"</code>).
	 * If the plug-in is not in {@link #isDebugEnabled() debug mode},
	 * the specified option is not changed.
	 */
	public void removeDebugOption(String option) {
		this.checkDebugOption(option);
		if (this.isDebugEnabled()) {
			this.removeDebugOption_(option);
		}
	}

	/**
	 * Pre-condition: the specified option is not blank.
	 */
	protected void removeDebugOption_(String option) {
		DebugOptions debugOptions = this.getDebugOptions();
		if (debugOptions != null) {
			StringBuilder scope = this.getDebugOptionScope();
			if (scope != null) {
				debugOptions.removeOption(scope.append(option).toString());
			}
		}
	}

	protected DebugOptions getDebugOptions() {
		ServiceTracker<DebugOptions, DebugOptions> tracker = this.getDebugOptionsTracker();
		return (tracker == null) ? null : tracker.getService();
	}

	private synchronized ServiceTracker<DebugOptions, DebugOptions> getDebugOptionsTracker() {
		if (this.debugOptionsTracker == null) {
			this.debugOptionsTracker = this.buildDebugOptionsTracker();
		}
		return this.debugOptionsTracker;
	}

	private ServiceTracker<DebugOptions, DebugOptions> buildDebugOptionsTracker() {
		try {
			return this.buildDebugOptionsTracker_();
		} catch (RuntimeException ex) {
			this.logError(ex);
			return null;
		}
	}

	/**
	 * @exception RuntimeException if the plug-in's
	 * bundle context is missing or invalid
	 */
	private ServiceTracker<DebugOptions, DebugOptions> buildDebugOptionsTracker_() {
		Bundle b = this.getBundle();
		if (b == null) {
			return null;
		}
		ServiceTracker<DebugOptions, DebugOptions> tracker = new ServiceTracker<DebugOptions, DebugOptions>(b.getBundleContext(), DebugOptions.class, null);
		tracker.open();
		return tracker;
	}

	protected synchronized void closeDebugOptionsTracker() {
		if (this.debugOptionsTracker != null) {
			this.debugOptionsTracker.close();
			this.debugOptionsTracker = null;
			this.debugTrace = null; // the debug trace is associated with the tracker's service(?)
		}
	}

	/**
	 * Return the name of the plug-in's debug option scope.
	 * Include an appended <code>'/'</code>.
	 * By default, this is in the form
	 * <em>&lt;plug-in ID&gt;/debug/</em>
	 * (e.g. <code>org.eclipse.jpt.common.core/debug/</code>).
	 * Return <code>null</code> if the plug-in has no bundle.
	 */
	protected StringBuilder getDebugOptionScope() {
		StringBuilder sb = this.getDebugOptionName();
		return (sb == null) ? null : sb.append('/');
	}

	protected void checkDebugOption(String option) {
		if (StringTools.isBlank(option)) {
			throw new IllegalArgumentException("debug option cannot be blank"); //$NON-NLS-1$
		}
	}


	// ********** tracing **********

	/**
	 * Trace the calling method for the specified debug option (with no message).
	 * This method should be called directly from the method to be traced,
	 * as only the name of that single method will be recorded.
	 * <p>
	 * The debug option is within the scope of the plug-in's debug options
	 * (e.g. for the plug-in <code>"org.eclipse.jpt.common.core"</code>,
	 * the specified option <code>"foo"</code> will be mapped to
	 * the {@link Platform} option
	 * <code>"org.eclipse.jpt.common.core/debug/foo"</code>).
	 * If the plug-in is not in {@link #isDebugEnabled() debug mode},
	 * the specified message is not traced.
	 * 
	 * @see #dumpStackTrace(String)
	 */
	public void trace(String option) {
		this.trace(option, (String) null);  // message can be null
	}

	/**
	 * Trace the specified message for the specified debug option.
	 * This method should be called directly from the method to be traced,
	 * as only the name of that single method will be recorded.
	 * <p>
	 * The debug option is within the scope of the plug-in's debug options
	 * (e.g. for the plug-in <code>"org.eclipse.jpt.common.core"</code>,
	 * the specified option <code>"foo"</code> will be mapped to
	 * the {@link Platform} option
	 * <code>"org.eclipse.jpt.common.core/debug/foo"</code>).
	 * If the plug-in is not in {@link #isDebugEnabled() debug mode},
	 * the specified message is not traced.
	 * 
	 * @see #dumpStackTrace(String, String)
	 */
	public void trace(String option, String message) {
		this.trace(option, message, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Trace the specified message for the specified debug option.
	 * This method should be called directly from the method to be traced,
	 * as only the name of that single method will be recorded.
	 * <p>
	 * The debug option is within the scope of the plug-in's debug options
	 * (e.g. for the plug-in <code>"org.eclipse.jpt.common.core"</code>,
	 * the specified option <code>"foo"</code> will be mapped to
	 * the {@link Platform} option
	 * <code>"org.eclipse.jpt.common.core/debug/foo"</code>).
	 * Bind the message to the specified arguments.
	 * If the plug-in is not in {@link #isDebugEnabled() debug mode},
	 * the specified message is not traced.
	 * 
	 * @see #dumpStackTrace(String, String, Object...)
	 */
	public void trace(String option, String message, Object... args) {
		this.trace(option, null, message, args);
	}

	/**
	 * Trace the specified exception for the specified debug option.
	 * This method should be called directly from the method to be traced,
	 * as only the name of that single method will be recorded.
	 * Only the specified exception's class name and
	 * {@link Throwable#getLocalizedMessage() message} will be recorded in the
	 * trace log (as opposed to the exception's stack trace).
	 * <p>
	 * The debug option is within the scope of the plug-in's debug options
	 * (e.g. for the plug-in <code>"org.eclipse.jpt.common.core"</code>,
	 * the specified option <code>"foo"</code> will be mapped to
	 * the {@link Platform} option
	 * <code>"org.eclipse.jpt.common.core/debug/foo"</code>).
	 * If the plug-in is not in {@link #isDebugEnabled() debug mode},
	 * the specified message is not traced.
	 */
	public void trace(String option, Throwable throwable) {
		this.trace(option, throwable, throwable.getLocalizedMessage());
	}

	/**
	 * Trace the specified exception and message for the specified debug option.
	 * This method should be called directly from the method to be traced,
	 * as only the name of that single method will be recorded.
	 * Only the specified exception's class name and
	 * {@link Throwable#getLocalizedMessage() message} will be recorded in the
	 * trace log (as opposed to the exception's stack trace).
	 * <p>
	 * The debug option is within the scope of the plug-in's debug options
	 * (e.g. for the plug-in <code>"org.eclipse.jpt.common.core"</code>,
	 * the specified option <code>"foo"</code> will be mapped to
	 * the {@link Platform} option
	 * <code>"org.eclipse.jpt.common.core/debug/foo"</code>).
	 * If the plug-in is not in {@link #isDebugEnabled() debug mode},
	 * the specified message is not traced.
	 */
	public void trace(String option, Throwable throwable, String message) {
		this.trace(option, throwable, message, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Trace the specified message and exception for the specified debug option.
	 * This method should be called directly from the method to be traced,
	 * as only the name of that single method will be recorded.
	 * Only the specified exception's class name and
	 * {@link Throwable#getLocalizedMessage() message} will be recorded in the
	 * trace log (as opposed to the exception's stack trace).
	 * <p>
	 * The debug option is within the scope of the plug-in's debug options
	 * (e.g. for the plug-in <code>"org.eclipse.jpt.common.core"</code>,
	 * the specified option <code>"foo"</code> will be mapped to
	 * the {@link Platform} option
	 * <code>"org.eclipse.jpt.common.core/debug/foo"</code>).
	 * Bind the message to the specified arguments.
	 * If the plug-in is not in {@link #isDebugEnabled() debug mode},
	 * the specified message is not traced.
	 */
	public void trace(String option, Throwable throwable, String message, Object... args) {
		this.checkDebugOption(option);
		if (this.isDebugEnabled()) {
			this.trace_(option, throwable, message, args);
		}
	}

	/**
	 * Pre-condition: the specified option is not blank.
	 */
	protected void trace_(String option, Throwable throwable, String message, Object... args) {
		if (args.length > 0) {
			message = NLS.bind(message, args);
		}
		DebugTrace trace = this.getDebugTrace();
		if (trace != null) {
			trace.trace(this.getTraceOptionScope().append(option).toString(), message, throwable);
		}
	}

	/**
	 * Dump a stack trace for the specified debug option.
	 * <p>
	 * The debug option is within the scope of the plug-in's debug options
	 * (e.g. for the plug-in <code>"org.eclipse.jpt.common.core"</code>,
	 * the specified option <code>"foo"</code> will be mapped to
	 * the {@link Platform} option
	 * <code>"org.eclipse.jpt.common.core/debug/foo"</code>).
	 * If the plug-in is not in {@link #isDebugEnabled() debug mode},
	 * the stack trace is not dumped.
	 */
	public void dumpStackTrace(String option) {
		this.dumpStackTrace(option, null);
	}

	/**
	 * Dump a stack trace for the specified debug option.
	 * Precede it with a trace for the specified message.
	 * This method should be called directly from the method to be traced,
	 * as only that single method will be recorded with the message.
	 * <p>
	 * The debug option is within the scope of the plug-in's debug options
	 * (e.g. for the plug-in <code>"org.eclipse.jpt.common.core"</code>,
	 * the specified option <code>"foo"</code> will be mapped to
	 * the {@link Platform} option
	 * <code>"org.eclipse.jpt.common.core/debug/foo"</code>).
	 * If the plug-in is not in {@link #isDebugEnabled() debug mode},
	 * the stack trace is not dumped.
	 */
	public void dumpStackTrace(String option, String message) {
		this.dumpStackTrace(option, message, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Dump a stack trace for the specified debug option.
	 * Precede it with a trace for the specified message.
	 * This method should be called directly from the method to be traced,
	 * as only that single method will be recorded with the message.
	 * <p>
	 * The debug option is within the scope of the plug-in's debug options
	 * (e.g. for the plug-in <code>"org.eclipse.jpt.common.core"</code>,
	 * the specified option <code>"foo"</code> will be mapped to
	 * the {@link Platform} option
	 * <code>"org.eclipse.jpt.common.core/debug/foo"</code>).
	 * Bind the message to the specified arguments.
	 * If the plug-in is not in {@link #isDebugEnabled() debug mode},
	 * the stack trace is not dumped.
	 */
	public void dumpStackTrace(String option, String message, Object... args) {
		this.checkDebugOption(option);
		if (this.isDebugEnabled()) {
			if (message != null) {
				this.trace_(option, null, message, args);
			}
			this.dumpStackTrace_(option);
		}
	}

	/**
	 * Pre-condition: the specified option is not blank.
	 */
	// TODO file bug with Platform:
	// EclipseDebugTrace.traceDumpStack(...) should filter out trace class
	// entries like FrameworkDebugTraceEntry constructor
	protected void dumpStackTrace_(String option) {
		DebugTrace trace = this.getDebugTrace();
		if (trace != null) {
			trace.traceDumpStack(this.getTraceOptionScope().append(option).toString());
		}
	}

	/**
	 * Default value: <code>"/debug/"</code>
	 */
	protected StringBuilder getTraceOptionScope() {
		StringBuilder sb = new StringBuilder();
		sb.append('/');
		sb.append(this.getRelativeTraceOptionName());
		sb.append('/');
		return sb;
	}

	/**
	 * @see #getRelativeDebugOptionName()
	 */
	protected String getRelativeTraceOptionName() {
		return this.getRelativeDebugOptionName();
	}

	protected synchronized DebugTrace getDebugTrace() {
		if (this.debugTrace == null) {
			this.debugTrace = this.buildDebugTrace();
		}
		return this.debugTrace;
	}

	protected DebugTrace buildDebugTrace() {
		return this.buildDebugTrace(this.getDebugTraceEntryClass());
	}

	/**
	 * @see #DEBUG_TRACE_ENTRY_CLASS
	 */
	protected Class<?> getDebugTraceEntryClass() {
		return DEBUG_TRACE_ENTRY_CLASS;
	}
	/**
	 * Value: <code>{@value}</code>
	 */
	protected static final Class<?> DEBUG_TRACE_ENTRY_CLASS = JptPlugin.class;

	/**
	 * The specified trace entry class must be the class where the trace methods
	 * are <em>defined</em> (<em>not</em> the class of the object that receives
	 * the message at runtime - i.e. do not use the result of
	 * {@link #getClass()}), as that is the class captured in a stacktrace.
	 */
	protected DebugTrace buildDebugTrace(Class<?> traceEntryClass) {
		DebugOptions debugOptions = this.getDebugOptions();
		if (debugOptions == null) {
			return null;
		}
		String id = this.getPluginID();
		return (id == null) ? null : debugOptions.newDebugTrace(id, traceEntryClass);
	}


	// ********** misc **********

	/**
	 * Return the plug-in's bundle.
	 */
	public Bundle getBundle() {
		return (this.bundle  != null) ? this.bundle : this.getBundle_();
	}

	/**
	 * If the plug-in has not yet been {@link #start(BundleContext) started}, we
	 * can still get the bundle from the classloader....
	 */
	private Bundle getBundle_() {
		return FrameworkUtil.getBundle(this.getClass());
//		ClassLoader cl = this.getClass().getClassLoader();
//		return (cl instanceof BundleReference) ? ((BundleReference) cl).getBundle() : null;
	}

	/**
	 * Return the plug-in's ID (i.e. the symbolic name of the plug-in's bundle).
	 * Return <code>null</code> if the plug-in has no bundle.
	 * @see Bundle#getSymbolicName()
	 */
	public String getPluginID() {
		Bundle b = this.getBundle();
		return (b == null) ? null : b.getSymbolicName();
	}

	/**
	 * Return the plug-in's "original" ID. This is useful for backward and
	 * forward compatibility of resource persistent properties and preferences.
	 * By default return the {@link #getPluginID() current plug-in ID}.
	 * Return <code>null</code> if the plug-in has no bundle.
	 */
	public final String getOriginalPluginID() {
		Bundle b = this.getBundle();
		return (b == null) ? null : this.getOriginalPluginID_();
	}

	/**
	 * Override this method if the {@link Bundle#getSymbolicName() plug-in's ID}
	 * changes but there are previously-saved resource persistent properties
	 * and/or preferences that use the original plug-in ID and backward and/or
	 * forward compatibility is desirable.
	 */
	protected String getOriginalPluginID_() {
		return this.getPluginID();
	}

	/**
	 * Return whether the plug-in has a bundle and is active; i.e. it has been
	 * {@link #start(BundleContext) started}.
	 * <p>
	 * <strong>NB:</strong> The plug-in is <em>not</em> active during the
	 * execution of the methods {@link #start(BundleContext)} or
	 * {@link #stop(BundleContext)}.
	 * 
	 * @see Bundle#getState()
	 * @see Bundle#ACTIVE
	 */
	public boolean isActive() {
		Bundle b = this.getBundle();
		return (b != null) && (b.getState() == Bundle.ACTIVE);
	}

	/**
	 * Return whether the plug-in has no bundle or is inactive; i.e. it has been
	 * {@link #stop(BundleContext) stopped} or not yet
	 * {@link #start(BundleContext) started}.
	 * <p>
	 * <strong>NB:</strong> The plug-in is <em>not</em> active during the
	 * execution of the methods {@link #start(BundleContext)} or
	 * {@link #stop(BundleContext)}.
	 * 
	 * @see Bundle#getState()
	 * @see Bundle#ACTIVE
	 */
	public boolean isInactive() {
		return ! this.isActive();
	}

	/**
	 * Qualify the specified relative name with the plug-in's ID.
	 * Return <code>null</code> if the plug-in has no bundle.
	 */
	public QualifiedName buildQualifiedName(String relativeName) {
		String id = this.getPluginID();
		return (id == null) ? null : new QualifiedName(id, relativeName);
	}

	/**
	 * Return <code>null</code> if the plug-in has no bundle
	 * or if the platform is running with no data area (<code>-data @none</code>).
	 * @see org.eclipse.core.runtime.Plugin#getStateLocation()
	 * @see Platform#getStateLocation(Bundle)
	 */
	public IPath getStateLocation() {
		try {
			return this.getStateLocation_();
		} catch (IllegalStateException ex) {
			return null; // -data @none
		}
	}

	protected IPath getStateLocation_() {
		Bundle b = this.getBundle();
		return (b == null) ? null : Platform.getStateLocation(b);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.getBundle());
	}
}
