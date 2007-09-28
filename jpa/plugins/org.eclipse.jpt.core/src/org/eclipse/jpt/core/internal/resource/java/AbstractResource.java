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

import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.jdtutility.Member;

public abstract class AbstractResource<E extends Member> implements JavaResource
{
	//TODO eventually this should be a jpaProject or a reference to the parent with a 
	//way to go up the containment hierarchy to the project and its platform
	private IJpaPlatform jpaPlatform;
	
	private final E member;
	
	protected AbstractResource(E member, IJpaPlatform jpaPlatform) {
		super();
		this.member = member;
		this.jpaPlatform = jpaPlatform;
	}
	
	public E getMember() {
		return this.member;
	}
	
	public IJpaPlatform jpaPlatform() {
		return this.jpaPlatform;
	}
}
