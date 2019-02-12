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

import java.util.Locale;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.graphiti.ui.editor.IDiagramContainerUI;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
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
	
	public DeleteAttributeCommand(PersistentType jpt, String attributeName,
							IJPAEditorFeatureProvider fp) {
		super();
		this.jpt = jpt;
		this.attributeName = attributeName;
		this.fp = fp;		
	}

	public void execute() {
		JpaArtifactFactory.instance().removeOrmPersistentAttribute(jpt, attributeName);

		try {
		IJavaProject jp = JavaCore.create(jpt.getJpaProject().getProject());
		IType javaType = jp.findType(jpt.getName());
		
		String attrNameWithCapitalLetter = attributeName.substring(0, 1)
				.toUpperCase(Locale.ENGLISH) + attributeName.substring(1);
			
		IMethod getAttributeMethod = findGetterMethod(javaType,
				attrNameWithCapitalLetter);
		
		String typeSignature = getReturnedType(getAttributeMethod);

		deleteGetterMethod(typeSignature, getAttributeMethod);
		deleteField(javaType);
		deleteSetterMethod(javaType, attrNameWithCapitalLetter,
					typeSignature);

		IWorkbenchSite ws = ((IDiagramContainerUI) fp.getDiagramTypeProvider().getDiagramBehavior().getDiagramContainer()).getSite();
		JPAEditorUtil.organizeImports(javaType.getCompilationUnit(), ws);

		jpt.getJavaResourceType().getJavaResourceCompilationUnit().synchronizeWithJavaSource();
		
		} catch (JavaModelException e) {
			JPADiagramEditorPlugin
					.logError(
							"Cannnot delete attribute with name " + attributeName, e); //$NON-NLS-1$				
		}

	}

	private String getReturnedType(IMethod getAttributeMethod)
			throws JavaModelException {
		String typeSignature = null;
		if ((getAttributeMethod != null) && getAttributeMethod.exists()) {
			typeSignature = getAttributeMethod.getReturnType();
		}
		return typeSignature;
	}

	private IMethod findGetterMethod(IType javaType,
			String attrNameWithCapitalLetter) {
		String getterPrefix = "get"; 			//$NON-NLS-1$
		String methodName = getterPrefix + attrNameWithCapitalLetter;
		IMethod getAttributeMethod = javaType.getMethod(methodName,
				new String[0]);
		if (!getAttributeMethod.exists()) {
			getterPrefix = "is";	//$NON-NLS-1$
		}
		methodName = getterPrefix + attrNameWithCapitalLetter; 	//$NON-NLS-1$
		getAttributeMethod = javaType.getMethod(methodName, new String[0]);
		return getAttributeMethod;
	}

	private void deleteSetterMethod(IType javaType,
			String attrNameWithCapitalLetter, String typeSignature)
			throws JavaModelException {
		String methodName = "set" + attrNameWithCapitalLetter; //$NON-NLS-1$
		if(typeSignature != null) {
			IMethod setAttributeMethod = javaType.getMethod(methodName,
					new String[] { typeSignature });
			if ((setAttributeMethod != null) && setAttributeMethod.exists())
				setAttributeMethod.delete(true, new NullProgressMonitor());
		}
	}

	private void deleteGetterMethod(String typeSignature, IMethod getAttributeMethod) throws JavaModelException {
		if (getAttributeMethod != null && getAttributeMethod.exists()) {
			getAttributeMethod.delete(true, new NullProgressMonitor());
		}
	}

	private void deleteField(IType javaType) throws JavaModelException {
		IField attributeField = javaType.getField(attributeName);
		if (attributeField != null) {
			if (!attributeField.exists()) {
				attributeField = javaType.getField(JPAEditorUtil.revertFirstLetterCase(attributeName));
			}
			attributeField.delete(true, new NullProgressMonitor());
		}
	}
	
}

