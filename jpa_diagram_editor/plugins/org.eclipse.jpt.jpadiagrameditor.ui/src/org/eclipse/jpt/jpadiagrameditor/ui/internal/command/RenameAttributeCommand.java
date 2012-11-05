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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.refactoring.RenameSupport;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;

public class RenameAttributeCommand implements Command {
	
	private JavaPersistentType jpt;
	private String oldName;
	private String newName;
	private IJPAEditorFeatureProvider fp;
	
	
	public RenameAttributeCommand(JavaPersistentType jpt, String oldName,
			String newName, IJPAEditorFeatureProvider fp){
		
		super();
		this.jpt = jpt;
		this.oldName = oldName;
		this.newName = newName;
		this.fp = fp;
		
	}

	public void execute() {
		ICompilationUnit cu = fp.getCompilationUnit(jpt);
		try {
			renameAttribute(cu, oldName, this.newName, fp, JpaArtifactFactory.instance().isMethodAnnotated(jpt));
			jpt.getJavaResourceType().getJavaResourceCompilationUnit().synchronizeWithJavaSource();
		} catch (InterruptedException e) {
			JPADiagramEditorPlugin.logError("Cannot rename attribute", e); //$NON-NLS-1$
		}
	}
	
	private void renameAttribute(ICompilationUnit cu, String oldName,
			String newName, IJPAEditorFeatureProvider fp, boolean isMethodAnnotated) throws InterruptedException {
		IType javaType = cu.findPrimaryType();
		if (javaType == null)
			return;
		IField attributeField = null;
		String typeSignature = null;
		if (isMethodAnnotated) {
			attributeField = javaType.getField(oldName);
			if (!attributeField.exists())
				attributeField =  javaType.getField(JPAEditorUtil.revertFirstLetterCase(oldName));
		} else {
			attributeField = javaType.getField(oldName);
		}
		String getterPrefix = "get";	//$NON-NLS-1$
		String methodName = getterPrefix + JPAEditorUtil.capitalizeFirstLetter(oldName); 	//$NON-NLS-1$
		IMethod getter = javaType.getMethod(methodName, new String[0]);
		if (!getter.exists()) {
			getterPrefix = "is";	//$NON-NLS-1$
		}
		methodName = getterPrefix + JPAEditorUtil.capitalizeFirstLetter(oldName); 	//$NON-NLS-1$
		getter = javaType.getMethod(methodName, new String[0]);		
		
		if (isMethodAnnotated) {
			try {
				typeSignature = getter.getReturnType();
			} catch (JavaModelException e1) {
				JPADiagramEditorPlugin.logError("Cannot obtain type signature of the getter of the attribute " + oldName, e1); //$NON-NLS-1$  
				return;
			}
			if ((typeSignature == null) || 
					(!"Z".equals(typeSignature) && !getterPrefix.equals("get"))) {		//$NON-NLS-1$ 	//$NON-NLS-2$ 
				JPADiagramEditorPlugin.logError("Cannot obtain type signature of the getter of the attribute " + oldName, new NullPointerException()); //$NON-NLS-1$  
				return;
			}
		} else {
			try {
				typeSignature = attributeField.getTypeSignature();
			} catch (JavaModelException e) {
				JPADiagramEditorPlugin.logError("Cannot obtain type signature of the field of the attribute " + oldName, e); //$NON-NLS-1$  
				return;
			}			
		}

		methodName = "set" + JPAEditorUtil.capitalizeFirstLetter(oldName); //$NON-NLS-1$
		IMethod setter = javaType.getMethod(methodName,
				new String[] { typeSignature });

		if (setter.exists())
			renameSetter(setter, newName);
		if (isMethodAnnotated) {
			if (attributeField.exists())
				renameField(attributeField, newName, isMethodAnnotated);			
			if (getter.exists())
				renameGetter(getter, newName);
		} else {
			if (getter.exists())
				renameGetter(getter, newName);
			if (attributeField.exists())
				renameField(attributeField, newName, isMethodAnnotated);
		}
	}
	
	private void renameField(IField field, String newName, boolean isMethodAnnotated) throws InterruptedException {
		if (!field.exists())
			return;
		String oldName = field.getElementName();
		if (oldName.equals(newName))
			return;
		try {
			RenameSupport s = RenameSupport.create(field, 
					   isMethodAnnotated ? JPAEditorUtil.decapitalizeFirstLetter(newName) : newName,
					   RenameSupport.UPDATE_REFERENCES);
			try {
				IWorkbenchWindow ww = JPADiagramEditorPlugin.getDefault()
						.getWorkbench().getActiveWorkbenchWindow();
				Shell sh = ww.getShell();
				s.perform(sh, ww);
			} catch (InvocationTargetException e) {
				JPADiagramEditorPlugin.logError("Cannot rename the field of the attribute " + oldName, e); //$NON-NLS-1$  
			}
		} catch (CoreException e1) {
			JPADiagramEditorPlugin.logError("Cannot rename the field of the attribute " + oldName, e1); //$NON-NLS-1$  
		}
	}

	private void renameGetter(IMethod getter, String newName) throws InterruptedException {
		if (!getter.exists())
			return;
		String oldName = getter.getElementName();
		String getterType = null;
		try {
			getterType = getter.getReturnType();
		} catch (JavaModelException e2) {
			JPADiagramEditorPlugin.logError("Can't obtain getter type", e2); //$NON-NLS-1$  
		}
		String newGetterName = ("Z".equals(getterType) ? "is" : "get") +		//$NON-NLS-1$	//$NON-NLS-2$	//$NON-NLS-3$
			JPAEditorUtil.capitalizeFirstLetter(newName);
		if (oldName.equals(newGetterName))
			return;
		try {
			RenameSupport s = RenameSupport.create(getter, newGetterName,
					RenameSupport.UPDATE_REFERENCES);
			try {
				IWorkbenchWindow ww = JPADiagramEditorPlugin.getDefault()
						.getWorkbench().getActiveWorkbenchWindow();
				Shell sh = ww.getShell();
				s.perform(sh, ww);
			} catch (InvocationTargetException e) {
				JPADiagramEditorPlugin.logError("Cannot rename the getter of the attribute " + oldName, e); //$NON-NLS-1$
			}
		} catch (CoreException e1) {
			JPADiagramEditorPlugin.logError("Cannot rename the getter of the attribute " + oldName, e1); //$NON-NLS-1$
		}
	}
		
	private void renameSetter(IMethod setter, String newName) throws InterruptedException {
		if (!setter.exists())
			return;
		String oldName = setter.getElementName();
		String newSetterName = "set"			//$NON-NLS-1$
			+ JPAEditorUtil.capitalizeFirstLetter(newName);
		if (oldName.equals(newSetterName))
			return;
		try {
			RenameSupport s = RenameSupport.create(setter, newSetterName,
					RenameSupport.UPDATE_REFERENCES);
			try {
				IWorkbenchWindow ww = JPADiagramEditorPlugin.getDefault()
						.getWorkbench().getActiveWorkbenchWindow();
				Shell sh = ww.getShell();
				s.perform(sh, ww);
			} catch (InvocationTargetException e) {
				JPADiagramEditorPlugin.logError("Cannot rename the setter of the attribute " + oldName, e); //$NON-NLS-1$
			}
		} catch (CoreException e1) {
			JPADiagramEditorPlugin.logError("Cannot rename the setter of the attribute " + oldName, e1); //$NON-NLS-1$
		}
	}

}
