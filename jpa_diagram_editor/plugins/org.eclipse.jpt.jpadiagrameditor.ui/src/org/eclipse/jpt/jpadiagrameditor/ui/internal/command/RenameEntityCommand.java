/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2012 SAP AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Petya Sabeva - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/

package org.eclipse.jpt.jpadiagrameditor.ui.internal.command;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.ui.refactoring.RenameSupport;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;

public class RenameEntityCommand implements Command {
	
	private JavaPersistentType jpt;
	private String newEntityName;
	private IJPAEditorFeatureProvider fp;
	
	public RenameEntityCommand(JavaPersistentType jpt, String newEntityName, IJPAEditorFeatureProvider fp){
		super();
		this.jpt = jpt;
		this.newEntityName = newEntityName;
		this.fp = fp;
	}

	public void execute() {
		renameEntityClass(this.fp.getCompilationUnit(this.jpt), this.newEntityName);
		this.jpt.getJavaResourceType().getJavaResourceCompilationUnit().synchronizeWithJavaSource();
	}
	
	private void renameEntityClass(ICompilationUnit cu, String newName) {
		IType javaType = cu.findPrimaryType();
		renameType(javaType, newName);
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
