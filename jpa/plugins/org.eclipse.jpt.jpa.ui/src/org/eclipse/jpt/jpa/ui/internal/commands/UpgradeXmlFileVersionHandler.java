/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.core.resource.xml.ERootObject;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.XmlFile;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * See org.eclipse.jpt.jpa.ui/plugin.xml
 */
public class UpgradeXmlFileVersionHandler
	extends AbstractHandler
{
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection 
			= (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);

		for (Object selectedObject : selection.toArray()) {
			upgradeXmlFileVersion(selectedObject);
		}
		return null;
	}

	protected void upgradeXmlFileVersion(Object selectedObject) {
		JptXmlResource xmlResource = PlatformTools.getAdapter(selectedObject, JptXmlResource.class);
		if (xmlResource == null) {
			XmlFile xmlFile = PlatformTools.getAdapter(selectedObject, XmlFile.class);
			if (xmlFile != null) {
				xmlResource = xmlFile.getXmlResource();
			}
		}
		if (xmlResource == null) {
			return;
		}

		ERootObject root = xmlResource.getRootObject();
		IContentType contentType = xmlResource.getContentType();
		JpaProject jpaProject = this.getJpaProject(xmlResource.getFile().getProject());
		String newVersion = jpaProject.getJpaPlatform().getMostRecentSupportedResourceType(contentType).getVersion();
		root.setVersion(newVersion);
		xmlResource.save();
	}

	private JpaProject getJpaProject(IProject project) {
		return (JpaProject) project.getAdapter(JpaProject.class);
	}
}
