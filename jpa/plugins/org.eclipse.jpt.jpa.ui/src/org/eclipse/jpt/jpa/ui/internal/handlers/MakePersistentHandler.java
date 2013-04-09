/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.handlers;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.wizards.makepersistent.JpaMakePersistentWizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 *  MakePersistentHandler
 */
public class MakePersistentHandler extends AbstractHandler
{
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelectionChecked(event);
		
		for (Map.Entry<IProject, Set<IType>> entry : this.buildSelectedTypes(selection).entrySet()) {
			IProject project = entry.getKey();
			Set<IType> types = entry.getValue();
			JpaProject jpaProject = (JpaProject) project.getAdapter(JpaProject.class);
			if (jpaProject != null) {
				//open the wizard once for each selected project
				JpaMakePersistentWizard wizard = new JpaMakePersistentWizard(jpaProject, types);
				WizardDialog dialog = new WizardDialog(this.getCurrentShell(), wizard);
				dialog.create();
				dialog.open();
			}
		}
		return null;
	}

	/**
	 * Return a map containing lists of types, keyed by project.
	 * <p>
	 * The action is contributed for:<ul>
	 * <li>{@link IType}
	 * <li>{@link ICompilationUnit}
	 * <li>{@link IPackageFragment}
	 * <li>{@link IPackageFragmentRoot} that is a source folder
	 * </ul>
	 */
	private Map<IProject, Set<IType>> buildSelectedTypes(ISelection currentSelection) {
		if ( ! (currentSelection instanceof StructuredSelection)) {
			return Collections.emptyMap();
		}
		HashMap<IProject, Set<IType>> types = new HashMap<IProject, Set<IType>>();
		for (Object sel : ((StructuredSelection) currentSelection).toList()) {
			switch (((IJavaElement) sel).getElementType()) {
				case IJavaElement.PACKAGE_FRAGMENT_ROOT :
					this.addSelectedTypes((IPackageFragmentRoot) sel, types);
					break;
				case IJavaElement.PACKAGE_FRAGMENT :
					this.addSelectedTypes((IPackageFragment) sel, types);
					break;
				case IJavaElement.COMPILATION_UNIT :
					this.addSelectedTypes((ICompilationUnit) sel, types);
					break;
				case IJavaElement.TYPE :
					this.addSelectedType((IType) sel, types);
					break;
				default :
					break;
			}
		}
		return types;
	}
	
	private void addSelectedTypes(IPackageFragmentRoot packageFragmentRoot, Map<IProject, Set<IType>> types) {
		for (IJavaElement pkgFragment : this.getPackageFragments(packageFragmentRoot)) {
			this.addSelectedTypes((IPackageFragment) pkgFragment, types);
		}
	}
	
	private void addSelectedTypes(IPackageFragment packageFragment, Map<IProject, Set<IType>> types) {
		for (ICompilationUnit compUnit : this.getCompilationUnits(packageFragment)) {
			this.addSelectedTypes(compUnit, types);
		}
	}
	
	private void addSelectedTypes(ICompilationUnit compilationUnit, Map<IProject, Set<IType>> types) {
		IType primaryType = compilationUnit.findPrimaryType();
		if (primaryType != null) {
			this.addSelectedType(primaryType, types);
		}
	}

	private void addSelectedType(IType primaryType, Map<IProject, Set<IType>> typesMap) {
		IProject project = primaryType.getJavaProject().getProject();
		Set<IType> types = typesMap.get(project);
		if (types == null) {
			types = new HashSet<IType>();
			typesMap.put(project, types);
		}
		types.add(primaryType);
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
	
	private Shell getCurrentShell() {
	    return Display.getCurrent().getActiveShell();
	}

}
