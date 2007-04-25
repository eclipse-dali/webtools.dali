/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import org.eclipse.wst.common.internal.emf.resource.TranslatorResource;


public interface OrmXmlResource extends TranslatorResource
{
	/**
	 * Return the root object
	 */
	EntityMappingsInternal getXmlFileContent();
}
