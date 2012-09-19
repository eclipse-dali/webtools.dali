/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.ui.internal;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jpt.common.ui.internal.jface.StaticItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFile;
import org.eclipse.jpt.jaxb.eclipselink.ui.internal.plugin.JptJaxbEclipseLinkUiPlugin;
import org.eclipse.jpt.jaxb.ui.internal.jaxb21.GenericJaxb_2_1_NavigatorItemLabelProviderFactory;


public class ELJaxbNavigatorItemLabelProviderFactory
		extends GenericJaxb_2_1_NavigatorItemLabelProviderFactory {
	
	private static ELJaxbNavigatorItemLabelProviderFactory INSTANCE = new ELJaxbNavigatorItemLabelProviderFactory();
	
	
	public static GenericJaxb_2_1_NavigatorItemLabelProviderFactory instance() {
		return INSTANCE;
	}
	
	
	private ELJaxbNavigatorItemLabelProviderFactory() {
		super();
	}
	
	
	@Override
	public ItemExtendedLabelProvider buildProvider(Object item, ItemExtendedLabelProvider.Manager manager) {
		
		if (item instanceof JaxbPersistentAttribute) {
			return new ELJaxbPersistentAttributeItemLabelProvider((JaxbPersistentAttribute) item, manager);
		}
		else if (item instanceof OxmFile) {
			return buildOxmFileLabelProvider((OxmFile) item, manager);
		}
		
		return super.buildProvider(item, manager);
	}
	
	protected ItemExtendedLabelProvider buildOxmFileLabelProvider(OxmFile file, ItemExtendedLabelProvider.Manager manager) {
		return new StaticItemExtendedLabelProvider(
					JptJaxbEclipseLinkUiPlugin.instance().getImage(JptJaxbEclipseLinkUiIcons.OXM_FILE),
					buildOxmFileText(file),
					buildOxmFileDescription(file)
				);
	}
	
	private String buildOxmFileText(OxmFile file) {
		StringBuffer text = new StringBuffer();
		IPath path = file.getOxmResource().getFile().getRawLocation();
		text.append(path.lastSegment());
		text.append(" - ");
		text.append(path.removeLastSegments(1).toOSString());
		return text.toString();
	}
	
	private String buildOxmFileDescription(OxmFile file) {
		// the same, for now
		return buildOxmFileText(file);
	}
}
