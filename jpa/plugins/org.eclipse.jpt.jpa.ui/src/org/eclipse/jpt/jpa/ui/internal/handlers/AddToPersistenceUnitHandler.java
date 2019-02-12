/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.handlers;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.ui.internal.swt.widgets.DisplayTools;
import org.eclipse.jpt.common.ui.internal.utility.SynchronousUiCommandContext;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaProjectManager;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.ui.handlers.HandlerUtil;

public class AddToPersistenceUnitHandler extends AbstractHandler
{
	private IFile persistenceXmlFile;

	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelectionChecked(event);

		for (Map.Entry<IProject, Set<IType>> entry : this.buildSelectedTypes(selection).entrySet()) {
			IProject project = entry.getKey();
			Set<IType> types = entry.getValue();
			JpaProject jpaProject = (JpaProject) project.getAdapter(JpaProject.class);
			if (jpaProject != null) {
				try {
					PersistenceXml persistenceXml = jpaProject.getContextRoot().getPersistenceXml();
					if (persistenceXml == null) {
						MessageDialog.openInformation(DisplayTools.getShell(), JptJpaUiMessages.ADD_CLASSES_TO_PERSISTENCE_UNIT_MESSAGE_TITLE, JptJpaUiMessages.ADD_CLASSES_TO_PERSISTENCE_UNIT_MESSAGE_TEXT);
					} else {
						persistenceXmlFile = (IFile) persistenceXml.getResource();
						IRunnableWithProgress runnable = new AddClassesRunnable(persistenceXmlFile, convertToTypeNames(types));
						this.buildProgressMonitorDialog().run(true, true, runnable);  // true => fork; true => cancellable
					}
				} catch (InvocationTargetException ex) {
					JptJpaUiPlugin.instance().logError(ex);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
					JptJpaUiPlugin.instance().logError(ex);
				}
			}
		}
		return null;
	}
	
	protected Iterable<String> convertToTypeNames(Iterable<IType> types) {
		return new TransformationIterable<IType, String>(types, new NameTransformer());
	}
	
	/* CU private */ class NameTransformer
		extends TransformerAdapter<IType, String> {
		@Override
		public String transform(IType type) {
			return type.getFullyQualifiedName('$');
		}
	}

	// ********** selected types **********

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

	private ProgressMonitorDialog buildProgressMonitorDialog() {
		return new ProgressMonitorDialog(null);
	}

	// ********** add classes runnable **********

	/**
	 * This is dispatched to the progress monitor dialog.
	 */
	/* CU private */ static class AddClassesRunnable implements IRunnableWithProgress
	{
		private final IFile persistenceXmlFile;
		private final Iterable<String> typeNames;

		AddClassesRunnable(IFile persistenceXmlFile, Iterable<String> typeNames) {
			super();
			this.persistenceXmlFile = persistenceXmlFile;
			this.typeNames = typeNames;
		}

		public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			try {
				this.run_(monitor);
			} catch (CoreException ex) {
				throw new InvocationTargetException(ex);
			}
		}

		private void run_(IProgressMonitor monitor) throws CoreException {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			// lock the entire project, since we might modify the metamodel classes
			ISchedulingRule rule = workspace.getRuleFactory().modifyRule(this.persistenceXmlFile.getProject());
			workspace.run(
					new AddClassesWorkspaceRunnable(this.persistenceXmlFile, typeNames),
					rule,
					IWorkspace.AVOID_UPDATE,
					monitor
					);
		}
	}


	// ********** add classes workspace runnable **********

	/**
	 * This is dispatched to the Eclipse workspace.
	 */
	/* CU private */ static class AddClassesWorkspaceRunnable implements IWorkspaceRunnable
	{
		private final IFile persistenceXmlFile;
		private final Iterable<String> typeNames;

		AddClassesWorkspaceRunnable(IFile persistenceXmlFile, Iterable<String> typeNames) {
			super();
			this.persistenceXmlFile = persistenceXmlFile;
			this.typeNames = typeNames;
		}

		public void run(IProgressMonitor monitor) throws CoreException {
			if (monitor.isCanceled()) {
				return;
			}
			SubMonitor sm = SubMonitor.convert(monitor, JptJpaUiMessages.ADD_CLASSES_TO_PERSISTENCE_UNIT_TASK_NAME, 20);

			JpaProject jpaProject = this.getJpaProject();
			if (jpaProject == null) {
				return;
			}

			JptXmlResource resource = jpaProject.getPersistenceXmlResource();
			if (resource == null) {
				// the resource can be null if the persistence.xml file has an invalid content type
				return;
			}

			if (sm.isCanceled()) {
				return;
			}
			sm.worked(1);

			PersistenceXml persistenceXml = jpaProject.getContextRoot().getPersistenceXml();
			if (persistenceXml == null) {
				return;  // unlikely...
			}

			Persistence persistence = persistenceXml.getRoot();
			if (persistence == null) {
				return;  // unlikely...
			}

			PersistenceUnit persistenceUnit = (persistence.getPersistenceUnitsSize() == 0) ?
					persistence.addPersistenceUnit() :
						persistence.getPersistenceUnit(0);
					if (sm.isCanceled()) {
						return;
					}
					sm.worked(1);

					Command addClassesCommand = new AddClassesCommand(persistenceUnit, typeNames, sm.newChild(17));
					JpaProjectManager mgr = this.getJpaProjectManager();
					try {
						if (mgr != null) {
							mgr.execute(addClassesCommand, SynchronousUiCommandContext.instance());
						}
					} catch (InterruptedException ex) {
						Thread.currentThread().interrupt();
						throw new RuntimeException(ex);
					}

					resource.save();
					sm.worked(1);
		}

		private JpaProjectManager getJpaProjectManager() {
			return (JpaProjectManager) this.getWorkspace().getAdapter(JpaProjectManager.class);
		}

		private IProject getProject() {
			return this.persistenceXmlFile.getProject();
		}

		private JpaProject getJpaProject() {
			return (JpaProject) this.getProject().getAdapter(JpaProject.class);
		}

		private IWorkspace getWorkspace() {
			return this.persistenceXmlFile.getWorkspace();
		}
	}


	// ********** add classes command **********

	/**
	 * This is dispatched to the JPA project manager.
	 */
	/* CU private */ static class AddClassesCommand implements Command
	{
		private final PersistenceUnit persistenceUnit;
		private final Iterable<String> typeNames;
		private final IProgressMonitor monitor;

		AddClassesCommand(PersistenceUnit persistenceUnit, Iterable<String> typeNames, IProgressMonitor monitor) {
			super();
			this.persistenceUnit = persistenceUnit;
			this.typeNames = typeNames;
			this.monitor = monitor;
		}

		public void execute() {
			this.persistenceUnit.addClasses(typeNames, this.monitor);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.persistenceUnit);
		}
	}
}
