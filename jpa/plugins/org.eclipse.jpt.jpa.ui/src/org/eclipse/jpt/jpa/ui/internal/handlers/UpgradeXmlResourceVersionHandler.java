/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.core.resource.xml.ERootObject;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Handler used to upgrade the version of a JptXmlResource
 * when the selected object adapts to an <code>JptXmlResource</code>.
 * See org.eclipse.jpt.jpa.ui/plugin.xml
 */
public class UpgradeXmlResourceVersionHandler
	extends AbstractHandler
{
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);

		for (Object selectedObject : selection.toArray()) {
			upgradeXmlResourceVersion(selectedObject);
		}
		return null;
	}

	protected void upgradeXmlResourceVersion(Object selectedObject) {
		upgradeXmlResourceVersion(this.adaptSelection(selectedObject));
	}

	protected JptXmlResource adaptSelection(Object selectedObject) {
		return PlatformTools.getAdapter(selectedObject, JptXmlResource.class);
	}


	protected static void upgradeXmlResourceVersion(JptXmlResource xmlResource) {
		ERootObject root = xmlResource.getRootObject();
		IContentType contentType = xmlResource.getContentType();
		JpaProject jpaProject = getJpaProject(xmlResource);
		if (jpaProject != null) {
			String newVersion = jpaProject.getJpaPlatform().getMostRecentSupportedResourceType(contentType).getVersion();
			root.setDocumentVersion(newVersion);
			xmlResource.save();
		}
	}

	private static JpaProject getJpaProject(JptXmlResource xmlResource) {
		return (JpaProject) xmlResource.getFile().getProject().getAdapter(JpaProject.class);
	}
}
