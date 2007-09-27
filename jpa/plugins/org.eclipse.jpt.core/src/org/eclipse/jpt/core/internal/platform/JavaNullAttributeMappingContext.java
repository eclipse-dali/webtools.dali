/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.List;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class JavaNullAttributeMappingContext extends JavaAttributeContext
{
	public JavaNullAttributeMappingContext(
			IContext parentContext, JavaNullAttributeMapping javaNullAttributeMapping) {
		super(parentContext, javaNullAttributeMapping);
	}
	
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
	}
}
