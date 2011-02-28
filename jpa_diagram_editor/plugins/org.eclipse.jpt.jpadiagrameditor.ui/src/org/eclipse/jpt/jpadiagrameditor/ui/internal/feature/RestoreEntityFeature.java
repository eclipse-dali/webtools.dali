/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
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

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.ui.PlatformUI;


public class RestoreEntityFeature extends AbstractCustomFeature {
	
	public RestoreEntityFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	public boolean canExecute(ICustomContext context) {
		return true;
	}
	
	public void execute(ICustomContext context) {
		JavaPersistentType jpt = (JavaPersistentType)getFeatureProvider().getBusinessObjectForPictogramElement(context.getPictogramElements()[0]);
		if (JPAEditorUtil.isEntityOpenElsewhere(jpt, true)) {
			String shortEntName = JPAEditorUtil
					.cutFromLastDot(JpaArtifactFactory.instance()
							.getEntityName(jpt));
			String message = NLS.bind(
					JPAEditorMessages.JPASolver_closeWarningMsg, shortEntName);
			MessageDialog dialog = new MessageDialog(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(),
					JPAEditorMessages.JPASolver_closeEditors, null, message,
					MessageDialog.WARNING,
					new String[] { JPAEditorMessages.BTN_OK }, 0) {
				protected int getShellStyle() {
					return SWT.CLOSE | SWT.TITLE | SWT.BORDER
							| SWT.APPLICATION_MODAL | getDefaultOrientation();
				}
			};
			dialog.open();
			return;
		}
		JpaArtifactFactory.instance().restoreEntityClass(jpt, getFeatureProvider());		
	}
	
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}	

}
