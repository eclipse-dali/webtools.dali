/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.ui.internal.navigator;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jpt.common.ui.internal.jface.StaticItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFile;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaAttribute;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlEnum;
import org.eclipse.jpt.jaxb.eclipselink.ui.JptJaxbEclipseLinkUiImages;
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
			return new ELJaxbPersistentAttributeLabelProvider((JaxbPersistentAttribute) item, manager);
		}
		else if (item instanceof OxmFile) {
			return buildOxmFileLabelProvider((OxmFile) item, manager);
		}
		else if (item instanceof OxmXmlEnum) {
			return buildOxmXmlEnumLabelProvider((OxmXmlEnum) item, manager);
		}
		else if (item instanceof OxmJavaType) {
			return buildOxmJavaTypeLabelProvider((OxmJavaType) item, manager);
		}
		else if (item instanceof OxmJavaAttribute) {
			return buildOxmJavaAttributeLabelProvider((OxmJavaAttribute) item, manager);
		}
		
		return super.buildProvider(item, manager);
	}
	
	protected ItemExtendedLabelProvider buildOxmFileLabelProvider(OxmFile file, ItemExtendedLabelProvider.Manager manager) {
		return new StaticItemExtendedLabelProvider(
					JptJaxbEclipseLinkUiImages.OXM_FILE,
					buildOxmFileText(file),
					buildOxmFileDescription(file),
					manager
				);
	}
	
	private String buildOxmFileText(OxmFile file) {
		StringBuffer text = new StringBuffer();
		IPath path = file.getOxmResource().getFile().getRawLocation();
		text.append(path.lastSegment());
		text.append(" - "); //$NON-NLS-1$
		text.append(path.removeLastSegments(1).toOSString());
		return text.toString();
	}
	
	private String buildOxmFileDescription(OxmFile file) {
		// the same, for now
		return buildOxmFileText(file);
	}
	
	protected ItemExtendedLabelProvider buildOxmJavaTypeLabelProvider(
			OxmJavaType item, ItemExtendedLabelProvider.Manager manager) {
		return new OxmJavaTypeLabelProvider(item, manager);
	}
	
	protected ItemExtendedLabelProvider buildOxmXmlEnumLabelProvider(
			OxmXmlEnum item, ItemExtendedLabelProvider.Manager manager) {
		return new OxmXmlEnumLabelProvider(item, manager);
	}
	
	protected ItemExtendedLabelProvider buildOxmJavaAttributeLabelProvider(
			OxmJavaAttribute item, ItemExtendedLabelProvider.Manager manager) {
		return new OxmJavaAttributeLabelProvider(item, manager);
	}
}
