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
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchSite;

public class DeleteAttributeCommand implements Command {
	
	private JavaPersistentType jpt;
	private String attributeName;
	private IJPAEditorFeatureProvider fp;
	
	public DeleteAttributeCommand(JavaPersistentType jpt, String attributeName,
							IJPAEditorFeatureProvider fp) {
		super();
		this.jpt = jpt;
		this.attributeName = attributeName;
		this.fp = fp;		
	}

	public void execute() {
		synchronized (jpt) {
			String attrNameWithCapitalLetter = attributeName.substring(0, 1)
					.toUpperCase(Locale.ENGLISH)
					+ attributeName.substring(1);
			ICompilationUnit compUnit = fp.getCompilationUnit(jpt);		
			IType javaType = compUnit.findPrimaryType();
			JavaPersistentAttribute jpa = jpt.getAttributeNamed(attributeName);

			String typeSignature = null;
			String getterPrefix = "get"; 			//$NON-NLS-1$
			String methodName = getterPrefix + attrNameWithCapitalLetter; 
			IMethod getAttributeMethod = javaType.getMethod(methodName,
					new String[0]);
			if (!getAttributeMethod.exists()) {
								
				String typeName = jpa.getResourceAttribute().getTypeBinding().getQualifiedName();
				if ("boolean".equals(typeName)) {										//$NON-NLS-1$
					getterPrefix = "is";												//$NON-NLS-1$
					methodName = getterPrefix + attrNameWithCapitalLetter; 				
					getAttributeMethod = javaType.getMethod(methodName,
							new String[0]);
				}		
				try {
					if ((getAttributeMethod != null) && getAttributeMethod.exists());
						typeSignature = getAttributeMethod.getReturnType();
				} catch (JavaModelException e1) {
					JPADiagramEditorPlugin.logError("Cannot obtain the type of the getter with name " + methodName + "()", e1); 	//$NON-NLS-1$	//$NON-NLS-2$
				}			
			}
			if (typeSignature == null)
			 	methodName = null;		
			
			boolean isMethodAnnotated = JpaArtifactFactory.instance()
					.isMethodAnnotated(jpt);
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
				IMethod setAttributeMethod = javaType.getMethod(methodName,
						new String[] { typeSignature });
				if ((setAttributeMethod != null) && setAttributeMethod.exists())
					setAttributeMethod.delete(true, new NullProgressMonitor());
			} catch (Exception e) {
				JPADiagramEditorPlugin.logError("Cannot remove the attribute setter with name " + methodName + "(...)", e); //$NON-NLS-1$ //$NON-NLS-2$	
			}

			IWorkbenchSite ws = ((IEditorPart) fp.getDiagramTypeProvider().getDiagramEditor()).getSite();
			JPAEditorUtil.organizeImports(compUnit, ws);
			
			this.jpt.getJavaResourceType().getJavaResourceCompilationUnit().synchronizeWithJavaSource();
			
		}
	}
	
}

