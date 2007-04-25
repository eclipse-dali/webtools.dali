/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import org.eclipse.jpt.core.internal.IMappingKeys;


public class XmlEmbeddableProvider implements IXmlTypeMappingProvider
{

	public XmlTypeMapping buildTypeMapping() {
		return OrmFactory.eINSTANCE.createXmlEmbeddable();
	}

	public String key() {
		return IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY;
	}
}
