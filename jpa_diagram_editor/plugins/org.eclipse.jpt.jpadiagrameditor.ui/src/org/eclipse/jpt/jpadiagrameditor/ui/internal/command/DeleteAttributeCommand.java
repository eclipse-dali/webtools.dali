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

import java.util.Locale;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.graphiti.ui.editor.IDiagramContainerUI;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.ui.IWorkbenchSite;

public class DeleteAttributeCommand implements Command {
	
	private PersistentType jpt;
	private String attributeName;
	private IJPAEditorFeatureProvider fp;
	private ICompilationUnit unit;
	
	public DeleteAttributeCommand(ICompilationUnit unit, PersistentType jpt, String attributeName,
							IJPAEditorFeatureProvider fp) {
		super();
		this.jpt = jpt;
		this.unit = unit;
		this.attributeName = attributeName;
		this.fp = fp;		
	}

	public void execute() {
		boolean isMethodAnnotated = false;
		if(jpt != null) {
			
			JpaArtifactFactory.instance().removeOrmPersistentAttribute(jpt, attributeName);
			
			unit = fp.getCompilationUnit(jpt);		
			isMethodAnnotated = JpaArtifactFactory.instance()
				.isMethodAnnotated(jpt);
		}
			
		IType javaType = unit.findPrimaryType();

		String attrNameWithCapitalLetter = attributeName.substring(0, 1)
				.toUpperCase(Locale.ENGLISH) + attributeName.substring(1);
			
		String typeSignature = null;
		String getterPrefix = "get"; 			//$NON-NLS-1$
		String methodName = getterPrefix + attrNameWithCapitalLetter;
		IMethod getAttributeMethod = javaType.getMethod(methodName,
				new String[0]);
		if (!getAttributeMethod.exists()) {
			getterPrefix = "is";	//$NON-NLS-1$
		}
		methodName = getterPrefix + attrNameWithCapitalLetter; 	//$NON-NLS-1$
		getAttributeMethod = javaType.getMethod(methodName, new String[0]);
		try {
			if ((getAttributeMethod != null) && getAttributeMethod.exists())
				typeSignature = getAttributeMethod.getReturnType();
		} catch (JavaModelException e1) {
			JPADiagramEditorPlugin.logError("Cannot obtain the type of the getter with name " + methodName + "()", e1); 	//$NON-NLS-1$	//$NON-NLS-2$
		}
			
		if (typeSignature == null)
		 	methodName = null;		
			
		if (isMethodAnnotated) {
			try {
				IField attributeField = javaType.getField(attributeName);
					
				if ((attributeField != null) && !attributeField.exists())
					attributeField = javaType.getField(JPAEditorUtil.revertFirstLetterCase(attributeName));
				if ((attributeField != null) && attributeField.exists()) 
					attributeField.delete(true, new NullProgressMonitor());
			} catch (JavaModelException e) {
				JPADiagramEditorPlugin.logError("Cannot remove the attribute field with name " + attributeName, e); 	//$NON-NLS-1$	
			} 
			try {
				methodName = getterPrefix + attrNameWithCapitalLetter; //$NON-NLS-1$
				if (getAttributeMethod != null) {
					typeSignature = getAttributeMethod.getReturnType();
					if (getAttributeMethod.exists())
						getAttributeMethod.delete(true, new NullProgressMonitor());
				}
			} catch (JavaModelException e) {
				JPADiagramEditorPlugin.logError("Cannot remove the attribute getter with name " + methodName + "()", e); 	//$NON-NLS-1$	 //$NON-NLS-2$
			} 	
		} else {
			try {
				methodName = getterPrefix + attrNameWithCapitalLetter; //$NON-NLS-1$
				if (getAttributeMethod.exists()) {
					typeSignature = getAttributeMethod.getReturnType();
					getAttributeMethod.delete(true, new NullProgressMonitor());
				}
			} catch (JavaModelException e) {
				JPADiagramEditorPlugin.logError("Cannot remove the attribute getter with name " + methodName + "()", e); 	//$NON-NLS-1$	 //$NON-NLS-2$
			} 	
			try {
				IField attributeField = javaType.getField(this.attributeName);
				if (attributeField != null)
					if (!attributeField.exists())
						attributeField = javaType.getField(JPAEditorUtil.revertFirstLetterCase(attributeName));			
				if ((attributeField != null) && attributeField.exists())
					attributeField.delete(true, new NullProgressMonitor());
			} catch (JavaModelException e) {
				JPADiagramEditorPlugin.logError("Cannot remove the attribute field with name " + attributeName, e); 	//$NON-NLS-1$	
			} 			
		}
		try {
			methodName = "set" + attrNameWithCapitalLetter; //$NON-NLS-1$
			if(typeSignature != null) {
				IMethod setAttributeMethod = javaType.getMethod(methodName,
						new String[] { typeSignature });
				if ((setAttributeMethod != null) && setAttributeMethod.exists())
					setAttributeMethod.delete(true, new NullProgressMonitor());
			}
		} catch (Exception e) {
			JPADiagramEditorPlugin.logError("Cannot remove the attribute setter with name " + methodName + "(...)", e); //$NON-NLS-1$ //$NON-NLS-2$	
		}

		IWorkbenchSite ws = ((IDiagramContainerUI)fp.getDiagramTypeProvider().getDiagramBehavior().getDiagramContainer()).getSite();
		JPAEditorUtil.organizeImports(unit, ws);
			
		if(jpt != null) {
			jpt.getJpaProject().getContextModelRoot().synchronizeWithResourceModel();
			JavaResourceType jrt = jpt.getJavaResourceType();
			jrt.getJavaResourceCompilationUnit().synchronizeWithJavaSource();
			jpt.update();
		}
	}
	
}

