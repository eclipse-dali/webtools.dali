/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class OrderByTranslator extends Translator implements OrmXmlMapper
{
	public OrderByTranslator() {
		super(ORDER_BY, JpaCoreMappingsPackage.eINSTANCE.getIMultiRelationshipMapping_OrderBy(), END_TAG_NO_INDENT);
	}
}
