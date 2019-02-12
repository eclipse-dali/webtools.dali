/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2013 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
import org.eclipse.graphiti.ui.editor.IDiagramContainerUI;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.dialog.SelectTypeDialog;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
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
		if ((bo == null) || (!(bo instanceof PersistentAttribute)))
				return;
		PersistentAttribute jpa = (PersistentAttribute)bo;
		String typeName = JPAEditorUtil.getAttributeTypeNameWithGenerics(jpa);
		String msg = MessageFormat.format(JPAEditorMessages.SelectTypeDialog_chooseAttributeTypeDialogText, 
				jpa.getName(), JPAEditorUtil.returnSimpleName(((PersistentType) jpa.getParent()).getName()));		
		SelectTypeDialog d = new SelectTypeDialog(msg, typeName);
		if (d.open() != IDialogConstants.OK_ID)
			return;
		String newTypeName = d.getTypeName();
		String[] attributeTypeTypeNames = JPAEditorUtil.getGenericsElementTypes(newTypeName);
		if (attributeTypeTypeNames != null)
			newTypeName = newTypeName.substring(0, newTypeName.indexOf('<')).trim();
		
		getFeatureProvider().addAddIgnore((PersistentType) jpa.getParent(), jpa.getName());
		getFeatureProvider().addRemoveIgnore((PersistentType) jpa.getParent(), jpa.getName());
		
		String mappingKey = jpa.getMappingKey();

		List<String> annotations = JpaArtifactFactory.instance().getAnnotationStrings(jpa);
		JpaArtifactFactory.instance().deleteAttribute((PersistentType) jpa.getParent(), jpa.getName(),
				getFeatureProvider());

		PersistentAttribute newAt = JpaArtifactFactory.instance().makeNewAttribute((PersistentType) jpa.getParent(),
				jpa.getName(), newTypeName, jpa.getName(), newTypeName, attributeTypeTypeNames, annotations, false);
		
		getFeatureProvider().replaceAttribute(jpa, newAt);
		
		JpaArtifactFactory.instance().addOrmPersistentAttribute((PersistentType) newAt.getParent(), newAt, mappingKey);
		
        IWorkbenchSite ws = ((IDiagramContainerUI)getFeatureProvider().getDiagramTypeProvider().getDiagramBehavior().getDiagramContainer()).getSite();
        ICompilationUnit cu = getFeatureProvider().getCompilationUnit((PersistentType) newAt.getParent());
        getFeatureProvider().getJPAEditorUtil().formatCode(cu, ws);	
        JPAEditorUtil.organizeImports(cu, ws);
		JpaArtifactFactory.instance().remakeRelations(getFeatureProvider(), 
				((Shape)pe).getContainer(), (PersistentType) newAt.getParent());
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
