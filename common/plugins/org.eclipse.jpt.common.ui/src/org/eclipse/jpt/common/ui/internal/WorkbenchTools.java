/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.help.IContext;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

/**
 * {@link IWorkbench} utility methods.
 */
public final class WorkbenchTools {

	// ********** workbench UI **********

	/**
	 * Return the Eclipse UI {@link IWorkbench workbench}
	 * {@link Display display}.
	 * Return <code>null</code> if the workbench is not running.
	 * @see #getWorkbench()
	 * @see IWorkbench#getDisplay()
	 */
	public static Display getDisplay() {
		IWorkbench wb = getWorkbench();
		return (wb == null) ? null : wb.getDisplay();
	}

	/**
	 * Return the currently active workbench window's shell.
	 * Return <code>null</code> if called from a non-UI thread.
	 * @see #getActiveWindow()
	 * @see #getWorkbench()
	 * @see IWorkbench#getActiveWorkbenchWindow()
	 */
	public static Shell getActiveShell() {
		IWorkbenchWindow ww = getActiveWindow();
		return (ww == null) ? null : ww.getShell();
	}

	/**
	 * Return the currently active workbench window's shell.
	 * Return <code>null</code> if called from a non-UI thread.
	 * @see #getActiveWindow()
	 * @see #getWorkbench()
	 * @see IWorkbenchWindow#getActivePage()
	 * @see IWorkbench#getActiveWorkbenchWindow()
	 */
	public static IWorkbenchPage getActivePage() {
		IWorkbenchWindow ww = getActiveWindow();
		return (ww == null) ? null : ww.getActivePage();
	}

	/**
	 * Return the currently active workbench window's shell.
	 * Return <code>null</code> if called from a non-UI thread.
	 * @see #getWorkbench()
	 * @see IWorkbench#getActiveWorkbenchWindow()
	 */
	public static IWorkbenchWindow getActiveWindow() {
		IWorkbench wb = getWorkbench();
		return (wb == null) ? null : wb.getActiveWorkbenchWindow();
	}

	/**
	 * Return the Eclipse UI {@link IWorkbench workbench}.
	 * Return <code>null</code> if the workbench is not running
	 * (i.e. either it has not been started or it has been terminated).
	 * <p>
	 * <strong>NB:</strong> There is no guarantee the workbench will continue
	 * running after it is returned.
	 * @see PlatformUI#isWorkbenchRunning()
	 */
	public static IWorkbench getWorkbench() {
		return PlatformUI.isWorkbenchRunning() ? PlatformUI.getWorkbench() : null;
	}


	// ********** editors **********

	/**
	 * Return the workbench's default editor for the specified file, as
	 * determined by the file's name <em>and</em> content.
	 * @see #getDefaultEditor(String, IContentType)
	 */
	public static IEditorDescriptor getDefaultEditor(IFile file) {
		return getDefaultEditor(file.getName(), IDE.getContentType(file));
	}

	/**
	 * Return the workbench's default editor for the specified file name and
	 * content type.
	 * @see #getWorkbench()
	 * @see IWorkbench#getEditorRegistry()
	 * @see org.eclipse.ui.IEditorRegistry#getDefaultEditor(String, IContentType)
	 */
	public static IEditorDescriptor getDefaultEditor(String fileName, IContentType contentType) {
		IWorkbench wb = getWorkbench();
		return (wb == null) ? null : wb.getEditorRegistry().getDefaultEditor(fileName, contentType);
	}


	// ********** shared images **********

	/**
	 * Return the specified image from the Eclipse UI {@link IWorkbench
	 * workbench}'s image registry.
	 * Return <code>null</code> if the workbench is not running.
	 * <p>
	 * <strong>NB:</strong> The returned image is managed by the workbench;
	 * clients must <em>not</em> dispose of the returned image.
	 * @see #getWorkbench()
	 * @see IWorkbench#getSharedImages()
	 * @see org.eclipse.ui.ISharedImages
	 */
	public static Image getSharedImage(String symbolicName) {
		IWorkbench wb = getWorkbench();
		return (wb == null) ? null : wb.getSharedImages().getImage(symbolicName);
	}

	/**
	 * Return the specified image descriptor from the Eclipse UI
	 * {@link IWorkbench workbench}'s image registry.
	 * Return <code>null</code> if the workbench is not running.
	 * @see #getWorkbench()
	 * @see IWorkbench#getSharedImages()
	 * @see org.eclipse.ui.ISharedImages
	 */
	public static ImageDescriptor getSharedImageDescriptor(String symbolicName) {
		IWorkbench wb = getWorkbench();
		return (wb == null) ? null : wb.getSharedImages().getImageDescriptor(symbolicName);
	}


	// ********** help **********

	/**
	 * Set the specified help context ID on the specified control.
	 * @see #getWorkbench()
	 * @see IWorkbench#getHelpSystem()
	 * @see org.eclipse.ui.help.IWorkbenchHelpSystem#setHelp(Control, String)
	 */
	public static void setHelp(Control control, String contextID) {
		IWorkbench wb = getWorkbench();
		if (wb != null) {
			wb.getHelpSystem().setHelp(control, contextID);
		}
	}

	/**
	 * Set the specified help context ID on the specified action.
	 * @see #getWorkbench()
	 * @see IWorkbench#getHelpSystem()
	 * @see org.eclipse.ui.help.IWorkbenchHelpSystem#setHelp(IAction, String)
	 */
	public static void setHelp(IAction action, String contextID) {
		IWorkbench wb = getWorkbench();
		if (wb != null) {
			wb.getHelpSystem().setHelp(action, contextID);
		}
	}

	/**
	 * Set the specified help context ID on the specified menu.
	 * @see #getWorkbench()
	 * @see IWorkbench#getHelpSystem()
	 * @see org.eclipse.ui.help.IWorkbenchHelpSystem#setHelp(Menu, String)
	 */
	public static void setHelp(Menu menu, String contextID) {
		IWorkbench wb = getWorkbench();
		if (wb != null) {
			wb.getHelpSystem().setHelp(menu, contextID);
		}
	}

	/**
	 * Set the specified help context ID on the specified control.
	 * @see #getWorkbench()
	 * @see IWorkbench#getHelpSystem()
	 * @see org.eclipse.ui.help.IWorkbenchHelpSystem#setHelp(MenuItem, String)
	 */
	public static void setHelp(MenuItem item, String contextID) {
		IWorkbench wb = getWorkbench();
		if (wb != null) {
			wb.getHelpSystem().setHelp(item, contextID);
		}
	}

	/**
	 * Display the entire help bookshelf.
	 * <p>
	 * <strong>NB:</strong> Must be called from the UI thread.
	 * <p>
	 * @see #getWorkbench()
	 * @see IWorkbench#getHelpSystem()
	 * @see org.eclipse.ui.help.IWorkbenchHelpSystem#displayHelp()
	 */
	public static void displayHelp() {
		IWorkbench wb = getWorkbench();
		if (wb != null) {
			wb.getHelpSystem().displayHelp();
		}
	}

	/**
	 * Display help for the specified context.
	 * <p>
	 * <strong>NB:</strong> Must be called from the UI thread.
	 * <p>
	 * @see #getWorkbench()
	 * @see IWorkbench#getHelpSystem()
	 * @see org.eclipse.ui.help.IWorkbenchHelpSystem#displayHelp(IContext)
	 */
	public static void displayHelp(IContext context) {
		IWorkbench wb = getWorkbench();
		if (wb != null) {
			wb.getHelpSystem().displayHelp(context);
		}
	}

	/**
	 * Display help for the specified context ID.
	 * <p>
	 * <strong>NB:</strong> Must be called from the UI thread.
	 * <p>
	 * @see #getWorkbench()
	 * @see IWorkbench#getHelpSystem()
	 * @see org.eclipse.ui.help.IWorkbenchHelpSystem#displayHelp(String)
	 */
	public static void displayHelp(String contextID) {
		IWorkbench wb = getWorkbench();
		if (wb != null) {
			wb.getHelpSystem().displayHelp(contextID);
		}
	}


	// ********** close views **********

	/**
	 * Close all the views in the platform workbench with the specified ID.
	 * @see #closeAllViews(IWorkbench, String)
	 * @see org.eclipse.ui.IWorkbenchPartSite#getId()
	 */
	public static void closeAllViews(String viewID) {
		IWorkbench wb = getWorkbench();
		if (wb != null) {
			closeAllViews(wb, viewID);
		}
	}

	/**
	 * Close all the views in the specified workbench with the specified ID.
	 * @see #closeAllViews(String)
	 */
	public static void closeAllViews(IWorkbench workbench, String viewID) {
		for (IWorkbenchWindow window : workbench.getWorkbenchWindows()) {
			WorkbenchWindowTools.closeAllViews(window, viewID);
		}
	}
 

	// ********** adapters **********

	/**
	 * Adapt the Eclipse UI {@link IWorkbench workbench} to the specified class.
	 * Return <code>null</code> if the workbench is not running
	 * (i.e. either it has not been started or it has been terminated).
	 * @see #getWorkbench()
	 * @see PlatformTools#getAdapter(Object, Class)
	 */
	public static <A> A getAdapter(Class<A> adapterType) {
		IWorkbench wb = getWorkbench();
		return (wb == null) ? null : PlatformTools.getAdapter(wb, adapterType);
	}

	/**
	 * Return the workbench service corresponding to the specified API.
	 * Return <code>null</code> if the workbench is not running
	 * (i.e. either it has not been started or it has been terminated).
	 * @see #getWorkbench()
	 * @see org.eclipse.ui.services.IServiceLocator#getService(Class)
	 */
	@SuppressWarnings("unchecked")
	public static <A> A getService(Class<A> api) {
		IWorkbench wb = getWorkbench();
		return (wb == null) ? null : (A) wb.getService(api);
	}


	// ********** disabled constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private WorkbenchTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
