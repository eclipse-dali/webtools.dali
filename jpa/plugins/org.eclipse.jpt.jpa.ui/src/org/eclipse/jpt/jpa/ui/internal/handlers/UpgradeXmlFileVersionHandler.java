/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.jpa.core.context.XmlFile;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Handler used to upgrade the version of a JptXmlResource
 * when the selected object adapts to an <code>XmlFile</code>.
 * See org.eclipse.jpt.jpa.ui/plugin.xml
 */
public class UpgradeXmlFileVersionHandler
	extends AbstractHandler
{
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);

		for (Object selectedObject : selection.toArray()) {
			this.upgradeXmlResourceVersion(selectedObject);
		}
		return null;
	}

	protected void upgradeXmlResourceVersion(Object selectedObject) {
		UpgradeXmlResourceVersionHandler.upgradeXmlResourceVersion(this.adaptSelection(selectedObject));
	}

	protected JptXmlResource adaptSelection(Object selectedObject) {
		XmlFile xmlFile = PlatformTools.getAdapter(selectedObject, XmlFile.class);
		return xmlFile.getXmlResource();
	}

}
