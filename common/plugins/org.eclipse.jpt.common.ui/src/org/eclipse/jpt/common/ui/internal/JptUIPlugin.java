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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.draw2d.ImageUtilities;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jpt.common.core.internal.utility.JptPlugin;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.Bundle;

/**
 * Common Dali UI plug-in behavior:<ul>
 * <li>dialog settings
 * <li>image registry
 * <li>UI preference store
 * </ul>
 */
public abstract class JptUIPlugin
	extends JptPlugin
{
	// NB: the plug-in must be synchronized whenever accessing any of this state
	private IDialogSettings dialogSettings;
	private ImageRegistry imageRegistry;
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
	protected void start_() throws Exception {
		super.start_();
	}

	@Override
	protected void stop_() throws Exception {
		try {
			if (this.imageRegistry != null) {
				this.imageRegistry.dispose();
			}
			if (this.dialogSettings != null) {
				this.saveDialogSettings();
			}
		} finally {
			this.imageRegistry = null;
			this.dialogSettings = null;
			super.stop_();
		}
	}


	// ********** dialog settings **********

	/**
	 * Return the dialog settings for the UI plug-in.
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#getDialogSettings()
	 */
	public synchronized IDialogSettings getDialogSettings() {
		if ((this.dialogSettings == null) && this.isActive()) {
			this.dialogSettings = this.buildDialogSettings();
		}
		return this.dialogSettings;
	}

	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#loadDialogSettings()
	 */
	protected IDialogSettings buildDialogSettings() {
		IDialogSettings result = this.buildDialogSettings_();
		String settingsFileName = this.getDialogSettingsFileName();
		if (settingsFileName == null) {
			return result;
		}
		File settingsFile = new File(settingsFileName);
		if (settingsFile.exists()) {
			try {
				result.load(settingsFileName);
			} catch (IOException ex) {
				// if there are problems, return an empty settings container
				return this.buildDialogSettings_();
			}
		}
		return result;
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
	protected void saveDialogSettings() {
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
		return stateLocation.append(this.getRelativeDialogSettingsFileName()).toOSString();
	}

	protected String getRelativeDialogSettingsFileName() {
		return RELATIVE_DIALOG_SETTINGS_FILE_NAME;
	}

	/**
	 * Value: <code>{@value}</code>
	 */
	protected static final String RELATIVE_DIALOG_SETTINGS_FILE_NAME = "dialog_settings.xml"; //$NON-NLS-1$


	// ********** images **********

	/**
	 * Return a "normal" (as opposed to "ghost") image for the specified key.
	 * The key will be transformed into the name of a
	 * <code>.gif</code> file in the plug-in's <code>icons</code> folder.
	 * This image is built, held, and disposed by the plug-in.
	 * 
	 * @see #getGhostImage(String)
	 * @see #getImage(String, boolean)
	 * @see org.eclipse.ui.IWorkbench#getSharedImages()
	 */
	public Image getImage(String key) {
		return this.getImage(key, false); // false = normal (non-ghost)
	}

	/**
	 * Return a "ghost" image for the specified key.
	 * The key will be transformed into the name of a
	 * <code>.gif</code> file in the plug-in's <code>icons</code> folder.
	 * This image is built, held, and disposed by the plug-in.
	 * 
	 * @see #getImage(String)
	 * @see #getImage(String, boolean)
	 * @see org.eclipse.ui.IWorkbench#getSharedImages()
	 */
	public Image getGhostImage(String key) {
		return this.getImage(key, true); // true = ghost
	}

	/**
	 * Return an image for the specified key. "Ghost" the image if the specified
	 * flag is <code>true</code>.
	 * The key will be transformed into the name of a
	 * <code>.gif</code> file in the plug-in's <code>icons</code> folder.
	 * This image is built, held, and disposed by the plug-in.
	 * 
	 * @see #getGhostImage(String)
	 * @see #getImage(String)
	 * @see org.eclipse.ui.IWorkbench#getSharedImages()
	 */
	public Image getImage(String key, boolean ghost) {
		this.checkImageKey(key);
		ImageRegistry registry = this.getImageRegistry();
		if (registry == null) {
			return null;
		}
		// lock the registry while retrieving (and possibly building) the image
		synchronized (registry) {
			return ghost ? this.getGhostImage(registry, key) : this.getImage(registry, key);
		}
	}

	/**
	 * Pre-condition: The specified registry is <code>synchronized</code>.
	 */
	protected Image getGhostImage(ImageRegistry registry, String key) {
		String ghostKey = this.buildGhostImageKey(key);
		Image ghostImage = registry.get(ghostKey);
		if (ghostImage == null) {
			ghostImage = this.buildGhostImage(registry, key);
			registry.put(ghostKey, ghostImage);
		}
		return ghostImage;
	}

	protected String buildGhostImageKey(String key) {
		return key + '-' + this.getGhostImageKeySuffix();
	}

	protected String getGhostImageKeySuffix() {
		return GHOST_IMAGE_KEY_SUFFIX;
	}

	/**
	 * Value: <code>{@value}</code>
	 */
	protected static final String GHOST_IMAGE_KEY_SUFFIX = "gray"; //$NON-NLS-1$

	protected Image buildGhostImage(ImageRegistry registry, String key) {
		Image image = this.getImage(registry, key);
		Color lightGray = new Color(image.getDevice(), 223, 223, 223);
		Image shadedImage = new Image(image.getDevice(), ImageUtilities.createShadedImage(image, lightGray));
		Image ghostImage = new Image(image.getDevice(), shadedImage, SWT.IMAGE_GRAY);
		shadedImage.dispose();
		lightGray.dispose();
		return ghostImage;
	}

	/**
	 * Pre-condition: The specified registry is <code>synchronized</code>.
	 */
	protected Image getImage(ImageRegistry registry, String key) {
		Image image = registry.get(key);
		if (image == null) {
			// a bad image descriptor will result in a "default" image
			registry.put(key, this.buildImageDescriptor_(key));
			image = registry.get(key);
		}
		return image;
	}

	/**
	 * Return an image descriptor for the specified key.
	 * The key will be transformed into the name of a
	 * <code>.gif</code> file in the plug-in's <code>icons</code> folder.
	 */
	public ImageDescriptor buildImageDescriptor(String key) {
		this.checkImageKey(key);
		return this.buildImageDescriptor_(key);
	}

	/**
	 * Pre-condition: the specified key is not blank.
	 */
	protected ImageDescriptor buildImageDescriptor_(String key) {
		Bundle bundle = this.getBundle();
		if (bundle == null) {
			return null;
		}

		IPath path = this.buildImageFilePath(key);
		URL url = FileLocator.find(bundle, path, null);
		return (url == null) ? null : ImageDescriptor.createFromURL(url);
	}

	protected IPath buildImageFilePath(String key) {
		return new Path(this.buildRelativeImageFileName(key));
	}

	protected String buildRelativeImageFileName(String key) {
		return this.getRelativeImageDirectoryName() + '/' + key + '.' + this.getImageFileExt();
	}

	protected String getRelativeImageDirectoryName() {
		return RELATIVE_IMAGE_DIRECTORY_NAME;
	}
	/**
	 * Value: <code>{@value}</code>
	 */
	protected static final String RELATIVE_IMAGE_DIRECTORY_NAME = "icons"; //$NON-NLS-1$

	protected String getImageFileExt() {
		return IMAGE_FILE_EXT;
	}
	/**
	 * Value: <code>{@value}</code>
	 */
	protected static final String IMAGE_FILE_EXT = "gif"; //$NON-NLS-1$

	protected void checkImageKey(String key) {
		if (StringTools.isBlank(key)) {
			throw new IllegalArgumentException("image key cannot be blank"); //$NON-NLS-1$
		}
	}

	/**
	 * Return the image registry for the UI plug-in.
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#getImageRegistry()
	 */
	protected synchronized ImageRegistry getImageRegistry() {
		if ((this.imageRegistry == null) && this.isActive()) {
			this.imageRegistry = this.buildImageRegistry();
		}
		return this.imageRegistry;
	}

	// TODO the image registry holds icons for the life of the plug-in
	// (i.e. until the workspace is closed). This is better than before when
	// we constantly created new images(!), but:
	// Bug 306437 is about cleaning this up and using Local Resource Managers
	// on our views so that closing the JPA perspective would mean the icons are disposed.
	// But then do we have multiple versions of the same icon?
	protected ImageRegistry buildImageRegistry() {
		Display display = SWTUtil.getDisplay();
		return (display == null) ? null : new ImageRegistry(display);
	}


	// ********** preference store **********

	/**
	 * Return the preference store for the UI plug-in.
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#getPreferenceStore()
	 */
	public synchronized IPreferenceStore getPreferenceStore() {
		if ((this.preferenceStore == null) && this.isActive()) {
			this.preferenceStore = this.buildPreferenceStore();
		}
		return this.preferenceStore;
	}

	protected IPreferenceStore buildPreferenceStore() {
		String id = this.getPluginID();
		return (id == null) ? null : new ScopedPreferenceStore(InstanceScope.INSTANCE, id);
	}
}
