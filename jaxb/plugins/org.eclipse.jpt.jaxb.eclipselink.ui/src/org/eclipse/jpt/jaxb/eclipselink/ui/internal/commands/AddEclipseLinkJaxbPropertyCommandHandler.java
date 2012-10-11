/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.ui.internal.commands;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.resource.jaxbprops.JaxbPropertiesResource;
import org.eclipse.jpt.jaxb.eclipselink.ui.internal.plugin.JptJaxbEclipseLinkUiPlugin;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;


public class AddEclipseLinkJaxbPropertyCommandHandler
		extends AbstractHandler {
	
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = 
			(IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);
		IWorkbenchWindow activeWindow = HandlerUtil.getActiveWorkbenchWindow(event);
		
		for (Iterator stream = selection.iterator(); stream.hasNext(); ) {
			addEclipseLinkJaxbProperty(stream.next(), activeWindow);
		}
		
		return null;
	}
	
	protected void addEclipseLinkJaxbProperty(Object selectedObject, IWorkbenchWindow activeWindow) {
		JaxbPackage jaxbPackage = 
				(JaxbPackage) Platform.getAdapterManager().getAdapter(selectedObject, JaxbPackage.class);
		
		JaxbPropertiesResource jaxbProperties = jaxbPackage.getJaxbProject().getJaxbPropertiesResource(jaxbPackage.getName());
		
		if (jaxbProperties == null) {
			createJaxbPropertiesWithProperty(jaxbPackage, activeWindow);
		}
		else {
			addPropertyToExistingProperties(jaxbPackage, activeWindow);
		}
	}
	
	protected void createJaxbPropertiesWithProperty(
			final JaxbPackage jaxbPackage, final IWorkbenchWindow activeWindow) {
		final IFolder folder;
		try {
			folder = getFolder(jaxbPackage);
		}
		catch (JavaModelException jme) {
			JptJaxbEclipseLinkUiPlugin.instance().logError(jme);
			return;
		}
		Job job = new Job("Creating jaxb.properties") {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					IFile file = folder.getFile("jaxb.properties");
					InputStream stream = createInputStream(createNewProperties());
					try {
						file.create(stream, true, null);
					}
					catch (CoreException ce) {
						JptJaxbEclipseLinkUiPlugin.instance().logError(ce);
						return JptJaxbEclipseLinkUiPlugin.instance().buildErrorStatus(ce, "Could not create jaxb.properties.");
					}
					AddEclipseLinkJaxbPropertyCommandHandler.this.revealAndOpenFile(file, activeWindow);
					return Status.OK_STATUS;
				}
			};
		job.setRule(folder);
		job.schedule();
	}
	
	protected void addPropertyToExistingProperties(
			final JaxbPackage jaxbPackage, final IWorkbenchWindow activeWindow) {
		final IFolder folder;
		try {
			folder = getFolder(jaxbPackage);
		}
		catch (JavaModelException jme) {
			JptJaxbEclipseLinkUiPlugin.instance().logError(jme);
			return;
		}
		Job job = new Job("Adding property to jaxb.properties.") {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					IFile file = folder.getFile("jaxb.properties");
					Properties properties = loadProperties(file);
					if (properties == null) {
						return JptJaxbEclipseLinkUiPlugin.instance().buildErrorStatus("Could not load jaxb.properties.");
					}
					adjustProperties(properties);
					InputStream stream = createInputStream(properties);
					try {
						file.setContents(stream, true, true, null);
					}
					catch (CoreException ce) {
						JptJaxbEclipseLinkUiPlugin.instance().logError(ce);
						return JptJaxbEclipseLinkUiPlugin.instance().buildErrorStatus(ce, "Could not load jaxb.properties.");
					}
					AddEclipseLinkJaxbPropertyCommandHandler.this.revealAndOpenFile(file, activeWindow);
					return Status.OK_STATUS;
				}
			};
		job.setRule(folder);
		job.schedule();
	}
	
	protected IFolder getFolder(JaxbPackage jaxbPackage) throws JavaModelException {
		for (IPackageFragment pkgFragment : jaxbPackage.getJaxbProject().getJavaProject().getPackageFragments()) {
			if (pkgFragment.getElementName().equals(jaxbPackage.getName())) {
				return (IFolder) pkgFragment.getUnderlyingResource();
			}
		}
		return null;
	}
	
	protected Properties createNewProperties() {
		Properties properties = new Properties();
		adjustProperties(properties);
		return properties;
	}
	
	protected Properties loadProperties(IFile file) {
		Properties properties = new Properties();
		InputStream stream = null;
		try {
			properties.load(file.getContents());
		}
		catch (Exception ex) {
			JptJaxbEclipseLinkUiPlugin.instance().logError(ex);
			return null;
		}
		finally {
			closeStream(stream);
		}
		return properties;
	}
	
	protected void adjustProperties(Properties properties) {
		String factoryProp = "javax.xml.bind.context.factory";
		String factoryPropValue = "org.eclipse.persistence.jaxb.JAXBContextFactory";
		properties.setProperty(factoryProp, factoryPropValue);
	}
	
	protected InputStream createInputStream(Properties properties) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		try {
			properties.store(outStream, null);
		}
		catch (Exception e) {
			JptJaxbEclipseLinkUiPlugin.instance().logError(e);
			return new ByteArrayInputStream(new byte[0]);
		}
		finally {
			closeStream(outStream);
		}
		
		return new ByteArrayInputStream(outStream.toByteArray());
	}
	
	protected void closeStream(Closeable stream) {
		try {
			if (stream != null) {
				stream.close();
			}
		} catch (IOException ex) {
			JptJaxbEclipseLinkUiPlugin.instance().logError(ex);
		}
	}
	
	protected void revealAndOpenFile(
			final IFile file, final IWorkbenchWindow activeWindow) {
		Job job = new UIJob("Opening jaxb.properties.") {
				@Override
				public IStatus runInUIThread(IProgressMonitor monitor) {
					BasicNewResourceWizard.selectAndReveal(file, activeWindow);
					try {
						IDE.openEditor(activeWindow.getActivePage(), file, true);
					}
					catch (PartInitException pie) {
						JptJaxbEclipseLinkUiPlugin.instance().logError(pie);
						return JptJaxbEclipseLinkUiPlugin.instance().buildErrorStatus(pie, "Could not open jaxb.properties in editor.");
					}
					return Status.OK_STATUS;
				}
			};
		job.setRule(file.getParent());
		job.schedule();
	}
}
