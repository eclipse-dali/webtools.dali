/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;


public interface NamedQueries extends ContainerAnnotation<NestableNamedQuery>
{
	String ANNOTATION_NAME = JPA.NAMED_QUERIES;

	String NAMED_QUERIES_LIST = "namedQueriesList";
}
