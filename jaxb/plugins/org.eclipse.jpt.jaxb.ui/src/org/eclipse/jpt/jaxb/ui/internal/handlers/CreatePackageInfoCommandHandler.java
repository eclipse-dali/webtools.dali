/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.handlers;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.ui.internal.plugin.JptJaxbUiPlugin;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;


public class CreatePackageInfoCommandHandler
		extends AbstractHandler {
	
	private List<IFile> createdFiles = new Vector<IFile>();
	
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = 
			(IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);
		
		for (Iterator stream = selection.iterator(); stream.hasNext(); ) {
			createPackageInfo(stream.next());
		}
		
		for (IFile file : this.createdFiles) {
			IWorkbenchWindow activeWindow = HandlerUtil.getActiveWorkbenchWindow(event);
			BasicNewResourceWizard.selectAndReveal(file, activeWindow);
			try {
				IDE.openEditor(activeWindow.getActivePage(), file, true);
			}
			catch (PartInitException pie) {
				JptJaxbUiPlugin.instance().logError(pie);
			}
		}
		
		return null;
	}
	
	private void createPackageInfo(Object selectedObject) {
		JaxbPackage jaxbPackage = 
				(JaxbPackage) Platform.getAdapterManager().getAdapter(selectedObject, JaxbPackage.class);
		
		IPackageFragment jdtPackage = getJdtPackage(jaxbPackage);
		try {
			ICompilationUnit cu = jdtPackage.createCompilationUnit("package-info.java", "package " + jdtPackage.getElementName() + ";", true, null);
			this.createdFiles.add((IFile) cu.getCorrespondingResource());
		}
		catch (JavaModelException jme) {
			JptJaxbUiPlugin.instance().logError(jme);
		}
	}
	
	private IPackageFragment getJdtPackage(JaxbPackage jaxbPackage) {
		return (IPackageFragment) IterableTools.get(
				jaxbPackage.getContextRoot().getJavaTypes(jaxbPackage), 0).
					getJavaResourceType().getJavaResourceCompilationUnit().getCompilationUnit().getParent();
	}
}
