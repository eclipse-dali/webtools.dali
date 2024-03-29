/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2012, 2015 SAP AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Petya Sabeva - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/

package org.eclipse.jpt.jpadiagrameditor.ui.internal.command;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.refactoring.RenameSupport;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;

public class RenameEntityCommand implements Command {
	
	private PersistentType jpt;
	private String newEntityName;
	
	public RenameEntityCommand(PersistentType jpt, String newEntityName){
		super();
		this.jpt = jpt;
		this.newEntityName = newEntityName;
	}

	public void execute() {
		renameEntityClass(JPAEditorUtil.getCompilationUnit(jpt), newEntityName);
		JavaResourceType jrt = jpt.getJavaResourceType();
		jrt.getJavaResourceCompilationUnit().synchronizeWithJavaSource();
	}
	
	private void renameEntityClass(ICompilationUnit cu, String newName) {
		IJavaProject jp = JavaCore.create(jpt.getJpaProject().getProject());
		try {
			IType javaType = jp.findType(jpt.getName());
			renameType(javaType, newName);

		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void renameType(IType type, String newName) {
		if (!type.exists())
			return;
		String oldName = type.getElementName();
		try {
			RenameSupport s = RenameSupport.create(type, newName, RenameSupport.UPDATE_REFERENCES);
			IWorkbenchWindow ww = JPADiagramEditorPlugin.getDefault()
					.getWorkbench().getActiveWorkbenchWindow();
			Shell sh = ww.getShell();
			s.perform(sh, ww);
		} catch (Exception e1) {
			JPADiagramEditorPlugin.logError("Cannot rename the type " + oldName, e1); //$NON-NLS-1$
		}
	}
	
}
