/*******************************************************************************
 *  Copyright (c) 2006 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.core.internal.content.orm.XmlPersistentAttribute;
import org.eclipse.ui.IActionFilter;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class JptUiPlugin extends AbstractUIPlugin
{
	private static JptUiPlugin INSTANCE;
	
	/**
	 * The plug-in identifier of JPA UI support
	 * (value <code>"org.eclipse.jpt.ui"</code>).
	 */
	public final static String PLUGIN_ID = "org.eclipse.jpt.ui";  //$NON-NLS-1$
	
	/**
	 * Returns the singleton Plugin
	 */
	public static JptUiPlugin getPlugin() {
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
	
	
	public JptUiPlugin() {
		super();
		INSTANCE = this;

		Platform.getAdapterManager().registerAdapters(buildPersistentAttributeAdapterFactory(), XmlPersistentAttribute.class);
	}
	
	private IAdapterFactory buildPersistentAttributeAdapterFactory() {
		return new IAdapterFactory() {
			public Class[] getAdapterList() {
				return new Class[] {IActionFilter.class};
			}
		
			public Object getAdapter(Object adaptableObject, Class adapterType) {
				return new IActionFilter() {
					public boolean testAttribute(Object target, String name, String value) {
						if (name.equals("specifiedInXml")) {
							return !((XmlPersistentAttribute) target).isVirtual();
						}
						if (name.equals("defaultedFromJava")) {
							return ((XmlPersistentAttribute) target).isVirtual();
						}
						throw new IllegalArgumentException();
					}
				};
			}
		};
	}
	
	/**
	 * This gets a .gif from the icons folder.
	 */
	public ImageDescriptor getImageDescriptor(String key) {
		if (! key.startsWith("icons/")) {
			key = "icons/" + key;
		}
		if (! key.endsWith(".gif")) {
			key = key + ".gif";
		}
		return imageDescriptorFromPlugin(PLUGIN_ID, key);
	}
}
