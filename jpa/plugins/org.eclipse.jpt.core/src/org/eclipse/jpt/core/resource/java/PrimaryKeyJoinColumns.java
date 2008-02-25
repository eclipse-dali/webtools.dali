/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;


public interface PrimaryKeyJoinColumns extends ContainerAnnotation<NestablePrimaryKeyJoinColumn>
{
	String ANNOTATION_NAME = JPA.PRIMARY_KEY_JOIN_COLUMNS;

	String PK_JOIN_COLUMNS_LIST = "pkJoinColumnsList";
}
