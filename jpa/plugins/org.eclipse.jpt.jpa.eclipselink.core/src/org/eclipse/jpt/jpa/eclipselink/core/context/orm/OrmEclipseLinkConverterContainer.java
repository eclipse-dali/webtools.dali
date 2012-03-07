/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.orm;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.OrmEclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.OrmEclipseLinkObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.OrmEclipseLinkStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.OrmEclipseLinkTypeConverter;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * EclipseLink <code>orm.xml</code> converter container
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 2.1
 */
public interface OrmEclipseLinkConverterContainer
	extends EclipseLinkConverterContainer, XmlContextNode
{

	ListIterable<OrmEclipseLinkCustomConverter> getCustomConverters();
	OrmEclipseLinkCustomConverter addCustomConverter(int index);
	OrmEclipseLinkCustomConverter addCustomConverter();

	ListIterable<OrmEclipseLinkObjectTypeConverter> getObjectTypeConverters();
	OrmEclipseLinkObjectTypeConverter addObjectTypeConverter(int index);
	OrmEclipseLinkObjectTypeConverter addObjectTypeConverter();
	
	ListIterable<OrmEclipseLinkStructConverter> getStructConverters();
	OrmEclipseLinkStructConverter addStructConverter(int index);
	OrmEclipseLinkStructConverter addStructConverter();

	ListIterable<OrmEclipseLinkTypeConverter> getTypeConverters();
	OrmEclipseLinkTypeConverter addTypeConverter(int index);
	OrmEclipseLinkTypeConverter addTypeConverter();


	// ********** refactoring **********

	Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName);
	Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage);
	Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName);


	interface Owner {
		int getNumberSupportedConverters();
	}
}
