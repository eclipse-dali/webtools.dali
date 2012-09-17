/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.core.resource.ResourceMappingFile;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.selection.JpaSelectionManager;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.IShowInTarget;
import org.eclipse.ui.part.ShowInContext;

/**
 * Factory to build Dali adapters for a {@link IViewPart}:<ul>
 * <li>{@link org.eclipse.ui.part.IShowInTarget}
 * </ul>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 */
public class ViewPartShowInAdapterFactory implements IAdapterFactory {

	private static final Class<?>[] ADAPTER_LIST = new Class[] { IShowInTarget.class};

	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}

	public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
		if (adaptableObject instanceof IViewPart) {
			return this.getAdapter((IViewPart) adaptableObject, adapterType);
		}
		return null;
	}

	private Object getAdapter(IViewPart viewPart, Class<?> adapterType) {
		if (adapterType == IShowInTarget.class) {
			return new ShowInTargetAdapter(viewPart);
		}
		return null;
	}

	// ********** Show In Target **********

	/**
	 * Adapt an #IViewPart to the #IShowInTarget interface
	 * 
	 * This adapter is currently implemented for JPA related views 
	 * such as #JpaDetailsView and #JpaStructureView. Users may 
	 * need to update the show() methods if they want to reuse 
	 * this adapter to support their own views.
	 */
	/* CU private */ static class ShowInTargetAdapter implements IShowInTarget {
		private IViewPart view;

		public ShowInTargetAdapter(IViewPart viewPart) {
			super();
			view = viewPart;
		}

		public boolean show(ShowInContext context) {
			IStructuredSelection selection = getSelection(context);
			if (selection != null && !selection.isEmpty()) {
				if (selection.getFirstElement() instanceof ICompilationUnit) {
					CompilationUnit compilationUnit = (CompilationUnit) selection.getFirstElement();
					this.show(compilationUnit);
				} 
				else if (selection.getFirstElement() instanceof IFile) {
					IFile file =	(IFile) selection.getFirstElement();
					this.show(file);
				}
				return true;
			}
			return false;
		}

		private void show(CompilationUnit compilationUnit) {
			try {
				this.openInEditor((IFile) compilationUnit.getTypeRoot().getCorrespondingResource());
			} catch (JavaModelException jme) {
				JptJpaUiPlugin.instance().logError(jme);
			}
			JpaProject jpaProject = (JpaProject) compilationUnit.getJavaProject().getProject().getAdapter(JpaProject.class);
			PersistenceUnit persistenceUnit = this.getPersistenceUnit(jpaProject);
			if (persistenceUnit != null) {
				String typeName = compilationUnit.getTypeRoot().findPrimaryType().getFullyQualifiedName();
				PersistentType persistentType =this.getPersistenceUnit(jpaProject).getPersistentType(typeName);		
				if (persistentType != null) {
					this.setSelection(persistentType);
				}
			}
		}

		private void show(IFile file) {
			this.openInEditor(file);
			JpaProject jpaProject = (JpaProject) file.getProject().getAdapter(JpaProject.class);
			IContentType contentType = PlatformTools.getContentType(file);
			if (contentType != null) {
				PersistenceUnit persistenceUnit = this.getPersistenceUnit(jpaProject);
				if (persistenceUnit != null) {
					if (contentType.isKindOf(ResourceMappingFile.Root.CONTENT_TYPE)) {
						for (MappingFileRef mappingFileRef : persistenceUnit.getMappingFileRefs()) {
							if (mappingFileRef.getMappingFile() != null &&
									mappingFileRef.getMappingFile().getResource().getProjectRelativePath().equals(file.getProjectRelativePath())) {
								this.setSelection(mappingFileRef.getMappingFile().getRoot());
							}
						}
					} else if (contentType.isKindOf(XmlPersistence.CONTENT_TYPE))  {
						// use persistence instead of persistence unit in order to set the selection to the root node
						this.setSelection(this.getPersistence(jpaProject));
					}
				}
			}
		}

		private IStructuredSelection getSelection(ShowInContext context) {
			if (context == null) {
				return StructuredSelection.EMPTY;
			}
			ISelection selection = context.getSelection();
			if (selection != null && !selection.isEmpty() && selection instanceof IStructuredSelection) {
				return (IStructuredSelection) selection;
			}
			Object input = context.getInput();
			if (input != null) {
				return new StructuredSelection(input);
			}
			return StructuredSelection.EMPTY;
		}

		private void openInEditor(IFile file) {
			IWorkbenchPage page = view.getSite().getPage();
			try {
				IDE.openEditor(page, file);
			} catch (PartInitException ex) {
				JptJpaUiPlugin.instance().logError(ex);
			}
		}

		private PersistenceUnit getPersistenceUnit(JpaProject jpaProject) {
			Persistence persistence = this.getPersistence(jpaProject);
			if (persistence == null) {
				return null;
			}
			if (persistence.getPersistenceUnitsSize() == 0) {
				return null;
			}
			return persistence.getPersistenceUnits().iterator().next();
		}

		private Persistence getPersistence(JpaProject jpaProject) {
			if (jpaProject == null) {
				return null;
			}
			PersistenceXml persistenceXml = jpaProject.getRootContextNode().getPersistenceXml();
			if (persistenceXml == null) {
				return null;
			}
			return persistenceXml.getRoot();
		}

		private void setSelection(JpaStructureNode node) {
			IWorkbenchPage page = view.getSite().getPage();
			JpaSelectionManager selectionManager = PlatformTools.getAdapter(page, JpaSelectionManager.class);
			selectionManager.setSelection(node);
		}
	}

}