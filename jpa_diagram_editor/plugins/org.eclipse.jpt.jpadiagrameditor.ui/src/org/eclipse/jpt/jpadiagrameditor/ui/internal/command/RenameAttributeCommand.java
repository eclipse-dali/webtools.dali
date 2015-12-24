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
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.refactoring.RenameSupport;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmManagedType;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;

public class RenameAttributeCommand implements Command {
	
	private PersistentType jpt;
	private String oldName;
	private String newName;
	
	
	public RenameAttributeCommand(PersistentType jpt, String oldName,
			String newName){
		
		super();
		this.jpt = jpt;
		this.oldName = oldName;
		this.newName = newName;		
	}

	public void execute() {
		try {			
			renameAttribute(jpt, oldName, newName);			
			boolean isMethodAnnotated = JpaArtifactFactory.instance().isMethodAnnotated(jpt);
				
			ICompilationUnit jptCompilationUnit = JPAEditorUtil.getCompilationUnit(jpt);
			renameAttribute(jptCompilationUnit, oldName, newName, isMethodAnnotated);
			
			
			JavaResourceType jrt = jpt.getJavaResourceType();
			jrt.getJavaResourceCompilationUnit().synchronizeWithJavaSource();

		} catch (Exception e) {
			JPADiagramEditorPlugin.logError("Cannot rename attribute", e); //$NON-NLS-1$
		}
	}

	private void renameAttribute(PersistentType jpt, String oldName, String newName) {
		MappingFileRef mapFileRef = JpaArtifactFactory.instance().getOrmXmlByForPersistentType(jpt);
		if(mapFileRef != null) {
			EntityMappings root = (EntityMappings) mapFileRef.getMappingFile().getRoot();
			Iterator<OrmManagedType> managedTypesIter = root.getManagedTypes().iterator();
			while(managedTypesIter.hasNext()) {
				XmlTypeMapping xmlType = (XmlTypeMapping) managedTypesIter.next().getXmlManagedType();
				if(xmlType.getAttributes() == null)
					return;
				List<XmlAttributeMapping> attributeMappings  = xmlType.getAttributes().getAttributeMappings();
				for(XmlAttributeMapping attr : attributeMappings){
					if(attr.getName().equals(oldName)){
						attr.setName(newName);
					}
				}
			}
		}
	}
	
	private void renameAttribute(ICompilationUnit cu, String oldName,
			String newName, boolean isMethodAnnotated) throws Exception {
		
		IJavaProject jp = JavaCore.create(jpt.getJpaProject().getProject());
		IType javaType = jp.findType(jpt.getName());
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
		String methodName = getterPrefix + JPAEditorUtil.capitalizeFirstLetter(oldName);
		IMethod getter = javaType.getMethod(methodName, new String[0]);
		if (!getter.exists()) {
			getterPrefix = "is";	//$NON-NLS-1$
		}
		methodName = getterPrefix + JPAEditorUtil.capitalizeFirstLetter(oldName);
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
