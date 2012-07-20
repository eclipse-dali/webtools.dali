/*******************************************************************************
* Copyright (c) 2010, 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.wizards.JpaMakePersistentWizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;


public class MakePersistentAction implements IObjectActionDelegate {

	private ISelection selection;
	
	public MakePersistentAction() {
		super();
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// do nothing		
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	// Action is contributed for IType, ICompilationUnit, IPackageFragment, and IPackageFragmentRoot that is a source folder
	protected Map<IProject, List<IType>> buildSelectedTypes() {
		Map<IProject, List<IType>> types = new HashMap<IProject, List<IType>>();
		if (this.selection instanceof StructuredSelection) {			
			for (Object selection : ((StructuredSelection) this.selection).toList()) {
				switch (((IJavaElement) selection).getElementType()) {
					case IJavaElement.TYPE :
						addSelectedType((IType) selection, types);
						break;
					case IJavaElement.COMPILATION_UNIT :
						addSelectedType((ICompilationUnit) selection, types);
						break;
					case IJavaElement.PACKAGE_FRAGMENT :
						addSelectedType((IPackageFragment) selection, types);
						break;
					case IJavaElement.PACKAGE_FRAGMENT_ROOT :
						addSelectedType((IPackageFragmentRoot) selection, types);
						break;
					default :
						break;
				}
			}
		}
		return types;
	}

	private void addSelectedType(IPackageFragmentRoot packageFragmentRoot, Map<IProject, List<IType>> types) {
		for (IJavaElement pkgFragment : getPackageFragments(packageFragmentRoot)) {
			addSelectedType((IPackageFragment) pkgFragment, types);
		}
	}

	private void addSelectedType(IPackageFragment packageFragment, Map<IProject, List<IType>> types) {
		for (ICompilationUnit compUnit : getCompilationUnits(packageFragment)) {
			addSelectedType(compUnit, types);
		}
	}
	
	private void addSelectedType(ICompilationUnit compilationUnit, Map<IProject, List<IType>> types) {
		IType primaryType = this.getPrimaryType(compilationUnit);
		if (primaryType != null) {
			this.addSelectedType(primaryType, types);
		}
	}
	
	private void addSelectedType(IType primaryType, Map<IProject, List<IType>> typesMap) {
		IProject project = primaryType.getJavaProject().getProject();
		List<IType> types = typesMap.get(project);
		if (types == null) {
			types = new ArrayList<IType>();
			typesMap.put(project, types);
		}
		if (!types.contains(primaryType)) {
			types.add(primaryType);
		}
	}

	private ICompilationUnit[] getCompilationUnits(IPackageFragment packageFragment) {
		try {
			return packageFragment.getCompilationUnits();
		}
		catch (JavaModelException e) {
			JptJpaUiPlugin.instance().logError(e);
		}
		return new ICompilationUnit[0];
	}
	
	private IJavaElement[] getPackageFragments(IPackageFragmentRoot packageFragmentRoot) {
		try {
			return packageFragmentRoot.getChildren();
		}
		catch (JavaModelException e) {
			JptJpaUiPlugin.instance().logError(e);
		}
		return new IJavaElement[0];
	}

	private IType getPrimaryType(ICompilationUnit compilationUnit) {
		return compilationUnit.findPrimaryType();
	}

	public void run(IAction action) {
		for (List<IType> types : this.buildSelectedTypes().values()) {
			IProject project = types.get(0).getResource().getProject();
			JpaProject jpaProject = (JpaProject) project.getAdapter(JpaProject.class);
			if (jpaProject != null) {
				//open the wizard once for each selected project
				JpaMakePersistentWizard wizard = new JpaMakePersistentWizard(jpaProject, types);
				WizardDialog dialog = new WizardDialog(this.getCurrentShell(), wizard);
				dialog.create();
				dialog.open();
			}
		}
	}
	
	private Shell getCurrentShell() {
	    return Display.getCurrent().getActiveShell();
	}
}
