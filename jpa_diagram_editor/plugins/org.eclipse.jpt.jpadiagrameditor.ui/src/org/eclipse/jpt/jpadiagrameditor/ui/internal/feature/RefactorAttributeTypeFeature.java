/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2012 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.feature;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.dialog.SelectTypeDialog;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchSite;

public class RefactorAttributeTypeFeature extends AbstractCustomFeature {
	
	public RefactorAttributeTypeFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}
			
	
	public void execute(ICustomContext context) {
		PictogramElement pe = context.getPictogramElements()[0];
		Object bo = getFeatureProvider().getBusinessObjectForPictogramElement(pe);
		if ((bo == null) || (!(bo instanceof JavaSpecifiedPersistentAttribute)))
				return;
		JavaSpecifiedPersistentAttribute jpa = (JavaSpecifiedPersistentAttribute)bo;
		String typeName = JPAEditorUtil.getAttributeTypeNameWithGenerics(jpa);
		String msg = MessageFormat.format(JPAEditorMessages.SelectTypeDialog_chooseAttributeTypeDialogText, 
				jpa.getName(), JPAEditorUtil.returnSimpleName(((PersistentType)jpa.getParent()).getName()));		
		SelectTypeDialog d = new SelectTypeDialog(msg, typeName);
		if (d.open() != IDialogConstants.OK_ID)
			return;
		String newTypeName = d.getTypeName();
		String[] attributeTypeTypeNames = JPAEditorUtil.getGenericsElementTypes(newTypeName);
		if (attributeTypeTypeNames != null)
			newTypeName = newTypeName.substring(0, newTypeName.indexOf('<')).trim();
		
		getFeatureProvider().addAddIgnore((JavaPersistentType)jpa.getParent(), jpa.getName());
		JavaResourceAttribute jra = jpa.getResourceAttribute();
		getFeatureProvider().addRemoveIgnore((JavaPersistentType)jpa.getParent(), jra.getName());

		List<String> annotations = JpaArtifactFactory.instance().getAnnotationStrings(jpa);
		JpaArtifactFactory.instance().deleteAttribute((JavaPersistentType)jpa.getParent(), jpa.getName(),
				getFeatureProvider());
		
		JavaSpecifiedPersistentAttribute newAt = JpaArtifactFactory.instance().makeNewAttribute(getFeatureProvider(), (JavaPersistentType)jpa.getParent(),
				null, jpa.getName(), newTypeName, jpa.getName(), newTypeName, attributeTypeTypeNames, annotations, false);
		
		getFeatureProvider().replaceAttribute(jpa, newAt);
		
        IWorkbenchSite ws = ((IEditorPart)getDiagramEditor()).getSite();
        ICompilationUnit cu = getFeatureProvider().getCompilationUnit((JavaPersistentType)newAt.getParent());
        getFeatureProvider().getJPAEditorUtil().formatCode(cu, ws);	
        JPAEditorUtil.organizeImports(cu, ws);
		JpaArtifactFactory.instance().remakeRelations(getFeatureProvider(), 
				((Shape)pe).getContainer(), (JavaPersistentType)newAt.getParent());
	}
	
	@Override
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider) super.getFeatureProvider();
	}
	
	@Override
	public String getName() {
		return JPAEditorMessages.RefactorAttributeTypeFeature_ContextMenuOperationDescription;
	}
	

}
