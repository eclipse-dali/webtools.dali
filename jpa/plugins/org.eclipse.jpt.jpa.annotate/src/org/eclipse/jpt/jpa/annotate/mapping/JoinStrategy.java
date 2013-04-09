/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/


package org.eclipse.jpt.jpa.annotate.mapping;

public enum JoinStrategy
{
    MAPPED_BY,    
    
    JOIN_COLUMNS,
        
    JOIN_TABLE,
    
	PRIMARY_KEY_JOIN_COLUMNS
}
