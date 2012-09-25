/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.persistence.details;

import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.jface.ArchiveFileViewerFilter;
import org.eclipse.jpt.ui.internal.persistence.JptUiPersistenceMessages;
import org.eclipse.jpt.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.navigator.ResourceComparator;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;
import org.eclipse.wst.common.componentcore.resources.IVirtualContainer;
import org.eclipse.wst.common.componentcore.resources.IVirtualFile;
import org.eclipse.wst.common.componentcore.resources.IVirtualFolder;
import org.eclipse.wst.common.componentcore.resources.IVirtualResource;

public class ArchiveFileSelectionDialog 
	extends ElementTreeSelectionDialog
{
	private final WritablePropertyValueModel<String> jarPathModel;
	
	private DeploymentPathCalculator pathCalculator;
	
	
	public ArchiveFileSelectionDialog(Shell parent) {
		this(parent, new SimpleDeploymentPathCalculator());
	}
	
	public ArchiveFileSelectionDialog(Shell parent, DeploymentPathCalculator pathCalculator) {
		super(parent, new WorkbenchLabelProvider(), new WorkbenchContentProvider());
		this.pathCalculator = pathCalculator;
		setComparator(new ResourceComparator(ResourceComparator.NAME));
		addFilter(new ArchiveFileViewerFilter());
		setValidator(new ArchiveFileSelectionValidator());
		this.jarPathModel = new SimplePropertyValueModel<String>();
	}
	
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		
		Label helpLabel = new Label(composite, SWT.WRAP);
		helpLabel.setText(JptUiPersistenceMessages.ArchiveFileSelectionDialog_jarPathHelpLabel);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 400;
		helpLabel.setLayoutData(gd);
		
		Composite subComposite = new Composite(composite, SWT.NONE);
		subComposite.setLayout(new GridLayout(2, false));
		subComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label jarPathLabel = new Label(subComposite, SWT.NONE);
		jarPathLabel.setFont(composite.getFont());
		jarPathLabel.setText(JptUiPersistenceMessages.ArchiveFileSelectionDialog_jarPathLabel);
		
		Text jarPathText = new Text(subComposite, SWT.BORDER);
		jarPathText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		SWTTools.bind(jarPathModel, jarPathText);
		
		return composite;
	}
	
	@Override
	protected TreeViewer doCreateTreeViewer(Composite parent, int style) {
		TreeViewer treeViewer = super.doCreateTreeViewer(parent, style);
		
		treeViewer.addSelectionChangedListener(
			new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent event) {
					updateJarPathModel(event.getSelection());
				}
			});
		
		return treeViewer;
	}
	
	protected void updateJarPathModel(ISelection selection) {
		Object selectedObj = ((IStructuredSelection) selection).getFirstElement();
		if (selectedObj instanceof IFile) {
			this.jarPathModel.setValue(calculateDeployPath((IFile) selectedObj));
		}
		else {
			this.jarPathModel.setValue("");
		}
	}
	
	protected String calculateDeployPath(IFile archiveFile) {
		return this.pathCalculator.calculateDeploymentPath(archiveFile);
	}
	
	@Override
	protected void computeResult() {
		setResult(Collections.singletonList(this.jarPathModel.getValue()));
	}
	
	
	private static class ArchiveFileSelectionValidator 
		implements ISelectionStatusValidator 
	{
		public ArchiveFileSelectionValidator() {
			super();
		}
		
		
		public IStatus validate(Object[] selection) {
			int nSelected= selection.length;
			if (nSelected == 0 || (nSelected > 1)) {
				return new Status(IStatus.ERROR, JptUiPlugin.PLUGIN_ID, "");  //$NON-NLS-1$
			}
			for (int i= 0; i < selection.length; i++) {
				Object curr= selection[i];
				if (curr instanceof IFile) {
					return new Status(IStatus.OK, JptUiPlugin.PLUGIN_ID, "");  //$NON-NLS-1$
				}
			}
			return new Status(IStatus.ERROR, JptUiPlugin.PLUGIN_ID, "");  //$NON-NLS-1$
		}
	}	
	
	
	public static interface DeploymentPathCalculator
	{
		String calculateDeploymentPath(IFile file);
	}
	
	
	public static class SimpleDeploymentPathCalculator
		implements DeploymentPathCalculator
	{
		public String calculateDeploymentPath(IFile file) {
			return file.getName();
		}
	}
	
	
	public static class ModuleDeploymentPathCalculator
		extends SimpleDeploymentPathCalculator
	{
		@Override
		public String calculateDeploymentPath(IFile file) {
			// first look for virtual component that matches this file, returning
			// the path to that virtual component
			IVirtualComponent vComponent = ComponentCore.createComponent(file.getProject());
			if (vComponent != null) {
				IVirtualFolder vFolder = vComponent.getRootFolder();
				IVirtualFile vFile = findVirtualFile(vFolder, file);
				if (vFile != null) {
					return calculatePersistenceRootRelativePath(vFile);
				}
			}
			
			// then default to simple behavior
			return super.calculateDeploymentPath(file);
		}
		
		protected IVirtualFile findVirtualFile(IVirtualContainer vContainer, IFile realFile) {
			try { 
				for (IVirtualResource vResource : vContainer.members()) {
					if (vResource.getType() == IVirtualResource.FILE) {
						IVirtualFile vFile = (IVirtualFile) vResource;
						if (realFile.equals(vFile.getUnderlyingFile())) {
							return vFile;
						}
					}
					else {
						IVirtualFile vFile = findVirtualFile((IVirtualContainer) vResource, realFile);
						if (vFile != null) {
							return vFile;
						}
					}
				}
			}
			catch (CoreException ce) {
				JptUiPlugin.log(ce);
			}
			
			return null;
		}
		
		protected String calculatePersistenceRootRelativePath(IVirtualFile vFile) {
			IProject project = vFile.getProject();
			IPath puRootPath = JptCorePlugin.getJarDeploymentRootPath(project);
			
			IPath path = vFile.getRuntimePath().makeRelativeTo(puRootPath);
			
			return path.toString();
		}
	}
}
