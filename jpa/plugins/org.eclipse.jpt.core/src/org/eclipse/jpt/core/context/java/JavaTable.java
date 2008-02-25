/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;

public interface JavaTable extends Table, JavaJpaContextNode
{
	void initializeFromResource(JavaResourcePersistentMember persistentResource);
	
	void update(JavaResourcePersistentMember persistentResource);
}