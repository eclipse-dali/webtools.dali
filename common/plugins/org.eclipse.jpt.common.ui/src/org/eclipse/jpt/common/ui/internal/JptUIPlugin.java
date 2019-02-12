/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.core.internal.utility.JptPlugin;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * Common Dali UI plug-in behavior:<ul>
 * <li>dialog settings
 * <li>image descriptor
 * <li>UI preference store
 * </ul>
 */
public abstract class JptUIPlugin
	extends JptPlugin
{
	// NB: the plug-in must be synchronized whenever accessing any of this state
	private IDialogSettings dialogSettings;
	private IPreferenceStore preferenceStore;


	/**
	 * Default constructor is required. Of course, subclass constructors must
	 * be <code>public</code>.
	 */
	protected JptUIPlugin() {
		super();
	}


	// ********** plug-in lifecycle **********

	@Override
	public void stop(BundleContext context) throws Exception {
		try {
			this.saveDialogSettings();
			// it seems like we can leave 'dialogSettings' and 'preferenceStore' in place...
		} finally {
			super.stop(context);
		}
	}


	// ********** dialog settings **********

	/**
	 * Return the UI plug-in dialog settings for the specified section.
	 * Create the section if it does not exist.
	 */
	public synchronized IDialogSettings getDialogSettings(String sectionName) {
		IDialogSettings pluginSettings = this.getDialogSettings();
		if (pluginSettings == null) {
			return null;
		}
		IDialogSettings sectionSettings = pluginSettings.getSection(sectionName);
		if (sectionSettings == null) {
			sectionSettings = pluginSettings.addNewSection(sectionName);
		}
		return sectionSettings;
	}

	/**
	 * Return the dialog settings for the UI plug-in.
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#getDialogSettings()
	 */
	public synchronized IDialogSettings getDialogSettings() {
		if (this.dialogSettings == null) {
			this.dialogSettings = this.buildDialogSettings();
		}
		return this.dialogSettings;
	}

	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#loadDialogSettings()
	 */
	protected IDialogSettings buildDialogSettings() {
		IDialogSettings settings = this.buildDialogSettings_();
		String settingsFileName = this.getDialogSettingsFileName();
		if (settingsFileName == null) {
			return settings;
		}
		File settingsFile = new File(settingsFileName);
		if (settingsFile.exists()) {
			try {
				settings.load(settingsFileName);
			} catch (IOException ex) {
				// if there are problems, return an empty settings container
				return this.buildDialogSettings_();
			}
		}
		return settings;
	}

	protected IDialogSettings buildDialogSettings_() {
		return new DialogSettings(this.getDialogSettingsSectionName());
	}

	protected String getDialogSettingsSectionName() {
		return DIALOG_SETTINGS_SECTION_NAME;
	}

	/**
	 * Value: <code>{@value}</code>
	 */
	protected static final String DIALOG_SETTINGS_SECTION_NAME = "Workbench"; //$NON-NLS-1$

	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#saveDialogSettings()
	 */
	protected synchronized void saveDialogSettings() {
		if (this.dialogSettings != null) {
			this.saveDialogSettings_();
		}
	}

	/**
	 * Pre-condition: the dialog settings are not <code>null</code>
	 */
	protected void saveDialogSettings_() {
		String settingsFileName = this.getDialogSettingsFileName();
		if (settingsFileName != null) {
			try {
				this.dialogSettings.save(settingsFileName);
			} catch (IOException ex) {
				// ignore silently
			}
		}
	}

	protected String getDialogSettingsFileName() {
		IPath stateLocation = this.getStateLocation();
		if (stateLocation == null) {
			return null;
		}
		IPath path = stateLocation.append(this.getSimpleDialogSettingsFileName());
		return path.toOSString();
	}

	protected String getSimpleDialogSettingsFileName() {
		return SIMPLE_DIALOG_SETTINGS_FILE_NAME;
	}

	/**
	 * Value: <code>{@value}</code>
	 */
	protected static final String SIMPLE_DIALOG_SETTINGS_FILE_NAME = "dialog_settings.xml"; //$NON-NLS-1$


	// ********** image descriptor **********

	/**
	 * Build an image descriptor for the image file with the specified path
	 * in the plug-in's bundle.
	 * 
	 * @see org.eclipse.ui.IWorkbench#getSharedImages()
	 * @see org.eclipse.ui.ISharedImages#getImageDescriptor(String)
	 * @see FileLocator#find(Bundle, IPath, java.util.Map)
	 */
	public ImageDescriptor buildImageDescriptor(String path) {
		if (StringTools.isBlank(path)) {
			throw new IllegalArgumentException("image path cannot be blank"); //$NON-NLS-1$
		}

		Bundle bundle = this.getBundle();
		if (bundle == null) {
			return null;
		}

		URL url = FileLocator.find(bundle, new Path(path), null);
		return (url == null) ? null : ImageDescriptor.createFromURL(url);
	}


	// ********** preference store **********

	/**
	 * Return the UI plug-in's preference store.
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#getPreferenceStore()
	 */
	public synchronized IPreferenceStore getPreferenceStore() {
		if (this.preferenceStore == null) {
			this.preferenceStore = this.buildPreferenceStore();
		}
		return this.preferenceStore;
	}

	protected IPreferenceStore buildPreferenceStore() {
		String id = this.getPluginID();
		return (id == null) ? null : new ScopedPreferenceStore(InstanceScope.INSTANCE, id);
	}
}
