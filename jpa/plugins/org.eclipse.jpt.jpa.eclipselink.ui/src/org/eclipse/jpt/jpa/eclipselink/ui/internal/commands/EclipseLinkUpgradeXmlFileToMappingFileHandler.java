/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.jpa.core.context.XmlFile;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Handler used to upgrade an orm.xml file to an eclipselink orm.xml file
 * when the selected object adapts to an <code>XmlFile</code>.
 * See org.eclipse.jpt.jpa.eclipselink.ui/plugin.xml
 */
public class EclipseLinkUpgradeXmlFileToMappingFileHandler
	extends AbstractHandler
{

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection 	= (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);

		for (Object selectedObject : selection.toArray()) {
			this.upgradeToEclipseLinkMappingFile(selectedObject);
		}
		return null;
	}

	protected void upgradeToEclipseLinkMappingFile(Object selectedObject) {
		UpgradeToEclipseLinkMappingFileXmlResourceHandler.upgradeToEclipseLinkMappingFile(this.adaptSelection(selectedObject));
	}

	protected JptXmlResource adaptSelection(Object selectedObject) {
		XmlFile xmlFile = PlatformTools.getAdapter(selectedObject, XmlFile.class);
		return xmlFile.getXmlResource();
	}
}
